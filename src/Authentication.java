import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public abstract class Authentication {
    static final private Scanner sc = new Scanner(System.in);
    public static void studentLogin(Data dataModel) {
        // loop variables
        boolean loggedIn = false;
        String usernameInput = "";
        String passwordInput = "";
        // main loop
        do {
            // display message
            // taking username
            System.out.println("You are logging in as a STUDENT");
            System.out.print("USERNAME: ");
            usernameInput = sc.nextLine();
            // error handling
            while (usernameInput.isEmpty()) {
                displayDottedLines();
                System.out.print("Enter a valid username: ");
                usernameInput = sc.nextLine();
            }
            // display message
            // taking password
            displayDottedLines();
            System.out.print("PASSWORD: ");
            passwordInput = sc.nextLine();
            while (passwordInput.isEmpty()) {
                displayDottedLines();
                System.out.print("Enter a valid password: ");
                passwordInput = sc.nextLine();
            }
            // checking login info
            displayDottedLines();
            System.out.println("Checking login info...");
            Student currentUser = checkIfStudentExists(usernameInput, passwordInput, dataModel);
            // checking returned student
            if(!currentUser.getUsername().isEmpty()) {
                System.out.println("Successfully logged in!");
                displayDottedLines();
                // stopping loop
                loggedIn = true;
                // loading student menu
                currentUser.loadMainMenu(dataModel);
            } else {
                // looping back to start
                System.out.println("Login failed, try again!");
                displayDottedLines();
            }
        } while(!loggedIn);
    }
    public static void instructorLogin(Data dataModel) {
        // loop variables
        boolean loggedIn = false;
        String usernameInput = "";
        String passwordInput = "";
        // username loop
        do {
            // display message
            // taking username
            System.out.println("You are logging in as an INSTRUCTOR");
            System.out.print("USERNAME: ");
            usernameInput = sc.nextLine();
            // error handling
            while (usernameInput.isEmpty()) {
                displayDottedLines();
                System.out.print("Enter a valid username: ");
                usernameInput = sc.nextLine();
            }
            // display message
            // taking password
            displayDottedLines();
            System.out.print("PASSWORD: ");
            passwordInput = sc.nextLine();
            while (passwordInput.isEmpty()) {
                displayDottedLines();
                System.out.print("Enter a valid password: ");
                passwordInput = sc.nextLine();
            }
            // checking login info
            displayDottedLines();
            System.out.println("Checking login info...");
            Instructor currentUser = checkIfInstructorExists(usernameInput, passwordInput, dataModel);
            // checking returned instructor
            if(!currentUser.getUsername().isEmpty()) {
                System.out.println("Successfully logged in!");
                displayDottedLines();
                // stopping loop
                loggedIn = true;
                // loading instructor menu
                currentUser.loadMainMenu(dataModel);
            } else {
                // looping back to start
                System.out.println("Login failed, try again!");
                displayDottedLines();
            }
        } while(!loggedIn);
    }
    public static void createStudent(Data dataModel) {
        // loop variables
        boolean userCreated = false;
        String fname = "";
        String lname = "";
        String passwordInput = "";
        String confirmPasswordInput = "";
        // main loop
        do {
            int attempts = 3;
            // display message
            // taking first name
            System.out.println("Creating a STUDENT ACCOUNT");
            System.out.print("Enter your first name: ");
            fname = sc.nextLine();
            // error handling
            while (fname.isEmpty()) {
                displayDottedLines();
                System.out.println("Field shouldn't be empty!");
                System.out.print("Enter a valid first name: ");
                fname = sc.nextLine();
            }
            // taking last name
            displayDottedLines();
            System.out.print("Enter your last name: ");
            lname = sc.nextLine();
            // error handling
            while (lname.isEmpty()) {
                displayDottedLines();
                System.out.println("Field shouldn't be empty!");
                System.out.print("Enter a valid last name: ");
                lname = sc.nextLine();
            }
            // taking password
            displayDottedLines();
            System.out.print("Enter your password (8 characters or more) : ");
            passwordInput = sc.nextLine();
            while (passwordInput.length() <= 8) {
                displayDottedLines();
                System.out.print("Enter a valid password (8 characters or more) : ");
                passwordInput = sc.nextLine();
            }
            // taking confirm password
            displayDottedLines();
            System.out.print("Confirm password: ");
            confirmPasswordInput = sc.nextLine();
            while (!confirmPasswordInput.equals(passwordInput)) {
                attempts--;
                if(attempts == 0) { break; }
                displayDottedLines();
                System.out.print("Passwords don't match, try again (" + attempts + " attempts left ) : ");
                confirmPasswordInput = sc.nextLine();
            }
            // checking sign up info
            Random rand = new Random();
            // creating username
            String username = fname + "_" + lname + "_" + rand.nextInt(1000, 10000);
            System.out.println("Creating account...");
            if(confirmPasswordInput.equals(passwordInput) && checkIfStudentUsernameUnique(username, dataModel)) {
                // creating new student
                Student s = new Student(username, confirmPasswordInput);
                // adding new student into data model
                dataModel.getStudents().add(s);
                // saving new student
                dataModel.saveData();
                // stopping loop
                System.out.println("STUDENT created!");
                System.out.println("Your new USERNAME is (" + s.getUsername() + ") Please memorize it!");
                displayDottedLines();
                userCreated = true;
                // starting main menu
                s.loadMainMenu(dataModel);
            } else {
                System.out.println("Failed to create STUDENT, try again.");
                if (!checkIfStudentUsernameUnique(username, dataModel)) {
                    System.out.println("USERNAME ALREADY EXISTS!");
                }
                displayDottedLines();
            }
        } while (!userCreated);
    }
    public static void createInstructor(Data dataModel) {
        // loop variables
        boolean userCreated = false;
        String fname = "";
        String lname = "";
        String passwordInput = "";
        String confirmPasswordInput = "";
        // main loop
        do {
            int attempts = 3;
            // display message
            // taking first name
            System.out.println("Creating an INSTRUCTOR ACCOUNT");
            System.out.print("Enter your first name: ");
            fname = sc.nextLine();
            // error handling
            while (fname.isEmpty()) {
                displayDottedLines();
                System.out.println("Field shouldn't be empty!");
                System.out.print("Enter a valid first name: ");
                fname = sc.nextLine();
            }
            // taking last name
            displayDottedLines();
            System.out.print("Enter your last name: ");
            lname = sc.nextLine();
            // error handling
            while (lname.isEmpty()) {
                displayDottedLines();
                System.out.println("Field shouldn't be empty!");
                System.out.print("Enter a valid last name: ");
                lname = sc.nextLine();
            }
            // taking password
            displayDottedLines();
            System.out.print("Enter your password (8 characters or more) : ");
            passwordInput = sc.nextLine();
            while (passwordInput.length() <= 8) {
                displayDottedLines();
                System.out.print("Enter a valid password (8 characters or more) : ");
                passwordInput = sc.nextLine();
            }
            // taking confirm password
            displayDottedLines();
            System.out.print("Confirm password: ");
            confirmPasswordInput = sc.nextLine();
            while (!confirmPasswordInput.equals(passwordInput)) {
                attempts--;
                if(attempts == 0) { break; }
                displayDottedLines();
                System.out.print("Passwords don't match, try again (" + attempts + " attempts left ) : ");
                confirmPasswordInput = sc.nextLine();
            }
            // checking sign up info
            Random rand = new Random();
            // creating username
            String username = fname + "_" + lname + "_" + rand.nextInt(1000, 10000);
            System.out.println("Creating account...");
            if(confirmPasswordInput.equals(passwordInput) && checkIfInstructorUsernameUnique(username, dataModel)) {
                // creating new instructor
                Instructor i = new Instructor(username, confirmPasswordInput);
                // adding new student into data model
                dataModel.getInstructors().add(i);
                // saving new instructor
                dataModel.saveData();
                // stopping loop
                System.out.println("INSTRUCTOR created!");
                System.out.println("Your new USERNAME is (" + i.getUsername() + ") Please memorize it!");
                displayDottedLines();
                userCreated = true;
                // starting main menu
                i.loadMainMenu(dataModel);
            } else {
                System.out.println("Failed to create INSTRUCTOR, try again.");
                if (!checkIfStudentUsernameUnique(username, dataModel)) {
                    System.out.println("USERNAME ALREADY EXISTS!");
                }
                displayDottedLines();
            }
        } while (!userCreated);
    }
    public static boolean checkRememberMe() {
        // contents of remember me file
        ArrayList<String> rememberMeContent = FileWorker.readFile("Data/Users/RememberMe.txt");
        // checking if it is empty
        return !rememberMeContent.isEmpty();
    }
    public static void performRememberMeLogin(Data mainDataModel) {
        // checking user account type
        if (getRememberMeUserType().equals("S")) {
            // if student
            getRememberMeStudent(mainDataModel).loadMainMenu(mainDataModel);
        } else {
            // if instructor
            getRememberInstructor(mainDataModel).loadMainMenu(mainDataModel);
        }
    }
    public static void setRememberMe(String username, String accountType) {
        // data to be written in file
        String fileData = username + ";" + accountType;
        FileWorker.writeFile(fileData, "Data/Users/RememberMe.txt");
    }
    public static void turnOffRememberMeOption() {
        FileWorker.writeFile("", "Data/Users/RememberMe.txt");
    }

    // HELPER METHODS
    private static void displayDottedLines() {
        System.out.println("********************************************");
    }
    private static Student checkIfStudentExists(String username, String password, Data dataModel) {
        // user if found to be saved here
        Student student = new Student("", "");
        // checking
        for (Student s : dataModel.getStudents()) {
            // check username matching
            if(s.getUsername().equals(username)) {
                // check if password matching
                if(s.getPassword().equals(password)) {
                    // user found
                    student = s;
                }
            }
        }
        return student;
    }
    private static Instructor checkIfInstructorExists(String username, String password, Data dataModel) {
        // user if found to be saved here
        Instructor instructor = new Instructor("", "");
        // checking
        for (Instructor i : dataModel.getInstructors()) {
            // check username matching
            if(i.getUsername().equals(username)) {
                // check if password matching
                if(i.getPassword().equals(password)) {
                    // user found
                    instructor = i;
                }
            }
        }
        return instructor;
    }
    private static boolean checkIfStudentUsernameUnique(String username, Data dataModel) {
        // initially set to true
        boolean isUsernameUnique = true;
        // looping over current students
        for (Student s : dataModel.getStudents()) {
            // checking username if equal
            if (s.getUsername().equals(username)) {
                isUsernameUnique = false;
                break;
            }
        }
        return isUsernameUnique;
    }
    private static boolean checkIfInstructorUsernameUnique(String username, Data dataModel) {
        // initially set to true
        boolean isUsernameUnique = true;
        // looping over current instructors
        for (Instructor i : dataModel.getInstructors()) {
            // checking username if equal
            if (i.getUsername().equals(username)) {
                isUsernameUnique = false;
                break;
            }
        }
        return isUsernameUnique;
    }
    private static String getRememberMeUserType() {
        return FileWorker.readFile("Data/Users/RememberMe.txt").getFirst().split(";")[1];
    }
    private static String getRememberMeUsernameValue() {
        return FileWorker.readFile("Data/Users/RememberMe.txt").getFirst().split(";")[0];
    }
    private static Student getRememberMeStudent(Data mainDataModel) {
        // student to be returned
        Student student = new Student("", "");
        // looping over students
        for (Student s : mainDataModel.getStudents()) {
            if (s.getUsername().equals(getRememberMeUsernameValue())) {
                student = s;
                break;
            }
        }
        return student;
    }
    private static Instructor getRememberInstructor(Data mainDataModel) {
        // instructor to be returned
        Instructor instructor = new Instructor("", "");
        // looping over instructors
        for (Instructor i : mainDataModel.getInstructors()) {
            if (i.getUsername().equals(getRememberMeUsernameValue())) {
                instructor = i;
                break;
            }
        }
        return instructor;
    }
}