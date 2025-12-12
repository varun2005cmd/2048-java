import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameUI extends JFrame {

    private Game2048 game;
    private JButton restartBtn;
    private GamePanel panel;

    public GameUI() {
        game = new Game2048();

        setTitle("2048 Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(420, 560));
        setResizable(true); // FULLSCREEN ENABLED

        // Use BorderLayout to allow resizing
        setLayout(new BorderLayout());

        // Smooth-rendered game panel
        panel = new GamePanel();
        panel.setFocusable(true);
        panel.addKeyListener(new MovementListener());

        // ---------------------------
        // Modernized Restart Button
        // ---------------------------
        restartBtn = new JButton("Restart");
        restartBtn.setFocusPainted(false);
        restartBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        restartBtn.setForeground(Color.WHITE);
        restartBtn.setBackground(new Color(143, 122, 102)); // 2048 brown theme
        restartBtn.setBorderPainted(false);
        restartBtn.setPreferredSize(new Dimension(200, 50));

        // Rounded edges
        restartBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Hover effect
        restartBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                restartBtn.setBackground(new Color(170, 150, 132));
            }
            public void mouseExited(MouseEvent e) {
                restartBtn.setBackground(new Color(143, 122, 102));
            }
        });

        // Restart logic
        restartBtn.addActionListener(e -> {
            game.restart();
            panel.repaint();
            panel.requestFocusInWindow();
        });

        // Add to UI
        add(panel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(250, 248, 239));
        bottomPanel.add(restartBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
        panel.requestFocusInWindow(); // Make panel receive key input
    }

    // --------------------------
    // Smooth Rendering Game Panel
    // --------------------------
    private class GamePanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // dynamic tile & board scaling
            int boardSize = (int)(Math.min(w, h - 100) * 0.85);
            int tileSize = boardSize / 4;

            int startX = (w - boardSize) / 2;
            int startY = (h - boardSize) / 2;

            // Score
            g2.setFont(new Font("Segoe UI", Font.BOLD, 32));
            g2.setColor(new Color(119, 110, 101));
            g2.drawString("Score: " + game.getScore(), startX, startY - 20);

            // Board background
            g2.setColor(new Color(187, 173, 160));
            g2.fillRoundRect(startX - 10, startY - 10, boardSize + 20, boardSize + 20, 20, 20);

            int[][] grid = game.getGrid();

            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {

                    int value = grid[row][col];
                    int x = startX + col * tileSize;
                    int y = startY + row * tileSize;

                    g2.setColor(getTileColor(value));
                    g2.fillRoundRect(x, y, tileSize - 5, tileSize - 5, 18, 18);

                    g2.setColor(getFontColor(value));
                    g2.setFont(new Font("Segoe UI", Font.BOLD, value < 128 ? tileSize/3 : tileSize/4));

                    if (value != 0) {
                        String text = String.valueOf(value);
                        FontMetrics fm = g2.getFontMetrics();
                        int textWidth = fm.stringWidth(text);
                        int textHeight = fm.getAscent();

                        g2.drawString(text, x + (tileSize - textWidth) / 2,
                                y + (tileSize + textHeight) / 2 - 5);
                    }
                }
            }

            // Win message
            if (game.hasWon()) {
                g2.setColor(new Color(0, 150, 0));
                g2.setFont(new Font("Segoe UI", Font.BOLD, 36));
                g2.drawString("🎉 YOU WIN! 🎉", startX, startY + boardSize + 40);
            }

            // Game over message
            if (game.isGameOver()) {
                g2.setColor(Color.RED);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 36));
                g2.drawString("GAME OVER", startX + boardSize / 6, startY + boardSize + 40);
            }
        }
    }

    // Colors for tiles
    private Color getTileColor(int value) {
        switch (value) {
            case 0: return new Color(205, 193, 180);
            case 2: return new Color(238, 228, 218);
            case 4: return new Color(237, 224, 200);
            case 8: return new Color(242, 177, 121);
            case 16: return new Color(245, 149, 99);
            case 32: return new Color(246, 124, 95);
            case 64: return new Color(246, 94, 59);
            case 128: return new Color(237, 207, 114);
            case 256: return new Color(237, 204, 97);
            case 512: return new Color(237, 200, 80);
            case 1024: return new Color(237, 197, 63);
            case 2048: return new Color(237, 194, 46);
            default: return new Color(60, 58, 50);
        }
    }

    private Color getFontColor(int value) {
        return value <= 4 ? new Color(119, 110, 101) : Color.WHITE;
    }

    // Keyboard handler
    private class MovementListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if (game.isGameOver()) return;

            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP: game.move("w"); break;

                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN: game.move("s"); break;

                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT: game.move("a"); break;

                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT: game.move("d"); break;
            }
            panel.repaint();
        }

        @Override public void keyTyped(KeyEvent e) {}
        @Override public void keyReleased(KeyEvent e) {}
    }

    public static void main(String[] args) {
        new GameUI();
    }
}
