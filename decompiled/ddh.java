import com.google.common.collect.Lists;
import com.google.common.math.DoubleMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.List;
import javax.annotation.Nullable;

public abstract class ddh {
   protected final dcw a;
   @Nullable
   private ddh[] b;

   ddh(dcw var1) {
      this.a = _snowman;
   }

   public double b(gc.a var1) {
      int _snowman = this.a.a(_snowman);
      return _snowman >= this.a.c(_snowman) ? Double.POSITIVE_INFINITY : this.a(_snowman, _snowman);
   }

   public double c(gc.a var1) {
      int _snowman = this.a.b(_snowman);
      return _snowman <= 0 ? Double.NEGATIVE_INFINITY : this.a(_snowman, _snowman);
   }

   public dci a() {
      if (this.b()) {
         throw (UnsupportedOperationException)x.c(new UnsupportedOperationException("No bounds for empty shape."));
      } else {
         return new dci(this.b(gc.a.a), this.b(gc.a.b), this.b(gc.a.c), this.c(gc.a.a), this.c(gc.a.b), this.c(gc.a.c));
      }
   }

   protected double a(gc.a var1, int var2) {
      return this.a(_snowman).getDouble(_snowman);
   }

   protected abstract DoubleList a(gc.a var1);

   public boolean b() {
      return this.a.a();
   }

   public ddh a(double var1, double var3, double var5) {
      return (ddh)(this.b() ? dde.a() : new dcp(this.a, new ddd(this.a(gc.a.a), _snowman), new ddd(this.a(gc.a.b), _snowman), new ddd(this.a(gc.a.c), _snowman)));
   }

   public ddh c() {
      ddh[] _snowman = new ddh[]{dde.a()};
      this.b((var1x, var3, var5, var7, var9, var11) -> _snowman[0] = dde.b(_snowman[0], dde.a(var1x, var3, var5, var7, var9, var11), dcr.o));
      return _snowman[0];
   }

   public void a(dde.a var1) {
      this.a
         .a(
            (var2, var3, var4, var5, var6, var7) -> _snowman.consume(
                  this.a(gc.a.a, var2), this.a(gc.a.b, var3), this.a(gc.a.c, var4), this.a(gc.a.a, var5), this.a(gc.a.b, var6), this.a(gc.a.c, var7)
               ),
            true
         );
   }

   public void b(dde.a var1) {
      DoubleList _snowman = this.a(gc.a.a);
      DoubleList _snowmanx = this.a(gc.a.b);
      DoubleList _snowmanxx = this.a(gc.a.c);
      this.a
         .b(
            (var4x, var5, var6, var7, var8, var9) -> _snowman.consume(
                  _snowman.getDouble(var4x), _snowman.getDouble(var5), _snowman.getDouble(var6), _snowman.getDouble(var7), _snowman.getDouble(var8), _snowman.getDouble(var9)
               ),
            true
         );
   }

   public List<dci> d() {
      List<dci> _snowman = Lists.newArrayList();
      this.b((var1x, var3, var5, var7, var9, var11) -> _snowman.add(new dci(var1x, var3, var5, var7, var9, var11)));
      return _snowman;
   }

   public double b(gc.a var1, double var2, double var4) {
      gc.a _snowman = fv.b.a(_snowman);
      gc.a _snowmanx = fv.c.a(_snowman);
      int _snowmanxx = this.a(_snowman, _snowman);
      int _snowmanxxx = this.a(_snowmanx, _snowman);
      int _snowmanxxxx = this.a.b(_snowman, _snowmanxx, _snowmanxxx);
      return _snowmanxxxx <= 0 ? Double.NEGATIVE_INFINITY : this.a(_snowman, _snowmanxxxx);
   }

   protected int a(gc.a var1, double var2) {
      return afm.a(0, this.a.c(_snowman) + 1, var4 -> {
         if (var4 < 0) {
            return false;
         } else {
            return var4 > this.a.c(_snowman) ? true : _snowman < this.a(_snowman, var4);
         }
      }) - 1;
   }

   protected boolean b(double var1, double var3, double var5) {
      return this.a.c(this.a(gc.a.a, _snowman), this.a(gc.a.b, _snowman), this.a(gc.a.c, _snowman));
   }

   @Nullable
   public dcj a(dcn var1, dcn var2, fx var3) {
      if (this.b()) {
         return null;
      } else {
         dcn _snowman = _snowman.d(_snowman);
         if (_snowman.g() < 1.0E-7) {
            return null;
         } else {
            dcn _snowmanx = _snowman.e(_snowman.a(0.001));
            return this.b(_snowmanx.b - (double)_snowman.u(), _snowmanx.c - (double)_snowman.v(), _snowmanx.d - (double)_snowman.w())
               ? new dcj(_snowmanx, gc.a(_snowman.b, _snowman.c, _snowman.d).f(), _snowman, true)
               : dci.a(this.d(), _snowman, _snowman, _snowman);
         }
      }
   }

