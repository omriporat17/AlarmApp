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
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;
import java.util.ArrayList;
import mazpen.rockettar.alarmproject.MainActivity;
import mazpen.rockettar.alarmproject.Objects.Notification;
import mazpen.rockettar.alarmproject.R;
import mazpen.rockettar.alarmproject.Model.LoginViewModel;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    public Serializable allUsers;
    public String currUser;
    public ArrayList<Notification> allNotifications;
    private final Context mContext;
    private final LoginViewModel loginViewModel = LoginViewModel.getInstance();
    private OnItemClickListener itemListener;
    private View.OnClickListener onClickListener;
    public Fragment detailsFragment;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickedListener(OnItemClickListener listener){
        this.itemListener  = listener;
    }
    public void setOnClickListener(View.OnClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
    }
    public RecyclerViewAdapter(Context context, ArrayList<Notification> allNotifications, Serializable allUsers, String currUser,
                               Fragment detailsFragment)
    {
        this.allNotifications = allNotifications;
        this.mContext = context;
        this.allUsers = allUsers;
        this.currUser = currUser;
        this.detailsFragment = detailsFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,parent, false);
        view.setOnClickListener(onClickListener);
        ViewHolder holder = new ViewHolder(view, itemListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.notificationDate.setText(allNotifications.get(position).notificationDate);
        holder.notificationTime.setText(allNotifications.get(position).notificationTime);
        holder.notificationTitle.setText(allNotifications.get(position).title);
        holder.notificationDescription.setText(allNotifications.get(position).description);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notification notification = new Notification(holder.notificationTitle.getText().toString(),
                        holder.notificationDescription.getText().toString(),
                        holder.notificationDate.getText().toString(),holder.notificationTime.getText().toString());
                loginViewModel.prevNotification = notification;
                Bundle arguments = new Bundle();
                LoginViewModel loginViewModel = LoginViewModel.getInstance();
                arguments.putSerializable("Prev Notification", notification);
                ((MainActivity)mContext).detailsFragment.setArguments(arguments);
                FragmentTransaction fragmentManager = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                fragmentManager.replace(R.id.fragmentFiller, detailsFragment);
                fragmentManager.commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        if(allNotifications == null) { return 0;}
        return allNotifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView notificationTitle;
        TextView notificationDescription;
        TextView notificationDate;
        TextView notificationTime;
        ConstraintLayout parentLayout;
        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            notificationDate = itemView.findViewById(R.id.notificationDate);
            notificationDescription = itemView.findViewById(R.id.notificationDescription);
            notificationTitle = itemView.findViewById(R.id.notificationTitle);
            notificationTime = itemView.findViewById(R.id.notificationTime);
            parentLayout = itemView.findViewById(R.id.parentLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

}
