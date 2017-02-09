package nagarjuna.com.nagarjuna.com.nagarjunaapa.adapter;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
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

import nagarjuna.com.nagarjunaapp.CalenderEvents;
import nagarjuna.com.nagarjunaapp.Events;
import nagarjuna.com.nagarjunaapp.Items;
import nagarjuna.com.nagarjunaapp.NoticeUtils;
import nagarjuna.com.nagarjunaapp.Notices;
import nagarjuna.com.nagarjunaapp.R;

public class PagerListAdapter extends RecyclerView.Adapter<PagerListAdapter.PageHolder> {
    ArrayList<CalenderEvents> events = new ArrayList<>();
    ArrayList<NoticeUtils> notices = new ArrayList<>();
    Context context;

    public PagerListAdapter(Context context) {
        this.context = context;
    }

    public void setEvents(ArrayList<CalenderEvents> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    public void setNotices(ArrayList<NoticeUtils> notices) {
        this.notices = notices;
        notifyDataSetChanged();
    }

    @Override
    public PageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PageHolder(LayoutInflater.from(context).inflate(R.layout.items_view, parent, false));
    }

    @Override
    public void onBindViewHolder(PageHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PageHolder extends RecyclerView.ViewHolder {

        TextView itemTitle, date, description, msg;
        ImageView itemImage, options;
        LinearLayout mainView;

        public PageHolder(View itemView) {
            super(itemView);

            itemTitle = (TextView) itemView.findViewById(R.id.item_title);
            date = (TextView) itemView.findViewById(R.id.item_date);
            description = (TextView) itemView.findViewById(R.id.item_description);
            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
            mainView = (LinearLayout) itemView.findViewById(R.id.mainView);
            options = (ImageView) itemView.findViewById(R.id.options);
            msg = (TextView) itemView.findViewById(R.id.msg);
            options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(context, view);
                    popupMenu.getMenuInflater().inflate(R.menu.share_menu, popupMenu.getMenu());
                    popupMenu.show();
                }
            });
        }
    }

    public class LoadImageAsync extends AsyncTask<Void, Void, Bitmap> {
        PagerListAdapter.PageHolder holder;


        WeakReference<PagerListAdapter.PageHolder> weakReference;
        String[] image_urls = {"https://www.google.com.np/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png",
                "https://developer.android.com/images/home/n-preview-hero.png",
                "https://www.gstatic.com/ac/settings/landing/welcome_bumper_228x177_0d3cd46b71f2281ccb4344ee6bbff44f.png",
                "https://developer.android.com/images/home/n-preview-hero.png"};

        public LoadImageAsync(PagerListAdapter.PageHolder holder) {
            weakReference = new WeakReference<PagerListAdapter.PageHolder>(holder);
            LoadImageAsync.this.holder = weakReference.get();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            holder.msg.setVisibility(View.VISIBLE);
            holder.msg.setText(R.string.loading);
            holder.mainView.setVisibility(View.GONE);
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                URL url = new URL(image_urls[new Random().nextInt(4)]);
                InputStream ims = new URL(url.toString()).openStream();
                return decodeSampledBitmapFromResource(ims, getRequiredPixels(100), getRequiredPixels(100));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public int getRequiredPixels(int pixels) {
            return (int) (context.getResources().getDisplayMetrics().density * pixels);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            holder = weakReference.get();
            if (bitmap != null) {
                holder.msg.setVisibility(View.GONE);
                holder.mainView.setVisibility(View.VISIBLE);
                holder.itemImage.setImageBitmap(bitmap);
                Integer[] color = null;
                Items i = (Items) holder.mainView.getTag();
                if (i instanceof Notices) {
                    color = new Integer[]{Color.parseColor("#502E7D32"), Color.parseColor("00000000")};
                } else if (i instanceof Events) {
                    color = new Integer[]{Color.parseColor("#503F51B5"), Color.parseColor("00000000")};
                }

                if (color != null && i.isNew()) {
                    ObjectAnimator anim = ObjectAnimator.ofObject(holder.mainView, "backgroundColor", new ArgbEvaluator(), color);
                    anim.setDuration(10000);
                    anim.start();
                    i.setNew(false);
                }
            }
        }

        public Bitmap decodeSampledBitmapFromResource(InputStream ims, int reqWidth, int reqHeight) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            return getResizedBitmap(BitmapFactory.decodeStream(ims, null, options), reqHeight, reqWidth);
        }

        public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {
                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                    inSampleSize *= 2;

                }
            }
            return inSampleSize;
        }

        public Bitmap getResizedBitmap(Bitmap image, int maxHeight, int maxWidth) {
            int width = image.getWidth();
            int height = image.getHeight();
            float bitmapRatio = (float) width / (float) height;
            if (bitmapRatio > 1) {
                width = maxWidth;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxHeight;
                width = (int) (height / bitmapRatio);
            }
            return Bitmap.createScaledBitmap(image, width, height, true);
        }
    }
}


