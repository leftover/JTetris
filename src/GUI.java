import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    Font smallFont = new Font("JetBrains Mono", Font.PLAIN, 18);
    Font bigFont = new Font("JetBrains Mono", Font.PLAIN, 48);
    JFrame frame;
    Canvas canvas;
    Game game;
    JTextField textField;

    public GUI(Game game) {
        this.game = game;
        SwingUtilities.invokeLater(() -> initGUI());
    }

    void initGUI() {
        frame = new JFrame(Const.TITLE);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == Const.ESC) game.isPaused = !game.isPaused;
                if (!game.isPaused) {
                    if (key == Const.LEFT) game.left();
                    else if (key == Const.RIGHT) game.right();
                    else if (key == Const.DOWN) game.step();
                    else if (key == Const.UP) game.rotate();
                    else if (key == Const.SPACEBAR) game.drop();
                    else if (key == Const.ENTER) game.isGameOver = false;
                }
                canvas.repaint();
            }
        });
        canvas = new Canvas();
        canvas.setLayout(null);
        canvas.setBackground(Color.BLACK);
        canvas.setPreferredSize(new Dimension(
                Const.BRICK_SIZE * Const.FIELD_WIDTH, Const.BRICK_SIZE * (Const.FIELD_HEIGHT + 1)));

        textField = new JTextField();
        textField.setBounds(100, 350, 160, 30);
        textField.setBorder(null);
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setFont(smallFont);
        textField.addActionListener(e -> {
            game.updateHighScore(textField.getText());
            game.isNewHighScore = false;
            textField.setVisible(false);
            frame.requestFocus();
            canvas.repaint();
        });
        textField.setVisible(false);
        canvas.add(textField);
        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void update() {
        canvas.repaint();
    }

    class Canvas extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            ((Graphics2D) g).setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            game.field.draw(g);

            if (!game.isGameOver) {
                game.nextBlock.draw(g,Const.NEXT_BLOCK_X, Const.NEXT_BLOCK_Y, Const.NEXT_BLOCK_COLOR);
                game.block.draw(g);
            }
            drawStats(g);
            if (game.isPaused && !game.isGameOver) drawPausePanel(g);
            if (game.isNewHighScore) drawHighScorePanel(g);
            else if (game.isGameOver) drawGameOverPanel(g);
        }

        private void drawStats(Graphics g) {
            g.setColor(new Color(Const.MEDIUM_TEXT_COLOR));
            g.setFont(smallFont);
            g.drawString("SCORE:" + game.score, 10, Const.BRICK_SIZE * Const.FIELD_HEIGHT + 21);
            g.drawString("ROWS:" + game.rowsCleared, 160, Const.BRICK_SIZE * Const.FIELD_HEIGHT + 21);
            g.drawString("LVL:" + game.level, 275, Const.BRICK_SIZE * Const.FIELD_HEIGHT + 21);
        }

        private void drawGameOverPanel(Graphics g) {
            drawPanel(g, 10, 5);
            g.setColor(new Color(Const.LIGHT_TEXT_COLOR));
            drawStringCenter(g, "GAME OVER", bigFont, 180, 290);
            g.setColor(new Color(Const.SCORE_TEXT_COLOR));
            drawStringLeft(g, "1." + game.highScores[0].getName(), smallFont, 58, 320);
            drawStringLeft(g, "2." + game.highScores[1].getName(), smallFont, 58, 340);
            drawStringLeft(g, "3." + game.highScores[2].getName(), smallFont, 58, 360);
            g.setColor(new Color(Const.MEDIUM_TEXT_COLOR));
            drawStringRight(g, "//" + game.highScores[0].getScore(), smallFont, 303, 320);
            drawStringRight(g, "//" + game.highScores[1].getScore(), smallFont, 303, 340);
            drawStringRight(g, "//" + game.highScores[2].getScore(), smallFont, 303, 360);
            g.setColor(new Color(Const.DARK_TEXT_COLOR));
            drawStringCenter(g, "<enter to restart>", smallFont, 180, 380);
        }

        public void drawHighScorePanel(Graphics g) {
            drawPanel(g, 8, 5);
            g.setColor(new Color(Const.SCORE_TEXT_COLOR));
            drawStringCenter(g, "NEW HIGH SCORE!", smallFont, 180, 268);
            g.setColor(new Color(Const.LIGHT_TEXT_COLOR));
            drawStringCenter(g, String.valueOf(game.score), bigFont, 180, 315);
            g.setColor(new Color(Const.MEDIUM_TEXT_COLOR));
            drawStringCenter(g, "ENTER YOUR NAME", smallFont, 180, 340);
            textField.setVisible(true);
            textField.requestFocus();
        }

        public void drawPausePanel(Graphics g) {
            drawPanel(g, 8, 3);
            g.setColor(new Color(Const.LIGHT_TEXT_COLOR));
            drawStringCenter(g, "PAUSED", bigFont, 180, 323);
            g.setColor(new Color(Const.DARK_TEXT_COLOR));
            drawStringCenter(g, "<ESC>", smallFont, 180, 347);
        }

        private void drawPanel(Graphics g, int width, int height) {
            Dimension size = this.getSize();
            int x = (size.width - width * Const.BRICK_SIZE) / 2;
            int y = (size.height - height * Const.BRICK_SIZE) / 2 - Const.BRICK_SIZE;
            g.setColor(new Color(Const.PANEL_COLOR));
            g.fillRect(x, y, width * Const.BRICK_SIZE, height * Const.BRICK_SIZE);
        }

        private void drawStringRight(Graphics g, String text, Font font, int x, int y) {
            g.setFont(font);
            FontMetrics metrics = g.getFontMetrics(font);
            g.drawString(text, x - metrics.stringWidth(text), y);
        }

        private void drawStringLeft(Graphics g, String text, Font font, int x, int y) {
            g.setFont(font);
            g.drawString(text, x, y);
        }

        private void drawStringCenter(Graphics g, String text, Font font, int x, int y) {
            g.setFont(font);
            FontMetrics metrics = g.getFontMetrics(font);
            g.drawString(text, x - metrics.stringWidth(text) / 2, y);
        }
    }
}

