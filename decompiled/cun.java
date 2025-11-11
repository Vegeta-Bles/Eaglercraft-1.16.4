import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import javax.annotation.Nullable;

public abstract class cun<M extends cui<M>> extends aac {
   protected static final cgb a = new cgb();
   private static final gc[] k = gc.values();
   private final bsf l;
   private final cgj m;
   protected final LongSet b = new LongOpenHashSet();
   protected final LongSet c = new LongOpenHashSet();
   protected final LongSet d = new LongOpenHashSet();
   protected volatile M e;
   protected final M f;
   protected final LongSet g = new LongOpenHashSet();
   protected final LongSet h = new LongOpenHashSet();
   protected final Long2ObjectMap<cgb> i = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap());
   private final LongSet n = new LongOpenHashSet();
   private final LongSet o = new LongOpenHashSet();
   private final LongSet p = new LongOpenHashSet();
   protected volatile boolean j;

   protected cun(bsf var1, cgj var2, M var3) {
      super(3, 16, 256);
      this.l = _snowman;
      this.m = _snowman;
      this.f = _snowman;
      this.e = _snowman.b();
      this.e.d();
   }

   protected boolean g(long var1) {
      return this.a(_snowman, true) != null;
   }

   @Nullable
   protected cgb a(long var1, boolean var3) {
      return this.a(_snowman ? this.f : this.e, _snowman);
   }

   @Nullable
   protected cgb a(M var1, long var2) {
      return _snowman.c(_snowman);
   }

   @Nullable
   public cgb h(long var1) {
      cgb _snowman = (cgb)this.i.get(_snowman);
      return _snowman != null ? _snowman : this.a(_snowman, false);
   }

   protected abstract int d(long var1);

   protected int i(long var1) {
      long _snowman = gp.e(_snowman);
      cgb _snowmanx = this.a(_snowman, true);
      return _snowmanx.a(gp.b(fx.b(_snowman)), gp.b(fx.c(_snowman)), gp.b(fx.d(_snowman)));
   }

   protected void b(long var1, int var3) {
      long _snowman = gp.e(_snowman);
      if (this.g.add(_snowman)) {
         this.f.a(_snowman);
      }

      cgb _snowmanx = this.a(_snowman, true);
      _snowmanx.a(gp.b(fx.b(_snowman)), gp.b(fx.c(_snowman)), gp.b(fx.d(_snowman)), _snowman);

      for (int _snowmanxx = -1; _snowmanxx <= 1; _snowmanxx++) {
         for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
            for (int _snowmanxxxx = -1; _snowmanxxxx <= 1; _snowmanxxxx++) {
               this.h.add(gp.e(fx.a(_snowman, _snowmanxxx, _snowmanxxxx, _snowmanxx)));
            }
         }
      }
   }

   @Override
   protected int c(long var1) {
      if (_snowman == Long.MAX_VALUE) {
         return 2;
      } else if (this.b.contains(_snowman)) {
         return 0;
      } else {
         return !this.p.contains(_snowman) && this.f.b(_snowman) ? 1 : 2;
      }
   }

   @Override
   protected int b(long var1) {
      if (this.c.contains(_snowman)) {
         return 2;
      } else {
         return !this.b.contains(_snowman) && !this.d.contains(_snowman) ? 2 : 0;
      }
   }

   @Override
   protected void a(long var1, int var3) {
      int _snowman = this.c(_snowman);
      if (_snowman != 0 && _snowman == 0) {
         this.b.add(_snowman);
         this.d.remove(_snowman);
      }

      if (_snowman == 0 && _snowman != 0) {
         this.b.remove(_snowman);
         this.c.remove(_snowman);
      }

      if (_snowman >= 2 && _snowman != 2) {
         if (this.p.contains(_snowman)) {
            this.p.remove(_snowman);
         } else {
            this.f.a(_snowman, this.j(_snowman));
            this.g.add(_snowman);
            this.k(_snowman);

            for (int _snowmanx = -1; _snowmanx <= 1; _snowmanx++) {
               for (int _snowmanxx = -1; _snowmanxx <= 1; _snowmanxx++) {
                  for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
                     this.h.add(gp.e(fx.a(_snowman, _snowmanxx, _snowmanxxx, _snowmanx)));
                  }
               }
            }
         }
      }

      if (_snowman != 2 && _snowman >= 2) {
         this.p.add(_snowman);
      }

      this.j = !this.p.isEmpty();
   }

   protected cgb j(long var1) {
      cgb _snowman = (cgb)this.i.get(_snowman);
      return _snowman != null ? _snowman : new cgb();
   }

   protected void a(cul<?, ?> var1, long var2) {
      if (_snowman.c() < 8192) {
         _snowman.a(var2x -> gp.e(var2x) == _snowman);
      } else {
         int _snowman = gp.c(gp.b(_snowman));
         int _snowmanx = gp.c(gp.c(_snowman));
         int _snowmanxx = gp.c(gp.d(_snowman));

         for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
            for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
               for (int _snowmanxxxxx = 0; _snowmanxxxxx < 16; _snowmanxxxxx++) {
                  long _snowmanxxxxxx = fx.a(_snowman + _snowmanxxx, _snowmanx + _snowmanxxxx, _snowmanxx + _snowmanxxxxx);
                  _snowman.e(_snowmanxxxxxx);
               }
            }
         }
      }
   }

   protected boolean a() {
      return this.j;
   }

   protected void a(cul<M, ?> var1, boolean var2, boolean var3) {
      if (this.a() || !this.i.isEmpty()) {
         LongIterator var4 = this.p.iterator();

         while (var4.hasNext()) {
            long _snowman = (Long)var4.next();
            this.a(_snowman, _snowman);
            cgb _snowmanx = (cgb)this.i.remove(_snowman);
            cgb _snowmanxx = this.f.d(_snowman);
            if (this.o.contains(gp.f(_snowman))) {
               if (_snowmanx != null) {
                  this.i.put(_snowman, _snowmanx);
               } else if (_snowmanxx != null) {
                  this.i.put(_snowman, _snowmanxx);
               }
            }
         }

         this.f.c();
         var4 = this.p.iterator();

         while (var4.hasNext()) {
            long _snowman = (Long)var4.next();
            this.l(_snowman);
         }

         this.p.clear();
         this.j = false;
         ObjectIterator var10 = this.i.long2ObjectEntrySet().iterator();

         while (var10.hasNext()) {
            Entry<cgb> _snowman = (Entry<cgb>)var10.next();
            long _snowmanx = _snowman.getLongKey();
            if (this.g(_snowmanx)) {
               cgb _snowmanxx = (cgb)_snowman.getValue();
               if (this.f.c(_snowmanx) != _snowmanxx) {
                  this.a(_snowman, _snowmanx);
                  this.f.a(_snowmanx, _snowmanxx);
                  this.g.add(_snowmanx);
               }
            }
         }

         this.f.c();
         if (!_snowman) {
            var4 = this.i.keySet().iterator();

            while (var4.hasNext()) {
               long _snowman = (Long)var4.next();
               this.b(_snowman, _snowman);
            }
         } else {
            var4 = this.n.iterator();

            while (var4.hasNext()) {
               long _snowman = (Long)var4.next();
               this.b(_snowman, _snowman);
            }
         }

         this.n.clear();
         ObjectIterator<Entry<cgb>> _snowman = this.i.long2ObjectEntrySet().iterator();

         while (_snowman.hasNext()) {
            Entry<cgb> _snowmanx = (Entry<cgb>)_snowman.next();
            long _snowmanxx = _snowmanx.getLongKey();
            if (this.g(_snowmanxx)) {
               _snowman.remove();
            }
         }
      }
   }

   private void b(cul<M, ?> var1, long var2) {
      if (this.g(_snowman)) {
         int _snowman = gp.c(gp.b(_snowman));
         int _snowmanx = gp.c(gp.c(_snowman));
         int _snowmanxx = gp.c(gp.d(_snowman));

         for (gc _snowmanxxx : k) {
            long _snowmanxxxx = gp.a(_snowman, _snowmanxxx);
            if (!this.i.containsKey(_snowmanxxxx) && this.g(_snowmanxxxx)) {
               for (int _snowmanxxxxx = 0; _snowmanxxxxx < 16; _snowmanxxxxx++) {
                  for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 16; _snowmanxxxxxx++) {
                     long _snowmanxxxxxxxx;
                     long _snowmanxxxxxxx;
                     switch (_snowmanxxx) {
                        case a:
                           _snowmanxxxxxxxx = fx.a(_snowman + _snowmanxxxxxx, _snowmanx, _snowmanxx + _snowmanxxxxx);
                           _snowmanxxxxxxx = fx.a(_snowman + _snowmanxxxxxx, _snowmanx - 1, _snowmanxx + _snowmanxxxxx);
                           break;
                        case b:
                           _snowmanxxxxxxxx = fx.a(_snowman + _snowmanxxxxxx, _snowmanx + 16 - 1, _snowmanxx + _snowmanxxxxx);
                           _snowmanxxxxxxx = fx.a(_snowman + _snowmanxxxxxx, _snowmanx + 16, _snowmanxx + _snowmanxxxxx);
                           break;
                        case c:
                           _snowmanxxxxxxxx = fx.a(_snowman + _snowmanxxxxx, _snowmanx + _snowmanxxxxxx, _snowmanxx);
                           _snowmanxxxxxxx = fx.a(_snowman + _snowmanxxxxx, _snowmanx + _snowmanxxxxxx, _snowmanxx - 1);
                           break;
                        case d:
                           _snowmanxxxxxxxx = fx.a(_snowman + _snowmanxxxxx, _snowmanx + _snowmanxxxxxx, _snowmanxx + 16 - 1);
                           _snowmanxxxxxxx = fx.a(_snowman + _snowmanxxxxx, _snowmanx + _snowmanxxxxxx, _snowmanxx + 16);
                           break;
                        case e:
                           _snowmanxxxxxxxx = fx.a(_snowman, _snowmanx + _snowmanxxxxx, _snowmanxx + _snowmanxxxxxx);
                           _snowmanxxxxxxx = fx.a(_snowman - 1, _snowmanx + _snowmanxxxxx, _snowmanxx + _snowmanxxxxxx);
                           break;
                        default:
                           _snowmanxxxxxxxx = fx.a(_snowman + 16 - 1, _snowmanx + _snowmanxxxxx, _snowmanxx + _snowmanxxxxxx);
                           _snowmanxxxxxxx = fx.a(_snowman + 16, _snowmanx + _snowmanxxxxx, _snowmanxx + _snowmanxxxxxx);
                     }

                     _snowman.a(_snowmanxxxxxxxx, _snowmanxxxxxxx, _snowman.b(_snowmanxxxxxxxx, _snowmanxxxxxxx, _snowman.c(_snowmanxxxxxxxx)), false);
                     _snowman.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowman.b(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowman.c(_snowmanxxxxxxx)), false);
                  }
               }
            }
         }
      }
   }

   protected void k(long var1) {
   }

   protected void l(long var1) {
   }

   protected void b(long var1, boolean var3) {
   }

   public void c(long var1, boolean var3) {
      if (_snowman) {
         this.o.add(_snowman);
      } else {
         this.o.remove(_snowman);
      }
   }

   protected void a(long var1, @Nullable cgb var3, boolean var4) {
      if (_snowman != null) {
         this.i.put(_snowman, _snowman);
         if (!_snowman) {
            this.n.add(_snowman);
         }
      } else {
         this.i.remove(_snowman);
      }
   }

   protected void d(long var1, boolean var3) {
      boolean _snowman = this.b.contains(_snowman);
      if (!_snowman && !_snowman) {
         this.d.add(_snowman);
         this.a(Long.MAX_VALUE, _snowman, 0, true);
      }

      if (_snowman && _snowman) {
         this.c.add(_snowman);
         this.a(Long.MAX_VALUE, _snowman, 2, false);
      }
   }

   protected void d() {
      if (this.b()) {
         this.b(Integer.MAX_VALUE);
      }
   }

   protected void e() {
      if (!this.g.isEmpty()) {
         M _snowman = this.f.b();
         _snowman.d();
         this.e = _snowman;
         this.g.clear();
      }

      if (!this.h.isEmpty()) {
         LongIterator _snowman = this.h.iterator();

         while (_snowman.hasNext()) {
            long _snowmanx = _snowman.nextLong();
            this.m.a(this.l, gp.a(_snowmanx));
         }

         this.h.clear();
      }
   }
}
