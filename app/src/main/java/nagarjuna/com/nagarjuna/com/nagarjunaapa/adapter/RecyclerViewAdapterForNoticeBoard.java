//package nagarjuna.com.nagarjuna.com.nagarjunaapa.adapter;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import nagarjuna.com.nagarjunaapp.NoticeUtils;
//import nagarjuna.com.nagarjunaapp.R;
//
///**
// * Created by dell on 2/17/2016.
// */
//public class RecyclerViewAdapterForNoticeBoard extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    private static final String TAG = "NoticeAdaptor";
//    Context context;
//    LayoutInflater inflater;
//    ArrayList<NoticeUtils> notices = new ArrayList<>();
//
//    private static final int FOOTER = 100;
//    private static final int BODY = 101;
//
//    public RecyclerViewAdapterForNoticeBoard(Context context) {
//        this.context = context;
//        inflater = LayoutInflater.from(context);
//
//    }
//    public void setNoticeItems(ArrayList<NoticeUtils> notices){
//        this.notices = notices;
//        notifyDataSetChanged();
//    }
//
//
//
//    @Override
//    public int getItemViewType(int position) {
//        if(position == notices.size()){
//            return FOOTER;
//        }else{
//            return BODY;
//        }
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        if (viewType == FOOTER) {
//            View itemView = inflater.inflate(R.layout.footer, parent, false);
//            Footer holder = new Footer(itemView);
//            return holder;
//        }
//        View itemView = inflater.inflate(R.layout.notice_board_layout, parent, false);
//        Holder holder = new Holder(itemView);
//        return holder;
//    }
//
//
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//
//        if (holder instanceof Holder) {
//            NoticeUtils utils = notices.get(position);
//          //  Bitmap image = BitmapFactory.decodeByteArray(utils.getImage(),0,utils.getImage().length);
//            Holder h = (Holder) holder;
//            h.noticeTitle.setText(utils.getNoticeTitle());
//            h.noticeDes.setText(utils.getNoticeDescription());
//          //  h.noticeImg.setImageBitmap(image);
//        }else if(holder instanceof Footer){
//
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return notices.size();
//    }
//
//    public class Holder extends RecyclerView.ViewHolder {
//        TextView noticeTitle;
//        TextView noticeDes;
//        ImageView noticeImg;
//
//        public Holder(View itemView) {
//            super(itemView);
//            noticeTitle = (TextView) itemView.findViewById(R.id.noticeTitle);
//            noticeDes = (TextView) itemView.findViewById(R.id.noticeDes);
//            noticeImg = (ImageView) itemView.findViewById(R.id.noticeImg);
//
//        }
//    }
//
//    public class Footer extends RecyclerView.ViewHolder {
//        public Footer(View itemView) {
//            super(itemView);
//        }
//    }
//}
