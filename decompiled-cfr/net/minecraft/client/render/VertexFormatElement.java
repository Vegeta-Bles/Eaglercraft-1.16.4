/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import java.util.function.IntConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormatElement {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Format format;
    private final Type type;
    private final int index;
    private final int count;
    private final int size;

    public VertexFormatElement(int index, Format format, Type type, int count) {
        if (this.isValidType(index, type)) {
            this.type = type;
        } else {
            LOGGER.warn("Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
            this.type = Type.UV;
        }
        this.format = format;
        this.index = index;
        this.count = count;
        this.size = format.getSize() * this.count;
    }

    private boolean isValidType(int index, Type type) {
        return index == 0 || type == Type.UV;
    }

    public final Format getFormat() {
        return this.format;
    }

    public final Type getType() {
        return this.type;
    }

    public final int getIndex() {
        return this.index;
    }

    public String toString() {
        return this.count + "," + this.type.getName() + "," + this.format.getName();
    }

    public final int getSize() {
        return this.size;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        VertexFormatElement vertexFormatElement = (VertexFormatElement)o;
        if (this.count != vertexFormatElement.count) {
            return false;
        }
        if (this.index != vertexFormatElement.index) {
            return false;
        }
        if (this.format != vertexFormatElement.format) {
            return false;
        }
        return this.type == vertexFormatElement.type;
    }

    public int hashCode() {
        int n = this.format.hashCode();
        n = 31 * n + this.type.hashCode();
        n = 31 * n + this.index;
        n = 31 * n + this.count;
        return n;
    }

    public void startDrawing(long pointer, int stride) {
        this.type.startDrawing(this.count, this.format.getGlId(), stride, pointer, this.index);
    }

    public void endDrawing() {
        this.type.endDrawing(this.index);
    }

    public static enum Format {
        FLOAT(4, "Float", 5126),
        UBYTE(1, "Unsigned Byte", 5121),
        BYTE(1, "Byte", 5120),
        USHORT(2, "Unsigned Short", 5123),
        SHORT(2, "Short", 5122),
        UINT(4, "Unsigned Int", 5125),
        INT(4, "Int", 5124);

        private final int size;
        private final String name;
        private final int glId;

        private Format(int size, String name, int glId) {
            this.size = size;
            this.name = name;
            this.glId = glId;
        }

        public int getSize() {
            return this.size;
        }

        public String getName() {
            return this.name;
        }

        public int getGlId() {
            return this.glId;
        }
    }

    public static enum Type {
        POSITION("Position", (n, n2, n3, l, n4) -> {
            GlStateManager.vertexPointer(n, n2, n3, l);
            GlStateManager.enableClientState(32884);
        }, n -> GlStateManager.disableClientState(32884)),
        NORMAL("Normal", (n, n2, n3, l, n4) -> {
            GlStateManager.normalPointer(n2, n3, l);
            GlStateManager.enableClientState(32885);
        }, n -> GlStateManager.disableClientState(32885)),
        COLOR("Vertex Color", (n, n2, n3, l, n4) -> {
            GlStateManager.colorPointer(n, n2, n3, l);
            GlStateManager.enableClientState(32886);
        }, n -> {
            GlStateManager.disableClientState(32886);
            GlStateManager.clearCurrentColor();
        }),
        UV("UV", (n, n2, n3, l, n4) -> {
            GlStateManager.clientActiveTexture(33984 + n4);
            GlStateManager.texCoordPointer(n, n2, n3, l);
            GlStateManager.enableClientState(32888);
            GlStateManager.clientActiveTexture(33984);
        }, n -> {
            GlStateManager.clientActiveTexture(33984 + n);
            GlStateManager.disableClientState(32888);
            GlStateManager.clientActiveTexture(33984);
        }),
        PADDING("Padding", (n, n2, n3, l, n4) -> {}, n -> {}),
        GENERIC("Generic", (n, n2, n3, l, n4) -> {
            GlStateManager.enableVertexAttribArray(n4);
            GlStateManager.vertexAttribPointer(n4, n, n2, false, n3, l);
        }, GlStateManager::method_22607);

        private final String name;
        private final Starter starter;
        private final IntConsumer finisher;

        private Type(String name, Starter starter, IntConsumer intConsumer) {
            this.name = name;
            this.starter = starter;
            this.finisher = intConsumer;
        }

        private void startDrawing(int count, int glId, int stride, long pointer, int elementIndex) {
            this.starter.setupBufferState(count, glId, stride, pointer, elementIndex);
        }

        public void endDrawing(int elementIndex) {
            this.finisher.accept(elementIndex);
        }

        public String getName() {
            return this.name;
        }

        static interface Starter {
            public void setupBufferState(int var1, int var2, int var3, long var4, int var6);
        }
    }
}

