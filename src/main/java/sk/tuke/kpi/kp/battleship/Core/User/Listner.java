package sk.tuke.kpi.kp.battleship.Core.User;

import sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap.Direction;

public interface Listner {
    void init_mines(int row, int collumn);
    void make_ship(int row, int collumn, int  health, Direction direction);
    void shot(int x, int y,Player player,Player player2);
    void quit();
}
