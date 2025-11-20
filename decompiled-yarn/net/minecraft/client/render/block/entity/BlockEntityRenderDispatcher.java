package net.minecraft.client.render.block.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockEntityRenderDispatcher {
   private final Map<BlockEntityType<?>, BlockEntityRenderer<?>> renderers = Maps.newHashMap();
   public static final BlockEntityRenderDispatcher INSTANCE = new BlockEntityRenderDispatcher();
   private final BufferBuilder bufferBuilder = new BufferBuilder(256);
   private TextRenderer textRenderer;
   public TextureManager textureManager;
   public World world;
   public Camera camera;
   public HitResult crosshairTarget;

   private BlockEntityRenderDispatcher() {
      this.register(BlockEntityType.SIGN, new SignBlockEntityRenderer(this));
      this.register(BlockEntityType.MOB_SPAWNER, new MobSpawnerBlockEntityRenderer(this));
      this.register(BlockEntityType.PISTON, new PistonBlockEntityRenderer(this));
      this.register(BlockEntityType.CHEST, new ChestBlockEntityRenderer<>(this));
      this.register(BlockEntityType.ENDER_CHEST, new ChestBlockEntityRenderer<>(this));
      this.register(BlockEntityType.TRAPPED_CHEST, new ChestBlockEntityRenderer<>(this));
      this.register(BlockEntityType.ENCHANTING_TABLE, new EnchantingTableBlockEntityRenderer(this));
      this.register(BlockEntityType.LECTERN, new LecternBlockEntityRenderer(this));
      this.register(BlockEntityType.END_PORTAL, new EndPortalBlockEntityRenderer<>(this));
      this.register(BlockEntityType.END_GATEWAY, new EndGatewayBlockEntityRenderer(this));
      this.register(BlockEntityType.BEACON, new BeaconBlockEntityRenderer(this));
      this.register(BlockEntityType.SKULL, new SkullBlockEntityRenderer(this));
      this.register(BlockEntityType.BANNER, new BannerBlockEntityRenderer(this));
      this.register(BlockEntityType.STRUCTURE_BLOCK, new StructureBlockBlockEntityRenderer(this));
      this.register(BlockEntityType.SHULKER_BOX, new ShulkerBoxBlockEntityRenderer(new ShulkerEntityModel(), this));
      this.register(BlockEntityType.BED, new BedBlockEntityRenderer(this));
      this.register(BlockEntityType.CONDUIT, new ConduitBlockEntityRenderer(this));
      this.register(BlockEntityType.BELL, new BellBlockEntityRenderer(this));
      this.register(BlockEntityType.CAMPFIRE, new CampfireBlockEntityRenderer(this));
   }

   private <E extends BlockEntity> void register(BlockEntityType<E> _snowman, BlockEntityRenderer<E> _snowman) {
      this.renderers.put(_snowman, _snowman);
   }

   @Nullable
   public <E extends BlockEntity> BlockEntityRenderer<E> get(E _snowman) {
      return (BlockEntityRenderer<E>)this.renderers.get(_snowman.getType());
   }

   public void configure(World world, TextureManager textureManager, TextRenderer textRenderer, Camera camera, HitResult crosshairTarget) {
      if (this.world != world) {
         this.setWorld(world);
      }

      this.textureManager = textureManager;
      this.camera = camera;
      this.textRenderer = textRenderer;
      this.crosshairTarget = crosshairTarget;
   }

   public <E extends BlockEntity> void render(E blockEntity, float tickDelta, MatrixStack matrix, VertexConsumerProvider _snowman) {
      if (Vec3d.ofCenter(blockEntity.getPos()).isInRange(this.camera.getPos(), blockEntity.getSquaredRenderDistance())) {
         BlockEntityRenderer<E> _snowmanx = this.get(blockEntity);
         if (_snowmanx != null) {
            if (blockEntity.hasWorld() && blockEntity.getType().supports(blockEntity.getCachedState().getBlock())) {
               runReported(blockEntity, () -> render(_snowman, blockEntity, tickDelta, matrix, _snowman));
            }
         }
      }
   }

   private static <T extends BlockEntity> void render(
      BlockEntityRenderer<T> renderer, T blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers
   ) {
      World _snowman = blockEntity.getWorld();
      int _snowmanx;
      if (_snowman != null) {
         _snowmanx = WorldRenderer.getLightmapCoordinates(_snowman, blockEntity.getPos());
      } else {
         _snowmanx = 15728880;
      }

      renderer.render(blockEntity, tickDelta, matrices, vertexConsumers, _snowmanx, OverlayTexture.DEFAULT_UV);
   }

   public <E extends BlockEntity> boolean renderEntity(E entity, MatrixStack matrix, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
      BlockEntityRenderer<E> _snowman = this.get(entity);
      if (_snowman == null) {
         return true;
      } else {
         runReported(entity, () -> _snowman.render(entity, 0.0F, matrix, vertexConsumerProvider, light, overlay));
         return false;
      }
   }

   private static void runReported(BlockEntity _snowman, Runnable _snowman) {
      try {
         _snowman.run();
      } catch (Throwable var5) {
         CrashReport _snowmanxx = CrashReport.create(var5, "Rendering Block Entity");
         CrashReportSection _snowmanxxx = _snowmanxx.addElement("Block Entity Details");
         _snowman.populateCrashReport(_snowmanxxx);
         throw new CrashException(_snowmanxx);
      }
   }

   public void setWorld(@Nullable World _snowman) {
      this.world = _snowman;
      if (_snowman == null) {
         this.camera = null;
      }
   }

   public TextRenderer getTextRenderer() {
      return this.textRenderer;
   }
}
