package ru.deknil.lightsimulation.core;

import ru.deknil.lightsimulation.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author Deknil
 * @GitHub <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * @Date 03.02.2024
 * @Description Canvas element for rendering the simulation.
 *              The simulation logic is ported from the JS version: <a href=https://github.com/ArtemOnigiri/Light-Simulation-JS>https://github.com/ArtemOnigiri/Light-Simulation-JS</a>
 * <p></p>
 * LightSimulation © 2024. All rights reserved.
 */
public class Canvas extends JPanel {
    private final BufferedImage image = new BufferedImage(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);

    private final double[][][] waveHeight = new double[Config.WINDOW_WIDTH][Config.WINDOW_HEIGHT][3];
    private final double[][][] waveVelocity = new double[Config.WINDOW_WIDTH][Config.WINDOW_HEIGHT][3];
    private final double[][][] accumulatedLight = new double[Config.WINDOW_WIDTH][Config.WINDOW_HEIGHT][3];
    private final double[][] pixelMass = new double[Config.WINDOW_WIDTH][Config.WINDOW_HEIGHT];

    private int frame = 0;
    /**
     * Canvas constructor
     */
    public Canvas() {
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        init();
    }

    /**
     * Initializing Basic Elements
     */
    private void init() {
        for (int width = 0; width < Config.WINDOW_WIDTH; width++) {
            for (int height = 0; height < Config.WINDOW_HEIGHT; height++) {
                // Initialize the wave height at each point in the scene as 0
                waveHeight[width][height] = new double[]{0, 0, 0};
                // Initialize the wave speed at each point in the scene as 0
                waveVelocity[width][height] = new double[]{0, 0, 0};
                // Initialize the accumulated light at each point in the scene as 0
                accumulatedLight[width][height] = new double[]{0, 0, 0};
                // Set pixel mass to 1 for all pixels except the center circle
                pixelMass[width][height] = 1;
                // If the pixel is inside the center circle, its mass is set to 0.75
                if (Math.sqrt(Math.pow(width - (double) Config.WINDOW_WIDTH / 2, 2) + Math.pow(height - (double) Config.WINDOW_HEIGHT / 2, 2)) < 100)
                    pixelMass[width][height] = 0.75;
            }
        }
    }

    /**
     * Drawing a simulation
     */
    private void render() {
        Graphics2D g = image.createGraphics();
        g.setColor(Config.BACKGROUND_COLOR);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create an initial wave effect for the first 300 frames of the animation
        if(frame < 300) {
            for (int j = Config.WINDOW_HEIGHT / 2 - 50; j < Config.WINDOW_HEIGHT / 2; j++) {
                for (int k = 0; k < 3; k++) {
                    waveHeight[165][j - 50][k] = Math.sin(frame * 0.8) * 20; // Wave Height Modification
                }
            }
        }

        // Calculate and draw each pixel based on waveform, accumulated light and pixel mass
        for (int width = 0; width < Config.WINDOW_WIDTH; width++) {
            for (int height = 0; height < Config.WINDOW_HEIGHT; height++) {
                int[] rgb = new int[3]; // Array for RGB values of each pixel
                for (int k = 0; k < 3; k++) {
                    waveHeight[width][height][k] += waveVelocity[width][height][k]; // Wave Height Update
                    accumulatedLight[width][height][k] += Math.abs(waveHeight[width][height][k]) * Config.ACCUMULATED_EXPOSURE; // Renewal of accumulated light
                    double lightValue = Math.pow(Math.min(accumulatedLight[width][height][k], 1), 2); // Calculation of illumination value
                    int colorValue = (int) (lightValue * 255); // Converting a Light Value to a Color Value
                    if (pixelMass[width][height] < 1) colorValue = Math.min(colorValue + Config.GLASS_COLORS[k], 255); // Color adjustment for pixel mass < 1
                    rgb[k] = colorValue; // Assigning a color value
                }
                int currentColor = new Color(rgb[0], rgb[1], rgb[2]).getRGB(); // Creating color based on RGB
                image.setRGB(width, height, currentColor); // Setting the pixel color
            }
        }

        // Calculation of wave speed and strength for each pixel
        for (int width = 1; width < Config.WINDOW_WIDTH - 1; width++) {
            for (int height = 1; height < Config.WINDOW_HEIGHT - 1; height++) {
                for (int k = 0; k < 3; k++) {
                    double speed = pixelMass[width][height] - Config.COLOR_SHIFT[k]; // Speed calculation
                    double force = waveHeight[width - 1][height][k] + waveHeight[width + 1][height][k] + waveHeight[width][height - 1][k] + waveHeight[width][height + 1][k]; // Force calculation
                    waveVelocity[width][height][k] += (force / 4 - waveHeight[width][height][k]) * speed; // Wave speed update
                }
            }
        }
    }

    /**
     * Canvas drawing logic
     */
    @Override
    public void paint(Graphics g) {
        render();
        ((Graphics2D)g).drawImage(image, null, 0, 0);
        frame++;
    }
}
