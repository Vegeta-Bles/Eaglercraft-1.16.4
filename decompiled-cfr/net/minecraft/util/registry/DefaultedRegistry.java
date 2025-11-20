/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Lifecycle
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 */
package net.minecraft.util.registry;

import com.mojang.serialization.Lifecycle;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;

public class DefaultedRegistry<T>
extends SimpleRegistry<T> {
    private final Identifier defaultId;
    private T defaultValue;

    public DefaultedRegistry(String defaultId, RegistryKey<? extends Registry<T>> registryKey, Lifecycle lifecycle) {
        super(registryKey, lifecycle);
        this.defaultId = new Identifier(defaultId);
    }

    @Override
    public <V extends T> V set(int rawId, RegistryKey<T> key, V entry, Lifecycle lifecycle) {
        if (this.defaultId.equals(key.getValue())) {
            this.defaultValue = entry;
        }
        return super.set(rawId, key, entry, lifecycle);
    }

    @Override
    public int getRawId(@Nullable T entry) {
        int n = super.getRawId(entry);
        return n == -1 ? super.getRawId(this.defaultValue) : n;
    }

    @Override
    @Nonnull
    public Identifier getId(T entry) {
        Identifier identifier = super.getId(entry);
        return identifier == null ? this.defaultId : identifier;
    }

    @Override
    @Nonnull
    public T get(@Nullable Identifier id) {
        Object t = super.get(id);
        return t == null ? this.defaultValue : t;
    }

    @Override
    public Optional<T> getOrEmpty(@Nullable Identifier id) {
        return Optional.ofNullable(super.get(id));
    }

    @Override
    @Nonnull
    public T get(int index) {
        Object t = super.get(index);
        return t == null ? this.defaultValue : t;
    }

    @Override
    @Nonnull
    public T getRandom(Random random) {
        Object t = super.getRandom(random);
        return t == null ? this.defaultValue : t;
    }

    public Identifier getDefaultId() {
        return this.defaultId;
    }
}

