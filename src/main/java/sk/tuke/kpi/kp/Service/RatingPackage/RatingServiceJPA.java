package sk.tuke.kpi.kp.Service.RatingPackage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;
import sk.tuke.kpi.kp.Entity.Rating;

import java.util.Date;

@Component
@Transactional
//@EntityScan( basePackages = {"package sk.tuke.kpi.kp.Entity.Rating"} )
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        // Проверяем, существует ли игрок с данным именем
        Rating existingRating = entityManager.createQuery("SELECT r FROM Rating r WHERE r.player = :player", Rating.class)
                .setParameter("player", rating.getPlayer())
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (existingRating != null) {
            // Если игрок существует, обновляем его рейтинг
            existingRating.setRating(rating.getRating());
            existingRating.setRatedOn(new Date()); // Обновляем дату рейтинга
            entityManager.merge(existingRating);
        } else {
            // Если игрок не существует, создаем новую запись
            entityManager.persist(rating);
        }
    }


    @Override
    public int getAverageRating(String game) throws RatingException {
        Double average = entityManager.createQuery("SELECT AVG(r.rating) FROM Rating r WHERE r.game = :game", Double.class)
                .setParameter("game", game)
                .getSingleResult();
        return average != null ? average.intValue() : 0;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        Rating rating = entityManager.createQuery("SELECT r FROM Rating r WHERE r.game = :game AND r.player = :player", Rating.class)
                .setParameter("game", game)
                .setParameter("player", player)
                .getSingleResult();
        return rating != null ? rating.getRating() : 0;
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createQuery("DELETE FROM Rating").executeUpdate();

    }
}
