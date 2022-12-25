package com.example.task4;

import static java.lang.Integer.parseInt;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Perevod {
    // Перевод целой части числа в 10
    public String ceil_to_10(String a, int original_system) {
        // a - строка, содержащее число для перевода
        // original_system - система счисления полученного числа
        // result_system - система счисления результата
        if (a.isEmpty() || a == " ") return "Неправильный ввод числа или его системы счисления!";
        //Проверка на минус
        short minus = 1;
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < a.length(); i++)
            if (a.charAt(i) == '-') minus *= -1; else r.append(a.charAt(i));
        a = r.toString();

        long k =0;
        long d = a.length() - 1;
        long result;
        boolean Current = false;

        // Переводим число в 10-ую систему счисления
        for (int i = 0; i < a.length(); i++) {
            result = 0;
            // Диапозоны ASCII
            if (((int) a.charAt(i) > 47) && (int) a.charAt(i) < 58)
                result = (int) a.charAt(i) - 48;
            if (((int) a.charAt(i) > 64) && (int) a.charAt(i) < 91)
                result = (int) a.charAt(i) - 55;
            if (((int) a.charAt(i) > 32) && (int) a.charAt(i) < 48)
                result = (int) a.charAt(i) + 3;
            if (((int) a.charAt(i) > 90) && (int) a.charAt(i) < 127)
                result = (int) a.charAt(i) - 40;
            if (((int) a.charAt(i) > 57) && (int) a.charAt(i) < 65)
                result = (int) a.charAt(i) - 29;

            if (result >= original_system) Current = true;
            if (a.charAt(i) != '0' && !Current)
                k += result * Math.pow(original_system, d);
            //Log.d("BETA_TEST CEIL_PEREVOD", result + " " + a.charAt(i) + " " + k + " " + d);
            d -= 1;
        }

        if (k > Long.MAX_VALUE-1) return "Слишком большое число. Такие пока что не поддерживаются";
        if (!Current) return String.valueOf(k*minus);
        return "Неправильный ввод числа или его системы счисления!";
    }

    // Перевод целой части числа в 10
    public String ceil_to_res(long k, int result_system){
        // result_system - система счисления результата
        // Переводим число из 10-ой в систему счисления результата
        String minus = "";
        if (k == 0) return "0";
        if (k > Long.MAX_VALUE-1) return "Слишком большое число. Такие пока что не поддерживаются";
        if (k < 0) { minus = "-"; k = -k;}

        String a;
        StringBuilder aBuilder = new StringBuilder();
        if (result_system <= 10) {
            // Если система счисления результата <= 10, то метод перевода следующий
            while (k > 0) {
                aBuilder.append(k % result_system);
                k = k / result_system;
            }
        } else {
            // Иначе система счисления результата > 10, и метод перевода другой
            while (k > 0) {
                if (k % result_system < 10) {
                    aBuilder.append(k % result_system);
                }
                else {
                    long i = (k % result_system);
                    // Диапозоны ASCII
                    if (i < 10)
                        aBuilder.append((char) (i + 48));
                    if (i > 9 && i < 36)
                        aBuilder.append((char) (i + 55));
                    if (i > 35 && i < 51)
                        aBuilder.append((char) (i - 3));
                    if (i > 50 && i < 87)
                        aBuilder.append((char) (i + 40));
                    if (i > 86 && i < 94)
                        aBuilder.append((char) (i + 29));
                }
                k = k / result_system;
            }
        }
        a = aBuilder.toString();
        return minus + new StringBuilder(a).reverse();
    }

    // Перевод дробной части из любой в десятичную систему счисления
    public String fractional_to_10(String a, int original_system) {
        if (a.isEmpty() || a == " ") return "Неправильный ввод числа или его системы счисления!";
        a = "0." + a;
        double s = 0.0;
        int stop = 150;
        boolean Current = false;
        long k = a.length();

        for (int i = 2; i < k; i++) {
            // Диапозоны ASCII
            if (((int) a.charAt(i) > 47) && (int) a.charAt(i) < 58) {
                s += ((int) a.charAt(i) - 48) * (1 / Math.pow(original_system, i - 1));
                if ((int) a.charAt(i) - 48 >= original_system) Current = true;
            }
            else if (((int) a.charAt(i) > 64) && (int) a.charAt(i) < 91) {
                s += ((int) a.charAt(i) - 55) * (1 / Math.pow(original_system, i - 1));
                if ((int) a.charAt(i) - 55 >= original_system) Current = true;
            }
            else if (((int) a.charAt(i) > 32) && (int) a.charAt(i) < 48) {
                s += ((int) a.charAt(i) + 3) * (1 / Math.pow(original_system, i - 1));
                if ((int) a.charAt(i) + 3 >= original_system) Current = true;
            }
            else if (((int) a.charAt(i) > 90) && (int) a.charAt(i) < 127) {
                s += ((int) a.charAt(i) - 40) * (1 / Math.pow(original_system, i - 1));
                if ((int) a.charAt(i) - 40 >= original_system) Current = true;
            }
            else if (((int) a.charAt(i) > 57) && (int) a.charAt(i) < 65) {
                s += ((int) a.charAt(i) - 29) * (1 / Math.pow(original_system, i - 1));
                if ((int) a.charAt(i) - 29 >= original_system) Current = true;
            }
            else {
                int number = parseInt(String.valueOf(a.charAt(i)));
                s += number * (1 / Math.pow(original_system, i - 1));
                if (number >= original_system) Current = true;
            }
            //Log.d("BETA_FRACTION", s+" "+a.charAt(i));
            stop -= 1;
            if (stop == 0) break;
        }
        a = String.valueOf((s));
        if (!Current) return a.substring(2);

        return "Неправильный ввод числа или его системы счисления!";
    }

    // Перевод дробной части из десятичной системы счисления в любую
    public String fractional_to_res(double a, long result_system) {
        if (a == 0.0) return "0";
        StringBuilder s = new StringBuilder();
        int stop = 150;
        while (a > 1e-6) {
            Double number = a * result_system;
            number = new BigDecimal(String.valueOf(number)).setScale(6, RoundingMode.HALF_UP).doubleValue();
            String h = String.valueOf(number);

            StringBuilder r = new StringBuilder();
            for (int i = 0; i < h.length(); i++)
                if (h.charAt(i) == '.') break;
                else r.append(h.charAt(i));
            int c = Integer.parseInt(r.toString());

            // Диапозоны ASCII
            if (c < 10) s.append((char) (c + 48));
            if (c > 9 && c < 36) s.append((char) (c + 55));
            if (c > 35 && c < 51) s.append((char) (c - 3));
            if (c > 50 && c < 87) s.append((char) (c + 40));
            if (c > 86 && c < 94) s.append((char) (c + 29));

            stop -= 1; if (stop == 0) break;

            //Log.d("BETA_FRACTION", c + " " + s + " " + a);

            StringBuilder drod = new StringBuilder("0.");
            int dlin = h.length(), i = 0;

            while (i < dlin) {
                if (h.charAt(i) == '.') break;
                i += 1;
            }
            i += 1;

            while (i < dlin) {
                drod.append(h.charAt(i));
                i += 1;
            }

            a = Double.parseDouble(drod.toString());
        }
        return (s.toString());
    }
}