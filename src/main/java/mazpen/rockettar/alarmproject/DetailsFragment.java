package mazpen.rockettar.alarmproject;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import mazpen.rockettar.alarmproject.Objects.Notification;
import mazpen.rockettar.alarmproject.Model.LoginViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    public DetailsFragment() {
        super(R.layout.fragment_details);
    }
    private final LoginViewModel loginViewModel = LoginViewModel.getInstance();
    private String inputDate = Calendar.getInstance().getTime().toString().substring(0, 10);
    private String inputTime = "";
    private String inputReminderName = "";
    private String inputDescription;
    private FragmentTransaction fragmentManager;
    public Notification prevNotification;
    private EditText reminderName;
    private EditText description;
    private TimePicker timePicker;
    private Button dateButton;
    private TextView dateText;
    Toolbar toolbar;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_details, container, false);
        initVars(view);
        if (((MainActivity) getActivity()).detailsFragment.getArguments() != null) {
            prevNotification = (Notification) ((MainActivity) getActivity()).detailsFragment
                    .getArguments().getSerializable("Prev Notification");
            description.setText(prevNotification.description);
            dateText.setText(prevNotification.notificationDate);
            reminderName.setText(prevNotification.title);
            ((MainActivity) getActivity()).detailsFragment.setArguments(null);
        }
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (prevNotification != null) {
            inflater.inflate(R.menu.edit_reminder_menu, menu);
        } else {
            inflater.inflate(R.menu.add_reminder_menu, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity) (getActivity())).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) (getActivity())).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.addBar){
            Intent intent1 = new Intent(getActivity(), MainActivity.class);
            NotificationSetUp();
            if (inputDate.equals("")) {
                Toast.makeText(getActivity(), "There is no chosen date", Toast.LENGTH_SHORT).show();
                inputDate = Calendar.getInstance().getTime().toString().substring(0, 10);
            }
            try {
                Date changedDate = simpleDateFormat.parse(inputDate);
                inputDate = changedDate.toString().substring(0, 10);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            loginViewModel.addNotificationToUser(inputReminderName, inputDescription, inputDate, inputTime);  // add the new notification
            startActivity(intent1);
        }
        else if( item.getItemId() == R.id.saveBar) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            NotificationSetUp();
            loginViewModel.addNotificationToUser(inputReminderName, inputDescription, inputDate, inputTime);  // add the new notification
            loginViewModel.removeNotificationFromUser(prevNotification);   // delete the prev notification
            startActivity(intent);
        }
        else if(item.getItemId() == android.R.id.home)
        {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    public String getTimeFromTimePicker(TimePicker timePicker) {
        String hour = timePicker.getCurrentHour().toString();
        int minutesInt = timePicker.getCurrentMinute();
        String minutes = "" + minutesInt;
        if (hour.equals("0")) {
            hour = "12";
        }
        if (minutesInt < 10 && minutesInt >= 0) {
            minutes = "0" + minutesInt;
        }
        return hour + ":" + minutes;
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()- 1000);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //view.setMinDate(System.currentTimeMillis() - 1000);
        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
        dateText.setText(date);
    }

    public void initVars(View view)
    {
        toolbar = view.findViewById(R.id.toolbar);
        reminderName = view.findViewById(R.id.reminderName);
        description = view.findViewById(R.id.description);
        timePicker = view.findViewById(R.id.timePicker);
        dateButton = view.findViewById(R.id.dateButton);
        dateText = view.findViewById(R.id.dateText);
        dateText.setText(Calendar.getInstance().getTime().toString().substring(0, 10));
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Details Fragment");


    }

    public void NotificationSetUp(){
        inputReminderName = reminderName.getText().toString();
        inputDescription = description.getText().toString();
        inputDate = dateText.getText().toString();
        inputTime = getTimeFromTimePicker(timePicker);
    }

}
