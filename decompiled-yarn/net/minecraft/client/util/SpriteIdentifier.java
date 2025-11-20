package net.minecraft.client.util;

import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;

public class SpriteIdentifier {
   private final Identifier atlas;
   private final Identifier texture;
   @Nullable
   private RenderLayer layer;

   public SpriteIdentifier(Identifier atlas, Identifier texture) {
      this.atlas = atlas;
      this.texture = texture;
   }

   public Identifier getAtlasId() {
      return this.atlas;
   }

   public Identifier getTextureId() {
      return this.texture;
   }

   public Sprite getSprite() {
      return MinecraftClient.getInstance().getSpriteAtlas(this.getAtlasId()).apply(this.getTextureId());
   }

   public RenderLayer getRenderLayer(Function<Identifier, RenderLayer> layerFactory) {
      if (this.layer == null) {
         this.layer = layerFactory.apply(this.atlas);
      }

      return this.layer;
   }

   public VertexConsumer getVertexConsumer(VertexConsumerProvider vertexConsumers, Function<Identifier, RenderLayer> layerFactory) {
      return this.getSprite().getTextureSpecificVertexConsumer(vertexConsumers.getBuffer(this.getRenderLayer(layerFactory)));
   }

   public VertexConsumer method_30001(VertexConsumerProvider _snowman, Function<Identifier, RenderLayer> _snowman, boolean _snowman) {
      return this.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(_snowman, this.getRenderLayer(_snowman), true, _snowman));
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         SpriteIdentifier _snowmanx = (SpriteIdentifier)_snowman;
         return this.atlas.equals(_snowmanx.atlas) && this.texture.equals(_snowmanx.texture);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.atlas, this.texture);
   }

   @Override
   public String toString() {
      return "Material{atlasLocation=" + this.atlas + ", texture=" + this.texture + '}';
   }
}
