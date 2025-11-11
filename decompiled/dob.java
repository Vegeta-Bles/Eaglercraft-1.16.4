import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.net.IDN;
import java.util.function.Predicate;

public class dob extends dot {
   private static final nr a = new of("addServer.enterName");
   private static final nr b = new of("addServer.enterIp");
   private dlj c;
   private final BooleanConsumer p;
   private final dwz q;
   private dlq r;
   private dlq s;
   private dlj t;
   private final dot u;
   private final Predicate<String> v = var0 -> {
      if (aft.b(var0)) {
         return true;
      } else {
         String[] _snowman = var0.split(":");
         if (_snowman.length == 0) {
            return true;
         } else {
            try {
               String _snowmanx = IDN.toASCII(_snowman[0]);
               return true;
            } catch (IllegalArgumentException var3x) {
               return false;
            }
         }
      }
   };

   public dob(dot var1, BooleanConsumer var2, dwz var3) {
      super(new of("addServer.title"));
      this.u = _snowman;
      this.p = _snowman;
      this.q = _snowman;
   }

   @Override
   public void d() {
      this.s.a();
      this.r.a();
   }

   @Override
   protected void b() {
      this.i.m.a(true);
      this.s = new dlq(this.o, this.k / 2 - 100, 66, 200, 20, new of("addServer.enterName"));
      this.s.e(true);
      this.s.a(this.q.a);
      this.s.a(this::b);
      this.e.add(this.s);
      this.r = new dlq(this.o, this.k / 2 - 100, 106, 200, 20, new of("addServer.enterIp"));
      this.r.k(128);
      this.r.a(this.q.b);
      this.r.a(this.v);
      this.r.a(this::b);
      this.e.add(this.r);
      this.t = this.a((dlj)(new dlj(this.k / 2 - 100, this.l / 4 + 72, 200, 20, a(this.q.b()), var1 -> {
         this.q.a(dwz.a.values()[(this.q.b().ordinal() + 1) % dwz.a.values().length]);
         this.t.a(a(this.q.b()));
      })));
      this.c = this.a((dlj)(new dlj(this.k / 2 - 100, this.l / 4 + 96 + 18, 200, 20, new of("addServer.add"), var1 -> this.h())));
      this.a((dlj)(new dlj(this.k / 2 - 100, this.l / 4 + 120 + 18, 200, 20, nq.d, var1 -> this.p.accept(false))));
      this.i();
   }

   private static nr a(dwz.a var0) {
      return new of("addServer.resourcePack").c(": ").a(_snowman.a());
   }

   @Override
   public void a(djz var1, int var2, int var3) {
      String _snowman = this.r.b();
      String _snowmanx = this.s.b();
      this.b(_snowman, _snowman, _snowman);
      this.r.a(_snowman);
      this.s.a(_snowmanx);
   }

   private void b(String var1) {
      this.i();
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   private void h() {
      this.q.a = this.s.b();
      this.q.b = this.r.b();
      this.p.accept(true);
   }

   @Override
   public void at_() {
      this.i();
      this.i.a(this.u);
   }

   private void i() {
      String _snowman = this.r.b();
      boolean _snowmanx = !_snowman.isEmpty() && _snowman.split(":").length > 0 && _snowman.indexOf(32) == -1;
      this.c.o = _snowmanx && !this.s.b().isEmpty();
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.d, this.k / 2, 17, 16777215);
      b(_snowman, this.o, a, this.k / 2 - 100, 53, 10526880);
      b(_snowman, this.o, b, this.k / 2 - 100, 94, 10526880);
      this.s.a(_snowman, _snowman, _snowman, _snowman);
      this.r.a(_snowman, _snowman, _snowman, _snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
