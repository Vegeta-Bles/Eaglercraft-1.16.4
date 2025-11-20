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
      Identifier _snowman = this.getUncheckedTagId(tag);
      if (_snowman == null) {
         throw new IllegalStateException("Unrecognized tag");
      } else {
         return _snowman;
      }
   }

   default Collection<Identifier> getTagIds() {
      return this.getTags().keySet();
   }

   default Collection<Identifier> getTagsFor(T object) {
      List<Identifier> _snowman = Lists.newArrayList();

      for (Entry<Identifier, Tag<T>> _snowmanx : this.getTags().entrySet()) {
         if (_snowmanx.getValue().contains(object)) {
            _snowman.add(_snowmanx.getKey());
         }
      }

      return _snowman;
   }

   default void toPacket(PacketByteBuf buf, DefaultedRegistry<T> registry) {
      Map<Identifier, Tag<T>> _snowman = this.getTags();
      buf.writeVarInt(_snowman.size());

      for (Entry<Identifier, Tag<T>> _snowmanx : _snowman.entrySet()) {
         buf.writeIdentifier(_snowmanx.getKey());
         buf.writeVarInt(_snowmanx.getValue().values().size());

         for (T _snowmanxx : _snowmanx.getValue().values()) {
            buf.writeVarInt(registry.getRawId(_snowmanxx));
         }
      }
   }

   static <T> TagGroup<T> fromPacket(PacketByteBuf buf, Registry<T> registry) {
      Map<Identifier, Tag<T>> _snowman = Maps.newHashMap();
      int _snowmanx = buf.readVarInt();

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         Identifier _snowmanxxx = buf.readIdentifier();
         int _snowmanxxxx = buf.readVarInt();
         Builder<T> _snowmanxxxxx = ImmutableSet.builder();

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxx; _snowmanxxxxxx++) {
            _snowmanxxxxx.add(registry.get(buf.readVarInt()));
         }

         _snowman.put(_snowmanxxx, Tag.of(_snowmanxxxxx.build()));
      }

      return create(_snowman);
   }

   static <T> TagGroup<T> createEmpty() {
      return create(ImmutableBiMap.of());
   }

   static <T> TagGroup<T> create(Map<Identifier, Tag<T>> tags) {
      final BiMap<Identifier, Tag<T>> _snowman = ImmutableBiMap.copyOf(tags);
      return new TagGroup<T>() {
         private final Tag<T> emptyTag = SetTag.empty();

         @Override
         public Tag<T> getTagOrEmpty(Identifier id) {
            return (Tag<T>)_snowman.getOrDefault(id, this.emptyTag);
         }

         @Nullable
         @Override
         public Identifier getUncheckedTagId(Tag<T> tag) {
            return tag instanceof Tag.Identified ? ((Tag.Identified)tag).getId() : (Identifier)_snowman.inverse().get(tag);
         }

         @Override
         public Map<Identifier, Tag<T>> getTags() {
            return _snowman;
         }
      };
   }
}
