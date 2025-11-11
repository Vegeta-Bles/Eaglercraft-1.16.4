import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class cuv extends cuw {
   public static final cey a = cex.i;
   public static final cfg b = cex.at;
   private static final ThreadLocal<Object2ByteLinkedOpenHashMap<buo.a>> e = ThreadLocal.withInitial(() -> {
      Object2ByteLinkedOpenHashMap<buo.a> _snowman = new Object2ByteLinkedOpenHashMap<buo.a>(200) {
         protected void rehash(int var1) {
         }
      };
      _snowman.defaultReturnValue((byte)127);
      return _snowman;
   });
   private final Map<cux, ddh> f = Maps.newIdentityHashMap();

   public cuv() {
   }

   @Override
   protected void a(cei.a<cuw, cux> var1) {
      _snowman.a(a);
   }

   @Override
   public dcn a(brc var1, fx var2, cux var3) {
      double _snowman = 0.0;
      double _snowmanx = 0.0;
      fx.a _snowmanxx = new fx.a();

      for (gc _snowmanxxx : gc.c.a) {
         _snowmanxx.a(_snowman, _snowmanxxx);
         cux _snowmanxxxx = _snowman.b(_snowmanxx);
         if (this.g(_snowmanxxxx)) {
            float _snowmanxxxxx = _snowmanxxxx.d();
            float _snowmanxxxxxx = 0.0F;
            if (_snowmanxxxxx == 0.0F) {
               if (!_snowman.d_(_snowmanxx).c().c()) {
                  fx _snowmanxxxxxxx = _snowmanxx.c();
                  cux _snowmanxxxxxxxx = _snowman.b(_snowmanxxxxxxx);
                  if (this.g(_snowmanxxxxxxxx)) {
                     _snowmanxxxxx = _snowmanxxxxxxxx.d();
                     if (_snowmanxxxxx > 0.0F) {
                        _snowmanxxxxxx = _snowman.d() - (_snowmanxxxxx - 0.8888889F);
                     }
                  }
               }
            } else if (_snowmanxxxxx > 0.0F) {
               _snowmanxxxxxx = _snowman.d() - _snowmanxxxxx;
            }

            if (_snowmanxxxxxx != 0.0F) {
               _snowman += (double)((float)_snowmanxxx.i() * _snowmanxxxxxx);
               _snowmanx += (double)((float)_snowmanxxx.k() * _snowmanxxxxxx);
            }
         }
      }

      dcn _snowmanxxxx = new dcn(_snowman, 0.0, _snowmanx);
      if (_snowman.c(a)) {
         for (gc _snowmanxxxxxxx : gc.c.a) {
            _snowmanxx.a(_snowman, _snowmanxxxxxxx);
            if (this.a(_snowman, _snowmanxx, _snowmanxxxxxxx) || this.a(_snowman, _snowmanxx.b(), _snowmanxxxxxxx)) {
               _snowmanxxxx = _snowmanxxxx.d().b(0.0, -6.0, 0.0);
               break;
            }
         }
      }

      return _snowmanxxxx.d();
   }

   private boolean g(cux var1) {
      return _snowman.c() || _snowman.a().a(this);
   }

   protected boolean a(brc var1, fx var2, gc var3) {
      ceh _snowman = _snowman.d_(_snowman);
      cux _snowmanx = _snowman.b(_snowman);
      if (_snowmanx.a().a(this)) {
         return false;
      } else if (_snowman == gc.b) {
         return true;
      } else {
         return _snowman.c() == cva.G ? false : _snowman.d(_snowman, _snowman, _snowman);
      }
   }

   protected void a(bry var1, fx var2, cux var3) {
      if (!_snowman.c()) {
         ceh _snowman = _snowman.d_(_snowman);
         fx _snowmanx = _snowman.c();
         ceh _snowmanxx = _snowman.d_(_snowmanx);
         cux _snowmanxxx = this.a((brz)_snowman, _snowmanx, _snowmanxx);
         if (this.a(_snowman, _snowman, _snowman, gc.a, _snowmanx, _snowmanxx, _snowman.b(_snowmanx), _snowmanxxx.a())) {
            this.a(_snowman, _snowmanx, _snowmanxx, gc.a, _snowmanxxx);
            if (this.a(_snowman, _snowman) >= 3) {
               this.a(_snowman, _snowman, _snowman, _snowman);
            }
         } else if (_snowman.b() || !this.a(_snowman, _snowmanxxx.a(), _snowman, _snowman, _snowmanx, _snowmanxx)) {
            this.a(_snowman, _snowman, _snowman, _snowman);
         }
      }
   }

   private void a(bry var1, fx var2, cux var3, ceh var4) {
      int _snowman = _snowman.e() - this.c(_snowman);
      if (_snowman.c(a)) {
         _snowman = 7;
      }

      if (_snowman > 0) {
         Map<gc, cux> _snowmanx = this.b(_snowman, _snowman, _snowman);

         for (Entry<gc, cux> _snowmanxx : _snowmanx.entrySet()) {
            gc _snowmanxxx = _snowmanxx.getKey();
            cux _snowmanxxxx = _snowmanxx.getValue();
            fx _snowmanxxxxx = _snowman.a(_snowmanxxx);
            ceh _snowmanxxxxxx = _snowman.d_(_snowmanxxxxx);
            if (this.a(_snowman, _snowman, _snowman, _snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowman.b(_snowmanxxxxx), _snowmanxxxx.a())) {
               this.a(_snowman, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxx, _snowmanxxxx);
            }
         }
      }
   }

   protected cux a(brz var1, fx var2, ceh var3) {
      int _snowman = 0;
      int _snowmanx = 0;

      for (gc _snowmanxx : gc.c.a) {
         fx _snowmanxxx = _snowman.a(_snowmanxx);
         ceh _snowmanxxxx = _snowman.d_(_snowmanxxx);
         cux _snowmanxxxxx = _snowmanxxxx.m();
         if (_snowmanxxxxx.a().a(this) && this.a(_snowmanxx, _snowman, _snowman, _snowman, _snowmanxxx, _snowmanxxxx)) {
            if (_snowmanxxxxx.b()) {
               _snowmanx++;
            }

            _snowman = Math.max(_snowman, _snowmanxxxxx.e());
         }
      }

      if (this.f() && _snowmanx >= 2) {
         ceh _snowmanxxx = _snowman.d_(_snowman.c());
         cux _snowmanxxxx = _snowmanxxx.m();
         if (_snowmanxxx.c().b() || this.h(_snowmanxxxx)) {
            return this.a(false);
         }
      }

      fx _snowmanxxx = _snowman.b();
      ceh _snowmanxxxx = _snowman.d_(_snowmanxxx);
      cux _snowmanxxxxx = _snowmanxxxx.m();
      if (!_snowmanxxxxx.c() && _snowmanxxxxx.a().a(this) && this.a(gc.b, _snowman, _snowman, _snowman, _snowmanxxx, _snowmanxxxx)) {
         return this.a(8, true);
      } else {
         int _snowmanxxxxxx = _snowman - this.c(_snowman);
         return _snowmanxxxxxx <= 0 ? cuy.a.h() : this.a(_snowmanxxxxxx, false);
      }
   }

   private boolean a(gc var1, brc var2, fx var3, ceh var4, fx var5, ceh var6) {
      Object2ByteLinkedOpenHashMap<buo.a> _snowman;
      if (!_snowman.b().o() && !_snowman.b().o()) {
         _snowman = e.get();
      } else {
         _snowman = null;
      }

      buo.a _snowmanx;
      if (_snowman != null) {
         _snowmanx = new buo.a(_snowman, _snowman, _snowman);
         byte _snowmanxx = _snowman.getAndMoveToFirst(_snowmanx);
         if (_snowmanxx != 127) {
            return _snowmanxx != 0;
         }
      } else {
         _snowmanx = null;
      }

      ddh _snowmanxx = _snowman.k(_snowman, _snowman);
      ddh _snowmanxxx = _snowman.k(_snowman, _snowman);
      boolean _snowmanxxxx = !dde.b(_snowmanxx, _snowmanxxx, _snowman);
      if (_snowman != null) {
         if (_snowman.size() == 200) {
            _snowman.removeLastByte();
         }

         _snowman.putAndMoveToFirst(_snowmanx, (byte)(_snowmanxxxx ? 1 : 0));
      }

      return _snowmanxxxx;
   }

   public abstract cuw d();

   public cux a(int var1, boolean var2) {
      return this.d().h().a(b, _snowman).a(a, _snowman);
   }

   public abstract cuw e();

   public cux a(boolean var1) {
      return this.e().h().a(a, _snowman);
   }

   protected abstract boolean f();

   protected void a(bry var1, fx var2, ceh var3, gc var4, cux var5) {
      if (_snowman.b() instanceof byc) {
         ((byc)_snowman.b()).a(_snowman, _snowman, _snowman, _snowman);
      } else {
         if (!_snowman.g()) {
            this.a(_snowman, _snowman, _snowman);
         }

         _snowman.a(_snowman, _snowman.g(), 3);
      }
   }

   protected abstract void a(bry var1, fx var2, ceh var3);

   private static short a(fx var0, fx var1) {
      int _snowman = _snowman.u() - _snowman.u();
      int _snowmanx = _snowman.w() - _snowman.w();
      return (short)((_snowman + 128 & 0xFF) << 8 | _snowmanx + 128 & 0xFF);
   }

   protected int a(brz var1, fx var2, int var3, gc var4, ceh var5, fx var6, Short2ObjectMap<Pair<ceh, cux>> var7, Short2BooleanMap var8) {
      int _snowman = 1000;

      for (gc _snowmanx : gc.c.a) {
         if (_snowmanx != _snowman) {
            fx _snowmanxx = _snowman.a(_snowmanx);
            short _snowmanxxx = a(_snowman, _snowmanxx);
            Pair<ceh, cux> _snowmanxxxx = (Pair<ceh, cux>)_snowman.computeIfAbsent(_snowmanxxx, var2x -> {
               ceh _snowmanxxxxx = _snowman.d_(_snowman);
               return Pair.of(_snowmanxxxxx, _snowmanxxxxx.m());
            });
            ceh _snowmanxxxxx = (ceh)_snowmanxxxx.getFirst();
            cux _snowmanxxxxxx = (cux)_snowmanxxxx.getSecond();
            if (this.a(_snowman, this.d(), _snowman, _snowman, _snowmanx, _snowmanxx, _snowmanxxxxx, _snowmanxxxxxx)) {
               boolean _snowmanxxxxxxx = _snowman.computeIfAbsent(_snowmanxxx, var4x -> {
                  fx _snowmanxxxxxxxx = _snowman.c();
                  ceh _snowmanx = _snowman.d_(_snowmanxxxxxxxx);
                  return this.a(_snowman, this.d(), _snowman, _snowman, _snowmanxxxxxxxx, _snowmanx);
               });
               if (_snowmanxxxxxxx) {
                  return _snowman;
               }

               if (_snowman < this.b(_snowman)) {
                  int _snowmanxxxxxxxx = this.a(_snowman, _snowmanxx, _snowman + 1, _snowmanx.f(), _snowmanxxxxx, _snowman, _snowman, _snowman);
                  if (_snowmanxxxxxxxx < _snowman) {
                     _snowman = _snowmanxxxxxxxx;
                  }
               }
            }
         }
      }

      return _snowman;
   }

   private boolean a(brc var1, cuw var2, fx var3, ceh var4, fx var5, ceh var6) {
      if (!this.a(gc.a, _snowman, _snowman, _snowman, _snowman, _snowman)) {
         return false;
      } else {
         return _snowman.m().a().a(this) ? true : this.a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   private boolean a(brc var1, cuw var2, fx var3, ceh var4, gc var5, fx var6, ceh var7, cux var8) {
      return !this.h(_snowman) && this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman) && this.a(_snowman, _snowman, _snowman, _snowman);
   }

   private boolean h(cux var1) {
      return _snowman.a().a(this) && _snowman.b();
   }

   protected abstract int b(brz var1);

   private int a(brz var1, fx var2) {
      int _snowman = 0;

      for (gc _snowmanx : gc.c.a) {
         fx _snowmanxx = _snowman.a(_snowmanx);
         cux _snowmanxxx = _snowman.b(_snowmanxx);
         if (this.h(_snowmanxxx)) {
            _snowman++;
         }
      }

      return _snowman;
   }

   protected Map<gc, cux> b(brz var1, fx var2, ceh var3) {
      int _snowman = 1000;
      Map<gc, cux> _snowmanx = Maps.newEnumMap(gc.class);
      Short2ObjectMap<Pair<ceh, cux>> _snowmanxx = new Short2ObjectOpenHashMap();
      Short2BooleanMap _snowmanxxx = new Short2BooleanOpenHashMap();

      for (gc _snowmanxxxx : gc.c.a) {
         fx _snowmanxxxxx = _snowman.a(_snowmanxxxx);
         short _snowmanxxxxxx = a(_snowman, _snowmanxxxxx);
         Pair<ceh, cux> _snowmanxxxxxxx = (Pair<ceh, cux>)_snowmanxx.computeIfAbsent(_snowmanxxxxxx, var2x -> {
            ceh _snowmanxxxxxxxx = _snowman.d_(_snowman);
            return Pair.of(_snowmanxxxxxxxx, _snowmanxxxxxxxx.m());
         });
         ceh _snowmanxxxxxxxx = (ceh)_snowmanxxxxxxx.getFirst();
         cux _snowmanxxxxxxxxx = (cux)_snowmanxxxxxxx.getSecond();
         cux _snowmanxxxxxxxxxx = this.a(_snowman, _snowmanxxxxx, _snowmanxxxxxxxx);
         if (this.a(_snowman, _snowmanxxxxxxxxxx.a(), _snowman, _snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx)) {
            fx _snowmanxxxxxxxxxxx = _snowmanxxxxx.c();
            boolean _snowmanxxxxxxxxxxxx = _snowmanxxx.computeIfAbsent(_snowmanxxxxxx, var5x -> {
               ceh _snowmanxxxxxxxxxxxxx = _snowman.d_(_snowman);
               return this.a(_snowman, this.d(), _snowman, _snowman, _snowman, _snowmanxxxxxxxxxxxxx);
            });
            int _snowmanxxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxxxx = 0;
            } else {
               _snowmanxxxxxxxxxxxxx = this.a(_snowman, _snowmanxxxxx, 1, _snowmanxxxx.f(), _snowmanxxxxxxxx, _snowman, _snowmanxx, _snowmanxxx);
            }

            if (_snowmanxxxxxxxxxxxxx < _snowman) {
               _snowmanx.clear();
            }

            if (_snowmanxxxxxxxxxxxxx <= _snowman) {
               _snowmanx.put(_snowmanxxxx, _snowmanxxxxxxxxxx);
               _snowman = _snowmanxxxxxxxxxxxxx;
            }
         }
      }

      return _snowmanx;
   }

   private boolean a(brc var1, fx var2, ceh var3, cuw var4) {
      buo _snowman = _snowman.b();
      if (_snowman instanceof byc) {
         return ((byc)_snowman).a(_snowman, _snowman, _snowman, _snowman);
      } else if (!(_snowman instanceof bwb) && !_snowman.a(aed.af) && _snowman != bup.cg && _snowman != bup.cH && _snowman != bup.lc) {
         cva _snowmanx = _snowman.c();
         return _snowmanx != cva.c && _snowmanx != cva.b && _snowmanx != cva.f && _snowmanx != cva.i ? !_snowmanx.c() : false;
      } else {
         return false;
      }
   }

   protected boolean a(brc var1, fx var2, ceh var3, gc var4, fx var5, ceh var6, cux var7, cuw var8) {
      return _snowman.a(_snowman, _snowman, _snowman, _snowman) && this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman) && this.a(_snowman, _snowman, _snowman, _snowman);
   }

   protected abstract int c(brz var1);

   protected int a(brx var1, fx var2, cux var3, cux var4) {
      return this.a(_snowman);
   }

   @Override
   public void a(brx var1, fx var2, cux var3) {
      if (!_snowman.b()) {
         cux _snowman = this.a((brz)_snowman, _snowman, _snowman.d_(_snowman));
         int _snowmanx = this.a(_snowman, _snowman, _snowman, _snowman);
         if (_snowman.c()) {
            _snowman = _snowman;
            _snowman.a(_snowman, bup.a.n(), 3);
         } else if (!_snowman.equals(_snowman)) {
            _snowman = _snowman;
            ceh _snowmanxx = _snowman.g();
            _snowman.a(_snowman, _snowmanxx, 2);
            _snowman.I().a(_snowman, _snowman.a(), _snowmanx);
            _snowman.b(_snowman, _snowmanxx.b());
         }
      }

      this.a((bry)_snowman, _snowman, _snowman);
   }

   protected static int e(cux var0) {
      return _snowman.b() ? 0 : 8 - Math.min(_snowman.e(), 8) + (_snowman.c(a) ? 8 : 0);
   }

   private static boolean c(cux var0, brc var1, fx var2) {
      return _snowman.a().a(_snowman.b(_snowman.b()).a());
   }

   @Override
   public float a(cux var1, brc var2, fx var3) {
      return c(_snowman, _snowman, _snowman) ? 1.0F : _snowman.d();
   }

   @Override
   public float a(cux var1) {
      return (float)_snowman.e() / 9.0F;
   }

   @Override
   public ddh b(cux var1, brc var2, fx var3) {
      return _snowman.e() == 9 && c(_snowman, _snowman, _snowman) ? dde.b() : this.f.computeIfAbsent(_snowman, var2x -> dde.a(0.0, 0.0, 0.0, 1.0, (double)var2x.a(_snowman, _snowman), 1.0));
   }
}
