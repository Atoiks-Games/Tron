package org.atoiks.games.tron;

import java.awt.Color;
import java.awt.Image;
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

    private final BitSet markedTiles = new BitSet(MATRIX_SIZE);

    private int p1x, p1y;
    private Direction p1d;

    private int p2x, p2y;
    private Direction p2d;

    private float counter, elapsedTime;

    private Image img;
    private boolean paused;

    @Override
    public void render(final IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();

        if (paused) {
            if (img != null) g.drawImage(img, 0, 0);
            return;
        }

        // P1
        final int p1RealX = p1x * TILE_SIZE;
        final int p1RealY = p1y * TILE_SIZE;
        g.setColor(Color.red);
        g.fillRect(p1RealX, p1RealY, p1RealX + TILE_SIZE, p1RealY + TILE_SIZE);

        // P2
        final int p2RealX = p2x * TILE_SIZE;
        final int p2RealY = p2y * TILE_SIZE;
        g.setColor(Color.cyan);
        g.fillRect(p2RealX, p2RealY, p2RealX + TILE_SIZE, p2RealY + TILE_SIZE);

        // Marked tiles
        g.setColor(Color.green);
        for (int i = 0; i < MATRIX_SIZE; ++i) {
            if ((i = markedTiles.nextSetBit(i)) < 0) break;
            final int x = i % MAX_X * TILE_SIZE;
            final int y = i / MAX_X * TILE_SIZE;
            g.fillRect(x, y, x + TILE_SIZE, y + TILE_SIZE);
        }
    }

    @Override
    public boolean update(final float dt) {
        if (scene.keyboard().isKeyDown(KeyEvent.VK_ESCAPE)) {
            paused = true;
        }

        if (paused) {
            if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) paused = false;
            else return true;
        }

        elapsedTime += dt;

        // Key handling
        if (scene.keyboard().isKeyDown(KeyEvent.VK_W) && p1d != Direction.DOWN) {
            p1d = Direction.UP;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_S) && p1d != Direction.UP) {
            p1d = Direction.DOWN;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_A) && p1d != Direction.RIGHT) {
            p1d = Direction.LEFT;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_D) && p1d != Direction.LEFT) {
            p1d = Direction.RIGHT;
        }

        if (scene.keyboard().isKeyDown(KeyEvent.VK_I) && p2d != Direction.DOWN) {
            p2d = Direction.UP;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_K) && p2d != Direction.UP) {
            p2d = Direction.DOWN;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_J) && p2d != Direction.RIGHT) {
            p2d = Direction.LEFT;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_L) && p2d != Direction.LEFT) {
            p2d = Direction.RIGHT;
        }

        if ((counter += dt) < generateDelay()) return true;
        counter = 0;

        // Collision Testing
        final boolean p1Col = markedTiles.get(locateOffset(p1x, p1y));
        final boolean p2Col = markedTiles.get(locateOffset(p2x, p2y));
        if (p1Col || p2Col) {
            scene.resources().put("state.p1", p1Col);
            scene.resources().put("state.p2", p2Col);
            scene.gotoNextScene();
            return true;
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
        p1x = 10;
        p1y = MAX_Y / 2;
        p1d = Direction.UP;

        p2x = MAX_X - 10;
        p2y = MAX_Y / 2;
        p2d = Direction.UP;

        markedTiles.clear();

        counter = 0;
        elapsedTime = 0;

        img = (Image) scene.resources().get("/pause.bmp");
        paused = false;
    }

    @Override
    public void leave() {
    }

    private double generateDelay() {
        return Math.pow(1.1, -elapsedTime);
    }

    private static int locateOffset(final int x, final int y) {
        return y * MAX_X + x;
    }
}