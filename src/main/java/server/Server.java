        package server;

        import java.io.IOException;
        import java.io.ObjectInputStream;
        import java.io.ObjectOutputStream;
        import java.net.ServerSocket;
        import java.net.Socket;

/**
 *
 * Ta klasa implementuje Socket server
 *
 */


        public class Server {
        private static ServerSocket server;
        /*port socket serer-a*/
        private static int port = 6666;



        public static void main() throws IOException, ClassNotFoundException{
        /*tworzenie socket serwer*/
        server = new ServerSocket(port);

        System.out.println("Stworzylem server");

        /*Tworzenie socket i czekanie na polaczenie z klientem*/
        /*Wysylam potwierdzenie do klienta1*/
        //Socket socket1 = server.accept();
        //tworz ObjectOutputStream object
        //ObjectOutputStream oos1 = new ObjectOutputStream(socket1.getOutputStream());
        //oos1.writeObject("Jestes graczem nr 1");

        //Socket socket1 = server.accept();
        /*Wysylam potwierdzenie do klienta2*/
        /*ObjectOutputStream oos1 = new ObjectOutputStream(socket2.getOutputStream());
        oos1.writeObject("Jestes graczem nr 1");
*/
  //       Socket socket2 = server.accept();
        /*Wysylam potwierdzenie do klienta2*/
        /*ObjectOutputStream oos2 = new ObjectOutputStream(socket2.getOutputStream());
        oos2.writeObject("Jestes graczem nr 2");
        */
        while(true){
                /**
                 *
                 * Lacze sie z kientem nr 1
                 *
                 * */

                /*Czekam na klienta 1*/
                System.out.println("Czekam na 1 gracza");
                Socket socket1 = server.accept();
                System.out.println("Gracz 1 dolaczyl do serwera");
                /*Teraz biore wiadomosc od klienta 1 */
                ObjectInputStream checker1 = new ObjectInputStream(socket1.getInputStream());

                /*Konwertuje na Stringa*/
                String message1 = (String) checker1.readObject();
                System.out.println("Dostalem widomosc od 1 gracza: " + message1);

                /*Sprawdzam czy to juz koniec naszej zabawy*/
                if(message1.equalsIgnoreCase("exit")) break;

                /**
                 *
                 * Lacze sie z kientem nr 2
                 *
                 * */
                /*Czekam na klienta 2*/
                System.out.println("Czekam na 2 gracza");
                Socket socket2 = server.accept();
                System.out.println("Gracz 2 dolaczyl do serwera");

                /*Daje klientowi 1 odpowiedz*/
                ObjectOutputStream oos1 = new ObjectOutputStream(socket1.getOutputStream());
                oos1.writeObject("Odpowiadam graczowi nr 1");

                /*Teraz biore wiadomosc od klienta 2*/
                ObjectInputStream checker2 = new ObjectInputStream(socket2.getInputStream());

                /*Konwertuje na Stringa*/
                String message2 = (String) checker2.readObject();
                System.out.println("Dostalem widomosc od 2 gracza: " + message2);

                /*Daje klientowi 2 odpowiedz*/
                ObjectOutputStream oos2 = new ObjectOutputStream(socket2.getOutputStream());
                oos2.writeObject("Odpowiadam graczowi nr 2");


                /*Konce obcowanie z klientem drugim*/
                /*zamykam wszystkie zrodla*/
                checker1.close();
                oos1.close();
                socket1.close();
                checker2.close();
                oos2.close();
                socket2.close();
                /*Sprawdzam czy to juz koniec naszej zabawy*/
                if(message2.equalsIgnoreCase("exit")) break;



        }
        System.out.println("Shutting down Socket server!!");
        //zamknij ServerSocket object
        server.close();
        }

        }


