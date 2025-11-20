package net.minecraft.tag;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.registry.Registry;

public interface TagManager {
   TagManager EMPTY = create(TagGroup.createEmpty(), TagGroup.createEmpty(), TagGroup.createEmpty(), TagGroup.createEmpty());

   TagGroup<Block> getBlocks();

   TagGroup<Item> getItems();

   TagGroup<Fluid> getFluids();

   TagGroup<EntityType<?>> getEntityTypes();

   default void apply() {
      RequiredTagListRegistry.updateTagManager(this);
      Blocks.refreshShapeCache();
   }

   default void toPacket(PacketByteBuf buf) {
      this.getBlocks().toPacket(buf, Registry.BLOCK);
      this.getItems().toPacket(buf, Registry.ITEM);
      this.getFluids().toPacket(buf, Registry.FLUID);
      this.getEntityTypes().toPacket(buf, Registry.ENTITY_TYPE);
   }

   static TagManager fromPacket(PacketByteBuf buf) {
      TagGroup<Block> _snowman = TagGroup.fromPacket(buf, Registry.BLOCK);
      TagGroup<Item> _snowmanx = TagGroup.fromPacket(buf, Registry.ITEM);
      TagGroup<Fluid> _snowmanxx = TagGroup.fromPacket(buf, Registry.FLUID);
      TagGroup<EntityType<?>> _snowmanxxx = TagGroup.fromPacket(buf, Registry.ENTITY_TYPE);
      return create(_snowman, _snowmanx, _snowmanxx, _snowmanxxx);
   }

   static TagManager create(TagGroup<Block> blocks, TagGroup<Item> items, TagGroup<Fluid> fluids, TagGroup<EntityType<?>> entityTypes) {
      return new TagManager() {
         @Override
         public TagGroup<Block> getBlocks() {
            return blocks;
         }

         @Override
         public TagGroup<Item> getItems() {
            return items;
         }

         @Override
         public TagGroup<Fluid> getFluids() {
            return fluids;
         }

         @Override
         public TagGroup<EntityType<?>> getEntityTypes() {
            return entityTypes;
         }
      };
   }
}
