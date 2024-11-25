package sk.tuke.kpi.kp.battleship.ConsoleUi;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.kpi.kp.Entity.Comment;
import sk.tuke.kpi.kp.Entity.InfoAboutGame;
import sk.tuke.kpi.kp.Entity.Rating;
import sk.tuke.kpi.kp.Service.CommentPackage.CommentService;
import sk.tuke.kpi.kp.Service.InfoAboutGamePackage.InfoAboutGameService;
import sk.tuke.kpi.kp.Service.RatingPackage.RatingException;
import sk.tuke.kpi.kp.Service.RatingPackage.RatingService;
import sk.tuke.kpi.kp.Service.ScorePackage.GameStudioException;
import sk.tuke.kpi.kp.Service.ScorePackage.ScoreService;
import sk.tuke.kpi.kp.battleship.Core.Field;
import sk.tuke.kpi.kp.Entity.Score;
import sk.tuke.kpi.kp.battleship.Core.Input;
import sk.tuke.kpi.kp.battleship.Core.User.Base;
import sk.tuke.kpi.kp.battleship.Core.User.Listner;
import sk.tuke.kpi.kp.battleship.Core.User.Player;
import sk.tuke.kpi.kp.battleship.Core.User.StateGame;
import org.springframework.stereotype.Component;

import java.util.regex.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Component
public class Game {
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private InfoAboutGameService infoAboutGameService;
    Scanner scanner = new Scanner(System.in);
    Input input_class = null;
    Listner listner = null;

    private char[][] first_player_field;
    private char[][] second_player_field;
    private boolean is_game_over;
    private StateGame st;
    private Base[] players;
    private int current_player;

    public Base[] getPlayers() {
        return players;
    }

    private void next_step() {
        current_player++;
        if (current_player >= players.length) {
            current_player = 0;
        }
    }

    public Game() {
        this.players = new Base[2];
        st = StateGame.FirstPlayerPlacesShips;
    }

    public void play() throws RatingException {
        String[] args = {"kolya", "ihor", "auto", "auto"};

        String name_first = args[0];
        String name_second = args[1];

        st = StateGame.Initialized;

        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                st = StateGame.FirstPlayerPlacesShips;
            } else {
                st = StateGame.SecondPlayerPlacesShips;
            }
            players[i] = new Player(this);
            players[i].setShoot_now(i == 0);
            players[i].setName((i == 0) ? name_first : name_second);

            Score playerScore = new Score();
            int score = (playerScore != null) ? playerScore.getPoints() : 100;
            players[i].setScore(score);

