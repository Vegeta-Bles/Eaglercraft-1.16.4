package net.minecraft.client.gui.hud;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.blaze3d.platform.GlDebugInfo;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.DataFixUtils;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.block.BlockState;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.ShaderEffect;
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
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LightType;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.light.LightingProvider;

public class DebugHud extends DrawableHelper {
   private static final Map<Heightmap.Type, String> HEIGHT_MAP_TYPES = Util.make(new EnumMap<>(Heightmap.Type.class), _snowman -> {
      _snowman.put(Heightmap.Type.WORLD_SURFACE_WG, "SW");
      _snowman.put(Heightmap.Type.WORLD_SURFACE, "S");
      _snowman.put(Heightmap.Type.OCEAN_FLOOR_WG, "OW");
      _snowman.put(Heightmap.Type.OCEAN_FLOOR, "O");
      _snowman.put(Heightmap.Type.MOTION_BLOCKING, "M");
      _snowman.put(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, "ML");
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
      Entity _snowman = this.client.getCameraEntity();
      this.blockHit = _snowman.raycast(20.0, 0.0F, false);
      this.fluidHit = _snowman.raycast(20.0, 0.0F, true);
      this.renderLeftText(matrices);
      this.renderRightText(matrices);
      RenderSystem.popMatrix();
      if (this.client.options.debugTpsEnabled) {
         int _snowmanx = this.client.getWindow().getScaledWidth();
         this.drawMetricsData(matrices, this.client.getMetricsData(), 0, _snowmanx / 2, true);
         IntegratedServer _snowmanxx = this.client.getServer();
         if (_snowmanxx != null) {
            this.drawMetricsData(matrices, _snowmanxx.getMetricsData(), _snowmanx - Math.min(_snowmanx / 2, 240), _snowmanx / 2, false);
         }
      }

      this.client.getProfiler().pop();
   }

   protected void renderLeftText(MatrixStack matrices) {
      List<String> _snowman = this.getLeftText();
      _snowman.add("");
      boolean _snowmanx = this.client.getServer() != null;
      _snowman.add(
         "Debug: Pie [shift]: "
            + (this.client.options.debugProfilerEnabled ? "visible" : "hidden")
            + (_snowmanx ? " FPS + TPS" : " FPS")
            + " [alt]: "
            + (this.client.options.debugTpsEnabled ? "visible" : "hidden")
      );
      _snowman.add("For help: press F3 + Q");

      for (int _snowmanxx = 0; _snowmanxx < _snowman.size(); _snowmanxx++) {
         String _snowmanxxx = _snowman.get(_snowmanxx);
         if (!Strings.isNullOrEmpty(_snowmanxxx)) {
            int _snowmanxxxx = 9;
            int _snowmanxxxxx = this.fontRenderer.getWidth(_snowmanxxx);
            int _snowmanxxxxxx = 2;
            int _snowmanxxxxxxx = 2 + _snowmanxxxx * _snowmanxx;
            fill(matrices, 1, _snowmanxxxxxxx - 1, 2 + _snowmanxxxxx + 1, _snowmanxxxxxxx + _snowmanxxxx - 1, -1873784752);
            this.fontRenderer.draw(matrices, _snowmanxxx, 2.0F, (float)_snowmanxxxxxxx, 14737632);
         }
      }
   }

   protected void renderRightText(MatrixStack matrices) {
      List<String> _snowman = this.getRightText();

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         String _snowmanxx = _snowman.get(_snowmanx);
         if (!Strings.isNullOrEmpty(_snowmanxx)) {
            int _snowmanxxx = 9;
            int _snowmanxxxx = this.fontRenderer.getWidth(_snowmanxx);
            int _snowmanxxxxx = this.client.getWindow().getScaledWidth() - 2 - _snowmanxxxx;
            int _snowmanxxxxxx = 2 + _snowmanxxx * _snowmanx;
            fill(matrices, _snowmanxxxxx - 1, _snowmanxxxxxx - 1, _snowmanxxxxx + _snowmanxxxx + 1, _snowmanxxxxxx + _snowmanxxx - 1, -1873784752);
            this.fontRenderer.draw(matrices, _snowmanxx, (float)_snowmanxxxxx, (float)_snowmanxxxxxx, 14737632);
         }
      }
   }

