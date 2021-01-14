package mazpen.rockettar.alarmproject.Adapters;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

import mazpen.rockettar.alarmproject.DetailsFragment;
import mazpen.rockettar.alarmproject.MainActivity;
import mazpen.rockettar.alarmproject.Objects.Reminder;
import mazpen.rockettar.alarmproject.Objects.User;
import mazpen.rockettar.alarmproject.R;
import mazpen.rockettar.alarmproject.ViewModel.AlarmViewModel;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public HashMap<String, ArrayList<Reminder>> allUsers;
    public String currUser;
    public ArrayList<Reminder> allReminders;
    private final Context mContext;
    private OnItemClickListener itemListener;
    public Fragment detailsFragment;
    public AlarmViewModel alarmViewModel = new AlarmViewModel();

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickedListener(OnItemClickListener listener) {
        this.itemListener = listener;
    }


    public RecyclerViewAdapter(Context context, ArrayList<Reminder> allReminders, HashMap<String, ArrayList<Reminder>> allUsers, String currUser,
                               Fragment detailsFragment) {
        this.allReminders = allReminders;
        this.mContext = context;
        this.allUsers = allUsers;
        this.currUser = currUser;
        this.detailsFragment = new DetailsFragment();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item, parent, false);
        return new ViewHolder(view, itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.reminderDate.setText(allReminders.get(position).ReminderDate);
        holder.reminderTime.setText(allReminders.get(position).ReminderTime);
        holder.reminderTitle.setText(allReminders.get(position).title);
        holder.reminderDescription.setText(allReminders.get(position).description);
        final String reminderId = allReminders.get(position).id;

        holder.reminderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                alarmViewModel.selectedReminderId = new Reminder(holder.reminderTitle.getText().toString(),
                        holder.reminderDescription.getText().toString(),
                        holder.reminderDate.getText().toString(), holder.reminderTime.getText().toString()).id;

                 */
                alarmViewModel.selectedReminderId = reminderId;
                Bundle arguments = new Bundle();
                arguments.putString("Prev Reminder Id", alarmViewModel.selectedReminderId);
                ((MainActivity) mContext).detailsFragment.setArguments(arguments);
                FragmentTransaction fragmentManager = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                fragmentManager.replace(R.id.fragmentFiller, detailsFragment);
                fragmentManager.commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        if (allReminders == null) {
            return 0;
        }
        return allReminders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView reminderTitle;
        TextView reminderDescription;
        TextView reminderDate;
        TextView reminderTime;
        //TextView reminderId;
        int reminderId;
        ConstraintLayout reminderItem;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            reminderDate = itemView.findViewById(R.id.ReminderDate);
            reminderDescription = itemView.findViewById(R.id.ReminderDescription);
            reminderTitle = itemView.findViewById(R.id.ReminderTitle);
            reminderTime = itemView.findViewById(R.id.ReminderTime);
            reminderItem = itemView.findViewById(R.id.reminder_item);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }


    }



}
