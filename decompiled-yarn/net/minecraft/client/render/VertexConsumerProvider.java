package net.minecraft.client.render;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public interface VertexConsumerProvider {
   static VertexConsumerProvider.Immediate immediate(BufferBuilder buffer) {
      return immediate(ImmutableMap.of(), buffer);
   }

   static VertexConsumerProvider.Immediate immediate(Map<RenderLayer, BufferBuilder> layerBuffers, BufferBuilder fallbackBuffer) {
      return new VertexConsumerProvider.Immediate(fallbackBuffer, layerBuffers);
   }

   VertexConsumer getBuffer(RenderLayer layer);

   public static class Immediate implements VertexConsumerProvider {
      protected final BufferBuilder fallbackBuffer;
      protected final Map<RenderLayer, BufferBuilder> layerBuffers;
      protected Optional<RenderLayer> currentLayer = Optional.empty();
      protected final Set<BufferBuilder> activeConsumers = Sets.newHashSet();

      protected Immediate(BufferBuilder fallbackBuffer, Map<RenderLayer, BufferBuilder> layerBuffers) {
         this.fallbackBuffer = fallbackBuffer;
         this.layerBuffers = layerBuffers;
      }

      @Override
      public VertexConsumer getBuffer(RenderLayer _snowman) {
         Optional<RenderLayer> _snowmanx = _snowman.asOptional();
         BufferBuilder _snowmanxx = this.getBufferInternal(_snowman);
         if (!Objects.equals(this.currentLayer, _snowmanx)) {
            if (this.currentLayer.isPresent()) {
               RenderLayer _snowmanxxx = this.currentLayer.get();
               if (!this.layerBuffers.containsKey(_snowmanxxx)) {
                  this.draw(_snowmanxxx);
               }
            }

            if (this.activeConsumers.add(_snowmanxx)) {
               _snowmanxx.begin(_snowman.getDrawMode(), _snowman.getVertexFormat());
            }

            this.currentLayer = _snowmanx;
         }

         return _snowmanxx;
      }

      private BufferBuilder getBufferInternal(RenderLayer layer) {
         return this.layerBuffers.getOrDefault(layer, this.fallbackBuffer);
      }

      public void draw() {
         this.currentLayer.ifPresent(_snowman -> {
            VertexConsumer _snowmanx = this.getBuffer(_snowman);
            if (_snowmanx == this.fallbackBuffer) {
               this.draw(_snowman);
            }
         });

         for (RenderLayer _snowman : this.layerBuffers.keySet()) {
            this.draw(_snowman);
         }
      }

      public void draw(RenderLayer layer) {
         BufferBuilder _snowman = this.getBufferInternal(layer);
         boolean _snowmanx = Objects.equals(this.currentLayer, layer.asOptional());
         if (_snowmanx || _snowman != this.fallbackBuffer) {
            if (this.activeConsumers.remove(_snowman)) {
               layer.draw(_snowman, 0, 0, 0);
               if (_snowmanx) {
                  this.currentLayer = Optional.empty();
               }
            }
         }
      }
   }
}
