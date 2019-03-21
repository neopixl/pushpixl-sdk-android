package com.neopixl.pushpixl.network.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Florian ALONSO (Neopixl SA).
 * Copyright © 2019 pushpixl-sdk-android. All rights reserved.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tag {

    private Long id;
    private String tagName;

    public Tag() {
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;

        Tag tag = (Tag) o;

        if (getId() != null ? !getId().equals(tag.getId()) : tag.getId() != null) return false;
        return getTagName() != null ? getTagName().equals(tag.getTagName()) : tag.getTagName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTagName() != null ? getTagName().hashCode() : 0);
        return result;
    }
}
