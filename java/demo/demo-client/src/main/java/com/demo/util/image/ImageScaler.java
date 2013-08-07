package com.demo.util.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageScaler {
    public static void scale(File imagePath, File destFile, float scale) throws IOException {
        Image image = ImageIO.read(imagePath);
        scale(image, destFile, scale);
    }

    public static void scale(File imagePath, File destFile, int preferredWidth, int preferredHeight) throws IOException {
        Image image = ImageIO.read(imagePath);
        scaleImage(destFile, preferredWidth, preferredHeight, image);
    }

    public static void scale(Image image, File destFile, float scale) throws IOException {
        int preferredWidth = (int) (scale * image.getWidth(null));
        int preferredHeight = (int) (scale * image.getHeight(null));
        scaleImage(destFile, preferredWidth, preferredHeight, image);
    }

    public static void scale(Image image, File destFile, int preferredWidth, int preferredHeight) throws IOException {
        scaleImage(destFile, preferredWidth, preferredHeight, image);
    }

    private static void scaleImage(File destFile, int preferredWidth, int preferredHeight, Image orignalImage)
            throws IOException {
        // int width = orignalImage.getWidth(null);
        // int height = orignalImage.getHeight(null);
        // if(width > height) {
        // height = (height / width) * preferredWidth;
        // width = preferredWidth;
        // } else if(width < height) {
        // width = (width / height) * preferredHeight;
        // height = preferredHeight;
        // }
        ImageIO.write(scaleImage(preferredWidth, orignalImage), "jpg", destFile);
    }

    public static BufferedImage scaleImage(int preferredWidth, Image orignalImage) {
        Image scalelImage = orignalImage.getScaledInstance(preferredWidth, -1, Image.SCALE_FAST);
        int width = scalelImage.getWidth(null);
        int height = scalelImage.getHeight(null);
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        buffer.getGraphics().drawImage(scalelImage, 0, 0, width, height, null);
        return buffer;
    }
}
