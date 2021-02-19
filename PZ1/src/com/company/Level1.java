package com.company;
import java.util.Scanner;

public class Level1 {

    public static void main(String[] args) throws InterruptedException {
        int a;
        int b;
        int c;
        char e;
        double l;
        double m;
        double n;
        int[] array;
        int choice = -1;
        while(choice != 0) {
            Scanner in = new Scanner(System.in);
            System.out.println("Выберите какой метод использовать:");
            System.out.println("1 - найти остаток от деления числа на число");
            System.out.println("2 - найти площадь треугольника по его высоте и основанию");
            System.out.println("3 - найти количество лап по количеству животных");
            System.out.println("4 - проверить наличие прибыли");
            System.out.println("5 - проверить числа на зависимость");
            System.out.println("6 - перевести символ в число");
            System.out.println("7 - вывести сумму всех предыдущих чисел включая переданное");
            System.out.println("8 - найти максимальную длину 3 стороны треугольника");
            System.out.println("9 - найти сумму кубов");
            System.out.println("10 - проссумировать число A B раз, проверить делимость суммы на С");
            System.out.println("0 - Выход");

            choice = in.nextInt();
            switch (choice) {
                case (1):

                    System.out.println("Введите два числа:");
                    a = in.nextInt();
                    b = in.nextInt();
                    System.out.println("Остаток от деления равен = " + remainder(a, b));
                    break;
                case (2):
                    System.out.println("Введите сторону и высоту:");
                    a = in.nextInt();
                    b = in.nextInt();
                    System.out.println("Площадь треугольника = " + triArea(a, b));
                    break;
                case (3):
                    System.out.println("Введите количество кур, коров, свиней:");
                    a = in.nextInt();
                    b = in.nextInt();
                    c = in.nextInt();
                    System.out.println("Количество лап = " + animals(a, b, c));
                    break;
                case (4):
                    System.out.println("Введите количество проданных товаров, цену, расходы:");
                    l = in.nextDouble();
                    m = in.nextDouble();
                    n = in.nextDouble();
                    System.out.println("Будет прибыль? " + profitableGamble(l, m, n));
                    break;
                case (5):
                    System.out.println("Введите три числа:");
                    l = in.nextDouble();
                    m = in.nextDouble();
                    n = in.nextDouble();
                    System.out.println("Числа " + operation(l, m, n));
                    break;
                case (6):
                    System.out.println("Введите символ:");
                    e = in.next().charAt(0);
                    System.out.println("Номер символа в кодировке ASCII = " + ctoa(e));
                    break;
                case (7):
                    System.out.println("Введите число:");
                    a = in.nextInt();
                    System.out.println("Сумма = " + addUpTo(a));
                    break;
                case (8):
                    System.out.println("Введите две стороны треугольника:");
                    a = in.nextInt();
                    b = in.nextInt();
                    System.out.println("Максимальная целая длина 3 стороны = " + nextEdge(a, b));
                    break;
                case (9):
                    System.out.println("Введите длину массива:");
                    a = in.nextInt();
                    array = new int[a];
                    System.out.println("Введите массив:");
                    for(int i = 0; i < a; i++)
                        array[i] = in.nextInt();
                    System.out.println("Сумма кубов = " + sumOfCubes(array));
                    break;
                case (10):
                    System.out.println("Введите три числа:");
                    a = in.nextInt();
                    b = in.nextInt();
                    c = in.nextInt();
                    System.out.println("Будет делиться? " + abcMath(a, b, c));
                    break;
            }
            Thread.sleep(1000);
        }
    }

    public static int remainder(int a, int b) {
        return (a % b);
    }

    public static double triArea(double a, double h) {
        return (0.5 * a * h);
    }

    public static int animals(int chickens, int cows, int pigs) {
        return (2 * chickens + 4 * cows + 4 * pigs);
    }

    public static boolean profitableGamble(double prob, double prize, double pay) {
        return (prob * prize > pay);
    }

    public static String operation(double a, double b, double N) {
        if (a + b == N)
            return ("Сложили");
        else if (a - b == N)
            return ("Вычли");
        else if (a / b == N)
            return ("Разделили");
        else if (a * b == N)
            return ("Умножили");
        else
            return ("Независимы");
    }

    public static int ctoa(char c) {
        return ((int) c);
    }

    public static int addUpTo(int a) {
        int sum = 0;
        for (int i = 1; i <= a; i++)
            sum += i;
        return (sum);
    }

    public static int nextEdge(int a, int b) {
        return (a + b - 1);
    }

    public static long sumOfCubes(int[] a) {
        long sum = 0;
        for (int i = 0; i < a.length; i++)
            sum += Math.pow(a[i], 3);
        return (sum);
    }

    public static boolean abcMath(int a, int b, int c) {
        for (int i = 0; i < b; i++)
            a += a;
        return (a % c == 0);
    }
}