package sk.tuke.kpi.kp.battleship.Core;

import sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap.Direction;
import sk.tuke.kpi.kp.battleship.Core.User.Player;

public interface Listner {
    void init_mines(int row, int collumn);
    void make_ship(int row, int collumn, int  health, Direction direction);
    void shot(int x, int y,Player player,Player player2);
    void quit();
}
