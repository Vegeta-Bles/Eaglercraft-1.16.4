package net.minecraft.tag;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.Identifier;

public class RequiredTagListRegistry {
   private static final Map<Identifier, RequiredTagList<?>> REQUIRED_TAG_LISTS = Maps.newHashMap();

   public static <T> RequiredTagList<T> register(Identifier id, Function<TagManager, TagGroup<T>> containerGetter) {
      RequiredTagList<T> _snowman = new RequiredTagList<>(containerGetter);
      RequiredTagList<?> _snowmanx = REQUIRED_TAG_LISTS.putIfAbsent(id, _snowman);
      if (_snowmanx != null) {
         throw new IllegalStateException("Duplicate entry for static tag collection: " + id);
      } else {
         return _snowman;
      }
   }

   public static void updateTagManager(TagManager tagManager) {
      REQUIRED_TAG_LISTS.values().forEach(list -> list.updateTagManager(tagManager));
   }

   public static void clearAllTags() {
      REQUIRED_TAG_LISTS.values().forEach(RequiredTagList::clearAllTags);
   }

   public static Multimap<Identifier, Identifier> getMissingTags(TagManager tagManager) {
      Multimap<Identifier, Identifier> _snowman = HashMultimap.create();
      REQUIRED_TAG_LISTS.forEach((id, list) -> _snowman.putAll(id, list.getMissingTags(tagManager)));
      return _snowman;
   }

   public static void validateRegistrations() {
      RequiredTagList[] _snowman = new RequiredTagList[]{BlockTags.REQUIRED_TAGS, ItemTags.REQUIRED_TAGS, FluidTags.REQUIRED_TAGS, EntityTypeTags.REQUIRED_TAGS};
      boolean _snowmanx = Stream.of(_snowman).anyMatch(list -> !REQUIRED_TAG_LISTS.containsValue(list));
      if (_snowmanx) {
         throw new IllegalStateException("Missing helper registrations");
      }
   }
}
