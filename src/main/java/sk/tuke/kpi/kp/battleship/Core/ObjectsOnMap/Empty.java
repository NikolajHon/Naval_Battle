package sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap;

import sk.tuke.kpi.kp.battleship.Core.User.Coordination;

public class Empty implements MapsObjects{
    private Coordination position;

    public Empty(Coordination position) {
        this.position = position;
    }

    @Override
    public Coordination getPos() {
        return this.position;
    }

    @Override
    public char getView() {
        return '*';
    }
}
