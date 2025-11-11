import com.mojang.serialization.Codec;
import java.util.Arrays;

public class chl extends cfy {
   public static final Codec<chl> d = cpf.a.fieldOf("settings").xmap(chl::new, chl::g).codec();
   private final cpf e;

   public chl(cpf var1) {
      super(new btd(_snowman.c()), new btd(_snowman.e()), _snowman.d(), 0L);
      this.e = _snowman;
   }

   @Override
   protected Codec<? extends cfy> a() {
      return d;
   }

   @Override
   public cfy a(long var1) {
      return this;
   }

   public cpf g() {
      return this.e;
   }

   @Override
   public void a(aam var1, cfw var2) {
   }

   @Override
   public int c() {
      ceh[] _snowman = this.e.g();

      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         ceh _snowmanxx = _snowman[_snowmanx] == null ? bup.a.n() : _snowman[_snowmanx];
         if (!chn.a.e.e().test(_snowmanxx)) {
            return _snowmanx - 1;
         }
      }

      return _snowman.length;
   }

   @Override
   public void a(bry var1, bsn var2, cfw var3) {
      ceh[] _snowman = this.e.g();
      fx.a _snowmanx = new fx.a();
      chn _snowmanxx = _snowman.a(chn.a.c);
      chn _snowmanxxx = _snowman.a(chn.a.a);

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.length; _snowmanxxxx++) {
         ceh _snowmanxxxxx = _snowman[_snowmanxxxx];
         if (_snowmanxxxxx != null) {
            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 16; _snowmanxxxxxx++) {
               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 16; _snowmanxxxxxxx++) {
                  _snowman.a(_snowmanx.d(_snowmanxxxxxx, _snowmanxxxx, _snowmanxxxxxxx), _snowmanxxxxx, false);
                  _snowmanxx.a(_snowmanxxxxxx, _snowmanxxxx, _snowmanxxxxxxx, _snowmanxxxxx);
                  _snowmanxxx.a(_snowmanxxxxxx, _snowmanxxxx, _snowmanxxxxxxx, _snowmanxxxxx);
               }
            }
         }
      }
   }

   @Override
   public int a(int var1, int var2, chn.a var3) {
      ceh[] _snowman = this.e.g();

      for (int _snowmanx = _snowman.length - 1; _snowmanx >= 0; _snowmanx--) {
         ceh _snowmanxx = _snowman[_snowmanx];
         if (_snowmanxx != null && _snowman.e().test(_snowmanxx)) {
            return _snowmanx + 1;
         }
      }

      return 0;
   }

   @Override
   public brc a(int var1, int var2) {
      return new bsh(Arrays.stream(this.e.g()).map(var0 -> var0 == null ? bup.a.n() : var0).toArray(ceh[]::new));
   }
}
