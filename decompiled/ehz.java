import com.mojang.authlib.GameProfile;
import org.apache.commons.lang3.StringUtils;

public class ehz<T extends aqm, M extends duc<T> & dui> extends eit<T, M> {
   private final float a;
   private final float b;
   private final float c;

   public ehz(egk<T, M> var1) {
      this(_snowman, 1.0F, 1.0F, 1.0F);
   }

   public ehz(egk<T, M> var1, float var2, float var3, float var4) {
      super(_snowman);
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public void a(dfm var1, eag var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      bmb _snowman = _snowman.b(aqf.f);
      if (!_snowman.a()) {
         blx _snowmanx = _snowman.b();
         _snowman.a();
         _snowman.a(this.a, this.b, this.c);
         boolean _snowmanxx = _snowman instanceof bfj || _snowman instanceof bek;
         if (_snowman.w_() && !(_snowman instanceof bfj)) {
            float _snowmanxxx = 2.0F;
            float _snowmanxxxx = 1.4F;
            _snowman.a(0.0, 0.03125, 0.0);
            _snowman.a(0.7F, 0.7F, 0.7F);
            _snowman.a(0.0, 1.0, 0.0);
         }

         this.aC_().c().a(_snowman);
         if (_snowmanx instanceof bkh && ((bkh)_snowmanx).e() instanceof btq) {
            float _snowmanxxx = 1.1875F;
            _snowman.a(1.1875F, -1.1875F, -1.1875F);
            if (_snowmanxx) {
               _snowman.a(0.0, 0.0625, 0.0);
            }

            GameProfile _snowmanxxxx = null;
            if (_snowman.n()) {
               md _snowmanxxxxx = _snowman.o();
               if (_snowmanxxxxx.c("SkullOwner", 10)) {
                  _snowmanxxxx = mp.a(_snowmanxxxxx.p("SkullOwner"));
               } else if (_snowmanxxxxx.c("SkullOwner", 8)) {
                  String _snowmanxxxxxx = _snowmanxxxxx.l("SkullOwner");
                  if (!StringUtils.isBlank(_snowmanxxxxxx)) {
                     _snowmanxxxx = cdg.b(new GameProfile(null, _snowmanxxxxxx));
                     _snowmanxxxxx.a("SkullOwner", mp.a(new md(), _snowmanxxxx));
                  }
               }
            }

            _snowman.a(-0.5, 0.0, -0.5);
            eco.a(null, 180.0F, ((btq)((bkh)_snowmanx).e()).b(), _snowmanxxxx, _snowman, _snowman, _snowman, _snowman);
         } else if (!(_snowmanx instanceof bjy) || ((bjy)_snowmanx).b() != aqf.f) {
            float _snowmanxxxx = 0.625F;
            _snowman.a(0.0, -0.25, 0.0);
            _snowman.a(g.d.a(180.0F));
            _snowman.a(0.625F, -0.625F, -0.625F);
            if (_snowmanxx) {
               _snowman.a(0.0, 0.1875, 0.0);
            }

            djz.C().ae().a(_snowman, _snowman, ebm.b.f, false, _snowman, _snowman, _snowman);
         }

         _snowman.b();
      }
   }
}
