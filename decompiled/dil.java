import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dil extends eoo {
   private static final Logger a = LogManager.getLogger();
   private static final nr b = new of("mco.configure.world.subscription.title");
   private static final nr c = new of("mco.configure.world.subscription.start");
   private static final nr p = new of("mco.configure.world.subscription.timeleft");
   private static final nr q = new of("mco.configure.world.subscription.recurring.daysleft");
   private static final nr r = new of("mco.configure.world.subscription.expired");
   private static final nr s = new of("mco.configure.world.subscription.less_than_a_day");
   private static final nr t = new of("mco.configure.world.subscription.month");
   private static final nr u = new of("mco.configure.world.subscription.months");
   private static final nr v = new of("mco.configure.world.subscription.day");
   private static final nr w = new of("mco.configure.world.subscription.days");
   private final dot x;
   private final dgq y;
   private final dot z;
   private nr A;
   private String B;
   private dha.a C;

   public dil(dot var1, dgq var2, dot var3) {
      this.x = _snowman;
      this.y = _snowman;
      this.z = _snowman;
   }

   @Override
   public void b() {
      this.a(this.y.a);
      eoj.a(b.getString(), c.getString(), this.B, p.getString(), this.A.getString());
      this.i.m.a(true);
      this.a((dlj)(new dlj(this.k / 2 - 100, j(6), 200, 20, new of("mco.configure.world.subscription.extend"), var1 -> {
         String _snowman = "https://aka.ms/ExtendJavaRealms?subscriptionId=" + this.y.b + "&profileId=" + this.i.J().b();
         this.i.m.a(_snowman);
         x.i().a(_snowman);
      })));
      this.a((dlj)(new dlj(this.k / 2 - 100, j(12), 200, 20, nq.h, var1 -> this.i.a(this.x))));
      if (this.y.j) {
         this.a((dlj)(new dlj(this.k / 2 - 100, j(10), 200, 20, new of("mco.configure.world.delete.button"), var1 -> {
            nr _snowman = new of("mco.configure.world.delete.question.line1");
            nr _snowmanx = new of("mco.configure.world.delete.question.line2");
            this.i.a(new dhy(this::c, dhy.a.a, _snowman, _snowmanx, true));
         })));
      }
   }

   private void c(boolean var1) {
      if (_snowman) {
         (new Thread("Realms-delete-realm") {
            @Override
            public void run() {
               try {
                  dgb _snowman = dgb.a();
                  _snowman.h(dil.this.y.a);
               } catch (dhi var2) {
                  dil.a.error("Couldn't delete world");
                  dil.a.error(var2);
               }

               dil.this.i.execute(() -> dil.this.i.a(dil.this.z));
            }
         }).start();
      }

      this.i.a(this);
   }

   private void a(long var1) {
      dgb _snowman = dgb.a();

      try {
         dha _snowmanx = _snowman.g(_snowman);
         this.A = this.a(_snowmanx.b);
         this.B = b(_snowmanx.a);
         this.C = _snowmanx.c;
      } catch (dhi var5) {
         a.error("Couldn't get subscription");
         this.i.a(new dhw(var5, this.x));
      }
   }

   private static String b(long var0) {
      Calendar _snowman = new GregorianCalendar(TimeZone.getDefault());
      _snowman.setTimeInMillis(_snowman);
      return DateFormat.getDateTimeInstance().format(_snowman.getTime());
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.i.a(this.x);
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      int _snowman = this.k / 2 - 100;
      a(_snowman, this.o, b, this.k / 2, 17, 16777215);
      this.o.b(_snowman, c, (float)_snowman, (float)j(0), 10526880);
      this.o.b(_snowman, this.B, (float)_snowman, (float)j(1), 16777215);
      if (this.C == dha.a.a) {
         this.o.b(_snowman, p, (float)_snowman, (float)j(3), 10526880);
      } else if (this.C == dha.a.b) {
         this.o.b(_snowman, q, (float)_snowman, (float)j(3), 10526880);
      }

      this.o.b(_snowman, this.A, (float)_snowman, (float)j(4), 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   private nr a(int var1) {
      if (_snowman < 0 && this.y.j) {
         return r;
      } else if (_snowman <= 1) {
         return s;
      } else {
         int _snowman = _snowman / 30;
         int _snowmanx = _snowman % 30;
         nx _snowmanxx = new oe("");
         if (_snowman > 0) {
            _snowmanxx.c(Integer.toString(_snowman)).c(" ");
            if (_snowman == 1) {
               _snowmanxx.a(t);
            } else {
               _snowmanxx.a(u);
            }
         }

         if (_snowmanx > 0) {
            if (_snowman > 0) {
               _snowmanxx.c(", ");
            }

            _snowmanxx.c(Integer.toString(_snowmanx)).c(" ");
            if (_snowmanx == 1) {
               _snowmanxx.a(v);
            } else {
               _snowmanxx.a(w);
            }
         }

         return _snowmanxx;
      }
   }
}
