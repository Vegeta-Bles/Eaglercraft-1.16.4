import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;

public class bfy {
   public final Int2IntMap a = new Int2IntOpenHashMap();

   public bfy() {
   }

   public void a(bmb var1) {
      if (!_snowman.f() && !_snowman.x() && !_snowman.t()) {
         this.b(_snowman);
      }
   }

   public void b(bmb var1) {
      this.a(_snowman, 64);
   }

   public void a(bmb var1, int var2) {
      if (!_snowman.a()) {
         int _snowman = c(_snowman);
         int _snowmanx = Math.min(_snowman, _snowman.E());
         this.b(_snowman, _snowmanx);
      }
   }

   public static int c(bmb var0) {
      return gm.T.a(_snowman.b());
   }

   private boolean b(int var1) {
      return this.a.get(_snowman) > 0;
   }

   private int a(int var1, int var2) {
      int _snowman = this.a.get(_snowman);
      if (_snowman >= _snowman) {
         this.a.put(_snowman, _snowman - _snowman);
         return _snowman;
      } else {
         return 0;
      }
   }

   private void b(int var1, int var2) {
      this.a.put(_snowman, this.a.get(_snowman) + _snowman);
   }

   public boolean a(boq<?> var1, @Nullable IntList var2) {
      return this.a(_snowman, _snowman, 1);
   }

   public boolean a(boq<?> var1, @Nullable IntList var2, int var3) {
      return new bfy.a(_snowman).a(_snowman, _snowman);
   }

   public int b(boq<?> var1, @Nullable IntList var2) {
      return this.a(_snowman, Integer.MAX_VALUE, _snowman);
   }

   public int a(boq<?> var1, int var2, @Nullable IntList var3) {
      return new bfy.a(_snowman).b(_snowman, _snowman);
   }

   public static bmb a(int var0) {
      return _snowman == 0 ? bmb.b : new bmb(blx.b(_snowman));
   }

   public void a() {
      this.a.clear();
   }

   class a {
      private final boq<?> b;
      private final List<bon> c = Lists.newArrayList();
      private final int d;
      private final int[] e;
      private final int f;
      private final BitSet g;
      private final IntList h = new IntArrayList();

      public a(boq<?> var2) {
         this.b = _snowman;
         this.c.addAll(_snowman.a());
         this.c.removeIf(bon::d);
         this.d = this.c.size();
         this.e = this.a();
         this.f = this.e.length;
         this.g = new BitSet(this.d + this.f + this.d + this.d * this.f);

         for (int _snowman = 0; _snowman < this.c.size(); _snowman++) {
            IntList _snowmanx = this.c.get(_snowman).b();

            for (int _snowmanxx = 0; _snowmanxx < this.f; _snowmanxx++) {
               if (_snowmanx.contains(this.e[_snowmanxx])) {
                  this.g.set(this.d(true, _snowmanxx, _snowman));
               }
            }
         }
      }

      public boolean a(int var1, @Nullable IntList var2) {
         if (_snowman <= 0) {
            return true;
         } else {
            int _snowman;
            for (_snowman = 0; this.a(_snowman); _snowman++) {
               bfy.this.a(this.e[this.h.getInt(0)], _snowman);
               int _snowmanx = this.h.size() - 1;
               this.c(this.h.getInt(_snowmanx));

               for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
                  this.c((_snowmanxx & 1) == 0, this.h.get(_snowmanxx), this.h.get(_snowmanxx + 1));
               }

               this.h.clear();
               this.g.clear(0, this.d + this.f);
            }

            boolean _snowmanx = _snowman == this.d;
            boolean _snowmanxx = _snowmanx && _snowman != null;
            if (_snowmanxx) {
               _snowman.clear();
            }

            this.g.clear(0, this.d + this.f + this.d);
            int _snowmanxxx = 0;
            List<bon> _snowmanxxxx = this.b.a();

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx.size(); _snowmanxxxxx++) {
               if (_snowmanxx && _snowmanxxxx.get(_snowmanxxxxx).d()) {
                  _snowman.add(0);
               } else {
                  for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < this.f; _snowmanxxxxxx++) {
                     if (this.b(false, _snowmanxxx, _snowmanxxxxxx)) {
                        this.c(true, _snowmanxxxxxx, _snowmanxxx);
                        bfy.this.b(this.e[_snowmanxxxxxx], _snowman);
                        if (_snowmanxx) {
                           _snowman.add(this.e[_snowmanxxxxxx]);
                        }
                     }
                  }

                  _snowmanxxx++;
               }
            }

