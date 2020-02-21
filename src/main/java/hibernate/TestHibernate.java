package hibernate;


import hibernate.dto.GameStateEntity;

import jdk.jfr.Unsigned;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class TestHibernate {

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        /**
         *
         * MACIEJU,
         *
         * tutaj mamy przyklad dodawania czegos do
         * bazy wystarczy to wpisac w odpowiednie miejsca
         */

        long s1= new Date().getTime();

        System.out.println(s1);




        //Add new Employee object


        for(int i=0;i<100;i++) {


            GameStateEntity emp = new GameStateEntity();
            emp.setGameId(s1);
            emp.setMoveId(i);
            emp.setY(9+i);
            emp.setX(6+i);
            emp.setisBlack(true);


            session.save(emp);
        }


        session.getTransaction().commit();



        HibernateUtil.shutdown();
    }

}
