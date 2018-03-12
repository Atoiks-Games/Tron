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