/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.client.gl;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import net.minecraft.client.gl.GlProgram;
import net.minecraft.client.texture.TextureUtil;
import org.apache.commons.lang3.StringUtils;

public class GlShader {
    private final Type shaderType;
    private final String name;
    private final int shaderRef;
    private int refCount;

    private GlShader(Type shaderType, int shaderRef, String name) {
        this.shaderType = shaderType;
        this.shaderRef = shaderRef;
        this.name = name;
    }

    public void attachTo(GlProgram glProgram) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        ++this.refCount;
        GlStateManager.attachShader(glProgram.getProgramRef(), this.shaderRef);
    }

    public void release() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        --this.refCount;
        if (this.refCount <= 0) {
            GlStateManager.deleteShader(this.shaderRef);
            this.shaderType.getLoadedShaders().remove(this.name);
        }
    }

    public String getName() {
        return this.name;
    }

    public static GlShader createFromResource(Type type, String name, InputStream sourceCode, String string) throws IOException {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        _snowman = TextureUtil.readAllToString(sourceCode);
        if (_snowman == null) {
            throw new IOException("Could not load program " + type.getName());
        }
        int n = GlStateManager.createShader(type.getGlType());
        GlStateManager.shaderSource(n, _snowman);
        GlStateManager.compileShader(n);
        if (GlStateManager.getShader(n, 35713) == 0) {
            String string2 = StringUtils.trim((String)GlStateManager.getShaderInfoLog(n, 32768));
            throw new IOException("Couldn't compile " + type.getName() + " program (" + string + ", " + name + ") : " + string2);
        }
        GlShader _snowman2 = new GlShader(type, n, name);
        type.getLoadedShaders().put(name, _snowman2);
        return _snowman2;
    }

    public static enum Type {
        VERTEX("vertex", ".vsh", 35633),
        FRAGMENT("fragment", ".fsh", 35632);

        private final String name;
        private final String fileExtension;
        private final int glType;
        private final Map<String, GlShader> loadedShaders = Maps.newHashMap();

        private Type(String string2, String string3, int n2) {
            this.name = string2;
            this.fileExtension = string3;
            this.glType = n2;
        }

        public String getName() {
            return this.name;
        }

        public String getFileExtension() {
            return this.fileExtension;
        }

        private int getGlType() {
            return this.glType;
        }

        public Map<String, GlShader> getLoadedShaders() {
            return this.loadedShaders;
        }
    }
}

