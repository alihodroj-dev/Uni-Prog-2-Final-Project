import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Student {
    private String username;
    private String password;
    private ArrayList<StudentGrade> grades = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);

    // Constructor initializing username and password
    public Student(String username, String password) {
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

    public ArrayList<StudentGrade> getGrades() {
        return grades;
    }

    // Setter for grades
    public void setGrades(ArrayList<StudentGrade> grades) {
        this.grades = grades;
    }

    // MENUS

    // Main menu of the application
    public void loadMainMenu(Data data) {

        int choice;
        String[] temp = this.username.split("_");

        System.out.println("Hello " + temp[0] + " " + temp[1] + ", What do you want to do next?");

        do {
            System.out.print(
                    " 1) Display Account Information\n" +
                            " 2) Edit Account Information\n" +
                            " 3) Check Academic Records\n" +
                            " 4) Take a test\n" +
                            " 5) Logout\n" +
                            "CHOICE (1 - 5): "
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
                    displayAcademicRecords(data);
                    break;

                case 4:
                    // takeTest();
                    break;
                case 5:
                    break;

                default:
                    System.out.println("Invalid Choice! Please choose a valid option.");
            }
        } while (choice != 5);
        data.saveData(); // Saving data in case of any changes
        System.out.println("Logging you out...");
    }

    // Menu for editing account information
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

        loadMainMenu(data); // Loading Main menu
    }

    // UPDATES

    // Updates the username based on the part being changed
    private void updateName(Data data, int choice) {
        int tempIndex = (choice == 1) ? 0 : 1;
        String str = (choice == 1) ? "First" : "Last";
        String[] temp = this.username.split("_");
        String newName;

        do {
            System.out.print("NEW " + str + " NAME : ");
            newName = sc.nextLine();
            displayDottedLine();

            // Removing Spaces . if any
            newName = newName.replaceAll(" " , "");


            if (newName.isEmpty()) {
                System.out.println(str + " Name Field is Required... No Changes were made!");
            }

            if (newName.equalsIgnoreCase(temp[tempIndex])) {
                System.out.println("Please Enter a name other than the current one... No Changes were made!");
            }
        } while (newName.isEmpty() || newName.equalsIgnoreCase(temp[tempIndex]));

        int index = studentIndexFinder(username, data);

        if (index != -1) {
            Random rand = new Random();
            String oldUsername = this.username;
            String newUsername;
            do {

                if(choice == 1)
                    newUsername = newName + "_" + temp[1] + "_" + rand.nextInt(1000, 10000);
                else
                    newUsername = temp[0] + "_" + newName + "_" +rand.nextInt(1000, 10000);

            } while (isTaken(newUsername, data));

            this.username = newUsername;

            data.getStudents().get(index).username = this.username;
            renameGradeFile(oldUsername, username);
            System.out.println(str + " Name was Successfully Modified");
            loadAccountMenu(data);
        }
    }

    // Updates user password
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
            }

            if (newPassword.length() > MAXLENGTH) {
                System.out.println("The password should have at most " + MAXLENGTH + " characters, please try again");
            }
        } while (newPassword.length() < MINLENGTH || newPassword.length() > MAXLENGTH);

        do {
            System.out.print("CONFIRM PASSWORD: ");
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
            int studentIndex = studentIndexFinder(username, data);
            this.password = newPassword;
            data.getStudents().get(studentIndex).password = this.password;
            System.out.println("Password was Successfully modified");
        }

        loadAccountMenu(data);
    }


    // DISPLAYS

    // Displays account information by splitting username into first and last name
    private void displayAccountInformation() {
        String[] temp = this.username.split("_");

        System.out.println(
                " First Name: " + temp[0] +
                        "\n Last Name: " + temp[1] +
                        "\n Password: " + this.password
        );

        displayDottedLine();
    }

    // Displays Academic Records
    private  void displayAcademicRecords(Data data) {
        data.loadData();
        String tempTitle="";
        System.out.println("Displaying All Taken tests...\n");
        for(int i=0 ; i < grades.size() ; i++) {
            for(Test t : data.getTests()) {
                if(grades.get(i).getTestId().equalsIgnoreCase(t.getTestId())) {
                    tempTitle = t.getTestName();
                    break;
                }
            }
            if(!tempTitle.isEmpty()) {
                System.out.println("Test Name : " + tempTitle);
                System.out.println(grades.get(i));
                System.out.println("====================");
            }
        }
        displayDottedLine();

    }



    @Override
    public String toString() {
        String[] temp = this.username.split("_");
        return "ROLE: STUDENT\n" +
                "NAME: " + temp[0].toUpperCase() + " " + temp[1].toUpperCase() + "\n";
    }



    // HELPER METHODS


    // Helper method to find student index
    private int studentIndexFinder(String u, Data data) {
        for (int i = 0; i < data.getStudents().size(); i++) {
            if (data.getStudents().get(i).username.equalsIgnoreCase(u)) return i;
        }
        return -1; // Not found
    }

    // Helper method to rename file linked to student in Grades folder
    private void renameGradeFile(String oldUsername, String newUsername) {
        String oldFilePath = "Data/Grades/" + oldUsername + ".txt";
        String newFilePath = "Data/Grades/" + newUsername + ".txt";
        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);
        oldFile.renameTo(newFile);
    }

    // Checks if username is already taken
    private boolean isTaken(String username, Data data) {
        for (Student s : data.getStudents()) {

            if (s.username.equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
    private static void displayDottedLine() {
        System.out.println("********************************************");
    }
}
