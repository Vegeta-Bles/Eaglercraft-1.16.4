/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.collect.EvictingQueue
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Queues
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.client.particle;

import com.google.common.base.Charsets;
import com.google.common.collect.EvictingQueue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.AshParticle;
import net.minecraft.client.particle.BarrierParticle;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.particle.BlockFallingDustParticle;
import net.minecraft.client.particle.BlockLeakParticle;
import net.minecraft.client.particle.BubbleColumnUpParticle;
import net.minecraft.client.particle.BubblePopParticle;
import net.minecraft.client.particle.CampfireSmokeParticle;
import net.minecraft.client.particle.CloudParticle;
import net.minecraft.client.particle.CrackParticle;
import net.minecraft.client.particle.CurrentDownParticle;
import net.minecraft.client.particle.DamageParticle;
import net.minecraft.client.particle.DragonBreathParticle;
import net.minecraft.client.particle.ElderGuardianAppearanceParticle;
import net.minecraft.client.particle.EmitterParticle;
import net.minecraft.client.particle.EmotionParticle;
import net.minecraft.client.particle.EnchantGlyphParticle;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.ExplosionEmitterParticle;
import net.minecraft.client.particle.ExplosionLargeParticle;
import net.minecraft.client.particle.ExplosionSmokeParticle;
import net.minecraft.client.particle.FireSmokeParticle;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.FishingParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.LargeFireSmokeParticle;
import net.minecraft.client.particle.LavaEmberParticle;
import net.minecraft.client.particle.NoteParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureData;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.RainSplashParticle;
import net.minecraft.client.particle.RedDustParticle;
import net.minecraft.client.particle.ReversePortalParticle;
import net.minecraft.client.particle.SoulParticle;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.particle.SpitParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.SquidInkParticle;
import net.minecraft.client.particle.SuspendParticle;
import net.minecraft.client.particle.SweepAttackParticle;
import net.minecraft.client.particle.TotemParticle;
import net.minecraft.client.particle.WaterBubbleParticle;
import net.minecraft.client.particle.WaterSplashParticle;
import net.minecraft.client.particle.WaterSuspendParticle;
import net.minecraft.client.particle.WhiteAshParticle;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;

