package com.example.denny.myanimation;

import android.animation.Animator;
import android.animation.StateListAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
    }

    public void startClick(View view){
        alpha();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animationUtils(){
        // get the center for the clipping circle
        int cx = (textView.getLeft() + textView.getRight()) / 2;
        int cy = (textView.getTop() + textView.getBottom()) / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(textView.getWidth(), textView.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(textView, cx, cy, 0, finalRadius);

        textView.setVisibility(View.VISIBLE);

        anim.start();
    }

    private void linear(){
        //動畫路徑設定(x1,x2,y1,y2)
        Animation am = new TranslateAnimation(10,200,10,500);

        //動畫開始到結束的時間，2秒
        am.setDuration( 2000 );

        // 動畫重覆次數 (-1表示一直重覆，0表示不重覆執行，所以只會執行一次)
        am.setRepeatCount( 0 );
        am.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        textView.startAnimation(am);
    }

    private void alpha(){
        Animation am = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        textView.startAnimation(am);
    }

}
