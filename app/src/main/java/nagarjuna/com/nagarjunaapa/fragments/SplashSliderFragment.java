package nagarjuna.com.nagarjunaapa.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import nagarjuna.com.nagarjuna.com.nagarjunaapa.adapter.CustomPagerAdapter;
import nagarjuna.com.nagarjunaapp.LoginActivity;
import nagarjuna.com.nagarjunaapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashSliderFragment extends Fragment {

    private Button openApp;
    private ViewPager pager;
    CustomPagerAdapter adapter;
    int pos = 0;
    Handler h = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {

            if (pos >= 5) {
                pos = 0;
            } else {
                pos++;
            }
            pager.setCurrentItem(pos, false);
            h.postDelayed(r, 5000);

        }
    };


    public SplashSliderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_slider, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        openApp = (Button) view.findViewById(R.id.openApp);
        openApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });

        pager = (ViewPager) view.findViewById(R.id.pager);
        adapter = new CustomPagerAdapter(getActivity().getSupportFragmentManager());
        pager.setCurrentItem(R.drawable.bg1);
        pager.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        h.post(r);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        h.removeCallbacks(r);
    }
}
