package Controllers;




import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ChatSerwerNapotem extends Thread{

    public ChatSerwerNapotem(Socket socket, ObjectOutputStream oss, ObjectInputStream ois){
        this.socket=socket;
        this.oos=oss;
        this.ois=ois;
    }




    Socket socket;
    ObjectOutputStream oos;
    ObjectInputStream ois;



    @Override
    public synchronized void run() {

    }
}
