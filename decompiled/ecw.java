import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Set;

public class ecw {
   private static final int a = (int)Math.pow(16.0, 0.0);
   private static final int b = (int)Math.pow(16.0, 1.0);
   private static final int c = (int)Math.pow(16.0, 2.0);
   private static final gc[] d = gc.values();
   private final BitSet e = new BitSet(4096);
   private static final int[] f = x.a(new int[1352], var0 -> {
      int _snowman = 0;
      int _snowmanx = 15;
      int _snowmanxx = 0;

      for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
         for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
            for (int _snowmanxxxxx = 0; _snowmanxxxxx < 16; _snowmanxxxxx++) {
               if (_snowmanxxx == 0 || _snowmanxxx == 15 || _snowmanxxxx == 0 || _snowmanxxxx == 15 || _snowmanxxxxx == 0 || _snowmanxxxxx == 15) {
                  var0[_snowmanxx++] = a(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
               }
            }
         }
      }
   });
   private int g = 4096;

   public ecw() {
   }

   public void a(fx var1) {
      this.e.set(c(_snowman), true);
      this.g--;
   }

   private static int c(fx var0) {
      return a(_snowman.u() & 15, _snowman.v() & 15, _snowman.w() & 15);
   }

   private static int a(int var0, int var1, int var2) {
      return _snowman << 0 | _snowman << 8 | _snowman << 4;
   }

   public ecx a() {
      ecx _snowman = new ecx();
      if (4096 - this.g < 256) {
         _snowman.a(true);
      } else if (this.g == 0) {
         _snowman.a(false);
      } else {
         for (int _snowmanx : f) {
            if (!this.e.get(_snowmanx)) {
               _snowman.a(this.a(_snowmanx));
            }
         }
      }

      return _snowman;
   }

   private Set<gc> a(int var1) {
      Set<gc> _snowman = EnumSet.noneOf(gc.class);
      IntPriorityQueue _snowmanx = new IntArrayFIFOQueue();
      _snowmanx.enqueue(_snowman);
      this.e.set(_snowman, true);

      while (!_snowmanx.isEmpty()) {
         int _snowmanxx = _snowmanx.dequeueInt();
         this.a(_snowmanxx, _snowman);

         for (gc _snowmanxxx : d) {
            int _snowmanxxxx = this.a(_snowmanxx, _snowmanxxx);
            if (_snowmanxxxx >= 0 && !this.e.get(_snowmanxxxx)) {
               this.e.set(_snowmanxxxx, true);
               _snowmanx.enqueue(_snowmanxxxx);
            }
         }
      }

      return _snowman;
   }

   private void a(int var1, Set<gc> var2) {
      int _snowman = _snowman >> 0 & 15;
      if (_snowman == 0) {
         _snowman.add(gc.e);
      } else if (_snowman == 15) {
         _snowman.add(gc.f);
      }

      int _snowmanx = _snowman >> 8 & 15;
      if (_snowmanx == 0) {
         _snowman.add(gc.a);
      } else if (_snowmanx == 15) {
         _snowman.add(gc.b);
      }

      int _snowmanxx = _snowman >> 4 & 15;
      if (_snowmanxx == 0) {
         _snowman.add(gc.c);
      } else if (_snowmanxx == 15) {
         _snowman.add(gc.d);
      }
   }

   private int a(int var1, gc var2) {
      switch (_snowman) {
         case a:
            if ((_snowman >> 8 & 15) == 0) {
               return -1;
            }

            return _snowman - c;
         case b:
            if ((_snowman >> 8 & 15) == 15) {
               return -1;
            }

            return _snowman + c;
         case c:
            if ((_snowman >> 4 & 15) == 0) {
               return -1;
            }

            return _snowman - b;
         case d:
            if ((_snowman >> 4 & 15) == 15) {
               return -1;
            }

            return _snowman + b;
         case e:
            if ((_snowman >> 0 & 15) == 0) {
               return -1;
            }

            return _snowman - a;
         case f:
            if ((_snowman >> 0 & 15) == 15) {
               return -1;
            }

            return _snowman + a;
         default:
            return -1;
      }
   }
}
