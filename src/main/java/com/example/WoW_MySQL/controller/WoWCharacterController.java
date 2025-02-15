package com.example.WoW_MySQL.controller;

import com.example.WoW_MySQL.model.WoWCharacter;
import com.example.WoW_MySQL.service.BlizzardApiService;
import com.example.WoW_MySQL.repository.WoWCharacterRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
public class WoWCharacterController {

    private final BlizzardApiService blizzardApiService;
    private final WoWCharacterRepository characterRepository;

    public WoWCharacterController(BlizzardApiService blizzardApiService, WoWCharacterRepository characterRepository) {
        this.blizzardApiService = blizzardApiService;
        this.characterRepository = characterRepository;
    }

    @GetMapping("/fetch/{region}/{realm}/{name}")
    public WoWCharacter fetchAndSaveCharacter(
            @PathVariable String region,
            @PathVariable String realm,
            @PathVariable String name) {
        return blizzardApiService.fetchCharacter(region, realm, name);
    }

    @GetMapping("/")
    public List<WoWCharacter> getAllCharacters() {
        return characterRepository.findAll();
    }
}
