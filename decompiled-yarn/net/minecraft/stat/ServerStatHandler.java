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

   public ServerStatHandler(MinecraftServer server, File _snowman) {
      this.server = server;
      this.file = _snowman;
      if (_snowman.isFile()) {
         try {
            this.parse(server.getDataFixer(), FileUtils.readFileToString(_snowman));
         } catch (IOException var4) {
            LOGGER.error("Couldn't read statistics file {}", _snowman, var4);
         } catch (JsonParseException var5) {
            LOGGER.error("Couldn't parse statistics file {}", _snowman, var5);
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
      Set<Stat<?>> _snowman = Sets.newHashSet(this.pendingStats);
      this.pendingStats.clear();
      return _snowman;
   }

   public void parse(DataFixer dataFixer, String json) {
      try {
         JsonReader _snowman = new JsonReader(new StringReader(json));
         Throwable var4 = null;

         try {
            _snowman.setLenient(false);
            JsonElement _snowmanx = Streams.parse(_snowman);
            if (!_snowmanx.isJsonNull()) {
               CompoundTag _snowmanxx = jsonToCompound(_snowmanx.getAsJsonObject());
               if (!_snowmanxx.contains("DataVersion", 99)) {
                  _snowmanxx.putInt("DataVersion", 1343);
               }

               _snowmanxx = NbtHelper.update(dataFixer, DataFixTypes.STATS, _snowmanxx, _snowmanxx.getInt("DataVersion"));
               if (_snowmanxx.contains("stats", 10)) {
                  CompoundTag _snowmanxxx = _snowmanxx.getCompound("stats");

                  for (String _snowmanxxxx : _snowmanxxx.getKeys()) {
                     if (_snowmanxxx.contains(_snowmanxxxx, 10)) {
                        Util.ifPresentOrElse(
                           Registry.STAT_TYPE.getOrEmpty(new Identifier(_snowmanxxxx)),
                           _snowmanxxxxx -> {
                              CompoundTag _snowmanxxxxxx = _snowman.getCompound(_snowman);

                              for (String _snowmanx : _snowmanxxxxxx.getKeys()) {
                                 if (_snowmanxxxxxx.contains(_snowmanx, 99)) {
                                    Util.ifPresentOrElse(
                                       this.createStat(_snowmanxxxxx, _snowmanx),
                                       _snowmanxxxxxxxx -> this.statMap.put(_snowmanxxxxxxxx, _snowman.getInt(_snowman)),
                                       () -> LOGGER.warn("Invalid statistic in {}: Don't know what {} is", this.file, _snowman)
                                    );
                                 } else {
                                    LOGGER.warn("Invalid statistic value in {}: Don't know what {} is for key {}", this.file, _snowmanxxxxxx.get(_snowmanx), _snowmanx);
                                 }
                              }
                           },
                           () -> LOGGER.warn("Invalid statistic type in {}: Don't know what {} is", this.file, _snowman)
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
            if (_snowman != null) {
               if (var4 != null) {
                  try {
                     _snowman.close();
                  } catch (Throwable var18) {
                     var4.addSuppressed(var18);
                  }
               } else {
                  _snowman.close();
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

   private static CompoundTag jsonToCompound(JsonObject _snowman) {
      CompoundTag _snowmanx = new CompoundTag();

      for (Entry<String, JsonElement> _snowmanxx : _snowman.entrySet()) {
         JsonElement _snowmanxxx = _snowmanxx.getValue();
         if (_snowmanxxx.isJsonObject()) {
            _snowmanx.put(_snowmanxx.getKey(), jsonToCompound(_snowmanxxx.getAsJsonObject()));
         } else if (_snowmanxxx.isJsonPrimitive()) {
            JsonPrimitive _snowmanxxxx = _snowmanxxx.getAsJsonPrimitive();
            if (_snowmanxxxx.isNumber()) {
               _snowmanx.putInt(_snowmanxx.getKey(), _snowmanxxxx.getAsInt());
            }
         }
      }

      return _snowmanx;
   }

   protected String asString() {
      Map<StatType<?>, JsonObject> _snowman = Maps.newHashMap();
      ObjectIterator var2 = this.statMap.object2IntEntrySet().iterator();

      while (var2.hasNext()) {
         it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Stat<?>> _snowmanx = (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Stat<?>>)var2.next();
         Stat<?> _snowmanxx = (Stat<?>)_snowmanx.getKey();
         _snowman.computeIfAbsent(_snowmanxx.getType(), _snowmanxxx -> new JsonObject()).addProperty(getStatId(_snowmanxx).toString(), _snowmanx.getIntValue());
      }

      JsonObject _snowmanx = new JsonObject();

      for (Entry<StatType<?>, JsonObject> _snowmanxx : _snowman.entrySet()) {
         _snowmanx.add(Registry.STAT_TYPE.getId(_snowmanxx.getKey()).toString(), (JsonElement)_snowmanxx.getValue());
      }

      JsonObject _snowmanxx = new JsonObject();
      _snowmanxx.add("stats", _snowmanx);
      _snowmanxx.addProperty("DataVersion", SharedConstants.getGameVersion().getWorldVersion());
      return _snowmanxx.toString();
   }

   private static <T> Identifier getStatId(Stat<T> _snowman) {
      return _snowman.getType().getRegistry().getId(_snowman.getValue());
   }

   public void updateStatSet() {
      this.pendingStats.addAll(this.statMap.keySet());
   }

   public void sendStats(ServerPlayerEntity player) {
      int _snowman = this.server.getTicks();
      Object2IntMap<Stat<?>> _snowmanx = new Object2IntOpenHashMap();
      if (_snowman - this.lastStatsUpdate > 300) {
         this.lastStatsUpdate = _snowman;

         for (Stat<?> _snowmanxx : this.takePendingStats()) {
            _snowmanx.put(_snowmanxx, this.getStat(_snowmanxx));
         }
      }

      player.networkHandler.sendPacket(new StatisticsS2CPacket(_snowmanx));
   }
}
