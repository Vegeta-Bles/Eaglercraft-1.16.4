package net.minecraft.tag;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.Identifier;

public class RequiredTagList<T> {
   private TagGroup<T> group = TagGroup.createEmpty();
   private final List<RequiredTagList.TagWrapper<T>> tags = Lists.newArrayList();
   private final Function<TagManager, TagGroup<T>> groupGetter;

   public RequiredTagList(Function<TagManager, TagGroup<T>> managerGetter) {
      this.groupGetter = managerGetter;
   }

   public Tag.Identified<T> add(String id) {
      RequiredTagList.TagWrapper<T> _snowman = new RequiredTagList.TagWrapper<>(new Identifier(id));
      this.tags.add(_snowman);
      return _snowman;
   }

   public void clearAllTags() {
      this.group = TagGroup.createEmpty();
      Tag<T> _snowman = SetTag.empty();
      this.tags.forEach(tag -> tag.updateDelegate(id -> _snowman));
   }

   public void updateTagManager(TagManager manager) {
      TagGroup<T> _snowman = this.groupGetter.apply(manager);
      this.group = _snowman;
      this.tags.forEach(tag -> tag.updateDelegate(_snowman::getTag));
   }

   public TagGroup<T> getGroup() {
      return this.group;
   }

   public List<? extends Tag.Identified<T>> getTags() {
      return this.tags;
   }

   public Set<Identifier> getMissingTags(TagManager manager) {
      TagGroup<T> _snowman = this.groupGetter.apply(manager);
      Set<Identifier> _snowmanx = this.tags.stream().map(RequiredTagList.TagWrapper::getId).collect(Collectors.toSet());
      ImmutableSet<Identifier> _snowmanxx = ImmutableSet.copyOf(_snowman.getTagIds());
      return Sets.difference(_snowmanx, _snowmanxx);
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
