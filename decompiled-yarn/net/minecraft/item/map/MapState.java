package net.minecraft.item.map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.MapUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.BlockView;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapState extends PersistentState {
   private static final Logger field_25019 = LogManager.getLogger();
   public int xCenter;
   public int zCenter;
   public RegistryKey<World> dimension;
   public boolean showIcons;
   public boolean unlimitedTracking;
   public byte scale;
   public byte[] colors = new byte[16384];
   public boolean locked;
   public final List<MapState.PlayerUpdateTracker> updateTrackers = Lists.newArrayList();
   private final Map<PlayerEntity, MapState.PlayerUpdateTracker> updateTrackersByPlayer = Maps.newHashMap();
   private final Map<String, MapBannerMarker> banners = Maps.newHashMap();
   public final Map<String, MapIcon> icons = Maps.newLinkedHashMap();
   private final Map<String, MapFrameMarker> frames = Maps.newHashMap();

   public MapState(String _snowman) {
      super(_snowman);
   }

   public void init(int x, int z, int scale, boolean showIcons, boolean unlimitedTracking, RegistryKey<World> dimension) {
      this.scale = (byte)scale;
      this.calculateCenter((double)x, (double)z, this.scale);
      this.dimension = dimension;
      this.showIcons = showIcons;
      this.unlimitedTracking = unlimitedTracking;
      this.markDirty();
   }

   public void calculateCenter(double x, double z, int scale) {
      int _snowman = 128 * (1 << scale);
      int _snowmanx = MathHelper.floor((x + 64.0) / (double)_snowman);
      int _snowmanxx = MathHelper.floor((z + 64.0) / (double)_snowman);
      this.xCenter = _snowmanx * _snowman + _snowman / 2 - 64;
      this.zCenter = _snowmanxx * _snowman + _snowman / 2 - 64;
   }

   @Override
   public void fromTag(CompoundTag tag) {
      this.dimension = (RegistryKey<World>)DimensionType.method_28521(new Dynamic(NbtOps.INSTANCE, tag.get("dimension")))
         .resultOrPartial(field_25019::error)
         .orElseThrow(() -> new IllegalArgumentException("Invalid map dimension: " + tag.get("dimension")));
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

      ListTag _snowman = tag.getList("banners", 10);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         MapBannerMarker _snowmanxx = MapBannerMarker.fromNbt(_snowman.getCompound(_snowmanx));
         this.banners.put(_snowmanxx.getKey(), _snowmanxx);
         this.addIcon(_snowmanxx.getIconType(), null, _snowmanxx.getKey(), (double)_snowmanxx.getPos().getX(), (double)_snowmanxx.getPos().getZ(), 180.0, _snowmanxx.getName());
      }

      ListTag _snowmanx = tag.getList("frames", 10);

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
         MapFrameMarker _snowmanxxx = MapFrameMarker.fromTag(_snowmanx.getCompound(_snowmanxx));
         this.frames.put(_snowmanxxx.getKey(), _snowmanxxx);
         this.addIcon(
            MapIcon.Type.FRAME,
            null,
            "frame-" + _snowmanxxx.getEntityId(),
            (double)_snowmanxxx.getPos().getX(),
            (double)_snowmanxxx.getPos().getZ(),
            (double)_snowmanxxx.getRotation(),
            null
         );
      }
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      Identifier.CODEC.encodeStart(NbtOps.INSTANCE, this.dimension.getValue()).resultOrPartial(field_25019::error).ifPresent(_snowmanx -> tag.put("dimension", _snowmanx));
      tag.putInt("xCenter", this.xCenter);
      tag.putInt("zCenter", this.zCenter);
      tag.putByte("scale", this.scale);
      tag.putByteArray("colors", this.colors);
      tag.putBoolean("trackingPosition", this.showIcons);
      tag.putBoolean("unlimitedTracking", this.unlimitedTracking);
      tag.putBoolean("locked", this.locked);
      ListTag _snowman = new ListTag();

      for (MapBannerMarker _snowmanx : this.banners.values()) {
         _snowman.add(_snowmanx.getNbt());
      }

      tag.put("banners", _snowman);
      ListTag _snowmanx = new ListTag();

      for (MapFrameMarker _snowmanxx : this.frames.values()) {
         _snowmanx.add(_snowmanxx.toTag());
      }

      tag.put("frames", _snowmanx);
      return tag;
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
      if (!this.updateTrackersByPlayer.containsKey(player)) {
         MapState.PlayerUpdateTracker _snowman = new MapState.PlayerUpdateTracker(player);
         this.updateTrackersByPlayer.put(player, _snowman);
         this.updateTrackers.add(_snowman);
      }

      if (!player.inventory.contains(stack)) {
         this.icons.remove(player.getName().getString());
      }

      for (int _snowman = 0; _snowman < this.updateTrackers.size(); _snowman++) {
         MapState.PlayerUpdateTracker _snowmanx = this.updateTrackers.get(_snowman);
         String _snowmanxx = _snowmanx.player.getName().getString();
         if (!_snowmanx.player.removed && (_snowmanx.player.inventory.contains(stack) || stack.isInFrame())) {
            if (!stack.isInFrame() && _snowmanx.player.world.getRegistryKey() == this.dimension && this.showIcons) {
               this.addIcon(MapIcon.Type.PLAYER, _snowmanx.player.world, _snowmanxx, _snowmanx.player.getX(), _snowmanx.player.getZ(), (double)_snowmanx.player.yaw, null);
            }
         } else {
            this.updateTrackersByPlayer.remove(_snowmanx.player);
            this.updateTrackers.remove(_snowmanx);
            this.icons.remove(_snowmanxx);
         }
      }

      if (stack.isInFrame() && this.showIcons) {
         ItemFrameEntity _snowmanx = stack.getFrame();
         BlockPos _snowmanxx = _snowmanx.getDecorationBlockPos();
         MapFrameMarker _snowmanxxx = this.frames.get(MapFrameMarker.getKey(_snowmanxx));
         if (_snowmanxxx != null && _snowmanx.getEntityId() != _snowmanxxx.getEntityId() && this.frames.containsKey(_snowmanxxx.getKey())) {
            this.icons.remove("frame-" + _snowmanxxx.getEntityId());
         }

         MapFrameMarker _snowmanxxxx = new MapFrameMarker(_snowmanxx, _snowmanx.getHorizontalFacing().getHorizontal() * 90, _snowmanx.getEntityId());
         this.addIcon(
            MapIcon.Type.FRAME,
            player.world,
            "frame-" + _snowmanx.getEntityId(),
            (double)_snowmanxx.getX(),
            (double)_snowmanxx.getZ(),
            (double)(_snowmanx.getHorizontalFacing().getHorizontal() * 90),
            null
         );
         this.frames.put(_snowmanxxxx.getKey(), _snowmanxxxx);
      }

      CompoundTag _snowmanx = stack.getTag();
      if (_snowmanx != null && _snowmanx.contains("Decorations", 9)) {
         ListTag _snowmanxx = _snowmanx.getList("Decorations", 10);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
            CompoundTag _snowmanxxxx = _snowmanxx.getCompound(_snowmanxxx);
            if (!this.icons.containsKey(_snowmanxxxx.getString("id"))) {
               this.addIcon(
                  MapIcon.Type.byId(_snowmanxxxx.getByte("type")),
                  player.world,
                  _snowmanxxxx.getString("id"),
                  _snowmanxxxx.getDouble("x"),
                  _snowmanxxxx.getDouble("z"),
                  _snowmanxxxx.getDouble("rot"),
                  null
               );
            }
         }
      }
   }

   public static void addDecorationsTag(ItemStack stack, BlockPos pos, String id, MapIcon.Type type) {
      ListTag _snowman;
      if (stack.hasTag() && stack.getTag().contains("Decorations", 9)) {
         _snowman = stack.getTag().getList("Decorations", 10);
      } else {
         _snowman = new ListTag();
         stack.putSubTag("Decorations", _snowman);
      }

      CompoundTag _snowmanx = new CompoundTag();
      _snowmanx.putByte("type", type.getId());
      _snowmanx.putString("id", id);
      _snowmanx.putDouble("x", (double)pos.getX());
      _snowmanx.putDouble("z", (double)pos.getZ());
      _snowmanx.putDouble("rot", 180.0);
      _snowman.add(_snowmanx);
      if (type.hasTintColor()) {
         CompoundTag _snowmanxx = stack.getOrCreateSubTag("display");
         _snowmanxx.putInt("MapColor", type.getTintColor());
      }
   }

   private void addIcon(MapIcon.Type type, @Nullable WorldAccess world, String key, double x, double z, double rotation, @Nullable Text text) {
      int _snowman = 1 << this.scale;
      float _snowmanx = (float)(x - (double)this.xCenter) / (float)_snowman;
      float _snowmanxx = (float)(z - (double)this.zCenter) / (float)_snowman;
      byte _snowmanxxx = (byte)((int)((double)(_snowmanx * 2.0F) + 0.5));
      byte _snowmanxxxx = (byte)((int)((double)(_snowmanxx * 2.0F) + 0.5));
      int _snowmanxxxxx = 63;
      byte _snowmanxxxxxx;
      if (_snowmanx >= -63.0F && _snowmanxx >= -63.0F && _snowmanx <= 63.0F && _snowmanxx <= 63.0F) {
         rotation += rotation < 0.0 ? -8.0 : 8.0;
         _snowmanxxxxxx = (byte)((int)(rotation * 16.0 / 360.0));
         if (this.dimension == World.NETHER && world != null) {
            int _snowmanxxxxxxx = (int)(world.getLevelProperties().getTimeOfDay() / 10L);
            _snowmanxxxxxx = (byte)(_snowmanxxxxxxx * _snowmanxxxxxxx * 34187121 + _snowmanxxxxxxx * 121 >> 15 & 15);
         }
      } else {
         if (type != MapIcon.Type.PLAYER) {
            this.icons.remove(key);
            return;
         }

         int _snowmanxxxxxxx = 320;
         if (Math.abs(_snowmanx) < 320.0F && Math.abs(_snowmanxx) < 320.0F) {
            type = MapIcon.Type.PLAYER_OFF_MAP;
         } else {
            if (!this.unlimitedTracking) {
               this.icons.remove(key);
               return;
            }

            type = MapIcon.Type.PLAYER_OFF_LIMITS;
         }

         _snowmanxxxxxx = 0;
         if (_snowmanx <= -63.0F) {
            _snowmanxxx = -128;
         }

         if (_snowmanxx <= -63.0F) {
            _snowmanxxxx = -128;
         }

         if (_snowmanx >= 63.0F) {
            _snowmanxxx = 127;
         }

         if (_snowmanxx >= 63.0F) {
            _snowmanxxxx = 127;
         }
      }

      this.icons.put(key, new MapIcon(type, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxx, text));
   }

   @Nullable
   public Packet<?> getPlayerMarkerPacket(ItemStack map, BlockView world, PlayerEntity pos) {
      MapState.PlayerUpdateTracker _snowman = this.updateTrackersByPlayer.get(pos);
      return _snowman == null ? null : _snowman.getPacket(map);
   }

   public void markDirty(int x, int z) {
      this.markDirty();

      for (MapState.PlayerUpdateTracker _snowman : this.updateTrackers) {
         _snowman.markDirty(x, z);
      }
   }

   public MapState.PlayerUpdateTracker getPlayerSyncData(PlayerEntity player) {
      MapState.PlayerUpdateTracker _snowman = this.updateTrackersByPlayer.get(player);
      if (_snowman == null) {
         _snowman = new MapState.PlayerUpdateTracker(player);
         this.updateTrackersByPlayer.put(player, _snowman);
         this.updateTrackers.add(_snowman);
      }

      return _snowman;
   }

   public void addBanner(WorldAccess world, BlockPos pos) {
      double _snowman = (double)pos.getX() + 0.5;
      double _snowmanx = (double)pos.getZ() + 0.5;
      int _snowmanxx = 1 << this.scale;
      double _snowmanxxx = (_snowman - (double)this.xCenter) / (double)_snowmanxx;
      double _snowmanxxxx = (_snowmanx - (double)this.zCenter) / (double)_snowmanxx;
      int _snowmanxxxxx = 63;
      boolean _snowmanxxxxxx = false;
      if (_snowmanxxx >= -63.0 && _snowmanxxxx >= -63.0 && _snowmanxxx <= 63.0 && _snowmanxxxx <= 63.0) {
         MapBannerMarker _snowmanxxxxxxx = MapBannerMarker.fromWorldBlock(world, pos);
         if (_snowmanxxxxxxx == null) {
            return;
         }

         boolean _snowmanxxxxxxxx = true;
         if (this.banners.containsKey(_snowmanxxxxxxx.getKey()) && this.banners.get(_snowmanxxxxxxx.getKey()).equals(_snowmanxxxxxxx)) {
            this.banners.remove(_snowmanxxxxxxx.getKey());
            this.icons.remove(_snowmanxxxxxxx.getKey());
            _snowmanxxxxxxxx = false;
            _snowmanxxxxxx = true;
         }

         if (_snowmanxxxxxxxx) {
            this.banners.put(_snowmanxxxxxxx.getKey(), _snowmanxxxxxxx);
            this.addIcon(_snowmanxxxxxxx.getIconType(), world, _snowmanxxxxxxx.getKey(), _snowman, _snowmanx, 180.0, _snowmanxxxxxxx.getName());
            _snowmanxxxxxx = true;
         }

         if (_snowmanxxxxxx) {
            this.markDirty();
         }
      }
   }

   public void removeBanner(BlockView world, int x, int z) {
      Iterator<MapBannerMarker> _snowman = this.banners.values().iterator();

      while (_snowman.hasNext()) {
         MapBannerMarker _snowmanx = _snowman.next();
         if (_snowmanx.getPos().getX() == x && _snowmanx.getPos().getZ() == z) {
            MapBannerMarker _snowmanxx = MapBannerMarker.fromWorldBlock(world, _snowmanx.getPos());
            if (!_snowmanx.equals(_snowmanxx)) {
               _snowman.remove();
               this.icons.remove(_snowmanx.getKey());
            }
         }
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

      public PlayerUpdateTracker(PlayerEntity _snowman) {
         this.player = _snowman;
      }

      @Nullable
      public Packet<?> getPacket(ItemStack stack) {
         if (this.dirty) {
            this.dirty = false;
            return new MapUpdateS2CPacket(
               FilledMapItem.getMapId(stack),
               MapState.this.scale,
               MapState.this.showIcons,
               MapState.this.locked,
               MapState.this.icons.values(),
               MapState.this.colors,
               this.startX,
               this.startZ,
               this.endX + 1 - this.startX,
               this.endZ + 1 - this.startZ
            );
         } else {
            return this.emptyPacketsRequested++ % 5 == 0
               ? new MapUpdateS2CPacket(
                  FilledMapItem.getMapId(stack),
                  MapState.this.scale,
                  MapState.this.showIcons,
                  MapState.this.locked,
                  MapState.this.icons.values(),
                  MapState.this.colors,
                  0,
                  0,
                  0,
                  0
               )
               : null;
         }
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
