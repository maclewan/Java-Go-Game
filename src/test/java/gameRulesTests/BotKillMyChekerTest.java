package gameRulesTests;

import Server.GameLogic.Board;
import org.junit.Test;

import java.net.UnknownHostException;

/**
 *
 * Test na wykonanie ruchu zabojstwa przez Bota
 *
 * */
public class BotKillMyChekerTest {

    @Test
    public void test() throws UnknownHostException {
        Board test= new Board(null);
        test.startArrayOfCheckers();

       /* test.addChecker(1, 1, true);
        test.addChecker(0, 1, false);
        test.addChecker(1, 0, false);
        test.addChecker(1, 2, false);*/

        test.addChecker(2, 0, false);
        test.addChecker(2, 1, false);
        test.addChecker(1, 2, false);
        test.addChecker(0, 2, false);
        test.addChecker(0, 0, true);
        test.addChecker(0, 1, true);
        test.addChecker(1, 1, true);
        test.botMove();
        assert(test.getChecker(0,0)==2);

    }
}
