package nagarjuna.com.nagarjunaapa.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import nagarjuna.com.nagarjuna.com.nagarjunaapa.adapter.EventNoticeAdapter;
import nagarjuna.com.nagarjunaapp.CalenderEvents;
import nagarjuna.com.nagarjunaapp.EventsDatabase;
import nagarjuna.com.nagarjunaapp.NoticeUtils;
import nagarjuna.com.nagarjunaapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeAndEventFrag extends Fragment {

    public NoticeAndEventFrag() {
        // Required empty public constructor
    }

    public NoticeAndEventFrag newInstance(int pos) {
        NoticeAndEventFrag frag = new NoticeAndEventFrag();
        Bundle b = new Bundle();
        b.putInt("KeyPos", pos);
        frag.setArguments(b);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notice_and_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView eventNoticeList = (RecyclerView) view.findViewById(R.id.event_notice_list);
        eventNoticeList.setLayoutManager(new LinearLayoutManager(getActivity()));

        EventNoticeAdapter adapter = new EventNoticeAdapter(getActivity());
        eventNoticeList.setAdapter(adapter);

        EventsDatabase database = new EventsDatabase(getActivity());


        int pos = getArguments().getInt("KeyPos");
        if (pos == 1) {

            ArrayList<CalenderEvents> events = new ArrayList<>();

            events = database.getAllEvents();

            if (events.size() == 0) {
                database.createEvents(new CalenderEvents("DummyEvent","2016/4/29", "DummpyDay",false, 2016, 6, 12,"sdfads",""));
                database.createEvents(new CalenderEvents("DummyEvent1","2016/4/29", "DummyDAy2",false, 2016, 6, 13,"sfadsf",""));
                database.getAllEvents();
            }

            adapter.setEvents(events);


        } else {

            ArrayList<NoticeUtils> notices = new ArrayList<>();
            notices = database.getNotices();
            if (notices.size() == 0) {
                database.createNotice(new NoticeUtils("DummyNotice", "DummyDiscription", "image", "2016/7/12"));
                database.createNotice(new NoticeUtils("DummyNotice", "DummyDiscription", "image", "2016/7/13"));
                database.getNotices();
            }

            adapter.setNoticeItems(notices);
        }


    }
}
