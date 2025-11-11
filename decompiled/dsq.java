import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import java.util.List;

public class dsq {
   private static final dss b = new dsq.a();
   private static final dss c = new dsq.b(-1, true);
   private static final dss d = new dsq.b(1, true);
   private static final dss e = new dsq.b(1, false);
   private static final nr f = new of("spectatorMenu.close");
   private static final nr g = new of("spectatorMenu.previous_page");
   private static final nr h = new of("spectatorMenu.next_page");
   public static final dss a = new dss() {
      @Override
      public void a(dsq var1) {
      }

      @Override
      public nr aA_() {
         return oe.d;
      }

      @Override
      public void a(dfm var1, float var2, int var3) {
      }

      @Override
      public boolean aB_() {
         return false;
      }
   };
   private final dst i;
   private dsr j;
   private int k = -1;
   private int l;

   public dsq(dst var1) {
      this.j = new dsp();
      this.i = _snowman;
   }

   public dss a(int var1) {
      int _snowman = _snowman + this.l * 6;
      if (this.l > 0 && _snowman == 0) {
         return c;
      } else if (_snowman == 7) {
         return _snowman < this.j.a().size() ? d : e;
      } else if (_snowman == 8) {
         return b;
      } else {
         return _snowman >= 0 && _snowman < this.j.a().size() ? (dss)MoreObjects.firstNonNull(this.j.a().get(_snowman), a) : a;
      }
   }

   public List<dss> a() {
      List<dss> _snowman = Lists.newArrayList();

      for (int _snowmanx = 0; _snowmanx <= 8; _snowmanx++) {
         _snowman.add(this.a(_snowmanx));
      }

      return _snowman;
   }

   public dss b() {
      return this.a(this.k);
   }

   public dsr c() {
      return this.j;
   }

   public void b(int var1) {
      dss _snowman = this.a(_snowman);
      if (_snowman != a) {
         if (this.k == _snowman && _snowman.aB_()) {
            _snowman.a(this);
         } else {
            this.k = _snowman;
         }
      }
   }

   public void d() {
      this.i.a(this);
   }

   public int e() {
      return this.k;
   }

   public void a(dsr var1) {
      this.j = _snowman;
      this.k = -1;
      this.l = 0;
   }

   public dsu f() {
      return new dsu(this.j, this.a(), this.k);
   }

   static class a implements dss {
      private a() {
      }

      @Override
      public void a(dsq var1) {
         _snowman.d();
      }

      @Override
      public nr aA_() {
         return dsq.f;
      }

      @Override
      public void a(dfm var1, float var2, int var3) {
         djz.C().M().a(dml.a);
         dkw.a(_snowman, 0, 0, 128.0F, 0.0F, 16, 16, 256, 256);
      }

      @Override
      public boolean aB_() {
         return true;
      }
   }

   static class b implements dss {
      private final int a;
      private final boolean b;

      public b(int var1, boolean var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      @Override
      public void a(dsq var1) {
         _snowman.l = _snowman.l + this.a;
      }

      @Override
      public nr aA_() {
         return this.a < 0 ? dsq.g : dsq.h;
      }

      @Override
      public void a(dfm var1, float var2, int var3) {
         djz.C().M().a(dml.a);
         if (this.a < 0) {
            dkw.a(_snowman, 0, 0, 144.0F, 0.0F, 16, 16, 256, 256);
         } else {
            dkw.a(_snowman, 0, 0, 160.0F, 0.0F, 16, 16, 256, 256);
         }
      }

      @Override
      public boolean aB_() {
         return this.b;
      }
   }
}
