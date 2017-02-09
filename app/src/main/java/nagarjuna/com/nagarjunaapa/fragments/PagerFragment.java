package nagarjuna.com.nagarjunaapa.fragments;


import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import nagarjuna.com.nagarjuna.com.nagarjunaapa.adapter.PagerListAdapter;
import nagarjuna.com.nagarjunaapp.CalenderEvents;
import nagarjuna.com.nagarjunaapp.Events;
import nagarjuna.com.nagarjunaapp.EventsDatabase;
import nagarjuna.com.nagarjunaapp.Items;
import nagarjuna.com.nagarjunaapp.MainActivity;
import nagarjuna.com.nagarjunaapp.NoticeUtils;
import nagarjuna.com.nagarjunaapp.Notices;
import nagarjuna.com.nagarjunaapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PagerFragment extends Fragment {
    RecyclerView rv;
    PagerListAdapter adapter;
    EventsDatabase database;

    public PagerFragment() {
        // Required empty public constructor
    }

    public PagerFragment newInstance(int pos) {
        PagerFragment frag = new PagerFragment();
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = (RecyclerView) view.findViewById(R.id.rv_event_notice);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PagerListAdapter(getActivity());
        rv.setAdapter(adapter);
        database = new EventsDatabase(getActivity());

        Bundle b = getArguments();
        int pos = b.getInt("pos");

        if (pos == 0) {
            ArrayList<CalenderEvents> events = new ArrayList<>();
            events = database.getAllEvents();
            if (events == null) {
                database.createEvents(new CalenderEvents("DummyEvent", "2016/4/29", "DummyDes", false, 2016, 7, 12, "Lalitpur",""));
                database.createEvents(new CalenderEvents("DummyEvent1", "2016/4/29", "DummyDes2", false, 2016, 7, 13, "Lalitpur",""));
                events = database.getAllEvents();
            }
        } else {
            ArrayList<NoticeUtils> notices = new ArrayList<>();
            notices = database.getNotices();
            if (notices == null) {
                database.createNotice(new NoticeUtils("Demo Title2", getString(R.string.message_content_less), "No Image", "kkj"));
                database.createNotice(new NoticeUtils("Demo Titledfss", getString(R.string.message_content_less), "No Image", "kkj"));
                notices = database.getNotices();

            }

        }
    }

}



