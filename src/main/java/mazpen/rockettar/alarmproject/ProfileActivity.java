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

import mazpen.rockettar.alarmproject.Objects.User;
import mazpen.rockettar.alarmproject.Model.LoginViewModel;

public class ProfileActivity extends AppCompatActivity {
    private EditText etChangeName;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if(item.getItemId() == android.R.id.home) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
            if(item.getItemId() == R.id.saveBar) {
                String inputName = etChangeName.getText().toString();
                User currUser = LoginViewModel.getInstance().currUser;
                ArrayList<User> userList = LoginViewModel.getInstance().allUser;
                if (!inputName.isEmpty()  && !currUser.username.isEmpty()) {
                    LoginViewModel loginViewModel = LoginViewModel.getInstance();
                    loginViewModel.updateUsername(userList, currUser, inputName);
                }
                Intent intent1 = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent1);
            }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Profile");
        etChangeName = findViewById(R.id.nameEditor);
        etChangeName.setText(LoginViewModel.getInstance().currUser.username);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
