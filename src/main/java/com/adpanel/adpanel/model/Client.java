package com.adpanel.adpanel.model;

import com.adpanel.adpanel.model.enums.Status;

import javax.persistence.*;

@Entity
public class Client {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private long id;
    private String fullName;
    private String address;
    private String email;
    private String phone;
    private String fileLink;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "link_id", referencedColumnName = "id")
    private Link link;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "fileDB_id", referencedColumnName = "id")
    private FileDB fileDB;

    public Client(int id, String fullName, String address, String email, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public Client() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public FileDB getFileDB() {
        return fileDB;
    }

    public void setFileDB(FileDB fileDB) {
        this.fileDB = fileDB;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }
}
