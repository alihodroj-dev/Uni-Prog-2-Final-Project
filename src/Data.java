import java.util.ArrayList;

public class Data {
    private final FileWorker fileWorker = new FileWorker();
    private ArrayList<Student> students = new ArrayList<Student>();
    private ArrayList<Instructor> instructors = new ArrayList<Instructor>();

    public void loadData() {
        // loading accounts
        this.loadAccounts();
        this.loadClassesAndTests();
    }

    public void loadAccounts() {
        // path variables
        final String studentsPath = "Data/Accounts/Students.txt";
        final String instructorsPath = "Data/Accounts/Instructors.txt";

        // initializing arrays to hold the accounts
        ArrayList<Student> students = new ArrayList<Student>();
        ArrayList<Instructor> instructors = new ArrayList<Instructor>();

        // arrays to hold data read from files
        ArrayList<String> linesFromStudentsFile = fileWorker.readFile(studentsPath);
        ArrayList<String> linesFromInstructorsFile = fileWorker.readFile(instructorsPath);

        // looping over lines of students file
        for(String line : linesFromStudentsFile) {
            // splitting line into parts
            final String[] lineParts = line.split(";");
            // creating new empty student object to fill with data
            Student s = new Student();
            // creating new empty array to fill with classes and then add to student object
            ArrayList<Class> classes = new ArrayList<Class>();
            // looping over line parts to fill student object with data
            for(int i = 0; i < lineParts.length; i++) {
                // setting username
                if(i == 0) {
                    s.setUsername(lineParts[i]);
                } else if(i == 1) {
                    // setting password
                    s.setPassword(lineParts[i]);
                } else if(i == 2) {
                    // setting quiz grade
                    s.setQuizGrade(Integer.parseInt(lineParts[i]));
                } else if(i == 3) {
                    // setting midterm grade
                    s.setMidtermGrade(Integer.parseInt(lineParts[i]));
                } else if(i == 4) {
                    // setting final grade
                    s.setFinalGrade(Integer.parseInt(lineParts[i]));
                } else {
                    // add class to classes array
                    Class c = new Class(lineParts[i]);
                    classes.add(c);
                }
            }
            // setting classes
            s.setClassesEnrolledIn(classes);
            // adding student object to student accounts data array
            this.students.add(s);
        }

        // looping over lines of instructors file
        for(String line : linesFromInstructorsFile) {
            // splitting line into parts
            final String[] lineParts = line.split(";");
            // creating new empty instructor object to fill with data
            Instructor i = new Instructor();
            // creating new empty array to fill with classes and then add to instructor object
            ArrayList<Class> classes = new ArrayList<Class>();
            // looping over line parts to fill instructor object with data
            for(int j = 0; j < lineParts.length; j++) {
                // setting username
                if(j == 0) {
                    i.setUsername(lineParts[j]);
                } else if(j == 1) {
                    // setting password
                    i.setPassword(lineParts[j]);
                } else {
                    // add class to classes array
                    Class c = new Class(lineParts[j]);
                    classes.add(c);
                }
            }
            // setting classes
            i.setClassesTeaching(classes);
            // adding instructor object to instructors accounts data array
            this.instructors.add(i);
        }
    }

    public void loadClassesAndTests() {
        // loading classes of students
        // looping over each student to set class for each one
        for(Student s : this.students) {
            for(Class c : s.getClassesEnrolledIn()) {
                // path for class directory
                final String path = "Data/Classes/" + c.getClassId();

                // final file data
                ArrayList<String> finalFileData = fileWorker.readFile(path + "/Final.txt");
                // midterm file data
                ArrayList<String> midtermFileData = fileWorker.readFile(path + "/Midterm.txt");
                // quiz file data
                ArrayList<String> quizFileData = fileWorker.readFile(path + "/Quiz.txt");

                // setting final data
                // creating empty Final object
                Final f = new Final();

                // looping over lines in finalFileData
                for(String line : finalFileData) {
                    // splitting line into parts
                    final String[] lineParts = line.split(";");
                    // creating empty Question object
                    Question q = new Question();
                    // looping over each part of the line
                    for(int i = 0; i < lineParts.length; i++) {
                        // setting description
                        if(i == 0) {
                            q.setDescription(lineParts[i]);
                        } else if(i == lineParts.length - 1) {
                            // setting correct answer index
                            q.setCorrectAnswerIndex(Integer.parseInt(lineParts[i]));
                        } else {
                            // setting options
                            q.getOptions().add(lineParts[i]);
                        }
                    }
                    // adding question to final object
                    f.getQuestions().add(q);
                    // setting final test for current class
                    c.setFinal_test(f);
                }

                // setting midterm data
                // creating empty midterm object
                Midterm m = new Midterm();

                // looping over lines in midtermFileData
                for(String line : midtermFileData) {
                    // splitting line into parts
                    final String[] lineParts = line.split(";");
                    // creating empty Question object
                    Question q = new Question();
                    // looping over each part of the line
                    for(int i = 0; i < lineParts.length; i++) {
                        // setting description
                        if(i == 0) {
                            q.setDescription(lineParts[i]);
                        } else if(i == lineParts.length - 1) {
                            // setting correct answer index
                            q.setCorrectAnswerIndex(Integer.parseInt(lineParts[i]));
                        } else {
                            // setting options
                            q.getOptions().add(lineParts[i]);
                        }
                    }
                    // adding question to midterm object
                    m.getQuestions().add(q);
                    // setting midterm test for current class
                    c.setMidterm_test(m);
                }

                // setting quiz data
                // creating empty Quiz object
                Quiz quiz = new Quiz();

                // looping over lines in quizFileData
                for(String line : quizFileData) {
                    // splitting line into parts
                    final String[] lineParts = line.split(";");
                    // creating empty Question object
                    Question q = new Question();
                    // looping over each part of the line
                    for(int i = 0; i < lineParts.length; i++) {
                        // setting description
                        if(i == 0) {
                            q.setDescription(lineParts[i]);
                        } else if(i == lineParts.length - 1) {
                            // setting correct answer index
                            q.setCorrectAnswerIndex(Integer.parseInt(lineParts[i]));
                        } else {
                            // setting options
                            q.getOptions().add(lineParts[i]);
                        }
                    }
                    // adding question to quiz object
                    quiz.getQuestions().add(q);
                    // setting final test for current class
                    c.setQuiz(quiz);
                }
            }
        }
    }

