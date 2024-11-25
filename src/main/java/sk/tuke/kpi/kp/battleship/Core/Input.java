package sk.tuke.kpi.kp.battleship.Core;

import sk.tuke.kpi.kp.battleship.ConsoleUi.Game;
import sk.tuke.kpi.kp.battleship.Core.Field;
import sk.tuke.kpi.kp.battleship.Core.User.Coordination;
import sk.tuke.kpi.kp.battleship.Core.User.Listner;
import sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap.Direction;
import sk.tuke.kpi.kp.battleship.Core.User.StateGame;
import sk.tuke.kpi.kp.battleship.Core.User.Player;

import java.util.Scanner;

public class Input {
    Scanner scanner;
    Listner listner;

    public Input(Listner listner) {
        this.listner = listner;
    }

    public void initialized_process(String[] info) {
        System.out.println(info[0]);
        System.out.println(info[1]);
        System.out.println(info[2]);
        System.out.println(info[3]);
        Coordination coordination = parce_coords(info);
        if (coordination == null) {
            return;
        }
        int posX = coordination.x;
        int posY = coordination.y;
        if (info.length >= 4) {
            System.out.println("lenght in norna");
            try {
                int health = Integer.parseInt(info[2]);
                System.out.println("heath in norna");
                Direction direction = Direction.None;
                if (info[3].toLowerCase().contains("H") || info[3].toLowerCase().contains("h")) {
                    direction = Direction.Horizontal;
                    if (posX + health - 1 > 10) {
                        return;
                    }
                } else if (info[3].toLowerCase().contains("V") || info[3].toLowerCase().contains("v")) {
                    if (posY + health - 1 > 10) {
                        return;
                    }
                    direction = Direction.Vertical;
                }
                System.out.println("direction in norna");
                if (direction != Direction.None) {
                    System.out.println("we are making ship");
                    listner.make_ship(posX, posY, health, direction);
                }else {
                    System.out.println("we are not make");
                }

            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.println("Invalid input format. Please check your input.");
                e.printStackTrace();
            }
        } else {
            listner.init_mines(posX, posY);
        }
    }

    public void do_process(Player player, Player player2, String[] info) {
        Coordination coordination = parce_coords(info);
        if (coordination == null || coordination.x > 10 || coordination.x < 1 || coordination.y > 10 || coordination.y < 1) {
            Game.lost_step();
            return;
        }
        listner.shot(coordination.x, coordination.y, player, player2);
    }

    private Coordination parce_coords(String[] info) {
        try {
            int posX = Integer.parseInt(info[0]);
            int posY = Integer.parseInt(info[1]);
            return new Coordination(posX, posY);
        } catch (NumberFormatException e) {
            return null;
        }
    }


}
