/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.io.Files
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.GameProfileRepository
 *  com.mojang.authlib.ProfileLookupCallback
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.ProfileLookupCallback;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserCache {
    private static final Logger field_25805 = LogManager.getLogger();
    private static boolean useRemote;
    private final Map<String, Entry> byName = Maps.newConcurrentMap();
    private final Map<UUID, Entry> byUuid = Maps.newConcurrentMap();
    private final GameProfileRepository profileRepository;
    private final Gson gson = new GsonBuilder().create();
    private final File cacheFile;
    private final AtomicLong field_25724 = new AtomicLong();

    public UserCache(GameProfileRepository profileRepository, File cacheFile) {
        this.profileRepository = profileRepository;
        this.cacheFile = cacheFile;
        Lists.reverse(this.load()).forEach(this::method_30164);
    }

    private void method_30164(Entry entry) {
        GameProfile gameProfile = entry.getProfile();
        entry.method_30171(this.method_30169());
        String _snowman2 = gameProfile.getName();
        if (_snowman2 != null) {
            this.byName.put(_snowman2.toLowerCase(Locale.ROOT), entry);
        }
        if ((_snowman = gameProfile.getId()) != null) {
            this.byUuid.put(_snowman, entry);
        }
    }

    @Nullable
    private static GameProfile findProfileByName(GameProfileRepository repository, String name) {
        final AtomicReference atomicReference = new AtomicReference();
        ProfileLookupCallback _snowman2 = new ProfileLookupCallback(){

            public void onProfileLookupSucceeded(GameProfile profile) {
                atomicReference.set(profile);
            }

            public void onProfileLookupFailed(GameProfile profile, Exception exception) {
                atomicReference.set(null);
            }
        };
        repository.findProfilesByNames(new String[]{name}, Agent.MINECRAFT, _snowman2);
        GameProfile _snowman3 = (GameProfile)atomicReference.get();
        if (!UserCache.shouldUseRemote() && _snowman3 == null) {
            UUID uUID = PlayerEntity.getUuidFromProfile(new GameProfile(null, name));
            _snowman3 = new GameProfile(uUID, name);
        }
        return _snowman3;
    }

    public static void setUseRemote(boolean value) {
        useRemote = value;
    }

    private static boolean shouldUseRemote() {
        return useRemote;
    }

    public void add(GameProfile gameProfile) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(2, 1);
        Date _snowman2 = calendar.getTime();
        Entry _snowman3 = new Entry(gameProfile, _snowman2);
        this.method_30164(_snowman3);
        this.save();
    }

    private long method_30169() {
        return this.field_25724.incrementAndGet();
    }

    @Nullable
    public GameProfile findByName(String string) {
        GameProfile _snowman3;
        string2 = string.toLowerCase(Locale.ROOT);
        Entry entry = this.byName.get(string2);
        boolean _snowman2 = false;
        if (entry != null && new Date().getTime() >= entry.expirationDate.getTime()) {
            this.byUuid.remove(entry.getProfile().getId());
            this.byName.remove(entry.getProfile().getName().toLowerCase(Locale.ROOT));
            _snowman2 = true;
            entry = null;
        }
        if (entry != null) {
            entry.method_30171(this.method_30169());
            _snowman3 = entry.getProfile();
        } else {
            String string2;
            _snowman3 = UserCache.findProfileByName(this.profileRepository, string2);
            if (_snowman3 != null) {
                this.add(_snowman3);
                _snowman2 = false;
            }
        }
        if (_snowman2) {
            this.save();
        }
        return _snowman3;
    }

    @Nullable
    public GameProfile getByUuid(UUID uUID) {
        Entry entry = this.byUuid.get(uUID);
        if (entry == null) {
            return null;
        }
        entry.method_30171(this.method_30169());
        return entry.getProfile();
    }

    private static DateFormat method_30170() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<Entry> load() {
        ArrayList arrayList = Lists.newArrayList();
        try (BufferedReader bufferedReader2 = Files.newReader((File)this.cacheFile, (Charset)StandardCharsets.UTF_8);){
            JsonArray jsonArray = (JsonArray)this.gson.fromJson((Reader)bufferedReader2, JsonArray.class);
            if (jsonArray == null) {
                ArrayList arrayList2 = arrayList;
                return arrayList2;
            }
            DateFormat _snowman2 = UserCache.method_30170();
            jsonArray.forEach(jsonElement -> {
                Entry entry = UserCache.method_30167(jsonElement, _snowman2);
                if (entry != null) {
                    arrayList.add(entry);
                }
            });
            return arrayList;
        }
        catch (FileNotFoundException bufferedReader2) {
            return arrayList;
        }
        catch (JsonParseException | IOException throwable) {
            field_25805.warn("Failed to load profile cache {}", (Object)this.cacheFile, (Object)throwable);
        }
        return arrayList;
    }

    public void save() {
        JsonArray jsonArray = new JsonArray();
        DateFormat _snowman2 = UserCache.method_30170();
        this.getLastAccessedEntries(1000).forEach(entry -> jsonArray.add(UserCache.method_30165(entry, _snowman2)));
        String _snowman3 = this.gson.toJson((JsonElement)jsonArray);
        try (BufferedWriter bufferedWriter = Files.newWriter((File)this.cacheFile, (Charset)StandardCharsets.UTF_8);){
            bufferedWriter.write(_snowman3);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private Stream<Entry> getLastAccessedEntries(int n) {
        return ImmutableList.copyOf(this.byUuid.values()).stream().sorted(Comparator.comparing(Entry::method_30172).reversed()).limit(n);
    }

    private static JsonElement method_30165(Entry entry, DateFormat dateFormat) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", entry.getProfile().getName());
        UUID _snowman2 = entry.getProfile().getId();
        jsonObject.addProperty("uuid", _snowman2 == null ? "" : _snowman2.toString());
        jsonObject.addProperty("expiresOn", dateFormat.format(entry.getExpirationDate()));
        return jsonObject;
    }

    @Nullable
    private static Entry method_30167(JsonElement jsonElement, DateFormat dateFormat) {
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement _snowman2 = jsonObject.get("name");
            JsonElement _snowman3 = jsonObject.get("uuid");
            JsonElement _snowman4 = jsonObject.get("expiresOn");
            if (_snowman2 == null || _snowman3 == null) {
                return null;
            }
            String _snowman5 = _snowman3.getAsString();
            String _snowman6 = _snowman2.getAsString();
            Date _snowman7 = null;
            if (_snowman4 != null) {
                try {
                    _snowman7 = dateFormat.parse(_snowman4.getAsString());
                }
                catch (ParseException parseException) {
                    // empty catch block
                }
            }
            if (_snowman6 == null || _snowman5 == null || _snowman7 == null) {
                return null;
            }
            try {
                UUID uUID = UUID.fromString(_snowman5);
            }
            catch (Throwable throwable) {
                return null;
            }
            return new Entry(new GameProfile(uUID, _snowman6), _snowman7);
        }
        return null;
    }

    static class Entry {
        private final GameProfile profile;
        private final Date expirationDate;
        private volatile long field_25726;

        private Entry(GameProfile gameProfile, Date date) {
            this.profile = gameProfile;
            this.expirationDate = date;
        }

        public GameProfile getProfile() {
            return this.profile;
        }

        public Date getExpirationDate() {
            return this.expirationDate;
        }

        public void method_30171(long l) {
            this.field_25726 = l;
        }

        public long method_30172() {
            return this.field_25726;
        }
    }
}

