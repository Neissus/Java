package com.company;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Scanner;
import java.util.*;

public class Level5 {

    public static void main(String[] args) throws InterruptedException, NoSuchAlgorithmException {
        String str;
        int n;
        int a;
        long card;
        String string;
        int [] arr;
        int choice = -1;
        Scanner in = new Scanner(System.in);
        while(choice != 0) {
            System.out.println("Выберите какой метод использовать:");
            System.out.println(" 1 - Зашифровка");
            System.out.println(" 2 - Шахматы");
            System.out.println(" 3 - Работа с преобразованием строк");
            System.out.println(" 4 - Работа с аргументами(произведение до 1 цифры)");
            System.out.println(" 5 - Одинаковые гласные");
            System.out.println(" 6 - Верный ввод кредитной карты");
            System.out.println(" 7 - Число в виде строки ");
            System.out.println(" 8 - Хеш-код SHA-256");
            System.out.println(" 9 - Игра престолов");
            System.out.println("10 - Гексагональная решетка");
            System.out.println(" 0 - Выход");
            choice = in.nextInt();
            switch (choice) {
                case (1) -> {
                    System.out.println("Выберите метод 1 - зашифровать, 2 - расшифровать");
                    a = in.nextInt();
                    if (a==1)
                    {
                        System.out.println("Введите строку");
                        str = in.nextLine();
                        str = in.nextLine();
                        System.out.println("Зашифрованное сообщение: "+encrypt(str));
                    }
                    if (a==2)
                    {
                        System.out.println("Введите длинну кода");
                        int j=in.nextInt();
                        arr = new int[j];
                        System.out.println("Введите код:");
                        for (int i = 0; i < j; i++)
                            arr[i] = in.nextInt();
                        System.out.println("Расшифрованное сообщение: "+decrypt(arr));
                    }
                }
                case (2) -> {
                    System.out.println("Введите через пробел (Фигура, Положение и Целевая позиция)");
                    System.out.println("Фигуры: Пешка, Слон, Конь, Ладья, Королева, король");
                    String name=in.next();
                    String start=in.next();
                    String end=in.next();
                    System.out.println("Может ходить? "+canMove(name,start,end));
                }
                case (3) -> {
                    System.out.println("Введите строку");
                    str=in.next();
                    System.out.println("Введите строку для провеерки");
                    string=in.next();
                    System.out.println("Слово может быть завершено? "+canComplete(str,string));
                }
                case (4) -> {
                    System.out.println("Введите длину массива");
                    n=in.nextInt();
                    arr = new int[n];
                    System.out.println("Введите массив:");
                    for (int i = 0; i < n; i++)
                        arr[i] = in.nextInt();
                    System.out.println("Результат: "+sumDigProd(arr));
                }
                case (5) -> {
                    System.out.println("Введите количество слов");
                    n = in.nextInt();
                    String[] S = new String[n];
                    System.out.println("Введите массив:");
                    for (int i = 0; i < n; i++)
                        S[i] = in.next();
                    System.out.println("Результат: " + sameVowelGroup(S));
                }
                case (6) -> {
                    System.out.println("Введите номер карты:");
                    card= in.nextLong();
                    System.out.println("Ввод карты верен? -"+validateCard(card));
                }
                case (7) -> {
                    System.out.println("Введите число");
                    n=in.nextInt();
                    System.out.println(numToEng(n));
                }
                case (8) -> {

                    System.out.println("Введите строку");
                    str = in.next();
                    System.out.println("Хеш-код: " + getSha256Hash(str));
                }
                case (9) -> {
                    System.out.println("Введите строку");
                    str = in.nextLine();
                    str = in.nextLine();
                    System.out.println(correctTitle(str));
                }
                case (10) -> {
                    System.out.println("Введите число:");
                    n = in.nextInt();
                    System.out.println(hexLattice(n));
                }
            }
            Thread.sleep(1000);
        }
    }

