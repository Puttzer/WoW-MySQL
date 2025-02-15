package com.example.WoW_MySQL.service;

import com.example.WoW_MySQL.model.WoWCharacter;
import com.example.WoW_MySQL.repository.WoWCharacterRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import java.util.Map;
import org.springframework.http.*;

@Service
public class BlizzardApiService {

    private final WoWCharacterRepository characterRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson for JSON parsing

    private final String clientId = "CLIENT_ID_HERE";
    private final String clientSecret = "CLIENT_SECRET_HERE";

    public BlizzardApiService(WoWCharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public String getAccessToken() {
        String url = "https://oauth.battle.net/token";
        String auth = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + auth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        return response.getBody().get("access_token").toString();
    }

    public WoWCharacter fetchCharacter(String region, String realm, String characterName) {
        String token = getAccessToken();
        String url = String.format(
                "https://%s.api.blizzard.com/profile/wow/character/%s/%s?namespace=profile-%s&locale=en_US",
                region, realm.toLowerCase(), characterName.toLowerCase(), region
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode json = objectMapper.readTree(response.getBody());

                String name = json.get("name").asText();
                int level = json.get("level").asInt();
                String characterClass = json.get("character_class").get("name").asText();

                WoWCharacter character = WoWCharacter.builder()
                        .name(name)
                        .realm(realm)
                        .characterClass(characterClass)
                        .level(level)
                        .build();

                return characterRepository.save(character);
            } else {
                throw new RuntimeException("Failed to fetch character: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            System.err.println("⚠️ Blizzard API returned an error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
