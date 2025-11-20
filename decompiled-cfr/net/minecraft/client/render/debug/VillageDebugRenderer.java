/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.render.debug;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.render.debug.NameGenerator;
import net.minecraft.client.render.debug.PathfindingDebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3i;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VillageDebugRenderer
implements DebugRenderer.Renderer {
    private static final Logger LOGGER = LogManager.getLogger();
    private final MinecraftClient client;
    private final Map<BlockPos, PointOfInterest> pointsOfInterest = Maps.newHashMap();
    private final Map<UUID, Brain> brains = Maps.newHashMap();
    @Nullable
    private UUID targetedEntity;

    public VillageDebugRenderer(MinecraftClient minecraftClient) {
        this.client = minecraftClient;
    }

    @Override
    public void clear() {
        this.pointsOfInterest.clear();
        this.brains.clear();
        this.targetedEntity = null;
    }

    public void addPointOfInterest(PointOfInterest pointOfInterest) {
        this.pointsOfInterest.put(pointOfInterest.pos, pointOfInterest);
    }

    public void removePointOfInterest(BlockPos blockPos) {
        this.pointsOfInterest.remove(blockPos);
    }

    public void setFreeTicketCount(BlockPos pos, int freeTicketCount) {
        PointOfInterest pointOfInterest = this.pointsOfInterest.get(pos);
        if (pointOfInterest == null) {
            LOGGER.warn("Strange, setFreeTicketCount was called for an unknown POI: " + pos);
            return;
        }
        pointOfInterest.freeTicketCount = freeTicketCount;
    }

    public void addBrain(Brain brain) {
        this.brains.put(brain.uuid, brain);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        this.method_24805();
        this.method_23135(cameraX, cameraY, cameraZ);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
        if (!this.client.player.isSpectator()) {
            this.updateTargetedEntity();
        }
    }

    private void method_24805() {
        this.brains.entrySet().removeIf(entry -> {
            Entity entity = this.client.world.getEntityById(((Brain)entry.getValue()).field_18924);
            return entity == null || entity.removed;
        });
    }

    private void method_23135(double d, double d2, double d3) {
        BlockPos blockPos = new BlockPos(d, d2, d3);
        this.brains.values().forEach(brain -> {
            if (this.isClose((Brain)brain)) {
                this.drawBrain((Brain)brain, d, d2, d3);
            }
        });
        for (BlockPos blockPos3 : this.pointsOfInterest.keySet()) {
            if (!blockPos.isWithinDistance(blockPos3, 30.0)) continue;
            VillageDebugRenderer.drawPointOfInterest(blockPos3);
        }
        this.pointsOfInterest.values().forEach(pointOfInterest -> {
            if (blockPos.isWithinDistance(pointOfInterest.pos, 30.0)) {
                this.drawPointOfInterestInfo((PointOfInterest)pointOfInterest);
            }
        });
        this.getGhostPointsOfInterest().forEach((blockPos2, list) -> {
            if (blockPos.isWithinDistance((Vec3i)blockPos2, 30.0)) {
                this.drawGhostPointOfInterest((BlockPos)blockPos2, (List<String>)list);
            }
        });
    }

    private static void drawPointOfInterest(BlockPos pos) {
        float f = 0.05f;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        DebugRenderer.drawBox(pos, 0.05f, 0.2f, 0.2f, 1.0f, 0.3f);
    }

    private void drawGhostPointOfInterest(BlockPos pos, List<String> brains) {
        float f = 0.05f;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        DebugRenderer.drawBox(pos, 0.05f, 0.2f, 0.2f, 1.0f, 0.3f);
        VillageDebugRenderer.drawString("" + brains, pos, 0, -256);
        VillageDebugRenderer.drawString("Ghost POI", pos, 1, -65536);
    }

    private void drawPointOfInterestInfo(PointOfInterest pointOfInterest) {
        int n = 0;
        Set<String> _snowman2 = this.getVillagerNames(pointOfInterest);
        if (_snowman2.size() < 4) {
            VillageDebugRenderer.drawString("Owners: " + _snowman2, pointOfInterest, n, -256);
        } else {
            VillageDebugRenderer.drawString("" + _snowman2.size() + " ticket holders", pointOfInterest, n, -256);
        }
        ++n;
        Set<String> _snowman3 = this.method_29385(pointOfInterest);
        if (_snowman3.size() < 4) {
            VillageDebugRenderer.drawString("Candidates: " + _snowman3, pointOfInterest, n, -23296);
        } else {
            VillageDebugRenderer.drawString("" + _snowman3.size() + " potential owners", pointOfInterest, n, -23296);
        }
        VillageDebugRenderer.drawString("Free tickets: " + pointOfInterest.freeTicketCount, pointOfInterest, ++n, -256);
        VillageDebugRenderer.drawString(pointOfInterest.field_18932, pointOfInterest, ++n, -1);
    }

    private void drawPath(Brain brain, double cameraX, double cameraY, double cameraZ) {
        if (brain.path != null) {
            PathfindingDebugRenderer.drawPath(brain.path, 0.5f, false, false, cameraX, cameraY, cameraZ);
        }
    }

    private void drawBrain(Brain brain, double cameraX, double cameraY, double cameraZ) {
        boolean bl = this.isTargeted(brain);
        int _snowman2 = 0;
        VillageDebugRenderer.drawString(brain.pos, _snowman2, brain.field_19328, -1, 0.03f);
        ++_snowman2;
        if (bl) {
            VillageDebugRenderer.drawString(brain.pos, _snowman2, brain.profession + " " + brain.xp + " xp", -1, 0.02f);
            ++_snowman2;
        }
        if (bl) {
            int n = brain.field_22406 < brain.field_22407 ? -23296 : -1;
            VillageDebugRenderer.drawString(brain.pos, _snowman2, "health: " + String.format("%.1f", Float.valueOf(brain.field_22406)) + " / " + String.format("%.1f", Float.valueOf(brain.field_22407)), n, 0.02f);
            ++_snowman2;
        }
        if (bl && !brain.field_19372.equals("")) {
            VillageDebugRenderer.drawString(brain.pos, _snowman2, brain.field_19372, -98404, 0.02f);
            ++_snowman2;
        }
        if (bl) {
            for (String string : brain.field_18928) {
                VillageDebugRenderer.drawString(brain.pos, _snowman2, string, -16711681, 0.02f);
                ++_snowman2;
            }
        }
        if (bl) {
            for (String string : brain.field_18927) {
                VillageDebugRenderer.drawString(brain.pos, _snowman2, string, -16711936, 0.02f);
                ++_snowman2;
            }
        }
        if (brain.wantsGolem) {
            VillageDebugRenderer.drawString(brain.pos, _snowman2, "Wants Golem", -23296, 0.02f);
            ++_snowman2;
        }
        if (bl) {
            for (String string : brain.field_19375) {
                if (string.startsWith(brain.field_19328)) {
                    VillageDebugRenderer.drawString(brain.pos, _snowman2, string, -1, 0.02f);
                } else {
                    VillageDebugRenderer.drawString(brain.pos, _snowman2, string, -23296, 0.02f);
                }
                ++_snowman2;
            }
        }
        if (bl) {
            for (String string : Lists.reverse(brain.field_19374)) {
                VillageDebugRenderer.drawString(brain.pos, _snowman2, string, -3355444, 0.02f);
                ++_snowman2;
            }
        }
        if (bl) {
            this.drawPath(brain, cameraX, cameraY, cameraZ);
        }
    }

    private static void drawString(String string, PointOfInterest pointOfInterest, int offsetY, int color) {
        BlockPos blockPos = pointOfInterest.pos;
        VillageDebugRenderer.drawString(string, blockPos, offsetY, color);
    }

    private static void drawString(String string, BlockPos pos, int offsetY, int color) {
        double d = 1.3;
        _snowman = 0.2;
        _snowman = (double)pos.getX() + 0.5;
        _snowman = (double)pos.getY() + 1.3 + (double)offsetY * 0.2;
        _snowman = (double)pos.getZ() + 0.5;
        DebugRenderer.drawString(string, _snowman, _snowman, _snowman, color, 0.02f, true, 0.0f, true);
    }

    private static void drawString(Position pos, int offsetY, String string, int color, float size) {
        double d = 2.4;
        _snowman = 0.25;
        BlockPos _snowman2 = new BlockPos(pos);
        _snowman = (double)_snowman2.getX() + 0.5;
        _snowman = pos.getY() + 2.4 + (double)offsetY * 0.25;
        _snowman = (double)_snowman2.getZ() + 0.5;
        float _snowman3 = 0.5f;
        DebugRenderer.drawString(string, _snowman, _snowman, _snowman, color, size, false, 0.5f, true);
    }

    private Set<String> getVillagerNames(PointOfInterest pointOfInterest) {
        return this.getBrains(pointOfInterest.pos).stream().map(NameGenerator::name).collect(Collectors.toSet());
    }

    private Set<String> method_29385(PointOfInterest pointOfInterest) {
        return this.method_29386(pointOfInterest.pos).stream().map(NameGenerator::name).collect(Collectors.toSet());
    }

    private boolean isTargeted(Brain brain) {
        return Objects.equals(this.targetedEntity, brain.uuid);
    }

    private boolean isClose(Brain brain) {
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        BlockPos _snowman2 = new BlockPos(clientPlayerEntity.getX(), brain.pos.getY(), clientPlayerEntity.getZ());
        BlockPos _snowman3 = new BlockPos(brain.pos);
        return _snowman2.isWithinDistance(_snowman3, 30.0);
    }

    private Collection<UUID> getBrains(BlockPos pointOfInterest) {
        return this.brains.values().stream().filter(brain -> ((Brain)brain).isPointOfInterest(pointOfInterest)).map(Brain::getUuid).collect(Collectors.toSet());
    }

    private Collection<UUID> method_29386(BlockPos blockPos) {
        return this.brains.values().stream().filter(brain -> ((Brain)brain).method_29388(blockPos)).map(Brain::getUuid).collect(Collectors.toSet());
    }

    private Map<BlockPos, List<String>> getGhostPointsOfInterest() {
        HashMap hashMap = Maps.newHashMap();
        for (Brain brain : this.brains.values()) {
            for (BlockPos blockPos2 : Iterables.concat(brain.pointsOfInterest, brain.field_25287)) {
                if (this.pointsOfInterest.containsKey(blockPos2)) continue;
                hashMap.computeIfAbsent(blockPos2, blockPos -> Lists.newArrayList()).add(brain.field_19328);
            }
        }
        return hashMap;
    }

    private void updateTargetedEntity() {
        DebugRenderer.getTargetedEntity(this.client.getCameraEntity(), 8).ifPresent(entity -> {
            this.targetedEntity = entity.getUuid();
        });
    }

    public static class Brain {
        public final UUID uuid;
        public final int field_18924;
        public final String field_19328;
        public final String profession;
        public final int xp;
        public final float field_22406;
        public final float field_22407;
        public final Position pos;
        public final String field_19372;
        public final Path path;
        public final boolean wantsGolem;
        public final List<String> field_18927 = Lists.newArrayList();
        public final List<String> field_18928 = Lists.newArrayList();
        public final List<String> field_19374 = Lists.newArrayList();
        public final List<String> field_19375 = Lists.newArrayList();
        public final Set<BlockPos> pointsOfInterest = Sets.newHashSet();
        public final Set<BlockPos> field_25287 = Sets.newHashSet();

        public Brain(UUID uUID, int n, String string, String profession, int xp, float f, float f2, Position position, String string2, @Nullable Path path, boolean bl) {
            this.uuid = uUID;
            this.field_18924 = n;
            this.field_19328 = string;
            this.profession = profession;
            this.xp = xp;
            this.field_22406 = f;
            this.field_22407 = f2;
            this.pos = position;
            this.field_19372 = string2;
            this.path = path;
            this.wantsGolem = bl;
        }

        private boolean isPointOfInterest(BlockPos blockPos) {
            return this.pointsOfInterest.stream().anyMatch(blockPos::equals);
        }

        private boolean method_29388(BlockPos blockPos) {
            return this.field_25287.contains(blockPos);
        }

        public UUID getUuid() {
            return this.uuid;
        }
    }

    public static class PointOfInterest {
        public final BlockPos pos;
        public String field_18932;
        public int freeTicketCount;

        public PointOfInterest(BlockPos blockPos, String string, int n) {
            this.pos = blockPos;
            this.field_18932 = string;
            this.freeTicketCount = n;
        }
    }
}

