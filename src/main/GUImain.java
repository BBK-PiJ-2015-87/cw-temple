package main;

import game.GameState;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Run this program to see a demonstration of the GUI interface
 */
public class GUImain {
    /**
     * The main program.
     */
    public static void main(String[] args) {
        Optional<Long> seed = Utilities.parseSeedArgs(args);
        GameState.runNewGame((seed.isPresent() ? seed.get() : 0), true);
    }
}
