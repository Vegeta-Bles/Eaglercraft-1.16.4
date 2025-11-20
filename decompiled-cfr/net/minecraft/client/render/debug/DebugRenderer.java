/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.render.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.debug.BeeDebugRenderer;
import net.minecraft.client.render.debug.BlockOutlineDebugRenderer;
import net.minecraft.client.render.debug.CaveDebugRenderer;
import net.minecraft.client.render.debug.ChunkBorderDebugRenderer;
import net.minecraft.client.render.debug.ChunkLoadingDebugRenderer;
import net.minecraft.client.render.debug.CollisionDebugRenderer;
import net.minecraft.client.render.debug.GameTestDebugRenderer;
import net.minecraft.client.render.debug.GoalSelectorDebugRenderer;
import net.minecraft.client.render.debug.HeightmapDebugRenderer;
import net.minecraft.client.render.debug.NeighborUpdateDebugRenderer;
import net.minecraft.client.render.debug.PathfindingDebugRenderer;
import net.minecraft.client.render.debug.RaidCenterDebugRenderer;
import net.minecraft.client.render.debug.SkyLightDebugRenderer;
import net.minecraft.client.render.debug.StructureDebugRenderer;
import net.minecraft.client.render.debug.VillageDebugRenderer;
import net.minecraft.client.render.debug.VillageSectionsDebugRenderer;
import net.minecraft.client.render.debug.WaterDebugRenderer;
import net.minecraft.client.render.debug.WorldGenAttemptDebugRenderer;
import net.minecraft.client.util.math.AffineTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

public class DebugRenderer {
    public final PathfindingDebugRenderer pathfindingDebugRenderer = new PathfindingDebugRenderer();
    public final Renderer waterDebugRenderer;
    public final Renderer chunkBorderDebugRenderer;
    public final Renderer heightmapDebugRenderer;
    public final Renderer collisionDebugRenderer;
    public final Renderer neighborUpdateDebugRenderer;
    public final CaveDebugRenderer caveDebugRenderer;
    public final StructureDebugRenderer structureDebugRenderer;
    public final Renderer skyLightDebugRenderer;
    public final Renderer worldGenAttemptDebugRenderer;
    public final Renderer blockOutlineDebugRenderer;
    public final Renderer chunkLoadingDebugRenderer;
    public final VillageDebugRenderer villageDebugRenderer;
    public final VillageSectionsDebugRenderer villageSectionsDebugRenderer;
    public final BeeDebugRenderer beeDebugRenderer;
    public final RaidCenterDebugRenderer raidCenterDebugRenderer;
    public final GoalSelectorDebugRenderer goalSelectorDebugRenderer;
    public final GameTestDebugRenderer gameTestDebugRenderer;
    private boolean showChunkBorder;

    public DebugRenderer(MinecraftClient client) {
        this.waterDebugRenderer = new WaterDebugRenderer(client);
        this.chunkBorderDebugRenderer = new ChunkBorderDebugRenderer(client);
        this.heightmapDebugRenderer = new HeightmapDebugRenderer(client);
        this.collisionDebugRenderer = new CollisionDebugRenderer(client);
        this.neighborUpdateDebugRenderer = new NeighborUpdateDebugRenderer(client);
        this.caveDebugRenderer = new CaveDebugRenderer();
        this.structureDebugRenderer = new StructureDebugRenderer(client);
        this.skyLightDebugRenderer = new SkyLightDebugRenderer(client);
        this.worldGenAttemptDebugRenderer = new WorldGenAttemptDebugRenderer();
        this.blockOutlineDebugRenderer = new BlockOutlineDebugRenderer(client);
        this.chunkLoadingDebugRenderer = new ChunkLoadingDebugRenderer(client);
        this.villageDebugRenderer = new VillageDebugRenderer(client);
        this.villageSectionsDebugRenderer = new VillageSectionsDebugRenderer();
        this.beeDebugRenderer = new BeeDebugRenderer(client);
        this.raidCenterDebugRenderer = new RaidCenterDebugRenderer(client);
        this.goalSelectorDebugRenderer = new GoalSelectorDebugRenderer(client);
        this.gameTestDebugRenderer = new GameTestDebugRenderer();
    }

    public void reset() {
        this.pathfindingDebugRenderer.clear();
        this.waterDebugRenderer.clear();
        this.chunkBorderDebugRenderer.clear();
        this.heightmapDebugRenderer.clear();
        this.collisionDebugRenderer.clear();
        this.neighborUpdateDebugRenderer.clear();
        this.caveDebugRenderer.clear();
        this.structureDebugRenderer.clear();
        this.skyLightDebugRenderer.clear();
        this.worldGenAttemptDebugRenderer.clear();
        this.blockOutlineDebugRenderer.clear();
        this.chunkLoadingDebugRenderer.clear();
        this.villageDebugRenderer.clear();
        this.villageSectionsDebugRenderer.clear();
        this.beeDebugRenderer.clear();
        this.raidCenterDebugRenderer.clear();
        this.goalSelectorDebugRenderer.clear();
        this.gameTestDebugRenderer.clear();
    }

