import java.util.ArrayList;
import java.util.Scanner;

public class Instructor {
    private String username;
    private String password;

    // Constructor
    public Instructor(String username, String password) {
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
}
