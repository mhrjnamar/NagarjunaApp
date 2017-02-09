package nagarjuna.com.nagarjunaapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    private static final String TAG = "Splash";

    Handler handler = new Handler();
    private SessionManager manager;
    Runnable holdScreen = new Runnable() {
        @Override
        public void run() {


            if (manager.isFirstLogin()) {
                Intent i = new Intent(Splash.this, LoginActivity.class);
                startActivity(i);

            } else {
                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
            }

        }
    };
    private ImageView icon;
    private TextView n;
    private TextView name;
    private TextView of_it;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        icon = (ImageView) findViewById(R.id.icon);
        n = (TextView) findViewById(R.id.n);
        name = (TextView) findViewById(R.id.name);
        of_it = (TextView) findViewById(R.id.of_IT);
        manager = new SessionManager(Splash.this);
        manager.storeUser("BIM", "8", "1234", "4448");
        manager.addUsers("amar", "amar123");

    }

    @Override
    protected void onResume() {
        super.onResume();

        anim_logo(icon);
        anim_n(n);
        anim_name(name, of_it);

    }

    public void anim_logo(final ImageView img) {
        img.setPivotY(0);
        img.setAlpha(0.0f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(img, "alpha", 0.1f, 1.0f);
        alpha.setDuration(1200);
        alpha.start();

        alpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ObjectAnimator translateY = ObjectAnimator.ofFloat(img, "translationY", 0, 50);
                translateY.setDuration(1200);
                translateY.start();
                handler.postDelayed(holdScreen, 1000);

            }
        });
    }

    public void anim_n(TextView n) {
        n.setScaleY(0f);
        n.setScaleX(0f);
        ObjectAnimator translateX = ObjectAnimator.ofFloat(n, "translationX", 0, -250);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(n, "scaleX", -4f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(n, "scaleY", -4f);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleX).with(scaleY).with(translateX);
        set.setInterpolator(new AccelerateInterpolator(2));
        set.setDuration(1000);
        set.start();
    }

    public void anim_name(TextView name, TextView of_it) {
        of_it.setAlpha(0f);
        name.setAlpha(0f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(name, "alpha", 0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f);
        ObjectAnimator translateX = ObjectAnimator.ofFloat(name, "translationX", -100, 20);
        ObjectAnimator of_it_alpha = ObjectAnimator.ofFloat(of_it, "alpha", 1f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(translateX, alpha, of_it_alpha);
        set.setInterpolator(new DecelerateInterpolator(2));
        set.setStartDelay(1200);
        set.setDuration(1000);
        set.start();

    }


}
