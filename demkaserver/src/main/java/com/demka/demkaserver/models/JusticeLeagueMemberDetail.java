package com.demka.demkaserver.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "justiceLeagueMembers")
public class JusticeLeagueMemberDetail {

    @Id
    private ObjectId id;

    @Indexed
    private String name;

    private String superPower;

    private String location;

    public JusticeLeagueMemberDetail(String name, String superPower, String location) {
        this.name = name;
        this.superPower = superPower;
        this.location = location;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(String id) {
        this.id = new ObjectId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperPower() {
        return superPower;
    }

    public void setSuperPower(String superPower) {
        this.superPower = superPower;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}