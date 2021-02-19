package com.company;

import java.util.Scanner;

class RomanN {

    private final int num;   // число в виде Int

    private static int[] numbers = {100, 90, 50, 40, 10, 9, 5, 4, 1};

    private static String[] letters = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public RomanN(int arabic) { //Конструктор из Int Может быть в пределах 100
        if (arabic < 1)
            throw new NumberFormatException("Ошибка! Значение римских чисел не может быть нулем или отрицательным!");
//        if (arabic > 100)
//            throw new NumberFormatException("Ошибка! В данной программе работаем с результатом в пределах 100!");
        num = arabic;
    }

    public RomanN(String roman) {// Конструктор из строки + нужно проверить 1..10

        roman = roman.toUpperCase();  // Приведем к верхнему регистру.

        int i = 0;
        int arabic = 0;

        while (i < roman.length()) {

            char letter = roman.charAt(i);
            int number = letterToNumber(letter);
            i++;
            if (i == roman.length()) {
                arabic += number;
            } else {
                int nextNumber = letterToNumber(roman.charAt(i));
                if (nextNumber > number) {
                    arabic += (nextNumber - number);
                    i++;
                } else {
                    arabic += number;
                }
            }
        }
        if (arabic<1 || arabic>10){
            throw new NumberFormatException("Ошибка! Ожидаются положительные числа от 1 до 10 включительно!");
        }
        num = arabic;
    }

    private int letterToNumber(char letter) {
        switch (letter) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            default:
                throw new NumberFormatException("Ошибка! Не известный символ "+letter+"!");
        }
    }

    public String toString() {
        String roman = "";
        int N = num;
        for (int i = 0; i < numbers.length; i++) {
            while (N >= numbers[i]) {
                roman += letters[i];
                N -= numbers[i];
            }
        }
        return roman;
    }

    public int toInt() {
        return num;
    }
}

class Calculator{
    private Calculator(){}
    public static int calculate(int one, int two, char operation){
        int res;
        switch (operation){
            case '+': res = one + two; break;
            case '-': res = one - two; break;
            case '*': res = one * two; break;
            case '/': res = one / two; break;
            default: throw  new IllegalArgumentException("Не верная операция");
        }
        return res;
    }

    public static boolean isNumber(String s) {
        try {
            int i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    public static boolean inRange(int n) {// Здесь проверим на правильность ввода от 1 до 10 включительно
        if (n < 0 || n > 10) {
            return false;
        }
        else return true;
    }
}
public class Main {

    public static void main(String[] args) {
        try
        {
            Scanner in = new Scanner(System.in);
            System.out.print("Для выхода напечатайте Q и нажмите Enter:\n ");
            System.out.print("Введите арифметическую операцию в одной строке(например:2 + 3 или III*VI пробелы игнорируются) и нажмите Enter для вычисления:\n ");
            // чтение построчно
            String text;
            String one;
            String two;
            char operation;
            while (!(text = in.nextLine()).equals("Q")) {
                String[] blocks = text.split("[+-/*]");
                if(blocks.length!=2){//не получилось разделить по знаку операции строку ввода - ошибка
                    throw new Exception("Ошибка ввода данных!");
                }
                one = blocks[0].trim();
                operation = text.charAt(blocks[0].length());
                two = blocks[1].trim();
                if (Calculator.isNumber(one) && Calculator.isNumber(two)) {//Если оба Int, то проверяем вхождение в 1..10 и вызываем калькулятор
                    if (Calculator.inRange(Integer.parseInt(one)) && Calculator.inRange(Integer.parseInt(two))) {
                        System.out.println(Calculator.calculate(Integer.parseInt(one), Integer.parseInt(two), operation));
                    }
                    else throw new NumberFormatException("Ошибка ввода данных! Ожидается ввод чисел от 1 до 10 включительно!");
                }
                else {
                    try {
                        RomanN onern = new RomanN(one);
                        RomanN tworn = new RomanN(two);
                        RomanN resrn = new RomanN(Calculator.calculate(onern.toInt(), tworn.toInt(),operation));
                        System.out.println(resrn.toString());
                    }catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        throw new Exception("Ошибка ввода данных! Не удалось преобразовать введенные символы в римские числа!");
                    }
                }
                System.out.print("Для выхода напечатайте Q и нажмите Enter:\n ");
                System.out.print("Введите арифметическую операцию в одной строке(например:2 + 3 или III*VI пробелы игнорируются) и нажмите Enter для вычисления:\n ");
            }
            System.out.print("Bay!");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}