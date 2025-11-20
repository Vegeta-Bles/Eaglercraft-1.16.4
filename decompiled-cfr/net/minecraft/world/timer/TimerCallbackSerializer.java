/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.timer;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.timer.FunctionTagTimerCallback;
import net.minecraft.world.timer.FunctionTimerCallback;
import net.minecraft.world.timer.TimerCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimerCallbackSerializer<C> {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final TimerCallbackSerializer<MinecraftServer> INSTANCE = new TimerCallbackSerializer<MinecraftServer>().registerSerializer(new FunctionTimerCallback.Serializer()).registerSerializer(new FunctionTagTimerCallback.Serializer());
    private final Map<Identifier, TimerCallback.Serializer<C, ?>> serializersByType = Maps.newHashMap();
    private final Map<Class<?>, TimerCallback.Serializer<C, ?>> serializersByClass = Maps.newHashMap();

    @VisibleForTesting
    public TimerCallbackSerializer() {
    }

    public TimerCallbackSerializer<C> registerSerializer(TimerCallback.Serializer<C, ?> serializer) {
        this.serializersByType.put(serializer.getId(), serializer);
        this.serializersByClass.put(serializer.getCallbackClass(), serializer);
        return this;
    }

    private <T extends TimerCallback<C>> TimerCallback.Serializer<C, T> getSerializer(Class<?> clazz) {
        return this.serializersByClass.get(clazz);
    }

    public <T extends TimerCallback<C>> CompoundTag serialize(T callback) {
        TimerCallback.Serializer<T, T> serializer = this.getSerializer(callback.getClass());
        CompoundTag _snowman2 = new CompoundTag();
        serializer.serialize(_snowman2, callback);
        _snowman2.putString("Type", serializer.getId().toString());
        return _snowman2;
    }

    @Nullable
    public TimerCallback<C> deserialize(CompoundTag tag) {
        Identifier identifier = Identifier.tryParse(tag.getString("Type"));
        TimerCallback.Serializer<C, ?> _snowman2 = this.serializersByType.get(identifier);
        if (_snowman2 == null) {
            LOGGER.error("Failed to deserialize timer callback: " + tag);
            return null;
        }
        try {
            return _snowman2.deserialize(tag);
        }
        catch (Exception _snowman3) {
            LOGGER.error("Failed to deserialize timer callback: " + tag, (Throwable)_snowman3);
            return null;
        }
    }
}

