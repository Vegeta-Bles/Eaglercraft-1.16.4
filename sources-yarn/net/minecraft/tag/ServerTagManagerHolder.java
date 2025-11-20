package net.minecraft.tag;

import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

public class ServerTagManagerHolder {
   private static volatile TagManager tagManager = TagManager.create(
      TagGroup.create(BlockTags.getRequiredTags().stream().collect(Collectors.toMap(Tag.Identified::getId, arg -> (Tag<Block>)arg))),
      TagGroup.create(ItemTags.getRequiredTags().stream().collect(Collectors.toMap(Tag.Identified::getId, arg -> (Tag<Item>)arg))),
      TagGroup.create(FluidTags.getRequiredTags().stream().collect(Collectors.toMap(Tag.Identified::getId, arg -> (Tag<Fluid>)arg))),
      TagGroup.create(EntityTypeTags.getRequiredTags().stream().collect(Collectors.toMap(Tag.Identified::getId, arg -> (Tag<EntityType<?>>)arg)))
   );

   public static TagManager getTagManager() {
      return tagManager;
   }

   public static void setTagManager(TagManager tagManager) {
      ServerTagManagerHolder.tagManager = tagManager;
   }
}
