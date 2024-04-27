import java.util.ArrayList;

public class Instructor {
    private String username;
    private String password;
    private ArrayList<Class> classesTeaching;

    public Instructor() {

    }

    public Instructor(String username, String password) {
        this.username = username;
        this.password = password;
    }

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

    public ArrayList<Class> getClassesTeaching() {
        return classesTeaching;
    }

    public void setClassesTeaching(ArrayList<Class> classesTeaching) {
        this.classesTeaching = classesTeaching;
    }
}
