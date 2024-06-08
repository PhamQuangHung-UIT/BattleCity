package com.uit.battlecity.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class ImportUtils {
    private static final FileHandle ASSETS = Gdx.files.internal("assets.txt");

    public static Array<FileHandle> list(String directoryPath) {
        Array<FileHandle> result = new Array<>();
        String output = ASSETS.readString();
        String[] lines = output.split("\n");
        for (String line : lines) {
            if (line.startsWith(directoryPath)) {
                result.add(Gdx.files.internal(line));
            }
        }
        return result;
    }
}
