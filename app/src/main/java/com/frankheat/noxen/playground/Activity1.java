package com.frankheat.noxen.playground;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView intentInfo = findViewById(R.id.intentInfo);
        intentInfo.setText(dumpIntent(getIntent()));
    }

    private String dumpIntent(Intent intent) {
        if (intent == null) return "(null intent)";

        StringBuilder sb = new StringBuilder();

        sb.append("ACTION\n");
        sb.append(intent.getAction() != null ? intent.getAction() : "(none)");
        sb.append("\n\n");

        sb.append("DATA\n");
        sb.append(intent.getDataString() != null ? intent.getDataString() : "(none)");
        sb.append("\n\n");

        sb.append("CATEGORIES\n");
        if (intent.getCategories() != null && !intent.getCategories().isEmpty()) {
            for (String cat : intent.getCategories()) {
                sb.append("  ").append(cat).append("\n");
            }
        } else {
            sb.append("(none)\n");
        }
        sb.append("\n");

        sb.append("EXTRAS\n");
        Bundle extras = intent.getExtras();
        if (extras != null && !extras.isEmpty()) {
            for (String key : extras.keySet()) {
                Object value = extras.get(key);
                String type = value != null ? value.getClass().getSimpleName() : "null";
                sb.append("  ").append(key)
                  .append(" (").append(type).append(")")
                  .append(": ").append(value).append("\n");
            }
        } else {
            sb.append("(none)\n");
        }

        return sb.toString();
    }
}
