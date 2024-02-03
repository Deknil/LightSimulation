package ru.deknil.lightsimulation;

import java.awt.*;

/**
 * @Author Deknil
 * @GitHub <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * @Date 03.02.2024
 * @Description Main application configuration file
 * <p></p>
 * LightSimulation © 2024. All rights reserved.
 */
public class Config {
    public static final String WINDOW_TITLE = "Light simulation"; // Window title
    public static final int WINDOW_WIDTH = 512; // Window width
    public static final int WINDOW_HEIGHT = 512; // Window height
    public static final int TIME_SPEED = 1; // Logic and rendering update time
    public static final double ACCUMULATED_EXPOSURE = 0.0003d;
    public static final double[] COLOR_SHIFT = new double[]{0.02d, 0.0d, -0.04d};
    public static final int[] GLASS_COLORS = new int[]{50, 50, 50};

    public static final Color BACKGROUND_COLOR = new Color(10, 10, 10, 255); // Background colors for the canvas
}