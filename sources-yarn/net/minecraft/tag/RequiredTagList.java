package net.minecraft.tag;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

public class RequiredTagList<T> {
   private TagGroup<T> group = TagGroup.createEmpty();
   private final List<RequiredTagList.TagWrapper<T>> tags = Lists.newArrayList();
   private final Function<TagManager, TagGroup<T>> groupGetter;

   public RequiredTagList(Function<TagManager, TagGroup<T>> managerGetter) {
      this.groupGetter = managerGetter;
   }

   public Tag.Identified<T> add(String id) {
      RequiredTagList.TagWrapper<T> lv = new RequiredTagList.TagWrapper<>(new Identifier(id));
      this.tags.add(lv);
      return lv;
   }

   @Environment(EnvType.CLIENT)
   public void clearAllTags() {
      this.group = TagGroup.createEmpty();
      Tag<T> lv = SetTag.empty();
      this.tags.forEach(tag -> tag.updateDelegate(id -> lv));
   }

   public void updateTagManager(TagManager manager) {
      TagGroup<T> lv = this.groupGetter.apply(manager);
      this.group = lv;
      this.tags.forEach(tag -> tag.updateDelegate(lv::getTag));
   }

   public TagGroup<T> getGroup() {
      return this.group;
   }

   public List<? extends Tag.Identified<T>> getTags() {
      return this.tags;
   }

   public Set<Identifier> getMissingTags(TagManager manager) {
      TagGroup<T> lv = this.groupGetter.apply(manager);
      Set<Identifier> set = this.tags.stream().map(RequiredTagList.TagWrapper::getId).collect(Collectors.toSet());
      ImmutableSet<Identifier> immutableSet = ImmutableSet.copyOf(lv.getTagIds());
      return Sets.difference(set, immutableSet);
   }

   static class TagWrapper<T> implements Tag.Identified<T> {
      @Nullable
      private Tag<T> delegate;
      protected final Identifier id;

      private TagWrapper(Identifier id) {
         this.id = id;
      }

      @Override
      public Identifier getId() {
         return this.id;
      }

      private Tag<T> get() {
         if (this.delegate == null) {
            throw new IllegalStateException("Tag " + this.id + " used before it was bound");
         } else {
            return this.delegate;
         }
      }

      void updateDelegate(Function<Identifier, Tag<T>> tagFactory) {
         this.delegate = tagFactory.apply(this.id);
      }

      @Override
      public boolean contains(T entry) {
         return this.get().contains(entry);
      }

      @Override
      public List<T> values() {
         return this.get().values();
      }
   }
}
