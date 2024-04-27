import java.util.ArrayList;

public abstract class Test {
    private char testType;
    private int percentage;
    private int numberOfQuestions;

    private ArrayList<Question> questions = new ArrayList<Question>();

    public Test(char testType, int percentage, int numberOfQuestions) {
        this.testType = testType;
        this.percentage = percentage;
        this.numberOfQuestions = numberOfQuestions;
    }

    public char getTestType() {
        return testType;
    }

    public void setTestType(char testType) {
        this.testType = testType;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
