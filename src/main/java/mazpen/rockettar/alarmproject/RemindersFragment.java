package mazpen.rockettar.alarmproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import mazpen.rockettar.alarmproject.Adapters.RecyclerViewAdapter;
import mazpen.rockettar.alarmproject.ViewModel.AlarmViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemindersFragment extends Fragment {
    private Activity hostActivity;
    private FragmentTransaction fragmentManager;
    private Fragment detailsFragment;
    private Toolbar toolbar;
    public AlarmViewModel alarmViewModel = new AlarmViewModel();

    public RemindersFragment() {
        super(R.layout.fragment_reminders);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getActivity() != null) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
            toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Reminders Fragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminders, container, false);
        initVars();
        final RecyclerView recyclerView = initRecyclerView(view);
        FloatingActionButton addReminderButton = view.findViewById(R.id.addReminderButton);
        addReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.replace(R.id.fragmentFiller, detailsFragment, "details fragment");
                fragmentManager.commit();
            }
        });
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = recyclerView.getChildLayoutPosition(v);
                alarmViewModel.selectedReminderId = alarmViewModel.currUser.userReminders.get(itemPosition).id;
                fragmentManager.replace(R.id.fragmentFiller, detailsFragment, "details fragment");
                fragmentManager.commit();

            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.navigation_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profileBar) {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.logoutBar) {
            Intent intent1 = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initVars() {
        toolbar.setTitle("hello " + alarmViewModel.currUser.username + "!");
        if (getActivity() != null) {
            hostActivity = getActivity();
            fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();
            //detailsFragment = ((MainActivity) (getActivity())).detailsFragment;
            detailsFragment = new DetailsFragment();
        }

    }

    private RecyclerView initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.all_reminders);
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(hostActivity, alarmViewModel.currUser.userReminders,
                alarmViewModel.allUsers, alarmViewModel.currUser.username, ((MainActivity) hostActivity).detailsFragment);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(hostActivity));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapter.allReminders.remove(alarmViewModel.currUser.userReminders
                        .get(viewHolder.getAdapterPosition()));
                adapter.notifyItemChanged(position);
                adapter.notifyItemRangeRemoved(position, 1);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.setOnItemClickedListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                alarmViewModel.selectedReminderId = alarmViewModel.currUser.userReminders.get(position).id;
            }
        });
        return recyclerView;

    }
}
