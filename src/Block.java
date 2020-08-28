import java.awt.*;

public class Block {
    private Brick[] bricks;
    private int color;
    private int x;
    private int y;

    Block(int type) {
        //TODO: create new array with cloned bricks
        bricks = Const.BLOCKS[type];
        color = Const.COLORS[type];
        this.x = Const.FIELD_WIDTH / 2;
        this.y = 0;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void moveDown() {
        y++;
    }

    public void moveUp() {
        y--;
    }

    public void rotate(boolean right) {
        for (Brick brick : bricks) {
            int tmp = brick.x;
            brick.x = brick.y;
            brick.y = tmp;
            if (right) brick.x = -brick.x;
            else brick.y = -brick.y;
        }
    }

    public void draw(Graphics g) {
        draw(g, x, y, color);
    }

    public void draw(Graphics g, int x, int y, int color) {
        for (Brick brick : bricks) {
            g.setColor(new Color(color));
            int pixX = (x + brick.x) * Const.BRICK_SIZE;
            int pixY = (y + brick.y) * Const.BRICK_SIZE;
            g.fillRect(pixX, pixY, Const.BRICK_SIZE - 1, Const.BRICK_SIZE - 1);
        }
    }

    public Brick[] getBricks() {
        return bricks;
    }

    public int getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
