import java.awt.*;

public class Field {
    private final int[][] field;
    private final int width;
    private final int height;
    private final Color fieldColor;

    public Field(int width, int height) {
        this.field = new int[height][width];
        this.width = width;
        this.height = height;
        fieldColor = new Color(Const.FIELD_COLOR);
    }

    public int getCell(int x, int y) {
        return field[y][x];
    }

    public void setCell(int val, int x, int y) {
        field[y][x] = val;
    }

    public boolean clearFilledRow() {
        boolean isCleared = false;
        int y = height;
        while (y > 0) {
            y--;
            isCleared = true;
            for (int cell : field[y]) {
                if (cell == 0) {
                    isCleared = false;
                    break;
                }
            }
            if (isCleared) {
                clearRow(y);
                break;
            }
        }
        return isCleared;
    }

    private void clearRow(int idx) {
        for (int y = idx; y > 0; y--) {
            for (int x = 0; x < width; x++) {
                field[y][x] = field[y - 1][x];
            }
        }
    }

    public void draw(Graphics g) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color c = field[y][x] == 0 ? fieldColor : new Color(field[y][x]);
                g.setColor(c);
                g.fillRect(x * Const.BRICK_SIZE, y * Const.BRICK_SIZE, Const.BRICK_SIZE - 1, Const.BRICK_SIZE - 1);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
