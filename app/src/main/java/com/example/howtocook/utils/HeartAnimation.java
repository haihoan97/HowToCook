package com.example.howtocook.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class HeartAnimation {

    public void animationBigger(View v) {

        ObjectAnimator animation1 = ObjectAnimator.ofFloat(v,
                "translationZ", -100f, 0f);
        animation1.setDuration(200);
        ObjectAnimator animationx = ObjectAnimator.ofFloat(v,
                "translationX", -50f, 0f);
        animation1.setDuration(200);
        ObjectAnimator animationy = ObjectAnimator.ofFloat(v,
                "translationY", -100f, 0f);
        animation1.setDuration(200);
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(v,
                "scaleX", 1.8f);
        animation2.setDuration(300);
        ObjectAnimator animation3 = ObjectAnimator.ofFloat(v,
                "scaleY", 1.8f);
        animation3.setDuration(300);
        ObjectAnimator animation4 = ObjectAnimator.ofFloat(v,
                "scaleX", 1.8f, 0.5f, 1f);
        animation4.setDuration(300);
        ObjectAnimator animation5 = ObjectAnimator.ofFloat(v,
                "scaleY", 1.8f, 0.5f, 1f);
        animation5.setDuration(300);

        float dest = 360;
        if (v.getRotation() == 360) {
            dest = 0;
        }
        ObjectAnimator rotation = ObjectAnimator.ofFloat(v,
                "rotation", dest);
        animation1.setDuration(300);
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.play(animationx).with(animation2).with(animation3).before(animation4).before(animation5);
        animatorSet.start();
    }
}
