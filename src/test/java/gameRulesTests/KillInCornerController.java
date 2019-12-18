package gameRulesTests;

import Server.GameLogic.Board;
import org.junit.Test;

/**
 *
 * Test na zabicie 1 pionka w rogu(lewym gornym)
 *
 * */
public class KillInCornerController{

    @Test
    public void test() {
        Board test= new Board(null);

        test.addChecker(0, 1, false);
        test.addChecker(1, 0, false);
        test.addChecker(1, 0, true);
       assert(test.getChecker(0,0)==1);

    }

}
