package com.adpanel.adpanel.model;

import com.adpanel.adpanel.model.enums.LinkUsability;

import javax.persistence.*;

@Entity
@Table(name = "links")
public class Link {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    private String link;
    @Enumerated(value = EnumType.STRING)
    private LinkUsability linkUsability;
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

    public LinkUsability getLinkUsability() {
        return linkUsability;
    }

    public void setLinkUsability(LinkUsability linkUsability) {
        this.linkUsability = linkUsability;
    }
}
