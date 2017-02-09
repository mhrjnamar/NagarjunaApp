package nagarjuna.com.nagarjunaapa.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import nagarjuna.com.nagarjunaapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageSliderFragment extends Fragment {

    public static int[] images = {R.drawable.bg1,R.drawable.bg2,R.drawable.bg3,R.drawable.bg4,R.drawable.bg5,R.drawable.nagarjuna_college};



    public ImageSliderFragment() {
        // Required empty public constructor
    }

    public static ImageSliderFragment newInstance(int position){
        ImageSliderFragment fragment = new ImageSliderFragment();
        Bundle b = new Bundle();
        b.putInt("pos",position);
        fragment.setArguments(b);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_slider, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView iv= (ImageView) view.findViewById(R.id.imageView);
        Bundle b = getArguments();
        int pos = b.getInt("pos");
        iv.setImageResource(images[pos]);

    }
}
