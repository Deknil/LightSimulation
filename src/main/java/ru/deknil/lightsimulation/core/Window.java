package ru.deknil.lightsimulation.core;

import ru.deknil.lightsimulation.Config;

import javax.swing.*;

/**
 * @Author Deknil
 * @GitHub <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * @Date 03.02.2024
 * @Description This class represents a custom window for the application.
 *              It extends JFrame and implements the Runnable interface.
 * <p></p>
 * LightSimulation © 2024. All rights reserved.
 */
public class Window extends JFrame implements Runnable {
    private final Canvas canvas;

    /**
     * Constructor for the Window class.
     * Initializes the window with the necessary properties.
     */
    public Window() {
        setTitle(Config.WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new Canvas();
        add(canvas);
        pack();

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * This method is the implementation of the Runnable interface's run method.
     * It runs the canvas's update method to refresh the display.
     */
    @Override
    public void run() {
        canvas.repaint();
    }
}