package sk.tuke.kpi.kp.Service.ScorePackage;


import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;
import sk.tuke.kpi.kp.Entity.Score;

import java.util.List;

@Component
@Transactional
//@EntityScan( basePackages = {"package sk.tuke.kpi.kp.Entity.Score"} )
public class ScoreServiceJPA implements ScoreService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score score) throws GameStudioException {
        entityManager.persist(score);
    }

    @Override
    public List<Score> getTopScores(String game) throws GameStudioException {
        return entityManager.createQuery("SELECT s FROM Score s WHERE s.game = :game ORDER BY s.points DESC", Score.class)
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public void reset() {
        entityManager.createQuery("DELETE FROM Score").executeUpdate();

    }

    @Override
    public Score getPlayerScore(String playerName, String game) {
        try {
            return entityManager.createQuery("SELECT s FROM Score s WHERE s.player = :playerName AND s.game = :game", Score.class)
                    .setParameter("playerName", playerName)
                    .setParameter("game", game)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
