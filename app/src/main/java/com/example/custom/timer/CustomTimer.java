package com.example.custom.timer;

import android.os.CountDownTimer;

public class CustomTimer extends CountDownTimer {
    boolean state;
    private String TimerName;
    private TickListener mListener;

    public interface TickListener{
        void UpdateTime(String name, long millis);
        void onFinished(String name);
    }

    public CustomTimer(String name, TickListener listener, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.TimerName = name;
        this.state = false;
        this.mListener = listener;
    }

    public void onTick(final long millis) {
        if (mListener != null) {
            this.mListener.UpdateTime(this.TimerName, millis);
        }
    }

    public void onFinish() {
        if (mListener != null) {
            this.mListener.onFinished(this.TimerName);
        }
        setState(false);
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void stop() {
        setState(false);
        this.cancel();
    }

    public void run() {
        setState(true);
        this.start();
    }

    public void setTimername(String name) {
        this.TimerName = name;
    }

    public String getTimername() {
        return this.TimerName;
    }
}
