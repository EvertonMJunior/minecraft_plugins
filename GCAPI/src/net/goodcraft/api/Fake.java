package net.goodcraft.api;

import java.util.HashMap;
import java.util.UUID;


public class Fake {
    public static HashMap<UUID, Fake> fakes = new HashMap<>();

    private final UUID uuid;
    private final String tag;

    public Fake(UUID uuid, String tag) {
        this.uuid = uuid;
        this.tag = tag;

        fakes.put(uuid, this);
    }

    public void set() {

    }
}
