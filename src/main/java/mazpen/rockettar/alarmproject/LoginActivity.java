package mazpen.rockettar.alarmproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import mazpen.rockettar.alarmproject.Objects.Reminder;
import mazpen.rockettar.alarmproject.Objects.User;
import mazpen.rockettar.alarmproject.ViewModel.AlarmViewModel;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private FloatingActionButton etLogin;
    private final AlarmViewModel alarmViewModel = new AlarmViewModel();
    public HashMap<String, ArrayList<Reminder>> allUsers = new HashMap<>();
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

    @Override
    public void onBackPressed() {
        finish();
    }

    private boolean validate(String name) {
        if (!allUsers.containsKey(name)) {
            allUsers.put(name, new ArrayList<Reminder>());
        }
        return true;
    }

    private void putInModel() {
        User user = new User(inputName, "");
        HashMap<String, ArrayList<Reminder>> allUsers = alarmViewModel.allUsers;
        if (!alarmViewModel.isUserExists(allUsers, user)) {
            alarmViewModel.allUsers.put(user.username, user.userReminders);
            alarmViewModel.currUser.userReminders = new ArrayList<>();
        } else {
            alarmViewModel.currUser.userReminders = allUsers.get(user.username);
        }
        alarmViewModel.currUser.username = user.username;
    }
}
