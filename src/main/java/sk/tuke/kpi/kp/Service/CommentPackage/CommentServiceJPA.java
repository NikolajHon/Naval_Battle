package sk.tuke.kpi.kp.Service.CommentPackage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.kpi.kp.Service.ScorePackage.GameStudioException;
import sk.tuke.kpi.kp.Entity.Comment;

import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) throws GameStudioException {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) throws GameStudioException {
        return entityManager.createQuery("SELECT c FROM Comment c WHERE c.game = :game", Comment.class)
                .setParameter("game", game)
                .getResultList();
    }

    @Override
    public void reset() {
        entityManager.createQuery("DELETE FROM Comment").executeUpdate();
    }
}
