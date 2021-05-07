package com.company;

import java.util.Scanner;
public class Game {

    public static void main(String[] args) throws InterruptedException {
        //поле для обработки данных о персонаже
        Hero hero;
        //поле для обработки данных о противнике
        Rogue rogue;
        //переменная для определения, чей ход
        int turn = 0;
        //переменная для записи, значения выпавшего на кубике
        int valueDice;
        //переменная, содержащая сложность противника
        int rogueDifficulty = 1;
        //строковая переменная (вспомогательная)
        String str;
        Scanner in = new Scanner(System.in);
        System.out.println("Добро пожаловать в игру \"Герои и разбойники!\"");
        System.out.println("Введите имя вашего игрока: ");
        str = in.next();
        hero = new Hero(str);
        System.out.println("Игра начинается...");
        Thread.sleep(1000);
        System.out.println("Вы знаменитый рыцарь, король вызвал Вас в тронный зал и обращается к Вам...");
        Thread.sleep(2000);
        System.out.println("Король: Сэр, " + hero.getName() + " на наше королевство напала банда разбойников, разберитесь с ними.");
        Thread.sleep(2000);
        System.out.println(hero.getName() + ": Я выгоню этих проходимцев с земель Вашего Величества или погибну в бою!");
        Thread.sleep(2000);
        System.out.println("Король: Возьми эти доспехи, они защищат тебя в бою.");
        hero.riseArmor(2);
        Thread.sleep(1500);
        System.out.println("Ваша броня увеличена на 2, значение вашей брони равно " + hero.getArmor() + "...");
        Thread.sleep(1500);
        System.out.println(hero.getName() + ": Ваше Величество, Вы очень великодушны, я отправлюсь в поход незамедлительно.");
        Thread.sleep(2000);
        System.out.println("Вы выходите из замка в поисках разбойников и сразу попадаете в засаду.");
        //пока мы не победили всех врагов, и игрок жив, игра продолжается
        while (rogueDifficulty > 0 && rogueDifficulty < 4) {
            //инициализируем врага с соответствующей сложностью
            rogue = new Rogue(rogueDifficulty);
            System.out.println("На вас напал " + rogue.getName() + "...");
            Thread.sleep(2000);
            //пока здоровье разбойника  и игрока больше 0 будем с ним драться,
            while (rogue.getHealth() > 0 && hero.getHealth() > 0) {
                //повторяем до тех пор пока ход игрока и противник не убит
                do {
                    //ждем пока игрок правильно введет название хода
                    do {
                        System.out.println("Ваш ход... (удар/блок)");
                        str = in.next();
                    } while (!checkInput(str));
                    //если игрок ввел команду "удар", то вызываем метод, реализующий атаку
                    if (str.equals("удар")) {
                        System.out.println("Бросок кубика...");
                        valueDice = rollTheDice();
                        Thread.sleep(1000);
                        System.out.println("Выпало число = " + valueDice + "...");
                        Thread.sleep(1000);
                        rogue.takeDamage(hero.hit(valueDice));
                        System.out.println("Вы нанесли " + hero.hit(valueDice) + " урона...");
                        Thread.sleep(1500);
                        System.out.println("Броня противника = " + rogue.getArmor() + ", здоровье противника = " + rogue.getHealth());
                        Thread.sleep(1500);
                        //если игрок не нанес урона, ход переходит противнику
                        if (hero.hit(valueDice) == 0) turn = 1;
                    }
                    //если игрок ввел команду "блок", то вызываем соответствующий метод, реализующий увеличение брони
                    else{
                        System.out.println("Бросок кубика...");
                        valueDice = rollTheDice();
                        Thread.sleep(1000);
                        System.out.println("Выпало число = " + valueDice + "...");
                        Thread.sleep(1000);
                        hero.riseArmor(valueDice);
                        System.out.println("Ваша броня увеличена на " + valueDice);
                        Thread.sleep(1500);
                        System.out.println("Ваша броня  = " + hero.getArmor() + ", ваше здоровье = " + hero.getHealth());
                        Thread.sleep(1500);
                        turn = 1;
                    }
                }while (turn == 0 && rogue.getHealth() > 0);
                //если враг повержен, то выходим из цикла
                if (rogue.getHealth() < 1) break;
                //после перехода хода кубик бросается и враг совершает одно из действий (атаковать/увеличить броню)
                System.out.println("Ход переходит к " + rogue.getName() + "...");
                System.out.println("Бросок кубика...");
                valueDice = rollTheDice();
                Thread.sleep(1000);
                System.out.println("Выпало число = " + valueDice + "...");
                Thread.sleep(1000);
                //если очков на атаку не хватило, то противник увеличивает броню
                if (rogue.turn(valueDice) == 0){
                    System.out.println("Броня противника увеличена на " + valueDice);
                    Thread.sleep(1500);
                    System.out.println("Броня противника = " + rogue.getArmor() + ", здоровье противника = " + rogue.getHealth());
                    Thread.sleep(1500);
                }
                //при достаточном количестве очков, враг атакует
                else {
                    System.out.println("Разбойник атакует с силой " + rogue.turn(valueDice));
                    hero.takeDamage(rogue.turn(valueDice));
                    Thread.sleep(1500);
                    System.out.println("Ваша броня  = " + hero.getArmor() + ", ваше здоровье = " + hero.getHealth());
                    Thread.sleep(1500);
                }
                //если игрок жив, то ход переходит к нему вне зависимости от хода противника
                if (hero.getHealth() > 0) {
                    turn = 0;
                    System.out.println("Ход переходит к " + hero.getName() + "...");
                    Thread.sleep(1500);
                }
            }
            //если игрок погиб, то выходим из цикла обнулив сложность
            if (hero.getHealth() < 0) rogueDifficulty = 0;
            //если игрок выжил (он победил предыдущего противника), то увеличиваем сложность
            else {
                rogueDifficulty++;
                //если противник побежден, выводим сообщение о победе
                if (rogueDifficulty < 4) {
                    System.out.println("Вы победили, продвигайтесь дальше...");
                    Thread.sleep(3000);
                    //если побежден Разбойник, то выводим подводку к Сильному разбойнику
                    if (rogueDifficulty == 2)
                        System.out.println("Вы идете в глубь леса, и на вашем пути возникает незнакомец...");
                    //если побежден Сильный разбойник, то выводим подводку к Главарю разбойников
                    else
                        System.out.println("Выйдя на поляну, Вы наткнулись на лагерь разбойников, в котором...");
                    Thread.sleep(1500);
                }
            }
        }
        //если игрок, погиб, то выводим сообщение о проигрыше и предлагаем сыграть снова
        if (hero.getHealth() < 0) System.out.println("К сожалению, вы проиграли, перезапустите игру и попробуйте еще раз...");
        //если игрок выжил (он победил всех противников), то выводим сообщение о победе и заканчиваем игру
        else{
            System.out.println("Вы уничтожили банду и можете возвращаться к королю за своей наградой...");
            Thread.sleep(3000);
            System.out.println("Вы входите в замок короля, Вас встречают как героя, хотя почему как?...");
            Thread.sleep(2000);
            System.out.println("Король: Сэр, " + hero.getName() + ", Вы настоящий герой.");
            Thread.sleep(2000);
            System.out.println("Король: В благодарность за помощь я дарую Вам фамильное поместье.");
            Thread.sleep(2000);
            System.out.println("Король: Владейте им, и будьте верным подданым короля!");
            Thread.sleep(2000);
            System.out.println(hero.getName() + ": Благодарю, Ваше Величество, я останусь верным короне до конца моих дней.");
            Thread.sleep(2000);
            System.out.println("Вы отправились в свое поместье, по дороге на вас напали троли...");
            Thread.sleep(2000);
            System.out.println("Но это уже совсем другая история...");
            Thread.sleep(3000);
            System.out.println();
            Thread.sleep(500);
            System.out.println();
            Thread.sleep(500);
            System.out.println();
            Thread.sleep(500);
            System.out.println("O  O OOOO OOOO OOOO OOOO OOOO");
            Thread.sleep(1000);
            System.out.println("O  O O    O  O O  O O    O   ");
            Thread.sleep(1000);
            System.out.println("OOOO OOO  OOO  O  O OOO  OOOO");
            Thread.sleep(1000);
            System.out.println("O  O O    O O  O  O O       O");
            Thread.sleep(1000);
            System.out.println("O  O OOOO O  O OOOO OOOO OOOO");
            Thread.sleep(1000);
            System.out.println("                             ");
            Thread.sleep(1000);
            System.out.println("        00  OOO  OOO         ");
            Thread.sleep(1000);
            System.out.println("       O  O O  O O  O        ");
            Thread.sleep(1000);
            System.out.println("       OOOO O  O O  O        ");
            Thread.sleep(1000);
            System.out.println("       O  O O  O O  O        ");
            Thread.sleep(1000);
            System.out.println("       O  O O  O OOO         ");
            Thread.sleep(1000);
            System.out.println("                             ");
            Thread.sleep(1000);
            System.out.println("OOOO OOOO  OOO O  O OOOO OOOO");
            Thread.sleep(1000);
            System.out.println("O  O O  O O    O  O O    O   ");
            Thread.sleep(1000);
            System.out.println("OOO  O  O O    O  O OOO  OOOO");
            Thread.sleep(1000);
            System.out.println("O O  O  O O OO O  O O       O");
            Thread.sleep(1000);
            System.out.println("O  O OOOO OOOO  OO  OOOO OOOO");
            System.out.println();
            System.out.println();
            System.out.println();
            Thread.sleep(3000);
            System.out.println("Курсовая работа");
            System.out.println("студента 2 курса");
            System.out.println("группы БИБ1903");
            System.out.println("Криволапова А.К.");
            System.out.println("проверила ст.преподаватель");
            System.out.println("Мосева М.С.");




        }
    }
    //метод для реализации броска кубика с гранями от 0 до 5
    public static int rollTheDice(){ return (int) (Math.random() * 6); }

    //метод для реализации проверки правильности введенной команды
    public static boolean checkInput(String str){
        if (!str.equals("удар") && !str.equals("блок")) {
            System.out.println("Введено неверное значение");
            return false;
        }
        return true;
    }
}
