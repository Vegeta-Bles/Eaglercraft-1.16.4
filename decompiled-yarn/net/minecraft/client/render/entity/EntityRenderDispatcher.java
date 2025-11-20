package net.minecraft.client.render.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class EntityRenderDispatcher {
   private static final RenderLayer SHADOW_LAYER = RenderLayer.getEntityShadow(new Identifier("textures/misc/shadow.png"));
   private final Map<EntityType<?>, EntityRenderer<?>> renderers = Maps.newHashMap();
   private final Map<String, PlayerEntityRenderer> modelRenderers = Maps.newHashMap();
   private final PlayerEntityRenderer playerRenderer;
   private final TextRenderer textRenderer;
   public final TextureManager textureManager;
   private World world;
   public Camera camera;
   private Quaternion rotation;
   public Entity targetedEntity;
   public final GameOptions gameOptions;
   private boolean renderShadows = true;
   private boolean renderHitboxes;

   public <E extends Entity> int getLight(E entity, float tickDelta) {
      return this.getRenderer(entity).getLight(entity, tickDelta);
   }

   private <T extends Entity> void register(EntityType<T> _snowman, EntityRenderer<? super T> _snowman) {
      this.renderers.put(_snowman, _snowman);
   }

   private void registerRenderers(ItemRenderer _snowman, ReloadableResourceManager _snowman) {
      this.register(EntityType.AREA_EFFECT_CLOUD, new AreaEffectCloudEntityRenderer(this));
      this.register(EntityType.ARMOR_STAND, new ArmorStandEntityRenderer(this));
      this.register(EntityType.ARROW, new ArrowEntityRenderer(this));
      this.register(EntityType.BAT, new BatEntityRenderer(this));
      this.register(EntityType.BEE, new BeeEntityRenderer(this));
      this.register(EntityType.BLAZE, new BlazeEntityRenderer(this));
      this.register(EntityType.BOAT, new BoatEntityRenderer(this));
      this.register(EntityType.CAT, new CatEntityRenderer(this));
      this.register(EntityType.CAVE_SPIDER, new CaveSpiderEntityRenderer(this));
      this.register(EntityType.CHEST_MINECART, new MinecartEntityRenderer<>(this));
      this.register(EntityType.CHICKEN, new ChickenEntityRenderer(this));
      this.register(EntityType.COD, new CodEntityRenderer(this));
      this.register(EntityType.COMMAND_BLOCK_MINECART, new MinecartEntityRenderer<>(this));
      this.register(EntityType.COW, new CowEntityRenderer(this));
      this.register(EntityType.CREEPER, new CreeperEntityRenderer(this));
      this.register(EntityType.DOLPHIN, new DolphinEntityRenderer(this));
      this.register(EntityType.DONKEY, new DonkeyEntityRenderer<>(this, 0.87F));
      this.register(EntityType.DRAGON_FIREBALL, new DragonFireballEntityRenderer(this));
      this.register(EntityType.DROWNED, new DrownedEntityRenderer(this));
      this.register(EntityType.EGG, new FlyingItemEntityRenderer<>(this, _snowman));
      this.register(EntityType.ELDER_GUARDIAN, new ElderGuardianEntityRenderer(this));
      this.register(EntityType.END_CRYSTAL, new EndCrystalEntityRenderer(this));
      this.register(EntityType.ENDER_DRAGON, new EnderDragonEntityRenderer(this));
      this.register(EntityType.ENDERMAN, new EndermanEntityRenderer(this));
      this.register(EntityType.ENDERMITE, new EndermiteEntityRenderer(this));
      this.register(EntityType.ENDER_PEARL, new FlyingItemEntityRenderer<>(this, _snowman));
      this.register(EntityType.EVOKER_FANGS, new EvokerFangsEntityRenderer(this));
      this.register(EntityType.EVOKER, new EvokerEntityRenderer<>(this));
      this.register(EntityType.EXPERIENCE_BOTTLE, new FlyingItemEntityRenderer<>(this, _snowman));
      this.register(EntityType.EXPERIENCE_ORB, new ExperienceOrbEntityRenderer(this));
      this.register(EntityType.EYE_OF_ENDER, new FlyingItemEntityRenderer<>(this, _snowman, 1.0F, true));
      this.register(EntityType.FALLING_BLOCK, new FallingBlockEntityRenderer(this));
      this.register(EntityType.FIREBALL, new FlyingItemEntityRenderer<>(this, _snowman, 3.0F, true));
      this.register(EntityType.FIREWORK_ROCKET, new FireworkEntityRenderer(this, _snowman));
      this.register(EntityType.FISHING_BOBBER, new FishingBobberEntityRenderer(this));
      this.register(EntityType.FOX, new FoxEntityRenderer(this));
      this.register(EntityType.FURNACE_MINECART, new MinecartEntityRenderer<>(this));
      this.register(EntityType.GHAST, new GhastEntityRenderer(this));
      this.register(EntityType.GIANT, new GiantEntityRenderer(this, 6.0F));
      this.register(EntityType.GUARDIAN, new GuardianEntityRenderer(this));
      this.register(EntityType.HOGLIN, new HoglinEntityRenderer(this));
      this.register(EntityType.HOPPER_MINECART, new MinecartEntityRenderer<>(this));
      this.register(EntityType.HORSE, new HorseEntityRenderer(this));
      this.register(EntityType.HUSK, new HuskEntityRenderer(this));
      this.register(EntityType.ILLUSIONER, new IllusionerEntityRenderer(this));
      this.register(EntityType.IRON_GOLEM, new IronGolemEntityRenderer(this));
      this.register(EntityType.ITEM, new ItemEntityRenderer(this, _snowman));
      this.register(EntityType.ITEM_FRAME, new ItemFrameEntityRenderer(this, _snowman));
      this.register(EntityType.LEASH_KNOT, new LeashKnotEntityRenderer(this));
      this.register(EntityType.LIGHTNING_BOLT, new LightningEntityRenderer(this));
      this.register(EntityType.LLAMA, new LlamaEntityRenderer(this));
      this.register(EntityType.LLAMA_SPIT, new LlamaSpitEntityRenderer(this));
      this.register(EntityType.MAGMA_CUBE, new MagmaCubeEntityRenderer(this));
      this.register(EntityType.MINECART, new MinecartEntityRenderer<>(this));
      this.register(EntityType.MOOSHROOM, new MooshroomEntityRenderer(this));
      this.register(EntityType.MULE, new DonkeyEntityRenderer<>(this, 0.92F));
      this.register(EntityType.OCELOT, new OcelotEntityRenderer(this));
      this.register(EntityType.PAINTING, new PaintingEntityRenderer(this));
      this.register(EntityType.PANDA, new PandaEntityRenderer(this));
      this.register(EntityType.PARROT, new ParrotEntityRenderer(this));
      this.register(EntityType.PHANTOM, new PhantomEntityRenderer(this));
      this.register(EntityType.PIG, new PigEntityRenderer(this));
      this.register(EntityType.PIGLIN, new PiglinEntityRenderer(this, false));
      this.register(EntityType.PIGLIN_BRUTE, new PiglinEntityRenderer(this, false));
      this.register(EntityType.PILLAGER, new PillagerEntityRenderer(this));
      this.register(EntityType.POLAR_BEAR, new PolarBearEntityRenderer(this));
      this.register(EntityType.POTION, new FlyingItemEntityRenderer<>(this, _snowman));
      this.register(EntityType.PUFFERFISH, new PufferfishEntityRenderer(this));
      this.register(EntityType.RABBIT, new RabbitEntityRenderer(this));
      this.register(EntityType.RAVAGER, new RavagerEntityRenderer(this));
      this.register(EntityType.SALMON, new SalmonEntityRenderer(this));
      this.register(EntityType.SHEEP, new SheepEntityRenderer(this));
      this.register(EntityType.SHULKER_BULLET, new ShulkerBulletEntityRenderer(this));
      this.register(EntityType.SHULKER, new ShulkerEntityRenderer(this));
      this.register(EntityType.SILVERFISH, new SilverfishEntityRenderer(this));
      this.register(EntityType.SKELETON_HORSE, new ZombieHorseEntityRenderer(this));
      this.register(EntityType.SKELETON, new SkeletonEntityRenderer(this));
      this.register(EntityType.SLIME, new SlimeEntityRenderer(this));
      this.register(EntityType.SMALL_FIREBALL, new FlyingItemEntityRenderer<>(this, _snowman, 0.75F, true));
      this.register(EntityType.SNOWBALL, new FlyingItemEntityRenderer<>(this, _snowman));
      this.register(EntityType.SNOW_GOLEM, new SnowGolemEntityRenderer(this));
      this.register(EntityType.SPAWNER_MINECART, new MinecartEntityRenderer<>(this));
      this.register(EntityType.SPECTRAL_ARROW, new SpectralArrowEntityRenderer(this));
      this.register(EntityType.SPIDER, new SpiderEntityRenderer<>(this));
      this.register(EntityType.SQUID, new SquidEntityRenderer(this));
      this.register(EntityType.STRAY, new StrayEntityRenderer(this));
      this.register(EntityType.TNT_MINECART, new TntMinecartEntityRenderer(this));
      this.register(EntityType.TNT, new TntEntityRenderer(this));
      this.register(EntityType.TRADER_LLAMA, new LlamaEntityRenderer(this));
      this.register(EntityType.TRIDENT, new TridentEntityRenderer(this));
      this.register(EntityType.TROPICAL_FISH, new TropicalFishEntityRenderer(this));
      this.register(EntityType.TURTLE, new TurtleEntityRenderer(this));
      this.register(EntityType.VEX, new VexEntityRenderer(this));
      this.register(EntityType.VILLAGER, new VillagerEntityRenderer(this, _snowman));
      this.register(EntityType.VINDICATOR, new VindicatorEntityRenderer(this));
      this.register(EntityType.WANDERING_TRADER, new WanderingTraderEntityRenderer(this));
      this.register(EntityType.WITCH, new WitchEntityRenderer(this));
      this.register(EntityType.WITHER, new WitherEntityRenderer(this));
      this.register(EntityType.WITHER_SKELETON, new WitherSkeletonEntityRenderer(this));
      this.register(EntityType.WITHER_SKULL, new WitherSkullEntityRenderer(this));
      this.register(EntityType.WOLF, new WolfEntityRenderer(this));
      this.register(EntityType.ZOGLIN, new ZoglinEntityRenderer(this));
      this.register(EntityType.ZOMBIE_HORSE, new ZombieHorseEntityRenderer(this));
      this.register(EntityType.ZOMBIE, new ZombieEntityRenderer(this));
      this.register(EntityType.ZOMBIFIED_PIGLIN, new PiglinEntityRenderer(this, true));
      this.register(EntityType.ZOMBIE_VILLAGER, new ZombieVillagerEntityRenderer(this, _snowman));
      this.register(EntityType.STRIDER, new StriderEntityRenderer(this));
   }

   public EntityRenderDispatcher(TextureManager textureManager, ItemRenderer _snowman, ReloadableResourceManager _snowman, TextRenderer _snowman, GameOptions _snowman) {
      this.textureManager = textureManager;
      this.textRenderer = _snowman;
      this.gameOptions = _snowman;
      this.registerRenderers(_snowman, _snowman);
      this.playerRenderer = new PlayerEntityRenderer(this);
      this.modelRenderers.put("default", this.playerRenderer);
      this.modelRenderers.put("slim", new PlayerEntityRenderer(this, true));

      for (EntityType<?> _snowmanxxxx : Registry.ENTITY_TYPE) {
         if (_snowmanxxxx != EntityType.PLAYER && !this.renderers.containsKey(_snowmanxxxx)) {
            throw new IllegalStateException("No renderer registered for " + Registry.ENTITY_TYPE.getId(_snowmanxxxx));
         }
      }
   }

   public <T extends Entity> EntityRenderer<? super T> getRenderer(T entity) {
      if (entity instanceof AbstractClientPlayerEntity) {
         String _snowman = ((AbstractClientPlayerEntity)entity).getModel();
         PlayerEntityRenderer _snowmanx = this.modelRenderers.get(_snowman);
         return _snowmanx != null ? _snowmanx : this.playerRenderer;
      } else {
         return (EntityRenderer<? super T>)this.renderers.get(entity.getType());
      }
   }

   public void configure(World world, Camera camera, Entity target) {
      this.world = world;
      this.camera = camera;
      this.rotation = camera.getRotation();
      this.targetedEntity = target;
   }

   public void setRotation(Quaternion rotation) {
      this.rotation = rotation;
   }

   public void setRenderShadows(boolean value) {
      this.renderShadows = value;
   }

   public void setRenderHitboxes(boolean value) {
      this.renderHitboxes = value;
   }

   public boolean shouldRenderHitboxes() {
      return this.renderHitboxes;
   }

   public <E extends Entity> boolean shouldRender(E entity, Frustum frustum, double x, double y, double z) {
      EntityRenderer<? super E> _snowman = this.getRenderer(entity);
      return _snowman.shouldRender(entity, frustum, x, y, z);
   }

   public <E extends Entity> void render(
      E entity, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light
   ) {
      EntityRenderer<? super E> _snowman = this.getRenderer(entity);

      try {
         Vec3d _snowmanx = _snowman.getPositionOffset(entity, tickDelta);
         double _snowmanxx = x + _snowmanx.getX();
         double _snowmanxxx = y + _snowmanx.getY();
         double _snowmanxxxx = z + _snowmanx.getZ();
         matrices.push();
         matrices.translate(_snowmanxx, _snowmanxxx, _snowmanxxxx);
         _snowman.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
         if (entity.doesRenderOnFire()) {
            this.renderFire(matrices, vertexConsumers, entity);
         }

         matrices.translate(-_snowmanx.getX(), -_snowmanx.getY(), -_snowmanx.getZ());
         if (this.gameOptions.entityShadows && this.renderShadows && _snowman.shadowRadius > 0.0F && !entity.isInvisible()) {
            double _snowmanxxxxx = this.getSquaredDistanceToCamera(entity.getX(), entity.getY(), entity.getZ());
            float _snowmanxxxxxx = (float)((1.0 - _snowmanxxxxx / 256.0) * (double)_snowman.shadowOpacity);
            if (_snowmanxxxxxx > 0.0F) {
               renderShadow(matrices, vertexConsumers, entity, _snowmanxxxxxx, tickDelta, this.world, _snowman.shadowRadius);
            }
         }

         if (this.renderHitboxes && !entity.isInvisible() && !MinecraftClient.getInstance().hasReducedDebugInfo()) {
            this.renderHitbox(matrices, vertexConsumers.getBuffer(RenderLayer.getLines()), entity, tickDelta);
         }

         matrices.pop();
      } catch (Throwable var24) {
         CrashReport _snowmanxxxxx = CrashReport.create(var24, "Rendering entity in world");
         CrashReportSection _snowmanxxxxxx = _snowmanxxxxx.addElement("Entity being rendered");
         entity.populateCrashReport(_snowmanxxxxxx);
         CrashReportSection _snowmanxxxxxxx = _snowmanxxxxx.addElement("Renderer details");
         _snowmanxxxxxxx.add("Assigned renderer", _snowman);
         _snowmanxxxxxxx.add("Location", CrashReportSection.createPositionString(x, y, z));
         _snowmanxxxxxxx.add("Rotation", yaw);
         _snowmanxxxxxxx.add("Delta", tickDelta);
         throw new CrashException(_snowmanxxxxx);
      }
   }

   private void renderHitbox(MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta) {
      float _snowman = entity.getWidth() / 2.0F;
      this.drawBox(matrices, vertices, entity, 1.0F, 1.0F, 1.0F);
      if (entity instanceof EnderDragonEntity) {
         double _snowmanx = -MathHelper.lerp((double)tickDelta, entity.lastRenderX, entity.getX());
         double _snowmanxx = -MathHelper.lerp((double)tickDelta, entity.lastRenderY, entity.getY());
         double _snowmanxxx = -MathHelper.lerp((double)tickDelta, entity.lastRenderZ, entity.getZ());

         for (EnderDragonPart _snowmanxxxx : ((EnderDragonEntity)entity).getBodyParts()) {
            matrices.push();
            double _snowmanxxxxx = _snowmanx + MathHelper.lerp((double)tickDelta, _snowmanxxxx.lastRenderX, _snowmanxxxx.getX());
            double _snowmanxxxxxx = _snowmanxx + MathHelper.lerp((double)tickDelta, _snowmanxxxx.lastRenderY, _snowmanxxxx.getY());
            double _snowmanxxxxxxx = _snowmanxxx + MathHelper.lerp((double)tickDelta, _snowmanxxxx.lastRenderZ, _snowmanxxxx.getZ());
            matrices.translate(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
            this.drawBox(matrices, vertices, _snowmanxxxx, 0.25F, 1.0F, 0.0F);
            matrices.pop();
         }
      }

      if (entity instanceof LivingEntity) {
         float _snowmanx = 0.01F;
         WorldRenderer.drawBox(
            matrices,
            vertices,
            (double)(-_snowman),
            (double)(entity.getStandingEyeHeight() - 0.01F),
            (double)(-_snowman),
            (double)_snowman,
            (double)(entity.getStandingEyeHeight() + 0.01F),
            (double)_snowman,
            1.0F,
            0.0F,
            0.0F,
            1.0F
         );
      }

      Vec3d _snowmanx = entity.getRotationVec(tickDelta);
      Matrix4f _snowmanxx = matrices.peek().getModel();
      vertices.vertex(_snowmanxx, 0.0F, entity.getStandingEyeHeight(), 0.0F).color(0, 0, 255, 255).next();
      vertices.vertex(_snowmanxx, (float)(_snowmanx.x * 2.0), (float)((double)entity.getStandingEyeHeight() + _snowmanx.y * 2.0), (float)(_snowmanx.z * 2.0)).color(0, 0, 255, 255).next();
   }

   private void drawBox(MatrixStack matrix, VertexConsumer vertices, Entity entity, float red, float green, float blue) {
      Box _snowman = entity.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ());
      WorldRenderer.drawBox(matrix, vertices, _snowman, red, green, blue, 1.0F);
   }

   private void renderFire(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity) {
      Sprite _snowman = ModelLoader.FIRE_0.getSprite();
      Sprite _snowmanx = ModelLoader.FIRE_1.getSprite();
      matrices.push();
      float _snowmanxx = entity.getWidth() * 1.4F;
      matrices.scale(_snowmanxx, _snowmanxx, _snowmanxx);
      float _snowmanxxx = 0.5F;
      float _snowmanxxxx = 0.0F;
      float _snowmanxxxxx = entity.getHeight() / _snowmanxx;
      float _snowmanxxxxxx = 0.0F;
      matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-this.camera.getYaw()));
      matrices.translate(0.0, 0.0, (double)(-0.3F + (float)((int)_snowmanxxxxx) * 0.02F));
      float _snowmanxxxxxxx = 0.0F;
      int _snowmanxxxxxxxx = 0;
      VertexConsumer _snowmanxxxxxxxxx = vertexConsumers.getBuffer(TexturedRenderLayers.getEntityCutout());

      for (MatrixStack.Entry _snowmanxxxxxxxxxx = matrices.peek(); _snowmanxxxxx > 0.0F; _snowmanxxxxxxxx++) {
         Sprite _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx % 2 == 0 ? _snowman : _snowmanx;
         float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getMinU();
         float _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getMinV();
         float _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getMaxU();
         float _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getMaxV();
         if (_snowmanxxxxxxxx / 2 % 2 == 0) {
            float _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx;
            _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx;
            _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
         }

         drawFireVertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxx - 0.0F, 0.0F - _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
         drawFireVertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, -_snowmanxxx - 0.0F, 0.0F - _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
         drawFireVertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, -_snowmanxxx - 0.0F, 1.4F - _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
         drawFireVertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxx - 0.0F, 1.4F - _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
         _snowmanxxxxx -= 0.45F;
         _snowmanxxxxxx -= 0.45F;
         _snowmanxxx *= 0.9F;
         _snowmanxxxxxxx += 0.03F;
      }

      matrices.pop();
   }

   private static void drawFireVertex(MatrixStack.Entry entry, VertexConsumer vertices, float x, float y, float z, float u, float v) {
      vertices.vertex(entry.getModel(), x, y, z)
         .color(255, 255, 255, 255)
         .texture(u, v)
         .overlay(0, 10)
         .light(240)
         .normal(entry.getNormal(), 0.0F, 1.0F, 0.0F)
         .next();
   }

   private static void renderShadow(
      MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity, float opacity, float tickDelta, WorldView world, float radius
   ) {
      float _snowman = radius;
      if (entity instanceof MobEntity) {
         MobEntity _snowmanx = (MobEntity)entity;
         if (_snowmanx.isBaby()) {
            _snowman = radius * 0.5F;
         }
      }

      double _snowmanx = MathHelper.lerp((double)tickDelta, entity.lastRenderX, entity.getX());
      double _snowmanxx = MathHelper.lerp((double)tickDelta, entity.lastRenderY, entity.getY());
      double _snowmanxxx = MathHelper.lerp((double)tickDelta, entity.lastRenderZ, entity.getZ());
      int _snowmanxxxx = MathHelper.floor(_snowmanx - (double)_snowman);
      int _snowmanxxxxx = MathHelper.floor(_snowmanx + (double)_snowman);
      int _snowmanxxxxxx = MathHelper.floor(_snowmanxx - (double)_snowman);
      int _snowmanxxxxxxx = MathHelper.floor(_snowmanxx);
      int _snowmanxxxxxxxx = MathHelper.floor(_snowmanxxx - (double)_snowman);
      int _snowmanxxxxxxxxx = MathHelper.floor(_snowmanxxx + (double)_snowman);
      MatrixStack.Entry _snowmanxxxxxxxxxx = matrices.peek();
      VertexConsumer _snowmanxxxxxxxxxxx = vertexConsumers.getBuffer(SHADOW_LAYER);

      for (BlockPos _snowmanxxxxxxxxxxxx : BlockPos.iterate(new BlockPos(_snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxxxxx), new BlockPos(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx))) {
         renderShadowPart(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, world, _snowmanxxxxxxxxxxxx, _snowmanx, _snowmanxx, _snowmanxxx, _snowman, opacity);
      }
   }

   private static void renderShadowPart(
      MatrixStack.Entry entry, VertexConsumer vertices, WorldView world, BlockPos pos, double x, double y, double z, float radius, float opacity
   ) {
      BlockPos _snowman = pos.down();
      BlockState _snowmanx = world.getBlockState(_snowman);
      if (_snowmanx.getRenderType() != BlockRenderType.INVISIBLE && world.getLightLevel(pos) > 3) {
         if (_snowmanx.isFullCube(world, _snowman)) {
            VoxelShape _snowmanxx = _snowmanx.getOutlineShape(world, pos.down());
            if (!_snowmanxx.isEmpty()) {
               float _snowmanxxx = (float)(((double)opacity - (y - (double)pos.getY()) / 2.0) * 0.5 * (double)world.getBrightness(pos));
               if (_snowmanxxx >= 0.0F) {
                  if (_snowmanxxx > 1.0F) {
                     _snowmanxxx = 1.0F;
                  }

                  Box _snowmanxxxx = _snowmanxx.getBoundingBox();
                  double _snowmanxxxxx = (double)pos.getX() + _snowmanxxxx.minX;
                  double _snowmanxxxxxx = (double)pos.getX() + _snowmanxxxx.maxX;
                  double _snowmanxxxxxxx = (double)pos.getY() + _snowmanxxxx.minY;
                  double _snowmanxxxxxxxx = (double)pos.getZ() + _snowmanxxxx.minZ;
                  double _snowmanxxxxxxxxx = (double)pos.getZ() + _snowmanxxxx.maxZ;
                  float _snowmanxxxxxxxxxx = (float)(_snowmanxxxxx - x);
                  float _snowmanxxxxxxxxxxx = (float)(_snowmanxxxxxx - x);
                  float _snowmanxxxxxxxxxxxx = (float)(_snowmanxxxxxxx - y);
                  float _snowmanxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxx - z);
                  float _snowmanxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxx - z);
                  float _snowmanxxxxxxxxxxxxxxx = -_snowmanxxxxxxxxxx / 2.0F / radius + 0.5F;
                  float _snowmanxxxxxxxxxxxxxxxx = -_snowmanxxxxxxxxxxx / 2.0F / radius + 0.5F;
                  float _snowmanxxxxxxxxxxxxxxxxx = -_snowmanxxxxxxxxxxxxx / 2.0F / radius + 0.5F;
                  float _snowmanxxxxxxxxxxxxxxxxxx = -_snowmanxxxxxxxxxxxxxx / 2.0F / radius + 0.5F;
                  drawShadowVertex(entry, vertices, _snowmanxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx);
                  drawShadowVertex(entry, vertices, _snowmanxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx);
                  drawShadowVertex(entry, vertices, _snowmanxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx);
                  drawShadowVertex(entry, vertices, _snowmanxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx);
               }
            }
         }
      }
   }

   private static void drawShadowVertex(MatrixStack.Entry entry, VertexConsumer vertices, float alpha, float x, float y, float z, float u, float v) {
      vertices.vertex(entry.getModel(), x, y, z)
         .color(1.0F, 1.0F, 1.0F, alpha)
         .texture(u, v)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(15728880)
         .normal(entry.getNormal(), 0.0F, 1.0F, 0.0F)
         .next();
   }

   public void setWorld(@Nullable World world) {
      this.world = world;
      if (world == null) {
         this.camera = null;
      }
   }

   public double getSquaredDistanceToCamera(Entity entity) {
      return this.camera.getPos().squaredDistanceTo(entity.getPos());
   }

   public double getSquaredDistanceToCamera(double x, double y, double z) {
      return this.camera.getPos().squaredDistanceTo(x, y, z);
   }

   public Quaternion getRotation() {
      return this.rotation;
   }

   public TextRenderer getTextRenderer() {
      return this.textRenderer;
   }
}
