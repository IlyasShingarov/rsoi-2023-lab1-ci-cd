package com.ishingarov.person.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "persons")
@Getter @Setter @Builder @EqualsAndHashCode @ToString @AllArgsConstructor @NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false, length = 80)
    String name;

    @Column
    Integer age;

    @Column
    String address;

    @Column
    String work;
}
