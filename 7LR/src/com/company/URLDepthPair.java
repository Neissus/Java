package com.company;
import java.net.*;

/**
 * Класс для предоставления пар [URL, depth] для класса Crawler
 */
public class URLDepthPair {
    //полля для текущей глубины и текущего URL
    private final int currentDepth;
    private final String currentURL;

    //конструктор, который записывает переданные глубину и URL в поля
    public URLDepthPair(String URL, int depth) {
        currentDepth = depth;
        currentURL = URL;
    }
    //метод, который возвращает текущий URL
    public String getURL() {
        return currentURL;
    }
    //метод, который возвращает текущую глубину
    public int getDepth() {
        return currentDepth;
    }
    //метод, который возвращает пару в виде строки
    public String toString() {
        String stringDepth = Integer.toString(currentDepth);
        return stringDepth + '\t' + currentURL;
    }
    //метод который возвращает путь к файлу по текущему URL
    public String getDocPath() {
        try {
            URL url = new URL(currentURL);
            return url.getPath();
        }
        catch (MalformedURLException e) {
            System.err.println("MalformedURLException: " + e.getMessage());
            return null;
        }
    }
    //метод, который возвращает, доменное имя по текущему URL
    public String getWebHost() {
        try {
            URL url = new URL(currentURL);
            return url.getHost();
        }
        catch (MalformedURLException e) {
            System.err.println("MalformedURLException: " + e.getMessage());
            return null;
        }
    }
}