package sk.tuke.kpi.kp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.Service.*;
import sk.tuke.kpi.kp.Service.CommentPackage.CommentService;
import sk.tuke.kpi.kp.Service.InfoAboutGamePackage.InfoAboutGameService;
import sk.tuke.kpi.kp.Service.PersonPackage.PersonService;
import sk.tuke.kpi.kp.Service.RatingPackage.RatingService;
import sk.tuke.kpi.kp.Service.ScorePackage.ScoreService;
import sk.tuke.kpi.kp.battleship.ConsoleUi.Game;
import sk.tuke.kpi.kp.battleship.Core.Field;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.kpi.kp.Server.*"))
public class SpringClient {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }
    @Bean
    public CommandLineRunner runner(Game game) {
        return args -> game.play();
    }
    @Bean
    public ScoreService scoreService() { return new ScoreServiceRestClient(); }
    @Bean
    public RatingService ratingService() {
        return new RatingServiceRestClient();
    }
    @Bean
    public CommentService commentService() { return new CommentServiceRestClient(); }
    @Bean
    public InfoAboutGameService infoAboutGameService(){return new InfoAboutGameServiceRestClient();}
    @Bean
    public PersonService personService(){return new PersonServiceRestClient();
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Bean
    public Game game(){return new Game();}
}
