package dev.drdpov.tetris.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.awt.event.KeyEvent.*;

public class Game implements KeyListener {

    private final Panel panel;
    private final AtomicBoolean isGameOver = new AtomicBoolean(false);

    public Game() {
        panel = new Panel();
    }

    public void run() {
        final var gameGui = new GameGUI(this);
        final var timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (isGameOver.get()) return;
                final var gameOver = !panel.tick();
                isGameOver.set(gameOver);
                if (gameOver) {
                    System.out.println("Game over");
                    timer.cancel();
                }
                gameGui.gameFieldChanged(panel.getField());
            }
        };
        timer.scheduleAtFixedRate(task, new Date(), 500);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        final var command = switch (e.getKeyCode()) {
            case VK_LEFT -> Command.LEFT;
            case VK_RIGHT -> Command.RIGHT;
            case VK_SHIFT -> Command.ROTATE_RIGHT;
            case VK_DOWN -> Command.DOWN;
            default -> null;
        };
        panel.move(command);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
