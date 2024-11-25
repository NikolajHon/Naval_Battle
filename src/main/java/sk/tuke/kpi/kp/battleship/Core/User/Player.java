package sk.tuke.kpi.kp.battleship.Core.User;

import sk.tuke.kpi.kp.battleship.ConsoleUi.Game;
import sk.tuke.kpi.kp.battleship.Core.Input;
import sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap.Mines;
import sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap.Ship;
import sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap.DeckType;
import sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap.Direction;

public class Player extends Base implements Listner {
    private Input input;
    public Player(Game game) {
        super(game);
        input = new Input(this);
    }
    public Player(){
        super();
        input = new Input(this);
    }

    @Override
    public void init_mines(int row, int collumn) {
        Mines.create_mines(row, collumn, this);
    }

    @Override
    public void make_ship(int row, int collumn, int health, Direction direction) {
        System.out.println(field);
        Ship.create(field,direction, DeckType.value_of(health), new Coordination(row, collumn), this);
    }


    @Override
    public void shot(int x, int y, Player player, Player player2) {
        super.shot( x, y, player,player2);
    }
//    public void process(Player player, Player player2){
//        super.process(game.getSt(), player, player2);
//        Game.process(game.getSt(), player, player2);
//    }
    public void quit(){
        game.setgame_over();
    }

}
