package net.minecraft.tag;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;

public interface TagGroup<T> {
   Map<Identifier, Tag<T>> getTags();

   @Nullable
   default Tag<T> getTag(Identifier id) {
      return this.getTags().get(id);
   }

   Tag<T> getTagOrEmpty(Identifier id);

   @Nullable
   Identifier getUncheckedTagId(Tag<T> tag);

   default Identifier getTagId(Tag<T> tag) {
      Identifier lv = this.getUncheckedTagId(tag);
      if (lv == null) {
         throw new IllegalStateException("Unrecognized tag");
      } else {
         return lv;
      }
   }

   default Collection<Identifier> getTagIds() {
      return this.getTags().keySet();
   }

   @Environment(EnvType.CLIENT)
   default Collection<Identifier> getTagsFor(T object) {
      List<Identifier> list = Lists.newArrayList();

      for (Entry<Identifier, Tag<T>> entry : this.getTags().entrySet()) {
         if (entry.getValue().contains(object)) {
            list.add(entry.getKey());
         }
      }

      return list;
   }

   default void toPacket(PacketByteBuf buf, DefaultedRegistry<T> registry) {
      Map<Identifier, Tag<T>> map = this.getTags();
      buf.writeVarInt(map.size());

      for (Entry<Identifier, Tag<T>> entry : map.entrySet()) {
         buf.writeIdentifier(entry.getKey());
         buf.writeVarInt(entry.getValue().values().size());

         for (T object : entry.getValue().values()) {
            buf.writeVarInt(registry.getRawId(object));
         }
      }
   }

   static <T> TagGroup<T> fromPacket(PacketByteBuf buf, Registry<T> registry) {
      Map<Identifier, Tag<T>> map = Maps.newHashMap();
      int i = buf.readVarInt();

      for (int j = 0; j < i; j++) {
         Identifier lv = buf.readIdentifier();
         int k = buf.readVarInt();
         Builder<T> builder = ImmutableSet.builder();

         for (int l = 0; l < k; l++) {
            builder.add(registry.get(buf.readVarInt()));
         }

         map.put(lv, Tag.of(builder.build()));
      }

      return create(map);
   }

   static <T> TagGroup<T> createEmpty() {
      return create(ImmutableBiMap.of());
   }

   static <T> TagGroup<T> create(Map<Identifier, Tag<T>> tags) {
      final BiMap<Identifier, Tag<T>> biMap = ImmutableBiMap.copyOf(tags);
      return new TagGroup<T>() {
         private final Tag<T> emptyTag = SetTag.empty();

         @Override
         public Tag<T> getTagOrEmpty(Identifier id) {
            return (Tag<T>)biMap.getOrDefault(id, this.emptyTag);
         }

         @Nullable
         @Override
         public Identifier getUncheckedTagId(Tag<T> tag) {
            return tag instanceof Tag.Identified ? ((Tag.Identified)tag).getId() : (Identifier)biMap.inverse().get(tag);
         }

         @Override
         public Map<Identifier, Tag<T>> getTags() {
            return biMap;
         }
      };
   }
}
