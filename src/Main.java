import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Creating main data model and loading DATA
        Data mainDataModel = new Data();
        mainDataModel.loadData();

        // checking if remember me
        if (Authentication.checkRememberMe()) {
            // performing automatic login with remember me
            Authentication.performRememberMeLogin(mainDataModel);
        } else {
            // manual login in main menu
            displayMainMenu(mainDataModel);
        }
    }
    private static void checkIfInt(Scanner sc) {
        while(!sc.hasNextInt() ) {
            sc.nextLine();
            displayDottedLine();
            System.out.print("Invalid choice , enter again : ");
        }
    }
    private static void displayDottedLine() {
        System.out.println("********************************************");
    }
    private static void displayMainMenu(Data mainDataModel) {
        Scanner sc = new Scanner(System.in);
        int input = 0;
        // main loop
        do {
            // display message
            System.out.println("Welcome to QUIZY! \n 1) Login \n 2) Sign Up \n 3) Exit");

            System.out.print("Enter your choice (1 - 3): ");
            // handling invalid input
            checkIfInt(sc);
            if(sc.hasNextInt()) {
                input = sc.nextInt();
                while(input <= 0 || input > 3) {
                    displayDottedLine();
                    System.out.print("Invalid choice, enter again : ");
                    input = sc.nextInt();
                }
            }
            sc.nextLine(); // clearing buffer

            /*while(input <= 0 || input > 3) {
                displayDottedLine();
                System.out.print("Invalid choice, enter again: ");
                input = sc.nextInt();
            } */
            // switching on input
            switch (input) {
                // case of login
                case 1:
                    int input2 = 0;
                    // display message
                    displayDottedLine();
                    System.out.println("Choose your account type: \n 1) Student \n 2) Instructor \n 3) Back");
                    System.out.print("Enter your choice (1 - 3): ");
                    // taking input2
                    // handling invalid input
                    // handling invalid input
                    checkIfInt(sc);
                    if(sc.hasNextInt()) {
                        input2 = sc.nextInt();
                        while(input2 < 1 || input2 > 3) {
                            displayDottedLine();
                            System.out.print("Invalid choice, enter again : ");
                            input2 = sc.nextInt();
                        }
                    }
                    sc.nextLine(); // clearing buffer
                    /*
                    while(input2 <= 0 || input2 > 3) {
                        displayDottedLine();
                        System.out.print("Invalid choice, enter again: ");
                        input2 = sc.nextInt();
                    }*/
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
                    System.out.print("CHOICE (1 - 3): ");
                    // taking input3
                    // handling invalid input
                    checkIfInt(sc);
                    if(sc.hasNextInt()) {
                        input3 = sc.nextInt();
                        while(input3 <= 0 || input3 > 3) {
                            displayDottedLine();
                            System.out.print("Invalid choice, enter again : ");
                            input3 = sc.nextInt();
                        }
                    }
                    sc.nextLine(); // clearing buffer
                    /*while(input3 <= 0 || input3 > 3) {
                        displayDottedLine();
                        System.out.print("Invalid choice, enter again: ");
                        input3 = sc.nextInt();
                    }

                     */
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
}