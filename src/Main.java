import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Creating main data model and loading DATA
        Data mainDataModel = new Data();
        mainDataModel.loadData();

        // Authentication loop
        Scanner sc = new Scanner(System.in);
        int input = 0;
        // Main menu loop
        do {
            // display message
            System.out.println("Welcome to QUIZY! \n 1) Login \n 2) Sign Up \n 3) Exit");
            System.out.print("Choice: ");
            input = sc.nextInt();
            // handling invalid input
            while(input <= 0 || input > 3) {
                displayDottedLine();
                System.out.print("Invalid choice, enter again: ");
                input = sc.nextInt();
            }
            // switching on input
            switch (input) {
                // case of login
                case 1:
                    int input2 = 0;
                    // display message
                    displayDottedLine();
                    System.out.println("Choose your account type: \n 1) Student \n 2) Instructor \n 3) Back");
                    System.out.print("Choice: ");
                    // taking input2
                    input2 = sc.nextInt();
                    // handling invalid input
                    while(input2 <= 0 || input2 > 3) {
                        displayDottedLine();
                        System.out.print("Invalid choice, enter again: ");
                        input2 = sc.nextInt();
                    }
                    // switching on input2
                    switch (input2) {
                        case 1:
                            displayDottedLine();
                            Authentication.studentLogin(mainDataModel);
                            input = 3;
                            break;
                        case 2:
                            displayDottedLine();
                            Authentication.instructorLogin(mainDataModel);
                            input = 3;
                            break;
                        case 3:
                            displayDottedLine();
                            break;
                    }
                    break;

                case 2:
                    int input3 = 0;
                    // display message
                    displayDottedLine();
                    System.out.println("Choose the type of account your want to create: \n 1) Student \n 2) Instructor \n 3) Back");
                    System.out.print("Choice: ");
                    // taking input3
                    input3 = sc.nextInt();
                    // handling invalid input
                    while(input3 <= 0 || input3 > 3) {
                        displayDottedLine();
                        System.out.print("Invalid choice, enter again: ");
                        input3 = sc.nextInt();
                    }
                    // switching on input3
                    switch (input3) {
                        case 1:
                            displayDottedLine();
                            Authentication.createStudent(mainDataModel);
                            input = 3;
                            break;
                        case 2:
                            displayDottedLine();
                            Authentication.createInstructor(mainDataModel);
                            input = 3;
                            break;
                        case 3:
                            displayDottedLine();
                            break;
                    }
                    break;

                case 3:
                    displayDottedLine();
                    System.out.println("Goodbye!");
                    break;
            }
        } while(input != 3);
    }

    private static void displayDottedLine() {
        System.out.println("********************************************");
    }
}