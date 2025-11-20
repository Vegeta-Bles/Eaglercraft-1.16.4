package net.minecraft.world.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.GourdBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.property.Properties;
import net.minecraft.util.EightWayDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpgradeData {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final UpgradeData NO_UPGRADE_DATA = new UpgradeData();
   private static final EightWayDirection[] EIGHT_WAYS = EightWayDirection.values();
   private final EnumSet<EightWayDirection> sidesToUpgrade = EnumSet.noneOf(EightWayDirection.class);
   private final int[][] centerIndicesToUpgrade = new int[16][];
   private static final Map<Block, UpgradeData.Logic> BLOCK_TO_LOGIC = new IdentityHashMap<>();
   private static final Set<UpgradeData.Logic> CALLBACK_LOGICS = Sets.newHashSet();

   private UpgradeData() {
   }

   public UpgradeData(CompoundTag tag) {
      this();
      if (tag.contains("Indices", 10)) {
         CompoundTag _snowman = tag.getCompound("Indices");

         for (int _snowmanx = 0; _snowmanx < this.centerIndicesToUpgrade.length; _snowmanx++) {
            String _snowmanxx = String.valueOf(_snowmanx);
            if (_snowman.contains(_snowmanxx, 11)) {
               this.centerIndicesToUpgrade[_snowmanx] = _snowman.getIntArray(_snowmanxx);
            }
         }
      }

      int _snowman = tag.getInt("Sides");

      for (EightWayDirection _snowmanxx : EightWayDirection.values()) {
         if ((_snowman & 1 << _snowmanxx.ordinal()) != 0) {
            this.sidesToUpgrade.add(_snowmanxx);
         }
      }
   }

   public void upgrade(WorldChunk chunk) {
      this.upgradeCenter(chunk);

      for (EightWayDirection _snowman : EIGHT_WAYS) {
         upgradeSide(chunk, _snowman);
      }

      World _snowman = chunk.getWorld();
      CALLBACK_LOGICS.forEach(_snowmanx -> _snowmanx.postUpdate(_snowman));
   }

   private static void upgradeSide(WorldChunk chunk, EightWayDirection side) {
      World _snowman = chunk.getWorld();
      if (chunk.getUpgradeData().sidesToUpgrade.remove(side)) {
         Set<Direction> _snowmanx = side.getDirections();
         int _snowmanxx = 0;
         int _snowmanxxx = 15;
         boolean _snowmanxxxx = _snowmanx.contains(Direction.EAST);
         boolean _snowmanxxxxx = _snowmanx.contains(Direction.WEST);
         boolean _snowmanxxxxxx = _snowmanx.contains(Direction.SOUTH);
         boolean _snowmanxxxxxxx = _snowmanx.contains(Direction.NORTH);
         boolean _snowmanxxxxxxxx = _snowmanx.size() == 1;
         ChunkPos _snowmanxxxxxxxxx = chunk.getPos();
         int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.getStartX() + (!_snowmanxxxxxxxx || !_snowmanxxxxxxx && !_snowmanxxxxxx ? (_snowmanxxxxx ? 0 : 15) : 1);
         int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.getStartX() + (!_snowmanxxxxxxxx || !_snowmanxxxxxxx && !_snowmanxxxxxx ? (_snowmanxxxxx ? 0 : 15) : 14);
         int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx.getStartZ() + (!_snowmanxxxxxxxx || !_snowmanxxxx && !_snowmanxxxxx ? (_snowmanxxxxxxx ? 0 : 15) : 1);
         int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxx.getStartZ() + (!_snowmanxxxxxxxx || !_snowmanxxxx && !_snowmanxxxxx ? (_snowmanxxxxxxx ? 0 : 15) : 14);
         Direction[] _snowmanxxxxxxxxxxxxxx = Direction.values();
         BlockPos.Mutable _snowmanxxxxxxxxxxxxxxx = new BlockPos.Mutable();

         for (BlockPos _snowmanxxxxxxxxxxxxxxxx : BlockPos.iterate(_snowmanxxxxxxxxxx, 0, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowman.getHeight() - 1, _snowmanxxxxxxxxxxxxx)) {
            BlockState _snowmanxxxxxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxxxxxxx);
            BlockState _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx;

            for (Direction _snowmanxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx);
               _snowmanxxxxxxxxxxxxxxxxxx = applyAdjacentBlock(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
            }

            Block.replace(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxxxxxx, 18);
         }
      }
   }

   private static BlockState applyAdjacentBlock(BlockState oldState, Direction dir, WorldAccess world, BlockPos currentPos, BlockPos otherPos) {
      return BLOCK_TO_LOGIC.getOrDefault(oldState.getBlock(), UpgradeData.BuiltinLogic.DEFAULT)
         .getUpdatedState(oldState, dir, world.getBlockState(otherPos), world, currentPos, otherPos);
   }

   private void upgradeCenter(WorldChunk chunk) {
      BlockPos.Mutable _snowman = new BlockPos.Mutable();
      BlockPos.Mutable _snowmanx = new BlockPos.Mutable();
      ChunkPos _snowmanxx = chunk.getPos();
      WorldAccess _snowmanxxx = chunk.getWorld();

      for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
         ChunkSection _snowmanxxxxx = chunk.getSectionArray()[_snowmanxxxx];
         int[] _snowmanxxxxxx = this.centerIndicesToUpgrade[_snowmanxxxx];
         this.centerIndicesToUpgrade[_snowmanxxxx] = null;
         if (_snowmanxxxxx != null && _snowmanxxxxxx != null && _snowmanxxxxxx.length > 0) {
            Direction[] _snowmanxxxxxxx = Direction.values();
            PalettedContainer<BlockState> _snowmanxxxxxxxx = _snowmanxxxxx.getContainer();

            for (int _snowmanxxxxxxxxx : _snowmanxxxxxx) {
               int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx & 15;
               int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx >> 8 & 15;
               int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx >> 4 & 15;
               _snowman.set(_snowmanxx.getStartX() + _snowmanxxxxxxxxxx, (_snowmanxxxx << 4) + _snowmanxxxxxxxxxxx, _snowmanxx.getStartZ() + _snowmanxxxxxxxxxxxx);
               BlockState _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxx.get(_snowmanxxxxxxxxx);
               BlockState _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx;

               for (Direction _snowmanxxxxxxxxxxxxxxx : _snowmanxxxxxxx) {
                  _snowmanx.set(_snowman, _snowmanxxxxxxxxxxxxxxx);
                  if (_snowman.getX() >> 4 == _snowmanxx.x && _snowman.getZ() >> 4 == _snowmanxx.z) {
                     _snowmanxxxxxxxxxxxxxx = applyAdjacentBlock(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxx, _snowman, _snowmanx);
                  }
               }

               Block.replace(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxx, _snowman, 18);
            }
         }
      }

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < this.centerIndicesToUpgrade.length; _snowmanxxxxx++) {
         if (this.centerIndicesToUpgrade[_snowmanxxxxx] != null) {
            LOGGER.warn("Discarding update data for section {} for chunk ({} {})", _snowmanxxxxx, _snowmanxx.x, _snowmanxx.z);
         }

         this.centerIndicesToUpgrade[_snowmanxxxxx] = null;
      }
   }

   public boolean isDone() {
      for (int[] _snowman : this.centerIndicesToUpgrade) {
         if (_snowman != null) {
            return false;
         }
      }

      return this.sidesToUpgrade.isEmpty();
   }

   public CompoundTag toTag() {
      CompoundTag _snowman = new CompoundTag();
      CompoundTag _snowmanx = new CompoundTag();

      for (int _snowmanxx = 0; _snowmanxx < this.centerIndicesToUpgrade.length; _snowmanxx++) {
         String _snowmanxxx = String.valueOf(_snowmanxx);
         if (this.centerIndicesToUpgrade[_snowmanxx] != null && this.centerIndicesToUpgrade[_snowmanxx].length != 0) {
            _snowmanx.putIntArray(_snowmanxxx, this.centerIndicesToUpgrade[_snowmanxx]);
         }
      }

      if (!_snowmanx.isEmpty()) {
         _snowman.put("Indices", _snowmanx);
      }

      int _snowmanxxx = 0;

      for (EightWayDirection _snowmanxxxx : this.sidesToUpgrade) {
         _snowmanxxx |= 1 << _snowmanxxxx.ordinal();
      }

      _snowman.putByte("Sides", (byte)_snowmanxxx);
      return _snowman;
   }

   static enum BuiltinLogic implements UpgradeData.Logic {
      BLACKLIST(
         Blocks.OBSERVER,
         Blocks.NETHER_PORTAL,
         Blocks.WHITE_CONCRETE_POWDER,
         Blocks.ORANGE_CONCRETE_POWDER,
         Blocks.MAGENTA_CONCRETE_POWDER,
         Blocks.LIGHT_BLUE_CONCRETE_POWDER,
         Blocks.YELLOW_CONCRETE_POWDER,
         Blocks.LIME_CONCRETE_POWDER,
         Blocks.PINK_CONCRETE_POWDER,
         Blocks.GRAY_CONCRETE_POWDER,
         Blocks.LIGHT_GRAY_CONCRETE_POWDER,
         Blocks.CYAN_CONCRETE_POWDER,
         Blocks.PURPLE_CONCRETE_POWDER,
         Blocks.BLUE_CONCRETE_POWDER,
         Blocks.BROWN_CONCRETE_POWDER,
         Blocks.GREEN_CONCRETE_POWDER,
         Blocks.RED_CONCRETE_POWDER,
         Blocks.BLACK_CONCRETE_POWDER,
         Blocks.ANVIL,
         Blocks.CHIPPED_ANVIL,
         Blocks.DAMAGED_ANVIL,
         Blocks.DRAGON_EGG,
         Blocks.GRAVEL,
         Blocks.SAND,
         Blocks.RED_SAND,
         Blocks.OAK_SIGN,
         Blocks.SPRUCE_SIGN,
         Blocks.BIRCH_SIGN,
         Blocks.ACACIA_SIGN,
         Blocks.JUNGLE_SIGN,
         Blocks.DARK_OAK_SIGN,
         Blocks.OAK_WALL_SIGN,
         Blocks.SPRUCE_WALL_SIGN,
         Blocks.BIRCH_WALL_SIGN,
         Blocks.ACACIA_WALL_SIGN,
         Blocks.JUNGLE_WALL_SIGN,
         Blocks.DARK_OAK_WALL_SIGN
      ) {
         @Override
         public BlockState getUpdatedState(BlockState _snowman, Direction _snowman, BlockState _snowman, WorldAccess _snowman, BlockPos _snowman, BlockPos _snowman) {
            return _snowman;
         }
      },
      DEFAULT {
         @Override
         public BlockState getUpdatedState(BlockState _snowman, Direction _snowman, BlockState _snowman, WorldAccess _snowman, BlockPos _snowman, BlockPos _snowman) {
            return _snowman.getStateForNeighborUpdate(_snowman, _snowman.getBlockState(_snowman), _snowman, _snowman, _snowman);
         }
      },
      CHEST(Blocks.CHEST, Blocks.TRAPPED_CHEST) {
         @Override
         public BlockState getUpdatedState(BlockState _snowman, Direction _snowman, BlockState _snowman, WorldAccess _snowman, BlockPos _snowman, BlockPos _snowman) {
            if (_snowman.isOf(_snowman.getBlock())
               && _snowman.getAxis().isHorizontal()
               && _snowman.get(ChestBlock.CHEST_TYPE) == ChestType.SINGLE
               && _snowman.get(ChestBlock.CHEST_TYPE) == ChestType.SINGLE) {
               Direction _snowmanxxxxxx = _snowman.get(ChestBlock.FACING);
               if (_snowman.getAxis() != _snowmanxxxxxx.getAxis() && _snowmanxxxxxx == _snowman.get(ChestBlock.FACING)) {
                  ChestType _snowmanxxxxxxx = _snowman == _snowmanxxxxxx.rotateYClockwise() ? ChestType.LEFT : ChestType.RIGHT;
                  _snowman.setBlockState(_snowman, _snowman.with(ChestBlock.CHEST_TYPE, _snowmanxxxxxxx.getOpposite()), 18);
                  if (_snowmanxxxxxx == Direction.NORTH || _snowmanxxxxxx == Direction.EAST) {
                     BlockEntity _snowmanxxxxxxxx = _snowman.getBlockEntity(_snowman);
                     BlockEntity _snowmanxxxxxxxxx = _snowman.getBlockEntity(_snowman);
                     if (_snowmanxxxxxxxx instanceof ChestBlockEntity && _snowmanxxxxxxxxx instanceof ChestBlockEntity) {
                        ChestBlockEntity.copyInventory((ChestBlockEntity)_snowmanxxxxxxxx, (ChestBlockEntity)_snowmanxxxxxxxxx);
                     }
                  }

                  return _snowman.with(ChestBlock.CHEST_TYPE, _snowmanxxxxxxx);
               }
            }

            return _snowman;
         }
      },
      LEAVES(true, Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES) {
         private final ThreadLocal<List<ObjectSet<BlockPos>>> distanceToPositions = ThreadLocal.withInitial(() -> Lists.newArrayListWithCapacity(7));

         @Override
         public BlockState getUpdatedState(BlockState _snowman, Direction _snowman, BlockState _snowman, WorldAccess _snowman, BlockPos _snowman, BlockPos _snowman) {
            BlockState _snowmanxxxxxx = _snowman.getStateForNeighborUpdate(_snowman, _snowman.getBlockState(_snowman), _snowman, _snowman, _snowman);
            if (_snowman != _snowmanxxxxxx) {
               int _snowmanxxxxxxx = _snowmanxxxxxx.get(Properties.DISTANCE_1_7);
               List<ObjectSet<BlockPos>> _snowmanxxxxxxxx = this.distanceToPositions.get();
               if (_snowmanxxxxxxxx.isEmpty()) {
                  for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < 7; _snowmanxxxxxxxxx++) {
                     _snowmanxxxxxxxx.add(new ObjectOpenHashSet());
                  }
               }

               _snowmanxxxxxxxx.get(_snowmanxxxxxxx).add(_snowman.toImmutable());
            }

            return _snowman;
         }

         @Override
         public void postUpdate(WorldAccess world) {
            BlockPos.Mutable _snowman = new BlockPos.Mutable();
            List<ObjectSet<BlockPos>> _snowmanx = this.distanceToPositions.get();

            for (int _snowmanxx = 2; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
               int _snowmanxxx = _snowmanxx - 1;
               ObjectSet<BlockPos> _snowmanxxxx = _snowmanx.get(_snowmanxxx);
               ObjectSet<BlockPos> _snowmanxxxxx = _snowmanx.get(_snowmanxx);
               ObjectIterator var8 = _snowmanxxxx.iterator();

               while (var8.hasNext()) {
                  BlockPos _snowmanxxxxxx = (BlockPos)var8.next();
                  BlockState _snowmanxxxxxxx = world.getBlockState(_snowmanxxxxxx);
                  if (_snowmanxxxxxxx.get(Properties.DISTANCE_1_7) >= _snowmanxxx) {
                     world.setBlockState(_snowmanxxxxxx, _snowmanxxxxxxx.with(Properties.DISTANCE_1_7, Integer.valueOf(_snowmanxxx)), 18);
                     if (_snowmanxx != 7) {
                        for (Direction _snowmanxxxxxxxx : DIRECTIONS) {
                           _snowman.set(_snowmanxxxxxx, _snowmanxxxxxxxx);
                           BlockState _snowmanxxxxxxxxx = world.getBlockState(_snowman);
                           if (_snowmanxxxxxxxxx.contains(Properties.DISTANCE_1_7) && _snowmanxxxxxxx.get(Properties.DISTANCE_1_7) > _snowmanxx) {
                              _snowmanxxxxx.add(_snowman.toImmutable());
                           }
                        }
                     }
                  }
               }
            }

            _snowmanx.clear();
         }
      },
      STEM_BLOCK(Blocks.MELON_STEM, Blocks.PUMPKIN_STEM) {
         @Override
         public BlockState getUpdatedState(BlockState _snowman, Direction _snowman, BlockState _snowman, WorldAccess _snowman, BlockPos _snowman, BlockPos _snowman) {
            if (_snowman.get(StemBlock.AGE) == 7) {
               GourdBlock _snowmanxxxxxx = ((StemBlock)_snowman.getBlock()).getGourdBlock();
               if (_snowman.isOf(_snowmanxxxxxx)) {
                  return _snowmanxxxxxx.getAttachedStem().getDefaultState().with(HorizontalFacingBlock.FACING, _snowman);
               }
            }

            return _snowman;
         }
      };

      public static final Direction[] DIRECTIONS = Direction.values();

      private BuiltinLogic(Block... blocks) {
         this(false, blocks);
      }

      private BuiltinLogic(boolean var3, Block... var4) {
         for (Block _snowman : _snowman) {
            UpgradeData.BLOCK_TO_LOGIC.put(_snowman, this);
         }

         if (_snowman) {
            UpgradeData.CALLBACK_LOGICS.add(this);
         }
      }
   }

   public interface Logic {
      BlockState getUpdatedState(BlockState var1, Direction var2, BlockState var3, WorldAccess var4, BlockPos var5, BlockPos var6);

      default void postUpdate(WorldAccess world) {
      }
   }
}
