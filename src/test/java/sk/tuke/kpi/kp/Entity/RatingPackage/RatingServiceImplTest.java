//package sk.tuke.kpi.kp.Entity.RatingPackage;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import sk.tuke.kpi.kp.Entity.Rating;
//
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class RatingServiceImplTest {
//
//    private RatingServiceImpl ratingService;
//
//    @BeforeEach
//    void setUp() {
//        ratingService = new RatingServiceImpl();
//    }
//
//    @Test
//    void setRating() throws RatingException {
//        Rating rating = new Rating("Player1", "Game1", 4, new Date());
//        ratingService.setRating(rating);
//        assertEquals(4, ratingService.getRating("Game1", "Player1"));
//    }
//
//    @Test
//    void getAverageRating() throws RatingException {
//        Rating rating1 = new Rating("Player1", "Game1", 4, new Date());
//        Rating rating2 = new Rating("Player2", "Game1", 3, new Date());
//        Rating rating3 = new Rating("Player3", "Game1", 5, new Date());
//        ratingService.setRating(rating1);
//        ratingService.setRating(rating2);
//        ratingService.setRating(rating3);
//        assertEquals(4, ratingService.getAverageRating("Game1"));
//    }
//
//    @Test
//    void getRating() throws RatingException {
//        Rating rating = new Rating("Player1", "Game1", 4, new Date());
//        ratingService.setRating(rating);
//        assertEquals(4, ratingService.getRating("Game1", "Player1"));
//    }
//
//    @Test
//    void reset() throws RatingException {
//        Rating rating = new Rating("Player1", "Game1", 4, new Date());
//        ratingService.setRating(rating);
//        ratingService.reset();
//        assertEquals(0, ratingService.getAverageRating("Game1"));
//    }
//}
