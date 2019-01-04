package com.ms.bunnet.domain;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

public class FileKey {
    private String name;
    private long created;
    private long modified;
    private long size;

    public FileKey(String name, BasicFileAttributes attributes) {
        this.name = name;
        this.created = attributes.creationTime().toMillis();
        this.modified = attributes.lastModifiedTime().toMillis();
        this.size = attributes.size();
    }

    public FileKey(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileKey fileKey = (FileKey) o;
        return Objects.equals(name, fileKey.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getModified() {
        return modified;
    }

    public void setModified(long modified) {
        this.modified = modified;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
