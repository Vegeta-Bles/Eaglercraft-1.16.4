package net.minecraft.data.server;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.function.SetNbtLootFunction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class GiftLootTableGenerator implements Consumer<BiConsumer<Identifier, LootTable.Builder>> {
   public GiftLootTableGenerator() {
   }

   public void accept(BiConsumer<Identifier, LootTable.Builder> _snowman) {
      _snowman.accept(
         LootTables.CAT_MORNING_GIFT_GAMEPLAY,
         LootTable.builder()
            .pool(
               LootPool.builder()
                  .rolls(ConstantLootTableRange.create(1))
                  .with(ItemEntry.builder(Items.RABBIT_HIDE).weight(10))
                  .with(ItemEntry.builder(Items.RABBIT_FOOT).weight(10))
                  .with(ItemEntry.builder(Items.CHICKEN).weight(10))
                  .with(ItemEntry.builder(Items.FEATHER).weight(10))
                  .with(ItemEntry.builder(Items.ROTTEN_FLESH).weight(10))
                  .with(ItemEntry.builder(Items.STRING).weight(10))
                  .with(ItemEntry.builder(Items.PHANTOM_MEMBRANE).weight(2))
            )
      );
      _snowman.accept(
         LootTables.HERO_OF_THE_VILLAGE_ARMORER_GIFT_GAMEPLAY,
         LootTable.builder()
            .pool(
               LootPool.builder()
                  .rolls(ConstantLootTableRange.create(1))
                  .with(ItemEntry.builder(Items.CHAINMAIL_HELMET))
                  .with(ItemEntry.builder(Items.CHAINMAIL_CHESTPLATE))
                  .with(ItemEntry.builder(Items.CHAINMAIL_LEGGINGS))
                  .with(ItemEntry.builder(Items.CHAINMAIL_BOOTS))
            )
      );
      _snowman.accept(
         LootTables.HERO_OF_THE_VILLAGE_BUTCHER_GIFT_GAMEPLAY,
         LootTable.builder()
            .pool(
               LootPool.builder()
                  .rolls(ConstantLootTableRange.create(1))
                  .with(ItemEntry.builder(Items.COOKED_RABBIT))
                  .with(ItemEntry.builder(Items.COOKED_CHICKEN))
                  .with(ItemEntry.builder(Items.COOKED_PORKCHOP))
                  .with(ItemEntry.builder(Items.COOKED_BEEF))
                  .with(ItemEntry.builder(Items.COOKED_MUTTON))
            )
      );
      _snowman.accept(
         LootTables.HERO_OF_THE_VILLAGE_CARTOGRAPHER_GIFT_GAMEPLAY,
         LootTable.builder()
            .pool(LootPool.builder().rolls(ConstantLootTableRange.create(1)).with(ItemEntry.builder(Items.MAP)).with(ItemEntry.builder(Items.PAPER)))
      );
      _snowman.accept(
         LootTables.HERO_OF_THE_VILLAGE_CLERIC_GIFT_GAMEPLAY,
         LootTable.builder()
            .pool(
               LootPool.builder().rolls(ConstantLootTableRange.create(1)).with(ItemEntry.builder(Items.REDSTONE)).with(ItemEntry.builder(Items.LAPIS_LAZULI))
            )
      );
      _snowman.accept(
         LootTables.HERO_OF_THE_VILLAGE_FARMER_GIFT_GAMEPLAY,
         LootTable.builder()
            .pool(
               LootPool.builder()
                  .rolls(ConstantLootTableRange.create(1))
                  .with(ItemEntry.builder(Items.BREAD))
                  .with(ItemEntry.builder(Items.PUMPKIN_PIE))
                  .with(ItemEntry.builder(Items.COOKIE))
            )
      );
      _snowman.accept(
         LootTables.HERO_OF_THE_VILLAGE_FISHERMAN_GIFT_GAMEPLAY,
         LootTable.builder()
            .pool(LootPool.builder().rolls(ConstantLootTableRange.create(1)).with(ItemEntry.builder(Items.COD)).with(ItemEntry.builder(Items.SALMON)))
      );
      _snowman.accept(
         LootTables.HERO_OF_THE_VILLAGE_FLETCHER_GIFT_GAMEPLAY,
         LootTable.builder()
            .pool(
               LootPool.builder()
                  .rolls(ConstantLootTableRange.create(1))
                  .with(ItemEntry.builder(Items.ARROW).weight(26))
                  .with(
                     ItemEntry.builder(Items.TIPPED_ARROW)
                        .apply(SetCountLootFunction.builder(UniformLootTableRange.between(0.0F, 1.0F)))
                        .apply(SetNbtLootFunction.builder(Util.make(new CompoundTag(), _snowmanx -> _snowmanx.putString("Potion", "minecraft:swiftness"))))
                  )
                  .with(
                     ItemEntry.builder(Items.TIPPED_ARROW)
                        .apply(SetCountLootFunction.builder(UniformLootTableRange.between(0.0F, 1.0F)))
                        .apply(SetNbtLootFunction.builder(Util.make(new CompoundTag(), _snowmanx -> _snowmanx.putString("Potion", "minecraft:slowness"))))
                  )
                  .with(
                     ItemEntry.builder(Items.TIPPED_ARROW)
                        .apply(SetCountLootFunction.builder(UniformLootTableRange.between(0.0F, 1.0F)))
                        .apply(SetNbtLootFunction.builder(Util.make(new CompoundTag(), _snowmanx -> _snowmanx.putString("Potion", "minecraft:strength"))))
                  )
                  .with(
                     ItemEntry.builder(Items.TIPPED_ARROW)
                        .apply(SetCountLootFunction.builder(UniformLootTableRange.between(0.0F, 1.0F)))
                        .apply(SetNbtLootFunction.builder(Util.make(new CompoundTag(), _snowmanx -> _snowmanx.putString("Potion", "minecraft:healing"))))
                  )
                  .with(
                     ItemEntry.builder(Items.TIPPED_ARROW)
                        .apply(SetCountLootFunction.builder(UniformLootTableRange.between(0.0F, 1.0F)))
                        .apply(SetNbtLootFunction.builder(Util.make(new CompoundTag(), _snowmanx -> _snowmanx.putString("Potion", "minecraft:harming"))))
                  )
                  .with(
                     ItemEntry.builder(Items.TIPPED_ARROW)
                        .apply(SetCountLootFunction.builder(UniformLootTableRange.between(0.0F, 1.0F)))
                        .apply(SetNbtLootFunction.builder(Util.make(new CompoundTag(), _snowmanx -> _snowmanx.putString("Potion", "minecraft:leaping"))))
                  )
                  .with(
                     ItemEntry.builder(Items.TIPPED_ARROW)
                        .apply(SetCountLootFunction.builder(UniformLootTableRange.between(0.0F, 1.0F)))
                        .apply(SetNbtLootFunction.builder(Util.make(new CompoundTag(), _snowmanx -> _snowmanx.putString("Potion", "minecraft:regeneration"))))
                  )
                  .with(
                     ItemEntry.builder(Items.TIPPED_ARROW)
                        .apply(SetCountLootFunction.builder(UniformLootTableRange.between(0.0F, 1.0F)))
                        .apply(SetNbtLootFunction.builder(Util.make(new CompoundTag(), _snowmanx -> _snowmanx.putString("Potion", "minecraft:fire_resistance"))))
                  )
                  .with(
                     ItemEntry.builder(Items.TIPPED_ARROW)
                        .apply(SetCountLootFunction.builder(UniformLootTableRange.between(0.0F, 1.0F)))
                        .apply(SetNbtLootFunction.builder(Util.make(new CompoundTag(), _snowmanx -> _snowmanx.putString("Potion", "minecraft:water_breathing"))))
                  )
                  .with(
                     ItemEntry.builder(Items.TIPPED_ARROW)
                        .apply(SetCountLootFunction.builder(UniformLootTableRange.between(0.0F, 1.0F)))
                        .apply(SetNbtLootFunction.builder(Util.make(new CompoundTag(), _snowmanx -> _snowmanx.putString("Potion", "minecraft:invisibility"))))
                  )
                  .with(
                     ItemEntry.builder(Items.TIPPED_ARROW)
                        .apply(SetCountLootFunction.builder(UniformLootTableRange.between(0.0F, 1.0F)))
                        .apply(SetNbtLootFunction.builder(Util.make(new CompoundTag(), _snowmanx -> _snowmanx.putString("Potion", "minecraft:night_vision"))))
                  )
                  .with(
                     ItemEntry.builder(Items.TIPPED_ARROW)
                        .apply(SetCountLootFunction.builder(UniformLootTableRange.between(0.0F, 1.0F)))
                        .apply(SetNbtLootFunction.builder(Util.make(new CompoundTag(), _snowmanx -> _snowmanx.putString("Potion", "minecraft:weakness"))))
                  )
                  .with(
                     ItemEntry.builder(Items.TIPPED_ARROW)
                        .apply(SetCountLootFunction.builder(UniformLootTableRange.between(0.0F, 1.0F)))
                        .apply(SetNbtLootFunction.builder(Util.make(new CompoundTag(), _snowmanx -> _snowmanx.putString("Potion", "minecraft:poison"))))
                  )
            )
      );
      _snowman.accept(
         LootTables.HERO_OF_THE_VILLAGE_LEATHERWORKER_GIFT_GAMEPLAY,
         LootTable.builder().pool(LootPool.builder().rolls(ConstantLootTableRange.create(1)).with(ItemEntry.builder(Items.LEATHER)))
      );
      _snowman.accept(
         LootTables.HERO_OF_THE_VILLAGE_LIBRARIAN_GIFT_GAMEPLAY,
         LootTable.builder().pool(LootPool.builder().rolls(ConstantLootTableRange.create(1)).with(ItemEntry.builder(Items.BOOK)))
      );
      _snowman.accept(
         LootTables.HERO_OF_THE_VILLAGE_MASON_GIFT_GAMEPLAY,
         LootTable.builder().pool(LootPool.builder().rolls(ConstantLootTableRange.create(1)).with(ItemEntry.builder(Items.CLAY)))
      );
      _snowman.accept(
         LootTables.HERO_OF_THE_VILLAGE_SHEPHERD_GIFT_GAMEPLAY,
         LootTable.builder()
            .pool(
               LootPool.builder()
                  .rolls(ConstantLootTableRange.create(1))
                  .with(ItemEntry.builder(Items.WHITE_WOOL))
                  .with(ItemEntry.builder(Items.ORANGE_WOOL))
                  .with(ItemEntry.builder(Items.MAGENTA_WOOL))
                  .with(ItemEntry.builder(Items.LIGHT_BLUE_WOOL))
                  .with(ItemEntry.builder(Items.YELLOW_WOOL))
                  .with(ItemEntry.builder(Items.LIME_WOOL))
                  .with(ItemEntry.builder(Items.PINK_WOOL))
                  .with(ItemEntry.builder(Items.GRAY_WOOL))
                  .with(ItemEntry.builder(Items.LIGHT_GRAY_WOOL))
                  .with(ItemEntry.builder(Items.CYAN_WOOL))
                  .with(ItemEntry.builder(Items.PURPLE_WOOL))
                  .with(ItemEntry.builder(Items.BLUE_WOOL))
                  .with(ItemEntry.builder(Items.BROWN_WOOL))
                  .with(ItemEntry.builder(Items.GREEN_WOOL))
                  .with(ItemEntry.builder(Items.RED_WOOL))
                  .with(ItemEntry.builder(Items.BLACK_WOOL))
            )
      );
      _snowman.accept(
         LootTables.HERO_OF_THE_VILLAGE_TOOLSMITH_GIFT_GAMEPLAY,
         LootTable.builder()
            .pool(
               LootPool.builder()
                  .rolls(ConstantLootTableRange.create(1))
                  .with(ItemEntry.builder(Items.STONE_PICKAXE))
                  .with(ItemEntry.builder(Items.STONE_AXE))
                  .with(ItemEntry.builder(Items.STONE_HOE))
                  .with(ItemEntry.builder(Items.STONE_SHOVEL))
            )
      );
      _snowman.accept(
         LootTables.HERO_OF_THE_VILLAGE_WEAPONSMITH_GIFT_GAMEPLAY,
         LootTable.builder()
            .pool(
               LootPool.builder()
                  .rolls(ConstantLootTableRange.create(1))
                  .with(ItemEntry.builder(Items.STONE_AXE))
                  .with(ItemEntry.builder(Items.GOLDEN_AXE))
                  .with(ItemEntry.builder(Items.IRON_AXE))
            )
      );
   }
}
