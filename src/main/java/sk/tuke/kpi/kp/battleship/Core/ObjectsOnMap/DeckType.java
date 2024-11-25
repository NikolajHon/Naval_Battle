package sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap;

public enum DeckType {
    Invalid(-1), One(1), Two(2), Three(3), Four(4);
    private final int value;
    private static DeckType[] map;
    static {
        map = new DeckType[5];
        int counter = 0;
        for(DeckType d : DeckType.values()){
            map[counter++] = d;
        }
    }
    DeckType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static DeckType value_of(int i){
        if(i >= 0 && i < 5) {
            return map[i];
        }
        return Invalid;
    }
}
