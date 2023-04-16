import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class GameGUI implements GameFieldChangedListener {

    private final JTextArea label = new JTextArea();

    public GameGUI(KeyListener keyListener) {
        final var frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.setVisible(true);
        final var panel = new JPanel();
        panel.setLayout(new GridLayout());
        panel.setFocusable(true);
        panel.requestFocus(true);
        panel.add(label);
        frame.add(panel);
        panel.addKeyListener(keyListener);
    }

    @Override
    public void gameFieldChanged(char[][] field) {
        final var builder = new StringBuilder();
        for (var line : field) {
            for (var el : line) {
                if (el == '*') builder.append("[]");
                else builder.append("  ");
            }
            builder.append("\n");
        }
        label.setText(builder.toString());
    }
}
