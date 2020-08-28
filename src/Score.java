public class Score implements Comparable<Score> {
    private String name;
    private int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Score that) {
        return that.score - this.score;
    }

    @Override
    public String toString() {
        return name + "|" + score;
    }
}
