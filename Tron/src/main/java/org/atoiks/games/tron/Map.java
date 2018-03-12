package org.atoiks.games.tron;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;

import java.util.BitSet;

import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;

public final class Map extends Scene {

    public static final int TILE_SIZE = 10;

    public static final int MAX_X = 600 / TILE_SIZE;
    public static final int MAX_Y = 480 / TILE_SIZE;

    public static final int MATRIX_SIZE = MAX_X * MAX_Y;

    private final BitSet markedTiles = new BitSet(MATRIX_SIZE);

    private Player p1 = new Player(TILE_SIZE, Color.red, new int[]{KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_X});
    private Player p2 = new Player(TILE_SIZE, Color.cyan, new int[]{KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_COMMA});

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

        p1.render(g);
        p2.render(g);

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
        p1.handleInput(dt, scene.keyboard());
        p2.handleInput(dt, scene.keyboard());

        if ((counter += dt) < generateDelay()) return true;
        counter = 0;

        // Collision Testing
        final int p1x = p1.getX();
        final int p1y = p1.getY();
        final int p2x = p2.getX();
        final int p2y = p2.getY();

        final boolean p1Col = markedTiles.get(locateOffset(p1x, p1y));
        final boolean p2Col = markedTiles.get(locateOffset(p2x, p2y));
        if (p1Col || p2Col) {
            scene.resources().put("state.p1", p1Col);
            scene.resources().put("state.p2", p2Col);
            scene.resources().put("state.time", elapsedTime);
            scene.gotoNextScene();
            return true;
        }

        // Mark tiles as occupied
        markedTiles.set(locateOffset(p1x, p1y));
        markedTiles.set(locateOffset(p2x, p2y));

        // Update location
        p1.update(dt, MAX_X, MAX_Y);
        p2.update(dt, MAX_X, MAX_Y);

        return true;
    }

    @Override
    public void resize(int x, int y) {
        // Screen size is fixed
    }

    @Override
    public void enter(int from) {
        p1.reset(10, MAX_Y / 2, Direction.UP);
        p2.reset(MAX_X - 10, MAX_Y / 2, Direction.UP);

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