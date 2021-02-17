package com.company;
import java.util.Scanner;//подключаем класс сканера для получения информации с клавиатуры

public class Palindrome
{
    public static void main(String[] args)
    {
        String n="";//создаем переменную для записи отдельных слов, записывем пустую строку
        char w;//создаем переменную для записи отдельных букв
        Scanner in = new Scanner(System.in);//создаем переменную типа Scanner для использования методов этого класса
        System.out.println("Введите слова одной строкой через пробел");
        String s = in.nextLine();//считываем введенную строку
        if(s.length() != 0)//если строка непустая, то
        {
            //проходим ко всем символам
            for (int i = 0; i < s.length(); i++)
            {
                w = s.charAt(i);//записываем символ в переменную
                if (w == ' ')//если символ является пробелом (слово закончилось)
                {
                    System.out.println(n);//выводим слово
                    if(isPalindrome(n))//проверяем является ли слово полиндромом
                        System.out.println('+');//если да, то пишем +
                    else
                        System.out.println('-');//иначе -
                    n = "";//очищаем переменную со словом
                }
                else//если символ не пробел, то добавляем его к слову
                    n+=w;
            }
            //так как после последнего слова пробела нет, его нужно проверить отдельно
            System.out.println(n);
            if(isPalindrome(n))
                System.out.println('+');
            else
                System.out.println('-');
        }
    }

    public static String reverseString(String s)//метод для переворачивания слова
    {
        String n="";//создаем переменную для записи перевернутого слова
        for(int i=s.length()-1; i>=0; i--)//находим количество букв в слове
            n+=s.charAt(i);//записываем буквы с конца
        return (n);//возвращаем перевернутое слово
    }

    public static boolean isPalindrome(String s)//метод для проверки на "полиндромность"
    {
        return s.equals(reverseString(s));//возвращаем результат сравнение слова и результата переворачивающей функции
    }
}
