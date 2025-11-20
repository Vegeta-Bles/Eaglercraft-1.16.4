package net.minecraft.client.texture;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureManager implements ResourceReloadListener, TextureTickListener, AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final Identifier MISSING_IDENTIFIER = new Identifier("");
   private final Map<Identifier, AbstractTexture> textures = Maps.newHashMap();
   private final Set<TextureTickListener> tickListeners = Sets.newHashSet();
   private final Map<String, Integer> dynamicIdCounters = Maps.newHashMap();
   private final ResourceManager resourceContainer;

   public TextureManager(ResourceManager resourceManager) {
      this.resourceContainer = resourceManager;
   }

   public void bindTexture(Identifier id) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.bindTextureInner(id));
      } else {
         this.bindTextureInner(id);
      }
   }

   private void bindTextureInner(Identifier id) {
      AbstractTexture _snowman = this.textures.get(id);
      if (_snowman == null) {
         _snowman = new ResourceTexture(id);
         this.registerTexture(id, _snowman);
      }

      _snowman.bindTexture();
   }

   public void registerTexture(Identifier _snowman, AbstractTexture _snowman) {
      _snowman = this.method_24303(_snowman, _snowman);
      AbstractTexture _snowmanxx = this.textures.put(_snowman, _snowman);
      if (_snowmanxx != _snowman) {
         if (_snowmanxx != null && _snowmanxx != MissingSprite.getMissingSpriteTexture()) {
            this.tickListeners.remove(_snowmanxx);
            this.method_30299(_snowman, _snowmanxx);
         }

         if (_snowman instanceof TextureTickListener) {
            this.tickListeners.add((TextureTickListener)_snowman);
         }
      }
   }

   private void method_30299(Identifier _snowman, AbstractTexture _snowman) {
      if (_snowman != MissingSprite.getMissingSpriteTexture()) {
         try {
            _snowman.close();
         } catch (Exception var4) {
            LOGGER.warn("Failed to close texture {}", _snowman, var4);
         }
      }

      _snowman.clearGlId();
   }

   private AbstractTexture method_24303(Identifier _snowman, AbstractTexture _snowman) {
      try {
         _snowman.load(this.resourceContainer);
         return _snowman;
      } catch (IOException var6) {
         if (_snowman != MISSING_IDENTIFIER) {
            LOGGER.warn("Failed to load texture: {}", _snowman, var6);
         }

         return MissingSprite.getMissingSpriteTexture();
      } catch (Throwable var7) {
         CrashReport _snowmanxx = CrashReport.create(var7, "Registering texture");
         CrashReportSection _snowmanxxx = _snowmanxx.addElement("Resource location being registered");
         _snowmanxxx.add("Resource location", _snowman);
         _snowmanxxx.add("Texture object class", () -> _snowman.getClass().getName());
         throw new CrashException(_snowmanxx);
      }
   }

   @Nullable
   public AbstractTexture getTexture(Identifier id) {
      return this.textures.get(id);
   }

   public Identifier registerDynamicTexture(String prefix, NativeImageBackedTexture texture) {
      Integer _snowman = this.dynamicIdCounters.get(prefix);
      if (_snowman == null) {
         _snowman = 1;
      } else {
         _snowman = _snowman + 1;
      }

      this.dynamicIdCounters.put(prefix, _snowman);
      Identifier _snowmanx = new Identifier(String.format("dynamic/%s_%d", prefix, _snowman));
      this.registerTexture(_snowmanx, texture);
      return _snowmanx;
   }

   public CompletableFuture<Void> loadTextureAsync(Identifier id, Executor executor) {
      if (!this.textures.containsKey(id)) {
         AsyncTexture _snowman = new AsyncTexture(this.resourceContainer, id, executor);
         this.textures.put(id, _snowman);
         return _snowman.getLoadCompleteFuture().thenRunAsync(() -> this.registerTexture(id, _snowman), TextureManager::runOnRenderThread);
      } else {
         return CompletableFuture.completedFuture(null);
      }
   }

   private static void runOnRenderThread(Runnable runnable) {
      MinecraftClient.getInstance().execute(() -> RenderSystem.recordRenderCall(runnable::run));
   }

   @Override
   public void tick() {
      for (TextureTickListener _snowman : this.tickListeners) {
         _snowman.tick();
      }
   }

   public void destroyTexture(Identifier id) {
      AbstractTexture _snowman = this.getTexture(id);
      if (_snowman != null) {
         TextureUtil.deleteId(_snowman.getGlId());
      }
   }

   @Override
   public void close() {
      this.textures.forEach(this::method_30299);
      this.textures.clear();
      this.tickListeners.clear();
      this.dynamicIdCounters.clear();
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
      return CompletableFuture.allOf(
            TitleScreen.loadTexturesAsync(this, prepareExecutor), this.loadTextureAsync(AbstractButtonWidget.WIDGETS_LOCATION, prepareExecutor)
         )
         .thenCompose(synchronizer::whenPrepared)
         .thenAcceptAsync(_snowmanxx -> {
            MissingSprite.getMissingSpriteTexture();
            RealmsMainScreen.method_23765(this.resourceContainer);
            Iterator<Entry<Identifier, AbstractTexture>> _snowmanxxx = this.textures.entrySet().iterator();

            while (_snowmanxxx.hasNext()) {
               Entry<Identifier, AbstractTexture> _snowmanx = _snowmanxxx.next();
               Identifier _snowmanxxx = _snowmanx.getKey();
               AbstractTexture _snowmanxxxx = _snowmanx.getValue();
               if (_snowmanxxxx == MissingSprite.getMissingSpriteTexture() && !_snowmanxxx.equals(MissingSprite.getMissingSpriteId())) {
                  _snowmanxxx.remove();
               } else {
                  _snowmanxxxx.registerTexture(this, manager, _snowmanxxx, applyExecutor);
               }
            }
         }, _snowman -> RenderSystem.recordRenderCall(_snowman::run));
   }
}
