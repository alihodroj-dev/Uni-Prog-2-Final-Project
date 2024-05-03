import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Student {
    private String username;
    private String password;
    private ArrayList<Class> classesEnrolledIn;
    private int quizGrade;
    private int midtermGrade;
    private int finalGrade;
    private Scanner sc;

    public Student() {
        sc = new Scanner(System.in);
    }

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

    public ArrayList<Class> getClassesEnrolledIn() {
        return classesEnrolledIn;
    }

    public void setClassesEnrolledIn(ArrayList<Class> classesEnrolledIn) {
        this.classesEnrolledIn = classesEnrolledIn;
    }

    public int getQuizGrade() {
        return quizGrade;
    }

    public void setQuizGrade(int quizGrade) {
        this.quizGrade = quizGrade;
    }

    public int getMidtermGrade() {
        return midtermGrade;
    }

    public void setMidtermGrade(int midtermGrade) {
        this.midtermGrade = midtermGrade;
    }

    public int getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(int finalGrade) {
        this.finalGrade = finalGrade;
    }

    // DISPLAYS

    public void displayAccountInformation() {
        System.out.print("username : " + this.username +
                "\npassword : " + this.password + "\n"
        );
    }

    public void displayClassesEnrolledIn() {
        System.out.println(); // empty line
        for (int i = 0; i < classesEnrolledIn.toArray().length; i++)
            System.out.print("( " + (i + 1) + " ) " + classesEnrolledIn.get(i).getClassId() + "\n");
        System.out.println();
    }

    // MENUS

    // Main Menu
    public void loadMainMenu() {
        int choice;
        System.out.print("Hello, " + this.username + " !, What do you want to do next ?\n");
        do {
            System.out.println(
                    "( 1 ) Display Account information\n" +
                            "( 2 ) Edit Account information\n" +
                            "( 3 ) Display your Classes\n" +
                            "( 4 ) Enroll/Drop from a Class\n" +
                            "( 5 ) Navigate to Class\n" +
                            "( 6 ) Check Academic Records\n" +
                            "( 7 ) Logout\n" +
                            "Enter your choice (1 - 7): "
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
                    displayClassesEnrolledIn();
                    break;
                case 4:
                    // Additional functionality can be placed here
                    break;
                case 5:
                    loadClassNavigatorMenu(data);
                    break;
                case 6:
                    // Additional functionality can be placed here
                    break;
                case 7:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid Choice!\n");
                    break;
            }
        } while (choice != 7);
    }

    // Account Menu
    public void loadAccountMenu(Data data) {
        // Loading Accounts
        data.loadAccounts();

        int choice;
        int index = findStudentIndex(data);
        do {
            System.out.println(
                    "( 1 ) Change Username\n" +
                            "( 2 ) Change Password\n" +
                            "( 3 ) Go Back\n" +
                            "Enter your choice : "
            );
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline here as well

            switch (choice) {
                case 1:
                    String newUsername;
                    do {
                        System.out.print("Enter your new username : ");
                        newUsername = sc.nextLine();
                        if (newUsername.isEmpty())
                            System.out.print("Username field is required!\n");
                        else if(isUsernameTaken(newUsername , data))
                            System.out.print("Username is already taken!\n");
                        else
                            setUsername(newUsername);

                    } while (newUsername.isEmpty() || isUsernameTaken(newUsername , data));

                    break;
                case 2:
                    String newPassword;
                    do {
                        System.out.print("Enter your new password : ");
                        newPassword = sc.nextLine();
                        if (newPassword.isEmpty())
                            System.out.print("Password field is required!\n");
                        else {
                            setPassword(newPassword);
                        }
                    } while (newPassword.isEmpty());
                    System.out.print("Password has been successfully set !");
                case 3:
                    break;
                default:
                    System.out.println("Invalid Choice!");
                    break;
            }

            if (index != -1 && choice != 3) {
                // Making changes in Students ArrayList
                data.getStudents().get(index).setUsername(this.username);
                data.getStudents().get(index).setPassword(this.password);

                System.out.println("Updated Account Information:\n");
                displayAccountInformation();
            }

            // Saving Changes
            data.saveAccounts();

        } while (choice != 3);
        System.out.println("Navigating back to main menu...\n");
        loadMainMenu();
    }
    // Class Navigator Menu
    public void loadClassNavigatorMenu(Data data) {
        // Loading CLasses and tests
        data.loadClassesAndTests();

        int classChoice;
        System.out.println("Pick a class : ");
        displayClassesEnrolledIn();
        System.out.println("( " + ((classesEnrolledIn.toArray().length) + 1) + " ) Go Back");
        System.out.print("Enter your choice : ");
        classChoice = sc.nextInt() - 1; // decrementing to actual arraylist index
        sc.nextLine(); // clearing line
        if(classChoice == ((classesEnrolledIn.toArray().length))) loadMainMenu();
        else loadTestTakerMenu(classChoice , data);

    }
    // Test Taker Menu
    public void loadTestTakerMenu(int classChoice , Data data) {
        Class c = new Class("");
        c = classesEnrolledIn.get(classChoice);
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

            System.out.println("( 1 ) Take Quiz\n" +
                    "( 2 ) Take Midterm\n" +
                    "( 3 ) Take Final\n" +
                    "( 4 ) Go Back\n" +
                    "Enter your choice : "
            );
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    // Start Quiz
                    break;
                case 2:
                    // Start Midterm
                    break;
                case 3:
                    // Start final
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



    // Searches

    // Index Finder
    public int findStudentIndex(Data data) {
        int index = 1; // Default Value
        for (int i = 0; i < data.getStudents().toArray().length; i++)
            if (data.getStudents().get(i).username.equals(this.username))
                index = i;
        return index;
    }

    // Booleans
    public boolean isUsernameTaken(String s, Data data) {
        for(Student student : data.getStudents()) {
           if( s.equalsIgnoreCase(student.username) ) return true;
        }
        return  false;
    }
}
