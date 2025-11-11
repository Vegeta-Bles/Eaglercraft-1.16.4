import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class dpk extends dlo<dpk.b> {
   private final dpl a;
   private int o;

   public dpk(dpl var1, djz var2) {
      super(_snowman, _snowman.k + 45, _snowman.l, 43, _snowman.l - 32, 20);
      this.a = _snowman;
      djw[] _snowman = (djw[])ArrayUtils.clone(_snowman.k.aF);
      Arrays.sort((Object[])_snowman);
      String _snowmanx = null;

      for (djw _snowmanxx : _snowman) {
         String _snowmanxxx = _snowmanxx.e();
         if (!_snowmanxxx.equals(_snowmanx)) {
            _snowmanx = _snowmanxxx;
            this.b(new dpk.a(new of(_snowmanxxx)));
         }

         nr _snowmanxxxx = new of(_snowmanxx.g());
         int _snowmanxxxxx = _snowman.g.a(_snowmanxxxx);
         if (_snowmanxxxxx > this.o) {
            this.o = _snowmanxxxxx;
         }

         this.b(new dpk.c(_snowmanxx, _snowmanxxxx));
      }
   }

   @Override
   protected int e() {
      return super.e() + 15;
   }

   @Override
   public int d() {
      return super.d() + 32;
   }

   public class a extends dpk.b {
      private final nr b;
      private final int c;

      public a(nr var2) {
         this.b = _snowman;
         this.c = dpk.this.b.g.a(this.b);
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         dpk.this.b.g.b(_snowman, this.b, (float)(dpk.this.b.y.k / 2 - this.c / 2), (float)(_snowman + _snowman - 9 - 1), 16777215);
      }

      @Override
      public boolean c_(boolean var1) {
         return false;
      }

      @Override
      public List<? extends dmi> au_() {
         return Collections.emptyList();
      }
   }

   public abstract static class b extends dlo.a<dpk.b> {
      public b() {
      }
   }

   public class c extends dpk.b {
      private final djw b;
      private final nr c;
      private final dlj d;
      private final dlj e;

      private c(final djw var2, final nr var3) {
         this.b = _snowman;
         this.c = _snowman;
         this.d = new dlj(0, 0, 75, 20, _snowman, var2x -> dpk.this.a.c = _snowman) {
            @Override
            protected nx c() {
               return _snowman.i() ? new of("narrator.controls.unbound", _snowman) : new of("narrator.controls.bound", _snowman, super.c());
            }
         };
         this.e = new dlj(0, 0, 50, 20, new of("controls.reset"), var2x -> {
            dpk.this.b.k.a(_snowman, _snowman.h());
            djw.c();
         }) {
            @Override
            protected nx c() {
               return new of("narrator.controls.reset", _snowman);
            }
         };
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         boolean _snowman = dpk.this.a.c == this.b;
         dpk.this.b.g.b(_snowman, this.c, (float)(_snowman + 90 - dpk.this.o), (float)(_snowman + _snowman / 2 - 9 / 2), 16777215);
         this.e.l = _snowman + 190;
         this.e.m = _snowman;
         this.e.o = !this.b.k();
         this.e.a(_snowman, _snowman, _snowman, _snowman);
         this.d.l = _snowman + 105;
         this.d.m = _snowman;
         this.d.a(this.b.j());
         boolean _snowmanx = false;
         if (!this.b.i()) {
            for (djw _snowmanxx : dpk.this.b.k.aF) {
               if (_snowmanxx != this.b && this.b.b(_snowmanxx)) {
                  _snowmanx = true;
                  break;
               }
            }
         }

         if (_snowman) {
            this.d.a(new oe("> ").a(this.d.i().e().a(k.o)).c(" <").a(k.o));
         } else if (_snowmanx) {
            this.d.a(this.d.i().e().a(k.m));
         }

         this.d.a(_snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public List<? extends dmi> au_() {
         return ImmutableList.of(this.d, this.e);
      }

      @Override
      public boolean a(double var1, double var3, int var5) {
         return this.d.a(_snowman, _snowman, _snowman) ? true : this.e.a(_snowman, _snowman, _snowman);
      }

      @Override
      public boolean c(double var1, double var3, int var5) {
         return this.d.c(_snowman, _snowman, _snowman) || this.e.c(_snowman, _snowman, _snowman);
      }
   }
}
