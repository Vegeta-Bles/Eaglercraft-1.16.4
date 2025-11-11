import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongList;
import java.util.function.LongPredicate;

public abstract class cuj {
   private final int a;
   private final LongLinkedOpenHashSet[] b;
   private final Long2ByteMap c;
   private int d;
   private volatile boolean e;

   protected cuj(int var1, final int var2, final int var3) {
      if (_snowman >= 254) {
         throw new IllegalArgumentException("Level count must be < 254.");
      } else {
         this.a = _snowman;
         this.b = new LongLinkedOpenHashSet[_snowman];

         for (int _snowman = 0; _snowman < _snowman; _snowman++) {
            this.b[_snowman] = new LongLinkedOpenHashSet(_snowman, 0.5F) {
               protected void rehash(int var1) {
                  if (_snowman > _snowman) {
                     super.rehash(_snowman);
                  }
               }
            };
         }

         this.c = new Long2ByteOpenHashMap(_snowman, 0.5F) {
            protected void rehash(int var1) {
               if (_snowman > _snowman) {
                  super.rehash(_snowman);
               }
            }
         };
         this.c.defaultReturnValue((byte)-1);
         this.d = _snowman;
      }
   }

   private int a(int var1, int var2) {
      int _snowman = _snowman;
      if (_snowman > _snowman) {
         _snowman = _snowman;
      }

      if (_snowman > this.a - 1) {
         _snowman = this.a - 1;
      }

      return _snowman;
   }

   private void a(int var1) {
      int _snowman = this.d;
      this.d = _snowman;

      for (int _snowmanx = _snowman + 1; _snowmanx < _snowman; _snowmanx++) {
         if (!this.b[_snowmanx].isEmpty()) {
            this.d = _snowmanx;
            break;
         }
      }
   }

   protected void e(long var1) {
      int _snowman = this.c.get(_snowman) & 255;
      if (_snowman != 255) {
         int _snowmanx = this.c(_snowman);
         int _snowmanxx = this.a(_snowmanx, _snowman);
         this.a(_snowman, _snowmanxx, this.a, true);
         this.e = this.d < this.a;
      }
   }

   public void a(LongPredicate var1) {
      LongList _snowman = new LongArrayList();
      this.c.keySet().forEach(var2x -> {
         if (_snowman.test(var2x)) {
            _snowman.add(var2x);
         }
      });
      _snowman.forEach(this::e);
   }

   private void a(long var1, int var3, int var4, boolean var5) {
      if (_snowman) {
         this.c.remove(_snowman);
      }

      this.b[_snowman].remove(_snowman);
      if (this.b[_snowman].isEmpty() && this.d == _snowman) {
         this.a(_snowman);
      }
   }

   private void a(long var1, int var3, int var4) {
      this.c.put(_snowman, (byte)_snowman);
      this.b[_snowman].add(_snowman);
      if (this.d > _snowman) {
         this.d = _snowman;
      }
   }

   protected void f(long var1) {
      this.a(_snowman, _snowman, this.a - 1, false);
   }

   protected void a(long var1, long var3, int var5, boolean var6) {
      this.a(_snowman, _snowman, _snowman, this.c(_snowman), this.c.get(_snowman) & 255, _snowman);
      this.e = this.d < this.a;
   }

   private void a(long var1, long var3, int var5, int var6, int var7, boolean var8) {
      if (!this.a(_snowman)) {
         _snowman = afm.a(_snowman, 0, this.a - 1);
         _snowman = afm.a(_snowman, 0, this.a - 1);
         boolean _snowman;
         if (_snowman == 255) {
            _snowman = true;
            _snowman = _snowman;
         } else {
            _snowman = false;
         }

         int _snowmanx;
         if (_snowman) {
            _snowmanx = Math.min(_snowman, _snowman);
         } else {
            _snowmanx = afm.a(this.a(_snowman, _snowman, _snowman), 0, this.a - 1);
         }

         int _snowmanxx = this.a(_snowman, _snowman);
         if (_snowman != _snowmanx) {
            int _snowmanxxx = this.a(_snowman, _snowmanx);
            if (_snowmanxx != _snowmanxxx && !_snowman) {
               this.a(_snowman, _snowmanxx, _snowmanxxx, false);
            }

            this.a(_snowman, _snowmanx, _snowmanxxx);
         } else if (!_snowman) {
            this.a(_snowman, _snowmanxx, this.a, true);
         }
      }
   }

   protected final void b(long var1, long var3, int var5, boolean var6) {
      int _snowman = this.c.get(_snowman) & 255;
      int _snowmanx = afm.a(this.b(_snowman, _snowman, _snowman), 0, this.a - 1);
      if (_snowman) {
         this.a(_snowman, _snowman, _snowmanx, this.c(_snowman), _snowman, true);
      } else {
         int _snowmanxx;
         boolean _snowmanxxx;
         if (_snowman == 255) {
            _snowmanxxx = true;
            _snowmanxx = afm.a(this.c(_snowman), 0, this.a - 1);
         } else {
            _snowmanxx = _snowman;
            _snowmanxxx = false;
         }

         if (_snowmanx == _snowmanxx) {
            this.a(_snowman, _snowman, this.a - 1, _snowmanxxx ? _snowmanxx : this.c(_snowman), _snowman, false);
         }
      }
   }

   protected final boolean b() {
      return this.e;
   }

   protected final int b(int var1) {
      if (this.d >= this.a) {
         return _snowman;
      } else {
         while (this.d < this.a && _snowman > 0) {
            _snowman--;
            LongLinkedOpenHashSet _snowman = this.b[this.d];
            long _snowmanx = _snowman.removeFirstLong();
            int _snowmanxx = afm.a(this.c(_snowmanx), 0, this.a - 1);
            if (_snowman.isEmpty()) {
               this.a(this.a);
            }

            int _snowmanxxx = this.c.remove(_snowmanx) & 255;
            if (_snowmanxxx < _snowmanxx) {
               this.a(_snowmanx, _snowmanxxx);
               this.a(_snowmanx, _snowmanxxx, true);
            } else if (_snowmanxxx > _snowmanxx) {
               this.a(_snowmanx, _snowmanxxx, this.a(this.a - 1, _snowmanxxx));
               this.a(_snowmanx, this.a - 1);
               this.a(_snowmanx, _snowmanxx, false);
            }
         }

         this.e = this.d < this.a;
         return _snowman;
      }
   }

   public int c() {
      return this.c.size();
   }

   protected abstract boolean a(long var1);

   protected abstract int a(long var1, long var3, int var5);

   protected abstract void a(long var1, int var3, boolean var4);

   protected abstract int c(long var1);

   protected abstract void a(long var1, int var3);

   protected abstract int b(long var1, long var3, int var5);
}
