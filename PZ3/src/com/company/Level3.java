package com.company;
import java.util.Scanner;

    public class Level3 {
        public static void main(String[] args) throws InterruptedException {
            String str;
            String string;
            int n;
            int a;
            int b;
            int c;
            int[] arr;
            int[] array;
            int choice = -1;
            Scanner in = new Scanner(System.in);
            while (choice != 0) {
                System.out.println("Выберите какой метод использовать:");
                System.out.println(" 1 - найти количество решений для квадратного уровня");
                System.out.println(" 2 - найти позицию второго вхождения zip в строку");
                System.out.println(" 3 - проверить на совершенное число");
                System.out.println(" 4 - поменять местами первый и последний символы");
                System.out.println(" 5 - узнать является ли строка допустимым шестнадцатиричным числом");
                System.out.println(" 6 - сравнить количество уникальных элементов массива");
                System.out.println(" 7 - проверка на соответствие числу Капрекара");
                System.out.println(" 8 - вывести максимальное число нулей");
                System.out.println(" 9 - ближайшее простое");
                System.out.println("10 - проверить стороны прямоугольного треугольника");
                System.out.println(" 0 - Выход");
                choice = in.nextInt();
                switch (choice) {
                    case (1) -> {
                        System.out.println("Введите a, b, c");
                        a = in.nextInt();
                        b = in.nextInt();
                        c = in.nextInt();
                        System.out.println("Количество решений для данных значений: " + numOfSolutions(a, b, c));
                    }
                    case (2) -> {
                        System.out.println("Введите строку:");
                        string = in.nextLine();
                        str = in.nextLine();
                        System.out.println(str.indexOf("zip", (str.indexOf("zip") + 1)));
                    }
                    case (3) -> {
                        System.out.println("Введите число");
                        n = in.nextInt();
                        System.out.println("Это число совершенное? " + checkPerfect(n));
                    }
                    case (4) -> {
                        System.out.println("Введите строку:");
                        string = in.nextLine();
                        str = in.nextLine();
                        System.out.println(changeBeginEndLetters(str));
                    }
                    case (5) -> {
                        System.out.println("Введите строку:");
                        str = in.next();
                        System.out.println(isValidHexCode(str));
                    }
                    case (6) -> {
                        System.out.println("Введите длину первого массива:");
                        n = in.nextInt();
                        arr = new int[n];
                        System.out.println("Введите первый массив:");
                        for (int i = 0; i < n; i++)
                            arr[i] = in.nextInt();
                        System.out.println("Введите длину второго массива:");
                        n = in.nextInt();
                        array = new int[n];
                        System.out.println("Введите второй массив:");
                        for (int i = 0; i < n; i++)
                            array[i] = in.nextInt();
                        System.out.println("Имеют ли массивы одинаковое количество уникальных элемментов? " + (uniqueElements(arr) == uniqueElements(array)));
                    }
                    case (7) -> {
                        System.out.println("Введите число");
                        n = in.nextInt();

                        System.out.println("Введенное число является числом Капрекара? " + isKaprekar(n));
                        //  System.out.println();
                    }
                    case (8) -> {
                        System.out.println("Введите строку:");
                        str = in.next();
                        System.out.println("Максимум нулей " + longestZeroSequence(str));
                    }
                    case (9) -> {
                        System.out.println("Введите число");
                        n = in.nextInt();
                        System.out.println("Ближайшее простое: " + nextPrime(n));
                    }
                    case (10) -> {
                        System.out.println("Введите стороны:");
                        a = in.nextInt();
                        b = in.nextInt();
                        c = in.nextInt();
                        System.out.println("Стороны являются ребрами прямоугольного треугольника? " + rightTriangle(a, b, c));
                    }
                }
                Thread.sleep(1000);
            }
        }

        public static int numOfSolutions(int a, int b, int c) {
            int i = b * b - 4 * a * c;
            if (i > 0) return 2;
            else if (i == 0) return 1;
            return 0;
        }

        public static String checkPerfect(int a) {
            int sum = 0;
            for (int i = 1; i <= a / 2; i++)
                if (a % i == 0) sum += i;
            if (sum == a) return ("Yes");
            return ("No");
        }

        public static String changeBeginEndLetters(String str) {
            String rev = "";
            if (str.length() < 2) return ("Это неприемлимо!");
            if (str.charAt(str.length() - 1) == str.charAt(0)) return ("Символы одинаковы!");
            else {
                rev += str.charAt(str.length() - 1);
                for (int i = 1; i < str.length() - 1; i++) rev += str.charAt(i);
                rev += str.charAt(0);
            }
            return (rev);
        }

        public static boolean isValidHexCode(String str) {
            if ((str.length() == 7))
                return str.matches("(#.*)[A-Fa-f0-9]");
            return false;
        }

        public static String longestZeroSequence(String str) {
            int n = 0;
            int max = 0;
            String string = "";
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '0') n++;
                else n = 0;
                if (n > max) max = n;
            }
            for (int i = 0; i < max; i++)
                string += '0';
            return string;
        }

        static int uniqueElements(int[] a) {
            int countUnique = 0;
            int count = 0;
            for (int i = 0; i < a.length; i++) {
                countUnique++;
                for (int j = i + 1; j < a.length; j++) {
                    if (a[j] == a[i]) {
                        count++;
                    }
                }
            }
            return countUnique - count;
        }

        public static int nextPrime(int n) {
            for (int i = n; i < n + 9; i++) {
                int test = 0;
                for (int j = 2; j < i; j++) {
                    if (i % j == 0) break;
                    if (j == i - 1) test += 1;
                }
                if (test == 1) return i;
            }
            return 0;
        }

        public static boolean isKaprekar(int n) {

            int square;
            int temp;
            int countDigits = 0;
            int firstPart;
            int secondPart;
            int sum;

            square = n * n;

            temp = square;
            while (temp != 0) {
                countDigits++;
                temp /= 10;
            }

            for (int i = countDigits - 1; i > 0; i--) {
                firstPart = square % (int) Math.pow(10, i);
                secondPart = square / (int) Math.pow(10, i);
                if (firstPart == 0 || secondPart == 0)
                    continue;
                sum = firstPart + secondPart;
                if (sum == n)
                    return true;
            }
            return false;
        }

        public static boolean rightTriangle(int a, int b, int c) {
            return ((a * a + b * b == c * c) || (c * c + b * b == a * a) || (a * a + c * c == b * b));
        }
    }