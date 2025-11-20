package net.minecraft.client.render;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.CloudRenderMode;
import net.minecraft.client.options.GraphicsMode;
import net.minecraft.client.options.Option;
import net.minecraft.client.options.ParticlesMode;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloadListener;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.border.WorldBorder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldRenderer implements SynchronousResourceReloadListener, AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Identifier MOON_PHASES = new Identifier("textures/environment/moon_phases.png");
   private static final Identifier SUN = new Identifier("textures/environment/sun.png");
   private static final Identifier CLOUDS = new Identifier("textures/environment/clouds.png");
   private static final Identifier END_SKY = new Identifier("textures/environment/end_sky.png");
   private static final Identifier FORCEFIELD = new Identifier("textures/misc/forcefield.png");
   private static final Identifier RAIN = new Identifier("textures/environment/rain.png");
   private static final Identifier SNOW = new Identifier("textures/environment/snow.png");
   public static final Direction[] DIRECTIONS = Direction.values();
   private final MinecraftClient client;
   private final TextureManager textureManager;
   private final EntityRenderDispatcher entityRenderDispatcher;
   private final BufferBuilderStorage bufferBuilders;
   private ClientWorld world;
   private Set<ChunkBuilder.BuiltChunk> chunksToRebuild = Sets.newLinkedHashSet();
   private final ObjectList<WorldRenderer.ChunkInfo> visibleChunks = new ObjectArrayList(69696);
   private final Set<BlockEntity> noCullingBlockEntities = Sets.newHashSet();
   private BuiltChunkStorage chunks;
   private final VertexFormat skyVertexFormat = VertexFormats.POSITION;
   @Nullable
   private VertexBuffer starsBuffer;
   @Nullable
   private VertexBuffer lightSkyBuffer;
   @Nullable
   private VertexBuffer darkSkyBuffer;
   private boolean cloudsDirty = true;
   @Nullable
   private VertexBuffer cloudsBuffer;
   private final FpsSmoother chunkUpdateSmoother = new FpsSmoother(100);
   private int ticks;
   private final Int2ObjectMap<BlockBreakingInfo> blockBreakingInfos = new Int2ObjectOpenHashMap();
   private final Long2ObjectMap<SortedSet<BlockBreakingInfo>> blockBreakingProgressions = new Long2ObjectOpenHashMap();
   private final Map<BlockPos, SoundInstance> playingSongs = Maps.newHashMap();
   @Nullable
   private Framebuffer entityOutlinesFramebuffer;
   @Nullable
   private ShaderEffect entityOutlineShader;
   @Nullable
   private Framebuffer translucentFramebuffer;
   @Nullable
   private Framebuffer entityFramebuffer;
   @Nullable
   private Framebuffer particlesFramebuffer;
   @Nullable
   private Framebuffer weatherFramebuffer;
   @Nullable
   private Framebuffer cloudsFramebuffer;
   @Nullable
   private ShaderEffect transparencyShader;
   private double lastCameraChunkUpdateX = Double.MIN_VALUE;
   private double lastCameraChunkUpdateY = Double.MIN_VALUE;
   private double lastCameraChunkUpdateZ = Double.MIN_VALUE;
   private int cameraChunkX = Integer.MIN_VALUE;
   private int cameraChunkY = Integer.MIN_VALUE;
   private int cameraChunkZ = Integer.MIN_VALUE;
   private double lastCameraX = Double.MIN_VALUE;
   private double lastCameraY = Double.MIN_VALUE;
   private double lastCameraZ = Double.MIN_VALUE;
   private double lastCameraPitch = Double.MIN_VALUE;
   private double lastCameraYaw = Double.MIN_VALUE;
   private int lastCloudsBlockX = Integer.MIN_VALUE;
   private int lastCloudsBlockY = Integer.MIN_VALUE;
   private int lastCloudsBlockZ = Integer.MIN_VALUE;
   private Vec3d lastCloudsColor = Vec3d.ZERO;
   private CloudRenderMode lastCloudsRenderMode;
   private ChunkBuilder chunkBuilder;
   private final VertexFormat vertexFormat = VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL;
   private int renderDistance = -1;
   private int regularEntityCount;
   private int blockEntityCount;
   private boolean shouldCaptureFrustum;
   @Nullable
   private Frustum capturedFrustum;
   private final Vector4f[] capturedFrustumOrientation = new Vector4f[8];
   private final Vector3d capturedFrustumPosition = new Vector3d(0.0, 0.0, 0.0);
   private double lastTranslucentSortX;
   private double lastTranslucentSortY;
   private double lastTranslucentSortZ;
   private boolean needsTerrainUpdate = true;
   private int frame;
   private int field_20793;
   private final float[] field_20794 = new float[1024];
   private final float[] field_20795 = new float[1024];

   public WorldRenderer(MinecraftClient client, BufferBuilderStorage bufferBuilders) {
      this.client = client;
      this.entityRenderDispatcher = client.getEntityRenderDispatcher();
      this.bufferBuilders = bufferBuilders;
      this.textureManager = client.getTextureManager();

      for (int _snowman = 0; _snowman < 32; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 32; _snowmanx++) {
            float _snowmanxx = (float)(_snowmanx - 16);
            float _snowmanxxx = (float)(_snowman - 16);
            float _snowmanxxxx = MathHelper.sqrt(_snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx);
            this.field_20794[_snowman << 5 | _snowmanx] = -_snowmanxxx / _snowmanxxxx;
            this.field_20795[_snowman << 5 | _snowmanx] = _snowmanxx / _snowmanxxxx;
         }
      }

      this.renderStars();
      this.renderLightSky();
      this.renderDarkSky();
   }

   private void renderWeather(LightmapTextureManager manager, float _snowman, double _snowman, double _snowman, double _snowman) {
      float _snowmanxxxx = this.client.world.getRainGradient(_snowman);
      if (!(_snowmanxxxx <= 0.0F)) {
         manager.enable();
         World _snowmanxxxxx = this.client.world;
         int _snowmanxxxxxx = MathHelper.floor(_snowman);
         int _snowmanxxxxxxx = MathHelper.floor(_snowman);
         int _snowmanxxxxxxxx = MathHelper.floor(_snowman);
         Tessellator _snowmanxxxxxxxxx = Tessellator.getInstance();
         BufferBuilder _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.getBuffer();
         RenderSystem.enableAlphaTest();
         RenderSystem.disableCull();
         RenderSystem.normal3f(0.0F, 1.0F, 0.0F);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.defaultAlphaFunc();
         RenderSystem.enableDepthTest();
         int _snowmanxxxxxxxxxxx = 5;
         if (MinecraftClient.isFancyGraphicsOrBetter()) {
            _snowmanxxxxxxxxxxx = 10;
         }

         RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
         int _snowmanxxxxxxxxxxxx = -1;
         float _snowmanxxxxxxxxxxxxx = (float)this.ticks + _snowman;
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         BlockPos.Mutable _snowmanxxxxxxxxxxxxxx = new BlockPos.Mutable();

         for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxx - _snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx <= _snowmanxxxxxxxx + _snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxx - _snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx <= _snowmanxxxxxx + _snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
               int _snowmanxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxx - _snowmanxxxxxxxx + 16) * 32 + _snowmanxxxxxxxxxxxxxxxx - _snowmanxxxxxx + 16;
               double _snowmanxxxxxxxxxxxxxxxxxx = (double)this.field_20794[_snowmanxxxxxxxxxxxxxxxxx] * 0.5;
               double _snowmanxxxxxxxxxxxxxxxxxxx = (double)this.field_20795[_snowmanxxxxxxxxxxxxxxxxx] * 0.5;
               _snowmanxxxxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxxxxxx, 0, _snowmanxxxxxxxxxxxxxxx);
               Biome _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxx.getBiome(_snowmanxxxxxxxxxxxxxx);
               if (_snowmanxxxxxxxxxxxxxxxxxxxx.getPrecipitation() != Biome.Precipitation.NONE) {
                  int _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxx.getTopPosition(Heightmap.Type.MOTION_BLOCKING, _snowmanxxxxxxxxxxxxxx).getY();
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx - _snowmanxxxxxxxxxxx;
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx + _snowmanxxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx;
                  }

                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx;
                  }

                  int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx;
                  }

                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxx != _snowmanxxxxxxxxxxxxxxxxxxxxxxx) {
                     Random _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = new Random(
                        (long)(
                           _snowmanxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxx * 3121 + _snowmanxxxxxxxxxxxxxxxx * 45238971
                              ^ _snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx * 418711 + _snowmanxxxxxxxxxxxxxxx * 13761
                        )
                     );
                     _snowmanxxxxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
                     float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx.getTemperature(_snowmanxxxxxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx >= 0.15F) {
                        if (_snowmanxxxxxxxxxxxx != 0) {
                           if (_snowmanxxxxxxxxxxxx >= 0) {
                              _snowmanxxxxxxxxx.draw();
                           }

                           _snowmanxxxxxxxxxxxx = 0;
                           this.client.getTextureManager().bindTexture(RAIN);
                           _snowmanxxxxxxxxxx.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
                        }

                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.ticks
                              + _snowmanxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxx * 3121
                              + _snowmanxxxxxxxxxxxxxxxx * 45238971
                              + _snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx * 418711
                              + _snowmanxxxxxxxxxxxxxxx * 13761
                           & 31;
                        float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = -((float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowman)
                           / 32.0F
                           * (3.0F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.nextFloat());
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)((float)_snowmanxxxxxxxxxxxxxxxx + 0.5F) - _snowman;
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)((float)_snowmanxxxxxxxxxxxxxxx + 0.5F) - _snowman;
                        float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.sqrt(
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                 + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                           )
                           / (float)_snowmanxxxxxxxxxxx;
                        float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ((1.0F - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.5F + 0.5F)
                           * _snowmanxxxx;
                        _snowmanxxxxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = getLightmapCoordinates(_snowmanxxxxx, _snowmanxxxxxxxxxxxxxx);
                        _snowmanxxxxxxxxxx.vertex(
                              (double)_snowmanxxxxxxxxxxxxxxxx - _snowman - _snowmanxxxxxxxxxxxxxxxxxx + 0.5,
                              (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxx - _snowman,
                              (double)_snowmanxxxxxxxxxxxxxxx - _snowman - _snowmanxxxxxxxxxxxxxxxxxxx + 0.5
                           )
                           .texture(0.0F, (float)_snowmanxxxxxxxxxxxxxxxxxxxxxx * 0.25F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .color(1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .light(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .next();
                        _snowmanxxxxxxxxxx.vertex(
                              (double)_snowmanxxxxxxxxxxxxxxxx - _snowman + _snowmanxxxxxxxxxxxxxxxxxx + 0.5,
                              (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxx - _snowman,
                              (double)_snowmanxxxxxxxxxxxxxxx - _snowman + _snowmanxxxxxxxxxxxxxxxxxxx + 0.5
                           )
                           .texture(1.0F, (float)_snowmanxxxxxxxxxxxxxxxxxxxxxx * 0.25F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .color(1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .light(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .next();
                        _snowmanxxxxxxxxxx.vertex(
                              (double)_snowmanxxxxxxxxxxxxxxxx - _snowman + _snowmanxxxxxxxxxxxxxxxxxx + 0.5,
                              (double)_snowmanxxxxxxxxxxxxxxxxxxxxxx - _snowman,
                              (double)_snowmanxxxxxxxxxxxxxxx - _snowman + _snowmanxxxxxxxxxxxxxxxxxxx + 0.5
                           )
                           .texture(1.0F, (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxx * 0.25F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .color(1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .light(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .next();
                        _snowmanxxxxxxxxxx.vertex(
                              (double)_snowmanxxxxxxxxxxxxxxxx - _snowman - _snowmanxxxxxxxxxxxxxxxxxx + 0.5,
                              (double)_snowmanxxxxxxxxxxxxxxxxxxxxxx - _snowman,
                              (double)_snowmanxxxxxxxxxxxxxxx - _snowman - _snowmanxxxxxxxxxxxxxxxxxxx + 0.5
                           )
                           .texture(0.0F, (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxx * 0.25F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .color(1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .light(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .next();
                     } else {
                        if (_snowmanxxxxxxxxxxxx != 1) {
                           if (_snowmanxxxxxxxxxxxx >= 0) {
                              _snowmanxxxxxxxxx.draw();
                           }

                           _snowmanxxxxxxxxxxxx = 1;
                           this.client.getTextureManager().bindTexture(SNOW);
                           _snowmanxxxxxxxxxx.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
                        }

                        float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = -((float)(this.ticks & 511) + _snowman) / 512.0F;
                        float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)(
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.nextDouble() + (double)_snowmanxxxxxxxxxxxxx * 0.01 * (double)((float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.nextGaussian())
                        );
                        float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)(
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.nextDouble() + (double)(_snowmanxxxxxxxxxxxxx * (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.nextGaussian()) * 0.001
                        );
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)((float)_snowmanxxxxxxxxxxxxxxxx + 0.5F) - _snowman;
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)((float)_snowmanxxxxxxxxxxxxxxx + 0.5F) - _snowman;
                        float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.sqrt(
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                 + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                           )
                           / (float)_snowmanxxxxxxxxxxx;
                        float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (
                              (1.0F - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.3F + 0.5F
                           )
                           * _snowmanxxxx;
                        _snowmanxxxxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = getLightmapCoordinates(_snowmanxxxxx, _snowmanxxxxxxxxxxxxxx);
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx >> 16 & 65535;
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx & 65535) * 3;
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 3 + 240) / 4;
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 3 + 240) / 4;
                        _snowmanxxxxxxxxxx.vertex(
                              (double)_snowmanxxxxxxxxxxxxxxxx - _snowman - _snowmanxxxxxxxxxxxxxxxxxx + 0.5,
                              (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxx - _snowman,
                              (double)_snowmanxxxxxxxxxxxxxxx - _snowman - _snowmanxxxxxxxxxxxxxxxxxxx + 0.5
                           )
                           .texture(
                              0.0F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              (float)_snowmanxxxxxxxxxxxxxxxxxxxxxx * 0.25F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                           )
                           .color(1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .light(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .next();
                        _snowmanxxxxxxxxxx.vertex(
                              (double)_snowmanxxxxxxxxxxxxxxxx - _snowman + _snowmanxxxxxxxxxxxxxxxxxx + 0.5,
                              (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxx - _snowman,
                              (double)_snowmanxxxxxxxxxxxxxxx - _snowman + _snowmanxxxxxxxxxxxxxxxxxxx + 0.5
                           )
                           .texture(
                              1.0F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              (float)_snowmanxxxxxxxxxxxxxxxxxxxxxx * 0.25F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                           )
                           .color(1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .light(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .next();
                        _snowmanxxxxxxxxxx.vertex(
                              (double)_snowmanxxxxxxxxxxxxxxxx - _snowman + _snowmanxxxxxxxxxxxxxxxxxx + 0.5,
                              (double)_snowmanxxxxxxxxxxxxxxxxxxxxxx - _snowman,
                              (double)_snowmanxxxxxxxxxxxxxxx - _snowman + _snowmanxxxxxxxxxxxxxxxxxxx + 0.5
                           )
                           .texture(
                              1.0F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxx * 0.25F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                           )
                           .color(1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .light(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .next();
                        _snowmanxxxxxxxxxx.vertex(
                              (double)_snowmanxxxxxxxxxxxxxxxx - _snowman - _snowmanxxxxxxxxxxxxxxxxxx + 0.5,
                              (double)_snowmanxxxxxxxxxxxxxxxxxxxxxx - _snowman,
                              (double)_snowmanxxxxxxxxxxxxxxx - _snowman - _snowmanxxxxxxxxxxxxxxxxxxx + 0.5
                           )
                           .texture(
                              0.0F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxx * 0.25F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                           )
                           .color(1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .light(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .next();
                     }
                  }
               }
            }
         }

         if (_snowmanxxxxxxxxxxxx >= 0) {
            _snowmanxxxxxxxxx.draw();
         }

         RenderSystem.enableCull();
         RenderSystem.disableBlend();
         RenderSystem.defaultAlphaFunc();
         RenderSystem.disableAlphaTest();
         manager.disable();
      }
   }

   public void tickRainSplashing(Camera camera) {
      float _snowman = this.client.world.getRainGradient(1.0F) / (MinecraftClient.isFancyGraphicsOrBetter() ? 1.0F : 2.0F);
      if (!(_snowman <= 0.0F)) {
         Random _snowmanx = new Random((long)this.ticks * 312987231L);
         WorldView _snowmanxx = this.client.world;
         BlockPos _snowmanxxx = new BlockPos(camera.getPos());
         BlockPos _snowmanxxxx = null;
         int _snowmanxxxxx = (int)(100.0F * _snowman * _snowman) / (this.client.options.particles == ParticlesMode.DECREASED ? 2 : 1);

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx; _snowmanxxxxxx++) {
            int _snowmanxxxxxxx = _snowmanx.nextInt(21) - 10;
            int _snowmanxxxxxxxx = _snowmanx.nextInt(21) - 10;
            BlockPos _snowmanxxxxxxxxx = _snowmanxx.getTopPosition(Heightmap.Type.MOTION_BLOCKING, _snowmanxxx.add(_snowmanxxxxxxx, 0, _snowmanxxxxxxxx)).down();
            Biome _snowmanxxxxxxxxxx = _snowmanxx.getBiome(_snowmanxxxxxxxxx);
            if (_snowmanxxxxxxxxx.getY() > 0
               && _snowmanxxxxxxxxx.getY() <= _snowmanxxx.getY() + 10
               && _snowmanxxxxxxxxx.getY() >= _snowmanxxx.getY() - 10
               && _snowmanxxxxxxxxxx.getPrecipitation() == Biome.Precipitation.RAIN
               && _snowmanxxxxxxxxxx.getTemperature(_snowmanxxxxxxxxx) >= 0.15F) {
               _snowmanxxxx = _snowmanxxxxxxxxx;
               if (this.client.options.particles == ParticlesMode.MINIMAL) {
                  break;
               }

               double _snowmanxxxxxxxxxxx = _snowmanx.nextDouble();
               double _snowmanxxxxxxxxxxxx = _snowmanx.nextDouble();
               BlockState _snowmanxxxxxxxxxxxxx = _snowmanxx.getBlockState(_snowmanxxxxxxxxx);
               FluidState _snowmanxxxxxxxxxxxxxx = _snowmanxx.getFluidState(_snowmanxxxxxxxxx);
               VoxelShape _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.getCollisionShape(_snowmanxx, _snowmanxxxxxxxxx);
               double _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.getEndingCoord(Direction.Axis.Y, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
               double _snowmanxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxx.getHeight(_snowmanxx, _snowmanxxxxxxxxx);
               double _snowmanxxxxxxxxxxxxxxxxxx = Math.max(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx);
               ParticleEffect _snowmanxxxxxxxxxxxxxxxxxxx = !_snowmanxxxxxxxxxxxxxx.isIn(FluidTags.LAVA)
                     && !_snowmanxxxxxxxxxxxxx.isOf(Blocks.MAGMA_BLOCK)
                     && !CampfireBlock.isLitCampfire(_snowmanxxxxxxxxxxxxx)
                  ? ParticleTypes.RAIN
                  : ParticleTypes.SMOKE;
               this.client
                  .world
                  .addParticle(
                     _snowmanxxxxxxxxxxxxxxxxxxx,
                     (double)_snowmanxxxxxxxxx.getX() + _snowmanxxxxxxxxxxx,
                     (double)_snowmanxxxxxxxxx.getY() + _snowmanxxxxxxxxxxxxxxxxxx,
                     (double)_snowmanxxxxxxxxx.getZ() + _snowmanxxxxxxxxxxxx,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }

         if (_snowmanxxxx != null && _snowmanx.nextInt(3) < this.field_20793++) {
            this.field_20793 = 0;
            if (_snowmanxxxx.getY() > _snowmanxxx.getY() + 1 && _snowmanxx.getTopPosition(Heightmap.Type.MOTION_BLOCKING, _snowmanxxx).getY() > MathHelper.floor((float)_snowmanxxx.getY())) {
               this.client.world.playSound(_snowmanxxxx, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 0.1F, 0.5F, false);
            } else {
               this.client.world.playSound(_snowmanxxxx, SoundEvents.WEATHER_RAIN, SoundCategory.WEATHER, 0.2F, 1.0F, false);
            }
         }
      }
   }

   @Override
   public void close() {
      if (this.entityOutlineShader != null) {
         this.entityOutlineShader.close();
      }

      if (this.transparencyShader != null) {
         this.transparencyShader.close();
      }
   }

   @Override
   public void apply(ResourceManager manager) {
      this.textureManager.bindTexture(FORCEFIELD);
      RenderSystem.texParameter(3553, 10242, 10497);
      RenderSystem.texParameter(3553, 10243, 10497);
      RenderSystem.bindTexture(0);
      this.loadEntityOutlineShader();
      if (MinecraftClient.isFabulousGraphicsOrBetter()) {
         this.loadTransparencyShader();
      }
   }

   public void loadEntityOutlineShader() {
      if (this.entityOutlineShader != null) {
         this.entityOutlineShader.close();
      }

      Identifier _snowman = new Identifier("shaders/post/entity_outline.json");

      try {
         this.entityOutlineShader = new ShaderEffect(this.client.getTextureManager(), this.client.getResourceManager(), this.client.getFramebuffer(), _snowman);
         this.entityOutlineShader.setupDimensions(this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight());
         this.entityOutlinesFramebuffer = this.entityOutlineShader.getSecondaryTarget("final");
      } catch (IOException var3) {
         LOGGER.warn("Failed to load shader: {}", _snowman, var3);
         this.entityOutlineShader = null;
         this.entityOutlinesFramebuffer = null;
      } catch (JsonSyntaxException var4) {
         LOGGER.warn("Failed to parse shader: {}", _snowman, var4);
         this.entityOutlineShader = null;
         this.entityOutlinesFramebuffer = null;
      }
   }

   private void loadTransparencyShader() {
      this.resetTransparencyShader();
      Identifier _snowman = new Identifier("shaders/post/transparency.json");

      try {
         ShaderEffect _snowmanx = new ShaderEffect(this.client.getTextureManager(), this.client.getResourceManager(), this.client.getFramebuffer(), _snowman);
         _snowmanx.setupDimensions(this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight());
         Framebuffer _snowmanxx = _snowmanx.getSecondaryTarget("translucent");
         Framebuffer _snowmanxxx = _snowmanx.getSecondaryTarget("itemEntity");
         Framebuffer _snowmanxxxx = _snowmanx.getSecondaryTarget("particles");
         Framebuffer _snowmanxxxxx = _snowmanx.getSecondaryTarget("weather");
         Framebuffer _snowmanxxxxxx = _snowmanx.getSecondaryTarget("clouds");
         this.transparencyShader = _snowmanx;
         this.translucentFramebuffer = _snowmanxx;
         this.entityFramebuffer = _snowmanxxx;
         this.particlesFramebuffer = _snowmanxxxx;
         this.weatherFramebuffer = _snowmanxxxxx;
         this.cloudsFramebuffer = _snowmanxxxxxx;
      } catch (Exception var9) {
         String _snowmanxxxxxxx = var9 instanceof JsonSyntaxException ? "parse" : "load";
         String _snowmanxxxxxxxx = "Failed to " + _snowmanxxxxxxx + " shader: " + _snowman;
         WorldRenderer.ShaderException _snowmanxxxxxxxxx = new WorldRenderer.ShaderException(_snowmanxxxxxxxx, var9);
         if (this.client.getResourcePackManager().getEnabledNames().size() > 1) {
            Text _snowmanxxxxxxxxxx;
            try {
               _snowmanxxxxxxxxxx = new LiteralText(this.client.getResourceManager().getResource(_snowman).getResourcePackName());
            } catch (IOException var8) {
               _snowmanxxxxxxxxxx = null;
            }

            this.client.options.graphicsMode = GraphicsMode.FANCY;
            this.client.method_31186(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
         } else {
            CrashReport _snowmanxxxxxxxxxx = this.client.addDetailsToCrashReport(new CrashReport(_snowmanxxxxxxxx, _snowmanxxxxxxxxx));
            this.client.options.graphicsMode = GraphicsMode.FANCY;
            this.client.options.write();
            LOGGER.fatal(_snowmanxxxxxxxx, _snowmanxxxxxxxxx);
            this.client.cleanUpAfterCrash();
            MinecraftClient.printCrashReport(_snowmanxxxxxxxxxx);
         }
      }
   }

   private void resetTransparencyShader() {
      if (this.transparencyShader != null) {
         this.transparencyShader.close();
         this.translucentFramebuffer.delete();
         this.entityFramebuffer.delete();
         this.particlesFramebuffer.delete();
         this.weatherFramebuffer.delete();
         this.cloudsFramebuffer.delete();
         this.transparencyShader = null;
         this.translucentFramebuffer = null;
         this.entityFramebuffer = null;
         this.particlesFramebuffer = null;
         this.weatherFramebuffer = null;
         this.cloudsFramebuffer = null;
      }
   }

   public void drawEntityOutlinesFramebuffer() {
      if (this.canDrawEntityOutlines()) {
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE
         );
         this.entityOutlinesFramebuffer.draw(this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight(), false);
         RenderSystem.disableBlend();
      }
   }

   protected boolean canDrawEntityOutlines() {
      return this.entityOutlinesFramebuffer != null && this.entityOutlineShader != null && this.client.player != null;
   }

   private void renderDarkSky() {
      Tessellator _snowman = Tessellator.getInstance();
      BufferBuilder _snowmanx = _snowman.getBuffer();
      if (this.darkSkyBuffer != null) {
         this.darkSkyBuffer.close();
      }

      this.darkSkyBuffer = new VertexBuffer(this.skyVertexFormat);
      this.renderSkyHalf(_snowmanx, -16.0F, true);
      _snowmanx.end();
      this.darkSkyBuffer.upload(_snowmanx);
   }

   private void renderLightSky() {
      Tessellator _snowman = Tessellator.getInstance();
      BufferBuilder _snowmanx = _snowman.getBuffer();
      if (this.lightSkyBuffer != null) {
         this.lightSkyBuffer.close();
      }

      this.lightSkyBuffer = new VertexBuffer(this.skyVertexFormat);
      this.renderSkyHalf(_snowmanx, 16.0F, false);
      _snowmanx.end();
      this.lightSkyBuffer.upload(_snowmanx);
   }

   private void renderSkyHalf(BufferBuilder buffer, float y, boolean bottom) {
      int _snowman = 64;
      int _snowmanx = 6;
      buffer.begin(7, VertexFormats.POSITION);

      for (int _snowmanxx = -384; _snowmanxx <= 384; _snowmanxx += 64) {
         for (int _snowmanxxx = -384; _snowmanxxx <= 384; _snowmanxxx += 64) {
            float _snowmanxxxx = (float)_snowmanxx;
            float _snowmanxxxxx = (float)(_snowmanxx + 64);
            if (bottom) {
               _snowmanxxxxx = (float)_snowmanxx;
               _snowmanxxxx = (float)(_snowmanxx + 64);
            }

            buffer.vertex((double)_snowmanxxxx, (double)y, (double)_snowmanxxx).next();
            buffer.vertex((double)_snowmanxxxxx, (double)y, (double)_snowmanxxx).next();
            buffer.vertex((double)_snowmanxxxxx, (double)y, (double)(_snowmanxxx + 64)).next();
            buffer.vertex((double)_snowmanxxxx, (double)y, (double)(_snowmanxxx + 64)).next();
         }
      }
   }

   private void renderStars() {
      Tessellator _snowman = Tessellator.getInstance();
      BufferBuilder _snowmanx = _snowman.getBuffer();
      if (this.starsBuffer != null) {
         this.starsBuffer.close();
      }

      this.starsBuffer = new VertexBuffer(this.skyVertexFormat);
      this.renderStars(_snowmanx);
      _snowmanx.end();
      this.starsBuffer.upload(_snowmanx);
   }

   private void renderStars(BufferBuilder buffer) {
      Random _snowman = new Random(10842L);
      buffer.begin(7, VertexFormats.POSITION);

      for (int _snowmanx = 0; _snowmanx < 1500; _snowmanx++) {
         double _snowmanxx = (double)(_snowman.nextFloat() * 2.0F - 1.0F);
         double _snowmanxxx = (double)(_snowman.nextFloat() * 2.0F - 1.0F);
         double _snowmanxxxx = (double)(_snowman.nextFloat() * 2.0F - 1.0F);
         double _snowmanxxxxx = (double)(0.15F + _snowman.nextFloat() * 0.1F);
         double _snowmanxxxxxx = _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx;
         if (_snowmanxxxxxx < 1.0 && _snowmanxxxxxx > 0.01) {
            _snowmanxxxxxx = 1.0 / Math.sqrt(_snowmanxxxxxx);
            _snowmanxx *= _snowmanxxxxxx;
            _snowmanxxx *= _snowmanxxxxxx;
            _snowmanxxxx *= _snowmanxxxxxx;
            double _snowmanxxxxxxx = _snowmanxx * 100.0;
            double _snowmanxxxxxxxx = _snowmanxxx * 100.0;
            double _snowmanxxxxxxxxx = _snowmanxxxx * 100.0;
            double _snowmanxxxxxxxxxx = Math.atan2(_snowmanxx, _snowmanxxxx);
            double _snowmanxxxxxxxxxxx = Math.sin(_snowmanxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxx = Math.cos(_snowmanxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxx = Math.atan2(Math.sqrt(_snowmanxx * _snowmanxx + _snowmanxxxx * _snowmanxxxx), _snowmanxxx);
            double _snowmanxxxxxxxxxxxxxx = Math.sin(_snowmanxxxxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxxxx = Math.cos(_snowmanxxxxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxxxxx = _snowman.nextDouble() * Math.PI * 2.0;
            double _snowmanxxxxxxxxxxxxxxxxx = Math.sin(_snowmanxxxxxxxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxxxxxxx = Math.cos(_snowmanxxxxxxxxxxxxxxxx);

            for (int _snowmanxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxxxxxxxxx++) {
               double _snowmanxxxxxxxxxxxxxxxxxxxx = 0.0;
               double _snowmanxxxxxxxxxxxxxxxxxxxxx = (double)((_snowmanxxxxxxxxxxxxxxxxxxx & 2) - 1) * _snowmanxxxxx;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxx = (double)((_snowmanxxxxxxxxxxxxxxxxxxx + 1 & 2) - 1) * _snowmanxxxxx;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxx = 0.0;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx + 0.0 * _snowmanxxxxxxxxxxxxxxx;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 * _snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx;
               buffer.vertex(_snowmanxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                  .next();
            }
         }
      }
   }

   public void setWorld(@Nullable ClientWorld _snowman) {
      this.lastCameraChunkUpdateX = Double.MIN_VALUE;
      this.lastCameraChunkUpdateY = Double.MIN_VALUE;
      this.lastCameraChunkUpdateZ = Double.MIN_VALUE;
      this.cameraChunkX = Integer.MIN_VALUE;
      this.cameraChunkY = Integer.MIN_VALUE;
      this.cameraChunkZ = Integer.MIN_VALUE;
      this.entityRenderDispatcher.setWorld(_snowman);
      this.world = _snowman;
      if (_snowman != null) {
         this.reload();
      } else {
         this.chunksToRebuild.clear();
         this.visibleChunks.clear();
         if (this.chunks != null) {
            this.chunks.clear();
            this.chunks = null;
         }

         if (this.chunkBuilder != null) {
            this.chunkBuilder.stop();
         }

         this.chunkBuilder = null;
         this.noCullingBlockEntities.clear();
      }
   }

   public void reload() {
      if (this.world != null) {
         if (MinecraftClient.isFabulousGraphicsOrBetter()) {
            this.loadTransparencyShader();
         } else {
            this.resetTransparencyShader();
         }

         this.world.reloadColor();
         if (this.chunkBuilder == null) {
            this.chunkBuilder = new ChunkBuilder(
               this.world, this, Util.getMainWorkerExecutor(), this.client.is64Bit(), this.bufferBuilders.getBlockBufferBuilders()
            );
         } else {
            this.chunkBuilder.setWorld(this.world);
         }

         this.needsTerrainUpdate = true;
         this.cloudsDirty = true;
         RenderLayers.setFancyGraphicsOrBetter(MinecraftClient.isFancyGraphicsOrBetter());
         this.renderDistance = this.client.options.viewDistance;
         if (this.chunks != null) {
            this.chunks.clear();
         }

         this.clearChunkRenderers();
         synchronized (this.noCullingBlockEntities) {
            this.noCullingBlockEntities.clear();
         }

         this.chunks = new BuiltChunkStorage(this.chunkBuilder, this.world, this.client.options.viewDistance, this);
         if (this.world != null) {
            Entity _snowman = this.client.getCameraEntity();
            if (_snowman != null) {
               this.chunks.updateCameraPosition(_snowman.getX(), _snowman.getZ());
            }
         }
      }
   }

   protected void clearChunkRenderers() {
      this.chunksToRebuild.clear();
      this.chunkBuilder.reset();
   }

   public void onResized(int _snowman, int _snowman) {
      this.scheduleTerrainUpdate();
      if (this.entityOutlineShader != null) {
         this.entityOutlineShader.setupDimensions(_snowman, _snowman);
      }

      if (this.transparencyShader != null) {
         this.transparencyShader.setupDimensions(_snowman, _snowman);
      }
   }

   public String getChunksDebugString() {
      int _snowman = this.chunks.chunks.length;
      int _snowmanx = this.getCompletedChunkCount();
      return String.format(
         "C: %d/%d %sD: %d, %s",
         _snowmanx,
         _snowman,
         this.client.chunkCullingEnabled ? "(s) " : "",
         this.renderDistance,
         this.chunkBuilder == null ? "null" : this.chunkBuilder.getDebugString()
      );
   }

   protected int getCompletedChunkCount() {
      int _snowman = 0;
      ObjectListIterator var2 = this.visibleChunks.iterator();

      while (var2.hasNext()) {
         WorldRenderer.ChunkInfo _snowmanx = (WorldRenderer.ChunkInfo)var2.next();
         if (!_snowmanx.chunk.getData().isEmpty()) {
            _snowman++;
         }
      }

      return _snowman;
   }

   public String getEntitiesDebugString() {
      return "E: " + this.regularEntityCount + "/" + this.world.getRegularEntityCount() + ", B: " + this.blockEntityCount;
   }

   private void setupTerrain(Camera camera, Frustum frustum, boolean hasForcedFrustum, int frame, boolean spectator) {
      Vec3d _snowman = camera.getPos();
      if (this.client.options.viewDistance != this.renderDistance) {
         this.reload();
      }

      this.world.getProfiler().push("camera");
      double _snowmanx = this.client.player.getX() - this.lastCameraChunkUpdateX;
      double _snowmanxx = this.client.player.getY() - this.lastCameraChunkUpdateY;
      double _snowmanxxx = this.client.player.getZ() - this.lastCameraChunkUpdateZ;
      if (this.cameraChunkX != this.client.player.chunkX
         || this.cameraChunkY != this.client.player.chunkY
         || this.cameraChunkZ != this.client.player.chunkZ
         || _snowmanx * _snowmanx + _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx > 16.0) {
         this.lastCameraChunkUpdateX = this.client.player.getX();
         this.lastCameraChunkUpdateY = this.client.player.getY();
         this.lastCameraChunkUpdateZ = this.client.player.getZ();
         this.cameraChunkX = this.client.player.chunkX;
         this.cameraChunkY = this.client.player.chunkY;
         this.cameraChunkZ = this.client.player.chunkZ;
         this.chunks.updateCameraPosition(this.client.player.getX(), this.client.player.getZ());
      }

      this.chunkBuilder.setCameraPosition(_snowman);
      this.world.getProfiler().swap("cull");
      this.client.getProfiler().swap("culling");
      BlockPos _snowmanxxxx = camera.getBlockPos();
      ChunkBuilder.BuiltChunk _snowmanxxxxx = this.chunks.getRenderedChunk(_snowmanxxxx);
      int _snowmanxxxxxx = 16;
      BlockPos _snowmanxxxxxxx = new BlockPos(MathHelper.floor(_snowman.x / 16.0) * 16, MathHelper.floor(_snowman.y / 16.0) * 16, MathHelper.floor(_snowman.z / 16.0) * 16);
      float _snowmanxxxxxxxx = camera.getPitch();
      float _snowmanxxxxxxxxx = camera.getYaw();
      this.needsTerrainUpdate = this.needsTerrainUpdate
         || !this.chunksToRebuild.isEmpty()
         || _snowman.x != this.lastCameraX
         || _snowman.y != this.lastCameraY
         || _snowman.z != this.lastCameraZ
         || (double)_snowmanxxxxxxxx != this.lastCameraPitch
         || (double)_snowmanxxxxxxxxx != this.lastCameraYaw;
      this.lastCameraX = _snowman.x;
      this.lastCameraY = _snowman.y;
      this.lastCameraZ = _snowman.z;
      this.lastCameraPitch = (double)_snowmanxxxxxxxx;
      this.lastCameraYaw = (double)_snowmanxxxxxxxxx;
      this.client.getProfiler().swap("update");
      if (!hasForcedFrustum && this.needsTerrainUpdate) {
         this.needsTerrainUpdate = false;
         this.visibleChunks.clear();
         Queue<WorldRenderer.ChunkInfo> _snowmanxxxxxxxxxx = Queues.newArrayDeque();
         Entity.setRenderDistanceMultiplier(
            MathHelper.clamp((double)this.client.options.viewDistance / 8.0, 1.0, 2.5) * (double)this.client.options.entityDistanceScaling
         );
         boolean _snowmanxxxxxxxxxxx = this.client.chunkCullingEnabled;
         if (_snowmanxxxxx != null) {
            if (spectator && this.world.getBlockState(_snowmanxxxx).isOpaqueFullCube(this.world, _snowmanxxxx)) {
               _snowmanxxxxxxxxxxx = false;
            }

            _snowmanxxxxx.setRebuildFrame(frame);
            _snowmanxxxxxxxxxx.add(new WorldRenderer.ChunkInfo(_snowmanxxxxx, null, 0));
         } else {
            int _snowmanxxxxxxxxxxxx = _snowmanxxxx.getY() > 0 ? 248 : 8;
            int _snowmanxxxxxxxxxxxxx = MathHelper.floor(_snowman.x / 16.0) * 16;
            int _snowmanxxxxxxxxxxxxxx = MathHelper.floor(_snowman.z / 16.0) * 16;
            List<WorldRenderer.ChunkInfo> _snowmanxxxxxxxxxxxxxxx = Lists.newArrayList();

            for (int _snowmanxxxxxxxxxxxxxxxx = -this.renderDistance; _snowmanxxxxxxxxxxxxxxxx <= this.renderDistance; _snowmanxxxxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxxxxx = -this.renderDistance; _snowmanxxxxxxxxxxxxxxxxx <= this.renderDistance; _snowmanxxxxxxxxxxxxxxxxx++) {
                  ChunkBuilder.BuiltChunk _snowmanxxxxxxxxxxxxxxxxxx = this.chunks
                     .getRenderedChunk(
                        new BlockPos(_snowmanxxxxxxxxxxxxx + (_snowmanxxxxxxxxxxxxxxxx << 4) + 8, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx + (_snowmanxxxxxxxxxxxxxxxxx << 4) + 8)
                     );
                  if (_snowmanxxxxxxxxxxxxxxxxxx != null && frustum.isVisible(_snowmanxxxxxxxxxxxxxxxxxx.boundingBox)) {
                     _snowmanxxxxxxxxxxxxxxxxxx.setRebuildFrame(frame);
                     _snowmanxxxxxxxxxxxxxxx.add(new WorldRenderer.ChunkInfo(_snowmanxxxxxxxxxxxxxxxxxx, null, 0));
                  }
               }
            }

            _snowmanxxxxxxxxxxxxxxx.sort(Comparator.comparingDouble(_snowmanxxxxxxxxxxxxxxxx -> _snowman.getSquaredDistance(_snowmanxxxxxxxxxxxxxxxx.chunk.getOrigin().add(8, 8, 8))));
            _snowmanxxxxxxxxxx.addAll(_snowmanxxxxxxxxxxxxxxx);
         }

         this.client.getProfiler().push("iteration");

         while (!_snowmanxxxxxxxxxx.isEmpty()) {
            WorldRenderer.ChunkInfo _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx.poll();
            ChunkBuilder.BuiltChunk _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.chunk;
            Direction _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.direction;
            this.visibleChunks.add(_snowmanxxxxxxxxxxxx);

            for (Direction _snowmanxxxxxxxxxxxxxxx : DIRECTIONS) {
               ChunkBuilder.BuiltChunk _snowmanxxxxxxxxxxxxxxxx = this.getAdjacentChunk(_snowmanxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
               if ((!_snowmanxxxxxxxxxxx || !_snowmanxxxxxxxxxxxx.canCull(_snowmanxxxxxxxxxxxxxxx.getOpposite()))
                  && (!_snowmanxxxxxxxxxxx || _snowmanxxxxxxxxxxxxxx == null || _snowmanxxxxxxxxxxxxx.getData().isVisibleThrough(_snowmanxxxxxxxxxxxxxx.getOpposite(), _snowmanxxxxxxxxxxxxxxx))
                  && _snowmanxxxxxxxxxxxxxxxx != null
                  && _snowmanxxxxxxxxxxxxxxxx.shouldBuild()
                  && _snowmanxxxxxxxxxxxxxxxx.setRebuildFrame(frame)
                  && frustum.isVisible(_snowmanxxxxxxxxxxxxxxxx.boundingBox)) {
                  WorldRenderer.ChunkInfo _snowmanxxxxxxxxxxxxxxxxxx = new WorldRenderer.ChunkInfo(
                     _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx.propagationLevel + 1
                  );
                  _snowmanxxxxxxxxxxxxxxxxxx.updateCullingState(_snowmanxxxxxxxxxxxx.cullingState, _snowmanxxxxxxxxxxxxxxx);
                  _snowmanxxxxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxxxx);
               }
            }
         }

         this.client.getProfiler().pop();
      }

      this.client.getProfiler().swap("rebuildNear");
      Set<ChunkBuilder.BuiltChunk> _snowmanxxxxxxxxxx = this.chunksToRebuild;
      this.chunksToRebuild = Sets.newLinkedHashSet();
      ObjectListIterator var31 = this.visibleChunks.iterator();

      while (var31.hasNext()) {
         WorldRenderer.ChunkInfo _snowmanxxxxxxxxxxx = (WorldRenderer.ChunkInfo)var31.next();
         ChunkBuilder.BuiltChunk _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.chunk;
         if (_snowmanxxxxxxxxxxxx.needsRebuild() || _snowmanxxxxxxxxxx.contains(_snowmanxxxxxxxxxxxx)) {
            this.needsTerrainUpdate = true;
            BlockPos _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.getOrigin().add(8, 8, 8);
            boolean _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.getSquaredDistance(_snowmanxxxx) < 768.0;
            if (!_snowmanxxxxxxxxxxxx.needsImportantRebuild() && !_snowmanxxxxxxxxxxxxxx) {
               this.chunksToRebuild.add(_snowmanxxxxxxxxxxxx);
            } else {
               this.client.getProfiler().push("build near");
               this.chunkBuilder.rebuild(_snowmanxxxxxxxxxxxx);
               _snowmanxxxxxxxxxxxx.cancelRebuild();
               this.client.getProfiler().pop();
            }
         }
      }

      this.chunksToRebuild.addAll(_snowmanxxxxxxxxxx);
      this.client.getProfiler().pop();
   }

   @Nullable
   private ChunkBuilder.BuiltChunk getAdjacentChunk(BlockPos pos, ChunkBuilder.BuiltChunk chunk, Direction direction) {
      BlockPos _snowman = chunk.getNeighborPosition(direction);
      if (MathHelper.abs(pos.getX() - _snowman.getX()) > this.renderDistance * 16) {
         return null;
      } else if (_snowman.getY() < 0 || _snowman.getY() >= 256) {
         return null;
      } else {
         return MathHelper.abs(pos.getZ() - _snowman.getZ()) > this.renderDistance * 16 ? null : this.chunks.getRenderedChunk(_snowman);
      }
   }

   private void captureFrustum(Matrix4f modelMatrix, Matrix4f _snowman, double x, double y, double z, Frustum frustum) {
      this.capturedFrustum = frustum;
      Matrix4f _snowmanx = _snowman.copy();
      _snowmanx.multiply(modelMatrix);
      _snowmanx.invert();
      this.capturedFrustumPosition.x = x;
      this.capturedFrustumPosition.y = y;
      this.capturedFrustumPosition.z = z;
      this.capturedFrustumOrientation[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
      this.capturedFrustumOrientation[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
      this.capturedFrustumOrientation[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
      this.capturedFrustumOrientation[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
      this.capturedFrustumOrientation[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
      this.capturedFrustumOrientation[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
      this.capturedFrustumOrientation[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.capturedFrustumOrientation[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);

      for (int _snowmanxx = 0; _snowmanxx < 8; _snowmanxx++) {
         this.capturedFrustumOrientation[_snowmanxx].transform(_snowmanx);
         this.capturedFrustumOrientation[_snowmanxx].normalizeProjectiveCoordinates();
      }
   }

   public void render(
      MatrixStack matrices,
      float tickDelta,
      long limitTime,
      boolean renderBlockOutline,
      Camera camera,
      GameRenderer gameRenderer,
      LightmapTextureManager _snowman,
      Matrix4f _snowman
   ) {
      BlockEntityRenderDispatcher.INSTANCE
         .configure(this.world, this.client.getTextureManager(), this.client.textRenderer, camera, this.client.crosshairTarget);
      this.entityRenderDispatcher.configure(this.world, camera, this.client.targetedEntity);
      Profiler _snowmanxx = this.world.getProfiler();
      _snowmanxx.swap("light_updates");
      this.client.world.getChunkManager().getLightingProvider().doLightUpdates(Integer.MAX_VALUE, true, true);
      Vec3d _snowmanxxx = camera.getPos();
      double _snowmanxxxx = _snowmanxxx.getX();
      double _snowmanxxxxx = _snowmanxxx.getY();
      double _snowmanxxxxxx = _snowmanxxx.getZ();
      Matrix4f _snowmanxxxxxxx = matrices.peek().getModel();
      _snowmanxx.swap("culling");
      boolean _snowmanxxxxxxxx = this.capturedFrustum != null;
      Frustum _snowmanxxxxxxxxx;
      if (_snowmanxxxxxxxx) {
         _snowmanxxxxxxxxx = this.capturedFrustum;
         _snowmanxxxxxxxxx.setPosition(this.capturedFrustumPosition.x, this.capturedFrustumPosition.y, this.capturedFrustumPosition.z);
      } else {
         _snowmanxxxxxxxxx = new Frustum(_snowmanxxxxxxx, _snowman);
         _snowmanxxxxxxxxx.setPosition(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      }

      this.client.getProfiler().swap("captureFrustum");
      if (this.shouldCaptureFrustum) {
         this.captureFrustum(_snowmanxxxxxxx, _snowman, _snowmanxxx.x, _snowmanxxx.y, _snowmanxxx.z, _snowmanxxxxxxxx ? new Frustum(_snowmanxxxxxxx, _snowman) : _snowmanxxxxxxxxx);
         this.shouldCaptureFrustum = false;
      }

      _snowmanxx.swap("clear");
      BackgroundRenderer.render(camera, tickDelta, this.client.world, this.client.options.viewDistance, gameRenderer.getSkyDarkness(tickDelta));
      RenderSystem.clear(16640, MinecraftClient.IS_SYSTEM_MAC);
      float _snowmanxxxxxxxxxx = gameRenderer.getViewDistance();
      boolean _snowmanxxxxxxxxxxx = this.client.world.getSkyProperties().useThickFog(MathHelper.floor(_snowmanxxxx), MathHelper.floor(_snowmanxxxxx))
         || this.client.inGameHud.getBossBarHud().shouldThickenFog();
      if (this.client.options.viewDistance >= 4) {
         BackgroundRenderer.applyFog(camera, BackgroundRenderer.FogType.FOG_SKY, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
         _snowmanxx.swap("sky");
         this.renderSky(matrices, tickDelta);
      }

      _snowmanxx.swap("fog");
      BackgroundRenderer.applyFog(camera, BackgroundRenderer.FogType.FOG_TERRAIN, Math.max(_snowmanxxxxxxxxxx - 16.0F, 32.0F), _snowmanxxxxxxxxxxx);
      _snowmanxx.swap("terrain_setup");
      this.setupTerrain(camera, _snowmanxxxxxxxxx, _snowmanxxxxxxxx, this.frame++, this.client.player.isSpectator());
      _snowmanxx.swap("updatechunks");
      int _snowmanxxxxxxxxxxxx = 30;
      int _snowmanxxxxxxxxxxxxx = this.client.options.maxFps;
      long _snowmanxxxxxxxxxxxxxx = 33333333L;
      long _snowmanxxxxxxxxxxxxxxx;
      if ((double)_snowmanxxxxxxxxxxxxx == Option.FRAMERATE_LIMIT.getMax()) {
         _snowmanxxxxxxxxxxxxxxx = 0L;
      } else {
         _snowmanxxxxxxxxxxxxxxx = (long)(1000000000 / _snowmanxxxxxxxxxxxxx);
      }

      long _snowmanxxxxxxxxxxxxxxxx = Util.getMeasuringTimeNano() - limitTime;
      long _snowmanxxxxxxxxxxxxxxxxx = this.chunkUpdateSmoother.getTargetUsedTime(_snowmanxxxxxxxxxxxxxxxx);
      long _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx * 3L / 2L;
      long _snowmanxxxxxxxxxxxxxxxxxxx = MathHelper.clamp(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, 33333333L);
      this.updateChunks(limitTime + _snowmanxxxxxxxxxxxxxxxxxxx);
      _snowmanxx.swap("terrain");
      this.renderLayer(RenderLayer.getSolid(), matrices, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      this.renderLayer(RenderLayer.getCutoutMipped(), matrices, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      this.renderLayer(RenderLayer.getCutout(), matrices, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      if (this.world.getSkyProperties().isDarkened()) {
         DiffuseLighting.enableForLevel(matrices.peek().getModel());
      } else {
         DiffuseLighting.method_27869(matrices.peek().getModel());
      }

      _snowmanxx.swap("entities");
      this.regularEntityCount = 0;
      this.blockEntityCount = 0;
      if (this.entityFramebuffer != null) {
         this.entityFramebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
         this.entityFramebuffer.copyDepthFrom(this.client.getFramebuffer());
         this.client.getFramebuffer().beginWrite(false);
      }

      if (this.weatherFramebuffer != null) {
         this.weatherFramebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
      }

      if (this.canDrawEntityOutlines()) {
         this.entityOutlinesFramebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
         this.client.getFramebuffer().beginWrite(false);
      }

      boolean _snowmanxxxxxxxxxxxxxxxxxxxx = false;
      VertexConsumerProvider.Immediate _snowmanxxxxxxxxxxxxxxxxxxxxx = this.bufferBuilders.getEntityVertexConsumers();

      for (Entity _snowmanxxxxxxxxxxxxxxxxxxxxxx : this.world.getEntities()) {
         if ((
               this.entityRenderDispatcher.shouldRender(_snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx)
                  || _snowmanxxxxxxxxxxxxxxxxxxxxxx.hasPassengerDeep(this.client.player)
            )
            && (
               _snowmanxxxxxxxxxxxxxxxxxxxxxx != camera.getFocusedEntity()
                  || camera.isThirdPerson()
                  || camera.getFocusedEntity() instanceof LivingEntity && ((LivingEntity)camera.getFocusedEntity()).isSleeping()
            )
            && (!(_snowmanxxxxxxxxxxxxxxxxxxxxxx instanceof ClientPlayerEntity) || camera.getFocusedEntity() == _snowmanxxxxxxxxxxxxxxxxxxxxxx)) {
            this.regularEntityCount++;
            if (_snowmanxxxxxxxxxxxxxxxxxxxxxx.age == 0) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxx.lastRenderX = _snowmanxxxxxxxxxxxxxxxxxxxxxx.getX();
               _snowmanxxxxxxxxxxxxxxxxxxxxxx.lastRenderY = _snowmanxxxxxxxxxxxxxxxxxxxxxx.getY();
               _snowmanxxxxxxxxxxxxxxxxxxxxxx.lastRenderZ = _snowmanxxxxxxxxxxxxxxxxxxxxxx.getZ();
            }

            VertexConsumerProvider _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
            if (this.canDrawEntityOutlines() && this.client.hasOutline(_snowmanxxxxxxxxxxxxxxxxxxxxxx)) {
               _snowmanxxxxxxxxxxxxxxxxxxxx = true;
               OutlineVertexConsumerProvider _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = this.bufferBuilders.getOutlineVertexConsumers();
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.getTeamColorValue();
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = 255;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx >> 16 & 0xFF;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx >> 8 & 0xFF;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx & 0xFF;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.setColor(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 255);
            } else {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx;
            }

            this.renderEntity(_snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, tickDelta, matrices, _snowmanxxxxxxxxxxxxxxxxxxxxxxx);
         }
      }

      this.checkEmpty(matrices);
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(RenderLayer.getEntitySolid(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE));
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(RenderLayer.getEntityCutout(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE));
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(RenderLayer.getEntityCutoutNoCull(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE));
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(RenderLayer.getEntitySmoothCutout(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE));
      _snowmanxx.swap("blockentities");
      ObjectListIterator var53 = this.visibleChunks.iterator();

      while (var53.hasNext()) {
         WorldRenderer.ChunkInfo _snowmanxxxxxxxxxxxxxxxxxxxxxxx = (WorldRenderer.ChunkInfo)var53.next();
         List<BlockEntity> _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxx.chunk.getData().getBlockEntities();
         if (!_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.isEmpty()) {
            for (BlockEntity _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxxxxxxxxx) {
               BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.getPos();
               VertexConsumerProvider _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx;
               matrices.push();
               matrices.translate(
                  (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.getX() - _snowmanxxxx,
                  (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.getY() - _snowmanxxxxx,
                  (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.getZ() - _snowmanxxxxxx
               );
               SortedSet<BlockBreakingInfo> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (SortedSet<BlockBreakingInfo>)this.blockBreakingProgressions
                  .get(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.asLong());
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.isEmpty()) {
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.last().getStage();
                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx >= 0) {
                     MatrixStack.Entry _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = matrices.peek();
                     VertexConsumer _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new OverlayVertexConsumer(
                        this.bufferBuilders
                           .getEffectVertexConsumers()
                           .getBuffer(ModelLoader.BLOCK_DESTRUCTION_RENDER_LAYERS.get(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)),
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getModel(),
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getNormal()
                     );
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -> {
                        VertexConsumer _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getBuffer(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                        return _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.hasCrumbling()
                           ? VertexConsumers.dual(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           : _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                     };
                  }
               }

               BlockEntityRenderDispatcher.INSTANCE.render(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, tickDelta, matrices, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx);
               matrices.pop();
            }
         }
      }

      synchronized (this.noCullingBlockEntities) {
         for (BlockEntity _snowmanxxxxxxxxxxxxxxxxxxxxxxx : this.noCullingBlockEntities) {
            BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxx.getPos();
            matrices.push();
            matrices.translate(
               (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.getX() - _snowmanxxxx,
               (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.getY() - _snowmanxxxxx,
               (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.getZ() - _snowmanxxxxxx
            );
            BlockEntityRenderDispatcher.INSTANCE.render(_snowmanxxxxxxxxxxxxxxxxxxxxxxx, tickDelta, matrices, _snowmanxxxxxxxxxxxxxxxxxxxxx);
            matrices.pop();
         }
      }

      this.checkEmpty(matrices);
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(RenderLayer.getSolid());
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(TexturedRenderLayers.getEntitySolid());
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(TexturedRenderLayers.getEntityCutout());
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(TexturedRenderLayers.getBeds());
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(TexturedRenderLayers.getShulkerBoxes());
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(TexturedRenderLayers.getSign());
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(TexturedRenderLayers.getChest());
      this.bufferBuilders.getOutlineVertexConsumers().draw();
      if (_snowmanxxxxxxxxxxxxxxxxxxxx) {
         this.entityOutlineShader.render(tickDelta);
         this.client.getFramebuffer().beginWrite(false);
      }

      _snowmanxx.swap("destroyProgress");
      ObjectIterator var55 = this.blockBreakingProgressions.long2ObjectEntrySet().iterator();

      while (var55.hasNext()) {
         Entry<SortedSet<BlockBreakingInfo>> _snowmanxxxxxxxxxxxxxxxxxxxxxxx = (Entry<SortedSet<BlockBreakingInfo>>)var55.next();
         BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = BlockPos.fromLong(_snowmanxxxxxxxxxxxxxxxxxxxxxxx.getLongKey());
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.getX() - _snowmanxxxx;
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.getY() - _snowmanxxxxx;
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.getZ() - _snowmanxxxxxx;
         if (!(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx
                  + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx
                  + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx
               > 1024.0
         )) {
            SortedSet<BlockBreakingInfo> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (SortedSet<BlockBreakingInfo>)_snowmanxxxxxxxxxxxxxxxxxxxxxxx.getValue();
            if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.isEmpty()) {
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.last().getStage();
               matrices.push();
               matrices.translate(
                  (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.getX() - _snowmanxxxx,
                  (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.getY() - _snowmanxxxxx,
                  (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.getZ() - _snowmanxxxxxx
               );
               MatrixStack.Entry _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = matrices.peek();
               VertexConsumer _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new OverlayVertexConsumer(
                  this.bufferBuilders.getEffectVertexConsumers().getBuffer(ModelLoader.BLOCK_DESTRUCTION_RENDER_LAYERS.get(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)),
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getModel(),
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getNormal()
               );
               this.client
                  .getBlockRenderManager()
                  .renderDamage(
                     this.world.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx), _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, this.world, matrices, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                  );
               matrices.pop();
            }
         }
      }

      this.checkEmpty(matrices);
      HitResult _snowmanxxxxxxxxxxxxxxxxxxxxxxx = this.client.crosshairTarget;
      if (renderBlockOutline && _snowmanxxxxxxxxxxxxxxxxxxxxxxx != null && _snowmanxxxxxxxxxxxxxxxxxxxxxxx.getType() == HitResult.Type.BLOCK) {
         _snowmanxx.swap("outline");
         BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = ((BlockHitResult)_snowmanxxxxxxxxxxxxxxxxxxxxxxx).getBlockPos();
         BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = this.world.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
         if (!_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.isAir() && this.world.getWorldBorder().contains(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx)) {
            VertexConsumer _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx.getBuffer(RenderLayer.getLines());
            this.drawBlockOutline(
               matrices, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, camera.getFocusedEntity(), _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx
            );
         }
      }

      RenderSystem.pushMatrix();
      RenderSystem.multMatrix(matrices.peek().getModel());
      this.client.debugRenderer.render(matrices, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      RenderSystem.popMatrix();
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(TexturedRenderLayers.getEntityTranslucentCull());
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(TexturedRenderLayers.getBannerPatterns());
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(TexturedRenderLayers.getShieldPatterns());
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(RenderLayer.getArmorGlint());
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(RenderLayer.getArmorEntityGlint());
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(RenderLayer.getGlint());
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(RenderLayer.getDirectGlint());
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(RenderLayer.method_30676());
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(RenderLayer.getEntityGlint());
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(RenderLayer.getDirectEntityGlint());
      _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(RenderLayer.getWaterMask());
      this.bufferBuilders.getEffectVertexConsumers().draw();
      if (this.transparencyShader != null) {
         _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(RenderLayer.getLines());
         _snowmanxxxxxxxxxxxxxxxxxxxxx.draw();
         this.translucentFramebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
         this.translucentFramebuffer.copyDepthFrom(this.client.getFramebuffer());
         _snowmanxx.swap("translucent");
         this.renderLayer(RenderLayer.getTranslucent(), matrices, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
         _snowmanxx.swap("string");
         this.renderLayer(RenderLayer.getTripwire(), matrices, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
         this.particlesFramebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
         this.particlesFramebuffer.copyDepthFrom(this.client.getFramebuffer());
         RenderPhase.PARTICLES_TARGET.startDrawing();
         _snowmanxx.swap("particles");
         this.client.particleManager.renderParticles(matrices, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowman, camera, tickDelta);
         RenderPhase.PARTICLES_TARGET.endDrawing();
      } else {
         _snowmanxx.swap("translucent");
         this.renderLayer(RenderLayer.getTranslucent(), matrices, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
         _snowmanxxxxxxxxxxxxxxxxxxxxx.draw(RenderLayer.getLines());
         _snowmanxxxxxxxxxxxxxxxxxxxxx.draw();
         _snowmanxx.swap("string");
         this.renderLayer(RenderLayer.getTripwire(), matrices, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
         _snowmanxx.swap("particles");
         this.client.particleManager.renderParticles(matrices, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowman, camera, tickDelta);
      }

      RenderSystem.pushMatrix();
      RenderSystem.multMatrix(matrices.peek().getModel());
      if (this.client.options.getCloudRenderMode() != CloudRenderMode.OFF) {
         if (this.transparencyShader != null) {
            this.cloudsFramebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
            RenderPhase.CLOUDS_TARGET.startDrawing();
            _snowmanxx.swap("clouds");
            this.renderClouds(matrices, tickDelta, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
            RenderPhase.CLOUDS_TARGET.endDrawing();
         } else {
            _snowmanxx.swap("clouds");
            this.renderClouds(matrices, tickDelta, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
         }
      }

      if (this.transparencyShader != null) {
         RenderPhase.WEATHER_TARGET.startDrawing();
         _snowmanxx.swap("weather");
         this.renderWeather(_snowman, tickDelta, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
         this.renderWorldBorder(camera);
         RenderPhase.WEATHER_TARGET.endDrawing();
         this.transparencyShader.render(tickDelta);
         this.client.getFramebuffer().beginWrite(false);
      } else {
         RenderSystem.depthMask(false);
         _snowmanxx.swap("weather");
         this.renderWeather(_snowman, tickDelta, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
         this.renderWorldBorder(camera);
         RenderSystem.depthMask(true);
      }

      this.renderChunkDebugInfo(camera);
      RenderSystem.shadeModel(7424);
      RenderSystem.depthMask(true);
      RenderSystem.disableBlend();
      RenderSystem.popMatrix();
      BackgroundRenderer.method_23792();
   }

   private void checkEmpty(MatrixStack matrices) {
      if (!matrices.isEmpty()) {
         throw new IllegalStateException("Pose stack not empty");
      }
   }

   private void renderEntity(
      Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers
   ) {
      double _snowman = MathHelper.lerp((double)tickDelta, entity.lastRenderX, entity.getX());
      double _snowmanx = MathHelper.lerp((double)tickDelta, entity.lastRenderY, entity.getY());
      double _snowmanxx = MathHelper.lerp((double)tickDelta, entity.lastRenderZ, entity.getZ());
      float _snowmanxxx = MathHelper.lerp(tickDelta, entity.prevYaw, entity.yaw);
      this.entityRenderDispatcher
         .render(
            entity,
            _snowman - cameraX,
            _snowmanx - cameraY,
            _snowmanxx - cameraZ,
            _snowmanxxx,
            tickDelta,
            matrices,
            vertexConsumers,
            this.entityRenderDispatcher.getLight(entity, tickDelta)
         );
   }

   private void renderLayer(RenderLayer _snowman, MatrixStack _snowman, double _snowman, double _snowman, double _snowman) {
      _snowman.startDrawing();
      if (_snowman == RenderLayer.getTranslucent()) {
         this.client.getProfiler().push("translucent_sort");
         double _snowmanxxxxx = _snowman - this.lastTranslucentSortX;
         double _snowmanxxxxxx = _snowman - this.lastTranslucentSortY;
         double _snowmanxxxxxxx = _snowman - this.lastTranslucentSortZ;
         if (_snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxx * _snowmanxxxxxx + _snowmanxxxxxxx * _snowmanxxxxxxx > 1.0) {
            this.lastTranslucentSortX = _snowman;
            this.lastTranslucentSortY = _snowman;
            this.lastTranslucentSortZ = _snowman;
            int _snowmanxxxxxxxx = 0;
            ObjectListIterator var16 = this.visibleChunks.iterator();

            while (var16.hasNext()) {
               WorldRenderer.ChunkInfo _snowmanxxxxxxxxx = (WorldRenderer.ChunkInfo)var16.next();
               if (_snowmanxxxxxxxx < 15 && _snowmanxxxxxxxxx.chunk.scheduleSort(_snowman, this.chunkBuilder)) {
                  _snowmanxxxxxxxx++;
               }
            }
         }

         this.client.getProfiler().pop();
      }

      this.client.getProfiler().push("filterempty");
      this.client.getProfiler().swap(() -> "render_" + _snowman);
      boolean _snowmanxxxxx = _snowman != RenderLayer.getTranslucent();
      ObjectListIterator<WorldRenderer.ChunkInfo> _snowmanxxxxxx = this.visibleChunks.listIterator(_snowmanxxxxx ? 0 : this.visibleChunks.size());

      while (_snowmanxxxxx ? _snowmanxxxxxx.hasNext() : _snowmanxxxxxx.hasPrevious()) {
         WorldRenderer.ChunkInfo _snowmanxxxxxxx = _snowmanxxxxx ? (WorldRenderer.ChunkInfo)_snowmanxxxxxx.next() : (WorldRenderer.ChunkInfo)_snowmanxxxxxx.previous();
         ChunkBuilder.BuiltChunk _snowmanxxxxxxxx = _snowmanxxxxxxx.chunk;
         if (!_snowmanxxxxxxxx.getData().isEmpty(_snowman)) {
            VertexBuffer _snowmanxxxxxxxxx = _snowmanxxxxxxxx.getBuffer(_snowman);
            _snowman.push();
            BlockPos _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.getOrigin();
            _snowman.translate((double)_snowmanxxxxxxxxxx.getX() - _snowman, (double)_snowmanxxxxxxxxxx.getY() - _snowman, (double)_snowmanxxxxxxxxxx.getZ() - _snowman);
            _snowmanxxxxxxxxx.bind();
            this.vertexFormat.startDrawing(0L);
            _snowmanxxxxxxxxx.draw(_snowman.peek().getModel(), 7);
            _snowman.pop();
         }
      }

      VertexBuffer.unbind();
      RenderSystem.clearCurrentColor();
      this.vertexFormat.endDrawing();
      this.client.getProfiler().pop();
      _snowman.endDrawing();
   }

   private void renderChunkDebugInfo(Camera camera) {
      Tessellator _snowman = Tessellator.getInstance();
      BufferBuilder _snowmanx = _snowman.getBuffer();
      if (this.client.debugChunkInfo || this.client.debugChunkOcclusion) {
         double _snowmanxx = camera.getPos().getX();
         double _snowmanxxx = camera.getPos().getY();
         double _snowmanxxxx = camera.getPos().getZ();
         RenderSystem.depthMask(true);
         RenderSystem.disableCull();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableTexture();

         for (ObjectListIterator var10 = this.visibleChunks.iterator(); var10.hasNext(); RenderSystem.popMatrix()) {
            WorldRenderer.ChunkInfo _snowmanxxxxx = (WorldRenderer.ChunkInfo)var10.next();
            ChunkBuilder.BuiltChunk _snowmanxxxxxx = _snowmanxxxxx.chunk;
            RenderSystem.pushMatrix();
            BlockPos _snowmanxxxxxxx = _snowmanxxxxxx.getOrigin();
            RenderSystem.translated((double)_snowmanxxxxxxx.getX() - _snowmanxx, (double)_snowmanxxxxxxx.getY() - _snowmanxxx, (double)_snowmanxxxxxxx.getZ() - _snowmanxxxx);
            if (this.client.debugChunkInfo) {
               _snowmanx.begin(1, VertexFormats.POSITION_COLOR);
               RenderSystem.lineWidth(10.0F);
               int _snowmanxxxxxxxx = _snowmanxxxxx.propagationLevel == 0 ? 0 : MathHelper.hsvToRgb((float)_snowmanxxxxx.propagationLevel / 50.0F, 0.9F, 0.9F);
               int _snowmanxxxxxxxxx = _snowmanxxxxxxxx >> 16 & 0xFF;
               int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx >> 8 & 0xFF;
               int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx & 0xFF;
               Direction _snowmanxxxxxxxxxxxx = _snowmanxxxxx.direction;
               if (_snowmanxxxxxxxxxxxx != null) {
                  _snowmanx.vertex(8.0, 8.0, 8.0).color(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, 255).next();
                  _snowmanx.vertex(
                        (double)(8 - 16 * _snowmanxxxxxxxxxxxx.getOffsetX()),
                        (double)(8 - 16 * _snowmanxxxxxxxxxxxx.getOffsetY()),
                        (double)(8 - 16 * _snowmanxxxxxxxxxxxx.getOffsetZ())
                     )
                     .color(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, 255)
                     .next();
               }

               _snowman.draw();
               RenderSystem.lineWidth(1.0F);
            }

            if (this.client.debugChunkOcclusion && !_snowmanxxxxxx.getData().isEmpty()) {
               _snowmanx.begin(1, VertexFormats.POSITION_COLOR);
               RenderSystem.lineWidth(10.0F);
               int _snowmanxxxxxxxx = 0;

               for (Direction _snowmanxxxxxxxxx : DIRECTIONS) {
                  for (Direction _snowmanxxxxxxxxxx : DIRECTIONS) {
                     boolean _snowmanxxxxxxxxxxx = _snowmanxxxxxx.getData().isVisibleThrough(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
                     if (!_snowmanxxxxxxxxxxx) {
                        _snowmanxxxxxxxx++;
                        _snowmanx.vertex(
                              (double)(8 + 8 * _snowmanxxxxxxxxx.getOffsetX()), (double)(8 + 8 * _snowmanxxxxxxxxx.getOffsetY()), (double)(8 + 8 * _snowmanxxxxxxxxx.getOffsetZ())
                           )
                           .color(1, 0, 0, 1)
                           .next();
                        _snowmanx.vertex(
                              (double)(8 + 8 * _snowmanxxxxxxxxxx.getOffsetX()),
                              (double)(8 + 8 * _snowmanxxxxxxxxxx.getOffsetY()),
                              (double)(8 + 8 * _snowmanxxxxxxxxxx.getOffsetZ())
                           )
                           .color(1, 0, 0, 1)
                           .next();
                     }
                  }
               }

               _snowman.draw();
               RenderSystem.lineWidth(1.0F);
               if (_snowmanxxxxxxxx > 0) {
                  _snowmanx.begin(7, VertexFormats.POSITION_COLOR);
                  float _snowmanxxxxxxxxx = 0.5F;
                  float _snowmanxxxxxxxxxxx = 0.2F;
                  _snowmanx.vertex(0.5, 15.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(15.5, 15.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(15.5, 15.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(0.5, 15.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(0.5, 0.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(15.5, 0.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(15.5, 0.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(0.5, 0.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(0.5, 15.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(0.5, 15.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(0.5, 0.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(0.5, 0.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(15.5, 0.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(15.5, 0.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(15.5, 15.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(15.5, 15.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(0.5, 0.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(15.5, 0.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(15.5, 15.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(0.5, 15.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(0.5, 15.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(15.5, 15.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(15.5, 0.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowmanx.vertex(0.5, 0.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).next();
                  _snowman.draw();
               }
            }
         }

         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
         RenderSystem.enableCull();
         RenderSystem.enableTexture();
      }

      if (this.capturedFrustum != null) {
         RenderSystem.disableCull();
         RenderSystem.disableTexture();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.lineWidth(10.0F);
         RenderSystem.pushMatrix();
         RenderSystem.translatef(
            (float)(this.capturedFrustumPosition.x - camera.getPos().x),
            (float)(this.capturedFrustumPosition.y - camera.getPos().y),
            (float)(this.capturedFrustumPosition.z - camera.getPos().z)
         );
         RenderSystem.depthMask(true);
         _snowmanx.begin(7, VertexFormats.POSITION_COLOR);
         this.method_22985(_snowmanx, 0, 1, 2, 3, 0, 1, 1);
         this.method_22985(_snowmanx, 4, 5, 6, 7, 1, 0, 0);
         this.method_22985(_snowmanx, 0, 1, 5, 4, 1, 1, 0);
         this.method_22985(_snowmanx, 2, 3, 7, 6, 0, 0, 1);
         this.method_22985(_snowmanx, 0, 4, 7, 3, 0, 1, 0);
         this.method_22985(_snowmanx, 1, 5, 6, 2, 1, 0, 1);
         _snowman.draw();
         RenderSystem.depthMask(false);
         _snowmanx.begin(1, VertexFormats.POSITION);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.method_22984(_snowmanx, 0);
         this.method_22984(_snowmanx, 1);
         this.method_22984(_snowmanx, 1);
         this.method_22984(_snowmanx, 2);
         this.method_22984(_snowmanx, 2);
         this.method_22984(_snowmanx, 3);
         this.method_22984(_snowmanx, 3);
         this.method_22984(_snowmanx, 0);
         this.method_22984(_snowmanx, 4);
         this.method_22984(_snowmanx, 5);
         this.method_22984(_snowmanx, 5);
         this.method_22984(_snowmanx, 6);
         this.method_22984(_snowmanx, 6);
         this.method_22984(_snowmanx, 7);
         this.method_22984(_snowmanx, 7);
         this.method_22984(_snowmanx, 4);
         this.method_22984(_snowmanx, 0);
         this.method_22984(_snowmanx, 4);
         this.method_22984(_snowmanx, 1);
         this.method_22984(_snowmanx, 5);
         this.method_22984(_snowmanx, 2);
         this.method_22984(_snowmanx, 6);
         this.method_22984(_snowmanx, 3);
         this.method_22984(_snowmanx, 7);
         _snowman.draw();
         RenderSystem.popMatrix();
         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
         RenderSystem.enableCull();
         RenderSystem.enableTexture();
         RenderSystem.lineWidth(1.0F);
      }
   }

   private void method_22984(VertexConsumer _snowman, int _snowman) {
      _snowman.vertex(
            (double)this.capturedFrustumOrientation[_snowman].getX(),
            (double)this.capturedFrustumOrientation[_snowman].getY(),
            (double)this.capturedFrustumOrientation[_snowman].getZ()
         )
         .next();
   }

   private void method_22985(VertexConsumer _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      float _snowmanxxxxxxxx = 0.25F;
      _snowman.vertex(
            (double)this.capturedFrustumOrientation[_snowman].getX(),
            (double)this.capturedFrustumOrientation[_snowman].getY(),
            (double)this.capturedFrustumOrientation[_snowman].getZ()
         )
         .color((float)_snowman, (float)_snowman, (float)_snowman, 0.25F)
         .next();
      _snowman.vertex(
            (double)this.capturedFrustumOrientation[_snowman].getX(),
            (double)this.capturedFrustumOrientation[_snowman].getY(),
            (double)this.capturedFrustumOrientation[_snowman].getZ()
         )
         .color((float)_snowman, (float)_snowman, (float)_snowman, 0.25F)
         .next();
      _snowman.vertex(
            (double)this.capturedFrustumOrientation[_snowman].getX(),
            (double)this.capturedFrustumOrientation[_snowman].getY(),
            (double)this.capturedFrustumOrientation[_snowman].getZ()
         )
         .color((float)_snowman, (float)_snowman, (float)_snowman, 0.25F)
         .next();
      _snowman.vertex(
            (double)this.capturedFrustumOrientation[_snowman].getX(),
            (double)this.capturedFrustumOrientation[_snowman].getY(),
            (double)this.capturedFrustumOrientation[_snowman].getZ()
         )
         .color((float)_snowman, (float)_snowman, (float)_snowman, 0.25F)
         .next();
   }

   public void tick() {
      this.ticks++;
      if (this.ticks % 20 == 0) {
         Iterator<BlockBreakingInfo> _snowman = this.blockBreakingInfos.values().iterator();

         while (_snowman.hasNext()) {
            BlockBreakingInfo _snowmanx = _snowman.next();
            int _snowmanxx = _snowmanx.getLastUpdateTick();
            if (this.ticks - _snowmanxx > 400) {
               _snowman.remove();
               this.removeBlockBreakingInfo(_snowmanx);
            }
         }
      }
   }

   private void removeBlockBreakingInfo(BlockBreakingInfo _snowman) {
      long _snowmanx = _snowman.getPos().asLong();
      Set<BlockBreakingInfo> _snowmanxx = (Set<BlockBreakingInfo>)this.blockBreakingProgressions.get(_snowmanx);
      _snowmanxx.remove(_snowman);
      if (_snowmanxx.isEmpty()) {
         this.blockBreakingProgressions.remove(_snowmanx);
      }
   }

   private void renderEndSky(MatrixStack matrices) {
      RenderSystem.disableAlphaTest();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.depthMask(false);
      this.textureManager.bindTexture(END_SKY);
      Tessellator _snowman = Tessellator.getInstance();
      BufferBuilder _snowmanx = _snowman.getBuffer();

      for (int _snowmanxx = 0; _snowmanxx < 6; _snowmanxx++) {
         matrices.push();
         if (_snowmanxx == 1) {
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
         }

         if (_snowmanxx == 2) {
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
         }

         if (_snowmanxx == 3) {
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F));
         }

         if (_snowmanxx == 4) {
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
         }

         if (_snowmanxx == 5) {
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-90.0F));
         }

         Matrix4f _snowmanxxx = matrices.peek().getModel();
         _snowmanx.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
         _snowmanx.vertex(_snowmanxxx, -100.0F, -100.0F, -100.0F).texture(0.0F, 0.0F).color(40, 40, 40, 255).next();
         _snowmanx.vertex(_snowmanxxx, -100.0F, -100.0F, 100.0F).texture(0.0F, 16.0F).color(40, 40, 40, 255).next();
         _snowmanx.vertex(_snowmanxxx, 100.0F, -100.0F, 100.0F).texture(16.0F, 16.0F).color(40, 40, 40, 255).next();
         _snowmanx.vertex(_snowmanxxx, 100.0F, -100.0F, -100.0F).texture(16.0F, 0.0F).color(40, 40, 40, 255).next();
         _snowman.draw();
         matrices.pop();
      }

      RenderSystem.depthMask(true);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      RenderSystem.enableAlphaTest();
   }

   public void renderSky(MatrixStack matrices, float tickDelta) {
      if (this.client.world.getSkyProperties().getSkyType() == SkyProperties.SkyType.END) {
         this.renderEndSky(matrices);
      } else if (this.client.world.getSkyProperties().getSkyType() == SkyProperties.SkyType.NORMAL) {
         RenderSystem.disableTexture();
         Vec3d _snowman = this.world.method_23777(this.client.gameRenderer.getCamera().getBlockPos(), tickDelta);
         float _snowmanx = (float)_snowman.x;
         float _snowmanxx = (float)_snowman.y;
         float _snowmanxxx = (float)_snowman.z;
         BackgroundRenderer.setFogBlack();
         BufferBuilder _snowmanxxxx = Tessellator.getInstance().getBuffer();
         RenderSystem.depthMask(false);
         RenderSystem.enableFog();
         RenderSystem.color3f(_snowmanx, _snowmanxx, _snowmanxxx);
         this.lightSkyBuffer.bind();
         this.skyVertexFormat.startDrawing(0L);
         this.lightSkyBuffer.draw(matrices.peek().getModel(), 7);
         VertexBuffer.unbind();
         this.skyVertexFormat.endDrawing();
         RenderSystem.disableFog();
         RenderSystem.disableAlphaTest();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         float[] _snowmanxxxxx = this.world.getSkyProperties().getFogColorOverride(this.world.getSkyAngle(tickDelta), tickDelta);
         if (_snowmanxxxxx != null) {
            RenderSystem.disableTexture();
            RenderSystem.shadeModel(7425);
            matrices.push();
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
            float _snowmanxxxxxx = MathHelper.sin(this.world.getSkyAngleRadians(tickDelta)) < 0.0F ? 180.0F : 0.0F;
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowmanxxxxxx));
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
            float _snowmanxxxxxxx = _snowmanxxxxx[0];
            float _snowmanxxxxxxxx = _snowmanxxxxx[1];
            float _snowmanxxxxxxxxx = _snowmanxxxxx[2];
            Matrix4f _snowmanxxxxxxxxxx = matrices.peek().getModel();
            _snowmanxxxx.begin(6, VertexFormats.POSITION_COLOR);
            _snowmanxxxx.vertex(_snowmanxxxxxxxxxx, 0.0F, 100.0F, 0.0F).color(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxx[3]).next();
            int _snowmanxxxxxxxxxxx = 16;

            for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx <= 16; _snowmanxxxxxxxxxxxx++) {
               float _snowmanxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxx * (float) (Math.PI * 2) / 16.0F;
               float _snowmanxxxxxxxxxxxxxx = MathHelper.sin(_snowmanxxxxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxxx);
               _snowmanxxxx.vertex(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx * 120.0F, _snowmanxxxxxxxxxxxxxxx * 120.0F, -_snowmanxxxxxxxxxxxxxxx * 40.0F * _snowmanxxxxx[3])
                  .color(_snowmanxxxxx[0], _snowmanxxxxx[1], _snowmanxxxxx[2], 0.0F)
                  .next();
            }

            _snowmanxxxx.end();
            BufferRenderer.draw(_snowmanxxxx);
            matrices.pop();
            RenderSystem.shadeModel(7424);
         }

         RenderSystem.enableTexture();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO
         );
         matrices.push();
         float _snowmanxxxxxx = 1.0F - this.world.getRainGradient(tickDelta);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, _snowmanxxxxxx);
         matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
         matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(this.world.getSkyAngle(tickDelta) * 360.0F));
         Matrix4f _snowmanxxxxxxx = matrices.peek().getModel();
         float _snowmanxxxxxxxx = 30.0F;
         this.textureManager.bindTexture(SUN);
         _snowmanxxxx.begin(7, VertexFormats.POSITION_TEXTURE);
         _snowmanxxxx.vertex(_snowmanxxxxxxx, -_snowmanxxxxxxxx, 100.0F, -_snowmanxxxxxxxx).texture(0.0F, 0.0F).next();
         _snowmanxxxx.vertex(_snowmanxxxxxxx, _snowmanxxxxxxxx, 100.0F, -_snowmanxxxxxxxx).texture(1.0F, 0.0F).next();
         _snowmanxxxx.vertex(_snowmanxxxxxxx, _snowmanxxxxxxxx, 100.0F, _snowmanxxxxxxxx).texture(1.0F, 1.0F).next();
         _snowmanxxxx.vertex(_snowmanxxxxxxx, -_snowmanxxxxxxxx, 100.0F, _snowmanxxxxxxxx).texture(0.0F, 1.0F).next();
         _snowmanxxxx.end();
         BufferRenderer.draw(_snowmanxxxx);
         _snowmanxxxxxxxx = 20.0F;
         this.textureManager.bindTexture(MOON_PHASES);
         int _snowmanxxxxxxxxx = this.world.getMoonPhase();
         int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx % 4;
         int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx / 4 % 2;
         float _snowmanxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxx + 0) / 4.0F;
         float _snowmanxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxx + 0) / 2.0F;
         float _snowmanxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxx + 1) / 4.0F;
         float _snowmanxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxx + 1) / 2.0F;
         _snowmanxxxx.begin(7, VertexFormats.POSITION_TEXTURE);
         _snowmanxxxx.vertex(_snowmanxxxxxxx, -_snowmanxxxxxxxx, -100.0F, _snowmanxxxxxxxx).texture(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx).next();
         _snowmanxxxx.vertex(_snowmanxxxxxxx, _snowmanxxxxxxxx, -100.0F, _snowmanxxxxxxxx).texture(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx).next();
         _snowmanxxxx.vertex(_snowmanxxxxxxx, _snowmanxxxxxxxx, -100.0F, -_snowmanxxxxxxxx).texture(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx).next();
         _snowmanxxxx.vertex(_snowmanxxxxxxx, -_snowmanxxxxxxxx, -100.0F, -_snowmanxxxxxxxx).texture(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx).next();
         _snowmanxxxx.end();
         BufferRenderer.draw(_snowmanxxxx);
         RenderSystem.disableTexture();
         float _snowmanxxxxxxxxxxxxxxxx = this.world.method_23787(tickDelta) * _snowmanxxxxxx;
         if (_snowmanxxxxxxxxxxxxxxxx > 0.0F) {
            RenderSystem.color4f(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
            this.starsBuffer.bind();
            this.skyVertexFormat.startDrawing(0L);
            this.starsBuffer.draw(matrices.peek().getModel(), 7);
            VertexBuffer.unbind();
            this.skyVertexFormat.endDrawing();
         }

         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableBlend();
         RenderSystem.enableAlphaTest();
         RenderSystem.enableFog();
         matrices.pop();
         RenderSystem.disableTexture();
         RenderSystem.color3f(0.0F, 0.0F, 0.0F);
         double _snowmanxxxxxxxxxxxxxxxxx = this.client.player.getCameraPosVec(tickDelta).y - this.world.getLevelProperties().getSkyDarknessHeight();
         if (_snowmanxxxxxxxxxxxxxxxxx < 0.0) {
            matrices.push();
            matrices.translate(0.0, 12.0, 0.0);
            this.darkSkyBuffer.bind();
            this.skyVertexFormat.startDrawing(0L);
            this.darkSkyBuffer.draw(matrices.peek().getModel(), 7);
            VertexBuffer.unbind();
            this.skyVertexFormat.endDrawing();
            matrices.pop();
         }

         if (this.world.getSkyProperties().isAlternateSkyColor()) {
            RenderSystem.color3f(_snowmanx * 0.2F + 0.04F, _snowmanxx * 0.2F + 0.04F, _snowmanxxx * 0.6F + 0.1F);
         } else {
            RenderSystem.color3f(_snowmanx, _snowmanxx, _snowmanxxx);
         }

         RenderSystem.enableTexture();
         RenderSystem.depthMask(true);
         RenderSystem.disableFog();
      }
   }

   public void renderClouds(MatrixStack matrices, float tickDelta, double cameraX, double cameraY, double cameraZ) {
      float _snowman = this.world.getSkyProperties().getCloudsHeight();
      if (!Float.isNaN(_snowman)) {
         RenderSystem.disableCull();
         RenderSystem.enableBlend();
         RenderSystem.enableAlphaTest();
         RenderSystem.enableDepthTest();
         RenderSystem.defaultAlphaFunc();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SrcFactor.SRC_ALPHA,
            GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SrcFactor.ONE,
            GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA
         );
         RenderSystem.enableFog();
         RenderSystem.depthMask(true);
         float _snowmanx = 12.0F;
         float _snowmanxx = 4.0F;
         double _snowmanxxx = 2.0E-4;
         double _snowmanxxxx = (double)(((float)this.ticks + tickDelta) * 0.03F);
         double _snowmanxxxxx = (cameraX + _snowmanxxxx) / 12.0;
         double _snowmanxxxxxx = (double)(_snowman - (float)cameraY + 0.33F);
         double _snowmanxxxxxxx = cameraZ / 12.0 + 0.33F;
         _snowmanxxxxx -= (double)(MathHelper.floor(_snowmanxxxxx / 2048.0) * 2048);
         _snowmanxxxxxxx -= (double)(MathHelper.floor(_snowmanxxxxxxx / 2048.0) * 2048);
         float _snowmanxxxxxxxx = (float)(_snowmanxxxxx - (double)MathHelper.floor(_snowmanxxxxx));
         float _snowmanxxxxxxxxx = (float)(_snowmanxxxxxx / 4.0 - (double)MathHelper.floor(_snowmanxxxxxx / 4.0)) * 4.0F;
         float _snowmanxxxxxxxxxx = (float)(_snowmanxxxxxxx - (double)MathHelper.floor(_snowmanxxxxxxx));
         Vec3d _snowmanxxxxxxxxxxx = this.world.getCloudsColor(tickDelta);
         int _snowmanxxxxxxxxxxxx = (int)Math.floor(_snowmanxxxxx);
         int _snowmanxxxxxxxxxxxxx = (int)Math.floor(_snowmanxxxxxx / 4.0);
         int _snowmanxxxxxxxxxxxxxx = (int)Math.floor(_snowmanxxxxxxx);
         if (_snowmanxxxxxxxxxxxx != this.lastCloudsBlockX
            || _snowmanxxxxxxxxxxxxx != this.lastCloudsBlockY
            || _snowmanxxxxxxxxxxxxxx != this.lastCloudsBlockZ
            || this.client.options.getCloudRenderMode() != this.lastCloudsRenderMode
            || this.lastCloudsColor.squaredDistanceTo(_snowmanxxxxxxxxxxx) > 2.0E-4) {
            this.lastCloudsBlockX = _snowmanxxxxxxxxxxxx;
            this.lastCloudsBlockY = _snowmanxxxxxxxxxxxxx;
            this.lastCloudsBlockZ = _snowmanxxxxxxxxxxxxxx;
            this.lastCloudsColor = _snowmanxxxxxxxxxxx;
            this.lastCloudsRenderMode = this.client.options.getCloudRenderMode();
            this.cloudsDirty = true;
         }

         if (this.cloudsDirty) {
            this.cloudsDirty = false;
            BufferBuilder _snowmanxxxxxxxxxxxxxxx = Tessellator.getInstance().getBuffer();
            if (this.cloudsBuffer != null) {
               this.cloudsBuffer.close();
            }

            this.cloudsBuffer = new VertexBuffer(VertexFormats.POSITION_TEXTURE_COLOR_NORMAL);
            this.renderClouds(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxxx);
            _snowmanxxxxxxxxxxxxxxx.end();
            this.cloudsBuffer.upload(_snowmanxxxxxxxxxxxxxxx);
         }

         this.textureManager.bindTexture(CLOUDS);
         matrices.push();
         matrices.scale(12.0F, 1.0F, 12.0F);
         matrices.translate((double)(-_snowmanxxxxxxxx), (double)_snowmanxxxxxxxxx, (double)(-_snowmanxxxxxxxxxx));
         if (this.cloudsBuffer != null) {
            this.cloudsBuffer.bind();
            VertexFormats.POSITION_TEXTURE_COLOR_NORMAL.startDrawing(0L);
            int _snowmanxxxxxxxxxxxxxxx = this.lastCloudsRenderMode == CloudRenderMode.FANCY ? 0 : 1;

            for (int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx < 2; _snowmanxxxxxxxxxxxxxxxx++) {
               if (_snowmanxxxxxxxxxxxxxxxx == 0) {
                  RenderSystem.colorMask(false, false, false, false);
               } else {
                  RenderSystem.colorMask(true, true, true, true);
               }

               this.cloudsBuffer.draw(matrices.peek().getModel(), 7);
            }

            VertexBuffer.unbind();
            VertexFormats.POSITION_TEXTURE_COLOR_NORMAL.endDrawing();
         }

         matrices.pop();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableAlphaTest();
         RenderSystem.enableCull();
         RenderSystem.disableBlend();
         RenderSystem.disableFog();
      }
   }

   private void renderClouds(BufferBuilder builder, double x, double y, double z, Vec3d color) {
      float _snowman = 4.0F;
      float _snowmanx = 0.00390625F;
      int _snowmanxx = 8;
      int _snowmanxxx = 4;
      float _snowmanxxxx = 9.765625E-4F;
      float _snowmanxxxxx = (float)MathHelper.floor(x) * 0.00390625F;
      float _snowmanxxxxxx = (float)MathHelper.floor(z) * 0.00390625F;
      float _snowmanxxxxxxx = (float)color.x;
      float _snowmanxxxxxxxx = (float)color.y;
      float _snowmanxxxxxxxxx = (float)color.z;
      float _snowmanxxxxxxxxxx = _snowmanxxxxxxx * 0.9F;
      float _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx * 0.9F;
      float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx * 0.9F;
      float _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxx * 0.7F;
      float _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxx * 0.7F;
      float _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx * 0.7F;
      float _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxx * 0.8F;
      float _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx * 0.8F;
      float _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx * 0.8F;
      builder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_NORMAL);
      float _snowmanxxxxxxxxxxxxxxxxxxx = (float)Math.floor(y / 4.0) * 4.0F;
      if (this.lastCloudsRenderMode == CloudRenderMode.FANCY) {
         for (int _snowmanxxxxxxxxxxxxxxxxxxxx = -3; _snowmanxxxxxxxxxxxxxxxxxxxx <= 4; _snowmanxxxxxxxxxxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxxxxxxxxxxx = -3; _snowmanxxxxxxxxxxxxxxxxxxxxx <= 4; _snowmanxxxxxxxxxxxxxxxxxxxxx++) {
               float _snowmanxxxxxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxxxxxxxx * 8);
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxxxxxxxxx * 8);
               if (_snowmanxxxxxxxxxxxxxxxxxxx > -5.0F) {
                  builder.vertex((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F))
                     .texture((_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxxx)
                     .color(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, 0.8F)
                     .normal(0.0F, -1.0F, 0.0F)
                     .next();
                  builder.vertex((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F))
                     .texture((_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxxx)
                     .color(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, 0.8F)
                     .normal(0.0F, -1.0F, 0.0F)
                     .next();
                  builder.vertex((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F))
                     .texture((_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxxx)
                     .color(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, 0.8F)
                     .normal(0.0F, -1.0F, 0.0F)
                     .next();
                  builder.vertex((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F))
                     .texture((_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxxx)
                     .color(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, 0.8F)
                     .normal(0.0F, -1.0F, 0.0F)
                     .next();
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxx <= 5.0F) {
                  builder.vertex(
                        (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                        (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F - 9.765625E-4F),
                        (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                     )
                     .texture((_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxxx)
                     .color(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.8F)
                     .normal(0.0F, 1.0F, 0.0F)
                     .next();
                  builder.vertex(
                        (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                        (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F - 9.765625E-4F),
                        (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                     )
                     .texture((_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxxx)
                     .color(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.8F)
                     .normal(0.0F, 1.0F, 0.0F)
                     .next();
                  builder.vertex(
                        (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                        (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F - 9.765625E-4F),
                        (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                     )
                     .texture((_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxxx)
                     .color(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.8F)
                     .normal(0.0F, 1.0F, 0.0F)
                     .next();
                  builder.vertex(
                        (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                        (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F - 9.765625E-4F),
                        (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                     )
                     .texture((_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxxx)
                     .color(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.8F)
                     .normal(0.0F, 1.0F, 0.0F)
                     .next();
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxx > -1) {
                  for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx < 8; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx++) {
                     builder.vertex(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                        )
                        .texture(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .color(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.8F)
                        .normal(-1.0F, 0.0F, 0.0F)
                        .next();
                     builder.vertex(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                        )
                        .texture(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .color(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.8F)
                        .normal(-1.0F, 0.0F, 0.0F)
                        .next();
                     builder.vertex(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .texture(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .color(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.8F)
                        .normal(-1.0F, 0.0F, 0.0F)
                        .next();
                     builder.vertex(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .texture(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .color(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.8F)
                        .normal(-1.0F, 0.0F, 0.0F)
                        .next();
                  }
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxx <= 1) {
                  for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx < 8; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx++) {
                     builder.vertex(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                        )
                        .texture(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .color(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.8F)
                        .normal(1.0F, 0.0F, 0.0F)
                        .next();
                     builder.vertex(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                        )
                        .texture(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .color(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.8F)
                        .normal(1.0F, 0.0F, 0.0F)
                        .next();
                     builder.vertex(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .texture(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .color(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.8F)
                        .normal(1.0F, 0.0F, 0.0F)
                        .next();
                     builder.vertex(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .texture(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .color(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.8F)
                        .normal(1.0F, 0.0F, 0.0F)
                        .next();
                  }
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxx > -1) {
                  for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx < 8; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx++) {
                     builder.vertex(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .texture(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .color(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.8F)
                        .normal(0.0F, 0.0F, -1.0F)
                        .next();
                     builder.vertex(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .texture(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .color(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.8F)
                        .normal(0.0F, 0.0F, -1.0F)
                        .next();
                     builder.vertex(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .texture(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .color(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.8F)
                        .normal(0.0F, 0.0F, -1.0F)
                        .next();
                     builder.vertex(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .texture(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .color(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.8F)
                        .normal(0.0F, 0.0F, -1.0F)
                        .next();
                  }
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxx <= 1) {
                  for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx < 8; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx++) {
                     builder.vertex(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F)
                        )
                        .texture(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .color(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.8F)
                        .normal(0.0F, 0.0F, 1.0F)
                        .next();
                     builder.vertex(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F)
                        )
                        .texture(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .color(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.8F)
                        .normal(0.0F, 0.0F, 1.0F)
                        .next();
                     builder.vertex(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F)
                        )
                        .texture(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .color(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.8F)
                        .normal(0.0F, 0.0F, 1.0F)
                        .next();
                     builder.vertex(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F)
                        )
                        .texture(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .color(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.8F)
                        .normal(0.0F, 0.0F, 1.0F)
                        .next();
                  }
               }
            }
         }
      } else {
         int _snowmanxxxxxxxxxxxxxxxxxxxx = 1;
         int _snowmanxxxxxxxxxxxxxxxxxxxxx = 32;

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = -32; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx < 32; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx += 32) {
            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = -32; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx < 32; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx += 32) {
               builder.vertex((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0), (double)_snowmanxxxxxxxxxxxxxxxxxxx, (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 32))
                  .texture((float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0) * 0.00390625F + _snowmanxxxxx, (float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 32) * 0.00390625F + _snowmanxxxxxx)
                  .color(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.8F)
                  .normal(0.0F, -1.0F, 0.0F)
                  .next();
               builder.vertex((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 32), (double)_snowmanxxxxxxxxxxxxxxxxxxx, (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 32))
                  .texture((float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 32) * 0.00390625F + _snowmanxxxxx, (float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 32) * 0.00390625F + _snowmanxxxxxx)
                  .color(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.8F)
                  .normal(0.0F, -1.0F, 0.0F)
                  .next();
               builder.vertex((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 32), (double)_snowmanxxxxxxxxxxxxxxxxxxx, (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 0))
                  .texture((float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 32) * 0.00390625F + _snowmanxxxxx, (float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 0) * 0.00390625F + _snowmanxxxxxx)
                  .color(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.8F)
                  .normal(0.0F, -1.0F, 0.0F)
                  .next();
               builder.vertex((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0), (double)_snowmanxxxxxxxxxxxxxxxxxxx, (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 0))
                  .texture((float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0) * 0.00390625F + _snowmanxxxxx, (float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 0) * 0.00390625F + _snowmanxxxxxx)
                  .color(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.8F)
                  .normal(0.0F, -1.0F, 0.0F)
                  .next();
            }
         }
      }
   }

   private void updateChunks(long limitTime) {
      this.needsTerrainUpdate = this.needsTerrainUpdate | this.chunkBuilder.upload();
      long _snowman = Util.getMeasuringTimeNano();
      int _snowmanx = 0;
      if (!this.chunksToRebuild.isEmpty()) {
         Iterator<ChunkBuilder.BuiltChunk> _snowmanxx = this.chunksToRebuild.iterator();

         while (_snowmanxx.hasNext()) {
            ChunkBuilder.BuiltChunk _snowmanxxx = _snowmanxx.next();
            if (_snowmanxxx.needsImportantRebuild()) {
               this.chunkBuilder.rebuild(_snowmanxxx);
            } else {
               _snowmanxxx.scheduleRebuild(this.chunkBuilder);
            }

            _snowmanxxx.cancelRebuild();
            _snowmanxx.remove();
            _snowmanx++;
            long _snowmanxxxx = Util.getMeasuringTimeNano();
            long _snowmanxxxxx = _snowmanxxxx - _snowman;
            long _snowmanxxxxxx = _snowmanxxxxx / (long)_snowmanx;
            long _snowmanxxxxxxx = limitTime - _snowmanxxxx;
            if (_snowmanxxxxxxx < _snowmanxxxxxx) {
               break;
            }
         }
      }
   }

   private void renderWorldBorder(Camera camera) {
      BufferBuilder _snowman = Tessellator.getInstance().getBuffer();
      WorldBorder _snowmanx = this.world.getWorldBorder();
      double _snowmanxx = (double)(this.client.options.viewDistance * 16);
      if (!(camera.getPos().x < _snowmanx.getBoundEast() - _snowmanxx)
         || !(camera.getPos().x > _snowmanx.getBoundWest() + _snowmanxx)
         || !(camera.getPos().z < _snowmanx.getBoundSouth() - _snowmanxx)
         || !(camera.getPos().z > _snowmanx.getBoundNorth() + _snowmanxx)) {
         double _snowmanxxx = 1.0 - _snowmanx.getDistanceInsideBorder(camera.getPos().x, camera.getPos().z) / _snowmanxx;
         _snowmanxxx = Math.pow(_snowmanxxx, 4.0);
         double _snowmanxxxx = camera.getPos().x;
         double _snowmanxxxxx = camera.getPos().y;
         double _snowmanxxxxxx = camera.getPos().z;
         RenderSystem.enableBlend();
         RenderSystem.enableDepthTest();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO
         );
         this.textureManager.bindTexture(FORCEFIELD);
         RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
         RenderSystem.pushMatrix();
         int _snowmanxxxxxxx = _snowmanx.getStage().getColor();
         float _snowmanxxxxxxxx = (float)(_snowmanxxxxxxx >> 16 & 0xFF) / 255.0F;
         float _snowmanxxxxxxxxx = (float)(_snowmanxxxxxxx >> 8 & 0xFF) / 255.0F;
         float _snowmanxxxxxxxxxx = (float)(_snowmanxxxxxxx & 0xFF) / 255.0F;
         RenderSystem.color4f(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, (float)_snowmanxxx);
         RenderSystem.polygonOffset(-3.0F, -3.0F);
         RenderSystem.enablePolygonOffset();
         RenderSystem.defaultAlphaFunc();
         RenderSystem.enableAlphaTest();
         RenderSystem.disableCull();
         float _snowmanxxxxxxxxxxx = (float)(Util.getMeasuringTimeMs() % 3000L) / 3000.0F;
         float _snowmanxxxxxxxxxxxx = 0.0F;
         float _snowmanxxxxxxxxxxxxx = 0.0F;
         float _snowmanxxxxxxxxxxxxxx = 128.0F;
         _snowman.begin(7, VertexFormats.POSITION_TEXTURE);
         double _snowmanxxxxxxxxxxxxxxx = Math.max((double)MathHelper.floor(_snowmanxxxxxx - _snowmanxx), _snowmanx.getBoundNorth());
         double _snowmanxxxxxxxxxxxxxxxx = Math.min((double)MathHelper.ceil(_snowmanxxxxxx + _snowmanxx), _snowmanx.getBoundSouth());
         if (_snowmanxxxx > _snowmanx.getBoundEast() - _snowmanxx) {
            float _snowmanxxxxxxxxxxxxxxxxx = 0.0F;

            for (double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx += 0.5F) {
               double _snowmanxxxxxxxxxxxxxxxxxxx = Math.min(1.0, _snowmanxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxxxxxxxxx * 0.5F;
               this.method_22978(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanx.getBoundEast(), 256, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + 0.0F);
               this.method_22978(
                  _snowman,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanx.getBoundEast(),
                  256,
                  _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + 0.0F
               );
               this.method_22978(
                  _snowman,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanx.getBoundEast(),
                  0,
                  _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + 128.0F
               );
               this.method_22978(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanx.getBoundEast(), 0, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + 128.0F);
               _snowmanxxxxxxxxxxxxxxxxxx++;
            }
         }

         if (_snowmanxxxx < _snowmanx.getBoundWest() + _snowmanxx) {
            float _snowmanxxxxxxxxxxxxxxxxx = 0.0F;

            for (double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx += 0.5F) {
               double _snowmanxxxxxxxxxxxxxxxxxxx = Math.min(1.0, _snowmanxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxxxxxxxxx * 0.5F;
               this.method_22978(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanx.getBoundWest(), 256, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + 0.0F);
               this.method_22978(
                  _snowman,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanx.getBoundWest(),
                  256,
                  _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + 0.0F
               );
               this.method_22978(
                  _snowman,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanx.getBoundWest(),
                  0,
                  _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + 128.0F
               );
               this.method_22978(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanx.getBoundWest(), 0, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + 128.0F);
               _snowmanxxxxxxxxxxxxxxxxxx++;
            }
         }

         _snowmanxxxxxxxxxxxxxxx = Math.max((double)MathHelper.floor(_snowmanxxxx - _snowmanxx), _snowmanx.getBoundWest());
         _snowmanxxxxxxxxxxxxxxxx = Math.min((double)MathHelper.ceil(_snowmanxxxx + _snowmanxx), _snowmanx.getBoundEast());
         if (_snowmanxxxxxx > _snowmanx.getBoundSouth() - _snowmanxx) {
            float _snowmanxxxxxxxxxxxxxxxxx = 0.0F;

            for (double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx += 0.5F) {
               double _snowmanxxxxxxxxxxxxxxxxxxx = Math.min(1.0, _snowmanxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxxxxxxxxx * 0.5F;
               this.method_22978(
                  _snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 256, _snowmanx.getBoundSouth(), _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + 0.0F
               );
               this.method_22978(
                  _snowman,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                  256,
                  _snowmanx.getBoundSouth(),
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + 0.0F
               );
               this.method_22978(
                  _snowman,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                  0,
                  _snowmanx.getBoundSouth(),
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + 128.0F
               );
               this.method_22978(
                  _snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0, _snowmanx.getBoundSouth(), _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + 128.0F
               );
               _snowmanxxxxxxxxxxxxxxxxxx++;
            }
         }

         if (_snowmanxxxxxx < _snowmanx.getBoundNorth() + _snowmanxx) {
            float _snowmanxxxxxxxxxxxxxxxxx = 0.0F;

            for (double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx += 0.5F) {
               double _snowmanxxxxxxxxxxxxxxxxxxx = Math.min(1.0, _snowmanxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxxxxxxxxx * 0.5F;
               this.method_22978(
                  _snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 256, _snowmanx.getBoundNorth(), _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + 0.0F
               );
               this.method_22978(
                  _snowman,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                  256,
                  _snowmanx.getBoundNorth(),
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + 0.0F
               );
               this.method_22978(
                  _snowman,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                  0,
                  _snowmanx.getBoundNorth(),
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + 128.0F
               );
               this.method_22978(
                  _snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0, _snowmanx.getBoundNorth(), _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + 128.0F
               );
               _snowmanxxxxxxxxxxxxxxxxxx++;
            }
         }

         _snowman.end();
         BufferRenderer.draw(_snowman);
         RenderSystem.enableCull();
         RenderSystem.disableAlphaTest();
         RenderSystem.polygonOffset(0.0F, 0.0F);
         RenderSystem.disablePolygonOffset();
         RenderSystem.enableAlphaTest();
         RenderSystem.disableBlend();
         RenderSystem.popMatrix();
         RenderSystem.depthMask(true);
      }
   }

   private void method_22978(BufferBuilder _snowman, double _snowman, double _snowman, double _snowman, double _snowman, int _snowman, double _snowman, float _snowman, float _snowman) {
      _snowman.vertex(_snowman - _snowman, (double)_snowman - _snowman, _snowman - _snowman).texture(_snowman, _snowman).next();
   }

   private void drawBlockOutline(MatrixStack _snowman, VertexConsumer _snowman, Entity _snowman, double _snowman, double _snowman, double _snowman, BlockPos _snowman, BlockState _snowman) {
      drawShapeOutline(
         _snowman, _snowman, _snowman.getOutlineShape(this.world, _snowman, ShapeContext.of(_snowman)), (double)_snowman.getX() - _snowman, (double)_snowman.getY() - _snowman, (double)_snowman.getZ() - _snowman, 0.0F, 0.0F, 0.0F, 0.4F
      );
   }

   public static void method_22983(MatrixStack _snowman, VertexConsumer _snowman, VoxelShape _snowman, double _snowman, double _snowman, double _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      List<Box> _snowmanxxxxxxxxxx = _snowman.getBoundingBoxes();
      int _snowmanxxxxxxxxxxx = MathHelper.ceil((double)_snowmanxxxxxxxxxx.size() / 3.0);

      for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < _snowmanxxxxxxxxxx.size(); _snowmanxxxxxxxxxxxx++) {
         Box _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx.get(_snowmanxxxxxxxxxxxx);
         float _snowmanxxxxxxxxxxxxxx = ((float)_snowmanxxxxxxxxxxxx % (float)_snowmanxxxxxxxxxxx + 1.0F) / (float)_snowmanxxxxxxxxxxx;
         float _snowmanxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxx / _snowmanxxxxxxxxxxx);
         float _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx * (float)(_snowmanxxxxxxxxxxxxxxx == 0.0F ? 1 : 0);
         float _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx * (float)(_snowmanxxxxxxxxxxxxxxx == 1.0F ? 1 : 0);
         float _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx * (float)(_snowmanxxxxxxxxxxxxxxx == 2.0F ? 1 : 0);
         drawShapeOutline(
            _snowman, _snowman, VoxelShapes.cuboid(_snowmanxxxxxxxxxxxxx.offset(0.0, 0.0, 0.0)), _snowman, _snowman, _snowman, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 1.0F
         );
      }
   }

   private static void drawShapeOutline(MatrixStack _snowman, VertexConsumer _snowman, VoxelShape _snowman, double _snowman, double _snowman, double _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      Matrix4f _snowmanxxxxxxxxxx = _snowman.peek().getModel();
      _snowman.forEachEdge((_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxx) -> {
         _snowman.vertex(_snowman, (float)(_snowmanxxxxxxxxxxxxxxxx + _snowman), (float)(_snowmanxxxxxxxxxxxxxxx + _snowman), (float)(_snowmanxxxxxxxxxxxxxx + _snowman)).color(_snowman, _snowman, _snowman, _snowman).next();
         _snowman.vertex(_snowman, (float)(_snowmanxxxxxxxxxxxxx + _snowman), (float)(_snowmanxxxxxxxxxxxx + _snowman), (float)(_snowmanxxxxxxxxxxx + _snowman)).color(_snowman, _snowman, _snowman, _snowman).next();
      });
   }

   public static void drawBox(MatrixStack matrices, VertexConsumer vertexConsumer, Box box, float red, float green, float blue, float alpha) {
      drawBox(matrices, vertexConsumer, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha, red, green, blue);
   }

   public static void drawBox(
      MatrixStack matrices,
      VertexConsumer vertexConsumer,
      double x1,
      double y1,
      double z1,
      double x2,
      double y2,
      double z2,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      drawBox(matrices, vertexConsumer, x1, y1, z1, x2, y2, z2, red, green, blue, alpha, red, green, blue);
   }

   public static void drawBox(
      MatrixStack matrices,
      VertexConsumer vertexConsumer,
      double x1,
      double y1,
      double z1,
      double x2,
      double y2,
      double z2,
      float red,
      float green,
      float blue,
      float alpha,
      float xAxisRed,
      float yAxisGreen,
      float zAxisBlue
   ) {
      Matrix4f _snowman = matrices.peek().getModel();
      float _snowmanx = (float)x1;
      float _snowmanxx = (float)y1;
      float _snowmanxxx = (float)z1;
      float _snowmanxxxx = (float)x2;
      float _snowmanxxxxx = (float)y2;
      float _snowmanxxxxxx = (float)z2;
      vertexConsumer.vertex(_snowman, _snowmanx, _snowmanxx, _snowmanxxx).color(red, yAxisGreen, zAxisBlue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxx).color(red, yAxisGreen, zAxisBlue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanx, _snowmanxx, _snowmanxxx).color(xAxisRed, green, zAxisBlue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanx, _snowmanxxxxx, _snowmanxxx).color(xAxisRed, green, zAxisBlue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanx, _snowmanxx, _snowmanxxx).color(xAxisRed, yAxisGreen, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanx, _snowmanxx, _snowmanxxxxxx).color(xAxisRed, yAxisGreen, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanx, _snowmanxxxxx, _snowmanxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanx, _snowmanxxxxx, _snowmanxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanx, _snowmanxxxxx, _snowmanxxxxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanx, _snowmanxxxxx, _snowmanxxxxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanx, _snowmanxx, _snowmanxxxxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanx, _snowmanxx, _snowmanxxxxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxxxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxxxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanx, _snowmanxxxxx, _snowmanxxxxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxxxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxx).color(red, green, blue, alpha).next();
      vertexConsumer.vertex(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx).color(red, green, blue, alpha).next();
   }

   public static void drawBox(
      BufferBuilder buffer, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha
   ) {
      buffer.vertex(x1, y1, z1).color(red, green, blue, alpha).next();
      buffer.vertex(x1, y1, z1).color(red, green, blue, alpha).next();
      buffer.vertex(x1, y1, z1).color(red, green, blue, alpha).next();
      buffer.vertex(x1, y1, z2).color(red, green, blue, alpha).next();
      buffer.vertex(x1, y2, z1).color(red, green, blue, alpha).next();
      buffer.vertex(x1, y2, z2).color(red, green, blue, alpha).next();
      buffer.vertex(x1, y2, z2).color(red, green, blue, alpha).next();
      buffer.vertex(x1, y1, z2).color(red, green, blue, alpha).next();
      buffer.vertex(x2, y2, z2).color(red, green, blue, alpha).next();
      buffer.vertex(x2, y1, z2).color(red, green, blue, alpha).next();
      buffer.vertex(x2, y1, z2).color(red, green, blue, alpha).next();
      buffer.vertex(x2, y1, z1).color(red, green, blue, alpha).next();
      buffer.vertex(x2, y2, z2).color(red, green, blue, alpha).next();
      buffer.vertex(x2, y2, z1).color(red, green, blue, alpha).next();
      buffer.vertex(x2, y2, z1).color(red, green, blue, alpha).next();
      buffer.vertex(x2, y1, z1).color(red, green, blue, alpha).next();
      buffer.vertex(x1, y2, z1).color(red, green, blue, alpha).next();
      buffer.vertex(x1, y1, z1).color(red, green, blue, alpha).next();
      buffer.vertex(x1, y1, z1).color(red, green, blue, alpha).next();
      buffer.vertex(x2, y1, z1).color(red, green, blue, alpha).next();
      buffer.vertex(x1, y1, z2).color(red, green, blue, alpha).next();
      buffer.vertex(x2, y1, z2).color(red, green, blue, alpha).next();
      buffer.vertex(x2, y1, z2).color(red, green, blue, alpha).next();
      buffer.vertex(x1, y2, z1).color(red, green, blue, alpha).next();
      buffer.vertex(x1, y2, z1).color(red, green, blue, alpha).next();
      buffer.vertex(x1, y2, z2).color(red, green, blue, alpha).next();
      buffer.vertex(x2, y2, z1).color(red, green, blue, alpha).next();
      buffer.vertex(x2, y2, z2).color(red, green, blue, alpha).next();
      buffer.vertex(x2, y2, z2).color(red, green, blue, alpha).next();
      buffer.vertex(x2, y2, z2).color(red, green, blue, alpha).next();
   }

   public void updateBlock(BlockView world, BlockPos pos, BlockState oldState, BlockState newState, int flags) {
      this.scheduleSectionRender(pos, (flags & 8) != 0);
   }

   private void scheduleSectionRender(BlockPos pos, boolean important) {
      for (int _snowman = pos.getZ() - 1; _snowman <= pos.getZ() + 1; _snowman++) {
         for (int _snowmanx = pos.getX() - 1; _snowmanx <= pos.getX() + 1; _snowmanx++) {
            for (int _snowmanxx = pos.getY() - 1; _snowmanxx <= pos.getY() + 1; _snowmanxx++) {
               this.scheduleChunkRender(_snowmanx >> 4, _snowmanxx >> 4, _snowman >> 4, important);
            }
         }
      }
   }

   public void scheduleBlockRenders(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
      for (int _snowman = minZ - 1; _snowman <= maxZ + 1; _snowman++) {
         for (int _snowmanx = minX - 1; _snowmanx <= maxX + 1; _snowmanx++) {
            for (int _snowmanxx = minY - 1; _snowmanxx <= maxY + 1; _snowmanxx++) {
               this.scheduleBlockRender(_snowmanx >> 4, _snowmanxx >> 4, _snowman >> 4);
            }
         }
      }
   }

   public void scheduleBlockRerenderIfNeeded(BlockPos pos, BlockState old, BlockState updated) {
      if (this.client.getBakedModelManager().shouldRerender(old, updated)) {
         this.scheduleBlockRenders(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
      }
   }

   public void scheduleBlockRenders(int x, int y, int z) {
      for (int _snowman = z - 1; _snowman <= z + 1; _snowman++) {
         for (int _snowmanx = x - 1; _snowmanx <= x + 1; _snowmanx++) {
            for (int _snowmanxx = y - 1; _snowmanxx <= y + 1; _snowmanxx++) {
               this.scheduleBlockRender(_snowmanx, _snowmanxx, _snowman);
            }
         }
      }
   }

   public void scheduleBlockRender(int x, int y, int z) {
      this.scheduleChunkRender(x, y, z, false);
   }

   private void scheduleChunkRender(int x, int y, int z, boolean important) {
      this.chunks.scheduleRebuild(x, y, z, important);
   }

   public void playSong(@Nullable SoundEvent song, BlockPos songPosition) {
      SoundInstance _snowman = this.playingSongs.get(songPosition);
      if (_snowman != null) {
         this.client.getSoundManager().stop(_snowman);
         this.playingSongs.remove(songPosition);
      }

      if (song != null) {
         MusicDiscItem _snowmanx = MusicDiscItem.bySound(song);
         if (_snowmanx != null) {
            this.client.inGameHud.setRecordPlayingOverlay(_snowmanx.getDescription());
         }

         SoundInstance var5 = PositionedSoundInstance.record(song, (double)songPosition.getX(), (double)songPosition.getY(), (double)songPosition.getZ());
         this.playingSongs.put(songPosition, var5);
         this.client.getSoundManager().play(var5);
      }

      this.updateEntitiesForSong(this.world, songPosition, song != null);
   }

   private void updateEntitiesForSong(World world, BlockPos pos, boolean playing) {
      for (LivingEntity _snowman : world.getNonSpectatingEntities(LivingEntity.class, new Box(pos).expand(3.0))) {
         _snowman.setNearbySongPlaying(pos, playing);
      }
   }

   public void addParticle(
      ParticleEffect parameters, boolean shouldAlwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ
   ) {
      this.addParticle(parameters, shouldAlwaysSpawn, false, x, y, z, velocityX, velocityY, velocityZ);
   }

   public void addParticle(
      ParticleEffect parameters,
      boolean shouldAlwaysSpawn,
      boolean important,
      double x,
      double y,
      double z,
      double velocityX,
      double velocityY,
      double velocityZ
   ) {
      try {
         this.spawnParticle(parameters, shouldAlwaysSpawn, important, x, y, z, velocityX, velocityY, velocityZ);
      } catch (Throwable var19) {
         CrashReport _snowman = CrashReport.create(var19, "Exception while adding particle");
         CrashReportSection _snowmanx = _snowman.addElement("Particle being added");
         _snowmanx.add("ID", Registry.PARTICLE_TYPE.getId(parameters.getType()));
         _snowmanx.add("Parameters", parameters.asString());
         _snowmanx.add("Position", () -> CrashReportSection.createPositionString(x, y, z));
         throw new CrashException(_snowman);
      }
   }

   private <T extends ParticleEffect> void addParticle(T parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      this.addParticle(parameters, parameters.getType().shouldAlwaysSpawn(), x, y, z, velocityX, velocityY, velocityZ);
   }

   @Nullable
   private Particle spawnParticle(
      ParticleEffect parameters, boolean alwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ
   ) {
      return this.spawnParticle(parameters, alwaysSpawn, false, x, y, z, velocityX, velocityY, velocityZ);
   }

   @Nullable
   private Particle spawnParticle(
      ParticleEffect parameters,
      boolean alwaysSpawn,
      boolean canSpawnOnMinimal,
      double x,
      double y,
      double z,
      double velocityX,
      double velocityY,
      double velocityZ
   ) {
      Camera _snowman = this.client.gameRenderer.getCamera();
      if (this.client != null && _snowman.isReady() && this.client.particleManager != null) {
         ParticlesMode _snowmanx = this.getRandomParticleSpawnChance(canSpawnOnMinimal);
         if (alwaysSpawn) {
            return this.client.particleManager.addParticle(parameters, x, y, z, velocityX, velocityY, velocityZ);
         } else if (_snowman.getPos().squaredDistanceTo(x, y, z) > 1024.0) {
            return null;
         } else {
            return _snowmanx == ParticlesMode.MINIMAL ? null : this.client.particleManager.addParticle(parameters, x, y, z, velocityX, velocityY, velocityZ);
         }
      } else {
         return null;
      }
   }

   private ParticlesMode getRandomParticleSpawnChance(boolean canSpawnOnMinimal) {
      ParticlesMode _snowman = this.client.options.particles;
      if (canSpawnOnMinimal && _snowman == ParticlesMode.MINIMAL && this.world.random.nextInt(10) == 0) {
         _snowman = ParticlesMode.DECREASED;
      }

      if (_snowman == ParticlesMode.DECREASED && this.world.random.nextInt(3) == 0) {
         _snowman = ParticlesMode.MINIMAL;
      }

      return _snowman;
   }

   public void method_3267() {
   }

   public void processGlobalEvent(int eventId, BlockPos pos, int _snowman) {
      switch (eventId) {
         case 1023:
         case 1028:
         case 1038:
            Camera _snowmanx = this.client.gameRenderer.getCamera();
            if (_snowmanx.isReady()) {
               double _snowmanxx = (double)pos.getX() - _snowmanx.getPos().x;
               double _snowmanxxx = (double)pos.getY() - _snowmanx.getPos().y;
               double _snowmanxxxx = (double)pos.getZ() - _snowmanx.getPos().z;
               double _snowmanxxxxx = Math.sqrt(_snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx);
               double _snowmanxxxxxx = _snowmanx.getPos().x;
               double _snowmanxxxxxxx = _snowmanx.getPos().y;
               double _snowmanxxxxxxxx = _snowmanx.getPos().z;
               if (_snowmanxxxxx > 0.0) {
                  _snowmanxxxxxx += _snowmanxx / _snowmanxxxxx * 2.0;
                  _snowmanxxxxxxx += _snowmanxxx / _snowmanxxxxx * 2.0;
                  _snowmanxxxxxxxx += _snowmanxxxx / _snowmanxxxxx * 2.0;
               }

               if (eventId == 1023) {
                  this.world.playSound(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 1.0F, 1.0F, false);
               } else if (eventId == 1038) {
                  this.world.playSound(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.HOSTILE, 1.0F, 1.0F, false);
               } else {
                  this.world.playSound(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, SoundEvents.ENTITY_ENDER_DRAGON_DEATH, SoundCategory.HOSTILE, 5.0F, 1.0F, false);
               }
            }
      }
   }

   public void processWorldEvent(PlayerEntity source, int eventId, BlockPos pos, int data) {
      Random _snowman = this.world.random;
      switch (eventId) {
         case 1000:
            this.world.playSound(pos, SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1001:
            this.world.playSound(pos, SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0F, 1.2F, false);
            break;
         case 1002:
            this.world.playSound(pos, SoundEvents.BLOCK_DISPENSER_LAUNCH, SoundCategory.BLOCKS, 1.0F, 1.2F, false);
            break;
         case 1003:
            this.world.playSound(pos, SoundEvents.ENTITY_ENDER_EYE_LAUNCH, SoundCategory.NEUTRAL, 1.0F, 1.2F, false);
            break;
         case 1004:
            this.world.playSound(pos, SoundEvents.ENTITY_FIREWORK_ROCKET_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.2F, false);
            break;
         case 1005:
            this.world.playSound(pos, SoundEvents.BLOCK_IRON_DOOR_OPEN, SoundCategory.BLOCKS, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1006:
            this.world.playSound(pos, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.BLOCKS, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1007:
            this.world.playSound(pos, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1008:
            this.world.playSound(pos, SoundEvents.BLOCK_FENCE_GATE_OPEN, SoundCategory.BLOCKS, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1009:
            this.world.playSound(pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (_snowman.nextFloat() - _snowman.nextFloat()) * 0.8F, false);
            break;
         case 1010:
            if (Item.byRawId(data) instanceof MusicDiscItem) {
               this.playSong(((MusicDiscItem)Item.byRawId(data)).getSound(), pos);
            } else {
               this.playSong(null, pos);
            }
            break;
         case 1011:
            this.world.playSound(pos, SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1012:
            this.world.playSound(pos, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1013:
            this.world.playSound(pos, SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1014:
            this.world.playSound(pos, SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundCategory.BLOCKS, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1015:
            this.world.playSound(pos, SoundEvents.ENTITY_GHAST_WARN, SoundCategory.HOSTILE, 10.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1016:
            this.world.playSound(pos, SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.HOSTILE, 10.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1017:
            this.world
               .playSound(pos, SoundEvents.ENTITY_ENDER_DRAGON_SHOOT, SoundCategory.HOSTILE, 10.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1018:
            this.world.playSound(pos, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.HOSTILE, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1019:
            this.world
               .playSound(pos, SoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, SoundCategory.HOSTILE, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1020:
            this.world
               .playSound(pos, SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, SoundCategory.HOSTILE, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1021:
            this.world
               .playSound(pos, SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, SoundCategory.HOSTILE, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1022:
            this.world.playSound(pos, SoundEvents.ENTITY_WITHER_BREAK_BLOCK, SoundCategory.HOSTILE, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1024:
            this.world.playSound(pos, SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.HOSTILE, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1025:
            this.world.playSound(pos, SoundEvents.ENTITY_BAT_TAKEOFF, SoundCategory.NEUTRAL, 0.05F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1026:
            this.world.playSound(pos, SoundEvents.ENTITY_ZOMBIE_INFECT, SoundCategory.HOSTILE, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1027:
            this.world
               .playSound(pos, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.NEUTRAL, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1029:
            this.world.playSound(pos, SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.BLOCKS, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1030:
            this.world.playSound(pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1031:
            this.world.playSound(pos, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 0.3F, this.world.random.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1032:
            this.client.getSoundManager().play(PositionedSoundInstance.ambient(SoundEvents.BLOCK_PORTAL_TRAVEL, _snowman.nextFloat() * 0.4F + 0.8F, 0.25F));
            break;
         case 1033:
            this.world.playSound(pos, SoundEvents.BLOCK_CHORUS_FLOWER_GROW, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1034:
            this.world.playSound(pos, SoundEvents.BLOCK_CHORUS_FLOWER_DEATH, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1035:
            this.world.playSound(pos, SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1036:
            this.world.playSound(pos, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1037:
            this.world.playSound(pos, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1039:
            this.world.playSound(pos, SoundEvents.ENTITY_PHANTOM_BITE, SoundCategory.HOSTILE, 0.3F, this.world.random.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1040:
            this.world
               .playSound(
                  pos, SoundEvents.ENTITY_ZOMBIE_CONVERTED_TO_DROWNED, SoundCategory.NEUTRAL, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false
               );
            break;
         case 1041:
            this.world
               .playSound(pos, SoundEvents.ENTITY_HUSK_CONVERTED_TO_ZOMBIE, SoundCategory.NEUTRAL, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1042:
            this.world.playSound(pos, SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 1.0F, this.world.random.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1043:
            this.world.playSound(pos, SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.BLOCKS, 1.0F, this.world.random.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1044:
            this.world.playSound(pos, SoundEvents.BLOCK_SMITHING_TABLE_USE, SoundCategory.BLOCKS, 1.0F, this.world.random.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1500:
            ComposterBlock.playEffects(this.world, pos, data > 0);
            break;
         case 1501:
            this.world.playSound(pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (_snowman.nextFloat() - _snowman.nextFloat()) * 0.8F, false);

            for (int _snowmanxx = 0; _snowmanxx < 8; _snowmanxx++) {
               this.world
                  .addParticle(
                     ParticleTypes.LARGE_SMOKE,
                     (double)pos.getX() + _snowman.nextDouble(),
                     (double)pos.getY() + 1.2,
                     (double)pos.getZ() + _snowman.nextDouble(),
                     0.0,
                     0.0,
                     0.0
                  );
            }
            break;
         case 1502:
            this.world
               .playSound(pos, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 0.5F, 2.6F + (_snowman.nextFloat() - _snowman.nextFloat()) * 0.8F, false);

            for (int _snowmanxx = 0; _snowmanxx < 5; _snowmanxx++) {
               double _snowmanxxx = (double)pos.getX() + _snowman.nextDouble() * 0.6 + 0.2;
               double _snowmanxxxx = (double)pos.getY() + _snowman.nextDouble() * 0.6 + 0.2;
               double _snowmanxxxxx = (double)pos.getZ() + _snowman.nextDouble() * 0.6 + 0.2;
               this.world.addParticle(ParticleTypes.SMOKE, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
            break;
         case 1503:
            this.world.playSound(pos, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F, false);

            for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
               double _snowmanxxx = (double)pos.getX() + (5.0 + _snowman.nextDouble() * 6.0) / 16.0;
               double _snowmanxxxx = (double)pos.getY() + 0.8125;
               double _snowmanxxxxx = (double)pos.getZ() + (5.0 + _snowman.nextDouble() * 6.0) / 16.0;
               this.world.addParticle(ParticleTypes.SMOKE, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
            break;
         case 2000:
            Direction _snowmanxx = Direction.byId(data);
            int _snowmanxxx = _snowmanxx.getOffsetX();
            int _snowmanxxxx = _snowmanxx.getOffsetY();
            int _snowmanxxxxx = _snowmanxx.getOffsetZ();
            double _snowmanxxxxxx = (double)pos.getX() + (double)_snowmanxxx * 0.6 + 0.5;
            double _snowmanxxxxxxxx = (double)pos.getY() + (double)_snowmanxxxx * 0.6 + 0.5;
            double _snowmanxxxxxxxxx = (double)pos.getZ() + (double)_snowmanxxxxx * 0.6 + 0.5;

            for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 10; _snowmanxxxxxxxxxx++) {
               double _snowmanxxxxxxxxxxx = _snowman.nextDouble() * 0.2 + 0.01;
               double _snowmanxxxxxxxxxxxx = _snowmanxxxxxx + (double)_snowmanxxx * 0.01 + (_snowman.nextDouble() - 0.5) * (double)_snowmanxxxxx * 0.5;
               double _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxx + (double)_snowmanxxxx * 0.01 + (_snowman.nextDouble() - 0.5) * (double)_snowmanxxxx * 0.5;
               double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxx + (double)_snowmanxxxxx * 0.01 + (_snowman.nextDouble() - 0.5) * (double)_snowmanxxx * 0.5;
               double _snowmanxxxxxxxxxxxxxxx = (double)_snowmanxxx * _snowmanxxxxxxxxxxx + _snowman.nextGaussian() * 0.01;
               double _snowmanxxxxxxxxxxxxxxxx = (double)_snowmanxxxx * _snowmanxxxxxxxxxxx + _snowman.nextGaussian() * 0.01;
               double _snowmanxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxx * _snowmanxxxxxxxxxxx + _snowman.nextGaussian() * 0.01;
               this.addParticle(ParticleTypes.SMOKE, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx);
            }
            break;
         case 2001:
            BlockState _snowmanx = Block.getStateFromRawId(data);
            if (!_snowmanx.isAir()) {
               BlockSoundGroup _snowmanxx = _snowmanx.getSoundGroup();
               this.world.playSound(pos, _snowmanxx.getBreakSound(), SoundCategory.BLOCKS, (_snowmanxx.getVolume() + 1.0F) / 2.0F, _snowmanxx.getPitch() * 0.8F, false);
            }

            this.client.particleManager.addBlockBreakParticles(pos, _snowmanx);
            break;
         case 2002:
         case 2007:
            Vec3d _snowmanxx = Vec3d.ofBottomCenter(pos);

            for (int _snowmanxxx = 0; _snowmanxxx < 8; _snowmanxxx++) {
               this.addParticle(
                  new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Items.SPLASH_POTION)),
                  _snowmanxx.x,
                  _snowmanxx.y,
                  _snowmanxx.z,
                  _snowman.nextGaussian() * 0.15,
                  _snowman.nextDouble() * 0.2,
                  _snowman.nextGaussian() * 0.15
               );
            }

            float _snowmanxxx = (float)(data >> 16 & 0xFF) / 255.0F;
            float _snowmanxxxx = (float)(data >> 8 & 0xFF) / 255.0F;
            float _snowmanxxxxx = (float)(data >> 0 & 0xFF) / 255.0F;
            ParticleEffect _snowmanxxxxxx = eventId == 2007 ? ParticleTypes.INSTANT_EFFECT : ParticleTypes.EFFECT;

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 100; _snowmanxxxxxxx++) {
               double _snowmanxxxxxxxx = _snowman.nextDouble() * 4.0;
               double _snowmanxxxxxxxxx = _snowman.nextDouble() * Math.PI * 2.0;
               double _snowmanxxxxxxxxxx = Math.cos(_snowmanxxxxxxxxx) * _snowmanxxxxxxxx;
               double _snowmanxxxxxxxxxxx = 0.01 + _snowman.nextDouble() * 0.5;
               double _snowmanxxxxxxxxxxxx = Math.sin(_snowmanxxxxxxxxx) * _snowmanxxxxxxxx;
               Particle _snowmanxxxxxxxxxxxxx = this.spawnParticle(
                  _snowmanxxxxxx,
                  _snowmanxxxxxx.getType().shouldAlwaysSpawn(),
                  _snowmanxx.x + _snowmanxxxxxxxxxx * 0.1,
                  _snowmanxx.y + 0.3,
                  _snowmanxx.z + _snowmanxxxxxxxxxxxx * 0.1,
                  _snowmanxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxx
               );
               if (_snowmanxxxxxxxxxxxxx != null) {
                  float _snowmanxxxxxxxxxxxxxx = 0.75F + _snowman.nextFloat() * 0.25F;
                  _snowmanxxxxxxxxxxxxx.setColor(_snowmanxxx * _snowmanxxxxxxxxxxxxxx, _snowmanxxxx * _snowmanxxxxxxxxxxxxxx, _snowmanxxxxx * _snowmanxxxxxxxxxxxxxx);
                  _snowmanxxxxxxxxxxxxx.move((float)_snowmanxxxxxxxx);
               }
            }

            this.world.playSound(pos, SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.NEUTRAL, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 2003:
            double _snowmanxx = (double)pos.getX() + 0.5;
            double _snowmanxxx = (double)pos.getY();
            double _snowmanxxxx = (double)pos.getZ() + 0.5;

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < 8; _snowmanxxxxx++) {
               this.addParticle(
                  new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Items.ENDER_EYE)),
                  _snowmanxx,
                  _snowmanxxx,
                  _snowmanxxxx,
                  _snowman.nextGaussian() * 0.15,
                  _snowman.nextDouble() * 0.2,
                  _snowman.nextGaussian() * 0.15
               );
            }

            for (double _snowmanxxxxx = 0.0; _snowmanxxxxx < Math.PI * 2; _snowmanxxxxx += Math.PI / 20) {
               this.addParticle(
                  ParticleTypes.PORTAL,
                  _snowmanxx + Math.cos(_snowmanxxxxx) * 5.0,
                  _snowmanxxx - 0.4,
                  _snowmanxxxx + Math.sin(_snowmanxxxxx) * 5.0,
                  Math.cos(_snowmanxxxxx) * -5.0,
                  0.0,
                  Math.sin(_snowmanxxxxx) * -5.0
               );
               this.addParticle(
                  ParticleTypes.PORTAL,
                  _snowmanxx + Math.cos(_snowmanxxxxx) * 5.0,
                  _snowmanxxx - 0.4,
                  _snowmanxxxx + Math.sin(_snowmanxxxxx) * 5.0,
                  Math.cos(_snowmanxxxxx) * -7.0,
                  0.0,
                  Math.sin(_snowmanxxxxx) * -7.0
               );
            }
            break;
         case 2004:
            for (int _snowmanxx = 0; _snowmanxx < 20; _snowmanxx++) {
               double _snowmanxxx = (double)pos.getX() + 0.5 + (_snowman.nextDouble() - 0.5) * 2.0;
               double _snowmanxxxx = (double)pos.getY() + 0.5 + (_snowman.nextDouble() - 0.5) * 2.0;
               double _snowmanxxxxx = (double)pos.getZ() + 0.5 + (_snowman.nextDouble() - 0.5) * 2.0;
               this.world.addParticle(ParticleTypes.SMOKE, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
               this.world.addParticle(ParticleTypes.FLAME, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
            break;
         case 2005:
            BoneMealItem.createParticles(this.world, pos, data);
            break;
         case 2006:
            for (int _snowmanx = 0; _snowmanx < 200; _snowmanx++) {
               float _snowmanxx = _snowman.nextFloat() * 4.0F;
               float _snowmanxxx = _snowman.nextFloat() * (float) (Math.PI * 2);
               double _snowmanxxxx = (double)(MathHelper.cos(_snowmanxxx) * _snowmanxx);
               double _snowmanxxxxx = 0.01 + _snowman.nextDouble() * 0.5;
               double _snowmanxxxxxx = (double)(MathHelper.sin(_snowmanxxx) * _snowmanxx);
               Particle _snowmanxxxxxxx = this.spawnParticle(
                  ParticleTypes.DRAGON_BREATH,
                  false,
                  (double)pos.getX() + _snowmanxxxx * 0.1,
                  (double)pos.getY() + 0.3,
                  (double)pos.getZ() + _snowmanxxxxxx * 0.1,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx
               );
               if (_snowmanxxxxxxx != null) {
                  _snowmanxxxxxxx.move(_snowmanxx);
               }
            }

            if (data == 1) {
               this.world.playSound(pos, SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.HOSTILE, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            }
            break;
         case 2008:
            this.world.addParticle(ParticleTypes.EXPLOSION, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);
            break;
         case 2009:
            for (int _snowmanxx = 0; _snowmanxx < 8; _snowmanxx++) {
               this.world
                  .addParticle(
                     ParticleTypes.CLOUD, (double)pos.getX() + _snowman.nextDouble(), (double)pos.getY() + 1.2, (double)pos.getZ() + _snowman.nextDouble(), 0.0, 0.0, 0.0
                  );
            }
            break;
         case 3000:
            this.world
               .addParticle(ParticleTypes.EXPLOSION_EMITTER, true, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);
            this.world
               .playSound(
                  pos,
                  SoundEvents.BLOCK_END_GATEWAY_SPAWN,
                  SoundCategory.BLOCKS,
                  10.0F,
                  (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F,
                  false
               );
            break;
         case 3001:
            this.world.playSound(pos, SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.HOSTILE, 64.0F, 0.8F + this.world.random.nextFloat() * 0.3F, false);
      }
   }

   public void setBlockBreakingInfo(int entityId, BlockPos pos, int stage) {
      if (stage >= 0 && stage < 10) {
         BlockBreakingInfo _snowman = (BlockBreakingInfo)this.blockBreakingInfos.get(entityId);
         if (_snowman != null) {
            this.removeBlockBreakingInfo(_snowman);
         }

         if (_snowman == null || _snowman.getPos().getX() != pos.getX() || _snowman.getPos().getY() != pos.getY() || _snowman.getPos().getZ() != pos.getZ()) {
            _snowman = new BlockBreakingInfo(entityId, pos);
            this.blockBreakingInfos.put(entityId, _snowman);
         }

         _snowman.setStage(stage);
         _snowman.setLastUpdateTick(this.ticks);
         ((SortedSet)this.blockBreakingProgressions.computeIfAbsent(_snowman.getPos().asLong(), _snowmanx -> Sets.newTreeSet())).add(_snowman);
      } else {
         BlockBreakingInfo _snowmanx = (BlockBreakingInfo)this.blockBreakingInfos.remove(entityId);
         if (_snowmanx != null) {
            this.removeBlockBreakingInfo(_snowmanx);
         }
      }
   }

   public boolean isTerrainRenderComplete() {
      return this.chunksToRebuild.isEmpty() && this.chunkBuilder.isEmpty();
   }

   public void scheduleTerrainUpdate() {
      this.needsTerrainUpdate = true;
      this.cloudsDirty = true;
   }

   public void updateNoCullingBlockEntities(Collection<BlockEntity> removed, Collection<BlockEntity> added) {
      synchronized (this.noCullingBlockEntities) {
         this.noCullingBlockEntities.removeAll(removed);
         this.noCullingBlockEntities.addAll(added);
      }
   }

   public static int getLightmapCoordinates(BlockRenderView world, BlockPos pos) {
      return getLightmapCoordinates(world, world.getBlockState(pos), pos);
   }

   public static int getLightmapCoordinates(BlockRenderView world, BlockState state, BlockPos pos) {
      if (state.hasEmissiveLighting(world, pos)) {
         return 15728880;
      } else {
         int _snowman = world.getLightLevel(LightType.SKY, pos);
         int _snowmanx = world.getLightLevel(LightType.BLOCK, pos);
         int _snowmanxx = state.getLuminance();
         if (_snowmanx < _snowmanxx) {
            _snowmanx = _snowmanxx;
         }

         return _snowman << 20 | _snowmanx << 4;
      }
   }

   @Nullable
   public Framebuffer getEntityOutlinesFramebuffer() {
      return this.entityOutlinesFramebuffer;
   }

   @Nullable
   public Framebuffer getTranslucentFramebuffer() {
      return this.translucentFramebuffer;
   }

   @Nullable
   public Framebuffer getEntityFramebuffer() {
      return this.entityFramebuffer;
   }

   @Nullable
   public Framebuffer getParticlesFramebuffer() {
      return this.particlesFramebuffer;
   }

   @Nullable
   public Framebuffer getWeatherFramebuffer() {
      return this.weatherFramebuffer;
   }

   @Nullable
   public Framebuffer getCloudsFramebuffer() {
      return this.cloudsFramebuffer;
   }

   class ChunkInfo {
      private final ChunkBuilder.BuiltChunk chunk;
      private final Direction direction;
      private byte cullingState;
      private final int propagationLevel;

      private ChunkInfo(ChunkBuilder.BuiltChunk chunk, Direction direction, @Nullable int propagationLevel) {
         this.chunk = chunk;
         this.direction = direction;
         this.propagationLevel = propagationLevel;
      }

      public void updateCullingState(byte parentCullingState, Direction from) {
         this.cullingState = (byte)(this.cullingState | parentCullingState | 1 << from.ordinal());
      }

      public boolean canCull(Direction from) {
         return (this.cullingState & 1 << from.ordinal()) > 0;
      }
   }

   public static class ShaderException extends RuntimeException {
      public ShaderException(String _snowman, Throwable _snowman) {
         super(_snowman, _snowman);
      }
   }
}
