package sk.tuke.kpi.kp.Server.Webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.Entity.InfoAboutGame;
import sk.tuke.kpi.kp.Service.ScorePackage.GameStudioException;
import sk.tuke.kpi.kp.Service.InfoAboutGamePackage.InfoAboutGameService;

import java.util.List;

@RestController
@RequestMapping("/api/info_about_game")
public class InfoAboutGameServiceRest{

    @Autowired
    private InfoAboutGameService infoAboutGameService;


    @PostMapping
    public void addInfo(@RequestBody InfoAboutGame infoAboutGame) {
        try {
            infoAboutGameService.addInfo(infoAboutGame);
        } catch (GameStudioException e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/{game}")
    public List<InfoAboutGame> getInfos(@PathVariable String game) {
        try {
            return infoAboutGameService.getInfos(game);
        } catch (GameStudioException e) {
            e.printStackTrace();
            return null;
        }
    }


    @PostMapping("/reset")
    public void reset() {
        infoAboutGameService.reset();
    }
}
