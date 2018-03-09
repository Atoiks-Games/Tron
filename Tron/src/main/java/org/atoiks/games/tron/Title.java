package org.atoiks.games.tron;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;

import org.atoiks.games.framework2d.Scene;
import org.atoiks.games.framework2d.IGraphics;

public final class Title extends Scene {

    private Image img;

    @Override
    public void render(final IGraphics g) {
        if (img != null) g.drawImage(img, 0, 0);
    }

    @Override
    public boolean update(final float dt) {
        if (scene.keyboard().isKeyPressed(KeyEvent.VK_ENTER)) {
            scene.gotoNextScene();
        }
        return true;
    }

    @Override
    public void resize(int w, int h) {
        // Screen size is fixed
    }

    @Override
    public void enter(int from) {
        img = (Image) scene.resources().get("/title.bmp");
    }

    @Override
    public void leave() {
        //
    }
}