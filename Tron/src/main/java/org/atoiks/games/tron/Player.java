package org.atoiks.games.tron;

import java.awt.Color;

import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.IKeyboard;

public class Player {

    public static final int VKEY_UP    = 0;
    public static final int VKEY_LEFT  = 1;
    public static final int VKEY_DOWN  = 2;
    public static final int VKEY_RIGHT = 3;
    public static final int VKEY_POW   = 4;

    public static final int SPEED = 1;

    private int x, y;
    private Direction dir;

    private final Color color;
    private final int tileSize;
    private final int[] wasdx;

    public Player(final int tileSize, final Color c, final int[] wasdx) {
        this.tileSize = tileSize;
        this.color = c;
        this.wasdx = wasdx;
    }

    public void render(final IGraphics g) {
        final int realX = x * tileSize;
        final int realY = y * tileSize;
        g.setColor(color);
        g.fillRect(realX, realY, realX + tileSize, realY + tileSize);
    }

    public void handleInput(final float dt, final IKeyboard kb) {
        if (kb.isKeyDown(wasdx[VKEY_UP]) && dir != Direction.DOWN) {
            dir = Direction.UP;
        }
        if (kb.isKeyDown(wasdx[VKEY_DOWN]) && dir != Direction.UP) {
            dir = Direction.DOWN;
        }
        if (kb.isKeyDown(wasdx[VKEY_LEFT]) && dir != Direction.RIGHT) {
            dir = Direction.LEFT;
        }
        if (kb.isKeyDown(wasdx[VKEY_RIGHT]) && dir != Direction.LEFT) {
            dir = Direction.RIGHT;
        }
    }

    public void update(final float dt, final int maxX, int maxY) {
        switch (dir) {
        case UP:    if ((y -= SPEED) < 0) y = maxY - 1; break;
        case DOWN:  if ((y += SPEED) > maxY - 1) y = 0; break;
        case LEFT:  if ((x -= SPEED) < 0) x = maxX - 1; break;
        case RIGHT: if ((x += SPEED) > maxX - 1) x = 0; break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void reset(final int initX, final int initY, final Direction initDir) {
        x = initX;
        y = initY;
        dir = initDir;
    }
}