            String placementChoice = args[i + 2];
            boolean auto = placementChoice.equalsIgnoreCase("auto");
            initializeShips((Player) players[i], (Player) players[(i + 1) % 2], auto);
            if (is_game_over) {
                write_game_over();
                return;
            }
            players[i].getField().updete();
        }
        first_player_field = players[0].getField().getCells();
        second_player_field = players[1].getField().getCells();
        players[0].setEnemy_fill(new Field());
        players[1].setEnemy_fill(new Field());
        st = StateGame.FirstPlayerShoots;

        drawBattleField((Player) players[0], (Player) players[1]);
        is_game_over = false;

        while (players[0].getCount_of_palubs() != 0 && players[1].getCount_of_palubs() != 0) {
            while (players[0].isShoot_now() && !players[1].isShoot_now() && players[1].getCount_of_palubs() != 0) {
                playTurn((Player) players[0], (Player) players[1]);
                drawBattleField((Player) players[0], (Player) players[1]);
                second_player_field = players[1].getField().getCells();
                if (!players[0].isShoot_now()) {
                    players[1].setShoot_now(true);
                }
            }

            while (!players[0].isShoot_now() && players[1].isShoot_now() && players[0].getCount_of_palubs() != 0) {
                playTurn((Player) players[1], (Player) players[0]);
                drawBattleField((Player) players[0], (Player) players[1]);
                first_player_field = players[0].getField().getCells();
                if (!players[1].isShoot_now()) {
                    players[0].setShoot_now(true);
                }
            }
        }

        printWinner((Player) players[0], (Player) players[1]);
        endGame();
        displayTopPlayers();

    }

    private void endGame() {
        InfoAboutGame infoAboutGame;
        if (players[0].getCount_of_palubs() == 0) {
            infoAboutGame = new InfoAboutGame("battleship", players[0].getName(), players[1].getName(), players[0].getName(), players[0].getScore(), players[1].getScore(), new Date());
        } else {
            infoAboutGame = new InfoAboutGame("battleship", players[0].getName(), players[1].getName(), players[1].getName(), players[0].getScore(), players[1].getScore(), new Date());
        }
        infoAboutGameService.addInfo(infoAboutGame);
        for (Base player : players) {
            Score score = new Score(player.getName(), "battleship", player.getScore(), new Date());

            try {
                System.out.println(score);
                scoreService.addScore(score);
            } catch (Exception e) {
                System.err.println("Error when saving an invoice: " + e.getMessage());
            }

            int playerRating = getPlayerRating();
            Rating rating = new Rating(player.getName(), "battleship", playerRating, new Date());
            System.out.println(rating);
            try {
                ratingService.setRating(rating);
            } catch (RatingException e) {
                System.err.println("Error when saving rating: " + e);
            }
            String playerFeedback = getPlayerFeedback();
            Comment comment = new Comment(player.getName(), "battleship", playerFeedback, new Date());
            try {
                commentService.addComment(comment);
            } catch (GameStudioException e) {
                System.err.println("Error when saving feedback: " + e);
            }
        }
        try {
            System.out.println(ratingService.getAverageRating("battleship"));
        } catch (RatingException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            List<Comment> comments = commentService.getComments("battleship");
            System.out.println("Comments for the game Battleship:");
            for (Comment comment : comments) {
                System.out.println("Author: " + comment.getPlayer());
                System.out.println("Commentary: " + comment.getComment());
                System.out.println(); // Пустая строка для разделения комментариев
            }
        } catch (GameStudioException e) {
            System.err.println("Error when receiving comments: " + e.getMessage());
        }
        List<InfoAboutGame> list_of_info = infoAboutGameService.getInfos("battleship");
        System.out.println("Match information:");
        for (InfoAboutGame infoAboutGame1 : list_of_info) {
            System.out.println(infoAboutGame1);
        }


    }

    private String getPlayerFeedback() {
        System.out.println("Please write your review of the game: ");
        return scanner.nextLine();
    }

    private int getPlayerRating() {
        System.out.println("Please rate the game from 1 to 5 (where 1 is bad, 5 is excellent): ");
        int rating;
        do {
            try {
                rating = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                rating = -1;
            }
            if (rating < 1 || rating > 5) {
                System.out.println("Please enter a rating from 1 to 5.");
            }
        } while (rating < 1 || rating > 5);
        return rating;
    }

    private void displayTopPlayers() {

        List<Score> topScores = scoreService.getTopScores("battleship");

        System.out.println("Top Players:");
        for (Score score : topScores) {
            System.out.println(score.getPlayer() + ": " + score.getPoints());
        }
    }

    public void initializeShips(Player currentPlayer, Player opponentPlayer, Boolean auto) {
        if (auto) {
            currentPlayer.placeShipsRandomly();
            return;
        }

        while (currentPlayer.getCount_of_ship() != 0 && !is_game_over) {
            write_count_of_ship(currentPlayer);
            if (st == StateGame.FirstPlayerPlacesShips) {
                process(StateGame.FirstPlayerPlacesShips, currentPlayer, opponentPlayer, 0, 0, false, null);
            } else if (st == StateGame.SecondPlayerPlacesShips) {
                process(StateGame.SecondPlayerPlacesShips, currentPlayer, opponentPlayer, 0, 0, false, null);
            }
        }
    }

    private void drawBattleField(Player player1, Player player2) {
        draw_battle(player1, player2);
    }


    private void playTurn(Player currentPlayer, Player opponentPlayer) {
        if (st == StateGame.FirstPlayerShoots) {
            process(StateGame.FirstPlayerShoots, currentPlayer, opponentPlayer, 0, 0, false,null);
        } else if (st == StateGame.SecondPlayerShoots) {
            process(StateGame.SecondPlayerShoots, currentPlayer, opponentPlayer, 0, 0, false, null);
        }
    }

    public StateGame getSt() {
        return st;
    }

    public void setgame_over() {
        this.is_game_over = true;
    }

    public static void draw(Player player) {
        char[][] cells = player.getField().getCells();
        System.out.print("  |");
        for (int x = 1; x <= 10 - 1; x++) {
            System.out.print("\033[34m " + x + " \033[0m|");
        }
        System.out.print("\033[34m " + 10 + "\033[0m|");
        System.out.println();
        System.out.print("  +");
        for (int x = 0; x < 10; x++) {
            System.out.print("---+");
        }
        System.out.println();
        for (int y = 0; y < 10; ++y) {
            if (y != 9) {
                System.out.print("\033[34m" + (y + 1) + " \033[0m|");
            } else {
                System.out.print("\033[34m" + (y + 1) + "\033[0m|");
            }
            for (int x = 0; x < 10; ++x) {
                String backgroundColor;
                if (cells[x][y] == 'M') {
                    // Желтый цвет для символов, представляющих мины
                    backgroundColor = "\033[43m";
                } else {
                    backgroundColor = (cells[x][y] == ' ') ? "\033[44m" : "\033[42m";
                }
                System.out.print(backgroundColor + "\033[30m " + cells[x][y] + " \033[0m|");
            }

            System.out.println();
            System.out.print("  +");
            for (int x = 0; x < 10; x++) {
                System.out.print("---+");
            }
            System.out.println();
        }
    }

    public void draw_battle(Player player1, Player player2) {
        Field gameField = player1.getField();
        if (gameField == null) {
            System.out.println("Game field is not set. Please set the game field using setGameField method.");
            return;
        }

        player1.getenemyFill().updete();
        player2.getenemyFill().updete();

        // Draw headers for Player 1 and Player 2
        System.out.println(player1.getName() + "                                                                   " + player2.getName());
        System.out.print("  |");
        for (int x = 1; x <= gameField.width - 1; x++) {
            System.out.print("\033[34m " + x + " \033[0m|");
        }
        System.out.print("\033[34m " + gameField.width + "\033[0m|");
        System.out.print("\t\t\t\t\t\t\t");
        for (int x = 1; x <= gameField.width - 1; x++) {
            System.out.print("\033[34m " + x + " \033[0m|");
        }
        System.out.print("\033[34m " + gameField.width + "\033[0m|");
        System.out.println();

        System.out.print("  +");
        for (int x = 0; x < gameField.width; x++) {
            System.out.print("---+");
        }
        System.out.print("\t\t\t\t\t\t\t");
        for (int x = 0; x < gameField.width; x++) {
            System.out.print("---+");
        }
        System.out.println();

        for (int y = 0; y < gameField.height; ++y) {
            if (y != 9) {
                System.out.print("\033[34m" + (y + 1) + " \033[0m|");
            } else {
                System.out.print("\033[34m" + (y + 1) + "\033[0m|");
            }

            // Draw cells for Player 1
            for (int x = 0; x < gameField.width; ++x) {
                String backgroundColor = "";
                if (player2.getenemyFill().getCells()[x][y] == '0') {
                    backgroundColor = "\033[42m"; // Green background for '0'
                } else if (player2.getenemyFill().getCells()[x][y] == 'X') {
                    backgroundColor = "\033[41m"; // Red for 'X'
                } else if (player2.getenemyFill().getCells()[x][y] == '*') {
                    backgroundColor = "\033[43m"; // Yellow for '*'
                } else if (player2.getenemyFill().getCells()[x][y] == 'M') {
                    backgroundColor = "\033[45m"; // Magenta for 'M'
                } else {
                    backgroundColor = "\033[44m"; // Default blue for others
                }

                System.out.print(backgroundColor + "   \033[0m|");
            }

            System.out.print("\t\t\t\t\t\t\t");

            for (int x = 0; x < gameField.width; ++x) {
                String backgroundColor = "";
                if (player1.getenemyFill().getCells()[x][y] == '0') {
                    backgroundColor = "\033[42m";
                } else if (player1.getenemyFill().getCells()[x][y] == 'X') {
                    backgroundColor = "\033[41m";
                } else if (player1.getenemyFill().getCells()[x][y] == '*') {
                    backgroundColor = "\033[43m";
                } else if (player1.getenemyFill().getCells()[x][y] == 'M') {
                    backgroundColor = "\033[45m";
                } else {
                    backgroundColor = "\033[44m";
                }

                System.out.print(backgroundColor + "   \033[0m|");
            }

            System.out.println();
            System.out.print("  +");
            for (int x = 0; x < gameField.width; x++) {
                System.out.print("---+");
            }
            System.out.print("\t\t\t\t\t\t\t");
            for (int x = 0; x < gameField.width; x++) {
                System.out.print("---+");
            }
            System.out.println();
        }
    }

    public void process(StateGame stateGame, Player player, Player player2, int coord_x, int coord_y, boolean from_front, String[] informatian_from_front) {
        String input;
        String[] info;
        input_class = new Input(player);
        boolean done = false;
        listner = player;
        if (listner != null) {
            if (done) {
                listner.quit();
                return;
            }

            if (stateGame == StateGame.FirstPlayerPlacesShips || stateGame == StateGame.SecondPlayerPlacesShips) {
                input_class.initialized_process(informatian_from_front);
            }
//            else {
//                input_class.do_process(player, player2, info);
//            }

        }

    }

    public void write_game_over() {
        System.out.println("game over");
    }

    public static void lost_step() {
        System.out.println("Your coordinate was wrong.");
    }

    public void write_count_of_ship(Player currentPlayer) {
        System.out.println(currentPlayer.getName() + " init ship");
        System.out.println("You have: " + currentPlayer.getCount_four_deck() + " four deck ships, "
                + currentPlayer.getCount_three_deck() + " three deck "
                + currentPlayer.getCount_two_deck() + " two decks "
                + currentPlayer.getCount_one_deck() + " one deck");
    }

    public void printWinner(Player player1, Player player2) {
        if (player1.getCount_of_palubs() == 0) {
            System.out.println("Congratulations " + player2.getName() + " - You WON");
            player1.addCoins(100);
        } else {
            System.out.println("Congratulations " + player1.getName() + " - You WON");
            player2.addCoins(100);
        }
    }

    public void write_init_rule() {
        System.out.println("To initialize ships, enter the coordinates, ship type and direction in the format (x,y,type,direction),\n without spaces. If you want to place a mine, just specify x and y. At the top it says what ships you have left ");
    }

    public void write_shot_rule() {
        System.out.println("You need to write the x and y coordinates for the shot. If you hit, the opponent's field will turn red, \nif not, it will turn yellow; if you hit a mine, it will be counted as a shot into your field");
    }

    public void who_shot(Player player) {
        System.out.println(player.getName() + " is shooting now");
    }

}
