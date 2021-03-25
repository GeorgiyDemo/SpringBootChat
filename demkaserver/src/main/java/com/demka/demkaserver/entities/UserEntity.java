package com.demka.demkaserver.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection="Users")
public class UserEntity {

    @Id
    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty
    private Integer login;
    @JsonProperty
    private String password;
    @JsonProperty
    private String key;
    @JsonProperty
    private Integer timeCreated;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", login=" + login +
                ", password='" + password + '\'' +
                ", key='" + key + '\'' +
                ", timeCreated=" + timeCreated +
                '}';
    }
}