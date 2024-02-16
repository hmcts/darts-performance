package Utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class TimestampGenerator {

    // Method to generate a timestamp representing the current time
    public static String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        // Format the timestamp using the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return now.format(formatter);
    }

    // Method to generate a random time within the specified range
    public static LocalDateTime getRandomTime(LocalDateTime startTime, int minHoursDifference, int maxHoursDifference) {
        int randomHoursDifference = ThreadLocalRandom.current().nextInt(minHoursDifference, maxHoursDifference + 1);
        return startTime.plusHours(randomHoursDifference);
    }

    public static void main(String[] args) {
        String startTimeString = getCurrentTimestamp(); // Get current timestamp as start time
        LocalDateTime startTime = LocalDateTime.parse(startTimeString);

        // Generate random hour difference between 1 and 5
        int minHoursDifference = 1;
        int maxHoursDifference = 5;
        LocalDateTime endTime = getRandomTime(startTime, minHoursDifference, maxHoursDifference);

        // Output the results
        System.out.println("Start Time: " + startTime);
        System.out.println("End Time: " + endTime);
    }
}
