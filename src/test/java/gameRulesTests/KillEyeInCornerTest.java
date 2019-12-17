/**
 *
 * Testy dot. zasad gry
 *
 * */
package gameRulesTests;

import Server.GameLogic.Board;
import org.junit.Test;

/**
 *
 * Test na zabicie oka w lewym gornym rogu
 *
 * */
public class KillEyeInCornerTest{

    //Server s = new Server();
    boolean isFirst=false;

    @Test
    public void test() {
        Board test= new Board(null);
        test.startArrayOfCheckers();
        test.addChecker(0, 1, false);
        test.addChecker(1, 0, false);
        test.addChecker(1, 1, false);
        test.addChecker(2, 0, true);
        test.addChecker(2, 1, true);
        test.addChecker(1, 2, true);
        test.addChecker(0, 2, true);
        test.addChecker(0, 0, true);

        assert(test.getChecker(0,0)==1) && (test.getChecker(1,1)==2) && (test.getChecker(1,0)==2) && (test.getChecker(0,1)==2);

    }
    /*public class server extends Thread {
        @Override
        public synchronized void run() {

            String[] args=null;
            s.main(args);
        }
    }*/


}
