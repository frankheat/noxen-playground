package com.frankheat.noxen.playground;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity6 extends AppCompatActivity {

    public static final String DYNAMIC_RECEIVER_ACTION =
            "com.frankheat.noxen.playground.action.DYNAMIC_RECEIVER_TEST";

    private BroadcastReceiver dynamicReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_6);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dynamicReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getStringExtra("message");
                Toast.makeText(context, "Broadcast received: " + data, Toast.LENGTH_SHORT).show();
            }
        };

        ContextCompat.registerReceiver(
                this,
                dynamicReceiver,
                new IntentFilter(DYNAMIC_RECEIVER_ACTION),
                ContextCompat.RECEIVER_NOT_EXPORTED
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dynamicReceiver);
    }
}
