/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.item.map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapBannerMarker;
import net.minecraft.item.map.MapFrameMarker;
import net.minecraft.item.map.MapIcon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.MapUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.BlockView;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapState
extends PersistentState {
    private static final Logger field_25019 = LogManager.getLogger();
    public int xCenter;
    public int zCenter;
    public RegistryKey<World> dimension;
    public boolean showIcons;
    public boolean unlimitedTracking;
    public byte scale;
    public byte[] colors = new byte[16384];
    public boolean locked;
    public final List<PlayerUpdateTracker> updateTrackers = Lists.newArrayList();
    private final Map<PlayerEntity, PlayerUpdateTracker> updateTrackersByPlayer = Maps.newHashMap();
    private final Map<String, MapBannerMarker> banners = Maps.newHashMap();
    public final Map<String, MapIcon> icons = Maps.newLinkedHashMap();
    private final Map<String, MapFrameMarker> frames = Maps.newHashMap();

    public MapState(String string) {
        super(string);
    }

    public void init(int x, int z, int scale, boolean showIcons, boolean unlimitedTracking, RegistryKey<World> dimension) {
        this.scale = (byte)scale;
        this.calculateCenter(x, z, this.scale);
        this.dimension = dimension;
        this.showIcons = showIcons;
        this.unlimitedTracking = unlimitedTracking;
        this.markDirty();
    }

    public void calculateCenter(double x, double z, int scale) {
        int n = 128 * (1 << scale);
        _snowman = MathHelper.floor((x + 64.0) / (double)n);
        _snowman = MathHelper.floor((z + 64.0) / (double)n);
        this.xCenter = _snowman * n + n / 2 - 64;
        this.zCenter = _snowman * n + n / 2 - 64;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        this.dimension = (RegistryKey)DimensionType.method_28521(new Dynamic((DynamicOps)NbtOps.INSTANCE, (Object)tag.get("dimension"))).resultOrPartial(arg_0 -> ((Logger)field_25019).error(arg_0)).orElseThrow(() -> new IllegalArgumentException("Invalid map dimension: " + tag.get("dimension")));
        this.xCenter = tag.getInt("xCenter");
        this.zCenter = tag.getInt("zCenter");
        this.scale = (byte)MathHelper.clamp(tag.getByte("scale"), 0, 4);
        this.showIcons = !tag.contains("trackingPosition", 1) || tag.getBoolean("trackingPosition");
        this.unlimitedTracking = tag.getBoolean("unlimitedTracking");
        this.locked = tag.getBoolean("locked");
        this.colors = tag.getByteArray("colors");
        if (this.colors.length != 16384) {
            this.colors = new byte[16384];
        }
        ListTag listTag = tag.getList("banners", 10);
        for (int i = 0; i < listTag.size(); ++i) {
            MapBannerMarker mapBannerMarker = MapBannerMarker.fromNbt(listTag.getCompound(i));
            this.banners.put(mapBannerMarker.getKey(), mapBannerMarker);
            this.addIcon(mapBannerMarker.getIconType(), null, mapBannerMarker.getKey(), mapBannerMarker.getPos().getX(), mapBannerMarker.getPos().getZ(), 180.0, mapBannerMarker.getName());
        }
        ListTag listTag2 = tag.getList("frames", 10);
        for (int i = 0; i < listTag2.size(); ++i) {
            MapFrameMarker mapFrameMarker = MapFrameMarker.fromTag(listTag2.getCompound(i));
            this.frames.put(mapFrameMarker.getKey(), mapFrameMarker);
            this.addIcon(MapIcon.Type.FRAME, null, "frame-" + mapFrameMarker.getEntityId(), mapFrameMarker.getPos().getX(), mapFrameMarker.getPos().getZ(), mapFrameMarker.getRotation(), null);
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag2) {
        ListTag listTag;
        Identifier.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, (Object)this.dimension.getValue()).resultOrPartial(arg_0 -> ((Logger)field_25019).error(arg_0)).ifPresent(tag -> tag2.put("dimension", (Tag)tag));
        tag2.putInt("xCenter", this.xCenter);
        tag2.putInt("zCenter", this.zCenter);
        tag2.putByte("scale", this.scale);
        tag2.putByteArray("colors", this.colors);
        tag2.putBoolean("trackingPosition", this.showIcons);
        tag2.putBoolean("unlimitedTracking", this.unlimitedTracking);
        tag2.putBoolean("locked", this.locked);
        ListTag listTag2 = new ListTag();
        for (MapBannerMarker mapBannerMarker : this.banners.values()) {
            listTag2.add(mapBannerMarker.getNbt());
        }
        tag2.put("banners", listTag2);
        listTag = new ListTag();
        for (MapFrameMarker mapFrameMarker : this.frames.values()) {
            listTag.add(mapFrameMarker.toTag());
        }
        tag2.put("frames", listTag);
        return tag2;
    }

    public void copyFrom(MapState state) {
        this.locked = true;
        this.xCenter = state.xCenter;
        this.zCenter = state.zCenter;
        this.banners.putAll(state.banners);
        this.icons.putAll(state.icons);
        System.arraycopy(state.colors, 0, this.colors, 0, state.colors.length);
        this.markDirty();
    }

    public void update(PlayerEntity player, ItemStack stack) {
        Object _snowman4;
        Object _snowman2;
        if (!this.updateTrackersByPlayer.containsKey(player)) {
            PlayerUpdateTracker playerUpdateTracker = new PlayerUpdateTracker(player);
            this.updateTrackersByPlayer.put(player, playerUpdateTracker);
            this.updateTrackers.add(playerUpdateTracker);
        }
        if (!player.inventory.contains(stack)) {
            this.icons.remove(player.getName().getString());
        }
        for (int i = 0; i < this.updateTrackers.size(); ++i) {
            _snowman2 = this.updateTrackers.get(i);
            _snowman3 = ((PlayerUpdateTracker)_snowman2).player.getName().getString();
            if (((PlayerUpdateTracker)_snowman2).player.removed || !((PlayerUpdateTracker)_snowman2).player.inventory.contains(stack) && !stack.isInFrame()) {
                this.updateTrackersByPlayer.remove(((PlayerUpdateTracker)_snowman2).player);
                this.updateTrackers.remove(_snowman2);
                this.icons.remove(_snowman3);
                continue;
            }
            if (stack.isInFrame() || ((PlayerUpdateTracker)_snowman2).player.world.getRegistryKey() != this.dimension || !this.showIcons) continue;
            this.addIcon(MapIcon.Type.PLAYER, ((PlayerUpdateTracker)_snowman2).player.world, (String)_snowman3, ((PlayerUpdateTracker)_snowman2).player.getX(), ((PlayerUpdateTracker)_snowman2).player.getZ(), ((PlayerUpdateTracker)_snowman2).player.yaw, null);
        }
        if (stack.isInFrame() && this.showIcons) {
            ItemFrameEntity itemFrameEntity = stack.getFrame();
            _snowman2 = itemFrameEntity.getDecorationBlockPos();
            Object _snowman3 = this.frames.get(MapFrameMarker.getKey((BlockPos)_snowman2));
            if (_snowman3 != null && itemFrameEntity.getEntityId() != ((MapFrameMarker)_snowman3).getEntityId() && this.frames.containsKey(((MapFrameMarker)_snowman3).getKey())) {
                this.icons.remove("frame-" + ((MapFrameMarker)_snowman3).getEntityId());
            }
            _snowman4 = new MapFrameMarker((BlockPos)_snowman2, itemFrameEntity.getHorizontalFacing().getHorizontal() * 90, itemFrameEntity.getEntityId());
            this.addIcon(MapIcon.Type.FRAME, player.world, "frame-" + itemFrameEntity.getEntityId(), ((Vec3i)_snowman2).getX(), ((Vec3i)_snowman2).getZ(), itemFrameEntity.getHorizontalFacing().getHorizontal() * 90, null);
            this.frames.put(((MapFrameMarker)_snowman4).getKey(), (MapFrameMarker)_snowman4);
        }
        if ((_snowman = stack.getTag()) != null && _snowman.contains("Decorations", 9)) {
            _snowman2 = _snowman.getList("Decorations", 10);
            for (int i = 0; i < ((ListTag)_snowman2).size(); ++i) {
                _snowman4 = ((ListTag)_snowman2).getCompound(i);
                if (this.icons.containsKey(((CompoundTag)_snowman4).getString("id"))) continue;
                this.addIcon(MapIcon.Type.byId(((CompoundTag)_snowman4).getByte("type")), player.world, ((CompoundTag)_snowman4).getString("id"), ((CompoundTag)_snowman4).getDouble("x"), ((CompoundTag)_snowman4).getDouble("z"), ((CompoundTag)_snowman4).getDouble("rot"), null);
            }
        }
    }

    public static void addDecorationsTag(ItemStack stack, BlockPos pos, String id, MapIcon.Type type) {
        ListTag listTag;
        if (stack.hasTag() && stack.getTag().contains("Decorations", 9)) {
            listTag = stack.getTag().getList("Decorations", 10);
        } else {
            listTag = new ListTag();
            stack.putSubTag("Decorations", listTag);
        }
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putByte("type", type.getId());
        compoundTag.putString("id", id);
        compoundTag.putDouble("x", pos.getX());
        compoundTag.putDouble("z", pos.getZ());
        compoundTag.putDouble("rot", 180.0);
        listTag.add(compoundTag);
        if (type.hasTintColor()) {
            _snowman = stack.getOrCreateSubTag("display");
            _snowman.putInt("MapColor", type.getTintColor());
        }
    }

    private void addIcon(MapIcon.Type type, @Nullable WorldAccess world, String key, double x, double z, double rotation, @Nullable Text text) {
        byte _snowman6;
        int n = 1 << this.scale;
        float _snowman2 = (float)(x - (double)this.xCenter) / (float)n;
        float _snowman3 = (float)(z - (double)this.zCenter) / (float)n;
        byte _snowman4 = (byte)((double)(_snowman2 * 2.0f) + 0.5);
        byte _snowman5 = (byte)((double)(_snowman3 * 2.0f) + 0.5);
        _snowman = 63;
        if (_snowman2 >= -63.0f && _snowman3 >= -63.0f && _snowman2 <= 63.0f && _snowman3 <= 63.0f) {
            _snowman6 = (byte)((rotation += rotation < 0.0 ? -8.0 : 8.0) * 16.0 / 360.0);
            if (this.dimension == World.NETHER && world != null) {
                int n2 = (int)(world.getLevelProperties().getTimeOfDay() / 10L);
                _snowman6 = (byte)(n2 * n2 * 34187121 + n2 * 121 >> 15 & 0xF);
            }
        } else if (type == MapIcon.Type.PLAYER) {
            _snowman = 320;
            if (Math.abs(_snowman2) < 320.0f && Math.abs(_snowman3) < 320.0f) {
                type = MapIcon.Type.PLAYER_OFF_MAP;
            } else if (this.unlimitedTracking) {
                type = MapIcon.Type.PLAYER_OFF_LIMITS;
            } else {
                this.icons.remove(key);
                return;
            }
            _snowman6 = 0;
            if (_snowman2 <= -63.0f) {
                _snowman4 = -128;
            }
            if (_snowman3 <= -63.0f) {
                _snowman5 = -128;
            }
            if (_snowman2 >= 63.0f) {
                _snowman4 = 127;
            }
            if (_snowman3 >= 63.0f) {
                _snowman5 = 127;
            }
        } else {
            this.icons.remove(key);
            return;
        }
        this.icons.put(key, new MapIcon(type, _snowman4, _snowman5, _snowman6, text));
    }

    @Nullable
    public Packet<?> getPlayerMarkerPacket(ItemStack map, BlockView world, PlayerEntity pos) {
        PlayerUpdateTracker playerUpdateTracker = this.updateTrackersByPlayer.get(pos);
        if (playerUpdateTracker == null) {
            return null;
        }
        return playerUpdateTracker.getPacket(map);
    }

    public void markDirty(int x, int z) {
        this.markDirty();
        for (PlayerUpdateTracker playerUpdateTracker : this.updateTrackers) {
            playerUpdateTracker.markDirty(x, z);
        }
    }

    public PlayerUpdateTracker getPlayerSyncData(PlayerEntity player) {
        PlayerUpdateTracker playerUpdateTracker = this.updateTrackersByPlayer.get(player);
        if (playerUpdateTracker == null) {
            playerUpdateTracker = new PlayerUpdateTracker(player);
            this.updateTrackersByPlayer.put(player, playerUpdateTracker);
            this.updateTrackers.add(playerUpdateTracker);
        }
        return playerUpdateTracker;
    }

    public void addBanner(WorldAccess world, BlockPos pos) {
        double d = (double)pos.getX() + 0.5;
        _snowman = (double)pos.getZ() + 0.5;
        int _snowman2 = 1 << this.scale;
        _snowman = (d - (double)this.xCenter) / (double)_snowman2;
        _snowman = (_snowman - (double)this.zCenter) / (double)_snowman2;
        int _snowman3 = 63;
        boolean _snowman4 = false;
        if (_snowman >= -63.0 && _snowman >= -63.0 && _snowman <= 63.0 && _snowman <= 63.0) {
            MapBannerMarker mapBannerMarker = MapBannerMarker.fromWorldBlock(world, pos);
            if (mapBannerMarker == null) {
                return;
            }
            boolean _snowman5 = true;
            if (this.banners.containsKey(mapBannerMarker.getKey()) && this.banners.get(mapBannerMarker.getKey()).equals(mapBannerMarker)) {
                this.banners.remove(mapBannerMarker.getKey());
                this.icons.remove(mapBannerMarker.getKey());
                _snowman5 = false;
                _snowman4 = true;
            }
            if (_snowman5) {
                this.banners.put(mapBannerMarker.getKey(), mapBannerMarker);
                this.addIcon(mapBannerMarker.getIconType(), world, mapBannerMarker.getKey(), d, _snowman, 180.0, mapBannerMarker.getName());
                _snowman4 = true;
            }
            if (_snowman4) {
                this.markDirty();
            }
        }
    }

    public void removeBanner(BlockView world, int x, int z) {
        Iterator<MapBannerMarker> iterator = this.banners.values().iterator();
        while (iterator.hasNext()) {
            MapBannerMarker mapBannerMarker = iterator.next();
            if (mapBannerMarker.getPos().getX() != x || mapBannerMarker.getPos().getZ() != z || mapBannerMarker.equals(_snowman = MapBannerMarker.fromWorldBlock(world, mapBannerMarker.getPos()))) continue;
            iterator.remove();
            this.icons.remove(mapBannerMarker.getKey());
        }
    }

    public void removeFrame(BlockPos pos, int id) {
        this.icons.remove("frame-" + id);
        this.frames.remove(MapFrameMarker.getKey(pos));
    }

    public class PlayerUpdateTracker {
        public final PlayerEntity player;
        private boolean dirty = true;
        private int startX;
        private int startZ;
        private int endX = 127;
        private int endZ = 127;
        private int emptyPacketsRequested;
        public int field_131;

        public PlayerUpdateTracker(PlayerEntity playerEntity) {
            this.player = playerEntity;
        }

        @Nullable
        public Packet<?> getPacket(ItemStack stack) {
            if (this.dirty) {
                this.dirty = false;
                return new MapUpdateS2CPacket(FilledMapItem.getMapId(stack), MapState.this.scale, MapState.this.showIcons, MapState.this.locked, MapState.this.icons.values(), MapState.this.colors, this.startX, this.startZ, this.endX + 1 - this.startX, this.endZ + 1 - this.startZ);
            }
            if (this.emptyPacketsRequested++ % 5 == 0) {
                return new MapUpdateS2CPacket(FilledMapItem.getMapId(stack), MapState.this.scale, MapState.this.showIcons, MapState.this.locked, MapState.this.icons.values(), MapState.this.colors, 0, 0, 0, 0);
            }
            return null;
        }

        public void markDirty(int x, int z) {
            if (this.dirty) {
                this.startX = Math.min(this.startX, x);
                this.startZ = Math.min(this.startZ, z);
                this.endX = Math.max(this.endX, x);
                this.endZ = Math.max(this.endZ, z);
            } else {
                this.dirty = true;
                this.startX = x;
                this.startZ = z;
                this.endX = x;
                this.endZ = z;
            }
        }
    }
}

