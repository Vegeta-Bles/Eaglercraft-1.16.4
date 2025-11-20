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
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
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

public class ParticleManager implements ResourceReloadListener {
   private static final List<ParticleTextureSheet> PARTICLE_TEXTURE_SHEETS = ImmutableList.of(
      ParticleTextureSheet.TERRAIN_SHEET,
      ParticleTextureSheet.PARTICLE_SHEET_OPAQUE,
      ParticleTextureSheet.PARTICLE_SHEET_LIT,
      ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT,
      ParticleTextureSheet.CUSTOM
   );
   protected ClientWorld world;
   private final Map<ParticleTextureSheet, Queue<Particle>> particles = Maps.newIdentityHashMap();
   private final Queue<EmitterParticle> newEmitterParticles = Queues.newArrayDeque();
   private final TextureManager textureManager;
   private final Random random = new Random();
   private final Int2ObjectMap<ParticleFactory<?>> factories = new Int2ObjectOpenHashMap();
   private final Queue<Particle> newParticles = Queues.newArrayDeque();
   private final Map<Identifier, ParticleManager.SimpleSpriteProvider> spriteAwareFactories = Maps.newHashMap();
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

   private <T extends ParticleEffect> void registerFactory(ParticleType<T> _snowman, ParticleManager.SpriteAwareFactory<T> _snowman) {
      ParticleManager.SimpleSpriteProvider _snowmanxx = new ParticleManager.SimpleSpriteProvider();
      this.spriteAwareFactories.put(Registry.PARTICLE_TYPE.getId(_snowman), _snowmanxx);
      this.factories.put(Registry.PARTICLE_TYPE.getRawId(_snowman), _snowman.create(_snowmanxx));
   }

   @Override
   public CompletableFuture<Void> reload(
      ResourceReloadListener.Synchronizer synchronizer,
      ResourceManager manager,
      Profiler prepareProfiler,
      Profiler applyProfiler,
      Executor prepareExecutor,
      Executor applyExecutor
   ) {
      Map<Identifier, List<Identifier>> _snowman = Maps.newConcurrentMap();
      CompletableFuture<?>[] _snowmanx = Registry.PARTICLE_TYPE
         .getIds()
         .stream()
         .map(_snowmanxxx -> CompletableFuture.runAsync(() -> this.loadTextureList(manager, _snowmanxxx, _snowman), prepareExecutor))
         .toArray(CompletableFuture[]::new);
      return CompletableFuture.allOf(_snowmanx)
         .thenApplyAsync(_snowmanxxx -> {
            prepareProfiler.startTick();
            prepareProfiler.push("stitching");
            SpriteAtlasTexture.Data _snowmanxx = this.particleAtlasTexture.stitch(manager, _snowman.values().stream().flatMap(Collection::stream), prepareProfiler, 0);
            prepareProfiler.pop();
            prepareProfiler.endTick();
            return _snowmanxx;
         }, prepareExecutor)
         .thenCompose(synchronizer::whenPrepared)
         .thenAcceptAsync(
            _snowmanxx -> {
               this.particles.clear();
               applyProfiler.startTick();
               applyProfiler.push("upload");
               this.particleAtlasTexture.upload(_snowmanxx);
               applyProfiler.swap("bindSpriteSets");
               Sprite _snowmanxx = this.particleAtlasTexture.getSprite(MissingSprite.getMissingSpriteId());
               _snowman.forEach(
                  (_snowmanxxxxx, _snowmanxxxx) -> {
                     ImmutableList<Sprite> _snowmanxxx = _snowmanxxxx.isEmpty()
                        ? ImmutableList.of(_snowman)
                        : _snowmanxxxx.stream().map(this.particleAtlasTexture::getSprite).collect(ImmutableList.toImmutableList());
                     this.spriteAwareFactories.get(_snowmanxxxxx).setSprites(_snowmanxxx);
                  }
               );
               applyProfiler.pop();
               applyProfiler.endTick();
            },
            applyExecutor
         );
   }

   public void clearAtlas() {
      this.particleAtlasTexture.clear();
   }

