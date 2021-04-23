package com.company;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Level6 {

    public static void main(String[] args) throws InterruptedException {
        String str;
        int n;
        int choice = -1;
        Scanner in = new Scanner(System.in);
        while (choice != 0) {
            System.out.println("Выберите какой метод использовать:");
            System.out.println(" 1 - найти число Белла");
            System.out.println(" 2 - перевести в поросячую латынь");
            System.out.println(" 3 - верность введенного формата RGB(A)");
            System.out.println(" 4 - редактировать URL");
            System.out.println(" 5 - преобразовать заголовок");
            System.out.println(" 6 - вернуть n-ое чилсло Улама");
            System.out.println(" 7 - найти самую длинную подстроку неповторяющихся символов");
            System.out.println(" 8 - преобразовать в римскую цифру");
            System.out.println(" 9 - проверить правильность равенства");
            System.out.println("10 - проверить число и потомки на палиндромность");
            System.out.println(" 0 - Выход");
            choice = in.nextInt();
            switch (choice) {
                case (1) -> {
                    System.out.println("Введите номер числа Белла:");
                    n = in.nextInt();
                    System.out.println("Значение " + n + " числа Белла = " + numBell(n));
                }
                case (2) -> {
                    String res = "";
                    System.out.println("Введите строку: ");
                    str = in.nextLine();
                    str = in.nextLine();
                    res = translateSentence(str);
                    System.out.println(res);
                }
                case (3) -> {
                    String res = "";
                    System.out.println("Введите строку: ");
                    str = in.nextLine();
                    str = in.nextLine();
                    System.out.println("Цвет записан верно? " + isValidColor(str));
                }
                case (4) -> {
                    String params = "";
                    String[] arrayParams;
                    System.out.println("Введите строку: ");
                    str = in.nextLine();
                    str = in.nextLine();
                    System.out.println("Введите параметры для удаления: ");
                    params = in.nextLine();
                    arrayParams = params.split(" ");
                    if(arrayParams.length == 0)
                        System.out.println(stripURLParams(str));
                    else
                        System.out.println(stripURLParams(str,arrayParams));
                }
                case (5) -> {
                    String res = null;
                    System.out.println("Введите строку: ");
                    str = in.nextLine();
                    str = in.nextLine();
                    res = getHashTags(str);
                    System.out.println(res);
                }
                case (6) -> {
                    System.out.println("Введите номер числа Улама:");
                    n = in.nextInt();
                    System.out.println("Значение " + n + " числа Улама = " + numOfUlam(n));
                }
                case (7) -> {
                    boolean res;
                    System.out.println("Введите строку: ");
                    str = in.nextLine();
                    str = in.nextLine();
                    System.out.println("Самая длинная неповторяющаяся подстрока: " + longestNonrepeatingSubstring(str));
                }
                case (8) -> {
                    System.out.println("Введите число: ");
                    n = in.nextInt();
                    if (n < 0 || n > 3999) {
                        System.out.println("ERROR!");
                        System.out.println("Это число не входит в заданный интервал!");
                    } else System.out.println("Получившееся римское число: " + toRoman(n));
                }
                case (9) -> {
                    boolean res;
                    System.out.println("Введите строку: ");
                    str = in.nextLine();
                    str = in.nextLine();
                    res = formula(str);
                    System.out.println("Формула верна? " + res);
                }
                case (10) -> {
                    System.out.println("Введите число:");
                    n = in.nextInt();
                    System.out.println("Один из потомков числа палиндром? " + isDescendantPalindrome(n));
                }
            }
                Thread.sleep(1000);
        }
    }
    public static int factorial(int n) {
        int res = 1;
        while (n > 0) {
            res *= n;
            n--;
        }
        return res;
    }

    public static int numOfComb(int n, int k) {
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    public static int numBell(int n) {
        int res = 0;
        if (n == 0) return 1;
        for (int i = n - 1; i >= 0; i--)
            res += numOfComb(n - 1, i) * numBell(i);
        return res;
    }

    public static String translateWord(String str) {
        String res = str;
        String subStr;
        Pattern p = Pattern.compile("^[b-df-hj-np-tv-xzB-DF-HJ-NP-TV-XZ]+");
        Matcher m = p.matcher(str);
        if (m.find()) {
            subStr = m.group();
            res = res.replaceFirst(subStr,"");
            if (isFirstCapitalLetter(subStr)) {
                res = firstToUpper(res);
                subStr = firstToLower(subStr);
            }
            res = res + subStr + "ay";
        }
        else{
            p = Pattern.compile("^[a|e|i|o|u|y|A|E|I|O|U|Y]");
            m = p.matcher(str);
            if (m.find())
                res = res + "yay";
            else
                return ("Введенные данные неверны");
        }
        return res;

    }

    public static String translateSentence(String str) {
        String res = "";
        String [] subStr;
        String [] betweenWords;
        betweenWords = str.split("\\w+");
        subStr = str.split("\\W+");
        for (int i = 0; i < subStr.length; i++)
            res = res + betweenWords[i] + translateWord(subStr[i]);
        if (betweenWords.length > subStr.length)
            res = res + betweenWords[betweenWords.length - 1];
        return res;
    }


    public static boolean isFirstCapitalLetter(String str) {
        Pattern p = Pattern.compile("^[A-Z]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static String firstToUpper(String str){
        char c = str.charAt(0);
        String firstChar = Character.toString(c);
        str = str.replaceFirst(firstChar,firstChar.toUpperCase());
        return str;
    }

    public static String firstToLower(String str){
        char c = str.charAt(0);
        String firstChar = Character.toString(c);
        str = str.replaceFirst(firstChar,firstChar.toLowerCase());
        return str;
    }

    public static String getHashTags(String str){
        String res = "";
        int maxInd1 = 0;
        int maxInd2 = 0;
        int maxInd3 = 0;
        int maxLen1 = 0;
        int maxLen2 = 0;
        int maxLen3 = 0;
        String[] subStr = str.split("\\s");
        if (subStr.length > 2){
            for(int i = 0; i < subStr.length; i++){
                if(subStr[i].length() > maxLen1) {
                    maxInd3 = maxInd2;
                    maxInd2 = maxInd1;
                    maxInd1 = i;
                    maxLen3 = maxLen2;
                    maxLen2 = maxLen1;
                    maxLen1 = subStr[i].length();
                }
                else
                    if(subStr[i].length() > maxLen2){
                        maxInd3 = maxInd2;
                        maxInd2 = i;
                        maxLen3 = maxLen2;
                        maxLen2 = subStr[i].length();
                    }
                    else
                        if(subStr[i].length() > maxLen3) {
                            maxInd3 = i;
                            maxLen3 = subStr[i].length();
                        }
            }
            res = res + "\"#" + subStr[maxInd1].toLowerCase() + "\", \"#" + subStr[maxInd2].toLowerCase() + "\", \"#" + subStr[maxInd3].toLowerCase() + "\"";
        }
        else
            if(subStr.length == 2) {
                if (subStr[0].length() > subStr[1].length())
                    maxInd2 = 1;
                else
                    maxInd1 = 1;
                res = res + "\"#" + subStr[maxInd1].toLowerCase() + "\", \"#" + subStr[maxInd2].toLowerCase() + "\"";
            }
            else
                res = res + "\"#" + subStr[maxInd1].toLowerCase() + "\"";
        return res;
    }

    public static int numOfUlam(int n) {
        int[] array = new int[n];
        array[0] = 1;
        if (n > 1)
            array[1] = 2;
        int length = 2;
        int currentSum = 3;
        while (length < n) {
            int coincidence = 0;
            for (int i = 0; i < length; i++) {
                for (int j = length - 1; j > i; j--) {
                    if (currentSum == array[i] + array[j])
                        coincidence++;
                    else if (coincidence > 1) break;
                }
                if (coincidence > 1) break;
            }
            if (coincidence == 1) {
                array[length] = currentSum;
                length++;
            }
            currentSum++;
        }
        return array[n - 1];
    }

    public static boolean formula (String str){
        String[] array = str.split(" ");
        Integer res1 =  Integer.parseInt(array[0]);
        Integer answer;
        Integer res2;
        boolean result;
        int i;
        for (i = 1; !array[i].equals("="); i+=2) {
            if (array[i].equals("+")) res1 += Integer.parseInt(array[i + 1]);
            if (array[i].equals("-")) res1 -= Integer.parseInt(array[i + 1]);
            if (array[i].equals("*")) res1 *= Integer.parseInt(array[i + 1]);
            if (array[i].equals("/")) res1 /= Integer.parseInt(array[i + 1]);
        }
        answer = Integer.parseInt(array[i + 1]);
        res2 =  Integer.parseInt(array[array.length - 1]);
        for (i = array.length - 2; !array[i].equals("="); i-=2) {
            if (array[i].equals("+")) res2 += Integer.parseInt(array[i - 1]);
            if (array[i].equals("-")) res2 -= Integer.parseInt(array[i - 1]);
            if (array[i].equals("*")) res2 *= Integer.parseInt(array[i - 1]);
            if (array[i].equals("/")) res2 /= Integer.parseInt(array[i - 1]);
        }
        return res1.equals(res2) && res1.equals(answer);
    }

    public static String isDescendantPalindrome(int n){
        int k = n;
        int count = 0;
        while (k != 0){
            k/=10;
            count++;
        }
        String str = "" + n;
        if (isPalindrome(str)) return "Само число палиндром";
        int[] array = new int[10];
        for (int i = 0; i < count; i ++){
            array[i] = n%10;
            n/=10;
        }
        while (count % 2 == 0 && count > 2){
            for (int i = 0; i < count; i += 2) {
                array[i / 2] = array[i] + array[i + 1];
                str += array[i / 2];
            }
            count/=2;
            if (isPalindrome(str)) return "Да";
            str = "";
        }
        if (isPalindrome(str)) return "Да";
        return "Нет";
    }

    public static String reverseString(String s)//метод для переворачивания слова
    {
        String res="";//создаем переменную для записи перевернутого слова
        for(int i=s.length()-1; i>=0; i--)//находим количество букв в слове
            res+=s.charAt(i);//записываем буквы с конца
        return (res);//возвращаем перевернутое слово
    }

    public static boolean isPalindrome(String s)//метод для проверки на "полиндромность"
    {
        return s.equals(reverseString(s));//возвращаем результат сравнение слова и результата переворачивающей функции
    }

    public static String longestNonrepeatingSubstring(String str){
        String res = "";
        String[] charStr = str.split("");
        String subSting = "";
        int maxLength = 0;
        int currentLength = 0;
        for(int i = 0; i < str.length() - 1; i++) {
            for(int j = i; j < str.length() && !subSting.contains(charStr[j]); j++){
                subSting += charStr[j];
                currentLength++;
            }
            if (currentLength > maxLength){
                res = "" + subSting;
                maxLength = currentLength;
            }
            currentLength = 0;
            subSting = "";
        }
        return res;
    }

    public static boolean isValidColor(String str){
        String[] subStr = new String[4];
        int a;
        float f;
        if (str.substring(0,4).equals("RGBA") || str.substring(0,4).equals("rgba")) {
            str = str.substring(5,str.length()-1);
            subStr = str.split(",");
            if(subStr.length == 4) {
                for (int i = 0; i < 3; i++)
                    try {
                        a = Integer.parseInt(subStr[i]);
                    } catch (NumberFormatException nfe) {
                        return false;
                    }
                for (int i = 0; i < 3; i++) {
                    if (Integer.parseInt(subStr[i]) < 0 || Integer.parseInt(subStr[i]) > 255)
                        return false;
                }
                try {
                    f = Float.parseFloat(subStr[3]);
                } catch (NumberFormatException nfe) {
                    return false;
                }
                if (Float.parseFloat(subStr[3]) >= 0 && Float.parseFloat(subStr[3]) <= 1)
                    return true;
            }
            else return false;
        }
        else if (str.substring(0,3).equals("RGB") || str.substring(0,3).equals("rgb")) {
            str = str.substring(4,str.length()-1);
            subStr = str.split(",");
            if(subStr.length == 3) {
                for (int i = 0; i < 3; i++)
                    try {
                        a = Integer.parseInt(subStr[i]);
                    } catch (NumberFormatException nfe) {
                        return false;
                    }
                for (int i = 0; i < 3; i++)
                    if (Integer.parseInt(subStr[i]) > 0 && Integer.parseInt(subStr[i]) < 255)
                        return true;
            }
            else return false;
        }
        return false;
    }

    public static String stripURLParams(String str, String[] param) {
        String url = "";
        String params = "";
        if (!str.contains("?"))
            return str;
        else {
            params = str.substring(str.indexOf("?") + 1);
            url = str.substring(0, str.indexOf("?") + 1);
        }
        String[] arrayParams = params.split("&");
        params = "";
        for (int i = 0; i < arrayParams.length - 1; i++) {
            for (int k = 0; k < param.length; k++)
                if (arrayParams[i].substring(0, 1).equals(param[k])) {
                    arrayParams[i] = null;
                    break;
                }
            if (arrayParams[i] != null) {
                for (int j = i + 1; j < arrayParams.length - 1; j++) {
                    if (arrayParams[j].substring(0, 1).equals(arrayParams[i].substring(0, 1))) {
                        arrayParams[i] = arrayParams[j];
                        arrayParams[j] = null;
                    }
                }
                params += arrayParams[i] + "&";
            }
        }
        if (params.length() != 0)
            return (url + params.substring(0, params.length() - 1));
        else
            return (url.substring(0, url.length() - 1));
    }

    public static String stripURLParams(String str){
        String url = "";
        String params = "";
        if (!str.contains("?"))
            return str;
        else {
            params = str.substring(str.indexOf("?") + 1);
            url = str.substring(0, str.indexOf("?") + 1);
        }
        String[] arrayParams = params.split("&");
        params = "";
        for(int i = 0; i < arrayParams.length - 1; i++){
            for(int j = i + 1; j < arrayParams.length - 1; j++){
                if(arrayParams[j].substring(0, 1).equals(arrayParams[i].substring(0, 1))){
                    arrayParams[i] = arrayParams[j];
                    arrayParams[j] = null;
                }
            }
            if(arrayParams[i] != null)
                params += arrayParams[i] + "&";
        }
        return (url + params.substring(0,params.length() - 1));
    }

    public static String romanDigit(int n, String one, String five, String ten){
        if(n >= 1)
        {
            if(n == 1)
            {
                return one;
            }
            else if (n == 2)
            {
                return one + one;
            }
            else if (n == 3)
            {
                return one + one + one;
            }
            else if (n==4)
            {
                return one + five;
            }
            else if (n == 5)
            {
                return five;
            }
            else if (n == 6)
            {
                return five + one;
            }
            else if (n == 7)
            {
                return five + one + one;
            }
            else if (n == 8)
            {
                return five + one + one + one;
            }
            else if (n == 9)
            {
                return one + ten;
            }
        }
        return "";
    }

    public static String toRoman(int number){
        String romanOnes = romanDigit( number%10, "I", "V", "X");
        number /=10;
        String romanTens = romanDigit( number%10, "X", "L", "C");
        number /=10;
        String romanHundreds = romanDigit(number%10, "C", "D", "M");
        number /=10;
        String romanThousands = romanDigit(number%10, "M", "", "");
        return romanThousands + romanHundreds + romanTens + romanOnes;
    }

}