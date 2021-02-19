package com.company;

import java.util.Scanner;

//class RomanNum{
//    private RomanNum(){}
//    public static boolean isRoman(String s) {
//        try {
//            ;
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }
//    public static String toRoman(int n) {
//        return "";
//    }
//    public static int toInt(String n) {
//        return -1;
//    }
//}

class RomanN {

    private final int num;   // число в виде Int

    private static int[] numbers = {100, 90, 50, 40, 10, 9, 5, 4, 1};

    private static String[] letters = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public RomanN(int arabic) { //Конструктор из Int Может быть в пределах 100
        if (arabic < 1)
            throw new NumberFormatException("Ошибка! Значение римских чисел не может быть нулем или отрицательным!");
        if (arabic > 100)
            throw new NumberFormatException("Ошибка! В данной программе работаем с результатом в пределах 100!");
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

    public static boolean isNumber(String s) {// Здесь проверим на правильность ввода от 1 до 10 включительно
        try {
            int i = Integer.parseInt(s);
            if (i<0 || i>10){
                throw new NumberFormatException("Ошибка ввода данных! Ожидается ввод чисел от 1 до 10 включительно!");
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
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
            Character operation;
            while (!(text = in.nextLine()).equals("Q")) {
                String[] blocks = text.split("[+-/*]");
                if(blocks.length!=2){//не получилось разделить по знаку операции строку ввода - ошибка
                    throw new Exception("Ошибка ввода данных!");
                }
                one = blocks[0].trim();
                operation = text.charAt(blocks[0].length());
                two = blocks[1].trim();
                if (Calculator.isNumber(one) && Calculator.isNumber(two)) {//Если оба Int, то вызываем калькулятор
                    System.out.println(Calculator.calculate(Integer.parseInt(one), Integer.parseInt(two), operation));
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
//                else if(RomanNum.isRoman(one) && RomanNum.isRoman(two)) {// Иначе проверяем на Римские числа
//                    //Если Римские, то переводим в int и вызываем калькулятор, результат переводим в Римские
//                    System.out.println(RomanNum.toRoman(Calculator.calculate(RomanNum.toInt(one), RomanNum.toInt(two), operation)));
//                }
//                else throw new Exception("Ошибка ввода данных!");//Иначе ошибка ввода данных
            }
            System.out.print("Bay!");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}





















//        int plus, minus, mult, div, pos;
//        try {
//            Scanner in = new Scanner(System.in);
//            System.out.print("Введите арифметическую операцию в одной строке(например:2+3) и нажмите Enter для вычисления: ");
//            String calcString = in.nextLine();
//            calcString = calcString.trim();// уберем крайние пробелы
//            //Разберем строку на три части
//            //Первое "число" без разбора, что в нем и уберем пробелы
//            //Знак арифметической операции (+,-,*,/)
//            //Второе "число" без разбора, что в нем и уберем пробелы
//            plus = -1;
//            minus = -1;
//            mult = -1;
//            div = -1;
//            pos = -1;
//            pos = plus = calcString.indexOf("+");
//            if (plus < 0) {
//                pos = minus = calcString.indexOf("-");
//                if (minus < 0) {
//                    pos = mult = calcString.indexOf("*");
//                    if (mult < 0) {
//                        pos = div = calcString.indexOf("/");
//                        if (div < 0) {
//                            //Исключение: Нет арифметического знака!
//                            throw new Exception("Нет арифметического знака!");
//                        }
//                    }
//                }
//            }
//            if (pos ==- 0) {
//                //Исключение: Нет первого "числа" в выражении!
//                throw new Exception("Нет первого \"числа\" в выражении!");
//            }else if (pos == (calcString.length() - 1)) {
//                //Исключение: Нет второго "числа" в выражении!
//                throw new Exception("Нет второго \"числа\" в выражении!");
//            }
//            System.out.printf("Your string: %s \n", calcString);
//            in.close();
//    } catch(Exception e) {
//            e.printStackTrace();
//        }
//
//

//    }

//    public static void main(String[] args) {
//        int value1 = 0;
//        int value2 = 0;
//        String operation = null;
//
//        System.out.println("Введите 2  целых числа: ");
//        Scanner scanner = new Scanner(System.in);
//        if (value1 > 0 || value1 < 10) {
//            value1 = scanner.nextInt();
//            operation = scanner.next();
//            value2 = scanner.nextInt();
//        }
//        if (operation.equals("+")) {
//            System.out.println(value1 + value2);
//        }
//        if (operation.equals("-")) {
//            System.out.println(value1 - value2);
//        }
//        if (operation.equals("*")) {
//            System.out.println(value1 * value2);
//        }
//        if (operation.equals("/")) {
//            System.out.println(value1 / value2);
//        } else {
//            System.out.println("error!");
//        }
//    }
//}