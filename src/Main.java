import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // creating initial data model
        Data data = new Data();
        // loading data
        data.loadData();
        /*
        // Authentication loop
        Scanner sc = new Scanner(System.in);
        int input = 0;

        do {
            // display message
            System.out.println("Welcome! \n 1) Login \n 2) Sign Up \n 3) Exit");
            System.out.print("Choice: ");
            input = sc.nextInt();
            // handling invalid input
            while(input <= 0 || input > 3) {
                System.out.print("Invalid choice, enter again: ");
                input = sc.nextInt();
            }
            // switching on input
            switch (input) {
                // case of login
                case 1:
                    int input2 = 0;
                    // display message
                    System.out.println("Choose your account type: \n 1) Student \n 2) Instructor \n 3) Back");
                    System.out.print("Choice: ");
                    // taking input2
                    input2 = sc.nextInt();
                    // handling invalid input
                    while(input2 <= 0 || input2 > 3) {
                        System.out.print("Invalid choice, enter again: ");
                        input2 = sc.nextInt();
                    }
                    // switching on input2
                    switch (input2) {
                        case 1:
                            Authentication.studentLogin();
                            input = 3;
                            break;
                        case 2:
                            Authentication.instructorLogin();
                            input = 3;
                            break;
                        case 3:
                            break;
                    }
                    break;

                case 2:
                    int input3 = 0;
                    // display message
                    System.out.println("Choose the type of account your want to create: \n 1) Student \n 2) Instructor \n 3) Back");
                    System.out.print("Choice: ");
                    // taking input3
                    input3 = sc.nextInt();
                    // handling invalid input
                    while(input3 <= 0 || input3 > 3) {
                        System.out.print("Invalid choice, enter again: ");
                        input3 = sc.nextInt();
                    }
                    // switching on input3
                    switch (input3) {
                        case 1:
                            Authentication.createStudent();
                            input = 3;
                            break;
                        case 2:
                            Authentication.createInstructor();
                            input = 3;
                            break;
                        case 3:
                            break;
                    }
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    break;
            }
        } while(input != 3);

         */
        Student s = new Student("" , "");
        s = data.getStudents().getFirst();
        s.loadMainMenu();

        /* Instructor i = new Instructor("" , "");
         i = data.getInstructors().getFirst();
         i.loadMainMenu();

         */
    }
}