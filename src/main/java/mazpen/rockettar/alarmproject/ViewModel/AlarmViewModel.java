package mazpen.rockettar.alarmproject.ViewModel;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.HashMap;
import mazpen.rockettar.alarmproject.Model.Repository;
import mazpen.rockettar.alarmproject.Objects.Reminder;
import mazpen.rockettar.alarmproject.Objects.User;

public class AlarmViewModel extends ViewModel {
    public HashMap<String, ArrayList<Reminder>> allUsers;
    public User currUser;
    public String selectedReminderId;
    Repository repository = Repository.getInstance();

    public AlarmViewModel() {
        super();
        currUser = repository.currUser;
        allUsers = repository.allUser;
        selectedReminderId = "";

    }

    public void removeReminder(Reminder selectedReminder) {
        repository.removeReminder(selectedReminder);
    }

    public void addReminder(String title, String desc, String date, String time) {
        repository.addReminder(title, desc, date, time);
    }

    public void updateUsername(HashMap<String, ArrayList<Reminder>> allUsers, User prevUser, String username) {
        repository.updateUsername(allUsers, prevUser, username);
    }

    public void updateReminder(Reminder selectedReminder, String title, String desc,
                               String date, String time){
        repository.updateReminder(selectedReminder, title, desc, date, time);
    }

    public boolean isUserExists(HashMap<String, ArrayList<Reminder>> allUsers, User user) {
        /*
        for (User user1 : allUsers) {
            if (user1.username.equals(user.username)) {
                return true;
            }
        }
        return false;

         */
        return allUsers.containsKey(user.username);
    }
    public Reminder getReminderById(String id){
        for(Reminder reminder: currUser.userReminders){
            if(reminder.id.equals(id)){
                return reminder;
            }
        }
        return null;
    }



}
