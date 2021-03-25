package com.demka.demkaserver.entities.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection="Messages")
public class MessageDBEntity {

    @Id
    @JsonProperty
    private String id;
    @JsonProperty
    private String user_id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String text;
    @JsonProperty
    private String roomId;
    @JsonProperty
    private Integer timeCreated;

    @Override
    public String toString() {
        return "MessageDBEntity{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", roomId='" + roomId + '\'' +
                ", timeCreated=" + timeCreated +
                '}';
    }
}