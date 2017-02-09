package nagarjuna.com.nagarjunaapa.fragments;


import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import nagarjuna.com.functions.WebServices;
import nagarjuna.com.nagarjunaapp.CalenderEvents;
import nagarjuna.com.nagarjunaapp.Events;
import nagarjuna.com.nagarjunaapp.EventsDatabase;
import nagarjuna.com.nagarjunaapp.Items;
import nagarjuna.com.nagarjunaapp.MainActivity;
import nagarjuna.com.nagarjunaapp.Notices;
import nagarjuna.com.nagarjunaapp.R;
import nagarjuna.com.nagarjunaapp.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    ViewPager pager;
    HomeItemFragment noticeFrag, eventFrag;
    ArrayList<Items> item_list = new ArrayList<>();
    EventsDatabase database;
    TabLayout tab;
    Toolbar tb;
    int i = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = new EventsDatabase(getActivity());
        pager = (ViewPager) view.findViewById(R.id.eventNoticePager);
        PagerAdapter adapter = new PageAdapter(getChildFragmentManager());
        item_list = new ArrayList<>();
        pager.setAdapter(adapter);
        View eventView = getTabView();
        final View noticeView = getTabView();


        // just For test to Increase no of events in the list
        eventView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eventFrag == null) {
                    eventFrag = (HomeItemFragment) getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + "0");
                }
                i++;
                CalenderEvents e = new CalenderEvents("New Event" + String.valueOf(i), "2016/7/"+ String.valueOf(i), "Events", true, 2016, 7, 22, "nepal","img");
                eventFrag.addData(e);
            }
        });

        tab = (TabLayout) view.findViewById(R.id.tab);
        tab.setupWithViewPager(pager, true);

        final int selected = getActivity().getResources().getColor(android.R.color.white);
        final int unselected = getActivity().getResources().getColor(android.R.color.darker_gray);
        tab.setSelectedTabIndicatorColor(selected);

        if (tab.getTabCount() != 0) {
            tab.getTabAt(1).setCustomView(noticeView);
            tab.getTabAt(0).setCustomView(eventView);
        }

        final TextView events = (TextView) eventView.findViewById(R.id.tab_name);
        final TextView notices = (TextView) noticeView.findViewById(R.id.tab_name);
        events.setTextColor(selected);
        events.setText(R.string.events);
        notices.setText(R.string.notice_board);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                events.setText(R.string.events);
                notices.setText(R.string.notice_board);

                if (position == 0) {
                    updateEvents();
                } else if (position == 1) {
                    updateNotice();
                }

                events.setTextColor(position == 0 ? selected : unselected);
                notices.setTextColor(position == 1 ? selected : unselected);

                int notice_bg = getResources().getColor(R.color.colorPrimary);
                int event_bg = getResources().getColor(R.color.tab_bg);

                Integer[] first = {notice_bg, event_bg};
                Integer[] second = {event_bg, notice_bg};

                tb = ((MainActivity) getActivity()).getCustomActionBar();
                ObjectAnimator anim = ObjectAnimator.ofObject(tab, "backgroundColor", new ArgbEvaluator(), position == 0 ? second : first);
                ObjectAnimator anim1 = ObjectAnimator.ofObject(tb, "backgroundColor", new ArgbEvaluator(), position == 0 ? second : first);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(anim, anim1);
                set.setDuration(200);
                set.start();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Initialize noticeFrag and eventFrag
        pager.post(new Runnable() {
            @Override
            public void run() {
                noticeFrag = (HomeItemFragment) getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.eventNoticePager + ":" + "1");
                eventFrag = (HomeItemFragment) getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.eventNoticePager + ":" + "0");


            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

//        new GetInformationAsync().execute();
    }

    private View getTabView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.tab_layout, null);
    }

    //update Events and Notices
    public void updateEvents() {
        if (eventFrag == null) {
            eventFrag = (HomeItemFragment) getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + "0");
        }
        item_list.clear();
        item_list.add(new CalenderEvents("EVents", "2016/7/21", "Events", true, 2016, 7, 22, "nepal","img"));
        item_list.add(new CalenderEvents("eventOne", "2016/7/21", "Events", true, 2016, 7, 22, "nepal","img"));
        eventFrag.setData(item_list);
    }

    public void updateNotice() {
        if (noticeFrag == null) {
            noticeFrag = (HomeItemFragment) getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.eventNoticePager + ":" + "1");
        }
        item_list.clear();
        item_list.add(new Notices("Notice", "2016/7/21", "Events", true,"img"));
        item_list.add(new Notices("Notice1", "2016/7/21", "Events", true,"img"));
        noticeFrag.setData(item_list);
    }

//    public void tabShow() {
//        SessionManager manager = new SessionManager(getActivity());
//        if (!manager.isLoggedIn()) {
//            if (tab.getTabCount() > 1) {
//                tab.removeTabAt(1);
//            }
//        } else {
//            tab.setupWithViewPager(pager);
//        }
//    }

    @Override
    public void onPause() {
        super.onPause();
//        tabShow();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tb = ((MainActivity) getActivity()).getCustomActionBar();
        tb.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    public class PageAdapter extends FragmentPagerAdapter {
        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new HomeItemFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

//    public class GetInformationAsync extends AsyncTask<Void, Void, SoapObject> {
//        private ArrayList<Events> events;
//        private ArrayList<Notices> notices;
//        private String error = "";
//
//        @Override
//        protected SoapObject doInBackground(Void... voids) {
//            SoapObject request = new SoapObject(WebServices.nameSpace, WebServices.method_GetInformation);
//            request.addProperty("USERNAME", "amar");
//            request.addProperty("PASSWORD", "amar123");
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            envelope.setOutputSoapObject(request);
//
//            HttpTransportSE transportSE = new HttpTransportSE(WebServices.url);
//            try {
//                transportSE.call(WebServices.getSoapAction(WebServices.method_GetInformation), envelope);
//                return (SoapObject) envelope.getResponse();
//            } catch (Exception e) {
//                e.printStackTrace();
//                error = e.toString();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(SoapObject soapObject) {
//            super.onPostExecute(soapObject);
//            events = new ArrayList<>();
//            notices = new ArrayList<>();
//            Log.i(TAG, "onPostExecute: respo " + soapObject);
//            if (soapObject != null) {
//                if (soapObject.getPropertyAsString("CODE").equals("0")) {
//                    for (int i = 0; i < soapObject.getPropertyCount(); i++) {
//                        SoapObject response = (SoapObject) soapObject.getProperty(i);
//                        String msg = response.getPropertyAsString("MSG");
//                        String title = response.getPropertyAsString("TITLE");
//                        String dated = response.getPropertyAsString("DATED");
//                        String img = response.getPropertyAsString("IMG");
//                        String des = response.getPropertyAsString("DESCRIPTION");
//                        String loc = response.getPropertyAsString("LOCATION");
//                        String type = response.getPropertyAsString("TYPE");
//
//                        if (type.equals("e")) {
//                            events.add(new Events(title, dated, des, true, loc, img));
//                        } else {
//                            notices.add(new Notices(title, dated, des, true, img));
//                        }
//                    }
//                }
//
//            }
//        }
//    }
}
