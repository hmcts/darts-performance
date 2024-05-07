package simulations.Scripts.Utilities;

import java.util.Random;

public class NumberGenerator {
    // Instance variable to hold the current value of the number
    private int currentValue;

    // Constructor to initialize the number generator with an initial value
    public NumberGenerator(int initialValue) {
        this.currentValue = initialValue;
    }

    // Method to generate the next number and return it as a formatted string
    public String generateNextNumber() {
        // Increment the current value
        currentValue++;
        // Format the number with leading zeros
        return String.format("%016d", currentValue);
    }



    public static void RandomNumberGenerator(String[] args) {
        // Create a Random object
        Random random = new Random();

        // Generate and print a random integer
        int randomNumber = random.nextInt();
        System.out.println("Random Number: " + randomNumber);

        // Generate and print a random integer within a specific range (0 to 100)
        int minRange = 0;
        int maxRange = 100;
        int randomInRange = random.nextInt(maxRange - minRange + 1) + minRange;
        System.out.println("Random Number in Range (0 to 100): " + randomInRange);

        // Generate and print a random double between 0.0 (inclusive) and 1.0 (exclusive)
        double randomDouble = random.nextDouble();
        System.out.println("Random Double: " + randomDouble);
    }

}


