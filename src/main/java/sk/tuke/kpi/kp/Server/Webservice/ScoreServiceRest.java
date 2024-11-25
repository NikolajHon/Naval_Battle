package sk.tuke.kpi.kp.Server.Webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.Service.ScorePackage.ScoreService;
import sk.tuke.kpi.kp.Entity.Score;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreServiceRest {

    @Autowired
    private ScoreService scoreService;
    @GetMapping("/{game}")
    @CrossOrigin(origins = "http://localhost:5173")
    public List<Score> getTopScores(@PathVariable String game) {
        return scoreService.getTopScores(game);
    }

    @PostMapping
    public void addScore(@RequestBody Score score) {
        scoreService.addScore(score);
    }
}