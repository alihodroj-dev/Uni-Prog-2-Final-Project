import java.util.ArrayList;
import java.util.Objects;

public class Class {
    private String classId;
    private Quiz quiz;
    private Midterm midterm_test;
    private Final final_test;

    public Class(String classId) {
        this.classId = classId;
    }
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Midterm getMidterm_test() {
        return midterm_test;
    }

    public void setMidterm_test(Midterm midterm_test) {
        this.midterm_test = midterm_test;
    }

    public Final getFinal_test() {
        return final_test;
    }

    public void setFinal_test(Final final_test) {
        this.final_test = final_test;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() == Class.class) {
            Class c = (Class) obj;
            if(Objects.equals(c.getClassId(), this.getClassId())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
