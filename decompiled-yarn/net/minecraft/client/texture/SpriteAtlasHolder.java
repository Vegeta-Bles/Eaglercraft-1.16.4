package net.minecraft.client.texture;

import java.util.stream.Stream;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

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

   protected SpriteAtlasTexture.Data prepare(ResourceManager _snowman, Profiler _snowman) {
      _snowman.startTick();
      _snowman.push("stitching");
      SpriteAtlasTexture.Data _snowmanxx = this.atlas.stitch(_snowman, this.getSprites().map(this::toSpriteId), _snowman, 0);
      _snowman.pop();
      _snowman.endTick();
      return _snowmanxx;
   }

   protected void apply(SpriteAtlasTexture.Data _snowman, ResourceManager _snowman, Profiler _snowman) {
      _snowman.startTick();
      _snowman.push("upload");
      this.atlas.upload(_snowman);
      _snowman.pop();
      _snowman.endTick();
   }

   @Override
   public void close() {
      this.atlas.clear();
   }
}
