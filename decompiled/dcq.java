import java.util.BitSet;

public final class dcq extends dcw {
   private final BitSet d;
   private int e;
   private int f;
   private int g;
   private int h;
   private int i;
   private int j;

   public dcq(int var1, int var2, int var3) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, 0, 0, 0);
   }

   public dcq(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      super(_snowman, _snowman, _snowman);
      this.d = new BitSet(_snowman * _snowman * _snowman);
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
   }

   public dcq(dcw var1) {
      super(_snowman.a, _snowman.b, _snowman.c);
      if (_snowman instanceof dcq) {
         this.d = (BitSet)((dcq)_snowman).d.clone();
      } else {
         this.d = new BitSet(this.a * this.b * this.c);

         for (int _snowman = 0; _snowman < this.a; _snowman++) {
            for (int _snowmanx = 0; _snowmanx < this.b; _snowmanx++) {
               for (int _snowmanxx = 0; _snowmanxx < this.c; _snowmanxx++) {
                  if (_snowman.b(_snowman, _snowmanx, _snowmanxx)) {
                     this.d.set(this.a(_snowman, _snowmanx, _snowmanxx));
                  }
               }
            }
         }
      }

      this.e = _snowman.a(gc.a.a);
      this.f = _snowman.a(gc.a.b);
      this.g = _snowman.a(gc.a.c);
      this.h = _snowman.b(gc.a.a);
      this.i = _snowman.b(gc.a.b);
      this.j = _snowman.b(gc.a.c);
   }

   protected int a(int var1, int var2, int var3) {
      return (_snowman * this.b + _snowman) * this.c + _snowman;
   }

   @Override
   public boolean b(int var1, int var2, int var3) {
      return this.d.get(this.a(_snowman, _snowman, _snowman));
   }

   @Override
   public void a(int var1, int var2, int var3, boolean var4, boolean var5) {
      this.d.set(this.a(_snowman, _snowman, _snowman), _snowman);
      if (_snowman && _snowman) {
         this.e = Math.min(this.e, _snowman);
         this.f = Math.min(this.f, _snowman);
         this.g = Math.min(this.g, _snowman);
         this.h = Math.max(this.h, _snowman + 1);
         this.i = Math.max(this.i, _snowman + 1);
         this.j = Math.max(this.j, _snowman + 1);
      }
   }

   @Override
   public boolean a() {
      return this.d.isEmpty();
   }

   @Override
   public int a(gc.a var1) {
      return _snowman.a(this.e, this.f, this.g);
   }

   @Override
   public int b(gc.a var1) {
      return _snowman.a(this.h, this.i, this.j);
   }

   @Override
   protected boolean a(int var1, int var2, int var3, int var4) {
      if (_snowman < 0 || _snowman < 0 || _snowman < 0) {
         return false;
      } else {
         return _snowman < this.a && _snowman < this.b && _snowman <= this.c ? this.d.nextClearBit(this.a(_snowman, _snowman, _snowman)) >= this.a(_snowman, _snowman, _snowman) : false;
      }
   }

   @Override
   protected void a(int var1, int var2, int var3, int var4, boolean var5) {
      this.d.set(this.a(_snowman, _snowman, _snowman), this.a(_snowman, _snowman, _snowman), _snowman);
   }

   static dcq a(dcw var0, dcw var1, dcz var2, dcz var3, dcz var4, dcr var5) {
      dcq _snowman = new dcq(_snowman.a().size() - 1, _snowman.a().size() - 1, _snowman.a().size() - 1);
      int[] _snowmanx = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
      _snowman.a((var7x, var8, var9) -> {
         boolean[] _snowmanxx = new boolean[]{false};
         boolean _snowmanx = _snowman.a((var10x, var11x, var12) -> {
            boolean[] _snowmanxx = new boolean[]{false};
            boolean _snowmanx = _snowman.a((var12x, var13x, var14x) -> {
               boolean _snowmanxx = _snowman.apply(_snowman.c(var7x, var10x, var12x), _snowman.c(var8, var11x, var13x));
               if (_snowmanxx) {
                  _snowman.d.set(_snowman.a(var9, var12, var14x));
                  _snowman[2] = Math.min(_snowman[2], var14x);
                  _snowman[5] = Math.max(_snowman[5], var14x);
                  _snowman[0] = true;
               }

               return true;
            });
            if (_snowmanxx[0]) {
               _snowman[1] = Math.min(_snowman[1], var12);
               _snowman[4] = Math.max(_snowman[4], var12);
               _snowman[0] = true;
            }

            return _snowmanx;
         });
         if (_snowmanxx[0]) {
            _snowman[0] = Math.min(_snowman[0], var9);
            _snowman[3] = Math.max(_snowman[3], var9);
         }

         return _snowmanx;
      });
      _snowman.e = _snowmanx[0];
      _snowman.f = _snowmanx[1];
      _snowman.g = _snowmanx[2];
      _snowman.h = _snowmanx[3] + 1;
      _snowman.i = _snowmanx[4] + 1;
      _snowman.j = _snowmanx[5] + 1;
      return _snowman;
   }
}
