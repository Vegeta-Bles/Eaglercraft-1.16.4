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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class DebugHud extends DrawableHelper {
   private static final Map<Heightmap.Type, String> HEIGHT_MAP_TYPES = Util.make(new EnumMap<>(Heightmap.Type.class), enumMap -> {
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
      Entity lv = this.client.getCameraEntity();
      this.blockHit = lv.raycast(20.0, 0.0F, false);
      this.fluidHit = lv.raycast(20.0, 0.0F, true);
      this.renderLeftText(matrices);
      this.renderRightText(matrices);
      RenderSystem.popMatrix();
      if (this.client.options.debugTpsEnabled) {
         int i = this.client.getWindow().getScaledWidth();
         this.drawMetricsData(matrices, this.client.getMetricsData(), 0, i / 2, true);
         IntegratedServer lv2 = this.client.getServer();
         if (lv2 != null) {
            this.drawMetricsData(matrices, lv2.getMetricsData(), i - Math.min(i / 2, 240), i / 2, false);
         }
      }

      this.client.getProfiler().pop();
   }

   protected void renderLeftText(MatrixStack matrices) {
      List<String> list = this.getLeftText();
      list.add("");
      boolean bl = this.client.getServer() != null;
      list.add(
         "Debug: Pie [shift]: "
            + (this.client.options.debugProfilerEnabled ? "visible" : "hidden")
            + (bl ? " FPS + TPS" : " FPS")
            + " [alt]: "
            + (this.client.options.debugTpsEnabled ? "visible" : "hidden")
      );
      list.add("For help: press F3 + Q");

      for (int i = 0; i < list.size(); i++) {
         String string = list.get(i);
         if (!Strings.isNullOrEmpty(string)) {
            int j = 9;
            int k = this.fontRenderer.getWidth(string);
            int l = 2;
            int m = 2 + j * i;
            fill(matrices, 1, m - 1, 2 + k + 1, m + j - 1, -1873784752);
            this.fontRenderer.draw(matrices, string, 2.0F, (float)m, 14737632);
         }
      }
   }

   protected void renderRightText(MatrixStack matrices) {
      List<String> list = this.getRightText();

      for (int i = 0; i < list.size(); i++) {
         String string = list.get(i);
         if (!Strings.isNullOrEmpty(string)) {
            int j = 9;
            int k = this.fontRenderer.getWidth(string);
            int l = this.client.getWindow().getScaledWidth() - 2 - k;
            int m = 2 + j * i;
            fill(matrices, l - 1, m - 1, l + k + 1, m + j - 1, -1873784752);
            this.fontRenderer.draw(matrices, string, (float)l, (float)m, 14737632);
         }
      }
   }

   protected List<String> getLeftText() {
      IntegratedServer lv = this.client.getServer();
      ClientConnection lv2 = this.client.getNetworkHandler().getConnection();
      float f = lv2.getAveragePacketsSent();
      float g = lv2.getAveragePacketsReceived();
      String string;
      if (lv != null) {
         string = String.format("Integrated server @ %.0f ms ticks, %.0f tx, %.0f rx", lv.getTickTime(), f, g);
      } else {
         string = String.format("\"%s\" server, %.0f tx, %.0f rx", this.client.player.getServerBrand(), f, g);
      }

      BlockPos lv3 = this.client.getCameraEntity().getBlockPos();
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
               string,
               this.client.worldRenderer.getChunksDebugString(),
               this.client.worldRenderer.getEntitiesDebugString(),
               "P: " + this.client.particleManager.getDebugString() + ". T: " + this.client.world.getRegularEntityCount(),
               this.client.world.getDebugString(),
               "",
               String.format("Chunk-relative: %d %d %d", lv3.getX() & 15, lv3.getY() & 15, lv3.getZ() & 15)
            }
         );
      } else {
         Entity lv4 = this.client.getCameraEntity();
         Direction lv5 = lv4.getHorizontalFacing();
         String string3;
         switch (lv5) {
            case NORTH:
               string3 = "Towards negative Z";
               break;
            case SOUTH:
               string3 = "Towards positive Z";
               break;
            case WEST:
               string3 = "Towards negative X";
               break;
            case EAST:
               string3 = "Towards positive X";
               break;
            default:
               string3 = "Invalid";
         }

         ChunkPos lv6 = new ChunkPos(lv3);
         if (!Objects.equals(this.pos, lv6)) {
            this.pos = lv6;
            this.resetChunk();
         }

         World lv7 = this.getWorld();
         LongSet longSet = (LongSet)(lv7 instanceof ServerWorld ? ((ServerWorld)lv7).getForcedChunks() : LongSets.EMPTY_SET);
         List<String> list = Lists.newArrayList(
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
               string,
               this.client.worldRenderer.getChunksDebugString(),
               this.client.worldRenderer.getEntitiesDebugString(),
               "P: " + this.client.particleManager.getDebugString() + ". T: " + this.client.world.getRegularEntityCount(),
               this.client.world.getDebugString()
            }
         );
         String string8 = this.getServerWorldDebugString();
         if (string8 != null) {
            list.add(string8);
         }

         list.add(this.client.world.getRegistryKey().getValue() + " FC: " + longSet.size());
         list.add("");
         list.add(
            String.format(
               Locale.ROOT,
               "XYZ: %.3f / %.5f / %.3f",
               this.client.getCameraEntity().getX(),
               this.client.getCameraEntity().getY(),
               this.client.getCameraEntity().getZ()
            )
         );
         list.add(String.format("Block: %d %d %d", lv3.getX(), lv3.getY(), lv3.getZ()));
         list.add(
            String.format("Chunk: %d %d %d in %d %d %d", lv3.getX() & 15, lv3.getY() & 15, lv3.getZ() & 15, lv3.getX() >> 4, lv3.getY() >> 4, lv3.getZ() >> 4)
         );
         list.add(String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", lv5, string3, MathHelper.wrapDegrees(lv4.yaw), MathHelper.wrapDegrees(lv4.pitch)));
         if (this.client.world != null) {
            if (this.client.world.isChunkLoaded(lv3)) {
               WorldChunk lv8 = this.getClientChunk();
               if (lv8.isEmpty()) {
                  list.add("Waiting for chunk...");
               } else {
                  int i = this.client.world.getChunkManager().getLightingProvider().getLight(lv3, 0);
                  int j = this.client.world.getLightLevel(LightType.SKY, lv3);
                  int k = this.client.world.getLightLevel(LightType.BLOCK, lv3);
                  list.add("Client Light: " + i + " (" + j + " sky, " + k + " block)");
                  WorldChunk lv9 = this.getChunk();
                  if (lv9 != null) {
                     LightingProvider lv10 = lv7.getChunkManager().getLightingProvider();
                     list.add(
                        "Server Light: (" + lv10.get(LightType.SKY).getLightLevel(lv3) + " sky, " + lv10.get(LightType.BLOCK).getLightLevel(lv3) + " block)"
                     );
                  } else {
                     list.add("Server Light: (?? sky, ?? block)");
                  }

                  StringBuilder stringBuilder = new StringBuilder("CH");

                  for (Heightmap.Type lv11 : Heightmap.Type.values()) {
                     if (lv11.shouldSendToClient()) {
                        stringBuilder.append(" ").append(HEIGHT_MAP_TYPES.get(lv11)).append(": ").append(lv8.sampleHeightmap(lv11, lv3.getX(), lv3.getZ()));
                     }
                  }

                  list.add(stringBuilder.toString());
                  stringBuilder.setLength(0);
                  stringBuilder.append("SH");

                  for (Heightmap.Type lv12 : Heightmap.Type.values()) {
                     if (lv12.isStoredServerSide()) {
                        stringBuilder.append(" ").append(HEIGHT_MAP_TYPES.get(lv12)).append(": ");
                        if (lv9 != null) {
                           stringBuilder.append(lv9.sampleHeightmap(lv12, lv3.getX(), lv3.getZ()));
                        } else {
                           stringBuilder.append("??");
                        }
                     }
                  }

                  list.add(stringBuilder.toString());
                  if (lv3.getY() >= 0 && lv3.getY() < 256) {
                     list.add("Biome: " + this.client.world.getRegistryManager().get(Registry.BIOME_KEY).getId(this.client.world.getBiome(lv3)));
                     long l = 0L;
                     float h = 0.0F;
                     if (lv9 != null) {
                        h = lv7.getMoonSize();
                        l = lv9.getInhabitedTime();
                     }

                     LocalDifficulty lv13 = new LocalDifficulty(lv7.getDifficulty(), lv7.getTimeOfDay(), l, h);
                     list.add(
                        String.format(
                           Locale.ROOT,
                           "Local Difficulty: %.2f // %.2f (Day %d)",
                           lv13.getLocalDifficulty(),
                           lv13.getClampedLocalDifficulty(),
                           this.client.world.getTimeOfDay() / 24000L
                        )
                     );
                  }
               }
            } else {
               list.add("Outside of world...");
            }
         } else {
            list.add("Outside of world...");
         }

         ServerWorld lv14 = this.getServerWorld();
         if (lv14 != null) {
            SpawnHelper.Info lv15 = lv14.getChunkManager().getSpawnInfo();
            if (lv15 != null) {
               Object2IntMap<SpawnGroup> object2IntMap = lv15.getGroupToCount();
               int m = lv15.getSpawningChunkCount();
               list.add(
                  "SC: "
                     + m
                     + ", "
                     + Stream.of(SpawnGroup.values())
                        .map(arg -> Character.toUpperCase(arg.getName().charAt(0)) + ": " + object2IntMap.getInt(arg))
                        .collect(Collectors.joining(", "))
               );
            } else {
               list.add("SC: N/A");
            }
         }

         ShaderEffect lv16 = this.client.gameRenderer.getShader();
         if (lv16 != null) {
            list.add("Shader: " + lv16.getName());
         }

         list.add(this.client.getSoundManager().getDebugString() + String.format(" (Mood %d%%)", Math.round(this.client.player.getMoodPercentage() * 100.0F)));
         return list;
      }
   }

   @Nullable
   private ServerWorld getServerWorld() {
      IntegratedServer lv = this.client.getServer();
      return lv != null ? lv.getWorld(this.client.world.getRegistryKey()) : null;
   }

   @Nullable
   private String getServerWorldDebugString() {
      ServerWorld lv = this.getServerWorld();
      return lv != null ? lv.getDebugString() : null;
   }

   private World getWorld() {
      return (World)DataFixUtils.orElse(
         Optional.ofNullable(this.client.getServer()).flatMap(arg -> Optional.ofNullable(arg.getWorld(this.client.world.getRegistryKey()))), this.client.world
      );
   }

   @Nullable
   private WorldChunk getChunk() {
      if (this.chunkFuture == null) {
         ServerWorld lv = this.getServerWorld();
         if (lv != null) {
            this.chunkFuture = lv.getChunkManager()
               .getChunkFutureSyncOnMainThread(this.pos.x, this.pos.z, ChunkStatus.FULL, false)
               .thenApply(either -> (WorldChunk)either.map(arg -> (WorldChunk)arg, arg -> null));
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
      long m = Runtime.getRuntime().totalMemory();
      long n = Runtime.getRuntime().freeMemory();
      long o = m - n;
      List<String> list = Lists.newArrayList(
         new String[]{
            String.format("Java: %s %dbit", System.getProperty("java.version"), this.client.is64Bit() ? 64 : 32),
            String.format("Mem: % 2d%% %03d/%03dMB", o * 100L / l, toMiB(o), toMiB(l)),
            String.format("Allocated: % 2d%% %03dMB", m * 100L / l, toMiB(m)),
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
         return list;
      } else {
         if (this.blockHit.getType() == HitResult.Type.BLOCK) {
            BlockPos lv = ((BlockHitResult)this.blockHit).getBlockPos();
            BlockState lv2 = this.client.world.getBlockState(lv);
            list.add("");
            list.add(Formatting.UNDERLINE + "Targeted Block: " + lv.getX() + ", " + lv.getY() + ", " + lv.getZ());
            list.add(String.valueOf(Registry.BLOCK.getId(lv2.getBlock())));
            UnmodifiableIterator var12 = lv2.getEntries().entrySet().iterator();

            while (var12.hasNext()) {
               Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var12.next();
               list.add(this.propertyToString(entry));
            }

            for (Identifier lv3 : this.client.getNetworkHandler().getTagManager().getBlocks().getTagsFor(lv2.getBlock())) {
               list.add("#" + lv3);
            }
         }

         if (this.fluidHit.getType() == HitResult.Type.BLOCK) {
            BlockPos lv4 = ((BlockHitResult)this.fluidHit).getBlockPos();
            FluidState lv5 = this.client.world.getFluidState(lv4);
            list.add("");
            list.add(Formatting.UNDERLINE + "Targeted Fluid: " + lv4.getX() + ", " + lv4.getY() + ", " + lv4.getZ());
            list.add(String.valueOf(Registry.FLUID.getId(lv5.getFluid())));
            UnmodifiableIterator var18 = lv5.getEntries().entrySet().iterator();

            while (var18.hasNext()) {
               Entry<Property<?>, Comparable<?>> entry2 = (Entry<Property<?>, Comparable<?>>)var18.next();
               list.add(this.propertyToString(entry2));
            }

            for (Identifier lv6 : this.client.getNetworkHandler().getTagManager().getFluids().getTagsFor(lv5.getFluid())) {
               list.add("#" + lv6);
            }
         }

         Entity lv7 = this.client.targetedEntity;
         if (lv7 != null) {
            list.add("");
            list.add(Formatting.UNDERLINE + "Targeted Entity");
            list.add(String.valueOf(Registry.ENTITY_TYPE.getId(lv7.getType())));
         }

         return list;
      }
   }

   private String propertyToString(Entry<Property<?>, Comparable<?>> propEntry) {
      Property<?> lv = propEntry.getKey();
      Comparable<?> comparable = propEntry.getValue();
      String string = Util.getValueAsString(lv, comparable);
      if (Boolean.TRUE.equals(comparable)) {
         string = Formatting.GREEN + string;
      } else if (Boolean.FALSE.equals(comparable)) {
         string = Formatting.RED + string;
      }

      return lv.getName() + ": " + string;
   }

   private void drawMetricsData(MatrixStack matrices, MetricsData metricsData, int x, int width, boolean showFps) {
      RenderSystem.disableDepthTest();
      int k = metricsData.getStartIndex();
      int l = metricsData.getCurrentIndex();
      long[] ls = metricsData.getSamples();
      int n = x;
      int o = Math.max(0, ls.length - width);
      int p = ls.length - o;
      int m = metricsData.wrapIndex(k + o);
      long q = 0L;
      int r = Integer.MAX_VALUE;
      int s = Integer.MIN_VALUE;

      for (int t = 0; t < p; t++) {
         int u = (int)(ls[metricsData.wrapIndex(m + t)] / 1000000L);
         r = Math.min(r, u);
         s = Math.max(s, u);
         q += (long)u;
      }

      int v = this.client.getWindow().getScaledHeight();
      fill(matrices, x, v - 60, x + p, v, -1873784752);
      BufferBuilder lv = Tessellator.getInstance().getBuffer();
      RenderSystem.enableBlend();
      RenderSystem.disableTexture();
      RenderSystem.defaultBlendFunc();
      lv.begin(7, VertexFormats.POSITION_COLOR);

      for (Matrix4f lv2 = AffineTransformation.identity().getMatrix(); m != l; m = metricsData.wrapIndex(m + 1)) {
         int w = metricsData.method_15248(ls[m], showFps ? 30 : 60, showFps ? 60 : 20);
         int xx = showFps ? 100 : 60;
         int y = this.getMetricsLineColor(MathHelper.clamp(w, 0, xx), 0, xx / 2, xx);
         int z = y >> 24 & 0xFF;
         int aa = y >> 16 & 0xFF;
         int ab = y >> 8 & 0xFF;
         int ac = y & 0xFF;
         lv.vertex(lv2, (float)(n + 1), (float)v, 0.0F).color(aa, ab, ac, z).next();
         lv.vertex(lv2, (float)(n + 1), (float)(v - w + 1), 0.0F).color(aa, ab, ac, z).next();
         lv.vertex(lv2, (float)n, (float)(v - w + 1), 0.0F).color(aa, ab, ac, z).next();
         lv.vertex(lv2, (float)n, (float)v, 0.0F).color(aa, ab, ac, z).next();
         n++;
      }

      lv.end();
      BufferRenderer.draw(lv);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      if (showFps) {
         fill(matrices, x + 1, v - 30 + 1, x + 14, v - 30 + 10, -1873784752);
         this.fontRenderer.draw(matrices, "60 FPS", (float)(x + 2), (float)(v - 30 + 2), 14737632);
         this.drawHorizontalLine(matrices, x, x + p - 1, v - 30, -1);
         fill(matrices, x + 1, v - 60 + 1, x + 14, v - 60 + 10, -1873784752);
         this.fontRenderer.draw(matrices, "30 FPS", (float)(x + 2), (float)(v - 60 + 2), 14737632);
         this.drawHorizontalLine(matrices, x, x + p - 1, v - 60, -1);
      } else {
         fill(matrices, x + 1, v - 60 + 1, x + 14, v - 60 + 10, -1873784752);
         this.fontRenderer.draw(matrices, "20 TPS", (float)(x + 2), (float)(v - 60 + 2), 14737632);
         this.drawHorizontalLine(matrices, x, x + p - 1, v - 60, -1);
      }

      this.drawHorizontalLine(matrices, x, x + p - 1, v - 1, -1);
      this.drawVerticalLine(matrices, x, v - 60, v, -1);
      this.drawVerticalLine(matrices, x + p - 1, v - 60, v, -1);
      if (showFps && this.client.options.maxFps > 0 && this.client.options.maxFps <= 250) {
         this.drawHorizontalLine(matrices, x, x + p - 1, v - 1 - (int)(1800.0 / (double)this.client.options.maxFps), -16711681);
      }

      String string = r + " ms min";
      String string2 = q / (long)p + " ms avg";
      String string3 = s + " ms max";
      this.fontRenderer.drawWithShadow(matrices, string, (float)(x + 2), (float)(v - 60 - 9), 14737632);
      this.fontRenderer.drawWithShadow(matrices, string2, (float)(x + p / 2 - this.fontRenderer.getWidth(string2) / 2), (float)(v - 60 - 9), 14737632);
      this.fontRenderer.drawWithShadow(matrices, string3, (float)(x + p - this.fontRenderer.getWidth(string3)), (float)(v - 60 - 9), 14737632);
      RenderSystem.enableDepthTest();
   }

   private int getMetricsLineColor(int value, int greenValue, int yellowValue, int redValue) {
      return value < yellowValue
         ? this.interpolateColor(-16711936, -256, (float)value / (float)yellowValue)
         : this.interpolateColor(-256, -65536, (float)(value - yellowValue) / (float)(redValue - yellowValue));
   }

   private int interpolateColor(int color1, int color2, float dt) {
      int k = color1 >> 24 & 0xFF;
      int l = color1 >> 16 & 0xFF;
      int m = color1 >> 8 & 0xFF;
      int n = color1 & 0xFF;
      int o = color2 >> 24 & 0xFF;
      int p = color2 >> 16 & 0xFF;
      int q = color2 >> 8 & 0xFF;
      int r = color2 & 0xFF;
      int s = MathHelper.clamp((int)MathHelper.lerp(dt, (float)k, (float)o), 0, 255);
      int t = MathHelper.clamp((int)MathHelper.lerp(dt, (float)l, (float)p), 0, 255);
      int u = MathHelper.clamp((int)MathHelper.lerp(dt, (float)m, (float)q), 0, 255);
      int v = MathHelper.clamp((int)MathHelper.lerp(dt, (float)n, (float)r), 0, 255);
      return s << 24 | t << 16 | u << 8 | v;
   }

   private static long toMiB(long bytes) {
      return bytes / 1024L / 1024L;
   }
}
