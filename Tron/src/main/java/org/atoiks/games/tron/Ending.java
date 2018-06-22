/**
 *  Tron
 *  Copyright (C) 2018  Atoiks-Games <atoiks-games@outlook.com>

 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
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