package sk.tuke.kpi.kp.battleship.Core;

import sk.tuke.kpi.kp.Service.ScorePackage.ScoreService;
import sk.tuke.kpi.kp.battleship.Core.User.Coordination;
import sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap.DeckType;
import sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap.Direction;
import sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap.MapsObjects;

import java.util.logging.Logger;

public class Field {
    
    private ScoreService scoreService;
    private static Logger log = Logger.getLogger(Field.class.getName());
    public final int width = 10;
    public final int height = 10;
    private char[][] cells;
    private MapsObjects[][] mapsObjects;
    private static Field gameField;

    public MapsObjects getElement(int row, int col) {
        return mapsObjects[row - 1][col - 1];
    }

    // Метод для установки нового значения по индексу
    public void setElement(int row, int col, MapsObjects newValue) {

        mapsObjects[row - 1][col - 1] = newValue;

    }

    public static void setGameField(Field field) {
        gameField = field;
    }

    public static Field getGameField() {
        return gameField;
    }

    public MapsObjects[][] getMapsObjects() {
        return mapsObjects;
    }

    public void setMapsObjects(MapsObjects[][] mapsObjects) {
        this.mapsObjects = mapsObjects;
    }

    public Field() {
        cells = new char[width][height];
        mapsObjects = new MapsObjects[width][height];
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                cells[x][y] = ' ';
            }
        }
    }

    public char[][] getCells() {
        return cells;
    }

    public void setCells(char[][] cells) {
        this.cells = cells;
    }

    public void add_object(MapsObjects objects) {
        Coordination position = objects.getPos();
        mapsObjects[position.x - 1][position.y - 1] = objects;
        updete();
    }


    public boolean hasNeighbour(int x, int y, Direction direction, DeckType deckType) {
        System.out.println(x);
        System.out.println(y);
        System.out.println(direction);
        System.out.println(deckType);
        int length = 0;
        if (x > 10 || x < 1 || y > 10 || x < 1) {
            return true;
        }
        if (deckType == DeckType.One) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int currentX = x + i;
                    int currentY = y + j;

                    if (currentX > 0 && currentX <= 10 && currentY > 0 && currentY <= 10) {
                        if (this.cells[currentX - 1][currentY - 1] != ' ') {
                            return true;
                        }
                    }
                }
            }
        }
        switch (deckType) {
            case One:
                length = 1;
                break;
            case Two:
                length = 2;
                break;
            case Three:
                length = 3;
                break;
            case Four:
                length = 4;
                break;
        }

        if (direction == Direction.Horizontal) {
            if (x - 1 > 0) {
                if (y - 1 > 0) {
                    if (this.cells[x - 2][y - 2] != ' ') {
                        return true;
                    }
                }
                if (y < 10) {
                    if (this.cells[x - 2][y] != ' ') {
                        return true;
                    }
                }
                if (this.cells[x - 2][y - 1] != ' ') {
                    return true;
                }
            }
            if (x + length < 10) {
                if (y - 1 > 0) {
                    if (this.cells[x + length][y - 2] != ' ') {
                        return true;
                    }
                }
                if (y < 10) {
                    if (this.cells[x + length][y] != ' ') {
                        return true;
                    }
                }
                if (this.cells[x + length][y - 1] != ' ') {
                    return true;
                }
            }
        } else { // Vertical orientation
            if (y - 1 > 0) {
                if (x - 1 > 0) {
                    if (this.cells[x - 2][y - 2] != ' ') {
                        return true;
                    }
                }
                if (x < 10) {
                    if (this.cells[x][y - 2] != ' ') {
                        return true;
                    }
                }
                if (this.cells[x - 1][y - 2] != ' ') {
                    return true;
                }
            }
            if (y + length < 10) {
                if (x - 1 > 0) {
                    if (this.cells[x - 2][y + length] != ' ') {
                        return true;
                    }
                }
                if (x < 10) {
                    if (this.cells[x][y + length] != ' ') {
                        return true;
                    }
                }
                if (this.cells[x - 1][y + length] != ' ') {
                    return true;
                }
            }
        }


        if ((direction == Direction.Horizontal.Horizontal && x + length < 10) || (direction == Direction.Vertical && y + length < 10)) {
            length++;
        }
        for (int i = 0; i < length; i++) {
            int currentX = x;
            int currentY = y;
            //System.out.println("x = " + currentX + " y = " + currentY + " lenght " + length + " " + smer);
            switch (direction) {
                case Horizontal:
                    currentX += i;
                    if (currentX <= 10) {
                        if (currentY - 1 > 0) {
                            if (this.cells[currentX - 1][currentY - 2] != ' ') {
                                return true;
                            }
                        }
                        if (currentY < 10) {
                            if (this.cells[currentX - 1][currentY] != ' ') {
                                return true;
                            }
                        }
                        if (this.cells[currentX - 1][currentY - 1] != ' ') {
                            return true;
                        }
                    } else {
                        return true;
                    }
                    break;

                case Vertical:
                    currentY += i;
                    if (currentY <= 10) {
                        if (currentX - 1 > 0) {
                            if (this.cells[currentX - 2][currentY - 1] != ' ') {
                                return true;
                            }
                        }
                        if (currentX < 10) {
                            if (this.cells[currentX][currentY - 1] != ' ') {
                                return true;
                            }
                        }
                        if (this.cells[currentX - 1][currentY - 1] != ' ') {
                            return true;
                        }
                    } else {
                        return true;
                    }
                    break;
            }
        }


        return false;
    }





    public void updete() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                MapsObjects curent_obj = mapsObjects[x][y];
                if (curent_obj != null) {
                    Coordination position = curent_obj.getPos();
                    cells[position.x - 1][position.y - 1] = curent_obj.getView();
                }
            }
        }
    }


}
