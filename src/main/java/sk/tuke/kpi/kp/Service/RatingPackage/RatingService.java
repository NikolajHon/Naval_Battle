package sk.tuke.kpi.kp.Service.RatingPackage;

import sk.tuke.kpi.kp.Entity.Rating;

import java.sql.SQLException;

public interface RatingService {

    void setRating(Rating rating) throws RatingException;

    int getAverageRating(String game) throws RatingException, SQLException;

    int getRating(String game, String player) throws RatingException;

    void reset() throws RatingException;

}