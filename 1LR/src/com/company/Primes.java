package com.company;

public class Primes
{
    public static void main(String[] args)
    {

        for(int i = 2; i<=100; i++)//проходим по всему интервалу, на котором ищем простые числа
            if(isPrime(i)) //проверяем, является ли число простым
            {
                System.out.print(i);//если да то выводим
                System.out.print(' ');
            }
    }

    public static boolean isPrime(int n)//метод для проверки, является ли число простым
    {
        for(int i=2; i<n; i++)//проходим по всем числам меньше проверяемого числа
            if(n%i==0) return false;//если на какое-то из них делится без остатка, то оно уже непростое
        return true;//если нет ни одного делителя, то простое
    }
}
