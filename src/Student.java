import java.util.ArrayList;

public class Student {
    private String username;
    private String password;
    private ArrayList<StudentGrade> grades = new ArrayList<StudentGrade>();

    // Constructor
    public Student(String username, String password) {
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
    public ArrayList<StudentGrade> getGrades() {
        return grades;
    }

    // SETTERS
    public void setGrades(ArrayList<StudentGrade> grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        String result = "ROLE: STUDENT\n";
        String[] temp = this.username.split("_");
        result += "NAME: " + temp[0].toUpperCase() + " " + temp[1].toUpperCase() + "\n";

        return result;
    }
}
