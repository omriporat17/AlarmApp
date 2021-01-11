package mazpen.rockettar.alarmproject.Objects;
import java.util.ArrayList;

public class User {
    public String username;
    public String password;
    public ArrayList<Notification> userNotifications;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.userNotifications = new ArrayList<Notification>();
    }
/*
    public void AddNotification(Notification notification){
        this.userNotifications.add(notification);
    }

 */
}

