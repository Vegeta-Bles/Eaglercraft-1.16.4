package net.minecraft.structure;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public abstract class StructurePiece {
   protected static final BlockState AIR = Blocks.CAVE_AIR.getDefaultState();
   protected BlockBox boundingBox;
   @Nullable
   private Direction facing;
   private BlockMirror mirror;
   private BlockRotation rotation;
   protected int chainLength;
   private final StructurePieceType type;
   private static final Set<Block> BLOCKS_NEEDING_POST_PROCESSING = ImmutableSet.builder()
      .add(Blocks.NETHER_BRICK_FENCE)
      .add(Blocks.TORCH)
      .add(Blocks.WALL_TORCH)
      .add(Blocks.OAK_FENCE)
      .add(Blocks.SPRUCE_FENCE)
      .add(Blocks.DARK_OAK_FENCE)
      .add(Blocks.ACACIA_FENCE)
      .add(Blocks.BIRCH_FENCE)
      .add(Blocks.JUNGLE_FENCE)
      .add(Blocks.LADDER)
      .add(Blocks.IRON_BARS)
      .build();

   protected StructurePiece(StructurePieceType type, int length) {
      this.type = type;
      this.chainLength = length;
   }

   public StructurePiece(StructurePieceType type, CompoundTag tag) {
      this(type, tag.getInt("GD"));
      if (tag.contains("BB")) {
         this.boundingBox = new BlockBox(tag.getIntArray("BB"));
      }

      int _snowman = tag.getInt("O");
      this.setOrientation(_snowman == -1 ? null : Direction.fromHorizontal(_snowman));
   }

   public final CompoundTag getTag() {
      CompoundTag _snowman = new CompoundTag();
      _snowman.putString("id", Registry.STRUCTURE_PIECE.getId(this.getType()).toString());
      _snowman.put("BB", this.boundingBox.toNbt());
      Direction _snowmanx = this.getFacing();
      _snowman.putInt("O", _snowmanx == null ? -1 : _snowmanx.getHorizontal());
      _snowman.putInt("GD", this.chainLength);
      this.toNbt(_snowman);
      return _snowman;
   }

   protected abstract void toNbt(CompoundTag tag);

   public void fillOpenings(StructurePiece start, List<StructurePiece> pieces, Random random) {
   }

   public abstract boolean generate(
      StructureWorldAccess var1,
      StructureAccessor structureAccessor,
      ChunkGenerator chunkGenerator,
      Random random,
      BlockBox boundingBox,
      ChunkPos var6,
      BlockPos var7
   );

   public BlockBox getBoundingBox() {
      return this.boundingBox;
   }

   public int getChainLength() {
      return this.chainLength;
   }

   public boolean intersectsChunk(ChunkPos _snowman, int offset) {
      int _snowmanx = _snowman.x << 4;
      int _snowmanxx = _snowman.z << 4;
      return this.boundingBox.intersectsXZ(_snowmanx - offset, _snowmanxx - offset, _snowmanx + 15 + offset, _snowmanxx + 15 + offset);
   }

   public static StructurePiece getOverlappingPiece(List<StructurePiece> pieces, BlockBox _snowman) {
      for (StructurePiece _snowmanx : pieces) {
         if (_snowmanx.getBoundingBox() != null && _snowmanx.getBoundingBox().intersects(_snowman)) {
            return _snowmanx;
         }
      }

      return null;
   }

   protected boolean isTouchingLiquid(BlockView _snowman, BlockBox _snowman) {
      int _snowmanxx = Math.max(this.boundingBox.minX - 1, _snowman.minX);
      int _snowmanxxx = Math.max(this.boundingBox.minY - 1, _snowman.minY);
      int _snowmanxxxx = Math.max(this.boundingBox.minZ - 1, _snowman.minZ);
      int _snowmanxxxxx = Math.min(this.boundingBox.maxX + 1, _snowman.maxX);
      int _snowmanxxxxxx = Math.min(this.boundingBox.maxY + 1, _snowman.maxY);
      int _snowmanxxxxxxx = Math.min(this.boundingBox.maxZ + 1, _snowman.maxZ);
      BlockPos.Mutable _snowmanxxxxxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxxxxxx = _snowmanxx; _snowmanxxxxxxxxx <= _snowmanxxxxx; _snowmanxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxx = _snowmanxxxx; _snowmanxxxxxxxxxx <= _snowmanxxxxxxx; _snowmanxxxxxxxxxx++) {
            if (_snowman.getBlockState(_snowmanxxxxxxxx.set(_snowmanxxxxxxxxx, _snowmanxxx, _snowmanxxxxxxxxxx)).getMaterial().isLiquid()) {
               return true;
            }

            if (_snowman.getBlockState(_snowmanxxxxxxxx.set(_snowmanxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxx)).getMaterial().isLiquid()) {
               return true;
            }
         }
      }

      for (int _snowmanxxxxxxxxx = _snowmanxx; _snowmanxxxxxxxxx <= _snowmanxxxxx; _snowmanxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxx = _snowmanxxx; _snowmanxxxxxxxxxx <= _snowmanxxxxxx; _snowmanxxxxxxxxxx++) {
            if (_snowman.getBlockState(_snowmanxxxxxxxx.set(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxx)).getMaterial().isLiquid()) {
               return true;
            }

            if (_snowman.getBlockState(_snowmanxxxxxxxx.set(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxx)).getMaterial().isLiquid()) {
               return true;
            }
         }
      }

      for (int _snowmanxxxxxxxxx = _snowmanxxxx; _snowmanxxxxxxxxx <= _snowmanxxxxxxx; _snowmanxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxx = _snowmanxxx; _snowmanxxxxxxxxxx <= _snowmanxxxxxx; _snowmanxxxxxxxxxx++) {
            if (_snowman.getBlockState(_snowmanxxxxxxxx.set(_snowmanxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx)).getMaterial().isLiquid()) {
               return true;
            }

            if (_snowman.getBlockState(_snowmanxxxxxxxx.set(_snowmanxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx)).getMaterial().isLiquid()) {
               return true;
            }
         }
      }

      return false;
   }

   protected int applyXTransform(int x, int z) {
      Direction _snowman = this.getFacing();
      if (_snowman == null) {
         return x;
      } else {
         switch (_snowman) {
            case NORTH:
            case SOUTH:
               return this.boundingBox.minX + x;
            case WEST:
               return this.boundingBox.maxX - z;
            case EAST:
               return this.boundingBox.minX + z;
            default:
               return x;
         }
      }
   }

   protected int applyYTransform(int y) {
      return this.getFacing() == null ? y : y + this.boundingBox.minY;
   }

   protected int applyZTransform(int x, int z) {
      Direction _snowman = this.getFacing();
      if (_snowman == null) {
         return z;
      } else {
         switch (_snowman) {
            case NORTH:
               return this.boundingBox.maxZ - z;
            case SOUTH:
               return this.boundingBox.minZ + z;
            case WEST:
            case EAST:
               return this.boundingBox.minZ + x;
            default:
               return z;
         }
      }
   }

   protected void addBlock(StructureWorldAccess _snowman, BlockState block, int x, int y, int z, BlockBox _snowman) {
      BlockPos _snowmanxx = new BlockPos(this.applyXTransform(x, z), this.applyYTransform(y), this.applyZTransform(x, z));
      if (_snowman.contains(_snowmanxx)) {
         if (this.mirror != BlockMirror.NONE) {
            block = block.mirror(this.mirror);
         }

         if (this.rotation != BlockRotation.NONE) {
            block = block.rotate(this.rotation);
         }

         _snowman.setBlockState(_snowmanxx, block, 2);
         FluidState _snowmanxxx = _snowman.getFluidState(_snowmanxx);
         if (!_snowmanxxx.isEmpty()) {
            _snowman.getFluidTickScheduler().schedule(_snowmanxx, _snowmanxxx.getFluid(), 0);
         }

         if (BLOCKS_NEEDING_POST_PROCESSING.contains(block.getBlock())) {
            _snowman.getChunk(_snowmanxx).markBlockForPostProcessing(_snowmanxx);
         }
      }
   }

   protected BlockState getBlockAt(BlockView _snowman, int x, int y, int z, BlockBox _snowman) {
      int _snowmanxx = this.applyXTransform(x, z);
      int _snowmanxxx = this.applyYTransform(y);
      int _snowmanxxxx = this.applyZTransform(x, z);
      BlockPos _snowmanxxxxx = new BlockPos(_snowmanxx, _snowmanxxx, _snowmanxxxx);
      return !_snowman.contains(_snowmanxxxxx) ? Blocks.AIR.getDefaultState() : _snowman.getBlockState(_snowmanxxxxx);
   }

   protected boolean isUnderSeaLevel(WorldView _snowman, int x, int z, int y, BlockBox _snowman) {
      int _snowmanxx = this.applyXTransform(x, y);
      int _snowmanxxx = this.applyYTransform(z + 1);
      int _snowmanxxxx = this.applyZTransform(x, y);
      BlockPos _snowmanxxxxx = new BlockPos(_snowmanxx, _snowmanxxx, _snowmanxxxx);
      return !_snowman.contains(_snowmanxxxxx) ? false : _snowmanxxx < _snowman.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, _snowmanxx, _snowmanxxxx);
   }

   protected void fill(StructureWorldAccess _snowman, BlockBox bounds, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
      for (int _snowmanx = minY; _snowmanx <= maxY; _snowmanx++) {
         for (int _snowmanxx = minX; _snowmanxx <= maxX; _snowmanxx++) {
            for (int _snowmanxxx = minZ; _snowmanxxx <= maxZ; _snowmanxxx++) {
               this.addBlock(_snowman, Blocks.AIR.getDefaultState(), _snowmanxx, _snowmanx, _snowmanxxx, bounds);
            }
         }
      }
   }

   protected void fillWithOutline(
      StructureWorldAccess _snowman,
      BlockBox _snowman,
      int minX,
      int minY,
      int minZ,
      int maxX,
      int maxY,
      int maxZ,
      BlockState outline,
      BlockState inside,
      boolean cantReplaceAir
   ) {
      for (int _snowmanxx = minY; _snowmanxx <= maxY; _snowmanxx++) {
         for (int _snowmanxxx = minX; _snowmanxxx <= maxX; _snowmanxxx++) {
            for (int _snowmanxxxx = minZ; _snowmanxxxx <= maxZ; _snowmanxxxx++) {
               if (!cantReplaceAir || !this.getBlockAt(_snowman, _snowmanxxx, _snowmanxx, _snowmanxxxx, _snowman).isAir()) {
                  if (_snowmanxx != minY && _snowmanxx != maxY && _snowmanxxx != minX && _snowmanxxx != maxX && _snowmanxxxx != minZ && _snowmanxxxx != maxZ) {
                     this.addBlock(_snowman, inside, _snowmanxxx, _snowmanxx, _snowmanxxxx, _snowman);
                  } else {
                     this.addBlock(_snowman, outline, _snowmanxxx, _snowmanxx, _snowmanxxxx, _snowman);
                  }
               }
            }
         }
      }
   }

   protected void fillWithOutline(
      StructureWorldAccess _snowman,
      BlockBox _snowman,
      int minX,
      int minY,
      int minZ,
      int maxX,
      int maxY,
      int maxZ,
      boolean cantReplaceAir,
      Random random,
      StructurePiece.BlockRandomizer _snowman
   ) {
      for (int _snowmanxxx = minY; _snowmanxxx <= maxY; _snowmanxxx++) {
         for (int _snowmanxxxx = minX; _snowmanxxxx <= maxX; _snowmanxxxx++) {
            for (int _snowmanxxxxx = minZ; _snowmanxxxxx <= maxZ; _snowmanxxxxx++) {
               if (!cantReplaceAir || !this.getBlockAt(_snowman, _snowmanxxxx, _snowmanxxx, _snowmanxxxxx, _snowman).isAir()) {
                  _snowman.setBlock(random, _snowmanxxxx, _snowmanxxx, _snowmanxxxxx, _snowmanxxx == minY || _snowmanxxx == maxY || _snowmanxxxx == minX || _snowmanxxxx == maxX || _snowmanxxxxx == minZ || _snowmanxxxxx == maxZ);
                  this.addBlock(_snowman, _snowman.getBlock(), _snowmanxxxx, _snowmanxxx, _snowmanxxxxx, _snowman);
               }
            }
         }
      }
   }

   protected void fillWithOutlineUnderSeaLevel(
      StructureWorldAccess _snowman,
      BlockBox _snowman,
      Random random,
      float blockChance,
      int minX,
      int minY,
      int minZ,
      int maxX,
      int maxY,
      int maxZ,
      BlockState outline,
      BlockState inside,
      boolean cantReplaceAir,
      boolean stayBelowSeaLevel
   ) {
      for (int _snowmanxx = minY; _snowmanxx <= maxY; _snowmanxx++) {
         for (int _snowmanxxx = minX; _snowmanxxx <= maxX; _snowmanxxx++) {
            for (int _snowmanxxxx = minZ; _snowmanxxxx <= maxZ; _snowmanxxxx++) {
               if (!(random.nextFloat() > blockChance)
                  && (!cantReplaceAir || !this.getBlockAt(_snowman, _snowmanxxx, _snowmanxx, _snowmanxxxx, _snowman).isAir())
                  && (!stayBelowSeaLevel || this.isUnderSeaLevel(_snowman, _snowmanxxx, _snowmanxx, _snowmanxxxx, _snowman))) {
                  if (_snowmanxx != minY && _snowmanxx != maxY && _snowmanxxx != minX && _snowmanxxx != maxX && _snowmanxxxx != minZ && _snowmanxxxx != maxZ) {
                     this.addBlock(_snowman, inside, _snowmanxxx, _snowmanxx, _snowmanxxxx, _snowman);
                  } else {
                     this.addBlock(_snowman, outline, _snowmanxxx, _snowmanxx, _snowmanxxxx, _snowman);
                  }
               }
            }
         }
      }
   }

   protected void addBlockWithRandomThreshold(StructureWorldAccess _snowman, BlockBox bounds, Random random, float threshold, int x, int y, int z, BlockState _snowman) {
      if (random.nextFloat() < threshold) {
         this.addBlock(_snowman, _snowman, x, y, z, bounds);
      }
   }

   protected void fillHalfEllipsoid(
      StructureWorldAccess _snowman, BlockBox bounds, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, BlockState block, boolean cantReplaceAir
   ) {
      float _snowmanx = (float)(maxX - minX + 1);
      float _snowmanxx = (float)(maxY - minY + 1);
      float _snowmanxxx = (float)(maxZ - minZ + 1);
      float _snowmanxxxx = (float)minX + _snowmanx / 2.0F;
      float _snowmanxxxxx = (float)minZ + _snowmanxxx / 2.0F;

      for (int _snowmanxxxxxx = minY; _snowmanxxxxxx <= maxY; _snowmanxxxxxx++) {
         float _snowmanxxxxxxx = (float)(_snowmanxxxxxx - minY) / _snowmanxx;

         for (int _snowmanxxxxxxxx = minX; _snowmanxxxxxxxx <= maxX; _snowmanxxxxxxxx++) {
            float _snowmanxxxxxxxxx = ((float)_snowmanxxxxxxxx - _snowmanxxxx) / (_snowmanx * 0.5F);

            for (int _snowmanxxxxxxxxxx = minZ; _snowmanxxxxxxxxxx <= maxZ; _snowmanxxxxxxxxxx++) {
               float _snowmanxxxxxxxxxxx = ((float)_snowmanxxxxxxxxxx - _snowmanxxxxx) / (_snowmanxxx * 0.5F);
               if (!cantReplaceAir || !this.getBlockAt(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxx, bounds).isAir()) {
                  float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx * _snowmanxxxxxxxxx + _snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxxx <= 1.05F) {
                     this.addBlock(_snowman, block, _snowmanxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxx, bounds);
                  }
               }
            }
         }
      }
   }

   protected void fillDownwards(StructureWorldAccess _snowman, BlockState _snowman, int x, int y, int z, BlockBox _snowman) {
      int _snowmanxxx = this.applyXTransform(x, z);
      int _snowmanxxxx = this.applyYTransform(y);
      int _snowmanxxxxx = this.applyZTransform(x, z);
      if (_snowman.contains(new BlockPos(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx))) {
         while ((_snowman.isAir(new BlockPos(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx)) || _snowman.getBlockState(new BlockPos(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx)).getMaterial().isLiquid()) && _snowmanxxxx > 1) {
            _snowman.setBlockState(new BlockPos(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx), _snowman, 2);
            _snowmanxxxx--;
         }
      }
   }

   protected boolean addChest(StructureWorldAccess _snowman, BlockBox boundingBox, Random random, int x, int y, int z, Identifier lootTableId) {
      BlockPos _snowmanx = new BlockPos(this.applyXTransform(x, z), this.applyYTransform(y), this.applyZTransform(x, z));
      return this.addChest(_snowman, boundingBox, random, _snowmanx, lootTableId, null);
   }

   public static BlockState orientateChest(BlockView _snowman, BlockPos _snowman, BlockState _snowman) {
      Direction _snowmanxxx = null;

      for (Direction _snowmanxxxx : Direction.Type.HORIZONTAL) {
         BlockPos _snowmanxxxxx = _snowman.offset(_snowmanxxxx);
         BlockState _snowmanxxxxxx = _snowman.getBlockState(_snowmanxxxxx);
         if (_snowmanxxxxxx.isOf(Blocks.CHEST)) {
            return _snowman;
         }

         if (_snowmanxxxxxx.isOpaqueFullCube(_snowman, _snowmanxxxxx)) {
            if (_snowmanxxx != null) {
               _snowmanxxx = null;
               break;
            }

            _snowmanxxx = _snowmanxxxx;
         }
      }

      if (_snowmanxxx != null) {
         return _snowman.with(HorizontalFacingBlock.FACING, _snowmanxxx.getOpposite());
      } else {
         Direction _snowmanxxxx = _snowman.get(HorizontalFacingBlock.FACING);
         BlockPos _snowmanxxxxxxx = _snowman.offset(_snowmanxxxx);
         if (_snowman.getBlockState(_snowmanxxxxxxx).isOpaqueFullCube(_snowman, _snowmanxxxxxxx)) {
            _snowmanxxxx = _snowmanxxxx.getOpposite();
            _snowmanxxxxxxx = _snowman.offset(_snowmanxxxx);
         }

         if (_snowman.getBlockState(_snowmanxxxxxxx).isOpaqueFullCube(_snowman, _snowmanxxxxxxx)) {
            _snowmanxxxx = _snowmanxxxx.rotateYClockwise();
            _snowmanxxxxxxx = _snowman.offset(_snowmanxxxx);
         }

         if (_snowman.getBlockState(_snowmanxxxxxxx).isOpaqueFullCube(_snowman, _snowmanxxxxxxx)) {
            _snowmanxxxx = _snowmanxxxx.getOpposite();
            _snowmanxxxxxxx = _snowman.offset(_snowmanxxxx);
         }

         return _snowman.with(HorizontalFacingBlock.FACING, _snowmanxxxx);
      }
   }

   protected boolean addChest(ServerWorldAccess _snowman, BlockBox boundingBox, Random random, BlockPos pos, Identifier lootTableId, @Nullable BlockState block) {
      if (boundingBox.contains(pos) && !_snowman.getBlockState(pos).isOf(Blocks.CHEST)) {
         if (block == null) {
            block = orientateChest(_snowman, pos, Blocks.CHEST.getDefaultState());
         }

         _snowman.setBlockState(pos, block, 2);
         BlockEntity _snowmanx = _snowman.getBlockEntity(pos);
         if (_snowmanx instanceof ChestBlockEntity) {
            ((ChestBlockEntity)_snowmanx).setLootTable(lootTableId, random.nextLong());
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean addDispenser(StructureWorldAccess _snowman, BlockBox boundingBox, Random random, int x, int y, int z, Direction facing, Identifier lootTableId) {
      BlockPos _snowmanx = new BlockPos(this.applyXTransform(x, z), this.applyYTransform(y), this.applyZTransform(x, z));
      if (boundingBox.contains(_snowmanx) && !_snowman.getBlockState(_snowmanx).isOf(Blocks.DISPENSER)) {
         this.addBlock(_snowman, Blocks.DISPENSER.getDefaultState().with(DispenserBlock.FACING, facing), x, y, z, boundingBox);
         BlockEntity _snowmanxx = _snowman.getBlockEntity(_snowmanx);
         if (_snowmanxx instanceof DispenserBlockEntity) {
            ((DispenserBlockEntity)_snowmanxx).setLootTable(lootTableId, random.nextLong());
         }

         return true;
      } else {
         return false;
      }
   }

   public void translate(int x, int y, int z) {
      this.boundingBox.move(x, y, z);
   }

   @Nullable
   public Direction getFacing() {
      return this.facing;
   }

   public void setOrientation(@Nullable Direction orientation) {
      this.facing = orientation;
      if (orientation == null) {
         this.rotation = BlockRotation.NONE;
         this.mirror = BlockMirror.NONE;
      } else {
         switch (orientation) {
            case SOUTH:
               this.mirror = BlockMirror.LEFT_RIGHT;
               this.rotation = BlockRotation.NONE;
               break;
            case WEST:
               this.mirror = BlockMirror.LEFT_RIGHT;
               this.rotation = BlockRotation.CLOCKWISE_90;
               break;
            case EAST:
               this.mirror = BlockMirror.NONE;
               this.rotation = BlockRotation.CLOCKWISE_90;
               break;
            default:
               this.mirror = BlockMirror.NONE;
               this.rotation = BlockRotation.NONE;
         }
      }
   }

   public BlockRotation getRotation() {
      return this.rotation;
   }

   public StructurePieceType getType() {
      return this.type;
   }

   public abstract static class BlockRandomizer {
      protected BlockState block = Blocks.AIR.getDefaultState();

      protected BlockRandomizer() {
      }

      public abstract void setBlock(Random random, int x, int y, int z, boolean placeBlock);

      public BlockState getBlock() {
         return this.block;
      }
   }
}
