package sk.tuke.kpi.kp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.Entity.Rating;
import sk.tuke.kpi.kp.Service.RatingPackage.RatingException;
import sk.tuke.kpi.kp.Service.RatingPackage.RatingService;

import java.util.Arrays;
import java.util.List;

@Component
public class RatingServiceRestClient implements RatingService {

    private final String url = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) throws RatingException {
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return restTemplate.getForObject(url + "/average/" + game, Integer.class);
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return restTemplate.getForObject(url + "?game=" + game + "&player=" + player, Integer.class);
    }

    @Override
    public void reset() throws RatingException {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
