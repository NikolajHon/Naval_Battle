package sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap;

import sk.tuke.kpi.kp.battleship.Core.Field;
import sk.tuke.kpi.kp.battleship.Core.User.Coordination;
import sk.tuke.kpi.kp.battleship.Core.User.Player;

public class Mines implements MapsObjects {
    private static final int price = 10;
    private Coordination coordination;

    public Mines(Coordination coordination, Field field) {
        this.coordination = coordination;
        field.add_object(this);
    }

    @Override
    public Coordination getPos() {
        return this.coordination;
    }

    @Override
    public char getView() {
        return 'M';
    }

    public int getPrice() {
        return price;
    }

    public static Mines create_mines(int row, int column, Player player) {
        Mines mines = null;
        if ((player.getCoins() >= price) && !(player.getField().getElement(row, column) instanceof Deck)) {
            player.subtractCoins(price);
            mines = new Mines(new Coordination(row, column), player.getField());
        }
        else {
            System.out.println("You do not have enough money or you cant place mine here");
        }
        return mines;
    }
}
