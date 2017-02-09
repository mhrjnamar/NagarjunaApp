//package nagarjuna.com.nagarjunaapa.fragments;
//
//
//import android.animation.AnimatorSet;
//import android.animation.ArgbEvaluator;
//import android.animation.ObjectAnimator;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//import nagarjuna.com.nagarjuna.com.nagarjunaapa.adapter.EventNoticeAdapter;
//import nagarjuna.com.nagarjunaapp.CalenderEvents;
//import nagarjuna.com.nagarjunaapp.EventsDatabase;
//import nagarjuna.com.nagarjunaapp.MainActivity;
//import nagarjuna.com.nagarjunaapp.NoticeUtils;
//import nagarjuna.com.nagarjunaapp.R;
//import nagarjuna.com.nagarjunaapp.SessionManager;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class HomeFrag extends Fragment {
//    private static final String TAG = "HomeFrag";
//    ViewPager vp;
//    TabLayout tab;
//    SessionManager manager;
//    TextView notice;
//    TextView event;
//
//    public HomeFrag() {
//        // Required empty public constructor
//    }
//    CustomPagerAdapter adapter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home2, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        manager = new SessionManager(getActivity());
//
//        vp = (ViewPager) view.findViewById(R.id.notice_events);
//
//        adapter = new CustomPagerAdapter(getChildFragmentManager());
//        vp.setAdapter(adapter);
//
//        EventsDatabase database = new EventsDatabase(getActivity());
//        ArrayList<CalenderEvents> events = new ArrayList<>();
//        ArrayList<NoticeUtils> notices = new ArrayList<>();
//        events = database.getAllEvents();
//        notices = database.getNotices();
//        tab = (TabLayout) view.findViewById(R.id.tab);
//        tab.setupWithViewPager(vp,true);
//
//
//
//
//        final int selected = getActivity().getResources().getColor(android.R.color.white);
//        final int unselected = getActivity().getResources().getColor(android.R.color.darker_gray);
//        tab.setSelectedTabIndicatorColor(selected);
//        if (events.size() == 0) {
//            database.createEvents(new CalenderEvents("DummyEvent","2016/4/29","DummyDes",false, 2016, 7, 12,"Lalitpur"));
//            database.createEvents(new CalenderEvents("DummyEvent1","2016/4/29","DummyDes2",false, 2016, 7, 13,"Lalitpur"));
//            database.getAllEvents();
//
//        }
//
//        if (notices.size() == 0) {
//            database.createNotice(new NoticeUtils("DummyNotice", "DummyDes", "noImage", "2016/7/14"));
//            database.createNotice(new NoticeUtils("DummyNotice2", "DummyDes", "noImage", "2016/7/14"));
//            database.getNotices();
//        }
//
//        EventNoticeAdapter a = new EventNoticeAdapter(getActivity());
//        a.setNoticeItems(notices);
//        a.setEvents(events);
//
//        updatView();
//
//        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                event.setTextColor(position == 1 ? selected : unselected);
//                notice.setTextColor(position == 0 ? selected : unselected);
//                Log.i(TAG, "onPageSelected: ");
//                int r = getActivity().getResources().getColor(R.color.colorPrimary);
//                int r1 = getActivity().getResources().getColor(R.color.tab_bg);
//                Toolbar action = ((MainActivity) getActivity()).getCustomActionBar();
//                Integer[] first = {r1, r};
//                Integer[] second = {r, r1};
//
//                ObjectAnimator anim = ObjectAnimator.ofObject(HomeFrag.this.tab, "backgroundColor", new ArgbEvaluator(), position == 0 ? first : second);
//                ObjectAnimator anim1 = ObjectAnimator.ofObject(action, "backgroundColor", new ArgbEvaluator(), position == 0 ? first : second);
//                AnimatorSet set = new AnimatorSet();
//
//                set.playTogether(anim, anim1);
//                set.setDuration(200);
//                set.start();
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//
//            }
//        });
//
//    }
//
//    private View getTabView(){
//        return LayoutInflater.from(getActivity()).inflate(R.layout.tab_layout, null);
//
//    }
//
//    private void updatView(){
//        final View v = LayoutInflater.from(getActivity()).inflate(R.layout.tab_layout, null);
//        final View v1 = LayoutInflater.from(getActivity()).inflate(R.layout.notice_layout, null);
//
//
//        notice = (TextView) v1.findViewById(R.id.n_tab);
//
//        event = (TextView) v.findViewById(R.id.event_notice_list);
//
//        if(vp.getCurrentItem() != 0 ){
//            vp.setCurrentItem(0,false);
//        }
//
//        //tab.setTabTextColors(getActivity().getResources().getColor(android.R.color.white),getActivity().getResources().getColor(android.R.color.darker_gray));
//        tab.getTabAt(0).setCustomView(v1);
//        if (manager.isLoggedIn()) {
//            tab.getTabAt(1).setCustomView(v);
//        }else{
//
//        }
//
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ((MainActivity) getActivity()).getCustomActionBar().setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
//    }
//
//    public void notifyDataSetChanged(){
//        adapter.notifyDataSetChanged();
//        updatView();
//    }
//
//    class CustomPagerAdapter extends FragmentPagerAdapter {
//
//        public CustomPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return new NoticeAndEventFrag().newInstance(position);
//        }
//
//        @Override
//        public int getCount() {
//            Log.i(TAG, "getCount: manager.isLoggedIn(): "+manager.isLoggedIn());
//            return manager.isLoggedIn()? 2:1;
//
//        }
//
//
//    }
//
//}
//
//
//
