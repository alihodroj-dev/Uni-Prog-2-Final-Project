import java.io.FileWriter;
import java.io.IOException;
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
        this.saveUsers();
        this.saveGrades();
        this.saveTests();
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
    private void saveUsers() {
        // saving students
        // file data to be written
        String studentsFileData = "";
        // looping over students
        for(Student s : this.students) {
            // line data
            String data = "";
            // adding username
            data += s.getUsername() + ";";
            // adding password
            data += s.getPassword();
            // adding data to file data
            studentsFileData += data;
            // checking if last to avoid empty line at the end
            if(!((this.students.indexOf(s) + 1 ) == students.size())) {
                studentsFileData += "\n";
            }
        }

        // saving instructors
        // file data to be written
        String instructorsFileData = "";
        // looping over instructors
        for(Instructor i : this.instructors) {
            // line data
            String data = "";
            // adding username
            data += i.getUsername() + ";";
            // adding password
            data += i.getPassword();
            // adding data to file data
            instructorsFileData += data;
            // checking if last to avoid empty line at the end
            if(!((this.instructors.indexOf(i) + 1 ) == instructors.size())) {
                instructorsFileData += "\n";
            }
        }

        try {
            FileWriter fileWriter1 = new FileWriter("Data/Users/Students.txt");
            FileWriter fileWriter2 = new FileWriter("Data/Users/Instructors.txt");
            fileWriter1.write(studentsFileData);
            fileWriter2.write(instructorsFileData);
            fileWriter1.close();
            fileWriter2.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private void saveGrades() {
        // path
        String path = "Data/Grades/";



        // looping over students
        for(Student s : this.students) {
            // file data to be written
            String fileData = "";
            for(StudentGrade g : s.getGrades()) {
                // line data
                String data = "";
                // adding test id
                data += g.getTestId() + ";";
                // adding date taken
                data += g.getDateTaken() + ";";
                // adding grade
                data += g.getGrade();
                // checking if last to avoid empty line at the end
                if(!((s.getGrades().indexOf(g) + 1 ) == s.getGrades().size())) {
                    data += "\n";
                }
                fileData += data;
            }

            try {
                FileWriter fileWriter = new FileWriter(path + s.getUsername() + ".txt");
                fileWriter.write(fileData);
                fileWriter.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private void saveTests() {
        // path
        String path = "Data/Tests/";
        // all current test file data
        String allCurrentTestsFileData = "";
        // looping over tests
        for(Test t : this.tests) {
            // adding test id
            allCurrentTestsFileData += t.getTestId() + ";";
            // adding test password
            allCurrentTestsFileData += t.getTestPassword() + ";";
            // adding test name
            allCurrentTestsFileData += t.getTestName() + ";";
            // adding test type
            allCurrentTestsFileData += t.getTestType();
            // checking if last to avoid empty line at the end
            if(!((this.tests.indexOf(t) + 1 ) == this.tests.size())) {
                allCurrentTestsFileData += "\n";
            }

            // current test file data
            String fileData = "";
            for(Question q : t.getQuestions()) {
                // line data
                String data = "";
                // adding desc
                data += q.getDescription() + ";";
                // adding options
                for(String o : q.getOptions()) {
                    data += o + ";";
                }
                // adding correct answer index
                data += q.getCorrectAnswerIndex();
                // checking if last to avoid empty line at the end
                if(!((t.getQuestions().indexOf(q) + 1 ) == t.getQuestions().size())) {
                    data += "\n";
                }
                // adding question
                fileData += data;
            }

            // writing each test file
            try {
                FileWriter fileWriter = new FileWriter(path + t.getTestId() + ".txt");
                fileWriter.write(fileData);
                fileWriter.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
        // writing all current tests file
        try {
            FileWriter fileWriter1 = new FileWriter(path + "AllCurrentTests.txt");
            fileWriter1.write(allCurrentTestsFileData);
            fileWriter1.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // GETTERS
    public ArrayList<Student> getStudents() {
        return students;
    }
    public ArrayList<Instructor> getInstructors() {
        return instructors;
    }
    public ArrayList<Test> getTests() {
        return tests;
    }
}
