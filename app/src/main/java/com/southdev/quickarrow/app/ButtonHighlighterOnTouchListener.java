package com.southdev.quickarrow.app;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by igorvelho on 18/03/14.
 */
public class ButtonHighlighterOnTouchListener implements View.OnTouchListener {

    ImageView imageView;
    int kind;


    public ButtonHighlighterOnTouchListener(final ImageView imageView, final int kind) {
        super();
        this.imageView = imageView;
        this.kind = kind;
    }


    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        if (imageView != null) {
            if(kind == R.drawable.up) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    imageView.setBackgroundResource(R.drawable.up_clicked);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageView.setBackgroundResource(R.drawable.up);
                }
            }
            if(kind == R.drawable.down) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    imageView.setBackgroundResource(R.drawable.down_clicked);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageView.setBackgroundResource(R.drawable.down);
                }
            }
            if(kind == R.drawable.left) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    imageView.setBackgroundResource(R.drawable.left_clicked);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageView.setBackgroundResource(R.drawable.left);
                }
            }
            if(kind == R.drawable.right) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    imageView.setBackgroundResource(R.drawable.right_clicked);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageView.setBackgroundResource(R.drawable.right);
                }
            }
        }
        return false;
    }

}
