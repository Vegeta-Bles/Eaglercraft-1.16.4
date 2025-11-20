/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  org.lwjgl.system.MemoryUtil
 */
package net.minecraft.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.nio.ByteBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormat;
import org.lwjgl.system.MemoryUtil;

public class BufferRenderer {
    public static void draw(BufferBuilder bufferBuilder) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> {
                Pair<BufferBuilder.DrawArrayParameters, ByteBuffer> pair = bufferBuilder.popData();
                BufferBuilder.DrawArrayParameters _snowman2 = (BufferBuilder.DrawArrayParameters)pair.getFirst();
                BufferRenderer.draw((ByteBuffer)pair.getSecond(), _snowman2.getMode(), _snowman2.getVertexFormat(), _snowman2.getCount());
            });
        } else {
            Pair<BufferBuilder.DrawArrayParameters, ByteBuffer> pair = bufferBuilder.popData();
            BufferBuilder.DrawArrayParameters _snowman2 = (BufferBuilder.DrawArrayParameters)pair.getFirst();
            BufferRenderer.draw((ByteBuffer)pair.getSecond(), _snowman2.getMode(), _snowman2.getVertexFormat(), _snowman2.getCount());
        }
    }

    private static void draw(ByteBuffer buffer, int mode, VertexFormat vertexFormat, int count) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        buffer.clear();
        if (count <= 0) {
            return;
        }
        vertexFormat.startDrawing(MemoryUtil.memAddress((ByteBuffer)buffer));
        GlStateManager.drawArrays(mode, 0, count);
        vertexFormat.endDrawing();
    }
}

