import com.mojang.bridge.Bridge;
import com.mojang.bridge.game.GameSession;
import com.mojang.bridge.game.GameVersion;
import com.mojang.bridge.game.Language;
import com.mojang.bridge.game.PerformanceMetrics;
import com.mojang.bridge.game.RunningGame;
import com.mojang.bridge.launcher.Launcher;
import com.mojang.bridge.launcher.SessionEventListener;
import javax.annotation.Nullable;

public class djs implements RunningGame {
   private final djz a;
   @Nullable
   private final Launcher b;
   private SessionEventListener c = SessionEventListener.NONE;

   public djs(djz var1) {
      this.a = _snowman;
      this.b = Bridge.getLauncher();
      if (this.b != null) {
         this.b.registerGame(this);
      }
   }

   public GameVersion getVersion() {
      return w.a();
   }

   public Language getSelectedLanguage() {
      return this.a.R().b();
   }

   @Nullable
   public GameSession getCurrentSession() {
      dwt _snowman = this.a.r;
      return _snowman == null ? null : new dki(_snowman, this.a.s, this.a.s.e);
   }

   public PerformanceMetrics getPerformanceMetrics() {
      afc _snowman = this.a.ag();
      long _snowmanx = 2147483647L;
      long _snowmanxx = -2147483648L;
      long _snowmanxxx = 0L;

      for (long _snowmanxxxx : _snowman.c()) {
         _snowmanx = Math.min(_snowmanx, _snowmanxxxx);
         _snowmanxx = Math.max(_snowmanxx, _snowmanxxxx);
         _snowmanxxx += _snowmanxxxx;
      }

      return new djs.a((int)_snowmanx, (int)_snowmanxx, (int)(_snowmanxxx / (long)_snowman.c().length), _snowman.c().length);
   }

   public void setSessionEventListener(SessionEventListener var1) {
      this.c = _snowman;
   }

   public void a() {
      this.c.onStartGameSession(this.getCurrentSession());
   }

   public void b() {
      this.c.onLeaveGameSession(this.getCurrentSession());
   }

   static class a implements PerformanceMetrics {
      private final int a;
      private final int b;
      private final int c;
      private final int d;

      public a(int var1, int var2, int var3, int var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      public int getMinTime() {
         return this.a;
      }

      public int getMaxTime() {
         return this.b;
      }

      public int getAverageTime() {
         return this.c;
      }

      public int getSampleCount() {
         return this.d;
      }
   }
}