   protected List<String> getLeftText() {
      IntegratedServer _snowman = this.client.getServer();
      ClientConnection _snowmanx = this.client.getNetworkHandler().getConnection();
      float _snowmanxx = _snowmanx.getAveragePacketsSent();
      float _snowmanxxx = _snowmanx.getAveragePacketsReceived();
      String _snowmanxxxx;
      if (_snowman != null) {
         _snowmanxxxx = String.format("Integrated server @ %.0f ms ticks, %.0f tx, %.0f rx", _snowman.getTickTime(), _snowmanxx, _snowmanxxx);
      } else {
         _snowmanxxxx = String.format("\"%s\" server, %.0f tx, %.0f rx", this.client.player.getServerBrand(), _snowmanxx, _snowmanxxx);
      }

      BlockPos _snowmanxxxxx = this.client.getCameraEntity().getBlockPos();
      if (this.client.hasReducedDebugInfo()) {
         return Lists.newArrayList(
            new String[]{
               "Minecraft "
                  + SharedConstants.getGameVersion().getName()
                  + " ("
                  + this.client.getGameVersion()
                  + "/"
                  + ClientBrandRetriever.getClientModName()
                  + ")",
               this.client.fpsDebugString,
               _snowmanxxxx,
               this.client.worldRenderer.getChunksDebugString(),
               this.client.worldRenderer.getEntitiesDebugString(),
               "P: " + this.client.particleManager.getDebugString() + ". T: " + this.client.world.getRegularEntityCount(),
               this.client.world.getDebugString(),
               "",
               String.format("Chunk-relative: %d %d %d", _snowmanxxxxx.getX() & 15, _snowmanxxxxx.getY() & 15, _snowmanxxxxx.getZ() & 15)
            }
         );
      } else {
         Entity _snowmanxxxxxx = this.client.getCameraEntity();
         Direction _snowmanxxxxxxx = _snowmanxxxxxx.getHorizontalFacing();
         String _snowmanxxxxxxxx;
         switch (_snowmanxxxxxxx) {
            case NORTH:
               _snowmanxxxxxxxx = "Towards negative Z";
               break;
            case SOUTH:
               _snowmanxxxxxxxx = "Towards positive Z";
               break;
            case WEST:
               _snowmanxxxxxxxx = "Towards negative X";
               break;
            case EAST:
               _snowmanxxxxxxxx = "Towards positive X";
               break;
            default:
               _snowmanxxxxxxxx = "Invalid";
         }

         ChunkPos _snowmanxxxxxx = new ChunkPos(_snowmanxxxxx);
         if (!Objects.equals(this.pos, _snowmanxxxxxx)) {
            this.pos = _snowmanxxxxxx;
            this.resetChunk();
         }

         World _snowmanxxxxxxx = this.getWorld();
         LongSet _snowmanxxxxxxxx = (LongSet)(_snowmanxxxxxxx instanceof ServerWorld ? ((ServerWorld)_snowmanxxxxxxx).getForcedChunks() : LongSets.EMPTY_SET);
         List<String> _snowmanxxxxxxxxx = Lists.newArrayList(
            new String[]{
               "Minecraft "
                  + SharedConstants.getGameVersion().getName()
                  + " ("
                  + this.client.getGameVersion()
                  + "/"
                  + ClientBrandRetriever.getClientModName()
                  + ("release".equalsIgnoreCase(this.client.getVersionType()) ? "" : "/" + this.client.getVersionType())
                  + ")",
               this.client.fpsDebugString,
               _snowmanxxxx,
               this.client.worldRenderer.getChunksDebugString(),
               this.client.worldRenderer.getEntitiesDebugString(),
               "P: " + this.client.particleManager.getDebugString() + ". T: " + this.client.world.getRegularEntityCount(),
               this.client.world.getDebugString()
            }
         );
         String _snowmanxxxxxxxxxx = this.getServerWorldDebugString();
         if (_snowmanxxxxxxxxxx != null) {
            _snowmanxxxxxxxxx.add(_snowmanxxxxxxxxxx);
         }

         _snowmanxxxxxxxxx.add(this.client.world.getRegistryKey().getValue() + " FC: " + _snowmanxxxxxxxx.size());
         _snowmanxxxxxxxxx.add("");
         _snowmanxxxxxxxxx.add(
            String.format(
               Locale.ROOT,
               "XYZ: %.3f / %.5f / %.3f",
               this.client.getCameraEntity().getX(),
               this.client.getCameraEntity().getY(),
               this.client.getCameraEntity().getZ()
            )
         );
         _snowmanxxxxxxxxx.add(String.format("Block: %d %d %d", _snowmanxxxxx.getX(), _snowmanxxxxx.getY(), _snowmanxxxxx.getZ()));
         _snowmanxxxxxxxxx.add(
            String.format(
               "Chunk: %d %d %d in %d %d %d",
               _snowmanxxxxx.getX() & 15,
               _snowmanxxxxx.getY() & 15,
               _snowmanxxxxx.getZ() & 15,
               _snowmanxxxxx.getX() >> 4,
               _snowmanxxxxx.getY() >> 4,
               _snowmanxxxxx.getZ() >> 4
            )
         );
         _snowmanxxxxxxxxx.add(
            String.format(
               Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", _snowmanxxxxxxx, _snowmanxxxxxxxx, MathHelper.wrapDegrees(_snowmanxxxxxx.yaw), MathHelper.wrapDegrees(_snowmanxxxxxx.pitch)
            )
         );
         if (this.client.world != null) {
            if (this.client.world.isChunkLoaded(_snowmanxxxxx)) {
               WorldChunk _snowmanxxxxxxxxxxx = this.getClientChunk();
               if (_snowmanxxxxxxxxxxx.isEmpty()) {
                  _snowmanxxxxxxxxx.add("Waiting for chunk...");
               } else {
                  int _snowmanxxxxxxxxxxxx = this.client.world.getChunkManager().getLightingProvider().getLight(_snowmanxxxxx, 0);
                  int _snowmanxxxxxxxxxxxxx = this.client.world.getLightLevel(LightType.SKY, _snowmanxxxxx);
                  int _snowmanxxxxxxxxxxxxxx = this.client.world.getLightLevel(LightType.BLOCK, _snowmanxxxxx);
                  _snowmanxxxxxxxxx.add("Client Light: " + _snowmanxxxxxxxxxxxx + " (" + _snowmanxxxxxxxxxxxxx + " sky, " + _snowmanxxxxxxxxxxxxxx + " block)");
                  WorldChunk _snowmanxxxxxxxxxxxxxxx = this.getChunk();
                  if (_snowmanxxxxxxxxxxxxxxx != null) {
                     LightingProvider _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxx.getChunkManager().getLightingProvider();
                     _snowmanxxxxxxxxx.add(
                        "Server Light: ("
                           + _snowmanxxxxxxxxxxxxxxxx.get(LightType.SKY).getLightLevel(_snowmanxxxxx)
                           + " sky, "
                           + _snowmanxxxxxxxxxxxxxxxx.get(LightType.BLOCK).getLightLevel(_snowmanxxxxx)
                           + " block)"
                     );
                  } else {
                     _snowmanxxxxxxxxx.add("Server Light: (?? sky, ?? block)");
                  }

                  StringBuilder _snowmanxxxxxxxxxxxxxxxx = new StringBuilder("CH");

                  for (Heightmap.Type _snowmanxxxxxxxxxxxxxxxxx : Heightmap.Type.values()) {
                     if (_snowmanxxxxxxxxxxxxxxxxx.shouldSendToClient()) {
                        _snowmanxxxxxxxxxxxxxxxx.append(" ")
                           .append(HEIGHT_MAP_TYPES.get(_snowmanxxxxxxxxxxxxxxxxx))
                           .append(": ")
                           .append(_snowmanxxxxxxxxxxx.sampleHeightmap(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxx.getX(), _snowmanxxxxx.getZ()));
                     }
                  }

                  _snowmanxxxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxx.toString());
                  _snowmanxxxxxxxxxxxxxxxx.setLength(0);
                  _snowmanxxxxxxxxxxxxxxxx.append("SH");

                  for (Heightmap.Type _snowmanxxxxxxxxxxxxxxxxxx : Heightmap.Type.values()) {
                     if (_snowmanxxxxxxxxxxxxxxxxxx.isStoredServerSide()) {
                        _snowmanxxxxxxxxxxxxxxxx.append(" ").append(HEIGHT_MAP_TYPES.get(_snowmanxxxxxxxxxxxxxxxxxx)).append(": ");
                        if (_snowmanxxxxxxxxxxxxxxx != null) {
                           _snowmanxxxxxxxxxxxxxxxx.append(_snowmanxxxxxxxxxxxxxxx.sampleHeightmap(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxx.getX(), _snowmanxxxxx.getZ()));
                        } else {
                           _snowmanxxxxxxxxxxxxxxxx.append("??");
                        }
                     }
                  }

                  _snowmanxxxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxx.toString());
                  if (_snowmanxxxxx.getY() >= 0 && _snowmanxxxxx.getY() < 256) {
                     _snowmanxxxxxxxxx.add("Biome: " + this.client.world.getRegistryManager().get(Registry.BIOME_KEY).getId(this.client.world.getBiome(_snowmanxxxxx)));
                     long _snowmanxxxxxxxxxxxxxxxxxxx = 0L;
                     float _snowmanxxxxxxxxxxxxxxxxxxxx = 0.0F;
                     if (_snowmanxxxxxxxxxxxxxxx != null) {
                        _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx.getMoonSize();
                        _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.getInhabitedTime();
                     }

                     LocalDifficulty _snowmanxxxxxxxxxxxxxxxxxxxxx = new LocalDifficulty(
                        _snowmanxxxxxxx.getDifficulty(), _snowmanxxxxxxx.getTimeOfDay(), _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx
                     );
                     _snowmanxxxxxxxxx.add(
                        String.format(
                           Locale.ROOT,
                           "Local Difficulty: %.2f // %.2f (Day %d)",
                           _snowmanxxxxxxxxxxxxxxxxxxxxx.getLocalDifficulty(),
                           _snowmanxxxxxxxxxxxxxxxxxxxxx.getClampedLocalDifficulty(),
                           this.client.world.getTimeOfDay() / 24000L
                        )
                     );
                  }
               }
            } else {
               _snowmanxxxxxxxxx.add("Outside of world...");
            }
         } else {
            _snowmanxxxxxxxxx.add("Outside of world...");
         }

