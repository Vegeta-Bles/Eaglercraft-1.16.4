package net.minecraft.client.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.util.PngFile;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class SpriteAtlasTexture extends AbstractTexture implements TextureTickListener {
   private static final Logger LOGGER = LogManager.getLogger();
   @Deprecated
   public static final Identifier BLOCK_ATLAS_TEXTURE = PlayerScreenHandler.BLOCK_ATLAS_TEXTURE;
   @Deprecated
   public static final Identifier PARTICLE_ATLAS_TEXTURE = new Identifier("textures/atlas/particles.png");
   private final List<Sprite> animatedSprites = Lists.newArrayList();
   private final Set<Identifier> spritesToLoad = Sets.newHashSet();
   private final Map<Identifier, Sprite> sprites = Maps.newHashMap();
   private final Identifier id;
   private final int maxTextureSize;

   public SpriteAtlasTexture(Identifier arg) {
      this.id = arg;
      this.maxTextureSize = RenderSystem.maxSupportedTextureSize();
   }

   @Override
   public void load(ResourceManager manager) throws IOException {
   }

   public void upload(SpriteAtlasTexture.Data arg) {
      this.spritesToLoad.clear();
      this.spritesToLoad.addAll(arg.spriteIds);
      LOGGER.info("Created: {}x{}x{} {}-atlas", arg.width, arg.height, arg.maxLevel, this.id);
      TextureUtil.allocate(this.getGlId(), arg.maxLevel, arg.width, arg.height);
      this.clear();

      for (Sprite lv : arg.sprites) {
         this.sprites.put(lv.getId(), lv);

         try {
            lv.upload();
         } catch (Throwable var7) {
            CrashReport lv2 = CrashReport.create(var7, "Stitching texture atlas");
            CrashReportSection lv3 = lv2.addElement("Texture being stitched together");
            lv3.add("Atlas path", this.id);
            lv3.add("Sprite", lv);
            throw new CrashException(lv2);
         }

         if (lv.isAnimated()) {
            this.animatedSprites.add(lv);
         }
      }
   }

   public SpriteAtlasTexture.Data stitch(ResourceManager resourceManager, Stream<Identifier> idStream, Profiler profiler, int mipmapLevel) {
      profiler.push("preparing");
      Set<Identifier> set = idStream.peek(arg -> {
         if (arg == null) {
            throw new IllegalArgumentException("Location cannot be null!");
         }
      }).collect(Collectors.toSet());
      int j = this.maxTextureSize;
      TextureStitcher lv = new TextureStitcher(j, j, mipmapLevel);
      int k = Integer.MAX_VALUE;
      int l = 1 << mipmapLevel;
      profiler.swap("extracting_frames");

      for (Sprite.Info lv2 : this.loadSprites(resourceManager, set)) {
         k = Math.min(k, Math.min(lv2.getWidth(), lv2.getHeight()));
         int m = Math.min(Integer.lowestOneBit(lv2.getWidth()), Integer.lowestOneBit(lv2.getHeight()));
         if (m < l) {
            LOGGER.warn(
               "Texture {} with size {}x{} limits mip level from {} to {}",
               lv2.getId(),
               lv2.getWidth(),
               lv2.getHeight(),
               MathHelper.log2(l),
               MathHelper.log2(m)
            );
            l = m;
         }

         lv.add(lv2);
      }

      int n = Math.min(k, l);
      int o = MathHelper.log2(n);
      int p;
      if (o < mipmapLevel) {
         LOGGER.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", this.id, mipmapLevel, o, n);
         p = o;
      } else {
         p = mipmapLevel;
      }

      profiler.swap("register");
      lv.add(MissingSprite.getMissingInfo());
      profiler.swap("stitching");

      try {
         lv.stitch();
      } catch (TextureStitcherCannotFitException var16) {
         CrashReport lv4 = CrashReport.create(var16, "Stitching");
         CrashReportSection lv5 = lv4.addElement("Stitcher");
         lv5.add(
            "Sprites",
            var16.getSprites().stream().map(arg -> String.format("%s[%dx%d]", arg.getId(), arg.getWidth(), arg.getHeight())).collect(Collectors.joining(","))
         );
         lv5.add("Max Texture Size", j);
         throw new CrashException(lv4);
      }

      profiler.swap("loading");
      List<Sprite> list = this.loadSprites(resourceManager, lv, p);
      profiler.pop();
      return new SpriteAtlasTexture.Data(set, lv.getWidth(), lv.getHeight(), p, list);
   }

   private Collection<Sprite.Info> loadSprites(ResourceManager resourceManager, Set<Identifier> ids) {
      List<CompletableFuture<?>> list = Lists.newArrayList();
      ConcurrentLinkedQueue<Sprite.Info> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

      for (Identifier lv : ids) {
         if (!MissingSprite.getMissingSpriteId().equals(lv)) {
            list.add(CompletableFuture.runAsync(() -> {
               Identifier lvx = this.getTexturePath(lv);

               Sprite.Info lv5;
               try (Resource lv2 = resourceManager.getResource(lvx)) {
                  PngFile lv3 = new PngFile(lv2.toString(), lv2.getInputStream());
                  AnimationResourceMetadata lv4 = lv2.getMetadata(AnimationResourceMetadata.READER);
                  if (lv4 == null) {
                     lv4 = AnimationResourceMetadata.EMPTY;
                  }

                  Pair<Integer, Integer> pair = lv4.method_24141(lv3.width, lv3.height);
                  lv5 = new Sprite.Info(lv, (Integer)pair.getFirst(), (Integer)pair.getSecond(), lv4);
               } catch (RuntimeException var22) {
                  LOGGER.error("Unable to parse metadata from {} : {}", lvx, var22);
                  return;
               } catch (IOException var23) {
                  LOGGER.error("Using missing texture, unable to load {} : {}", lvx, var23);
                  return;
               }

               concurrentLinkedQueue.add(lv5);
            }, Util.getMainWorkerExecutor()));
         }
      }

      CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
      return concurrentLinkedQueue;
   }

   private List<Sprite> loadSprites(ResourceManager arg, TextureStitcher arg2, int maxLevel) {
      ConcurrentLinkedQueue<Sprite> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
      List<CompletableFuture<?>> list = Lists.newArrayList();
      arg2.getStitchedSprites((arg2x, atlasWidth, atlasHeight, x, y) -> {
         if (arg2x == MissingSprite.getMissingInfo()) {
            MissingSprite lv = MissingSprite.getMissingSprite(this, maxLevel, atlasWidth, atlasHeight, x, y);
            concurrentLinkedQueue.add(lv);
         } else {
            list.add(CompletableFuture.runAsync(() -> {
               Sprite lvx = this.loadSprite(arg, arg2x, atlasWidth, atlasHeight, maxLevel, x, y);
               if (lvx != null) {
                  concurrentLinkedQueue.add(lvx);
               }
            }, Util.getMainWorkerExecutor()));
         }
      });
      CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
      return Lists.newArrayList(concurrentLinkedQueue);
   }

   @Nullable
   private Sprite loadSprite(ResourceManager container, Sprite.Info arg2, int atlasWidth, int atlasHeight, int maxLevel, int x, int y) {
      Identifier lv = this.getTexturePath(arg2.getId());

      try (Resource lv2 = container.getResource(lv)) {
         NativeImage lv3 = NativeImage.read(lv2.getInputStream());
         return new Sprite(this, arg2, maxLevel, atlasWidth, atlasHeight, x, y, lv3);
      } catch (RuntimeException var25) {
         LOGGER.error("Unable to parse metadata from {}", lv, var25);
         return null;
      } catch (IOException var26) {
         LOGGER.error("Using missing texture, unable to load {}", lv, var26);
         return null;
      }
   }

   private Identifier getTexturePath(Identifier arg) {
      return new Identifier(arg.getNamespace(), String.format("textures/%s%s", arg.getPath(), ".png"));
   }

   public void tickAnimatedSprites() {
      this.bindTexture();

      for (Sprite lv : this.animatedSprites) {
         lv.tickAnimation();
      }
   }

   @Override
   public void tick() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(this::tickAnimatedSprites);
      } else {
         this.tickAnimatedSprites();
      }
   }

   public Sprite getSprite(Identifier id) {
      Sprite lv = this.sprites.get(id);
      return lv == null ? this.sprites.get(MissingSprite.getMissingSpriteId()) : lv;
   }

   public void clear() {
      for (Sprite lv : this.sprites.values()) {
         lv.close();
      }

      this.sprites.clear();
      this.animatedSprites.clear();
   }

   public Identifier getId() {
      return this.id;
   }

   public void applyTextureFilter(SpriteAtlasTexture.Data arg) {
      this.setFilter(false, arg.maxLevel > 0);
   }

   @Environment(EnvType.CLIENT)
   public static class Data {
      final Set<Identifier> spriteIds;
      final int width;
      final int height;
      final int maxLevel;
      final List<Sprite> sprites;

      public Data(Set<Identifier> spriteIds, int width, int height, int maxLevel, List<Sprite> sprites) {
         this.spriteIds = spriteIds;
         this.width = width;
         this.height = height;
         this.maxLevel = maxLevel;
         this.sprites = sprites;
      }
   }
}