   private void loadTextureList(ResourceManager resourceManager, Identifier id, Map<Identifier, List<Identifier>> result) {
      Identifier _snowman = new Identifier(id.getNamespace(), "particles/" + id.getPath() + ".json");

      try (
         Resource _snowmanx = resourceManager.getResource(_snowman);
         Reader _snowmanxx = new InputStreamReader(_snowmanx.getInputStream(), Charsets.UTF_8);
      ) {
         ParticleTextureData _snowmanxxx = ParticleTextureData.load(JsonHelper.deserialize(_snowmanxx));
         List<Identifier> _snowmanxxxx = _snowmanxxx.getTextureList();
         boolean _snowmanxxxxx = this.spriteAwareFactories.containsKey(id);
         if (_snowmanxxxx == null) {
            if (_snowmanxxxxx) {
               throw new IllegalStateException("Missing texture list for particle " + id);
            }
         } else {
            if (!_snowmanxxxxx) {
               throw new IllegalStateException("Redundant texture list for particle " + id);
            }

            result.put(id, _snowmanxxxx.stream().map(_snowmanxxxxxx -> new Identifier(_snowmanxxxxxx.getNamespace(), "particle/" + _snowmanxxxxxx.getPath())).collect(Collectors.toList()));
         }
      } catch (IOException var39) {
         throw new IllegalStateException("Failed to load description for particle " + id, var39);
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
      Particle _snowman = this.createParticle(parameters, x, y, z, velocityX, velocityY, velocityZ);
      if (_snowman != null) {
         this.addParticle(_snowman);
         return _snowman;
      } else {
         return null;
      }
   }

   @Nullable
   private <T extends ParticleEffect> Particle createParticle(T parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      ParticleFactory<T> _snowman = (ParticleFactory<T>)this.factories.get(Registry.PARTICLE_TYPE.getRawId(parameters.getType()));
      return _snowman == null ? null : _snowman.createParticle(parameters, this.world, x, y, z, velocityX, velocityY, velocityZ);
   }

   public void addParticle(Particle particle) {
      this.newParticles.add(particle);
   }

   public void tick() {
      this.particles.forEach((_snowman, _snowmanx) -> {
         this.world.getProfiler().push(_snowman.toString());
         this.tickParticles(_snowmanx);
         this.world.getProfiler().pop();
      });
      if (!this.newEmitterParticles.isEmpty()) {
         List<EmitterParticle> _snowman = Lists.newArrayList();

         for (EmitterParticle _snowmanx : this.newEmitterParticles) {
            _snowmanx.tick();
            if (!_snowmanx.isAlive()) {
               _snowman.add(_snowmanx);
            }
         }

         this.newEmitterParticles.removeAll(_snowman);
      }

      Particle _snowman;
      if (!this.newParticles.isEmpty()) {
         while ((_snowman = this.newParticles.poll()) != null) {
            this.particles.computeIfAbsent(_snowman.getType(), _snowmanxx -> EvictingQueue.create(16384)).add(_snowman);
         }
      }
   }

   private void tickParticles(Collection<Particle> _snowman) {
      if (!_snowman.isEmpty()) {
         Iterator<Particle> _snowmanx = _snowman.iterator();

         while (_snowmanx.hasNext()) {
            Particle _snowmanxx = _snowmanx.next();
            this.tickParticle(_snowmanxx);
            if (!_snowmanxx.isAlive()) {
               _snowmanx.remove();
            }
         }
      }
   }

   private void tickParticle(Particle particle) {
      try {
         particle.tick();
      } catch (Throwable var5) {
         CrashReport _snowman = CrashReport.create(var5, "Ticking Particle");
         CrashReportSection _snowmanx = _snowman.addElement("Particle being ticked");
         _snowmanx.add("Particle", particle::toString);
         _snowmanx.add("Particle Type", particle.getType()::toString);
         throw new CrashException(_snowman);
      }
   }

   public void renderParticles(MatrixStack _snowman, VertexConsumerProvider.Immediate _snowman, LightmapTextureManager _snowman, Camera _snowman, float _snowman) {
      _snowman.enable();
      RenderSystem.enableAlphaTest();
      RenderSystem.defaultAlphaFunc();
      RenderSystem.enableDepthTest();
      RenderSystem.enableFog();
      RenderSystem.pushMatrix();
      RenderSystem.multMatrix(_snowman.peek().getModel());

      for (ParticleTextureSheet _snowmanxxxxx : PARTICLE_TEXTURE_SHEETS) {
         Iterable<Particle> _snowmanxxxxxx = this.particles.get(_snowmanxxxxx);
         if (_snowmanxxxxxx != null) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            Tessellator _snowmanxxxxxxx = Tessellator.getInstance();
            BufferBuilder _snowmanxxxxxxxx = _snowmanxxxxxxx.getBuffer();
            _snowmanxxxxx.begin(_snowmanxxxxxxxx, this.textureManager);

            for (Particle _snowmanxxxxxxxxx : _snowmanxxxxxx) {
               try {
                  _snowmanxxxxxxxxx.buildGeometry(_snowmanxxxxxxxx, _snowman, _snowman);
               } catch (Throwable var16) {
                  CrashReport _snowmanxxxxxxxxxx = CrashReport.create(var16, "Rendering Particle");
                  CrashReportSection _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.addElement("Particle being rendered");
                  _snowmanxxxxxxxxxxx.add("Particle", _snowmanxxxxxxxxx::toString);
                  _snowmanxxxxxxxxxxx.add("Particle Type", _snowmanxxxxx::toString);
                  throw new CrashException(_snowmanxxxxxxxxxx);
               }
            }

            _snowmanxxxxx.draw(_snowmanxxxxxxx);
         }
      }

      RenderSystem.popMatrix();
      RenderSystem.depthMask(true);
      RenderSystem.depthFunc(515);
      RenderSystem.disableBlend();
      RenderSystem.defaultAlphaFunc();
      _snowman.disable();
      RenderSystem.disableFog();
   }

   public void setWorld(@Nullable ClientWorld world) {
      this.world = world;
      this.particles.clear();
      this.newEmitterParticles.clear();
   }

