package com.demka.demkaserver.entities.database;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection="Users")
public class UserDBEntity {

    @Id
    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty
    @Indexed(unique=true)
    private String login;
    @JsonProperty
    private String password;
    @JsonProperty
    private String key;
    @JsonProperty
    @Field(name = "master_key")
    private String masterKey;
    @JsonProperty
    @Field(name = "time_created")
    private Long timeCreated;

    @Override
    public String toString() {
        return "UserDBEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", masterKey='" + masterKey + '\'' +
                ", key='" + key + '\'' +
                ", timeCreated=" + timeCreated +
                '}';
    }
}