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
      Optional<MutableRegistry<E>> optional = this.manager.getOptional(registryReference);
      if (optional.isPresent()) {
         MutableRegistry<E> lv = optional.get();
         Optional<RegistryKey<E>> optional2 = lv.getKey(input);
         if (optional2.isPresent()) {
            RegistryKey<E> lv2 = optional2.get();
            return Identifier.CODEC.encode(lv2.getValue(), this.delegate, prefix);
         }
      }

      return codec.encode(input, this, prefix);
   }
}
