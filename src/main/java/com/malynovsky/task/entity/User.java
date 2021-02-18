package com.malynovsky.task.entity;

import com.malynovsky.task.util.LocalDateAttributeConverter;
import com.malynovsky.task.util.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name can't be empty")
    private String lastName;

    @Column(name = "date_of_birth")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column
    private MaritalStatus status;

}
