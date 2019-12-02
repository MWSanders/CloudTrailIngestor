package edu.mines.utils;

import edu.mines.model.OperationExercisedActions;
import edu.mines.model.SlidingWindowPeriod;
import edu.mines.model.evaluation.EvaluationTrialRunWeekdayHandler;
import org.mongodb.morphia.Datastore;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Created by Matt on 1/22/2017.
 */
public class DateUtils {

    public static SlidingWindowPeriod adjustSinglePeriod(SlidingWindowPeriod period, int windowIncrement, EvaluationTrialRunWeekdayHandler genModel) {
        ZonedDateTime obsStart = period.getObsStart();
        ZonedDateTime obsEndOprStart = period.getObsEndOprStart();
        ZonedDateTime oprEnd = period.getOprEnd();
        int daysLimit = 121;
        String weekdayHandling = genModel.getWeekdayHandling();

        if (weekdayHandling.equals("filler_weekend")) {
            daysLimit = 35;
        } else if (weekdayHandling.equals("filler_weekday")) {
            daysLimit = 87;
        }
        if (genModel.getGenMethod().startsWith("decision"))
            daysLimit = 7;
        while (zonedDateTimeDifference(obsStart, obsEndOprStart, ChronoUnit.DAYS) > daysLimit) {
            System.out.println(zonedDateTimeDifference(obsStart, obsEndOprStart, ChronoUnit.DAYS));
            obsStart = obsStart.plusDays(windowIncrement);
        }
        return new SlidingWindowPeriod(obsStart, obsEndOprStart, oprEnd);
    }

    static int countWeekdays(ZonedDateTime start, ZonedDateTime end, String weekdayHandling) {
        ZonedDateTime currentDay = ZonedDateTime.ofInstant(start.toInstant(), start.getZone());
        int weekdays = 0;
        int weekends = 0;
        while (currentDay.compareTo(end) < 0) {
            if (currentDay.getDayOfWeek().getValue() <= 5)
                weekdays++;
            else
                weekends++;
            currentDay = currentDay.plusDays(1);
        }
        if (weekdayHandling.contains("weekday"))
            return weekdays;
        if (weekdayHandling.contains("weekend"))
            return weekends;
        return weekdays + weekdays;
    }

    static long zonedDateTimeDifference(ZonedDateTime d1, ZonedDateTime d2, ChronoUnit unit) {
        long dayDiff = unit.between(d1, d2);
        return dayDiff;
    }

    public static ArrayList<SlidingWindowPeriod> generateWindowPeriods(ZonedDateTime startZ, ZonedDateTime endZ, int observationDays, int operationDays,
                                                                       int windowIncrement) {
        ArrayList<SlidingWindowPeriod> slidingWindowPeriods = new ArrayList<>();
        ZonedDateTime obsStart = startZ;
        ZonedDateTime obsEndOprStart = startZ.plusDays(observationDays);
        ZonedDateTime oprEnd = obsEndOprStart.plusDays(operationDays);

        SlidingWindowPeriod firstPeriod = new SlidingWindowPeriod(obsStart, obsEndOprStart, oprEnd);
        slidingWindowPeriods.add(firstPeriod);

        while (oprEnd.toEpochSecond() < endZ.toEpochSecond()) {
            obsStart = obsStart.plusDays(windowIncrement);
            obsEndOprStart = obsEndOprStart.plusDays(windowIncrement);
            oprEnd = oprEnd.plusDays(windowIncrement);
            slidingWindowPeriods.add(new SlidingWindowPeriod(obsStart, obsEndOprStart, oprEnd));
        }
        return slidingWindowPeriods;
    }

    public static ArrayList<OperationExercisedActions> generateSegmentedObservationPeriod(String observationPeriodKey, String dayTypeHandling, Datastore ds) {
        ZonedDateTime startTime = OperationExercisedActions.getStartFromKey(observationPeriodKey);
        ZonedDateTime endTime = OperationExercisedActions.getEndFromKey(observationPeriodKey);
        long trainDays = startTime.until(endTime, ChronoUnit.DAYS);
        ArrayList<OperationExercisedActions> segregatedObservationPeriod = new ArrayList<>();
        if (dayTypeHandling.contains("filler")) {
            while (segregatedObservationPeriod.size() < trainDays) {
                String currentKey = "From " + endTime.minusDays(1).toString() + " to " + endTime;
                OperationExercisedActions currentPeriod = ds.get(OperationExercisedActions.class, currentKey);
                if (currentPeriod == null)
                    return null;
                if (dayTypeHandling.contains("filler_weekday") && currentPeriod.isWeekday())
                    segregatedObservationPeriod.add(currentPeriod);
                else if (dayTypeHandling.contains("filler_weekend") && !currentPeriod.isWeekday())
                    segregatedObservationPeriod.add(currentPeriod);
                endTime = endTime.minusDays(1);
            }
        } else {
            while (startTime.compareTo(endTime) < 0) {
                String currentKey = "From " + startTime.toString() + " to " + startTime.plusDays(1);
                OperationExercisedActions currentPeriod = ds.get(OperationExercisedActions.class, currentKey);
                if (dayTypeHandling.contains("filter_weekday") && currentPeriod.isWeekday())
                    segregatedObservationPeriod.add(currentPeriod);
                else if (dayTypeHandling.contains("filter_weekend") && !currentPeriod.isWeekday())
                    segregatedObservationPeriod.add(currentPeriod);
                else if (dayTypeHandling.contains("none"))
                    segregatedObservationPeriod.add(currentPeriod);
                startTime = startTime.plusDays(1);
            }
        }
        return segregatedObservationPeriod;
    }
}
