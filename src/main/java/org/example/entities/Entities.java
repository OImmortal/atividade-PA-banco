package org.example.entities;

import java.util.UUID;

public class Entities {

    private UUID uuid;

    public Entities() {
        this.uuid = UUID.randomUUID();
    }

    public Entities(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
