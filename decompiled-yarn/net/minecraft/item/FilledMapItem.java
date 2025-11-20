package net.minecraft.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.map.MapState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;

public class FilledMapItem extends NetworkSyncedItem {
   public FilledMapItem(Item.Settings _snowman) {
      super(_snowman);
   }

   public static ItemStack createMap(World world, int x, int z, byte scale, boolean showIcons, boolean unlimitedTracking) {
      ItemStack _snowman = new ItemStack(Items.FILLED_MAP);
      createMapState(_snowman, world, x, z, scale, showIcons, unlimitedTracking, world.getRegistryKey());
      return _snowman;
   }

   @Nullable
   public static MapState getMapState(ItemStack stack, World world) {
      return world.getMapState(getMapName(getMapId(stack)));
   }

   @Nullable
   public static MapState getOrCreateMapState(ItemStack map, World world) {
      MapState _snowman = getMapState(map, world);
      if (_snowman == null && world instanceof ServerWorld) {
         _snowman = createMapState(map, world, world.getLevelProperties().getSpawnX(), world.getLevelProperties().getSpawnZ(), 3, false, false, world.getRegistryKey());
      }

      return _snowman;
   }

   public static int getMapId(ItemStack stack) {
      CompoundTag _snowman = stack.getTag();
      return _snowman != null && _snowman.contains("map", 99) ? _snowman.getInt("map") : 0;
   }

   private static MapState createMapState(
      ItemStack stack, World world, int x, int z, int scale, boolean showIcons, boolean unlimitedTracking, RegistryKey<World> dimension
   ) {
      int _snowman = world.getNextMapId();
      MapState _snowmanx = new MapState(getMapName(_snowman));
      _snowmanx.init(x, z, scale, showIcons, unlimitedTracking, dimension);
      world.putMapState(_snowmanx);
      stack.getOrCreateTag().putInt("map", _snowman);
      return _snowmanx;
   }

   public static String getMapName(int mapId) {
      return "map_" + mapId;
   }

