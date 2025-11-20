/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
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
import net.minecraft.client.render.entity.AreaEffectCloudEntityRenderer;
import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.BatEntityRenderer;
import net.minecraft.client.render.entity.BeeEntityRenderer;
import net.minecraft.client.render.entity.BlazeEntityRenderer;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.CatEntityRenderer;
import net.minecraft.client.render.entity.CaveSpiderEntityRenderer;
import net.minecraft.client.render.entity.ChickenEntityRenderer;
import net.minecraft.client.render.entity.CodEntityRenderer;
import net.minecraft.client.render.entity.CowEntityRenderer;
import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.DolphinEntityRenderer;
import net.minecraft.client.render.entity.DonkeyEntityRenderer;
import net.minecraft.client.render.entity.DragonFireballEntityRenderer;
import net.minecraft.client.render.entity.DrownedEntityRenderer;
import net.minecraft.client.render.entity.ElderGuardianEntityRenderer;
import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import net.minecraft.client.render.entity.EnderDragonEntityRenderer;
import net.minecraft.client.render.entity.EndermanEntityRenderer;
import net.minecraft.client.render.entity.EndermiteEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EvokerEntityRenderer;
import net.minecraft.client.render.entity.EvokerFangsEntityRenderer;
import net.minecraft.client.render.entity.ExperienceOrbEntityRenderer;
import net.minecraft.client.render.entity.FallingBlockEntityRenderer;
import net.minecraft.client.render.entity.FireworkEntityRenderer;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.FoxEntityRenderer;
import net.minecraft.client.render.entity.GhastEntityRenderer;
import net.minecraft.client.render.entity.GiantEntityRenderer;
import net.minecraft.client.render.entity.GuardianEntityRenderer;
import net.minecraft.client.render.entity.HoglinEntityRenderer;
import net.minecraft.client.render.entity.HorseEntityRenderer;
import net.minecraft.client.render.entity.HuskEntityRenderer;
import net.minecraft.client.render.entity.IllusionerEntityRenderer;
import net.minecraft.client.render.entity.IronGolemEntityRenderer;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.render.entity.LeashKnotEntityRenderer;
import net.minecraft.client.render.entity.LightningEntityRenderer;
import net.minecraft.client.render.entity.LlamaEntityRenderer;
import net.minecraft.client.render.entity.LlamaSpitEntityRenderer;
import net.minecraft.client.render.entity.MagmaCubeEntityRenderer;
import net.minecraft.client.render.entity.MinecartEntityRenderer;
import net.minecraft.client.render.entity.MooshroomEntityRenderer;
import net.minecraft.client.render.entity.OcelotEntityRenderer;
import net.minecraft.client.render.entity.PaintingEntityRenderer;
import net.minecraft.client.render.entity.PandaEntityRenderer;
import net.minecraft.client.render.entity.ParrotEntityRenderer;
import net.minecraft.client.render.entity.PhantomEntityRenderer;
import net.minecraft.client.render.entity.PigEntityRenderer;
import net.minecraft.client.render.entity.PiglinEntityRenderer;
import net.minecraft.client.render.entity.PillagerEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.PolarBearEntityRenderer;
import net.minecraft.client.render.entity.PufferfishEntityRenderer;
import net.minecraft.client.render.entity.RabbitEntityRenderer;
import net.minecraft.client.render.entity.RavagerEntityRenderer;
import net.minecraft.client.render.entity.SalmonEntityRenderer;
import net.minecraft.client.render.entity.SheepEntityRenderer;
import net.minecraft.client.render.entity.ShulkerBulletEntityRenderer;
import net.minecraft.client.render.entity.ShulkerEntityRenderer;
import net.minecraft.client.render.entity.SilverfishEntityRenderer;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.SlimeEntityRenderer;
import net.minecraft.client.render.entity.SnowGolemEntityRenderer;
import net.minecraft.client.render.entity.SpectralArrowEntityRenderer;
import net.minecraft.client.render.entity.SpiderEntityRenderer;
import net.minecraft.client.render.entity.SquidEntityRenderer;
import net.minecraft.client.render.entity.StrayEntityRenderer;
import net.minecraft.client.render.entity.StriderEntityRenderer;
import net.minecraft.client.render.entity.TntEntityRenderer;
import net.minecraft.client.render.entity.TntMinecartEntityRenderer;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.client.render.entity.TropicalFishEntityRenderer;
import net.minecraft.client.render.entity.TurtleEntityRenderer;
import net.minecraft.client.render.entity.VexEntityRenderer;
import net.minecraft.client.render.entity.VillagerEntityRenderer;
import net.minecraft.client.render.entity.VindicatorEntityRenderer;
import net.minecraft.client.render.entity.WanderingTraderEntityRenderer;
import net.minecraft.client.render.entity.WitchEntityRenderer;
import net.minecraft.client.render.entity.WitherEntityRenderer;
import net.minecraft.client.render.entity.WitherSkeletonEntityRenderer;
import net.minecraft.client.render.entity.WitherSkullEntityRenderer;
import net.minecraft.client.render.entity.WolfEntityRenderer;
import net.minecraft.client.render.entity.ZoglinEntityRenderer;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.client.render.entity.ZombieHorseEntityRenderer;
import net.minecraft.client.render.entity.ZombieVillagerEntityRenderer;
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

    private <T extends Entity> void register(EntityType<T> entityType, EntityRenderer<? super T> entityRenderer) {
        this.renderers.put(entityType, entityRenderer);
    }

    private void registerRenderers(ItemRenderer itemRenderer, ReloadableResourceManager reloadableResourceManager) {
        this.register(EntityType.AREA_EFFECT_CLOUD, new AreaEffectCloudEntityRenderer(this));
        this.register(EntityType.ARMOR_STAND, new ArmorStandEntityRenderer(this));
        this.register(EntityType.ARROW, new ArrowEntityRenderer(this));
        this.register(EntityType.BAT, new BatEntityRenderer(this));
        this.register(EntityType.BEE, new BeeEntityRenderer(this));
        this.register(EntityType.BLAZE, new BlazeEntityRenderer(this));
        this.register(EntityType.BOAT, new BoatEntityRenderer(this));
        this.register(EntityType.CAT, new CatEntityRenderer(this));
        this.register(EntityType.CAVE_SPIDER, new CaveSpiderEntityRenderer(this));
        this.register(EntityType.CHEST_MINECART, new MinecartEntityRenderer(this));
        this.register(EntityType.CHICKEN, new ChickenEntityRenderer(this));
        this.register(EntityType.COD, new CodEntityRenderer(this));
        this.register(EntityType.COMMAND_BLOCK_MINECART, new MinecartEntityRenderer(this));
        this.register(EntityType.COW, new CowEntityRenderer(this));
        this.register(EntityType.CREEPER, new CreeperEntityRenderer(this));
        this.register(EntityType.DOLPHIN, new DolphinEntityRenderer(this));
        this.register(EntityType.DONKEY, new DonkeyEntityRenderer(this, 0.87f));
        this.register(EntityType.DRAGON_FIREBALL, new DragonFireballEntityRenderer(this));
        this.register(EntityType.DROWNED, new DrownedEntityRenderer(this));
        this.register(EntityType.EGG, new FlyingItemEntityRenderer(this, itemRenderer));
        this.register(EntityType.ELDER_GUARDIAN, new ElderGuardianEntityRenderer(this));
        this.register(EntityType.END_CRYSTAL, new EndCrystalEntityRenderer(this));
        this.register(EntityType.ENDER_DRAGON, new EnderDragonEntityRenderer(this));
        this.register(EntityType.ENDERMAN, new EndermanEntityRenderer(this));
        this.register(EntityType.ENDERMITE, new EndermiteEntityRenderer(this));
        this.register(EntityType.ENDER_PEARL, new FlyingItemEntityRenderer(this, itemRenderer));
        this.register(EntityType.EVOKER_FANGS, new EvokerFangsEntityRenderer(this));
        this.register(EntityType.EVOKER, new EvokerEntityRenderer(this));
        this.register(EntityType.EXPERIENCE_BOTTLE, new FlyingItemEntityRenderer(this, itemRenderer));
        this.register(EntityType.EXPERIENCE_ORB, new ExperienceOrbEntityRenderer(this));
        this.register(EntityType.EYE_OF_ENDER, new FlyingItemEntityRenderer(this, itemRenderer, 1.0f, true));
        this.register(EntityType.FALLING_BLOCK, new FallingBlockEntityRenderer(this));
        this.register(EntityType.FIREBALL, new FlyingItemEntityRenderer(this, itemRenderer, 3.0f, true));
        this.register(EntityType.FIREWORK_ROCKET, new FireworkEntityRenderer(this, itemRenderer));
        this.register(EntityType.FISHING_BOBBER, new FishingBobberEntityRenderer(this));
        this.register(EntityType.FOX, new FoxEntityRenderer(this));
        this.register(EntityType.FURNACE_MINECART, new MinecartEntityRenderer(this));
        this.register(EntityType.GHAST, new GhastEntityRenderer(this));
        this.register(EntityType.GIANT, new GiantEntityRenderer(this, 6.0f));
        this.register(EntityType.GUARDIAN, new GuardianEntityRenderer(this));
        this.register(EntityType.HOGLIN, new HoglinEntityRenderer(this));
        this.register(EntityType.HOPPER_MINECART, new MinecartEntityRenderer(this));
        this.register(EntityType.HORSE, new HorseEntityRenderer(this));
        this.register(EntityType.HUSK, new HuskEntityRenderer(this));
        this.register(EntityType.ILLUSIONER, new IllusionerEntityRenderer(this));
        this.register(EntityType.IRON_GOLEM, new IronGolemEntityRenderer(this));
        this.register(EntityType.ITEM, new ItemEntityRenderer(this, itemRenderer));
        this.register(EntityType.ITEM_FRAME, new ItemFrameEntityRenderer(this, itemRenderer));
        this.register(EntityType.LEASH_KNOT, new LeashKnotEntityRenderer(this));
        this.register(EntityType.LIGHTNING_BOLT, new LightningEntityRenderer(this));
        this.register(EntityType.LLAMA, new LlamaEntityRenderer(this));
        this.register(EntityType.LLAMA_SPIT, new LlamaSpitEntityRenderer(this));
        this.register(EntityType.MAGMA_CUBE, new MagmaCubeEntityRenderer(this));
        this.register(EntityType.MINECART, new MinecartEntityRenderer(this));
        this.register(EntityType.MOOSHROOM, new MooshroomEntityRenderer(this));
        this.register(EntityType.MULE, new DonkeyEntityRenderer(this, 0.92f));
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
        this.register(EntityType.POTION, new FlyingItemEntityRenderer(this, itemRenderer));
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
        this.register(EntityType.SMALL_FIREBALL, new FlyingItemEntityRenderer(this, itemRenderer, 0.75f, true));
        this.register(EntityType.SNOWBALL, new FlyingItemEntityRenderer(this, itemRenderer));
        this.register(EntityType.SNOW_GOLEM, new SnowGolemEntityRenderer(this));
        this.register(EntityType.SPAWNER_MINECART, new MinecartEntityRenderer(this));
        this.register(EntityType.SPECTRAL_ARROW, new SpectralArrowEntityRenderer(this));
        this.register(EntityType.SPIDER, new SpiderEntityRenderer(this));
        this.register(EntityType.SQUID, new SquidEntityRenderer(this));
        this.register(EntityType.STRAY, new StrayEntityRenderer(this));
        this.register(EntityType.TNT_MINECART, new TntMinecartEntityRenderer(this));
        this.register(EntityType.TNT, new TntEntityRenderer(this));
        this.register(EntityType.TRADER_LLAMA, new LlamaEntityRenderer(this));
        this.register(EntityType.TRIDENT, new TridentEntityRenderer(this));
        this.register(EntityType.TROPICAL_FISH, new TropicalFishEntityRenderer(this));
        this.register(EntityType.TURTLE, new TurtleEntityRenderer(this));
        this.register(EntityType.VEX, new VexEntityRenderer(this));
        this.register(EntityType.VILLAGER, new VillagerEntityRenderer(this, reloadableResourceManager));
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
        this.register(EntityType.ZOMBIE_VILLAGER, new ZombieVillagerEntityRenderer(this, reloadableResourceManager));
        this.register(EntityType.STRIDER, new StriderEntityRenderer(this));
    }

    public EntityRenderDispatcher(TextureManager textureManager, ItemRenderer itemRenderer, ReloadableResourceManager reloadableResourceManager, TextRenderer textRenderer, GameOptions gameOptions) {
        this.textureManager = textureManager;
        this.textRenderer = textRenderer;
        this.gameOptions = gameOptions;
        this.registerRenderers(itemRenderer, reloadableResourceManager);
        this.playerRenderer = new PlayerEntityRenderer(this);
        this.modelRenderers.put("default", this.playerRenderer);
        this.modelRenderers.put("slim", new PlayerEntityRenderer(this, true));
        for (EntityType entityType : Registry.ENTITY_TYPE) {
            if (entityType == EntityType.PLAYER || this.renderers.containsKey(entityType)) continue;
            throw new IllegalStateException("No renderer registered for " + Registry.ENTITY_TYPE.getId(entityType));
        }
    }

    public <T extends Entity> EntityRenderer<? super T> getRenderer(T entity) {
        if (entity instanceof AbstractClientPlayerEntity) {
            String string = ((AbstractClientPlayerEntity)entity).getModel();
            PlayerEntityRenderer _snowman2 = this.modelRenderers.get(string);
            if (_snowman2 != null) {
                return _snowman2;
            }
            return this.playerRenderer;
        }
        return this.renderers.get(entity.getType());
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
        EntityRenderer<E> entityRenderer = this.getRenderer(entity);
        return entityRenderer.shouldRender(entity, frustum, x, y, z);
    }

    public <E extends Entity> void render(E entity, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        EntityRenderer<E> entityRenderer = this.getRenderer(entity);
        try {
            Vec3d vec3d = entityRenderer.getPositionOffset(entity, tickDelta);
            double _snowman2 = x + vec3d.getX();
            double _snowman3 = y + vec3d.getY();
            double _snowman4 = z + vec3d.getZ();
            matrices.push();
            matrices.translate(_snowman2, _snowman3, _snowman4);
            entityRenderer.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
            if (entity.doesRenderOnFire()) {
                this.renderFire(matrices, vertexConsumers, entity);
            }
            matrices.translate(-vec3d.getX(), -vec3d.getY(), -vec3d.getZ());
            if (this.gameOptions.entityShadows && this.renderShadows && entityRenderer.shadowRadius > 0.0f && !entity.isInvisible() && (_snowman = (float)((1.0 - (_snowman = this.getSquaredDistanceToCamera(entity.getX(), entity.getY(), entity.getZ())) / 256.0) * (double)entityRenderer.shadowOpacity)) > 0.0f) {
                EntityRenderDispatcher.renderShadow(matrices, vertexConsumers, entity, _snowman, tickDelta, this.world, entityRenderer.shadowRadius);
            }
            if (this.renderHitboxes && !entity.isInvisible() && !MinecraftClient.getInstance().hasReducedDebugInfo()) {
                this.renderHitbox(matrices, vertexConsumers.getBuffer(RenderLayer.getLines()), entity, tickDelta);
            }
            matrices.pop();
        }
        catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.create(throwable, "Rendering entity in world");
            CrashReportSection _snowman5 = crashReport.addElement("Entity being rendered");
            entity.populateCrashReport(_snowman5);
            CrashReportSection _snowman6 = crashReport.addElement("Renderer details");
            _snowman6.add("Assigned renderer", entityRenderer);
            _snowman6.add("Location", CrashReportSection.createPositionString(x, y, z));
            _snowman6.add("Rotation", Float.valueOf(yaw));
            _snowman6.add("Delta", Float.valueOf(tickDelta));
            throw new CrashException(crashReport);
        }
    }

    private void renderHitbox(MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta) {
        float f = entity.getWidth() / 2.0f;
        this.drawBox(matrices, vertices, entity, 1.0f, 1.0f, 1.0f);
        if (entity instanceof EnderDragonEntity) {
            double d = -MathHelper.lerp((double)tickDelta, entity.lastRenderX, entity.getX());
            _snowman = -MathHelper.lerp((double)tickDelta, entity.lastRenderY, entity.getY());
            _snowman = -MathHelper.lerp((double)tickDelta, entity.lastRenderZ, entity.getZ());
            for (EnderDragonPart enderDragonPart : ((EnderDragonEntity)entity).getBodyParts()) {
                matrices.push();
                double d2 = d + MathHelper.lerp((double)tickDelta, enderDragonPart.lastRenderX, enderDragonPart.getX());
                _snowman = _snowman + MathHelper.lerp((double)tickDelta, enderDragonPart.lastRenderY, enderDragonPart.getY());
                _snowman = _snowman + MathHelper.lerp((double)tickDelta, enderDragonPart.lastRenderZ, enderDragonPart.getZ());
                matrices.translate(d2, _snowman, _snowman);
                this.drawBox(matrices, vertices, enderDragonPart, 0.25f, 1.0f, 0.0f);
                matrices.pop();
            }
        }
        if (entity instanceof LivingEntity) {
            float f2 = 0.01f;
            WorldRenderer.drawBox(matrices, vertices, -f, entity.getStandingEyeHeight() - 0.01f, -f, f, entity.getStandingEyeHeight() + 0.01f, f, 1.0f, 0.0f, 0.0f, 1.0f);
        }
        Vec3d vec3d = entity.getRotationVec(tickDelta);
        Matrix4f _snowman2 = matrices.peek().getModel();
        vertices.vertex(_snowman2, 0.0f, entity.getStandingEyeHeight(), 0.0f).color(0, 0, 255, 255).next();
        vertices.vertex(_snowman2, (float)(vec3d.x * 2.0), (float)((double)entity.getStandingEyeHeight() + vec3d.y * 2.0), (float)(vec3d.z * 2.0)).color(0, 0, 255, 255).next();
    }

    private void drawBox(MatrixStack matrix, VertexConsumer vertices, Entity entity, float red, float green, float blue) {
        Box box = entity.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ());
        WorldRenderer.drawBox(matrix, vertices, box, red, green, blue, 1.0f);
    }

    private void renderFire(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity) {
        Sprite sprite = ModelLoader.FIRE_0.getSprite();
        _snowman = ModelLoader.FIRE_1.getSprite();
        matrices.push();
        float _snowman2 = entity.getWidth() * 1.4f;
        matrices.scale(_snowman2, _snowman2, _snowman2);
        float _snowman3 = 0.5f;
        float _snowman4 = 0.0f;
        float _snowman5 = entity.getHeight() / _snowman2;
        float _snowman6 = 0.0f;
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-this.camera.getYaw()));
        matrices.translate(0.0, 0.0, -0.3f + (float)((int)_snowman5) * 0.02f);
        float _snowman7 = 0.0f;
        int _snowman8 = 0;
        VertexConsumer _snowman9 = vertexConsumers.getBuffer(TexturedRenderLayers.getEntityCutout());
        MatrixStack.Entry _snowman10 = matrices.peek();
        while (_snowman5 > 0.0f) {
            _snowman = _snowman8 % 2 == 0 ? sprite : _snowman;
            float f = _snowman.getMinU();
            _snowman = _snowman.getMinV();
            _snowman = _snowman.getMaxU();
            _snowman = _snowman.getMaxV();
            if (_snowman8 / 2 % 2 == 0) {
                _snowman = _snowman;
                _snowman = f;
                f = _snowman;
            }
            EntityRenderDispatcher.drawFireVertex(_snowman10, _snowman9, _snowman3 - 0.0f, 0.0f - _snowman6, _snowman7, _snowman, _snowman);
            EntityRenderDispatcher.drawFireVertex(_snowman10, _snowman9, -_snowman3 - 0.0f, 0.0f - _snowman6, _snowman7, f, _snowman);
            EntityRenderDispatcher.drawFireVertex(_snowman10, _snowman9, -_snowman3 - 0.0f, 1.4f - _snowman6, _snowman7, f, _snowman);
            EntityRenderDispatcher.drawFireVertex(_snowman10, _snowman9, _snowman3 - 0.0f, 1.4f - _snowman6, _snowman7, _snowman, _snowman);
            _snowman5 -= 0.45f;
            _snowman6 -= 0.45f;
            _snowman3 *= 0.9f;
            _snowman7 += 0.03f;
            ++_snowman8;
        }
        matrices.pop();
    }

    private static void drawFireVertex(MatrixStack.Entry entry, VertexConsumer vertices, float x, float y, float z, float u, float v) {
        vertices.vertex(entry.getModel(), x, y, z).color(255, 255, 255, 255).texture(u, v).overlay(0, 10).light(240).normal(entry.getNormal(), 0.0f, 1.0f, 0.0f).next();
    }

    private static void renderShadow(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity, float opacity, float tickDelta, WorldView world, float radius) {
        float f = radius;
        if (entity instanceof MobEntity && (_snowman = (MobEntity)entity).isBaby()) {
            f *= 0.5f;
        }
        double _snowman2 = MathHelper.lerp((double)tickDelta, entity.lastRenderX, entity.getX());
        double _snowman3 = MathHelper.lerp((double)tickDelta, entity.lastRenderY, entity.getY());
        double _snowman4 = MathHelper.lerp((double)tickDelta, entity.lastRenderZ, entity.getZ());
        int _snowman5 = MathHelper.floor(_snowman2 - (double)f);
        int _snowman6 = MathHelper.floor(_snowman2 + (double)f);
        int _snowman7 = MathHelper.floor(_snowman3 - (double)f);
        int _snowman8 = MathHelper.floor(_snowman3);
        int _snowman9 = MathHelper.floor(_snowman4 - (double)f);
        int _snowman10 = MathHelper.floor(_snowman4 + (double)f);
        MatrixStack.Entry _snowman11 = matrices.peek();
        VertexConsumer _snowman12 = vertexConsumers.getBuffer(SHADOW_LAYER);
        for (BlockPos blockPos : BlockPos.iterate(new BlockPos(_snowman5, _snowman7, _snowman9), new BlockPos(_snowman6, _snowman8, _snowman10))) {
            EntityRenderDispatcher.renderShadowPart(_snowman11, _snowman12, world, blockPos, _snowman2, _snowman3, _snowman4, f, opacity);
        }
    }

    private static void renderShadowPart(MatrixStack.Entry entry, VertexConsumer vertices, WorldView world, BlockPos pos, double x, double y, double z, float radius, float opacity) {
        BlockPos blockPos = pos.down();
        BlockState _snowman2 = world.getBlockState(blockPos);
        if (_snowman2.getRenderType() == BlockRenderType.INVISIBLE || world.getLightLevel(pos) <= 3) {
            return;
        }
        if (!_snowman2.isFullCube(world, blockPos)) {
            return;
        }
        VoxelShape _snowman3 = _snowman2.getOutlineShape(world, pos.down());
        if (_snowman3.isEmpty()) {
            return;
        }
        float _snowman4 = (float)(((double)opacity - (y - (double)pos.getY()) / 2.0) * 0.5 * (double)world.getBrightness(pos));
        if (_snowman4 >= 0.0f) {
            if (_snowman4 > 1.0f) {
                _snowman4 = 1.0f;
            }
            Box box = _snowman3.getBoundingBox();
            double _snowman5 = (double)pos.getX() + box.minX;
            double _snowman6 = (double)pos.getX() + box.maxX;
            double _snowman7 = (double)pos.getY() + box.minY;
            double _snowman8 = (double)pos.getZ() + box.minZ;
            double _snowman9 = (double)pos.getZ() + box.maxZ;
            float _snowman10 = (float)(_snowman5 - x);
            float _snowman11 = (float)(_snowman6 - x);
            float _snowman12 = (float)(_snowman7 - y);
            float _snowman13 = (float)(_snowman8 - z);
            float _snowman14 = (float)(_snowman9 - z);
            float _snowman15 = -_snowman10 / 2.0f / radius + 0.5f;
            float _snowman16 = -_snowman11 / 2.0f / radius + 0.5f;
            float _snowman17 = -_snowman13 / 2.0f / radius + 0.5f;
            float _snowman18 = -_snowman14 / 2.0f / radius + 0.5f;
            EntityRenderDispatcher.drawShadowVertex(entry, vertices, _snowman4, _snowman10, _snowman12, _snowman13, _snowman15, _snowman17);
            EntityRenderDispatcher.drawShadowVertex(entry, vertices, _snowman4, _snowman10, _snowman12, _snowman14, _snowman15, _snowman18);
            EntityRenderDispatcher.drawShadowVertex(entry, vertices, _snowman4, _snowman11, _snowman12, _snowman14, _snowman16, _snowman18);
            EntityRenderDispatcher.drawShadowVertex(entry, vertices, _snowman4, _snowman11, _snowman12, _snowman13, _snowman16, _snowman17);
        }
    }

    private static void drawShadowVertex(MatrixStack.Entry entry, VertexConsumer vertices, float alpha, float x, float y, float z, float u, float v) {
        vertices.vertex(entry.getModel(), x, y, z).color(1.0f, 1.0f, 1.0f, alpha).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(0xF000F0).normal(entry.getNormal(), 0.0f, 1.0f, 0.0f).next();
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

