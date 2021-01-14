package mazpen.rockettar.alarmproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;

import mazpen.rockettar.alarmproject.Objects.Reminder;
import mazpen.rockettar.alarmproject.Objects.User;
import mazpen.rockettar.alarmproject.ViewModel.AlarmViewModel;

public class ProfileActivity extends AppCompatActivity {
    private EditText etChangeName;
    private final AlarmViewModel alarmViewModel = new AlarmViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Profile");
        etChangeName = findViewById(R.id.nameEditor);
        etChangeName.setText(alarmViewModel.currUser.username);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.saveBar) {
            String inputName = etChangeName.getText().toString();
            User currUser = alarmViewModel.currUser;
            HashMap<String, ArrayList<Reminder>> userList = alarmViewModel.allUsers;
            if (!inputName.isEmpty() && !currUser.username.isEmpty()) {
                alarmViewModel.updateUsername(userList, currUser, inputName);
            }
            Intent intent1 = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent1);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
