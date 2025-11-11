import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class drq {
   private final List<drs> a = Lists.newArrayListWithCapacity(20);
   private drs b;
   private final dro c = new dro();
   private djz d;
   private final List<dru> e = Lists.newArrayList();
   private List<drt> f;
   private dma g;
   private dma h;
   private int i;
   private int j;
   private adt k;
   private boq<?> l;
   private drt m;

   public drq() {
      for (int _snowman = 0; _snowman < 20; _snowman++) {
         this.a.add(new drs());
      }
   }

   public void a(djz var1, int var2, int var3) {
      this.d = _snowman;
      this.k = _snowman.s.F();

      for (int _snowman = 0; _snowman < this.a.size(); _snowman++) {
         this.a.get(_snowman).a(_snowman + 11 + 25 * (_snowman % 5), _snowman + 31 + 25 * (_snowman / 5));
      }

      this.g = new dma(_snowman + 93, _snowman + 137, 12, 17, false);
      this.g.a(1, 208, 13, 18, drp.a);
      this.h = new dma(_snowman + 38, _snowman + 137, 12, 17, true);
      this.h.a(1, 208, 13, 18, drp.a);
   }

   public void a(drp var1) {
      this.e.remove(_snowman);
      this.e.add(_snowman);
   }

   public void a(List<drt> var1, boolean var2) {
      this.f = _snowman;
      this.i = (int)Math.ceil((double)_snowman.size() / 20.0);
      if (this.i <= this.j || _snowman) {
         this.j = 0;
      }

      this.f();
   }

   private void f() {
      int _snowman = 20 * this.j;

      for (int _snowmanx = 0; _snowmanx < this.a.size(); _snowmanx++) {
         drs _snowmanxx = this.a.get(_snowmanx);
         if (_snowman + _snowmanx < this.f.size()) {
            drt _snowmanxxx = this.f.get(_snowman + _snowmanx);
            _snowmanxx.a(_snowmanxxx, this);
            _snowmanxx.p = true;
         } else {
            _snowmanxx.p = false;
         }
      }

      this.g();
   }

   private void g() {
      this.g.p = this.i > 1 && this.j < this.i - 1;
      this.h.p = this.i > 1 && this.j > 0;
   }

   public void a(dfm var1, int var2, int var3, int var4, int var5, float var6) {
      if (this.i > 1) {
         String _snowman = this.j + 1 + "/" + this.i;
         int _snowmanx = this.d.g.b(_snowman);
         this.d.g.b(_snowman, _snowman, (float)(_snowman - _snowmanx / 2 + 73), (float)(_snowman + 141), -1);
      }

      this.b = null;

      for (drs _snowman : this.a) {
         _snowman.a(_snowman, _snowman, _snowman, _snowman);
         if (_snowman.p && _snowman.g()) {
            this.b = _snowman;
         }
      }

      this.h.a(_snowman, _snowman, _snowman, _snowman);
      this.g.a(_snowman, _snowman, _snowman, _snowman);
      this.c.a(_snowman, _snowman, _snowman, _snowman);
   }

   public void a(dfm var1, int var2, int var3) {
      if (this.d.y != null && this.b != null && !this.c.c()) {
         this.d.y.b(_snowman, this.b.a(this.d.y), _snowman, _snowman);
      }
   }

   @Nullable
   public boq<?> a() {
      return this.l;
   }

   @Nullable
   public drt b() {
      return this.m;
   }

   public void c() {
      this.c.a(false);
   }

   public boolean a(double var1, double var3, int var5, int var6, int var7, int var8, int var9) {
      this.l = null;
      this.m = null;
      if (this.c.c()) {
         if (this.c.a(_snowman, _snowman, _snowman)) {
            this.l = this.c.b();
            this.m = this.c.a();
         } else {
            this.c.a(false);
         }

         return true;
      } else if (this.g.a(_snowman, _snowman, _snowman)) {
         this.j++;
         this.f();
         return true;
      } else if (this.h.a(_snowman, _snowman, _snowman)) {
         this.j--;
         this.f();
         return true;
      } else {
         for (drs _snowman : this.a) {
            if (_snowman.a(_snowman, _snowman, _snowman)) {
               if (_snowman == 0) {
                  this.l = _snowman.d();
                  this.m = _snowman.a();
               } else if (_snowman == 1 && !this.c.c() && !_snowman.b()) {
                  this.c.a(this.d, _snowman.a(), _snowman.l, _snowman.m, _snowman + _snowman / 2, _snowman + 13 + _snowman / 2, (float)_snowman.h());
               }

               return true;
            }
         }

         return false;
      }
   }

   public void a(List<boq<?>> var1) {
      for (dru _snowman : this.e) {
         _snowman.a(_snowman);
      }
   }

   public djz d() {
      return this.d;
   }

   public adt e() {
      return this.k;
   }
}
