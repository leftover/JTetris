public class Const {
    static final String TITLE = "Tetris";
    //DIMENSIONS:
    static final int BRICK_SIZE = 30;  //pixels
    static final int FIELD_WIDTH = 12; //bricks
    static final int FIELD_HEIGHT = 22; //bricks
    static final int NEXT_BLOCK_X = 2;
    static final int NEXT_BLOCK_Y = 2;
    //KEYS:
    static final int UP = 38;
    static final int DOWN = 40;
    static final int LEFT = 37;
    static final int RIGHT = 39;
    static final int SPACEBAR = 32;
    static final int ESC = 27;
    static final int ENTER = '\n';

    static final Brick[][] BLOCKS = {
            {new Brick(0, -1), new Brick(-1, 0), new Brick(0, 0), new Brick(1, 0)},   //T
            {new Brick(-1, 0), new Brick(0, 0), new Brick(1, 0), new Brick(2, 0)},    //I
            {new Brick(-1, -1), new Brick(-1, 0), new Brick(0, 0), new Brick(1, 0)},  //J
            {new Brick(1, -1), new Brick(-1, 0), new Brick(0, 0), new Brick(1, 0)},   //L
            {new Brick(-1, -1), new Brick(0, -1), new Brick(-1, 0), new Brick(0, 0)}, //O
            {new Brick(0, -1), new Brick(1, -1), new Brick(-1, 0), new Brick(0, 0)},  //S
            {new Brick(-1, -1), new Brick(0, -1), new Brick(0, 0), new Brick(1, 0)}}; //Z
    //COLORS:
    static final int[] COLORS = {0xE18955, 0xBD5ED3, 0x6879AD, 0x2D8FB7, 0x788F49, 0x789E9E, 0x94BED1};
    static final int NEXT_BLOCK_COLOR = 0x333333;
    static final int FIELD_COLOR = 0x404040;
    static final int PANEL_COLOR = 0x252525;
    static final int LIGHT_TEXT_COLOR = 0xBBBBBB;
    static final int MEDIUM_TEXT_COLOR = 0x6B6B6B;
    static final int DARK_TEXT_COLOR = 0x515151;
    static final int SCORE_TEXT_COLOR = 0x788F49;
    //GAME:
    static final int LEVEL_THRESHOLD = 10;
    static final int[] SCORES = {40, 100, 300, 1200};
    static final int[] DELAYS = {450, 410, 360, 330, 300, 275, 250, 225, 200, 180, 165, 150, 138, 125, 112, 100};
    static final String FILE_NAME = "JTetris_Scores.tmp";
}
