package com.adpanel.adpanel.model;

import javax.persistence.*;

@Entity
@Table(name = "links")
public class Link {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    private String link;
    @OneToOne(mappedBy = "link")
    private Client client;

    public Link(String link) {
        this.link = link;
    }

    public Link() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