         ServerWorld _snowmanxxxxxxxxxxx = this.getServerWorld();
         if (_snowmanxxxxxxxxxxx != null) {
            SpawnHelper.Info _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getChunkManager().getSpawnInfo();
            if (_snowmanxxxxxxxxxxxxxxxx != null) {
               Object2IntMap<SpawnGroup> _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.getGroupToCount();
               int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.getSpawningChunkCount();
               _snowmanxxxxxxxxx.add(
                  "SC: "
                     + _snowmanxxxxxxxxxxxxxxxxxxxx
                     + ", "
                     + Stream.of(SpawnGroup.values())
                        .map(
                           _snowmanxxxxxxxxxxxxxxxxxxxxx -> Character.toUpperCase(_snowmanxxxxxxxxxxxxxxxxxxxxx.getName().charAt(0))
                                 + ": "
                                 + _snowman.getInt(_snowmanxxxxxxxxxxxxxxxxxxxxx)
                        )
                        .collect(Collectors.joining(", "))
               );
            } else {
               _snowmanxxxxxxxxx.add("SC: N/A");
            }
         }

         ShaderEffect _snowmanxxxxxxxxxxxxxxxx = this.client.gameRenderer.getShader();
         if (_snowmanxxxxxxxxxxxxxxxx != null) {
            _snowmanxxxxxxxxx.add("Shader: " + _snowmanxxxxxxxxxxxxxxxx.getName());
         }

