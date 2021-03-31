package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class FractalExplorer {
    //поле для ограничения высоты и ширины квадратного экрана
    private final int displaySize;
    //ссылка для обновления отображения
    private final JImageDisplay display;
    //ссылка на базовый класс для отображения других видов фракталов
    private FractalGenerator fractalType;
    //диапазон комплексной плоскости, которая выводится на экран
    private final Rectangle2D.Double range;
    //конструктор инициализации
    public FractalExplorer (int size){
        //устанавливаем размер окна
        displaySize = size;
        //инициализируем тип фрактала и диапазон
        fractalType = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractalType.getInitialRange(range);
        display = new JImageDisplay(displaySize,displaySize);
    }
    /**
     * Этот метод инициализирует графический интерфейс Swing с помощью JFrame,
     * содержащего объект JImageDisplay и кнопки для сброса дисплея,
     * для сохранения текущего фрактального изображения и JComboBox для выбора
     * типа фрактала.
     */
    public void createAndShowGUI()
    {
        //создаем окно с заголовком Fractal Explorer
        JFrame myFrame = new JFrame("Fractal Explorer");
        //Устанавливаем у окна режима отображения содержимого - BorderLayout
        display.setLayout(new BorderLayout());
        //перемещаем окно отображения фрактала в центр окна
        myFrame.add(display, BorderLayout.CENTER);
        //инициализируем кнопку сброса
        JButton resetButton = new JButton("Reset");
        //Экземпляр ResetButton на кнопке сброса
        ButtonHandler resetHandler = new ButtonHandler();
        resetButton.addActionListener(resetHandler);
        //Экземпляр MouseHandler на компоненте дисплея
        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);
        //добавляем закрытие окна по умолчанию
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //устанавливаем компонент ComboBox
        JComboBox myComboBox = new JComboBox();
        // добавляем в combo box все типы фракталов
        FractalGenerator mandelbrotFractal = new Mandelbrot();
        myComboBox.addItem(mandelbrotFractal);
        FractalGenerator tricornFractal;
        tricornFractal = new Tricorn();
        myComboBox.addItem(tricornFractal);
        FractalGenerator burningShipFractal = new BurningShip();
        myComboBox.addItem(burningShipFractal);
        // добавляем экземпляр ButtonHandler на ComboBox.
        ButtonHandler fractalChooser = new ButtonHandler();
        myComboBox.addActionListener(fractalChooser);
        // Создаем новый объект JPanel, добавляем к нему объект JLabel
        // и объект JComboBox и добавляем панель в рамку в верхней части макета
        JPanel myPanel = new JPanel();
        JLabel myLabel = new JLabel("Fractal:");
        myPanel.add(myLabel);
        myPanel.add(myComboBox);
        myFrame.add(myPanel, BorderLayout.NORTH);
        // Создаем кнопку сохранения, создаем новыый объект JPanel, добавляем
        // к нему кнопки сохранения и сброса и располагаем в нижней части макета
        //поля для кнопки сохранения, сброса и выпадающего меню
        JButton saveButton = new JButton("Save");
        JPanel myBottomPanel = new JPanel();
        myBottomPanel.add(saveButton);
        myBottomPanel.add(resetButton);
        myFrame.add(myBottomPanel, BorderLayout.SOUTH);
        // добавляем экземпляр ButtonHandler на кнопку сохранения
        ButtonHandler saveHandler = new ButtonHandler();
        saveButton.addActionListener(saveHandler);
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
    private void drawFractal()
    {
        //проходим по всем пикселям на дисплее
        for (int x=0; x<displaySize; x++){
            for (int y=0; y<displaySize; y++){
                //находим соответствующие координаты на фрактале
                double xCoord = fractalType.getCoord(range.x,
                        range.x + range.width, displaySize, x);
                double yCoord = fractalType.getCoord(range.y,
                        range.y + range.height, displaySize, y);
                //вычисляем количество итераций в для отрисовки фрактала
                int iteration = fractalType.numIterations(xCoord, yCoord);
                //если количество итераций = -1, то закрашиваем пиксель в черный
                if (iteration == -1){
                    display.drawPixel(x, y, 0);
                }
                else {
                    //иначе закрашиваем цветов в зависимости от количества повторений
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    //перерисовываем пиксель соответствующего цвета
                    display.drawPixel(x, y, rgbColor);
                }
            }
        }
        //перерисовываем фрактал после того как все пиксели стали нужных цветов
        display.repaint();
    }
    /**
     * Внутренний класс для обработки событий ActionListener
     */
    private class ButtonHandler implements ActionListener {
        //метод сбрасывает диапазон до изначально заданного генератором и рисует фрактал
        public void actionPerformed(ActionEvent e) {
            //узнаем откуда источник
            String command = e.getActionCommand();
            //если источник - ComboBox, то рисуем фрактал, выбранный пользователем
            if (e.getSource() instanceof JComboBox) {
                JComboBox mySource = (JComboBox) e.getSource();
                fractalType = (FractalGenerator) mySource.getSelectedItem();
                fractalType.getInitialRange(range);
                drawFractal();
            }
            //если источник - кнопка сброса, то сбрасываем дисплей
            // до изначального состояния и рисуем фрактал в первоначальном маштабе
            else if (command.equals("Reset")) {
                fractalType.getInitialRange(range);
                drawFractal();
            }
            //если источник - кнопка сохранения, сохраняем фрактал как картинку
            else if (command.equals("Save")) {
                //позволяем пользователю выбрати каким образом сохранить картинку
                JFileChooser myFileChooser = new JFileChooser();
                //сохраняем только в png формате
                FileFilter extensionFilter = new FileNameExtensionFilter("PNG Images", "png");
                myFileChooser.setFileFilter(extensionFilter);
                //запрещаем сохранять файлы не-png
                myFileChooser.setAcceptAllFileFilterUsed(false);
                //создаем окно сохранения, для выбора пользователем директории
                //сохранения и выбора файла
                int userSelection = myFileChooser.showSaveDialog(display);
                //если результатом операции выбора файла является APPROVE_OPTION,
                //продолжаем сохранять
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    //получаем файл и его имя
                    java.io.File file = myFileChooser.getSelectedFile();
                    String file_name = file.toString();

                    //пробуем сохранить файл на диск
                    try {
                        BufferedImage displayImage = display.getImage();
                        javax.imageio.ImageIO.write(displayImage, "png", file);
                    }
                    //отлавливаем все исключения и выводим сообщение об ошибке
                    catch (Exception exception) {
                        JOptionPane.showMessageDialog(display,exception.getMessage(), "Cannot Save Image",JOptionPane.ERROR_MESSAGE);
                    }
                }
                //если результат не APPROVE_OPTION, выходим из функции
                else return;
            }
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
    /**
     * Статический метод main создает объект типа FractalExplorer,
     * инициализируем с размером дисплея 700, вызываем метод createAndShowGUI
     * и метод drawFractal
     */
    public static void main(String[] args) {
        FractalExplorer newFractal = new FractalExplorer(700);
        newFractal.createAndShowGUI();
        newFractal.drawFractal();
    }

}
