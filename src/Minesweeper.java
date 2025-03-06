import javax.swing.*;
import java.awt.*;

public class Minesweeper extends JFrame {

    public Minesweeper() {
        initUI();
    }

    private void initUI() {
        JLabel statusBar = new JLabel("");
        add(statusBar, BorderLayout.SOUTH);
        add(new Board(statusBar));
        setResizable(false);
        pack();
        setTitle("Minesweeper");

        var path = "src/resources/icon.png";
        Image img = (new ImageIcon(path)).getImage();
        setIconImage(img);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            var ex = new Minesweeper();
            ex.setVisible(true);
        });
    }
}
