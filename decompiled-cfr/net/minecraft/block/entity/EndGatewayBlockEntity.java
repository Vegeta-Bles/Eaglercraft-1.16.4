/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.block.entity;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.EndPortalBlockEntity;
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
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.EndGatewayFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EndGatewayBlockEntity
extends EndPortalBlockEntity
implements Tickable {
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
        boolean bl = this.isRecentlyGenerated();
        _snowman = this.needsCooldownBeforeTeleporting();
        ++this.age;
        if (_snowman) {
            --this.teleportCooldown;
        } else if (!this.world.isClient) {
            List<Entity> list = this.world.getEntitiesByClass(Entity.class, new Box(this.getPos()), EndGatewayBlockEntity::method_30276);
            if (!list.isEmpty()) {
                this.tryTeleportingEntity(list.get(this.world.random.nextInt(list.size())));
            }
            if (this.age % 2400L == 0L) {
                this.startTeleportCooldown();
            }
        }
        if (bl != this.isRecentlyGenerated() || _snowman != this.needsCooldownBeforeTeleporting()) {
            this.markDirty();
        }
    }

    public static boolean method_30276(Entity entity) {
        return EntityPredicates.EXCEPT_SPECTATOR.test(entity) && !entity.getRootVehicle().hasNetherPortalCooldown();
    }

    public boolean isRecentlyGenerated() {
        return this.age < 200L;
    }

    public boolean needsCooldownBeforeTeleporting() {
        return this.teleportCooldown > 0;
    }

    public float getRecentlyGeneratedBeamHeight(float tickDelta) {
        return MathHelper.clamp(((float)this.age + tickDelta) / 200.0f, 0.0f, 1.0f);
    }

    public float getCooldownBeamHeight(float tickDelta) {
        return 1.0f - MathHelper.clamp(((float)this.teleportCooldown - tickDelta) / 40.0f, 0.0f, 1.0f);
    }

    @Override
    @Nullable
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
        }
        return super.onSyncedBlockEvent(type, data);
    }

    public void tryTeleportingEntity(Entity entity) {
        if (!(this.world instanceof ServerWorld) || this.needsCooldownBeforeTeleporting()) {
            return;
        }
        this.teleportCooldown = 100;
        if (this.exitPortalPos == null && this.world.getRegistryKey() == World.END) {
            this.createPortal((ServerWorld)this.world);
        }
        if (this.exitPortalPos != null) {
            BlockPos blockPos = _snowman = this.exactTeleport ? this.exitPortalPos : this.findBestPortalExitPos();
            if (entity instanceof EnderPearlEntity) {
                _snowman = ((EnderPearlEntity)entity).getOwner();
                if (_snowman instanceof ServerPlayerEntity) {
                    Criteria.ENTER_BLOCK.trigger((ServerPlayerEntity)_snowman, this.world.getBlockState(this.getPos()));
                }
                if (_snowman != null) {
                    _snowman = _snowman;
                    entity.remove();
                } else {
                    _snowman = entity;
                }
            } else {
                _snowman = entity.getRootVehicle();
            }
            _snowman.resetNetherPortalCooldown();
            _snowman.teleport((double)_snowman.getX() + 0.5, _snowman.getY(), (double)_snowman.getZ() + 0.5);
        }
        this.startTeleportCooldown();
    }

    private BlockPos findBestPortalExitPos() {
        BlockPos blockPos = EndGatewayBlockEntity.findExitPortalPos(this.world, this.exitPortalPos.add(0, 2, 0), 5, false);
        LOGGER.debug("Best exit position for portal at {} is {}", (Object)this.exitPortalPos, (Object)blockPos);
        return blockPos.up();
    }

    private void createPortal(ServerWorld world) {
        Vec3d vec3d = new Vec3d(this.getPos().getX(), 0.0, this.getPos().getZ()).normalize();
        _snowman = vec3d.multiply(1024.0);
        int _snowman2 = 16;
        while (EndGatewayBlockEntity.getChunk(world, _snowman).getHighestNonEmptySectionYOffset() > 0 && _snowman2-- > 0) {
            LOGGER.debug("Skipping backwards past nonempty chunk at {}", (Object)_snowman);
            _snowman = _snowman.add(vec3d.multiply(-16.0));
        }
        _snowman2 = 16;
        while (EndGatewayBlockEntity.getChunk(world, _snowman).getHighestNonEmptySectionYOffset() == 0 && _snowman2-- > 0) {
            LOGGER.debug("Skipping forward past empty chunk at {}", (Object)_snowman);
            _snowman = _snowman.add(vec3d.multiply(16.0));
        }
        LOGGER.debug("Found chunk at {}", (Object)_snowman);
        WorldChunk _snowman3 = EndGatewayBlockEntity.getChunk(world, _snowman);
        this.exitPortalPos = EndGatewayBlockEntity.findPortalPosition(_snowman3);
        if (this.exitPortalPos == null) {
            this.exitPortalPos = new BlockPos(_snowman.x + 0.5, 75.0, _snowman.z + 0.5);
            LOGGER.debug("Failed to find suitable block, settling on {}", (Object)this.exitPortalPos);
            ConfiguredFeatures.END_ISLAND.generate(world, world.getChunkManager().getChunkGenerator(), new Random(this.exitPortalPos.asLong()), this.exitPortalPos);
        } else {
            LOGGER.debug("Found block at {}", (Object)this.exitPortalPos);
        }
        this.exitPortalPos = EndGatewayBlockEntity.findExitPortalPos(world, this.exitPortalPos, 16, true);
        LOGGER.debug("Creating portal at {}", (Object)this.exitPortalPos);
        this.exitPortalPos = this.exitPortalPos.up(10);
        this.createPortal(world, this.exitPortalPos);
        this.markDirty();
    }

    private static BlockPos findExitPortalPos(BlockView world, BlockPos pos, int searchRadius, boolean bl) {
        Vec3i _snowman3 = null;
        for (int i = -searchRadius; i <= searchRadius; ++i) {
            block1: for (_snowman = -searchRadius; _snowman <= searchRadius; ++_snowman) {
                if (i == 0 && _snowman == 0 && !bl) continue;
                for (_snowman = 255; _snowman > (_snowman3 == null ? 0 : _snowman3.getY()); --_snowman) {
                    BlockPos blockPos = new BlockPos(pos.getX() + i, _snowman, pos.getZ() + _snowman);
                    BlockState _snowman2 = world.getBlockState(blockPos);
                    if (!_snowman2.isFullCube(world, blockPos) || !bl && _snowman2.isOf(Blocks.BEDROCK)) continue;
                    _snowman3 = blockPos;
                    continue block1;
                }
            }
        }
        return _snowman3 == null ? pos : _snowman3;
    }

    private static WorldChunk getChunk(World world, Vec3d pos) {
        return world.getChunk(MathHelper.floor(pos.x / 16.0), MathHelper.floor(pos.z / 16.0));
    }

    @Nullable
    private static BlockPos findPortalPosition(WorldChunk chunk) {
        ChunkPos chunkPos = chunk.getPos();
        BlockPos _snowman2 = new BlockPos(chunkPos.getStartX(), 30, chunkPos.getStartZ());
        int _snowman3 = chunk.getHighestNonEmptySectionYOffset() + 16 - 1;
        BlockPos _snowman4 = new BlockPos(chunkPos.getEndX(), _snowman3, chunkPos.getEndZ());
        BlockPos _snowman5 = null;
        double _snowman6 = 0.0;
        for (BlockPos blockPos : BlockPos.iterate(_snowman2, _snowman4)) {
            BlockState blockState = chunk.getBlockState(blockPos);
            BlockPos _snowman7 = blockPos.up();
            BlockPos _snowman8 = blockPos.up(2);
            if (!blockState.isOf(Blocks.END_STONE) || chunk.getBlockState(_snowman7).isFullCube(chunk, _snowman7) || chunk.getBlockState(_snowman8).isFullCube(chunk, _snowman8)) continue;
            double _snowman9 = blockPos.getSquaredDistance(0.0, 0.0, 0.0, true);
            if (_snowman5 != null && !(_snowman9 < _snowman6)) continue;
            _snowman5 = blockPos;
            _snowman6 = _snowman9;
        }
        return _snowman5;
    }

    private void createPortal(ServerWorld world, BlockPos pos) {
        Feature.END_GATEWAY.configure(EndGatewayFeatureConfig.createConfig(this.getPos(), false)).generate(world, world.getChunkManager().getChunkGenerator(), new Random(), pos);
    }

    @Override
    public boolean shouldDrawSide(Direction direction) {
        return Block.shouldDrawSide(this.getCachedState(), this.world, this.getPos(), direction);
    }

    public int getDrawnSidesCount() {
        int n = 0;
        for (Direction direction : Direction.values()) {
            n += this.shouldDrawSide(direction) ? 1 : 0;
        }
        return n;
    }

    public void setExitPortalPos(BlockPos pos, boolean exactTeleport) {
        this.exactTeleport = exactTeleport;
        this.exitPortalPos = pos;
    }
}

