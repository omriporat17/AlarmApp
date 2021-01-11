package mazpen.rockettar.alarmproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import mazpen.rockettar.alarmproject.Objects.Notification;
import mazpen.rockettar.alarmproject.Objects.User;
import mazpen.rockettar.alarmproject.Model.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private final LoginViewModel loginViewModel = LoginViewModel.getInstance();
    private EditText etUsername;
    private FloatingActionButton etLogin;
    public ArrayList<String> allUsers = new ArrayList<String>();
    public String inputName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.usernameButton);
        etLogin = findViewById(R.id.loginButton);
        etLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputName = etUsername.getText().toString();
                boolean isValid;
                isValid = validate(inputName);
                if (inputName.isEmpty() || !isValid) {
                    etLogin.setEnabled(false);
                } else {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        putInModel();
                        startActivity(intent);
                    }
                etLogin.setEnabled(true);
            }
        });
    }
    private boolean validate(String name) {
        if (!allUsers.contains(name)) {
            allUsers.add(name);
        }
        return true;
    }

    private boolean isUserExists(ArrayList<User> allUsers, User user) {
        for(User user1: allUsers){
            if(user1.username.equals(user.username)){
                return true;
            }
        }
        return false;
    }


    private void putInModel()
    {
        User user = new User(inputName, "");
        ArrayList<User> allUsers = loginViewModel.getAllUsers();
        if(!isUserExists(allUsers, user))
        {
            loginViewModel.allUser.add(user);
            loginViewModel.getCurrUser().userNotifications = new ArrayList<Notification>();
        }
        else{
            loginViewModel.getCurrUser().userNotifications = getUserNotificationsFromAllUsers(user);
        }
        loginViewModel.getCurrUser().username = user.username;
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    private ArrayList<Notification> getUserNotificationsFromAllUsers(User user){
        for (User iterUser: loginViewModel.allUser){
            if(iterUser.username.equals(user.username)){
                return iterUser.userNotifications;
            }
        }
        return new ArrayList<Notification>();
    }
}
