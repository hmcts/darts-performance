package Utilities;

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
}


