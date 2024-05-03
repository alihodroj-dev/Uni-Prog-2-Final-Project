import java.util.ArrayList;

public class Data {
    private final FileWorker fileWorker = new FileWorker();
    private ArrayList<Student> students = new ArrayList<Student>();
    private ArrayList<Instructor> instructors = new ArrayList<Instructor>();
    private ArrayList<Test> tests = new ArrayList<Test>();

    public void loadData() {
        this.loadUsers();
        this.loadGrades();
        this.loadTests();
    }
    public void saveData() {

    }

    // HELPER METHODS
    private void loadUsers() {
        // path
        final String path = "Data/Users/";

        // loading students
        ArrayList<String> studentsFileData = fileWorker.readFile(path + "Students.txt");
        // looping over each student
        for(String line : studentsFileData) {
            // splitting line into parts
            String[] lineParts = line.split(";");
            // adding new student
            this.students.add(new Student(lineParts[0], lineParts[1]));
        }

        // loading instructors
        ArrayList<String> instructorsFileData = fileWorker.readFile(path + "Instructors.txt");
        // looping over each instructor
        for(String line : instructorsFileData) {
            // splitting line into parts
            String[] lineParts = line.split(";");
            // adding new instructor
            this.instructors.add(new Instructor(lineParts[0], lineParts[1]));
        }
    }
    private void loadGrades() {
        // looping over each student
        for(Student s : students) {
            // path
            String filePath = "Data/Grades/" + s.getUsername() + ".txt";
            // data from file
            ArrayList<String> fileData = fileWorker.readFile(filePath);
            // looping over lines in file
            for(String line : fileData) {
                // splitting line into parts
                String[] lineParts = line.split(";");
                // add grade to student
                s.getGrades().add(new StudentGrade(lineParts[0], lineParts[1], Double.parseDouble(lineParts[2])));
            }
        }
    }
    private void loadTests() {
        // file data
        ArrayList<String> fileData = fileWorker.readFile("Data/Tests/AllCurrentTests.txt");
        // looping over lines
        for(String line : fileData) {
            // splitting line into parts
            String[] lineParts = line.split(";");
            // adding test
            switch (lineParts[3].charAt(0)) {
                case 'Q':
                    this.tests.add(new Quiz(lineParts[0], lineParts[1], lineParts[3].charAt(0), lineParts[2]));
                    break;
                case 'M':
                    this.tests.add(new Midterm(lineParts[0], lineParts[1], lineParts[3].charAt(0), lineParts[2]));
                    break;
                case 'F':
                    this.tests.add(new Final(lineParts[0], lineParts[1], lineParts[3].charAt(0), lineParts[2]));
                    break;
            }
        }

        // looping over tests
        for(Test t : this.tests) {
            // path
            final String path = "Data/Tests/" + t.getTestId() + ".txt";
            // file data
            ArrayList<String> testFileData = fileWorker.readFile(path);
            // looping over lines
            for(String line : testFileData) {
                // splitting line into parts
                String[] lineParts = line.split(";");
                // adding questions to test
                ArrayList<String> options = new ArrayList<String>();
                options.add(lineParts[1]);
                options.add(lineParts[2]);
                options.add(lineParts[3]);
                options.add(lineParts[4]);
                t.getQuestions().add(new Question(lineParts[0], options, Integer.parseInt(lineParts[5])));
            }
        }
    }

    // GETTERS
    public ArrayList<Student> getStudents() {
        return students;
    }
    public ArrayList<Instructor> getInstructors() {
        return instructors;
    }
}
