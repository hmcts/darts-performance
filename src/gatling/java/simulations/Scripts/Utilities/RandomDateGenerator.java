package simulations.Scripts.Utilities;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDateGenerator {

    public static void main(String[] args) {
        // Get current date
        LocalDate currentDate = LocalDate.now();

        // Define the date range constraints
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 3, 15);

        // Ensure the endDate is at least 2 years from the startDate
        LocalDate minEndDate = startDate.plusYears(2);

        // Adjust the endDate if necessary
        if (endDate.isBefore(minEndDate)) {
            endDate = minEndDate;
        }

        // Generate random dates within the range
        LocalDate randomDateFrom = getRandomDate(startDate, endDate.minusYears(2));
        LocalDate randomDateTo = getRandomDate(randomDateFrom.plusYears(2), endDate);

        // Ensure dates don't go past the current date
        randomDateFrom = randomDateFrom.isAfter(currentDate) ? currentDate : randomDateFrom;
        randomDateTo = randomDateTo.isAfter(currentDate) ? currentDate : randomDateTo;

        // Print the random dates
        System.out.println("Random Date From: " + randomDateFrom);
        System.out.println("Random Date To: " + randomDateTo);
    }

    public static LocalDate getRandomDate(LocalDate startDate, LocalDate endDate) {
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);
        return LocalDate.ofEpochDay(randomEpochDay);
    }
}
