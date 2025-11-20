/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gl;

import net.minecraft.client.gl.GlShader;

public interface GlProgram {
    public int getProgramRef();

    public void markUniformsDirty();

    public GlShader getVertexShader();

    public GlShader getFragmentShader();
}

