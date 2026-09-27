package dev.noxen.playground;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class UnresolvedPermissionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Broadcast received (unresolved-permission receiver)", Toast.LENGTH_SHORT).show();
    }
}
