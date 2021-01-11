package mazpen.rockettar.alarmproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    public FragmentTransaction fragmentManager;
    public Fragment remindersFragment;
    public Fragment detailsFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            fragmentManager = getSupportFragmentManager().beginTransaction();
            fragmentManager.replace(R.id.fragmentFiller, detailsFragment);
            fragmentManager.commit();
        }

        setContentView(R.layout.activity_main);
        initToolbar();

        initFragments();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        Fragment currFragment = getSupportFragmentManager().findFragmentByTag("details fragment");
        if(currFragment!= null && currFragment.isVisible()){
            if (LoginViewModel.getInstance().prevNotification != null) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.edit_reminder_menu, menu);
        }   else {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.add_reminder_menu, menu);
        }
        }
        else{
            currFragment = getSupportFragmentManager().findFragmentByTag("reminders fragment");
            if(currFragment!= null && currFragment.isVisible()){
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.navigation_menu, menu);
            }
        }
        return true;
    }
*/
    @Override
    public void onBackPressed() {
        Fragment fragment =  getSupportFragmentManager().getFragments().get(0);
        if(fragment != null && fragment.isVisible() && fragment.getClass().equals(DetailsFragment.class))
        {
            FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
            fragmentManager.replace(R.id.fragmentFiller, remindersFragment, "reminders fragment");
            fragmentManager.commit();
        }
        else{
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

    public void initFragments()
    {
        remindersFragment = new RemindersFragment();
        detailsFragment = new DetailsFragment();
        fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.fragmentFiller, remindersFragment, "reminders fragment");
        fragmentManager.commit();
    }

    public void initToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
