package ru.peil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import ru.peil.models.User;

import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        //configuration.addAnnotatedClass(User.class);
        //кэмел кейс преобразовывает в формат называний полей в бд
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            System.out.println("OK");
            User user = User.builder()
                    .username("anna@gmail.com")
                    .lastname("Frolova1")
                    .firstname("Anna")
                    .birthDate(LocalDate.of(2000, 4, 23))
                    .age(23)
                    .build();
            session.save(user);
            session.getTransaction().commit();
        }
    }
}
