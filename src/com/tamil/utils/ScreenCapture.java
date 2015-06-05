package com.tamil.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * Author : Tamilselvan Teivasekamani
 *
 * Description :
 *
 */

public class ScreenCapture {

    public static void main(String[] args) throws IOException {

        try {

            Robot robot = new Robot();
			// Creates the delay of 5 sec so that you can open notepad before
			// Robot start writting
            robot.delay(5000);
            robot.keyPress(KeyEvent.VK_H);
            robot.keyPress(KeyEvent.VK_I);
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.keyPress(KeyEvent.VK_B);
            robot.keyPress(KeyEvent.VK_U);
            robot.keyPress(KeyEvent.VK_D);
            robot.keyPress(KeyEvent.VK_D);
            robot.keyPress(KeyEvent.VK_Y);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle rect = new Rectangle(0,0,dim.width,dim.height);
            BufferedImage bi = robot.createScreenCapture(rect);
            bi.flush();
            FileOutputStream fi = new FileOutputStream(new File("D:\\screencapture.jpg"));
            ImageIO.write(bi, "jpg", fi);

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
