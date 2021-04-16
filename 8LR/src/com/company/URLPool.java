package com.company;
import java.util.*;

/**
 * Пул для сохранения списка ссылок, найденных на сайтах, в виде пар
 */
public class URLPool {
    int maxDepth;
    //список пар для URL ожидающих обработки
    private LinkedList<URLDepthPair> pendingURLs;
    //список пар для обработанных URL
    public LinkedList<URLDepthPair> processedURLs;
    //массив пар просмотренных адресов
    public ArrayList<String> seenURLs = new ArrayList<String>();
    //целое число для отслеживания количества запущенных потоков
    public int waitingThreads;
    //конструктор инициализации
    public URLPool() {
        waitingThreads = 0;
        pendingURLs = new LinkedList<URLDepthPair>();
        processedURLs = new LinkedList<URLDepthPair>();
    }
    //cинхронизированный метод для получения количества запущенных потоков
    public synchronized int getWaitThreads() {
        return waitingThreads;
    }
    //cинхронизированный метод для получения размера пула
    public synchronized int size() {
        return pendingURLs.size();
    }
    //cинхронизированный метод для добавления пары в пул
    public synchronized void put(URLDepthPair depthPair) {
            pendingURLs.addLast(depthPair);
            this.notify();
    }
    // синхронизированный метод для получения пары из пула
    public synchronized URLDepthPair get() {
        //очищаем переменную
        URLDepthPair myDepthPair = null;
        //ждем пока пул пуст
        if (pendingURLs.size() == 0) {
            waitingThreads++;
            try {
                this.wait();
            }
            catch (InterruptedException e) {
                System.err.println("MalformedURLException: " + e.getMessage());
                return null;
            }
        }
        //удаляем первую пару, доабвляем ее в просмотренные и обрабатываемые и возвращаем ее
        myDepthPair = pendingURLs.removeFirst();
        seenURLs.add(myDepthPair.getURL());
        processedURLs.add(myDepthPair);
        return myDepthPair;
    }
    //синхронизированный метод для получения листа просмотренных URL
    public synchronized ArrayList<String> getSeenList() {
        return seenURLs;
    }
}