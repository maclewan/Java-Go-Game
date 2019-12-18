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

        Observer ob2 = new Observer(null);
        ob2.setTest();
        ob2.start();
        ob2.run();

        assert(!ob1.isThereServer() && !ob2.isThereServer());

    }
    public class player extends Thread {


        @Override
        public synchronized void run() {
        }

    }


}
