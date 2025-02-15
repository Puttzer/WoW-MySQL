package com.example.WoW_MySQL.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data                // Lombok annotation to generate getters, setters, toString, equals, and hashCode
@NoArgsConstructor   // Generates a no-argument constructor
@AllArgsConstructor  // Generates a constructor with all fields
@Builder             // Enables a flexible object creation pattern
public class WoWCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String realm;
    private String characterClass;
    private int level;


    public WoWCharacter(String name, String realm, String characterClass, int level) {
        this.name = name;
        this.realm = realm;
        this.characterClass = characterClass;
        this.level = level;
    }
}
