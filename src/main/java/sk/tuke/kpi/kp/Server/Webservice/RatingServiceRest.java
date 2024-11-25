package sk.tuke.kpi.kp.Server.Webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.Entity.Rating;
import sk.tuke.kpi.kp.Service.RatingPackage.RatingException;
import sk.tuke.kpi.kp.Service.RatingPackage.RatingService;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {

    @Autowired
    private RatingService ratingService;

    @PostMapping()
    @CrossOrigin(origins = "http://localhost:5173")
    public void setRating(@RequestBody Rating rating) {
        try {
            ratingService.setRating(rating);
        } catch (RatingException e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/average/{game}")
    @CrossOrigin(origins = "http://localhost:5173")
    public int getAverageRating(@PathVariable String game) {
        try {
            return ratingService.getAverageRating(game);
        } catch (RatingException e) {
            e.printStackTrace();
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{game}/{player}")
    public int getRating(@PathVariable String game, @PathVariable String player) {
        try {
            return ratingService.getRating(game, player);
        } catch (RatingException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