    public static String encrypt(String str) {
        int[] mass = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            if (i == 0)
                mass[i] = str.charAt(i);
            else
                mass[i] += str.charAt(i) - (str.charAt(i - 1));
        }
        return Arrays.toString(mass);
    }

    public static String decrypt(int[] mass) {
        char[] str = new char[mass.length];
        for (int i = 0; i < mass.length; i++) {
            if (i == 0) {
                str[i] = (char) mass[i];
            } else {
                mass[i] = mass[i - 1] + mass[i];
                str[i] = (char) (mass[i]);

            }
        }
        return new String(str);
    }

    public static boolean canMove(String name, String start, String end) {
        char startLiteral = start.charAt(0);
        int startNumber = Integer.parseInt(String.valueOf(start.charAt(1)));
        char endLiteral = end.charAt(0);
        int endNumber = Integer.parseInt(String.valueOf(end.charAt(1)));
        if (startLiteral == endLiteral && startNumber == endNumber) return false;
        switch (name) {
            case "Пешка": {
                if (startLiteral == endLiteral && ((startNumber == 2 && endNumber == 4) || endNumber == startNumber + 1))
                    return true;
                return startLiteral == endLiteral && endNumber == (startNumber + 1);
            }
            case "Конь": {
                return (Math.abs(startLiteral - endLiteral) == 2 && Math.abs(startNumber - endNumber) == 1) || (Math.abs(startLiteral - endLiteral) == 1 && Math.abs(startNumber - endNumber) == 2);
            }
            case "Слон": {
                return Math.abs(startLiteral - endLiteral) == Math.abs(startNumber - endNumber);
            }
            case "Ладья": {
                return (startLiteral == endLiteral && startNumber != endNumber) || (startLiteral != endLiteral && startNumber == endNumber);
            }
            case "Королева": {
                if ((startLiteral == endLiteral && startNumber != endNumber) || (startLiteral != endLiteral && startNumber == endNumber))
                    return true;
                if (Math.abs(startLiteral - endLiteral) == Math.abs(startNumber - endNumber))
                    return true;
                break;
            }
            case "Король": {
                return Math.abs(startLiteral - endLiteral) < 2 && Math.abs(startNumber - endNumber) < 2;
            }
            default:
                return false;
        }
        return false;
    }

    public static boolean canComplete(String str1, String str2) {
        char[] masStr1 = str1.toCharArray();
        int num = 0;
        for (char c : masStr1) {
            if (str2.indexOf(String.valueOf(c), num) != -1)
                num = str2.indexOf(String.valueOf(c), num) + 1;
            else
                return false;
        }
        return true;
    }

    public static int sumDigProd(int[] mass) {
        int number = Arrays.stream(mass).sum();
        while (number > 9) {
            int multiplier = 1;
            while (number > 0) {
                multiplier *= number % 10;
                number /= 10;
            }
            number = multiplier;
        }
        return number;
    }

    public static String sameVowelGroup(String[] str) {
        String vowel = "aeiouyAEIOUY";
        StringBuilder first = new StringBuilder();
        String second = "";
        ArrayList <String> words = new ArrayList<>();
        Collections.addAll(words, str);

        for (int i = 0; i < words.get(0).length(); i++) {
            if (vowel.indexOf(words.get(0).charAt(i)) != -1)
                first.append(words.get(0).charAt(i));
        }

        for (int i = words.size() - 1; i >= 0; i--) {
            for (int j = 0; j < words.get(i).length(); j++) {
                if (vowel.indexOf(words.get(i).charAt(j)) != -1) {
                    second += words.get(i).charAt(j);
                }
            }
            for (int k = 0; k < second.length();) {
                if (first.toString().indexOf(second.charAt(k)) != -1)
                    k++;
                else {
                    words.remove(i);
                    second = "";
                }
            }
        }
        return String.valueOf(words);
    }

    public static boolean validateCard(long cardNum) {
        StringBuilder str= new StringBuilder();
        if ( Long.toString(cardNum).length()>= 14 && Long.toString(cardNum).length() <= 19) {
            long lastNum = cardNum%10;
            StringBuilder cardNumStr = new StringBuilder(Long.toString(cardNum/=10));
            cardNumStr.reverse();
            for (int i = 0; i< cardNumStr.length(); i++){
                if (i%2==0){
                    int c = Character.getNumericValue(cardNumStr.charAt(i))*2;
                    if(c>9){
                        String buf = Integer.toString(c);
                        str.append(Character.getNumericValue(buf.charAt(0)) + Character.getNumericValue(buf.charAt(1)));
                    }
                    else str.append(c);
                }
                else str.append(cardNumStr.charAt(i));
            }
            int sum=0;
            for (int i=0;i<str.length();i++)
                sum+=Character.getNumericValue(str.charAt(i));
            return lastNum == 10 - sum % 10;
        }
        return false;
    }

    public static String numToEng(int num) {
        String strnum = "";
        if (num == 0) return "zero";
        // сотни
        switch (num / 100) {
            case 1: {
                strnum += "one hundred ";
                break;
            }
            case 2: {
                strnum += "two hundred ";
                break;
            }
            case 3: {
                strnum += "three hundred ";
                break;
            }
            case 4: {
                strnum += "four hundred ";
                break;
            }
            case 5: {
                strnum += "five hundred ";
                break;
            }
            case 6: {
                strnum += "six hundred ";
                break;
            }
            case 7: {
                strnum += "seven hundred ";
                break;
            }
            case 8: {
                strnum += "eight hundred ";
                break;
            }
            case 9: {
                strnum += "nine hundred ";
                break;
            }
        }
        switch (num / 10 % 10) {
            case 1: {
                switch (num % 10) {
                    case 0: {
                        strnum += "ten";
                        return strnum;
                    }
                    case 1: {
                        strnum += "eleven";
                        return strnum;
                    }
                    case 2: {
                        strnum += "twelve";
                        return strnum;
                    }
                    case 3: {
                        strnum += "thirteen";
                        return strnum;
                    }
                    case 4: {
                        strnum += "fourteen";
                        return strnum;
                    }
                    case 5: {
                        strnum += "fifteen";
                        return strnum;
                    }
                    case 6: {
                        strnum += "sixteen";
                        return strnum;
                    }
                    case 7: {
                        strnum += "seventeen";
                        return strnum;
                    }
                    case 8: {
                        strnum += "eighteen";
                        return strnum;
                    }
                    case 9: {
                        strnum += "nineteen";
                        return strnum;
                    }
                }
            }
            // десятки после 20
            case 2: {
                strnum += "twenty ";
                break;
            }
            case 3: {
                strnum += "thirty ";
                break;
            }
            case 4: {
                strnum += "forty ";
                break;
            }
            case 5: {
                strnum += "fifty ";
                break;
            }
            case 6: {
                strnum += "sixty ";
                break;
            }
            case 7: {
                strnum += "seventy ";
                break;
            }
            case 8: {
                strnum += "eighty ";
                break;
            }
            case 9: {
                strnum += "ninety ";
                break;
            }
        }
        // единицы
        switch (num % 10) {
            case 1: {
                strnum += "one";
                break;
            }
            case 2: {
                strnum += "two";
                break;
            }
            case 3: {
                strnum += "three";
                break;
            }
            case 4: {
                strnum += "four";
                break;
            }
            case 5: {
                strnum += "five";
                break;
            }
            case 6: {
                strnum += "six";
                break;
            }
            case 7: {
                strnum += "seven";
                break;
            }
            case 8: {
                strnum += "eight";
                break;
            }
            case 9: {
                strnum += "nine";
                break;
            }
        }
        return strnum;
    }

    public static String numToRus(int num) {
        String strnum = "";
        if (num == 0) return "ноль";
        // сотни
        switch (num / 100) {
            case 1: {
                strnum += "сто ";
                break;
            }
            case 2: {
                strnum += "двести ";
                break;
            }
            case 3: {
                strnum += "триста ";
                break;
            }
            case 4: {
                strnum += "четыреста ";
                break;
            }
            case 5: {
                strnum += "пятьсот ";
                break;
            }
            case 6: {
                strnum += "шестьсот ";
                break;
            }
            case 7: {
                strnum += "семьсот ";
                break;
            }
            case 8: {
                strnum += "восемьсот ";
                break;
            }
            case 9: {
                strnum += "девятьсот ";
                break;
            }
        }
        // десятки до 20
        switch (num / 10 % 10) {
            case 1: {
                switch (num % 10) {
                    case 0: {
                        strnum += "десять";
                        return strnum;
                    }
                    case 1: {
                        strnum += "одиннадцать";
                        return strnum;
                    }
                    case 2: {
                        strnum += "двенадцать";
                        return strnum;
                    }
                    case 3: {
                        strnum += "тринадцать";
                        return strnum;
                    }
                    case 4: {
                        strnum += "четырнадцать";
                        return strnum;
                    }
                    case 5: {
                        strnum += "пятнадцать";
                        return strnum;
                    }
                    case 6: {
                        strnum += "шестнадцать";
                        return strnum;
                    }
                    case 7: {
                        strnum += "семнадцать";
                        return strnum;
                    }
                    case 8: {
                        strnum += "восемьнадцать";
                        return strnum;
                    }
                    case 9: {
                        strnum += "двадцать";
                        return strnum;
                    }
                }
            }
            // десятки после 20
            case 2: {
                strnum += "двадцать ";
                break;
            }
            case 3: {
                strnum += "тридцать ";
                break;
            }
            case 4: {
                strnum += "сорок ";
                break;
            }
            case 5: {
                strnum += "пятьдесят ";
                break;
            }
            case 6: {
                strnum += "шестьдесят ";
                break;
            }
            case 7: {
                strnum += "семьдесят ";
                break;
            }
            case 8: {
                strnum += "восемьдесят ";
                break;
            }
            case 9: {
                strnum += "девяносто ";
                break;
            }
        }
        // единицы
        switch (num % 10) {
            case 1: {
                strnum += "один";
                break;
            }
            case 2: {
                strnum += "два";
                break;
            }
            case 3: {
                strnum += "три";
                break;
            }
            case 4: {
                strnum += "четыре";
                break;
            }
            case 5: {
                strnum += "пять";
                break;
            }
            case 6: {
                strnum += "шесть";
                break;
            }
            case 7: {
                strnum += "семь";
                break;
            }
            case 8: {
                strnum += "восемь";
                break;
            }
            case 9: {
                strnum += "девять";
                break;
            }
        }
        return strnum;
    }

    static final class  HexBin {
        static private final int  BASELENGTH   = 128;
        static private final int  LOOKUPLENGTH = 16;
        static final private byte [] hexNumberTable    = new byte[BASELENGTH];
        static final private char [] lookUpHexAlphabet = new char[LOOKUPLENGTH];


        static {
            for (int i = 0; i < BASELENGTH; i++ ) {
                hexNumberTable[i] = -1;
            }
            for ( int i = '9'; i >= '0'; i--) {
                hexNumberTable[i] = (byte) (i-'0');
            }
            for ( int i = 'F'; i>= 'A'; i--) {
                hexNumberTable[i] = (byte) ( i-'A' + 10 );
            }
            for ( int i = 'f'; i>= 'a'; i--) {
                hexNumberTable[i] = (byte) ( i-'a' + 10 );
            }

            for(int i = 0; i<10; i++ ) {
                lookUpHexAlphabet[i] = (char)('0'+i);
            }
            for(int i = 10; i<=15; i++ ) {
                lookUpHexAlphabet[i] = (char)('A'+i -10);
            }
        }
        static public String encode(byte[] binaryData) {
            if (binaryData == null)
                return null;
            int lengthData   = binaryData.length;
            int lengthEncode = lengthData * 2;
            char[] encodedData = new char[lengthEncode];
            int temp;
            for (int i = 0; i < lengthData; i++) {
                temp = binaryData[i];
                if (temp < 0)
                    temp += 256;
                encodedData[i*2] = lookUpHexAlphabet[temp >> 4];
                encodedData[i*2+1] = lookUpHexAlphabet[temp & 0xf];
            }
            return new String(encodedData);
        }
    }

    public static String getSha256Hash(String str)throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] text=digest.digest(str.getBytes(StandardCharsets.UTF_8));
        return HexBin.encode(text).toLowerCase();
    }

    public static String correctTitle(String title) {
        String[] text = title.toLowerCase().split(" ");
        title = "";
        for (int i = 0; i < text.length; i++) {
            if (text[i].equals("in") || text[i].equals("of") || text[i].equals("and") || text[i].equals("the"))
                title += text[i].toLowerCase() + " ";
            else
                title += text[i].substring(0, 1).toUpperCase() + text[i].substring(1) + " ";
        }
        return title;
    }

    public static String hexLattice(int n){
        int num = 1;
        int i = 1;
        String res="";
        String str2="";
        while (n>num) {
            i++;
            num = 3 * i * (i - 1) + 1;
        }
        int l = i;
        // верхняя половина
        if (n != num)
            res = "Invalid";
        else {
            while (l < i * 2 - 1) {
                for (int a = 0; a < i * 2 - 1 - l; a++)
                    res += "  ";
                for (int b = 0; b < l; b++)
                    res += " o  ";
                res += "\n";
                l++;
            }
            // нижняя половина
            while (l >= i) {
                for (int a = 0; a < i * 2 - 1 - l; a++)
                    res += "  ";
                for (int b = l; b > 0; b--)
                    res += " o  ";
                res += "\n";
                l--;
            }
        }
        return res;
    }


}
