package mazpen.rockettar.alarmproject.Model;

import java.io.Serializable;
import java.util.ArrayList;
import mazpen.rockettar.alarmproject.Objects.Notification;
import mazpen.rockettar.alarmproject.Objects.User;

public class LoginViewModel implements Serializable {

    private static LoginViewModel instance = null;

    public User currUser;
    public ArrayList<User> allUser;
    public Notification prevNotification = null;

    //constructor
    private LoginViewModel() {
        currUser = new User("", "");
        this.allUser = new ArrayList<>();
    }

    public static LoginViewModel getInstance() {
        if (instance == null) {
            instance = new LoginViewModel();
        }
        return instance;
    }
    public User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }


    public ArrayList<User> getAllUsers() {
        return allUser;
    }

    public void setAllUsernames(ArrayList<User> allUsernames) {
        this.allUser = allUsernames;
    }


    public void updateUsername(ArrayList<User> listUsers, User prevUser, String username) {
        for(User user: listUsers){
            if(user.username.equals(prevUser.username))
            {
                user.username = username;
            }
        }
        this.currUser.username = username;
    }

    public void addUserToList(ArrayList<User> listUsers, User user) {
        if (!listUsers.contains(user)) {
            listUsers.add(user);
        }
    }


    public void addNotificationToUser(String title, String desc, String date, String time) {
        Notification notification = new Notification(title, desc, date, time);
        //this.currUser.userNotifications.add(notification);
        for(User user : this.allUser){
            if(user.username.equals(this.currUser.username)) {
                user.userNotifications.add(notification);
                getInstance().currUser.userNotifications = user.userNotifications;
            }
        }
    }

    public void removeNotificationFromUser(Notification prevNotification) {
        ArrayList<Notification> userNotification = this.currUser.userNotifications;
        int position = 0;
        for (Notification noti : userNotification) {
            if(prevNotification.equals(noti))
            {
                userNotification.remove(position);
                return;
            }
            position++;
        }
        for(User user : this.getAllUsers()){
            if(user.username.equals(this.currUser.username)) {
                user.userNotifications = this.currUser.userNotifications;
            }}
    }
}


