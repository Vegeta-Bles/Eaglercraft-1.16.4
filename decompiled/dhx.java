import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dhx extends eoo {
   private static final Logger a = LogManager.getLogger();
   private static final nr b = new of("mco.configure.world.invite.profile.name");
   private static final nr c = new of("mco.configure.world.players.error");
   private dlq p;
   private final dgq q;
   private final dhs r;
   private final dot s;
   @Nullable
   private nr t;

   public dhx(dhs var1, dot var2, dgq var3) {
      this.r = _snowman;
      this.s = _snowman;
      this.q = _snowman;
   }

   @Override
   public void d() {
      this.p.a();
   }

   @Override
   public void b() {
      this.i.m.a(true);
      this.p = new dlq(this.i.g, this.k / 2 - 100, j(2), 200, 20, null, new of("mco.configure.world.invite.profile.name"));
      this.d(this.p);
      this.b(this.p);
      this.a((dlj)(new dlj(this.k / 2 - 100, j(10), 200, 20, new of("mco.configure.world.buttons.invite"), var1 -> this.h())));
      this.a((dlj)(new dlj(this.k / 2 - 100, j(12), 200, 20, nq.d, var1 -> this.i.a(this.s))));
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   private void h() {
      dgb _snowman = dgb.a();
      if (this.p.b() != null && !this.p.b().isEmpty()) {
         try {
            dgq _snowmanx = _snowman.b(this.q.a, this.p.b().trim());
            if (_snowmanx != null) {
               this.q.h = _snowmanx.h;
               this.i.a(new did(this.r, this.q));
            } else {
               this.a(c);
            }
         } catch (Exception var3) {
            a.error("Couldn't invite user");
            this.a(c);
         }
      } else {
         this.a(c);
      }
   }

   private void a(nr var1) {
      this.t = _snowman;
      eoj.a(_snowman.getString());
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.i.a(this.s);
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.o.b(_snowman, b, (float)(this.k / 2 - 100), (float)j(1), 10526880);
      if (this.t != null) {
         a(_snowman, this.o, this.t, this.k / 2, j(5), 16711680);
      }

      this.p.a(_snowman, _snowman, _snowman, _snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
