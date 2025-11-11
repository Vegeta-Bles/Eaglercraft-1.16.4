import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Arrays;

public class cur extends cun<cur.a> {
   private static final gc[] k = new gc[]{gc.c, gc.d, gc.e, gc.f};
   private final LongSet l = new LongOpenHashSet();
   private final LongSet m = new LongOpenHashSet();
   private final LongSet n = new LongOpenHashSet();
   private final LongSet o = new LongOpenHashSet();
   private volatile boolean p;

   protected cur(cgj var1) {
      super(bsf.a, _snowman, new cur.a(new Long2ObjectOpenHashMap(), new Long2IntOpenHashMap(), Integer.MAX_VALUE));
   }

   @Override
   protected int d(long var1) {
      long _snowman = gp.e(_snowman);
      int _snowmanx = gp.c(_snowman);
      cur.a _snowmanxx = this.e;
      int _snowmanxxx = _snowmanxx.c.get(gp.f(_snowman));
      if (_snowmanxxx != _snowmanxx.b && _snowmanx < _snowmanxxx) {
         cgb _snowmanxxxx = this.a(_snowmanxx, _snowman);
         if (_snowmanxxxx == null) {
            for (_snowman = fx.f(_snowman); _snowmanxxxx == null; _snowmanxxxx = this.a(_snowmanxx, _snowman)) {
               _snowman = gp.a(_snowman, gc.b);
               if (++_snowmanx >= _snowmanxxx) {
                  return 15;
               }

               _snowman = fx.a(_snowman, 0, 16, 0);
            }
         }

         return _snowmanxxxx.a(gp.b(fx.b(_snowman)), gp.b(fx.c(_snowman)), gp.b(fx.d(_snowman)));
      } else {
         return 15;
      }
   }

   @Override
   protected void k(long var1) {
      int _snowman = gp.c(_snowman);
      if (this.f.b > _snowman) {
         this.f.b = _snowman;
         this.f.c.defaultReturnValue(this.f.b);
      }

      long _snowmanx = gp.f(_snowman);
      int _snowmanxx = this.f.c.get(_snowmanx);
      if (_snowmanxx < _snowman + 1) {
         this.f.c.put(_snowmanx, _snowman + 1);
         if (this.o.contains(_snowmanx)) {
            this.q(_snowman);
            if (_snowmanxx > this.f.b) {
               long _snowmanxxx = gp.b(gp.b(_snowman), _snowmanxx - 1, gp.d(_snowman));
               this.p(_snowmanxxx);
            }

            this.f();
         }
      }
   }

   private void p(long var1) {
      this.n.add(_snowman);
      this.m.remove(_snowman);
   }

   private void q(long var1) {
      this.m.add(_snowman);
      this.n.remove(_snowman);
   }

   private void f() {
      this.p = !this.m.isEmpty() || !this.n.isEmpty();
   }

   @Override
   protected void l(long var1) {
      long _snowman = gp.f(_snowman);
      boolean _snowmanx = this.o.contains(_snowman);
      if (_snowmanx) {
         this.p(_snowman);
      }

      int _snowmanxx = gp.c(_snowman);
      if (this.f.c.get(_snowman) == _snowmanxx + 1) {
         long _snowmanxxx;
         for (_snowmanxxx = _snowman; !this.g(_snowmanxxx) && this.a(_snowmanxx); _snowmanxxx = gp.a(_snowmanxxx, gc.a)) {
            _snowmanxx--;
         }

         if (this.g(_snowmanxxx)) {
            this.f.c.put(_snowman, _snowmanxx + 1);
            if (_snowmanx) {
               this.q(_snowmanxxx);
            }
         } else {
            this.f.c.remove(_snowman);
         }
      }

      if (_snowmanx) {
         this.f();
      }
   }

