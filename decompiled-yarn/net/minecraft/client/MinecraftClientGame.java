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
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.MetricsData;

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
      ClientWorld _snowman = this.client.world;
      return _snowman == null ? null : new ClientGameSession(_snowman, this.client.player, this.client.player.networkHandler);
   }

   public PerformanceMetrics getPerformanceMetrics() {
      MetricsData _snowman = this.client.getMetricsData();
      long _snowmanx = 2147483647L;
      long _snowmanxx = -2147483648L;
      long _snowmanxxx = 0L;

      for (long _snowmanxxxx : _snowman.getSamples()) {
         _snowmanx = Math.min(_snowmanx, _snowmanxxxx);
         _snowmanxx = Math.max(_snowmanxx, _snowmanxxxx);
         _snowmanxxx += _snowmanxxxx;
      }

      return new MinecraftClientGame.PerformanceMetricsImpl((int)_snowmanx, (int)_snowmanxx, (int)(_snowmanxxx / (long)_snowman.getSamples().length), _snowman.getSamples().length);
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
