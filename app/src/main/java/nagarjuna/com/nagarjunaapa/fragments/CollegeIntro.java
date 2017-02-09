package nagarjuna.com.nagarjunaapa.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import nagarjuna.com.nagarjuna.com.nagarjunaapa.adapter.CustomPagerAdapter;
import nagarjuna.com.nagarjunaapp.LoginActivity;
import nagarjuna.com.nagarjunaapp.R;
import nagarjuna.com.nagarjunaapp.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollegeIntro extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {


    public LinearLayout indicator;
    public ImageView[] dot;
    LinearLayout openApp;
    SessionManager manager;
    TextView msg_view_more;
    TextView intro_view_more;
    TextView collegeIntro;
    TextView msg_from_director;
    ViewPager pager;
    CustomPagerAdapter adapter;
    int dotCount = 0;
    int pos = 0;
    int current = 0;
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
    ArrayList<ImageView> images = new ArrayList<>();

    public CollegeIntro() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_college_intro, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CollapsingToolbarLayout tb = (CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout);
        tb.setTitle("Nagarjuna College of IT");
        tb.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        indicator = (LinearLayout) view.findViewById(R.id.indicator);
        collegeIntro = (TextView) view.findViewById(R.id.collegeIntro);
        msg_from_director = (TextView) view.findViewById(R.id.msgFromDiector);
        msg_view_more = (TextView) view.findViewById(R.id.msg_view_more);
        intro_view_more = (TextView) view.findViewById(R.id.info_view_more);
        msg_view_more.setOnClickListener(this);
        intro_view_more.setOnClickListener(this);
        openApp = (LinearLayout) view.findViewById(R.id.openApp);

        //intialize instance of SessionManager to access its methods to check First Login
        manager = new SessionManager(getActivity());

        openApp.setVisibility(manager.isFirstLogin() ? View.VISIBLE : View.GONE);

        openApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //replace the View from the base activity to new fragment Login()
//               getChildFragmentManager().beginTransaction().replace(R.id.splashHolder, new Login()).commit();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });

        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.addOnPageChangeListener(this);
        adapter = new CustomPagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(adapter);
        setIndicator();

    }

    @Override
    public void onResume() {
        super.onResume();
        h.removeCallbacks(r);
        h.post(r);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        h.removeCallbacks(r);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.info_view_more) {
            if (intro_view_more.getText().equals("View More")) {
                intro_view_more.setText(R.string.view_less);
                collegeIntro.setText(getString(R.string.welcome_content));
            } else {
                intro_view_more.setText(R.string.view_more);
                collegeIntro.setText(getString(R.string.welcome_content_less));
            }
        } else if (v.getId() == R.id.msg_view_more) {
            if (msg_view_more.getText().equals("View More")) {
                msg_view_more.setText(R.string.view_less);
                msg_from_director.setText(getString(R.string.message_content));
            } else {
                msg_view_more.setText(R.string.view_more);
                msg_from_director.setText(getString(R.string.message_content_less));
            }

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < images.size(); i++) {
            ImageView iv = images.get(i);
            if (i != position) {
                iv.setImageDrawable(getResources().getDrawable(R.drawable.unselected_circle_indicator));
            } else {
                iv.setImageDrawable(getResources().getDrawable(R.drawable.selected_circle_indicator));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setIndicator() {
        dotCount = adapter.getCount();
        for (int i = 0; i < dotCount; i++) {
            ImageView iv = new ImageView(getActivity());
            iv.setImageDrawable(getResources().getDrawable(R.drawable.unselected_circle_indicator));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            float  dp = getResources().getDisplayMetrics().density;
            int margin = (int) (dp * 4);
            Log.i("TAG", "setIndicator: dp: "+dp);
            params.setMargins(margin, 0, margin, 0);
            indicator.addView(iv, params);
            images.add(iv);
            iv.setTag(i);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                  pager.setCurrentItem(pos);
                }
            });
        }
        images.get(0).setImageDrawable(getResources().getDrawable(R.drawable.selected_circle_indicator));


    }
}

