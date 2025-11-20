package net.minecraft.datafixer;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import net.minecraft.SharedConstants;
import net.minecraft.datafixer.fix.AddTrappedChestFix;
import net.minecraft.datafixer.fix.AdvancementRenameFix;
import net.minecraft.datafixer.fix.AdvancementsFix;
import net.minecraft.datafixer.fix.BedBlockEntityFix;
import net.minecraft.datafixer.fix.BedItemColorFix;
import net.minecraft.datafixer.fix.BeehiveRenameFix;
import net.minecraft.datafixer.fix.BiomeFormatFix;
import net.minecraft.datafixer.fix.BiomeRenameFix;
import net.minecraft.datafixer.fix.BiomesFix;
import net.minecraft.datafixer.fix.BitStorageAlignFix;
import net.minecraft.datafixer.fix.BlockEntityBannerColorFix;
import net.minecraft.datafixer.fix.BlockEntityBlockStateFix;
import net.minecraft.datafixer.fix.BlockEntityCustomNameToTextFix;
import net.minecraft.datafixer.fix.BlockEntityIdFix;
import net.minecraft.datafixer.fix.BlockEntityJukeboxFix;
import net.minecraft.datafixer.fix.BlockEntityKeepPacked;
import net.minecraft.datafixer.fix.BlockEntityShulkerBoxColorFix;
import net.minecraft.datafixer.fix.BlockEntitySignTextStrictJsonFix;
import net.minecraft.datafixer.fix.BlockEntityUuidFix;
import net.minecraft.datafixer.fix.BlockNameFix;
import net.minecraft.datafixer.fix.BlockNameFlatteningFix;
import net.minecraft.datafixer.fix.BlockStateStructureTemplateFix;
import net.minecraft.datafixer.fix.CatTypeFix;
import net.minecraft.datafixer.fix.ChoiceFix;
import net.minecraft.datafixer.fix.ChoiceTypesFix;
import net.minecraft.datafixer.fix.ChunkLightRemoveFix;
import net.minecraft.datafixer.fix.ChunkPalettedStorageFix;
import net.minecraft.datafixer.fix.ChunkStatusFix;
import net.minecraft.datafixer.fix.ChunkStatusFix2;
import net.minecraft.datafixer.fix.ChunkStructuresTemplateRenameFix;
import net.minecraft.datafixer.fix.ChunkToProtoChunkFix;
import net.minecraft.datafixer.fix.ColorlessShulkerEntityFix;
import net.minecraft.datafixer.fix.EntityArmorStandSilentFix;
import net.minecraft.datafixer.fix.EntityBlockStateFix;
import net.minecraft.datafixer.fix.EntityCatSplitFix;
import net.minecraft.datafixer.fix.EntityCodSalmonFix;
import net.minecraft.datafixer.fix.EntityCustomNameToTextFix;
import net.minecraft.datafixer.fix.EntityElderGuardianSplitFix;
import net.minecraft.datafixer.fix.EntityEquipmentToArmorAndHandFix;
import net.minecraft.datafixer.fix.EntityHealthFix;
import net.minecraft.datafixer.fix.EntityHorseSaddleFix;
import net.minecraft.datafixer.fix.EntityHorseSplitFix;
import net.minecraft.datafixer.fix.EntityIdFix;
import net.minecraft.datafixer.fix.EntityItemFrameDirectionFix;
import net.minecraft.datafixer.fix.EntityMinecartIdentifiersFix;
import net.minecraft.datafixer.fix.EntityPaintingMotiveFix;
import net.minecraft.datafixer.fix.EntityProjectileOwnerFix;
import net.minecraft.datafixer.fix.EntityPufferfishRenameFix;
import net.minecraft.datafixer.fix.EntityRavagerRenameFix;
import net.minecraft.datafixer.fix.EntityRedundantChanceTagsFix;
import net.minecraft.datafixer.fix.EntityRidingToPassengerFix;
import net.minecraft.datafixer.fix.EntityShulkerColorFix;
import net.minecraft.datafixer.fix.EntityShulkerRotationFix;
import net.minecraft.datafixer.fix.EntitySkeletonSplitFix;
import net.minecraft.datafixer.fix.EntityStringUuidFix;
import net.minecraft.datafixer.fix.EntityTheRenameningBlock;
import net.minecraft.datafixer.fix.EntityTippedArrowFix;
import net.minecraft.datafixer.fix.EntityUuidFix;
import net.minecraft.datafixer.fix.EntityWolfColorFix;
import net.minecraft.datafixer.fix.EntityZombieSplitFix;
import net.minecraft.datafixer.fix.EntityZombieVillagerTypeFix;
import net.minecraft.datafixer.fix.EntityZombifiedPiglinRenameFix;
import net.minecraft.datafixer.fix.FurnaceRecipesFix;
import net.minecraft.datafixer.fix.HangingEntityFix;
import net.minecraft.datafixer.fix.HeightmapRenamingFix;
import net.minecraft.datafixer.fix.IglooMetadataRemovalFix;
import net.minecraft.datafixer.fix.ItemBannerColorFix;
import net.minecraft.datafixer.fix.ItemCustomNameToComponentFix;
import net.minecraft.datafixer.fix.ItemIdFix;
import net.minecraft.datafixer.fix.ItemInstanceMapIdFix;
import net.minecraft.datafixer.fix.ItemInstanceSpawnEggFix;
import net.minecraft.datafixer.fix.ItemInstanceTheFlatteningFix;
import net.minecraft.datafixer.fix.ItemLoreToTextFix;
import net.minecraft.datafixer.fix.ItemNameFix;
import net.minecraft.datafixer.fix.ItemPotionFix;
import net.minecraft.datafixer.fix.ItemShulkerBoxColorFix;
import net.minecraft.datafixer.fix.ItemSpawnEggFix;
import net.minecraft.datafixer.fix.ItemStackEnchantmentFix;
import net.minecraft.datafixer.fix.ItemStackUuidFix;
import net.minecraft.datafixer.fix.ItemWaterPotionFix;
import net.minecraft.datafixer.fix.ItemWrittenBookPagesStrictJsonFix;
import net.minecraft.datafixer.fix.JigsawPropertiesFix;
import net.minecraft.datafixer.fix.JigsawRotationFix;
import net.minecraft.datafixer.fix.LeavesFix;
import net.minecraft.datafixer.fix.LevelDataGeneratorOptionsFix;
import net.minecraft.datafixer.fix.LevelFlatGeneratorInfoFix;
import net.minecraft.datafixer.fix.MapIdFix;
import net.minecraft.datafixer.fix.MemoryExpiryDataFix;
import net.minecraft.datafixer.fix.MissingDimensionFix;
import net.minecraft.datafixer.fix.MobSpawnerEntityIdentifiersFix;
import net.minecraft.datafixer.fix.NewVillageFix;
import net.minecraft.datafixer.fix.ObjectiveDisplayNameFix;
import net.minecraft.datafixer.fix.ObjectiveRenderTypeFix;
import net.minecraft.datafixer.fix.OminousBannerBlockEntityRenameFix;
import net.minecraft.datafixer.fix.OminousBannerItemRenameFix;
import net.minecraft.datafixer.fix.OptionFix;
import net.minecraft.datafixer.fix.OptionsAddTextBackgroundFix;
import net.minecraft.datafixer.fix.OptionsForceVBOFix;
import net.minecraft.datafixer.fix.OptionsKeyLwjgl3Fix;
import net.minecraft.datafixer.fix.OptionsKeyTranslationFix;
import net.minecraft.datafixer.fix.OptionsLowerCaseLanguageFix;
import net.minecraft.datafixer.fix.PersistentStateUuidFix;
import net.minecraft.datafixer.fix.PlayerUuidFix;
import net.minecraft.datafixer.fix.PointOfInterestReorganizationFix;
import net.minecraft.datafixer.fix.RecipeFix;
import net.minecraft.datafixer.fix.RecipeRenameFix;
import net.minecraft.datafixer.fix.RecipeRenamingFix;
import net.minecraft.datafixer.fix.RedstoneConnectionsFix;
import net.minecraft.datafixer.fix.RemoveGolemGossipFix;
import net.minecraft.datafixer.fix.RemovePoiValidTagFix;
import net.minecraft.datafixer.fix.RenameItemStackAttributesFix;
import net.minecraft.datafixer.fix.SavedDataVillageCropFix;
import net.minecraft.datafixer.fix.StatsCounterFix;
import net.minecraft.datafixer.fix.StriderGravityFix;
import net.minecraft.datafixer.fix.StructureReferenceFix;
import net.minecraft.datafixer.fix.StructureSeparationDataFix;
import net.minecraft.datafixer.fix.SwimStatsRenameFix;
import net.minecraft.datafixer.fix.TeamDisplayNameFix;
import net.minecraft.datafixer.fix.VillagerFollowRangeFix;
import net.minecraft.datafixer.fix.VillagerGossipFix;
import net.minecraft.datafixer.fix.VillagerProfessionFix;
import net.minecraft.datafixer.fix.VillagerTradeFix;
import net.minecraft.datafixer.fix.VillagerXpRebuildFix;
import net.minecraft.datafixer.fix.WallPropertyFix;
import net.minecraft.datafixer.fix.WorldUuidFix;
import net.minecraft.datafixer.fix.WriteAndReadFix;
import net.minecraft.datafixer.fix.ZombieVillagerXpRebuildFix;
import net.minecraft.datafixer.mapping.LegacyCoralBlockMapping;
import net.minecraft.datafixer.mapping.LegacyCoralFanBlockMapping;
import net.minecraft.datafixer.mapping.LegacyDyeItemMapping;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;
import net.minecraft.datafixer.schema.Schema100;
import net.minecraft.datafixer.schema.Schema102;
import net.minecraft.datafixer.schema.Schema1022;
import net.minecraft.datafixer.schema.Schema106;
import net.minecraft.datafixer.schema.Schema107;
import net.minecraft.datafixer.schema.Schema1125;
import net.minecraft.datafixer.schema.Schema135;
import net.minecraft.datafixer.schema.Schema143;
import net.minecraft.datafixer.schema.Schema1451;
import net.minecraft.datafixer.schema.Schema1451v1;
import net.minecraft.datafixer.schema.Schema1451v2;
import net.minecraft.datafixer.schema.Schema1451v3;
import net.minecraft.datafixer.schema.Schema1451v4;
import net.minecraft.datafixer.schema.Schema1451v5;
import net.minecraft.datafixer.schema.Schema1451v6;
import net.minecraft.datafixer.schema.Schema1451v7;
import net.minecraft.datafixer.schema.Schema1460;
import net.minecraft.datafixer.schema.Schema1466;
import net.minecraft.datafixer.schema.Schema1470;
import net.minecraft.datafixer.schema.Schema1481;
import net.minecraft.datafixer.schema.Schema1483;
import net.minecraft.datafixer.schema.Schema1486;
import net.minecraft.datafixer.schema.Schema1510;
import net.minecraft.datafixer.schema.Schema1800;
import net.minecraft.datafixer.schema.Schema1801;
import net.minecraft.datafixer.schema.Schema1904;
import net.minecraft.datafixer.schema.Schema1906;
import net.minecraft.datafixer.schema.Schema1909;
import net.minecraft.datafixer.schema.Schema1920;
import net.minecraft.datafixer.schema.Schema1928;
import net.minecraft.datafixer.schema.Schema1929;
import net.minecraft.datafixer.schema.Schema1931;
import net.minecraft.datafixer.schema.Schema2100;
import net.minecraft.datafixer.schema.Schema2501;
import net.minecraft.datafixer.schema.Schema2502;
import net.minecraft.datafixer.schema.Schema2505;
import net.minecraft.datafixer.schema.Schema2509;
import net.minecraft.datafixer.schema.Schema2519;
import net.minecraft.datafixer.schema.Schema2522;
import net.minecraft.datafixer.schema.Schema2551;
import net.minecraft.datafixer.schema.Schema2568;
import net.minecraft.datafixer.schema.Schema501;
import net.minecraft.datafixer.schema.Schema700;
import net.minecraft.datafixer.schema.Schema701;
import net.minecraft.datafixer.schema.Schema702;
import net.minecraft.datafixer.schema.Schema703;
import net.minecraft.datafixer.schema.Schema704;
import net.minecraft.datafixer.schema.Schema705;
import net.minecraft.datafixer.schema.Schema808;
import net.minecraft.datafixer.schema.Schema99;
import net.minecraft.util.Util;

