package com.company;

public final class Complex {
    private double re;
    private double im;
    //конструктор по умолчанию
    public Complex (){
        re = 0;
        im = 0;
    }

    //конструктор инициализации
    public Complex (double x, double y){
        re = x;
        im = y;
    }

    /**
     * метод возвращает квадрат модуля данного комплексного числа
     */
    public double sqrAbsoluteValue (){
        return re*re+im*im;
    }

    public Complex sqrValue (){
        Complex z = new Complex(re,im);
        z.re = re*re - im*im;
        z.im = 2*re*im;
        return z;
    }

    public Complex add (Complex z){
        z.re += re;
        z.im += im;
        return z;
    }

    public void coupling(){
        re = -re;
    }

    public void absolutComponent(){
        if (re < 0)
            re = -re;
        if (im < 0)
            im = -im;
    }
}
