package net.minecraft.block.entity;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.EndGatewayFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EndGatewayBlockEntity extends EndPortalBlockEntity implements Tickable {
   private static final Logger LOGGER = LogManager.getLogger();
   private long age;
   private int teleportCooldown;
   @Nullable
   private BlockPos exitPortalPos;
   private boolean exactTeleport;

   public EndGatewayBlockEntity() {
      super(BlockEntityType.END_GATEWAY);
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      super.toTag(tag);
      tag.putLong("Age", this.age);
      if (this.exitPortalPos != null) {
         tag.put("ExitPortal", NbtHelper.fromBlockPos(this.exitPortalPos));
      }

      if (this.exactTeleport) {
         tag.putBoolean("ExactTeleport", this.exactTeleport);
      }

      return tag;
   }

   @Override
   public void fromTag(BlockState state, CompoundTag tag) {
      super.fromTag(state, tag);
      this.age = tag.getLong("Age");
      if (tag.contains("ExitPortal", 10)) {
         this.exitPortalPos = NbtHelper.toBlockPos(tag.getCompound("ExitPortal"));
      }

      this.exactTeleport = tag.getBoolean("ExactTeleport");
   }

   @Override
   public double getSquaredRenderDistance() {
      return 256.0;
   }

   @Override
   public void tick() {
      boolean _snowman = this.isRecentlyGenerated();
      boolean _snowmanx = this.needsCooldownBeforeTeleporting();
      this.age++;
      if (_snowmanx) {
         this.teleportCooldown--;
      } else if (!this.world.isClient) {
         List<Entity> _snowmanxx = this.world.getEntitiesByClass(Entity.class, new Box(this.getPos()), EndGatewayBlockEntity::method_30276);
         if (!_snowmanxx.isEmpty()) {
            this.tryTeleportingEntity(_snowmanxx.get(this.world.random.nextInt(_snowmanxx.size())));
         }

         if (this.age % 2400L == 0L) {
            this.startTeleportCooldown();
         }
      }

      if (_snowman != this.isRecentlyGenerated() || _snowmanx != this.needsCooldownBeforeTeleporting()) {
         this.markDirty();
      }
   }

   public static boolean method_30276(Entity _snowman) {
      return EntityPredicates.EXCEPT_SPECTATOR.test(_snowman) && !_snowman.getRootVehicle().hasNetherPortalCooldown();
   }

   public boolean isRecentlyGenerated() {
      return this.age < 200L;
   }

   public boolean needsCooldownBeforeTeleporting() {
      return this.teleportCooldown > 0;
   }

   public float getRecentlyGeneratedBeamHeight(float tickDelta) {
      return MathHelper.clamp(((float)this.age + tickDelta) / 200.0F, 0.0F, 1.0F);
   }

   public float getCooldownBeamHeight(float tickDelta) {
      return 1.0F - MathHelper.clamp(((float)this.teleportCooldown - tickDelta) / 40.0F, 0.0F, 1.0F);
   }

   @Nullable
   @Override
   public BlockEntityUpdateS2CPacket toUpdatePacket() {
      return new BlockEntityUpdateS2CPacket(this.pos, 8, this.toInitialChunkDataTag());
   }

   @Override
   public CompoundTag toInitialChunkDataTag() {
      return this.toTag(new CompoundTag());
   }

   public void startTeleportCooldown() {
      if (!this.world.isClient) {
         this.teleportCooldown = 40;
         this.world.addSyncedBlockEvent(this.getPos(), this.getCachedState().getBlock(), 1, 0);
         this.markDirty();
      }
   }

   @Override
   public boolean onSyncedBlockEvent(int type, int data) {
      if (type == 1) {
         this.teleportCooldown = 40;
         return true;
      } else {
         return super.onSyncedBlockEvent(type, data);
      }
   }

   public void tryTeleportingEntity(Entity _snowman) {
      if (this.world instanceof ServerWorld && !this.needsCooldownBeforeTeleporting()) {
         this.teleportCooldown = 100;
         if (this.exitPortalPos == null && this.world.getRegistryKey() == World.END) {
            this.createPortal((ServerWorld)this.world);
         }

         if (this.exitPortalPos != null) {
            BlockPos _snowmanx = this.exactTeleport ? this.exitPortalPos : this.findBestPortalExitPos();
            Entity _snowmanxx;
            if (_snowman instanceof EnderPearlEntity) {
               Entity _snowmanxxx = ((EnderPearlEntity)_snowman).getOwner();
               if (_snowmanxxx instanceof ServerPlayerEntity) {
                  Criteria.ENTER_BLOCK.trigger((ServerPlayerEntity)_snowmanxxx, this.world.getBlockState(this.getPos()));
               }

               if (_snowmanxxx != null) {
                  _snowmanxx = _snowmanxxx;
                  _snowman.remove();
               } else {
                  _snowmanxx = _snowman;
               }
            } else {
               _snowmanxx = _snowman.getRootVehicle();
            }

            _snowmanxx.resetNetherPortalCooldown();
            _snowmanxx.teleport((double)_snowmanx.getX() + 0.5, (double)_snowmanx.getY(), (double)_snowmanx.getZ() + 0.5);
         }

         this.startTeleportCooldown();
      }
   }

   private BlockPos findBestPortalExitPos() {
      BlockPos _snowman = findExitPortalPos(this.world, this.exitPortalPos.add(0, 2, 0), 5, false);
      LOGGER.debug("Best exit position for portal at {} is {}", this.exitPortalPos, _snowman);
      return _snowman.up();
   }

   private void createPortal(ServerWorld world) {
      Vec3d _snowman = new Vec3d((double)this.getPos().getX(), 0.0, (double)this.getPos().getZ()).normalize();
      Vec3d _snowmanx = _snowman.multiply(1024.0);

      for (int _snowmanxx = 16; getChunk(world, _snowmanx).getHighestNonEmptySectionYOffset() > 0 && _snowmanxx-- > 0; _snowmanx = _snowmanx.add(_snowman.multiply(-16.0))) {
         LOGGER.debug("Skipping backwards past nonempty chunk at {}", _snowmanx);
      }

      for (int var6 = 16; getChunk(world, _snowmanx).getHighestNonEmptySectionYOffset() == 0 && var6-- > 0; _snowmanx = _snowmanx.add(_snowman.multiply(16.0))) {
         LOGGER.debug("Skipping forward past empty chunk at {}", _snowmanx);
      }

      LOGGER.debug("Found chunk at {}", _snowmanx);
      WorldChunk _snowmanxx = getChunk(world, _snowmanx);
      this.exitPortalPos = findPortalPosition(_snowmanxx);
      if (this.exitPortalPos == null) {
         this.exitPortalPos = new BlockPos(_snowmanx.x + 0.5, 75.0, _snowmanx.z + 0.5);
         LOGGER.debug("Failed to find suitable block, settling on {}", this.exitPortalPos);
         ConfiguredFeatures.END_ISLAND
            .generate(world, world.getChunkManager().getChunkGenerator(), new Random(this.exitPortalPos.asLong()), this.exitPortalPos);
      } else {
         LOGGER.debug("Found block at {}", this.exitPortalPos);
      }

      this.exitPortalPos = findExitPortalPos(world, this.exitPortalPos, 16, true);
      LOGGER.debug("Creating portal at {}", this.exitPortalPos);
      this.exitPortalPos = this.exitPortalPos.up(10);
      this.createPortal(world, this.exitPortalPos);
      this.markDirty();
   }

   private static BlockPos findExitPortalPos(BlockView world, BlockPos pos, int searchRadius, boolean _snowman) {
      BlockPos _snowmanx = null;

      for (int _snowmanxx = -searchRadius; _snowmanxx <= searchRadius; _snowmanxx++) {
         for (int _snowmanxxx = -searchRadius; _snowmanxxx <= searchRadius; _snowmanxxx++) {
            if (_snowmanxx != 0 || _snowmanxxx != 0 || _snowman) {
               for (int _snowmanxxxx = 255; _snowmanxxxx > (_snowmanx == null ? 0 : _snowmanx.getY()); _snowmanxxxx--) {
                  BlockPos _snowmanxxxxx = new BlockPos(pos.getX() + _snowmanxx, _snowmanxxxx, pos.getZ() + _snowmanxxx);
                  BlockState _snowmanxxxxxx = world.getBlockState(_snowmanxxxxx);
                  if (_snowmanxxxxxx.isFullCube(world, _snowmanxxxxx) && (_snowman || !_snowmanxxxxxx.isOf(Blocks.BEDROCK))) {
                     _snowmanx = _snowmanxxxxx;
                     break;
                  }
               }
            }
         }
      }

      return _snowmanx == null ? pos : _snowmanx;
   }

   private static WorldChunk getChunk(World world, Vec3d pos) {
      return world.getChunk(MathHelper.floor(pos.x / 16.0), MathHelper.floor(pos.z / 16.0));
   }

   @Nullable
   private static BlockPos findPortalPosition(WorldChunk chunk) {
      ChunkPos _snowman = chunk.getPos();
      BlockPos _snowmanx = new BlockPos(_snowman.getStartX(), 30, _snowman.getStartZ());
      int _snowmanxx = chunk.getHighestNonEmptySectionYOffset() + 16 - 1;
      BlockPos _snowmanxxx = new BlockPos(_snowman.getEndX(), _snowmanxx, _snowman.getEndZ());
      BlockPos _snowmanxxxx = null;
      double _snowmanxxxxx = 0.0;

      for (BlockPos _snowmanxxxxxx : BlockPos.iterate(_snowmanx, _snowmanxxx)) {
         BlockState _snowmanxxxxxxx = chunk.getBlockState(_snowmanxxxxxx);
         BlockPos _snowmanxxxxxxxx = _snowmanxxxxxx.up();
         BlockPos _snowmanxxxxxxxxx = _snowmanxxxxxx.up(2);
         if (_snowmanxxxxxxx.isOf(Blocks.END_STONE)
            && !chunk.getBlockState(_snowmanxxxxxxxx).isFullCube(chunk, _snowmanxxxxxxxx)
            && !chunk.getBlockState(_snowmanxxxxxxxxx).isFullCube(chunk, _snowmanxxxxxxxxx)) {
            double _snowmanxxxxxxxxxx = _snowmanxxxxxx.getSquaredDistance(0.0, 0.0, 0.0, true);
            if (_snowmanxxxx == null || _snowmanxxxxxxxxxx < _snowmanxxxxx) {
               _snowmanxxxx = _snowmanxxxxxx;
               _snowmanxxxxx = _snowmanxxxxxxxxxx;
            }
         }
      }

      return _snowmanxxxx;
   }

   private void createPortal(ServerWorld world, BlockPos pos) {
      Feature.END_GATEWAY
         .configure(EndGatewayFeatureConfig.createConfig(this.getPos(), false))
         .generate(world, world.getChunkManager().getChunkGenerator(), new Random(), pos);
   }

   @Override
   public boolean shouldDrawSide(Direction direction) {
      return Block.shouldDrawSide(this.getCachedState(), this.world, this.getPos(), direction);
   }

   public int getDrawnSidesCount() {
      int _snowman = 0;

      for (Direction _snowmanx : Direction.values()) {
         _snowman += this.shouldDrawSide(_snowmanx) ? 1 : 0;
      }

      return _snowman;
   }

   public void setExitPortalPos(BlockPos pos, boolean exactTeleport) {
      this.exactTeleport = exactTeleport;
      this.exitPortalPos = pos;
   }
}
