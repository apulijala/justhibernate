package com.madhusudhan.jh.basics;

import com.madhusudhan.jh.domain.DomainUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


import com.madhusudhan.jh.domain.Movie;
import org.jpwh.model.helloworld.Message;

import javax.transaction.UserTransaction;
import java.util.List;


// import com.madhusudhan.jh.domain.DomainUtil;

public class PlainHibernateTest {


    private SessionFactory sessionFactory = null;
    private ServiceRegistry serviceRegistry = null;
    private Configuration config = null;


    private void initDefault() {

        config = new Configuration().configure();

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(config.getProperties()).buildServiceRegistry();
        sessionFactory = config.buildSessionFactory(serviceRegistry);
        System.out.println("Obtained: " + sessionFactory);

    }


    private void persist() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();


        Message message = new Message();
        message.setText("Hello World!");
        session.persist(message);

        session.getTransaction().commit();

        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<Message> messages =
                sessionFactory.getCurrentSession().createCriteria(
                        Message.class
                ).list();

      System.out.println("size of messages " + messages.size());

      System.out.println(" Message is " + messages.get(0).getText());


    }


    public static void main(String[] args) {
        System.out.println("Plain Hibernate test");
        PlainHibernateTest test = new PlainHibernateTest();
        test.initDefault();
        test.persist();


    }


    public void storeLoadMessage() {

    }


}