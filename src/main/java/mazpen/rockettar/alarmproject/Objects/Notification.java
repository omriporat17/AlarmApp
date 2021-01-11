package mazpen.rockettar.alarmproject.Objects;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Notification implements Serializable {
    public String title;
    public String description;
    public String notificationDate;
    public String notificationTime;

    public Notification(String title, String description, String notificationDate, String notificationTime){
        this.title = title;
        this.description = description;
        this.notificationDate = notificationDate;
        this.notificationTime = notificationTime;
    }

    public boolean equals(@Nullable Notification other_notification) {
        if(this.title.equals(other_notification.title) && this.description.equals(other_notification.description)) {
            if(this.notificationDate.equals(other_notification.notificationDate) && this.notificationTime.equals(other_notification.notificationTime)) {
                return true;
            }
        }
        return false;
    }
}
