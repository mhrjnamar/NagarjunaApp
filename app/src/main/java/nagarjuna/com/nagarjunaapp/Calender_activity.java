package nagarjuna.com.nagarjunaapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import nagarjuna.com.nagarjunaapa.fragments.Calender_Frag;

public class Calender_activity extends AppCompatActivity {
    private static final String TAG = "Calender_activity";
    final int default_pos = 3;
    ViewPager vp;
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    CustomPagerAdapter cpa;
    Spinner monthsSpinner;
    ArrayList<String> requiredMonths;
    int counter = 1;
    Toolbar tb;
//    Boolean ischangeView = false;
//    String view_mode = "default_mode";
    LinearLayout daysHolder;
    Calender_Frag f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_activity);


        tb = (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("");
        tb.setTitle("");

        daysHolder = (LinearLayout) findViewById(R.id.daysHolder);


        //create calender instance to get the systems current month
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month = new SimpleDateFormat("M", Locale.getDefault());
        final int m = Integer.parseInt(month.format(cal.getTime())) - 4;


        requiredMonths = new ArrayList<>();
        monthsSpinner = (Spinner) findViewById(R.id.monthHolder);
        for (int i = default_pos; i < months.length; i++) {
            requiredMonths.add(months[i]);
            if (counter == 6) {
                break;
            }
            counter++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Calender_activity.this, R.layout.custom_spinner, R.id.text, requiredMonths);
        monthsSpinner.setAdapter(adapter);
        // adapter.setDropDownViewResource(R.layout.custom_spinner);


        //intitialize views
        vp = (ViewPager) findViewById(R.id.viewPager);

        // initialize and set viewPager adapter
        cpa = new CustomPagerAdapter();
        vp.setAdapter(cpa);
        vp.setCurrentItem(m);
        f = (Calender_Frag) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + vp.getCurrentItem());
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                f = (Calender_Frag) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + position);
               // updateView(view_mode);
                monthsSpinner.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        monthsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    vp.setCurrentItem(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


    }






//    private void updateView(String view) {
//        if (view.equalsIgnoreCase("event_mode")) {
//            view_mode = "event_mode";
//            f.updateView(view_mode);
//            daysHolder.setVisibility(View.GONE);
//        } else if (view.equalsIgnoreCase("cal_mode")) {
//            daysHolder.setVisibility(View.VISIBLE);
//            view_mode = "cal_mode";
//            f.updateView(view_mode);
//        } else {
//            daysHolder.setVisibility(View.VISIBLE);
//            view_mode = "default_mode";
//            Log.i("TAG", "updateView: "+f);
//            f.updateView(view_mode);
//        }
//    }

    public class CustomPagerAdapter extends FragmentPagerAdapter {

        String v;

        public CustomPagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            //return new instance of the Calendar_Frag according to the view Pager Position

            return new Calender_Frag().newInstance(position);
        }

        @Override
        public int getCount() {
            return 6;
        }

        public void changeView(String v) {
            this.v = v;
        }
    }


}
