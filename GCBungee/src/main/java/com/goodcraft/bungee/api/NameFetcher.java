package com.goodcraft.bungee.api;

import com.google.common.collect.ImmutableList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.Callable;

public class NameFetcher implements Callable<Map<UUID, NameFetcher.Lookup<NameFetcher.NameHistory>>> {
    private static final String PROFILE_URL = "https://api.mojang.com/user/profiles/%s/names";
    private final JSONParser jsonParser = new JSONParser();
    private final List<UUID> uuids;

    public NameFetcher(List<UUID> uuids) {
        this.uuids = ImmutableList.copyOf(uuids);
    }

    public static Callable<Lookup<NameHistory>> getNameHistory(final UUID uuid) {
        return new Callable<Lookup<NameHistory>>() {
            @Override
            public Lookup<NameHistory> call() throws Exception {
                return new NameFetcher(Collections.singletonList(uuid)).call().get(uuid);
            }
        };
    }

    @Override
    public Map<UUID, Lookup<NameHistory>> call() throws Exception {
        Map<UUID, Lookup<NameHistory>> uuidNameHistory = new HashMap<>();
        for (UUID uuid : uuids) {
            try {
                URLConnection connection = new URL(String.format(PROFILE_URL, uuid.toString().replace("-", ""))).openConnection();
                List<JSONObject> response = (List<JSONObject>) jsonParser.parse(new InputStreamReader(connection.getInputStream()));
                NameHistory history = new NameHistory();
                for (JSONObject object : response) {
                    String name = (String) object.get("name");
                    Date changedToAt = object.containsKey("changedToAt") ? new Date((long) object.get("changedToAt")) : null;
                    history.getHistory().add(new NameHistoryEntry(name, changedToAt));
                }
                history.sort();
                uuidNameHistory.put(uuid, new Lookup<>(history, null));
            } catch (Exception e) {
                uuidNameHistory.put(uuid, new Lookup<NameHistory>(null, e));
            }
        }
        return uuidNameHistory;
    }

    public static class Lookup<T> {
        private final T value;
        private final Exception exception;

        public Lookup(T value, Exception exception) {
            this.value = value;
            this.exception = exception;
        }

        public boolean wasSuccessful() {
            return exception == null;
        }

        public T getValue() {
            return value;
        }

        public Exception getException() {
            return exception;
        }
    }

    public static class NameHistory {
        private final List<NameHistoryEntry> history = new ArrayList<>();

        public String getOriginalName() {
            return history.get(0).getName();
        }

        public String getCurrentName() {
            return history.get(history.size() - 1).getName();
        }

        public List<NameHistoryEntry> getHistory() {
            return history;
        }

        private void sort() {
            Collections.sort(history, new Comparator<NameHistoryEntry>() {
                @Override
                public int compare(NameHistoryEntry o1, NameHistoryEntry o2) {
                    if (o1.getChangedAt() == null) {
                        return -1;
                    } else if (o2.getChangedAt() == null) {
                        return 1;
                    } else {
                        return o1.getChangedAt().compareTo(o2.getChangedAt());
                    }
                }
            });
        }

        @Override
        public String toString() {
            return "NameHistory{" +
                    "history=" + history +
                    '}';
        }
    }

    public static class NameHistoryEntry {
        private final String name;
        private final Date changedAt;

        public NameHistoryEntry(String name, Date changedAt) {
            this.name = name;
            this.changedAt = changedAt;
        }

        public String getName() {
            return name;
        }

        /**
         * Gets the date that the user changed to this name at.  If this NameHistoryEntry represents the user's first
         * name, this value will be null.
         *
         * @return the date the user changed to this name or null
         */
        public Date getChangedAt() {
            return changedAt;
        }

        @Override
        public String toString() {
            return "NameHistoryEntry{" +
                    "name='" + name + '\'' +
                    ", changedAt=" + changedAt +
                    '}';
        }
    }
}
