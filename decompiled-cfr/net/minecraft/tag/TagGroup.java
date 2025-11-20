/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.BiMap
 *  com.google.common.collect.ImmutableBiMap
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.ImmutableSet$Builder
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.tag;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.tag.SetTag;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;

public interface TagGroup<T> {
    public Map<Identifier, Tag<T>> getTags();

    @Nullable
    default public Tag<T> getTag(Identifier id) {
        return this.getTags().get(id);
    }

    public Tag<T> getTagOrEmpty(Identifier var1);

    @Nullable
    public Identifier getUncheckedTagId(Tag<T> var1);

    default public Identifier getTagId(Tag<T> tag) {
        Identifier identifier = this.getUncheckedTagId(tag);
        if (identifier == null) {
            throw new IllegalStateException("Unrecognized tag");
        }
        return identifier;
    }

    default public Collection<Identifier> getTagIds() {
        return this.getTags().keySet();
    }

    default public Collection<Identifier> getTagsFor(T object) {
        ArrayList arrayList = Lists.newArrayList();
        for (Map.Entry<Identifier, Tag<T>> entry : this.getTags().entrySet()) {
            if (!entry.getValue().contains(object)) continue;
            arrayList.add(entry.getKey());
        }
        return arrayList;
    }

    default public void toPacket(PacketByteBuf buf, DefaultedRegistry<T> registry) {
        Map<Identifier, Tag<T>> map = this.getTags();
        buf.writeVarInt(map.size());
        for (Map.Entry<Identifier, Tag<T>> entry : map.entrySet()) {
            buf.writeIdentifier(entry.getKey());
            buf.writeVarInt(entry.getValue().values().size());
            for (T t : entry.getValue().values()) {
                buf.writeVarInt(registry.getRawId(t));
            }
        }
    }

    public static <T> TagGroup<T> fromPacket(PacketByteBuf buf, Registry<T> registry) {
        HashMap hashMap = Maps.newHashMap();
        int _snowman2 = buf.readVarInt();
        for (int i = 0; i < _snowman2; ++i) {
            Identifier identifier = buf.readIdentifier();
            int _snowman3 = buf.readVarInt();
            ImmutableSet.Builder _snowman4 = ImmutableSet.builder();
            for (int j = 0; j < _snowman3; ++j) {
                _snowman4.add(registry.get(buf.readVarInt()));
            }
            hashMap.put(identifier, Tag.of(_snowman4.build()));
        }
        return TagGroup.create(hashMap);
    }

    public static <T> TagGroup<T> createEmpty() {
        return TagGroup.create(ImmutableBiMap.of());
    }

    public static <T> TagGroup<T> create(Map<Identifier, Tag<T>> tags) {
        ImmutableBiMap immutableBiMap = ImmutableBiMap.copyOf(tags);
        return new TagGroup<T>((BiMap)immutableBiMap){
            private final Tag<T> emptyTag = SetTag.empty();
            final /* synthetic */ BiMap tags;
            {
                this.tags = biMap;
            }

            @Override
            public Tag<T> getTagOrEmpty(Identifier id) {
                return (Tag)this.tags.getOrDefault((Object)id, this.emptyTag);
            }

            @Override
            @Nullable
            public Identifier getUncheckedTagId(Tag<T> tag) {
                if (tag instanceof Tag.Identified) {
                    return ((Tag.Identified)tag).getId();
                }
                return (Identifier)this.tags.inverse().get(tag);
            }

            @Override
            public Map<Identifier, Tag<T>> getTags() {
                return this.tags;
            }
        };
    }
}

