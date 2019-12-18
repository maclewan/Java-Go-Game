package gameServerTests;

import Server.ChatServer;
import client.Main;
import javafx.stage.Stage;
import org.junit.Test;

/**
 *
 * Test na polaczenie sie 1 gracza do servera chatu
 *
 * */
public class GameObserverTest {

    @Test
    public void test() throws Exception {
        ChatServer chatServer = new ChatServer(null);
        Main main = new Main();
        main.start(null);
        chatServer.start();
        Stage primaryStage;

        assert(!chatServer.isGameEnded());

    }


}
