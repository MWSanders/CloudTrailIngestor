package edu.mines;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import edu.mines.model.*;
import edu.mines.utils.DatastoreFactory;
import org.bson.Document;
import org.mongodb.morphia.Morphia;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by Matt on 4/23/2016.
 * <p/>
 * Generate all possible combinations of 1m, 2m, 3m EntitySummarySubsets
 */
public class EntitySummarySubsetGenerator {

    public static void main(String... args) {
        Morphia morphia = new Morphia();
        morphia.map(Service.class).map(edu.mines.model.Action.class).map(ActionSummary.class).map(ServiceSummary.class)
                .map(EntitySummary.class).map(ServiceLevelArn.class).map(OperationExercisedActions.class);
        MongoCollection<Document> eventCollection = DatastoreFactory.getEventCollection();
        FindIterable<Document> iterable = eventCollection.find().sort(Sorts.ascending("eventTime")).limit(1);
        Document startEvent = iterable.first();
        FindIterable<Document> iterableLast = eventCollection.find().sort(Sorts.descending("eventTime")).limit(1);
        Document endEvent = iterableLast.first();
        ZonedDateTime endZ = ZonedDateTime.parse(endEvent.getString("eventTime"));
        endZ = endZ.plusDays(1).truncatedTo(ChronoUnit.DAYS);

        ZonedDateTime previousDate = ZonedDateTime.parse(startEvent.getString("eventTime"));
        previousDate = previousDate.truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime currentDate = previousDate.plusDays(1);
        while (currentDate.toEpochSecond() < endZ.toEpochSecond()) {
            ActionNormalizer actionNormalizer = new ActionNormalizer(previousDate, currentDate);
            System.out.println("Generating " + previousDate.toString() + "-" + currentDate.toString());
            actionNormalizer.process();
            previousDate = previousDate.plusDays(1);
            currentDate = currentDate.plusDays(1);
        }

//        previousDate = ZonedDateTime.parse(startEvent.getString("eventTime"));
//        currentDate = previousDate.plusDays(28);
//        while (currentDate.toEpochSecond() < endZ.toEpochSecond()) {
//            ActionNormalizer actionNormalizer = new ActionNormalizer(previousDate, currentDate);
//            System.out.println("Generating " + previousDate.toString() + "-" + currentDate.toString());
//            actionNormalizer.process();
//            previousDate = previousDate.plusDays(28);
//            currentDate = currentDate.plusDays(28);
//        }
    }

}
