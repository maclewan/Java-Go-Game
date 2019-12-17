
package gameRulesTests;

import Server.GameLogic.Board;
import org.junit.Test;

/**
 *
 * Test na zabicie 1 pionka
 *
 * */
public class KillOneCheckerTest {

    @Test
    public void test() {
        Board test= new Board(null);

        test.addChecker(2, 2, false);
        test.addChecker(3, 3, false);
        test.addChecker(1, 3, false);
        test.addChecker(2, 4, false);

        test.addChecker(2, 3, true);

        assert(test.getChecker(2,3)==1);
    }

}
