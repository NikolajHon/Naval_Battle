package sk.tuke.kpi.kp.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Date;
@Entity
//@Getter
//@Setter
public class InfoAboutGame implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ident;
    private String game;
    private String name_first_player;
    private String name_second_player;
    private String name_of_winner;
    private int rating_of_first_player;
    private int rating_of_second_player;
    private Date ratedOn;

    public InfoAboutGame(String game, String name_first_player, String name_second_player, String name_of_winner, int rating_of_first_player, int rating_of_second_player, Date ratedOn) {
        this.game = game;
        this.name_first_player = name_first_player;
        this.name_second_player = name_second_player;
        this.name_of_winner = name_of_winner;
        this.rating_of_first_player = rating_of_first_player;
        this.rating_of_second_player = rating_of_second_player;
        this.ratedOn = ratedOn;
    }

    public InfoAboutGame() {
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    public String getName_first_player() {
        return name_first_player;
    }

    public void setName_first_player(String name_first_player) {
        this.name_first_player = name_first_player;
    }

    public String getName_second_player() {
        return name_second_player;
    }

    public void setName_second_player(String name_second_player) {
        this.name_second_player = name_second_player;
    }

    public String getName_of_winner() {
        return name_of_winner;
    }

    public void setName_of_winner(String name_of_winner) {
        this.name_of_winner = name_of_winner;
    }

    public int getRating_of_first_player() {
        return rating_of_first_player;
    }

    public void setRating_of_first_player(int rating_of_first_player) {
        this.rating_of_first_player = rating_of_first_player;
    }

    public int getRating_of_second_player() {
        return rating_of_second_player;
    }

    public void setRating_of_second_player(int rating_of_second_player) {
        this.rating_of_second_player = rating_of_second_player;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Date ratedOn) {
        this.ratedOn = ratedOn;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "Match ID: " + ident +
                "\nFirst Player: " + name_first_player +
                "\nSecond Player: " + name_second_player +
                "\nWinner: " + name_of_winner +
                "\nRating of First Player: " + rating_of_first_player +
                "\nRating of Second Player: " + rating_of_second_player +
                "\nRated On: " + ratedOn + "\n";
    }
}
