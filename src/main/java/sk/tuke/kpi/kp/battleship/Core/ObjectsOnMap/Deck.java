package sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap;

import sk.tuke.kpi.kp.battleship.Core.User.Coordination;

public class Deck implements MapsObjects{
    private Coordination position;
    private char alive_view = '0';
    private char broken_view = 'X';
    private boolean is_broken;

    public Deck(Coordination position) {
        this.position = position;
    }
    public boolean is_alive(){
        return !is_broken;
    }

    @Override
    public Coordination getPos() {
        return position;
    }

    @Override
    public char getView() {
        return is_broken ? broken_view : alive_view;
    }
    public void broke_paluba(){is_broken = !is_broken;}
}
