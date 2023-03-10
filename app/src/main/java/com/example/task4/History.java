package com.example.task4;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class History extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setSupportActionBar(findViewById(R.id.history_toolbar));

        Button go_home = findViewById(R.id.button_home);
        go_home.setOnClickListener(view -> onBackPressed());

        TextView text = (TextView) findViewById(R.id.gl_1);
        text.setText("Глава 1. Введение в системы счисления");
        text = (TextView) findViewById(R.id.abz_1);
        text.setText("\t\t\t\tТема «Системы счисления» входит в школьную программу по информатике и начинает изучаться и активно использоваться " +
                "учениками с 8 по 11 класс. Она очень обширна и сложна для некоторых ребят, и, чтобы они могли себя проверить, им пригодиться" +
                " многофункциональный калькулятор систем счисления.\n\n\t\t\t\tРаньше системы счисления использовались даже в телеграфии. " +
                "Телеграфф представлял собой комбинацию двух устройств: передающей части, которая служит для перевода буквы в " +
                "соответствующую систему импульсов, посылаемых по линии связи, и приемного устройства, которое по заданной комбинации " +
                "импульсов печатает на телеграфной ленте соответствующую букву. Буквы кодировались пятёрками нулей и единиц (использовалась " +
                "2-ая система счисления), что было очень удобно для превращения двоичного числа в систему электрических сигналов. Любой " +
                "калькулятор – это предельно узкоспециализированная расчётная электроника.\n");
        text = (TextView) findViewById(R.id.gl_2);
        text.setText("Глава 2. Виды систем счисления");
        text = (TextView) findViewById(R.id.abz_2);
        text.setText("\t\t\t\tСистема счисления - это совокупность правил записи чисел посредством конечного набора символов " +
                "(цифр). Они бывают:\n\n" +
                "-) непозиционными (В этих системах значение цифры не зависит от ее позиции — положения в записи" +
                "числа. Например, унарная, римская, древнерусская)\n-) позиционными (значение цифры зависит от позиции).\n");
        text = (TextView) findViewById(R.id.abz_3);
        text.setText("Древнеегипетская система счисления (непозиционная)");
        text = (TextView) findViewById(R.id.abz_4);
        text.setText("Вавилонская система счисления (позиционная с основанием 60)");
        text = (TextView) findViewById(R.id.abz_5);
        text.setText("Система счисления майя (непозиционная с основанием 20)\n");
        text = (TextView) findViewById(R.id.gl_3);
        text.setText("Глава 3. Особенности позиционной системы счисления");
        text = (TextView) findViewById(R.id.abz_6);
        text.setText("\t\t\t\tВ позиционных системах " +
                "счисления вес каждой цифры изменяется в зависимости от ее позиции в после-" +
                "довательности цифр, которые изображают число.\n\n\t\t\t\tКаждая позиционная система характеризуется своим " +
                "основанием. Основание системы счисления - количество различных знаков либо символов, используемых в этой" +
                "системе. Количество цифр всегда на единицу меньше основания системы счисления." +
                "Основанием позиционной системы счисления может служить любое натуральное число q > 1." +
                "\n\n\t\t\t\tАлфавитом произвольной позиционной системы счисления с основанием q служат числа 0, 1, ..., q-1, " +
                "каждое из которых может быть записано с помощью одного уникального символа; младшей цифрой всегда является 0." +
                "В позиционной системе счисления с основанием q любое число может быть представлено в виде " +
                "формулы развёрнутой записи числа:");
        text = (TextView) findViewById(R.id.abz_7);
        text.setText("q  - снование системы счисления;\n" +
                "a - цифры, принадлежащие алфавиту данной системы счисления; \n" +
                "n  - количество целых разрядов числа;\n" +
                "m - количество дробных разрядов числа;\n" +
                "qi  - «вес» i-то разряда.\n");
        text = (TextView) findViewById(R.id.gl_4);
        text.setText("Глава 4. Правила перевода в различные системы счисления:");
        text = (TextView) findViewById(R.id.abz_8);
        text.setText("\t\t\t\t1.  Для перевода целого числа А из десятичной системы счисления в систему счисления с основанием q " +
                "необходимо число А делить на основание q до получения целого остатка, меньшего q. Полученное " +
                "частное едует снова делить на q до получения целого остатка, меньшего q, и т.д. до тех пор, пока " +
                "последнее частное не будет меньше q. Тогда десятичное число А в системе счисления с основанием q " +
                "следует записать в виде последовательности остатков деления в порядке, обратном их получению, причем " +
                "старший разряд дает последнее частное.");
        text = (TextView) findViewById(R.id.abz_9);
        text.setText("\t\t\t\t2.  Для перевода нецелого сила A, A<0, из десятичной системы счисления в систему счисления с основа-" +
                "нием q следует умножить это число на основание q. Целая часть произведения будет первой цифрой " +
                "числа в системе счисления с основанием q. Затем, отбросив целую часть, снова умножить на основание " +
                "q и т.д. до тех пор, пока не будет получено требуемое число разрядов в новой системе счисления или " +
                "пока перевод не закончится." +
                "\n\n" +
                "\t\t\t\t3.  Для перевода нецелого числа A, A>0, из десятичной системы счисления переводятся в два приема: " +
                "отдельно целая часть по своему правилу и отдельно дробная часть по своему правилу. Затем записы" +
                "вается общий результат, у которого дробная часть отделяется запятой.");
        text = (TextView) findViewById(R.id.abz_10);
        text.setText("Для перевода числа A из системы счисления q в систему счисления q1, q<>10 и q1<>10, нужно сначала " +
                "число A перевести в десятичную систему счислению, а затем в систему счисления с основанием q1. Если " +
                "число A – нецелое, то оно переводиться по частям, сначала переводится целая часть числа, затем переводится его дробная часть.\n");
        text = (TextView) findViewById(R.id.gl_5);
        text.setText("Глава 5. Арифметические операции в позиционных системах счисления");
        text = (TextView) findViewById(R.id.abz_11);
        text.setText("\t\t\t\tПри выполнении любых арифметических операций над числами, представленными в разных системах " +
                "счисления, следует предварительно перевести их в одну и ту же систему. Арифметические операции во " +
                "всех позиционных системах счисления выполняются по одним и тем же хорошо известным правилам.\n\n" +
                "\t\t1.  Если сумма складываемых цифр больше или равна основанию системы счисления, то единица пере-\n" +
                "носится в следующий слева разряд.\n\n" +
                "\t\t2.  Вычитание осуществляется по тем же правилам, что и в десятичной системе счисления.\n\n" +
                "\t\t3.  При вычитании из меньшего числа большего производится заём из старшего разряда.");
        text = (TextView) findViewById(R.id.abz_12);
        text.setText("\t\t4.  Выполняя умножение многозначных чисел в различных позиционных системах счисления, можно ис" +
                "пользовать обычный алгоритм перемножения чисел в столбик, но при этом результаты перемножения и " +
                "сложения однозначных чисел необходимо заимствовать из соответствующих рассматриваемой системе " +
                "таблиц умножения и сложения.\n\n" +
                "\t\t5.  Умножение многоразрядных чисел в различных позиционных системах счисления происходит по " +
                "обычной схеме, применяемой в десятичной системе счисления, с последовательным умножением мно" +
                "жимого на очередную цифру множителя.\n\n\n\n\n\n\n");
    }
}