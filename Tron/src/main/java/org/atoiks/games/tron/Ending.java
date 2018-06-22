/**
 *   Tron  Copyright (C) 2018  Atoiks-Games <atoiks-games@outlook.com>
 *   This program comes with ABSOLUTELY NO WARRANTY;
 *   This is free software, and you are welcome to redistribute it
 *   under certain conditions;
 */

package org.atoiks.games.tron;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;

import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;

public final class Ending extends Scene {

    private Image img;
    private String time;

    @Override
    public void render(final IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
        if (img != null) g.drawImage(img, 0, 0);
        g.setColor(Color.white);
        g.drawString(time, 300, 392);
    }

    @Override
    public boolean update(final float dt) {
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
            scene.switchToScene(1);
        }
        return true;
    }

    @Override
    public void resize(int w, int h) {
        // Screen is fixed
    }

    @Override
    public void enter(int from) {
        time = String.format("%.2fs", scene.resources().getOrDefault("state.time", 0.0f));

        final boolean p1Lost = (boolean) scene.resources().getOrDefault("state.p1", false);
        final boolean p2Lost = (boolean) scene.resources().getOrDefault("state.p2", false);

        img = (Image) scene.resources().get(p1Lost == p2Lost ? "/tie.bmp" :
            (p2Lost ? "/p1_win.bmp" : "/p2_win.bmp"));
    }

    @Override
    public void leave() {
        //
    }
}