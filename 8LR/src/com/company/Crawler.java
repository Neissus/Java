package com.company;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 * Класс реализует основную функциональность приложения.
 */
public class Crawler {
    //основной метод класса, программа должна получить URL и глубину поиска
    //в виде строки, если данные введены неправильно, выводится сообщение об ошибке,
    //после отработки всех найденных ссылок или достижения глубины поиска,
    //выводится список проверенных сайтов с их глубиной поиска
    public static void main(String[] args) {
        //переменная для текущей глубины поиска
        int depth = 0;
        //переменная для количества потоков
        int numThreads = 0;
        //проверяем введенные данные на количество данны,
        //если это не 3 аргумента, то выводим сообщение об ошибке
        if (args.length != 3) {
            System.out.println("usage: java Crawler <URL> <depth> <number of crawler threads");
            System.exit(1);
        }
        //если данные корректны, то продолжаем
        else {
            try {
                //преобразуем глубину и количество потоков в числа
                depth = Integer.parseInt(args[1]);
                numThreads = Integer.parseInt(args[2]);
            } catch (NumberFormatException nfe) {
                //если они не преобразуются в целочисленный тип, то вывводим сообщение об ошибке
                System.out.println("usage: java Crawler <URL> <depth> <number of crawler threads");
                System.exit(1);
            }
        }
        //сайт введенный пользователем сайт преобразуем в пару URL-глубина
        URLDepthPair currentDepthPair = new URLDepthPair(args[0], 0);
        //создаем пул URL и добавляем в него текущий сайт
        URLPool pool = new URLPool();
        pool.put(currentDepthPair);

        //переменная для общего количесва потоков
        int totalThreads = 0;
        //переменная для начальных потоков
        int initialActive = Thread.activeCount();
        //Пока количество запущенных потоков не равно количеству запрошенных,
        //проверяем общее количество потоков, если оно меньше количества запрошенных
        //создаем больше потоков и запускаем недостающее количество, иначе спим
        while (pool.getWaitThreads() != numThreads) {
            if (Thread.activeCount() - initialActive < numThreads) {
                CrawlerTask crawler = new CrawlerTask(pool);
                new Thread(crawler).start();
            }
            else {
                try {
                    Thread.sleep(100);  // 0.1 секунды
                }
                //выводим сообщение об ошибке если таковая возникает
                catch (InterruptedException ie) {
                    System.out.println("Caught unexpected " + "InterruptedException, ignoring...");
                }
            }
        }
        //когда все потоки запущены выводим все найденные URL с их глубиной
        Iterator<URLDepthPair> iter = pool.processedURLs.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        System.exit(0);
    }

    //Метод, который принимает пару и возвращает список строк. Создает соединение
    //с сайтом из пары, находит все ссылки на сайте и добавляет их к списку, который вернет
    public static LinkedList<String> getAllLinks(URLDepthPair myDepthPair) {

        //список найденных на сайте ссылок
        LinkedList<String> URLs = new LinkedList<String>();

        //создаем сокет
        Socket sock;

        //пытаемся создать соединение с сайтом из пары на 80 порту
        try {
            sock = new Socket(myDepthPair.getWebHost(), 80);
        }
        //если возникает исключение UnknownHostException, возвращаем пустой список
        catch (UnknownHostException e) {
            System.err.println("UnknownHostException: " + e.getMessage());
            return URLs;
        }
        //если возникает исключение IOException, возвращаем пустой список
        catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
            return URLs;
        }
        //пытаемся создать соединение с сервером в течение 3 секунд
        try {
            sock.setSoTimeout(3000);
        }
        //если возникает исключение SocketException, возвращаем пустой список
        catch (SocketException exc) {
            System.err.println("SocketException: " + exc.getMessage());
            return URLs;
        }
        //строка для пути к файлу, полученного из URL-адреса
        //переданного как URLDepthPair и строка для доменного имени
        String docPath = myDepthPair.getDocPath();
        String webHost = myDepthPair.getWebHost();
        //создаем поток вывода
        OutputStream outStream;
        //пытаемся получить выходной поток с сокета
        try {
            outStream = sock.getOutputStream();
        }
        //если возникло исключение IOException, возращаем пустой список
        catch (IOException exce) {
            System.err.println("IOException: " + exce.getMessage());
            return URLs;
        }
        //инициализируем PrintWriter, истина = сброс после каждого вывода
        PrintWriter myWriter = new PrintWriter(outStream, true);

        //отправляем запросы серверу
        myWriter.println("GET " + docPath + " HTTP/1.1");
        myWriter.println("Host: " + webHost);
        myWriter.println("Connection: close");
        myWriter.println();

        //создаем поток ввода
        InputStream inStream;
        //пытаемся получить входной поток с сокета
        try {
            inStream = sock.getInputStream();
        }
        //если возникло исключение IOException, возращаем пустой список
        catch (IOException excep){
            System.err.println("IOException: " + excep.getMessage());
            return URLs;
        }
        //создаем новые InputStreamReader and BufferedReader для чтения строк с сервера
        InputStreamReader inStreamReader = new InputStreamReader(inStream);
        BufferedReader BuffReader = new BufferedReader(inStreamReader);
        //пытаемся считать строки из BuffReader
        while (true) {
            String line;
            try {
                line = BuffReader.readLine();
            }
            //если возникло исключение IOException, возращаем пустой список
            catch (IOException except) {
                System.err.println("IOException: " + except.getMessage());
                return URLs;
            }
            //документ прочитан, прерываем цикл
            if (line == null)
                break;
            //переменные для начального и коннечного индексов, а также текущего индекса
            int beginIndex = 0;
            int endIndex = 0;
            int index = 0;
            while (true) {
                //строковая константа для идентификации ссылки
                String URL_INDICATOR = "a href=\"";
                //строковая константа обозначающая конец доменного имени и начало пути к файлу
                String END_URL = "\"";
                //ищем начало в текущей строке
                index = line.indexOf(URL_INDICATOR, index);
                if (index == -1)
                    break;
                //обновляем текущий индекс и устанавливаем индекс начала
                index += URL_INDICATOR.length();
                beginIndex = index;
                //находим конец строки и записываем индекс конца
                endIndex = line.indexOf(END_URL, index);
                index = endIndex;
                //устанавливаем ссылку в подстроку между индексом начала и конца, добавляем в список URL
                String newLink = line.substring(beginIndex, endIndex);
                URLs.add(newLink);
            }
        }
        //возвращаем список URL
        return URLs;
    }
}
//данные для отладки
//http://room.mtuci.ru/ 100 100
//http://p-bot.ru/ 100 100