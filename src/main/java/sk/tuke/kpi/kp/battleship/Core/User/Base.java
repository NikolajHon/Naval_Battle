package sk.tuke.kpi.kp.battleship.Core.User;

import sk.tuke.kpi.kp.battleship.Core.Field;
import sk.tuke.kpi.kp.battleship.ConsoleUi.Game;
import sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap.*;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Base {
    private String name;

    protected Game game;

    private int ID_player = 0;
    protected Field field;
    protected Field enemy_fill;
    private int count_one_deck = 4;
    private int count_two_deck = 3;
    private int count_three_deck = 2;
    private int count_four_deck = 1;
    private int count_of_ship = 10;
    private int count_of_palubs = 20;
    private boolean shoot_now;
    private int coins = 100;
    private int score = 0;


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int amount) {
        this.score += amount;
    }


    public int getCoins() {
        return coins;
    }


    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public void subtractCoins(int amount) {
        if (this.coins >= amount) {
            this.coins -= amount;
            return;
        }
        System.out.println("Недостаточно монет!");
    }

    public boolean isShoot_now() {
        return shoot_now;
    }

    public void setShoot_now(boolean shoot_now) {
        this.shoot_now = shoot_now;
    }

    public int getID_player() {
        return ID_player;
    }

    public void setID_player(int ID_player) {
        this.ID_player = ID_player;
    }

    public int getCount_of_palubs() {
        return count_of_palubs;
    }

    public void setCount_of_palubs(int count_of_palubs) {
        this.count_of_palubs = count_of_palubs;
    }

    public Field getenemyFill() {
        return enemy_fill;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setEnemy_fill(Field enemy_fill) {
        this.enemy_fill = enemy_fill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount_of_ship() {
        return count_of_ship;
    }

    public void setCount_of_ship(int count_of_ship) {
        this.count_of_ship = count_of_ship;
    }

    public int getCount_one_deck() {
        return count_one_deck;
    }

    public void setCount_one_deck(int count_one_deck) {
        this.count_one_deck = count_one_deck;
    }

    public int getCount_two_deck() {
        return count_two_deck;
    }

    public void setCount_two_deck(int count_two_deck) {
        this.count_two_deck = count_two_deck;
    }

    public int getCount_three_deck() {
        return count_three_deck;
    }

    public void setCount_three_deck(int count_three_deck) {
        this.count_three_deck = count_three_deck;
    }

    public int getCount_four_deck() {
        return count_four_deck;
    }

    public void setCount_four_deck(int count_four_deck) {
        this.count_four_deck = count_four_deck;
    }

    public Base(Game game) {
        this.game = game;
        field = new Field();
        enemy_fill = new Field();
        Field.setGameField(field);

    }

    public Base() {
        field = new Field();
        enemy_fill = new Field();
        Field.setGameField(field);
    }

    private static void playSound(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.start();

            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        event.getLine().close();
                    }
                }
            });

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void shot(int x, int y, Player player, Player player2) {
        if(player.getenemyFill().getElement(x,y) == null) {
            if (player2.field.getElement(x, y) instanceof Deck ) {
                broke_deck(player.enemy_fill, x, y);
                player2.setCount_of_palubs(player2.getCount_of_palubs() - 1);

                player.setScore(player.getScore() + 25);
            } else if (player2.field.getElement(x, y) instanceof Mines) {
                if (player2.field.getElement(x, y) instanceof Deck) {
                    broke_deck(player2.enemy_fill, x, y);
                    player.setCount_of_palubs(player.getCount_of_palubs() - 1);

                    player2.setScore(player2.getScore() + 25);
                } else {
                    mark_empty_fill(player.enemy_fill, x, y);
                }
                mark_mine(player.enemy_fill, x, y);
            } else {
                mark_empty_fill(player.enemy_fill, x, y);
                player.setShoot_now(false);
            }
        }
    }



    public void broke_deck(Field player_field, int row, int collumn){
        Deck deck = new Deck(new Coordination(row, collumn));
        deck.broke_paluba();
        player_field.setElement(row, collumn, deck);
        playSound("on_target.wav");
    }
    public void mark_empty_fill(Field player_field, int row, int collumn){
        MapsObjects title = new Empty(new Coordination(row, collumn));
        player_field.setElement(title.getPos().x, title.getPos().y, title);
        playSound("miss.wav");
    }
    public void mark_mine(Field player_field, int row, int collumn){
        new Mines(new Coordination(row, collumn), player_field);
        playSound("on_target.wav");
    }


    public void placeShipsRandomly() {
        placeShipsRandomly(count_four_deck, 4);
        placeShipsRandomly(count_three_deck, 3);
        placeShipsRandomly(count_two_deck, 2);
        placeShipsRandomly(count_one_deck, 1);
    }

    private boolean canPlaceShip(int x, int y, Direction direction, int palubasCount) {


        if(field.hasNeighbour(x,y,direction, DeckType.value_of(palubasCount))){
            return false;
        }

        return true;
    }

    private void placeShipsRandomly(int count, int palubasCount) {

        for (int i = 0; i < count; i++) {
            int x = 0;
            int y = 0;
            boolean placed = false;

            while (!placed) {
                x = (int) (Math.random() * field.width) + 1;
                y = (int) (Math.random() * field.height) + 1;

                Direction direction = Math.random() < 0.5 ? Direction.Horizontal : Direction.Vertical;


                if (canPlaceShip(x, y, direction, palubasCount)) {
                    placeShip(x, y, direction, palubasCount);
                    placed = true;
                }
            }
        }
    }


    private void placeShip(int x, int y, Direction direction, int deckType) {
        for (int i = 0; i < deckType; i++) {
            int currentX = x;
            int currentY = y;

            switch (direction) {
                case Horizontal:
                    currentX += i;
                    break;
                case Vertical:
                    currentY += i;
                    break;
            }

            // Assuming `field` is an instance of the Field class
            field.add_object(new Deck(new Coordination(currentX, currentY)));
            field.updete();
        }
    }




}
