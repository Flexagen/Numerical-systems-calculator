package com.example.task4;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Help extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Button go_home = findViewById(R.id.button_home_4);
        go_home.setOnClickListener(view -> onBackPressed());

        TextView text = (TextView) findViewById(R.id.help_abz_1);
        text.setText("\t\t\t\t1. Для работы с калькулятором нужно ввести число в поле «Первое число».\n\n" +
                "\t\t\t\t2. В списке «Система счисления первого числа» необходимо указать систему счисления числа. " +
                "Если это не будет сделано, то по умолчанию будет выбрана 10-ая система счисления.\n\n" +
                "\t\t\t\t3. В поле «Второе число» нужно ввести число, если необходимо произвести умножение, сложение, " +
                "вычитание, деление. Если необходимо просто перевести первое число, то второе число не будет учитываться, " +
                "даже если оно введено.\n\n" +
                "\t\t\t\t4. Если второе число введено, то в поле «Система счисления второго числа» необходимо указать " +
                "систему счисления числа. Если это не будет сделано, то по умолчанию будет выбрана 10-ая система счисления.\n\n" +
                "\t\t\t\t5. В поле «Система счисления результата» необходимо указать систему счисления, в которой нужно " +
                "вывести результат. Если это не будет сделано, то по умолчанию будет выбрана 10-ая система счисления.\n\n" +
                "\t\t\t\t6. В поле «Количество цифр после запятой» нужно указать до какого разряда необходимо округлить " +
                "число при операции «Деление». Если это не указано, то по-умолчанию число будет округлено до 3-х знаков в дробной части.\n\n" +
                "\t\t\t\t7. Для работы калькулятора, когда введены все необходимые данные, необходимо нажать на кнопку с соответствующей операцией.\n\n\n\n");
    }
}