   public ddh a(gc var1) {
      if (!this.b() && this != dde.b()) {
         if (this.b != null) {
            ddh _snowman = this.b[_snowman.ordinal()];
            if (_snowman != null) {
               return _snowman;
            }
         } else {
            this.b = new ddh[6];
         }

         ddh _snowman = this.b(_snowman);
         this.b[_snowman.ordinal()] = _snowman;
         return _snowman;
      } else {
         return this;
      }
   }

   private ddh b(gc var1) {
      gc.a _snowman = _snowman.n();
      gc.b _snowmanx = _snowman.e();
      DoubleList _snowmanxx = this.a(_snowman);
      if (_snowmanxx.size() == 2 && DoubleMath.fuzzyEquals(_snowmanxx.getDouble(0), 0.0, 1.0E-7) && DoubleMath.fuzzyEquals(_snowmanxx.getDouble(1), 1.0, 1.0E-7)) {
         return this;
      } else {
         int _snowmanxxx = this.a(_snowman, _snowmanx == gc.b.a ? 0.9999999 : 1.0E-7);
         return new ddf(this, _snowman, _snowmanxxx);
      }
   }

   public double a(gc.a var1, dci var2, double var3) {
      return this.a(fv.a(_snowman, gc.a.a), _snowman, _snowman);
   }

   protected double a(fv var1, dci var2, double var3) {
      if (this.b()) {
         return _snowman;
      } else if (Math.abs(_snowman) < 1.0E-7) {
         return 0.0;
      } else {
         fv _snowman = _snowman.a();
         gc.a _snowmanx = _snowman.a(gc.a.a);
         gc.a _snowmanxx = _snowman.a(gc.a.b);
         gc.a _snowmanxxx = _snowman.a(gc.a.c);
         double _snowmanxxxx = _snowman.b(_snowmanx);
         double _snowmanxxxxx = _snowman.a(_snowmanx);
         int _snowmanxxxxxx = this.a(_snowmanx, _snowmanxxxxx + 1.0E-7);
         int _snowmanxxxxxxx = this.a(_snowmanx, _snowmanxxxx - 1.0E-7);
         int _snowmanxxxxxxxx = Math.max(0, this.a(_snowmanxx, _snowman.a(_snowmanxx) + 1.0E-7));
         int _snowmanxxxxxxxxx = Math.min(this.a.c(_snowmanxx), this.a(_snowmanxx, _snowman.b(_snowmanxx) - 1.0E-7) + 1);
         int _snowmanxxxxxxxxxx = Math.max(0, this.a(_snowmanxxx, _snowman.a(_snowmanxxx) + 1.0E-7));
         int _snowmanxxxxxxxxxxx = Math.min(this.a.c(_snowmanxxx), this.a(_snowmanxxx, _snowman.b(_snowmanxxx) - 1.0E-7) + 1);
         int _snowmanxxxxxxxxxxxx = this.a.c(_snowmanx);
         if (_snowman > 0.0) {
            for (int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxx + 1; _snowmanxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxx; _snowmanxxxxxxxxxxxxxx < _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx++) {
                     if (this.a.a(_snowman, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)) {
                        double _snowmanxxxxxxxxxxxxxxxx = this.a(_snowmanx, _snowmanxxxxxxxxxxxxx) - _snowmanxxxx;
                        if (_snowmanxxxxxxxxxxxxxxxx >= -1.0E-7) {
                           _snowman = Math.min(_snowman, _snowmanxxxxxxxxxxxxxxxx);
                        }

                        return _snowman;
                     }
                  }
               }
            }
         } else if (_snowman < 0.0) {
            for (int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxx - 1; _snowmanxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxx--) {
               for (int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxx; _snowmanxxxxxxxxxxxxxx < _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
                     if (this.a.a(_snowman, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx)) {
                        double _snowmanxxxxxxxxxxxxxxxxx = this.a(_snowmanx, _snowmanxxxxxxxxxxxxx + 1) - _snowmanxxxxx;
                        if (_snowmanxxxxxxxxxxxxxxxxx <= 1.0E-7) {
                           _snowman = Math.max(_snowman, _snowmanxxxxxxxxxxxxxxxxx);
                        }

                        return _snowman;
                     }
                  }
               }
            }
         }

         return _snowman;
      }
   }

   @Override
   public String toString() {
      return this.b() ? "EMPTY" : "VoxelShape[" + this.a() + "]";
   }
}
