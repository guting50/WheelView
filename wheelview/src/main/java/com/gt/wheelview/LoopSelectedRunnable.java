package com.gt.wheelview;


final class LoopSelectedRunnable implements Runnable {
    final LoopView loopView;

    LoopSelectedRunnable(LoopView loopview) {
        loopView = loopview;
    }

    @Override
    public final void run() {
        loopView.onItemSelectedListener.onItemSelected(loopView.getSelectedItem());
    }
}
