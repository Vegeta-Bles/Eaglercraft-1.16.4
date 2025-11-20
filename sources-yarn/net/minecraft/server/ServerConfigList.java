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

   protected boolean contains(K object) {
      return this.map.containsKey(this.toString(object));
   }

   private void removeInvalidEntries() {
      List<K> list = Lists.newArrayList();

      for (V lv : this.map.values()) {
         if (lv.isInvalid()) {
            list.add(lv.getKey());
         }
      }

      for (K object : list) {
         this.map.remove(this.toString(object));
      }
   }

   protected abstract ServerConfigEntry<K> fromJson(JsonObject json);

   public Collection<V> values() {
      return this.map.values();
   }

   public void save() throws IOException {
      JsonArray jsonArray = new JsonArray();
      this.map.values().stream().map(arg -> Util.make(new JsonObject(), arg::fromJson)).forEach(jsonArray::add);

      try (BufferedWriter bufferedWriter = Files.newWriter(this.file, StandardCharsets.UTF_8)) {
         GSON.toJson(jsonArray, bufferedWriter);
      }
   }

   public void load() throws IOException {
      if (this.file.exists()) {
         try (BufferedReader bufferedReader = Files.newReader(this.file, StandardCharsets.UTF_8)) {
            JsonArray jsonArray = (JsonArray)GSON.fromJson(bufferedReader, JsonArray.class);
            this.map.clear();

            for (JsonElement jsonElement : jsonArray) {
               JsonObject jsonObject = JsonHelper.asObject(jsonElement, "entry");
               ServerConfigEntry<K> lv = this.fromJson(jsonObject);
               if (lv.getKey() != null) {
                  this.map.put(this.toString(lv.getKey()), (V)lv);
               }
            }
         }
      }
   }
}
