package com.yunkang.prenatalgt.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.hlib.util.AppUtil;
import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.ui.base.BaseActivity;

import java.util.Random;

public class SplashActivity extends BaseActivity {

    private TextView[] ts;
    private View container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        initViews();

    }


    private void initViews() {
        container = findViewById(R.id.splash_container);
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[]{
                        getResources().getColor(R.color.colorPrimary),
                        getResources().getColor(R.color.colorPrimaryDark)
                });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            container.setBackground(gd);
        }
        container.setClickable(false);

        ts = new TextView[]{
                (TextView) findViewById(R.id.splash_m),
                (TextView) findViewById(R.id.splash_u),
                (TextView) findViewById(R.id.splash_s),
                (TextView) findViewById(R.id.splash_i),
                (TextView) findViewById(R.id.splash_c),
                (TextView) findViewById(R.id.splash_o),
                (TextView) findViewById(R.id.splash_c1),
                (TextView) findViewById(R.id.splash_o1)
        };
        ts[0].post(new Runnable() {
            @Override
            public void run() {
                for (TextView t : ts) {
                    t.setVisibility(View.VISIBLE);
                    startTextInAnim(t);
                }
            }
        });
    }


    private void startTextInAnim(TextView t) {
        Random r = new Random();
        DisplayMetrics metrics = AppUtil.getMetrics(this);
        int x = r.nextInt(metrics.widthPixels * 4 / 3);
        int y = r.nextInt(metrics.heightPixels * 4 / 3);
        float s = r.nextFloat() + 4.0f;
        ValueAnimator tranY = ObjectAnimator.ofFloat(t, "translationY", y - t.getY(), 0);
        ValueAnimator tranX = ObjectAnimator.ofFloat(t, "translationX", x - t.getX(), 0);
        ValueAnimator scaleX = ObjectAnimator.ofFloat(t, "scaleX", s, 1.0f);
        ValueAnimator scaleY = ObjectAnimator.ofFloat(t, "scaleY", s, 1.0f);
        ValueAnimator alpha = ObjectAnimator.ofFloat(t, "alpha", 0.0f, 1.0f);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(1800);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.play(tranX).with(tranY).with(scaleX).with(scaleY).with(alpha);
        if (t == findViewById(R.id.splash_o1)) {
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    startFinalAnim();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        set.start();
    }

    private void startFinalAnim() {
        final ImageView image = (ImageView) findViewById(R.id.splash_logo);
        final TextView name = (TextView) findViewById(R.id.splash_name);

        ValueAnimator alpha = ObjectAnimator.ofFloat(image, "alpha", 0.0f, 1.0f);
        alpha.setDuration(1000);
        ValueAnimator alphaN = ObjectAnimator.ofFloat(name, "alpha", 0.0f, 1.0f);
        alphaN.setDuration(1000);
        ValueAnimator tranY = ObjectAnimator.ofFloat(image, "translationY", -image.getHeight() / 3, 0);
        tranY.setDuration(1000);
        ValueAnimator wait = ObjectAnimator.ofInt(0, 100);
        wait.setDuration(1000);
        wait.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startMainActivity();
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new LinearInterpolator());
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                image.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        set.play(alpha).with(alphaN).with(tranY).before(wait);
        set.start();
    }

    private void startMainActivity() {
        LoginActivity.startActivity(this);
    }


}
