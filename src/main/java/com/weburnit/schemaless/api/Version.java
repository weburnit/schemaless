package com.weburnit.schemaless.api;

import lombok.Data;

@Data
public class Version {

    String version;
    String name;

    public Version(String version, String name) {
        this.version = version;
        this.name = name;
    }
}
