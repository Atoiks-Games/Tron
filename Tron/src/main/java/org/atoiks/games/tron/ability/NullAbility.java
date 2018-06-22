/**
 *   Tron  Copyright (C) 2018  Atoiks-Games <atoiks-games@outlook.com>
 *   This program comes with ABSOLUTELY NO WARRANTY;
 *   This is free software, and you are welcome to redistribute it
 *   under certain conditions;
 */

package org.atoiks.games.tron.ability;

import org.atoiks.games.framework2d.IGraphics;

public class NullAbility implements Ability {

    public void render(final IGraphics g) {
        // Do nothing
    }

    public void update(final float dt) {
        // Do nothing
    }

    public boolean tryActivateAbility() {
        // Ability is never activated
        return false;
    }

    public void reset() {
        // Do nothing
    }
}