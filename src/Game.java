import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class Game {
    Random random = new Random();
    String tmpdir;
    GUI gui;
    Field field;
    Block block;
    Block nextBlock;
    boolean isPaused;
    boolean isGameOver;
    boolean isNewHighScore;
    int level;
    int score;
    Score[] highScores;
    int rowsCleared;

    public static void main(String[] args) {
        Game game = new Game();
        game.delay(200);
        game.loop();
    }

    public Game() {
        initGame();
        gui = new GUI(this);
        tmpdir = System.getProperty("java.io.tmpdir");
        loadHighScores();
    }

    void initGame() {
        score = 0;
        level = 0;
        rowsCleared = 0;
        field = new Field(Const.FIELD_WIDTH, Const.FIELD_HEIGHT);
        block = new Block(random.nextInt(Const.BLOCKS.length));
        nextBlock = new Block(random.nextInt(Const.BLOCKS.length));
    }

    void loop() {
        while (true) {
            initGame();
            while (!isGameOver) {
                delay(Const.DELAYS[level]);
                while (isPaused) delay(50);
                gui.update();
                int rows = 0;
                while (field.clearFilledRow()) {
                    rows++;
                    gui.update();
                    delay(30);
                }
                updateStats(rows);
                step();
            }

            if (isNewHighScore()) {
                isNewHighScore = true;
                gui.update();
            }

            while (isGameOver) delay(50);
        }
    }

    void right() {
        block.moveRight();
        if (isCollided()) block.moveLeft();
    }

    void left() {
        block.moveLeft();
        if (isCollided()) block.moveRight();
    }

    void step() {
        block.moveDown();
        if (isCollided()) {
            block.moveUp();
            freeze();
        }
    }

    void rotate() {
        block.rotate(true);
        if (isCollided()) {
            block.rotate(false);
        }
    }

    void drop() {
        while (!isCollided()) block.moveDown();
        block.moveUp();
        freeze();
    }

    void freeze() {
        for (Brick brick : block.getBricks()) {
            int bX = block.getX() + brick.x;
            int bY = block.getY() + brick.y;
            if (bY < 0) isGameOver = true;
            if (bY >= 0) field.setCell(block.getColor(), bX, bY);
        }
        block = nextBlock;
        nextBlock = new Block(random.nextInt(Const.BLOCKS.length));
    }

    void updateStats(int rows) {
        this.rowsCleared += rows;
        if (rows != 0) score += Const.SCORES[rows - 1] * (level + 1);
        level = Math.min(rowsCleared / Const.LEVEL_THRESHOLD, 15);
    }

    public boolean isCollided() {
        for (Brick brick : block.getBricks()) {
            int bX = block.getX() + brick.x;
            int bY = block.getY() + brick.y;
            if (bX < 0 || bX >= field.getWidth()) return true;
            if (bY >= field.getHeight()) return true;
            if (bY >= 0 && field.getCell(bX, bY) != 0) return true;
        }
        return false;
    }

    boolean isNewHighScore() {
        return score > highScores[2].getScore();
    }

    public void updateHighScore(String name) {
        highScores[2] = new Score(name.trim(), score);
        isNewHighScore = false;
        Arrays.sort(highScores);
        saveHighScores();
    }

    public void loadHighScores() {
        highScores = new Score[3];
        String path = tmpdir + Const.FILE_NAME;
        try (FileInputStream fis = new FileInputStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            for (int i = 0; i < highScores.length; i++) {
                String[] score = reader.readLine().split("\\|");
                highScores[i] = new Score(score[0], Integer.parseInt(score[1]));
            }
        } catch (IOException e) {
            System.out.println("WARNING: File not found.");
            for (int i = 0; i < highScores.length; i++) {
                highScores[i] = new Score("NULL", 0);
            }
        }
    }

    public void saveHighScores() {
        String path = tmpdir + Const.FILE_NAME;
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path, false), 1024)) {
            for (Score score : highScores) {
                outputStream.write((score.toString() + System.lineSeparator()).getBytes());
            }
        } catch (IOException e) {
            System.out.println("ERROR: Unable to write a file.");
        }
    }

    void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}
