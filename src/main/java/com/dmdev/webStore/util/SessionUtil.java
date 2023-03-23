package com.dmdev.webStore.util;


import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.Transaction;


@UtilityClass
public class SessionUtil {
    @Getter
    private Session session;
    @Getter
    private Transaction transaction;

    public Session openTransactionSession() {
        session = HibernateUtil.buildSessionFactory().openSession();
        transaction = session.beginTransaction();
        return session;
    }

    public void closeSession() {
        session.close();
    }

    public void closeTransactionSession() {
        transaction.commit();
//        closeSession();
    }
}