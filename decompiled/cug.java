import org.apache.commons.lang3.mutable.MutableInt;

public final class cug extends cul<cuh.a, cuh> {
   private static final gc[] e = gc.values();
   private final fx.a f = new fx.a();

   public cug(cgj var1) {
      super(_snowman, bsf.b, new cuh(_snowman));
   }

   private int d(long var1) {
      int _snowman = fx.b(_snowman);
      int _snowmanx = fx.c(_snowman);
      int _snowmanxx = fx.d(_snowman);
      brc _snowmanxxx = this.a.c(_snowman >> 4, _snowmanxx >> 4);
      return _snowmanxxx != null ? _snowmanxxx.g(this.f.d(_snowman, _snowmanx, _snowmanxx)) : 0;
   }

   @Override
   protected int b(long var1, long var3, int var5) {
      if (_snowman == Long.MAX_VALUE) {
         return 15;
      } else if (_snowman == Long.MAX_VALUE) {
         return _snowman + 15 - this.d(_snowman);
      } else if (_snowman >= 15) {
         return _snowman;
      } else {
         int _snowman = Integer.signum(fx.b(_snowman) - fx.b(_snowman));
         int _snowmanx = Integer.signum(fx.c(_snowman) - fx.c(_snowman));
         int _snowmanxx = Integer.signum(fx.d(_snowman) - fx.d(_snowman));
         gc _snowmanxxx = gc.a(_snowman, _snowmanx, _snowmanxx);
         if (_snowmanxxx == null) {
            return 15;
         } else {
            MutableInt _snowmanxxxx = new MutableInt();
            ceh _snowmanxxxxx = this.a(_snowman, _snowmanxxxx);
            if (_snowmanxxxx.getValue() >= 15) {
               return 15;
            } else {
               ceh _snowmanxxxxxx = this.a(_snowman, null);
               ddh _snowmanxxxxxxx = this.a(_snowmanxxxxxx, _snowman, _snowmanxxx);
               ddh _snowmanxxxxxxxx = this.a(_snowmanxxxxx, _snowman, _snowmanxxx.f());
               return dde.b(_snowmanxxxxxxx, _snowmanxxxxxxxx) ? 15 : _snowman + Math.max(1, _snowmanxxxx.getValue());
            }
         }
      }
   }

   @Override
   protected void a(long var1, int var3, boolean var4) {
      long _snowman = gp.e(_snowman);

      for (gc _snowmanx : e) {
         long _snowmanxx = fx.a(_snowman, _snowmanx);
         long _snowmanxxx = gp.e(_snowmanxx);
         if (_snowman == _snowmanxxx || this.c.g(_snowmanxxx)) {
            this.b(_snowman, _snowmanxx, _snowman, _snowman);
         }
      }
   }

   @Override
   protected int a(long var1, long var3, int var5) {
      int _snowman = _snowman;
      if (Long.MAX_VALUE != _snowman) {
         int _snowmanx = this.b(Long.MAX_VALUE, _snowman, 0);
         if (_snowman > _snowmanx) {
            _snowman = _snowmanx;
         }

         if (_snowman == 0) {
            return _snowman;
         }
      }

      long _snowmanxx = gp.e(_snowman);
      cgb _snowmanxxx = this.c.a(_snowmanxx, true);

      for (gc _snowmanxxxx : e) {
         long _snowmanxxxxx = fx.a(_snowman, _snowmanxxxx);
         if (_snowmanxxxxx != _snowman) {
            long _snowmanxxxxxx = gp.e(_snowmanxxxxx);
            cgb _snowmanxxxxxxx;
            if (_snowmanxx == _snowmanxxxxxx) {
               _snowmanxxxxxxx = _snowmanxxx;
            } else {
               _snowmanxxxxxxx = this.c.a(_snowmanxxxxxx, true);
            }

            if (_snowmanxxxxxxx != null) {
               int _snowmanxxxxxxxx = this.b(_snowmanxxxxx, _snowman, this.a(_snowmanxxxxxxx, _snowmanxxxxx));
               if (_snowman > _snowmanxxxxxxxx) {
                  _snowman = _snowmanxxxxxxxx;
               }

               if (_snowman == 0) {
                  return _snowman;
               }
            }
         }
      }

      return _snowman;
   }

   @Override
   public void a(fx var1, int var2) {
      this.c.d();
      this.a(Long.MAX_VALUE, _snowman.a(), 15 - _snowman, true);
   }
}
