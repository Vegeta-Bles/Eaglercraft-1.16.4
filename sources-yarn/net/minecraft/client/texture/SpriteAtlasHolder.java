package net.minecraft.client.texture;

import java.util.stream.Stream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

@Environment(EnvType.CLIENT)
public abstract class SpriteAtlasHolder extends SinglePreparationResourceReloadListener<SpriteAtlasTexture.Data> implements AutoCloseable {
   private final SpriteAtlasTexture atlas;
   private final String pathPrefix;

   public SpriteAtlasHolder(TextureManager textureManager, Identifier atlasId, String pathPrefix) {
      this.pathPrefix = pathPrefix;
      this.atlas = new SpriteAtlasTexture(atlasId);
      textureManager.registerTexture(this.atlas.getId(), this.atlas);
   }

   protected abstract Stream<Identifier> getSprites();

   protected Sprite getSprite(Identifier objectId) {
      return this.atlas.getSprite(this.toSpriteId(objectId));
   }

   private Identifier toSpriteId(Identifier objectId) {
      return new Identifier(objectId.getNamespace(), this.pathPrefix + "/" + objectId.getPath());
   }

   protected SpriteAtlasTexture.Data prepare(ResourceManager arg, Profiler arg2) {
      arg2.startTick();
      arg2.push("stitching");
      SpriteAtlasTexture.Data lv = this.atlas.stitch(arg, this.getSprites().map(this::toSpriteId), arg2, 0);
      arg2.pop();
      arg2.endTick();
      return lv;
   }

   protected void apply(SpriteAtlasTexture.Data arg, ResourceManager arg2, Profiler arg3) {
      arg3.startTick();
      arg3.push("upload");
      this.atlas.upload(arg);
      arg3.pop();
      arg3.endTick();
   }

   @Override
   public void close() {
      this.atlas.clear();
   }
}
