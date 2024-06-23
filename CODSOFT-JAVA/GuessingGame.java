import java.util.Random;
import java.util.Scanner;

public class GuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain = true;
        int roundsWon = 0;

        System.out.println("Welcome to the Guessing Game!");

        while (playAgain) {
            int numberToGuess = random.nextInt(100) + 1;
            int attempts = 0;
            int maxAttempts = 10;
            boolean guessedCorrectly = false;

            System.out.println("\nA new number has been generated. Try to guess it!");
            System.out.println("You have " + maxAttempts + " attempts.");

            while (attempts < maxAttempts && !guessedCorrectly) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess < numberToGuess) {
                    System.out.println("low!");
                } else if (userGuess > numberToGuess) {
                    System.out.println("high!");
                } else {
                    System.out.println("Correct! You've guessed the number in " + attempts + " attempts.");
                    guessedCorrectly = true;
                    roundsWon++;
                }
            }

            if (!guessedCorrectly) {
                System.out.println("You've used all your attempts. The number was " + numberToGuess);
            }

            System.out.print("Do you want to play another round? (yes/no): ");
            scanner.nextLine();  // Consume the newline character
            String response = scanner.nextLine().trim().toLowerCase();
            playAgain = response.equals("yes");

            System.out.println("Your score: " + roundsWon + " rounds won.");
        }

        System.out.println("Thank you for playing! Your final score: " + roundsWon + " rounds won.");
        scanner.close();
    }
}
