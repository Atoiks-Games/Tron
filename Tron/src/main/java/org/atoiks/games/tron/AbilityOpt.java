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

public final class AbilityOpt extends Scene {

    private static final int P1_X = 50;
    private static final int P2_X = 330;
    private static final int[] OPT_Y = new int[]{315, 356, 394};

    public static final int SIZE = 5;

    private Image img;
    private int p1Opt, p2Opt;

    @Override
    public void render(final IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();

        if (img != null) g.drawImage(img, 0, 0);

        g.setColor(Color.white);

        final int y1 = OPT_Y[p1Opt];
        g.fillRect(P1_X, y1, P1_X + SIZE, y1 + SIZE);

        final int y2 = OPT_Y[p2Opt];
        g.fillRect(P2_X, y2, P2_X + SIZE, y2 + SIZE);
    }

    @Override
    public boolean update(final float dt) {
        // Player 1
        if (scene.keyboard().isKeyDown(KeyEvent.VK_W)) {
            p1Opt = 0;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_A)) {
            p1Opt = 1;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_S)) {
            p1Opt = 2;
        }

        // Player 2
        if (scene.keyboard().isKeyDown(KeyEvent.VK_I)) {
            p2Opt = 0;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_J)) {
            p2Opt = 1;
        }
        if (scene.keyboard().isKeyDown(KeyEvent.VK_K)) {
            p2Opt = 2;
        }

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
    public void enter(final int from) {
        img = (Image) scene.resources().get("/ability.bmp");

        p1Opt = p2Opt = 0;
    }

    @Override
    public void leave() {
        scene.resources().put("trait.p1", p1Opt);
        scene.resources().put("trait.p2", p2Opt);
    }
}