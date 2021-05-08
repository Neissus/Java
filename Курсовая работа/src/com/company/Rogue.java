package com.company;
//класс, реализующий поведение разбойников
public final class Rogue {
    //поля для значений имени, здоровья, брони, силы удара
    private final String name;
    private int health;
    private int armor;
    private final int force;

    //конструктор для инициализации разбойников по сложности
    Rogue(int val) {
        //массив имен врагов
        String[] nameVersions = new String[3];
        nameVersions[0] = "Разбойник";
        nameVersions[1] = "Сильный разбойник";
        nameVersions[2] = "Главарь разбойников";
        //устанавливаем имя врага в зависимости от переданной сложности
        if (val == 1)
            name = nameVersions[0];
        else if (val == 2)
            name = nameVersions[1];
        else
            name = nameVersions[2];
        //устанавливаем значения для полей класса в зависимости от сложности
        health = 2 * val;
        armor = val;
        force = val;
    }

    //метод, реализующий получение урона противником
    public void takeDamage(int damage) {
        if (damage > armor) {
            health = health - (damage - armor);
            armor = 0;
            if (health < 0) health = 0;
        } else
            armor -= damage;
    }

    //метод, реализующий ход разбойника
    public int turn(int val) {
        //если значение на кубике меньше 3, то увеличивает значение брони
        if (val < 3) {
            riseArmor(val);
            return 0;
        }
        //иначе атакует
        else
            return force;
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

