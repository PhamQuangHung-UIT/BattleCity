package com.uit.battlecity.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.uit.battlecity.screens.LevelScreen;
import com.uit.battlecity.utils.ImportUtils;
import com.uit.battlecity.utils.Point;

import java.util.Arrays;

public class LevelBoard {
    final Texture board = new Texture(ImportUtils.importPixmap(Gdx.files.internal("ui/board.png"), 3));
    final Texture tankIcon = new Texture(ImportUtils.importPixmap(Gdx.files.internal("ui/tank_icon.png"), 3));
    final Texture nullIcon = new Texture(ImportUtils.importPixmap(Gdx.files.internal("ui/null_icon.png"), 3));
    final Array<Texture> numIcons = new Array<>();
    final Point drawStartPos = new Point(232, 200);
    final int maxEnemies = 20;
    final boolean isTwoPlayer;
    final int level;
    private static final int scale = 3;

    int remainingEnemies = 20;
    int firstPlayerRemainingLives = 2;
    int secondPlayerRemainingLives = 2;

    public LevelBoard(int level, boolean isTwoPlayer) {
        this.level = level;
        this.isTwoPlayer = isTwoPlayer;
        FileHandle handle = Gdx.files.internal("ui/numbers/");
        for (FileHandle fh : handle.list()) {
            numIcons.add(new Texture(ImportUtils.importPixmap(fh, 3)));
        }
    }

    public void removeEnemy() {
        remainingEnemies--;
    }

    public void setFirstPlayerRemainingLives(int remainingLives) {
        firstPlayerRemainingLives = remainingLives;
        if (firstPlayerRemainingLives > 9) {
            firstPlayerRemainingLives = 9;
        }
    }

    public void setSecondPlayerRemainingLives(int remainingLives) {
        secondPlayerRemainingLives = remainingLives;
        if (secondPlayerRemainingLives > 9) {
            secondPlayerRemainingLives = 9;
        }
    }

    public void draw(Batch batch) {
        batch.draw(board, 0, 0);

        // Draw enemy tank icons
        for (int i = 0; i < maxEnemies; i++) {
            int dx = (i % 2) * 8;
            int dy = -(i / 2) * 8;
            batch.draw(i < remainingEnemies ? tankIcon : nullIcon,
                    (drawStartPos.getX() + dx) * scale,
                    (drawStartPos.getY() + dy) * scale,
                    8 * scale,
                    8 * scale);
        }
        batch.draw(numIcons.get(firstPlayerRemainingLives), 240 * scale, 80 * scale);
        if (isTwoPlayer) {
            batch.draw(numIcons.get(secondPlayerRemainingLives), 240 * scale, 56 * scale);
        }
        if (level / 10 == 0) {
            batch.draw(nullIcon, 232 * scale, 24 * scale);
        }
        else batch.draw(numIcons.get(level / 10), 232 * scale, 24 * scale);
        batch.draw(numIcons.get(level % 10), 240 * scale, 24 * scale);
    }

    public void dispose() {
        board.dispose();
        tankIcon.dispose();
        nullIcon.dispose();
    }
}
