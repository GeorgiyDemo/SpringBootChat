package com.demka.demkaserver.entities.response;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSearchResponseEntity {
    private String id;
    private String name;
    private Long timeCreated;
}