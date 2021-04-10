package com.demka.demkaserver.entities.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Сущность сообщения пользователя в MongoDB
 */
@Data
@NoArgsConstructor
@Document(collection = "Messages")
public class MessageDBEntity {

    @Id
    @JsonProperty
    private String id;
    @JsonProperty
    @Field(name = "user_name")
    private String userName;
    @JsonProperty
    @Field(name = "user_id")
    private String userId;
    @JsonProperty
    private String text;
    @JsonProperty
    @Field(name = "room_id")
    private String roomId;
    @JsonProperty
    @Field(name = "time_created")
    private Long timeCreated;

    @Override
    public String toString() {
        return "MessageDBEntity{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", text='" + text + '\'' +
                ", roomId='" + roomId + '\'' +
                ", timeCreated=" + timeCreated +
                '}';
    }
}