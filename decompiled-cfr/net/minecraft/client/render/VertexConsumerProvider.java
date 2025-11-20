/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Sets
 */
package net.minecraft.client.render;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;

public interface VertexConsumerProvider {
    public static Immediate immediate(BufferBuilder buffer) {
        return VertexConsumerProvider.immediate((Map<RenderLayer, BufferBuilder>)ImmutableMap.of(), buffer);
    }

    public static Immediate immediate(Map<RenderLayer, BufferBuilder> layerBuffers, BufferBuilder fallbackBuffer) {
        return new Immediate(fallbackBuffer, layerBuffers);
    }

    public VertexConsumer getBuffer(RenderLayer var1);

    public static class Immediate
    implements VertexConsumerProvider {
        protected final BufferBuilder fallbackBuffer;
        protected final Map<RenderLayer, BufferBuilder> layerBuffers;
        protected Optional<RenderLayer> currentLayer = Optional.empty();
        protected final Set<BufferBuilder> activeConsumers = Sets.newHashSet();

        protected Immediate(BufferBuilder fallbackBuffer, Map<RenderLayer, BufferBuilder> layerBuffers) {
            this.fallbackBuffer = fallbackBuffer;
            this.layerBuffers = layerBuffers;
        }

        @Override
        public VertexConsumer getBuffer(RenderLayer renderLayer) {
            Optional<RenderLayer> optional = renderLayer.asOptional();
            BufferBuilder _snowman2 = this.getBufferInternal(renderLayer);
            if (!Objects.equals(this.currentLayer, optional)) {
                if (this.currentLayer.isPresent() && !this.layerBuffers.containsKey(_snowman = this.currentLayer.get())) {
                    this.draw(_snowman);
                }
                if (this.activeConsumers.add(_snowman2)) {
                    _snowman2.begin(renderLayer.getDrawMode(), renderLayer.getVertexFormat());
                }
                this.currentLayer = optional;
            }
            return _snowman2;
        }

        private BufferBuilder getBufferInternal(RenderLayer layer) {
            return this.layerBuffers.getOrDefault(layer, this.fallbackBuffer);
        }

        public void draw() {
            this.currentLayer.ifPresent(renderLayer -> {
                VertexConsumer vertexConsumer = this.getBuffer((RenderLayer)renderLayer);
                if (vertexConsumer == this.fallbackBuffer) {
                    this.draw((RenderLayer)renderLayer);
                }
            });
            for (RenderLayer renderLayer2 : this.layerBuffers.keySet()) {
                this.draw(renderLayer2);
            }
        }

        public void draw(RenderLayer layer) {
            BufferBuilder bufferBuilder = this.getBufferInternal(layer);
            boolean _snowman2 = Objects.equals(this.currentLayer, layer.asOptional());
            if (!_snowman2 && bufferBuilder == this.fallbackBuffer) {
                return;
            }
            if (!this.activeConsumers.remove(bufferBuilder)) {
                return;
            }
            layer.draw(bufferBuilder, 0, 0, 0);
            if (_snowman2) {
                this.currentLayer = Optional.empty();
            }
        }
    }
}

