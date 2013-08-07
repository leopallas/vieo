package com.demo.constants;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PhotoConstantsExt extends PhotoConstants {
    public final static Dimension              THUMB_SIZE_SMALL    = new Dimension(64, 64);

    public final static Dimension              THUMB_SIZE_MEDIUM   = new Dimension(128, 128);

    public final static Dimension              PREVIEW_SIZE_SMALL  = new Dimension(320, 480);

    public final static Dimension              PREVIEW_SIZE_MEDIUM = new Dimension(480, 800);

    public final static Dimension              PREVIEW_SIZE_LARGE  = new Dimension(800, 1280);

    public final static String                 PREVIEW_LARGE_NAME  = "preview-large";

    public final static Dimension              PREVIEW_SIZE_IPHONE = new Dimension(480, 800);

    public final static Dimension              PREVIEW_SIZE_IPAD   = new Dimension(768, 1024);

    public final static Map<String, Dimension> scaleMap            = new HashMap<String, Dimension>();

    static {
        scaleMap.put(THUMB_SMALL_NAME, THUMB_SIZE_SMALL);
        scaleMap.put(THUMB_MEDIUM_NAME, THUMB_SIZE_MEDIUM);
        scaleMap.put(PREVIEW_SMALL_NAME, PREVIEW_SIZE_SMALL);
        scaleMap.put(PREVIEW_MEDIUM_NAME, PREVIEW_SIZE_MEDIUM);
        scaleMap.put(PREVIEW_LARGE_NAME, PREVIEW_SIZE_LARGE);
        scaleMap.put(PREVIEW_IPHONE_NAME, PREVIEW_SIZE_IPHONE);
        scaleMap.put(PREVIEW_IPAD_NAME, PREVIEW_SIZE_IPAD);
    }
}
