package simulations.Scripts.Utilities;

import java.util.Random;

public class NumberGenerator {
    private int currentValue;

    // Constructor to initialize the number generator with an initial value
    public NumberGenerator(int initialValue) {
        this.currentValue = initialValue;
    }

    // Method to generate the next number and return it as a formatted string
    public String generateNextNumber() {
        currentValue++;
        return String.format("%016d", currentValue); // 16 digits with leading zeros
    }

    // Method to generate a 13-digit random number
    public static long generateRandom13DigitNumber() {
        Random random = new Random();
        return 1000000000000L + (long)(random.nextDouble() * 9000000000000L);
    }

    // Random number and range methods as they were
    public static void RandomNumberGenerator(String[] args) {
        Random random = new Random();
        int randomNumber = random.nextInt();
        System.out.println("Random Number: " + randomNumber);

        int minRange = 0;
        int maxRange = 100;
        int randomInRange = random.nextInt(maxRange - minRange + 1) + minRange;
        System.out.println("Random Number in Range (0 to 100): " + randomInRange);

        double randomDouble = random.nextDouble();
        System.out.println("Random Double: " + randomDouble);
    }
}
