package net.minecraft.structure;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.structure.processor.BlackstoneReplacementStructureProcessor;
import net.minecraft.structure.processor.BlockAgeStructureProcessor;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.structure.processor.LavaSubmergedBlockStructureProcessor;
import net.minecraft.structure.processor.RuleStructureProcessor;
import net.minecraft.structure.processor.StructureProcessorRule;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RuinedPortalStructurePiece extends SimpleStructurePiece {
   private static final Logger field_24992 = LogManager.getLogger();
   private final Identifier template;
   private final BlockRotation rotation;
   private final BlockMirror mirror;
   private final RuinedPortalStructurePiece.VerticalPlacement verticalPlacement;
   private final RuinedPortalStructurePiece.Properties properties;

   public RuinedPortalStructurePiece(
      BlockPos pos,
      RuinedPortalStructurePiece.VerticalPlacement verticalPlacement,
      RuinedPortalStructurePiece.Properties properties,
      Identifier template,
      Structure structure,
      BlockRotation rotation,
      BlockMirror mirror,
      BlockPos center
   ) {
      super(StructurePieceType.RUINED_PORTAL, 0);
      this.pos = pos;
      this.template = template;
      this.rotation = rotation;
      this.mirror = mirror;
      this.verticalPlacement = verticalPlacement;
      this.properties = properties;
      this.processProperties(structure, center);
   }

   public RuinedPortalStructurePiece(StructureManager manager, CompoundTag tag) {
      super(StructurePieceType.RUINED_PORTAL, tag);
      this.template = new Identifier(tag.getString("Template"));
      this.rotation = BlockRotation.valueOf(tag.getString("Rotation"));
      this.mirror = BlockMirror.valueOf(tag.getString("Mirror"));
      this.verticalPlacement = RuinedPortalStructurePiece.VerticalPlacement.getFromId(tag.getString("VerticalPlacement"));
      this.properties = (RuinedPortalStructurePiece.Properties)RuinedPortalStructurePiece.Properties.CODEC
         .parse(new Dynamic(NbtOps.INSTANCE, tag.get("Properties")))
         .getOrThrow(true, field_24992::error);
      Structure _snowman = manager.getStructureOrBlank(this.template);
      this.processProperties(_snowman, new BlockPos(_snowman.getSize().getX() / 2, 0, _snowman.getSize().getZ() / 2));
   }

   @Override
   protected void toNbt(CompoundTag tag) {
      super.toNbt(tag);
      tag.putString("Template", this.template.toString());
      tag.putString("Rotation", this.rotation.name());
      tag.putString("Mirror", this.mirror.name());
      tag.putString("VerticalPlacement", this.verticalPlacement.getId());
      RuinedPortalStructurePiece.Properties.CODEC
         .encodeStart(NbtOps.INSTANCE, this.properties)
         .resultOrPartial(field_24992::error)
         .ifPresent(_snowmanx -> tag.put("Properties", _snowmanx));
   }

   private void processProperties(Structure structure, BlockPos center) {
      BlockIgnoreStructureProcessor _snowman = this.properties.airPocket
         ? BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS
         : BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS;
      List<StructureProcessorRule> _snowmanx = Lists.newArrayList();
      _snowmanx.add(createReplacementRule(Blocks.GOLD_BLOCK, 0.3F, Blocks.AIR));
      _snowmanx.add(this.createLavaReplacementRule());
      if (!this.properties.cold) {
         _snowmanx.add(createReplacementRule(Blocks.NETHERRACK, 0.07F, Blocks.MAGMA_BLOCK));
      }

      StructurePlacementData _snowmanxx = new StructurePlacementData()
         .setRotation(this.rotation)
         .setMirror(this.mirror)
         .setPosition(center)
         .addProcessor(_snowman)
         .addProcessor(new RuleStructureProcessor(_snowmanx))
         .addProcessor(new BlockAgeStructureProcessor(this.properties.mossiness))
         .addProcessor(new LavaSubmergedBlockStructureProcessor());
      if (this.properties.replaceWithBlackstone) {
         _snowmanxx.addProcessor(BlackstoneReplacementStructureProcessor.INSTANCE);
      }

      this.setStructureData(structure, this.pos, _snowmanxx);
   }

   private StructureProcessorRule createLavaReplacementRule() {
      if (this.verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.ON_OCEAN_FLOOR) {
         return createReplacementRule(Blocks.LAVA, Blocks.MAGMA_BLOCK);
      } else {
         return this.properties.cold ? createReplacementRule(Blocks.LAVA, Blocks.NETHERRACK) : createReplacementRule(Blocks.LAVA, 0.2F, Blocks.MAGMA_BLOCK);
      }
   }

   @Override
   public boolean generate(
      StructureWorldAccess _snowman, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos _snowman, BlockPos _snowman
   ) {
      if (!boundingBox.contains(this.pos)) {
         return true;
      } else {
         boundingBox.encompass(this.structure.calculateBoundingBox(this.placementData, this.pos));
         boolean _snowmanxxx = super.generate(_snowman, structureAccessor, chunkGenerator, random, boundingBox, _snowman, _snowman);
         this.placeNetherrackBase(random, _snowman);
         this.updateNetherracksInBound(random, _snowman);
         if (this.properties.vines || this.properties.overgrown) {
            BlockPos.stream(this.getBoundingBox()).forEach(_snowmanxxxx -> {
               if (this.properties.vines) {
                  this.generateVines(random, _snowman, _snowmanxxxx);
               }

               if (this.properties.overgrown) {
                  this.generateOvergrownLeaves(random, _snowman, _snowmanxxxx);
               }
            });
         }

         return _snowmanxxx;
      }
   }

   @Override
   protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess _snowman, Random random, BlockBox boundingBox) {
   }

   private void generateVines(Random random, WorldAccess world, BlockPos pos) {
      BlockState _snowman = world.getBlockState(pos);
      if (!_snowman.isAir() && !_snowman.isOf(Blocks.VINE)) {
         Direction _snowmanx = Direction.Type.HORIZONTAL.random(random);
         BlockPos _snowmanxx = pos.offset(_snowmanx);
         BlockState _snowmanxxx = world.getBlockState(_snowmanxx);
         if (_snowmanxxx.isAir()) {
            if (Block.isFaceFullSquare(_snowman.getCollisionShape(world, pos), _snowmanx)) {
               BooleanProperty _snowmanxxxx = VineBlock.getFacingProperty(_snowmanx.getOpposite());
               world.setBlockState(_snowmanxx, Blocks.VINE.getDefaultState().with(_snowmanxxxx, Boolean.valueOf(true)), 3);
            }
         }
      }
   }

   private void generateOvergrownLeaves(Random random, WorldAccess world, BlockPos pos) {
      if (random.nextFloat() < 0.5F && world.getBlockState(pos).isOf(Blocks.NETHERRACK) && world.getBlockState(pos.up()).isAir()) {
         world.setBlockState(pos.up(), Blocks.JUNGLE_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, Boolean.valueOf(true)), 3);
      }
   }

   private void updateNetherracksInBound(Random random, WorldAccess world) {
      for (int _snowman = this.boundingBox.minX + 1; _snowman < this.boundingBox.maxX; _snowman++) {
         for (int _snowmanx = this.boundingBox.minZ + 1; _snowmanx < this.boundingBox.maxZ; _snowmanx++) {
            BlockPos _snowmanxx = new BlockPos(_snowman, this.boundingBox.minY, _snowmanx);
            if (world.getBlockState(_snowmanxx).isOf(Blocks.NETHERRACK)) {
               this.updateNetherracks(random, world, _snowmanxx.down());
            }
         }
      }
   }

   private void updateNetherracks(Random random, WorldAccess world, BlockPos pos) {
      BlockPos.Mutable _snowman = pos.mutableCopy();
      this.placeNetherrackBottom(random, world, _snowman);
      int _snowmanx = 8;

      while (_snowmanx > 0 && random.nextFloat() < 0.5F) {
         _snowman.move(Direction.DOWN);
         _snowmanx--;
         this.placeNetherrackBottom(random, world, _snowman);
      }
   }

   private void placeNetherrackBase(Random random, WorldAccess world) {
      boolean _snowman = this.verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.ON_LAND_SURFACE
         || this.verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.ON_OCEAN_FLOOR;
      Vec3i _snowmanx = this.boundingBox.getCenter();
      int _snowmanxx = _snowmanx.getX();
      int _snowmanxxx = _snowmanx.getZ();
      float[] _snowmanxxxx = new float[]{1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.9F, 0.9F, 0.8F, 0.7F, 0.6F, 0.4F, 0.2F};
      int _snowmanxxxxx = _snowmanxxxx.length;
      int _snowmanxxxxxx = (this.boundingBox.getBlockCountX() + this.boundingBox.getBlockCountZ()) / 2;
      int _snowmanxxxxxxx = random.nextInt(Math.max(1, 8 - _snowmanxxxxxx / 2));
      int _snowmanxxxxxxxx = 3;
      BlockPos.Mutable _snowmanxxxxxxxxx = BlockPos.ORIGIN.mutableCopy();

      for (int _snowmanxxxxxxxxxx = _snowmanxx - _snowmanxxxxx; _snowmanxxxxxxxxxx <= _snowmanxx + _snowmanxxxxx; _snowmanxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxx = _snowmanxxx - _snowmanxxxxx; _snowmanxxxxxxxxxxx <= _snowmanxxx + _snowmanxxxxx; _snowmanxxxxxxxxxxx++) {
            int _snowmanxxxxxxxxxxxx = Math.abs(_snowmanxxxxxxxxxx - _snowmanxx) + Math.abs(_snowmanxxxxxxxxxxx - _snowmanxxx);
            int _snowmanxxxxxxxxxxxxx = Math.max(0, _snowmanxxxxxxxxxxxx + _snowmanxxxxxxx);
            if (_snowmanxxxxxxxxxxxxx < _snowmanxxxxx) {
               float _snowmanxxxxxxxxxxxxxx = _snowmanxxxx[_snowmanxxxxxxxxxxxxx];
               if (random.nextDouble() < (double)_snowmanxxxxxxxxxxxxxx) {
                  int _snowmanxxxxxxxxxxxxxxx = getBaseHeight(world, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, this.verticalPlacement);
                  int _snowmanxxxxxxxxxxxxxxxx = _snowman ? _snowmanxxxxxxxxxxxxxxx : Math.min(this.boundingBox.minY, _snowmanxxxxxxxxxxxxxxx);
                  _snowmanxxxxxxxxx.set(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx);
                  if (Math.abs(_snowmanxxxxxxxxxxxxxxxx - this.boundingBox.minY) <= 3 && this.canFillNetherrack(world, _snowmanxxxxxxxxx)) {
                     this.placeNetherrackBottom(random, world, _snowmanxxxxxxxxx);
                     if (this.properties.overgrown) {
                        this.generateOvergrownLeaves(random, world, _snowmanxxxxxxxxx);
                     }

                     this.updateNetherracks(random, world, _snowmanxxxxxxxxx.down());
                  }
               }
            }
         }
      }
   }

   private boolean canFillNetherrack(WorldAccess world, BlockPos pos) {
      BlockState _snowman = world.getBlockState(pos);
      return !_snowman.isOf(Blocks.AIR)
         && !_snowman.isOf(Blocks.OBSIDIAN)
         && !_snowman.isOf(Blocks.CHEST)
         && (this.verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.IN_NETHER || !_snowman.isOf(Blocks.LAVA));
   }

   private void placeNetherrackBottom(Random random, WorldAccess world, BlockPos pos) {
      if (!this.properties.cold && random.nextFloat() < 0.07F) {
         world.setBlockState(pos, Blocks.MAGMA_BLOCK.getDefaultState(), 3);
      } else {
         world.setBlockState(pos, Blocks.NETHERRACK.getDefaultState(), 3);
      }
   }

   private static int getBaseHeight(WorldAccess world, int x, int y, RuinedPortalStructurePiece.VerticalPlacement verticalPlacement) {
      return world.getTopY(getHeightmapType(verticalPlacement), x, y) - 1;
   }

   public static Heightmap.Type getHeightmapType(RuinedPortalStructurePiece.VerticalPlacement verticalPlacement) {
      return verticalPlacement == RuinedPortalStructurePiece.VerticalPlacement.ON_OCEAN_FLOOR ? Heightmap.Type.OCEAN_FLOOR_WG : Heightmap.Type.WORLD_SURFACE_WG;
   }

   private static StructureProcessorRule createReplacementRule(Block old, float chance, Block updated) {
      return new StructureProcessorRule(new RandomBlockMatchRuleTest(old, chance), AlwaysTrueRuleTest.INSTANCE, updated.getDefaultState());
   }

   private static StructureProcessorRule createReplacementRule(Block old, Block updated) {
      return new StructureProcessorRule(new BlockMatchRuleTest(old), AlwaysTrueRuleTest.INSTANCE, updated.getDefaultState());
   }

   public static class Properties {
      public static final Codec<RuinedPortalStructurePiece.Properties> CODEC = RecordCodecBuilder.create(
         _snowman -> _snowman.group(
                  Codec.BOOL.fieldOf("cold").forGetter(_snowmanx -> _snowmanx.cold),
                  Codec.FLOAT.fieldOf("mossiness").forGetter(_snowmanx -> _snowmanx.mossiness),
                  Codec.BOOL.fieldOf("air_pocket").forGetter(_snowmanx -> _snowmanx.airPocket),
                  Codec.BOOL.fieldOf("overgrown").forGetter(_snowmanx -> _snowmanx.overgrown),
                  Codec.BOOL.fieldOf("vines").forGetter(_snowmanx -> _snowmanx.vines),
                  Codec.BOOL.fieldOf("replace_with_blackstone").forGetter(_snowmanx -> _snowmanx.replaceWithBlackstone)
               )
               .apply(_snowman, RuinedPortalStructurePiece.Properties::new)
      );
      public boolean cold;
      public float mossiness = 0.2F;
      public boolean airPocket;
      public boolean overgrown;
      public boolean vines;
      public boolean replaceWithBlackstone;

      public Properties() {
      }

      public <T> Properties(boolean cold, float mossiness, boolean airPocket, boolean overgrown, boolean vines, boolean replaceWithBlackstone) {
         this.cold = cold;
         this.mossiness = mossiness;
         this.airPocket = airPocket;
         this.overgrown = overgrown;
         this.vines = vines;
         this.replaceWithBlackstone = replaceWithBlackstone;
      }
   }

   public static enum VerticalPlacement {
      ON_LAND_SURFACE("on_land_surface"),
      PARTLY_BURIED("partly_buried"),
      ON_OCEAN_FLOOR("on_ocean_floor"),
      IN_MOUNTAIN("in_mountain"),
      UNDERGROUND("underground"),
      IN_NETHER("in_nether");

      private static final Map<String, RuinedPortalStructurePiece.VerticalPlacement> VERTICAL_PLACEMENTS = Arrays.stream(values())
         .collect(Collectors.toMap(RuinedPortalStructurePiece.VerticalPlacement::getId, _snowman -> (RuinedPortalStructurePiece.VerticalPlacement)_snowman));
      private final String id;

      private VerticalPlacement(String id) {
         this.id = id;
      }

      public String getId() {
         return this.id;
      }

      public static RuinedPortalStructurePiece.VerticalPlacement getFromId(String id) {
         return VERTICAL_PLACEMENTS.get(id);
      }
   }
}
