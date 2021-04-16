package com.company;
import java.util.*;
/**
 * Класс реализует запускаемый интерфейс, получает  ссылку на параметр класса URLPool.
 * Получает из пула пару (URL-глубина), извлекает веб-станицу, получает все
 * адреса со страницы и записывает каждый из них как пару (URL-глубина)
 */
public class CrawlerTask implements Runnable {
    //поле для полученной пары
    public URLDepthPair depthPair;
    //поле для пула
    public URLPool myPool;
    //конструктор для записи в соответствующее поле переданного пула
    public CrawlerTask(URLPool pool) { myPool = pool; }
    //метод для выполнения задач класса
    public void run() {
        //получаем пару из пула
        depthPair = myPool.get();
        //получаем глубину из пары
        int myDepth = depthPair.getDepth();
        //считываем все URL на сайте и записываем их в новый список
        LinkedList<String> linksList = new LinkedList<String>();
        linksList = Crawler.getAllLinks(depthPair);
        //проходим по всем ссылкам
        for (int i=0;i<linksList.size();i++) {
            String newURL = linksList.get(i);
            //создаем для каждой ссылки соответствующую пару и записываем в пул
            URLDepthPair newDepthPair = new URLDepthPair(newURL, myDepth + 1);
            myPool.put(newDepthPair);
        }
    }
}