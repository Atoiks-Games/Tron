package org.atoiks.games.tron;

import java.awt.Color;

import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.IKeyboard;

import org.atoiks.games.tron.ability.Ability;
import org.atoiks.games.tron.ability.NullAbility;

public class Player {

    public static final int VKEY_UP    = 0;
    public static final int VKEY_LEFT  = 1;
    public static final int VKEY_DOWN  = 2;
    public static final int VKEY_RIGHT = 3;
    public static final int VKEY_POW   = 4;

    public Ability ability;

    private int x, y;
    private Direction dir;
    private int speed;
    private boolean shieldOn;

    private final Color color;
    private final int tileSize;
    private final int[] wasdx;

    public Player(final int tileSize, final Color c, final int[] wasdx) {
        this.tileSize = tileSize;
        this.color = c;
        this.wasdx = wasdx;
        this.ability = new NullAbility();
    }

    public void render(final IGraphics g) {
        final int realX = x * tileSize;
        final int realY = y * tileSize;
        g.setColor(color);
        g.fillRect(realX, realY, realX + tileSize, realY + tileSize);
        ability.render(g);
    }

    public void handleInput(final float dt, final IKeyboard kb) {
        ability.update(dt);
        if (kb.isKeyDown(wasdx[VKEY_UP]) && dir != Direction.DOWN) {
            dir = Direction.UP;
        }
        if (kb.isKeyDown(wasdx[VKEY_DOWN]) && dir != Direction.UP) {
            dir = Direction.DOWN;
        }
        if (kb.isKeyDown(wasdx[VKEY_LEFT]) && dir != Direction.RIGHT) {
            dir = Direction.LEFT;
        }
        if (kb.isKeyDown(wasdx[VKEY_RIGHT]) && dir != Direction.LEFT) {
            dir = Direction.RIGHT;
        }
        if (kb.isKeyDown(wasdx[VKEY_POW])) {
            ability.tryActivateAbility();
        }
    }

    public void update(final float dt, final int maxX, int maxY) {
        switch (dir) {
        case UP:    if ((y -= speed) < 0) y = maxY - 1; break;
        case DOWN:  if ((y += speed) > maxY - 1) y = 0; break;
        case LEFT:  if ((x -= speed) < 0) x = maxX - 1; break;
        case RIGHT: if ((x += speed) > maxX - 1) x = 0; break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void reset(final int initX, final int initY, final Direction initDir) {
        ability.reset();

        x = initX;
        y = initY;
        dir = initDir;
        speed = 1;
        shieldOn = false;
    }

    public boolean onHit() {
        return !shieldOn;
    }

    public class ShieldAbility implements Ability {

        public static final float TIMEOUT  = 1f;
        public static final float COOLDOWN = TIMEOUT + 1.5f;

        private float elapsed = COOLDOWN;

        @Override
        public void render(final IGraphics g) {
            if (elapsed < TIMEOUT) {
                final int realX = x * tileSize;
                final int realY = y * tileSize;
                g.setColor(Color.magenta);
                g.drawRect(realX - 5, realY - 5, realX + tileSize + 5, realY + tileSize + 5);
            }
        }

        @Override
        public void update(final float dt) {
            if ((elapsed += dt) < TIMEOUT) {
                shieldOn = true;
            } else {
                shieldOn = false;
            }
        }

        @Override
        public boolean tryActivateAbility() {
            if (elapsed >= COOLDOWN) {
                elapsed = 0;
                return true;
            }
            return false;
        }

        @Override
        public void reset() {
            elapsed = COOLDOWN;
        }
    }

    public class DashAbility implements Ability {

        public static final float TIMEOUT = 1f;

        private float elapsed = TIMEOUT;

        @Override
        public void render(final IGraphics g) {
            if (elapsed < TIMEOUT) {
                final int realX = x * tileSize;
                final int realY = y * tileSize;
                g.setColor(Color.yellow);
                g.drawRect(realX - 5, realY - 5, realX + tileSize + 5, realY + tileSize + 5);
            }
        }

        @Override
        public void update(final float dt) {
            if ((elapsed += dt) < TIMEOUT) {
                speed = 2;
            } else {
                speed = 1;
            }
        }

        @Override
        public boolean tryActivateAbility() {
            if (elapsed >= TIMEOUT) {
                elapsed = 0;
                return true;
            }
            return false;
        }

        @Override
        public void reset() {
            elapsed = TIMEOUT;
        }
    }
}