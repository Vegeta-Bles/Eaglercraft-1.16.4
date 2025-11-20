package net.minecraft.data.server;

import java.util.function.Consumer;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.ChangedDimensionCriterion;
import net.minecraft.advancement.criterion.CuredZombieVillagerCriterion;
import net.minecraft.advancement.criterion.EnchantedItemCriterion;
import net.minecraft.advancement.criterion.EntityHurtPlayerCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.LocationArrivalCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.predicate.DamagePredicate;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.tag.ItemTags;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.StructureFeature;

public class StoryTabAdvancementGenerator implements Consumer<Consumer<Advancement>> {
   public StoryTabAdvancementGenerator() {
   }

   public void accept(Consumer<Advancement> _snowman) {
      Advancement _snowmanx = Advancement.Task.create()
         .display(
            Blocks.GRASS_BLOCK,
            new TranslatableText("advancements.story.root.title"),
            new TranslatableText("advancements.story.root.description"),
            new Identifier("textures/gui/advancements/backgrounds/stone.png"),
            AdvancementFrame.TASK,
            false,
            false,
            false
         )
         .criterion("crafting_table", InventoryChangedCriterion.Conditions.items(Blocks.CRAFTING_TABLE))
         .build(_snowman, "story/root");
      Advancement _snowmanxx = Advancement.Task.create()
         .parent(_snowmanx)
         .display(
            Items.WOODEN_PICKAXE,
            new TranslatableText("advancements.story.mine_stone.title"),
            new TranslatableText("advancements.story.mine_stone.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criterion("get_stone", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(ItemTags.STONE_TOOL_MATERIALS).build()))
         .build(_snowman, "story/mine_stone");
      Advancement _snowmanxxx = Advancement.Task.create()
         .parent(_snowmanxx)
         .display(
            Items.STONE_PICKAXE,
            new TranslatableText("advancements.story.upgrade_tools.title"),
            new TranslatableText("advancements.story.upgrade_tools.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criterion("stone_pickaxe", InventoryChangedCriterion.Conditions.items(Items.STONE_PICKAXE))
         .build(_snowman, "story/upgrade_tools");
      Advancement _snowmanxxxx = Advancement.Task.create()
         .parent(_snowmanxxx)
         .display(
            Items.IRON_INGOT,
            new TranslatableText("advancements.story.smelt_iron.title"),
            new TranslatableText("advancements.story.smelt_iron.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criterion("iron", InventoryChangedCriterion.Conditions.items(Items.IRON_INGOT))
         .build(_snowman, "story/smelt_iron");
      Advancement _snowmanxxxxx = Advancement.Task.create()
         .parent(_snowmanxxxx)
         .display(
            Items.IRON_PICKAXE,
            new TranslatableText("advancements.story.iron_tools.title"),
            new TranslatableText("advancements.story.iron_tools.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criterion("iron_pickaxe", InventoryChangedCriterion.Conditions.items(Items.IRON_PICKAXE))
         .build(_snowman, "story/iron_tools");
      Advancement _snowmanxxxxxx = Advancement.Task.create()
         .parent(_snowmanxxxxx)
         .display(
            Items.DIAMOND,
            new TranslatableText("advancements.story.mine_diamond.title"),
            new TranslatableText("advancements.story.mine_diamond.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criterion("diamond", InventoryChangedCriterion.Conditions.items(Items.DIAMOND))
         .build(_snowman, "story/mine_diamond");
      Advancement _snowmanxxxxxxx = Advancement.Task.create()
         .parent(_snowmanxxxx)
         .display(
            Items.LAVA_BUCKET,
            new TranslatableText("advancements.story.lava_bucket.title"),
            new TranslatableText("advancements.story.lava_bucket.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criterion("lava_bucket", InventoryChangedCriterion.Conditions.items(Items.LAVA_BUCKET))
         .build(_snowman, "story/lava_bucket");
      Advancement _snowmanxxxxxxxx = Advancement.Task.create()
         .parent(_snowmanxxxx)
         .display(
            Items.IRON_CHESTPLATE,
            new TranslatableText("advancements.story.obtain_armor.title"),
            new TranslatableText("advancements.story.obtain_armor.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criteriaMerger(CriterionMerger.OR)
         .criterion("iron_helmet", InventoryChangedCriterion.Conditions.items(Items.IRON_HELMET))
         .criterion("iron_chestplate", InventoryChangedCriterion.Conditions.items(Items.IRON_CHESTPLATE))
         .criterion("iron_leggings", InventoryChangedCriterion.Conditions.items(Items.IRON_LEGGINGS))
         .criterion("iron_boots", InventoryChangedCriterion.Conditions.items(Items.IRON_BOOTS))
         .build(_snowman, "story/obtain_armor");
      Advancement.Task.create()
         .parent(_snowmanxxxxxx)
         .display(
            Items.ENCHANTED_BOOK,
            new TranslatableText("advancements.story.enchant_item.title"),
            new TranslatableText("advancements.story.enchant_item.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criterion("enchanted_item", EnchantedItemCriterion.Conditions.any())
         .build(_snowman, "story/enchant_item");
      Advancement _snowmanxxxxxxxxx = Advancement.Task.create()
         .parent(_snowmanxxxxxxx)
         .display(
            Blocks.OBSIDIAN,
            new TranslatableText("advancements.story.form_obsidian.title"),
            new TranslatableText("advancements.story.form_obsidian.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criterion("obsidian", InventoryChangedCriterion.Conditions.items(Blocks.OBSIDIAN))
         .build(_snowman, "story/form_obsidian");
      Advancement.Task.create()
         .parent(_snowmanxxxxxxxx)
         .display(
            Items.SHIELD,
            new TranslatableText("advancements.story.deflect_arrow.title"),
            new TranslatableText("advancements.story.deflect_arrow.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criterion(
            "deflected_projectile",
            EntityHurtPlayerCriterion.Conditions.create(
               DamagePredicate.Builder.create().type(DamageSourcePredicate.Builder.create().projectile(true)).blocked(true)
            )
         )
         .build(_snowman, "story/deflect_arrow");
      Advancement.Task.create()
         .parent(_snowmanxxxxxx)
         .display(
            Items.DIAMOND_CHESTPLATE,
            new TranslatableText("advancements.story.shiny_gear.title"),
            new TranslatableText("advancements.story.shiny_gear.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criteriaMerger(CriterionMerger.OR)
         .criterion("diamond_helmet", InventoryChangedCriterion.Conditions.items(Items.DIAMOND_HELMET))
         .criterion("diamond_chestplate", InventoryChangedCriterion.Conditions.items(Items.DIAMOND_CHESTPLATE))
         .criterion("diamond_leggings", InventoryChangedCriterion.Conditions.items(Items.DIAMOND_LEGGINGS))
         .criterion("diamond_boots", InventoryChangedCriterion.Conditions.items(Items.DIAMOND_BOOTS))
         .build(_snowman, "story/shiny_gear");
      Advancement _snowmanxxxxxxxxxx = Advancement.Task.create()
         .parent(_snowmanxxxxxxxxx)
         .display(
            Items.FLINT_AND_STEEL,
            new TranslatableText("advancements.story.enter_the_nether.title"),
            new TranslatableText("advancements.story.enter_the_nether.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criterion("entered_nether", ChangedDimensionCriterion.Conditions.to(World.NETHER))
         .build(_snowman, "story/enter_the_nether");
      Advancement.Task.create()
         .parent(_snowmanxxxxxxxxxx)
         .display(
            Items.GOLDEN_APPLE,
            new TranslatableText("advancements.story.cure_zombie_villager.title"),
            new TranslatableText("advancements.story.cure_zombie_villager.description"),
            null,
            AdvancementFrame.GOAL,
            true,
            true,
            false
         )
         .criterion("cured_zombie", CuredZombieVillagerCriterion.Conditions.any())
         .build(_snowman, "story/cure_zombie_villager");
      Advancement _snowmanxxxxxxxxxxx = Advancement.Task.create()
         .parent(_snowmanxxxxxxxxxx)
         .display(
            Items.ENDER_EYE,
            new TranslatableText("advancements.story.follow_ender_eye.title"),
            new TranslatableText("advancements.story.follow_ender_eye.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criterion("in_stronghold", LocationArrivalCriterion.Conditions.create(LocationPredicate.feature(StructureFeature.STRONGHOLD)))
         .build(_snowman, "story/follow_ender_eye");
      Advancement.Task.create()
         .parent(_snowmanxxxxxxxxxxx)
         .display(
            Blocks.END_STONE,
            new TranslatableText("advancements.story.enter_the_end.title"),
            new TranslatableText("advancements.story.enter_the_end.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criterion("entered_end", ChangedDimensionCriterion.Conditions.to(World.END))
         .build(_snowman, "story/enter_the_end");
   }
}
