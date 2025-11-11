import java.util.Random;

public class eax implements aci {
   private final eaw a;
   private final eaz b;
   private final eay c;
   private final Random d = new Random();
   private final dko e;

   public eax(eaw var1, dko var2) {
      this.a = _snowman;
      this.e = _snowman;
      this.b = new eaz(this.e);
      this.c = new eay();
   }

   public eaw a() {
      return this.a;
   }

   public void a(ceh var1, fx var2, bra var3, dfm var4, dfq var5) {
      if (_snowman.h() == bzh.c) {
         elo _snowman = this.a.b(_snowman);
         long _snowmanx = _snowman.a(_snowman);
         this.b.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, true, this.d, _snowmanx, ejw.a);
      }
   }

   public boolean a(ceh var1, fx var2, bra var3, dfm var4, dfq var5, boolean var6, Random var7) {
      try {
         bzh _snowman = _snowman.h();
         return _snowman != bzh.c ? false : this.b.a(_snowman, this.a(_snowman), _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman.a(_snowman), ejw.a);
      } catch (Throwable var11) {
         l _snowmanx = l.a(var11, "Tesselating block in world");
         m _snowmanxx = _snowmanx.a("Block being tesselated");
         m.a(_snowmanxx, _snowman, _snowman);
         throw new u(_snowmanx);
      }
   }

   public boolean a(fx var1, bra var2, dfq var3, cux var4) {
      try {
         return this.c.a(_snowman, _snowman, _snowman, _snowman);
      } catch (Throwable var8) {
         l _snowman = l.a(var8, "Tesselating liquid in world");
         m _snowmanx = _snowman.a("Block being tesselated");
         m.a(_snowmanx, _snowman, null);
         throw new u(_snowman);
      }
   }

   public eaz b() {
      return this.b;
   }

   public elo a(ceh var1) {
      return this.a.b(_snowman);
   }

   public void a(ceh var1, dfm var2, eag var3, int var4, int var5) {
      bzh _snowman = _snowman.h();
      if (_snowman != bzh.a) {
         switch (_snowman) {
            case c:
               elo _snowmanx = this.a(_snowman);
               int _snowmanxx = this.e.a(_snowman, null, null, 0);
               float _snowmanxxx = (float)(_snowmanxx >> 16 & 0xFF) / 255.0F;
               float _snowmanxxxx = (float)(_snowmanxx >> 8 & 0xFF) / 255.0F;
               float _snowmanxxxxx = (float)(_snowmanxx & 0xFF) / 255.0F;
               this.b.a(_snowman.c(), _snowman.getBuffer(eab.a(_snowman, false)), _snowman, _snowmanx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowman, _snowman);
               break;
            case b:
               dzs.a.a(new bmb(_snowman.b()), ebm.b.a, _snowman, _snowman, _snowman, _snowman);
         }
      }
   }

   @Override
   public void a(ach var1) {
      this.c.a();
   }
}
