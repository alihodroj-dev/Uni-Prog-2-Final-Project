public class StudentGrade {
    private final String testId;
    private final String dateTaken;
    private final double grade;

    // Constructor
    public StudentGrade(String testId, String dateTaken, double grade) {
        this.testId = testId;
        this.dateTaken = dateTaken;
        this.grade = grade;
    }

    // GETTERS
    public String getTestId() {
        return testId;
    }
    public String getDateTaken() {
        return dateTaken;
    }
    public double getGrade() {
        return grade;
    }
}
