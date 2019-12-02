package edu.mines;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import edu.mines.model.SlidingWindowPeriod;
import edu.mines.model.SlidingWindowTrial;

import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Created by Matt on 7/31/2017.
 */
public class OperationExercisedActionPopulator {
    private static Config config = ConfigFactory.load();
    private ZonedDateTime startZ = ZonedDateTime.parse(config.getString("env.startDate"));
    private ZonedDateTime endZ = ZonedDateTime.parse(config.getString("env.endDate"));

    public static void main(String... args) {
        OperationExercisedActionPopulator operationExercisedActionPopulator = new OperationExercisedActionPopulator();
        Integer[] observationPeriodRange = config.getIntList("env.observationDays").stream().toArray(Integer[]::new);
        Integer[] operationPeriodRange = config.getIntList("env.operationDays").stream().toArray(Integer[]::new);

        operationExercisedActionPopulator.generate(observationPeriodRange, operationPeriodRange);
    }

    private void generate(Integer[] observationPeriodRange, Integer[] operationPeriodRange) {
        for (int operationPeriod : operationPeriodRange) {
            for (int observationPeriod : observationPeriodRange) {
                System.out.println("Starting Processing for obs: " + observationPeriod + " opr: " + operationPeriod);
                this.populateOperationExercisedActionsCollection(startZ, endZ, observationPeriod, operationPeriod);
            }
        }
    }

    private void populateOperationExercisedActionsCollection(ZonedDateTime startZ, ZonedDateTime endZ, int observationPeriod, int operationPeriod) {
        long start = System.currentTimeMillis();
        System.out.println("Generating OperationExercisedActions for observationPeriod: " + observationPeriod);
        ArrayList<SlidingWindowPeriod> slidingWindowPeriods = edu.mines.utils.DateUtils.generateWindowPeriods(startZ, endZ, observationPeriod, operationPeriod, 1);
        SlidingWindowTrial slidingWindowTrial = new SlidingWindowTrial(slidingWindowPeriods, observationPeriod, operationPeriod, 1);
        slidingWindowTrial.getSlidingWindowPeriods().stream().forEach(period -> {
            System.out.println("Populating actions for period:" + period.toString());
            new ActionNormalizer(period.getObsEndOprStart(), period.getOprEnd()).loadOrProcess();
            new ActionNormalizer(period.getObsStart(), period.getObsEndOprStart()).loadOrProcess();
        });
        System.out.println("populateOperationExercisedActionsCollection: " + (System.currentTimeMillis() - start) / 60000 + "m");
    }
}
