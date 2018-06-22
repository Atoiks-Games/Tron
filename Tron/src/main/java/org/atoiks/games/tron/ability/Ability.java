/**
 *   Tron  Copyright (C) 2018  Atoiks-Games <atoiks-games@outlook.com>
 *   This program comes with ABSOLUTELY NO WARRANTY;
 *   This is free software, and you are welcome to redistribute it
 *   under certain conditions;
 */

package org.atoiks.games.tron.ability;

import org.atoiks.games.framework2d.IGraphics;

public interface Ability {

    public <T> void render(final IGraphics<T> g);

    public void update(final float dt);

    public boolean tryActivateAbility();

    public void reset();
}