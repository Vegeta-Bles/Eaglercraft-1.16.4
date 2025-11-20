/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  org.apache.commons.io.IOUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.Resource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreditsScreen
extends Screen {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Identifier MINECRAFT_TITLE_TEXTURE = new Identifier("textures/gui/title/minecraft.png");
    private static final Identifier EDITION_TITLE_TEXTURE = new Identifier("textures/gui/title/edition.png");
    private static final Identifier VIGNETTE_TEXTURE = new Identifier("textures/misc/vignette.png");
    private static final String OBFUSCATION_PLACEHOLDER = "" + (Object)((Object)Formatting.WHITE) + (Object)((Object)Formatting.OBFUSCATED) + (Object)((Object)Formatting.GREEN) + (Object)((Object)Formatting.AQUA);
    private final boolean endCredits;
    private final Runnable finishAction;
    private float time;
    private List<OrderedText> credits;
    private IntSet centeredLines;
    private int creditsHeight;
    private float speed = 0.5f;

    public CreditsScreen(boolean endCredits, Runnable finishAction) {
        super(NarratorManager.EMPTY);
        this.endCredits = endCredits;
        this.finishAction = finishAction;
        if (!endCredits) {
            this.speed = 0.75f;
        }
    }

    @Override
    public void tick() {
        this.client.getMusicTracker().tick();
        this.client.getSoundManager().tick(false);
        float f = (float)(this.creditsHeight + this.height + this.height + 24) / this.speed;
        if (this.time > f) {
            this.close();
        }
    }

    @Override
    public void onClose() {
        this.close();
    }

    private void close() {
        this.finishAction.run();
        this.client.openScreen(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void init() {
        if (this.credits != null) {
            return;
        }
        this.credits = Lists.newArrayList();
        this.centeredLines = new IntOpenHashSet();
        Resource resource = null;
        try {
            String _snowman4;
            int n = 274;
            if (this.endCredits) {
                String _snowman5;
                resource = this.client.getResourceManager().getResource(new Identifier("texts/end.txt"));
                InputStream inputStream = resource.getInputStream();
                BufferedReader _snowman6 = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                Random _snowman7 = new Random(8124371L);
                while ((_snowman5 = _snowman6.readLine()) != null) {
                    _snowman5 = _snowman5.replaceAll("PLAYERNAME", this.client.getSession().getUsername());
                    while ((_snowman = _snowman5.indexOf(OBFUSCATION_PLACEHOLDER)) != -1) {
                        String string = _snowman5.substring(0, _snowman);
                        String string2 = _snowman5.substring(_snowman + OBFUSCATION_PLACEHOLDER.length());
                        _snowman5 = string + (Object)((Object)Formatting.WHITE) + (Object)((Object)Formatting.OBFUSCATED) + "XXXXXXXX".substring(0, _snowman7.nextInt(4) + 3) + string2;
                    }
                    this.credits.addAll(this.client.textRenderer.wrapLines(new LiteralText(_snowman5), 274));
                    this.credits.add(OrderedText.EMPTY);
                }
                inputStream.close();
                for (int i = 0; i < 8; ++i) {
                    this.credits.add(OrderedText.EMPTY);
                }
            }
            InputStream _snowman2 = this.client.getResourceManager().getResource(new Identifier("texts/credits.txt")).getInputStream();
            BufferedReader _snowman3 = new BufferedReader(new InputStreamReader(_snowman2, StandardCharsets.UTF_8));
            while ((_snowman4 = _snowman3.readLine()) != null) {
                boolean bl;
                _snowman4 = _snowman4.replaceAll("PLAYERNAME", this.client.getSession().getUsername());
                if ((_snowman4 = _snowman4.replaceAll("\t", "    ")).startsWith("[C]")) {
                    _snowman4 = _snowman4.substring(3);
                    boolean bl2 = true;
                } else {
                    bl = false;
                }
                List<OrderedText> list = this.client.textRenderer.wrapLines(new LiteralText(_snowman4), 274);
                for (OrderedText orderedText : list) {
                    if (bl) {
                        this.centeredLines.add(this.credits.size());
                    }
                    this.credits.add(orderedText);
                }
                this.credits.add(OrderedText.EMPTY);
            }
            _snowman2.close();
            this.creditsHeight = this.credits.size() * 12;
            IOUtils.closeQuietly((Closeable)resource);
        }
        catch (Exception exception) {
            LOGGER.error("Couldn't load credits", (Throwable)exception);
        }
        finally {
            IOUtils.closeQuietly(resource);
        }
    }

    private void renderBackground(int mouseX, int mouseY, float tickDelta) {
        this.client.getTextureManager().bindTexture(DrawableHelper.OPTIONS_BACKGROUND_TEXTURE);
        int n = this.width;
        float _snowman2 = -this.time * 0.5f * this.speed;
        float _snowman3 = (float)this.height - this.time * 0.5f * this.speed;
        float _snowman4 = 0.015625f;
        float _snowman5 = this.time * 0.02f;
        float _snowman6 = (float)(this.creditsHeight + this.height + this.height + 24) / this.speed;
        float _snowman7 = (_snowman6 - 20.0f - this.time) * 0.005f;
        if (_snowman7 < _snowman5) {
            _snowman5 = _snowman7;
        }
        if (_snowman5 > 1.0f) {
            _snowman5 = 1.0f;
        }
        _snowman5 *= _snowman5;
        _snowman5 = _snowman5 * 96.0f / 255.0f;
        Tessellator _snowman8 = Tessellator.getInstance();
        BufferBuilder _snowman9 = _snowman8.getBuffer();
        _snowman9.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        _snowman9.vertex(0.0, this.height, this.getZOffset()).texture(0.0f, _snowman2 * 0.015625f).color(_snowman5, _snowman5, _snowman5, 1.0f).next();
        _snowman9.vertex(n, this.height, this.getZOffset()).texture((float)n * 0.015625f, _snowman2 * 0.015625f).color(_snowman5, _snowman5, _snowman5, 1.0f).next();
        _snowman9.vertex(n, 0.0, this.getZOffset()).texture((float)n * 0.015625f, _snowman3 * 0.015625f).color(_snowman5, _snowman5, _snowman5, 1.0f).next();
        _snowman9.vertex(0.0, 0.0, this.getZOffset()).texture(0.0f, _snowman3 * 0.015625f).color(_snowman5, _snowman5, _snowman5, 1.0f).next();
        _snowman8.draw();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(mouseX, mouseY, delta);
        int n3 = 274;
        _snowman = this.width / 2 - 137;
        _snowman = this.height + 50;
        this.time += delta;
        float _snowman2 = -this.time * this.speed;
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0f, _snowman2, 0.0f);
        this.client.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURE);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        this.method_29343(_snowman, _snowman, (n, n2) -> {
            this.drawTexture(matrices, n + 0, (int)n2, 0, 0, 155, 44);
            this.drawTexture(matrices, n + 155, (int)n2, 0, 45, 155, 44);
        });
        RenderSystem.disableBlend();
        this.client.getTextureManager().bindTexture(EDITION_TITLE_TEXTURE);
        CreditsScreen.drawTexture(matrices, _snowman + 88, _snowman + 37, 0.0f, 0.0f, 98, 14, 128, 16);
        RenderSystem.disableAlphaTest();
        _snowman = _snowman + 100;
        for (n4 = 0; n4 < this.credits.size(); ++n4) {
            if (n4 == this.credits.size() - 1 && (_snowman = (float)_snowman + _snowman2 - (float)(this.height / 2 - 6)) < 0.0f) {
                RenderSystem.translatef(0.0f, -_snowman, 0.0f);
            }
            if ((float)_snowman + _snowman2 + 12.0f + 8.0f > 0.0f && (float)_snowman + _snowman2 < (float)this.height) {
                OrderedText orderedText = this.credits.get(n4);
                if (this.centeredLines.contains(n4)) {
                    this.textRenderer.drawWithShadow(matrices, orderedText, (float)(_snowman + (274 - this.textRenderer.getWidth(orderedText)) / 2), (float)_snowman, 0xFFFFFF);
                } else {
                    this.textRenderer.random.setSeed((long)((float)((long)n4 * 4238972211L) + this.time / 4.0f));
                    this.textRenderer.drawWithShadow(matrices, orderedText, (float)_snowman, (float)_snowman, 0xFFFFFF);
                }
            }
            _snowman += 12;
        }
        RenderSystem.popMatrix();
        this.client.getTextureManager().bindTexture(VIGNETTE_TEXTURE);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR);
        int n4 = this.width;
        _snowman = this.height;
        Tessellator _snowman3 = Tessellator.getInstance();
        BufferBuilder _snowman4 = _snowman3.getBuffer();
        _snowman4.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        _snowman4.vertex(0.0, _snowman, this.getZOffset()).texture(0.0f, 1.0f).color(1.0f, 1.0f, 1.0f, 1.0f).next();
        _snowman4.vertex(n4, _snowman, this.getZOffset()).texture(1.0f, 1.0f).color(1.0f, 1.0f, 1.0f, 1.0f).next();
        _snowman4.vertex(n4, 0.0, this.getZOffset()).texture(1.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).next();
        _snowman4.vertex(0.0, 0.0, this.getZOffset()).texture(0.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).next();
        _snowman3.draw();
        RenderSystem.disableBlend();
        super.render(matrices, mouseX, mouseY, delta);
    }
}

