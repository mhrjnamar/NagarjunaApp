package nagarjuna.com.nagarjunaapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {
    private static final String TAG = "Notifications";

    RecyclerView rv;
    SessionManager manager;
    Toolbar tb;
    NotificationAdapter adapter;
    EventsDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        tb = (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        manager = new SessionManager(Notifications.this);
        String msg = manager.getNotificationMsg();
        Log.i(TAG, "onCreate:msg "+msg);

        database = new EventsDatabase(Notifications.this);


        rv = (RecyclerView) findViewById(R.id.rv);
        updateList();
    }


    public void updateList(){
        rv.setLayoutManager(new LinearLayoutManager(Notifications.this));
        adapter = new NotificationAdapter(Notifications.this);
        Log.i(TAG, "onCreate: NoOFMsg :" +database.getNotifyMsg().size());
        adapter.updateMsgs(database.getNotifyMsg());
        rv.setAdapter(adapter);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Holder>{

        ArrayList<String> msgs;
        Context context;
        LayoutInflater inflater;

        public NotificationAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        public void updateMsgs(ArrayList<String> msgs){
            this.msgs = msgs;
            notifyDataSetChanged();
        }

        @Override
        public NotificationAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(inflater.inflate(R.layout.notification_msgs,parent,false));
        }

        @Override
        public void onBindViewHolder(NotificationAdapter.Holder holder, int position) {
            holder.msg.setText(msgs.get(position));

        }

        @Override
        public int getItemCount() {
            return msgs.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            TextView msg;
            public Holder(View itemView) {
                super(itemView);
                msg = (TextView) itemView.findViewById(R.id.notify_msg);
            }
        }
    }
}
