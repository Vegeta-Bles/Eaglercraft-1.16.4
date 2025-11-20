/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 */
package net.minecraft.util.dynamic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.ForwardingDynamicOps;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class RegistryReadingOps<T>
extends ForwardingDynamicOps<T> {
    private final DynamicRegistryManager manager;

    public static <T> RegistryReadingOps<T> of(DynamicOps<T> delegate, DynamicRegistryManager tracker) {
        return new RegistryReadingOps<T>(delegate, tracker);
    }

    private RegistryReadingOps(DynamicOps<T> delegate, DynamicRegistryManager tracker) {
        super(delegate);
        this.manager = tracker;
    }

    protected <E> DataResult<T> encodeOrId(E input, T prefix, RegistryKey<? extends Registry<E>> registryReference, Codec<E> codec) {
        Optional optional = this.manager.getOptional(registryReference);
        if (optional.isPresent() && (_snowman = (_snowman = optional.get()).getKey(input)).isPresent()) {
            RegistryKey<E> registryKey = _snowman.get();
            return Identifier.CODEC.encode((Object)registryKey.getValue(), this.delegate, prefix);
        }
        return codec.encode(input, (DynamicOps)this, prefix);
    }
}

