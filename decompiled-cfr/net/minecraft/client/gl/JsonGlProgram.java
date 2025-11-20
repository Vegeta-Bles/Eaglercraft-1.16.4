/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  javax.annotation.Nullable
 *  org.apache.commons.io.IOUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.gl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.client.gl.GlBlendState;
import net.minecraft.client.gl.GlProgram;
import net.minecraft.client.gl.GlProgramManager;
import net.minecraft.client.gl.GlShader;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderParseException;
import net.minecraft.client.gl.Uniform;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonGlProgram
implements GlProgram,
AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Uniform dummyUniform = new Uniform();
    private static JsonGlProgram activeProgram;
    private static int activeProgramRef;
    private final Map<String, IntSupplier> samplerBinds = Maps.newHashMap();
    private final List<String> samplerNames = Lists.newArrayList();
    private final List<Integer> samplerShaderLocs = Lists.newArrayList();
    private final List<GlUniform> uniformData = Lists.newArrayList();
    private final List<Integer> uniformLocs = Lists.newArrayList();
    private final Map<String, GlUniform> uniformByName = Maps.newHashMap();
    private final int programRef;
    private final String name;
    private boolean uniformStateDirty;
    private final GlBlendState blendState;
    private final List<Integer> attribLocs;
    private final List<String> attribNames;
    private final GlShader vertexShader;
    private final GlShader fragmentShader;

    public JsonGlProgram(ResourceManager resource, String name) throws IOException {
        Identifier identifier = new Identifier("shaders/program/" + name + ".json");
        this.name = name;
        Resource _snowman2 = null;
        try {
            JsonArray jsonArray;
            JsonArray jsonArray2;
            _snowman2 = resource.getResource(identifier);
            JsonObject jsonObject = JsonHelper.deserialize(new InputStreamReader(_snowman2.getInputStream(), StandardCharsets.UTF_8));
            String _snowman3 = JsonHelper.getString(jsonObject, "vertex");
            String _snowman4 = JsonHelper.getString(jsonObject, "fragment");
            JsonArray _snowman5 = JsonHelper.getArray(jsonObject, "samplers", null);
            if (_snowman5 != null) {
                int n = 0;
                for (Object object : _snowman5) {
                    try {
                        this.addSampler((JsonElement)object);
                    }
                    catch (Exception exception) {
                        ShaderParseException shaderParseException = ShaderParseException.wrap(exception);
                        shaderParseException.addFaultyElement("samplers[" + n + "]");
                        throw shaderParseException;
                    }
                    ++n;
                }
            }
            if ((jsonArray2 = JsonHelper.getArray(jsonObject, "attributes", null)) != null) {
                int n = 0;
                this.attribLocs = Lists.newArrayListWithCapacity((int)jsonArray2.size());
                this.attribNames = Lists.newArrayListWithCapacity((int)jsonArray2.size());
                for (JsonElement jsonElement : jsonArray2) {
                    try {
                        this.attribNames.add(JsonHelper.asString(jsonElement, "attribute"));
                    }
                    catch (Exception exception) {
                        ShaderParseException shaderParseException = ShaderParseException.wrap(exception);
                        shaderParseException.addFaultyElement("attributes[" + n + "]");
                        throw shaderParseException;
                    }
                    ++n;
                }
            } else {
                this.attribLocs = null;
                this.attribNames = null;
            }
            if ((jsonArray = JsonHelper.getArray(jsonObject, "uniforms", null)) != null) {
                int n = 0;
                for (JsonElement jsonElement : jsonArray) {
                    try {
                        this.addUniform(jsonElement);
                    }
                    catch (Exception exception) {
                        ShaderParseException shaderParseException = ShaderParseException.wrap(exception);
                        shaderParseException.addFaultyElement("uniforms[" + n + "]");
                        throw shaderParseException;
                    }
                    ++n;
                }
            }
            this.blendState = JsonGlProgram.deserializeBlendState(JsonHelper.getObject(jsonObject, "blend", null));
            this.vertexShader = JsonGlProgram.getShader(resource, GlShader.Type.VERTEX, _snowman3);
            this.fragmentShader = JsonGlProgram.getShader(resource, GlShader.Type.FRAGMENT, _snowman4);
            this.programRef = GlProgramManager.createProgram();
            GlProgramManager.linkProgram(this);
            this.finalizeUniformsAndSamplers();
            if (this.attribNames != null) {
                for (String string : this.attribNames) {
                    int n = GlUniform.getAttribLocation(this.programRef, string);
                    this.attribLocs.add(n);
                }
            }
        }
        catch (Exception exception) {
            String string;
            if (_snowman2 != null) {
                String string2 = " (" + _snowman2.getResourcePackName() + ")";
            } else {
                string = "";
            }
            ShaderParseException _snowman6 = ShaderParseException.wrap(exception);
            _snowman6.addFaultyFile(identifier.getPath() + string);
            throw _snowman6;
        }
        finally {
            IOUtils.closeQuietly((Closeable)_snowman2);
        }
        this.markUniformsDirty();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static GlShader getShader(ResourceManager resourceManager, GlShader.Type type, String name) throws IOException {
        GlShader glShader = type.getLoadedShaders().get(name);
        if (glShader == null) {
            Identifier identifier = new Identifier("shaders/program/" + name + type.getFileExtension());
            Resource _snowman2 = resourceManager.getResource(identifier);
            try {
                glShader = GlShader.createFromResource(type, name, _snowman2.getInputStream(), _snowman2.getResourcePackName());
            }
            finally {
                IOUtils.closeQuietly((Closeable)_snowman2);
            }
        }
        return glShader;
    }

    public static GlBlendState deserializeBlendState(JsonObject json) {
        if (json == null) {
            return new GlBlendState();
        }
        int n = 32774;
        _snowman = 1;
        _snowman = 0;
        _snowman = 1;
        _snowman = 0;
        boolean _snowman2 = true;
        boolean _snowman3 = false;
        if (JsonHelper.hasString(json, "func") && (n = GlBlendState.getFuncFromString(json.get("func").getAsString())) != 32774) {
            _snowman2 = false;
        }
        if (JsonHelper.hasString(json, "srcrgb") && (_snowman = GlBlendState.getComponentFromString(json.get("srcrgb").getAsString())) != 1) {
            _snowman2 = false;
        }
        if (JsonHelper.hasString(json, "dstrgb") && (_snowman = GlBlendState.getComponentFromString(json.get("dstrgb").getAsString())) != 0) {
            _snowman2 = false;
        }
        if (JsonHelper.hasString(json, "srcalpha")) {
            _snowman = GlBlendState.getComponentFromString(json.get("srcalpha").getAsString());
            if (_snowman != 1) {
                _snowman2 = false;
            }
            _snowman3 = true;
        }
        if (JsonHelper.hasString(json, "dstalpha")) {
            _snowman = GlBlendState.getComponentFromString(json.get("dstalpha").getAsString());
            if (_snowman != 0) {
                _snowman2 = false;
            }
            _snowman3 = true;
        }
        if (_snowman2) {
            return new GlBlendState();
        }
        if (_snowman3) {
            return new GlBlendState(_snowman, _snowman, _snowman, _snowman, n);
        }
        return new GlBlendState(_snowman, _snowman, n);
    }

    @Override
    public void close() {
        for (GlUniform glUniform : this.uniformData) {
            glUniform.close();
        }
        GlProgramManager.deleteProgram(this);
    }

    public void disable() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlProgramManager.useProgram(0);
        activeProgramRef = -1;
        activeProgram = null;
        for (int i = 0; i < this.samplerShaderLocs.size(); ++i) {
            if (this.samplerBinds.get(this.samplerNames.get(i)) == null) continue;
            GlStateManager.activeTexture(33984 + i);
            GlStateManager.disableTexture();
            GlStateManager.bindTexture(0);
        }
    }

    public void enable() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        this.uniformStateDirty = false;
        activeProgram = this;
        this.blendState.enable();
        if (this.programRef != activeProgramRef) {
            GlProgramManager.useProgram(this.programRef);
            activeProgramRef = this.programRef;
        }
        for (int i = 0; i < this.samplerShaderLocs.size(); ++i) {
            String string = this.samplerNames.get(i);
            IntSupplier _snowman2 = this.samplerBinds.get(string);
            if (_snowman2 == null) continue;
            RenderSystem.activeTexture(33984 + i);
            RenderSystem.enableTexture();
            int _snowman3 = _snowman2.getAsInt();
            if (_snowman3 == -1) continue;
            RenderSystem.bindTexture(_snowman3);
            GlUniform.uniform1(this.samplerShaderLocs.get(i), i);
        }
        for (GlUniform glUniform : this.uniformData) {
            glUniform.upload();
        }
    }

    @Override
    public void markUniformsDirty() {
        this.uniformStateDirty = true;
    }

    @Nullable
    public GlUniform getUniformByName(String name) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return this.uniformByName.get(name);
    }

    public Uniform getUniformByNameOrDummy(String name) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        GlUniform glUniform = this.getUniformByName(name);
        return glUniform == null ? dummyUniform : glUniform;
    }

    private void finalizeUniformsAndSamplers() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        IntArrayList intArrayList = new IntArrayList();
        for (int n = 0; n < this.samplerNames.size(); ++n) {
            String string = this.samplerNames.get(n);
            int _snowman2 = GlUniform.getUniformLocation(this.programRef, string);
            if (_snowman2 == -1) {
                LOGGER.warn("Shader {} could not find sampler named {} in the specified shader program.", (Object)this.name, (Object)string);
                this.samplerBinds.remove(string);
                intArrayList.add(n);
                continue;
            }
            this.samplerShaderLocs.add(_snowman2);
        }
        for (int i = intArrayList.size() - 1; i >= 0; --i) {
            this.samplerNames.remove(intArrayList.getInt(i));
        }
        for (GlUniform glUniform : this.uniformData) {
            String string = glUniform.getName();
            int _snowman3 = GlUniform.getUniformLocation(this.programRef, string);
            if (_snowman3 == -1) {
                LOGGER.warn("Could not find uniform named {} in the specified shader program.", (Object)string);
                continue;
            }
            this.uniformLocs.add(_snowman3);
            glUniform.setLoc(_snowman3);
            this.uniformByName.put(string, glUniform);
        }
    }

    private void addSampler(JsonElement jsonElement) {
        JsonObject jsonObject = JsonHelper.asObject(jsonElement, "sampler");
        String _snowman2 = JsonHelper.getString(jsonObject, "name");
        if (!JsonHelper.hasString(jsonObject, "file")) {
            this.samplerBinds.put(_snowman2, null);
            this.samplerNames.add(_snowman2);
            return;
        }
        this.samplerNames.add(_snowman2);
    }

    public void bindSampler(String samplerName, IntSupplier intSupplier) {
        if (this.samplerBinds.containsKey(samplerName)) {
            this.samplerBinds.remove(samplerName);
        }
        this.samplerBinds.put(samplerName, intSupplier);
        this.markUniformsDirty();
    }

    private void addUniform(JsonElement jsonElement) throws ShaderParseException {
        Object _snowman82;
        JsonObject jsonObject = JsonHelper.asObject(jsonElement, "uniform");
        String _snowman2 = JsonHelper.getString(jsonObject, "name");
        int _snowman3 = GlUniform.getTypeIndex(JsonHelper.getString(jsonObject, "type"));
        int _snowman4 = JsonHelper.getInt(jsonObject, "count");
        float[] _snowman5 = new float[Math.max(_snowman4, 16)];
        JsonArray _snowman6 = JsonHelper.getArray(jsonObject, "values");
        if (_snowman6.size() != _snowman4 && _snowman6.size() > 1) {
            throw new ShaderParseException("Invalid amount of values specified (expected " + _snowman4 + ", found " + _snowman6.size() + ")");
        }
        int _snowman7 = 0;
        for (Object _snowman82 : _snowman6) {
            try {
                _snowman5[_snowman7] = JsonHelper.asFloat((JsonElement)_snowman82, "value");
            }
            catch (Exception exception) {
                ShaderParseException shaderParseException = ShaderParseException.wrap(exception);
                shaderParseException.addFaultyElement("values[" + _snowman7 + "]");
                throw shaderParseException;
            }
            ++_snowman7;
        }
        if (_snowman4 > 1 && _snowman6.size() == 1) {
            while (_snowman7 < _snowman4) {
                _snowman5[_snowman7] = _snowman5[0];
                ++_snowman7;
            }
        }
        int n = _snowman4 > 1 && _snowman4 <= 4 && _snowman3 < 8 ? _snowman4 - 1 : 0;
        _snowman82 = new GlUniform(_snowman2, _snowman3 + n, _snowman4, this);
        if (_snowman3 <= 3) {
            ((GlUniform)_snowman82).set((int)_snowman5[0], (int)_snowman5[1], (int)_snowman5[2], (int)_snowman5[3]);
        } else if (_snowman3 <= 7) {
            ((GlUniform)_snowman82).setForDataType(_snowman5[0], _snowman5[1], _snowman5[2], _snowman5[3]);
        } else {
            ((GlUniform)_snowman82).set(_snowman5);
        }
        this.uniformData.add((GlUniform)_snowman82);
    }

    @Override
    public GlShader getVertexShader() {
        return this.vertexShader;
    }

    @Override
    public GlShader getFragmentShader() {
        return this.fragmentShader;
    }

    @Override
    public int getProgramRef() {
        return this.programRef;
    }

    static {
        activeProgramRef = -1;
    }
}

