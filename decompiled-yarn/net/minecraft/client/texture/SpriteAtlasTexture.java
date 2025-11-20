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

   public SpriteAtlasTexture(Identifier _snowman) {
      this.id = _snowman;
      this.maxTextureSize = RenderSystem.maxSupportedTextureSize();
   }

   @Override
   public void load(ResourceManager manager) throws IOException {
   }

   public void upload(SpriteAtlasTexture.Data _snowman) {
      this.spritesToLoad.clear();
      this.spritesToLoad.addAll(_snowman.spriteIds);
      LOGGER.info("Created: {}x{}x{} {}-atlas", _snowman.width, _snowman.height, _snowman.maxLevel, this.id);
      TextureUtil.allocate(this.getGlId(), _snowman.maxLevel, _snowman.width, _snowman.height);
      this.clear();

      for (Sprite _snowmanx : _snowman.sprites) {
         this.sprites.put(_snowmanx.getId(), _snowmanx);

         try {
            _snowmanx.upload();
         } catch (Throwable var7) {
            CrashReport _snowmanxx = CrashReport.create(var7, "Stitching texture atlas");
            CrashReportSection _snowmanxxx = _snowmanxx.addElement("Texture being stitched together");
            _snowmanxxx.add("Atlas path", this.id);
            _snowmanxxx.add("Sprite", _snowmanx);
            throw new CrashException(_snowmanxx);
         }

         if (_snowmanx.isAnimated()) {
            this.animatedSprites.add(_snowmanx);
         }
      }
   }

   public SpriteAtlasTexture.Data stitch(ResourceManager resourceManager, Stream<Identifier> idStream, Profiler profiler, int mipmapLevel) {
      profiler.push("preparing");
      Set<Identifier> _snowman = idStream.peek(_snowmanx -> {
         if (_snowmanx == null) {
            throw new IllegalArgumentException("Location cannot be null!");
         }
      }).collect(Collectors.toSet());
      int _snowmanx = this.maxTextureSize;
      TextureStitcher _snowmanxx = new TextureStitcher(_snowmanx, _snowmanx, mipmapLevel);
      int _snowmanxxx = Integer.MAX_VALUE;
      int _snowmanxxxx = 1 << mipmapLevel;
      profiler.swap("extracting_frames");

      for (Sprite.Info _snowmanxxxxx : this.loadSprites(resourceManager, _snowman)) {
         _snowmanxxx = Math.min(_snowmanxxx, Math.min(_snowmanxxxxx.getWidth(), _snowmanxxxxx.getHeight()));
         int _snowmanxxxxxx = Math.min(Integer.lowestOneBit(_snowmanxxxxx.getWidth()), Integer.lowestOneBit(_snowmanxxxxx.getHeight()));
         if (_snowmanxxxxxx < _snowmanxxxx) {
            LOGGER.warn(
               "Texture {} with size {}x{} limits mip level from {} to {}",
               _snowmanxxxxx.getId(),
               _snowmanxxxxx.getWidth(),
               _snowmanxxxxx.getHeight(),
               MathHelper.log2(_snowmanxxxx),
               MathHelper.log2(_snowmanxxxxxx)
            );
            _snowmanxxxx = _snowmanxxxxxx;
         }

         _snowmanxx.add(_snowmanxxxxx);
      }

      int _snowmanxxxxx = Math.min(_snowmanxxx, _snowmanxxxx);
      int _snowmanxxxxxx = MathHelper.log2(_snowmanxxxxx);
      int _snowmanxxxxxxx;
      if (_snowmanxxxxxx < mipmapLevel) {
         LOGGER.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", this.id, mipmapLevel, _snowmanxxxxxx, _snowmanxxxxx);
         _snowmanxxxxxxx = _snowmanxxxxxx;
      } else {
         _snowmanxxxxxxx = mipmapLevel;
      }

      profiler.swap("register");
      _snowmanxx.add(MissingSprite.getMissingInfo());
      profiler.swap("stitching");

      try {
         _snowmanxx.stitch();
      } catch (TextureStitcherCannotFitException var16) {
         CrashReport _snowmanxxxxxxxx = CrashReport.create(var16, "Stitching");
         CrashReportSection _snowmanxxxxxxxxx = _snowmanxxxxxxxx.addElement("Stitcher");
         _snowmanxxxxxxxxx.add(
            "Sprites",
            var16.getSprites()
               .stream()
               .map(_snowmanxxxxxxxxxx -> String.format("%s[%dx%d]", _snowmanxxxxxxxxxx.getId(), _snowmanxxxxxxxxxx.getWidth(), _snowmanxxxxxxxxxx.getHeight()))
               .collect(Collectors.joining(","))
         );
         _snowmanxxxxxxxxx.add("Max Texture Size", _snowmanx);
         throw new CrashException(_snowmanxxxxxxxx);
      }

      profiler.swap("loading");
      List<Sprite> _snowmanxxxxxxxx = this.loadSprites(resourceManager, _snowmanxx, _snowmanxxxxxxx);
      profiler.pop();
      return new SpriteAtlasTexture.Data(_snowman, _snowmanxx.getWidth(), _snowmanxx.getHeight(), _snowmanxxxxxxx, _snowmanxxxxxxxx);
   }

   private Collection<Sprite.Info> loadSprites(ResourceManager resourceManager, Set<Identifier> ids) {
      List<CompletableFuture<?>> _snowman = Lists.newArrayList();
      ConcurrentLinkedQueue<Sprite.Info> _snowmanx = new ConcurrentLinkedQueue<>();

      for (Identifier _snowmanxx : ids) {
         if (!MissingSprite.getMissingSpriteId().equals(_snowmanxx)) {
            _snowman.add(CompletableFuture.runAsync(() -> {
               Identifier _snowmanxxx = this.getTexturePath(_snowman);

               Sprite.Info _snowmanx;
               try (Resource _snowmanxx = resourceManager.getResource(_snowmanxxx)) {
                  PngFile _snowmanxxx = new PngFile(_snowmanxx.toString(), _snowmanxx.getInputStream());
                  AnimationResourceMetadata _snowmanxxxx = _snowmanxx.getMetadata(AnimationResourceMetadata.READER);
                  if (_snowmanxxxx == null) {
                     _snowmanxxxx = AnimationResourceMetadata.EMPTY;
                  }

                  Pair<Integer, Integer> _snowmanxxxxx = _snowmanxxxx.method_24141(_snowmanxxx.width, _snowmanxxx.height);
                  _snowmanx = new Sprite.Info(_snowman, (Integer)_snowmanxxxxx.getFirst(), (Integer)_snowmanxxxxx.getSecond(), _snowmanxxxx);
               } catch (RuntimeException var22) {
                  LOGGER.error("Unable to parse metadata from {} : {}", _snowmanxxx, var22);
                  return;
               } catch (IOException var23) {
                  LOGGER.error("Using missing texture, unable to load {} : {}", _snowmanxxx, var23);
                  return;
               }

               _snowman.add(_snowmanx);
            }, Util.getMainWorkerExecutor()));
         }
      }

      CompletableFuture.allOf(_snowman.toArray(new CompletableFuture[0])).join();
      return _snowmanx;
   }

   private List<Sprite> loadSprites(ResourceManager _snowman, TextureStitcher _snowman, int maxLevel) {
      ConcurrentLinkedQueue<Sprite> _snowmanxx = new ConcurrentLinkedQueue<>();
      List<CompletableFuture<?>> _snowmanxxx = Lists.newArrayList();
      _snowman.getStitchedSprites((_snowmanxxxx, atlasWidth, atlasHeight, x, y) -> {
         if (_snowmanxxxx == MissingSprite.getMissingInfo()) {
            MissingSprite _snowmanxxxxx = MissingSprite.getMissingSprite(this, maxLevel, atlasWidth, atlasHeight, x, y);
            _snowman.add(_snowmanxxxxx);
         } else {
            _snowman.add(CompletableFuture.runAsync(() -> {
               Sprite _snowmanxxxxxxxx = this.loadSprite(_snowman, _snowmanxxx, atlasWidth, atlasHeight, maxLevel, x, y);
               if (_snowmanxxxxxxxx != null) {
                  _snowman.add(_snowmanxxxxxxxx);
               }
            }, Util.getMainWorkerExecutor()));
         }
      });
      CompletableFuture.allOf(_snowmanxxx.toArray(new CompletableFuture[0])).join();
      return Lists.newArrayList(_snowmanxx);
   }

   @Nullable
   private Sprite loadSprite(ResourceManager container, Sprite.Info _snowman, int atlasWidth, int atlasHeight, int maxLevel, int x, int y) {
      Identifier _snowmanx = this.getTexturePath(_snowman.getId());

      try (Resource _snowmanxx = container.getResource(_snowmanx)) {
         NativeImage _snowmanxxx = NativeImage.read(_snowmanxx.getInputStream());
         return new Sprite(this, _snowman, maxLevel, atlasWidth, atlasHeight, x, y, _snowmanxxx);
      } catch (RuntimeException var25) {
         LOGGER.error("Unable to parse metadata from {}", _snowmanx, var25);
         return null;
      } catch (IOException var26) {
         LOGGER.error("Using missing texture, unable to load {}", _snowmanx, var26);
         return null;
      }
   }

   private Identifier getTexturePath(Identifier _snowman) {
      return new Identifier(_snowman.getNamespace(), String.format("textures/%s%s", _snowman.getPath(), ".png"));
   }

   public void tickAnimatedSprites() {
      this.bindTexture();

      for (Sprite _snowman : this.animatedSprites) {
         _snowman.tickAnimation();
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
      Sprite _snowman = this.sprites.get(id);
      return _snowman == null ? this.sprites.get(MissingSprite.getMissingSpriteId()) : _snowman;
   }

   public void clear() {
      for (Sprite _snowman : this.sprites.values()) {
         _snowman.close();
      }

      this.sprites.clear();
      this.animatedSprites.clear();
   }

   public Identifier getId() {
      return this.id;
   }

   public void applyTextureFilter(SpriteAtlasTexture.Data _snowman) {
      this.setFilter(false, _snowman.maxLevel > 0);
   }

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
