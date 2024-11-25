package sk.tuke.kpi.kp.Service.CommentPackage;

import sk.tuke.kpi.kp.Service.ScorePackage.GameStudioException;
import sk.tuke.kpi.kp.Entity.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment) throws GameStudioException;

    List<Comment> getComments(String game) throws GameStudioException;

    void reset() throws GameStudioException;
}

