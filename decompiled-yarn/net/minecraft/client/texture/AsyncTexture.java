package net.minecraft.client.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class AsyncTexture extends ResourceTexture {
   @Nullable
   private CompletableFuture<ResourceTexture.TextureData> future;

   public AsyncTexture(ResourceManager _snowman, Identifier _snowman, Executor _snowman) {
      super(_snowman);
      this.future = CompletableFuture.supplyAsync(() -> ResourceTexture.TextureData.load(_snowman, _snowman), _snowman);
   }

   @Override
   protected ResourceTexture.TextureData loadTextureData(ResourceManager resourceManager) {
      if (this.future != null) {
         ResourceTexture.TextureData _snowman = this.future.join();
         this.future = null;
         return _snowman;
      } else {
         return ResourceTexture.TextureData.load(resourceManager, this.location);
      }
   }

   public CompletableFuture<Void> getLoadCompleteFuture() {
      return this.future == null ? CompletableFuture.completedFuture(null) : this.future.thenApply(_snowman -> null);
   }

   @Override
   public void registerTexture(TextureManager _snowman, ResourceManager _snowman, Identifier _snowman, Executor _snowman) {
      this.future = CompletableFuture.supplyAsync(() -> ResourceTexture.TextureData.load(_snowman, this.location), Util.getMainWorkerExecutor());
      this.future.thenRunAsync(() -> _snowman.registerTexture(this.location, this), method_22808(_snowman));
   }

   private static Executor method_22808(Executor _snowman) {
      return _snowmanxx -> _snowman.execute(() -> RenderSystem.recordRenderCall(_snowmanxx::run));
   }
}
