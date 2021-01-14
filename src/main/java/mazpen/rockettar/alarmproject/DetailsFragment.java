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

import mazpen.rockettar.alarmproject.Objects.Reminder;
import mazpen.rockettar.alarmproject.ViewModel.AlarmViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private String inputDate = Calendar.getInstance().getTime().toString().substring(0, 10);
    private String inputTime = "";
    private String inputReminderName = "";
    private String inputDescription;
    public Reminder selectedReminder;
    private EditText reminderName;
    private EditText description;
    private TimePicker timePicker;
    private Button dateButton;
    private TextView dateText;
    private Toolbar toolbar;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private RemindersFragment remindersFragment;
    private FragmentTransaction fragmentManager;
    public AlarmViewModel alarmViewModel = new AlarmViewModel();


    public DetailsFragment() {
        super(R.layout.fragment_details);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getActivity() != null && (((MainActivity) (getActivity())).getSupportActionBar()) != null) {
            ((MainActivity) (getActivity())).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) (getActivity())).getSupportActionBar().setDisplayShowHomeEnabled(true);
            fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        initVars(view);
        if (getActivity() != null && ((MainActivity) getActivity()).detailsFragment.getArguments() != null) {
            loadselectedReminder();
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
        if (!alarmViewModel.selectedReminderId.equals("")) {
            inflater.inflate(R.menu.edit_reminder_menu, menu);
        } else {
            inflater.inflate(R.menu.add_reminder_menu, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addBar) {
            addReminder();
        } else if (item.getItemId() == R.id.saveBar) {
            updateReminder();
        } else if (item.getItemId() == android.R.id.home) {
            //Intent intent = new Intent(getActivity(), MainActivity.class);
            //startActivity(intent);
            if (((MainActivity) getActivity()) != null) {
                fragmentManager.replace(R.id.fragmentFiller, remindersFragment);
                fragmentManager.commit();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
        dateText.setText(date);
    }

    private void initVars(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        reminderName = view.findViewById(R.id.reminderName);
        description = view.findViewById(R.id.description);
        timePicker = view.findViewById(R.id.timePicker);
        dateButton = view.findViewById(R.id.dateButton);
        dateText = view.findViewById(R.id.dateText);
        dateText.setText(Calendar.getInstance().getTime().toString().substring(0, 10));
        //reminderName.setText("");
        //description.setText("");
        remindersFragment = new RemindersFragment();
        if (getActivity() != null) {
            toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Details Fragment");
        }
    }

    private void ReminderSetUp() {
        inputReminderName = reminderName.getText().toString();
        inputDescription = description.getText().toString();
        inputDate = dateText.getText().toString();
        inputTime = getTimeFromTimePicker(timePicker);
    }

    private String getTimeFromTimePicker(TimePicker timePicker) {
        String hour = timePicker.getCurrentHour().toString();
        Integer minutesInt = timePicker.getCurrentMinute();
        String minutes = minutesInt.toString();
        if (minutesInt < 10 && minutesInt >= 0) {
            minutes = "0" + minutesInt;
        }
        return hour + ":" + minutes;
    }

    private void showDatePickerDialog() {
        if (getActivity() != null) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        }
    }

    private void loadselectedReminder() {

        if (getActivity() != null) {
            String selectedReminderId = ((MainActivity) getActivity()).detailsFragment
                    .getArguments().getString("Prev Reminder Id");
            alarmViewModel.selectedReminderId = selectedReminderId;
            selectedReminder = alarmViewModel.getReminderById(selectedReminderId);
        }

        if (selectedReminder != null) {
            description.setText(selectedReminder.description);
            dateText.setText(selectedReminder.ReminderDate);
            reminderName.setText(selectedReminder.title);
        }
        ((MainActivity) getActivity()).detailsFragment.setArguments(null);
    }

    private void tryChangeDate() {
        try {
            if (simpleDateFormat.parse(inputDate) != null) {
                Date changedDate = simpleDateFormat.parse(inputDate);
                inputDate = changedDate.toString().substring(0, 10);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void addReminder() {
        ReminderSetUp();
        if (inputDate.trim().isEmpty()) {
            Toast.makeText(getActivity(), "There is no chosen date", Toast.LENGTH_SHORT).show();
            inputDate = Calendar.getInstance().getTime().toString().substring(0, 10);
        }
        tryChangeDate();
        alarmViewModel.addReminder(inputReminderName, inputDescription, inputDate, inputTime);
        if (((MainActivity) getActivity()) != null) {
            fragmentManager.replace(R.id.fragmentFiller, remindersFragment);
            fragmentManager.commit();
        }
    }

    private void updateReminder() {
        ReminderSetUp();
        tryChangeDate();
        alarmViewModel.updateReminder(selectedReminder, inputReminderName, inputDescription, inputDate, inputTime);
        if (((MainActivity) getActivity()) != null) {
            fragmentManager.replace(R.id.fragmentFiller, remindersFragment);
            fragmentManager.commit();
        }
    }

}
