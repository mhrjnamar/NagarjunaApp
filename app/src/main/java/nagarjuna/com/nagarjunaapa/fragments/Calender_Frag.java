package nagarjuna.com.nagarjunaapa.fragments;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import nagarjuna.com.nagarjuna.com.nagarjunaapa.adapter.EventAdapter;
import nagarjuna.com.nagarjuna.com.nagarjunaapa.adapter.EventNoticeAdapter;
import nagarjuna.com.nagarjunaapp.CalanderUtils;
import nagarjuna.com.nagarjunaapp.CalenderEvents;
import nagarjuna.com.nagarjunaapp.DaysUtils;
import nagarjuna.com.nagarjunaapp.EventsDatabase;
import nagarjuna.com.nagarjunaapp.Items;
import nagarjuna.com.nagarjunaapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Calender_Frag extends Fragment {



    static final String KEY_POSITION = "position";


    public static final int default_no = 3;
    RecyclerView rv;
    RecyclerView event_rv;
    RecyclerViewAdapter adapter;
    EventAdapter eventAdapter;
    EventNoticeAdapter ea;
    LinearLayout event_holder;


//    RecyclerViewAdapterForNoticeAndEvents ea;
    int current;
    int j = 0;
    String[] daysNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    EventsDatabase event_db;


    public Calender_Frag() {
        // Required empty public constructor
    }

    /**
     *
     * @param pos position of the viewPager to set position of the new instance
     * @return
     */
    public Calender_Frag newInstance(int pos) {
        //create new instance of the fragment and setArgument its position using Bundle
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_POSITION, pos);
        Calender_Frag fragment = new Calender_Frag();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize recycler view for calender and set adapter
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        adapter = new RecyclerViewAdapter();
        rv.setAdapter(adapter);


        event_holder = (LinearLayout) view.findViewById(R.id.event_hodor);

        //intialize recycler view for events and set adapter
        event_rv = (RecyclerView) view.findViewById(R.id.event_rv);
        event_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        ea = new RecyclerViewAdapterForNoticeAndEvents(getActivity());
        ea = new EventNoticeAdapter(getActivity());
        eventAdapter = new EventAdapter(getActivity());
        ea.isCalendar(true);
//        event_rv.setAdapter(ea);
        updateEventAdapter();

        //get Position of the current fragment instance and set the current position according to the requirement by adding default_no
        int pos = getArguments().getInt(KEY_POSITION);
        current = pos + default_no;

        // Create instance of CalenderUtils and use it to call its methods to find first day and total days
        CalanderUtils utils = new CalanderUtils();
        int firstDay = utils.getFirstDayOfCurrentMonth(current);
        int totalDays = utils.getMaxDaysInCurrentMonth(current);

        //initilize database and get events from database according to the current position of the month and call setEvents() mehod of event adapter
        // to update events
        event_db = new EventsDatabase(getActivity());
        ArrayList<CalenderEvents> events = event_db.getEventForMonth(current);
        if (events.size() == 0) {
//            event_db.createEvents(new CalenderEvents("title1", "discription", 2016, 4, 1));
//            event_db.createEvents(new CalenderEvents("title1", "discription", 2016, 5, 21));
            events = event_db.getEventForMonth(current);
        }


        ea.setEvents(events);
        eventAdapter.setEvents(events);

        //initialize ArrayList with type DaysUtils to add the days of the month from when they start and add blankSpace before that
        //call adapter function to setDays int the calender recyclerView
        ArrayList<DaysUtils> days = new ArrayList<>();
        for (int i = 1; i < firstDay; i++) {
            days.add(new DaysUtils(-1, ""));
        }
        j = firstDay - 1;
        for (int i = 1; i <= totalDays; i++) {
            if (j >= daysNames.length) {
                j = 0;
            }
            DaysUtils day = new DaysUtils(i, daysNames[j]);
            //check if the ArrayList of events contain events in days that matches with "i" and set events accordingly
            for (CalenderEvents event : events) {
                if (event.getDay() == i) {
                    day.setEvents(event);
                }
            }
            days.add(day);
            j++;
        }
        adapter.setDays(days);


        event_holder.setVisibility(eventAdapter.getItemCount()==0?View.GONE:View.VISIBLE);
    }

    public void updateEventAdapter(){
        event_rv.setAdapter(eventAdapter);
    }

    public void updateView(String v){

        if (v.equals("event_mode")){
            event_holder.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) rv.getLayoutParams();
            p.weight = 0.0F;
            rv.setLayoutParams(p);
            LinearLayout.LayoutParams pa = (LinearLayout.LayoutParams) event_rv.getLayoutParams();
            pa.weight = 1.5F;
            event_rv.setLayoutParams(pa);
        }else if (v.equals("cal_mode")){
            LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) rv.getLayoutParams();
            p.weight = 1.0F;
            rv.setLayoutParams(p);
            event_holder.setVisibility(View.GONE);
        }else {
            event_holder.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) rv.getLayoutParams();
            p.weight = 1.5F;
            rv.setLayoutParams(p);
            LinearLayout.LayoutParams pa = (LinearLayout.LayoutParams) event_rv.getLayoutParams();
            pa.weight = 1.F;
            event_rv.setLayoutParams(pa);
        }
    }
    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> implements View.OnClickListener {

        ArrayList<DaysUtils> days = new ArrayList<>();

        public void setDays(ArrayList<DaysUtils> days) {
            this.days = days;
            notifyDataSetChanged();

        }


        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(getActivity()).inflate(R.layout.day_view, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            //create instance of DaysUtils to get day position and set text color of the day according to the day type ie saturday or day with or without events
            DaysUtils day = days.get(position);
            int dayPos = day.getDay();
            holder.day.setText(dayPos == -1 ? "" : String.valueOf(dayPos));
            holder.day.setBackgroundColor(day.getDayName().equals(daysNames[6]) ? getActivity().getResources().getColor(android.R.color.transparent) : Color.TRANSPARENT);
            holder.day.setTextColor(day.getDayName().equals(daysNames[6]) ? Color.RED : day.getEvents() != null ? Color.BLUE : getResources().getColor(R.color.textColor));

            //setTag DayUtils instance day to itemView to get same instance in onClick method of itemView.
            holder.itemView.setTag(day);
            holder.itemView.setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return days.size();
        }

        @Override
        public void onClick(View v) {
           //get DayUtils instance using getTag() method and get selected date
            final DaysUtils day = (DaysUtils) v.getTag();
            final int date = day.getDay();

            //Create alertDialog to get Events from the user and store to EventDatabase and update Adapters
            final AlertDialog.Builder getEventDialog = new AlertDialog.Builder(getActivity());
            getEventDialog.setTitle("Create Event");
            final View body = LayoutInflater.from(getActivity()).inflate(R.layout.get_event_dialog, null);

            //initialize views
            final EditText eventTitle = (EditText) body.findViewById(R.id.eventTitle);
            final EditText eventDescription = (EditText) body.findViewById(R.id.eventDescription);

            getEventDialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Create new instance of Calender Events
                    CalenderEvents events = new CalenderEvents(eventTitle.getText().toString(),"2016"+current+date,eventDescription.getText().toString(),true, 2016, current, date,"Lalitpur","img");

                    //save the event to database
                    Boolean isInserted = event_db.createEvents(events);

                    //update adapters with new events
                    day.setEvents(events);
                    ea.setEvents(event_db.getEventForMonth(events.getMonth()));
                    eventAdapter.setEvents(event_db.getEventForMonth(events.getMonth()));

                    Toast.makeText(getActivity(), isInserted ? "Data Inserted" : "Failed", Toast.LENGTH_LONG).show();
                    notifyDataSetChanged();
                }
            });
            getEventDialog.setView(body);
            getEventDialog.create().show();
        }

        public class Holder extends RecyclerView.ViewHolder {
            TextView day;

            public Holder(View itemView) {
                super(itemView);
                day = (TextView) itemView.findViewById(R.id.day);
            }
        }
    }


}


