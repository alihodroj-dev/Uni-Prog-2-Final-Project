import java.util.ArrayList;

public abstract class Test {
    private String testName;
    private String testId;
    private String testPassword;
    private char testType;
    private int percentage;
    private int numberOfQuestions;
    private ArrayList<Question> questions = new ArrayList<Question>();

    // Constructor
    public Test(String testId, String testPassword, String testName, char testType) {
        this.testName = testName;
        this.testId = testId;
        this.testPassword = testPassword;
        this.testType = testType;
        switch (this.testType) {
            case 'Q':
                this.percentage = 20;
                this.numberOfQuestions = 5;
                break;
            case 'M':
                this.percentage = 35;
                this.numberOfQuestions = 10;
                break;
            case 'F':
                this.percentage = 45;
                this.numberOfQuestions = 15;
                break;
        }
    }

    // GETTERS
    public String getTestName() {
        return testName;
    }
    public String getTestId() {
        return testId;
    }
    public String getTestPassword() {
        return testPassword;
    }
    public char getTestType() {
        return testType;
    }
    public int getPercentage() {
        return percentage;
    }
    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    // SETTERS
    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
