package com.frankheat.noxen.playground;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setClickListener(R.id.btnStartActivityWithExtras, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Activity1");

            Bundle nestedBundle = new Bundle();
            nestedBundle.putString("bundle_key", "Inside a bundle");

            Intent nestedIntent = new Intent();
            nestedIntent.putExtra("a", "a");

            intent.putExtra("intent", nestedIntent);
            intent.putExtra("my_parcelable", Uri.parse("https://android.com"));      // Parcelable
            intent.putExtra("my_byte", (byte) 1);                  // byte
            intent.putExtra("my_charsequence", (CharSequence) "Hello"); // CharSequence
            intent.putExtra("my_int", 42);                         // int
            intent.putExtra("my_bundle", nestedBundle);            // Bundle
            intent.putExtra("my_double", 3.14159);                 // double
            intent.putExtra("my_boolean", true);                   // boolean
            intent.putExtra("my_string", "test string");           // String
            intent.putExtra("my_long", 100L);                      // long
            intent.putExtra("my_char", 'A');                       // char
            //intent.putExtra("my_serializable", serializableMap);   // Serializable
            intent.putExtra("my_float", 1.2f);                     // float
            intent.putExtra("my_short", (short) 5);                // short

            startActivity(intent);
        });
        setClickListener(R.id.btnStartActivityForResultWithOptions, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Activity2");
            startActivityForResult(intent, 100, null);
        });
        setClickListener(R.id.btnStartActivityForResultCallback, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Activity4");
            startActivityForResult(intent, 100);
        });
        setClickListener(R.id.btnStartActivitySingleTaskTwice, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Activity5");
            intent.putExtra("Number", "First");
            startActivity(intent);
            new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                Intent intent2 = new Intent();
                intent2.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Activity5");
                intent2.putExtra("Number", "Second");
                startActivity(intent2);
            }, 3000);
        });
        setClickListener(R.id.btnSendBroadcast, v -> {
           Intent intent = new Intent();
           intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Receiver1");
           intent.putExtra("test", "test");
           intent.addCategory(Intent.CATEGORY_DEFAULT);
           intent.addFlags(1);
           sendBroadcast(intent);
        });
        setClickListener(R.id.btnSendBroadcastWithPermission, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Receiver2");
            sendBroadcast(intent, "com.frankheat.noxen.playground.permission.MY_CUSTOM_PERMISSION");
        });
        setClickListener(R.id.btnDynamicReceiverBroadcast, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Activity6");
            startActivity(intent);

            new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                Intent intent2 = new Intent(Activity6.DYNAMIC_RECEIVER_ACTION);
                intent2.putExtra("message", "Hello from MainActivity!");
                sendBroadcast(intent2);
            }, 3000);

        });
        setClickListener(R.id.btnSendOrderedBroadcast, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Receiver1");
            sendOrderedBroadcast(intent, null);
        });
        setClickListener(R.id.btnSendOrderedBroadcastWithResultReceiver, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Receiver1");
            sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Toast.makeText(context, "Broadcast received in MainActivity", Toast.LENGTH_SHORT).show();
                }
            }, null, 0, null, null);
        });
        setClickListener(R.id.btnSendOrderedBroadcastWithReceiverPermissionApi30, v -> {
            if (android.os.Build.VERSION.SDK_INT >= 30) {
                Intent intent = new Intent();
                intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Receiver1");
                sendOrderedBroadcast(intent, (String) null, (String) null, new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        Toast.makeText(context, "Broadcast received in MainActivity", Toast.LENGTH_SHORT).show();
                    }
                }, null, 0, null, null);
            }
        });
        setClickListener(R.id.btnSendOrderedBroadcastWithFlagsApi30, v -> {
            if (android.os.Build.VERSION.SDK_INT >= 30) {
                Intent intent = new Intent();
                intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Receiver1");
                sendOrderedBroadcast(intent, 0, null, null, new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        Toast.makeText(context, "Broadcast received in MainActivity", Toast.LENGTH_SHORT).show();
                    }
                }, null, null, null, null);
            }
        });
        setClickListener(R.id.btnSendOrderedBroadcastWithOptionsApi34, v -> {
            if (android.os.Build.VERSION.SDK_INT >= 34) {
                Intent intent = new Intent();
                intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Receiver1");
                Bundle options = new Bundle();
                sendOrderedBroadcast(intent, null, options);
            }
        });
        setClickListener(R.id.btnSendOrderedBroadcastWithOptionsAndReceiverApi34, v -> {
            if (android.os.Build.VERSION.SDK_INT >= 34) {
                Intent intent = new Intent();
                intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Receiver1");
                Bundle options = new Bundle();
                sendOrderedBroadcast(intent, null, options, new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        Toast.makeText(context, "Broadcast received in MainActivity", Toast.LENGTH_SHORT).show();
                    }
                }, null, 0, null, null);
            }
        });
        setClickListener(R.id.btnStartService, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Service1");
            startService(intent);
        });
        setClickListener(R.id.btnStartForegroundService, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Service1");
            startForegroundService(intent);
        });
        setClickListener(R.id.btnBindService, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Service1");
            bindService(intent, new android.content.ServiceConnection() {
                @Override public void onServiceConnected(android.content.ComponentName n, android.os.IBinder s) {}
                @Override public void onServiceDisconnected(android.content.ComponentName n) {}
            }, BIND_AUTO_CREATE);
        });
        setClickListener(R.id.btnBindServiceWithExecutorApi29, v -> {
            if (android.os.Build.VERSION.SDK_INT >= 29) {
                Intent intent = new Intent();
                intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Service1");
                bindService(intent, BIND_AUTO_CREATE, getMainExecutor(), new android.content.ServiceConnection() {
                    @Override public void onServiceConnected(android.content.ComponentName n, android.os.IBinder s) {}
                    @Override public void onServiceDisconnected(android.content.ComponentName n) {}
                });
            }
        });
        setClickListener(R.id.btnBindServiceWithFlagsApi34, v -> {
            if (android.os.Build.VERSION.SDK_INT >= 34) {
                Intent intent = new Intent();
                intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Service1");
                bindService(intent, new android.content.ServiceConnection() {
                    @Override public void onServiceConnected(android.content.ComponentName n, android.os.IBinder s) {}
                    @Override public void onServiceDisconnected(android.content.ComponentName n) {}
                }, Context.BindServiceFlags.of(BIND_AUTO_CREATE));
            }
        });
        setClickListener(R.id.btnBindServiceWithFlagsExecutorApi34, v -> {
            if (android.os.Build.VERSION.SDK_INT >= 34) {
                Intent intent = new Intent();
                intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Service1");
                bindService(intent, Context.BindServiceFlags.of(BIND_AUTO_CREATE), getMainExecutor(), new android.content.ServiceConnection() {
                    @Override public void onServiceConnected(android.content.ComponentName n, android.os.IBinder s) {}
                    @Override public void onServiceDisconnected(android.content.ComponentName n) {}
                });
            }
        });
        setClickListener(R.id.btnSendBroadcastWithOptionsApi34, v -> {
            if (android.os.Build.VERSION.SDK_INT >= 34) {
                Intent intent = new Intent();
                intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Receiver1");
                intent.putExtra("test", "test");
                Bundle options = new Bundle();
                options.putInt("delivery_group_policy", 0);
                sendBroadcast(intent, null, options);
            }
        });
        setClickListener(R.id.btnStartActivityForSetResult, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Activity7");
            startActivityForResult(intent, 100);
        });
        setClickListener(R.id.btnContextStartActivity, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Activity2");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
        });
        setClickListener(R.id.btnContextStartActivityWithBundle, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Activity2");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            bundle.putString("source", "ContextWrapper");
            getApplicationContext().startActivity(intent, bundle);
        });
        setClickListener(R.id.btnPendingIntentActivity, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Activity2");
            intent.putExtra("pending_source", "getActivity");
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        });
        setClickListener(R.id.btnPendingIntentBroadcast, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Receiver1");
            intent.putExtra("pending_source", "getBroadcast");
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        });
        setClickListener(R.id.btnPendingIntentService, v -> {
            Intent intent = new Intent();
            intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Service1");
            intent.putExtra("pending_source", "getService");
            PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        });
        setClickListener(R.id.btnAttackSurfaceExplicitExportedActivity, v -> {
            // Explicit intent: ComponentName is set → Noxen shows "Explicit"
            // Activity8 is exported → Noxen shows "Exported" on getIntent()
            startActivity(new Intent(this, Activity8.class));
        });
        setClickListener(R.id.btnAttackSurfaceImplicitExportedActivity, v -> {
            // Implicit intent: no ComponentName, resolved by Android → Noxen shows "Implicit"
            // Activity8 matches the filter and is exported → Noxen shows "Exported" on getIntent()
            startActivity(new Intent("com.frankheat.noxen.playground.action.ATTACK_TEST"));
        });
        setClickListener(R.id.btnAttackSurfaceExplicitNotExportedActivity, v -> {
            // Explicit intent: ComponentName is set → Noxen shows "Explicit"
            // Activity9 is not exported → Noxen shows "Not exported" on getIntent()
            startActivity(new Intent(this, Activity9.class));
        });
        setClickListener(R.id.btnConcurrentBroadcasts2, v -> fireConcurrentBroadcasts(2));
        setClickListener(R.id.btnConcurrentBroadcasts5, v -> fireConcurrentBroadcasts(5));

        int api = android.os.Build.VERSION.SDK_INT;
        disableViewsIfUnsupported(api, 29, R.id.btnBindServiceWithExecutorApi29);
        disableViewsIfUnsupported(
                api,
                30,
                R.id.btnSendOrderedBroadcastWithReceiverPermissionApi30,
                R.id.btnSendOrderedBroadcastWithFlagsApi30
        );
        disableViewsIfUnsupported(
                api,
                34,
                R.id.btnSendBroadcastWithOptionsApi34,
                R.id.btnSendOrderedBroadcastWithOptionsApi34,
                R.id.btnSendOrderedBroadcastWithOptionsAndReceiverApi34,
                R.id.btnBindServiceWithFlagsApi34,
                R.id.btnBindServiceWithFlagsExecutorApi34
        );
    }

    /**
     * Fires `count` sendBroadcast calls simultaneously from `count` separate threads.
     * A CountDownLatch synchronises all threads so they call sendBroadcast at the
     * same time, producing genuinely concurrent hook events in the Frida agent.
     * Each intent carries "concurrent_index" (1-based) and "concurrent_total" extras
     * so the researcher can identify each member of the group in the proxy.
     */
    private void fireConcurrentBroadcasts(int count) {
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            final int index = i + 1;
            new Thread(() -> {
                latch.countDown();
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                Intent intent = new Intent();
                intent.setClassName("com.frankheat.noxen.playground", "com.frankheat.noxen.playground.Receiver1");
                intent.putExtra("concurrent_index", index);
                intent.putExtra("concurrent_total", count);
                sendBroadcast(intent);
            }).start();
        }
    }

    private void setClickListener(int viewId, View.OnClickListener listener) {
        findViewById(viewId).setOnClickListener(listener);
    }

    private void disableViewsIfUnsupported(int currentApi, int requiredApi, int... viewIds) {
        if (currentApi >= requiredApi) {
            return;
        }
        for (int viewId : viewIds) {
            View view = findViewById(viewId);
            view.setEnabled(false);
            view.setAlpha(0.4f);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
