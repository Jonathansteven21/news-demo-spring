package com.egg.news.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Arrays;

@Entity
public class Image {

    // ID field with UUID generation strategy
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String mime;
    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="MEDIUMBLOB")
    private byte[] content;

    public Image() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", mime='" + mime + '\'' +
                ", name='" + name + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
