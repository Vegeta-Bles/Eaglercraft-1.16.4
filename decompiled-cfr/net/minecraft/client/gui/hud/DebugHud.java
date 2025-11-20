/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.DataFixUtils
 *  it.unimi.dsi.fastutil.longs.LongSets
 *  it.unimi.dsi.fastutil.longs.LongSets$EmptySet
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.hud;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlDebugInfo;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.DataFixUtils;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.block.BlockState;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.AffineTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.fluid.FluidState;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Property;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.MetricsData;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LightType;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.light.LightingProvider;

public class DebugHud
extends DrawableHelper {
    private static final Map<Heightmap.Type, String> HEIGHT_MAP_TYPES = Util.make(new EnumMap(Heightmap.Type.class), enumMap -> {
        enumMap.put(Heightmap.Type.WORLD_SURFACE_WG, "SW");
        enumMap.put(Heightmap.Type.WORLD_SURFACE, "S");
        enumMap.put(Heightmap.Type.OCEAN_FLOOR_WG, "OW");
        enumMap.put(Heightmap.Type.OCEAN_FLOOR, "O");
        enumMap.put(Heightmap.Type.MOTION_BLOCKING, "M");
        enumMap.put(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, "ML");
    });
    private final MinecraftClient client;
    private final TextRenderer fontRenderer;
    private HitResult blockHit;
    private HitResult fluidHit;
    @Nullable
    private ChunkPos pos;
    @Nullable
    private WorldChunk chunk;
    @Nullable
    private CompletableFuture<WorldChunk> chunkFuture;

    public DebugHud(MinecraftClient client) {
        this.client = client;
        this.fontRenderer = client.textRenderer;
    }

    public void resetChunk() {
        this.chunkFuture = null;
        this.chunk = null;
    }

    public void render(MatrixStack matrices) {
        this.client.getProfiler().push("debug");
        RenderSystem.pushMatrix();
        Entity entity = this.client.getCameraEntity();
        this.blockHit = entity.raycast(20.0, 0.0f, false);
        this.fluidHit = entity.raycast(20.0, 0.0f, true);
        this.renderLeftText(matrices);
        this.renderRightText(matrices);
        RenderSystem.popMatrix();
        if (this.client.options.debugTpsEnabled) {
            int n = this.client.getWindow().getScaledWidth();
            this.drawMetricsData(matrices, this.client.getMetricsData(), 0, n / 2, true);
            IntegratedServer _snowman2 = this.client.getServer();
            if (_snowman2 != null) {
                this.drawMetricsData(matrices, _snowman2.getMetricsData(), n - Math.min(n / 2, 240), n / 2, false);
            }
        }
        this.client.getProfiler().pop();
    }

    protected void renderLeftText(MatrixStack matrices) {
        List<String> list = this.getLeftText();
        list.add("");
        boolean _snowman2 = this.client.getServer() != null;
        list.add("Debug: Pie [shift]: " + (this.client.options.debugProfilerEnabled ? "visible" : "hidden") + (_snowman2 ? " FPS + TPS" : " FPS") + " [alt]: " + (this.client.options.debugTpsEnabled ? "visible" : "hidden"));
        list.add("For help: press F3 + Q");
        for (int i = 0; i < list.size(); ++i) {
            String string = list.get(i);
            if (Strings.isNullOrEmpty((String)string)) continue;
            int _snowman3 = this.fontRenderer.fontHeight;
            int _snowman4 = this.fontRenderer.getWidth(string);
            int _snowman5 = 2;
            int _snowman6 = 2 + _snowman3 * i;
            DebugHud.fill(matrices, 1, _snowman6 - 1, 2 + _snowman4 + 1, _snowman6 + _snowman3 - 1, -1873784752);
            this.fontRenderer.draw(matrices, string, 2.0f, (float)_snowman6, 0xE0E0E0);
        }
    }

    protected void renderRightText(MatrixStack matrices) {
        List<String> list = this.getRightText();
        for (int i = 0; i < list.size(); ++i) {
            String string = list.get(i);
            if (Strings.isNullOrEmpty((String)string)) continue;
            int _snowman2 = this.fontRenderer.fontHeight;
            int _snowman3 = this.fontRenderer.getWidth(string);
            int _snowman4 = this.client.getWindow().getScaledWidth() - 2 - _snowman3;
            int _snowman5 = 2 + _snowman2 * i;
            DebugHud.fill(matrices, _snowman4 - 1, _snowman5 - 1, _snowman4 + _snowman3 + 1, _snowman5 + _snowman2 - 1, -1873784752);
            this.fontRenderer.draw(matrices, string, (float)_snowman4, (float)_snowman5, 0xE0E0E0);
        }
    }

    protected List<String> getLeftText() {
        World world;
        String string;
        IntegratedServer integratedServer = this.client.getServer();
        ClientConnection _snowman2 = this.client.getNetworkHandler().getConnection();
        float _snowman3 = _snowman2.getAveragePacketsSent();
        float _snowman4 = _snowman2.getAveragePacketsReceived();
        if (integratedServer != null) {
            String _snowman5 = String.format("Integrated server @ %.0f ms ticks, %.0f tx, %.0f rx", Float.valueOf(integratedServer.getTickTime()), Float.valueOf(_snowman3), Float.valueOf(_snowman4));
        } else {
            string = String.format("\"%s\" server, %.0f tx, %.0f rx", this.client.player.getServerBrand(), Float.valueOf(_snowman3), Float.valueOf(_snowman4));
        }
        BlockPos _snowman6 = this.client.getCameraEntity().getBlockPos();
        if (this.client.hasReducedDebugInfo()) {
            return Lists.newArrayList((Object[])new String[]{"Minecraft " + SharedConstants.getGameVersion().getName() + " (" + this.client.getGameVersion() + "/" + ClientBrandRetriever.getClientModName() + ")", this.client.fpsDebugString, string, this.client.worldRenderer.getChunksDebugString(), this.client.worldRenderer.getEntitiesDebugString(), "P: " + this.client.particleManager.getDebugString() + ". T: " + this.client.world.getRegularEntityCount(), this.client.world.getDebugString(), "", String.format("Chunk-relative: %d %d %d", _snowman6.getX() & 0xF, _snowman6.getY() & 0xF, _snowman6.getZ() & 0xF)});
        }
        Entity _snowman7 = this.client.getCameraEntity();
        Direction _snowman8 = _snowman7.getHorizontalFacing();
        switch (_snowman8) {
            case NORTH: {
                String string2 = "Towards negative Z";
                break;
            }
            case SOUTH: {
                String string3 = "Towards positive Z";
                break;
            }
            case WEST: {
                String string3 = "Towards negative X";
                break;
            }
            case EAST: {
                String string3 = "Towards positive X";
                break;
            }
            default: {
                String string3 = "Invalid";
            }
        }
        ChunkPos chunkPos = new ChunkPos(_snowman6);
        if (!Objects.equals(this.pos, chunkPos)) {
            this.pos = chunkPos;
            this.resetChunk();
        }
        LongSets.EmptySet _snowman9 = (world = this.getWorld()) instanceof ServerWorld ? ((ServerWorld)world).getForcedChunks() : LongSets.EMPTY_SET;
        ArrayList _snowman10 = Lists.newArrayList((Object[])new String[]{"Minecraft " + SharedConstants.getGameVersion().getName() + " (" + this.client.getGameVersion() + "/" + ClientBrandRetriever.getClientModName() + ("release".equalsIgnoreCase(this.client.getVersionType()) ? "" : "/" + this.client.getVersionType()) + ")", this.client.fpsDebugString, string, this.client.worldRenderer.getChunksDebugString(), this.client.worldRenderer.getEntitiesDebugString(), "P: " + this.client.particleManager.getDebugString() + ". T: " + this.client.world.getRegularEntityCount(), this.client.world.getDebugString()});
        String _snowman11 = this.getServerWorldDebugString();
        if (_snowman11 != null) {
            _snowman10.add(_snowman11);
        }
        _snowman10.add(this.client.world.getRegistryKey().getValue() + " FC: " + _snowman9.size());
        _snowman10.add("");
        _snowman10.add(String.format(Locale.ROOT, "XYZ: %.3f / %.5f / %.3f", this.client.getCameraEntity().getX(), this.client.getCameraEntity().getY(), this.client.getCameraEntity().getZ()));
        _snowman10.add(String.format("Block: %d %d %d", _snowman6.getX(), _snowman6.getY(), _snowman6.getZ()));
        _snowman10.add(String.format("Chunk: %d %d %d in %d %d %d", _snowman6.getX() & 0xF, _snowman6.getY() & 0xF, _snowman6.getZ() & 0xF, _snowman6.getX() >> 4, _snowman6.getY() >> 4, _snowman6.getZ() >> 4));
        _snowman10.add(String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", _snowman8, string3, Float.valueOf(MathHelper.wrapDegrees(_snowman7.yaw)), Float.valueOf(MathHelper.wrapDegrees(_snowman7.pitch))));
        if (this.client.world != null) {
            if (this.client.world.isChunkLoaded(_snowman6)) {
                WorldChunk worldChunk = this.getClientChunk();
                if (worldChunk.isEmpty()) {
                    _snowman10.add("Waiting for chunk...");
                } else {
                    int n = this.client.world.getChunkManager().getLightingProvider().getLight(_snowman6, 0);
                    int n2 = this.client.world.getLightLevel(LightType.SKY, _snowman6);
                    _snowman = this.client.world.getLightLevel(LightType.BLOCK, _snowman6);
                    _snowman10.add("Client Light: " + n + " (" + n2 + " sky, " + _snowman + " block)");
                    WorldChunk _snowman12 = this.getChunk();
                    if (_snowman12 != null) {
                        LightingProvider object = world.getChunkManager().getLightingProvider();
                        _snowman10.add("Server Light: (" + object.get(LightType.SKY).getLightLevel(_snowman6) + " sky, " + object.get(LightType.BLOCK).getLightLevel(_snowman6) + " block)");
                    } else {
                        _snowman10.add("Server Light: (?? sky, ?? block)");
                    }
                    StringBuilder _snowman16 = new StringBuilder("CH");
                    for (Heightmap.Type type : Heightmap.Type.values()) {
                        if (!type.shouldSendToClient()) continue;
                        _snowman16.append(" ").append(HEIGHT_MAP_TYPES.get(type)).append(": ").append(worldChunk.sampleHeightmap(type, _snowman6.getX(), _snowman6.getZ()));
                    }
                    _snowman10.add(_snowman16.toString());
                    _snowman16.setLength(0);
                    _snowman16.append("SH");
                    for (Heightmap.Type type : Heightmap.Type.values()) {
                        if (!type.isStoredServerSide()) continue;
                        _snowman16.append(" ").append(HEIGHT_MAP_TYPES.get(type)).append(": ");
                        if (_snowman12 != null) {
                            _snowman16.append(_snowman12.sampleHeightmap(type, _snowman6.getX(), _snowman6.getZ()));
                            continue;
                        }
                        _snowman16.append("??");
                    }
                    _snowman10.add(_snowman16.toString());
                    if (_snowman6.getY() >= 0 && _snowman6.getY() < 256) {
                        _snowman10.add("Biome: " + this.client.world.getRegistryManager().get(Registry.BIOME_KEY).getId(this.client.world.getBiome(_snowman6)));
                        long l = 0L;
                        float _snowman13 = 0.0f;
                        if (_snowman12 != null) {
                            _snowman13 = world.getMoonSize();
                            l = _snowman12.getInhabitedTime();
                        }
                        LocalDifficulty localDifficulty = new LocalDifficulty(world.getDifficulty(), world.getTimeOfDay(), l, _snowman13);
                        _snowman10.add(String.format(Locale.ROOT, "Local Difficulty: %.2f // %.2f (Day %d)", Float.valueOf(localDifficulty.getLocalDifficulty()), Float.valueOf(localDifficulty.getClampedLocalDifficulty()), this.client.world.getTimeOfDay() / 24000L));
                    }
                }
            } else {
                _snowman10.add("Outside of world...");
            }
        } else {
            _snowman10.add("Outside of world...");
        }
        ServerWorld blockView = this.getServerWorld();
        if (blockView != null) {
            SpawnHelper.Info info = blockView.getChunkManager().getSpawnInfo();
            if (info != null) {
                Object2IntMap<SpawnGroup> object2IntMap = info.getGroupToCount();
                int _snowman15 = info.getSpawningChunkCount();
                _snowman10.add("SC: " + _snowman15 + ", " + Stream.of(SpawnGroup.values()).map(spawnGroup -> Character.toUpperCase(spawnGroup.getName().charAt(0)) + ": " + object2IntMap.getInt(spawnGroup)).collect(Collectors.joining(", ")));
            } else {
                _snowman10.add("SC: N/A");
            }
        }
        if ((_snowman = this.client.gameRenderer.getShader()) != null) {
            _snowman10.add("Shader: " + _snowman.getName());
        }
        _snowman10.add(this.client.getSoundManager().getDebugString() + String.format(" (Mood %d%%)", Math.round(this.client.player.getMoodPercentage() * 100.0f)));
        return _snowman10;
    }

    @Nullable
    private ServerWorld getServerWorld() {
        IntegratedServer integratedServer = this.client.getServer();
        if (integratedServer != null) {
            return integratedServer.getWorld(this.client.world.getRegistryKey());
        }
        return null;
    }

    @Nullable
    private String getServerWorldDebugString() {
        ServerWorld serverWorld = this.getServerWorld();
        if (serverWorld != null) {
            return serverWorld.getDebugString();
        }
        return null;
    }

    private World getWorld() {
        return (World)DataFixUtils.orElse(Optional.ofNullable(this.client.getServer()).flatMap(integratedServer -> Optional.ofNullable(integratedServer.getWorld(this.client.world.getRegistryKey()))), (Object)this.client.world);
    }

    @Nullable
    private WorldChunk getChunk() {
        if (this.chunkFuture == null) {
            ServerWorld serverWorld = this.getServerWorld();
            if (serverWorld != null) {
                this.chunkFuture = serverWorld.getChunkManager().getChunkFutureSyncOnMainThread(this.pos.x, this.pos.z, ChunkStatus.FULL, false).thenApply(either -> (WorldChunk)either.map(chunk -> (WorldChunk)chunk, unloaded -> null));
            }
            if (this.chunkFuture == null) {
                this.chunkFuture = CompletableFuture.completedFuture(this.getClientChunk());
            }
        }
        return this.chunkFuture.getNow(null);
    }

    private WorldChunk getClientChunk() {
        if (this.chunk == null) {
            this.chunk = this.client.world.getChunk(this.pos.x, this.pos.z);
        }
        return this.chunk;
    }

    protected List<String> getRightText() {
        long l = Runtime.getRuntime().maxMemory();
        long l2 = Runtime.getRuntime().totalMemory();
        _snowman = Runtime.getRuntime().freeMemory();
        _snowman = l2 - _snowman;
        ArrayList _snowman2 = Lists.newArrayList((Object[])new String[]{String.format("Java: %s %dbit", System.getProperty("java.version"), this.client.is64Bit() ? 64 : 32), String.format("Mem: % 2d%% %03d/%03dMB", _snowman * 100L / l, DebugHud.toMiB(_snowman), DebugHud.toMiB(l)), String.format("Allocated: % 2d%% %03dMB", l2 * 100L / l, DebugHud.toMiB(l2)), "", String.format("CPU: %s", GlDebugInfo.getCpuInfo()), "", String.format("Display: %dx%d (%s)", MinecraftClient.getInstance().getWindow().getFramebufferWidth(), MinecraftClient.getInstance().getWindow().getFramebufferHeight(), GlDebugInfo.getVendor()), GlDebugInfo.getRenderer(), GlDebugInfo.getVersion()});
        if (this.client.hasReducedDebugInfo()) {
            return _snowman2;
        }
        if (this.blockHit.getType() == HitResult.Type.BLOCK) {
            BlockPos object = ((BlockHitResult)this.blockHit).getBlockPos();
            BlockState _snowman3 = this.client.world.getBlockState(object);
            _snowman2.add("");
            _snowman2.add((Object)((Object)Formatting.UNDERLINE) + "Targeted Block: " + object.getX() + ", " + object.getY() + ", " + object.getZ());
            _snowman2.add(String.valueOf(Registry.BLOCK.getId(_snowman3.getBlock())));
            for (Map.Entry entry : _snowman3.getEntries().entrySet()) {
                _snowman2.add(this.propertyToString(entry));
            }
            for (Identifier identifier : this.client.getNetworkHandler().getTagManager().getBlocks().getTagsFor(_snowman3.getBlock())) {
                _snowman2.add("#" + identifier);
            }
        }
        if (this.fluidHit.getType() == HitResult.Type.BLOCK) {
            Object object = ((BlockHitResult)this.fluidHit).getBlockPos();
            FluidState _snowman4 = this.client.world.getFluidState((BlockPos)object);
            _snowman2.add("");
            _snowman2.add((Object)((Object)Formatting.UNDERLINE) + "Targeted Fluid: " + ((Vec3i)object).getX() + ", " + ((Vec3i)object).getY() + ", " + ((Vec3i)object).getZ());
            _snowman2.add(String.valueOf(Registry.FLUID.getId(_snowman4.getFluid())));
            for (Map.Entry entry : _snowman4.getEntries().entrySet()) {
                _snowman2.add(this.propertyToString(entry));
            }
            for (Identifier identifier : this.client.getNetworkHandler().getTagManager().getFluids().getTagsFor(_snowman4.getFluid())) {
                _snowman2.add("#" + identifier);
            }
        }
        if ((object = this.client.targetedEntity) != null) {
            _snowman2.add("");
            _snowman2.add((Object)((Object)Formatting.UNDERLINE) + "Targeted Entity");
            _snowman2.add(String.valueOf(Registry.ENTITY_TYPE.getId(((Entity)object).getType())));
        }
        return _snowman2;
    }

    private String propertyToString(Map.Entry<Property<?>, Comparable<?>> propEntry) {
        Property<?> property = propEntry.getKey();
        Comparable<?> _snowman2 = propEntry.getValue();
        String _snowman3 = Util.getValueAsString(property, _snowman2);
        if (Boolean.TRUE.equals(_snowman2)) {
            _snowman3 = (Object)((Object)Formatting.GREEN) + _snowman3;
        } else if (Boolean.FALSE.equals(_snowman2)) {
            _snowman3 = (Object)((Object)Formatting.RED) + _snowman3;
        }
        return property.getName() + ": " + _snowman3;
    }

    private void drawMetricsData(MatrixStack matrices, MetricsData metricsData, int x, int width, boolean showFps) {
        RenderSystem.disableDepthTest();
        int n = metricsData.getStartIndex();
        _snowman = metricsData.getCurrentIndex();
        long[] _snowman2 = metricsData.getSamples();
        _snowman = n;
        _snowman = x;
        _snowman = Math.max(0, _snowman2.length - width);
        _snowman = _snowman2.length - _snowman;
        _snowman = metricsData.wrapIndex(_snowman + _snowman);
        long _snowman3 = 0L;
        _snowman = Integer.MAX_VALUE;
        _snowman = Integer.MIN_VALUE;
        for (_snowman = 0; _snowman < _snowman; ++_snowman) {
            _snowman = (int)(_snowman2[metricsData.wrapIndex(_snowman + _snowman)] / 1000000L);
            _snowman = Math.min(_snowman, _snowman);
            _snowman = Math.max(_snowman, _snowman);
            _snowman3 += (long)_snowman;
        }
        _snowman = this.client.getWindow().getScaledHeight();
        DebugHud.fill(matrices, x, _snowman - 60, x + _snowman, _snowman, -1873784752);
        BufferBuilder _snowman4 = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        _snowman4.begin(7, VertexFormats.POSITION_COLOR);
        Matrix4f _snowman5 = AffineTransformation.identity().getMatrix();
        while (_snowman != _snowman) {
            _snowman = metricsData.method_15248(_snowman2[_snowman], showFps ? 30 : 60, showFps ? 60 : 20);
            _snowman = showFps ? 100 : 60;
            _snowman = this.getMetricsLineColor(MathHelper.clamp(_snowman, 0, _snowman), 0, _snowman / 2, _snowman);
            _snowman = _snowman >> 24 & 0xFF;
            _snowman = _snowman >> 16 & 0xFF;
            _snowman = _snowman >> 8 & 0xFF;
            _snowman = _snowman & 0xFF;
            _snowman4.vertex(_snowman5, _snowman + 1, _snowman, 0.0f).color(_snowman, _snowman, _snowman, _snowman).next();
            _snowman4.vertex(_snowman5, _snowman + 1, _snowman - _snowman + 1, 0.0f).color(_snowman, _snowman, _snowman, _snowman).next();
            _snowman4.vertex(_snowman5, _snowman, _snowman - _snowman + 1, 0.0f).color(_snowman, _snowman, _snowman, _snowman).next();
            _snowman4.vertex(_snowman5, _snowman, _snowman, 0.0f).color(_snowman, _snowman, _snowman, _snowman).next();
            ++_snowman;
            _snowman = metricsData.wrapIndex(_snowman + 1);
        }
        _snowman4.end();
        BufferRenderer.draw(_snowman4);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        if (showFps) {
            DebugHud.fill(matrices, x + 1, _snowman - 30 + 1, x + 14, _snowman - 30 + 10, -1873784752);
            this.fontRenderer.draw(matrices, "60 FPS", (float)(x + 2), (float)(_snowman - 30 + 2), 0xE0E0E0);
            this.drawHorizontalLine(matrices, x, x + _snowman - 1, _snowman - 30, -1);
            DebugHud.fill(matrices, x + 1, _snowman - 60 + 1, x + 14, _snowman - 60 + 10, -1873784752);
            this.fontRenderer.draw(matrices, "30 FPS", (float)(x + 2), (float)(_snowman - 60 + 2), 0xE0E0E0);
            this.drawHorizontalLine(matrices, x, x + _snowman - 1, _snowman - 60, -1);
        } else {
            DebugHud.fill(matrices, x + 1, _snowman - 60 + 1, x + 14, _snowman - 60 + 10, -1873784752);
            this.fontRenderer.draw(matrices, "20 TPS", (float)(x + 2), (float)(_snowman - 60 + 2), 0xE0E0E0);
            this.drawHorizontalLine(matrices, x, x + _snowman - 1, _snowman - 60, -1);
        }
        this.drawHorizontalLine(matrices, x, x + _snowman - 1, _snowman - 1, -1);
        this.drawVerticalLine(matrices, x, _snowman - 60, _snowman, -1);
        this.drawVerticalLine(matrices, x + _snowman - 1, _snowman - 60, _snowman, -1);
        if (showFps && this.client.options.maxFps > 0 && this.client.options.maxFps <= 250) {
            this.drawHorizontalLine(matrices, x, x + _snowman - 1, _snowman - 1 - (int)(1800.0 / (double)this.client.options.maxFps), -16711681);
        }
        String _snowman6 = _snowman + " ms min";
        String _snowman7 = _snowman3 / (long)_snowman + " ms avg";
        String _snowman8 = _snowman + " ms max";
        this.fontRenderer.drawWithShadow(matrices, _snowman6, (float)(x + 2), (float)(_snowman - 60 - this.fontRenderer.fontHeight), 0xE0E0E0);
        this.fontRenderer.drawWithShadow(matrices, _snowman7, (float)(x + _snowman / 2 - this.fontRenderer.getWidth(_snowman7) / 2), (float)(_snowman - 60 - this.fontRenderer.fontHeight), 0xE0E0E0);
        this.fontRenderer.drawWithShadow(matrices, _snowman8, (float)(x + _snowman - this.fontRenderer.getWidth(_snowman8)), (float)(_snowman - 60 - this.fontRenderer.fontHeight), 0xE0E0E0);
        RenderSystem.enableDepthTest();
    }

    private int getMetricsLineColor(int value, int greenValue, int yellowValue, int redValue) {
        if (value < yellowValue) {
            return this.interpolateColor(-16711936, -256, (float)value / (float)yellowValue);
        }
        return this.interpolateColor(-256, -65536, (float)(value - yellowValue) / (float)(redValue - yellowValue));
    }

    private int interpolateColor(int color1, int color2, float dt) {
        int n = color1 >> 24 & 0xFF;
        _snowman = color1 >> 16 & 0xFF;
        _snowman = color1 >> 8 & 0xFF;
        _snowman = color1 & 0xFF;
        _snowman = color2 >> 24 & 0xFF;
        _snowman = color2 >> 16 & 0xFF;
        _snowman = color2 >> 8 & 0xFF;
        _snowman = color2 & 0xFF;
        _snowman = MathHelper.clamp((int)MathHelper.lerp(dt, n, _snowman), 0, 255);
        _snowman = MathHelper.clamp((int)MathHelper.lerp(dt, _snowman, _snowman), 0, 255);
        _snowman = MathHelper.clamp((int)MathHelper.lerp(dt, _snowman, _snowman), 0, 255);
        _snowman = MathHelper.clamp((int)MathHelper.lerp(dt, _snowman, _snowman), 0, 255);
        return _snowman << 24 | _snowman << 16 | _snowman << 8 | _snowman;
    }

    private static long toMiB(long bytes) {
        return bytes / 1024L / 1024L;
    }
}

