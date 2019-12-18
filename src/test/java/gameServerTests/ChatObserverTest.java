package gameServerTests;

import Observers.ChatObserver;
import Server.ChatServer;
import org.junit.Test;

import java.net.UnknownHostException;
/**
 *
 * Test na polaczenie sie 1 gracza do servera chatu
 *
 * */
public class ChatObserverTest { ;
    ChatObserver one= new ChatObserver();
    ChatObserver two= new ChatObserver();
    @Test
    public void test() throws UnknownHostException {
        ChatServer chatServer = new ChatServer(null);
        chatServer.start();
        two = new ChatObserver();
        two.start();
        one = new ChatObserver();
        one.start();
        one.setMesOut("hi");
        assert(!chatServer.isGameEnded());

    }


}
