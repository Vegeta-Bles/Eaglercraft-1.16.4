import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;

public class ddl extends ddp {
   private final ddn a;
   private final String b;
   private final Set<String> c = Sets.newHashSet();
   private nr d;
   private nr e;
   private nr f;
   private boolean g;
   private boolean h;
   private ddp.b i;
   private ddp.b j;
   private k k;
   private ddp.a l;
   private final ob m;

   public ddl(ddn var1, String var2) {
      this.e = oe.d;
      this.f = oe.d;
      this.g = true;
      this.h = true;
      this.i = ddp.b.a;
      this.j = ddp.b.a;
      this.k = k.v;
      this.l = ddp.a.a;
      this.a = _snowman;
      this.b = _snowman;
      this.d = new oe(_snowman);
      this.m = ob.a.a(_snowman).a(new nv(nv.a.a, new oe(_snowman)));
   }

   @Override
   public String b() {
      return this.b;
   }

   public nr c() {
      return this.d;
   }

   public nx d() {
      nx _snowman = ns.a((nr)this.d.e().c(this.m));
      k _snowmanx = this.n();
      if (_snowmanx != k.v) {
         _snowman.a(_snowmanx);
      }

      return _snowman;
   }

   public void a(nr var1) {
      if (_snowman == null) {
         throw new IllegalArgumentException("Name cannot be null");
      } else {
         this.d = _snowman;
         this.a.b(this);
      }
   }

   public void b(@Nullable nr var1) {
      this.e = _snowman == null ? oe.d : _snowman;
      this.a.b(this);
   }

   public nr e() {
      return this.e;
   }

   public void c(@Nullable nr var1) {
      this.f = _snowman == null ? oe.d : _snowman;
      this.a.b(this);
   }

   public nr f() {
      return this.f;
   }

   @Override
   public Collection<String> g() {
      return this.c;
   }

   @Override
   public nx d(nr var1) {
      nx _snowman = new oe("").a(this.e).a(_snowman).a(this.f);
      k _snowmanx = this.n();
      if (_snowmanx != k.v) {
         _snowman.a(_snowmanx);
      }

      return _snowman;
   }

   public static nx a(@Nullable ddp var0, nr var1) {
      return _snowman == null ? _snowman.e() : _snowman.d(_snowman);
   }

   @Override
   public boolean h() {
      return this.g;
   }

   public void a(boolean var1) {
      this.g = _snowman;
      this.a.b(this);
   }

   @Override
   public boolean i() {
      return this.h;
   }

   public void b(boolean var1) {
      this.h = _snowman;
      this.a.b(this);
   }

   @Override
   public ddp.b j() {
      return this.i;
   }

   @Override
   public ddp.b k() {
      return this.j;
   }

   public void a(ddp.b var1) {
      this.i = _snowman;
      this.a.b(this);
   }

   public void b(ddp.b var1) {
      this.j = _snowman;
      this.a.b(this);
   }

   @Override
   public ddp.a l() {
      return this.l;
   }

   public void a(ddp.a var1) {
      this.l = _snowman;
      this.a.b(this);
   }

   public int m() {
      int _snowman = 0;
      if (this.h()) {
         _snowman |= 1;
      }

      if (this.i()) {
         _snowman |= 2;
      }

      return _snowman;
   }

   public void a(int var1) {
      this.a((_snowman & 1) > 0);
      this.b((_snowman & 2) > 0);
   }

   public void a(k var1) {
      this.k = _snowman;
      this.a.b(this);
   }

   @Override
   public k n() {
      return this.k;
   }
}
