package com.demka.demkaserver.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@Document(collection="Rooms")
public class RoomEntity {

    @Id
    @JsonProperty
    private String id;
    @JsonProperty
    private String creator_id;
    @JsonProperty
    private Integer users;
    @JsonProperty
    private String name;
    @JsonProperty
    private Integer timeCreated;

    @Override
    public String toString() {
        return "RoomEntity{" +
                "id='" + id + '\'' +
                ", creator_id='" + creator_id + '\'' +
                ", users=" + users +
                ", name='" + name + '\'' +
                ", timeCreated=" + timeCreated +
                '}';
    }
}