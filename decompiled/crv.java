import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class crv<C extends cma> {
   public static final crv<?> a = new crv<cme>(cla.c, 0, 0, cra.a(), 0, 0L) {
      public void a(gn var1, cfy var2, csw var3, int var4, int var5, bsv var6, cme var7) {
      }
   };
   private final cla<C> e;
   protected final List<cru> b = Lists.newArrayList();
   protected cra c;
   private final int f;
   private final int g;
   private int h;
   protected final chx d;

   public crv(cla<C> var1, int var2, int var3, cra var4, int var5, long var6) {
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.d = new chx();
      this.d.c(_snowman, _snowman, _snowman);
      this.c = _snowman;
   }

   public abstract void a(gn var1, cfy var2, csw var3, int var4, int var5, bsv var6, C var7);

   public cra c() {
      return this.c;
   }

   public List<cru> d() {
      return this.b;
   }

   public void a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6) {
      synchronized (this.b) {
         if (!this.b.isEmpty()) {
            cra _snowman = this.b.get(0).n;
            gr _snowmanx = _snowman.g();
            fx _snowmanxx = new fx(_snowmanx.u(), _snowman.b, _snowmanx.w());
            Iterator<cru> _snowmanxxx = this.b.iterator();

            while (_snowmanxxx.hasNext()) {
               cru _snowmanxxxx = _snowmanxxx.next();
               if (_snowmanxxxx.g().b(_snowman) && !_snowmanxxxx.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowmanxx)) {
                  _snowmanxxx.remove();
               }
            }

            this.b();
         }
      }
   }

   protected void b() {
      this.c = cra.a();

      for (cru _snowman : this.b) {
         this.c.c(_snowman.g());
      }
   }

   public md a(int var1, int var2) {
      md _snowman = new md();
      if (this.e()) {
         _snowman.a("id", gm.aG.b(this.l()).toString());
         _snowman.b("ChunkX", _snowman);
         _snowman.b("ChunkZ", _snowman);
         _snowman.b("references", this.h);
         _snowman.a("BB", this.c.h());
         mj var4 = new mj();
         synchronized (this.b) {
            for (cru _snowmanx : this.b) {
               var4.add(_snowmanx.f());
            }
         }

         _snowman.a("Children", var4);
         return _snowman;
      } else {
         _snowman.a("id", "INVALID");
         return _snowman;
      }
   }

   protected void a(int var1, Random var2, int var3) {
      int _snowman = _snowman - _snowman;
      int _snowmanx = this.c.e() + 1;
      if (_snowmanx < _snowman) {
         _snowmanx += _snowman.nextInt(_snowman - _snowmanx);
      }

      int _snowmanxx = _snowmanx - this.c.e;
      this.c.a(0, _snowmanxx, 0);

      for (cru _snowmanxxx : this.b) {
         _snowmanxxx.a(0, _snowmanxx, 0);
      }
   }

   protected void a(Random var1, int var2, int var3) {
      int _snowman = _snowman - _snowman + 1 - this.c.e();
      int _snowmanx;
      if (_snowman > 1) {
         _snowmanx = _snowman + _snowman.nextInt(_snowman);
      } else {
         _snowmanx = _snowman;
      }

      int _snowmanxx = _snowmanx - this.c.b;
      this.c.a(0, _snowmanxx, 0);

      for (cru _snowmanxxx : this.b) {
         _snowmanxxx.a(0, _snowmanxx, 0);
      }
   }

   public boolean e() {
      return !this.b.isEmpty();
   }

   public int f() {
      return this.f;
   }

   public int g() {
      return this.g;
   }

   public fx a() {
      return new fx(this.f << 4, 0, this.g << 4);
   }

   public boolean h() {
      return this.h < this.k();
   }

   public void i() {
      this.h++;
   }

   public int j() {
      return this.h;
   }

   protected int k() {
      return 1;
   }

   public cla<?> l() {
      return this.e;
   }
}
