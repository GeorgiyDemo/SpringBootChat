package com.demka.demkaserver.entities.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection="LongPolls")
public class LongPollDBEntity {

    @Id
    @JsonProperty
    private String id;
    @JsonProperty
    private String user_id;
    @JsonProperty
    private String ts;
    @JsonProperty
    private String key;
    @JsonProperty
    private String url;


}