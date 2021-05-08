package com.company;
//класс реализующий поведение главного персонажа
public final class Hero {
    //поля для значений здоровья, брони, силы удара
    private final String name;
    private int health;
    private int armor;
    private final int force;

    //конструктор для инициализации персонажа с его именем
    Hero(String name) {
        //устанавливаем значения для полей класса
        this.name = name;
        health = 4;
        armor = 1;
        force = 3;
    }

    //метод, реализующий получение урона
    public void takeDamage(int damage) {
        if (damage > armor) {
            health = health - (damage - armor);
            armor = 0;
            if (health < 0) health = 0;
        }
        else
            armor -= damage;
    }

    //метод, реализующий атаку персонажа
    public int hit(int val) {
        if (val > 2) {
            return force;
        }
        return 0;
    }

    //метод реализующий увеличение брони
    public void riseArmor(int val) { armor += val; }
    //метод возвращающий имя игрока
    public String getName() { return name; }
    //метод возвращающий значение здоровья игрока
    public int getHealth() { return health; }
    //метод возвращающий значение брони игрока
    public int getArmor() { return armor; }

}