    public void saveData() {
        this.saveAccounts();
        this.saveClassesAndTests();
    }

    public void saveAccounts() {
        // saving students
        // creating array to hold the lines of each files
        String linesToBeWritten = "";
        String path = "Data/Accounts/";

        // looping over each student
        for(Student s : students) {
            // creating empty line
            String line = "";
            // adding dat to line
            line += s.getUsername() + ";";
            line += s.getPassword() + ";";
            line += s.getQuizGrade() + ";";
            line += s.getMidtermGrade() + ";";
            line += s.getFinalGrade() + ";";
            for(Class c : s.getClassesEnrolledIn()) {
                line += c.getClassId() + ";";
            }
            // adding line
            linesToBeWritten += line;
            // checking if last student to avoid extra line in the end
            if ((students.indexOf(s) + 1) != students.size()) {
                linesToBeWritten += "\n";
            }
        }
        // writing to file
        fileWorker.writeFile(linesToBeWritten, (path + "Students.txt"));

        // saving instructors
        linesToBeWritten = "";

        // looping over each instructor
        for(Instructor i : instructors) {
            // creating empty line
            String line = "";
            // adding dat to line
            line += i.getUsername() + ";";
            line += i.getPassword() + ";";
            for(Class c : i.getClassesTeaching()) {
                line += c.getClassId() + ";";
            }
            // adding line
            linesToBeWritten += line;
            // checking if last to avoid extra line in the end
            if ((instructors.indexOf(i) + 1) != instructors.size()) {
                linesToBeWritten += "\n";
            }
        }
        // writing to file
        fileWorker.writeFile(linesToBeWritten, (path + "Instructors.txt"));

    }

    public void saveClassesAndTests() {
        // creating array to hold all temp currently available
        ArrayList<Class> temp = new ArrayList<Class>();
        // adding all temp from students
        for(Student s : students) {
            for(Class c : s.getClassesEnrolledIn()) {
                if(!temp.contains(c)) {
                    temp.add(c);
                }
            }
        }
        // adding all temp from students
        for(Instructor i : instructors) {
            for(Class c : i.getClassesTeaching()) {
                if(!temp.contains(c)) {
                    temp.add(c);
                }
            }
        }

        // creating classes array and removing duplicates
        ArrayList<Class> classes = new ArrayList<Class>();
        for(Class c : temp.stream().distinct().toList()) {
            classes.add(c);
        }

        // looping over each class
        for(Class c : classes) {
            // creating path
            String path = "Data/Classes/" + c.getClassId() +"/";
            // data
            String quizData = "";
            String midtermData = "";
            String finalData = "";

            // saving Quiz
            for(Question q : c.getQuiz().getQuestions()) {
                // adding desc
                quizData += q.getDescription() + ";";
                // adding options
                for(String s : q.getOptions()) {
                    quizData += s + ";";
                }
                // adding correct answer
                quizData += q.getCorrectAnswerIndex();
                // checking for last to avoid extra empty line
                if(!((c.getQuiz().getQuestions().indexOf(q) + 1) == c.getQuiz().getQuestions().size())) {
                    quizData += "\n";
                }
            }

            // saving midterm
            for(Question q : c.getMidterm_test().getQuestions()) {
                // adding desc
                midtermData += q.getDescription() + ";";
                // adding options
                for(String s : q.getOptions()) {
                    midtermData += s + ";";
                }
                // adding correct answer
                midtermData += q.getCorrectAnswerIndex();
                // checking for last to avoid extra empty line
                if(!((c.getMidterm_test().getQuestions().indexOf(q) + 1) == c.getMidterm_test().getQuestions().size())) {
                    midtermData += "\n";
                }
            }

            // saving final
            for(Question q : c.getFinal_test().getQuestions()) {
                // adding desc
                finalData += q.getDescription() + ";";
                // adding options
                for(String s : q.getOptions()) {
                    finalData += s + ";";
                }
                // adding correct answer
                finalData += q.getCorrectAnswerIndex();
                // checking for last to avoid extra empty line
                if(!((c.getFinal_test().getQuestions().indexOf(q) + 1) == c.getFinal_test().getQuestions().size())) {
                    finalData += "\n";
                }
            }

            // writing data to files
            fileWorker.writeFile(quizData, (path + "Quiz.txt"));
            fileWorker.writeFile(midtermData, (path + "Midterm.txt"));
            fileWorker.writeFile(finalData, (path + "Final.txt"));
        }
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Instructor> getInstructors() {
        return instructors;
    }
}
