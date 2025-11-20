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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.MetricsData;

@Environment(EnvType.CLIENT)
public class MinecraftClientGame implements RunningGame {
   private final MinecraftClient client;
   @Nullable
   private final Launcher launcher;
   private SessionEventListener listener = SessionEventListener.NONE;

   public MinecraftClientGame(MinecraftClient client) {
      this.client = client;
      this.launcher = Bridge.getLauncher();
      if (this.launcher != null) {
         this.launcher.registerGame(this);
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
      ClientWorld lv = this.client.world;
      return lv == null ? null : new ClientGameSession(lv, this.client.player, this.client.player.networkHandler);
   }

   public PerformanceMetrics getPerformanceMetrics() {
      MetricsData lv = this.client.getMetricsData();
      long l = 2147483647L;
      long m = -2147483648L;
      long n = 0L;

      for (long o : lv.getSamples()) {
         l = Math.min(l, o);
         m = Math.max(m, o);
         n += o;
      }

      return new MinecraftClientGame.PerformanceMetricsImpl((int)l, (int)m, (int)(n / (long)lv.getSamples().length), lv.getSamples().length);
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

   @Environment(EnvType.CLIENT)
   static class PerformanceMetricsImpl implements PerformanceMetrics {
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
