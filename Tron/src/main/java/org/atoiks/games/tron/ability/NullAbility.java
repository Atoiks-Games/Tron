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