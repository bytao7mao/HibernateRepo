package hello;

import org.hibernate.Hibernate;
import org.hibernate.*;

import java.util.Iterator;
import java.util.List;

public class HelloWorld {
    public static void main(String[] args) {

        //First unit of work
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Message message = new Message("Hello World");
        Long msgId = (Long) session.save(message);
        tx.commit();
        session.close();

        //Second unit of work
        Session newSession =
                HibernateUtil.getSessionFactory().openSession();
        Transaction newTransaction = newSession.beginTransaction();

        List messages =
                newSession.createQuery("from hello.Message m ").list();
        System.out.println(messages.size() + " message(s) found:" );

        for (Iterator iter = messages.iterator();
            iter.hasNext();) {
            Message loadedMsg = (Message) iter.next();
            System.out.println(loadedMsg.getText());
        }
        newTransaction.commit();
        newSession.close();
        //shutting down the application
        HibernateUtil.shutdown();


        //Third unit of work
        Session thirdSession =
                HibernateUtil.getSessionFactory().openSession();
        Transaction thirdTransaction = thirdSession.beginTransaction();
        //msgId holds the identifier value of the first message
        message.setText("Greetings Earthling");
        message.setNextMessage(
                new Message("Take me to your leader")
        );
        thirdTransaction.commit();
        thirdSession.close();
        System.out.println("Hello world!");
    }
}