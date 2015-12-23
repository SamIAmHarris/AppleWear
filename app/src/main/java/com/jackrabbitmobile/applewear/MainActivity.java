package com.jackrabbitmobile.applewear;

import android.content.ClipData;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends WearableActivity {

    View redView;
    View grayView;
    View purpleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();
        
        redView = findViewById(R.id.red_view);
        purpleView = findViewById(R.id.purple_view);
        grayView = findViewById(R.id.gray_view);
        grayView.setOnTouchListener(new AWDragTouchListener());

        purpleView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.i("Drag", "Drag started");
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        purpleView.setBackground(getResources().getDrawable(R.drawable.transparent_purple_circle));
                        Log.i("Drag", "Drag entered");
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        purpleView.setBackground(getResources().getDrawable(R.drawable.purple_circle_view));
                        Log.i("Drag", "Drag exited");
                        break;
                    case DragEvent.ACTION_DROP:
                        Log.i("Drag", "Drag drop");
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.i("Drag", "Drag ended");
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
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.i("Drag", "Drag ended");
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
}
