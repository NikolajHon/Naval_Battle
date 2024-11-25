package sk.tuke.kpi.kp.Service.ScorePackage;

import sk.tuke.kpi.kp.Entity.Score;

import java.util.List;

public interface ScoreService {

    void addScore(Score score);

    List<Score> getTopScores(String game);

    void reset();
    Score getPlayerScore(String playerName, String game);

}


