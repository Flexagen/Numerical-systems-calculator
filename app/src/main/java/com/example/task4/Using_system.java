package com.example.task4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Using_system extends AppCompatActivity {

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_system);

        Button go_home = findViewById(R.id.button_home_2);
        go_home.setOnClickListener(view -> onBackPressed());

        TextView support_tv = findViewById(R.id.using_system_tv);
        StringBuilder s = new StringBuilder();
        for (int i = 2; i < 95; i++) {
            s.append("----------------------------------------\n");
            s.append(i).append("-ричная система счисления\n");
            s.append("----------------------------------------\n");
            s.append("Используемые символы:\n");

            StringBuilder r = new StringBuilder("\n");
            for (int j = 0; j < i; j++) {
                if (j != i) {
                    if (j < 10) r.append((char) (j + 48)).append(", ");
                    if (j > 9 && j < 36) r.append((char) (j + 55)).append("(").append(j).append("), ");
                    if (j > 35 && j < 51) r.append((char) (j - 3)).append("(").append(j).append("), ");
                    if (j > 50 && j < 87) r.append((char) (j + 40)).append("(").append(j).append("), ");
                    if (j > 86) r.append((char) (j + 29)).append("(").append(j).append("), ");
                } else {
                    if (j < 10) r.append((char) (j + 48)).append(".");
                    if (j > 9 && j < 36) r.append((char) (j + 55)).append("(").append(j).append(").");
                    if (j > 35 && j < 51) r.append((char) (j - 3)).append("(").append(j).append(").");
                    if (j > 50 && j < 87) r.append((char) (j + 40)).append("(").append(j).append(").");
                    if (j > 86 && j < 94) r.append((char) (j + 29)).append("(").append(j).append(").");
                }
            }
            s.append(r).append("\n\n");
        }
        support_tv.setText(s+"\n\n\n");
    }
}