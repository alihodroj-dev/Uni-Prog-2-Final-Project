import java.util.ArrayList;

public class Question {
    private String description;
    private ArrayList<String> options = new ArrayList<String>();
    private int correctAnswerIndex;

    // Constructor
    public Question(String description, ArrayList<String> options, int correctAnswerIndex) {
        this.description = description;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    // GETTERS
    public String getDescription() {
        return description;
    }
    public ArrayList<String> getOptions() {
        return options;
    }
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}
