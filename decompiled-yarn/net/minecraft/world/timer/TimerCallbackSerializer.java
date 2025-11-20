package net.minecraft.world.timer;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimerCallbackSerializer<C> {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final TimerCallbackSerializer<MinecraftServer> INSTANCE = new TimerCallbackSerializer<MinecraftServer>()
      .registerSerializer(new FunctionTimerCallback.Serializer())
      .registerSerializer(new FunctionTagTimerCallback.Serializer());
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

   private <T extends TimerCallback<C>> TimerCallback.Serializer<C, T> getSerializer(Class<?> _snowman) {
      return (TimerCallback.Serializer<C, T>)this.serializersByClass.get(_snowman);
   }

   public <T extends TimerCallback<C>> CompoundTag serialize(T callback) {
      TimerCallback.Serializer<C, T> _snowman = this.getSerializer(callback.getClass());
      CompoundTag _snowmanx = new CompoundTag();
      _snowman.serialize(_snowmanx, callback);
      _snowmanx.putString("Type", _snowman.getId().toString());
      return _snowmanx;
   }

   @Nullable
   public TimerCallback<C> deserialize(CompoundTag tag) {
      Identifier _snowman = Identifier.tryParse(tag.getString("Type"));
      TimerCallback.Serializer<C, ?> _snowmanx = this.serializersByType.get(_snowman);
      if (_snowmanx == null) {
         LOGGER.error("Failed to deserialize timer callback: " + tag);
         return null;
      } else {
         try {
            return _snowmanx.deserialize(tag);
         } catch (Exception var5) {
            LOGGER.error("Failed to deserialize timer callback: " + tag, var5);
            return null;
         }
      }
   }
}
