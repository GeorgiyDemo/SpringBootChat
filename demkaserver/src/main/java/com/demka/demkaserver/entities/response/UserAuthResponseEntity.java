package com.demka.demkaserver.entities.response;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAuthResponseEntity {
    private String id;
    private String name;
    private String key;
    private Long timeCreated;
}