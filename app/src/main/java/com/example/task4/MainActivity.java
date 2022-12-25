package com.example.task4;

import static com.example.task4.R.id.first_num_ss;
import static com.example.task4.R.id.octopus;
import static com.example.task4.R.id.res_num_ss;
import static com.example.task4.R.id.second_num_ss;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // Индекс заднего фона
    int back = 0;
    HashMap<Integer, String> background = new HashMap<>();
    // Медиаплеер музыки на фоне
    MediaPlayer music = new MediaPlayer();
    HashMap<Integer, String> sounds = new HashMap<>();
    // Указатель на текущее место в композиции, Индекс текущей песни
    int music_length = 0, music_index;
    // Флаг для выбора мотивирующей картинки
    boolean mem_flag = false;
    // Флаг готовности части вычислений
    boolean[] done = {true, true, true, true};
    Dialog dialog;
    RelativeLayout rLayout;
    // Время вычислений
    final long[] time = {0};
    Thread timer;
    // Перевод целой части, дробной части
    Thread ceil_part, fractional_part;
    // Массив результата. 0 -  целая часть первого числа, 1 - дробная первого, 2 - целая часть второго, 3 - дробная
    String[] res_n = new String[4];
    // Ошибка (ввода, выбора системы счисления или размера числа)
    int error = 0;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Музыка на заднем фоне
        for (int i = 0; i < 9; i++)
            sounds.put(i, "music_"+(i+1));
        music_index = new Random().nextInt(sounds.size());
        set_music();


        // Задний фон приложения
        for (int i = 0; i < 9; i++)
            background.put(i, "back"+(i+1));
        back = new Random().nextInt(background.size());
        change_back();
        set_back();

        /*
         * Меню
         */
        ImageButton menu_button = findViewById(R.id.menu_button);
        menu_button.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(this, view, Gravity.START);
            popup.getMenu().add(Menu.NONE, 0, Menu.NONE, "Инструкция по работе");
            popup.getMenu().add(Menu.NONE, 1, Menu.NONE, "История систем счисления");
            popup.getMenu().add(Menu.NONE, 2, Menu.NONE, "Поддерживаемые системы счисления");
            popup.show();
            final Intent[] new_activity = new Intent[2];
            popup.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case 0:
                        new_activity[0] = new Intent(MainActivity.this, Help.class);
                        MainActivity.this.startActivity(new_activity[0]);
                        MainActivity.this.onRestart();
                        break;
                    case 1:
                        new_activity[1] = new Intent(MainActivity.this, History.class);
                        MainActivity.this.startActivity(new_activity[1]);
                        MainActivity.this.onRestart();
                        break;
                    case 2:
                        new_activity[1] = new Intent(MainActivity.this, Using_system.class);
                        MainActivity.this.startActivity(new_activity[1]);
                        MainActivity.this.onRestart();
                }
                return true;
            });
        });

        /*
         * Настройки
         */
        ImageButton settings_button = findViewById(R.id.settings_button);
        settings_button.setOnClickListener(view -> {
            this.onStop();
            Intent new_activity = new Intent(MainActivity.this, SettingsActivity.class);
            MainActivity.this.startActivity(new_activity);
            this.onRestart();
        });


        /*
         *  Перевод первого числа
         */
        ImageButton one_per = findViewById(R.id.button_one_p);
        one_per.setOnClickListener(view -> work_with_numbers(-1));

        /*
         * Сумма двух чисел
         */
        ImageButton summ = findViewById(R.id.button_plus);
        summ.setOnClickListener(view -> work_with_numbers(0));

        /*
         *  Разность двух чисел
         */
        ImageButton minus = findViewById(R.id.button_minus);
        minus.setOnClickListener(view -> work_with_numbers(1));

        /*
         * Частное двух чисел
         */
        ImageButton division = findViewById(R.id.button_division);
        division.setOnClickListener(view -> work_with_numbers(2));

        /*
         * Произведение двух чисел
         */
        ImageButton multiply = findViewById(R.id.button_multiply);
        multiply.setOnClickListener(view -> work_with_numbers(3));

        /*
         * Возведение первого числа в степень второго числа
         */
        ImageButton pow = findViewById(R.id.button_pow);
        pow.setOnClickListener(view -> work_with_numbers(4));

        /*
         * Преднастройки калькулятора
         */
        EditText first_ss = findViewById(first_num_ss);
        first_ss.setText("10");
        EditText second_ss = findViewById(second_num_ss);
        second_ss.setText("10");
        EditText res_ss = findViewById(res_num_ss);
        res_ss.setText("10");
    }

    void work_with_numbers(int mode){
        // Обрабатываем полученные значения
        EditText first_num = findViewById(R.id.first_num_value);
        EditText first_ss = findViewById(first_num_ss);
        EditText second_num = findViewById(R.id.second_num_value);
        EditText second_ss = findViewById(second_num_ss);
        EditText res_ss = findViewById(res_num_ss);

        // По умолчанию систему счисления - 10
        int first_s, second_s = 10, res_s;
        String[] first_n, second_n;
        String first_ceil, first_fractional="0", second_ceil="0", second_fractional="0";

        first_n = first_num.getText().toString().replace(" ", "").split(",", 2);
        if (first_n[0].isEmpty()) first_n[0] = "0";

        done[0] = false;
        first_ceil = first_n[0];
        if (first_n.length == 2 && first_n[1].length() > 0) { first_fractional = first_n[1]; done[1] = false;}
        first_s = Integer.parseInt(first_ss.getText().toString());

        if (mode != -1) {
            second_n = second_num.getText().toString().replace(" ", "").split(",", 2);
            if (second_n[0].isEmpty()) second_n[0] = "0";
            done[2] = false;
            second_ceil = second_n[0];
            if (second_n.length == 2 && second_n[1].length() > 0) { second_fractional = second_n[1]; done[3] = false;}
            second_s = Integer.parseInt(second_ss.getText().toString());
        }
        res_s = Integer.parseInt(res_ss.getText().toString());

        if (res_s > 1) {
            set_motivating_pic(create_dialog());
            dialog.show();

            Log.d("BETA", first_ceil + "|" + first_fractional + "|" + second_ceil + "|" + second_fractional);
            if (!done[0]) ceil_part(first_ceil, first_s, 0);
            if (!done[1]) fractional_part(first_fractional, first_s, 1);
            if (!done[2]) ceil_part(second_ceil, second_s, 2);
            if (!done[3]) fractional_part(second_fractional, second_s, 3);

            timer(first_ceil + "." + first_fractional, first_s, second_ceil + "." + second_fractional, second_s, res_s, mode);
            button_interrupt();
        }
    }

    void ceil_part(String First_n, int First_s, int index){
        /*
         * Переводич целую часть в фоновом потоке
         */
        ceil_part = new Thread(() -> {
            res_n[index]= new Perevod().ceil_to_10(First_n, First_s);
            if (res_n[index].equals("Неправильный ввод числа или его системы счисления!") ||
                    res_n[index].equals("Слишком большое число. Такие пока что не поддерживаются")) error = index+1;
            done[index] = true;
        });
        ceil_part.start();
    }

    void fractional_part(String First_n, int First_s, int index) {
        /*
         * Переводич дробную часть в фоновом потоке
         */
        fractional_part = new Thread(() -> {
            res_n[index]= new Perevod().fractional_to_10(First_n, First_s);
            if (res_n[index].equals("Неправильный ввод числа или его системы счисления!") ||
                    res_n[index].equals("Слишком большое число. Такие пока что не поддерживаются")) error = index+1;
            done[index] = true;
        });
        fractional_part.start();
    }

    @SuppressLint("SetTextI18n")
    void timer(String First_n, int First_s, String Second_n, int Second_s, int Res_s, int mode){
        /*
         * Время вычислений
         */
        timer = new Thread(() -> {
            try {
                TextView time_tv = rLayout.findViewById(R.id.time_tv);

                while (!done[0] || !done[1] || !done[2] || !done[3] || time[0] < 4) {
                    if (time[0] < 60)  time_tv.setText(time[0]+" секунд");
                    else time_tv.setText(time[0]/60+" минут "+time[0]%60+" секунд");
                    Thread.sleep(1000);

                    new Thread(() -> time[0]++).start();
                }

                print_result(First_n, First_s, Second_n, Second_s, Res_s, mode);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        timer.start();
    }

    void button_interrupt(){
        /*
         * Прерывание вычислений
         */
        Button stop_button = rLayout.findViewById(R.id.button_stop);
        stop_button.setOnClickListener(view -> {
            dialog.dismiss();
            if (timer != null) timer.interrupt();
            if (ceil_part != null) ceil_part.interrupt();
            if (fractional_part != null) fractional_part.interrupt();
            time[0] = 0;
            done = new boolean[] {true, true, true, true};
            res_n = new String[]{"0", "0", "0", null};
            error = 0;
        });
    }

    View create_dialog(){
        @SuppressLint("InflateParams") View dialog_view = getLayoutInflater().inflate(R.layout.long_translation, null);
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(dialog_view);
        dialog.setCanceledOnTouchOutside(false);
        rLayout = dialog_view.findViewById(R.id.rLayout);
        return dialog_view;
    }

    @SuppressLint("SetTextI18n")
    void print_result(String First_n, int First_s, String Second_n, int Second_s, int Res_s, int mode){
            runOnUiThread(() -> {
                TextView time_tv = rLayout.findViewById(R.id.time_tv);
                TextView user_message = rLayout.findViewById(R.id.user_message);
                TextView message_to_user = rLayout.findViewById(R.id.message_to_user);
                Button button_stop = rLayout.findViewById(R.id.button_stop);
                time_tv.setText("");
                user_message.setText("Результат");

                Log.d("BETA_RESULT", res_n[0]+" | "+res_n[1]+" | "+res_n[2]+" | "+res_n[3]);
                Double  result, result_1 = 0.0, result_2 = 0.0;
                //Проверка на минус
                short minus_1 = 1, minus_2 = 1;
                if (res_n[0] != null && error == 0) { result_1 += Double.parseDouble(res_n[0]); if (res_n[0].contains("-")) minus_1 *= -1; }
                if (res_n[2] != null && error == 0) { result_2 += Double.parseDouble(res_n[2]); if (res_n[2].contains("-")) minus_2 *= -1; }
                if (res_n[1] != null && error == 0) result_1 += minus_1 * Double.parseDouble("0."+res_n[1]);
                if (res_n[3] != null && error == 0) result_2 += minus_2 * Double.parseDouble("0."+res_n[3]);

                if (mode == 0 && error == 0) {
                     result = result_1 + result_2;
                    Log.d("BETA_RESULT", result + " "+ result_1 + " " + result_2);
                    message_to_user.setText("\t\t" + First_n + "(" + First_s + ") + " + Second_n + "(" + Second_s + ") = " + new Perevod().ceil_to_res(result.intValue(), Res_s) +"." + new Perevod().fractional_to_res(Math.abs(result)-Math.abs(result.intValue()), Res_s) + "(" + Res_s + ")");
                }
                else if (mode == 1 && error == 0) {
                    result = result_1 - result_2;
                    message_to_user.setText("\t\t" + First_n + "(" + First_s + ") - " + Second_n + "(" + Second_s + ") = " + new Perevod().ceil_to_res(result.intValue(), Res_s) + "." + new Perevod().fractional_to_res(Math.abs(result)-Math.abs(result.intValue()), Res_s) + "(" + Res_s + ")");
                }
                else if (mode == 2 && error == 0) {
                    if (result_2 == 0.0) result_2 = 1.0;
                    result = result_1 / result_2;
                    message_to_user.setText("\t\t" + First_n + "(" + First_s + ") / " + Second_n + "(" + Second_s + ") = " + new Perevod().ceil_to_res(result.intValue(), Res_s) + "." + new Perevod().fractional_to_res(Math.abs(result)-Math.abs(result.intValue()), Res_s) + "(" + Res_s + ")");
                }
                else if (mode == 3 && error == 0) {
                    result = result_1 * result_2;
                    message_to_user.setText("\t\t" + First_n + "(" + First_s + ") * " + Second_n + "(" + Second_s + ") = " + new Perevod().ceil_to_res(result.intValue(), Res_s) + "." + new Perevod().fractional_to_res(Math.abs(result)-Math.abs(result.intValue()), Res_s) + "(" + Res_s + ")");
                }
                else if (mode == 4 && error == 0) {
                    result = Math.pow(result_1, result_2);
                    message_to_user.setText("\t\t" + First_n + "(" + First_s + ") ^ " + Second_n + "(" + Second_s + ") = " + new Perevod().ceil_to_res(result.intValue(), Res_s) + "." + new Perevod().fractional_to_res(Math.abs(result)-Math.abs(result.intValue()), Res_s) + "(" + Res_s + ")");
                }
                else if(error == 0)
                    message_to_user.setText("\t\t"+First_n+"("+First_s+") = "+new Perevod().ceil_to_res(result_1.intValue(), Res_s)+"."+ new Perevod().fractional_to_res(Math.abs(result_1) - Math.abs(result_1.intValue()), Res_s)+"("+Res_s+")");
                else
                    message_to_user.setText(res_n[error-1]);
                button_stop.setText("Продолжить");
            });
    }

    @Override
    protected void onRestart(){
        set_music();
        set_back();
        super.onRestart();
    }

    @Override
    protected void onResume(){
        set_music();
        set_back();
        super.onResume();
    }

    @Override
    protected  void onPause(){
        music.pause();
        music_length = music.getCurrentPosition();
        super.onPause();
    }

    @Override
    protected void onStop(){
        music.pause();
        music_length = music.getCurrentPosition();
        super.onStop();
    }

    void set_music(){
        music.stop();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.getAll();
        int settings = Integer.parseInt(sharedPreferences.getString("musics", "0"));
        if (settings != 0) { music_index = settings-1; music_length = 0;}
        music = MediaPlayer.create(MainActivity.this, getResources().getIdentifier(sounds.get(music_index), "raw", getPackageName()));
        final float volume = (float) (1 - (Math.log(100 - sharedPreferences.getInt("volume", 0)) / Math.log(100)));
        music.setVolume(volume, volume);
        music.seekTo(music_length);
        // Если композиция закончилась
        music.setOnCompletionListener(mediaPlayer -> {
            music_length = 0;
            change_music();
            set_music();
        });
        music.start();
    }

    void change_music(){
        int i = new Random().nextInt(sounds.size());
        while (music_index == i)
            i = new Random().nextInt(sounds.size());
        music_index = i;
    }

    void set_back() {
        // Функция установки выбранной темы
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.getAll();
        int settings_back = Integer.parseInt(sharedPreferences.getString("wallpapers", "0"));
        if (settings_back != 0) back = settings_back-1;
        ImageView gif =  findViewById(R.id.gif);
        if (back == 1) gif.setScaleY(1.7F);
        else if (back == 2) gif.setScaleY(1.6F);
        else if (back == 3) gif.setScaleY(1.6F);
        else if (back == 5) gif.setScaleY(1.5F);
        else if (back == 6) { gif.setScaleX(1.6F); gif.setScaleY(1.6F);}
        else if(back == 8) gif.setScaleY(1.7F);
        Glide.with(this).load(getResources().getIdentifier(background.get(back), "drawable", getPackageName())).into(gif);
        gif = findViewById(R.id.mem1_iv);
        Glide.with(this).load(R.drawable.mem1).into(gif);
    }

    void change_back() {
        int i = new Random().nextInt(background.size());
        while (back == i)
            i = new Random().nextInt(background.size());
        back = i;
    }

    void set_motivating_pic(View dialog_view){
        /*
         * Установка мотивирующей картинки на фон диалогового окна
         */
        ImageView mem = rLayout.findViewById(octopus);
        if (mem_flag) {
            mem.setScaleY((float) 0.8);
            mem.setScaleX((float) 0.9);
            Glide.with(dialog_view).load(R.drawable.mem2).into(mem);
        } else {
            mem.setScaleY((float) 1.05);
            mem.setScaleX((float) 1.1);
            Glide.with(dialog_view).load(R.drawable.mem3).into(mem);
        }
        mem_flag = !mem_flag;
    }
}