package net.minecraft.client.render.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.AffineTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class DebugRenderer {
   public final PathfindingDebugRenderer pathfindingDebugRenderer = new PathfindingDebugRenderer();
   public final DebugRenderer.Renderer waterDebugRenderer;
   public final DebugRenderer.Renderer chunkBorderDebugRenderer;
   public final DebugRenderer.Renderer heightmapDebugRenderer;
   public final DebugRenderer.Renderer collisionDebugRenderer;
   public final DebugRenderer.Renderer neighborUpdateDebugRenderer;
   public final CaveDebugRenderer caveDebugRenderer;
   public final StructureDebugRenderer structureDebugRenderer;
   public final DebugRenderer.Renderer skyLightDebugRenderer;
   public final DebugRenderer.Renderer worldGenAttemptDebugRenderer;
   public final DebugRenderer.Renderer blockOutlineDebugRenderer;
   public final DebugRenderer.Renderer chunkLoadingDebugRenderer;
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

   public static Optional<Entity> getTargetedEntity(@Nullable Entity entity, int maxDistance) {
      if (entity == null) {
         return Optional.empty();
      } else {
         Vec3d lv = entity.getCameraPosVec(1.0F);
         Vec3d lv2 = entity.getRotationVec(1.0F).multiply((double)maxDistance);
         Vec3d lv3 = lv.add(lv2);
         Box lv4 = entity.getBoundingBox().stretch(lv2).expand(1.0);
         int j = maxDistance * maxDistance;
         Predicate<Entity> predicate = arg -> !arg.isSpectator() && arg.collides();
         EntityHitResult lv5 = ProjectileUtil.raycast(entity, lv, lv3, lv4, predicate, (double)j);
         if (lv5 == null) {
            return Optional.empty();
         } else {
            return lv.squaredDistanceTo(lv5.getPos()) > (double)j ? Optional.empty() : Optional.of(lv5.getEntity());
         }
      }
   }

   public static void drawBox(BlockPos pos1, BlockPos pos2, float red, float green, float blue, float alpha) {
      Camera lv = MinecraftClient.getInstance().gameRenderer.getCamera();
      if (lv.isReady()) {
         Vec3d lv2 = lv.getPos().negate();
         Box lv3 = new Box(pos1, pos2).offset(lv2);
         drawBox(lv3, red, green, blue, alpha);
      }
   }

   public static void drawBox(BlockPos pos, float expand, float red, float green, float blue, float alpha) {
      Camera lv = MinecraftClient.getInstance().gameRenderer.getCamera();
      if (lv.isReady()) {
         Vec3d lv2 = lv.getPos().negate();
         Box lv3 = new Box(pos).offset(lv2).expand((double)expand);
         drawBox(lv3, red, green, blue, alpha);
      }
   }

   public static void drawBox(Box box, float red, float green, float blue, float alpha) {
      drawBox(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha);
   }

   public static void drawBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha) {
      Tessellator lv = Tessellator.getInstance();
      BufferBuilder lv2 = lv.getBuffer();
      lv2.begin(5, VertexFormats.POSITION_COLOR);
      WorldRenderer.drawBox(lv2, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
      lv.draw();
   }

   public static void drawString(String string, int x, int y, int z, int color) {
      drawString(string, (double)x + 0.5, (double)y + 0.5, (double)z + 0.5, color);
   }

   public static void drawString(String string, double x, double y, double z, int color) {
      drawString(string, x, y, z, color, 0.02F);
   }

   public static void drawString(String string, double x, double y, double z, int color, float size) {
      drawString(string, x, y, z, color, size, true, 0.0F, false);
   }

   public static void drawString(
      String string, double x, double y, double z, int color, float size, boolean center, float offset, boolean visibleThroughObjects
   ) {
      MinecraftClient lv = MinecraftClient.getInstance();
      Camera lv2 = lv.gameRenderer.getCamera();
      if (lv2.isReady() && lv.getEntityRenderDispatcher().gameOptions != null) {
         TextRenderer lv3 = lv.textRenderer;
         double j = lv2.getPos().x;
         double k = lv2.getPos().y;
         double l = lv2.getPos().z;
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)(x - j), (float)(y - k) + 0.07F, (float)(z - l));
         RenderSystem.normal3f(0.0F, 1.0F, 0.0F);
         RenderSystem.multMatrix(new Matrix4f(lv2.getRotation()));
         RenderSystem.scalef(size, -size, size);
         RenderSystem.enableTexture();
         if (visibleThroughObjects) {
            RenderSystem.disableDepthTest();
         } else {
            RenderSystem.enableDepthTest();
         }

         RenderSystem.depthMask(true);
         RenderSystem.scalef(-1.0F, 1.0F, 1.0F);
         float m = center ? (float)(-lv3.getWidth(string)) / 2.0F : 0.0F;
         m -= offset / size;
         RenderSystem.enableAlphaTest();
         VertexConsumerProvider.Immediate lv4 = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
         lv3.draw(string, m, 0.0F, color, false, AffineTransformation.identity().getMatrix(), lv4, visibleThroughObjects, 0, 15728880);
         lv4.draw();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.enableDepthTest();
         RenderSystem.popMatrix();
      }
   }

   @Environment(EnvType.CLIENT)
   public interface Renderer {
      void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ);

      default void clear() {
      }
   }
}
