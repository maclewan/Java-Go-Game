package gameServerTests;

import Observers.Observer;
import org.junit.Test;

/**
 *
 * Test na polaczenie sie 1 gracza do servera chatu
 *
 * */
public class ObserverTest {
    Observer ob1;
    Observer ob2;
    @Test
    public void test() throws Exception {
        Observer ob1 = new Observer(null);
        ob1.setTest();
        ob1.start();
        ob1.run();
        //ob1.runGame();
        assert(!ob1.isThereServer());

    }
    public class player extends Thread {


        @Override
        public synchronized void run() {
        }

    }


}
