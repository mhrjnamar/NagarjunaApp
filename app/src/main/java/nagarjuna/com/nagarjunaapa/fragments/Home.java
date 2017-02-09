//package nagarjuna.com.nagarjunaapa.fragments;
//
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Locale;
//
//import nagarjuna.com.nagarjuna.com.nagarjunaapa.adapter.EventNoticeAdapter;
//import nagarjuna.com.nagarjunaapp.CalenderEvents;
//import nagarjuna.com.nagarjunaapp.EventsDatabase;
//import nagarjuna.com.nagarjunaapp.NoticeUtils;
//import nagarjuna.com.nagarjunaapp.R;
//import nagarjuna.com.nagarjunaapp.SessionManager;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class Home extends Fragment {
//
//
//    FragmentManager fm;
//
//    TextView eventsTitle;
//    RecyclerView upComingEvents;
//    RecyclerView noticeLists;
//    //    RecyclerViewAdapterForNoticeBoard adapterForNoticeBoard;
////    EventAdapter eventAdapter;
//    EventsDatabase database;
//    //    RecyclerViewAdapterForNoticeAndEvents adapter;
//    EventNoticeAdapter adapter;
//    ArrayList<CalenderEvents> events = new ArrayList<>();
//    ArrayList<NoticeUtils> notices = new ArrayList<>();
//    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
//    RelativeLayout eventsHolder, noticeBoard;
//    SessionManager manager;
//    String[] titles = {"Events", "Titles"};
//
//
//    public Home() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//
//        fm = getActivity().getSupportFragmentManager();
//
//
//        eventsHolder = (RelativeLayout) view.findViewById(R.id.eventsHolder);
//        noticeBoard = (RelativeLayout) view.findViewById(R.id.noticeBoard);
//        eventsTitle = (TextView) view.findViewById(R.id.upCommingEvents);
//        manager = new SessionManager(getActivity());
//        database = new EventsDatabase(getActivity());
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
//
//        //initilize recyclerView for upComming events and set Adapter
//        upComingEvents = (RecyclerView) view.findViewById(R.id.rv_upcomming_events);
//        upComingEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
////        eventAdapter = new EventAdapter(getActivity());
////        upComingEvents.setAdapter(eventAdapter);
//
////        adapter = new RecyclerViewAdapterForNoticeAndEvents(getActivity());
//        adapter = new EventNoticeAdapter(getActivity());
////        upComingEvents.setAdapter(adapter);
//
//        //initilize recyclerView for noticeList and setAdapter
//        noticeLists = (RecyclerView) view.findViewById(R.id.notice_recycler_view);
//        noticeLists.setLayoutManager(new LinearLayoutManager(getActivity()));
////        adapterForNoticeBoard = new RecyclerViewAdapterForNoticeBoard(getActivity());
////        noticeLists.setAdapter(adapterForNoticeBoard);
//        noticeLists.setVisibility(View.GONE);
//
////        noticeLists.setAdapter(adapter);
//
//        //create calender instance to get the systems current month
//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat month = new SimpleDateFormat("M", Locale.getDefault());
//        int m = Integer.parseInt(month.format(cal.getTime())) - 1;
//        //eventsTitle.setText(Html.fromHtml("Upcoming events of <b>"+months[m]+"</b>"));
//        eventsTitle.setVisibility(View.GONE);
//        String eventTitle = String.valueOf(Html.fromHtml("Upcoming events of <b>" + months[m] + "</b>"));
//
//
//        //get events from database according to current month and update the events to the adapter
//        events = database.getEventForMonth(m);
//        if (events.size() == 0) {
//            database.createEvents(new CalenderEvents("may","2016/4/29","may",false,2016, 4, 29,"Lalitpur"));
//            database.createEvents(new CalenderEvents("title1","2016/4/29","des1",false,2016, 5, 9,"Lalitpur"));
//            database.createEvents(new CalenderEvents("title2","2016/4/29","des2",false,2016, 5, 2,"Lalitpur"));
//            events = database.getEventForMonth(m);
//        }
////        eventAdapter.setEvents(events);
////        adapter.setTitle(eventTitle);
//        if (manager.isLoggedIn()) {
//            adapter.setEvents(events);
//        }
//        //Convert image to bitmap and set compressed data to outputStream
////        ByteArrayOutputStream stream = new ByteArrayOutputStream();
////        Bitmap map = BitmapFactory.decodeResource(getResources(),R.drawable.background_1);
////        map.compress(Bitmap.CompressFormat.PNG,100,stream);
//
//        //get notice data form database and update to adapter
//        notices = database.getNotices();
//
//        if (notices.size() == 0) {
//            Boolean isCreated = database.createNotice(new NoticeUtils("Demo Title", "Demo Description", "No Image", "kkj"));
//            database.createNotice(new NoticeUtils("Demo Title1", "It’s my great pleasure to welcome you to the Nagarjuna Academy web site. We have been progressing academically since 1988 to fulfill the needs of quality education that our country needs in the present ICT Era.", "No Image", "kkj"));
//            database.createNotice(new NoticeUtils("Demo Title2", getString(R.string.message_content_less), "No Image", "kkj"));
//            database.createNotice(new NoticeUtils("Demo Title3", getString(R.string.message_content_less), "No Image", "kkj"));
//            database.createNotice(new NoticeUtils("Demo Title4", getString(R.string.message_content_less), "No Image", "kkj"));
//            if (isCreated) {
//                Toast.makeText(getActivity(), "Created", Toast.LENGTH_LONG).show();
//            }
//            notices = database.getNotices();
//        }
////        adapterForNoticeBoard.setNoticeItems(notices);
//
//
////        adapter.setTitle("Notices");
//        adapter.setNoticeItems(notices);
//
//        fm.beginTransaction().replace(R.id.dummyLayout, new Hommie()).commit();
//
//
////        //sets visibility of the events gone if not logged in
////        if (manager.isLoggedIn()){
////            eventsHolder.setVisibility(View.VISIBLE);
////        }else {
////            eventsHolder.setVisibility(View.GONE);
////        }
//
//
//    }
//
//}
