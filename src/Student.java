import java.util.ArrayList;

public class Student {
    private String username;
    private String password;
    private ArrayList<Class> classesEnrolledIn;
    private int quizGrade;
    private int midtermGrade;
    private int finalGrade;

    public Student() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Class> getClassesEnrolledIn() {
        return classesEnrolledIn;
    }

    public void setClassesEnrolledIn(ArrayList<Class> classesEnrolledIn) {
        this.classesEnrolledIn = classesEnrolledIn;
    }

    public int getQuizGrade() {
        return quizGrade;
    }

    public void setQuizGrade(int quizGrade) {
        this.quizGrade = quizGrade;
    }

    public int getMidtermGrade() {
        return midtermGrade;
    }

    public void setMidtermGrade(int midtermGrade) {
        this.midtermGrade = midtermGrade;
    }

    public int getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(int finalGrade) {
        this.finalGrade = finalGrade;
    }
}
