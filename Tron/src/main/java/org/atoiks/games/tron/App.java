package org.atoiks.games.tron;

import org.atoiks.games.framework2d.IFrame;
import org.atoiks.games.framework2d.FrameInfo;

import org.atoiks.games.framework2d.swing.Frame;

public class App {

    public static void main(String[] args) {
        final FrameInfo info = new FrameInfo()
                .setTitle("Atoiks Games - Tron")
                .setResizable(false)
                .setSize(600, 480)
                .setScenes(new Loading(), new Title(), new AbilityOpt(), new Map(), new Ending());
        try (final IFrame<?> frame = new Frame(info)) {
            frame.init();
            frame.loop();
        }
    }
}