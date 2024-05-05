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


    // MENUS

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
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        } while (mainChoice != 4);


        loadMainMenu(data); // Loading main menu
    }



    // UPDATES


    // Updates the name based on user selection
    private void updateName(Data data, int choice) {
        int tempIndex = (choice == 1) ? 0 : 1;
        String str = (choice == 1) ? "First" : "Last";
        String[] temp = this.username.split("_");
        String name;

        do {
            System.out.print("Enter your " + str + " Name: ");
            name = sc.nextLine();
            displayDottedLine();

            // Removing Spaces , if Any
            name = name.replaceAll(" " , "");

            if (name.isEmpty()) {
                System.out.println(str + " Name Field is Required... No Changes were made!");
            }

            if (name.equalsIgnoreCase(temp[tempIndex])) {
                System.out.println("Please Enter a name other than the current one... No Changes were made!");
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
        }
    }

    // Updates the password after validating length and confirmation
    private void updatePassword(Data data) {
        final int MINLENGTH = 8, MAXLENGTH = 16;
        String newPassword, confirmPassword;
        int attempts = 3;

        do {
            System.out.print("Enter your new password: ");
            newPassword = sc.nextLine();
            displayDottedLine();
            if (newPassword.length() < MINLENGTH) {
                System.out.println("The password should have at least " + MINLENGTH + " characters, please try again");
            }

            if (newPassword.length() > MAXLENGTH) {
                System.out.println("The password should have at most " + MAXLENGTH + " characters, please try again");
            }
        } while (newPassword.length() < MINLENGTH || newPassword.length() > MAXLENGTH);

        do {
            System.out.print("Confirm password: ");
            confirmPassword = sc.nextLine();
            displayDottedLine();

            if (!confirmPassword.equals(newPassword) && attempts != 0) {
                attempts--;
                System.out.println("Passwords Don't Match, try again (" + attempts + " attempts left)");
            }

            if (attempts == 0) {
                System.out.println("No Attempts left! Navigating Back to Menu");
                break;
            }
        } while (!confirmPassword.equals(newPassword) && attempts != 0);

        if (attempts != 0) {
            int instructorIndex = instructorIndexFinder(username, data);
            this.password = newPassword;
            data.getInstructors().get(instructorIndex).password = this.password;
            System.out.println("Password was Successfully modified");
        }

        loadAccountMenu(data);
    }




    // DISPLAYS

    // Displays account information
    private void displayAccountInformation() {
        String[] temp = this.username.split("_");
        System.out.println(
                "First Name: " + temp[0] +
                        "\nLast Name: " + temp[1] +
                        "\nPassword: " + this.password
        );
    }




    // HELPER METHODS


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
