/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  org.apache.commons.io.IOUtils
 */
package net.minecraft.client.gl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.PostProcessShader;
import net.minecraft.client.gl.ShaderParseException;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Matrix4f;
import org.apache.commons.io.IOUtils;

public class ShaderEffect
implements AutoCloseable {
    private final Framebuffer mainTarget;
    private final ResourceManager resourceManager;
    private final String name;
    private final List<PostProcessShader> passes = Lists.newArrayList();
    private final Map<String, Framebuffer> targetsByName = Maps.newHashMap();
    private final List<Framebuffer> defaultSizedTargets = Lists.newArrayList();
    private Matrix4f projectionMatrix;
    private int width;
    private int height;
    private float time;
    private float lastTickDelta;

    public ShaderEffect(TextureManager textureManager, ResourceManager resourceManager, Framebuffer framebuffer, Identifier location) throws IOException, JsonSyntaxException {
        this.resourceManager = resourceManager;
        this.mainTarget = framebuffer;
        this.time = 0.0f;
        this.lastTickDelta = 0.0f;
        this.width = framebuffer.viewportWidth;
        this.height = framebuffer.viewportHeight;
        this.name = location.toString();
        this.setupProjectionMatrix();
        this.parseEffect(textureManager, location);
    }

    private void parseEffect(TextureManager textureManager, Identifier location) throws IOException, JsonSyntaxException {
        block11: {
            Resource resource = null;
            try {
                int _snowman2;
                JsonArray _snowman3;
                resource = this.resourceManager.getResource(location);
                JsonObject jsonObject = JsonHelper.deserialize(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
                if (JsonHelper.hasArray(jsonObject, "targets")) {
                    _snowman3 = jsonObject.getAsJsonArray("targets");
                    _snowman2 = 0;
                    for (JsonElement jsonElement : _snowman3) {
                        try {
                            this.parseTarget(jsonElement);
                        }
                        catch (Exception exception) {
                            ShaderParseException shaderParseException = ShaderParseException.wrap(exception);
                            shaderParseException.addFaultyElement("targets[" + _snowman2 + "]");
                            throw shaderParseException;
                        }
                        ++_snowman2;
                    }
                }
                if (!JsonHelper.hasArray(jsonObject, "passes")) break block11;
                _snowman3 = jsonObject.getAsJsonArray("passes");
                _snowman2 = 0;
                for (JsonElement jsonElement : _snowman3) {
                    try {
                        this.parsePass(textureManager, jsonElement);
                    }
                    catch (Exception exception) {
                        ShaderParseException shaderParseException = ShaderParseException.wrap(exception);
                        shaderParseException.addFaultyElement("passes[" + _snowman2 + "]");
                        throw shaderParseException;
                    }
                    ++_snowman2;
                }
            }
            catch (Exception exception) {
                try {
                    String string = resource != null ? " (" + resource.getResourcePackName() + ")" : "";
                    ShaderParseException _snowman4 = ShaderParseException.wrap(exception);
                    _snowman4.addFaultyFile(location.getPath() + string);
                    throw _snowman4;
                }
                catch (Throwable throwable) {
                    IOUtils.closeQuietly(resource);
                    throw throwable;
                }
            }
        }
        IOUtils.closeQuietly((Closeable)resource);
    }

    private void parseTarget(JsonElement jsonTarget) throws ShaderParseException {
        if (JsonHelper.isString(jsonTarget)) {
            this.addTarget(jsonTarget.getAsString(), this.width, this.height);
        } else {
            JsonObject jsonObject = JsonHelper.asObject(jsonTarget, "target");
            String _snowman2 = JsonHelper.getString(jsonObject, "name");
            int _snowman3 = JsonHelper.getInt(jsonObject, "width", this.width);
            int _snowman4 = JsonHelper.getInt(jsonObject, "height", this.height);
            if (this.targetsByName.containsKey(_snowman2)) {
                throw new ShaderParseException(_snowman2 + " is already defined");
            }
            this.addTarget(_snowman2, _snowman3, _snowman4);
        }
    }

    private void parsePass(TextureManager textureManager, JsonElement jsonPass) throws IOException {
        JsonArray jsonArray;
        Object _snowman11;
        block21: {
            JsonObject jsonObject = JsonHelper.asObject(jsonPass, "pass");
            String _snowman2 = JsonHelper.getString(jsonObject, "name");
            String _snowman3 = JsonHelper.getString(jsonObject, "intarget");
            String _snowman4 = JsonHelper.getString(jsonObject, "outtarget");
            Framebuffer _snowman5 = this.getTarget(_snowman3);
            Framebuffer _snowman6 = this.getTarget(_snowman4);
            if (_snowman5 == null) {
                throw new ShaderParseException("Input target '" + _snowman3 + "' does not exist");
            }
            if (_snowman6 == null) {
                throw new ShaderParseException("Output target '" + _snowman4 + "' does not exist");
            }
            PostProcessShader _snowman7 = this.addPass(_snowman2, _snowman5, _snowman6);
            JsonArray _snowman8 = JsonHelper.getArray(jsonObject, "auxtargets", null);
            if (_snowman8 == null) break block21;
            int _snowman9 = 0;
            for (JsonElement jsonElement : _snowman8) {
                block20: {
                    Object _snowman10;
                    try {
                        block22: {
                            Object _snowman12;
                            JsonObject jsonObject2 = JsonHelper.asObject(jsonElement, "auxtarget");
                            _snowman10 = JsonHelper.getString(jsonObject2, "name");
                            _snowman11 = JsonHelper.getString(jsonObject2, "id");
                            if (((String)_snowman11).endsWith(":depth")) {
                                boolean bl = true;
                                _snowman12 = ((String)_snowman11).substring(0, ((String)_snowman11).lastIndexOf(58));
                            } else {
                                bl = false;
                                _snowman12 = _snowman11;
                            }
                            Framebuffer framebuffer = this.getTarget((String)_snowman12);
                            if (framebuffer != null) break block22;
                            if (bl) {
                                throw new ShaderParseException("Render target '" + (String)_snowman12 + "' can't be used as depth buffer");
                            }
                            Identifier _snowman13 = new Identifier("textures/effect/" + (String)_snowman12 + ".png");
                            Resource _snowman14 = null;
                            try {
                                _snowman14 = this.resourceManager.getResource(_snowman13);
                            }
                            catch (FileNotFoundException _snowman15) {
                                try {
                                    throw new ShaderParseException("Render target or texture '" + (String)_snowman12 + "' does not exist");
                                }
                                catch (Throwable throwable) {
                                    IOUtils.closeQuietly(_snowman14);
                                    throw throwable;
                                }
                            }
                            IOUtils.closeQuietly((Closeable)_snowman14);
                            textureManager.bindTexture(_snowman13);
                            AbstractTexture _snowman16 = textureManager.getTexture(_snowman13);
                            int _snowman17 = JsonHelper.getInt(jsonObject2, "width");
                            int _snowman18 = JsonHelper.getInt(jsonObject2, "height");
                            boolean _snowman19 = JsonHelper.getBoolean(jsonObject2, "bilinear");
                            if (_snowman19) {
                                RenderSystem.texParameter(3553, 10241, 9729);
                                RenderSystem.texParameter(3553, 10240, 9729);
                            } else {
                                RenderSystem.texParameter(3553, 10241, 9728);
                                RenderSystem.texParameter(3553, 10240, 9728);
                            }
                            _snowman7.addAuxTarget((String)_snowman10, _snowman16::getGlId, _snowman17, _snowman18);
                            break block20;
                        }
                        if (bl) {
                            _snowman7.addAuxTarget((String)_snowman10, framebuffer::getDepthAttachment, framebuffer.textureWidth, framebuffer.textureHeight);
                        } else {
                            _snowman7.addAuxTarget((String)_snowman10, framebuffer::getColorAttachment, framebuffer.textureWidth, framebuffer.textureHeight);
                        }
                    }
                    catch (Exception exception) {
                        _snowman10 = ShaderParseException.wrap(exception);
                        ((ShaderParseException)_snowman10).addFaultyElement("auxtargets[" + _snowman9 + "]");
                        throw _snowman10;
                    }
                }
                ++_snowman9;
            }
        }
        if ((jsonArray = JsonHelper.getArray(jsonObject, "uniforms", null)) != null) {
            int n = 0;
            for (JsonObject jsonObject2 : jsonArray) {
                try {
                    this.parseUniform((JsonElement)jsonObject2);
                }
                catch (Exception exception) {
                    _snowman11 = ShaderParseException.wrap(exception);
                    ((ShaderParseException)_snowman11).addFaultyElement("uniforms[" + n + "]");
                    throw _snowman11;
                }
                ++n;
            }
        }
    }

    private void parseUniform(JsonElement jsonUniform) throws ShaderParseException {
        JsonObject jsonObject = JsonHelper.asObject(jsonUniform, "uniform");
        String _snowman2 = JsonHelper.getString(jsonObject, "name");
        GlUniform _snowman3 = this.passes.get(this.passes.size() - 1).getProgram().getUniformByName(_snowman2);
        if (_snowman3 == null) {
            throw new ShaderParseException("Uniform '" + _snowman2 + "' does not exist");
        }
        float[] _snowman4 = new float[4];
        int _snowman5 = 0;
        JsonArray _snowman6 = JsonHelper.getArray(jsonObject, "values");
        for (JsonElement jsonElement : _snowman6) {
            try {
                _snowman4[_snowman5] = JsonHelper.asFloat(jsonElement, "value");
            }
            catch (Exception exception) {
                ShaderParseException shaderParseException = ShaderParseException.wrap(exception);
                shaderParseException.addFaultyElement("values[" + _snowman5 + "]");
                throw shaderParseException;
            }
            ++_snowman5;
        }
        switch (_snowman5) {
            case 0: {
                break;
            }
            case 1: {
                _snowman3.set(_snowman4[0]);
                break;
            }
            case 2: {
                _snowman3.set(_snowman4[0], _snowman4[1]);
                break;
            }
            case 3: {
                _snowman3.set(_snowman4[0], _snowman4[1], _snowman4[2]);
                break;
            }
            case 4: {
                _snowman3.set(_snowman4[0], _snowman4[1], _snowman4[2], _snowman4[3]);
            }
        }
    }

    public Framebuffer getSecondaryTarget(String name) {
        return this.targetsByName.get(name);
    }

    public void addTarget(String name, int width, int height) {
        Framebuffer framebuffer = new Framebuffer(width, height, true, MinecraftClient.IS_SYSTEM_MAC);
        framebuffer.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.targetsByName.put(name, framebuffer);
        if (width == this.width && height == this.height) {
            this.defaultSizedTargets.add(framebuffer);
        }
    }

    @Override
    public void close() {
        for (Framebuffer framebuffer : this.targetsByName.values()) {
            framebuffer.delete();
        }
        for (PostProcessShader postProcessShader : this.passes) {
            postProcessShader.close();
        }
        this.passes.clear();
    }

    public PostProcessShader addPass(String programName, Framebuffer source, Framebuffer dest) throws IOException {
        PostProcessShader postProcessShader = new PostProcessShader(this.resourceManager, programName, source, dest);
        this.passes.add(this.passes.size(), postProcessShader);
        return postProcessShader;
    }

    private void setupProjectionMatrix() {
        this.projectionMatrix = Matrix4f.projectionMatrix(this.mainTarget.textureWidth, this.mainTarget.textureHeight, 0.1f, 1000.0f);
    }

    public void setupDimensions(int targetsWidth, int targetsHeight) {
        this.width = this.mainTarget.textureWidth;
        this.height = this.mainTarget.textureHeight;
        this.setupProjectionMatrix();
        for (PostProcessShader postProcessShader : this.passes) {
            postProcessShader.setProjectionMatrix(this.projectionMatrix);
        }
        for (Framebuffer framebuffer : this.defaultSizedTargets) {
            framebuffer.resize(targetsWidth, targetsHeight, MinecraftClient.IS_SYSTEM_MAC);
        }
    }

    public void render(float tickDelta) {
        if (tickDelta < this.lastTickDelta) {
            this.time += 1.0f - this.lastTickDelta;
            this.time += tickDelta;
        } else {
            this.time += tickDelta - this.lastTickDelta;
        }
        this.lastTickDelta = tickDelta;
        while (this.time > 20.0f) {
            this.time -= 20.0f;
        }
        for (PostProcessShader postProcessShader : this.passes) {
            postProcessShader.render(this.time / 20.0f);
        }
    }

    public final String getName() {
        return this.name;
    }

    private Framebuffer getTarget(String name) {
        if (name == null) {
            return null;
        }
        if (name.equals("minecraft:main")) {
            return this.mainTarget;
        }
        return this.targetsByName.get(name);
    }
}

