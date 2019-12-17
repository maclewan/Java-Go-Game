package gameRulesTests;

import Server.GameLogic.Board;
import org.junit.Test;

/**
 *
 * Test na zabicie wielu pionkow
 *
 * */
public class KillMoreControllerTest {

    @Test
    public void test() {
        Board test= new Board(null);


        //*dodawanie bialych*//
        test.addChecker(2, 2, false);
        test.addChecker(3, 2, false);
        test.addChecker(4, 2, false);
        test.addChecker(5, 2, false);
        test.addChecker(6, 2, false);
        test.addChecker(7, 2, false);
        test.addChecker(7, 3, false);
        test.addChecker(7, 4, false);
        test.addChecker(7, 5, false);
        test.addChecker(6, 5, false);
        test.addChecker(5, 5, false);
        test.addChecker(5, 4, false);
        test.addChecker(4, 4, false);
        test.addChecker(3, 4, false);
        test.addChecker(2, 4, false);
        test.addChecker(2, 3, false);

        //*dodawanie czarnych*//
        test.addChecker(6, 4, true);
        test.addChecker(6, 3, true);
        test.addChecker(5, 3, true);
        test.addChecker(3, 3, true);
        test.addChecker(4, 3, true);

        assert(test.getChecker(3,3)==1);


    }
}
