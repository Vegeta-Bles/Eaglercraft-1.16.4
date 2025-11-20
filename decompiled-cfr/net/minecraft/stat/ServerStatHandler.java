/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.internal.Streams
 *  com.google.gson.stream.JsonReader
 *  com.mojang.datafixers.DataFixer
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  org.apache.commons.io.FileUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.stat;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.SharedConstants;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.StatisticsS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.StatType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerStatHandler
extends StatHandler {
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
                this.parse(server.getDataFixer(), FileUtils.readFileToString((File)file));
            }
            catch (IOException iOException) {
                LOGGER.error("Couldn't read statistics file {}", (Object)file, (Object)iOException);
            }
            catch (JsonParseException jsonParseException) {
                LOGGER.error("Couldn't parse statistics file {}", (Object)file, (Object)jsonParseException);
            }
        }
    }

    public void save() {
        try {
            FileUtils.writeStringToFile((File)this.file, (String)this.asString());
        }
        catch (IOException iOException) {
            LOGGER.error("Couldn't save stats", (Throwable)iOException);
        }
    }

    @Override
    public void setStat(PlayerEntity player, Stat<?> stat, int value) {
        super.setStat(player, stat, value);
        this.pendingStats.add(stat);
    }

    private Set<Stat<?>> takePendingStats() {
        HashSet hashSet = Sets.newHashSet(this.pendingStats);
        this.pendingStats.clear();
        return hashSet;
    }

    public void parse(DataFixer dataFixer, String json) {
        try (JsonReader jsonReader = new JsonReader((Reader)new StringReader(json));){
            jsonReader.setLenient(false);
            JsonElement jsonElement = Streams.parse((JsonReader)jsonReader);
            if (jsonElement.isJsonNull()) {
                LOGGER.error("Unable to parse Stat data from {}", (Object)this.file);
                return;
            }
            CompoundTag _snowman2 = ServerStatHandler.jsonToCompound(jsonElement.getAsJsonObject());
            if (!_snowman2.contains("DataVersion", 99)) {
                _snowman2.putInt("DataVersion", 1343);
            }
            if ((_snowman2 = NbtHelper.update(dataFixer, DataFixTypes.STATS, _snowman2, _snowman2.getInt("DataVersion"))).contains("stats", 10)) {
                CompoundTag compoundTag = _snowman2.getCompound("stats");
                for (String string : compoundTag.getKeys()) {
                    if (!compoundTag.contains(string, 10)) continue;
                    Util.ifPresentOrElse(Registry.STAT_TYPE.getOrEmpty(new Identifier(string)), statType -> {
                        CompoundTag compoundTag2 = compoundTag.getCompound(string);
                        for (String string2 : compoundTag2.getKeys()) {
                            if (compoundTag2.contains(string2, 99)) {
                                Util.ifPresentOrElse(this.createStat((StatType)statType, string2), stat -> this.statMap.put(stat, compoundTag2.getInt(string2)), () -> LOGGER.warn("Invalid statistic in {}: Don't know what {} is", (Object)this.file, (Object)string2));
                                continue;
                            }
                            LOGGER.warn("Invalid statistic value in {}: Don't know what {} is for key {}", (Object)this.file, (Object)compoundTag2.get(string2), (Object)string2);
                        }
                    }, () -> LOGGER.warn("Invalid statistic type in {}: Don't know what {} is", (Object)this.file, (Object)string));
                }
            }
        }
        catch (JsonParseException | IOException throwable) {
            LOGGER.error("Unable to parse Stat data from {}", (Object)this.file, (Object)throwable);
        }
    }

    private <T> Optional<Stat<T>> createStat(StatType<T> type, String id) {
        return Optional.ofNullable(Identifier.tryParse(id)).flatMap(type.getRegistry()::getOrEmpty).map(type::getOrCreateStat);
    }

    private static CompoundTag jsonToCompound(JsonObject jsonObject) {
        CompoundTag compoundTag = new CompoundTag();
        for (Map.Entry entry : jsonObject.entrySet()) {
            JsonElement jsonElement = (JsonElement)entry.getValue();
            if (jsonElement.isJsonObject()) {
                compoundTag.put((String)entry.getKey(), ServerStatHandler.jsonToCompound(jsonElement.getAsJsonObject()));
                continue;
            }
            if (!jsonElement.isJsonPrimitive() || !(_snowman = jsonElement.getAsJsonPrimitive()).isNumber()) continue;
            compoundTag.putInt((String)entry.getKey(), _snowman.getAsInt());
        }
        return compoundTag;
    }

    protected String asString() {
        HashMap hashMap = Maps.newHashMap();
        for (Object object2 : this.statMap.object2IntEntrySet()) {
            Stat stat = (Stat)object2.getKey();
            hashMap.computeIfAbsent(stat.getType(), statType -> new JsonObject()).addProperty(ServerStatHandler.getStatId(stat).toString(), (Number)object2.getIntValue());
        }
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry entry : hashMap.entrySet()) {
            jsonObject.add(Registry.STAT_TYPE.getId((StatType<?>)entry.getKey()).toString(), (JsonElement)entry.getValue());
        }
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.add("stats", (JsonElement)jsonObject);
        jsonObject2.addProperty("DataVersion", (Number)SharedConstants.getGameVersion().getWorldVersion());
        return jsonObject2.toString();
    }

    private static <T> Identifier getStatId(Stat<T> stat) {
        return stat.getType().getRegistry().getId(stat.getValue());
    }

    public void updateStatSet() {
        this.pendingStats.addAll((Collection<Stat<?>>)this.statMap.keySet());
    }

    public void sendStats(ServerPlayerEntity player) {
        int n = this.server.getTicks();
        Object2IntOpenHashMap _snowman2 = new Object2IntOpenHashMap();
        if (n - this.lastStatsUpdate > 300) {
            this.lastStatsUpdate = n;
            for (Stat<?> stat : this.takePendingStats()) {
                _snowman2.put(stat, this.getStat(stat));
            }
        }
        player.networkHandler.sendPacket(new StatisticsS2CPacket((Object2IntMap<Stat<?>>)_snowman2));
    }
}

