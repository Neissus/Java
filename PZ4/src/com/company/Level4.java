package com.company;


import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

public class Level4 {

    public static void main(String[] args) throws InterruptedException {
        String str;
        int n;
        int a;
        int b;
        char s;
        String string;
        float [] arr;
        int choice = -1;
        Scanner in = new Scanner(System.in);
        while(choice != 0) {
            System.out.println("Выберите какой метод использовать:");
            System.out.println(" 1 - Работа с эссе");
            System.out.println(" 2 - Кластер скобок");
            System.out.println(" 3 - Camel & Snake");
            System.out.println(" 4 - Калькулятор заработка");
            System.out.println(" 5 - Индекс массы тела");
            System.out.println(" 6 - Найти мультипликативное постоянство");
            System.out.println(" 7 - Звездная стенография");
            System.out.println(" 8 - Рифма");
            System.out.println(" 9 - Работа с числами и их повторениями");
            System.out.println("10 - Одинаковые символы");
            System.out.println(" 0 - Выход");
            choice = in.nextInt();
            switch (choice) {
                case (1) -> {
                    System.out.println("Введите n, k,");
                    a = in.nextInt();
                    b = in.nextInt();
                    System.out.println("Введите строку");
                    str=in.nextLine();
                    str = in.nextLine();
                    System.out.println(Essay(a,b,str));
                }
                case (2) -> {
                    System.out.println("Введите строку:");
                    str = in.next();
                    System.out.println("Исправленная строка:"+Split(str));
                }
                case (3) -> {
                    System.out.println("Введите строку");
                    str=in.next();
                    System.out.println("Какой метод использовать: 1-Camel, 2-Snake");
                    n=in.nextInt();
                    if (n==1)
                        System.out.println("Измененная строка "+toCamelCase(str));
                    else if (n==2)
                        System.out.println("Измененная строка "+toSnakeCase(str));
                    else
                        System.out.println("ERROR");
                }
                case (4) -> {

                    arr = new float[4];
                    System.out.println("Введите массив (начало, конец раб дня, ставка, множитель):");
                    for (int i = 0; i < 4; i++)
                        arr[i] = in.nextFloat();
                    if(arr[1]<=17)
                        System.out.println((arr[1]-arr[0])*arr[2]);
                    else if (arr[1]>17) System.out.println((17-arr[0])*arr[2]+(arr[1]-17)*arr[2]*arr[3]);
                }
                case (5) -> {
                    str="";
                    arr = new float[2];
                    System.out.println("Введите Вес (единица измерения через пробел: k-kg,p-pounds): ");
                    arr[0] = in.nextFloat();
                    str+=in.next();
                    System.out.println("Введите Рост (единица измерения через пробел: m-meters,i-inches):");
                    arr[1] = in.nextFloat();
                    str+=in.next();
                    System.out.println("Ваш индекс массы тела: "+BMI(arr,str));
                }
                case (6) -> {
                    System.out.println("Введите число:");
                    n= in.nextInt();
                    System.out.println("Мультипликативное постоянство:"+Bugger(n));
                }
                case (7) -> {
                    System.out.println("Введите строку");
                    str = in.nextLine();
                    str = in.nextLine();
                    System.out.println("Измененная строка " + ToStarShorthand(str));
                }
                case (8) -> {

                    System.out.println("Введите первую строку");
                    str = in.nextLine();
                    str = in.nextLine();
                    System.out.println("Введите вторую строку");
                    string = in.nextLine();
                    System.out.println("Строки рифмуются? " + DoesRhyme(str,string));
                }
                case (9) -> {
                    System.out.println("Введите первое число ");
                    a=in.nextInt();
                    System.out.println("Введите второе число ");
                    b=in.nextInt();
                    System.out.println(Trouble(a,b));
                }
                case (10) -> {
                    System.out.println("Введите строку");
                    str = in.next();
                    System.out.println("Введите символ-фильтр");
                    s = in.next().charAt(0);
                    System.out.println("Количество уникальных символов: "+ CountUniqueBooks(str,s));
                }
            }
            Thread.sleep(1000);
        }
    }

