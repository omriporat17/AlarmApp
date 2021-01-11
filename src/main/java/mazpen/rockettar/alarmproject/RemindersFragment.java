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
import mazpen.rockettar.alarmproject.Model.LoginViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemindersFragment extends Fragment {
    private Activity hostActivity;
    private FragmentTransaction fragmentManager;
    private Fragment detailsFragment;

    public RemindersFragment() {
        super(R.layout.fragment_reminders);
    }

    private Toolbar toolbar;
    private final LoginViewModel loginViewModel = LoginViewModel.getInstance();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Reminders Fragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminders, container, false);
        this.hostActivity = getActivity();
        this.fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();
        this.detailsFragment = ((MainActivity)(getActivity())).detailsFragment;
        final RecyclerView recyclerView = initRecyclerView(view);
        FloatingActionButton addNotificationButton = view.findViewById(R.id.addNotificationButton);
        toolbar.setTitle("hello " + loginViewModel.currUser.username + "!");

        addNotificationButton.setOnClickListener(new View.OnClickListener() {
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
                loginViewModel.prevNotification = loginViewModel.getCurrUser().userNotifications.get(itemPosition);
                fragmentManager.replace(R.id.fragmentFiller, detailsFragment, "details fragment");
                fragmentManager.commit();

            }
        });
        return view;
    }


    private RecyclerView initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.allNotifications);
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(hostActivity, loginViewModel.getCurrUser().userNotifications,
                loginViewModel.getAllUsers(), loginViewModel.getCurrUser().username, ((MainActivity) getActivity()).detailsFragment);
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
                adapter.allNotifications.remove(loginViewModel.currUser.userNotifications
                        .get(viewHolder.getAdapterPosition()));
                adapter.notifyItemChanged(position);
                adapter.notifyItemRangeRemoved(position, 1);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.setOnItemClickedListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                loginViewModel.prevNotification = loginViewModel.getCurrUser().userNotifications.get(position);
            }
        });
        return recyclerView;

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
}
