package mazpen.rockettar.alarmproject.Objects;

import java.util.ArrayList;

public class User {
    public String username;
    public String password;
    public ArrayList<Reminder> userReminders;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.userReminders = new ArrayList<Reminder>();
    }
}

