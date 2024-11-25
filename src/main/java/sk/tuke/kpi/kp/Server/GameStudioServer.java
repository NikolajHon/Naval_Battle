package sk.tuke.kpi.kp.Server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.kpi.kp.Server.Webservice.Controller.JsonConverter;
import sk.tuke.kpi.kp.Service.CommentPackage.CommentService;
import sk.tuke.kpi.kp.Service.CommentPackage.CommentServiceJPA;
import sk.tuke.kpi.kp.Service.InfoAboutGamePackage.InfoAboutGameService;
import sk.tuke.kpi.kp.Service.InfoAboutGamePackage.InfoAboutGameServiceJPA;
import sk.tuke.kpi.kp.Service.InfoAboutGameServiceRestClient;
import sk.tuke.kpi.kp.Service.PersonPackage.PersonService;
import sk.tuke.kpi.kp.Service.PersonPackage.PersonServiceJPA;
import sk.tuke.kpi.kp.Service.RatingPackage.RatingService;
import sk.tuke.kpi.kp.Service.RatingPackage.RatingServiceJPA;
import sk.tuke.kpi.kp.Service.ScorePackage.ScoreService;
import sk.tuke.kpi.kp.Service.ScorePackage.ScoreServiceJPA;
import sk.tuke.kpi.kp.battleship.ConsoleUi.Game;

@SpringBootApplication
@Configuration
@EntityScan("sk.tuke.kpi.kp.Entity")
public class GameStudioServer {
    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class, args);
    }
    @Bean
    public ScoreService scoreService() { return new ScoreServiceJPA(); }
    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }
    @Bean
    public CommentService commentService() { return new CommentServiceJPA(); }
    @Bean
    public InfoAboutGameService infoAboutGameService(){
        return new InfoAboutGameServiceJPA();
    }
    @Bean
    public PersonService personService(){return new PersonServiceJPA();
    }
    @Bean
    public Game game(){return new Game();}
    @Bean
    public JsonConverter jsonConverter(){return new JsonConverter();}
}