//package sk.tuke.kpi.kp.battleship.User;
//
//import org.junit.Test;
//import sk.tuke.kpi.kp.battleship.Core.Field;
//import sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap.Deck;
//import sk.tuke.kpi.kp.battleship.Core.ObjectsOnMap.Empty;
//import sk.tuke.kpi.kp.battleship.Core.User.Base;
//import sk.tuke.kpi.kp.battleship.Core.User.Coordination;
//import sk.tuke.kpi.kp.battleship.Core.User.Player;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//
//public class BaseTest {
//
//    @Test
//    public void testAddCoins() {
//
//        Base player1 = new Base();
//        int initialCoins = player1.getCoins();
//
//        int amountToAdd = 50;
//        player1.addCoins(amountToAdd);
//
//        assertEquals(amountToAdd + initialCoins, player1.getCoins());
//
//    }
//
//    @Test
//    public void testSubtractCoinsEnoughBalance() {
//        Base player1 = new Base();
//        int initialCoins = player1.getCoins();
//        int amountToSubtract = 30;
//
//        player1.subtractCoins(amountToSubtract);
//        assertEquals(initialCoins - amountToSubtract, player1.getCoins());
//    }
//
//    @Test
//    public void testSubtractCoinsNotEnoughBalance() {
//        Base player1 = new Base();
//        int initialCoins = player1.getCoins();
//        int amountToSubtract = 120;
//
//        player1.subtractCoins(amountToSubtract);
//        assertEquals(initialCoins, player1.getCoins());
//    }
//
//    @Test
//    public void testShotBrokeDeck() {
//        Base player1 = new Player();
//        Base player2 = new Player();
//        player1.setField(new Field());
//        player2.setField(new Field());
//        player1.setEnemy_fill(new Field());
//        player2.setEnemy_fill(new Field());
//        int initialPalubs = player2.getCount_of_palubs();
//        player2.getField().setElement(1,1,new Deck(new Coordination(1,1)));
//        player1.shot(1, 1, (Player) player1, (Player) player2);
//
//        assertEquals(initialPalubs - 1, player2.getCount_of_palubs());
//    }
//
//    @Test
//    public void testShotMarkEmptyFill() {
//        Base player1 = new Player();
//        Base player2 = new Player();
//        player1.setField(new Field());
//        player2.setField(new Field());
//        player1.setEnemy_fill(new Field());
//        player2.setEnemy_fill(new Field());
//        player2.getField().setElement(1, 1, new Empty(new Coordination(1, 1)));
//        player1.shot(1, 1, (Player) player1, (Player) player2);
//
//        assertTrue(player1.getenemyFill().getElement(1, 1) instanceof Empty);
//    }
//}
