package com.jackrabbitmobile.applewear;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    View redView;
    View grayView;
    View purpleView;

    TextView whatColorTV;
    TextView isAnAppleTV;

    View centerView;
    ImageView checkmarkView;

    TextView correctTV;
    TextView redTV;

    RelativeLayout mainBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mainBackground = (RelativeLayout) findViewById(R.id.main_background);

        centerView = findViewById(R.id.center_view);
        checkmarkView = (ImageView) findViewById(R.id.checkmark_iv);

        correctTV = (TextView) findViewById(R.id.correct_tv);
        redTV = (TextView) findViewById(R.id.red_tv);

        whatColorTV = (TextView) findViewById(R.id.what_color_tv);
        isAnAppleTV = (TextView) findViewById(R.id.is_an_apple_tv);

        redView = findViewById(R.id.red_view);
        purpleView = findViewById(R.id.purple_view);
        grayView = findViewById(R.id.gray_view);
        grayView.setOnTouchListener(new AWDragTouchListener());

        purpleView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        purpleView.setBackground(getResources().getDrawable(R.drawable.transparent_purple_circle));
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        purpleView.setBackground(getResources().getDrawable(R.drawable.purple_circle_view));
                        break;
                    case DragEvent.ACTION_DROP:
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                    default:
                        break;
                }
                return true;
            }
        });

        redView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.i("Drag", "Drag started");
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        redView.setBackground(getResources().getDrawable(R.drawable.transparent_red_circle));
                        Log.i("Drag", "Drag entered");
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        redView.setBackground(getResources().getDrawable(R.drawable.red_circle_view));
                        Log.i("Drag", "Drag exited");
                        break;
                    case DragEvent.ACTION_DROP:
                        Log.i("Drag", "Drag drop");
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:{
                        v.post(new Runnable (){
                            public void run() {
                                correctAnswerViewChanges();
                            }
                        });
                        break;
                    }
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
    }

    @Override
    public void onExitAmbient() {
        super.onExitAmbient();
    }

    private final class AWDragTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    private void correctAnswerViewChanges() {
        fadeOutViewAnimation(purpleView);
        fadeOutViewAnimation(whatColorTV);
        fadeOutViewAnimation(isAnAppleTV);

        //redViewAnimationWithDualRotate(redView);
        //redViewAnimationWithBounce(redView);
        //redViewAnimationWithOvershoot(redView);
        redViewAnimationWithSingleRotate(redView);

        fadeInViewAnimation(correctTV);
        fadeInViewAnimation(redTV);
        fadeInViewAnimation(checkmarkView);
    }

    public void fadeInViewAnimation(View v) {
        v.setVisibility(View.VISIBLE);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(v, "alpha", 0f,
                100f);
        fadeIn.setDuration(5000);
        fadeIn.start();
    }

    public void fadeOutViewAnimation(View v) {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(v, "alpha",
                0f);
        fadeIn.setDuration(400);
        fadeIn.start();
    }

    public void redViewAnimationWithDualRotate(View v) {
        v.setBackground(getResources().getDrawable(R.drawable.red_circle_view));
        float parentCenterX = centerView.getX();
        float parentCenterY = centerView.getY();

        ObjectAnimator translateX = ObjectAnimator.ofFloat(v, View.TRANSLATION_X,
                parentCenterX - 32);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(v, View.TRANSLATION_Y,
                -(parentCenterY-85));
        ObjectAnimator rotateX = ObjectAnimator.ofFloat(v, View.ROTATION_X,
                360);
        ObjectAnimator rotateY = ObjectAnimator.ofFloat(v, View.ROTATION_Y,
                360);

        translateX.setDuration(500);
        translateY.setDuration(500);
        rotateX.setDuration(500);
        rotateY.setDuration(500);

        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(translateX, translateY, rotateX, rotateY);
        set.start();
    }


    public void redViewAnimationWithSingleRotate(View v) {
        v.setBackground(getResources().getDrawable(R.drawable.red_circle_view));
        float parentCenterX = centerView.getX();
        float parentCenterY = centerView.getY();

        ObjectAnimator translateX = ObjectAnimator.ofFloat(v, View.TRANSLATION_X,
                parentCenterX - 32);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(v, View.TRANSLATION_Y,
                -(parentCenterY-85));
        ObjectAnimator rotateX = ObjectAnimator.ofFloat(v, View.ROTATION_Y,
                360);


        translateX.setDuration(500);
        translateY.setDuration(500);
        rotateX.setDuration(500);

        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(translateX, translateY, rotateX);
        set.start();
    }

    public void redViewAnimationWithOvershoot(View v) {
        v.setBackground(getResources().getDrawable(R.drawable.red_circle_view));
        float parentCenterX = centerView.getX();
        float parentCenterY = centerView.getY();

        ObjectAnimator translateX = ObjectAnimator.ofFloat(v, View.TRANSLATION_X,
                parentCenterX - 32);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(v, View.TRANSLATION_Y,
                -(parentCenterY-85));

        translateX.setDuration(500);
        translateY.setDuration(500);

        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new AnticipateOvershootInterpolator());
        set.playTogether(translateX, translateY);
        set.start();
    }

    public void redViewAnimationWithBounce(View v) {
        v.setBackground(getResources().getDrawable(R.drawable.red_circle_view));
        float parentCenterX = centerView.getX();
        float parentCenterY = centerView.getY();

        ObjectAnimator translateX = ObjectAnimator.ofFloat(v, View.TRANSLATION_X,
                parentCenterX - 32);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(v, View.TRANSLATION_Y,
                -(parentCenterY-85));

        translateX.setDuration(500);
        translateY.setDuration(500);

        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new BounceInterpolator());
        set.playTogether(translateX, translateY);
        set.start();
    }

}
