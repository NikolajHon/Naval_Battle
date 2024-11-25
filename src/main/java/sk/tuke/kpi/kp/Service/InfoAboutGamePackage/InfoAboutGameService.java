package sk.tuke.kpi.kp.Service.InfoAboutGamePackage;

import sk.tuke.kpi.kp.Entity.Comment;
import sk.tuke.kpi.kp.Entity.InfoAboutGame;
import sk.tuke.kpi.kp.Service.ScorePackage.GameStudioException;

import java.util.List;

public interface InfoAboutGameService {
    void addInfo(InfoAboutGame infoAboutGame) throws GameStudioException;

    List<InfoAboutGame> getInfos(String game) throws GameStudioException;

    void reset() throws GameStudioException;
}
