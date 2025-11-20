package net.minecraft.util.dynamic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class RegistryReadingOps<T> extends ForwardingDynamicOps<T> {
   private final DynamicRegistryManager manager;

   public static <T> RegistryReadingOps<T> of(DynamicOps<T> delegate, DynamicRegistryManager tracker) {
      return new RegistryReadingOps<>(delegate, tracker);
   }

   private RegistryReadingOps(DynamicOps<T> delegate, DynamicRegistryManager tracker) {
      super(delegate);
      this.manager = tracker;
   }

   protected <E> DataResult<T> encodeOrId(E input, T prefix, RegistryKey<? extends Registry<E>> registryReference, Codec<E> codec) {
      Optional<MutableRegistry<E>> _snowman = this.manager.getOptional(registryReference);
      if (_snowman.isPresent()) {
         MutableRegistry<E> _snowmanx = _snowman.get();
         Optional<RegistryKey<E>> _snowmanxx = _snowmanx.getKey(input);
         if (_snowmanxx.isPresent()) {
            RegistryKey<E> _snowmanxxx = _snowmanxx.get();
            return Identifier.CODEC.encode(_snowmanxxx.getValue(), this.delegate, prefix);
         }
      }

      return codec.encode(input, this, prefix);
   }
}
