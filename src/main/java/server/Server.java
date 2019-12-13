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

/**
 * MICHALE
 * todo:
 * Przeanalizuj ServerMenagment i pozwól na 2 połączenia na ruch- pozwol nam grać.
 *
 */


public class Server {
        private static ServerSocket server;
        /*port socket serwer-a*/
        private static int port = 6666;




        public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
                /**tworzenie socket serwer*/
                server = new ServerSocket(port);
                int a1=21;
                int b1=21;
                int a2=21;
                int b2=21;
                boolean first =true;
                System.out.println("Stworzylem server");
                /*Tutaj daje wiadomosc klientom ktory byl pierwszy*/

                //******************************//
                //---- UNDER CONSTRUCKTION------//
                //******************************//

                /*Czekam na klienta 1*/
                System.out.println("Czekam na 1 gracza");
                /**Czekam na klienta 1*/
                Socket socket1 = server.accept();
                System.out.println("Gracz 1 dolaczyl do serwera");
                /**Daje klientowi 1 odpowiedz*/
                ObjectOutputStream oos1 = new ObjectOutputStream(socket1.getOutputStream());
                oos1.writeObject(true);

                /**Teraz biore wiadomosc od klienta 1 */
                ObjectInputStream ois1 = new ObjectInputStream(socket1.getInputStream());


                /*Czekam na klienta 2*/
                System.out.println("Czekam na 2 gracza");
                /**Czekam na klienta 2*/
                Socket socket2 = server.accept();
                System.out.println("Gracz 2 dolaczyl do serwera");

                /**Konwertuje na inta*/
                String a = (String) ois1.readObject();
                System.out.println("Dostalem waidomosc od 1 gracza: " + a);

                /**Daje klientowi 2 odpowiedz*/
                ObjectOutputStream oos2 = new ObjectOutputStream(socket2.getOutputStream());
                oos2.writeObject(false);

                /**Teraz biore wiadomosc od klienta 2 */
                ObjectInputStream ois2 = new ObjectInputStream(socket2.getInputStream());

                /**Konwertuje na inta*/
                String b = (String) ois2.readObject();
                System.out.println("Dostalem widomosc od 2 gracza: " + b);



                /**informuje klienta 1 ze wszyscy sa*/
                oos2.writeObject(true);

                /**informuje klienta 2 ze wszyscy sa*/
                oos1.writeObject(true);


                //oos2.close();
                //oos1.close();
                //ois1.close();
                //ois2.close();

                //checkera.close();
               // checkerb.close();
               // oosP1.close();
                //socket1.close();
                //oosP2.close();
                //socket2.close();
                /**
                 *
                 * Lacze sie z kientem nr 1
                 *
                 * */

                /*Czekam na klienta 1*/
                //System.out.println("Czekam na 1 gracza");
                //Socket socket1 = server.accept();
                //System.out.println("Gracz 1 dolaczyl do serwera");

                /**
                 *
                 * Lacze sie z kientem nr 2
                 *
                 * */


                /*Czekam na klienta 2*/
                //System.out.println("Czekam na 2");
                //Socket socket2 = server.accept();
                //System.out.println("Gracz 2 dolaczyl ");

