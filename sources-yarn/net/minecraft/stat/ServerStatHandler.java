package net.minecraft.stat;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.SharedConstants;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.StatisticsS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerStatHandler extends StatHandler {
   private static final Logger LOGGER = LogManager.getLogger();
   private final MinecraftServer server;
   private final File file;
   private final Set<Stat<?>> pendingStats = Sets.newHashSet();
   private int lastStatsUpdate = -300;

   public ServerStatHandler(MinecraftServer server, File file) {
      this.server = server;
      this.file = file;
      if (file.isFile()) {
         try {
            this.parse(server.getDataFixer(), FileUtils.readFileToString(file));
         } catch (IOException var4) {
            LOGGER.error("Couldn't read statistics file {}", file, var4);
         } catch (JsonParseException var5) {
            LOGGER.error("Couldn't parse statistics file {}", file, var5);
         }
      }
   }

   public void save() {
      try {
         FileUtils.writeStringToFile(this.file, this.asString());
      } catch (IOException var2) {
         LOGGER.error("Couldn't save stats", var2);
      }
   }

   @Override
   public void setStat(PlayerEntity player, Stat<?> stat, int value) {
      super.setStat(player, stat, value);
      this.pendingStats.add(stat);
   }

   private Set<Stat<?>> takePendingStats() {
      Set<Stat<?>> set = Sets.newHashSet(this.pendingStats);
      this.pendingStats.clear();
      return set;
   }

   public void parse(DataFixer dataFixer, String json) {
      try {
         JsonReader jsonReader = new JsonReader(new StringReader(json));
         Throwable var4 = null;

         try {
            jsonReader.setLenient(false);
            JsonElement jsonElement = Streams.parse(jsonReader);
            if (!jsonElement.isJsonNull()) {
               CompoundTag lv = jsonToCompound(jsonElement.getAsJsonObject());
               if (!lv.contains("DataVersion", 99)) {
                  lv.putInt("DataVersion", 1343);
               }

               lv = NbtHelper.update(dataFixer, DataFixTypes.STATS, lv, lv.getInt("DataVersion"));
               if (lv.contains("stats", 10)) {
                  CompoundTag lv2 = lv.getCompound("stats");

                  for (String string2 : lv2.getKeys()) {
                     if (lv2.contains(string2, 10)) {
                        Util.ifPresentOrElse(
                           Registry.STAT_TYPE.getOrEmpty(new Identifier(string2)),
                           arg2 -> {
                              CompoundTag lvx = lv2.getCompound(string2);

                              for (String string2x : lvx.getKeys()) {
                                 if (lvx.contains(string2x, 99)) {
                                    Util.ifPresentOrElse(
                                       this.createStat(arg2, string2x),
                                       arg2x -> this.statMap.put(arg2x, lvx.getInt(string2x)),
                                       () -> LOGGER.warn("Invalid statistic in {}: Don't know what {} is", this.file, string2x)
                                    );
                                 } else {
                                    LOGGER.warn("Invalid statistic value in {}: Don't know what {} is for key {}", this.file, lvx.get(string2x), string2x);
                                 }
                              }
                           },
                           () -> LOGGER.warn("Invalid statistic type in {}: Don't know what {} is", this.file, string2)
                        );
                     }
                  }
               }

               return;
            }

            LOGGER.error("Unable to parse Stat data from {}", this.file);
         } catch (Throwable var19) {
            var4 = var19;
            throw var19;
         } finally {
            if (jsonReader != null) {
               if (var4 != null) {
                  try {
                     jsonReader.close();
                  } catch (Throwable var18) {
                     var4.addSuppressed(var18);
                  }
               } else {
                  jsonReader.close();
               }
            }
         }
      } catch (IOException | JsonParseException var21) {
         LOGGER.error("Unable to parse Stat data from {}", this.file, var21);
      }
   }

   private <T> Optional<Stat<T>> createStat(StatType<T> type, String id) {
      return Optional.ofNullable(Identifier.tryParse(id)).flatMap(type.getRegistry()::getOrEmpty).map(type::getOrCreateStat);
   }

   private static CompoundTag jsonToCompound(JsonObject jsonObject) {
      CompoundTag lv = new CompoundTag();

      for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
         JsonElement jsonElement = entry.getValue();
         if (jsonElement.isJsonObject()) {
            lv.put(entry.getKey(), jsonToCompound(jsonElement.getAsJsonObject()));
         } else if (jsonElement.isJsonPrimitive()) {
            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
            if (jsonPrimitive.isNumber()) {
               lv.putInt(entry.getKey(), jsonPrimitive.getAsInt());
            }
         }
      }

      return lv;
   }

   protected String asString() {
      Map<StatType<?>, JsonObject> map = Maps.newHashMap();
      ObjectIterator jsonObject = this.statMap.object2IntEntrySet().iterator();

      while (jsonObject.hasNext()) {
         it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Stat<?>> entry = (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Stat<?>>)jsonObject.next();
         Stat<?> lv = (Stat<?>)entry.getKey();
         map.computeIfAbsent(lv.getType(), arg -> new JsonObject()).addProperty(getStatId(lv).toString(), entry.getIntValue());
      }

      JsonObject jsonObjectx = new JsonObject();

      for (Entry<StatType<?>, JsonObject> entry2 : map.entrySet()) {
         jsonObjectx.add(Registry.STAT_TYPE.getId(entry2.getKey()).toString(), (JsonElement)entry2.getValue());
      }

      JsonObject jsonObject2 = new JsonObject();
      jsonObject2.add("stats", jsonObjectx);
      jsonObject2.addProperty("DataVersion", SharedConstants.getGameVersion().getWorldVersion());
      return jsonObject2.toString();
   }

   private static <T> Identifier getStatId(Stat<T> arg) {
      return arg.getType().getRegistry().getId(arg.getValue());
   }

   public void updateStatSet() {
      this.pendingStats.addAll(this.statMap.keySet());
   }

   public void sendStats(ServerPlayerEntity player) {
      int i = this.server.getTicks();
      Object2IntMap<Stat<?>> object2IntMap = new Object2IntOpenHashMap();
      if (i - this.lastStatsUpdate > 300) {
         this.lastStatsUpdate = i;

         for (Stat<?> lv : this.takePendingStats()) {
            object2IntMap.put(lv, this.getStat(lv));
         }
      }

      player.networkHandler.sendPacket(new StatisticsS2CPacket(object2IntMap));
   }
}