    public boolean toggleShowChunkBorder() {
        this.showChunkBorder = !this.showChunkBorder;
        return this.showChunkBorder;
    }

    public void render(MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        if (this.showChunkBorder && !MinecraftClient.getInstance().hasReducedDebugInfo()) {
            this.chunkBorderDebugRenderer.render(matrices, vertexConsumers, cameraX, cameraY, cameraZ);
        }
        this.gameTestDebugRenderer.render(matrices, vertexConsumers, cameraX, cameraY, cameraZ);
    }

    public static Optional<Entity> getTargetedEntity(@Nullable Entity entity2, int maxDistance) {
        if (entity2 == null) {
            return Optional.empty();
        }
        Vec3d vec3d = entity2.getCameraPosVec(1.0f);
        EntityHitResult _snowman2 = ProjectileUtil.raycast(entity2, vec3d, _snowman = vec3d.add(_snowman = entity2.getRotationVec(1.0f).multiply(maxDistance)), _snowman = entity2.getBoundingBox().stretch(_snowman).expand(1.0), _snowman = entity -> !entity.isSpectator() && entity.collides(), _snowman = maxDistance * maxDistance);
        if (_snowman2 == null) {
            return Optional.empty();
        }
        if (vec3d.squaredDistanceTo(_snowman2.getPos()) > (double)_snowman) {
            return Optional.empty();
        }
        return Optional.of(_snowman2.getEntity());
    }

    public static void drawBox(BlockPos pos1, BlockPos pos2, float red, float green, float blue, float alpha) {
        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        if (!camera.isReady()) {
            return;
        }
        Vec3d _snowman2 = camera.getPos().negate();
        Box _snowman3 = new Box(pos1, pos2).offset(_snowman2);
        DebugRenderer.drawBox(_snowman3, red, green, blue, alpha);
    }

    public static void drawBox(BlockPos pos, float expand, float red, float green, float blue, float alpha) {
        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        if (!camera.isReady()) {
            return;
        }
        Vec3d _snowman2 = camera.getPos().negate();
        Box _snowman3 = new Box(pos).offset(_snowman2).expand(expand);
        DebugRenderer.drawBox(_snowman3, red, green, blue, alpha);
    }

    public static void drawBox(Box box, float red, float green, float blue, float alpha) {
        DebugRenderer.drawBox(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha);
    }

    public static void drawBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder _snowman2 = tessellator.getBuffer();
        _snowman2.begin(5, VertexFormats.POSITION_COLOR);
        WorldRenderer.drawBox(_snowman2, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
        tessellator.draw();
    }

    public static void drawString(String string, int x, int y, int z, int color) {
        DebugRenderer.drawString(string, (double)x + 0.5, (double)y + 0.5, (double)z + 0.5, color);
    }

    public static void drawString(String string, double x, double y, double z, int color) {
        DebugRenderer.drawString(string, x, y, z, color, 0.02f);
    }

    public static void drawString(String string, double x, double y, double z, int color, float size) {
        DebugRenderer.drawString(string, x, y, z, color, size, true, 0.0f, false);
    }

    public static void drawString(String string, double x, double y, double z, int color, float size, boolean center, float offset, boolean visibleThroughObjects) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        Camera _snowman2 = minecraftClient.gameRenderer.getCamera();
        if (!_snowman2.isReady() || minecraftClient.getEntityRenderDispatcher().gameOptions == null) {
            return;
        }
        TextRenderer _snowman3 = minecraftClient.textRenderer;
        double _snowman4 = _snowman2.getPos().x;
        double _snowman5 = _snowman2.getPos().y;
        double _snowman6 = _snowman2.getPos().z;
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)(x - _snowman4), (float)(y - _snowman5) + 0.07f, (float)(z - _snowman6));
        RenderSystem.normal3f(0.0f, 1.0f, 0.0f);
        RenderSystem.multMatrix(new Matrix4f(_snowman2.getRotation()));
        RenderSystem.scalef(size, -size, size);
        RenderSystem.enableTexture();
        if (visibleThroughObjects) {
            RenderSystem.disableDepthTest();
        } else {
            RenderSystem.enableDepthTest();
        }
        RenderSystem.depthMask(true);
        RenderSystem.scalef(-1.0f, 1.0f, 1.0f);
        float _snowman7 = center ? (float)(-_snowman3.getWidth(string)) / 2.0f : 0.0f;
        RenderSystem.enableAlphaTest();
        VertexConsumerProvider.Immediate _snowman8 = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        _snowman3.draw(string, _snowman7 -= offset / size, 0.0f, color, false, AffineTransformation.identity().getMatrix(), (VertexConsumerProvider)_snowman8, visibleThroughObjects, 0, 0xF000F0);
        _snowman8.draw();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableDepthTest();
        RenderSystem.popMatrix();
    }

    public static interface Renderer {
        public void render(MatrixStack var1, VertexConsumerProvider var2, double var3, double var5, double var7);

        default public void clear() {
        }
    }
}

