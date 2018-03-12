package org.atoiks.games.tron.ability;

import org.atoiks.games.framework2d.IGraphics;

public interface Ability {

    public <T> void render(final IGraphics<T> g);

    public void update(final float dt);

    public boolean tryActivateAbility();

    public void reset();
}