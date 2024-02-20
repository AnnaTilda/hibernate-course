package ru.peil.models;

import jakarta.persistence.*;
import lombok.*;
import ru.peil.converter.BirthdayConverter;

@Table(name = "users", schema = "public")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String username;
    private String firstname;
    private String lastname;
    //@Convert(converter = BirthdayConverter.class)
    private Birthday birthDate;
    @Enumerated(EnumType.STRING)
    private Role role;
}
