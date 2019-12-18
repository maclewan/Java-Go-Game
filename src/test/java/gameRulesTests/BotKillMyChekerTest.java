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

        test.addChecker(1, 1, true);
        test.addChecker(0, 1, false);
        test.addChecker(1, 0, false);
        test.addChecker(1, 2, false);
        test.botMove();
        assert(test.getChecker(2,1)==0 && test.getChecker(1,1)==2 );

    }
}
