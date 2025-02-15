package com.example.WoW_MySQL.repository;

import com.example.WoW_MySQL.model.WoWCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WoWCharacterRepository extends JpaRepository<WoWCharacter, Long> {
}
