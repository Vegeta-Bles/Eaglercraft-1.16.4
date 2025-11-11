import java.util.Random;

public class crc extends crq {
   private final boolean[] e = new boolean[4];

   public crc(Random var1, int var2, int var3) {
      super(clb.L, _snowman, _snowman, 64, _snowman, 21, 15, 21);
   }

   public crc(csw var1, md var2) {
      super(clb.L, _snowman);
      this.e[0] = _snowman.q("hasPlacedChest0");
      this.e[1] = _snowman.q("hasPlacedChest1");
      this.e[2] = _snowman.q("hasPlacedChest2");
      this.e[3] = _snowman.q("hasPlacedChest3");
   }

   @Override
   protected void a(md var1) {
      super.a(_snowman);
      _snowman.a("hasPlacedChest0", this.e[0]);
      _snowman.a("hasPlacedChest1", this.e[1]);
      _snowman.a("hasPlacedChest2", this.e[2]);
      _snowman.a("hasPlacedChest3", this.e[3]);
   }

   @Override
   public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
      this.a(_snowman, _snowman, 0, -4, 0, this.a - 1, 0, this.c - 1, bup.at.n(), bup.at.n(), false);

      for (int _snowman = 1; _snowman <= 9; _snowman++) {
         this.a(_snowman, _snowman, _snowman, _snowman, _snowman, this.a - 1 - _snowman, _snowman, this.c - 1 - _snowman, bup.at.n(), bup.at.n(), false);
         this.a(_snowman, _snowman, _snowman + 1, _snowman, _snowman + 1, this.a - 2 - _snowman, _snowman, this.c - 2 - _snowman, bup.a.n(), bup.a.n(), false);
      }

