package net.minecraft.entity.ai.brain;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;

public class Memory<T> {
   private final T value;
   private long expiry;

   public Memory(T value, long expiry) {
      this.value = value;
      this.expiry = expiry;
   }

   public void tick() {
      if (this.method_24914()) {
         this.expiry--;
      }
   }

   public static <T> Memory<T> method_28355(T _snowman) {
      return new Memory<>(_snowman, Long.MAX_VALUE);
   }

   public static <T> Memory<T> timed(T value, long expiry) {
      return new Memory<>(value, expiry);
   }

   public T getValue() {
      return this.value;
   }

   public boolean isExpired() {
      return this.expiry <= 0L;
   }

   @Override
   public String toString() {
      return this.value.toString() + (this.method_24914() ? " (ttl: " + this.expiry + ")" : "");
   }

   public boolean method_24914() {
      return this.expiry != Long.MAX_VALUE;
   }

   public static <T> Codec<Memory<T>> createCodec(Codec<T> codec) {
      return RecordCodecBuilder.create(
         _snowmanx -> _snowmanx.group(
                  codec.fieldOf("value").forGetter(_snowmanxx -> _snowmanxx.value),
                  Codec.LONG.optionalFieldOf("ttl").forGetter(_snowmanxx -> _snowmanxx.method_24914() ? Optional.of(_snowmanxx.expiry) : Optional.empty())
               )
               .apply(_snowmanx, (_snowmanxx, _snowmanxxx) -> new Memory<>(_snowmanxx, _snowmanxxx.orElse(Long.MAX_VALUE)))
      );
   }
}