   @Override
   protected void b(long var1, boolean var3) {
      this.d();
      if (_snowman && this.o.add(_snowman)) {
         int _snowman = this.f.c.get(_snowman);
         if (_snowman != this.f.b) {
            long _snowmanx = gp.b(gp.b(_snowman), _snowman - 1, gp.d(_snowman));
            this.q(_snowmanx);
            this.f();
         }
      } else if (!_snowman) {
         this.o.remove(_snowman);
      }
   }

   @Override
   protected boolean a() {
      return super.a() || this.p;
   }

   @Override
   protected cgb j(long var1) {
      cgb _snowman = (cgb)this.i.get(_snowman);
      if (_snowman != null) {
         return _snowman;
      } else {
         long _snowmanx = gp.a(_snowman, gc.b);
         int _snowmanxx = this.f.c.get(gp.f(_snowman));
         if (_snowmanxx != this.f.b && gp.c(_snowmanx) < _snowmanxx) {
            cgb _snowmanxxx;
            while ((_snowmanxxx = this.a(_snowmanx, true)) == null) {
               _snowmanx = gp.a(_snowmanx, gc.b);
            }

            return new cgb(new cuk(_snowmanxxx, 0).a());
         } else {
            return new cgb();
         }
      }
   }

   @Override
   protected void a(cul<cur.a, ?> var1, boolean var2, boolean var3) {
      super.a(_snowman, _snowman, _snowman);
      if (_snowman) {
         if (!this.m.isEmpty()) {
            LongIterator var4 = this.m.iterator();

            while (var4.hasNext()) {
               long _snowman = (Long)var4.next();
               int _snowmanx = this.c(_snowman);
               if (_snowmanx != 2 && !this.n.contains(_snowman) && this.l.add(_snowman)) {
                  if (_snowmanx == 1) {
                     this.a(_snowman, _snowman);
                     if (this.g.add(_snowman)) {
                        this.f.a(_snowman);
                     }

                     Arrays.fill(this.a(_snowman, true).a(), (byte)-1);
                     int _snowmanxx = gp.c(gp.b(_snowman));
                     int _snowmanxxx = gp.c(gp.c(_snowman));
                     int _snowmanxxxx = gp.c(gp.d(_snowman));

                     for (gc _snowmanxxxxx : k) {
                        long _snowmanxxxxxx = gp.a(_snowman, _snowmanxxxxx);
                        if ((this.n.contains(_snowmanxxxxxx) || !this.l.contains(_snowmanxxxxxx) && !this.m.contains(_snowmanxxxxxx)) && this.g(_snowmanxxxxxx)) {
                           for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 16; _snowmanxxxxxxx++) {
                              for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 16; _snowmanxxxxxxxx++) {
                                 long _snowmanxxxxxxxxx;
                                 long _snowmanxxxxxxxxxx;
                                 switch (_snowmanxxxxx) {
                                    case c:
                                       _snowmanxxxxxxxxx = fx.a(_snowmanxx + _snowmanxxxxxxx, _snowmanxxx + _snowmanxxxxxxxx, _snowmanxxxx);
                                       _snowmanxxxxxxxxxx = fx.a(_snowmanxx + _snowmanxxxxxxx, _snowmanxxx + _snowmanxxxxxxxx, _snowmanxxxx - 1);
                                       break;
                                    case d:
                                       _snowmanxxxxxxxxx = fx.a(_snowmanxx + _snowmanxxxxxxx, _snowmanxxx + _snowmanxxxxxxxx, _snowmanxxxx + 16 - 1);
                                       _snowmanxxxxxxxxxx = fx.a(_snowmanxx + _snowmanxxxxxxx, _snowmanxxx + _snowmanxxxxxxxx, _snowmanxxxx + 16);
                                       break;
                                    case e:
                                       _snowmanxxxxxxxxx = fx.a(_snowmanxx, _snowmanxxx + _snowmanxxxxxxx, _snowmanxxxx + _snowmanxxxxxxxx);
                                       _snowmanxxxxxxxxxx = fx.a(_snowmanxx - 1, _snowmanxxx + _snowmanxxxxxxx, _snowmanxxxx + _snowmanxxxxxxxx);
                                       break;
                                    default:
                                       _snowmanxxxxxxxxx = fx.a(_snowmanxx + 16 - 1, _snowmanxxx + _snowmanxxxxxxx, _snowmanxxxx + _snowmanxxxxxxxx);
                                       _snowmanxxxxxxxxxx = fx.a(_snowmanxx + 16, _snowmanxxx + _snowmanxxxxxxx, _snowmanxxxx + _snowmanxxxxxxxx);
                                 }

                                 _snowman.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowman.b(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, 0), true);
                              }
                           }
                        }
                     }

                     for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 16; _snowmanxxxxxx++) {
                        for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 16; _snowmanxxxxxxx++) {
                           long _snowmanxxxxxxxx = fx.a(gp.c(gp.b(_snowman)) + _snowmanxxxxxx, gp.c(gp.c(_snowman)), gp.c(gp.d(_snowman)) + _snowmanxxxxxxx);
                           long _snowmanxxxxxxxxx = fx.a(gp.c(gp.b(_snowman)) + _snowmanxxxxxx, gp.c(gp.c(_snowman)) - 1, gp.c(gp.d(_snowman)) + _snowmanxxxxxxx);
                           _snowman.a(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowman.b(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0), true);
                        }
                     }
                  } else {
                     for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
                        for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
                           long _snowmanxxxx = fx.a(gp.c(gp.b(_snowman)) + _snowmanxx, gp.c(gp.c(_snowman)) + 16 - 1, gp.c(gp.d(_snowman)) + _snowmanxxx);
                           _snowman.a(Long.MAX_VALUE, _snowmanxxxx, 0, true);
                        }
                     }
                  }
               }
            }
         }

         this.m.clear();
         if (!this.n.isEmpty()) {
            LongIterator var23 = this.n.iterator();

            while (var23.hasNext()) {
               long _snowman = (Long)var23.next();
               if (this.l.remove(_snowman) && this.g(_snowman)) {
                  for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
                     for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
                        long _snowmanxxx = fx.a(gp.c(gp.b(_snowman)) + _snowmanx, gp.c(gp.c(_snowman)) + 16 - 1, gp.c(gp.d(_snowman)) + _snowmanxx);
                        _snowman.a(Long.MAX_VALUE, _snowmanxxx, 15, false);
                     }
                  }
               }
            }
         }

         this.n.clear();
         this.p = false;
      }
   }

   protected boolean a(int var1) {
      return _snowman >= this.f.b;
   }

   protected boolean m(long var1) {
      int _snowman = fx.c(_snowman);
      if ((_snowman & 15) != 15) {
         return false;
      } else {
         long _snowmanx = gp.e(_snowman);
         long _snowmanxx = gp.f(_snowmanx);
         if (!this.o.contains(_snowmanxx)) {
            return false;
         } else {
            int _snowmanxxx = this.f.c.get(_snowmanxx);
            return gp.c(_snowmanxxx) == _snowman + 16;
         }
      }
   }

   protected boolean n(long var1) {
      long _snowman = gp.f(_snowman);
      int _snowmanx = this.f.c.get(_snowman);
      return _snowmanx == this.f.b || gp.c(_snowman) >= _snowmanx;
   }

   protected boolean o(long var1) {
      long _snowman = gp.f(_snowman);
      return this.o.contains(_snowman);
   }

   public static final class a extends cui<cur.a> {
      private int b;
      private final Long2IntOpenHashMap c;

      public a(Long2ObjectOpenHashMap<cgb> var1, Long2IntOpenHashMap var2, int var3) {
         super(_snowman);
         this.c = _snowman;
         _snowman.defaultReturnValue(_snowman);
         this.b = _snowman;
      }

      public cur.a a() {
         return new cur.a(this.a.clone(), this.c.clone(), this.b);
      }
   }
}
