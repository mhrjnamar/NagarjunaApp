//package nagarjuna.com.nagarjuna.com.nagarjunaapa.adapter;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//import nagarjuna.com.nagarjunaapp.CalenderEvents;
//import nagarjuna.com.nagarjunaapp.NoticeUtils;
//import nagarjuna.com.nagarjunaapp.R;
//import nagarjuna.com.nagarjunaapp.RoundedView;
//
///**
// * Created by User on 6/5/2016.
// */
//public class RecyclerViewAdapterForNoticeAndEvents extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    private static final String TAG = "NoticeAndEvents";
//
//    ArrayList<Object> data = new ArrayList<>();
//    ;
//
//    Context context;
//
//    public static final int NOTICES = 0X00;
//    public static final int EVENTS = 0X01;
//    public static final int TITLES = 0X02;
//
//    ArrayList<NoticeUtils> notices = new ArrayList<>();
//    ArrayList<CalenderEvents> events = new ArrayList<>();
//    String title;
//
//
//    public void setNoticeItems(ArrayList<NoticeUtils> notices) {
//        this.notices = notices;
//        for (int i = 0; i < notices.size(); i++) {
//            data.add(notices.get(i));
//        }
//        notifyDataSetChanged();
//    }
//
//    /**
//     * @param events sets the ArrayList with type CalenderEvents in adapter
//     */
//    public void setEvents(ArrayList<CalenderEvents> events) {
//        this.events = events;
//
//        for (int i = 0; i < events.size(); i++) {
//            data.add(events.get(i));
//        }
//        notifyDataSetChanged();
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//        data.add(title);
//        notifyDataSetChanged();
//
//    }
//
//
//    /**
//     * @param event add instance of CalenderEvents to ArrayList with type CalenderEvents in adapter
//     */
//    public void addEvents(CalenderEvents event) {
//        events.add(event);
//        for (int i = 0; i < events.size(); i++) {
//            data.add(events.get(i));
//        }
//        notifyDataSetChanged();
//    }
//
//
//    public RecyclerViewAdapterForNoticeAndEvents(Context context) {
//
//        Log.i(TAG, "RecyclerViewAdapterForNoticeAndEvents: ");
//
//        this.context = context;
//
//
////        for (int i = 0; i<notices.size() ;i++) {
////            data.add(notices.get(i));
////        }
//
////        for (int i = 0; i<events.size() ;i++) {
////            data.add(events.get(i));
////        }
//        Log.i(TAG, "RecyclerViewAdapterForNoticeAndEvents: " + data);
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        Log.i(TAG, "getItemViewType: " + position);
//        if (data.get(position) instanceof NoticeUtils) {
//            return NOTICES;
//        } else if (data.get(position) instanceof CalenderEvents) {
//            return EVENTS;
//        } else if (data.get(position) != null)
//            return TITLES;
//        return -1;
//
//
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        Log.i(TAG, "onCreateViewHolder: ");
//        LayoutInflater inflater = LayoutInflater.from(context);
//        if (viewType == NOTICES) {
//            View v1 = inflater.inflate(R.layout.notice_board_layout, parent, false);
//            return new NoticeHolder(v1);
//        } else if (viewType == EVENTS) {
//            View v2 = inflater.inflate(R.layout.event_view, parent, false);
//            return new EventHolder(v2);
//        } else {
//            View v3 = inflater.inflate(R.layout.title_view, parent, false);
//            return new TitlesHolder(v3);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        Log.i(TAG, "onBindViewHolder:real pos  " + position);
//
//        if (holder.getItemViewType() == NOTICES) {
//            Log.i(TAG, "onBindViewHolder:NotPos " + position);
//            NoticeHolder h = (NoticeHolder) holder;
//            configureNotices(h, position - (2 + events.size()));
//
//        } else if (holder.getItemViewType() == EVENTS) {
//            Log.i(TAG, "onBindViewHolder:EvePos " + position);
//            EventHolder h = (EventHolder) holder;
//            configureEvents(h, position - 1);
//        } else {
//            Log.i(TAG, "onBindViewHolder:TitPos " + position);
//            TitlesHolder h = (TitlesHolder) holder;
//            configureTitleHeader(h, position);
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        Log.i(TAG, "getItemCount:size " + data.size());
//        return data.size();
//    }
//
//
//    class EventHolder extends RecyclerView.ViewHolder {
//        TextView date;
//        TextView eventTitle;
//        TextView eventDes;
//        RoundedView roundedView;
//
//        public EventHolder(View itemView) {
//            super(itemView);
//            //date = (TextView) itemView.findViewById(R.id.dayNo);
//            eventTitle = (TextView) itemView.findViewById(R.id.event);
//            eventDes = (TextView) itemView.findViewById(R.id.description);
//            roundedView = (RoundedView) itemView.findViewById(R.id.roundView);
//            roundedView.setRoundViewTextColor(Color.WHITE);
//
//        }
//    }
//
//    class NoticeHolder extends RecyclerView.ViewHolder {
//        TextView noticeTitle;
//        TextView noticeDes;
//        ImageView noticeImg;
//
//        public NoticeHolder(View itemView) {
//            super(itemView);
//            noticeTitle = (TextView) itemView.findViewById(R.id.noticeTitle);
//            noticeDes = (TextView) itemView.findViewById(R.id.noticeDes);
//            noticeImg = (ImageView) itemView.findViewById(R.id.noticeImg);
//
//        }
//    }
//
//    class TitlesHolder extends RecyclerView.ViewHolder {
//        TextView titleHeader;
//
//        public TitlesHolder(View itemView) {
//            super(itemView);
//            titleHeader = (TextView) itemView.findViewById(R.id.title_header);
//        }
//    }
//
//
//    public void configureEvents(EventHolder h, int p) {
//        //create instance of CalenderEvents to set text eventDate and Title in textViews
//        if (events != null) {
//            Log.i(TAG, "configureEvents:events size " + events.size());
//            Log.i(TAG, "configureEvents:pos " + p);
//
//            CalenderEvents event = events.get(p);
//            //holder.date.setText(String.valueOf(event.getDate()));
//            h.eventTitle.setText(event.getTitle());
//            h.eventDes.setText(event.getDescription());
//            h.roundedView.setText(String.valueOf(event.getDate()));
//            h.roundedView.setRoundViewBackgroundColor(R.color.accentColor);
//
//        }
//    }
//
//    public void configureNotices(NoticeHolder h, int p) {
//
//        //  Bitmap image = BitmapFactory.decodeByteArray(utils.getImage(),0,utils.getImage().length);
//
//        Log.i(TAG, "configureNotices:pos " + p);
//        NoticeUtils utils = notices.get(p);
//        Log.i(TAG, "configureNotices: " + utils.getNoticeTitle());
//        h.noticeTitle.setText(utils.getNoticeTitle());
//        h.noticeDes.setText(utils.getNoticeDescription());
//        //  h.noticeImg.setImageBitmap(image);
//
//    }
//
//    public void configureTitleHeader(TitlesHolder h, int p) {
//        if (p == 0) {
//            h.titleHeader.setText(data.get(0).toString());
//        }else {
//            h.titleHeader.setText(data.get(1+events.size()).toString());
//        }
//
//    }
//
//
//}
