package com.frankheat.noxen.playground;

import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class Service1 extends WrapService {
    public Service1() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        Toast.makeText(this, "onStartCommand() executed", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}