package net.minecraft.tag;

import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

public class ServerTagManagerHolder {
   private static volatile TagManager tagManager = TagManager.create(
      TagGroup.create(BlockTags.getRequiredTags().stream().collect(Collectors.toMap(Tag.Identified::getId, _snowman -> (Tag<Block>)_snowman))),
      TagGroup.create(ItemTags.getRequiredTags().stream().collect(Collectors.toMap(Tag.Identified::getId, _snowman -> (Tag<Item>)_snowman))),
      TagGroup.create(FluidTags.getRequiredTags().stream().collect(Collectors.toMap(Tag.Identified::getId, _snowman -> (Tag<Fluid>)_snowman))),
      TagGroup.create(EntityTypeTags.getRequiredTags().stream().collect(Collectors.toMap(Tag.Identified::getId, _snowman -> (Tag<EntityType<?>>)_snowman)))
   );

   public static TagManager getTagManager() {
      return tagManager;
   }

   public static void setTagManager(TagManager tagManager) {
      ServerTagManagerHolder.tagManager = tagManager;
   }
}
