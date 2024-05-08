import java.io.File;
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


    // GETTERS

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }


    // MAIN MENU

    public void loadMainMenu(Data data) {
        int choice;
        String[] temp = this.username.split("_");

        System.out.println("Hello " + temp[0] + " " + temp[1] + ", What do you want to do next?");

        do {
            System.out.print(
                    " 1) Display Account Information\n" +
                            " 2) Edit Account Information\n" +
                            " 3) Create test\n" +
                            " 4) Update existing test\n" +
                            " 5) Delete test\n" +
                            " 6) Change remember me option\n" +
                            " 7) Logout\n" +
                            "Enter your choice (1 - 7 ): "
            );
            while(!sc.hasNextInt()) {
                displayDottedLine();
                System.out.println("Enter a valid choice ( 1 - 7 ) .");
                sc.nextLine(); // clearing buffer
            }
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
                    updateTest(data);
                    break;
                case 5:
                    deleteTest(data);
                    break;
                case 6:
                    changeRememberMeOption();
                    break;
                case 7:
                    break;
                default:
                    displayInvalid();
                    break;
            }
        } while (choice != 7);
        data.saveData(); // Saving data in case of any changes
        System.out.println("Logging you out...");
    }


    // UPDATE ACCOUNT MENU

    private void loadAccountMenu(Data data) {
        int mainChoice;

        do {
            System.out.print(
                    " 1) Edit First Name\n" +
                            " 2) Edit Last Name\n" +
                            " 3) Edit Password\n" +
                            " 4) Go Back\n" +
                            "Enter your choice (1 - 4): "
            );
            while(!sc.hasNextInt()) {
                displayDottedLine();
                System.out.println("Enter a valid choice ( 1 - 6 ) .");
                sc.nextLine(); // clearing buffer
            }

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
                    displayInvalid();
            }
        } while (mainChoice != 4);
    }


    // CREATE METHOD ( TEST )

    private void createTest(Data data) {
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


    // UPDATE METHOD ( TEST )

    private void updateTest(Data data) {
        String tID;
        String tPassword;
        String tName;
        do {
            System.out.print("Enter new test ID : ");
            tID = sc.nextLine();
            displayDottedLine();
        }while (!isTestAvailable(tID , data));
        int index = testIndexFinder(tID , data);
        if(index != -1) {
            Test t = data.getTests().get(index);
            int attempts = 3;
            do{
                System.out.print("Enter test password : ");
                tPassword = sc.nextLine();
                displayDottedLine();
                if(!t.getTestPassword().equals(tPassword)) {
                    System.out.print("Invalid Password...Try again");
                    attempts--;
                    if(attempts != 0)
                        System.out.println("You have " + attempts + " attempts left");
                    else
                        System.out.println("No attempts left...Navigating you back!");
                }
            }while (!t.getTestPassword().equals(tPassword) && attempts!=0);
            if(attempts !=0) {
                int choice;
                char testType = t.getTestType();
                tName = t.getTestName();
                Test updated;
                do {
                    toggleUpdateMenu();
                    while (!sc.hasNextInt()) {
                        displayDottedLine();
                        System.out.println("Enter a valid choice ( 1 - 5 ) : ");
                        sc.nextLine(); // clearing buffer
                    }
                    choice = sc.nextInt();
                    sc.nextLine();
                    displayDottedLine();
                    switch (choice) {
                        case 1:
                            tID = setTestID(data);
                            break;
                        case 2:
                            tName = setTestName();
                            break;
                        case 3:
                            tPassword = setTestPassword();
                            break;
                        case 4:
                            int updateQChoice;
                            int bound = t.getQuestions().size();
                            do {
                                toggleUpdateQuestionMenu(t , bound);
                                while (!sc.hasNextInt()) {
                                    displayDottedLine();
                                    System.out.println("Enter a valid choice ( 1 - " + bound + " ) : ");
                                    sc.nextLine(); // clearing buffer
                                }
                                updateQChoice = sc.nextInt();
                                sc.nextLine();
                                displayDottedLine();
                                if (updateQChoice != (bound + 1))
                                    updateSpecificQuestion(t, t.getQuestions().get(updateQChoice - 1));
                            } while (updateQChoice != (bound + 1));
                            break;
                        case 5:
                            break;
                        default:
                            displayInvalid();
                    }
                }while (choice != 5);
                if (testType == 'Q')
                    updated = new Quiz(tID, tPassword, testType, tName);
                else if (testType == 'M')
                    updated = new Midterm(tID, tPassword, testType, tName);
                else
                    updated = new Final(tID, tPassword, testType, tName);
                if(!t.equals(updated)) {
                    updated.setQuestions(t.getQuestions());
                    int tIndex = data.getTests().indexOf(t);
                    renameTestFile(t.getTestId() , tID);
                    data.getTests().set(tIndex , updated);
                }
            }
        }
    }


    // DELETE METHOD ( TEST )

    private void deleteTest( Data data) {
        String testID;
        do {
            System.out.print("Enter test ID : ");
            testID = sc.nextLine().trim(); // Trim to remove leading/trailing spaces
            if(testID.isEmpty()) System.out.println("Field shouldn't be empty!");
        }while (testID.isEmpty());
        String testPassword;
        int index =-1;
        boolean found = false;
        Test toBeDeleted;
        for(int i=0 ; i< data.getTests().size() ; i++) {
            if(data.getTests().get(i).getTestId().equals(testID)) {
                testPassword = data.getTests().get(i).getTestPassword();
                found = true;
                index = i;
                break;
            }
        }
        if(!found) System.out.println("Test was not found!...navigating you back");
        else {
            String choice;

            do {
                System.out.print("Are you sure you want to delete the test of id "
                                     + testID + " ? ( yes / no ) : ");;
                choice = sc.nextLine();
                if(choice.isEmpty())
                    System.out.println("Field shouldn't be empty!");
                if(!choice.equalsIgnoreCase("yes") &&
                        !choice.equalsIgnoreCase("no"))
                         displayInvalid();
            }while (!choice.equalsIgnoreCase("yes") &&
                    !choice.equalsIgnoreCase("no"));
            if(choice.equalsIgnoreCase("yes")) {
                int attempts =3;
                do {
                    System.out.print("Enter test password : ");
                    testPassword = sc.nextLine();
                    if(!testPassword.equals(data.getTests().get(index).getTestPassword())
                            && attempts !=0) {
                        attempts--;
                        System.out.println("Invalid password..." + attempts + " attempts left!");
                    }
                }while (!testPassword.equals(data.getTests().get(index).getTestPassword())
                        && attempts !=0);
                if(attempts != 0) {
                    data.getTests().remove(index);
                    deleteTestFile(testID);
                    System.out.println("Test was successfully deleted");
                }
                else {
                    System.out.println("No attempts left ! Navigating you back....");
                }
            }
            else {
                System.out.println("Deletion cancelled ! Navigating you back....");
            }
        }
    }


    // UPDATE METHODS ( INSTRUCTOR )

    private void updateName(Data data, int choice) {
        int tempIndex = (choice == 1) ? 0 : 1;
        String str = (choice == 1) ? "First" : "Last";
        String[] temp = this.username.split("_");
        String newName;

        do {
            System.out.print("Enter your new " + str + " name : ");
            newName = sc.nextLine();
            displayDottedLine();

            // Removing Spaces . if any
            newName = newName.replaceAll(" ", "");

            if (newName.isEmpty()) {
                System.out.println("Field shouldn't be empty!");
                displayDottedLine();
            }

            if (newName.equalsIgnoreCase(temp[tempIndex])) {
                System.out.println("Please Enter a name other than the current one... No Changes were made!");
                displayDottedLine();
            }
        } while (newName.isEmpty() || newName.equalsIgnoreCase(temp[tempIndex]));

        int index = instructorIndexFinder(username , data);

        if (index != -1) {
            Random rand = new Random();
            String oldUsername = this.username;
            String newUsername;
            do {

                if (choice == 1)
                    newUsername = newName + "_" + temp[1] + "_" + rand.nextInt(1000, 10000);
                else
                    newUsername = temp[0] + "_" + newName + "_" + rand.nextInt(1000, 10000);

            } while (isTaken(newUsername, data));

            this.username = newUsername;

            data.getInstructors().get(index).username = this.username;
            System.out.println(str + " Name was Successfully Modified");
            System.out.println("Your new username is : " + username);
            displayDottedLine();
        }
    }
    private void updatePassword(Data data) {
        final int MINLENGTH = 8, MAXLENGTH = 16;
        String newPassword, confirmPassword;
        int attempts = 3;

        do {
            System.out.print("Enter your new password : ");
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
            System.out.print("Confirm your new password : ");
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
            int index = instructorIndexFinder(username, data);
            this.password = newPassword;
            data.getInstructors().get(index).password = this.password;
            System.out.println("Password was Successfully modified");
            displayDottedLine();
        }

    }
    private void changeRememberMeOption() {
        if(Authentication.checkRememberMe()) {
            String input = "";
            System.out.print("Remember me option is turned on \nDo you want to turn it off? (Yes/No) : ");
            input = sc.nextLine();
            while(!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no")) {
                displayDottedLine();
                System.out.print("Invalid choice, please enter again: ");
                input = sc.nextLine();
            }
            // turning off remember me option
            if (input.equalsIgnoreCase("yes")) {
                Authentication.turnOffRememberMeOption();
                System.out.println("Remember me option was turned off, navigating you back...");
                displayDottedLine();
            } else {
                displayDottedLine();
                System.out.println("Remember me option is still on, navigating you back...");
                displayDottedLine();
            }
        } else {
            String input = "";
            System.out.print("Do you want to turn on remember me option? (Yes/No) : ");
            input = sc.nextLine();
            while(!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no")) {
                displayDottedLine();
                System.out.print("Invalid choice, please enter again: ");
                input = sc.nextLine();
            }
            // turn on remember me
            if (input.equalsIgnoreCase("yes")) {
                displayDottedLine();
                System.out.println("Turning on remember me option...");
                Authentication.setRememberMe(this.username, "I");
                System.out.println("Remember me option is turned on, navigating you back...");
                displayDottedLine();
            } else {
                System.out.println("Remember me is unchanged, navigating you back...");
                displayDottedLine();
            }
        }
    }


    // DISPLAYS

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


    // CREATE TEST HELPER METHODS

    private String setTestID(Data data) {
        String id;
        final int MINLENGTH = 4;
        do {
            System.out.print("Enter test ID: ");
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
    private String setTestPassword() {
        String password;
        String confirmPassword;
        final int MINLENGTH = 8, MAXLENGTH = 16;
        do {
            System.out.print("Enter test password: ");
            password = sc.nextLine();
            displayDottedLine();
            // Check password requirements
            if (password.isEmpty())
                System.out.println("Field shouldn't be empty!");
            if (password.length() < MINLENGTH)
                System.out.println("Password must have at least " + MINLENGTH + " characters");
            if (password.length() > MAXLENGTH)
                System.out.println("Password must have at most " + MAXLENGTH + " characters");
        } while (password.length() < MINLENGTH || password.length() > MAXLENGTH);

        do {
            System.out.print("Confirm test password : ");
            confirmPassword = sc.nextLine();
            displayDottedLine();
            // Confirm password
            if (!confirmPassword.equals(password)) {
                System.out.println("Passwords Don't Match, try again");
            }
        } while (!confirmPassword.equals(password));
        return password;
    }
    private String setTestName() {
        String name;
        do {
            System.out.print("Enter test name : ");
            name = sc.nextLine();
            displayDottedLine();
            // Check if the name is not empty
            if (name.isEmpty())
                System.out.println("Field shouldn't be empty!");
        } while (name.isEmpty());
        return name;
    }
    private char setTestType() {
        int choice;
        System.out.println("Choose your test's type:");
        do {
            System.out.print(" 1) Quiz\n 2) Midterm\n 3) Final\n" +
                    "Enter your choice (1 - 3): ");
            while(!sc.hasNextInt()) {
                displayDottedLine();
                System.out.println("Enter a valid option ( 1 - 3 ) : ");
                sc.nextLine(); // clearing buffer
            }
            choice = sc.nextInt();
            sc.nextLine();
            displayDottedLine();
            // Validate choice
            if (choice < 1 || choice > 3)
                displayInvalid();
        } while (choice < 1 || choice > 3);

        if(choice ==1)
            return 'Q';
        else if(choice ==2)
            return 'M';
        else
            return 'F';
    }
    private void setTestQuestions(Test test) {
        ArrayList<Question> questions = new ArrayList<>();
        for (int i = 0; i < test.getNumberOfQuestions(); i++) {
            System.out.println("Question " + (i + 1) + " : ");
            Question q = setQuestion();
            questions.add(q);
        }
        test.setQuestions(questions);
    }
    private Question setQuestion() {
        String description = setQuestionDescription();

        ArrayList<String> options = setOptions();
        int correctIndex = setCorrectAnswer();

        return new Question(description, options, correctIndex);
    }
    private String setQuestionDescription() {
        String description;
        do {
            System.out.print("Enter the description : ");
            description = sc.nextLine();
            displayDottedLine();
            if (description.isEmpty())
                System.out.println("Field shouldn't be empty!");
        } while (description.isEmpty());
        return description;
    }
    private ArrayList<String> setOptions() {
        ArrayList<String> options = new ArrayList<>();
        final int NBOPTIONS = 4;
        String tempChoice;
        System.out.println("Enter choices : ");
        for (int i = 0; i < NBOPTIONS; i++) {
            do {
                System.out.print("Choice " + (i + 1) + ": ");
                tempChoice = sc.nextLine();
                displayDottedLine();
                if (tempChoice.isEmpty())
                    System.out.println("Field shouldn't be empty!");
            } while (tempChoice.isEmpty());
            options.add(tempChoice);
        }
        return options;
    }
    private int setCorrectAnswer() {
        int index;
        do {
            System.out.print("Enter the correct answer ( 1 - 4 ): ");
            while(!sc.hasNextInt()) {
                System.out.println("Enter a valid choice ( 1 - 4 ) .");
                sc.nextLine(); // clearing buffer
                displayDottedLine();
            }
            index = sc.nextInt();
            sc.nextLine(); // Clear buffer after input
            displayDottedLine();
            if (index < 1 || index > 4)
                displayInvalid();
        } while (index < 1 || index > 4);
        return index - 1;
    }
    private boolean isTestIdTaken(String id, Data data) {
        for (Test t : data.getTests()) {
            if (t.getTestId().equalsIgnoreCase(id)) return true;
        }
        return false;
    }


    // DELETE TEST HELPER METHODS

    private  void deleteTestFile(String ID) {
        String path ="Data/Tests/" + ID + ".txt";
        File fileToDelete = new File(path);
        fileToDelete.delete();
    }


    // UPDATE TEST HELPER METHODS

    private int testIndexFinder(String id , Data data) {
        for(int i=0 ; i < data.getTests().size() ; i++) {
            if(data.getTests().get(i).getTestId().equals(id)) return i;
        }
        return -1;
    }
    private boolean isTestAvailable(String id , Data data) {
        if(testIndexFinder(id , data) == -1) {
            System.out.println("Test was not found !");
            return false;
        }
        return true;
    }
    private void updateSpecificQuestion(Test t ,Question q) {
        displayQuestion(q);
        String description = q.getDescription();
        ArrayList<String> options = q.getOptions();
        int correctIndex = q.getCorrectAnswerIndex();
        int choice;
        do{
        System.out.println(" 1) Change description\n" +
                " 2) Change a choice\n" +
                " 3) Change the correct answer\n" +
                " 4) Go back and save changes");

            System.out.print("Enter your choice ( 1 - 4 ) :");
            while (!sc.hasNextInt()) {
                displayDottedLine();
                System.out.println("Enter a valid choice ( 1 - 4 ) .");
                sc.nextLine(); // clearing buffer
            }
            choice = sc.nextInt();
            sc.nextLine();
            displayDottedLine();
            switch (choice) {
                case 1:
                    System.out.println("Current description : " + description);
                    description = setQuestionDescription();
                    System.out.println("Description updated successfully");
                    break;
                case 2:
                    updateSpecificOptionMenu(options);
                    break;
                case 3:
                    System.out.println("Current correct answer : " + (correctIndex+1));
                    correctIndex = setCorrectAnswer();
                    System.out.println("Correct answer updated successfully");
                    break;
                case 4:
                    break;
                default:
                    displayInvalid();
            }
        }while (choice !=4);
        Question temp = new Question(description , options , correctIndex);
        if(!q.equals(temp)) {
            int qIndex = t.getQuestions().indexOf(q);
            t.getQuestions().set(qIndex , temp);
        }
    }
    private void updateSpecificOptionMenu(ArrayList<String> options) {
        int oChoice;
        do {
            System.out.println("Enter the option you want to change ( 1 - 4 ) : ");
            while (!sc.hasNextInt()) {
                displayDottedLine();
                System.out.println("Enter a valid choice ( 1 - 4 ) .");
                sc.nextLine(); // clearing buffer
            }
            oChoice = sc.nextInt();
            sc.nextLine();
            displayDottedLine();
            if(oChoice < 1 || oChoice > 4)
                displayInvalid();
        }   while (oChoice < 1 || oChoice > 4 );
        oChoice--;
        System.out.println("Current option : " + options.get(oChoice) ) ;
        options.set(oChoice , updateSpecificOption(oChoice));
    }
    private String updateSpecificOption(int choice) {
        String tempChoice;
            do {
                System.out.print("Enter updated choice  " + (choice+1) + ": ");
                tempChoice = sc.nextLine();
                displayDottedLine();
                if (tempChoice.isEmpty())
                    System.out.println("Field shouldn't be empty!");
            } while (tempChoice.isEmpty());
        System.out.println("Choice updated successfully");
        return  tempChoice;
    }
    private void displayQuestion(Question q) {
        System.out.println("Description : " + q.getDescription());
        System.out.println("=============================");
        System.out.println("Options : ");
        for(int i=0 ; i< q.getOptions().size(); i++) {
            System.out.println(" Option " + (i+1) + " : " +  q.getOptions().get(i));
        }
        System.out.println("=============================");
        System.out.println("Correct answer index : " + (q.getCorrectAnswerIndex() +1));
        System.out.println("=============================");
    }
    private void renameTestFile(String oldID, String newID) {
        String oldFilePath = "Data/Tests/" + oldID + ".txt";
        String newFilePath = "Data/Tests/" + newID + ".txt";
        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);
        oldFile.renameTo(newFile);
    }
    private void toggleUpdateMenu() {
        System.out.println(" 1) Change test ID\n" +
                " 2) Change test Name\n" +
                " 3) Change test password\n" +
                " 4) Set new questions\n" +
                " 5) Save Changes");
        System.out.print("Enter your choice ( 1 - 5 ) : ");
    }
    private void toggleUpdateQuestionMenu(Test t, int bound) {
        System.out.println("Choose the question you want to update : ");
        for (int i = 0; i < t.getQuestions().size(); i++) {
            System.out.println(" " + (i + 1) + ") " + t.getQuestions().get(i).getDescription());
        }
        System.out.println(" " + ( bound + 1) + ") Go back and save changes");
        System.out.print("Enter your choice ( 1 - " + bound + " ) : ");
    }


    // UPDATE USERNAME HELPER METHODS

    private int instructorIndexFinder(String u, Data data) {
        for (int i = 0; i < data.getInstructors().size(); i++) {
            if (data.getInstructors().get(i).username.equalsIgnoreCase(u)) return i;
        }
        return -1;
    }
    private boolean isTaken(String username, Data data) {
        for (Instructor i : data.getInstructors()) {
            if (i.username.equalsIgnoreCase(username)) return true;
        }
        return false;
    }


    // DEFAULT DISPLAY HELPER METHOD

    private static void displayDottedLine() {
        System.out.println("********************************************");
    }
    private static void displayInvalid() { System.out.println("Invalid Choice!...Try Again"); }
}
