package mazpen.rockettar.alarmproject.Objects;
import androidx.annotation.Nullable;
import java.util.UUID;

public class Reminder {
    public String id;
    public String title;
    public String description;
    public String ReminderDate;
    public String ReminderTime;

    public Reminder(String title, String description, String ReminderDate, String ReminderTime) {
        id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.ReminderDate = ReminderDate;
        this.ReminderTime = ReminderTime;
    }

    public boolean equals(@Nullable Reminder otherReminder) {
        /*
        if (otherReminder != null && title.equals(otherReminder.title) &&
                description.equals(otherReminder.description) && ReminderDate.equals(otherReminder.ReminderDate) &&
                ReminderTime.equals(otherReminder.ReminderTime)) {
            return true;
        }
        return false;
         */
        if(otherReminder!= null){
            return id.equals(otherReminder.id);
        }
        return false;
    }
}
