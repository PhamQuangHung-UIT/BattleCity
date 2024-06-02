package com.uit.battlecity.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;

public class ImportUtils {
    public static Pixmap importPixmap(FileHandle file, float scale) {
        Pixmap pixmap = new Pixmap(file);
        Pixmap scalePixmap = new Pixmap((int) (pixmap.getWidth() * scale), (int) (pixmap.getHeight() * scale), pixmap.getFormat());
        scalePixmap.drawPixmap(pixmap, 0, 0, pixmap.getWidth(), pixmap.getHeight(),
                0, 0, scalePixmap.getWidth(), scalePixmap.getHeight());
        return scalePixmap;
    }
}