   public void addBlockBreakParticles(BlockPos pos, BlockState state) {
      if (!state.isAir()) {
         VoxelShape _snowman = state.getOutlineShape(this.world, pos);
         double _snowmanx = 0.25;
         _snowman.forEachBox(
            (_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx) -> {
               double _snowmanxx = Math.min(1.0, _snowmanxxxxx - _snowmanxx);
               double _snowmanx = Math.min(1.0, _snowmanxxxxxx - _snowmanxxx);
               double _snowmanxxxxxxxx = Math.min(1.0, _snowmanxxxxxxx - _snowmanxxxx);
               int _snowmanxxxxxxxxx = Math.max(2, MathHelper.ceil(_snowmanxx / 0.25));
               int _snowmanxxxxxxxxxx = Math.max(2, MathHelper.ceil(_snowmanx / 0.25));
               int _snowmanxxxxxxxxxxx = Math.max(2, MathHelper.ceil(_snowmanxxxxxxxx / 0.25));

               for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
                     for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxx++) {
                        double _snowmanxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxx + 0.5) / (double)_snowmanxxxxxxxxx;
                        double _snowmanxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxx + 0.5) / (double)_snowmanxxxxxxxxxx;
                        double _snowmanxxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxx + 0.5) / (double)_snowmanxxxxxxxxxxx;
                        double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx * _snowmanxx + _snowmanxx;
                        double _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx * _snowmanx + _snowmanxxx;
                        double _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxx + _snowmanxxxx;
                        this.addParticle(
                           new BlockDustParticle(
                                 this.world,
                                 (double)pos.getX() + _snowmanxxxxxxxxxxxxxxxxxx,
                                 (double)pos.getY() + _snowmanxxxxxxxxxxxxxxxxxxx,
                                 (double)pos.getZ() + _snowmanxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxx - 0.5,
                                 _snowmanxxxxxxxxxxxxxxxx - 0.5,
                                 _snowmanxxxxxxxxxxxxxxxxx - 0.5,
                                 state
                              )
                              .setBlockPos(pos)
                        );
                     }
                  }
               }
            }
         );
      }
   }

   public void addBlockBreakingParticles(BlockPos pos, Direction direction) {
      BlockState _snowman = this.world.getBlockState(pos);
      if (_snowman.getRenderType() != BlockRenderType.INVISIBLE) {
         int _snowmanx = pos.getX();
         int _snowmanxx = pos.getY();
         int _snowmanxxx = pos.getZ();
         float _snowmanxxxx = 0.1F;
         Box _snowmanxxxxx = _snowman.getOutlineShape(this.world, pos).getBoundingBox();
         double _snowmanxxxxxx = (double)_snowmanx + this.random.nextDouble() * (_snowmanxxxxx.maxX - _snowmanxxxxx.minX - 0.2F) + 0.1F + _snowmanxxxxx.minX;
         double _snowmanxxxxxxx = (double)_snowmanxx + this.random.nextDouble() * (_snowmanxxxxx.maxY - _snowmanxxxxx.minY - 0.2F) + 0.1F + _snowmanxxxxx.minY;
         double _snowmanxxxxxxxx = (double)_snowmanxxx + this.random.nextDouble() * (_snowmanxxxxx.maxZ - _snowmanxxxxx.minZ - 0.2F) + 0.1F + _snowmanxxxxx.minZ;
         if (direction == Direction.DOWN) {
            _snowmanxxxxxxx = (double)_snowmanxx + _snowmanxxxxx.minY - 0.1F;
         }

         if (direction == Direction.UP) {
            _snowmanxxxxxxx = (double)_snowmanxx + _snowmanxxxxx.maxY + 0.1F;
         }

         if (direction == Direction.NORTH) {
            _snowmanxxxxxxxx = (double)_snowmanxxx + _snowmanxxxxx.minZ - 0.1F;
         }

         if (direction == Direction.SOUTH) {
            _snowmanxxxxxxxx = (double)_snowmanxxx + _snowmanxxxxx.maxZ + 0.1F;
         }

         if (direction == Direction.WEST) {
            _snowmanxxxxxx = (double)_snowmanx + _snowmanxxxxx.minX - 0.1F;
         }

         if (direction == Direction.EAST) {
            _snowmanxxxxxx = (double)_snowmanx + _snowmanxxxxx.maxX + 0.1F;
         }

         this.addParticle(new BlockDustParticle(this.world, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, 0.0, 0.0, 0.0, _snowman).setBlockPos(pos).move(0.2F).scale(0.6F));
      }
   }

   public String getDebugString() {
      return String.valueOf(this.particles.values().stream().mapToInt(Collection::size).sum());
   }

   class SimpleSpriteProvider implements SpriteProvider {
      private List<Sprite> sprites;

      private SimpleSpriteProvider() {
      }

      @Override
      public Sprite getSprite(int _snowman, int _snowman) {
         return this.sprites.get(_snowman * (this.sprites.size() - 1) / _snowman);
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
   interface SpriteAwareFactory<T extends ParticleEffect> {
      ParticleFactory<T> create(SpriteProvider spriteProvider);
   }
}