    public static int CountUniqueBooks(String str,char s){
        HashMap<Character, Integer> values = new HashMap<>();
        boolean Check = true;
        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i) == s && Check) {
                i++;
                while (str.charAt(i) != s){
                    Integer n = values.get(str.charAt(i));
                    if (n == null)
                        values.put(str.charAt(i), 1);
                    else
                        values.put(str.charAt(i), ++n);
                    i++;
                    // System.out.println(values);
                }
                Check = false;
            }
            if (str.charAt(i) == s)
                Check = true;
        }

        return values.size();
    }


    public static String  Split(String str){
        StringBuilder string= new StringBuilder("['");
        int n=0;
        for (int i=0;i<str.length();i++) {
            if (str.charAt(i) == '(') n++;
            if (str.charAt(i) == ')') n--;
        }
        if(n==0) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '(') {
                    n++;
                    string.append(str.charAt(i));
                }
                if (str.charAt(i) == ')') {
                    n--;
                    if ((n == 0)&&i!=str.length()-1) string.append(str.charAt(i)).append("', '");
                    else if((n == 0)&&i==str.length()-1) string.append(str.charAt(i)).append("'");
                    else string.append(str.charAt(i));
                }
            }
            string.append("]");
        } else return "error";
        return string.toString();
    }

    public static String Essay(int n, int k , String str){
        String[] string = str.split(" ");
        str="";
        StringBuilder finalist= new StringBuilder();
        StringBuilder strBuilder = new StringBuilder(str);
        for (int i = 0; i < n; i++) {
            if (strBuilder.length() + string[i].length() > k) {
                finalist = new StringBuilder(finalist.toString().trim() + "\r\n" + string[i] + " ");
                strBuilder = new StringBuilder(string[i]);
            } else {
                finalist.append(string[i]).append(" ");
                strBuilder.append(string[i]);
            }
        }
        return finalist.toString().trim();
    }


    public static int Bugger(int n){
        int p;
        int x;
        int check=0;
        while (n>9) {
            p=1; x=n;
            while (x>0) {
                p*=x%10;
                x/=10;
            }check++;
            n=p;
        }
        return check;
    }

    public static boolean DoesRhyme(String str1, String str2) {
        str1 = str1.substring(str1.lastIndexOf(" ") + 1);
        str2 = str2.substring(str2.lastIndexOf(" ") + 1);
        String check = "aeiouyAEIOUY";
        StringBuilder rez1 = new StringBuilder();
        StringBuilder rez2 = new StringBuilder();
        for (int i = 0; i < str1.length(); i++) {
            if ( check.indexOf(str1.charAt(i)) != -1)
                rez1.append(str1.charAt(i));
        }
        for (int i = 0; i < str2.length(); i++) {
            if (check.indexOf(str2.charAt(i)) != -1)
                rez2.append(str2.charAt(i));
        }
        return (rez1.toString().equalsIgnoreCase(rez2.toString()));
    }

    public static boolean Trouble(int n1,int n2){
        int check=0;
        int count=1;
        while(n1!=0) {
            if (n1 % 10 == ((n1 / 10) % 10)) {
                check = n1 % 10;
                count++;
            } else if (count != 3) count = 1;
            n1 /= 10;
        }
        if (count==3){
            while (n2!=0){
                if (((n2%10)==((n2/10)%10))&&((n2%10)!=((n2/100)%10))&&(n2%10==check)) return true;
                n2/=10;
            }
        }
        return false;
    }

    public static String BMI(float []n,String str) {
        DecimalFormat df=new DecimalFormat("###.#");
        if(str.charAt(0)=='p') n[0]*=0.453592;
        if(str.charAt(1)=='i') n[1]*=0.0254;
        System.out.println(df.format(n[0]/(n[1]*n[1])));
        if ((n[0]/(n[1]*n[1]))<18.5) return "Underweight";
        else if ((n[0]/(n[1]*n[1]))>=25) return "Overweight";
        return "Normal weight";
    }

    public static String ToStarShorthand(String str) {
        int count = 1;
        char sim = str.charAt(0);
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != sim) {
                if (count != 1)
                    string.append(sim).append("*").append(count);
                else
                    string.append(sim);
                sim = str.charAt(i);
                count = 1;
            } else
                count++;
        }
        if (count != 1)
            string.append(sim).append("*").append(count);
        else
            string.append(sim);
        return string.toString();
    }


    public static String toCamelCase(String str) {
        char currentChar;
        char previousChar = '\u0000';
        StringBuilder result = new StringBuilder();
        boolean firstLetterArrived = false;
        boolean nextLetterInUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            currentChar = str.charAt(i);
            if (!Character.isLetterOrDigit(currentChar) || (
                    ((Character.isLetter(previousChar) && Character.isLowerCase(previousChar)) || Character.isDigit(previousChar)) &&
                            Character.isLetter(currentChar) && Character.isUpperCase(currentChar))){
                nextLetterInUpperCase = true;
                if (!Character.isLetterOrDigit(currentChar)) {
                    previousChar = currentChar;
                    continue;
                }
            }
            if (nextLetterInUpperCase && firstLetterArrived) {
                result.append(Character.toUpperCase(currentChar));
            }
            else {
                result.append(Character.toLowerCase(currentChar));
            }
            firstLetterArrived = true;
            nextLetterInUpperCase = false;
            previousChar = currentChar;
        }
        return result.toString();
    }

    private static String toSnakeCase(String str) {
        if (str == null) return null;
        int length = str.length();
        StringBuilder result = new StringBuilder(length * 2);
        int resultLength = 0;
        boolean wasPrevTranslated = false;
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            if (i > 0 || c != '_')
            {
                if (Character.isUpperCase(c)) {
                    if (!wasPrevTranslated && resultLength > 0 && result.charAt(resultLength - 1) != '_') {
                        result.append('_');
                        resultLength++;
                    }
                    c = Character.toLowerCase(c);
                    wasPrevTranslated = true;
                } else {
                    wasPrevTranslated = false;
                }
                result.append(c);
                resultLength++;
            }
        }
        return resultLength > 0 ? result.toString() : str;
    }
}