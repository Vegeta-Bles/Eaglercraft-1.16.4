package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ServerConfigList<K, V extends ServerConfigEntry<K>> {
   protected static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final File file;
   private final Map<String, V> map = Maps.newHashMap();

   public ServerConfigList(File file) {
      this.file = file;
   }

   public File getFile() {
      return this.file;
   }

   public void add(V entry) {
      this.map.put(this.toString(entry.getKey()), entry);

      try {
         this.save();
      } catch (IOException var3) {
         LOGGER.warn("Could not save the list after adding a user.", var3);
      }
   }

   @Nullable
   public V get(K key) {
      this.removeInvalidEntries();
      return this.map.get(this.toString(key));
   }

   public void remove(K key) {
      this.map.remove(this.toString(key));

      try {
         this.save();
      } catch (IOException var3) {
         LOGGER.warn("Could not save the list after removing a user.", var3);
      }
   }

   public void remove(ServerConfigEntry<K> entry) {
      this.remove(entry.getKey());
   }

   public String[] getNames() {
      return this.map.keySet().toArray(new String[this.map.size()]);
   }

   public boolean isEmpty() {
      return this.map.size() < 1;
   }

   protected String toString(K profile) {
      return profile.toString();
   }

   protected boolean contains(K _snowman) {
      return this.map.containsKey(this.toString(_snowman));
   }

   private void removeInvalidEntries() {
      List<K> _snowman = Lists.newArrayList();

      for (V _snowmanx : this.map.values()) {
         if (_snowmanx.isInvalid()) {
            _snowman.add(_snowmanx.getKey());
         }
      }

      for (K _snowmanxx : _snowman) {
         this.map.remove(this.toString(_snowmanxx));
      }
   }

   protected abstract ServerConfigEntry<K> fromJson(JsonObject json);

   public Collection<V> values() {
      return this.map.values();
   }

   public void save() throws IOException {
      JsonArray _snowman = new JsonArray();
      this.map.values().stream().map(_snowmanx -> Util.make(new JsonObject(), _snowmanx::fromJson)).forEach(_snowman::add);

      try (BufferedWriter _snowmanx = Files.newWriter(this.file, StandardCharsets.UTF_8)) {
         GSON.toJson(_snowman, _snowmanx);
      }
   }

   public void load() throws IOException {
      if (this.file.exists()) {
         try (BufferedReader _snowman = Files.newReader(this.file, StandardCharsets.UTF_8)) {
            JsonArray _snowmanx = (JsonArray)GSON.fromJson(_snowman, JsonArray.class);
            this.map.clear();

            for (JsonElement _snowmanxx : _snowmanx) {
               JsonObject _snowmanxxx = JsonHelper.asObject(_snowmanxx, "entry");
               ServerConfigEntry<K> _snowmanxxxx = this.fromJson(_snowmanxxx);
               if (_snowmanxxxx.getKey() != null) {
                  this.map.put(this.toString(_snowmanxxxx.getKey()), (V)_snowmanxxxx);
               }
            }
         }
      }
   }
}
