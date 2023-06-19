import GameMechanisms.Enemy;

public interface ScoreListener {
    void onScore(boolean win, Enemy enemy);
}
