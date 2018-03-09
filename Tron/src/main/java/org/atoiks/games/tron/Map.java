package org.atoiks.games.tron;

import java.awt.Color;
import java.awt.event.KeyEvent;

import java.util.BitSet;

import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;

public final class Map extends Scene {

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public static final int TILE_SIZE = 10;
    public static final int TILE_HALF_SIZE = TILE_SIZE / 2;

    public static final int MAX_X = 600 / TILE_SIZE;
    public static final int MAX_Y = 480 / TILE_SIZE;

    public static final int MATRIX_SIZE = MAX_X * MAX_Y;

    public static final int SPEED = 1;

    private int p1x = 10;
    private int p1y = MAX_Y / 2;
    private Direction p1d = Direction.UP;

    private int p2x = MAX_X - 10;
    private int p2y = MAX_Y / 2;
    private Direction p2d = Direction.UP;

    private final BitSet markedTiles = new BitSet(MATRIX_SIZE);

    private float elapsedTime = 0;

    @Override
    public void render(final IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();

        // P1
        final int p1RealX = p1x * TILE_SIZE;
        final int p1RealY = p1y * TILE_SIZE;
        g.setColor(Color.red);
        g.fillRect(p1RealX, p1RealY,
                   p1RealX + TILE_SIZE, p1RealY + TILE_SIZE);

        // P2
        final int p2RealX = p2x * TILE_SIZE;
        final int p2RealY = p2y * TILE_SIZE;
        g.setColor(Color.cyan);
        g.fillRect(p2RealX, p2RealY,
                   p2RealX + TILE_SIZE, p2RealY + TILE_SIZE);

        // Marked tiles
        g.setColor(Color.green);
        for (int i = 0; i < MATRIX_SIZE; ++i) {
            if ((i = markedTiles.nextSetBit(i)) < 0) break;
            final int x = i % MAX_X * TILE_SIZE;
            final int y = i / MAX_X * TILE_SIZE;
            g.fillRect(x, y,
                       x + TILE_SIZE, y + TILE_SIZE);
        }
    }

    @Override
    public boolean update(final float dt) {
        // Key handling
        if (scene.keyboard().isKeyDown(KeyEvent.VK_W)) {
            p1d = Direction.UP;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_S)) {
            p1d = Direction.DOWN;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_A)) {
            p1d = Direction.LEFT;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_D)) {
            p1d = Direction.RIGHT;
        }

        if (scene.keyboard().isKeyDown(KeyEvent.VK_I)) {
            p2d = Direction.UP;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_K)) {
            p2d = Direction.DOWN;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_J)) {
            p2d = Direction.LEFT;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_L)) {
            p2d = Direction.RIGHT;
        }

        if ((elapsedTime += dt) < 0.2) return true;
        elapsedTime = 0;

        // Collision Testing
        if (markedTiles.get(locateOffset(p1x, p1y))) {
            // P1 lost
            return false;
        }
        if (markedTiles.get(locateOffset(p2x, p2y))) {
            // P2 lost
            return false;
        }

        // P1
        markedTiles.set(locateOffset(p1x, p1y));
        switch (p1d) {
        case UP:    if ((p1y -= SPEED) < 0) p1y = MAX_Y - 1; break;
        case DOWN:  if ((p1y += SPEED) > MAX_Y - 1) p1y = 0; break;
        case LEFT:  if ((p1x -= SPEED) < 0) p1x = MAX_X - 1; break;
        case RIGHT: if ((p1x += SPEED) > MAX_X - 1) p1x = 0; break;
        }

        // P2
        markedTiles.set(locateOffset(p2x, p2y));
        switch (p2d) {
        case UP:    if ((p2y -= SPEED) < 0) p2y = MAX_Y - 1; break;
        case DOWN:  if ((p2y += SPEED) > MAX_Y - 1) p2y = 0; break;
        case LEFT:  if ((p2x -= SPEED) < 0) p2x = MAX_X - 1; break;
        case RIGHT: if ((p2x += SPEED) > MAX_X - 1) p2x = 0; break;
        }

        return true;
    }

    @Override
    public void resize(int x, int y) {
        // Screen size is fixed
    }

    @Override
    public void enter(int from) {
        elapsedTime = 0;
    }

    @Override
    public void leave() {
    }

    private static int locateOffset(final int x, final int y) {
        return y * MAX_X + x;
    }
}