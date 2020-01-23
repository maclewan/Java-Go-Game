package hibernate;


import hibernate.dto.GameStateEntity;

import org.hibernate.Session;

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

        //Add new Employee object
        GameStateEntity emp = new GameStateEntity();
        emp.setGameId(1);
        emp.setMoveId(1);
        emp.setY(1);
        emp.setX(2);
        emp.setisBlack(true);

        session.save(emp);

        session.getTransaction().commit();
        HibernateUtil.shutdown();
    }

}
