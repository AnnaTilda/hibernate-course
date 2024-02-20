package ru.peil.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.peil.models.Birthday;

import java.sql.Date;
import java.util.Optional;

//или так
@Converter(autoApply = true)
public class BirthdayConverter implements AttributeConverter<Birthday, Date> {
    @Override
    public Date convertToDatabaseColumn(Birthday attribute) {
        return Optional.ofNullable(attribute)
                .map(Birthday::birthday)
                .map(Date::valueOf)
                .orElse(null);
    }

    @Override
    public Birthday convertToEntityAttribute(Date dbData) {
        return Optional.ofNullable(dbData)
                .map(Date::toLocalDate)
                .map(Birthday::new)
                .orElse(null);
    }
}
