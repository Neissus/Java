package com.company;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Класс позволяет отображать фракталы
 */
public class JImageDisplay extends JComponent {
    //создаем поле с информацией об изображении
    private final BufferedImage image;
    /**
    * Конструктор инициализирует поле и задает размеры изображения
    */
    JImageDisplay (int width, int height) {
        //инициализируем поле с заданной шириной, высотой и типом изображения INT_RGB
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //задаем размеры изображения при отображении с помощью метода родительского класса setPreferredSize
        Dimension val = new Dimension(width,height);
        super.setPreferredSize(val);
    }   

    /**
     * Для правильного отображения границ изображения используем компонет суперкласса, а потом рисуем изобраежние
     * с помощью метода drawImage
     */
    @Override
    /*здесь вроде как должен стоять public, но если будет работать то оставить protected*/
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0,0,image.getWidth(),image.getHeight(),null);
    }

    /**
     * Метод меняет цвет всех пикселей на черный
     */
    public void clearImage(){
        int[] rgbArray = new int[getWidth() * getHeight()];
        image.setRGB(0,0,getWidth(),getHeight(),rgbArray,0,1);
    }

    /**
     * Метод меняет цвет пикселя на заданный
     */
    public void drawPixel (int x, int y, int rgbColor){
        image.setRGB(x,y,rgbColor);
    }
}
