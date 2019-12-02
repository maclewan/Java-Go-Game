package clients;

import java.io.IOException;
import java.net.InetAddress;


public class serverMenagment {

    private static InetAddress host;
    int a;
    int b;

    public serverMenagment() throws IOException {

    }

    /*public static int getInfo() throws IOException, ClassNotFoundException {
        /*Lacze z serwerrem*/
        /*skonfiguruj polaczenie socket do servera*/
     /*   Socket socket = new Socket(host.getHostName(), 6666);

        /*odbierz odpowiedz serwera*/
     /*   ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        int a1 = (int) ois.readObject();
        int b1 = (int) ois.readObject();
        if (a1 != 20 && b1 != 20) {
            /*cleanAllreadyChecked();
            if ((checkers[a1][b1] != null) && ((!isBlack && checkers[a1][b1].getFill().equals(Color.BLACK)) || (isBlack && checkers[a1][b1].getFill().equals(Color.WHITE) ))) {
                removeChecker(a1,b1);
            }
            else {
                isBlack = !isBlack;
                addChecker(a1, b1);
                isBlack = !isBlack;
            }
        }*/
        /*    socket.close();
            ois.close();
        }

    /*public static void sendInfo(int a, int b) throws IOException, ClassNotFoundException {

        /*Lacze z serwerrem*/
        /*skonfiguruj polaczenie socket do servera*/
        /*Socket  socket = new Socket(host.getHostName(), 6666);

        /*napisz do socket uzywajac ObjectOutputStream*/
        /*ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(a);
        oos.writeObject(b);

        socket.close();
        oos.close();
    }*/


    //}
}