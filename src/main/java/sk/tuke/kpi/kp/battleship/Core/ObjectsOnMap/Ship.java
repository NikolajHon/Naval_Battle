package sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap;

import sk.tuke.kpi.kp.battleship.Core.User.Coordination;
import sk.tuke.kpi.kp.battleship.Core.Field;
import sk.tuke.kpi.kp.battleship.Core.User.Player;

public class Ship {
    private Coordination step;
    private Deck[] decks;
    private Direction direction;
    private DeckType deckType;
    Coordination[] coord;

    public Deck[] getDecks() {
        return decks;
    }

    public void setDecks(Deck[] decks) {
        this.decks = decks;
    }

    public Ship(Field field, DeckType deckType, Direction direction, Coordination[] coord) {
        this.direction = direction;
        this.deckType = deckType;
        this.coord = coord;
        decks = new Deck[deckType.getValue()];
        for (int i = 0; i < decks.length; i++) {
            Deck deck = new Deck(coord[i]);
            decks[i] = deck;
            System.out.println("add to fiels");
            System.out.println(field);
            field.add_object(deck);
        }
    }

    public boolean is_valid() {
        return deckType != DeckType.Invalid &&
                direction != Direction.None &&
                coord != null;
    }

    public boolean is_alive() {
        for (Deck deck : decks) {
            if (deck.is_alive()) {
                return true;
            }
        }
        return false;
    }

    public static Ship create(Field field, Direction direction, DeckType deckType, Coordination start_position, Player player) {


        Coordination step = null;
        if (direction == Direction.Horizontal) {
            step = new Coordination(1, 0);
        } else {
            step = new Coordination(0, 1);
        }

        boolean is_can_place = true;

        Coordination[] coords = new Coordination[deckType.getValue()];
        Coordination position = new Coordination(start_position.x, start_position.y);

        for (int i = 0; i < deckType.getValue(); ++i) {
            System.out.println(start_position.x);
            System.out.println(start_position.y);
            System.out.println(direction);
            System.out.println(deckType);
            boolean has_neighbour = field.hasNeighbour(start_position.x, start_position.y, direction, deckType);
            // boolean is_in_map = field.is_in_map(position);

            is_can_place = !has_neighbour;
            if (!is_can_place) {
                System.out.println("Wrong input");
                return null;
            }
            coords[i] = new Coordination(position.x, position.y);
            position.x += step.x;
            position.y += step.y;
            System.out.println("deck was init");
        }

        switch (deckType) {
            case One:
                if (player.getCount_one_deck() != 0) {
                    player.setCount_one_deck(player.getCount_one_deck() - 1);
                    player.setCount_of_ship(player.getCount_of_ship() - 1);
                } else {
                    System.out.println("You cant use this one deck ship");
                    return null;
                }
                break;
            case Two:
                if (player.getCount_two_deck() != 0) {
                    player.setCount_two_deck(player.getCount_two_deck() - 1);
                    player.setCount_of_ship(player.getCount_of_ship() - 1);
                } else {
                    System.out.println("You cant use this two deck ship");
                    return null;
                }
                break;
            case Three:
                if (player.getCount_three_deck() != 0) {
                    player.setCount_three_deck(player.getCount_three_deck() - 1);
                    player.setCount_of_ship(player.getCount_of_ship() - 1);
                } else {
                    System.out.println("You cant use this three deck ship");
                    return null;
                }
                break;
            case Four:
                if (player.getCount_four_deck() != 0) {
                    player.setCount_four_deck(player.getCount_four_deck() - 1);
                    player.setCount_of_ship(player.getCount_of_ship() - 1);
                } else {
                    System.out.println("You cant use this four deck ship");
                    return null;
                }
                break;
        }
        System.out.println("we are here");
        System.out.println(field);
        return new Ship(field, deckType, direction, coords);
    }
}
