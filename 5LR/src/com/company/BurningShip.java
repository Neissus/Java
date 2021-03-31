package com.company;

import java.awt.geom.Rectangle2D;
/**
 * Класс для вычисления фрактала BurningShip
 */
public class BurningShip extends FractalGenerator{
    //константа с максимальным количеством итераций
    public static final int MAX_ITERATIONS = 2000;
    /**
     * Метод позволяет генератору фракталов определить наиболее
     * «интересную» область комплексной плоскости для конкретного фрактала
     */
    public void getInitialRange(Rectangle2D.Double range){
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
    }
    /**
     * Метод реализует итеративную функцию для фрактала Мандельброта,
     * возвращает количество итераций или -1, если количество итераций
     * достигло максимума.
     */
    public int numIterations(double x, double y)
    {
        //создаем переменную, в которую запишем количество итераций
        int i;
        //инициализируем комплексное число равное 0
        Complex z = new Complex ();
        //создаем комплексную переменную для определенной точки фрактала,
        // которую мы отображаем на экране
        Complex c = new Complex (x,y);
        /*
          Вычисляем фрактал по функции Zn = Zn-1^2+с,
          где все значения — это комплексные числа, z0 = 0.
          Вычисления повторяются до тех пор, пока |z| > 2
          или пока число итераций не достигнет максимального значения.
         */
        for (i = 0; i < MAX_ITERATIONS && z.sqrAbsoluteValue() < 4; i++) {
            //каждый компонент комплексного числа берем по модулю
            z.absolutComponent();
            z = c.add(z.sqrValue()); //z = c + z^2
        }
        //если колличество повторений достигло заданного максимума, то возвращаем -1
        if (i == MAX_ITERATIONS) return -1;
        //возвращаем количество итераций
        return i;
    }
    public String toString() {
        return "BurningShip";
    }
}
