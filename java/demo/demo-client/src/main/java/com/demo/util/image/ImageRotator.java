package com.demo.util.image;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageRotator {

    public static BufferedImage rotateImage(int orientation, BufferedImage orignalImage) {
        int width = orignalImage.getWidth(null);
        int height = orignalImage.getHeight(null);
        int dstWidth = width;
        int dstHeight = height;
        AffineTransform t = new AffineTransform();
        switch (orientation) {
            case 1:
                return orignalImage;
            case 2: // Flip X
                t.scale(-1.0, 1.0);
                t.translate(-width, 0);
                break;
            case 3: // PI rotation
                t.translate(width, height);
                t.rotate(Math.PI);
                break;
            case 4: // Flip Y
                t.scale(1.0, -1.0);
                t.translate(0, -height);
                break;
            case 5: // - PI/2 and Flip X
                t.rotate(-Math.PI / 2);
                t.scale(-1.0, 1.0);
                dstWidth = height;
                dstHeight = width;
                break;
            case 6: // -PI/2 and -width
                t.translate(height, 0);
                t.rotate(Math.PI / 2);
                dstWidth = height;
                dstHeight = width;
                break;
            case 7: // PI/2 and Flip
                t.scale(-1.0, 1.0);
                t.translate(-height, 0);
                t.translate(0, width);
                t.rotate(3 * Math.PI / 2);
                dstWidth = height;
                dstHeight = width;
                break;
            case 8: // PI / 2
                t.translate(0, width);
                t.rotate(3 * Math.PI / 2);
                dstWidth = height;
                dstHeight = width;
                break;
            default:
                return orignalImage;
        }

        BufferedImage buffer = new BufferedImage(dstWidth, dstHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = buffer.createGraphics();
        g2d.drawImage(orignalImage, t, null);
        g2d.dispose();
        return buffer;
    }

    // util
    // public static void main(String[] args) {
    // try {
    // BufferedImage read = ImageIO.read(new File(
    // "/home/qinyu/Pictures/wallpaper409063.jpg"));
    //
    // for (int i = 1; i <= 8; i++) {
    // ImageIO.write(rotateImage(i, read), "jpg", new File(
    // "/home/qinyu/Pictures/" + i));
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
}