         _snowmanxxxxxxxxx.add(
            this.client.getSoundManager().getDebugString() + String.format(" (Mood %d%%)", Math.round(this.client.player.getMoodPercentage() * 100.0F))
         );
         return _snowmanxxxxxxxxx;
      }
   }

   @Nullable
   private ServerWorld getServerWorld() {
      IntegratedServer _snowman = this.client.getServer();
      return _snowman != null ? _snowman.getWorld(this.client.world.getRegistryKey()) : null;
   }

   @Nullable
   private String getServerWorldDebugString() {
      ServerWorld _snowman = this.getServerWorld();
      return _snowman != null ? _snowman.getDebugString() : null;
   }

   private World getWorld() {
      return (World)DataFixUtils.orElse(
         Optional.ofNullable(this.client.getServer()).flatMap(_snowman -> Optional.ofNullable(_snowman.getWorld(this.client.world.getRegistryKey()))), this.client.world
      );
   }

   @Nullable
   private WorldChunk getChunk() {
      if (this.chunkFuture == null) {
         ServerWorld _snowman = this.getServerWorld();
         if (_snowman != null) {
            this.chunkFuture = _snowman.getChunkManager()
               .getChunkFutureSyncOnMainThread(this.pos.x, this.pos.z, ChunkStatus.FULL, false)
               .thenApply(_snowmanx -> (WorldChunk)_snowmanx.map(_snowmanxx -> (WorldChunk)_snowmanxx, _snowmanxx -> null));
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
      long _snowman = Runtime.getRuntime().maxMemory();
      long _snowmanx = Runtime.getRuntime().totalMemory();
      long _snowmanxx = Runtime.getRuntime().freeMemory();
      long _snowmanxxx = _snowmanx - _snowmanxx;
      List<String> _snowmanxxxx = Lists.newArrayList(
         new String[]{
            String.format("Java: %s %dbit", System.getProperty("java.version"), this.client.is64Bit() ? 64 : 32),
            String.format("Mem: % 2d%% %03d/%03dMB", _snowmanxxx * 100L / _snowman, toMiB(_snowmanxxx), toMiB(_snowman)),
            String.format("Allocated: % 2d%% %03dMB", _snowmanx * 100L / _snowman, toMiB(_snowmanx)),
            "",
            String.format("CPU: %s", GlDebugInfo.getCpuInfo()),
            "",
            String.format(
               "Display: %dx%d (%s)",
               MinecraftClient.getInstance().getWindow().getFramebufferWidth(),
               MinecraftClient.getInstance().getWindow().getFramebufferHeight(),
               GlDebugInfo.getVendor()
            ),
            GlDebugInfo.getRenderer(),
            GlDebugInfo.getVersion()
         }
      );
      if (this.client.hasReducedDebugInfo()) {
         return _snowmanxxxx;
      } else {
         if (this.blockHit.getType() == HitResult.Type.BLOCK) {
            BlockPos _snowmanxxxxx = ((BlockHitResult)this.blockHit).getBlockPos();
            BlockState _snowmanxxxxxx = this.client.world.getBlockState(_snowmanxxxxx);
            _snowmanxxxx.add("");
            _snowmanxxxx.add(Formatting.UNDERLINE + "Targeted Block: " + _snowmanxxxxx.getX() + ", " + _snowmanxxxxx.getY() + ", " + _snowmanxxxxx.getZ());
            _snowmanxxxx.add(String.valueOf(Registry.BLOCK.getId(_snowmanxxxxxx.getBlock())));
            UnmodifiableIterator var12 = _snowmanxxxxxx.getEntries().entrySet().iterator();

            while (var12.hasNext()) {
               Entry<Property<?>, Comparable<?>> _snowmanxxxxxxx = (Entry<Property<?>, Comparable<?>>)var12.next();
               _snowmanxxxx.add(this.propertyToString(_snowmanxxxxxxx));
            }

            for (Identifier _snowmanxxxxxxx : this.client.getNetworkHandler().getTagManager().getBlocks().getTagsFor(_snowmanxxxxxx.getBlock())) {
               _snowmanxxxx.add("#" + _snowmanxxxxxxx);
            }
         }

         if (this.fluidHit.getType() == HitResult.Type.BLOCK) {
            BlockPos _snowmanxxxxx = ((BlockHitResult)this.fluidHit).getBlockPos();
            FluidState _snowmanxxxxxx = this.client.world.getFluidState(_snowmanxxxxx);
            _snowmanxxxx.add("");
            _snowmanxxxx.add(Formatting.UNDERLINE + "Targeted Fluid: " + _snowmanxxxxx.getX() + ", " + _snowmanxxxxx.getY() + ", " + _snowmanxxxxx.getZ());
            _snowmanxxxx.add(String.valueOf(Registry.FLUID.getId(_snowmanxxxxxx.getFluid())));
            UnmodifiableIterator var18 = _snowmanxxxxxx.getEntries().entrySet().iterator();

            while (var18.hasNext()) {
               Entry<Property<?>, Comparable<?>> _snowmanxxxxxxx = (Entry<Property<?>, Comparable<?>>)var18.next();
               _snowmanxxxx.add(this.propertyToString(_snowmanxxxxxxx));
            }

            for (Identifier _snowmanxxxxxxx : this.client.getNetworkHandler().getTagManager().getFluids().getTagsFor(_snowmanxxxxxx.getFluid())) {
               _snowmanxxxx.add("#" + _snowmanxxxxxxx);
            }
         }

         Entity _snowmanxxxxx = this.client.targetedEntity;
         if (_snowmanxxxxx != null) {
            _snowmanxxxx.add("");
            _snowmanxxxx.add(Formatting.UNDERLINE + "Targeted Entity");
            _snowmanxxxx.add(String.valueOf(Registry.ENTITY_TYPE.getId(_snowmanxxxxx.getType())));
         }

         return _snowmanxxxx;
      }
   }

   private String propertyToString(Entry<Property<?>, Comparable<?>> propEntry) {
      Property<?> _snowman = propEntry.getKey();
      Comparable<?> _snowmanx = propEntry.getValue();
      String _snowmanxx = Util.getValueAsString(_snowman, _snowmanx);
      if (Boolean.TRUE.equals(_snowmanx)) {
         _snowmanxx = Formatting.GREEN + _snowmanxx;
      } else if (Boolean.FALSE.equals(_snowmanx)) {
         _snowmanxx = Formatting.RED + _snowmanxx;
      }

      return _snowman.getName() + ": " + _snowmanxx;
   }

   private void drawMetricsData(MatrixStack matrices, MetricsData metricsData, int x, int width, boolean showFps) {
      RenderSystem.disableDepthTest();
      int _snowman = metricsData.getStartIndex();
      int _snowmanx = metricsData.getCurrentIndex();
      long[] _snowmanxx = metricsData.getSamples();
      int _snowmanxxx = x;
      int _snowmanxxxx = Math.max(0, _snowmanxx.length - width);
      int _snowmanxxxxx = _snowmanxx.length - _snowmanxxxx;
      int var9 = metricsData.wrapIndex(_snowman + _snowmanxxxx);
      long _snowmanxxxxxx = 0L;
      int _snowmanxxxxxxx = Integer.MAX_VALUE;
      int _snowmanxxxxxxxx = Integer.MIN_VALUE;

      for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxxx++) {
         int _snowmanxxxxxxxxxx = (int)(_snowmanxx[metricsData.wrapIndex(var9 + _snowmanxxxxxxxxx)] / 1000000L);
         _snowmanxxxxxxx = Math.min(_snowmanxxxxxxx, _snowmanxxxxxxxxxx);
         _snowmanxxxxxxxx = Math.max(_snowmanxxxxxxxx, _snowmanxxxxxxxxxx);
         _snowmanxxxxxx += (long)_snowmanxxxxxxxxxx;
      }

      int _snowmanxxxxxxxxx = this.client.getWindow().getScaledHeight();
      fill(matrices, x, _snowmanxxxxxxxxx - 60, x + _snowmanxxxxx, _snowmanxxxxxxxxx, -1873784752);
      BufferBuilder _snowmanxxxxxxxxxx = Tessellator.getInstance().getBuffer();
      RenderSystem.enableBlend();
      RenderSystem.disableTexture();
      RenderSystem.defaultBlendFunc();
      _snowmanxxxxxxxxxx.begin(7, VertexFormats.POSITION_COLOR);

      for (Matrix4f _snowmanxxxxxxxxxxx = AffineTransformation.identity().getMatrix(); var9 != _snowmanx; var9 = metricsData.wrapIndex(var9 + 1)) {
         int _snowmanxxxxxxxxxxxx = metricsData.method_15248(_snowmanxx[var9], showFps ? 30 : 60, showFps ? 60 : 20);
         int _snowmanxxxxxxxxxxxxx = showFps ? 100 : 60;
         int _snowmanxxxxxxxxxxxxxx = this.getMetricsLineColor(MathHelper.clamp(_snowmanxxxxxxxxxxxx, 0, _snowmanxxxxxxxxxxxxx), 0, _snowmanxxxxxxxxxxxxx / 2, _snowmanxxxxxxxxxxxxx);
         int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx >> 24 & 0xFF;
         int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx >> 16 & 0xFF;
         int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx >> 8 & 0xFF;
         int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx & 0xFF;
         _snowmanxxxxxxxxxx.vertex(_snowmanxxxxxxxxxxx, (float)(_snowmanxxx + 1), (float)_snowmanxxxxxxxxx, 0.0F)
            .color(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)
            .next();
         _snowmanxxxxxxxxxx.vertex(_snowmanxxxxxxxxxxx, (float)(_snowmanxxx + 1), (float)(_snowmanxxxxxxxxx - _snowmanxxxxxxxxxxxx + 1), 0.0F)
            .color(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)
            .next();
         _snowmanxxxxxxxxxx.vertex(_snowmanxxxxxxxxxxx, (float)_snowmanxxx, (float)(_snowmanxxxxxxxxx - _snowmanxxxxxxxxxxxx + 1), 0.0F)
            .color(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)
            .next();
         _snowmanxxxxxxxxxx.vertex(_snowmanxxxxxxxxxxx, (float)_snowmanxxx, (float)_snowmanxxxxxxxxx, 0.0F)
            .color(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)
            .next();
         _snowmanxxx++;
      }

      _snowmanxxxxxxxxxx.end();
      BufferRenderer.draw(_snowmanxxxxxxxxxx);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      if (showFps) {
         fill(matrices, x + 1, _snowmanxxxxxxxxx - 30 + 1, x + 14, _snowmanxxxxxxxxx - 30 + 10, -1873784752);
         this.fontRenderer.draw(matrices, "60 FPS", (float)(x + 2), (float)(_snowmanxxxxxxxxx - 30 + 2), 14737632);
         this.drawHorizontalLine(matrices, x, x + _snowmanxxxxx - 1, _snowmanxxxxxxxxx - 30, -1);
         fill(matrices, x + 1, _snowmanxxxxxxxxx - 60 + 1, x + 14, _snowmanxxxxxxxxx - 60 + 10, -1873784752);
         this.fontRenderer.draw(matrices, "30 FPS", (float)(x + 2), (float)(_snowmanxxxxxxxxx - 60 + 2), 14737632);
         this.drawHorizontalLine(matrices, x, x + _snowmanxxxxx - 1, _snowmanxxxxxxxxx - 60, -1);
      } else {
         fill(matrices, x + 1, _snowmanxxxxxxxxx - 60 + 1, x + 14, _snowmanxxxxxxxxx - 60 + 10, -1873784752);
         this.fontRenderer.draw(matrices, "20 TPS", (float)(x + 2), (float)(_snowmanxxxxxxxxx - 60 + 2), 14737632);
         this.drawHorizontalLine(matrices, x, x + _snowmanxxxxx - 1, _snowmanxxxxxxxxx - 60, -1);
      }

      this.drawHorizontalLine(matrices, x, x + _snowmanxxxxx - 1, _snowmanxxxxxxxxx - 1, -1);
      this.drawVerticalLine(matrices, x, _snowmanxxxxxxxxx - 60, _snowmanxxxxxxxxx, -1);
      this.drawVerticalLine(matrices, x + _snowmanxxxxx - 1, _snowmanxxxxxxxxx - 60, _snowmanxxxxxxxxx, -1);
      if (showFps && this.client.options.maxFps > 0 && this.client.options.maxFps <= 250) {
         this.drawHorizontalLine(matrices, x, x + _snowmanxxxxx - 1, _snowmanxxxxxxxxx - 1 - (int)(1800.0 / (double)this.client.options.maxFps), -16711681);
      }

      String _snowmanxxxxxxxxxxx = _snowmanxxxxxxx + " ms min";
      String _snowmanxxxxxxxxxxxx = _snowmanxxxxxx / (long)_snowmanxxxxx + " ms avg";
      String _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxx + " ms max";
      this.fontRenderer.drawWithShadow(matrices, _snowmanxxxxxxxxxxx, (float)(x + 2), (float)(_snowmanxxxxxxxxx - 60 - 9), 14737632);
      this.fontRenderer
         .drawWithShadow(
            matrices, _snowmanxxxxxxxxxxxx, (float)(x + _snowmanxxxxx / 2 - this.fontRenderer.getWidth(_snowmanxxxxxxxxxxxx) / 2), (float)(_snowmanxxxxxxxxx - 60 - 9), 14737632
         );
      this.fontRenderer
         .drawWithShadow(matrices, _snowmanxxxxxxxxxxxxx, (float)(x + _snowmanxxxxx - this.fontRenderer.getWidth(_snowmanxxxxxxxxxxxxx)), (float)(_snowmanxxxxxxxxx - 60 - 9), 14737632);
      RenderSystem.enableDepthTest();
   }

   private int getMetricsLineColor(int value, int greenValue, int yellowValue, int redValue) {
      return value < yellowValue
         ? this.interpolateColor(-16711936, -256, (float)value / (float)yellowValue)
         : this.interpolateColor(-256, -65536, (float)(value - yellowValue) / (float)(redValue - yellowValue));
   }

   private int interpolateColor(int color1, int color2, float dt) {
      int _snowman = color1 >> 24 & 0xFF;
      int _snowmanx = color1 >> 16 & 0xFF;
      int _snowmanxx = color1 >> 8 & 0xFF;
      int _snowmanxxx = color1 & 0xFF;
      int _snowmanxxxx = color2 >> 24 & 0xFF;
      int _snowmanxxxxx = color2 >> 16 & 0xFF;
      int _snowmanxxxxxx = color2 >> 8 & 0xFF;
      int _snowmanxxxxxxx = color2 & 0xFF;
      int _snowmanxxxxxxxx = MathHelper.clamp((int)MathHelper.lerp(dt, (float)_snowman, (float)_snowmanxxxx), 0, 255);
      int _snowmanxxxxxxxxx = MathHelper.clamp((int)MathHelper.lerp(dt, (float)_snowmanx, (float)_snowmanxxxxx), 0, 255);
      int _snowmanxxxxxxxxxx = MathHelper.clamp((int)MathHelper.lerp(dt, (float)_snowmanxx, (float)_snowmanxxxxxx), 0, 255);
      int _snowmanxxxxxxxxxxx = MathHelper.clamp((int)MathHelper.lerp(dt, (float)_snowmanxxx, (float)_snowmanxxxxxxx), 0, 255);
      return _snowmanxxxxxxxx << 24 | _snowmanxxxxxxxxx << 16 | _snowmanxxxxxxxxxx << 8 | _snowmanxxxxxxxxxxx;
   }

   private static long toMiB(long bytes) {
      return bytes / 1024L / 1024L;
   }
}
