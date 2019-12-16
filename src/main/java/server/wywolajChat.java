package server;

/*-------------------------*/
/*-------SCENARIUSZ--------*/
/*-------------------------*/
/*Ta klasa to klient on sie polaczy i zacznie czatowac gdy otrzyma wiadomosc koniec zakonczy swoj zywot
*
* */
public class wywolajChat {

    public static void main(String[] args) {
        Server s = null;

        /**------------------------------------------------//
         //-----------tworzenie servera--------------------//
         //------------------------------------------------*/

        //done
        /*deklarowanie chatu - zrob to w serverze(EDIT: juz to zrobilem)*/
        ChatServer chat = new ChatServer(s);

        //done
        /*start serverachatu - zrob to w serwerze gdy sie wszyscy polacza*/
        chat.start();

        // todo:
        /*gdy dostaniesz wiadomosc od obu graczy ze czas zaczac chat to otworz drzwi - SERVER*/
        chat.chatDoor();

    }

}