            return _snowmanx;
         }
      }

      private int[] a() {
         IntCollection _snowman = new IntAVLTreeSet();

         for (bon _snowmanx : this.c) {
            _snowman.addAll(_snowmanx.b());
         }

         IntIterator _snowmanx = _snowman.iterator();

         while (_snowmanx.hasNext()) {
            if (!bfy.this.b(_snowmanx.nextInt())) {
               _snowmanx.remove();
            }
         }

         return _snowman.toIntArray();
      }

      private boolean a(int var1) {
         int _snowman = this.f;

         for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
            if (bfy.this.a.get(this.e[_snowmanx]) >= _snowman) {
               this.a(false, _snowmanx);

               while (!this.h.isEmpty()) {
                  int _snowmanxx = this.h.size();
                  boolean _snowmanxxx = (_snowmanxx & 1) == 1;
                  int _snowmanxxxx = this.h.getInt(_snowmanxx - 1);
                  if (!_snowmanxxx && !this.b(_snowmanxxxx)) {
                     break;
                  }

                  int _snowmanxxxxx = _snowmanxxx ? this.d : _snowman;
                  int _snowmanxxxxxx = 0;

                  while (true) {
                     if (_snowmanxxxxxx < _snowmanxxxxx) {
                        if (this.b(_snowmanxxx, _snowmanxxxxxx) || !this.a(_snowmanxxx, _snowmanxxxx, _snowmanxxxxxx) || !this.b(_snowmanxxx, _snowmanxxxx, _snowmanxxxxxx)) {
                           _snowmanxxxxxx++;
                           continue;
                        }

                        this.a(_snowmanxxx, _snowmanxxxxxx);
                     }

                     _snowmanxxxxxx = this.h.size();
                     if (_snowmanxxxxxx == _snowmanxx) {
                        this.h.removeInt(_snowmanxxxxxx - 1);
                     }
                     break;
                  }
               }

               if (!this.h.isEmpty()) {
                  return true;
               }
            }
         }

         return false;
      }

      private boolean b(int var1) {
         return this.g.get(this.d(_snowman));
      }

      private void c(int var1) {
         this.g.set(this.d(_snowman));
      }

      private int d(int var1) {
         return this.d + this.f + _snowman;
      }

      private boolean a(boolean var1, int var2, int var3) {
         return this.g.get(this.d(_snowman, _snowman, _snowman));
      }

      private boolean b(boolean var1, int var2, int var3) {
         return _snowman != this.g.get(1 + this.d(_snowman, _snowman, _snowman));
      }

      private void c(boolean var1, int var2, int var3) {
         this.g.flip(1 + this.d(_snowman, _snowman, _snowman));
      }

      private int d(boolean var1, int var2, int var3) {
         int _snowman = _snowman ? _snowman * this.d + _snowman : _snowman * this.d + _snowman;
         return this.d + this.f + this.d + 2 * _snowman;
      }

      private void a(boolean var1, int var2) {
         this.g.set(this.c(_snowman, _snowman));
         this.h.add(_snowman);
      }

      private boolean b(boolean var1, int var2) {
         return this.g.get(this.c(_snowman, _snowman));
      }

      private int c(boolean var1, int var2) {
         return (_snowman ? 0 : this.d) + _snowman;
      }

      public int b(int var1, @Nullable IntList var2) {
         int _snowman = 0;
         int _snowmanx = Math.min(_snowman, this.b()) + 1;

         while (true) {
            int _snowmanxx = (_snowman + _snowmanx) / 2;
            if (this.a(_snowmanxx, null)) {
               if (_snowmanx - _snowman <= 1) {
                  if (_snowmanxx > 0) {
                     this.a(_snowmanxx, _snowman);
                  }

                  return _snowmanxx;
               }

               _snowman = _snowmanxx;
            } else {
               _snowmanx = _snowmanxx;
            }
         }
      }

      private int b() {
         int _snowman = Integer.MAX_VALUE;

         for (bon _snowmanx : this.c) {
            int _snowmanxx = 0;
            IntListIterator var5 = _snowmanx.b().iterator();

            while (var5.hasNext()) {
               int _snowmanxxx = (Integer)var5.next();
               _snowmanxx = Math.max(_snowmanxx, bfy.this.a.get(_snowmanxxx));
            }

            if (_snowman > 0) {
               _snowman = Math.min(_snowman, _snowmanxx);
            }
         }

         return _snowman;
      }
   }
}
