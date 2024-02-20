package ru.peil;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;
import ru.peil.models.Birthday;
import ru.peil.models.User;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class HibernateRunnerTest {
    @Test
    void checkReflectionAPI() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .username("anna@gmail.com")
                .lastname("Frolova1")
                .firstname("Anna")
                .birthDate(new Birthday(LocalDate.of(2000, 4, 23)))
                .build();
        final Class<? extends User> userClass = user.getClass();
        final String tableName = Optional.ofNullable(userClass.getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(userClass.getName());

        final Field[] declaredFields = userClass.getDeclaredFields();

        final String columnNames = Arrays.stream(declaredFields)
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(Collectors.joining(","));

        final String questions = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(Collectors.joining(","));

        final String sql = "insert" +
                        "\n into" +
                        "\n %s" +
                        "\n (%s)" +
                        "\n values" +
                        "\n (%s)";
        ;

        Connection connection = null;
        final PreparedStatement preparedStatement =
                connection.prepareStatement(sql.formatted(tableName, columnNames, questions));
        int i = 0;
        for (final Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            preparedStatement.setObject(i++, declaredField.get(user));
        }
    }

}