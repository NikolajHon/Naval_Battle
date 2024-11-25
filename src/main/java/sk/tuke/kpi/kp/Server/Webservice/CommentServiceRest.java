package sk.tuke.kpi.kp.Server.Webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.Entity.Comment;
import sk.tuke.kpi.kp.Service.CommentPackage.CommentService;
import sk.tuke.kpi.kp.Service.ScorePackage.GameStudioException;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentServiceRest {

    @Autowired
    private CommentService commentService;

    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public void addComment(@RequestBody Comment comment) {
        try {
            commentService.addComment(comment);
        } catch (GameStudioException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/{game}")
    @CrossOrigin(origins = "http://localhost:5173")
    public List<Comment> getComments(@PathVariable String game) {
        try {
            return commentService.getComments(game);
        } catch (GameStudioException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/reset")
    public void resetComments() {
        commentService.reset();
    }
}
