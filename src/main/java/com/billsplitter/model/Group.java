package com.billsplitter.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "groups")
public class Group {

    @Id
    private String id;
    private String name;
    private Set<String> members = new HashSet<>();  // Storing User IDs as Strings

    public Group() {}

    public Group(String name) {
        this.name = name;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<String> getMembers() { return members; }
    public void setMembers(Set<String> members) { this.members = members; }
}
