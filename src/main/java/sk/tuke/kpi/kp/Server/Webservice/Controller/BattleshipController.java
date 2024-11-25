package sk.tuke.kpi.kp.Server.Webservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.kpi.kp.Entity.Score;
import sk.tuke.kpi.kp.Service.ScorePackage.ScoreService;
import sk.tuke.kpi.kp.battleship.ConsoleUi.Game;
import sk.tuke.kpi.kp.battleship.Core.Field;
import sk.tuke.kpi.kp.battleship.Core.User.Base;
import sk.tuke.kpi.kp.battleship.Core.User.Player;
import sk.tuke.kpi.kp.battleship.Core.User.StateGame;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/battleship")
@CrossOrigin(origins = "http://localhost:5173")
@Scope(WebApplicationContext.SCOPE_APPLICATION)
public class BattleshipController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private JsonConverter jsonConverter;

    private Game game = new Game();
    private StateGame stateGame = game.getSt();
    Base[] players = new Base[2];
    private boolean was_init = false;
    int count_of_deck_first = 20;
    int count_of_deck_second = 20;
    private boolean auto_for_first;
    private boolean auto_for_second;
    @PostMapping("/setPlayers")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<String> setPlayers(@RequestBody Map<String, Object> requestData) {
        players[0] = new Player();
        players[1] = new Player();

        players[0].setName((String) requestData.get("player1Name"));
        players[1].setName((String) requestData.get("player2Name"));
        count_of_deck_second = 20;
        count_of_deck_first = 20;
        System.out.println("ALLRIGHT");
        stateGame = StateGame.FirstPlayerPlacesShips;
        return ResponseEntity.ok().body("Players set successfully.");
    }

    @RequestMapping("/convertToJson")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<String> convertFieldsToJson() {
        System.out.println(players[0]);
        System.out.println(players[1]);
        char[][] field1;
        char[][] field2;
        if(stateGame == StateGame.FirstPlayerShoots || stateGame ==StateGame.SecondPlayerShoots){
            field1 = players[0].getenemyFill().getCells();
            field2 = players[1].getenemyFill().getCells();

        }else{
            field1 = players[0].getField().getCells();
            field2 = players[1].getField().getCells();
        }
        String token = stateGame.toString(); // Получаем токен
        String[] playerNames = new String[2];
        playerNames[0] = players[0].getName();
        playerNames[1] = players[1].getName();
        String json = jsonConverter.convertFieldsToJson(field1, field2, token, playerNames);
        System.out.println(json);
        return ResponseEntity.ok().body(json);
    }


    @RequestMapping(value = "/placeShip", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> places_ship(@RequestBody Map<String, String> shipDetails) {
        String row = shipDetails.get("row");
        String column = shipDetails.get("column");
        String shipType = shipDetails.get("shipType");
        String shipDirection = shipDetails.get("shipDirection");
        if(shipDirection == "vertical"){
            shipDirection = "V";
        }
        if(shipDirection == "horizontal"){
            shipDirection = "H";
        }
        String[] input = {row, column, shipType, shipDirection};


        if (stateGame == StateGame.FirstPlayerPlacesShips) {
            game.process(stateGame, (Player) players[0], (Player) players[1], 0, 0, true, input);
            players[0].getField().updete();
            System.out.println("Count of ship"+players[0].getCount_of_ship());
            if(players[0].getCount_of_ship() == 0){
                stateGame = StateGame.SecondPlayerPlacesShips;
            }

        } else {
            game.process(stateGame, (Player) players[1], (Player) players[0], 0, 0, true, input);
            System.out.println("Count of ship"+players[1].getCount_of_ship());
            if(players[1].getCount_of_ship() == 0){
                stateGame = StateGame.FirstPlayerShoots;
            }
        }

        return ResponseEntity.ok().build();
    }
    @RequestMapping(value = "/switchPlayer", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<String> switchPlayer() {
        try {
           if(stateGame == StateGame.FirstPlayerPlacesShips){
               stateGame = StateGame.SecondPlayerPlacesShips;
           } else if (stateGame == StateGame.SecondPlayerPlacesShips) {
               stateGame = StateGame.FirstPlayerShoots;
           }
            return ResponseEntity.ok().body("Fields generate successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reset fields: " + e.getMessage());
        }
    }
    @RequestMapping(value = "/generateAuto", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<String> generateAuto() {
        try {
            if(stateGame == StateGame.FirstPlayerPlacesShips) {
                players[0].setCount_of_palubs(20);
                players[0].setCount_four_deck(1);
                players[0].setCount_one_deck(4);
                players[0].setCount_two_deck(3);
                players[0].setCount_three_deck(2);

                players[0].setField(new Field());
                players[0].setEnemy_fill(new Field());
                game.initializeShips((Player) players[0], (Player) players[1], true);
            }else {
                players[1].setCount_of_palubs(20);
                players[1].setCount_four_deck(1);
                players[1].setCount_one_deck(4);
                players[1].setCount_two_deck(3);
                players[1].setCount_three_deck(2);

                players[1].setField(new Field());
                players[1].setEnemy_fill(new Field());
                game.initializeShips((Player) players[1], (Player) players[0], true);
            }
            return ResponseEntity.ok().body("Fields generate successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reset fields: " + e.getMessage());
        }
    }

    @RequestMapping(value = "/sendCoordinates", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> shoot(@RequestBody Map<String, String> coordinatesMap) {
        if (coordinatesMap.containsKey("row") && coordinatesMap.containsKey("column")) {
            int row = Integer.parseInt(coordinatesMap.get("row"));
            int column = Integer.parseInt(coordinatesMap.get("column"));
            System.out.println(row);
            System.out.println(column);
            System.out.println(stateGame);

            shoot(row, column, (Player) players[0], (Player) players[1]);
            if(count_of_deck_second <= 0){
                stateGame = StateGame.FirstPlayerWon;
                Score score = new Score(players[0].getName(), "battleship", players[0].getScore(), new Date());
                Score score2 = new Score(players[1].getName(), "battleship", players[1].getScore(), new Date());
                scoreService.addScore(score);
                scoreService.addScore(score2);
                players[0].setCount_of_palubs(20);
                players[0].setCount_of_ship(10);
                players[0].setCount_four_deck(1);
                players[0].setCount_one_deck(4);
                players[0].setCount_two_deck(3);
                players[0].setCount_three_deck(2);

            } else if (count_of_deck_first <= 0) {
                stateGame = StateGame.SecondPlayerWon;
                stateGame = StateGame.FirstPlayerWon;
                Score score = new Score(players[0].getName(), "battleship", players[0].getScore(), new Date());
                Score score2 = new Score(players[1].getName(), "battleship", players[1].getScore(), new Date());
                scoreService.addScore(score);
                scoreService.addScore(score2);

                players[1].setCount_of_palubs(20);
                players[1].setCount_of_ship(10);
                players[1].setCount_four_deck(1);
                players[1].setCount_one_deck(4);
                players[1].setCount_two_deck(3);
                players[1].setCount_three_deck(2);
            }
            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.badRequest().body("Invalid coordinates format.");
        }
    }
    @RequestMapping(value = "/resetFields", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<String> resetFields() {
        try {
            if(stateGame == StateGame.FirstPlayerPlacesShips) {
                players[0].setCount_of_palubs(20);
                players[0].setCount_four_deck(1);
                players[0].setCount_one_deck(4);
                players[0].setCount_two_deck(3);
                players[0].setCount_three_deck(2);

                players[0].setField(new Field());
                players[0].setEnemy_fill(new Field());
            }else {
                players[1].setCount_of_palubs(20);
                players[1].setCount_four_deck(1);
                players[1].setCount_one_deck(4);
                players[1].setCount_two_deck(3);
                players[1].setCount_three_deck(2);
                players[1].setEnemy_fill(new Field());
                players[1].setField(new Field());
            }
            return ResponseEntity.ok().body("Fields reset successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reset fields: " + e.getMessage());
        }
    }

    private void shoot(int row, int column, Player player1, Player player2) {
        if (stateGame == StateGame.FirstPlayerShoots) {
            player1.shot(row, column, player1, player2);
            player1.getenemyFill().updete();
            player1.getField().updete();
            player2.getenemyFill().updete();
            player2.getField().updete();
            if (count_of_deck_second == player2.getCount_of_palubs()) {
                stateGame = StateGame.SecondPlayerShoots;
            } else {
                count_of_deck_second--;
            }
        } else if (stateGame == StateGame.SecondPlayerShoots) {
            player1.shot(row, column, player2, player1);
            player2.getenemyFill().updete();
            player2.getField().updete();
            player1.getenemyFill().updete();
            player1.getField().updete();
            if (count_of_deck_first == player1.getCount_of_palubs()) {
                stateGame = StateGame.FirstPlayerShoots;
            } else {
                count_of_deck_first--;
            }
        }
    }




}
