public class cvt implements cwp {
   private static final int[] a = new int[]{2, 4, 3, 6, 1, 5};
   private static final int[] b = new int[]{2, 2, 2, 35, 35, 1};
   private static final int[] c = new int[]{4, 29, 3, 1, 27, 6};
   private static final int[] d = new int[]{4, 3, 5, 1};
   private static final int[] e = new int[]{12, 12, 12, 30};
   private int[] f = b;

   public cvt(boolean var1) {
      if (_snowman) {
         this.f = a;
      }
   }

   @Override
   public int a(cvk var1, int var2) {
      int _snowman = (_snowman & 3840) >> 8;
      _snowman &= -3841;
      if (!cvx.a(_snowman) && _snowman != 14) {
         switch (_snowman) {
            case 1:
               if (_snowman > 0) {
                  return _snowman.a(3) == 0 ? 39 : 38;
               }

               return this.f[_snowman.a(this.f.length)];
            case 2:
               if (_snowman > 0) {
                  return 21;
               }

               return c[_snowman.a(c.length)];
            case 3:
               if (_snowman > 0) {
                  return 32;
               }

               return d[_snowman.a(d.length)];
            case 4:
               return e[_snowman.a(e.length)];
            default:
               return 14;
         }
      } else {
         return _snowman;
      }
   }
}
