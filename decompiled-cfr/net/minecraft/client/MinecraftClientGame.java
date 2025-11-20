/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.bridge.Bridge
 *  com.mojang.bridge.game.GameSession
 *  com.mojang.bridge.game.GameVersion
 *  com.mojang.bridge.game.Language
 *  com.mojang.bridge.game.PerformanceMetrics
 *  com.mojang.bridge.game.RunningGame
 *  com.mojang.bridge.launcher.Launcher
 *  com.mojang.bridge.launcher.SessionEventListener
 *  javax.annotation.Nullable
 */
package net.minecraft.client;

import com.mojang.bridge.Bridge;
import com.mojang.bridge.game.GameSession;
import com.mojang.bridge.game.GameVersion;
import com.mojang.bridge.game.Language;
import com.mojang.bridge.game.PerformanceMetrics;
import com.mojang.bridge.game.RunningGame;
import com.mojang.bridge.launcher.Launcher;
import com.mojang.bridge.launcher.SessionEventListener;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.ClientGameSession;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.MetricsData;

public class MinecraftClientGame
implements RunningGame {
    private final MinecraftClient client;
    @Nullable
    private final Launcher launcher;
    private SessionEventListener listener = SessionEventListener.NONE;

    public MinecraftClientGame(MinecraftClient client) {
        this.client = client;
        this.launcher = Bridge.getLauncher();
        if (this.launcher != null) {
            this.launcher.registerGame((RunningGame)this);
        }
    }

    public GameVersion getVersion() {
        return SharedConstants.getGameVersion();
    }

    public Language getSelectedLanguage() {
        return this.client.getLanguageManager().getLanguage();
    }

    @Nullable
    public GameSession getCurrentSession() {
        ClientWorld clientWorld = this.client.world;
        return clientWorld == null ? null : new ClientGameSession(clientWorld, this.client.player, this.client.player.networkHandler);
    }

    public PerformanceMetrics getPerformanceMetrics() {
        MetricsData metricsData = this.client.getMetricsData();
        long _snowman2 = Integer.MAX_VALUE;
        long _snowman3 = Integer.MIN_VALUE;
        long _snowman4 = 0L;
        for (long l : metricsData.getSamples()) {
            _snowman2 = Math.min(_snowman2, l);
            _snowman3 = Math.max(_snowman3, l);
            _snowman4 += l;
        }
        return new PerformanceMetricsImpl((int)_snowman2, (int)_snowman3, (int)(_snowman4 / (long)metricsData.getSamples().length), metricsData.getSamples().length);
    }

    public void setSessionEventListener(SessionEventListener listener) {
        this.listener = listener;
    }

    public void onStartGameSession() {
        this.listener.onStartGameSession(this.getCurrentSession());
    }

    public void onLeaveGameSession() {
        this.listener.onLeaveGameSession(this.getCurrentSession());
    }

    static class PerformanceMetricsImpl
    implements PerformanceMetrics {
        private final int minTime;
        private final int maxTime;
        private final int averageTime;
        private final int sampleCount;

        public PerformanceMetricsImpl(int minTime, int maxTime, int averageTime, int sampleCount) {
            this.minTime = minTime;
            this.maxTime = maxTime;
            this.averageTime = averageTime;
            this.sampleCount = sampleCount;
        }

        public int getMinTime() {
            return this.minTime;
        }

        public int getMaxTime() {
            return this.maxTime;
        }

        public int getAverageTime() {
            return this.averageTime;
        }

        public int getSampleCount() {
            return this.sampleCount;
        }
    }
}

