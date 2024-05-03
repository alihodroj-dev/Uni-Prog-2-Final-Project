import java.util.ArrayList;
import java.util.Scanner;

public class Instructor {
    private String username;
    private String password;
    private ArrayList<Class> classesTeaching;
    private Scanner sc;

    public Instructor() {
        sc = new Scanner(System.in);
    }

    public Instructor(String username, String password) {
        this.username = username;
        this.password = password;
    } // ?

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Class> getClassesTeaching() {
        return classesTeaching;
    }

    public void setClassesTeaching(ArrayList<Class> classesTeaching) {
        this.classesTeaching = classesTeaching;
    }


    // DISPLAYS
    // Display account information
    public void displayAccountInformation() {
        System.out.print("Username: " + this.username +
                "\nPassword: " + this.password + "\n"
        );
    }

    // Displays classes teaching
    public void displayClassesTeaching() {
        System.out.println(); // empty line for better readability
        for (int i = 0; i < classesTeaching.size(); i++)
            System.out.print("( " + (i + 1) + " ) " + classesTeaching.get(i).getClassId() + "\n");
    }

    // MENUS
    // Main menu for instructor actions
    public void loadMainMenu() {
        int choice;
        System.out.print("Hello, " + this.username + " ! What do you want to do next?\n");
        do {
            System.out.println(
                    "( 1 ) Display Account information\n" +
                            "( 2 ) Edit Account information\n" +
                            "( 3 ) Display your Classes\n" +
                            "( 4 ) Navigate to Class\n" +
                            "( 5 ) Logout\n" +
                            "Enter your choice (1 - 5): "
            );
            choice = sc.nextInt();
            sc.nextLine(); // Consume the newline after number input
            Data data = new Data();

            switch (choice) {
                case 1:
                    displayAccountInformation();
                    break;
                case 2:
                    loadAccountMenu(data);
                    break;
                case 3:
                    displayClassesTeaching();
                    break;
                case 4:
                    loadClassNavigatorMenu(data);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid Choice!");
                    break;
            }

        } while (choice != 5);
    }

    // Menu for editing account information
    public void loadAccountMenu(Data data) {
        data.loadAccounts(); // Load account data

        int choice;
        int index = finInstructorIndex(data);
        do {
            System.out.println(
                    "( 1 ) Change Username\n" +
                            "( 2 ) Change Password\n" +
                            "( 3 ) Go Back\n" +
                            "Enter your choice: "
            );
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline here as well

            switch (choice) {
                case 1:
                    String newUsername;
                    do {
                        System.out.print("Enter your new username: ");
                        newUsername = sc.nextLine();
                        if (newUsername.isEmpty())
                            System.out.print("Username field is required!\n");
                        else if(isUsernameTaken(newUsername , data))
                            System.out.print("Username is already in use!\n");
                        else
                            setUsername(newUsername);
                    } while (newUsername.isEmpty() || isUsernameTaken(newUsername , data));
                    break;
                case 2:
                    String newPassword;
                    do {
                        System.out.print("Enter your new password: ");
                        newPassword = sc.nextLine();
                        if (newPassword.isEmpty())
                            System.out.print("Password field is required!\n");
                        else {
                            setPassword(newPassword);
                            System.out.print("Password has been successfully set!");
                        }
                    } while (newPassword.isEmpty());
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid Choice!");
                    break;
            }

            if (index != -1 && choice != 3) {
                // Update instructor's details in data
                data.getInstructors().get(index).setUsername(this.username);
                data.getInstructors().get(index).setPassword(this.password);
                System.out.println("Updated Account Information:\n");
                displayAccountInformation();
            }

            data.saveAccounts(); // Save changes to accounts

        } while (choice != 3);
        System.out.println("Navigating back to main menu...\n");
        loadMainMenu();
    }

    // Class navigator menu
    public void loadClassNavigatorMenu(Data data) {
        data.loadClassesAndTests(); // Load classes and tests

        int classChoice;
        System.out.println("Pick a class: ");
        displayClassesTeaching();
        System.out.println("( " + (classesTeaching.size() + 1) + " ) Go Back");
        System.out.print("Enter your choice: ");
        classChoice = sc.nextInt() - 1; // Decrement to actual ArrayList index
        sc.nextLine(); // Clearing line

        if (classChoice == classesTeaching.size()) {
            loadMainMenu();
        } else {
            loadTestTakerMenu(classChoice, data);
        }
    }

    // Test Maker Menu
    public void loadTestTakerMenu(int classChoice , Data data) {
        Class c = new Class("");
        c = classesTeaching.get(classChoice);
        int choice;
        do {
            System.out.println("Class : " + c.getClassId());
            System.out.print("Quiz : ");
            if (c.getIsQuizSet()) System.out.print("Available\n");
            else System.out.print("Not Available\n");

            System.out.print("Midterm : ");
            if (c.getIsMidtermSet()) System.out.print("Available\n");
            else System.out.print("Not Available\n");

            System.out.print("Final : ");
            if (c.getIsFinalSet()) System.out.print("Available\n");
            else System.out.print("Not Available\n");

            System.out.println("( 1 ) Make Quiz\n" +
                    "( 2 ) Make Midterm\n" +
                    "( 3 ) Make Final\n" +
                    "( 4 ) Go Back\n" +
                    "Enter your choice : "
            );
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    // Make Quiz
                    break;
                case 2:
                    // Make Midterm
                    break;
                case 3:
                    // Make final
                    break;
                case 4:
                    // go back to navigator menu
                    break;
                default:
                    System.out.println("Invalid Choice!");
                    break;
            }
        }while(choice != 4);
    }

    // SEARCHES
    // Method to find instructor's index in data
    public int finInstructorIndex(Data data) {
        for (int i = 0; i < data.getInstructors().size(); i++)
            if (data.getInstructors().get(i).username.equals(this.username))
                return i;
        return -1; // Return -1 if not found
    }

    // BOOLEANS
    public boolean isUsernameTaken(String u, Data data) {
        for(Instructor i : data.getInstructors()) {
            if( u.equalsIgnoreCase(i.username) ) return true;
        }
        return  false;
    }
}