                while(true){


                        /*Teraz biore wiadomosc od klienta 1 */
                        //ObjectInputStream checker1 = new ObjectInputStream(socket1.getInputStream());

                        /**BIore wiadomosc od klij. 1 i konwertuje na inta*/
                        a1 = (int) ois1.readObject();
                        System.out.println("Dostalem widomosc od 1 gracza: " + a1);
                        /**BIore wiadomosc od klij. 1 i konwertuje na inta*/
                        b1 = (int) ois1.readObject();
                        System.out.println("Dostalem widomosc od 1 gracza: " + b1);

                        /**Sprawdzam czy to juz koniec naszej zabawy*/
                        if(a1==20 && a2==20 && b1==20 && b2==20)
                        {                        /*Teraz biore wiadomosc od klienta 2*/
                                //ObjectInputStream checker2 = new ObjectInputStream(socket2.getInputStream());

                                /**Konwertuje na inta*/
                                a2 = (int) ois2.readObject();
                                System.out.println("Dostalem widomosc od 2 gracza: " + a2);
                                b2 = (int) ois2.readObject();
                                System.out.println("Dostalem widomosc od 2 gracza: " + b2);

                                System.out.println("daje odpow 1 graczowi");
                                /**Daje klientowi 1 odpowiedz*/
                                // ObjectOutputStream oos1a = new ObjectOutputStream(socket1.getOutputStream());
                                oos1.writeObject(a2);
                                oos1.writeObject(b2);
                                break;
                        }

                        /**Teraz biore wiadomosc od klienta 2 i konwertuje na inta*/
                        a2 = (int) ois2.readObject();
                        System.out.println("Dostalem widomosc od 2 gracza: " + a2);
                        /**Teraz biore wiadomosc od klienta 2 i konwertuje na inta*/
                        b2 = (int) ois2.readObject();
                        System.out.println("Dostalem widomosc od 2 gracza: " + b2);

                        System.out.println("daje odpow 1 graczowi");
                        /**Daje klientowi 1 odpowiedz*/
                        oos1.writeObject(a2);
                        oos1.writeObject(b2);

                        System.out.println("daje odpow 2 graczowi");
                        /**Daje klientowi 2 odpowiedz*/
                        oos2.writeObject(a1);
                        oos2.writeObject(b1);

                        /**Sprawdzam czy to juz koniec naszej zabawy*/
                        if(a1==20 && a2==20 && b1==20 && b2==20)
                        {
                                break;
                        }
                }


                /******************************************/
                /*Otwieram czat do rozmowy miedzy graczami*/
                /******************************************/

                /*Czekam na klienta 1*/
                /*System.out.println("Czekam na 1 gracza");
                Socket sockettP1 = server.accept();
                System.out.println("Gracz 1 dolaczyl do serwera");*/

                /*Czekam na klienta 2*/
                /*System.out.println("Czekam na 2 gracza");
                Socket sockettP2 = server.accept();
                System.out.println("Gracz 2 dolaczyl do serwera");*/

                String mes1,mes2;
                while(true){

                        /**Teraz biore wiadomosc od klienta 2*/
                        //ObjectInputStream checker2 = new ObjectInputStream(sockett2.getInputStream());

                        /**Konwertuje na String*/
                        mes2 = (String) ois2.readObject();
                        System.out.println("Dostalem widomosc od 2 gracza: " + mes2);


                        /**Daje klientowi 1 odpowiedz*/
                        //ObjectOutputStream oos1 = new ObjectOutputStream(sockettP1.getOutputStream());
                        oos1.writeObject(mes2);


                        /**Teraz biore wiadomosc od klienta 1*/
                        //ObjectInputStream checker1 = new ObjectInputStream(sockett2.getInputStream());

                        /**Konwertuje na String*/
                        mes1 = (String) ois1.readObject();
                        System.out.println("Dostalem widomosc od 1 gracza: " + mes1);

                        /**Daje klientowi 2 odpowiedz*/
                       // ObjectOutputStream oos2 = new ObjectOutputStream(sockettP2.getOutputStream());
                        oos2.writeObject(mes1);

                        /**Sprawdzam czy doszlo do porozumienia miedzy graczami*/
                        if((mes1=="WIN" && mes2 =="LOSE") || (mes1=="LOSE" && mes2=="WIN")) break;
                }

                /**zamykam wszystkie zrodla*/
                oos2.close();
                ois1.close();
                oos1.close();
                oos2.close();
                socket2.close();
                socket1.close();

                 System.out.println("Shutting down Socket server!!");
                /**zamykam ServerSocket object*/
                server.close();
        }

}

