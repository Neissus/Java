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
        //проверяем введенные данные на корректность по длине, если длинна не соответствует,
        //выводим сообщение об ошибке
        if (args.length != 2) {
            System.out.println("usage: java Crawler <URL> <depth>");
            System.exit(1);
        }
        //если данные корректны, то продолжаем
        else {
            try {
                //преобразуем строковые данные в число
                depth = Integer.parseInt(args[1]);
            } catch (NumberFormatException nfe) {
                //если второй аргумент не преобразуется в целочисленный тип,
                //выводим сообщение об ошибке
                System.out.println("usage: java Crawler <URL> <depth>");
                System.exit(1);
            }
        }
        //список пар для URL ожидающих обработки
        LinkedList<URLDepthPair> pendingURLs = new LinkedList<URLDepthPair>();

        //список пар для обработанных URL
        LinkedList<URLDepthPair> processedURLs = new LinkedList<URLDepthPair>();

        //текущая пара (введенная пользователем)
        URLDepthPair currentDepthPair = new URLDepthPair(args[0], 0);

        //добавляем введенную пользователем пару в список ожидающих обработки
        pendingURLs.add(currentDepthPair);

        //массив пар просмотренных адресов
        ArrayList<String> seenURLs = new ArrayList<String>();
        //добавялем текущую пару в просмотренные
        seenURLs.add(currentDepthPair.getURL());

        //пока список ожидающих обработки пар не пуст, заходим на каждый URL и ищем на нем ссылки
        while (pendingURLs.size() != 0) {
            //из списка ожидающих пар берем URL и добавляем его в обрабатываемые, запоминаем глубину поиска
            URLDepthPair depthPair = pendingURLs.pop();
            processedURLs.add(depthPair);
            int currentDepth = depthPair.getDepth();

            //получаем все ссылки на сайте и записываем в новый список пар
            LinkedList<String> linksList = new LinkedList<String>();
            linksList = Crawler.getAllLinks(depthPair);

            // Если мы не достигли максимальной глубины добавляем ссылки с сайта, которых еще нет в списке ожидания
            // и списке просмотренных
            if (currentDepth < depth) {
                //проходим по всем ссылкам
                for (int i = 0; i < linksList.size(); i++) {
                    String newURL = linksList.get(i);
                    //если ссылка уже была встречена, то пропускаем ее
                    if (seenURLs.contains(newURL)) {
                        continue;
                    }
                    //если ссылка встречена впервые создаем новую пару с глубиной на 1 больше чем текущая
                    // и добавляем ее в список ожидающих пар и список просмотренных пар
                    else {
                        URLDepthPair newDepthPair = new URLDepthPair(newURL, currentDepth + 1);
                        pendingURLs.add(newDepthPair);
                        seenURLs.add(newURL);
                    }
                }
            }
        }
        //выводим все пары
        Iterator<URLDepthPair> iter = processedURLs.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
     //Метод, который принимает пару и возвращает список строк. Создает соединение
     //с сайтом из пары, находит все ссылки на сайте и добавляет их к списку, который вернет
     private static LinkedList<String> getAllLinks(URLDepthPair myDepthPair) {

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
        // Строка для пути к файлу, полученного из URL-адреса
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