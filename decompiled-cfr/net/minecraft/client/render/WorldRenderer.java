/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Queues
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonSyntaxException
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectListIterator
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
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
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
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
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.BlockBreakingInfo;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltChunkStorage;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.FpsSmoother;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.OverlayVertexConsumer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
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
import net.minecraft.particle.DefaultParticleType;
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
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.border.WorldBorder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldRenderer
implements SynchronousResourceReloadListener,
AutoCloseable {
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
    private final ObjectList<ChunkInfo> visibleChunks = new ObjectArrayList(69696);
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
        for (int i = 0; i < 32; ++i) {
            for (_snowman = 0; _snowman < 32; ++_snowman) {
                float f = _snowman - 16;
                _snowman = i - 16;
                _snowman = MathHelper.sqrt(f * f + _snowman * _snowman);
                this.field_20794[i << 5 | _snowman] = -_snowman / _snowman;
                this.field_20795[i << 5 | _snowman] = f / _snowman;
            }
        }
        this.renderStars();
        this.renderLightSky();
        this.renderDarkSky();
    }

    private void renderWeather(LightmapTextureManager manager, float f, double d, double d2, double d3) {
        float f2 = this.client.world.getRainGradient(f);
        if (f2 <= 0.0f) {
            return;
        }
        manager.enable();
        ClientWorld _snowman2 = this.client.world;
        int _snowman3 = MathHelper.floor(d);
        int _snowman4 = MathHelper.floor(d2);
        int _snowman5 = MathHelper.floor(d3);
        Tessellator _snowman6 = Tessellator.getInstance();
        BufferBuilder _snowman7 = _snowman6.getBuffer();
        RenderSystem.enableAlphaTest();
        RenderSystem.disableCull();
        RenderSystem.normal3f(0.0f, 1.0f, 0.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableDepthTest();
        int _snowman8 = 5;
        if (MinecraftClient.isFancyGraphicsOrBetter()) {
            _snowman8 = 10;
        }
        RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
        int _snowman9 = -1;
        _snowman = (float)this.ticks + f;
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        BlockPos.Mutable _snowman10 = new BlockPos.Mutable();
        for (int i = _snowman5 - _snowman8; i <= _snowman5 + _snowman8; ++i) {
            for (n = _snowman3 - _snowman8; n <= _snowman3 + _snowman8; ++n) {
                int n;
                _snowman = (i - _snowman5 + 16) * 32 + n - _snowman3 + 16;
                double d4 = (double)this.field_20794[_snowman] * 0.5;
                _snowman = (double)this.field_20795[_snowman] * 0.5;
                _snowman10.set(n, 0, i);
                Biome _snowman11 = _snowman2.getBiome(_snowman10);
                if (_snowman11.getPrecipitation() == Biome.Precipitation.NONE) continue;
                int _snowman12 = _snowman2.getTopPosition(Heightmap.Type.MOTION_BLOCKING, _snowman10).getY();
                int _snowman13 = _snowman4 - _snowman8;
                int _snowman14 = _snowman4 + _snowman8;
                if (_snowman13 < _snowman12) {
                    _snowman13 = _snowman12;
                }
                if (_snowman14 < _snowman12) {
                    _snowman14 = _snowman12;
                }
                if ((n2 = _snowman12) < _snowman4) {
                    int n2 = _snowman4;
                }
                if (_snowman13 == _snowman14) continue;
                Random _snowman15 = new Random(n * n * 3121 + n * 45238971 ^ i * i * 418711 + i * 13761);
                _snowman10.set(n, _snowman13, i);
                float _snowman16 = _snowman11.getTemperature(_snowman10);
                if (_snowman16 >= 0.15f) {
                    if (_snowman9 != 0) {
                        if (_snowman9 >= 0) {
                            _snowman6.draw();
                        }
                        _snowman9 = 0;
                        this.client.getTextureManager().bindTexture(RAIN);
                        _snowman7.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
                    }
                    _snowman = this.ticks + n * n * 3121 + n * 45238971 + i * i * 418711 + i * 13761 & 0x1F;
                    float f3 = -((float)_snowman + f) / 32.0f * (3.0f + _snowman15.nextFloat());
                    double _snowman17 = (double)((float)n + 0.5f) - d;
                    double _snowman18 = (double)((float)i + 0.5f) - d3;
                    _snowman = MathHelper.sqrt(_snowman17 * _snowman17 + _snowman18 * _snowman18) / (float)_snowman8;
                    _snowman20 = ((1.0f - _snowman * _snowman) * 0.5f + 0.5f) * f2;
                    _snowman10.set(n, n2, i);
                    int _snowman19 = WorldRenderer.getLightmapCoordinates(_snowman2, _snowman10);
                    _snowman7.vertex((double)n - d - d4 + 0.5, (double)_snowman14 - d2, (double)i - d3 - _snowman + 0.5).texture(0.0f, (float)_snowman13 * 0.25f + f3).color(1.0f, 1.0f, 1.0f, _snowman20).light(_snowman19).next();
                    _snowman7.vertex((double)n - d + d4 + 0.5, (double)_snowman14 - d2, (double)i - d3 + _snowman + 0.5).texture(1.0f, (float)_snowman13 * 0.25f + f3).color(1.0f, 1.0f, 1.0f, _snowman20).light(_snowman19).next();
                    _snowman7.vertex((double)n - d + d4 + 0.5, (double)_snowman13 - d2, (double)i - d3 + _snowman + 0.5).texture(1.0f, (float)_snowman14 * 0.25f + f3).color(1.0f, 1.0f, 1.0f, _snowman20).light(_snowman19).next();
                    _snowman7.vertex((double)n - d - d4 + 0.5, (double)_snowman13 - d2, (double)i - d3 - _snowman + 0.5).texture(0.0f, (float)_snowman14 * 0.25f + f3).color(1.0f, 1.0f, 1.0f, _snowman20).light(_snowman19).next();
                    continue;
                }
                if (_snowman9 != 1) {
                    if (_snowman9 >= 0) {
                        _snowman6.draw();
                    }
                    _snowman9 = 1;
                    this.client.getTextureManager().bindTexture(SNOW);
                    _snowman7.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
                }
                _snowman = -((float)(this.ticks & 0x1FF) + f) / 512.0f;
                f3 = (float)(_snowman15.nextDouble() + (double)_snowman * 0.01 * (double)((float)_snowman15.nextGaussian()));
                _snowman = (float)(_snowman15.nextDouble() + (double)(_snowman * (float)_snowman15.nextGaussian()) * 0.001);
                double d5 = (double)((float)n + 0.5f) - d;
                _snowman = (double)((float)i + 0.5f) - d3;
                float _snowman20 = MathHelper.sqrt(d5 * d5 + _snowman * _snowman) / (float)_snowman8;
                float _snowman21 = ((1.0f - _snowman20 * _snowman20) * 0.3f + 0.5f) * f2;
                _snowman10.set(n, n2, i);
                int _snowman22 = WorldRenderer.getLightmapCoordinates(_snowman2, _snowman10);
                int _snowman23 = _snowman22 >> 16 & 0xFFFF;
                int _snowman24 = (_snowman22 & 0xFFFF) * 3;
                int _snowman25 = (_snowman23 * 3 + 240) / 4;
                int _snowman26 = (_snowman24 * 3 + 240) / 4;
                _snowman7.vertex((double)n - d - d4 + 0.5, (double)_snowman14 - d2, (double)i - d3 - _snowman + 0.5).texture(0.0f + f3, (float)_snowman13 * 0.25f + _snowman + _snowman).color(1.0f, 1.0f, 1.0f, _snowman21).light(_snowman26, _snowman25).next();
                _snowman7.vertex((double)n - d + d4 + 0.5, (double)_snowman14 - d2, (double)i - d3 + _snowman + 0.5).texture(1.0f + f3, (float)_snowman13 * 0.25f + _snowman + _snowman).color(1.0f, 1.0f, 1.0f, _snowman21).light(_snowman26, _snowman25).next();
                _snowman7.vertex((double)n - d + d4 + 0.5, (double)_snowman13 - d2, (double)i - d3 + _snowman + 0.5).texture(1.0f + f3, (float)_snowman14 * 0.25f + _snowman + _snowman).color(1.0f, 1.0f, 1.0f, _snowman21).light(_snowman26, _snowman25).next();
                _snowman7.vertex((double)n - d - d4 + 0.5, (double)_snowman13 - d2, (double)i - d3 - _snowman + 0.5).texture(0.0f + f3, (float)_snowman14 * 0.25f + _snowman + _snowman).color(1.0f, 1.0f, 1.0f, _snowman21).light(_snowman26, _snowman25).next();
            }
        }
        if (_snowman9 >= 0) {
            _snowman6.draw();
        }
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.disableAlphaTest();
        manager.disable();
    }

    public void tickRainSplashing(Camera camera) {
        float f = this.client.world.getRainGradient(1.0f) / (MinecraftClient.isFancyGraphicsOrBetter() ? 1.0f : 2.0f);
        if (f <= 0.0f) {
            return;
        }
        Random _snowman2 = new Random((long)this.ticks * 312987231L);
        ClientWorld _snowman3 = this.client.world;
        BlockPos _snowman4 = new BlockPos(camera.getPos());
        Vec3i _snowman5 = null;
        int _snowman6 = (int)(100.0f * f * f) / (this.client.options.particles == ParticlesMode.DECREASED ? 2 : 1);
        for (int i = 0; i < _snowman6; ++i) {
            _snowman = _snowman2.nextInt(21) - 10;
            _snowman = _snowman2.nextInt(21) - 10;
            BlockPos blockPos = _snowman3.getTopPosition(Heightmap.Type.MOTION_BLOCKING, _snowman4.add(_snowman, 0, _snowman)).down();
            Biome _snowman7 = _snowman3.getBiome(blockPos);
            if (blockPos.getY() <= 0 || blockPos.getY() > _snowman4.getY() + 10 || blockPos.getY() < _snowman4.getY() - 10 || _snowman7.getPrecipitation() != Biome.Precipitation.RAIN || !(_snowman7.getTemperature(blockPos) >= 0.15f)) continue;
            _snowman5 = blockPos;
            if (this.client.options.particles == ParticlesMode.MINIMAL) break;
            double _snowman8 = _snowman2.nextDouble();
            double _snowman9 = _snowman2.nextDouble();
            BlockState _snowman10 = _snowman3.getBlockState((BlockPos)_snowman5);
            FluidState _snowman11 = _snowman3.getFluidState((BlockPos)_snowman5);
            VoxelShape _snowman12 = _snowman10.getCollisionShape(_snowman3, (BlockPos)_snowman5);
            double _snowman13 = _snowman12.getEndingCoord(Direction.Axis.Y, _snowman8, _snowman9);
            double _snowman14 = _snowman11.getHeight(_snowman3, (BlockPos)_snowman5);
            double _snowman15 = Math.max(_snowman13, _snowman14);
            DefaultParticleType _snowman16 = _snowman11.isIn(FluidTags.LAVA) || _snowman10.isOf(Blocks.MAGMA_BLOCK) || CampfireBlock.isLitCampfire(_snowman10) ? ParticleTypes.SMOKE : ParticleTypes.RAIN;
            this.client.world.addParticle(_snowman16, (double)_snowman5.getX() + _snowman8, (double)_snowman5.getY() + _snowman15, (double)_snowman5.getZ() + _snowman9, 0.0, 0.0, 0.0);
        }
        if (_snowman5 != null && _snowman2.nextInt(3) < this.field_20793++) {
            this.field_20793 = 0;
            if (_snowman5.getY() > _snowman4.getY() + 1 && _snowman3.getTopPosition(Heightmap.Type.MOTION_BLOCKING, _snowman4).getY() > MathHelper.floor(_snowman4.getY())) {
                this.client.world.playSound((BlockPos)_snowman5, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 0.1f, 0.5f, false);
            } else {
                this.client.world.playSound((BlockPos)_snowman5, SoundEvents.WEATHER_RAIN, SoundCategory.WEATHER, 0.2f, 1.0f, false);
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
        Identifier identifier = new Identifier("shaders/post/entity_outline.json");
        try {
            this.entityOutlineShader = new ShaderEffect(this.client.getTextureManager(), this.client.getResourceManager(), this.client.getFramebuffer(), identifier);
            this.entityOutlineShader.setupDimensions(this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight());
            this.entityOutlinesFramebuffer = this.entityOutlineShader.getSecondaryTarget("final");
        }
        catch (IOException _snowman2) {
            LOGGER.warn("Failed to load shader: {}", (Object)identifier, (Object)_snowman2);
            this.entityOutlineShader = null;
            this.entityOutlinesFramebuffer = null;
        }
        catch (JsonSyntaxException _snowman3) {
            LOGGER.warn("Failed to parse shader: {}", (Object)identifier, (Object)_snowman3);
            this.entityOutlineShader = null;
            this.entityOutlinesFramebuffer = null;
        }
    }

    private void loadTransparencyShader() {
        this.resetTransparencyShader();
        Identifier identifier = new Identifier("shaders/post/transparency.json");
        try {
            ShaderEffect shaderEffect = new ShaderEffect(this.client.getTextureManager(), this.client.getResourceManager(), this.client.getFramebuffer(), identifier);
            shaderEffect.setupDimensions(this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight());
            Framebuffer _snowman2 = shaderEffect.getSecondaryTarget("translucent");
            Framebuffer _snowman3 = shaderEffect.getSecondaryTarget("itemEntity");
            Framebuffer _snowman4 = shaderEffect.getSecondaryTarget("particles");
            Framebuffer _snowman5 = shaderEffect.getSecondaryTarget("weather");
            Framebuffer _snowman6 = shaderEffect.getSecondaryTarget("clouds");
            this.transparencyShader = shaderEffect;
            this.translucentFramebuffer = _snowman2;
            this.entityFramebuffer = _snowman3;
            this.particlesFramebuffer = _snowman4;
            this.weatherFramebuffer = _snowman5;
            this.cloudsFramebuffer = _snowman6;
        }
        catch (Exception exception) {
            String string;
            String string2 = exception instanceof JsonSyntaxException ? "parse" : "load";
            string = "Failed to " + string2 + " shader: " + identifier;
            ShaderException _snowman7 = new ShaderException(string, exception);
            if (this.client.getResourcePackManager().getEnabledNames().size() > 1) {
                LiteralText literalText;
                try {
                    literalText = new LiteralText(this.client.getResourceManager().getResource(identifier).getResourcePackName());
                }
                catch (IOException iOException) {
                    literalText = null;
                }
                this.client.options.graphicsMode = GraphicsMode.FANCY;
                this.client.method_31186(_snowman7, literalText);
            }
            CrashReport _snowman8 = this.client.addDetailsToCrashReport(new CrashReport(string, _snowman7));
            this.client.options.graphicsMode = GraphicsMode.FANCY;
            this.client.options.write();
            LOGGER.fatal(string, (Throwable)_snowman7);
            this.client.cleanUpAfterCrash();
            MinecraftClient.printCrashReport(_snowman8);
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
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE);
            this.entityOutlinesFramebuffer.draw(this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight(), false);
            RenderSystem.disableBlend();
        }
    }

    protected boolean canDrawEntityOutlines() {
        return this.entityOutlinesFramebuffer != null && this.entityOutlineShader != null && this.client.player != null;
    }

    private void renderDarkSky() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder _snowman2 = tessellator.getBuffer();
        if (this.darkSkyBuffer != null) {
            this.darkSkyBuffer.close();
        }
        this.darkSkyBuffer = new VertexBuffer(this.skyVertexFormat);
        this.renderSkyHalf(_snowman2, -16.0f, true);
        _snowman2.end();
        this.darkSkyBuffer.upload(_snowman2);
    }

    private void renderLightSky() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder _snowman2 = tessellator.getBuffer();
        if (this.lightSkyBuffer != null) {
            this.lightSkyBuffer.close();
        }
        this.lightSkyBuffer = new VertexBuffer(this.skyVertexFormat);
        this.renderSkyHalf(_snowman2, 16.0f, false);
        _snowman2.end();
        this.lightSkyBuffer.upload(_snowman2);
    }

    private void renderSkyHalf(BufferBuilder buffer, float y, boolean bottom) {
        int n = 64;
        _snowman = 6;
        buffer.begin(7, VertexFormats.POSITION);
        for (_snowman = -384; _snowman <= 384; _snowman += 64) {
            for (_snowman = -384; _snowman <= 384; _snowman += 64) {
                float f = _snowman;
                _snowman = _snowman + 64;
                if (bottom) {
                    _snowman = _snowman;
                    f = _snowman + 64;
                }
                buffer.vertex(f, y, _snowman).next();
                buffer.vertex(_snowman, y, _snowman).next();
                buffer.vertex(_snowman, y, _snowman + 64).next();
                buffer.vertex(f, y, _snowman + 64).next();
            }
        }
    }

    private void renderStars() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder _snowman2 = tessellator.getBuffer();
        if (this.starsBuffer != null) {
            this.starsBuffer.close();
        }
        this.starsBuffer = new VertexBuffer(this.skyVertexFormat);
        this.renderStars(_snowman2);
        _snowman2.end();
        this.starsBuffer.upload(_snowman2);
    }

    private void renderStars(BufferBuilder buffer) {
        Random random = new Random(10842L);
        buffer.begin(7, VertexFormats.POSITION);
        for (int i = 0; i < 1500; ++i) {
            double d = random.nextFloat() * 2.0f - 1.0f;
            _snowman = random.nextFloat() * 2.0f - 1.0f;
            _snowman = random.nextFloat() * 2.0f - 1.0f;
            _snowman = 0.15f + random.nextFloat() * 0.1f;
            _snowman = d * d + _snowman * _snowman + _snowman * _snowman;
            if (!(_snowman < 1.0) || !(_snowman > 0.01)) continue;
            _snowman = 1.0 / Math.sqrt(_snowman);
            _snowman = (d *= _snowman) * 100.0;
            _snowman = (_snowman *= _snowman) * 100.0;
            _snowman = (_snowman *= _snowman) * 100.0;
            _snowman = Math.atan2(d, _snowman);
            _snowman = Math.sin(_snowman);
            _snowman = Math.cos(_snowman);
            _snowman = Math.atan2(Math.sqrt(d * d + _snowman * _snowman), _snowman);
            _snowman = Math.sin(_snowman);
            _snowman = Math.cos(_snowman);
            _snowman = random.nextDouble() * Math.PI * 2.0;
            _snowman = Math.sin(_snowman);
            _snowman = Math.cos(_snowman);
            for (int j = 0; j < 4; ++j) {
                double d2 = 0.0;
                _snowman = (double)((j & 2) - 1) * _snowman;
                _snowman = (double)((j + 1 & 2) - 1) * _snowman;
                _snowman = 0.0;
                _snowman = _snowman * _snowman - _snowman * _snowman;
                _snowman = _snowman = _snowman * _snowman + _snowman * _snowman;
                _snowman = _snowman * _snowman + 0.0 * _snowman;
                _snowman = 0.0 * _snowman - _snowman * _snowman;
                _snowman = _snowman * _snowman - _snowman * _snowman;
                _snowman = _snowman;
                _snowman = _snowman * _snowman + _snowman * _snowman;
                buffer.vertex(_snowman + _snowman, _snowman + _snowman, _snowman + _snowman).next();
            }
        }
    }

    public void setWorld(@Nullable ClientWorld clientWorld) {
        this.lastCameraChunkUpdateX = Double.MIN_VALUE;
        this.lastCameraChunkUpdateY = Double.MIN_VALUE;
        this.lastCameraChunkUpdateZ = Double.MIN_VALUE;
        this.cameraChunkX = Integer.MIN_VALUE;
        this.cameraChunkY = Integer.MIN_VALUE;
        this.cameraChunkZ = Integer.MIN_VALUE;
        this.entityRenderDispatcher.setWorld(clientWorld);
        this.world = clientWorld;
        if (clientWorld != null) {
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void reload() {
        Entity entity;
        if (this.world == null) {
            return;
        }
        if (MinecraftClient.isFabulousGraphicsOrBetter()) {
            this.loadTransparencyShader();
        } else {
            this.resetTransparencyShader();
        }
        this.world.reloadColor();
        if (this.chunkBuilder == null) {
            this.chunkBuilder = new ChunkBuilder(this.world, this, Util.getMainWorkerExecutor(), this.client.is64Bit(), this.bufferBuilders.getBlockBufferBuilders());
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
        Set<BlockEntity> set = this.noCullingBlockEntities;
        synchronized (set) {
            this.noCullingBlockEntities.clear();
        }
        this.chunks = new BuiltChunkStorage(this.chunkBuilder, this.world, this.client.options.viewDistance, this);
        if (this.world != null && (entity = this.client.getCameraEntity()) != null) {
            this.chunks.updateCameraPosition(entity.getX(), entity.getZ());
        }
    }

    protected void clearChunkRenderers() {
        this.chunksToRebuild.clear();
        this.chunkBuilder.reset();
    }

    public void onResized(int n, int n2) {
        this.scheduleTerrainUpdate();
        if (this.entityOutlineShader != null) {
            this.entityOutlineShader.setupDimensions(n, n2);
        }
        if (this.transparencyShader != null) {
            this.transparencyShader.setupDimensions(n, n2);
        }
    }

    public String getChunksDebugString() {
        int n = this.chunks.chunks.length;
        _snowman = this.getCompletedChunkCount();
        return String.format("C: %d/%d %sD: %d, %s", _snowman, n, this.client.chunkCullingEnabled ? "(s) " : "", this.renderDistance, this.chunkBuilder == null ? "null" : this.chunkBuilder.getDebugString());
    }

    protected int getCompletedChunkCount() {
        int n = 0;
        for (ChunkInfo chunkInfo : this.visibleChunks) {
            if (chunkInfo.chunk.getData().isEmpty()) continue;
            ++n;
        }
        return n;
    }

    public String getEntitiesDebugString() {
        return "E: " + this.regularEntityCount + "/" + this.world.getRegularEntityCount() + ", B: " + this.blockEntityCount;
    }

    private void setupTerrain(Camera camera, Frustum frustum, boolean hasForcedFrustum, int frame, boolean spectator) {
        Collection<ChunkBuilder.BuiltChunk> collection;
        Vec3d vec3d = camera.getPos();
        if (this.client.options.viewDistance != this.renderDistance) {
            this.reload();
        }
        this.world.getProfiler().push("camera");
        double _snowman2 = this.client.player.getX() - this.lastCameraChunkUpdateX;
        double _snowman3 = this.client.player.getY() - this.lastCameraChunkUpdateY;
        double _snowman4 = this.client.player.getZ() - this.lastCameraChunkUpdateZ;
        if (this.cameraChunkX != this.client.player.chunkX || this.cameraChunkY != this.client.player.chunkY || this.cameraChunkZ != this.client.player.chunkZ || _snowman2 * _snowman2 + _snowman3 * _snowman3 + _snowman4 * _snowman4 > 16.0) {
            this.lastCameraChunkUpdateX = this.client.player.getX();
            this.lastCameraChunkUpdateY = this.client.player.getY();
            this.lastCameraChunkUpdateZ = this.client.player.getZ();
            this.cameraChunkX = this.client.player.chunkX;
            this.cameraChunkY = this.client.player.chunkY;
            this.cameraChunkZ = this.client.player.chunkZ;
            this.chunks.updateCameraPosition(this.client.player.getX(), this.client.player.getZ());
        }
        this.chunkBuilder.setCameraPosition(vec3d);
        this.world.getProfiler().swap("cull");
        this.client.getProfiler().swap("culling");
        BlockPos _snowman5 = camera.getBlockPos();
        ChunkBuilder.BuiltChunk _snowman6 = this.chunks.getRenderedChunk(_snowman5);
        int _snowman7 = 16;
        BlockPos _snowman8 = new BlockPos(MathHelper.floor(vec3d.x / 16.0) * 16, MathHelper.floor(vec3d.y / 16.0) * 16, MathHelper.floor(vec3d.z / 16.0) * 16);
        float _snowman9 = camera.getPitch();
        float _snowman10 = camera.getYaw();
        this.needsTerrainUpdate = this.needsTerrainUpdate || !this.chunksToRebuild.isEmpty() || vec3d.x != this.lastCameraX || vec3d.y != this.lastCameraY || vec3d.z != this.lastCameraZ || (double)_snowman9 != this.lastCameraPitch || (double)_snowman10 != this.lastCameraYaw;
        this.lastCameraX = vec3d.x;
        this.lastCameraY = vec3d.y;
        this.lastCameraZ = vec3d.z;
        this.lastCameraPitch = _snowman9;
        this.lastCameraYaw = _snowman10;
        this.client.getProfiler().swap("update");
        if (!hasForcedFrustum && this.needsTerrainUpdate) {
            this.needsTerrainUpdate = false;
            this.visibleChunks.clear();
            collection = Queues.newArrayDeque();
            Entity.setRenderDistanceMultiplier(MathHelper.clamp((double)this.client.options.viewDistance / 8.0, 1.0, 2.5) * (double)this.client.options.entityDistanceScaling);
            boolean _snowman11 = this.client.chunkCullingEnabled;
            if (_snowman6 == null) {
                int n = _snowman5.getY() > 0 ? 248 : 8;
                _snowman = MathHelper.floor(vec3d.x / 16.0) * 16;
                _snowman = MathHelper.floor(vec3d.z / 16.0) * 16;
                Direction[] _snowman12 = Lists.newArrayList();
                for (_snowman = -this.renderDistance; _snowman <= this.renderDistance; ++_snowman) {
                    for (_snowman = -this.renderDistance; _snowman <= this.renderDistance; ++_snowman) {
                        ChunkBuilder.BuiltChunk builtChunk = this.chunks.getRenderedChunk(new BlockPos(_snowman + (_snowman << 4) + 8, n, _snowman + (_snowman << 4) + 8));
                        if (builtChunk == null || !frustum.isVisible(builtChunk.boundingBox)) continue;
                        builtChunk.setRebuildFrame(frame);
                        _snowman12.add(new ChunkInfo(builtChunk, null, 0));
                    }
                }
                _snowman12.sort(Comparator.comparingDouble(chunkInfo -> _snowman5.getSquaredDistance(((ChunkInfo)chunkInfo).chunk.getOrigin().add(8, 8, 8))));
                collection.addAll((Collection<ChunkBuilder.BuiltChunk>)_snowman12);
            } else {
                if (spectator && this.world.getBlockState(_snowman5).isOpaqueFullCube(this.world, _snowman5)) {
                    _snowman11 = false;
                }
                _snowman6.setRebuildFrame(frame);
                collection.add((ChunkBuilder.BuiltChunk)((Object)new ChunkInfo(_snowman6, null, 0)));
            }
            this.client.getProfiler().push("iteration");
            while (!collection.isEmpty()) {
                ChunkInfo _snowman13 = (ChunkInfo)collection.poll();
                ChunkBuilder.BuiltChunk _snowman14 = _snowman13.chunk;
                Direction _snowman15 = _snowman13.direction;
                this.visibleChunks.add((Object)_snowman13);
                for (Direction direction : DIRECTIONS) {
                    ChunkBuilder.BuiltChunk builtChunk = this.getAdjacentChunk(_snowman8, _snowman14, direction);
                    if (_snowman11 && _snowman13.canCull(direction.getOpposite()) || _snowman11 && _snowman15 != null && !_snowman14.getData().isVisibleThrough(_snowman15.getOpposite(), direction) || builtChunk == null || !builtChunk.shouldBuild() || !builtChunk.setRebuildFrame(frame) || !frustum.isVisible(builtChunk.boundingBox)) continue;
                    ChunkInfo _snowman16 = new ChunkInfo(builtChunk, direction, _snowman13.propagationLevel + 1);
                    _snowman16.updateCullingState(_snowman13.cullingState, direction);
                    collection.add((ChunkBuilder.BuiltChunk)((Object)_snowman16));
                }
            }
            this.client.getProfiler().pop();
        }
        this.client.getProfiler().swap("rebuildNear");
        collection = this.chunksToRebuild;
        this.chunksToRebuild = Sets.newLinkedHashSet();
        for (ChunkInfo chunkInfo2 : this.visibleChunks) {
            ChunkBuilder.BuiltChunk builtChunk = chunkInfo2.chunk;
            if (!builtChunk.needsRebuild() && !collection.contains(builtChunk)) continue;
            this.needsTerrainUpdate = true;
            BlockPos _snowman17 = builtChunk.getOrigin().add(8, 8, 8);
            boolean bl = _snowman = _snowman17.getSquaredDistance(_snowman5) < 768.0;
            if (builtChunk.needsImportantRebuild() || _snowman) {
                this.client.getProfiler().push("build near");
                this.chunkBuilder.rebuild(builtChunk);
                builtChunk.cancelRebuild();
                this.client.getProfiler().pop();
                continue;
            }
            this.chunksToRebuild.add(builtChunk);
        }
        this.chunksToRebuild.addAll(collection);
        this.client.getProfiler().pop();
    }

    @Nullable
    private ChunkBuilder.BuiltChunk getAdjacentChunk(BlockPos pos, ChunkBuilder.BuiltChunk chunk, Direction direction) {
        BlockPos blockPos = chunk.getNeighborPosition(direction);
        if (MathHelper.abs(pos.getX() - blockPos.getX()) > this.renderDistance * 16) {
            return null;
        }
        if (blockPos.getY() < 0 || blockPos.getY() >= 256) {
            return null;
        }
        if (MathHelper.abs(pos.getZ() - blockPos.getZ()) > this.renderDistance * 16) {
            return null;
        }
        return this.chunks.getRenderedChunk(blockPos);
    }

    private void captureFrustum(Matrix4f modelMatrix, Matrix4f matrix4f, double x, double y, double z, Frustum frustum) {
        this.capturedFrustum = frustum;
        _snowman = matrix4f.copy();
        _snowman.multiply(modelMatrix);
        _snowman.invert();
        this.capturedFrustumPosition.x = x;
        this.capturedFrustumPosition.y = y;
        this.capturedFrustumPosition.z = z;
        this.capturedFrustumOrientation[0] = new Vector4f(-1.0f, -1.0f, -1.0f, 1.0f);
        this.capturedFrustumOrientation[1] = new Vector4f(1.0f, -1.0f, -1.0f, 1.0f);
        this.capturedFrustumOrientation[2] = new Vector4f(1.0f, 1.0f, -1.0f, 1.0f);
        this.capturedFrustumOrientation[3] = new Vector4f(-1.0f, 1.0f, -1.0f, 1.0f);
        this.capturedFrustumOrientation[4] = new Vector4f(-1.0f, -1.0f, 1.0f, 1.0f);
        this.capturedFrustumOrientation[5] = new Vector4f(1.0f, -1.0f, 1.0f, 1.0f);
        this.capturedFrustumOrientation[6] = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.capturedFrustumOrientation[7] = new Vector4f(-1.0f, 1.0f, 1.0f, 1.0f);
        for (int i = 0; i < 8; ++i) {
            this.capturedFrustumOrientation[i].transform(_snowman);
            this.capturedFrustumOrientation[i].normalizeProjectiveCoordinates();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f2) {
        int _snowman22;
        Object object2;
        Frustum _snowman7;
        BlockEntityRenderDispatcher.INSTANCE.configure(this.world, this.client.getTextureManager(), this.client.textRenderer, camera, this.client.crosshairTarget);
        this.entityRenderDispatcher.configure(this.world, camera, this.client.targetedEntity);
        Profiler profiler = this.world.getProfiler();
        profiler.swap("light_updates");
        this.client.world.getChunkManager().getLightingProvider().doLightUpdates(Integer.MAX_VALUE, true, true);
        Vec3d _snowman2 = camera.getPos();
        double _snowman3 = _snowman2.getX();
        double _snowman4 = _snowman2.getY();
        double _snowman5 = _snowman2.getZ();
        Matrix4f _snowman6 = matrices.peek().getModel();
        profiler.swap("culling");
        boolean bl = _snowman = this.capturedFrustum != null;
        if (_snowman) {
            _snowman7 = this.capturedFrustum;
            _snowman7.setPosition(this.capturedFrustumPosition.x, this.capturedFrustumPosition.y, this.capturedFrustumPosition.z);
        } else {
            Matrix4f matrix4f2;
            _snowman7 = new Frustum(_snowman6, matrix4f2);
            _snowman7.setPosition(_snowman3, _snowman4, _snowman5);
        }
        this.client.getProfiler().swap("captureFrustum");
        if (this.shouldCaptureFrustum) {
            this.captureFrustum(_snowman6, matrix4f2, _snowman2.x, _snowman2.y, _snowman2.z, _snowman ? new Frustum(_snowman6, matrix4f2) : _snowman7);
            this.shouldCaptureFrustum = false;
        }
        profiler.swap("clear");
        BackgroundRenderer.render(camera, tickDelta, this.client.world, this.client.options.viewDistance, gameRenderer.getSkyDarkness(tickDelta));
        RenderSystem.clear(16640, MinecraftClient.IS_SYSTEM_MAC);
        float f = gameRenderer.getViewDistance();
        boolean bl2 = _snowman = this.client.world.getSkyProperties().useThickFog(MathHelper.floor(_snowman3), MathHelper.floor(_snowman4)) || this.client.inGameHud.getBossBarHud().shouldThickenFog();
        if (this.client.options.viewDistance >= 4) {
            BackgroundRenderer.applyFog(camera, BackgroundRenderer.FogType.FOG_SKY, f, _snowman);
            profiler.swap("sky");
            this.renderSky(matrices, tickDelta);
        }
        profiler.swap("fog");
        BackgroundRenderer.applyFog(camera, BackgroundRenderer.FogType.FOG_TERRAIN, Math.max(f - 16.0f, 32.0f), _snowman);
        profiler.swap("terrain_setup");
        this.setupTerrain(camera, _snowman7, _snowman, this.frame++, this.client.player.isSpectator());
        profiler.swap("updatechunks");
        int _snowman8 = 30;
        int _snowman9 = this.client.options.maxFps;
        long _snowman10 = 33333333L;
        long _snowman11 = (double)_snowman9 == Option.FRAMERATE_LIMIT.getMax() ? 0L : (long)(1000000000 / _snowman9);
        long _snowman12 = Util.getMeasuringTimeNano() - limitTime;
        long _snowman13 = this.chunkUpdateSmoother.getTargetUsedTime(_snowman12);
        long _snowman14 = _snowman13 * 3L / 2L;
        long _snowman15 = MathHelper.clamp(_snowman14, _snowman11, 33333333L);
        this.updateChunks(limitTime + _snowman15);
        profiler.swap("terrain");
        this.renderLayer(RenderLayer.getSolid(), matrices, _snowman3, _snowman4, _snowman5);
        this.renderLayer(RenderLayer.getCutoutMipped(), matrices, _snowman3, _snowman4, _snowman5);
        this.renderLayer(RenderLayer.getCutout(), matrices, _snowman3, _snowman4, _snowman5);
        if (this.world.getSkyProperties().isDarkened()) {
            DiffuseLighting.enableForLevel(matrices.peek().getModel());
        } else {
            DiffuseLighting.method_27869(matrices.peek().getModel());
        }
        profiler.swap("entities");
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
        boolean _snowman16 = false;
        VertexConsumerProvider.Immediate _snowman17 = this.bufferBuilders.getEntityVertexConsumers();
        for (Entity entity : this.world.getEntities()) {
            if (!this.entityRenderDispatcher.shouldRender(entity, _snowman7, _snowman3, _snowman4, _snowman5) && !entity.hasPassengerDeep(this.client.player) || entity == camera.getFocusedEntity() && !camera.isThirdPerson() && (!(camera.getFocusedEntity() instanceof LivingEntity) || !((LivingEntity)camera.getFocusedEntity()).isSleeping()) || entity instanceof ClientPlayerEntity && camera.getFocusedEntity() != entity) continue;
            ++this.regularEntityCount;
            if (entity.age == 0) {
                entity.lastRenderX = entity.getX();
                entity.lastRenderY = entity.getY();
                entity.lastRenderZ = entity.getZ();
            }
            if (this.canDrawEntityOutlines() && this.client.hasOutline(entity)) {
                _snowman16 = true;
                object2 = _snowman = this.bufferBuilders.getOutlineVertexConsumers();
                int _snowman18 = entity.getTeamColorValue();
                int _snowman19 = 255;
                int _snowman20 = _snowman18 >> 16 & 0xFF;
                int _snowman21 = _snowman18 >> 8 & 0xFF;
                _snowman22 = _snowman18 & 0xFF;
                ((OutlineVertexConsumerProvider)_snowman).setColor(_snowman20, _snowman21, _snowman22, 255);
            } else {
                object2 = _snowman17;
            }
            this.renderEntity(entity, _snowman3, _snowman4, _snowman5, tickDelta, matrices, (VertexConsumerProvider)object2);
        }
        this.checkEmpty(matrices);
        _snowman17.draw(RenderLayer.getEntitySolid(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE));
        _snowman17.draw(RenderLayer.getEntityCutout(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE));
        _snowman17.draw(RenderLayer.getEntityCutoutNoCull(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE));
        _snowman17.draw(RenderLayer.getEntitySmoothCutout(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE));
        profiler.swap("blockentities");
        for (ChunkInfo chunkInfo : this.visibleChunks) {
            object2 = chunkInfo.chunk.getData().getBlockEntities();
            if (object2.isEmpty()) continue;
            _snowman = object2.iterator();
            while (_snowman.hasNext()) {
                BlockEntity _snowman23 = (BlockEntity)_snowman.next();
                BlockPos _snowman24 = _snowman23.getPos();
                VertexConsumerProvider _snowman25 = _snowman17;
                matrices.push();
                matrices.translate((double)_snowman24.getX() - _snowman3, (double)_snowman24.getY() - _snowman4, (double)_snowman24.getZ() - _snowman5);
                SortedSet _snowman26 = (SortedSet)this.blockBreakingProgressions.get(_snowman24.asLong());
                if (_snowman26 != null && !_snowman26.isEmpty() && (_snowman22 = ((BlockBreakingInfo)_snowman26.last()).getStage()) >= 0) {
                    _snowman = matrices.peek();
                    OverlayVertexConsumer overlayVertexConsumer = new OverlayVertexConsumer(this.bufferBuilders.getEffectVertexConsumers().getBuffer(ModelLoader.BLOCK_DESTRUCTION_RENDER_LAYERS.get(_snowman22)), ((MatrixStack.Entry)_snowman).getModel(), ((MatrixStack.Entry)_snowman).getNormal());
                    _snowman25 = renderLayer -> {
                        VertexConsumer vertexConsumer2 = _snowman17.getBuffer(renderLayer);
                        if (renderLayer.hasCrumbling()) {
                            return VertexConsumers.dual(overlayVertexConsumer, vertexConsumer2);
                        }
                        return vertexConsumer2;
                    };
                }
                BlockEntityRenderDispatcher.INSTANCE.render(_snowman23, tickDelta, matrices, _snowman25);
                matrices.pop();
            }
        }
        Set<BlockEntity> set = this.noCullingBlockEntities;
        synchronized (set) {
            for (Object object2 : this.noCullingBlockEntities) {
                _snowman = ((BlockEntity)object2).getPos();
                matrices.push();
                matrices.translate((double)((Vec3i)_snowman).getX() - _snowman3, (double)((Vec3i)_snowman).getY() - _snowman4, (double)((Vec3i)_snowman).getZ() - _snowman5);
                BlockEntityRenderDispatcher.INSTANCE.render(object2, tickDelta, matrices, _snowman17);
                matrices.pop();
            }
        }
        this.checkEmpty(matrices);
        _snowman17.draw(RenderLayer.getSolid());
        _snowman17.draw(TexturedRenderLayers.getEntitySolid());
        _snowman17.draw(TexturedRenderLayers.getEntityCutout());
        _snowman17.draw(TexturedRenderLayers.getBeds());
        _snowman17.draw(TexturedRenderLayers.getShulkerBoxes());
        _snowman17.draw(TexturedRenderLayers.getSign());
        _snowman17.draw(TexturedRenderLayers.getChest());
        this.bufferBuilders.getOutlineVertexConsumers().draw();
        if (_snowman16) {
            this.entityOutlineShader.render(tickDelta);
            this.client.getFramebuffer().beginWrite(false);
        }
        profiler.swap("destroyProgress");
        for (Long2ObjectMap.Entry entry : this.blockBreakingProgressions.long2ObjectEntrySet()) {
            object2 = BlockPos.fromLong(entry.getLongKey());
            double _snowman27 = (double)((Vec3i)object2).getX() - _snowman3;
            if (_snowman27 * _snowman27 + (_snowman = (double)((Vec3i)object2).getY() - _snowman4) * _snowman + (_snowman = (double)((Vec3i)object2).getZ() - _snowman5) * _snowman > 1024.0 || (_snowman = (SortedSet)entry.getValue()) == null || _snowman.isEmpty()) continue;
            int _snowman28 = ((BlockBreakingInfo)_snowman.last()).getStage();
            matrices.push();
            matrices.translate((double)((Vec3i)object2).getX() - _snowman3, (double)((Vec3i)object2).getY() - _snowman4, (double)((Vec3i)object2).getZ() - _snowman5);
            MatrixStack.Entry _snowman29 = matrices.peek();
            OverlayVertexConsumer _snowman30 = new OverlayVertexConsumer(this.bufferBuilders.getEffectVertexConsumers().getBuffer(ModelLoader.BLOCK_DESTRUCTION_RENDER_LAYERS.get(_snowman28)), _snowman29.getModel(), _snowman29.getNormal());
            this.client.getBlockRenderManager().renderDamage(this.world.getBlockState((BlockPos)object2), (BlockPos)object2, this.world, matrices, _snowman30);
            matrices.pop();
        }
        this.checkEmpty(matrices);
        HitResult hitResult = this.client.crosshairTarget;
        if (renderBlockOutline && hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
            profiler.swap("outline");
            BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
            object2 = this.world.getBlockState(blockPos);
            if (!((AbstractBlock.AbstractBlockState)object2).isAir() && this.world.getWorldBorder().contains(blockPos)) {
                VertexConsumer vertexConsumer = _snowman17.getBuffer(RenderLayer.getLines());
                this.drawBlockOutline(matrices, vertexConsumer, camera.getFocusedEntity(), _snowman3, _snowman4, _snowman5, blockPos, (BlockState)object2);
            }
        }
        RenderSystem.pushMatrix();
        RenderSystem.multMatrix(matrices.peek().getModel());
        this.client.debugRenderer.render(matrices, _snowman17, _snowman3, _snowman4, _snowman5);
        RenderSystem.popMatrix();
        _snowman17.draw(TexturedRenderLayers.getEntityTranslucentCull());
        _snowman17.draw(TexturedRenderLayers.getBannerPatterns());
        _snowman17.draw(TexturedRenderLayers.getShieldPatterns());
        _snowman17.draw(RenderLayer.getArmorGlint());
        _snowman17.draw(RenderLayer.getArmorEntityGlint());
        _snowman17.draw(RenderLayer.getGlint());
        _snowman17.draw(RenderLayer.getDirectGlint());
        _snowman17.draw(RenderLayer.method_30676());
        _snowman17.draw(RenderLayer.getEntityGlint());
        _snowman17.draw(RenderLayer.getDirectEntityGlint());
        _snowman17.draw(RenderLayer.getWaterMask());
        this.bufferBuilders.getEffectVertexConsumers().draw();
        if (this.transparencyShader != null) {
            _snowman17.draw(RenderLayer.getLines());
            _snowman17.draw();
            this.translucentFramebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
            this.translucentFramebuffer.copyDepthFrom(this.client.getFramebuffer());
            profiler.swap("translucent");
            this.renderLayer(RenderLayer.getTranslucent(), matrices, _snowman3, _snowman4, _snowman5);
            profiler.swap("string");
            this.renderLayer(RenderLayer.getTripwire(), matrices, _snowman3, _snowman4, _snowman5);
            this.particlesFramebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
            this.particlesFramebuffer.copyDepthFrom(this.client.getFramebuffer());
            RenderPhase.PARTICLES_TARGET.startDrawing();
            profiler.swap("particles");
            this.client.particleManager.renderParticles(matrices, _snowman17, lightmapTextureManager, camera, tickDelta);
            RenderPhase.PARTICLES_TARGET.endDrawing();
        } else {
            profiler.swap("translucent");
            this.renderLayer(RenderLayer.getTranslucent(), matrices, _snowman3, _snowman4, _snowman5);
            _snowman17.draw(RenderLayer.getLines());
            _snowman17.draw();
            profiler.swap("string");
            this.renderLayer(RenderLayer.getTripwire(), matrices, _snowman3, _snowman4, _snowman5);
            profiler.swap("particles");
            this.client.particleManager.renderParticles(matrices, _snowman17, lightmapTextureManager, camera, tickDelta);
        }
        RenderSystem.pushMatrix();
        RenderSystem.multMatrix(matrices.peek().getModel());
        if (this.client.options.getCloudRenderMode() != CloudRenderMode.OFF) {
            if (this.transparencyShader != null) {
                this.cloudsFramebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
                RenderPhase.CLOUDS_TARGET.startDrawing();
                profiler.swap("clouds");
                this.renderClouds(matrices, tickDelta, _snowman3, _snowman4, _snowman5);
                RenderPhase.CLOUDS_TARGET.endDrawing();
            } else {
                profiler.swap("clouds");
                this.renderClouds(matrices, tickDelta, _snowman3, _snowman4, _snowman5);
            }
        }
        if (this.transparencyShader != null) {
            RenderPhase.WEATHER_TARGET.startDrawing();
            profiler.swap("weather");
            this.renderWeather(lightmapTextureManager, tickDelta, _snowman3, _snowman4, _snowman5);
            this.renderWorldBorder(camera);
            RenderPhase.WEATHER_TARGET.endDrawing();
            this.transparencyShader.render(tickDelta);
            this.client.getFramebuffer().beginWrite(false);
        } else {
            RenderSystem.depthMask(false);
            profiler.swap("weather");
            this.renderWeather(lightmapTextureManager, tickDelta, _snowman3, _snowman4, _snowman5);
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

    private void renderEntity(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        double d = MathHelper.lerp((double)tickDelta, entity.lastRenderX, entity.getX());
        _snowman = MathHelper.lerp((double)tickDelta, entity.lastRenderY, entity.getY());
        _snowman = MathHelper.lerp((double)tickDelta, entity.lastRenderZ, entity.getZ());
        float _snowman2 = MathHelper.lerp(tickDelta, entity.prevYaw, entity.yaw);
        this.entityRenderDispatcher.render(entity, d - cameraX, _snowman - cameraY, _snowman - cameraZ, _snowman2, tickDelta, matrices, vertexConsumers, this.entityRenderDispatcher.getLight(entity, tickDelta));
    }

    private void renderLayer(RenderLayer renderLayer2, MatrixStack matrixStack, double d, double d2, double d3) {
        RenderLayer renderLayer2;
        renderLayer2.startDrawing();
        if (renderLayer2 == RenderLayer.getTranslucent()) {
            this.client.getProfiler().push("translucent_sort");
            _snowman = d - this.lastTranslucentSortX;
            _snowman = d2 - this.lastTranslucentSortY;
            _snowman = d3 - this.lastTranslucentSortZ;
            if (_snowman * _snowman + _snowman * _snowman + _snowman * _snowman > 1.0) {
                this.lastTranslucentSortX = d;
                this.lastTranslucentSortY = d2;
                this.lastTranslucentSortZ = d3;
                int n = 0;
                for (ChunkInfo chunkInfo : this.visibleChunks) {
                    if (n >= 15 || !chunkInfo.chunk.scheduleSort(renderLayer2, this.chunkBuilder)) continue;
                    ++n;
                }
            }
            this.client.getProfiler().pop();
        }
        this.client.getProfiler().push("filterempty");
        this.client.getProfiler().swap(() -> "render_" + renderLayer2);
        boolean _snowman2 = renderLayer2 != RenderLayer.getTranslucent();
        ObjectListIterator _snowman3 = this.visibleChunks.listIterator(_snowman2 ? 0 : this.visibleChunks.size());
        while (_snowman2 ? _snowman3.hasNext() : _snowman3.hasPrevious()) {
            ChunkInfo chunkInfo = _snowman2 ? (ChunkInfo)_snowman3.next() : (ChunkInfo)_snowman3.previous();
            ChunkBuilder.BuiltChunk _snowman4 = chunkInfo.chunk;
            if (_snowman4.getData().isEmpty(renderLayer2)) continue;
            VertexBuffer _snowman5 = _snowman4.getBuffer(renderLayer2);
            matrixStack.push();
            BlockPos _snowman6 = _snowman4.getOrigin();
            matrixStack.translate((double)_snowman6.getX() - d, (double)_snowman6.getY() - d2, (double)_snowman6.getZ() - d3);
            _snowman5.bind();
            this.vertexFormat.startDrawing(0L);
            _snowman5.draw(matrixStack.peek().getModel(), 7);
            matrixStack.pop();
        }
        VertexBuffer.unbind();
        RenderSystem.clearCurrentColor();
        this.vertexFormat.endDrawing();
        this.client.getProfiler().pop();
        renderLayer2.endDrawing();
    }

    private void renderChunkDebugInfo(Camera camera) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder _snowman2 = tessellator.getBuffer();
        if (this.client.debugChunkInfo || this.client.debugChunkOcclusion) {
            double d = camera.getPos().getX();
            _snowman = camera.getPos().getY();
            _snowman = camera.getPos().getZ();
            RenderSystem.depthMask(true);
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableTexture();
            for (ChunkInfo chunkInfo : this.visibleChunks) {
                ChunkBuilder.BuiltChunk builtChunk = chunkInfo.chunk;
                RenderSystem.pushMatrix();
                BlockPos _snowman3 = builtChunk.getOrigin();
                RenderSystem.translated((double)_snowman3.getX() - d, (double)_snowman3.getY() - _snowman, (double)_snowman3.getZ() - _snowman);
                if (this.client.debugChunkInfo) {
                    _snowman2.begin(1, VertexFormats.POSITION_COLOR);
                    RenderSystem.lineWidth(10.0f);
                    int n = chunkInfo.propagationLevel == 0 ? 0 : MathHelper.hsvToRgb((float)chunkInfo.propagationLevel / 50.0f, 0.9f, 0.9f);
                    _snowman = n >> 16 & 0xFF;
                    _snowman = n >> 8 & 0xFF;
                    _snowman = n & 0xFF;
                    Direction _snowman4 = chunkInfo.direction;
                    if (_snowman4 != null) {
                        _snowman2.vertex(8.0, 8.0, 8.0).color(_snowman, _snowman, _snowman, 255).next();
                        _snowman2.vertex(8 - 16 * _snowman4.getOffsetX(), 8 - 16 * _snowman4.getOffsetY(), 8 - 16 * _snowman4.getOffsetZ()).color(_snowman, _snowman, _snowman, 255).next();
                    }
                    tessellator.draw();
                    RenderSystem.lineWidth(1.0f);
                }
                if (this.client.debugChunkOcclusion && !builtChunk.getData().isEmpty()) {
                    _snowman2.begin(1, VertexFormats.POSITION_COLOR);
                    RenderSystem.lineWidth(10.0f);
                    n = 0;
                    for (Direction _snowman4 : DIRECTIONS) {
                        for (Direction direction : DIRECTIONS) {
                            boolean bl = builtChunk.getData().isVisibleThrough(_snowman4, direction);
                            if (bl) continue;
                            ++n;
                            _snowman2.vertex(8 + 8 * _snowman4.getOffsetX(), 8 + 8 * _snowman4.getOffsetY(), 8 + 8 * _snowman4.getOffsetZ()).color(1, 0, 0, 1).next();
                            _snowman2.vertex(8 + 8 * direction.getOffsetX(), 8 + 8 * direction.getOffsetY(), 8 + 8 * direction.getOffsetZ()).color(1, 0, 0, 1).next();
                        }
                    }
                    tessellator.draw();
                    RenderSystem.lineWidth(1.0f);
                    if (n > 0) {
                        _snowman2.begin(7, VertexFormats.POSITION_COLOR);
                        float f = 0.5f;
                        _snowman = 0.2f;
                        _snowman2.vertex(0.5, 15.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(15.5, 15.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(15.5, 15.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(0.5, 15.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(0.5, 0.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(15.5, 0.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(15.5, 0.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(0.5, 0.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(0.5, 15.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(0.5, 15.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(0.5, 0.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(0.5, 0.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(15.5, 0.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(15.5, 0.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(15.5, 15.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(15.5, 15.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(0.5, 0.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(15.5, 0.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(15.5, 15.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(0.5, 15.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(0.5, 15.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(15.5, 15.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(15.5, 0.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        _snowman2.vertex(0.5, 0.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).next();
                        tessellator.draw();
                    }
                }
                RenderSystem.popMatrix();
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
            RenderSystem.lineWidth(10.0f);
            RenderSystem.pushMatrix();
            RenderSystem.translatef((float)(this.capturedFrustumPosition.x - camera.getPos().x), (float)(this.capturedFrustumPosition.y - camera.getPos().y), (float)(this.capturedFrustumPosition.z - camera.getPos().z));
            RenderSystem.depthMask(true);
            _snowman2.begin(7, VertexFormats.POSITION_COLOR);
            this.method_22985(_snowman2, 0, 1, 2, 3, 0, 1, 1);
            this.method_22985(_snowman2, 4, 5, 6, 7, 1, 0, 0);
            this.method_22985(_snowman2, 0, 1, 5, 4, 1, 1, 0);
            this.method_22985(_snowman2, 2, 3, 7, 6, 0, 0, 1);
            this.method_22985(_snowman2, 0, 4, 7, 3, 0, 1, 0);
            this.method_22985(_snowman2, 1, 5, 6, 2, 1, 0, 1);
            tessellator.draw();
            RenderSystem.depthMask(false);
            _snowman2.begin(1, VertexFormats.POSITION);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.method_22984(_snowman2, 0);
            this.method_22984(_snowman2, 1);
            this.method_22984(_snowman2, 1);
            this.method_22984(_snowman2, 2);
            this.method_22984(_snowman2, 2);
            this.method_22984(_snowman2, 3);
            this.method_22984(_snowman2, 3);
            this.method_22984(_snowman2, 0);
            this.method_22984(_snowman2, 4);
            this.method_22984(_snowman2, 5);
            this.method_22984(_snowman2, 5);
            this.method_22984(_snowman2, 6);
            this.method_22984(_snowman2, 6);
            this.method_22984(_snowman2, 7);
            this.method_22984(_snowman2, 7);
            this.method_22984(_snowman2, 4);
            this.method_22984(_snowman2, 0);
            this.method_22984(_snowman2, 4);
            this.method_22984(_snowman2, 1);
            this.method_22984(_snowman2, 5);
            this.method_22984(_snowman2, 2);
            this.method_22984(_snowman2, 6);
            this.method_22984(_snowman2, 3);
            this.method_22984(_snowman2, 7);
            tessellator.draw();
            RenderSystem.popMatrix();
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            RenderSystem.enableCull();
            RenderSystem.enableTexture();
            RenderSystem.lineWidth(1.0f);
        }
    }

    private void method_22984(VertexConsumer vertexConsumer, int n) {
        vertexConsumer.vertex(this.capturedFrustumOrientation[n].getX(), this.capturedFrustumOrientation[n].getY(), this.capturedFrustumOrientation[n].getZ()).next();
    }

    private void method_22985(VertexConsumer vertexConsumer, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        float f = 0.25f;
        vertexConsumer.vertex(this.capturedFrustumOrientation[n].getX(), this.capturedFrustumOrientation[n].getY(), this.capturedFrustumOrientation[n].getZ()).color((float)n5, (float)n6, (float)n7, 0.25f).next();
        vertexConsumer.vertex(this.capturedFrustumOrientation[n2].getX(), this.capturedFrustumOrientation[n2].getY(), this.capturedFrustumOrientation[n2].getZ()).color((float)n5, (float)n6, (float)n7, 0.25f).next();
        vertexConsumer.vertex(this.capturedFrustumOrientation[n3].getX(), this.capturedFrustumOrientation[n3].getY(), this.capturedFrustumOrientation[n3].getZ()).color((float)n5, (float)n6, (float)n7, 0.25f).next();
        vertexConsumer.vertex(this.capturedFrustumOrientation[n4].getX(), this.capturedFrustumOrientation[n4].getY(), this.capturedFrustumOrientation[n4].getZ()).color((float)n5, (float)n6, (float)n7, 0.25f).next();
    }

    public void tick() {
        ++this.ticks;
        if (this.ticks % 20 != 0) {
            return;
        }
        ObjectIterator objectIterator = this.blockBreakingInfos.values().iterator();
        while (objectIterator.hasNext()) {
            BlockBreakingInfo blockBreakingInfo = (BlockBreakingInfo)objectIterator.next();
            int _snowman2 = blockBreakingInfo.getLastUpdateTick();
            if (this.ticks - _snowman2 <= 400) continue;
            objectIterator.remove();
            this.removeBlockBreakingInfo(blockBreakingInfo);
        }
    }

    private void removeBlockBreakingInfo(BlockBreakingInfo blockBreakingInfo) {
        long l = blockBreakingInfo.getPos().asLong();
        Set _snowman2 = (Set)this.blockBreakingProgressions.get(l);
        _snowman2.remove(blockBreakingInfo);
        if (_snowman2.isEmpty()) {
            this.blockBreakingProgressions.remove(l);
        }
    }

    private void renderEndSky(MatrixStack matrices) {
        RenderSystem.disableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);
        this.textureManager.bindTexture(END_SKY);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder _snowman2 = tessellator.getBuffer();
        for (int i = 0; i < 6; ++i) {
            matrices.push();
            if (i == 1) {
                matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0f));
            }
            if (i == 2) {
                matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90.0f));
            }
            if (i == 3) {
                matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0f));
            }
            if (i == 4) {
                matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0f));
            }
            if (i == 5) {
                matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-90.0f));
            }
            Matrix4f matrix4f = matrices.peek().getModel();
            _snowman2.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
            _snowman2.vertex(matrix4f, -100.0f, -100.0f, -100.0f).texture(0.0f, 0.0f).color(40, 40, 40, 255).next();
            _snowman2.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(0.0f, 16.0f).color(40, 40, 40, 255).next();
            _snowman2.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(16.0f, 16.0f).color(40, 40, 40, 255).next();
            _snowman2.vertex(matrix4f, 100.0f, -100.0f, -100.0f).texture(16.0f, 0.0f).color(40, 40, 40, 255).next();
            tessellator.draw();
            matrices.pop();
        }
        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
    }

    public void renderSky(MatrixStack matrices, float tickDelta) {
        int _snowman8;
        float f;
        if (this.client.world.getSkyProperties().getSkyType() == SkyProperties.SkyType.END) {
            this.renderEndSky(matrices);
            return;
        }
        if (this.client.world.getSkyProperties().getSkyType() != SkyProperties.SkyType.NORMAL) {
            return;
        }
        RenderSystem.disableTexture();
        Vec3d vec3d = this.world.method_23777(this.client.gameRenderer.getCamera().getBlockPos(), tickDelta);
        float _snowman2 = (float)vec3d.x;
        float _snowman3 = (float)vec3d.y;
        float _snowman4 = (float)vec3d.z;
        BackgroundRenderer.setFogBlack();
        BufferBuilder _snowman5 = Tessellator.getInstance().getBuffer();
        RenderSystem.depthMask(false);
        RenderSystem.enableFog();
        RenderSystem.color3f(_snowman2, _snowman3, _snowman4);
        this.lightSkyBuffer.bind();
        this.skyVertexFormat.startDrawing(0L);
        this.lightSkyBuffer.draw(matrices.peek().getModel(), 7);
        VertexBuffer.unbind();
        this.skyVertexFormat.endDrawing();
        RenderSystem.disableFog();
        RenderSystem.disableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        float[] _snowman6 = this.world.getSkyProperties().getFogColorOverride(this.world.getSkyAngle(tickDelta), tickDelta);
        if (_snowman6 != null) {
            RenderSystem.disableTexture();
            RenderSystem.shadeModel(7425);
            matrices.push();
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0f));
            f = MathHelper.sin(this.world.getSkyAngleRadians(tickDelta)) < 0.0f ? 180.0f : 0.0f;
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(f));
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0f));
            _snowman = _snowman6[0];
            _snowman = _snowman6[1];
            _snowman = _snowman6[2];
            Matrix4f _snowman7 = matrices.peek().getModel();
            _snowman5.begin(6, VertexFormats.POSITION_COLOR);
            _snowman5.vertex(_snowman7, 0.0f, 100.0f, 0.0f).color(_snowman, _snowman, _snowman, _snowman6[3]).next();
            _snowman8 = 16;
            for (int i = 0; i <= 16; ++i) {
                float f2 = (float)i * ((float)Math.PI * 2) / 16.0f;
                _snowman = MathHelper.sin(f2);
                _snowman = MathHelper.cos(f2);
                _snowman5.vertex(_snowman7, _snowman * 120.0f, _snowman * 120.0f, -_snowman * 40.0f * _snowman6[3]).color(_snowman6[0], _snowman6[1], _snowman6[2], 0.0f).next();
            }
            _snowman5.end();
            BufferRenderer.draw(_snowman5);
            matrices.pop();
            RenderSystem.shadeModel(7424);
        }
        RenderSystem.enableTexture();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        matrices.push();
        f = 1.0f - this.world.getRainGradient(tickDelta);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, f);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90.0f));
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(this.world.getSkyAngle(tickDelta) * 360.0f));
        Matrix4f _snowman9 = matrices.peek().getModel();
        _snowman = 30.0f;
        this.textureManager.bindTexture(SUN);
        _snowman5.begin(7, VertexFormats.POSITION_TEXTURE);
        _snowman5.vertex(_snowman9, -_snowman, 100.0f, -_snowman).texture(0.0f, 0.0f).next();
        _snowman5.vertex(_snowman9, _snowman, 100.0f, -_snowman).texture(1.0f, 0.0f).next();
        _snowman5.vertex(_snowman9, _snowman, 100.0f, _snowman).texture(1.0f, 1.0f).next();
        _snowman5.vertex(_snowman9, -_snowman, 100.0f, _snowman).texture(0.0f, 1.0f).next();
        _snowman5.end();
        BufferRenderer.draw(_snowman5);
        _snowman = 20.0f;
        this.textureManager.bindTexture(MOON_PHASES);
        int _snowman10 = this.world.getMoonPhase();
        int _snowman11 = _snowman10 % 4;
        _snowman8 = _snowman10 / 4 % 2;
        _snowman = (float)(_snowman11 + 0) / 4.0f;
        f2 = (float)(_snowman8 + 0) / 2.0f;
        _snowman = (float)(_snowman11 + 1) / 4.0f;
        _snowman = (float)(_snowman8 + 1) / 2.0f;
        _snowman5.begin(7, VertexFormats.POSITION_TEXTURE);
        _snowman5.vertex(_snowman9, -_snowman, -100.0f, _snowman).texture(_snowman, _snowman).next();
        _snowman5.vertex(_snowman9, _snowman, -100.0f, _snowman).texture(_snowman, _snowman).next();
        _snowman5.vertex(_snowman9, _snowman, -100.0f, -_snowman).texture(_snowman, f2).next();
        _snowman5.vertex(_snowman9, -_snowman, -100.0f, -_snowman).texture(_snowman, f2).next();
        _snowman5.end();
        BufferRenderer.draw(_snowman5);
        RenderSystem.disableTexture();
        _snowman = this.world.method_23787(tickDelta) * f;
        if (_snowman > 0.0f) {
            RenderSystem.color4f(_snowman, _snowman, _snowman, _snowman);
            this.starsBuffer.bind();
            this.skyVertexFormat.startDrawing(0L);
            this.starsBuffer.draw(matrices.peek().getModel(), 7);
            VertexBuffer.unbind();
            this.skyVertexFormat.endDrawing();
        }
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableFog();
        matrices.pop();
        RenderSystem.disableTexture();
        RenderSystem.color3f(0.0f, 0.0f, 0.0f);
        double _snowman12 = this.client.player.getCameraPosVec((float)tickDelta).y - this.world.getLevelProperties().getSkyDarknessHeight();
        if (_snowman12 < 0.0) {
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
            RenderSystem.color3f(_snowman2 * 0.2f + 0.04f, _snowman3 * 0.2f + 0.04f, _snowman4 * 0.6f + 0.1f);
        } else {
            RenderSystem.color3f(_snowman2, _snowman3, _snowman4);
        }
        RenderSystem.enableTexture();
        RenderSystem.depthMask(true);
        RenderSystem.disableFog();
    }

    public void renderClouds(MatrixStack matrices, float tickDelta, double cameraX, double cameraY, double cameraZ) {
        float f;
        float f2 = this.world.getSkyProperties().getCloudsHeight();
        if (Float.isNaN(f2)) {
            return;
        }
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableDepthTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableFog();
        RenderSystem.depthMask(true);
        _snowman = 12.0f;
        _snowman = 4.0f;
        double _snowman2 = 2.0E-4;
        double _snowman3 = ((float)this.ticks + tickDelta) * 0.03f;
        double _snowman4 = (cameraX + _snowman3) / 12.0;
        double _snowman5 = f2 - (float)cameraY + 0.33f;
        double _snowman6 = cameraZ / 12.0 + (double)0.33f;
        _snowman4 -= (double)(MathHelper.floor(_snowman4 / 2048.0) * 2048);
        _snowman6 -= (double)(MathHelper.floor(_snowman6 / 2048.0) * 2048);
        f = (float)(_snowman4 - (double)MathHelper.floor(_snowman4));
        _snowman = (float)(_snowman5 / 4.0 - (double)MathHelper.floor(_snowman5 / 4.0)) * 4.0f;
        _snowman = (float)(_snowman6 - (double)MathHelper.floor(_snowman6));
        Vec3d _snowman7 = this.world.getCloudsColor(tickDelta);
        int _snowman8 = (int)Math.floor(_snowman4);
        int _snowman9 = (int)Math.floor(_snowman5 / 4.0);
        int _snowman10 = (int)Math.floor(_snowman6);
        if (_snowman8 != this.lastCloudsBlockX || _snowman9 != this.lastCloudsBlockY || _snowman10 != this.lastCloudsBlockZ || this.client.options.getCloudRenderMode() != this.lastCloudsRenderMode || this.lastCloudsColor.squaredDistanceTo(_snowman7) > 2.0E-4) {
            this.lastCloudsBlockX = _snowman8;
            this.lastCloudsBlockY = _snowman9;
            this.lastCloudsBlockZ = _snowman10;
            this.lastCloudsColor = _snowman7;
            this.lastCloudsRenderMode = this.client.options.getCloudRenderMode();
            this.cloudsDirty = true;
        }
        if (this.cloudsDirty) {
            this.cloudsDirty = false;
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
            if (this.cloudsBuffer != null) {
                this.cloudsBuffer.close();
            }
            this.cloudsBuffer = new VertexBuffer(VertexFormats.POSITION_TEXTURE_COLOR_NORMAL);
            this.renderClouds(bufferBuilder, _snowman4, _snowman5, _snowman6, _snowman7);
            bufferBuilder.end();
            this.cloudsBuffer.upload(bufferBuilder);
        }
        this.textureManager.bindTexture(CLOUDS);
        matrices.push();
        matrices.scale(12.0f, 1.0f, 12.0f);
        matrices.translate(-f, _snowman, -_snowman);
        if (this.cloudsBuffer != null) {
            this.cloudsBuffer.bind();
            VertexFormats.POSITION_TEXTURE_COLOR_NORMAL.startDrawing(0L);
            for (int i = _snowman = this.lastCloudsRenderMode == CloudRenderMode.FANCY ? 0 : 1; i < 2; ++i) {
                if (i == 0) {
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
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableAlphaTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.disableFog();
    }

    private void renderClouds(BufferBuilder builder, double x, double y, double z, Vec3d color) {
        float f = 4.0f;
        _snowman = 0.00390625f;
        int _snowman2 = 8;
        int _snowman3 = 4;
        _snowman = 9.765625E-4f;
        _snowman = (float)MathHelper.floor(x) * 0.00390625f;
        _snowman = (float)MathHelper.floor(z) * 0.00390625f;
        _snowman = (float)color.x;
        _snowman = (float)color.y;
        _snowman = (float)color.z;
        _snowman = _snowman * 0.9f;
        _snowman = _snowman * 0.9f;
        _snowman = _snowman * 0.9f;
        _snowman = _snowman * 0.7f;
        _snowman = _snowman * 0.7f;
        _snowman = _snowman * 0.7f;
        _snowman = _snowman * 0.8f;
        _snowman = _snowman * 0.8f;
        _snowman = _snowman * 0.8f;
        builder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_NORMAL);
        _snowman = (float)Math.floor(y / 4.0) * 4.0f;
        if (this.lastCloudsRenderMode == CloudRenderMode.FANCY) {
            for (int i = -3; i <= 4; ++i) {
                for (_snowman = -3; _snowman <= 4; ++_snowman) {
                    float f2 = i * 8;
                    _snowman = _snowman * 8;
                    if (_snowman > -5.0f) {
                        builder.vertex(f2 + 0.0f, _snowman + 0.0f, _snowman + 8.0f).texture((f2 + 0.0f) * 0.00390625f + _snowman, (_snowman + 8.0f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, -1.0f, 0.0f).next();
                        builder.vertex(f2 + 8.0f, _snowman + 0.0f, _snowman + 8.0f).texture((f2 + 8.0f) * 0.00390625f + _snowman, (_snowman + 8.0f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, -1.0f, 0.0f).next();
                        builder.vertex(f2 + 8.0f, _snowman + 0.0f, _snowman + 0.0f).texture((f2 + 8.0f) * 0.00390625f + _snowman, (_snowman + 0.0f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, -1.0f, 0.0f).next();
                        builder.vertex(f2 + 0.0f, _snowman + 0.0f, _snowman + 0.0f).texture((f2 + 0.0f) * 0.00390625f + _snowman, (_snowman + 0.0f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, -1.0f, 0.0f).next();
                    }
                    if (_snowman <= 5.0f) {
                        builder.vertex(f2 + 0.0f, _snowman + 4.0f - 9.765625E-4f, _snowman + 8.0f).texture((f2 + 0.0f) * 0.00390625f + _snowman, (_snowman + 8.0f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, 1.0f, 0.0f).next();
                        builder.vertex(f2 + 8.0f, _snowman + 4.0f - 9.765625E-4f, _snowman + 8.0f).texture((f2 + 8.0f) * 0.00390625f + _snowman, (_snowman + 8.0f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, 1.0f, 0.0f).next();
                        builder.vertex(f2 + 8.0f, _snowman + 4.0f - 9.765625E-4f, _snowman + 0.0f).texture((f2 + 8.0f) * 0.00390625f + _snowman, (_snowman + 0.0f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, 1.0f, 0.0f).next();
                        builder.vertex(f2 + 0.0f, _snowman + 4.0f - 9.765625E-4f, _snowman + 0.0f).texture((f2 + 0.0f) * 0.00390625f + _snowman, (_snowman + 0.0f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, 1.0f, 0.0f).next();
                    }
                    if (i > -1) {
                        for (int j = 0; j < 8; ++j) {
                            builder.vertex(f2 + (float)j + 0.0f, _snowman + 0.0f, _snowman + 8.0f).texture((f2 + (float)j + 0.5f) * 0.00390625f + _snowman, (_snowman + 8.0f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(-1.0f, 0.0f, 0.0f).next();
                            builder.vertex(f2 + (float)j + 0.0f, _snowman + 4.0f, _snowman + 8.0f).texture((f2 + (float)j + 0.5f) * 0.00390625f + _snowman, (_snowman + 8.0f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(-1.0f, 0.0f, 0.0f).next();
                            builder.vertex(f2 + (float)j + 0.0f, _snowman + 4.0f, _snowman + 0.0f).texture((f2 + (float)j + 0.5f) * 0.00390625f + _snowman, (_snowman + 0.0f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(-1.0f, 0.0f, 0.0f).next();
                            builder.vertex(f2 + (float)j + 0.0f, _snowman + 0.0f, _snowman + 0.0f).texture((f2 + (float)j + 0.5f) * 0.00390625f + _snowman, (_snowman + 0.0f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(-1.0f, 0.0f, 0.0f).next();
                        }
                    }
                    if (i <= 1) {
                        for (j = 0; j < 8; ++j) {
                            builder.vertex(f2 + (float)j + 1.0f - 9.765625E-4f, _snowman + 0.0f, _snowman + 8.0f).texture((f2 + (float)j + 0.5f) * 0.00390625f + _snowman, (_snowman + 8.0f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(1.0f, 0.0f, 0.0f).next();
                            builder.vertex(f2 + (float)j + 1.0f - 9.765625E-4f, _snowman + 4.0f, _snowman + 8.0f).texture((f2 + (float)j + 0.5f) * 0.00390625f + _snowman, (_snowman + 8.0f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(1.0f, 0.0f, 0.0f).next();
                            builder.vertex(f2 + (float)j + 1.0f - 9.765625E-4f, _snowman + 4.0f, _snowman + 0.0f).texture((f2 + (float)j + 0.5f) * 0.00390625f + _snowman, (_snowman + 0.0f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(1.0f, 0.0f, 0.0f).next();
                            builder.vertex(f2 + (float)j + 1.0f - 9.765625E-4f, _snowman + 0.0f, _snowman + 0.0f).texture((f2 + (float)j + 0.5f) * 0.00390625f + _snowman, (_snowman + 0.0f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(1.0f, 0.0f, 0.0f).next();
                        }
                    }
                    if (_snowman > -1) {
                        for (j = 0; j < 8; ++j) {
                            builder.vertex(f2 + 0.0f, _snowman + 4.0f, _snowman + (float)j + 0.0f).texture((f2 + 0.0f) * 0.00390625f + _snowman, (_snowman + (float)j + 0.5f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, 0.0f, -1.0f).next();
                            builder.vertex(f2 + 8.0f, _snowman + 4.0f, _snowman + (float)j + 0.0f).texture((f2 + 8.0f) * 0.00390625f + _snowman, (_snowman + (float)j + 0.5f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, 0.0f, -1.0f).next();
                            builder.vertex(f2 + 8.0f, _snowman + 0.0f, _snowman + (float)j + 0.0f).texture((f2 + 8.0f) * 0.00390625f + _snowman, (_snowman + (float)j + 0.5f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, 0.0f, -1.0f).next();
                            builder.vertex(f2 + 0.0f, _snowman + 0.0f, _snowman + (float)j + 0.0f).texture((f2 + 0.0f) * 0.00390625f + _snowman, (_snowman + (float)j + 0.5f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, 0.0f, -1.0f).next();
                        }
                    }
                    if (_snowman > 1) continue;
                    for (j = 0; j < 8; ++j) {
                        builder.vertex(f2 + 0.0f, _snowman + 4.0f, _snowman + (float)j + 1.0f - 9.765625E-4f).texture((f2 + 0.0f) * 0.00390625f + _snowman, (_snowman + (float)j + 0.5f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, 0.0f, 1.0f).next();
                        builder.vertex(f2 + 8.0f, _snowman + 4.0f, _snowman + (float)j + 1.0f - 9.765625E-4f).texture((f2 + 8.0f) * 0.00390625f + _snowman, (_snowman + (float)j + 0.5f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, 0.0f, 1.0f).next();
                        builder.vertex(f2 + 8.0f, _snowman + 0.0f, _snowman + (float)j + 1.0f - 9.765625E-4f).texture((f2 + 8.0f) * 0.00390625f + _snowman, (_snowman + (float)j + 0.5f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, 0.0f, 1.0f).next();
                        builder.vertex(f2 + 0.0f, _snowman + 0.0f, _snowman + (float)j + 1.0f - 9.765625E-4f).texture((f2 + 0.0f) * 0.00390625f + _snowman, (_snowman + (float)j + 0.5f) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, 0.0f, 1.0f).next();
                    }
                }
            }
        } else {
            boolean bl = true;
            int _snowman4 = 32;
            for (int i = -32; i < 32; i += 32) {
                for (_snowman = -32; _snowman < 32; _snowman += 32) {
                    builder.vertex(i + 0, _snowman, _snowman + 32).texture((float)(i + 0) * 0.00390625f + _snowman, (float)(_snowman + 32) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, -1.0f, 0.0f).next();
                    builder.vertex(i + 32, _snowman, _snowman + 32).texture((float)(i + 32) * 0.00390625f + _snowman, (float)(_snowman + 32) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, -1.0f, 0.0f).next();
                    builder.vertex(i + 32, _snowman, _snowman + 0).texture((float)(i + 32) * 0.00390625f + _snowman, (float)(_snowman + 0) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, -1.0f, 0.0f).next();
                    builder.vertex(i + 0, _snowman, _snowman + 0).texture((float)(i + 0) * 0.00390625f + _snowman, (float)(_snowman + 0) * 0.00390625f + _snowman).color(_snowman, _snowman, _snowman, 0.8f).normal(0.0f, -1.0f, 0.0f).next();
                }
            }
        }
    }

    private void updateChunks(long limitTime) {
        this.needsTerrainUpdate |= this.chunkBuilder.upload();
        long l = Util.getMeasuringTimeNano();
        int _snowman2 = 0;
        if (!this.chunksToRebuild.isEmpty()) {
            Iterator<ChunkBuilder.BuiltChunk> iterator = this.chunksToRebuild.iterator();
            while (iterator.hasNext()) {
                ChunkBuilder.BuiltChunk builtChunk = iterator.next();
                if (builtChunk.needsImportantRebuild()) {
                    this.chunkBuilder.rebuild(builtChunk);
                } else {
                    builtChunk.scheduleRebuild(this.chunkBuilder);
                }
                builtChunk.cancelRebuild();
                iterator.remove();
                long _snowman3 = Util.getMeasuringTimeNano();
                long _snowman4 = limitTime - _snowman3;
                if (_snowman4 >= (_snowman = (_snowman = _snowman3 - l) / (long)(++_snowman2))) continue;
                break;
            }
        }
    }

    private void renderWorldBorder(Camera camera) {
        float _snowman19;
        double d;
        double _snowman18;
        float f;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        WorldBorder _snowman2 = this.world.getWorldBorder();
        double _snowman3 = this.client.options.viewDistance * 16;
        if (camera.getPos().x < _snowman2.getBoundEast() - _snowman3 && camera.getPos().x > _snowman2.getBoundWest() + _snowman3 && camera.getPos().z < _snowman2.getBoundSouth() - _snowman3 && camera.getPos().z > _snowman2.getBoundNorth() + _snowman3) {
            return;
        }
        double _snowman4 = 1.0 - _snowman2.getDistanceInsideBorder(camera.getPos().x, camera.getPos().z) / _snowman3;
        _snowman4 = Math.pow(_snowman4, 4.0);
        double _snowman5 = camera.getPos().x;
        double _snowman6 = camera.getPos().y;
        double _snowman7 = camera.getPos().z;
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        this.textureManager.bindTexture(FORCEFIELD);
        RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
        RenderSystem.pushMatrix();
        int _snowman8 = _snowman2.getStage().getColor();
        float _snowman9 = (float)(_snowman8 >> 16 & 0xFF) / 255.0f;
        float _snowman10 = (float)(_snowman8 >> 8 & 0xFF) / 255.0f;
        float _snowman11 = (float)(_snowman8 & 0xFF) / 255.0f;
        RenderSystem.color4f(_snowman9, _snowman10, _snowman11, (float)_snowman4);
        RenderSystem.polygonOffset(-3.0f, -3.0f);
        RenderSystem.enablePolygonOffset();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableAlphaTest();
        RenderSystem.disableCull();
        float _snowman12 = (float)(Util.getMeasuringTimeMs() % 3000L) / 3000.0f;
        float _snowman13 = 0.0f;
        float _snowman14 = 0.0f;
        float _snowman15 = 128.0f;
        bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
        double _snowman16 = Math.max((double)MathHelper.floor(_snowman7 - _snowman3), _snowman2.getBoundNorth());
        double _snowman17 = Math.min((double)MathHelper.ceil(_snowman7 + _snowman3), _snowman2.getBoundSouth());
        if (_snowman5 > _snowman2.getBoundEast() - _snowman3) {
            f = 0.0f;
            _snowman18 = _snowman16;
            while (_snowman18 < _snowman17) {
                d = Math.min(1.0, _snowman17 - _snowman18);
                _snowman19 = (float)d * 0.5f;
                this.method_22978(bufferBuilder, _snowman5, _snowman6, _snowman7, _snowman2.getBoundEast(), 256, _snowman18, _snowman12 + f, _snowman12 + 0.0f);
                this.method_22978(bufferBuilder, _snowman5, _snowman6, _snowman7, _snowman2.getBoundEast(), 256, _snowman18 + d, _snowman12 + _snowman19 + f, _snowman12 + 0.0f);
                this.method_22978(bufferBuilder, _snowman5, _snowman6, _snowman7, _snowman2.getBoundEast(), 0, _snowman18 + d, _snowman12 + _snowman19 + f, _snowman12 + 128.0f);
                this.method_22978(bufferBuilder, _snowman5, _snowman6, _snowman7, _snowman2.getBoundEast(), 0, _snowman18, _snowman12 + f, _snowman12 + 128.0f);
                _snowman18 += 1.0;
                f += 0.5f;
            }
        }
        if (_snowman5 < _snowman2.getBoundWest() + _snowman3) {
            f = 0.0f;
            _snowman18 = _snowman16;
            while (_snowman18 < _snowman17) {
                d = Math.min(1.0, _snowman17 - _snowman18);
                _snowman19 = (float)d * 0.5f;
                this.method_22978(bufferBuilder, _snowman5, _snowman6, _snowman7, _snowman2.getBoundWest(), 256, _snowman18, _snowman12 + f, _snowman12 + 0.0f);
                this.method_22978(bufferBuilder, _snowman5, _snowman6, _snowman7, _snowman2.getBoundWest(), 256, _snowman18 + d, _snowman12 + _snowman19 + f, _snowman12 + 0.0f);
                this.method_22978(bufferBuilder, _snowman5, _snowman6, _snowman7, _snowman2.getBoundWest(), 0, _snowman18 + d, _snowman12 + _snowman19 + f, _snowman12 + 128.0f);
                this.method_22978(bufferBuilder, _snowman5, _snowman6, _snowman7, _snowman2.getBoundWest(), 0, _snowman18, _snowman12 + f, _snowman12 + 128.0f);
                _snowman18 += 1.0;
                f += 0.5f;
            }
        }
        _snowman16 = Math.max((double)MathHelper.floor(_snowman5 - _snowman3), _snowman2.getBoundWest());
        _snowman17 = Math.min((double)MathHelper.ceil(_snowman5 + _snowman3), _snowman2.getBoundEast());
        if (_snowman7 > _snowman2.getBoundSouth() - _snowman3) {
            f = 0.0f;
            _snowman18 = _snowman16;
            while (_snowman18 < _snowman17) {
                d = Math.min(1.0, _snowman17 - _snowman18);
                _snowman19 = (float)d * 0.5f;
                this.method_22978(bufferBuilder, _snowman5, _snowman6, _snowman7, _snowman18, 256, _snowman2.getBoundSouth(), _snowman12 + f, _snowman12 + 0.0f);
                this.method_22978(bufferBuilder, _snowman5, _snowman6, _snowman7, _snowman18 + d, 256, _snowman2.getBoundSouth(), _snowman12 + _snowman19 + f, _snowman12 + 0.0f);
                this.method_22978(bufferBuilder, _snowman5, _snowman6, _snowman7, _snowman18 + d, 0, _snowman2.getBoundSouth(), _snowman12 + _snowman19 + f, _snowman12 + 128.0f);
                this.method_22978(bufferBuilder, _snowman5, _snowman6, _snowman7, _snowman18, 0, _snowman2.getBoundSouth(), _snowman12 + f, _snowman12 + 128.0f);
                _snowman18 += 1.0;
                f += 0.5f;
            }
        }
        if (_snowman7 < _snowman2.getBoundNorth() + _snowman3) {
            f = 0.0f;
            _snowman18 = _snowman16;
            while (_snowman18 < _snowman17) {
                d = Math.min(1.0, _snowman17 - _snowman18);
                _snowman19 = (float)d * 0.5f;
                this.method_22978(bufferBuilder, _snowman5, _snowman6, _snowman7, _snowman18, 256, _snowman2.getBoundNorth(), _snowman12 + f, _snowman12 + 0.0f);
                this.method_22978(bufferBuilder, _snowman5, _snowman6, _snowman7, _snowman18 + d, 256, _snowman2.getBoundNorth(), _snowman12 + _snowman19 + f, _snowman12 + 0.0f);
                this.method_22978(bufferBuilder, _snowman5, _snowman6, _snowman7, _snowman18 + d, 0, _snowman2.getBoundNorth(), _snowman12 + _snowman19 + f, _snowman12 + 128.0f);
                this.method_22978(bufferBuilder, _snowman5, _snowman6, _snowman7, _snowman18, 0, _snowman2.getBoundNorth(), _snowman12 + f, _snowman12 + 128.0f);
                _snowman18 += 1.0;
                f += 0.5f;
            }
        }
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableCull();
        RenderSystem.disableAlphaTest();
        RenderSystem.polygonOffset(0.0f, 0.0f);
        RenderSystem.disablePolygonOffset();
        RenderSystem.enableAlphaTest();
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
        RenderSystem.depthMask(true);
    }

    private void method_22978(BufferBuilder bufferBuilder, double d, double d2, double d3, double d4, int n, double d5, float f, float f2) {
        bufferBuilder.vertex(d4 - d, (double)n - d2, d5 - d3).texture(f, f2).next();
    }

    private void drawBlockOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, Entity entity, double d, double d2, double d3, BlockPos blockPos, BlockState blockState) {
        WorldRenderer.drawShapeOutline(matrixStack, vertexConsumer, blockState.getOutlineShape(this.world, blockPos, ShapeContext.of(entity)), (double)blockPos.getX() - d, (double)blockPos.getY() - d2, (double)blockPos.getZ() - d3, 0.0f, 0.0f, 0.0f, 0.4f);
    }

    public static void method_22983(MatrixStack matrixStack, VertexConsumer vertexConsumer, VoxelShape voxelShape, double d, double d2, double d3, float f, float f2, float f3, float f4) {
        List<Box> list = voxelShape.getBoundingBoxes();
        int _snowman2 = MathHelper.ceil((double)list.size() / 3.0);
        for (int i = 0; i < list.size(); ++i) {
            Box box = list.get(i);
            float _snowman3 = ((float)i % (float)_snowman2 + 1.0f) / (float)_snowman2;
            float _snowman4 = i / _snowman2;
            float _snowman5 = _snowman3 * (float)(_snowman4 == 0.0f ? 1 : 0);
            float _snowman6 = _snowman3 * (float)(_snowman4 == 1.0f ? 1 : 0);
            float _snowman7 = _snowman3 * (float)(_snowman4 == 2.0f ? 1 : 0);
            WorldRenderer.drawShapeOutline(matrixStack, vertexConsumer, VoxelShapes.cuboid(box.offset(0.0, 0.0, 0.0)), d, d2, d3, _snowman5, _snowman6, _snowman7, 1.0f);
        }
    }

    private static void drawShapeOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, VoxelShape voxelShape, double d, double d2, double d3, float f, float f2, float f3, float f4) {
        Matrix4f matrix4f = matrixStack.peek().getModel();
        voxelShape.forEachEdge((d4, d5, d6, d7, d8, d9) -> {
            vertexConsumer.vertex(matrix4f, (float)(d4 + d), (float)(d5 + d2), (float)(d6 + d3)).color(f, f2, f3, f4).next();
            vertexConsumer.vertex(matrix4f, (float)(d7 + d), (float)(d8 + d2), (float)(d9 + d3)).color(f, f2, f3, f4).next();
        });
    }

    public static void drawBox(MatrixStack matrices, VertexConsumer vertexConsumer, Box box, float red, float green, float blue, float alpha) {
        WorldRenderer.drawBox(matrices, vertexConsumer, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha, red, green, blue);
    }

    public static void drawBox(MatrixStack matrices, VertexConsumer vertexConsumer, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha) {
        WorldRenderer.drawBox(matrices, vertexConsumer, x1, y1, z1, x2, y2, z2, red, green, blue, alpha, red, green, blue);
    }

    public static void drawBox(MatrixStack matrices, VertexConsumer vertexConsumer, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha, float xAxisRed, float yAxisGreen, float zAxisBlue) {
        Matrix4f matrix4f = matrices.peek().getModel();
        float _snowman2 = (float)x1;
        float _snowman3 = (float)y1;
        float _snowman4 = (float)z1;
        float _snowman5 = (float)x2;
        float _snowman6 = (float)y2;
        float _snowman7 = (float)z2;
        vertexConsumer.vertex(matrix4f, _snowman2, _snowman3, _snowman4).color(red, yAxisGreen, zAxisBlue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman5, _snowman3, _snowman4).color(red, yAxisGreen, zAxisBlue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman2, _snowman3, _snowman4).color(xAxisRed, green, zAxisBlue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman2, _snowman6, _snowman4).color(xAxisRed, green, zAxisBlue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman2, _snowman3, _snowman4).color(xAxisRed, yAxisGreen, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman2, _snowman3, _snowman7).color(xAxisRed, yAxisGreen, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman5, _snowman3, _snowman4).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman5, _snowman6, _snowman4).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman5, _snowman6, _snowman4).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman2, _snowman6, _snowman4).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman2, _snowman6, _snowman4).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman2, _snowman6, _snowman7).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman2, _snowman6, _snowman7).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman2, _snowman3, _snowman7).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman2, _snowman3, _snowman7).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman5, _snowman3, _snowman7).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman5, _snowman3, _snowman7).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman5, _snowman3, _snowman4).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman2, _snowman6, _snowman7).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman5, _snowman6, _snowman7).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman5, _snowman3, _snowman7).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman5, _snowman6, _snowman7).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman5, _snowman6, _snowman4).color(red, green, blue, alpha).next();
        vertexConsumer.vertex(matrix4f, _snowman5, _snowman6, _snowman7).color(red, green, blue, alpha).next();
    }

    public static void drawBox(BufferBuilder buffer, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha) {
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
        for (int i = pos.getZ() - 1; i <= pos.getZ() + 1; ++i) {
            for (_snowman = pos.getX() - 1; _snowman <= pos.getX() + 1; ++_snowman) {
                for (_snowman = pos.getY() - 1; _snowman <= pos.getY() + 1; ++_snowman) {
                    this.scheduleChunkRender(_snowman >> 4, _snowman >> 4, i >> 4, important);
                }
            }
        }
    }

    public void scheduleBlockRenders(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        for (int i = minZ - 1; i <= maxZ + 1; ++i) {
            for (_snowman = minX - 1; _snowman <= maxX + 1; ++_snowman) {
                for (_snowman = minY - 1; _snowman <= maxY + 1; ++_snowman) {
                    this.scheduleBlockRender(_snowman >> 4, _snowman >> 4, i >> 4);
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
        for (int i = z - 1; i <= z + 1; ++i) {
            for (_snowman = x - 1; _snowman <= x + 1; ++_snowman) {
                for (_snowman = y - 1; _snowman <= y + 1; ++_snowman) {
                    this.scheduleBlockRender(_snowman, _snowman, i);
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
        SoundInstance _snowman2 = this.playingSongs.get(songPosition);
        if (_snowman2 != null) {
            this.client.getSoundManager().stop(_snowman2);
            this.playingSongs.remove(songPosition);
        }
        if (song != null) {
            MusicDiscItem musicDiscItem = MusicDiscItem.bySound(song);
            if (musicDiscItem != null) {
                this.client.inGameHud.setRecordPlayingOverlay(musicDiscItem.getDescription());
            }
            _snowman2 = PositionedSoundInstance.record(song, songPosition.getX(), songPosition.getY(), songPosition.getZ());
            this.playingSongs.put(songPosition, _snowman2);
            this.client.getSoundManager().play(_snowman2);
        }
        this.updateEntitiesForSong(this.world, songPosition, song != null);
    }

    private void updateEntitiesForSong(World world, BlockPos pos, boolean playing) {
        List<LivingEntity> list = world.getNonSpectatingEntities(LivingEntity.class, new Box(pos).expand(3.0));
        for (LivingEntity livingEntity : list) {
            livingEntity.setNearbySongPlaying(pos, playing);
        }
    }

    public void addParticle(ParticleEffect parameters, boolean shouldAlwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        this.addParticle(parameters, shouldAlwaysSpawn, false, x, y, z, velocityX, velocityY, velocityZ);
    }

    public void addParticle(ParticleEffect parameters, boolean shouldAlwaysSpawn, boolean important, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        try {
            this.spawnParticle(parameters, shouldAlwaysSpawn, important, x, y, z, velocityX, velocityY, velocityZ);
        }
        catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.create(throwable, "Exception while adding particle");
            CrashReportSection _snowman2 = crashReport.addElement("Particle being added");
            _snowman2.add("ID", Registry.PARTICLE_TYPE.getId(parameters.getType()));
            _snowman2.add("Parameters", parameters.asString());
            _snowman2.add("Position", () -> CrashReportSection.createPositionString(x, y, z));
            throw new CrashException(crashReport);
        }
    }

    private <T extends ParticleEffect> void addParticle(T parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        this.addParticle(parameters, parameters.getType().shouldAlwaysSpawn(), x, y, z, velocityX, velocityY, velocityZ);
    }

    @Nullable
    private Particle spawnParticle(ParticleEffect parameters, boolean alwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        return this.spawnParticle(parameters, alwaysSpawn, false, x, y, z, velocityX, velocityY, velocityZ);
    }

    @Nullable
    private Particle spawnParticle(ParticleEffect parameters, boolean alwaysSpawn, boolean canSpawnOnMinimal, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        Camera camera = this.client.gameRenderer.getCamera();
        if (this.client == null || !camera.isReady() || this.client.particleManager == null) {
            return null;
        }
        ParticlesMode _snowman2 = this.getRandomParticleSpawnChance(canSpawnOnMinimal);
        if (alwaysSpawn) {
            return this.client.particleManager.addParticle(parameters, x, y, z, velocityX, velocityY, velocityZ);
        }
        if (camera.getPos().squaredDistanceTo(x, y, z) > 1024.0) {
            return null;
        }
        if (_snowman2 == ParticlesMode.MINIMAL) {
            return null;
        }
        return this.client.particleManager.addParticle(parameters, x, y, z, velocityX, velocityY, velocityZ);
    }

    private ParticlesMode getRandomParticleSpawnChance(boolean canSpawnOnMinimal) {
        ParticlesMode particlesMode = this.client.options.particles;
        if (canSpawnOnMinimal && particlesMode == ParticlesMode.MINIMAL && this.world.random.nextInt(10) == 0) {
            particlesMode = ParticlesMode.DECREASED;
        }
        if (particlesMode == ParticlesMode.DECREASED && this.world.random.nextInt(3) == 0) {
            particlesMode = ParticlesMode.MINIMAL;
        }
        return particlesMode;
    }

    public void method_3267() {
    }

    public void processGlobalEvent(int eventId, BlockPos pos, int n) {
        switch (eventId) {
            case 1023: 
            case 1028: 
            case 1038: {
                Camera camera = this.client.gameRenderer.getCamera();
                if (!camera.isReady()) break;
                double _snowman2 = (double)pos.getX() - camera.getPos().x;
                double _snowman3 = (double)pos.getY() - camera.getPos().y;
                double _snowman4 = (double)pos.getZ() - camera.getPos().z;
                double _snowman5 = Math.sqrt(_snowman2 * _snowman2 + _snowman3 * _snowman3 + _snowman4 * _snowman4);
                double _snowman6 = camera.getPos().x;
                double _snowman7 = camera.getPos().y;
                double _snowman8 = camera.getPos().z;
                if (_snowman5 > 0.0) {
                    _snowman6 += _snowman2 / _snowman5 * 2.0;
                    _snowman7 += _snowman3 / _snowman5 * 2.0;
                    _snowman8 += _snowman4 / _snowman5 * 2.0;
                }
                if (eventId == 1023) {
                    this.world.playSound(_snowman6, _snowman7, _snowman8, SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 1.0f, 1.0f, false);
                    break;
                }
                if (eventId == 1038) {
                    this.world.playSound(_snowman6, _snowman7, _snowman8, SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.HOSTILE, 1.0f, 1.0f, false);
                    break;
                }
                this.world.playSound(_snowman6, _snowman7, _snowman8, SoundEvents.ENTITY_ENDER_DRAGON_DEATH, SoundCategory.HOSTILE, 5.0f, 1.0f, false);
            }
        }
    }

    public void processWorldEvent(PlayerEntity source, int eventId, BlockPos pos, int data) {
        Random random = this.world.random;
        switch (eventId) {
            case 1035: {
                this.world.playSound(pos, SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
                break;
            }
            case 1033: {
                this.world.playSound(pos, SoundEvents.BLOCK_CHORUS_FLOWER_GROW, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
                break;
            }
            case 1034: {
                this.world.playSound(pos, SoundEvents.BLOCK_CHORUS_FLOWER_DEATH, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
                break;
            }
            case 1032: {
                this.client.getSoundManager().play(PositionedSoundInstance.ambient(SoundEvents.BLOCK_PORTAL_TRAVEL, random.nextFloat() * 0.4f + 0.8f, 0.25f));
                break;
            }
            case 1001: {
                this.world.playSound(pos, SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0f, 1.2f, false);
                break;
            }
            case 1000: {
                this.world.playSound(pos, SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
                break;
            }
            case 1003: {
                this.world.playSound(pos, SoundEvents.ENTITY_ENDER_EYE_LAUNCH, SoundCategory.NEUTRAL, 1.0f, 1.2f, false);
                break;
            }
            case 1004: {
                this.world.playSound(pos, SoundEvents.ENTITY_FIREWORK_ROCKET_SHOOT, SoundCategory.NEUTRAL, 1.0f, 1.2f, false);
                break;
            }
            case 1002: {
                this.world.playSound(pos, SoundEvents.BLOCK_DISPENSER_LAUNCH, SoundCategory.BLOCKS, 1.0f, 1.2f, false);
                break;
            }
            case 2000: {
                Direction direction = Direction.byId(data);
                int _snowman2 = direction.getOffsetX();
                int _snowman3 = direction.getOffsetY();
                int _snowman4 = direction.getOffsetZ();
                double _snowman5 = (double)pos.getX() + (double)_snowman2 * 0.6 + 0.5;
                double _snowman6 = (double)pos.getY() + (double)_snowman3 * 0.6 + 0.5;
                double _snowman7 = (double)pos.getZ() + (double)_snowman4 * 0.6 + 0.5;
                for (int i = 0; i < 10; ++i) {
                    double d = random.nextDouble() * 0.2 + 0.01;
                    _snowman = _snowman5 + (double)_snowman2 * 0.01 + (random.nextDouble() - 0.5) * (double)_snowman4 * 0.5;
                    _snowman = _snowman6 + (double)_snowman3 * 0.01 + (random.nextDouble() - 0.5) * (double)_snowman3 * 0.5;
                    _snowman = _snowman7 + (double)_snowman4 * 0.01 + (random.nextDouble() - 0.5) * (double)_snowman2 * 0.5;
                    _snowman = (double)_snowman2 * d + random.nextGaussian() * 0.01;
                    _snowman = (double)_snowman3 * d + random.nextGaussian() * 0.01;
                    _snowman = (double)_snowman4 * d + random.nextGaussian() * 0.01;
                    this.addParticle(ParticleTypes.SMOKE, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
                }
                break;
            }
            case 2003: {
                double d = (double)pos.getX() + 0.5;
                _snowman = pos.getY();
                _snowman = (double)pos.getZ() + 0.5;
                for (int i = 0; i < 8; ++i) {
                    this.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Items.ENDER_EYE)), d, _snowman, _snowman, random.nextGaussian() * 0.15, random.nextDouble() * 0.2, random.nextGaussian() * 0.15);
                }
                for (double d2 = 0.0; d2 < Math.PI * 2; d2 += 0.15707963267948966) {
                    this.addParticle(ParticleTypes.PORTAL, d + Math.cos(d2) * 5.0, _snowman - 0.4, _snowman + Math.sin(d2) * 5.0, Math.cos(d2) * -5.0, 0.0, Math.sin(d2) * -5.0);
                    this.addParticle(ParticleTypes.PORTAL, d + Math.cos(d2) * 5.0, _snowman - 0.4, _snowman + Math.sin(d2) * 5.0, Math.cos(d2) * -7.0, 0.0, Math.sin(d2) * -7.0);
                }
                break;
            }
            case 2002: 
            case 2007: {
                Vec3d vec3d = Vec3d.ofBottomCenter(pos);
                for (int i = 0; i < 8; ++i) {
                    this.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Items.SPLASH_POTION)), vec3d.x, vec3d.y, vec3d.z, random.nextGaussian() * 0.15, random.nextDouble() * 0.2, random.nextGaussian() * 0.15);
                }
                float f = (float)(data >> 16 & 0xFF) / 255.0f;
                _snowman = (float)(data >> 8 & 0xFF) / 255.0f;
                _snowman = (float)(data >> 0 & 0xFF) / 255.0f;
                DefaultParticleType _snowman8 = eventId == 2007 ? ParticleTypes.INSTANT_EFFECT : ParticleTypes.EFFECT;
                for (int i = 0; i < 100; ++i) {
                    double d = random.nextDouble() * 4.0;
                    _snowman = random.nextDouble() * Math.PI * 2.0;
                    _snowman = Math.cos(_snowman) * d;
                    _snowman = 0.01 + random.nextDouble() * 0.5;
                    _snowman = Math.sin(_snowman) * d;
                    Particle _snowman9 = this.spawnParticle(_snowman8, _snowman8.getType().shouldAlwaysSpawn(), vec3d.x + _snowman * 0.1, vec3d.y + 0.3, vec3d.z + _snowman * 0.1, _snowman, _snowman, _snowman);
                    if (_snowman9 == null) continue;
                    float _snowman10 = 0.75f + random.nextFloat() * 0.25f;
                    _snowman9.setColor(f * _snowman10, _snowman * _snowman10, _snowman * _snowman10);
                    _snowman9.move((float)d);
                }
                this.world.playSound(pos, SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.NEUTRAL, 1.0f, random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 2001: {
                BlockState blockState = Block.getStateFromRawId(data);
                if (!blockState.isAir()) {
                    BlockSoundGroup blockSoundGroup = blockState.getSoundGroup();
                    this.world.playSound(pos, blockSoundGroup.getBreakSound(), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0f) / 2.0f, blockSoundGroup.getPitch() * 0.8f, false);
                }
                this.client.particleManager.addBlockBreakParticles(pos, blockState);
                break;
            }
            case 2004: {
                for (int i = 0; i < 20; ++i) {
                    double d = (double)pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 2.0;
                    _snowman = (double)pos.getY() + 0.5 + (random.nextDouble() - 0.5) * 2.0;
                    _snowman = (double)pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 2.0;
                    this.world.addParticle(ParticleTypes.SMOKE, d, _snowman, _snowman, 0.0, 0.0, 0.0);
                    this.world.addParticle(ParticleTypes.FLAME, d, _snowman, _snowman, 0.0, 0.0, 0.0);
                }
                break;
            }
            case 2005: {
                BoneMealItem.createParticles(this.world, pos, data);
                break;
            }
            case 2008: {
                this.world.addParticle(ParticleTypes.EXPLOSION, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);
                break;
            }
            case 1500: {
                ComposterBlock.playEffects(this.world, pos, data > 0);
                break;
            }
            case 1501: {
                this.world.playSound(pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (random.nextFloat() - random.nextFloat()) * 0.8f, false);
                for (int i = 0; i < 8; ++i) {
                    this.world.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX() + random.nextDouble(), (double)pos.getY() + 1.2, (double)pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                }
                break;
            }
            case 1502: {
                this.world.playSound(pos, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 0.5f, 2.6f + (random.nextFloat() - random.nextFloat()) * 0.8f, false);
                for (int i = 0; i < 5; ++i) {
                    double d = (double)pos.getX() + random.nextDouble() * 0.6 + 0.2;
                    _snowman = (double)pos.getY() + random.nextDouble() * 0.6 + 0.2;
                    _snowman = (double)pos.getZ() + random.nextDouble() * 0.6 + 0.2;
                    this.world.addParticle(ParticleTypes.SMOKE, d, _snowman, _snowman, 0.0, 0.0, 0.0);
                }
                break;
            }
            case 1503: {
                this.world.playSound(pos, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
                for (int i = 0; i < 16; ++i) {
                    double d = (double)pos.getX() + (5.0 + random.nextDouble() * 6.0) / 16.0;
                    _snowman = (double)pos.getY() + 0.8125;
                    _snowman = (double)pos.getZ() + (5.0 + random.nextDouble() * 6.0) / 16.0;
                    this.world.addParticle(ParticleTypes.SMOKE, d, _snowman, _snowman, 0.0, 0.0, 0.0);
                }
                break;
            }
            case 2006: {
                for (int i = 0; i < 200; ++i) {
                    float f = random.nextFloat() * 4.0f;
                    _snowman = random.nextFloat() * ((float)Math.PI * 2);
                    double _snowman11 = MathHelper.cos(_snowman) * f;
                    double _snowman12 = 0.01 + random.nextDouble() * 0.5;
                    double _snowman13 = MathHelper.sin(_snowman) * f;
                    Particle _snowman14 = this.spawnParticle(ParticleTypes.DRAGON_BREATH, false, (double)pos.getX() + _snowman11 * 0.1, (double)pos.getY() + 0.3, (double)pos.getZ() + _snowman13 * 0.1, _snowman11, _snowman12, _snowman13);
                    if (_snowman14 == null) continue;
                    _snowman14.move(f);
                }
                if (data != 1) break;
                this.world.playSound(pos, SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.HOSTILE, 1.0f, random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 2009: {
                for (int i = 0; i < 8; ++i) {
                    this.world.addParticle(ParticleTypes.CLOUD, (double)pos.getX() + random.nextDouble(), (double)pos.getY() + 1.2, (double)pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                }
                break;
            }
            case 1012: {
                this.world.playSound(pos, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0f, random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1036: {
                this.world.playSound(pos, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0f, random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1013: {
                this.world.playSound(pos, SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0f, random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1014: {
                this.world.playSound(pos, SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundCategory.BLOCKS, 1.0f, random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1011: {
                this.world.playSound(pos, SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0f, random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1006: {
                this.world.playSound(pos, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.BLOCKS, 1.0f, random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1007: {
                this.world.playSound(pos, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0f, random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1037: {
                this.world.playSound(pos, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0f, random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1008: {
                this.world.playSound(pos, SoundEvents.BLOCK_FENCE_GATE_OPEN, SoundCategory.BLOCKS, 1.0f, random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1005: {
                this.world.playSound(pos, SoundEvents.BLOCK_IRON_DOOR_OPEN, SoundCategory.BLOCKS, 1.0f, random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1009: {
                this.world.playSound(pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (random.nextFloat() - random.nextFloat()) * 0.8f, false);
                break;
            }
            case 1029: {
                this.world.playSound(pos, SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.BLOCKS, 1.0f, random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1030: {
                this.world.playSound(pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0f, random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1044: {
                this.world.playSound(pos, SoundEvents.BLOCK_SMITHING_TABLE_USE, SoundCategory.BLOCKS, 1.0f, this.world.random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1031: {
                this.world.playSound(pos, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 0.3f, this.world.random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1039: {
                this.world.playSound(pos, SoundEvents.ENTITY_PHANTOM_BITE, SoundCategory.HOSTILE, 0.3f, this.world.random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1010: {
                if (Item.byRawId(data) instanceof MusicDiscItem) {
                    this.playSong(((MusicDiscItem)Item.byRawId(data)).getSound(), pos);
                    break;
                }
                this.playSong(null, pos);
                break;
            }
            case 1015: {
                this.world.playSound(pos, SoundEvents.ENTITY_GHAST_WARN, SoundCategory.HOSTILE, 10.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1017: {
                this.world.playSound(pos, SoundEvents.ENTITY_ENDER_DRAGON_SHOOT, SoundCategory.HOSTILE, 10.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1016: {
                this.world.playSound(pos, SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.HOSTILE, 10.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1019: {
                this.world.playSound(pos, SoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, SoundCategory.HOSTILE, 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1022: {
                this.world.playSound(pos, SoundEvents.ENTITY_WITHER_BREAK_BLOCK, SoundCategory.HOSTILE, 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1021: {
                this.world.playSound(pos, SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, SoundCategory.HOSTILE, 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1020: {
                this.world.playSound(pos, SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, SoundCategory.HOSTILE, 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1018: {
                this.world.playSound(pos, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.HOSTILE, 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1024: {
                this.world.playSound(pos, SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.HOSTILE, 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1026: {
                this.world.playSound(pos, SoundEvents.ENTITY_ZOMBIE_INFECT, SoundCategory.HOSTILE, 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1027: {
                this.world.playSound(pos, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.NEUTRAL, 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1040: {
                this.world.playSound(pos, SoundEvents.ENTITY_ZOMBIE_CONVERTED_TO_DROWNED, SoundCategory.NEUTRAL, 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1041: {
                this.world.playSound(pos, SoundEvents.ENTITY_HUSK_CONVERTED_TO_ZOMBIE, SoundCategory.NEUTRAL, 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1025: {
                this.world.playSound(pos, SoundEvents.ENTITY_BAT_TAKEOFF, SoundCategory.NEUTRAL, 0.05f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1042: {
                this.world.playSound(pos, SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 1.0f, this.world.random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1043: {
                this.world.playSound(pos, SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.BLOCKS, 1.0f, this.world.random.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 3000: {
                this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, true, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 0.0, 0.0, 0.0);
                this.world.playSound(pos, SoundEvents.BLOCK_END_GATEWAY_SPAWN, SoundCategory.BLOCKS, 10.0f, (1.0f + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2f) * 0.7f, false);
                break;
            }
            case 3001: {
                this.world.playSound(pos, SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.HOSTILE, 64.0f, 0.8f + this.world.random.nextFloat() * 0.3f, false);
            }
        }
    }

    public void setBlockBreakingInfo(int entityId, BlockPos pos, int stage) {
        if (stage < 0 || stage >= 10) {
            BlockBreakingInfo blockBreakingInfo = (BlockBreakingInfo)this.blockBreakingInfos.remove(entityId);
            if (blockBreakingInfo != null) {
                this.removeBlockBreakingInfo(blockBreakingInfo);
            }
        } else {
            BlockBreakingInfo blockBreakingInfo = (BlockBreakingInfo)this.blockBreakingInfos.get(entityId);
            if (blockBreakingInfo != null) {
                this.removeBlockBreakingInfo(blockBreakingInfo);
            }
            if (blockBreakingInfo == null || blockBreakingInfo.getPos().getX() != pos.getX() || blockBreakingInfo.getPos().getY() != pos.getY() || blockBreakingInfo.getPos().getZ() != pos.getZ()) {
                blockBreakingInfo = new BlockBreakingInfo(entityId, pos);
                this.blockBreakingInfos.put(entityId, (Object)blockBreakingInfo);
            }
            blockBreakingInfo.setStage(stage);
            blockBreakingInfo.setLastUpdateTick(this.ticks);
            ((SortedSet)this.blockBreakingProgressions.computeIfAbsent(blockBreakingInfo.getPos().asLong(), l -> Sets.newTreeSet())).add(blockBreakingInfo);
        }
    }

    public boolean isTerrainRenderComplete() {
        return this.chunksToRebuild.isEmpty() && this.chunkBuilder.isEmpty();
    }

    public void scheduleTerrainUpdate() {
        this.needsTerrainUpdate = true;
        this.cloudsDirty = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateNoCullingBlockEntities(Collection<BlockEntity> removed, Collection<BlockEntity> added) {
        Set<BlockEntity> set = this.noCullingBlockEntities;
        synchronized (set) {
            this.noCullingBlockEntities.removeAll(removed);
            this.noCullingBlockEntities.addAll(added);
        }
    }

    public static int getLightmapCoordinates(BlockRenderView world, BlockPos pos) {
        return WorldRenderer.getLightmapCoordinates(world, world.getBlockState(pos), pos);
    }

    public static int getLightmapCoordinates(BlockRenderView world, BlockState state, BlockPos pos) {
        if (state.hasEmissiveLighting(world, pos)) {
            return 0xF000F0;
        }
        int n = world.getLightLevel(LightType.SKY, pos);
        _snowman = world.getLightLevel(LightType.BLOCK, pos);
        if (_snowman < (_snowman = state.getLuminance())) {
            _snowman = _snowman;
        }
        return n << 20 | _snowman << 4;
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

    public static class ShaderException
    extends RuntimeException {
        public ShaderException(String string, Throwable throwable) {
            super(string, throwable);
        }
    }

    class ChunkInfo {
        private final ChunkBuilder.BuiltChunk chunk;
        private final Direction direction;
        private byte cullingState;
        private final int propagationLevel;

        private ChunkInfo(ChunkBuilder.BuiltChunk chunk, @Nullable Direction direction, int propagationLevel) {
            this.chunk = chunk;
            this.direction = direction;
            this.propagationLevel = propagationLevel;
        }

        public void updateCullingState(byte parentCullingState, Direction from) {
            this.cullingState = (byte)(this.cullingState | (parentCullingState | 1 << from.ordinal()));
        }

        public boolean canCull(Direction from) {
            return (this.cullingState & 1 << from.ordinal()) > 0;
        }
    }
}

