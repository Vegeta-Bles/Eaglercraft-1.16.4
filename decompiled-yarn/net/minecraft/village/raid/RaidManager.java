package net.minecraft.village.raid;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.PersistentState;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class RaidManager extends PersistentState {
   private final Map<Integer, Raid> raids = Maps.newHashMap();
   private final ServerWorld world;
   private int nextAvailableId;
   private int currentTime;

   public RaidManager(ServerWorld world) {
      super(nameFor(world.getDimension()));
      this.world = world;
      this.nextAvailableId = 1;
      this.markDirty();
   }

   public Raid getRaid(int id) {
      return this.raids.get(id);
   }

   public void tick() {
      this.currentTime++;
      Iterator<Raid> _snowman = this.raids.values().iterator();

      while (_snowman.hasNext()) {
         Raid _snowmanx = _snowman.next();
         if (this.world.getGameRules().getBoolean(GameRules.DISABLE_RAIDS)) {
            _snowmanx.invalidate();
         }

         if (_snowmanx.hasStopped()) {
            _snowman.remove();
            this.markDirty();
         } else {
            _snowmanx.tick();
         }
      }

      if (this.currentTime % 200 == 0) {
         this.markDirty();
      }

      DebugInfoSender.sendRaids(this.world, this.raids.values());
   }

   public static boolean isValidRaiderFor(RaiderEntity raider, Raid raid) {
      return raider != null && raid != null && raid.getWorld() != null
         ? raider.isAlive() && raider.canJoinRaid() && raider.getDespawnCounter() <= 2400 && raider.world.getDimension() == raid.getWorld().getDimension()
         : false;
   }

   @Nullable
   public Raid startRaid(ServerPlayerEntity player) {
      if (player.isSpectator()) {
         return null;
      } else if (this.world.getGameRules().getBoolean(GameRules.DISABLE_RAIDS)) {
         return null;
      } else {
         DimensionType _snowman = player.world.getDimension();
         if (!_snowman.hasRaids()) {
            return null;
         } else {
            BlockPos _snowmanx = player.getBlockPos();
            List<PointOfInterest> _snowmanxx = this.world
               .getPointOfInterestStorage()
               .getInCircle(PointOfInterestType.ALWAYS_TRUE, _snowmanx, 64, PointOfInterestStorage.OccupationStatus.IS_OCCUPIED)
               .collect(Collectors.toList());
            int _snowmanxxx = 0;
            Vec3d _snowmanxxxx = Vec3d.ZERO;

            for (PointOfInterest _snowmanxxxxx : _snowmanxx) {
               BlockPos _snowmanxxxxxx = _snowmanxxxxx.getPos();
               _snowmanxxxx = _snowmanxxxx.add((double)_snowmanxxxxxx.getX(), (double)_snowmanxxxxxx.getY(), (double)_snowmanxxxxxx.getZ());
               _snowmanxxx++;
            }

            BlockPos _snowmanxxxxx;
            if (_snowmanxxx > 0) {
               _snowmanxxxx = _snowmanxxxx.multiply(1.0 / (double)_snowmanxxx);
               _snowmanxxxxx = new BlockPos(_snowmanxxxx);
            } else {
               _snowmanxxxxx = _snowmanx;
            }

            Raid _snowmanxxxxxx = this.getOrCreateRaid(player.getServerWorld(), _snowmanxxxxx);
            boolean _snowmanxxxxxxx = false;
            if (!_snowmanxxxxxx.hasStarted()) {
               if (!this.raids.containsKey(_snowmanxxxxxx.getRaidId())) {
                  this.raids.put(_snowmanxxxxxx.getRaidId(), _snowmanxxxxxx);
               }

               _snowmanxxxxxxx = true;
            } else if (_snowmanxxxxxx.getBadOmenLevel() < _snowmanxxxxxx.getMaxAcceptableBadOmenLevel()) {
               _snowmanxxxxxxx = true;
            } else {
               player.removeStatusEffect(StatusEffects.BAD_OMEN);
               player.networkHandler.sendPacket(new EntityStatusS2CPacket(player, (byte)43));
            }

            if (_snowmanxxxxxxx) {
               _snowmanxxxxxx.start(player);
               player.networkHandler.sendPacket(new EntityStatusS2CPacket(player, (byte)43));
               if (!_snowmanxxxxxx.hasSpawned()) {
                  player.incrementStat(Stats.RAID_TRIGGER);
                  Criteria.VOLUNTARY_EXILE.trigger(player);
               }
            }

            this.markDirty();
            return _snowmanxxxxxx;
         }
      }
   }

   private Raid getOrCreateRaid(ServerWorld world, BlockPos pos) {
      Raid _snowman = world.getRaidAt(pos);
      return _snowman != null ? _snowman : new Raid(this.nextId(), world, pos);
   }

   @Override
   public void fromTag(CompoundTag tag) {
      this.nextAvailableId = tag.getInt("NextAvailableID");
      this.currentTime = tag.getInt("Tick");
      ListTag _snowman = tag.getList("Raids", 10);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         CompoundTag _snowmanxx = _snowman.getCompound(_snowmanx);
         Raid _snowmanxxx = new Raid(this.world, _snowmanxx);
         this.raids.put(_snowmanxxx.getRaidId(), _snowmanxxx);
      }
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      tag.putInt("NextAvailableID", this.nextAvailableId);
      tag.putInt("Tick", this.currentTime);
      ListTag _snowman = new ListTag();

      for (Raid _snowmanx : this.raids.values()) {
         CompoundTag _snowmanxx = new CompoundTag();
         _snowmanx.toTag(_snowmanxx);
         _snowman.add(_snowmanxx);
      }

      tag.put("Raids", _snowman);
      return tag;
   }

   public static String nameFor(DimensionType _snowman) {
      return "raids" + _snowman.getSuffix();
   }

   private int nextId() {
      return ++this.nextAvailableId;
   }

   @Nullable
   public Raid getRaidAt(BlockPos pos, int _snowman) {
      Raid _snowmanx = null;
      double _snowmanxx = (double)_snowman;

      for (Raid _snowmanxxx : this.raids.values()) {
         double _snowmanxxxx = _snowmanxxx.getCenter().getSquaredDistance(pos);
         if (_snowmanxxx.isActive() && _snowmanxxxx < _snowmanxx) {
            _snowmanx = _snowmanxxx;
            _snowmanxx = _snowmanxxxx;
         }
      }

      return _snowmanx;
   }
}
