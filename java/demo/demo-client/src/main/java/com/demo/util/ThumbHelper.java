package com.demo.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.demo.constants.PhotoConstants;
import com.demo.constants.PhotoConstantsExt;
import com.demo.util.image.ImageRotator;
import com.demo.util.image.ImageScaler;

public class ThumbHelper {
    public static void scalePhoto(File dstFile, File srcFile, String dimenString, int orientation) throws IOException {
        scalePhoto(dstFile, srcFile, dimenString, orientation, false);
    }

    public static void scalePhoto(File dstFile, File srcFile, String dimenString, int orientation, boolean crop)
            throws IOException {
        if (!srcFile.exists()) {
            return;
        }

        Dimension size = extractDimen(dimenString);

        if (!dstFile.exists()) {
            BufferedImage image = ImageIO.read(srcFile);
            if (image == null) {
                return;
            }

            int width = image.getWidth();
            int height = image.getHeight();
            if (width > size.width || height > size.height) {
                float scaleH = ((float) size.width) / width;
                float scaleV = ((float) size.height) / height;
                if (scaleV > scaleH) {
                    image = ImageScaler.scaleImage((int) (scaleV * width), image);
                } else {
                    image = ImageScaler.scaleImage(size.width, image);
                }
            }

            image = ImageRotator.rotateImage(orientation, image);
            if (crop) {
                width = image.getWidth();
                height = image.getHeight();
                if (width > height) {
                    image = image.getSubimage((width - height) / 2, 0, height, height);
                } else
                    if (height > width) {
                        image = image.getSubimage(0, (height - width) / 2, width, width);
                    }
            }
            ImageIO.write(image, "jpg", dstFile.getAbsoluteFile());
        }
    }

    public static Dimension extractDimen(String dimenString) throws IOException {
        Dimension size = PhotoConstantsExt.scaleMap.get(dimenString);

        if (size == null) {
            if (dimenString.startsWith(PhotoConstants.SMART_PREVIEW_NAME)) {
                size = parseSize(dimenString.substring(PhotoConstants.SMART_PREVIEW_NAME.length()));
            } else
                if (dimenString.startsWith(PhotoConstants.SMART_THUMB_NAME)) {
                    size = parseSize(dimenString.substring(PhotoConstants.SMART_THUMB_NAME.length()));
                }
            if (size == null) {
                throw new IOException("Invalid type:" + dimenString);
            }
        }
        return size;
    }

    public static Dimension parseSize(String dimenString) {
        String[] sizes = dimenString.toLowerCase().split("x");
        if (sizes.length > 1) {
            try {
                int width = Integer.parseInt(sizes[0]);
                int height = Integer.parseInt(sizes[1]);
                return new Dimension(width, height);
            } catch (NumberFormatException e) {
            }
        }
        return null;
    }

    // public static File getThumbResourcePath(MediaItemInfo item, String
    // dimeString) throws IOException {
    // String tempPath = SystemConfiguration.getWorkFolder();
    // File folderPath = new File(tempPath,
    // item.getType().name().toLowerCase());
    // folderPath = new File(folderPath, item.getHash());
    // if (!folderPath.exists()) {
    // if (!folderPath.mkdirs()) {
    // throw new IOException("Create folder for [" + folderPath + "] failed!");
    // }
    // }
    // return new File(folderPath, dimeString +
    // PhotoConstants.DEFAULT_PHOTO_TYPE);
    // }
}
