package sk.tuke.kpi.kp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.Entity.InfoAboutGame;
import sk.tuke.kpi.kp.Service.InfoAboutGamePackage.InfoAboutGameService;
import sk.tuke.kpi.kp.Service.ScorePackage.GameStudioException;

import java.util.Arrays;
import java.util.List;

@Component
public class InfoAboutGameServiceRestClient implements InfoAboutGameService {

    private final String url = "http://localhost:8080/api/info_about_game";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addInfo(InfoAboutGame infoAboutGame) throws GameStudioException {
        restTemplate.postForEntity(url, infoAboutGame, InfoAboutGame.class);
    }

    @Override
    public List<InfoAboutGame> getInfos(String game) throws GameStudioException {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, InfoAboutGame[].class).getBody()); // отправка GET-запроса для получения информации о игре
    }

    @Override
    public void reset() throws GameStudioException {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}