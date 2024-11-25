package sk.tuke.kpi.kp.Service.InfoAboutGamePackage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.kpi.kp.Entity.InfoAboutGame;
import sk.tuke.kpi.kp.Service.ScorePackage.GameStudioException;

import java.util.List;
@Transactional
public class InfoAboutGameServiceJPA implements InfoAboutGameService{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addInfo(InfoAboutGame infoAboutGame) throws GameStudioException {
        entityManager.persist(infoAboutGame);
    }

    @Override
    public List<InfoAboutGame> getInfos(String game) throws GameStudioException {
        return entityManager.createQuery("SELECT i FROM InfoAboutGame i WHERE i.game = :game", InfoAboutGame.class)
                .setParameter("game", game)
                .getResultList();
    }

    @Override
    public void reset() throws GameStudioException {
        entityManager.createQuery("DELETE FROM InfoAboutGame").executeUpdate();
    }
}
