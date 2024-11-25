package sk.tuke.kpi.kp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.Entity.Comment;
import sk.tuke.kpi.kp.Service.CommentPackage.CommentService;
import sk.tuke.kpi.kp.Service.ScorePackage.GameStudioException;

import java.util.Arrays;
import java.util.List;

@Component
public class CommentServiceRestClient implements CommentService {

    private final String url = "http://localhost:8080/api/comment";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addComment(Comment comment) throws GameStudioException {
        restTemplate.postForEntity(url, comment, Comment.class);
    }

    @Override
    public List<Comment> getComments(String game) throws GameStudioException {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, Comment[].class).getBody());

    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
