package nagarjuna.com.nagarjuna.com.nagarjunaapa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nagarjuna.com.nagarjunaapp.CalenderEvents;
import nagarjuna.com.nagarjunaapp.R;
import nagarjuna.com.nagarjunaapp.RoundedView;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    Context context;
    ArrayList<CalenderEvents> events = new ArrayList<>();
    public EventAdapter(Context context) {
        this.context = context;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //teturn instance of the EventHoldet class after inflating the layout event_view
        return new EventHolder(LayoutInflater.from(context).inflate(R.layout.calendar_event_view, parent, false));
    }

    /**
     *
     * @param events sets the ArrayList with type CalenderEvents in adapter
     */
    public void setEvents(ArrayList<CalenderEvents> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    /**
     *
     * @param event add instance of CalenderEvents to ArrayList with type CalenderEvents in adapter
     */
    public void addEvents(CalenderEvents event) {
        events.add(event);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        //create instance of CalenderEvents to settext eventDate and Title in textViews
        CalenderEvents event = events.get(position);
        //holder.date.setText(String.valueOf(event.getDate()));
        holder.eventTitle.setText(event.getTitle());
//        holder.roundedView.setText(String.valueOf(event.getDate()));
//        holder.roundedView.setRoundViewBackgroundColor(R.color.accentColor);

        holder.date.setText(event.getDate());


    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        TextView eventTitle,date;
//        RoundedView roundedView;

        public EventHolder(View itemView) {
            super(itemView);

            eventTitle = (TextView) itemView.findViewById(R.id.cal_title);
//            roundedView = (RoundedView) itemView.findViewById(R.id.cal_round_view);
//            roundedView.setRoundViewTextColor(Color.WHITE);
            date = (TextView) itemView.findViewById(R.id.date);

        }
    }


}