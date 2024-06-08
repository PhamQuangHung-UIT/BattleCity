package com.uit.battlecity.misc;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.uit.battlecity.utils.ImportUtils;
import com.uit.battlecity.utils.Point;

import static com.uit.battlecity.utils.GameConstants.SCALE;

public class LevelBoard extends Actor {
    final Sprite board = new Sprite(new Texture("ui/board.png"));
    final Texture tankIcon = new Texture("ui/tank_icon.png");
    final Texture nullIcon = new Texture("ui/null_icon.png");
    final Sprite tankSprite = new Sprite(tankIcon);
    final Sprite nullSprite = new Sprite(nullIcon);
    final Array<Sprite> numIcons = new Array<>();
    final Point drawStartPos = new Point(232, 200);
    final int maxEnemies = 20;
    final boolean isTwoPlayer;
    private final int level;
    public boolean firstPlayerLastLive = false;
    public boolean secondPlayerLastLive = false;
    int remainingEnemies;
    int firstPlayerRemainingLives;
    int secondPlayerRemainingLives;

    public LevelBoard(int level, boolean isTwoPlayer, int enemyCount) {
        this.level = level;
        this.isTwoPlayer = isTwoPlayer;
        remainingEnemies = enemyCount;
        for (FileHandle fh : ImportUtils.list("ui/numbers")) {
            Sprite digit = new Sprite(new Texture(fh));
            digit.setOrigin(0, 0);
            digit.setScale(SCALE);
            numIcons.add(digit);
        }
        tankSprite.setScale(SCALE);
        tankSprite.setOrigin(0, 0);
        nullSprite.setScale(SCALE);
        nullSprite.setOrigin(0, 0);
        board.setScale(SCALE);
        board.setOrigin(0, 0);
    }

    public void removeEnemy() {
        remainingEnemies--;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        board.draw(batch, parentAlpha);

        // Draw enemy tank icons
        for (int i = 0; i < maxEnemies; i++) {
            int dx = (i % 2) * 8;
            int dy = -(i / 2) * 8;
            Sprite sprite = new Sprite(i < remainingEnemies ? tankSprite : nullSprite);
            sprite.setPosition((drawStartPos.getX() + dx) * SCALE,
                    (drawStartPos.getY() + dy) * SCALE);
            sprite.draw(batch);
        }

        Sprite numIcon = new Sprite(numIcons.get(firstPlayerRemainingLives));
        numIcon.setPosition(240 * SCALE, 80 * SCALE);
        numIcon.draw(batch);
        if (isTwoPlayer) {
            numIcon = new Sprite(numIcons.get(secondPlayerRemainingLives));
            numIcon.setPosition(240 * SCALE, 56 * SCALE);
            numIcon.draw(batch);
        }
        if (level / 10 == 0) {
            nullSprite.setPosition(232 * SCALE, 24 * SCALE);
            nullSprite.draw(batch);
        } else {
            numIcon = numIcons.get(level / 10);
            numIcon.setPosition(232 * SCALE, 24 * SCALE);
            numIcon.draw(batch);
        }
        numIcon = numIcons.get(level % 10);
        numIcon.setPosition(240 * SCALE, 24 * SCALE);
        numIcon.draw(batch);
    }

    public void dispose() {
        board.getTexture().dispose();
        tankIcon.dispose();
        nullIcon.dispose();
    }

    public void addSecondPlayerLive() {
        secondPlayerRemainingLives++;
        if (secondPlayerRemainingLives > 9) {
            secondPlayerRemainingLives = 9;
        }
    }

    public void addFirstPlayerLive() {
        firstPlayerRemainingLives++;
        if (firstPlayerRemainingLives > 9) {
            firstPlayerRemainingLives = 9;
        }
    }

    public void decreaseFirstPlayerLives() {
        firstPlayerRemainingLives--;
        if (firstPlayerRemainingLives < 0) {
            firstPlayerRemainingLives = 0;
            firstPlayerLastLive = true;
        }
    }

    public void decreaseSecondPlayerLives() {
        secondPlayerRemainingLives--;
        if (secondPlayerRemainingLives < 0) {
            secondPlayerRemainingLives = 0;
            secondPlayerLastLive = true;
        }
    }

    public int getFirstPlayerRemainingLives() {
        return firstPlayerRemainingLives;
    }

    public void setFirstPlayerRemainingLives(int remainingLives) {
        firstPlayerRemainingLives = remainingLives;
    }

    public int getSecondPlayerRemainingLives() {
        return secondPlayerRemainingLives;
    }

    public void setSecondPlayerRemainingLives(int remainingLives) {
        secondPlayerRemainingLives = remainingLives;
    }

    public int getLevel() {
        return level;
    }
}
