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
        String hql = "SELECT E.x FROM games E";
        //Query query = session.createQuery(hql);
        //List results = query.list();

        //Add new Employee object
        GameStateEntity emp = new GameStateEntity();
        Long a= Long.valueOf(1);
        emp.setGameId(a);
        emp.setMoveId(4);
        emp.setY(1);
        emp.setX(2);
        emp.setisBlack(false);

        session.save(emp);

        session.getTransaction().commit();
        HibernateUtil.shutdown();
    }

}
