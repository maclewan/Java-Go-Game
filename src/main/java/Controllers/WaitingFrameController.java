package Controllers;

import clients.Client;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class WaitingFrameController{
    Stage stage;
    //boolean isBlack=false;


    /*DO SOCKETA*/
    Scanner scan = new Scanner(System.in);
    //get the localhost IP address, if server is running on some other IP, you need to use that
    InetAddress host;

    {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    Socket socket = null;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    String myMessage;
    String message;




    @FXML
    void initialize() throws IOException, ClassNotFoundException {

        ClientController clientController = new ClientController();


        /*Tutaj sprawdzam czy to byl pierwszy*/
        System.out.println("Dolaczylem sb do servera");
        //******************************//
        //---- UNDER CONSTRUCKTION------//
        //******************************//

        /*Lacze z serwerrem*/
        /*skonfiguruj polaczenie socket do servera*/
        socket = new Socket(host.getHostName(), 6666);

        /*odbierz odpowiedz serwera*/
        ois = new ObjectInputStream(socket.getInputStream());
        clientController.isBlack= (boolean) ois.readObject();
        if(clientController.isBlack)        System.out.println("Jestem czarny");
        else  {
            System.out.println("Jestem bialy");
            clientController.yourTurn=false;
        }


        /*napisz do socket uzywajac ObjectOutputStream*/
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("OK");

        //////////// MACIEJU //////////////////
        /////////////////////////////////////
        //TU TRZE WYSWIETLIC OKNO LADOWANIA//



        /////////////////////////////////////
        ////////////MACIEJU//////////////////


        /*Odbierz wiadomosc nt 2 gracza*/
        /*odbierz odpowiedz serwera*/
        boolean isSecond = (boolean) ois.readObject();       //tu sie wiesza




        //otwieram gui


        socket.close();
        ois.close();
        oos.close();

    }
/*
    public void setCreateServer(boolean createServer) throws IOException, ClassNotFoundException {
        this.createServer = createServer;

    }
*/

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void startGame(){



        }

    }

