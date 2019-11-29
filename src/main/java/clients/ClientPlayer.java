package clients;

import Controllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * Ta klasa implementuje socket Client
 *
 */
public class ClientPlayer {
    public void openMyBoard() throws IOException, ClassNotFoundException, InterruptedException {
        boolean tmp=true;
            /*Wyswietlam okno*/
            FXMLLoader loaderG = new FXMLLoader(getClass().getClassLoader().getResource("GUI.fxml"));
            GuiController gc = new GuiController();
            loaderG.setController(gc);

            Scene sceneG = new Scene(loaderG.load());
            Stage stageG = new Stage();
            stageG.setTitle("Go");
            stageG.setScene(sceneG);
            stageG.show();
            tmp=!tmp;
            System.out.println("Wyswietlilem okno");
    }

    public void handleServer() throws IOException, InterruptedException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        String myMessage;
        String message;
        /*tutaj rozpoczynam dzialanie serwera*/
        while(true){
            /*skonfiguruj polaczenie socket do servera*/
            socket = new Socket(host.getHostName(), 6666);

            /*napisz do socket uzywajac ObjectOutputStream*/
            myMessage  = scan.nextLine();
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(myMessage);

            /*odbierz odpowiedz serwera*/
            ois = new ObjectInputStream(socket.getInputStream());
            message= (String) ois.readObject();
            System.out.println("Message: " + message);



            /*pozamykaj wszystko*/
            ois.close();
            oos.close();
            Thread.sleep(100);
        }

    }




    String myMessage;
    public void main() throws IOException, ClassNotFoundException, InterruptedException {
        /*Wyswietlam okno*/
        openMyBoard();
        handleServer();


/*koncze wyswietlac okno*/


    }
}
