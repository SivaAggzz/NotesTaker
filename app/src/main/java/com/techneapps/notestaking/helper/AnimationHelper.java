package com.techneapps.notestaking.helper;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.os.Handler;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.techneapps.notestaking.R;

public class AnimationHelper {
    public static void rotateFABToDelete(FloatingActionButton floatingActionButton) {

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                new Handler().postDelayed(() -> floatingActionButton.setImageResource(R.drawable.ic_delete_white_24dp), 200);
                floatingActionButton
                        .setBackgroundTintList(ColorStateList
                                .valueOf(floatingActionButton.getContext()
                                        .getResources().getColor(R.color.md_red_600)));

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
        };

        ObjectAnimator rotation = ObjectAnimator
                .ofFloat(floatingActionButton, "rotation", 0f, 360f);
        rotation.setDuration(400);
        rotation.addListener(animatorListener);
        rotation.start();


    }

    public static void rotateFABToAdd(FloatingActionButton floatingActionButton) {

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                new Handler().postDelayed(() -> floatingActionButton.setImageResource(R.drawable.ic_add_white_24dp), 200);
                floatingActionButton
                        .setBackgroundTintList(ColorStateList
                                .valueOf(floatingActionButton.getContext()
                                        .getResources().getColor(R.color.colorAccent)));

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
        };
        ObjectAnimator rotation = ObjectAnimator
                .ofFloat(floatingActionButton, "rotation", 0f, 360f);
        rotation.setDuration(400);
        rotation.addListener(animatorListener);
        rotation.start();
    }

    public static void rotateFABToEdit(FloatingActionButton floatingActionButton) {

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                new Handler().postDelayed(() -> floatingActionButton.setImageResource(R.drawable.ic_edit_white_24dp), 200);
                floatingActionButton
                        .setBackgroundTintList(ColorStateList
                                .valueOf(floatingActionButton.getContext()
                                        .getResources().getColor(R.color.colorAccent)));

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
        };
        ObjectAnimator rotation = ObjectAnimator
                .ofFloat(floatingActionButton, "rotation", 0f, 360f);
        rotation.setDuration(400);
        rotation.addListener(animatorListener);
        rotation.start();
    }

}
