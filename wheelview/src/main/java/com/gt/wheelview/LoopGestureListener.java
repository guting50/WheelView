package com.gt.wheelview;

import android.view.MotionEvent;

final class LoopGestureListener extends android.view.GestureDetector.SimpleOnGestureListener {

    final LoopView loopView;

    LoopGestureListener(LoopView loopview) {
        loopView = loopview;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        loopView.scrollBy(velocityY);
        return true;
    }
}
