import org.apache.commons.lang3.mutable.MutableInt;

public final class cuq extends cul<cur.a, cur> {
   private static final gc[] e = gc.values();
   private static final gc[] f = new gc[]{gc.c, gc.d, gc.e, gc.f};

   public cuq(cgj var1) {
      super(_snowman, bsf.a, new cur(_snowman));
   }

   @Override
   protected int b(long var1, long var3, int var5) {
      if (_snowman == Long.MAX_VALUE) {
         return 15;
      } else {
         if (_snowman == Long.MAX_VALUE) {
            if (!this.c.m(_snowman)) {
               return 15;
            }

            _snowman = 0;
         }

         if (_snowman >= 15) {
            return _snowman;
         } else {
            MutableInt _snowman = new MutableInt();
            ceh _snowmanx = this.a(_snowman, _snowman);
            if (_snowman.getValue() >= 15) {
               return 15;
            } else {
               int _snowmanxx = fx.b(_snowman);
               int _snowmanxxx = fx.c(_snowman);
               int _snowmanxxxx = fx.d(_snowman);
               int _snowmanxxxxx = fx.b(_snowman);
               int _snowmanxxxxxx = fx.c(_snowman);
               int _snowmanxxxxxxx = fx.d(_snowman);
               boolean _snowmanxxxxxxxx = _snowmanxx == _snowmanxxxxx && _snowmanxxxx == _snowmanxxxxxxx;
               int _snowmanxxxxxxxxx = Integer.signum(_snowmanxxxxx - _snowmanxx);
               int _snowmanxxxxxxxxxx = Integer.signum(_snowmanxxxxxx - _snowmanxxx);
               int _snowmanxxxxxxxxxxx = Integer.signum(_snowmanxxxxxxx - _snowmanxxxx);
               gc _snowmanxxxxxxxxxxxx;
               if (_snowman == Long.MAX_VALUE) {
                  _snowmanxxxxxxxxxxxx = gc.a;
               } else {
                  _snowmanxxxxxxxxxxxx = gc.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
               }

               ceh _snowmanxxxxxxxxxxxxx = this.a(_snowman, null);
               if (_snowmanxxxxxxxxxxxx != null) {
                  ddh _snowmanxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxx);
                  ddh _snowmanxxxxxxxxxxxxxxx = this.a(_snowmanx, _snowman, _snowmanxxxxxxxxxxxx.f());
                  if (dde.b(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)) {
                     return 15;
                  }
               } else {
                  ddh _snowmanxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxxxxxx, _snowman, gc.a);
                  if (dde.b(_snowmanxxxxxxxxxxxxxx, dde.a())) {
                     return 15;
                  }

                  int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxx ? -1 : 0;
                  gc _snowmanxxxxxxxxxxxxxxxx = gc.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxxxxxxx == null) {
                     return 15;
                  }

                  ddh _snowmanxxxxxxxxxxxxxxxxx = this.a(_snowmanx, _snowman, _snowmanxxxxxxxxxxxxxxxx.f());
                  if (dde.b(dde.a(), _snowmanxxxxxxxxxxxxxxxxx)) {
                     return 15;
                  }
               }

               boolean _snowmanxxxxxxxxxxxxxxxxx = _snowman == Long.MAX_VALUE || _snowmanxxxxxxxx && _snowmanxxx > _snowmanxxxxxx;
               return _snowmanxxxxxxxxxxxxxxxxx && _snowman == 0 && _snowman.getValue() == 0 ? 0 : _snowman + Math.max(1, _snowman.getValue());
            }
         }
      }
   }

   @Override
   protected void a(long var1, int var3, boolean var4) {
      long _snowman = gp.e(_snowman);
      int _snowmanx = fx.c(_snowman);
      int _snowmanxx = gp.b(_snowmanx);
      int _snowmanxxx = gp.a(_snowmanx);
      int _snowmanxxxx;
      if (_snowmanxx != 0) {
         _snowmanxxxx = 0;
      } else {
         int _snowmanxxxxx = 0;

         while (!this.c.g(gp.a(_snowman, 0, -_snowmanxxxxx - 1, 0)) && this.c.a(_snowmanxxx - _snowmanxxxxx - 1)) {
            _snowmanxxxxx++;
         }

         _snowmanxxxx = _snowmanxxxxx;
      }

      long _snowmanxxxxx = fx.a(_snowman, 0, -1 - _snowmanxxxx * 16, 0);
      long _snowmanxxxxxx = gp.e(_snowmanxxxxx);
      if (_snowman == _snowmanxxxxxx || this.c.g(_snowmanxxxxxx)) {
         this.b(_snowman, _snowmanxxxxx, _snowman, _snowman);
      }

      long _snowmanxxxxxxx = fx.a(_snowman, gc.b);
      long _snowmanxxxxxxxx = gp.e(_snowmanxxxxxxx);
      if (_snowman == _snowmanxxxxxxxx || this.c.g(_snowmanxxxxxxxx)) {
         this.b(_snowman, _snowmanxxxxxxx, _snowman, _snowman);
      }

      for (gc _snowmanxxxxxxxxx : f) {
         int _snowmanxxxxxxxxxx = 0;

         do {
            long _snowmanxxxxxxxxxxx = fx.a(_snowman, _snowmanxxxxxxxxx.i(), -_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx.k());
            long _snowmanxxxxxxxxxxxx = gp.e(_snowmanxxxxxxxxxxx);
            if (_snowman == _snowmanxxxxxxxxxxxx) {
               this.b(_snowman, _snowmanxxxxxxxxxxx, _snowman, _snowman);
               break;
            }

            if (this.c.g(_snowmanxxxxxxxxxxxx)) {
               this.b(_snowman, _snowmanxxxxxxxxxxx, _snowman, _snowman);
            }
         } while (++_snowmanxxxxxxxxxx > _snowmanxxxx * 16);
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
         long _snowmanxxxxxx = gp.e(_snowmanxxxxx);
         cgb _snowmanxxxxxxx;
         if (_snowmanxx == _snowmanxxxxxx) {
            _snowmanxxxxxxx = _snowmanxxx;
         } else {
            _snowmanxxxxxxx = this.c.a(_snowmanxxxxxx, true);
         }

         if (_snowmanxxxxxxx != null) {
            if (_snowmanxxxxx != _snowman) {
               int _snowmanxxxxxxxx = this.b(_snowmanxxxxx, _snowman, this.a(_snowmanxxxxxxx, _snowmanxxxxx));
               if (_snowman > _snowmanxxxxxxxx) {
                  _snowman = _snowmanxxxxxxxx;
               }

               if (_snowman == 0) {
                  return _snowman;
               }
            }
         } else if (_snowmanxxxx != gc.a) {
            for (_snowmanxxxxx = fx.f(_snowmanxxxxx); !this.c.g(_snowmanxxxxxx) && !this.c.n(_snowmanxxxxxx); _snowmanxxxxx = fx.a(_snowmanxxxxx, 0, 16, 0)) {
               _snowmanxxxxxx = gp.a(_snowmanxxxxxx, gc.b);
            }

            cgb _snowmanxxxxxxxxx = this.c.a(_snowmanxxxxxx, true);
            if (_snowmanxxxxx != _snowman) {
               int _snowmanxxxxxxxxxx;
               if (_snowmanxxxxxxxxx != null) {
                  _snowmanxxxxxxxxxx = this.b(_snowmanxxxxx, _snowman, this.a(_snowmanxxxxxxxxx, _snowmanxxxxx));
               } else {
                  _snowmanxxxxxxxxxx = this.c.o(_snowmanxxxxxx) ? 0 : 15;
               }

               if (_snowman > _snowmanxxxxxxxxxx) {
                  _snowman = _snowmanxxxxxxxxxx;
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
   protected void f(long var1) {
      this.c.d();
      long _snowman = gp.e(_snowman);
      if (this.c.g(_snowman)) {
         super.f(_snowman);
      } else {
         for (_snowman = fx.f(_snowman); !this.c.g(_snowman) && !this.c.n(_snowman); _snowman = fx.a(_snowman, 0, 16, 0)) {
            _snowman = gp.a(_snowman, gc.b);
         }

         if (this.c.g(_snowman)) {
            super.f(_snowman);
         }
      }
   }

   @Override
   public String b(long var1) {
      return super.b(_snowman) + (this.c.n(_snowman) ? "*" : "");
   }
}
