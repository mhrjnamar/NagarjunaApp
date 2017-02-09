package nagarjuna.com.nagarjunaapa.fragments;


import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
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

import nagarjuna.com.nagarjunaapp.CalenderEvents;
import nagarjuna.com.nagarjunaapp.DetailView;
import nagarjuna.com.nagarjunaapp.Items;
import nagarjuna.com.nagarjunaapp.Notices;
import nagarjuna.com.nagarjunaapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeItemFragment extends Fragment {
    private static final String TAG = "HomeItemFragment";
    URL url;
    HomeItemFragment.LoadImageAsync lis;
    String[] image_urls = {
            "http://blog.adl.org/wp-content/uploads/2013/12/michigan-mock-eviction-notice.jpg",
            "https://www.gstatic.com/ac/settings/landing/welcome_bumper_228x177_0d3cd46b71f2281ccb4344ee6bbff44f.png",
            "https://www.gstatic.com/ac/settings/landing/welcome_bumper_228x177_0d3cd46b71f2281ccb4344ee6bbff44f.png",
            "https://www.gstatic.com/ac/settings/landing/welcome_bumper_228x177_0d3cd46b71f2281ccb4344ee6bbff44f.png",
            "https://www.gstatic.com/ac/settings/landing/welcome_bumper_228x177_0d3cd46b71f2281ccb4344ee6bbff44f.png",
            "http://blog.theenglishmanner.com/wp-content/uploads/2015/08/Ojjm2.jpg"};
    private ListAdapter adapter;


    public HomeItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_item, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView list = (RecyclerView) view.findViewById(R.id.list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(manager);
        adapter = new ListAdapter();
        list.setAdapter(adapter);
    }

    public synchronized void setData(ArrayList<Items> datas) {
        adapter.setData(datas);
    }

    public synchronized void addData(Items data) {
        adapter.addData(data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (lis != null) {
            lis.cancel(true);
        }
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {
        ArrayList<Items> data = new ArrayList<>();
        private int lastPosition = 0;

        public ArrayList<Items> getData() {
            return data;
        }

        public synchronized void setData(ArrayList<Items> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        public synchronized void addData(Items item) {
            data.add(0, item);
            notifyItemChanged(0, item);
        }

        @Override
        public ListAdapter.ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ListHolder(LayoutInflater.from(getActivity()).inflate(R.layout.items_view, parent, false));
        }

        @Override
        public void onBindViewHolder(final ListAdapter.ListHolder holder, int position) {
            final Items item = data.get(position);
            if (item instanceof CalenderEvents){
                holder.itemTitle.setText(item.getTitle());
                holder.date.setText(item.getDate());
                lis = new LoadImageAsync(holder);
                lis.execute(position);

            }else if (item instanceof Notices){
                holder.itemTitle.setText(item.getTitle());
                holder.date.setText(item.getDate());
                lis = new LoadImageAsync(holder);
                lis.execute(position);
            }

            holder.mainView.setTag(item);

            animateHolder(holder, lastPosition >= holder.getAdapterPosition());
            lastPosition = holder.getAdapterPosition();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), DetailView.class);
                    i.putExtra("title", item.getTitle());
                    i.putExtra("description", item.getDescription());
                    i.putExtra("url", image_urls[holder.getAdapterPosition()]);
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        private void animateHolder(RecyclerView.ViewHolder holder, Boolean goesDown) {
            int holderHeight = holder.itemView.getHeight();
            holder.itemView.setPivotY(goesDown ? 0 : holderHeight);
            holder.itemView.setPivotX(holder.itemView.getHeight());
            ObjectAnimator translationY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown ? -300 : 300, 0);
            translationY.setDuration(200).start();
        }

        public class ListHolder extends RecyclerView.ViewHolder {
            TextView itemTitle, date, description, msg;
            ImageView itemImage, options;
            LinearLayout mainView;

            public ListHolder(View itemView) {
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
                        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                        popupMenu.getMenuInflater().inflate(R.menu.share_menu, popupMenu.getMenu());
                        popupMenu.show();
                    }
                });
            }
        }
    }

    //load image form web
    private class LoadImageAsync extends AsyncTask<Integer, Void, Bitmap> {
        ListAdapter.ListHolder holder;
        WeakReference<ListAdapter.ListHolder> weakReference;

        public LoadImageAsync(ListAdapter.ListHolder holder) {
            weakReference = new WeakReference<ListAdapter.ListHolder>(holder);
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
        protected Bitmap doInBackground(Integer... integers) {
            try {
                int pos = integers[0];
                url = new URL(image_urls[pos]);
                InputStream ims = new URL(url.toString()).openStream();
                return decodeSampledBitmapFromResource(ims, getRequiredPixels(350), getRequiredPixels(350));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public int getRequiredPixels(int pixels) {
            return (int) (getResources().getDisplayMetrics().density * pixels);
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
                    color = new Integer[]{Color.parseColor("#502E7D32"), Color.parseColor("#00000000")};
                } else if (i instanceof CalenderEvents) {
                    color = new Integer[]{Color.parseColor("#503F51B5"), Color.parseColor("#00000000")};
                }

                if (color != null && i.isNew()) {
                    ObjectAnimator anim = ObjectAnimator.ofObject(holder.mainView, "backgroundColor", new ArgbEvaluator(), color);
                    anim.setDuration(10000);
                    anim.start();
                    i.setNew(false);
                }
            }
        }

        //convert to control size and quality of image
        public Bitmap decodeSampledBitmapFromResource(InputStream ims, int reqWidth, int reqHeight) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            Bitmap b = BitmapFactory.decodeStream(ims, null, options);
            return getResizedBitmap(b, reqHeight, reqWidth);
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
