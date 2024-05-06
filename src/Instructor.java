import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Instructor {
    private String username;
    private String password;
    private final Scanner sc = new Scanner(System.in);

    // Constructor to initialize username and password
    public Instructor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Main menu handling
    public void loadMainMenu(Data data) {
        int choice;
        String[] temp = this.username.split("_");

        System.out.println("Hello " + temp[0] + " " + temp[1] + ", What do you want to do next?");

        do {
            System.out.print(
                    " 1) Display Account Information\n" +
                            " 2) Edit Account Information\n" +
                            " 3) Create test\n" +
                            " 4) Logout\n" +
                            "CHOICE (1 - 4): "
            );

            choice = sc.nextInt();
            sc.nextLine(); // Clearing buffer after input
            displayDottedLine();

            switch (choice) {
                case 1:
                    displayAccountInformation();
                    break;
                case 2:
                    loadAccountMenu(data);
                    break;
                case 3:
                    createTest(data);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }
        } while (choice != 4);
        data.saveData(); // Saving data in case of any changes
        System.out.println("Logging you out...");
    }

    // Menu for account editing
    private void loadAccountMenu(Data data) {
        int mainChoice;

        do {
            System.out.print(
                    " 1) Edit First Name\n" +
                            " 2) Edit Last Name\n" +
                            " 3) Edit Password\n" +
                            " 4) Go Back\n" +
                            "CHOICE (1 - 4): "
            );

            mainChoice = sc.nextInt();
            sc.nextLine(); // Clearing buffer after input
            displayDottedLine();

            switch (mainChoice) {
                case 1, 2:
                    updateName(data, mainChoice);
                    break;
                case 3:
                    updatePassword(data);
                    break;
                case 4:
                    System.out.println("Navigating back to main menu...");
                    displayDottedLine();
                    break;
                default:
                    System.out.print("Invalid choice, enter again: ");
            }
        } while (mainChoice != 4);
    }

    // Method to create a test
    public void createTest(Data data) {
        String tName, tId, tPassword;
        char testType;

        tName = setTestName();
        tId = setTestID(data);
        tPassword = setTestPassword();
        testType = setTestType();

        Test test;
        if (testType == 'Q')
            test = new Quiz(tId, tPassword, testType, tName);
        else if (testType == 'M')
            test = new Midterm(tId, tPassword, testType, tName);
        else
            test = new Final(tId, tPassword, testType, tName);

        setTestQuestions(test);
        data.getTests().add(test);

        System.out.println("The test " + tName + " of ID " + tId + " has been successfully created");
        displayDottedLine();
    }

    // Updates the name based on user selection
    private void updateName(Data data, int choice) {
        int tempIndex = (choice == 1) ? 0 : 1;
        String str = (choice == 1) ? "First" : "Last";
        String[] temp = this.username.split("_");
        String name;

        do {
            System.out.print("NEW " + str.toUpperCase() + " NAME : ");
            name = sc.nextLine();
            displayDottedLine();

            // Removing spaces, if any
            name = name.replaceAll(" ", "");

            if (name.isEmpty()) {
                System.out.println(str + " Name Field is Required... No Changes were made!");
                displayDottedLine();
            }

            if (name.equalsIgnoreCase(temp[tempIndex])) {
                System.out.println("Please Enter a name other than the current one... No Changes were made!");
                displayDottedLine();
            }
        } while (name.isEmpty() || name.equalsIgnoreCase(temp[tempIndex]));

        int index = instructorIndexFinder(username, data);

        if (index != -1) {
            Random rand = new Random();

            String newUsername;
            do {
                newUsername = (choice == 1) ? name + "_" + temp[1] : temp[0] + "_" + name;
                newUsername += "_" + rand.nextInt(1000, 10000);
            } while (isTaken(newUsername, data));

            this.username = newUsername;
            data.getInstructors().get(index).username = this.username;
            System.out.println(str + " Name was Successfully Modified");
            System.out.println("Your new username is : " + username);
            displayDottedLine();
        }
    }

    // Updates the password after validating length and confirmation
    private void updatePassword(Data data) {
        final int MINLENGTH = 8, MAXLENGTH = 16;
        String newPassword, confirmPassword;
        int attempts = 3;

        do {
            System.out.print("NEW PASSWORD: ");
            newPassword = sc.nextLine();
            displayDottedLine();
            if (newPassword.length() < MINLENGTH) {
                System.out.println("The password should have at least " + MINLENGTH + " characters, please try again");
                displayDottedLine();
            }

            if (newPassword.length() > MAXLENGTH) {
                System.out.println("The password should have at most " + MAXLENGTH + " characters, please try again");
                displayDottedLine();
            }
        } while (newPassword.length() < MINLENGTH || newPassword.length() > MAXLENGTH);

        do {
            System.out.print("CONFIRM PASSWORD: ");
            confirmPassword = sc.nextLine();
            displayDottedLine();

            if (!confirmPassword.equals(newPassword) && attempts != 0) {
                attempts--;
                System.out.println("Passwords Don't Match, try again (" + attempts + " attempts left)");
                displayDottedLine();
            }

            if (attempts == 0) {
                System.out.println("No Attempts left! Navigating Back to Menu");
                displayDottedLine();
                break;
            }
        } while (!confirmPassword.equals(newPassword) && attempts != 0);

        if (attempts != 0) {
            int instructorIndex = instructorIndexFinder(username, data);
            this.password = newPassword;
            data.getInstructors().get(instructorIndex).password = this.password;
            System.out.println("Password was Successfully modified");
            displayDottedLine();
        }
    }

    // Displays account information
    private void displayAccountInformation() {
        String[] temp = this.username.split("_");
        System.out.println(
                " Username : " + this.username +
                        "\n First Name: " + temp[0] +
                        "\n Last Name: " + temp[1] +
                        "\n Password: " + this.password
        );
        displayDottedLine();
    }

    // HELPER METHODS

    // test creator helper methods

    // Helper method to set a unique test ID
    private String setTestID(Data data) {
        String id;
        final int MINLENGTH = 4;
        do {
            System.out.print("ENTER TEST ID: ");
            id = sc.nextLine();
            displayDottedLine();
            // Validate ID length and uniqueness
            if (id.length() < MINLENGTH)
                System.out.println("ID must have at least " + MINLENGTH + " characters");
            if (isTestIdTaken(id, data))
                System.out.println("The ID " + id + " already exists for another test");
        } while (id.length() < MINLENGTH || isTestIdTaken(id, data));
        return id;
    }

    // Helper method to set a password for the test
    private String setTestPassword() {
        String password;
        String confirmPassword;
        final int MINLENGTH = 8, MAXLENGTH = 16;
        do {
            System.out.print("ENTER TEST PASSWORD: ");
            password = sc.nextLine();
            displayDottedLine();
            // Check password requirements
            if (password.isEmpty())
                System.out.println("Password field is required");
            if (password.length() < MINLENGTH)
                System.out.println("Password must have at least " + MINLENGTH + " characters");
            if (password.length() > MAXLENGTH)
                System.out.println("Password must have at most " + MAXLENGTH + " characters");
        } while (password.length() < MINLENGTH || password.length() > MAXLENGTH);

        do {
            System.out.print("CONFIRM PASSWORD: ");
            confirmPassword = sc.nextLine();
            displayDottedLine();
            // Confirm password
            if (!confirmPassword.equals(password)) {
                System.out.println("Passwords Don't Match, try again");
            }
        } while (!confirmPassword.equals(password));
        return password;
    }

    // Helper method to set the name for the test
    private String setTestName() {
        String name;
        do {
            System.out.print("ENTER TEST NAME: ");
            name = sc.nextLine();
            displayDottedLine();
            // Check if the name is not empty
            if (name.isEmpty())
                System.out.println("Name field is required");
        } while (name.isEmpty());
        return name;
    }

    // Helper method to select the type of test
    private char setTestType() {
        int choice;
        do {
            System.out.print("ENTER TEST TYPE:\n 1) Quiz\n 2) Midterm\n 3) Final\nCHOICE (1 - 3): ");
            choice = sc.nextInt();
            sc.nextLine();
            displayDottedLine();
            // Validate choice
            if (choice < 1 || choice > 3)
                System.out.println("Invalid Choice...Try again");
        } while (choice < 1 || choice > 3);

        if(choice ==1)
            return 'Q';
        else if(choice ==2)
            return 'M';
        else
            return 'F';
    }

    // Helper method to initialize and set questions for the test
    private void setTestQuestions(Test test) {
        ArrayList<Question> questions = new ArrayList<>();
        for (int i = 0; i < test.getNumberOfQuestions(); i++) {
            System.out.println("QUESTION " + (i + 1) + " : ");
            Question q = setQuestion();
            questions.add(q);
        }
        test.setQuestions(questions);
    }

    // Helper method to create a question object
    private Question setQuestion() {
        String description = setQuestionDescription();

        ArrayList<String> options = setOptions();
        int correctIndex = setCorrectAnswer();

        return new Question(description, options, correctIndex);
    }

    // Helper method to set the description of a question
    private String setQuestionDescription() {
        String description;
        do {
            System.out.print("ENTER DESCRIPTION : ");
            description = sc.nextLine();
            displayDottedLine();
            if (description.isEmpty())
                System.out.println("Description Field is Required");
        } while (description.isEmpty());
        return description;
    }

    // Helper method to set options for a question
    private ArrayList<String> setOptions() {
        ArrayList<String> options = new ArrayList<>();
        final int NBOPTIONS = 4;
        String tempChoice;
        System.out.println("ENTER CHOICES : ");
        for (int i = 0; i < NBOPTIONS; i++) {
            do {
                System.out.print("CHOICE " + (i + 1) + ": ");
                tempChoice = sc.nextLine();
                displayDottedLine();
                if (tempChoice.isEmpty())
                    System.out.println("Choice field is required");
            } while (tempChoice.isEmpty());
            options.add(tempChoice);
        }
        return options;
    }

    // Helper method to set the correct answer index for a question
    private int setCorrectAnswer() {
        int index;
        do {
            System.out.print("ENTER CORRECT ANSWER (1 - 4): ");
            index = sc.nextInt();
            sc.nextLine(); // Clear buffer after input
            displayDottedLine();
            if (index < 1 || index > 4)
                System.out.println("Invalid Choice...Try Again");
        } while (index < 1 || index > 4);
        return index - 1;
    }

    // Check if test ID is already taken
    private boolean isTestIdTaken(String id, Data data) {
        for (Test t : data.getTests()) {
            if (t.getTestId().equalsIgnoreCase(id)) return true;
        }
        return false;
    }

    // Helper method to find instructor index
    private int instructorIndexFinder(String u, Data data) {
        for (int i = 0; i < data.getInstructors().size(); i++) {
            if (data.getInstructors().get(i).username.equalsIgnoreCase(u)) return i;
        }
        return -1;
    }

    // Checks if username is already taken
    private boolean isTaken(String username, Data data) {
        for (Instructor i : data.getInstructors()) {
            if (i.username.equalsIgnoreCase(username)) return true;
        }
        return false;
    }

    private static void displayDottedLine() {
        System.out.println("********************************************");
    }
}
