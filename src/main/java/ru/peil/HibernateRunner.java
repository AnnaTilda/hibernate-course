package ru.peil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import ru.peil.converter.BirthdayConverter;
import ru.peil.models.Birthday;
import ru.peil.models.Role;
import ru.peil.models.User;

import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        //configuration.addAnnotatedClass(User.class);
        //чтобы не обозначать конвертер над каждой переменной. Флаг обязательно в true,
        //иначе работать не будет или установить с помощью аннотации над конвертером
        //configuration.addAttributeConverter(new BirthdayConverter(), true);
        configuration.addAttributeConverter(new BirthdayConverter());
        //кэмел кейс преобразовывает в формат называний полей в бд
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            System.out.println("OK");
            User user = User.builder()
                    .username("anna3@gmail.com")
                    .lastname("Frolova2")
                    .firstname("Anna")
                    .birthDate(new Birthday(LocalDate.of(2000, 4, 23)))
                    .role(Role.ADMIN)
                    .build();
            session.save(user);
            session.getTransaction().commit();
        }
    }
}
