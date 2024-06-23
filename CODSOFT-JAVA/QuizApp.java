import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    String question;
    String[] options;
    int correctAnswer;

    public Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public void display() {
        System.out.println(question);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    public boolean checkAnswer(int answer) {
        return answer == correctAnswer;
    }
}

public class QuizApp {
    private static final int QUESTION_TIME_LIMIT = 10; // Time limit for each question in seconds
    private Question[] questions;
    private int score;
    private int[] userAnswers;
    private Scanner scanner;

    public QuizApp(Question[] questions) {
        this.questions = questions;
        this.score = 0;
        this.userAnswers = new int[questions.length];
        this.scanner = new Scanner(System.in);
    }

    public void startQuiz() {
        for (int i = 0; i < questions.length; i++) {
            System.out.println("Question " + (i + 1) + ":");
            questions[i].display();

            // Handle answer with a timer
            boolean answered = handleQuestionWithTimer(i);

            // If the user did not answer in time, set answer to -1
            if (!answered) {
                userAnswers[i] = -1;
            }
        }
        displayResults();
    }

    private boolean handleQuestionWithTimer(int questionIndex) {
        Timer timer = new Timer();
        boolean[] answered = {false};

        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("\nTime's up!");
                timer.cancel();
            }
        };
        timer.schedule(task, QUESTION_TIME_LIMIT * 1000);

        System.out.print("Enter your answer (1-" + questions[questionIndex].options.length + "): ");
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < QUESTION_TIME_LIMIT * 1000 && !answered[0]) {
            if (scanner.hasNextInt()) {
                int answer = scanner.nextInt();
                userAnswers[questionIndex] = answer;
                if (questions[questionIndex].checkAnswer(answer)) {
                    score++;
                }
                answered[0] = true;
            }
        }
        timer.cancel();
        return answered[0];
    }

    public void displayResults() {
        System.out.println("\nQuiz Results:");
        System.out.println("Your Score: " + score + "/" + questions.length);
        for (int i = 0; i < questions.length; i++) {
            System.out.println("\nQuestion " + (i + 1) + ": " + questions[i].question);
            System.out.println("Your answer: " + (userAnswers[i] == -1 ? "No answer" : questions[i].options[userAnswers[i] - 1]));
            System.out.println("Correct answer: " + questions[i].options[questions[i].correctAnswer - 1]);
            if (questions[i].checkAnswer(userAnswers[i])) {
                System.out.println("Status: Correct");
            } else {
                System.out.println("Status: Incorrect");
            }
        }
    }

    public static void main(String[] args) {
        Question[] questions = new Question[]{
            new Question("What is the capital of France?", new String[]{"Berlin", "Madrid", "Paris", "Rome"}, 3),
            new Question("What is the largest planet in our solar system?", new String[]{"Earth", "Jupiter", "Mars", "Saturn"}, 2),
            new Question("Who wrote 'To Kill a Mockingbird'?", new String[]{"Harper Lee", "Jane Austen", "Mark Twain", "Charles Dickens"}, 1)
        };

        QuizApp quiz = new QuizApp(questions);
        quiz.startQuiz();
    }
}
