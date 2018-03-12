package org.atoiks.games.tron;

import java.awt.Color;

import java.io.InputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;

public final class Loading extends Scene {

    private enum LoadState {
        WAITING, LOADING, DONE, NO_RES
    }

    public static final int RADIUS = 100;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 480;

    private final ExecutorService loader = Executors.newSingleThreadExecutor();

    private LoadState loaded = LoadState.WAITING;

    private float time;

    @Override
    public void render(final IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
        g.setColor(Color.white);
        for (double i = 0; i < Math.PI * 2; i += Math.PI / 6) {
            final int x = (int) (Math.cos(time + i) * Math.sin(time) * RADIUS) + WIDTH / 2;
            final int y = (int) (Math.sin(time + i) * Math.sin(time) * RADIUS) + HEIGHT / 2;
            g.drawOval(x - 5, y - 5, x + 5, y + 5);
        }
    }

    @Override
    public boolean update(final float dt) {
        time += dt;
        switch (loaded) {
        case NO_RES:  return false;
        case LOADING: break;
        case DONE:
            loader.shutdown();
            scene.gotoNextScene();
            break;
        case WAITING:
            loaded = LoadState.LOADING;
            loader.submit(() -> {
                loadResourceAsImage("/title.bmp");
                loadResourceAsImage("/ability.bmp");
                loadResourceAsImage("/pause.bmp");
                loadResourceAsImage("/tie.bmp");
                loadResourceAsImage("/p1_win.bmp");
                loadResourceAsImage("/p2_win.bmp");

                loaded = LoadState.DONE;
            });
            break;
        }
        return true;
    }

    @Override
    public void resize(int w, int h) {
        // Do nothing
    }

    @Override
    public void enter(int from) {
        //
    }

    @Override
    public void leave() {
        //
    }

    private void loadResourceAsImage(final String path) {
        try {
            final InputStream is = this.getClass().getResourceAsStream(path);
            if (is != null) {
                scene.resources().put(path, ImageIO.read(is));
                return;
            }
        } catch (IOException ex) {
            //
        }
        loaded = LoadState.NO_RES;
    }
}