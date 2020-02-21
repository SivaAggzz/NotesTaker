package com.techneapps.notestaking.helper;

import android.content.res.ColorStateList;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.techneapps.notestaking.R;

public class AnimationHelper {
    public static void rotateFABForward(FloatingActionButton floatingActionButton) {

        ViewPropertyAnimatorListener listener = new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(View view) {
                floatingActionButton
                        .setBackgroundTintList(ColorStateList
                                .valueOf(floatingActionButton.getContext()
                                        .getResources().getColor(R.color.md_red_600)));
            }

            @Override
            public void onAnimationEnd(View view) {

            }

            @Override
            public void onAnimationCancel(View view) {

            }
        };
        ViewCompat.animate(floatingActionButton)
                .rotation(135.0F)
                .withLayer()
                .setDuration(400L)
                .setInterpolator(new OvershootInterpolator())
                .setListener(listener)
                .start();

    }

    public static void rotateFABBackward(FloatingActionButton floatingActionButton) {
        ViewPropertyAnimatorListener listener = new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(View view) {
                floatingActionButton
                        .setBackgroundTintList(ColorStateList
                                .valueOf(floatingActionButton.getContext()
                                        .getResources().getColor(R.color.colorAccent)));
            }

            @Override
            public void onAnimationEnd(View view) {

            }

            @Override
            public void onAnimationCancel(View view) {

            }
        };
        ViewCompat.animate(floatingActionButton)
                .rotation(0.0F)
                .withLayer()
                .setDuration(400L)
                .setInterpolator(new OvershootInterpolator())
                .setListener(listener)
                .start();
    }

}
