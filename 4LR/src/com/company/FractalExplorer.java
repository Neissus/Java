package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
    //переменная для ограничения высоты и ширины квадратного экрана
    private final int displaySize;
    //ссылка для обновления отображения
    private final JImageDisplay display;
    //ссылка на базовый класс для отображения других видов фракталов
    private final FractalGenerator fractalType;
    //диапазон комплексной плоскости, которая выводится на экран
    private final Rectangle2D.Double range;
    //конструктор инициализации
    public FractalExplorer (int size){
        displaySize = size;
        fractalType = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractalType.getInitialRange(range);
        display = new JImageDisplay(displaySize,displaySize);
    }

    public void createAndShowGUI()
    {
        //создаем окно с заголовком Fractal Explorer
        JFrame myFrame = new JFrame("Fractal Explorer");
        //Устанавливаем у окна режима отображения содержимого - BorderLayout
        display.setLayout(new BorderLayout());
        //перемещаем окно отображения фрактала в центр окна
        myFrame.add(display, BorderLayout.CENTER);
        //создаем кнопку сброса
        JButton resetButton = new JButton("Reset Display");
        //перемещаем кнопку сброса вниз
        myFrame.add(resetButton, BorderLayout.SOUTH);

        //Instance of the ResetButton on the reset button.
        ResetButton handler = new ResetButton();
        resetButton.addActionListener(handler);

        //Instance of the MouseHandler on the fractal-display component.
        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);

        //добавляем закрытие окна по умолчанию
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //размещаем содержимое окна
        myFrame.pack();
        //делаем окно видимым
        myFrame.setVisible(true);
        //запрещаем изменение размеров окна
        myFrame.setResizable(false);
    }

    /**
     * Метод выводит фрактал на экран
     */
    private void drawFractal() {
        //перебираем каждый пиксель на дисплее
        for (int x=0; x<displaySize; x++){
            for (int y=0; y<displaySize; y++){

                //находим соответсвующие координаты xCoord и yCoord в области отображения фрактала
                double xCoord = FractalGenerator.getCoord(range.x,range.x + range.width, displaySize, x);
                double yCoord = FractalGenerator.getCoord(range.y,range.y + range.height, displaySize, y);

                //вычисляем количество итераций для координат в области отображения фрактала
                int iteration = fractalType.numIterations(xCoord, yCoord);
                //если количество итераций равно -1, значит точка находится внутри границ фрактала
                //следовательно, закрашиваем ее в черный цвет
                if (iteration == -1){
                    display.drawPixel(x, y, 0);
                }
                else {
                    //выбираем оттенок в зависимости от количества итераций
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    //закрашиваем пиксель в соответствующий цвет
                    display.drawPixel(x, y, rgbColor);
                }

            }
        }
        //после раскрашивания всех пикселей обновляем дисплей, чтобы изменения отобразились в окне
        display.repaint();
    }

    private class ResetButton implements ActionListener
    {
        //метод сбрасывает диапазон до изначально заданного генератором и рисует фрактал
        public void actionPerformed(ActionEvent e)
        {
            fractalType.getInitialRange(range);
            drawFractal();
        }
    }
    //внутренний класс для обработки событий MouseListener с дисплея
    private class MouseHandler extends MouseAdapter
    {
        //При получении события о щелчке мышью, класс должен передать
        //пиксельные кооринаты щелчка в область фрактала.
        @Override
        public void mouseClicked(MouseEvent e)
        {
            //получаем координату x клика мыши
            int x = e.getX();
            double xCoord = FractalGenerator.getCoord(range.x,range.x + range.width, displaySize, x);
            //получаем координату у клика мыши
            int y = e.getY();
            double yCoord = FractalGenerator.getCoord(range.y,range.y + range.height, displaySize, y);
            //метод увеличивает область фрактала, в которую мы кликнули мышью в 2 раза
            fractalType.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            //перерисовываем фрактал после приближения
            drawFractal();
        }
    }

    public static void main(String[] args) {
        FractalExplorer newFractal = new FractalExplorer(700);
        newFractal.createAndShowGUI();
        newFractal.drawFractal();
    }

}
