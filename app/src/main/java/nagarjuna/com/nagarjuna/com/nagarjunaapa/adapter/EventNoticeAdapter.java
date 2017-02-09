package nagarjuna.com.nagarjuna.com.nagarjunaapa.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import nagarjuna.com.headerlibs.StickyHeaderAdapter;
import nagarjuna.com.nagarjunaapp.CalenderEvents;
import nagarjuna.com.nagarjunaapp.EventsDatabase;
import nagarjuna.com.nagarjunaapp.NoticeUtils;
import nagarjuna.com.nagarjunaapp.R;
import nagarjuna.com.nagarjunaapp.RoundedView;
import nagarjuna.com.nagarjunaapp.SessionManager;

/**
 * Created by User on 6/16/2016.
 */
public class EventNoticeAdapter extends RecyclerView.Adapter<EventNoticeAdapter.ItemHolder> implements StickyHeaderAdapter<EventNoticeAdapter.HeaderHolder> {

    Context context;
    EventsDatabase database;
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    ArrayList<Object> data = new ArrayList<>();
    ArrayList<NoticeUtils> notices = new ArrayList<>();
    ArrayList<CalenderEvents> events = new ArrayList<>();
    LayoutInflater inflater;
    String eventTitle;
    Boolean isCalendar = false;
    SessionManager manager;

    String[] titles = {"Notice", "Event"};

    public EventNoticeAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        database = new EventsDatabase(context);
        manager = new SessionManager(context);

//        getNoticeItems();
//        if (manager.isLoggedIn()) {
//            getEventItems();
//        }
    }

    public void isCalendar(Boolean isCalendar) {
        this.isCalendar = isCalendar;
        notifyDataSetChanged();

    }

    public void getNoticeItems() {
        notices = database.getNotices();

        if (notices.size() == 0) {
            Boolean isCreated = database.createNotice(new NoticeUtils("Demo Title", context.getString(R.string.message_content_less), "No Image", "2016/3/17"));
            database.createNotice(new NoticeUtils("Demo Title1", context.getString(R.string.message_content_less), "No Image", "2016/3/17"));
            database.createNotice(new NoticeUtils("Demo Title1", context.getString(R.string.message_content_less), "No Image", "2016/3/17"));
            database.createNotice(new NoticeUtils("Demo Title2", context.getString(R.string.message_content_less), "No Image", "2016/3/17"));
            database.createNotice(new NoticeUtils("Demo Title4", context.getString(R.string.message_content_less), "No Image", "2016/3/17"));
            database.createNotice(new NoticeUtils("Demo Title4", context.getString(R.string.message_content_less), "No Image", "2016/3/17"));
            database.createNotice(new NoticeUtils("Demo Title4", context.getString(R.string.message_content_less), "No Image", "2016/3/14"));
            database.createNotice(new NoticeUtils("Demo Title4", context.getString(R.string.message_content_less), "No Image", "2016/3/17"));
            database.createNotice(new NoticeUtils("Demo Title4", context.getString(R.string.message_content_less), "No Image", "2016/3/18"));
            if (isCreated) {
                Toast.makeText(context, "Created", Toast.LENGTH_LONG).show();
            }
            notices = database.getNotices();
        }
    }

    public void getEventItems() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month = new SimpleDateFormat("M", Locale.getDefault());
        int m = Integer.parseInt(month.format(cal.getTime())) - 1;
        eventTitle = String.valueOf(Html.fromHtml("Upcoming events of <b>" + months[m] + "</b>"));

        //get events from database according to current month and update the events to the adapter
        events = database.getEventForMonth(m);
        if (events.size() == 0) {
            database.createEvents(new CalenderEvents("may","2016/4/29","may",false,2016, 4, 29,"Lalitpur",""));
            database.createEvents(new CalenderEvents("title1","2016/6/9",context.getString(R.string.message_content_less),false, 2016, 6, 9,"Lalitpur",""));
            database.createEvents(new CalenderEvents("title2","2016/6/2",context.getString(R.string.message_content_less),false, 2016, 6, 2,"Lalitpur",""));
            events = database.getEventForMonth(m);
        }
    }





    public static void scaleY(RecyclerView.ViewHolder holder, Boolean isdown) {
        int holderHeight = holder.itemView.getHeight();
        int holderWidth = holder.itemView.getWidth();

        holder.itemView.setPivotY(holderHeight / 2);
        holder.itemView.setPivotX(holderWidth / 2);

        AnimatorSet set = new AnimatorSet();
        ObjectAnimator translationY = ObjectAnimator.ofFloat(holder.itemView, "translationY", isdown ? 0 : holderHeight, 0);
        set.playTogether(translationY);
        set.setDuration(400);
        set.start();
    }


    public void setNoticeItems(ArrayList<NoticeUtils> notices) {
        this.notices = notices;
        for (int i = 0; i < notices.size(); i++) {
            data.add(notices.get(i));
        }
        notifyDataSetChanged();
    }

    /**
     * @param events sets the ArrayList with type CalenderEvents in adapter
     */
    public void setEvents(ArrayList<CalenderEvents> events) {
        this.events = events;

        for (int i = 0; i < events.size(); i++) {
            data.add(events.get(i));
        }
        notifyDataSetChanged();
    }


    @Override
    public EventNoticeAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v1 = inflater.inflate(R.layout.event_view, parent, false);
        return new ItemHolder(v1);
    }


    int nextPos = 0;

    @Override
    public void onBindViewHolder(EventNoticeAdapter.ItemHolder holder, int position) {


        if (position < events.size()) {

            CalenderEvents event = events.get(position);
            holder.date.setText(String.valueOf(event.getYear() + "/" + event.getMonth() + "/" + event.getDate()));
            holder.eventTitle.setText(event.getTitle());
            holder.eventDes.setText(event.getDescription());
//            holder.roundedView.setText(String.valueOf(event.getDate()));
//            holder.roundedView.setRoundViewBackgroundColor(R.color.accentColor);
        } else {
            NoticeUtils utils = notices.get(position - events.size());
            holder.eventTitle.setText(utils.getNoticeTitle());
            holder.eventDes.setText(utils.getNoticeDescription());
//            holder.roundedView.setText(utils.getNoticeTitle().substring(0, 1));
//            holder.roundedView.setRoundViewBackgroundColor(R.color.primaryColor);
            holder.date.setText(utils.getDate());
        }

        scaleY(holder, nextPos > position);
        nextPos = position;


    }

    @Override
    public int getItemCount() {
        if (isCalendar) {

            return events.size();
        } else if (!manager.isLoggedIn()) {

            return notices.size();
        } else {

            return events.size() + notices.size();
        }
    }

    @Override
    public long getHeaderId(int position) {
        if (position < events.size()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = inflater.inflate(R.layout.header_test, parent, false);
        return new HeaderHolder(view);

    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position) {

        if (position != 0) {

            viewholder.header.setText(titles[0]);
        } else {

            if (manager.isLoggedIn()) {


                viewholder.header.setText(eventTitle);
            } else {
                viewholder.header.setText(titles[0]);
            }

        }


    }

    public class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView;
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView eventTitle;
        TextView eventDes;
//        RoundedView roundedView;

        public ItemHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            eventTitle = (TextView) itemView.findViewById(R.id.event);
            eventDes = (TextView) itemView.findViewById(R.id.description);
//            roundedView = (RoundedView) itemView.findViewById(R.id.roundView);
//            roundedView.setRoundViewTextColor(Color.WHITE);
        }
    }


}