public class ParticleManager
implements ResourceReloadListener {
    private static final List<ParticleTextureSheet> PARTICLE_TEXTURE_SHEETS = ImmutableList.of((Object)ParticleTextureSheet.TERRAIN_SHEET, (Object)ParticleTextureSheet.PARTICLE_SHEET_OPAQUE, (Object)ParticleTextureSheet.PARTICLE_SHEET_LIT, (Object)ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT, (Object)ParticleTextureSheet.CUSTOM);
    protected ClientWorld world;
    private final Map<ParticleTextureSheet, Queue<Particle>> particles = Maps.newIdentityHashMap();
    private final Queue<EmitterParticle> newEmitterParticles = Queues.newArrayDeque();
    private final TextureManager textureManager;
    private final Random random = new Random();
    private final Int2ObjectMap<ParticleFactory<?>> factories = new Int2ObjectOpenHashMap();
    private final Queue<Particle> newParticles = Queues.newArrayDeque();
    private final Map<Identifier, SimpleSpriteProvider> spriteAwareFactories = Maps.newHashMap();
    private final SpriteAtlasTexture particleAtlasTexture = new SpriteAtlasTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);

    public ParticleManager(ClientWorld world, TextureManager textureManager) {
        textureManager.registerTexture(this.particleAtlasTexture.getId(), this.particleAtlasTexture);
        this.world = world;
        this.textureManager = textureManager;
        this.registerDefaultFactories();
    }

    private void registerDefaultFactories() {
        this.registerFactory(ParticleTypes.AMBIENT_ENTITY_EFFECT, SpellParticle.EntityAmbientFactory::new);
        this.registerFactory(ParticleTypes.ANGRY_VILLAGER, EmotionParticle.AngryVillagerFactory::new);
        this.registerFactory(ParticleTypes.BARRIER, new BarrierParticle.Factory());
        this.registerFactory(ParticleTypes.BLOCK, new BlockDustParticle.Factory());
        this.registerFactory(ParticleTypes.BUBBLE, WaterBubbleParticle.Factory::new);
        this.registerFactory(ParticleTypes.BUBBLE_COLUMN_UP, BubbleColumnUpParticle.Factory::new);
        this.registerFactory(ParticleTypes.BUBBLE_POP, BubblePopParticle.Factory::new);
        this.registerFactory(ParticleTypes.CAMPFIRE_COSY_SMOKE, CampfireSmokeParticle.CosySmokeFactory::new);
        this.registerFactory(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, CampfireSmokeParticle.SignalSmokeFactory::new);
        this.registerFactory(ParticleTypes.CLOUD, CloudParticle.CloudFactory::new);
        this.registerFactory(ParticleTypes.COMPOSTER, SuspendParticle.Factory::new);
        this.registerFactory(ParticleTypes.CRIT, DamageParticle.Factory::new);
        this.registerFactory(ParticleTypes.CURRENT_DOWN, CurrentDownParticle.Factory::new);
        this.registerFactory(ParticleTypes.DAMAGE_INDICATOR, DamageParticle.DefaultFactory::new);
        this.registerFactory(ParticleTypes.DRAGON_BREATH, DragonBreathParticle.Factory::new);
        this.registerFactory(ParticleTypes.DOLPHIN, SuspendParticle.DolphinFactory::new);
        this.registerFactory(ParticleTypes.DRIPPING_LAVA, BlockLeakParticle.DrippingLavaFactory::new);
        this.registerFactory(ParticleTypes.FALLING_LAVA, BlockLeakParticle.FallingLavaFactory::new);
        this.registerFactory(ParticleTypes.LANDING_LAVA, BlockLeakParticle.LandingLavaFactory::new);
        this.registerFactory(ParticleTypes.DRIPPING_WATER, BlockLeakParticle.DrippingWaterFactory::new);
        this.registerFactory(ParticleTypes.FALLING_WATER, BlockLeakParticle.FallingWaterFactory::new);
        this.registerFactory(ParticleTypes.DUST, RedDustParticle.Factory::new);
        this.registerFactory(ParticleTypes.EFFECT, SpellParticle.DefaultFactory::new);
        this.registerFactory(ParticleTypes.ELDER_GUARDIAN, new ElderGuardianAppearanceParticle.Factory());
        this.registerFactory(ParticleTypes.ENCHANTED_HIT, DamageParticle.EnchantedHitFactory::new);
        this.registerFactory(ParticleTypes.ENCHANT, EnchantGlyphParticle.EnchantFactory::new);
        this.registerFactory(ParticleTypes.END_ROD, EndRodParticle.Factory::new);
        this.registerFactory(ParticleTypes.ENTITY_EFFECT, SpellParticle.EntityFactory::new);
        this.registerFactory(ParticleTypes.EXPLOSION_EMITTER, new ExplosionEmitterParticle.Factory());
        this.registerFactory(ParticleTypes.EXPLOSION, ExplosionLargeParticle.Factory::new);
        this.registerFactory(ParticleTypes.FALLING_DUST, BlockFallingDustParticle.Factory::new);
        this.registerFactory(ParticleTypes.FIREWORK, FireworksSparkParticle.ExplosionFactory::new);
        this.registerFactory(ParticleTypes.FISHING, FishingParticle.Factory::new);
        this.registerFactory(ParticleTypes.FLAME, FlameParticle.Factory::new);
        this.registerFactory(ParticleTypes.SOUL, SoulParticle.Factory::new);
        this.registerFactory(ParticleTypes.SOUL_FIRE_FLAME, FlameParticle.Factory::new);
        this.registerFactory(ParticleTypes.FLASH, FireworksSparkParticle.FlashFactory::new);
        this.registerFactory(ParticleTypes.HAPPY_VILLAGER, SuspendParticle.HappyVillagerFactory::new);
        this.registerFactory(ParticleTypes.HEART, EmotionParticle.HeartFactory::new);
        this.registerFactory(ParticleTypes.INSTANT_EFFECT, SpellParticle.InstantFactory::new);
        this.registerFactory(ParticleTypes.ITEM, new CrackParticle.ItemFactory());
        this.registerFactory(ParticleTypes.ITEM_SLIME, new CrackParticle.SlimeballFactory());
        this.registerFactory(ParticleTypes.ITEM_SNOWBALL, new CrackParticle.SnowballFactory());
        this.registerFactory(ParticleTypes.LARGE_SMOKE, LargeFireSmokeParticle.Factory::new);
        this.registerFactory(ParticleTypes.LAVA, LavaEmberParticle.Factory::new);
        this.registerFactory(ParticleTypes.MYCELIUM, SuspendParticle.MyceliumFactory::new);
        this.registerFactory(ParticleTypes.NAUTILUS, EnchantGlyphParticle.NautilusFactory::new);
        this.registerFactory(ParticleTypes.NOTE, NoteParticle.Factory::new);
        this.registerFactory(ParticleTypes.POOF, ExplosionSmokeParticle.Factory::new);
        this.registerFactory(ParticleTypes.PORTAL, PortalParticle.Factory::new);
        this.registerFactory(ParticleTypes.RAIN, RainSplashParticle.Factory::new);
        this.registerFactory(ParticleTypes.SMOKE, FireSmokeParticle.Factory::new);
        this.registerFactory(ParticleTypes.SNEEZE, CloudParticle.SneezeFactory::new);
        this.registerFactory(ParticleTypes.SPIT, SpitParticle.Factory::new);
        this.registerFactory(ParticleTypes.SWEEP_ATTACK, SweepAttackParticle.Factory::new);
        this.registerFactory(ParticleTypes.TOTEM_OF_UNDYING, TotemParticle.Factory::new);
        this.registerFactory(ParticleTypes.SQUID_INK, SquidInkParticle.Factory::new);
        this.registerFactory(ParticleTypes.UNDERWATER, WaterSuspendParticle.UnderwaterFactory::new);
        this.registerFactory(ParticleTypes.SPLASH, WaterSplashParticle.SplashFactory::new);
        this.registerFactory(ParticleTypes.WITCH, SpellParticle.WitchFactory::new);
        this.registerFactory(ParticleTypes.DRIPPING_HONEY, BlockLeakParticle.DrippingHoneyFactory::new);
        this.registerFactory(ParticleTypes.FALLING_HONEY, BlockLeakParticle.FallingHoneyFactory::new);
        this.registerFactory(ParticleTypes.LANDING_HONEY, BlockLeakParticle.LandingHoneyFactory::new);
        this.registerFactory(ParticleTypes.FALLING_NECTAR, BlockLeakParticle.FallingNectarFactory::new);
        this.registerFactory(ParticleTypes.ASH, AshParticle.Factory::new);
        this.registerFactory(ParticleTypes.CRIMSON_SPORE, WaterSuspendParticle.CrimsonSporeFactory::new);
        this.registerFactory(ParticleTypes.WARPED_SPORE, WaterSuspendParticle.WarpedSporeFactory::new);
        this.registerFactory(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, BlockLeakParticle.DrippingObsidianTearFactory::new);
        this.registerFactory(ParticleTypes.FALLING_OBSIDIAN_TEAR, BlockLeakParticle.FallingObsidianTearFactory::new);
        this.registerFactory(ParticleTypes.LANDING_OBSIDIAN_TEAR, BlockLeakParticle.LandingObsidianTearFactory::new);
        this.registerFactory(ParticleTypes.REVERSE_PORTAL, ReversePortalParticle.Factory::new);
        this.registerFactory(ParticleTypes.WHITE_ASH, WhiteAshParticle.Factory::new);
    }

    private <T extends ParticleEffect> void registerFactory(ParticleType<T> type, ParticleFactory<T> factory) {
        this.factories.put(Registry.PARTICLE_TYPE.getRawId(type), factory);
    }

    private <T extends ParticleEffect> void registerFactory(ParticleType<T> particleType, SpriteAwareFactory<T> spriteAwareFactory) {
        SimpleSpriteProvider simpleSpriteProvider = new SimpleSpriteProvider();
        this.spriteAwareFactories.put(Registry.PARTICLE_TYPE.getId(particleType), simpleSpriteProvider);
        this.factories.put(Registry.PARTICLE_TYPE.getRawId(particleType), spriteAwareFactory.create(simpleSpriteProvider));
    }

    @Override
    public CompletableFuture<Void> reload(ResourceReloadListener.Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        ConcurrentMap concurrentMap = Maps.newConcurrentMap();
        CompletableFuture[] _snowman2 = (CompletableFuture[])Registry.PARTICLE_TYPE.getIds().stream().map(identifier -> CompletableFuture.runAsync(() -> this.loadTextureList(manager, (Identifier)identifier, concurrentMap), prepareExecutor)).toArray(CompletableFuture[]::new);
        return ((CompletableFuture)((CompletableFuture)CompletableFuture.allOf(_snowman2).thenApplyAsync(void_ -> {
            prepareProfiler.startTick();
            prepareProfiler.push("stitching");
            SpriteAtlasTexture.Data data = this.particleAtlasTexture.stitch(manager, concurrentMap.values().stream().flatMap(Collection::stream), prepareProfiler, 0);
            prepareProfiler.pop();
            prepareProfiler.endTick();
            return data;
        }, prepareExecutor)).thenCompose(synchronizer::whenPrepared)).thenAcceptAsync(data -> {
            this.particles.clear();
            applyProfiler.startTick();
            applyProfiler.push("upload");
            this.particleAtlasTexture.upload((SpriteAtlasTexture.Data)data);
            applyProfiler.swap("bindSpriteSets");
            Sprite sprite = this.particleAtlasTexture.getSprite(MissingSprite.getMissingSpriteId());
            concurrentMap.forEach((identifier, list) -> {
                ImmutableList immutableList = list.isEmpty() ? ImmutableList.of((Object)sprite) : (ImmutableList)list.stream().map(this.particleAtlasTexture::getSprite).collect(ImmutableList.toImmutableList());
                this.spriteAwareFactories.get(identifier).setSprites((List<Sprite>)immutableList);
            });
            applyProfiler.pop();
            applyProfiler.endTick();
        }, applyExecutor);
    }

    public void clearAtlas() {
        this.particleAtlasTexture.clear();
    }

    private void loadTextureList(ResourceManager resourceManager, Identifier id, Map<Identifier, List<Identifier>> result) {
        Identifier identifier2 = new Identifier(id.getNamespace(), "particles/" + id.getPath() + ".json");
        try (Resource resource = resourceManager.getResource(identifier2);
             InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream(), Charsets.UTF_8);){
            ParticleTextureData particleTextureData = ParticleTextureData.load(JsonHelper.deserialize(inputStreamReader));
            List<Identifier> _snowman2 = particleTextureData.getTextureList();
            boolean _snowman3 = this.spriteAwareFactories.containsKey(id);
            if (_snowman2 == null) {
                if (_snowman3) {
                    throw new IllegalStateException("Missing texture list for particle " + id);
                }
            } else {
                if (!_snowman3) {
                    throw new IllegalStateException("Redundant texture list for particle " + id);
                }
                result.put(id, _snowman2.stream().map(identifier -> new Identifier(identifier.getNamespace(), "particle/" + identifier.getPath())).collect(Collectors.toList()));
            }
        }
        catch (IOException iOException) {
            throw new IllegalStateException("Failed to load description for particle " + id, iOException);
        }
    }

    public void addEmitter(Entity entity, ParticleEffect parameters) {
        this.newEmitterParticles.add(new EmitterParticle(this.world, entity, parameters));
    }

    public void addEmitter(Entity entity, ParticleEffect parameters, int maxAge) {
        this.newEmitterParticles.add(new EmitterParticle(this.world, entity, parameters, maxAge));
    }

    @Nullable
    public Particle addParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        Particle particle = this.createParticle(parameters, x, y, z, velocityX, velocityY, velocityZ);
        if (particle != null) {
            this.addParticle(particle);
            return particle;
        }
        return null;
    }

    @Nullable
    private <T extends ParticleEffect> Particle createParticle(T parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        ParticleFactory particleFactory = (ParticleFactory)this.factories.get(Registry.PARTICLE_TYPE.getRawId(parameters.getType()));
        if (particleFactory == null) {
            return null;
        }
        return particleFactory.createParticle(parameters, this.world, x, y, z, velocityX, velocityY, velocityZ);
    }

    public void addParticle(Particle particle) {
        this.newParticles.add(particle);
    }

    public void tick() {
        Object object;
        this.particles.forEach((particleTextureSheet, queue) -> {
            this.world.getProfiler().push(particleTextureSheet.toString());
            this.tickParticles((Collection<Particle>)queue);
            this.world.getProfiler().pop();
        });
        if (!this.newEmitterParticles.isEmpty()) {
            object = Lists.newArrayList();
            for (EmitterParticle emitterParticle : this.newEmitterParticles) {
                emitterParticle.tick();
                if (emitterParticle.isAlive()) continue;
                object.add(emitterParticle);
            }
            this.newEmitterParticles.removeAll((Collection<?>)object);
        }
        if (!this.newParticles.isEmpty()) {
            while ((object = this.newParticles.poll()) != null) {
                this.particles.computeIfAbsent(((Particle)object).getType(), particleTextureSheet -> EvictingQueue.create((int)16384)).add(object);
            }
        }
    }

    private void tickParticles(Collection<Particle> collection) {
        if (!collection.isEmpty()) {
            Iterator<Particle> iterator = collection.iterator();
            while (iterator.hasNext()) {
                Particle particle = iterator.next();
                this.tickParticle(particle);
                if (particle.isAlive()) continue;
                iterator.remove();
            }
        }
    }

    private void tickParticle(Particle particle) {
        try {
            particle.tick();
        }
        catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.create(throwable, "Ticking Particle");
            CrashReportSection _snowman2 = crashReport.addElement("Particle being ticked");
            _snowman2.add("Particle", particle::toString);
            _snowman2.add("Particle Type", particle.getType()::toString);
            throw new CrashException(crashReport);
        }
    }

    public void renderParticles(MatrixStack matrixStack, VertexConsumerProvider.Immediate immediate, LightmapTextureManager lightmapTextureManager, Camera camera, float f) {
        lightmapTextureManager.enable();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.enableFog();
        RenderSystem.pushMatrix();
        RenderSystem.multMatrix(matrixStack.peek().getModel());
        for (ParticleTextureSheet particleTextureSheet : PARTICLE_TEXTURE_SHEETS) {
            Iterable iterable = this.particles.get(particleTextureSheet);
            if (iterable == null) continue;
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            Tessellator _snowman2 = Tessellator.getInstance();
            BufferBuilder _snowman3 = _snowman2.getBuffer();
            particleTextureSheet.begin(_snowman3, this.textureManager);
            for (Particle particle : iterable) {
                try {
                    particle.buildGeometry(_snowman3, camera, f);
                }
                catch (Throwable throwable) {
                    CrashReport crashReport = CrashReport.create(throwable, "Rendering Particle");
                    CrashReportSection _snowman4 = crashReport.addElement("Particle being rendered");
                    _snowman4.add("Particle", particle::toString);
                    _snowman4.add("Particle Type", particleTextureSheet::toString);
                    throw new CrashException(crashReport);
                }
            }
            particleTextureSheet.draw(_snowman2);
        }
        RenderSystem.popMatrix();
        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(515);
        RenderSystem.disableBlend();
        RenderSystem.defaultAlphaFunc();
        lightmapTextureManager.disable();
        RenderSystem.disableFog();
    }

    public void setWorld(@Nullable ClientWorld world) {
        this.world = world;
        this.particles.clear();
        this.newEmitterParticles.clear();
    }

    public void addBlockBreakParticles(BlockPos pos, BlockState state) {
        if (state.isAir()) {
            return;
        }
        VoxelShape voxelShape = state.getOutlineShape(this.world, pos);
        double _snowman2 = 0.25;
        voxelShape.forEachBox((d, d2, d3, d4, d5, d6) -> {
            _snowman = Math.min(1.0, d4 - d);
            _snowman = Math.min(1.0, d5 - d2);
            _snowman = Math.min(1.0, d6 - d3);
            int n = Math.max(2, MathHelper.ceil(_snowman / 0.25));
            _snowman = Math.max(2, MathHelper.ceil(_snowman / 0.25));
            _snowman = Math.max(2, MathHelper.ceil(_snowman / 0.25));
            for (_snowman = 0; _snowman < n; ++_snowman) {
                for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                    for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                        double d7 = ((double)_snowman + 0.5) / (double)n;
                        _snowman = ((double)_snowman + 0.5) / (double)_snowman;
                        _snowman = ((double)_snowman + 0.5) / (double)_snowman;
                        _snowman = d7 * _snowman + d;
                        _snowman = _snowman * _snowman + d2;
                        _snowman = _snowman * _snowman + d3;
                        this.addParticle(new BlockDustParticle(this.world, (double)pos.getX() + _snowman, (double)pos.getY() + _snowman, (double)pos.getZ() + _snowman, d7 - 0.5, _snowman - 0.5, _snowman - 0.5, state).setBlockPos(pos));
                    }
                }
            }
        });
    }

    public void addBlockBreakingParticles(BlockPos pos, Direction direction) {
        BlockState blockState = this.world.getBlockState(pos);
        if (blockState.getRenderType() == BlockRenderType.INVISIBLE) {
            return;
        }
        int _snowman2 = pos.getX();
        int _snowman3 = pos.getY();
        int _snowman4 = pos.getZ();
        float _snowman5 = 0.1f;
        Box _snowman6 = blockState.getOutlineShape(this.world, pos).getBoundingBox();
        double _snowman7 = (double)_snowman2 + this.random.nextDouble() * (_snowman6.maxX - _snowman6.minX - (double)0.2f) + (double)0.1f + _snowman6.minX;
        double _snowman8 = (double)_snowman3 + this.random.nextDouble() * (_snowman6.maxY - _snowman6.minY - (double)0.2f) + (double)0.1f + _snowman6.minY;
        double _snowman9 = (double)_snowman4 + this.random.nextDouble() * (_snowman6.maxZ - _snowman6.minZ - (double)0.2f) + (double)0.1f + _snowman6.minZ;
        if (direction == Direction.DOWN) {
            _snowman8 = (double)_snowman3 + _snowman6.minY - (double)0.1f;
        }
        if (direction == Direction.UP) {
            _snowman8 = (double)_snowman3 + _snowman6.maxY + (double)0.1f;
        }
        if (direction == Direction.NORTH) {
            _snowman9 = (double)_snowman4 + _snowman6.minZ - (double)0.1f;
        }
        if (direction == Direction.SOUTH) {
            _snowman9 = (double)_snowman4 + _snowman6.maxZ + (double)0.1f;
        }
        if (direction == Direction.WEST) {
            _snowman7 = (double)_snowman2 + _snowman6.minX - (double)0.1f;
        }
        if (direction == Direction.EAST) {
            _snowman7 = (double)_snowman2 + _snowman6.maxX + (double)0.1f;
        }
        this.addParticle(new BlockDustParticle(this.world, _snowman7, _snowman8, _snowman9, 0.0, 0.0, 0.0, blockState).setBlockPos(pos).move(0.2f).scale(0.6f));
    }

    public String getDebugString() {
        return String.valueOf(this.particles.values().stream().mapToInt(Collection::size).sum());
    }

    class SimpleSpriteProvider
    implements SpriteProvider {
        private List<Sprite> sprites;

        private SimpleSpriteProvider() {
        }

        @Override
        public Sprite getSprite(int n, int n2) {
            return this.sprites.get(n * (this.sprites.size() - 1) / n2);
        }

        @Override
        public Sprite getSprite(Random random) {
            return this.sprites.get(random.nextInt(this.sprites.size()));
        }

        public void setSprites(List<Sprite> sprites) {
            this.sprites = ImmutableList.copyOf(sprites);
        }
    }

    @FunctionalInterface
    static interface SpriteAwareFactory<T extends ParticleEffect> {
        public ParticleFactory<T> create(SpriteProvider var1);
    }
}

