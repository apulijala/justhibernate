package com.madhusudhan.jh.basics;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.jpwh.model.helloworld.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class HelloWorldJPA {

    private SessionFactory sessionFactory = null;
    private ServiceRegistry serviceRegistry = null;
    private Configuration config = null;


    private void initDefault() {

        config = new Configuration().configure();
        serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(config.getProperties()).buildServiceRegistry();
        sessionFactory = config.buildSessionFactory(serviceRegistry);
        System.out.println("Obtained: " + sessionFactory);

    }


    private void persist() {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("HelloWorldPU");

        Session session = null;
        {

                session = sessionFactory.getCurrentSession();
                session.beginTransaction();

                EntityManager em =
                    emf.createEntityManager();

                Message message = new Message();
                message.setText("Hello World!");
                em.persist(message);

                session.getTransaction().commit();

                em.close();
        }

        {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            EntityManager em =
                    emf.createEntityManager();
            List<Message> messages =
                    em.createQuery("select m from Message m").getResultList();
            System.out.println("size of messages " + messages.size());
            System.out.println(" Message is " + messages.get(0).getText());


        }

    }

    public static void main(String[] args) {

        HelloWorldJPA helloWorldJPA =
                new HelloWorldJPA();
        helloWorldJPA.initDefault();
        helloWorldJPA.persist();

    }
}
