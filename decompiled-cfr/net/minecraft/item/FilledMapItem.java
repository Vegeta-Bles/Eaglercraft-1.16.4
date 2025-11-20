/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.LinkedHashMultiset
 *  com.google.common.collect.Multiset
 *  com.google.common.collect.Multisets
 *  javax.annotation.Nullable
 */
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.NetworkSyncedItem;
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

public class FilledMapItem
extends NetworkSyncedItem {
    public FilledMapItem(Item.Settings settings) {
        super(settings);
    }

    public static ItemStack createMap(World world, int x, int z, byte scale, boolean showIcons, boolean unlimitedTracking) {
        ItemStack itemStack = new ItemStack(Items.FILLED_MAP);
        FilledMapItem.createMapState(itemStack, world, x, z, scale, showIcons, unlimitedTracking, world.getRegistryKey());
        return itemStack;
    }

    @Nullable
    public static MapState getMapState(ItemStack stack, World world) {
        return world.getMapState(FilledMapItem.getMapName(FilledMapItem.getMapId(stack)));
    }

    @Nullable
    public static MapState getOrCreateMapState(ItemStack map, World world) {
        MapState mapState = FilledMapItem.getMapState(map, world);
        if (mapState == null && world instanceof ServerWorld) {
            mapState = FilledMapItem.createMapState(map, world, world.getLevelProperties().getSpawnX(), world.getLevelProperties().getSpawnZ(), 3, false, false, world.getRegistryKey());
        }
        return mapState;
    }

    public static int getMapId(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        return compoundTag != null && compoundTag.contains("map", 99) ? compoundTag.getInt("map") : 0;
    }

    private static MapState createMapState(ItemStack stack, World world, int x, int z, int scale, boolean showIcons, boolean unlimitedTracking, RegistryKey<World> dimension) {
        int n = world.getNextMapId();
        MapState _snowman2 = new MapState(FilledMapItem.getMapName(n));
        _snowman2.init(x, z, scale, showIcons, unlimitedTracking, dimension);
        world.putMapState(_snowman2);
        stack.getOrCreateTag().putInt("map", n);
        return _snowman2;
    }

    public static String getMapName(int mapId) {
        return "map_" + mapId;
    }

    public void updateColors(World world, Entity entity, MapState state) {
        if (world.getRegistryKey() != state.dimension || !(entity instanceof PlayerEntity)) {
            return;
        }
        int n = 1 << state.scale;
        _snowman = state.xCenter;
        _snowman = state.zCenter;
        _snowman = MathHelper.floor(entity.getX() - (double)_snowman) / n + 64;
        _snowman = MathHelper.floor(entity.getZ() - (double)_snowman) / n + 64;
        _snowman = 128 / n;
        if (world.getDimension().hasCeiling()) {
            _snowman /= 2;
        }
        MapState.PlayerUpdateTracker _snowman2 = state.getPlayerSyncData((PlayerEntity)entity);
        ++_snowman2.field_131;
        boolean _snowman3 = false;
        for (_snowman = _snowman - _snowman + 1; _snowman < _snowman + _snowman; ++_snowman) {
            if ((_snowman & 0xF) != (_snowman2.field_131 & 0xF) && !_snowman3) continue;
            _snowman3 = false;
            double _snowman14 = 0.0;
            for (int i = _snowman - _snowman - 1; i < _snowman + _snowman; ++i) {
                if (_snowman < 0 || i < -1 || _snowman >= 128 || i >= 128) continue;
                _snowman = _snowman - _snowman;
                _snowman = i - _snowman;
                boolean bl = _snowman * _snowman + _snowman * _snowman > (_snowman - 2) * (_snowman - 2);
                int _snowman4 = (_snowman / n + _snowman - 64) * n;
                int _snowman5 = (_snowman / n + i - 64) * n;
                LinkedHashMultiset _snowman6 = LinkedHashMultiset.create();
                WorldChunk _snowman7 = world.getWorldChunk(new BlockPos(_snowman4, 0, _snowman5));
                if (_snowman7.isEmpty()) continue;
                ChunkPos _snowman8 = _snowman7.getPos();
                int _snowman9 = _snowman4 & 0xF;
                int _snowman10 = _snowman5 & 0xF;
                int _snowman11 = 0;
                double _snowman12 = 0.0;
                if (world.getDimension().hasCeiling()) {
                    int n2 = _snowman4 + _snowman5 * 231871;
                    if (((n2 = n2 * n2 * 31287121 + n2 * 11) >> 20 & 1) == 0) {
                        _snowman6.add((Object)Blocks.DIRT.getDefaultState().getTopMaterialColor(world, BlockPos.ORIGIN), 10);
                    } else {
                        _snowman6.add((Object)Blocks.STONE.getDefaultState().getTopMaterialColor(world, BlockPos.ORIGIN), 100);
                    }
                    _snowman12 = 100.0;
                } else {
                    BlockPos.Mutable mutable = new BlockPos.Mutable();
                    _snowman = new BlockPos.Mutable();
                    for (int j = 0; j < n; ++j) {
                        for (_snowman = 0; _snowman < n; ++_snowman) {
                            BlockState blockState;
                            _snowman = _snowman7.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, j + _snowman9, _snowman + _snowman10) + 1;
                            if (_snowman > 1) {
                                do {
                                    mutable.set(_snowman8.getStartX() + j + _snowman9, --_snowman, _snowman8.getStartZ() + _snowman + _snowman10);
                                } while ((blockState = _snowman7.getBlockState(mutable)).getTopMaterialColor(world, mutable) == MaterialColor.CLEAR && _snowman > 0);
                                if (_snowman > 0 && !blockState.getFluidState().isEmpty()) {
                                    _snowman = _snowman - 1;
                                    _snowman.set(mutable);
                                    do {
                                        _snowman.setY(_snowman--);
                                        BlockState blockState2 = _snowman7.getBlockState(_snowman);
                                        ++_snowman11;
                                    } while (_snowman > 0 && !blockState2.getFluidState().isEmpty());
                                    blockState = this.getFluidStateIfVisible(world, blockState, mutable);
                                }
                            } else {
                                blockState = Blocks.BEDROCK.getDefaultState();
                            }
                            state.removeBanner(world, _snowman8.getStartX() + j + _snowman9, _snowman8.getStartZ() + _snowman + _snowman10);
                            _snowman12 += (double)_snowman / (double)(n * n);
                            _snowman6.add((Object)blockState.getTopMaterialColor(world, mutable));
                        }
                    }
                }
                _snowman11 /= n * n;
                double _snowman13 = (_snowman12 - _snowman14) * 4.0 / (double)(n + 4) + ((double)(_snowman + i & 1) - 0.5) * 0.4;
                j = 1;
                if (_snowman13 > 0.6) {
                    j = 2;
                }
                if (_snowman13 < -0.6) {
                    j = 0;
                }
                if ((_snowman = (MaterialColor)Iterables.getFirst((Iterable)Multisets.copyHighestCountFirst((Multiset)_snowman6), (Object)MaterialColor.CLEAR)) == MaterialColor.WATER) {
                    _snowman13 = (double)_snowman11 * 0.1 + (double)(_snowman + i & 1) * 0.2;
                    j = 1;
                    if (_snowman13 < 0.5) {
                        j = 2;
                    }
                    if (_snowman13 > 0.9) {
                        j = 0;
                    }
                }
                _snowman14 = _snowman12;
                if (i < 0 || _snowman * _snowman + _snowman * _snowman >= _snowman * _snowman || bl && (_snowman + i & 1) == 0 || (_snowman = state.colors[_snowman + i * 128]) == (_snowman = (byte)(_snowman.id * 4 + j))) continue;
                state.colors[_snowman + i * 128] = _snowman;
                state.markDirty(_snowman, i);
                _snowman3 = true;
            }
        }
    }

    private BlockState getFluidStateIfVisible(World world, BlockState state, BlockPos pos) {
        FluidState fluidState = state.getFluidState();
        if (!fluidState.isEmpty() && !state.isSideSolidFullSquare(world, pos, Direction.UP)) {
            return fluidState.getBlockState();
        }
        return state;
    }

    private static boolean hasPositiveDepth(Biome[] biomes, int scale, int x, int z) {
        return biomes[x * scale + z * scale * 128 * scale].getDepth() >= 0.0f;
    }

    public static void fillExplorationMap(ServerWorld serverWorld, ItemStack map) {
        int n;
        MapState mapState = FilledMapItem.getOrCreateMapState(map, serverWorld);
        if (mapState == null) {
            return;
        }
        if (serverWorld.getRegistryKey() != mapState.dimension) {
            return;
        }
        int _snowman2 = 1 << mapState.scale;
        int _snowman3 = mapState.xCenter;
        int _snowman4 = mapState.zCenter;
        Biome[] _snowman5 = new Biome[128 * _snowman2 * 128 * _snowman2];
        for (n = 0; n < 128 * _snowman2; ++n) {
            for (_snowman = 0; _snowman < 128 * _snowman2; ++_snowman) {
                _snowman5[n * 128 * _snowman2 + _snowman] = serverWorld.getBiome(new BlockPos((_snowman3 / _snowman2 - 64) * _snowman2 + _snowman, 0, (_snowman4 / _snowman2 - 64) * _snowman2 + n));
            }
        }
        for (n = 0; n < 128; ++n) {
            for (_snowman = 0; _snowman < 128; ++_snowman) {
                if (n <= 0 || _snowman <= 0 || n >= 127 || _snowman >= 127) continue;
                Biome biome = _snowman5[n * _snowman2 + _snowman * _snowman2 * 128 * _snowman2];
                int _snowman6 = 8;
                if (FilledMapItem.hasPositiveDepth(_snowman5, _snowman2, n - 1, _snowman - 1)) {
                    --_snowman6;
                }
                if (FilledMapItem.hasPositiveDepth(_snowman5, _snowman2, n - 1, _snowman + 1)) {
                    --_snowman6;
                }
                if (FilledMapItem.hasPositiveDepth(_snowman5, _snowman2, n - 1, _snowman)) {
                    --_snowman6;
                }
                if (FilledMapItem.hasPositiveDepth(_snowman5, _snowman2, n + 1, _snowman - 1)) {
                    --_snowman6;
                }
                if (FilledMapItem.hasPositiveDepth(_snowman5, _snowman2, n + 1, _snowman + 1)) {
                    --_snowman6;
                }
                if (FilledMapItem.hasPositiveDepth(_snowman5, _snowman2, n + 1, _snowman)) {
                    --_snowman6;
                }
                if (FilledMapItem.hasPositiveDepth(_snowman5, _snowman2, n, _snowman - 1)) {
                    --_snowman6;
                }
                if (FilledMapItem.hasPositiveDepth(_snowman5, _snowman2, n, _snowman + 1)) {
                    --_snowman6;
                }
                int _snowman7 = 3;
                MaterialColor _snowman8 = MaterialColor.CLEAR;
                if (biome.getDepth() < 0.0f) {
                    _snowman8 = MaterialColor.ORANGE;
                    if (_snowman6 > 7 && _snowman % 2 == 0) {
                        _snowman7 = (n + (int)(MathHelper.sin((float)_snowman + 0.0f) * 7.0f)) / 8 % 5;
                        if (_snowman7 == 3) {
                            _snowman7 = 1;
                        } else if (_snowman7 == 4) {
                            _snowman7 = 0;
                        }
                    } else if (_snowman6 > 7) {
                        _snowman8 = MaterialColor.CLEAR;
                    } else if (_snowman6 > 5) {
                        _snowman7 = 1;
                    } else if (_snowman6 > 3) {
                        _snowman7 = 0;
                    } else if (_snowman6 > 1) {
                        _snowman7 = 0;
                    }
                } else if (_snowman6 > 0) {
                    _snowman8 = MaterialColor.BROWN;
                    _snowman7 = _snowman6 > 3 ? 1 : 3;
                }
                if (_snowman8 == MaterialColor.CLEAR) continue;
                mapState.colors[n + _snowman * 128] = (byte)(_snowman8.id * 4 + _snowman7);
                mapState.markDirty(n, _snowman);
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) {
            return;
        }
        MapState mapState = FilledMapItem.getOrCreateMapState(stack, world);
        if (mapState == null) {
            return;
        }
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)entity;
            mapState.update(playerEntity, stack);
        }
        if (!mapState.locked && (selected || entity instanceof PlayerEntity && ((PlayerEntity)entity).getOffHandStack() == stack)) {
            this.updateColors(world, entity, mapState);
        }
    }

    @Override
    @Nullable
    public Packet<?> createSyncPacket(ItemStack stack, World world, PlayerEntity player) {
        return FilledMapItem.getOrCreateMapState(stack, world).getPlayerMarkerPacket(stack, world, player);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null && compoundTag.contains("map_scale_direction", 99)) {
            FilledMapItem.scale(stack, world, compoundTag.getInt("map_scale_direction"));
            compoundTag.remove("map_scale_direction");
        } else if (compoundTag != null && compoundTag.contains("map_to_lock", 1) && compoundTag.getBoolean("map_to_lock")) {
            FilledMapItem.copyMap(world, stack);
            compoundTag.remove("map_to_lock");
        }
    }

    protected static void scale(ItemStack map, World world, int amount) {
        MapState mapState = FilledMapItem.getOrCreateMapState(map, world);
        if (mapState != null) {
            FilledMapItem.createMapState(map, world, mapState.xCenter, mapState.zCenter, MathHelper.clamp(mapState.scale + amount, 0, 4), mapState.showIcons, mapState.unlimitedTracking, mapState.dimension);
        }
    }

    public static void copyMap(World world, ItemStack stack) {
        MapState mapState = FilledMapItem.getOrCreateMapState(stack, world);
        if (mapState != null) {
            _snowman = FilledMapItem.createMapState(stack, world, 0, 0, mapState.scale, mapState.showIcons, mapState.unlimitedTracking, mapState.dimension);
            _snowman.copyFrom(mapState);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        MapState mapState;
        MapState mapState2 = mapState = world == null ? null : FilledMapItem.getOrCreateMapState(stack, world);
        if (mapState != null && mapState.locked) {
            tooltip.add(new TranslatableText("filled_map.locked", FilledMapItem.getMapId(stack)).formatted(Formatting.GRAY));
        }
        if (context.isAdvanced()) {
            if (mapState != null) {
                tooltip.add(new TranslatableText("filled_map.id", FilledMapItem.getMapId(stack)).formatted(Formatting.GRAY));
                tooltip.add(new TranslatableText("filled_map.scale", 1 << mapState.scale).formatted(Formatting.GRAY));
                tooltip.add(new TranslatableText("filled_map.level", mapState.scale, 4).formatted(Formatting.GRAY));
            } else {
                tooltip.add(new TranslatableText("filled_map.unknown").formatted(Formatting.GRAY));
            }
        }
    }

    public static int getMapColor(ItemStack stack) {
        CompoundTag compoundTag = stack.getSubTag("display");
        if (compoundTag != null && compoundTag.contains("MapColor", 99)) {
            int n = compoundTag.getInt("MapColor");
            return 0xFF000000 | n & 0xFFFFFF;
        }
        return -12173266;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());
        if (blockState.isIn(BlockTags.BANNERS)) {
            if (!context.getWorld().isClient) {
                MapState mapState = FilledMapItem.getOrCreateMapState(context.getStack(), context.getWorld());
                mapState.addBanner(context.getWorld(), context.getBlockPos());
            }
            return ActionResult.success(context.getWorld().isClient);
        }
        return super.useOnBlock(context);
    }
}

