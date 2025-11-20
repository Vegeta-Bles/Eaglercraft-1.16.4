/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 */
package net.minecraft.client.gui.screen;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.client.gui.WorldGenerationProgressTracker;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.ChunkStatus;

public class LevelLoadingScreen
extends Screen {
    private final WorldGenerationProgressTracker progressProvider;
    private long field_19101 = -1L;
    private static final Object2IntMap<ChunkStatus> STATUS_TO_COLOR = (Object2IntMap)Util.make(new Object2IntOpenHashMap(), map -> {
        map.defaultReturnValue(0);
        map.put((Object)ChunkStatus.EMPTY, 0x545454);
        map.put((Object)ChunkStatus.STRUCTURE_STARTS, 0x999999);
        map.put((Object)ChunkStatus.STRUCTURE_REFERENCES, 6250897);
        map.put((Object)ChunkStatus.BIOMES, 8434258);
        map.put((Object)ChunkStatus.NOISE, 0xD1D1D1);
        map.put((Object)ChunkStatus.SURFACE, 7497737);
        map.put((Object)ChunkStatus.CARVERS, 7169628);
        map.put((Object)ChunkStatus.LIQUID_CARVERS, 3159410);
        map.put((Object)ChunkStatus.FEATURES, 2213376);
        map.put((Object)ChunkStatus.LIGHT, 0xCCCCCC);
        map.put((Object)ChunkStatus.SPAWN, 15884384);
        map.put((Object)ChunkStatus.HEIGHTMAPS, 0xEEEEEE);
        map.put((Object)ChunkStatus.FULL, 0xFFFFFF);
    });

    public LevelLoadingScreen(WorldGenerationProgressTracker progressProvider) {
        super(NarratorManager.EMPTY);
        this.progressProvider = progressProvider;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void removed() {
        NarratorManager.INSTANCE.narrate(new TranslatableText("narrator.loading.done").getString());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        String string = MathHelper.clamp(this.progressProvider.getProgressPercentage(), 0, 100) + "%";
        long _snowman2 = Util.getMeasuringTimeMs();
        if (_snowman2 - this.field_19101 > 2000L) {
            this.field_19101 = _snowman2;
            NarratorManager.INSTANCE.narrate(new TranslatableText("narrator.loading", string).getString());
        }
        int _snowman3 = this.width / 2;
        int _snowman4 = this.height / 2;
        int _snowman5 = 30;
        LevelLoadingScreen.drawChunkMap(matrices, this.progressProvider, _snowman3, _snowman4 + 30, 2, 0);
        LevelLoadingScreen.drawCenteredString(matrices, this.textRenderer, string, _snowman3, _snowman4 - this.textRenderer.fontHeight / 2 - 30, 0xFFFFFF);
    }

    public static void drawChunkMap(MatrixStack matrixStack, WorldGenerationProgressTracker worldGenerationProgressTracker, int n, int n2, int n3, int n4) {
        _snowman = n3 + n4;
        _snowman = worldGenerationProgressTracker.getCenterSize();
        _snowman = _snowman * _snowman - n4;
        _snowman = worldGenerationProgressTracker.getSize();
        _snowman = _snowman * _snowman - n4;
        _snowman = n - _snowman / 2;
        _snowman = n2 - _snowman / 2;
        _snowman = _snowman / 2 + 1;
        _snowman = -16772609;
        if (n4 != 0) {
            LevelLoadingScreen.fill(matrixStack, n - _snowman, n2 - _snowman, n - _snowman + 1, n2 + _snowman, -16772609);
            LevelLoadingScreen.fill(matrixStack, n + _snowman - 1, n2 - _snowman, n + _snowman, n2 + _snowman, -16772609);
            LevelLoadingScreen.fill(matrixStack, n - _snowman, n2 - _snowman, n + _snowman, n2 - _snowman + 1, -16772609);
            LevelLoadingScreen.fill(matrixStack, n - _snowman, n2 + _snowman - 1, n + _snowman, n2 + _snowman, -16772609);
        }
        for (_snowman = 0; _snowman < _snowman; ++_snowman) {
            for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                ChunkStatus chunkStatus = worldGenerationProgressTracker.getChunkStatus(_snowman, _snowman);
                int _snowman2 = _snowman + _snowman * _snowman;
                int _snowman3 = _snowman + _snowman * _snowman;
                LevelLoadingScreen.fill(matrixStack, _snowman2, _snowman3, _snowman2 + n3, _snowman3 + n3, STATUS_TO_COLOR.getInt((Object)chunkStatus) | 0xFF000000);
            }
        }
    }
}

