//package sk.tuke.kpi.kp.Entity.CommentPackage;
//
//import org.junit.jupiter.api.Test;
//import sk.tuke.kpi.kp.Entity.Comment;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.sql.Date;
//
//public class CommentServiceJDBCTest {
//    private CommentService commentService = new CommentServiceJDBC();
//
//    @Test
//    public void reset() {
//        assertDoesNotThrow(() -> commentService.reset());
//        assertEquals(0, commentService.getComments("mines").size());
//    }
//
//    @Test
//    public void addComment() {
//        commentService.reset();
//        var date = new Date(System.currentTimeMillis());
//        Comment testComment = new Comment("Jaro", "mines", "Test comment", date);
//        assertDoesNotThrow(() -> commentService.addComment(testComment));
//
//        var comments = commentService.getComments("mines");
//        assertEquals(1, comments.size());
//        assertEquals("mines", comments.get(0).getGame());
//        assertEquals("Jaro", comments.get(0).getPlayer());
//        assertEquals("Test comment", comments.get(0).getComment());
//        assertEquals(date.toString(), comments.get(0).getCommentedOn().toString());
//
//    }
//
//    @Test
//    public void getComments() {
//        commentService.reset();
//        var date = new Date(System.currentTimeMillis());
//        commentService.addComment(new Comment("Jaro", "mines", "Comment 1", date));
//        commentService.addComment(new Comment("Katka", "mines", "Comment 2", date));
//        commentService.addComment(new Comment("Zuzka", "tiles", "Comment 3", date));
//        commentService.addComment(new Comment("Jaro", "mines", "Comment 4", date));
//
//        var comments = commentService.getComments("mines");
//
//        assertEquals(3, comments.size());
//
//        assertEquals("mines", comments.get(0).getGame());
//        assertEquals("Jaro", comments.get(0).getPlayer());
//        assertEquals("Comment 1", comments.get(0).getComment());
//        assertEquals(date.toString(), comments.get(0).getCommentedOn().toString());
//
//        assertEquals("mines", comments.get(1).getGame());
//        assertEquals("Katka", comments.get(1).getPlayer());
//        assertEquals("Comment 2", comments.get(1).getComment());
//        assertEquals(date.toString(), comments.get(1).getCommentedOn().toString());
//
//        assertEquals("mines", comments.get(2).getGame());
//        assertEquals("Jaro", comments.get(2).getPlayer());
//        assertEquals("Comment 4", comments.get(2).getComment());
//        assertEquals(date.toString(), comments.get(2).getCommentedOn().toString());
//
//    }
//}
