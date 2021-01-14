package mazpen.rockettar.alarmproject.Model;
import java.util.ArrayList;
import java.util.HashMap;
import mazpen.rockettar.alarmproject.Objects.Reminder;
import mazpen.rockettar.alarmproject.Objects.User;

public class Repository {
    private static Repository instance = null;
    public User currUser;
    public HashMap<String, ArrayList<Reminder>> allUser;

    private Repository() {
        currUser = new User("", "");
        allUser = new HashMap<>();
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public void updateUsername(HashMap<String, ArrayList<Reminder>> allUser, User prevUser, String username) {
        /*
        for (User user : allUser) {
            if (user.username.equals(prevUser.username)) {
                user.username = username;
            }
        }

         */
        ArrayList<Reminder> reminders = allUser.get(prevUser.username);
        allUser.remove(prevUser.username);
        allUser.put(username, reminders);
        currUser.username = username;
    }

    public void addReminder(String title, String desc, String date, String time) {
        Reminder reminder = new Reminder(title, desc, date, time);
        /*
        for (User user : allUser) {
            if (user.username.equals(currUser.username)) {
                user.userReminders.add(Reminder);
                currUser.userReminders = user.userReminders;
            }
        }
         */
        allUser.get(currUser.username).add(reminder);
        currUser.userReminders.add(reminder);
    }

    public void removeReminder(Reminder selectedReminder) {
        //ArrayList<Reminder> userReminder = currUser.userReminders;
        //int position = 0;
        currUser.userReminders.remove(selectedReminder);
        /*
        for (Reminder currReminder : userReminder) {
            if (selectedReminder.equals(currReminder)) {
                userReminder.remove(position);
                return;
            }
            position++;
        }
         */
        /*
        for (User user : allUser) {
            if (user.username.equals(currUser.username)) {
                user.userReminders = currUser.userReminders;
            }
        }
         */
        allUser.get(currUser.username).remove(selectedReminder);
    }

    public void updateReminder(Reminder selectedReminder, String title, String desc,
                               String date, String time){
        allUser.get(currUser.username).remove(selectedReminder);
        selectedReminder.title = title;
        selectedReminder.description = desc;
        selectedReminder.ReminderDate = date;
        selectedReminder.ReminderTime = time;
        allUser.get(currUser.username).add(selectedReminder);
    }


}


