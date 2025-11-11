import javax.annotation.Nullable;

public class bny extends boa {
   private final fx b;
   protected boolean a = true;

   public bny(bfw var1, aot var2, bmb var3, dcj var4) {
      this(_snowman.l, _snowman, _snowman, _snowman, _snowman);
   }

   public bny(boa var1) {
      this(_snowman.p(), _snowman.n(), _snowman.o(), _snowman.m(), _snowman.i());
   }

   protected bny(brx var1, @Nullable bfw var2, aot var3, bmb var4, dcj var5) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman);
      this.b = _snowman.a().a(_snowman.b());
      this.a = _snowman.d_(_snowman.a()).a(this);
   }

   public static bny a(bny var0, fx var1, gc var2) {
      return new bny(
         _snowman.p(),
         _snowman.n(),
         _snowman.o(),
         _snowman.m(),
         new dcj(
            new dcn((double)_snowman.u() + 0.5 + (double)_snowman.i() * 0.5, (double)_snowman.v() + 0.5 + (double)_snowman.j() * 0.5, (double)_snowman.w() + 0.5 + (double)_snowman.k() * 0.5),
            _snowman,
            _snowman,
            false
         )
      );
   }

   @Override
   public fx a() {
      return this.a ? super.a() : this.b;
   }

   public boolean b() {
      return this.a || this.p().d_(this.a()).a(this);
   }

   public boolean c() {
      return this.a;
   }

   public gc d() {
      return gc.a(this.n())[0];
   }

   public gc[] e() {
      gc[] _snowman = gc.a(this.n());
      if (this.a) {
         return _snowman;
      } else {
         gc _snowmanx = this.j();
         int _snowmanxx = 0;

         while (_snowmanxx < _snowman.length && _snowman[_snowmanxx] != _snowmanx.f()) {
            _snowmanxx++;
         }

         if (_snowmanxx > 0) {
            System.arraycopy(_snowman, 0, _snowman, 1, _snowmanxx);
            _snowman[0] = _snowmanx.f();
         }

         return _snowman;
      }
   }
}
