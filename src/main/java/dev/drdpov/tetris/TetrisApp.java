package dev.drdpov.tetris;

import dev.drdpov.tetris.game.Game;

public class TetrisApp {

    public static void main(String[] args) {
        final var game = new Game();
        game.run();
    }
}