public class Schemas {
   private static final BiFunction<Integer, Schema, Schema> EMPTY = Schema::new;
   private static final BiFunction<Integer, Schema, Schema> EMPTY_IDENTIFIER_NORMALIZE = IdentifierNormalizingSchema::new;
   private static final DataFixer FIXER = create();

   private static DataFixer create() {
      DataFixerBuilder _snowman = new DataFixerBuilder(SharedConstants.getGameVersion().getWorldVersion());
      build(_snowman);
      return _snowman.build(Util.getBootstrapExecutor());
   }

   public static DataFixer getFixer() {
      return FIXER;
   }

   private static void build(DataFixerBuilder builder) {
      Schema _snowman = builder.addSchema(99, Schema99::new);
      Schema _snowmanx = builder.addSchema(100, Schema100::new);
      builder.addFixer(new EntityEquipmentToArmorAndHandFix(_snowmanx, true));
      Schema _snowmanxx = builder.addSchema(101, EMPTY);
      builder.addFixer(new BlockEntitySignTextStrictJsonFix(_snowmanxx, false));
      Schema _snowmanxxx = builder.addSchema(102, Schema102::new);
      builder.addFixer(new ItemIdFix(_snowmanxxx, true));
      builder.addFixer(new ItemPotionFix(_snowmanxxx, false));
      Schema _snowmanxxxx = builder.addSchema(105, EMPTY);
      builder.addFixer(new ItemSpawnEggFix(_snowmanxxxx, true));
      Schema _snowmanxxxxx = builder.addSchema(106, Schema106::new);
      builder.addFixer(new MobSpawnerEntityIdentifiersFix(_snowmanxxxxx, true));
      Schema _snowmanxxxxxx = builder.addSchema(107, Schema107::new);
      builder.addFixer(new EntityMinecartIdentifiersFix(_snowmanxxxxxx, true));
      Schema _snowmanxxxxxxx = builder.addSchema(108, EMPTY);
      builder.addFixer(new EntityStringUuidFix(_snowmanxxxxxxx, true));
      Schema _snowmanxxxxxxxx = builder.addSchema(109, EMPTY);
      builder.addFixer(new EntityHealthFix(_snowmanxxxxxxxx, true));
      Schema _snowmanxxxxxxxxx = builder.addSchema(110, EMPTY);
      builder.addFixer(new EntityHorseSaddleFix(_snowmanxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxx = builder.addSchema(111, EMPTY);
      builder.addFixer(new HangingEntityFix(_snowmanxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxx = builder.addSchema(113, EMPTY);
      builder.addFixer(new EntityRedundantChanceTagsFix(_snowmanxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxx = builder.addSchema(135, Schema135::new);
      builder.addFixer(new EntityRidingToPassengerFix(_snowmanxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxx = builder.addSchema(143, Schema143::new);
      builder.addFixer(new EntityTippedArrowFix(_snowmanxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxx = builder.addSchema(147, EMPTY);
      builder.addFixer(new EntityArmorStandSilentFix(_snowmanxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxx = builder.addSchema(165, EMPTY);
      builder.addFixer(new ItemWrittenBookPagesStrictJsonFix(_snowmanxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxx = builder.addSchema(501, Schema501::new);
      builder.addFixer(new ChoiceTypesFix(_snowmanxxxxxxxxxxxxxxxx, "Add 1.10 entities fix", TypeReferences.ENTITY));
      Schema _snowmanxxxxxxxxxxxxxxxxx = builder.addSchema(502, EMPTY);
      builder.addFixer(
         ItemNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxx,
            "cooked_fished item renamer",
            _snowmanxxxxxxxxxxxxxxxxxx -> Objects.equals(IdentifierNormalizingSchema.normalize(_snowmanxxxxxxxxxxxxxxxxxx), "minecraft:cooked_fished")
                  ? "minecraft:cooked_fish"
                  : _snowmanxxxxxxxxxxxxxxxxxx
         )
      );
      builder.addFixer(new EntityZombieVillagerTypeFix(_snowmanxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxx = builder.addSchema(505, EMPTY);
      builder.addFixer(new OptionsForceVBOFix(_snowmanxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxx = builder.addSchema(700, Schema700::new);
      builder.addFixer(new EntityElderGuardianSplitFix(_snowmanxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxx = builder.addSchema(701, Schema701::new);
      builder.addFixer(new EntitySkeletonSplitFix(_snowmanxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(702, Schema702::new);
      builder.addFixer(new EntityZombieSplitFix(_snowmanxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(703, Schema703::new);
      builder.addFixer(new EntityHorseSplitFix(_snowmanxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(704, Schema704::new);
      builder.addFixer(new BlockEntityIdFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(705, Schema705::new);
      builder.addFixer(new EntityIdFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(804, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new ItemBannerColorFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(806, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new ItemWaterPotionFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(808, Schema808::new);
      builder.addFixer(new ChoiceTypesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, "added shulker box", TypeReferences.BLOCK_ENTITY));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(808, 1, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new EntityShulkerColorFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(813, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new ItemShulkerBoxColorFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      builder.addFixer(new BlockEntityShulkerBoxColorFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(816, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new OptionsLowerCaseLanguageFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(820, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(
         ItemNameFix.create(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "totem item renamer", method_30068("minecraft:totem", "minecraft:totem_of_undying"))
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1022, Schema1022::new);
      builder.addFixer(new WriteAndReadFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "added shoulder entities to players", TypeReferences.PLAYER));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1125, Schema1125::new);
      builder.addFixer(new BedBlockEntityFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      builder.addFixer(new BedItemColorFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1344, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new OptionsKeyLwjgl3Fix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1446, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new OptionsKeyTranslationFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1450, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new BlockStateStructureTemplateFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1451, Schema1451::new);
      builder.addFixer(new ChoiceTypesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "AddTrappedChestFix", TypeReferences.BLOCK_ENTITY));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1451, 1, Schema1451v1::new);
      builder.addFixer(new ChunkPalettedStorageFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1451, 2, Schema1451v2::new);
      builder.addFixer(new BlockEntityBlockStateFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1451, 3, Schema1451v3::new);
      builder.addFixer(new EntityBlockStateFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      builder.addFixer(new ItemInstanceMapIdFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1451, 4, Schema1451v4::new);
      builder.addFixer(new BlockNameFlatteningFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      builder.addFixer(new ItemInstanceTheFlatteningFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1451, 5, Schema1451v5::new);
      builder.addFixer(new ChoiceTypesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "RemoveNoteBlockFlowerPotFix", TypeReferences.BLOCK_ENTITY));
      builder.addFixer(new ItemInstanceSpawnEggFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      builder.addFixer(new EntityWolfColorFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      builder.addFixer(new BlockEntityBannerColorFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      builder.addFixer(new LevelFlatGeneratorInfoFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1451, 6, Schema1451v6::new);
      builder.addFixer(new StatsCounterFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      builder.addFixer(new BlockEntityJukeboxFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1451, 7, Schema1451v7::new);
      builder.addFixer(new SavedDataVillageCropFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1451, 7, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new VillagerTradeFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1456, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new EntityItemFrameDirectionFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1458, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new EntityCustomNameToTextFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      builder.addFixer(new ItemCustomNameToComponentFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      builder.addFixer(new BlockEntityCustomNameToTextFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1460, Schema1460::new);
      builder.addFixer(new EntityPaintingMotiveFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1466, Schema1466::new);
      builder.addFixer(new ChunkToProtoChunkFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1470, Schema1470::new);
      builder.addFixer(new ChoiceTypesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add 1.13 entities fix", TypeReferences.ENTITY));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1474, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new ColorlessShulkerEntityFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      builder.addFixer(
         BlockNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Colorless shulker block fixer",
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -> Objects.equals(
                     IdentifierNormalizingSchema.normalize(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx), "minecraft:purple_shulker_box"
                  )
                  ? "minecraft:shulker_box"
                  : _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         )
      );
      builder.addFixer(
         ItemNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Colorless shulker item fixer",
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -> Objects.equals(
                     IdentifierNormalizingSchema.normalize(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx), "minecraft:purple_shulker_box"
                  )
                  ? "minecraft:shulker_box"
                  : _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1475, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(
         BlockNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Flowing fixer",
            method_30070(ImmutableMap.of("minecraft:flowing_water", "minecraft:water", "minecraft:flowing_lava", "minecraft:lava"))
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1480, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(
         BlockNameFix.create(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename coral blocks", method_30070(LegacyCoralBlockMapping.MAP))
      );
      builder.addFixer(
         ItemNameFix.create(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename coral items", method_30070(LegacyCoralBlockMapping.MAP))
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1481, Schema1481::new);
      builder.addFixer(new ChoiceTypesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add conduit", TypeReferences.BLOCK_ENTITY));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1483, Schema1483::new);
      builder.addFixer(new EntityPufferfishRenameFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      builder.addFixer(
         ItemNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename pufferfish egg item", method_30070(EntityPufferfishRenameFix.RENAMED_FISH)
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1484, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(
         ItemNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename seagrass items",
            method_30070(ImmutableMap.of("minecraft:sea_grass", "minecraft:seagrass", "minecraft:tall_sea_grass", "minecraft:tall_seagrass"))
         )
      );
      builder.addFixer(
         BlockNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename seagrass blocks",
            method_30070(ImmutableMap.of("minecraft:sea_grass", "minecraft:seagrass", "minecraft:tall_sea_grass", "minecraft:tall_seagrass"))
         )
      );
      builder.addFixer(new HeightmapRenamingFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1486, Schema1486::new);
      builder.addFixer(new EntityCodSalmonFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      builder.addFixer(
         ItemNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename cod/salmon egg items", method_30070(EntityCodSalmonFix.SPAWN_EGGS)
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1487, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(
         ItemNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename prismarine_brick(s)_* blocks",
            method_30070(
               ImmutableMap.of(
                  "minecraft:prismarine_bricks_slab",
                  "minecraft:prismarine_brick_slab",
                  "minecraft:prismarine_bricks_stairs",
                  "minecraft:prismarine_brick_stairs"
               )
            )
         )
      );
      builder.addFixer(
         BlockNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename prismarine_brick(s)_* items",
            method_30070(
               ImmutableMap.of(
                  "minecraft:prismarine_bricks_slab",
                  "minecraft:prismarine_brick_slab",
                  "minecraft:prismarine_bricks_stairs",
                  "minecraft:prismarine_brick_stairs"
               )
            )
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1488, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(
         BlockNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename kelp/kelptop",
            method_30070(ImmutableMap.of("minecraft:kelp_top", "minecraft:kelp", "minecraft:kelp", "minecraft:kelp_plant"))
         )
      );
      builder.addFixer(
         ItemNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename kelptop", method_30068("minecraft:kelp_top", "minecraft:kelp")
         )
      );
      builder.addFixer(
         new ChoiceFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Command block block entity custom name fix",
            TypeReferences.BLOCK_ENTITY,
            "minecraft:command_block"
         ) {
            @Override
            protected Typed<?> transform(Typed<?> inputType) {
               return inputType.update(DSL.remainderFinder(), EntityCustomNameToTextFix::fixCustomName);
            }
         }
      );
      builder.addFixer(
         new ChoiceFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Command block minecart custom name fix",
            TypeReferences.ENTITY,
            "minecraft:commandblock_minecart"
         ) {
            @Override
            protected Typed<?> transform(Typed<?> inputType) {
               return inputType.update(DSL.remainderFinder(), EntityCustomNameToTextFix::fixCustomName);
            }
         }
      );
      builder.addFixer(new IglooMetadataRemovalFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1490, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(
         BlockNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename melon_block", method_30068("minecraft:melon_block", "minecraft:melon")
         )
      );
      builder.addFixer(
         ItemNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename melon_block/melon/speckled_melon",
            method_30070(
               ImmutableMap.of(
                  "minecraft:melon_block",
                  "minecraft:melon",
                  "minecraft:melon",
                  "minecraft:melon_slice",
                  "minecraft:speckled_melon",
                  "minecraft:glistering_melon_slice"
               )
            )
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1492, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new ChunkStructuresTemplateRenameFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1494, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new ItemStackEnchantmentFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1496, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new LeavesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1500, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new BlockEntityKeepPacked(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1501, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new AdvancementsFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1502, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new RecipeFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1506, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new LevelDataGeneratorOptionsFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1510, Schema1510::new);
      builder.addFixer(
         BlockNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Block renamening fix", method_30070(EntityTheRenameningBlock.BLOCKS)
         )
      );
      builder.addFixer(
         ItemNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Item renamening fix", method_30070(EntityTheRenameningBlock.ITEMS)
         )
      );
      builder.addFixer(new RecipeRenamingFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      builder.addFixer(new EntityTheRenameningBlock(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      builder.addFixer(new SwimStatsRenameFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1514, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new ObjectiveDisplayNameFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      builder.addFixer(new TeamDisplayNameFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      builder.addFixer(new ObjectiveRenderTypeFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1515, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(
         BlockNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename coral fan blocks", method_30070(LegacyCoralFanBlockMapping.MAP)
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1624, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new AddTrappedChestFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1800, Schema1800::new);
      builder.addFixer(
         new ChoiceTypesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added 1.14 mobs fix", TypeReferences.ENTITY)
      );
      builder.addFixer(
         ItemNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename dye items", method_30070(LegacyDyeItemMapping.MAP)
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1801, Schema1801::new);
      builder.addFixer(
         new ChoiceTypesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Illager Beast", TypeReferences.ENTITY)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1802, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(
         BlockNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename sign blocks & stone slabs",
            method_30070(
               ImmutableMap.of(
                  "minecraft:stone_slab",
                  "minecraft:smooth_stone_slab",
                  "minecraft:sign",
                  "minecraft:oak_sign",
                  "minecraft:wall_sign",
                  "minecraft:oak_wall_sign"
               )
            )
         )
      );
      builder.addFixer(
         ItemNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename sign item & stone slabs",
            method_30070(ImmutableMap.of("minecraft:stone_slab", "minecraft:smooth_stone_slab", "minecraft:sign", "minecraft:oak_sign"))
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1803, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new ItemLoreToTextFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1904, Schema1904::new);
      builder.addFixer(new ChoiceTypesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Cats", TypeReferences.ENTITY));
      builder.addFixer(new EntityCatSplitFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1905, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new ChunkStatusFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1906, Schema1906::new);
      builder.addFixer(
         new ChoiceTypesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add POI Blocks", TypeReferences.BLOCK_ENTITY)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1909, Schema1909::new);
      builder.addFixer(
         new ChoiceTypesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add jigsaw", TypeReferences.BLOCK_ENTITY)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1911, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new ChunkStatusFix2(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1917, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new CatTypeFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1918, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new VillagerProfessionFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "minecraft:villager"));
      builder.addFixer(
         new VillagerProfessionFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "minecraft:zombie_villager")
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1920, Schema1920::new);
      builder.addFixer(new NewVillageFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      builder.addFixer(
         new ChoiceTypesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add campfire", TypeReferences.BLOCK_ENTITY)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1925, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new MapIdFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1928, Schema1928::new);
      builder.addFixer(new EntityRavagerRenameFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      builder.addFixer(
         ItemNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename ravager egg item",
            method_30070(EntityRavagerRenameFix.ITEMS)
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1929, Schema1929::new);
      builder.addFixer(
         new ChoiceTypesFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Add Wandering Trader and Trader Llama",
            TypeReferences.ENTITY
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1931, Schema1931::new);
      builder.addFixer(
         new ChoiceTypesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Fox", TypeReferences.ENTITY)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1936, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new OptionsAddTextBackgroundFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1946, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new PointOfInterestReorganizationFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1948, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new OminousBannerItemRenameFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1953, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(
         new OminousBannerBlockEntityRenameFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(1955, EMPTY_IDENTIFIER_NORMALIZE);
      builder.addFixer(new VillagerXpRebuildFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      builder.addFixer(new ZombieVillagerXpRebuildFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         1961, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(new ChunkLightRemoveFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         1963, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(new RemoveGolemGossipFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(2100, Schema2100::new);
      builder.addFixer(
         new ChoiceTypesFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Added Bee and Bee Stinger",
            TypeReferences.ENTITY
         )
      );
      builder.addFixer(
         new ChoiceTypesFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add beehive", TypeReferences.BLOCK_ENTITY
         )
      );
      builder.addFixer(
         new RecipeRenameFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Rename sugar recipe",
            method_30068("minecraft:sugar", "sugar_from_sugar_cane")
         )
      );
      builder.addFixer(
         new AdvancementRenameFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Rename sugar recipe advancement",
            method_30068("minecraft:recipes/misc/sugar", "minecraft:recipes/misc/sugar_from_sugar_cane")
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2202, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(new BiomeFormatFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2209, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(
         ItemNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename bee_hive item to beehive",
            method_30068("minecraft:bee_hive", "minecraft:beehive")
         )
      );
      builder.addFixer(new BeehiveRenameFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      builder.addFixer(
         BlockNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename bee_hive block to beehive",
            method_30068("minecraft:bee_hive", "minecraft:beehive")
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2211, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(new StructureReferenceFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2218, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(new RemovePoiValidTagFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(2501, Schema2501::new);
      builder.addFixer(new FurnaceRecipesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(2502, Schema2502::new);
      builder.addFixer(
         new ChoiceTypesFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Hoglin", TypeReferences.ENTITY
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2503, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(new WallPropertyFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      builder.addFixer(
         new AdvancementRenameFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Composter category change",
            method_30068("minecraft:recipes/misc/composter", "minecraft:recipes/decorations/composter")
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(2505, Schema2505::new);
      builder.addFixer(
         new ChoiceTypesFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Piglin", TypeReferences.ENTITY
         )
      );
      builder.addFixer(
         new MemoryExpiryDataFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "minecraft:villager")
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2508, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(
         ItemNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Renamed fungi items to fungus",
            method_30070(ImmutableMap.of("minecraft:warped_fungi", "minecraft:warped_fungus", "minecraft:crimson_fungi", "minecraft:crimson_fungus"))
         )
      );
      builder.addFixer(
         BlockNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Renamed fungi blocks to fungus",
            method_30070(ImmutableMap.of("minecraft:warped_fungi", "minecraft:warped_fungus", "minecraft:crimson_fungi", "minecraft:crimson_fungus"))
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2509, Schema2509::new
      );
      builder.addFixer(
         new EntityZombifiedPiglinRenameFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      builder.addFixer(
         ItemNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename zombie pigman egg item",
            method_30070(EntityZombifiedPiglinRenameFix.RENAMES)
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2511, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(
         new EntityProjectileOwnerFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2514, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(new EntityUuidFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      builder.addFixer(new BlockEntityUuidFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      builder.addFixer(new PlayerUuidFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      builder.addFixer(new WorldUuidFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      builder.addFixer(new PersistentStateUuidFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      builder.addFixer(new ItemStackUuidFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2516, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(
         new VillagerGossipFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "minecraft:villager"
         )
      );
      builder.addFixer(
         new VillagerGossipFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "minecraft:zombie_villager"
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2518, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(
         new JigsawPropertiesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false)
      );
      builder.addFixer(
         new JigsawRotationFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2519, Schema2519::new
      );
      builder.addFixer(
         new ChoiceTypesFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Added Strider",
            TypeReferences.ENTITY
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2522, Schema2522::new
      );
      builder.addFixer(
         new ChoiceTypesFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Added Zoglin",
            TypeReferences.ENTITY
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2523, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(
         new RenameItemStackAttributesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2527, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(
         new BitStorageAlignFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2528, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(
         ItemNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename soul fire torch and soul fire lantern",
            method_30070(ImmutableMap.of("minecraft:soul_fire_torch", "minecraft:soul_torch", "minecraft:soul_fire_lantern", "minecraft:soul_lantern"))
         )
      );
      builder.addFixer(
         BlockNameFix.create(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename soul fire torch and soul fire lantern",
            method_30070(
               ImmutableMap.of(
                  "minecraft:soul_fire_torch",
                  "minecraft:soul_torch",
                  "minecraft:soul_fire_wall_torch",
                  "minecraft:soul_wall_torch",
                  "minecraft:soul_fire_lantern",
                  "minecraft:soul_lantern"
               )
            )
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2529, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(
         new StriderGravityFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2531, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(
         new RedstoneConnectionsFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2533, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(
         new VillagerFollowRangeFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2535, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(
         new EntityShulkerRotationFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2550, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(
         new StructureSeparationDataFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2551, Schema2551::new
      );
      builder.addFixer(
         new WriteAndReadFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "add types to WorldGenData",
            TypeReferences.CHUNK_GENERATOR_SETTINGS
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2552, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(
         new BiomeRenameFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Nether biome rename",
            ImmutableMap.of("minecraft:nether", "minecraft:nether_wastes")
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2553, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(
         new BiomesFix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2558, EMPTY_IDENTIFIER_NORMALIZE
      );
      builder.addFixer(
         new MissingDimensionFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false
         )
      );
      builder.addFixer(
         new OptionFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Rename swapHands setting",
            "key_key.swapHands",
            "key_key.swapOffhand"
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = builder.addSchema(
         2568, Schema2568::new
      );
      builder.addFixer(
         new ChoiceTypesFix(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Added Piglin Brute",
            TypeReferences.ENTITY
         )
      );
   }

   private static UnaryOperator<String> method_30070(Map<String, String> _snowman) {
      return _snowmanxx -> _snowman.getOrDefault(_snowmanxx, _snowmanxx);
   }

   private static UnaryOperator<String> method_30068(String _snowman, String _snowman) {
      return _snowmanxxx -> Objects.equals(_snowmanxxx, _snowman) ? _snowman : _snowmanxxx;
   }
}
