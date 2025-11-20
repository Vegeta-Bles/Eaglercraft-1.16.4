package net.minecraft.data.server;

import java.util.function.Consumer;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.BeeNestDestroyedCriterion;
import net.minecraft.advancement.criterion.BredAnimalsCriterion;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.advancement.criterion.FilledBucketCriterion;
import net.minecraft.advancement.criterion.FishingRodHookedCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.ItemUsedOnBlockCriterion;
import net.minecraft.advancement.criterion.PlacedBlockCriterion;
import net.minecraft.advancement.criterion.TameAnimalCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HusbandryTabAdvancementGenerator implements Consumer<Consumer<Advancement>> {
   private static final EntityType<?>[] BREEDABLE_ANIMALS = new EntityType[]{
      EntityType.HORSE,
      EntityType.DONKEY,
      EntityType.MULE,
      EntityType.SHEEP,
      EntityType.COW,
      EntityType.MOOSHROOM,
      EntityType.PIG,
      EntityType.CHICKEN,
      EntityType.WOLF,
      EntityType.OCELOT,
      EntityType.RABBIT,
      EntityType.LLAMA,
      EntityType.CAT,
      EntityType.PANDA,
      EntityType.FOX,
      EntityType.BEE,
      EntityType.HOGLIN,
      EntityType.STRIDER
   };
   private static final Item[] FISH_ITEMS = new Item[]{Items.COD, Items.TROPICAL_FISH, Items.PUFFERFISH, Items.SALMON};
   private static final Item[] FISH_BUCKET_ITEMS = new Item[]{Items.COD_BUCKET, Items.TROPICAL_FISH_BUCKET, Items.PUFFERFISH_BUCKET, Items.SALMON_BUCKET};
   private static final Item[] FOOD_ITEMS = new Item[]{
      Items.APPLE,
      Items.MUSHROOM_STEW,
      Items.BREAD,
      Items.PORKCHOP,
      Items.COOKED_PORKCHOP,
      Items.GOLDEN_APPLE,
      Items.ENCHANTED_GOLDEN_APPLE,
      Items.COD,
      Items.SALMON,
      Items.TROPICAL_FISH,
      Items.PUFFERFISH,
      Items.COOKED_COD,
      Items.COOKED_SALMON,
      Items.COOKIE,
      Items.MELON_SLICE,
      Items.BEEF,
      Items.COOKED_BEEF,
      Items.CHICKEN,
      Items.COOKED_CHICKEN,
      Items.ROTTEN_FLESH,
      Items.SPIDER_EYE,
      Items.CARROT,
      Items.POTATO,
      Items.BAKED_POTATO,
      Items.POISONOUS_POTATO,
      Items.GOLDEN_CARROT,
      Items.PUMPKIN_PIE,
      Items.RABBIT,
      Items.COOKED_RABBIT,
      Items.RABBIT_STEW,
      Items.MUTTON,
      Items.COOKED_MUTTON,
      Items.CHORUS_FRUIT,
      Items.BEETROOT,
      Items.BEETROOT_SOUP,
      Items.DRIED_KELP,
      Items.SUSPICIOUS_STEW,
      Items.SWEET_BERRIES,
      Items.HONEY_BOTTLE
   };

   public HusbandryTabAdvancementGenerator() {
   }

   public void accept(Consumer<Advancement> _snowman) {
      Advancement _snowmanx = Advancement.Task.create()
         .display(
            Blocks.HAY_BLOCK,
            new TranslatableText("advancements.husbandry.root.title"),
            new TranslatableText("advancements.husbandry.root.description"),
            new Identifier("textures/gui/advancements/backgrounds/husbandry.png"),
            AdvancementFrame.TASK,
            false,
            false,
            false
         )
         .criterion("consumed_item", ConsumeItemCriterion.Conditions.any())
         .build(_snowman, "husbandry/root");
      Advancement _snowmanxx = Advancement.Task.create()
         .parent(_snowmanx)
         .display(
            Items.WHEAT,
            new TranslatableText("advancements.husbandry.plant_seed.title"),
            new TranslatableText("advancements.husbandry.plant_seed.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criteriaMerger(CriterionMerger.OR)
         .criterion("wheat", PlacedBlockCriterion.Conditions.block(Blocks.WHEAT))
         .criterion("pumpkin_stem", PlacedBlockCriterion.Conditions.block(Blocks.PUMPKIN_STEM))
         .criterion("melon_stem", PlacedBlockCriterion.Conditions.block(Blocks.MELON_STEM))
         .criterion("beetroots", PlacedBlockCriterion.Conditions.block(Blocks.BEETROOTS))
         .criterion("nether_wart", PlacedBlockCriterion.Conditions.block(Blocks.NETHER_WART))
         .build(_snowman, "husbandry/plant_seed");
      Advancement _snowmanxxx = Advancement.Task.create()
         .parent(_snowmanx)
         .display(
            Items.WHEAT,
            new TranslatableText("advancements.husbandry.breed_an_animal.title"),
            new TranslatableText("advancements.husbandry.breed_an_animal.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criteriaMerger(CriterionMerger.OR)
         .criterion("bred", BredAnimalsCriterion.Conditions.any())
         .build(_snowman, "husbandry/breed_an_animal");
      this.requireFoodItemsEaten(Advancement.Task.create())
         .parent(_snowmanxx)
         .display(
            Items.APPLE,
            new TranslatableText("advancements.husbandry.balanced_diet.title"),
            new TranslatableText("advancements.husbandry.balanced_diet.description"),
            null,
            AdvancementFrame.CHALLENGE,
            true,
            true,
            false
         )
         .rewards(AdvancementRewards.Builder.experience(100))
         .build(_snowman, "husbandry/balanced_diet");
      Advancement.Task.create()
         .parent(_snowmanxx)
         .display(
            Items.NETHERITE_HOE,
            new TranslatableText("advancements.husbandry.netherite_hoe.title"),
            new TranslatableText("advancements.husbandry.netherite_hoe.description"),
            null,
            AdvancementFrame.CHALLENGE,
            true,
            true,
            false
         )
         .rewards(AdvancementRewards.Builder.experience(100))
         .criterion("netherite_hoe", InventoryChangedCriterion.Conditions.items(Items.NETHERITE_HOE))
         .build(_snowman, "husbandry/obtain_netherite_hoe");
      Advancement _snowmanxxxx = Advancement.Task.create()
         .parent(_snowmanx)
         .display(
            Items.LEAD,
            new TranslatableText("advancements.husbandry.tame_an_animal.title"),
            new TranslatableText("advancements.husbandry.tame_an_animal.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .criterion("tamed_animal", TameAnimalCriterion.Conditions.any())
         .build(_snowman, "husbandry/tame_an_animal");
      this.requireListedAnimalsBred(Advancement.Task.create())
         .parent(_snowmanxxx)
         .display(
            Items.GOLDEN_CARROT,
            new TranslatableText("advancements.husbandry.breed_all_animals.title"),
            new TranslatableText("advancements.husbandry.breed_all_animals.description"),
            null,
            AdvancementFrame.CHALLENGE,
            true,
            true,
            false
         )
         .rewards(AdvancementRewards.Builder.experience(100))
         .build(_snowman, "husbandry/bred_all_animals");
      Advancement _snowmanxxxxx = this.requireListedFishCaught(Advancement.Task.create())
         .parent(_snowmanx)
         .criteriaMerger(CriterionMerger.OR)
         .display(
            Items.FISHING_ROD,
            new TranslatableText("advancements.husbandry.fishy_business.title"),
            new TranslatableText("advancements.husbandry.fishy_business.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .build(_snowman, "husbandry/fishy_business");
      this.requireListedFishBucketsFilled(Advancement.Task.create())
         .parent(_snowmanxxxxx)
         .criteriaMerger(CriterionMerger.OR)
         .display(
            Items.PUFFERFISH_BUCKET,
            new TranslatableText("advancements.husbandry.tactical_fishing.title"),
            new TranslatableText("advancements.husbandry.tactical_fishing.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .build(_snowman, "husbandry/tactical_fishing");
      this.requireAllCatsTamed(Advancement.Task.create())
         .parent(_snowmanxxxx)
         .display(
            Items.COD,
            new TranslatableText("advancements.husbandry.complete_catalogue.title"),
            new TranslatableText("advancements.husbandry.complete_catalogue.description"),
            null,
            AdvancementFrame.CHALLENGE,
            true,
            true,
            false
         )
         .rewards(AdvancementRewards.Builder.experience(50))
         .build(_snowman, "husbandry/complete_catalogue");
      Advancement.Task.create()
         .parent(_snowmanx)
         .criterion(
            "safely_harvest_honey",
            ItemUsedOnBlockCriterion.Conditions.create(
               LocationPredicate.Builder.create().block(BlockPredicate.Builder.create().method_29233(BlockTags.BEEHIVES).build()).smokey(true),
               ItemPredicate.Builder.create().item(Items.GLASS_BOTTLE)
            )
         )
         .display(
            Items.HONEY_BOTTLE,
            new TranslatableText("advancements.husbandry.safely_harvest_honey.title"),
            new TranslatableText("advancements.husbandry.safely_harvest_honey.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .build(_snowman, "husbandry/safely_harvest_honey");
      Advancement.Task.create()
         .parent(_snowmanx)
         .criterion(
            "silk_touch_nest",
            BeeNestDestroyedCriterion.Conditions.create(
               Blocks.BEE_NEST,
               ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))),
               NumberRange.IntRange.exactly(3)
            )
         )
         .display(
            Blocks.BEE_NEST,
            new TranslatableText("advancements.husbandry.silk_touch_nest.title"),
            new TranslatableText("advancements.husbandry.silk_touch_nest.description"),
            null,
            AdvancementFrame.TASK,
            true,
            true,
            false
         )
         .build(_snowman, "husbandry/silk_touch_nest");
   }

   private Advancement.Task requireFoodItemsEaten(Advancement.Task _snowman) {
      for (Item _snowmanx : FOOD_ITEMS) {
         _snowman.criterion(Registry.ITEM.getId(_snowmanx).getPath(), ConsumeItemCriterion.Conditions.item(_snowmanx));
      }

      return _snowman;
   }

   private Advancement.Task requireListedAnimalsBred(Advancement.Task _snowman) {
      for (EntityType<?> _snowmanx : BREEDABLE_ANIMALS) {
         _snowman.criterion(EntityType.getId(_snowmanx).toString(), BredAnimalsCriterion.Conditions.create(EntityPredicate.Builder.create().type(_snowmanx)));
      }

      _snowman.criterion(
         EntityType.getId(EntityType.TURTLE).toString(),
         BredAnimalsCriterion.Conditions.method_29918(
            EntityPredicate.Builder.create().type(EntityType.TURTLE).build(),
            EntityPredicate.Builder.create().type(EntityType.TURTLE).build(),
            EntityPredicate.ANY
         )
      );
      return _snowman;
   }

   private Advancement.Task requireListedFishBucketsFilled(Advancement.Task _snowman) {
      for (Item _snowmanx : FISH_BUCKET_ITEMS) {
         _snowman.criterion(Registry.ITEM.getId(_snowmanx).getPath(), FilledBucketCriterion.Conditions.create(ItemPredicate.Builder.create().item(_snowmanx).build()));
      }

      return _snowman;
   }

   private Advancement.Task requireListedFishCaught(Advancement.Task _snowman) {
      for (Item _snowmanx : FISH_ITEMS) {
         _snowman.criterion(
            Registry.ITEM.getId(_snowmanx).getPath(),
            FishingRodHookedCriterion.Conditions.create(ItemPredicate.ANY, EntityPredicate.ANY, ItemPredicate.Builder.create().item(_snowmanx).build())
         );
      }

      return _snowman;
   }

   private Advancement.Task requireAllCatsTamed(Advancement.Task _snowman) {
      CatEntity.TEXTURES
         .forEach((_snowmanxxx, _snowmanxx) -> _snowman.criterion(_snowmanxx.getPath(), TameAnimalCriterion.Conditions.create(EntityPredicate.Builder.create().type(_snowmanxx).build())));
      return _snowman;
   }
}