   public void updateColors(World world, Entity entity, MapState state) {
      if (world.getRegistryKey() == state.dimension && entity instanceof PlayerEntity) {
         int _snowman = 1 << state.scale;
         int _snowmanx = state.xCenter;
         int _snowmanxx = state.zCenter;
         int _snowmanxxx = MathHelper.floor(entity.getX() - (double)_snowmanx) / _snowman + 64;
         int _snowmanxxxx = MathHelper.floor(entity.getZ() - (double)_snowmanxx) / _snowman + 64;
         int _snowmanxxxxx = 128 / _snowman;
         if (world.getDimension().hasCeiling()) {
            _snowmanxxxxx /= 2;
         }

         MapState.PlayerUpdateTracker _snowmanxxxxxx = state.getPlayerSyncData((PlayerEntity)entity);
         _snowmanxxxxxx.field_131++;
         boolean _snowmanxxxxxxx = false;

         for (int _snowmanxxxxxxxx = _snowmanxxx - _snowmanxxxxx + 1; _snowmanxxxxxxxx < _snowmanxxx + _snowmanxxxxx; _snowmanxxxxxxxx++) {
            if ((_snowmanxxxxxxxx & 15) == (_snowmanxxxxxx.field_131 & 15) || _snowmanxxxxxxx) {
               _snowmanxxxxxxx = false;
               double _snowmanxxxxxxxxx = 0.0;

               for (int _snowmanxxxxxxxxxx = _snowmanxxxx - _snowmanxxxxx - 1; _snowmanxxxxxxxxxx < _snowmanxxxx + _snowmanxxxxx; _snowmanxxxxxxxxxx++) {
                  if (_snowmanxxxxxxxx >= 0 && _snowmanxxxxxxxxxx >= -1 && _snowmanxxxxxxxx < 128 && _snowmanxxxxxxxxxx < 128) {
                     int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx - _snowmanxxx;
                     int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx - _snowmanxxxx;
                     boolean _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx > (_snowmanxxxxx - 2) * (_snowmanxxxxx - 2);
                     int _snowmanxxxxxxxxxxxxxx = (_snowmanx / _snowman + _snowmanxxxxxxxx - 64) * _snowman;
                     int _snowmanxxxxxxxxxxxxxxx = (_snowmanxx / _snowman + _snowmanxxxxxxxxxx - 64) * _snowman;
                     Multiset<MaterialColor> _snowmanxxxxxxxxxxxxxxxx = LinkedHashMultiset.create();
                     WorldChunk _snowmanxxxxxxxxxxxxxxxxx = world.getWorldChunk(new BlockPos(_snowmanxxxxxxxxxxxxxx, 0, _snowmanxxxxxxxxxxxxxxx));
                     if (!_snowmanxxxxxxxxxxxxxxxxx.isEmpty()) {
                        ChunkPos _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.getPos();
                        int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx & 15;
                        int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx & 15;
                        int _snowmanxxxxxxxxxxxxxxxxxxxxx = 0;
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0.0;
                        if (world.getDimension().hasCeiling()) {
                           int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxx * 231871;
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxx * 31287121 + _snowmanxxxxxxxxxxxxxxxxxxxxxxx * 11;
                           if ((_snowmanxxxxxxxxxxxxxxxxxxxxxxx >> 20 & 1) == 0) {
                              _snowmanxxxxxxxxxxxxxxxx.add(Blocks.DIRT.getDefaultState().getTopMaterialColor(world, BlockPos.ORIGIN), 10);
                           } else {
                              _snowmanxxxxxxxxxxxxxxxx.add(Blocks.STONE.getDefaultState().getTopMaterialColor(world, BlockPos.ORIGIN), 100);
                           }

                           _snowmanxxxxxxxxxxxxxxxxxxxxxx = 100.0;
                        } else {
                           BlockPos.Mutable _snowmanxxxxxxxxxxxxxxxxxxxxxxx = new BlockPos.Mutable();
                           BlockPos.Mutable _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = new BlockPos.Mutable();

                           for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx < _snowman; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx++) {
                              for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowman; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
                                 int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.sampleHeightmap(
                                       Heightmap.Type.WORLD_SURFACE,
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx
                                    )
                                    + 1;
                                 BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                                 if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx <= 1) {
                                    _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Blocks.BEDROCK.getDefaultState();
                                 } else {
                                    do {
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxx.set(
                                          _snowmanxxxxxxxxxxxxxxxxxx.getStartX() + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                                          --_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                          _snowmanxxxxxxxxxxxxxxxxxx.getStartZ() + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx
                                       );
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxx);
                                    } while (
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getTopMaterialColor(world, _snowmanxxxxxxxxxxxxxxxxxxxxxxx) == MaterialColor.CLEAR
                                          && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx > 0
                                    );

                                    if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx > 0 && !_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFluidState().isEmpty()) {
                                       int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx - 1;
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxxxxxxxxxxxxx);

                                       BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                                       do {
                                          _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.setY(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx--);
                                          _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
                                          _snowmanxxxxxxxxxxxxxxxxxxxxx++;
                                       } while (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx > 0 && !_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getFluidState().isEmpty());

                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getFluidStateIfVisible(
                                          world, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx
                                       );
                                    }
                                 }

                                 state.removeBanner(
                                    world,
                                    _snowmanxxxxxxxxxxxxxxxxxx.getStartX() + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                                    _snowmanxxxxxxxxxxxxxxxxxx.getStartZ() + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx
                                 );
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxx += (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx / (double)(_snowman * _snowman);
                                 _snowmanxxxxxxxxxxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getTopMaterialColor(world, _snowmanxxxxxxxxxxxxxxxxxxxxxxx));
                              }
                           }
                        }

                        _snowmanxxxxxxxxxxxxxxxxxxxxx /= _snowman * _snowman;
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxx) * 4.0 / (double)(_snowman + 4)
                           + ((double)(_snowmanxxxxxxxx + _snowmanxxxxxxxxxx & 1) - 0.5) * 0.4;
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 1;
                        if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx > 0.6) {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 2;
                        }

                        if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx < -0.6) {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0;
                        }

                        MaterialColor _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = (MaterialColor)Iterables.getFirst(
                           Multisets.copyHighestCountFirst(_snowmanxxxxxxxxxxxxxxxx), MaterialColor.CLEAR
                        );
                        if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx == MaterialColor.WATER) {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxxxxxxxx * 0.1 + (double)(_snowmanxxxxxxxx + _snowmanxxxxxxxxxx & 1) * 0.2;
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 1;
                           if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx < 0.5) {
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 2;
                           }

                           if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx > 0.9) {
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0;
                           }
                        }

                        _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx;
                        if (_snowmanxxxxxxxxxx >= 0
                           && _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx < _snowmanxxxxx * _snowmanxxxxx
                           && (!_snowmanxxxxxxxxxxxxx || (_snowmanxxxxxxxx + _snowmanxxxxxxxxxx & 1) != 0)) {
                           byte _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = state.colors[_snowmanxxxxxxxx + _snowmanxxxxxxxxxx * 128];
                           byte _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = (byte)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.id * 4 + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
                           if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx != _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                              state.colors[_snowmanxxxxxxxx + _snowmanxxxxxxxxxx * 128] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                              state.markDirty(_snowmanxxxxxxxx, _snowmanxxxxxxxxxx);
                              _snowmanxxxxxxx = true;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private BlockState getFluidStateIfVisible(World world, BlockState state, BlockPos pos) {
      FluidState _snowman = state.getFluidState();
      return !_snowman.isEmpty() && !state.isSideSolidFullSquare(world, pos, Direction.UP) ? _snowman.getBlockState() : state;
   }

   private static boolean hasPositiveDepth(Biome[] biomes, int scale, int x, int z) {
      return biomes[x * scale + z * scale * 128 * scale].getDepth() >= 0.0F;
   }

   public static void fillExplorationMap(ServerWorld _snowman, ItemStack map) {
      MapState _snowmanx = getOrCreateMapState(map, _snowman);
      if (_snowmanx != null) {
         if (_snowman.getRegistryKey() == _snowmanx.dimension) {
            int _snowmanxx = 1 << _snowmanx.scale;
            int _snowmanxxx = _snowmanx.xCenter;
            int _snowmanxxxx = _snowmanx.zCenter;
            Biome[] _snowmanxxxxx = new Biome[128 * _snowmanxx * 128 * _snowmanxx];

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 128 * _snowmanxx; _snowmanxxxxxx++) {
               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 128 * _snowmanxx; _snowmanxxxxxxx++) {
                  _snowmanxxxxx[_snowmanxxxxxx * 128 * _snowmanxx + _snowmanxxxxxxx] = _snowman.getBiome(new BlockPos((_snowmanxxx / _snowmanxx - 64) * _snowmanxx + _snowmanxxxxxxx, 0, (_snowmanxxxx / _snowmanxx - 64) * _snowmanxx + _snowmanxxxxxx));
               }
            }

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 128; _snowmanxxxxxx++) {
               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 128; _snowmanxxxxxxx++) {
                  if (_snowmanxxxxxx > 0 && _snowmanxxxxxxx > 0 && _snowmanxxxxxx < 127 && _snowmanxxxxxxx < 127) {
                     Biome _snowmanxxxxxxxx = _snowmanxxxxx[_snowmanxxxxxx * _snowmanxx + _snowmanxxxxxxx * _snowmanxx * 128 * _snowmanxx];
                     int _snowmanxxxxxxxxx = 8;
                     if (hasPositiveDepth(_snowmanxxxxx, _snowmanxx, _snowmanxxxxxx - 1, _snowmanxxxxxxx - 1)) {
                        _snowmanxxxxxxxxx--;
                     }

                     if (hasPositiveDepth(_snowmanxxxxx, _snowmanxx, _snowmanxxxxxx - 1, _snowmanxxxxxxx + 1)) {
                        _snowmanxxxxxxxxx--;
                     }

                     if (hasPositiveDepth(_snowmanxxxxx, _snowmanxx, _snowmanxxxxxx - 1, _snowmanxxxxxxx)) {
                        _snowmanxxxxxxxxx--;
                     }

                     if (hasPositiveDepth(_snowmanxxxxx, _snowmanxx, _snowmanxxxxxx + 1, _snowmanxxxxxxx - 1)) {
                        _snowmanxxxxxxxxx--;
                     }

                     if (hasPositiveDepth(_snowmanxxxxx, _snowmanxx, _snowmanxxxxxx + 1, _snowmanxxxxxxx + 1)) {
                        _snowmanxxxxxxxxx--;
                     }

                     if (hasPositiveDepth(_snowmanxxxxx, _snowmanxx, _snowmanxxxxxx + 1, _snowmanxxxxxxx)) {
                        _snowmanxxxxxxxxx--;
                     }

                     if (hasPositiveDepth(_snowmanxxxxx, _snowmanxx, _snowmanxxxxxx, _snowmanxxxxxxx - 1)) {
                        _snowmanxxxxxxxxx--;
                     }

                     if (hasPositiveDepth(_snowmanxxxxx, _snowmanxx, _snowmanxxxxxx, _snowmanxxxxxxx + 1)) {
                        _snowmanxxxxxxxxx--;
                     }

                     int _snowmanxxxxxxxxxx = 3;
                     MaterialColor _snowmanxxxxxxxxxxx = MaterialColor.CLEAR;
                     if (_snowmanxxxxxxxx.getDepth() < 0.0F) {
                        _snowmanxxxxxxxxxxx = MaterialColor.ORANGE;
                        if (_snowmanxxxxxxxxx > 7 && _snowmanxxxxxxx % 2 == 0) {
                           _snowmanxxxxxxxxxx = (_snowmanxxxxxx + (int)(MathHelper.sin((float)_snowmanxxxxxxx + 0.0F) * 7.0F)) / 8 % 5;
                           if (_snowmanxxxxxxxxxx == 3) {
                              _snowmanxxxxxxxxxx = 1;
                           } else if (_snowmanxxxxxxxxxx == 4) {
                              _snowmanxxxxxxxxxx = 0;
                           }
                        } else if (_snowmanxxxxxxxxx > 7) {
                           _snowmanxxxxxxxxxxx = MaterialColor.CLEAR;
                        } else if (_snowmanxxxxxxxxx > 5) {
                           _snowmanxxxxxxxxxx = 1;
                        } else if (_snowmanxxxxxxxxx > 3) {
                           _snowmanxxxxxxxxxx = 0;
                        } else if (_snowmanxxxxxxxxx > 1) {
                           _snowmanxxxxxxxxxx = 0;
                        }
                     } else if (_snowmanxxxxxxxxx > 0) {
                        _snowmanxxxxxxxxxxx = MaterialColor.BROWN;
                        if (_snowmanxxxxxxxxx > 3) {
                           _snowmanxxxxxxxxxx = 1;
                        } else {
                           _snowmanxxxxxxxxxx = 3;
                        }
                     }

                     if (_snowmanxxxxxxxxxxx != MaterialColor.CLEAR) {
                        _snowmanx.colors[_snowmanxxxxxx + _snowmanxxxxxxx * 128] = (byte)(_snowmanxxxxxxxxxxx.id * 4 + _snowmanxxxxxxxxxx);
                        _snowmanx.markDirty(_snowmanxxxxxx, _snowmanxxxxxxx);
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
      if (!world.isClient) {
         MapState _snowman = getOrCreateMapState(stack, world);
         if (_snowman != null) {
            if (entity instanceof PlayerEntity) {
               PlayerEntity _snowmanx = (PlayerEntity)entity;
               _snowman.update(_snowmanx, stack);
            }

            if (!_snowman.locked && (selected || entity instanceof PlayerEntity && ((PlayerEntity)entity).getOffHandStack() == stack)) {
               this.updateColors(world, entity, _snowman);
            }
         }
      }
   }

   @Nullable
   @Override
   public Packet<?> createSyncPacket(ItemStack stack, World world, PlayerEntity player) {
      return getOrCreateMapState(stack, world).getPlayerMarkerPacket(stack, world, player);
   }

   @Override
   public void onCraft(ItemStack stack, World world, PlayerEntity player) {
      CompoundTag _snowman = stack.getTag();
      if (_snowman != null && _snowman.contains("map_scale_direction", 99)) {
         scale(stack, world, _snowman.getInt("map_scale_direction"));
         _snowman.remove("map_scale_direction");
      } else if (_snowman != null && _snowman.contains("map_to_lock", 1) && _snowman.getBoolean("map_to_lock")) {
         copyMap(world, stack);
         _snowman.remove("map_to_lock");
      }
   }

   protected static void scale(ItemStack map, World world, int amount) {
      MapState _snowman = getOrCreateMapState(map, world);
      if (_snowman != null) {
         createMapState(map, world, _snowman.xCenter, _snowman.zCenter, MathHelper.clamp(_snowman.scale + amount, 0, 4), _snowman.showIcons, _snowman.unlimitedTracking, _snowman.dimension);
      }
   }

   public static void copyMap(World world, ItemStack stack) {
      MapState _snowman = getOrCreateMapState(stack, world);
      if (_snowman != null) {
         MapState _snowmanx = createMapState(stack, world, 0, 0, _snowman.scale, _snowman.showIcons, _snowman.unlimitedTracking, _snowman.dimension);
         _snowmanx.copyFrom(_snowman);
      }
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
      MapState _snowman = world == null ? null : getOrCreateMapState(stack, world);
      if (_snowman != null && _snowman.locked) {
         tooltip.add(new TranslatableText("filled_map.locked", getMapId(stack)).formatted(Formatting.GRAY));
      }

      if (context.isAdvanced()) {
         if (_snowman != null) {
            tooltip.add(new TranslatableText("filled_map.id", getMapId(stack)).formatted(Formatting.GRAY));
            tooltip.add(new TranslatableText("filled_map.scale", 1 << _snowman.scale).formatted(Formatting.GRAY));
            tooltip.add(new TranslatableText("filled_map.level", _snowman.scale, 4).formatted(Formatting.GRAY));
         } else {
            tooltip.add(new TranslatableText("filled_map.unknown").formatted(Formatting.GRAY));
         }
      }
   }

   public static int getMapColor(ItemStack stack) {
      CompoundTag _snowman = stack.getSubTag("display");
      if (_snowman != null && _snowman.contains("MapColor", 99)) {
         int _snowmanx = _snowman.getInt("MapColor");
         return 0xFF000000 | _snowmanx & 16777215;
      } else {
         return -12173266;
      }
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      BlockState _snowman = context.getWorld().getBlockState(context.getBlockPos());
      if (_snowman.isIn(BlockTags.BANNERS)) {
         if (!context.getWorld().isClient) {
            MapState _snowmanx = getOrCreateMapState(context.getStack(), context.getWorld());
            _snowmanx.addBanner(context.getWorld(), context.getBlockPos());
         }

         return ActionResult.success(context.getWorld().isClient);
      } else {
         return super.useOnBlock(context);
      }
   }
}
