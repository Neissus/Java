package com.company;

public class Point3d {
    //поля для записи координат
    private double xCoord;
    private double yCoord;
    private double zCoord;
    //конструктор по умолчанию
    public Point3d() {
        xCoord = 0.0;
        yCoord = 0.0;
        zCoord = 0.0;
    }
    //конструктор инициализации
    public Point3d(double x,double y, double z) {
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }
    //получение координаты X
    public double getX() { return xCoord; }

    //получение координаты Y
    public double getY() { return yCoord; }

    //получение координаты Z
    public double getZ() { return zCoord; }

    //изменение координаты X
    public void setX(double x) { xCoord = x; }

    //изменение координаты Y
    public void setY(double y) { yCoord = y; }

    //изменение координаты Z
    public void setZ(double z) { zCoord = z; }

    //сравнение 2 точек
    public boolean equalPoints(Point3d a){
        return xCoord == a.xCoord & yCoord == a.yCoord & zCoord == a.zCoord;
    }

    //вычисление расстояния до заданной точки
    public Double distanceTo(Point3d a){
        double dist = Math.sqrt(Math.pow((xCoord - a.xCoord), 2) + Math.pow((yCoord - a.yCoord), 2) + Math.pow((zCoord - a.zCoord), 2));
        return Math.ceil(dist*100)/100;//округление до 2 знаков после запятой
    }
}
