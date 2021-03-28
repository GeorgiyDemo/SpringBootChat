package com.demka.demkaserver.entities.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


@Data
@NoArgsConstructor
@Document(collection="Rooms")
public class RoomDBEntity {

    @Id
    @JsonProperty
    private String id;
    @JsonProperty
    private String creator_id;
    @JsonProperty
    private List<String> users;
    @JsonProperty
    private String name;
    @JsonProperty
    @Field(name = "time_created")
    private Long timeCreated;

    @Override
    public String toString() {
        return "RoomDBEntity{" +
                "id='" + id + '\'' +
                ", creator_id='" + creator_id + '\'' +
                ", users=" + users +
                ", name='" + name + '\'' +
                ", timeCreated=" + timeCreated +
                '}';
    }
}