      for (int _snowman = 0; _snowman < this.a; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < this.c; _snowmanx++) {
            int _snowmanxx = -5;
            this.b(_snowman, bup.at.n(), _snowman, -5, _snowmanx, _snowman);
         }
      }

      ceh _snowman = bup.ei.n().a(cak.a, gc.c);
      ceh _snowmanx = bup.ei.n().a(cak.a, gc.d);
      ceh _snowmanxx = bup.ei.n().a(cak.a, gc.f);
      ceh _snowmanxxx = bup.ei.n().a(cak.a, gc.e);
      this.a(_snowman, _snowman, 0, 0, 0, 4, 9, 4, bup.at.n(), bup.a.n(), false);
      this.a(_snowman, _snowman, 1, 10, 1, 3, 10, 3, bup.at.n(), bup.at.n(), false);
      this.a(_snowman, _snowman, 2, 10, 0, _snowman);
      this.a(_snowman, _snowmanx, 2, 10, 4, _snowman);
      this.a(_snowman, _snowmanxx, 0, 10, 2, _snowman);
      this.a(_snowman, _snowmanxxx, 4, 10, 2, _snowman);
      this.a(_snowman, _snowman, this.a - 5, 0, 0, this.a - 1, 9, 4, bup.at.n(), bup.a.n(), false);
      this.a(_snowman, _snowman, this.a - 4, 10, 1, this.a - 2, 10, 3, bup.at.n(), bup.at.n(), false);
      this.a(_snowman, _snowman, this.a - 3, 10, 0, _snowman);
      this.a(_snowman, _snowmanx, this.a - 3, 10, 4, _snowman);
      this.a(_snowman, _snowmanxx, this.a - 5, 10, 2, _snowman);
      this.a(_snowman, _snowmanxxx, this.a - 1, 10, 2, _snowman);
      this.a(_snowman, _snowman, 8, 0, 0, 12, 4, 4, bup.at.n(), bup.a.n(), false);
      this.a(_snowman, _snowman, 9, 1, 0, 11, 3, 4, bup.a.n(), bup.a.n(), false);
      this.a(_snowman, bup.av.n(), 9, 1, 1, _snowman);
      this.a(_snowman, bup.av.n(), 9, 2, 1, _snowman);
      this.a(_snowman, bup.av.n(), 9, 3, 1, _snowman);
      this.a(_snowman, bup.av.n(), 10, 3, 1, _snowman);
      this.a(_snowman, bup.av.n(), 11, 3, 1, _snowman);
      this.a(_snowman, bup.av.n(), 11, 2, 1, _snowman);
      this.a(_snowman, bup.av.n(), 11, 1, 1, _snowman);
      this.a(_snowman, _snowman, 4, 1, 1, 8, 3, 3, bup.at.n(), bup.a.n(), false);
      this.a(_snowman, _snowman, 4, 1, 2, 8, 2, 2, bup.a.n(), bup.a.n(), false);
      this.a(_snowman, _snowman, 12, 1, 1, 16, 3, 3, bup.at.n(), bup.a.n(), false);
      this.a(_snowman, _snowman, 12, 1, 2, 16, 2, 2, bup.a.n(), bup.a.n(), false);
      this.a(_snowman, _snowman, 5, 4, 5, this.a - 6, 4, this.c - 6, bup.at.n(), bup.at.n(), false);
      this.a(_snowman, _snowman, 9, 4, 9, 11, 4, 11, bup.a.n(), bup.a.n(), false);
      this.a(_snowman, _snowman, 8, 1, 8, 8, 3, 8, bup.av.n(), bup.av.n(), false);
      this.a(_snowman, _snowman, 12, 1, 8, 12, 3, 8, bup.av.n(), bup.av.n(), false);
      this.a(_snowman, _snowman, 8, 1, 12, 8, 3, 12, bup.av.n(), bup.av.n(), false);
      this.a(_snowman, _snowman, 12, 1, 12, 12, 3, 12, bup.av.n(), bup.av.n(), false);
      this.a(_snowman, _snowman, 1, 1, 5, 4, 4, 11, bup.at.n(), bup.at.n(), false);
      this.a(_snowman, _snowman, this.a - 5, 1, 5, this.a - 2, 4, 11, bup.at.n(), bup.at.n(), false);
      this.a(_snowman, _snowman, 6, 7, 9, 6, 7, 11, bup.at.n(), bup.at.n(), false);
      this.a(_snowman, _snowman, this.a - 7, 7, 9, this.a - 7, 7, 11, bup.at.n(), bup.at.n(), false);
      this.a(_snowman, _snowman, 5, 5, 9, 5, 7, 11, bup.av.n(), bup.av.n(), false);
      this.a(_snowman, _snowman, this.a - 6, 5, 9, this.a - 6, 7, 11, bup.av.n(), bup.av.n(), false);
      this.a(_snowman, bup.a.n(), 5, 5, 10, _snowman);
      this.a(_snowman, bup.a.n(), 5, 6, 10, _snowman);
      this.a(_snowman, bup.a.n(), 6, 6, 10, _snowman);
      this.a(_snowman, bup.a.n(), this.a - 6, 5, 10, _snowman);
      this.a(_snowman, bup.a.n(), this.a - 6, 6, 10, _snowman);
      this.a(_snowman, bup.a.n(), this.a - 7, 6, 10, _snowman);
      this.a(_snowman, _snowman, 2, 4, 4, 2, 6, 4, bup.a.n(), bup.a.n(), false);
      this.a(_snowman, _snowman, this.a - 3, 4, 4, this.a - 3, 6, 4, bup.a.n(), bup.a.n(), false);
      this.a(_snowman, _snowman, 2, 4, 5, _snowman);
      this.a(_snowman, _snowman, 2, 3, 4, _snowman);
      this.a(_snowman, _snowman, this.a - 3, 4, 5, _snowman);
      this.a(_snowman, _snowman, this.a - 3, 3, 4, _snowman);
      this.a(_snowman, _snowman, 1, 1, 3, 2, 2, 3, bup.at.n(), bup.at.n(), false);
      this.a(_snowman, _snowman, this.a - 3, 1, 3, this.a - 2, 2, 3, bup.at.n(), bup.at.n(), false);
      this.a(_snowman, bup.at.n(), 1, 1, 2, _snowman);
      this.a(_snowman, bup.at.n(), this.a - 2, 1, 2, _snowman);
      this.a(_snowman, bup.hS.n(), 1, 2, 2, _snowman);
      this.a(_snowman, bup.hS.n(), this.a - 2, 2, 2, _snowman);
      this.a(_snowman, _snowmanxxx, 2, 1, 2, _snowman);
      this.a(_snowman, _snowmanxx, this.a - 3, 1, 2, _snowman);
      this.a(_snowman, _snowman, 4, 3, 5, 4, 3, 17, bup.at.n(), bup.at.n(), false);
      this.a(_snowman, _snowman, this.a - 5, 3, 5, this.a - 5, 3, 17, bup.at.n(), bup.at.n(), false);
      this.a(_snowman, _snowman, 3, 1, 5, 4, 2, 16, bup.a.n(), bup.a.n(), false);
      this.a(_snowman, _snowman, this.a - 6, 1, 5, this.a - 5, 2, 16, bup.a.n(), bup.a.n(), false);

      for (int _snowmanxxxx = 5; _snowmanxxxx <= 17; _snowmanxxxx += 2) {
         this.a(_snowman, bup.av.n(), 4, 1, _snowmanxxxx, _snowman);
         this.a(_snowman, bup.au.n(), 4, 2, _snowmanxxxx, _snowman);
         this.a(_snowman, bup.av.n(), this.a - 5, 1, _snowmanxxxx, _snowman);
         this.a(_snowman, bup.au.n(), this.a - 5, 2, _snowmanxxxx, _snowman);
      }

      this.a(_snowman, bup.fG.n(), 10, 0, 7, _snowman);
      this.a(_snowman, bup.fG.n(), 10, 0, 8, _snowman);
      this.a(_snowman, bup.fG.n(), 9, 0, 9, _snowman);
      this.a(_snowman, bup.fG.n(), 11, 0, 9, _snowman);
      this.a(_snowman, bup.fG.n(), 8, 0, 10, _snowman);
      this.a(_snowman, bup.fG.n(), 12, 0, 10, _snowman);
      this.a(_snowman, bup.fG.n(), 7, 0, 10, _snowman);
      this.a(_snowman, bup.fG.n(), 13, 0, 10, _snowman);
      this.a(_snowman, bup.fG.n(), 9, 0, 11, _snowman);
      this.a(_snowman, bup.fG.n(), 11, 0, 11, _snowman);
      this.a(_snowman, bup.fG.n(), 10, 0, 12, _snowman);
      this.a(_snowman, bup.fG.n(), 10, 0, 13, _snowman);
      this.a(_snowman, bup.fQ.n(), 10, 0, 10, _snowman);

      for (int _snowmanxxxx = 0; _snowmanxxxx <= this.a - 1; _snowmanxxxx += this.a - 1) {
         this.a(_snowman, bup.av.n(), _snowmanxxxx, 2, 1, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx, 2, 2, _snowman);
         this.a(_snowman, bup.av.n(), _snowmanxxxx, 2, 3, _snowman);
         this.a(_snowman, bup.av.n(), _snowmanxxxx, 3, 1, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx, 3, 2, _snowman);
         this.a(_snowman, bup.av.n(), _snowmanxxxx, 3, 3, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx, 4, 1, _snowman);
         this.a(_snowman, bup.au.n(), _snowmanxxxx, 4, 2, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx, 4, 3, _snowman);
         this.a(_snowman, bup.av.n(), _snowmanxxxx, 5, 1, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx, 5, 2, _snowman);
         this.a(_snowman, bup.av.n(), _snowmanxxxx, 5, 3, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx, 6, 1, _snowman);
         this.a(_snowman, bup.au.n(), _snowmanxxxx, 6, 2, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx, 6, 3, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx, 7, 1, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx, 7, 2, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx, 7, 3, _snowman);
         this.a(_snowman, bup.av.n(), _snowmanxxxx, 8, 1, _snowman);
         this.a(_snowman, bup.av.n(), _snowmanxxxx, 8, 2, _snowman);
         this.a(_snowman, bup.av.n(), _snowmanxxxx, 8, 3, _snowman);
      }

      for (int _snowmanxxxx = 2; _snowmanxxxx <= this.a - 3; _snowmanxxxx += this.a - 3 - 2) {
         this.a(_snowman, bup.av.n(), _snowmanxxxx - 1, 2, 0, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx, 2, 0, _snowman);
         this.a(_snowman, bup.av.n(), _snowmanxxxx + 1, 2, 0, _snowman);
         this.a(_snowman, bup.av.n(), _snowmanxxxx - 1, 3, 0, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx, 3, 0, _snowman);
         this.a(_snowman, bup.av.n(), _snowmanxxxx + 1, 3, 0, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx - 1, 4, 0, _snowman);
         this.a(_snowman, bup.au.n(), _snowmanxxxx, 4, 0, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx + 1, 4, 0, _snowman);
         this.a(_snowman, bup.av.n(), _snowmanxxxx - 1, 5, 0, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx, 5, 0, _snowman);
         this.a(_snowman, bup.av.n(), _snowmanxxxx + 1, 5, 0, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx - 1, 6, 0, _snowman);
         this.a(_snowman, bup.au.n(), _snowmanxxxx, 6, 0, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx + 1, 6, 0, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx - 1, 7, 0, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx, 7, 0, _snowman);
         this.a(_snowman, bup.fG.n(), _snowmanxxxx + 1, 7, 0, _snowman);
         this.a(_snowman, bup.av.n(), _snowmanxxxx - 1, 8, 0, _snowman);
         this.a(_snowman, bup.av.n(), _snowmanxxxx, 8, 0, _snowman);
         this.a(_snowman, bup.av.n(), _snowmanxxxx + 1, 8, 0, _snowman);
      }

      this.a(_snowman, _snowman, 8, 4, 0, 12, 6, 0, bup.av.n(), bup.av.n(), false);
      this.a(_snowman, bup.a.n(), 8, 6, 0, _snowman);
      this.a(_snowman, bup.a.n(), 12, 6, 0, _snowman);
      this.a(_snowman, bup.fG.n(), 9, 5, 0, _snowman);
      this.a(_snowman, bup.au.n(), 10, 5, 0, _snowman);
      this.a(_snowman, bup.fG.n(), 11, 5, 0, _snowman);
      this.a(_snowman, _snowman, 8, -14, 8, 12, -11, 12, bup.av.n(), bup.av.n(), false);
      this.a(_snowman, _snowman, 8, -10, 8, 12, -10, 12, bup.au.n(), bup.au.n(), false);
      this.a(_snowman, _snowman, 8, -9, 8, 12, -9, 12, bup.av.n(), bup.av.n(), false);
      this.a(_snowman, _snowman, 8, -8, 8, 12, -1, 12, bup.at.n(), bup.at.n(), false);
      this.a(_snowman, _snowman, 9, -11, 9, 11, -1, 11, bup.a.n(), bup.a.n(), false);
      this.a(_snowman, bup.cq.n(), 10, -11, 10, _snowman);
      this.a(_snowman, _snowman, 9, -13, 9, 11, -13, 11, bup.bH.n(), bup.a.n(), false);
      this.a(_snowman, bup.a.n(), 8, -11, 10, _snowman);
      this.a(_snowman, bup.a.n(), 8, -10, 10, _snowman);
      this.a(_snowman, bup.au.n(), 7, -10, 10, _snowman);
      this.a(_snowman, bup.av.n(), 7, -11, 10, _snowman);
      this.a(_snowman, bup.a.n(), 12, -11, 10, _snowman);
      this.a(_snowman, bup.a.n(), 12, -10, 10, _snowman);
      this.a(_snowman, bup.au.n(), 13, -10, 10, _snowman);
      this.a(_snowman, bup.av.n(), 13, -11, 10, _snowman);
      this.a(_snowman, bup.a.n(), 10, -11, 8, _snowman);
      this.a(_snowman, bup.a.n(), 10, -10, 8, _snowman);
      this.a(_snowman, bup.au.n(), 10, -10, 7, _snowman);
      this.a(_snowman, bup.av.n(), 10, -11, 7, _snowman);
      this.a(_snowman, bup.a.n(), 10, -11, 12, _snowman);
      this.a(_snowman, bup.a.n(), 10, -10, 12, _snowman);
      this.a(_snowman, bup.au.n(), 10, -10, 13, _snowman);
      this.a(_snowman, bup.av.n(), 10, -11, 13, _snowman);

      for (gc _snowmanxxxx : gc.c.a) {
         if (!this.e[_snowmanxxxx.d()]) {
            int _snowmanxxxxx = _snowmanxxxx.i() * 2;
            int _snowmanxxxxxx = _snowmanxxxx.k() * 2;
            this.e[_snowmanxxxx.d()] = this.a(_snowman, _snowman, _snowman, 10 + _snowmanxxxxx, -11, 10 + _snowmanxxxxxx, cyq.z);
         }
      }

      return true;
   }
}
