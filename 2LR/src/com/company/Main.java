package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //создаем переменную для считывания информации
        Scanner in = new Scanner(System.in);
        //создаем переменные для записи координат
        Point3d A = new Point3d();
        Point3d B = new Point3d();
        Point3d C = new Point3d();
        //создаем переменную для изменения данных
        int t = 1;
        //цикл для повторения рассчета
        while(t != 0) {
            System.out.println("Для рассчета площади треугольника введите координаты 3 точек.");
            System.out.println("Координаты точки A:");
            A.setX(in.nextDouble());
            A.setY(in.nextDouble());
            A.setZ(in.nextDouble());
            System.out.println("Координаты точки B:");
            B.setX(in.nextDouble());
            B.setY(in.nextDouble());
            B.setZ(in.nextDouble());
            System.out.println("Координаты точки C:");
            C.setX(in.nextDouble());
            C.setY(in.nextDouble());
            C.setZ(in.nextDouble());

            /*//проверка совпадения координат
            if (A.equalPoints(B) | A.equalPoints(C) | B.equalPoints(C))
                System.out.println("Некоторые точки совпадают, площадь равна 0");
            else
                System.out.println("Площадь треугольника равна " + computeArea(A, B, C));*/

            //проверка, находятся ли точки на одной прямой (универсальнее чем проверять совпадение координат)
            if (((C.getX() - A.getX()) / (B.getX() - A.getX()) == ((C.getY() - A.getY()) / (B.getY() - A.getY()))) & (((C.getY() - A.getY()) / (B.getY() - A.getY())) == ((C.getZ() - A.getZ()) / (B.getZ() - A.getZ()))))
                System.out.println("Точки находятся на одной прямой, площадь равна 0");
            else
                System.out.println("Площадь треугольника равна " + computeArea(A, B, C));
            System.out.println("Повторить? (0/1)");
            t = in.nextInt();
        }
    }
    //метод для рассчета площади треугольника по формуле Герона
    public static double computeArea(Point3d A, Point3d B, Point3d C){
        //переменная для записи значения площади
        double s;
        //переменная для записи значения полупериметра
        double p;
        //рассчет полупериметра треугольника
        p = (A.distanceTo(B) + A.distanceTo(C) + B.distanceTo(C))/2;
        //расчет площади треугольника по формуле Герона
        s = Math.sqrt(p * (p - A.distanceTo(B))*(p - A.distanceTo(C))*(p - B.distanceTo(C)));
        return s;
    }
}
