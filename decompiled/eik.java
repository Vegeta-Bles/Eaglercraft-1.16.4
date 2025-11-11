import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class eik<T extends aqm, M extends dum<T>, A extends dum<T>> extends eit<T, M> {
   private static final Map<String, vk> a = Maps.newHashMap();
   private final A b;
   private final A c;

   public eik(egk<T, M> var1, A var2, A var3) {
      super(_snowman);
      this.b = _snowman;
      this.c = _snowman;
   }

   public void a(dfm var1, eag var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      this.a(_snowman, _snowman, _snowman, aqf.e, _snowman, this.a(aqf.e));
      this.a(_snowman, _snowman, _snowman, aqf.d, _snowman, this.a(aqf.d));
      this.a(_snowman, _snowman, _snowman, aqf.c, _snowman, this.a(aqf.c));
      this.a(_snowman, _snowman, _snowman, aqf.f, _snowman, this.a(aqf.f));
   }

   private void a(dfm var1, eag var2, T var3, aqf var4, int var5, A var6) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman.b() instanceof bjy) {
         bjy _snowmanx = (bjy)_snowman.b();
         if (_snowmanx.b() == _snowman) {
            this.aC_().a(_snowman);
            this.a(_snowman, _snowman);
            boolean _snowmanxx = this.b(_snowman);
            boolean _snowmanxxx = _snowman.u();
            if (_snowmanx instanceof bkz) {
               int _snowmanxxxx = ((bkz)_snowmanx).b(_snowman);
               float _snowmanxxxxx = (float)(_snowmanxxxx >> 16 & 0xFF) / 255.0F;
               float _snowmanxxxxxx = (float)(_snowmanxxxx >> 8 & 0xFF) / 255.0F;
               float _snowmanxxxxxxx = (float)(_snowmanxxxx & 0xFF) / 255.0F;
               this.a(_snowman, _snowman, _snowman, _snowmanx, _snowmanxxx, _snowman, _snowmanxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, null);
               this.a(_snowman, _snowman, _snowman, _snowmanx, _snowmanxxx, _snowman, _snowmanxx, 1.0F, 1.0F, 1.0F, "overlay");
            } else {
               this.a(_snowman, _snowman, _snowman, _snowmanx, _snowmanxxx, _snowman, _snowmanxx, 1.0F, 1.0F, 1.0F, null);
            }
         }
      }
   }

   protected void a(A var1, aqf var2) {
      _snowman.d_(false);
      switch (_snowman) {
         case f:
            _snowman.f.h = true;
            _snowman.g.h = true;
            break;
         case e:
            _snowman.h.h = true;
            _snowman.i.h = true;
            _snowman.j.h = true;
            break;
         case d:
            _snowman.h.h = true;
            _snowman.k.h = true;
            _snowman.l.h = true;
            break;
         case c:
            _snowman.k.h = true;
            _snowman.l.h = true;
      }
   }

   private void a(dfm var1, eag var2, int var3, bjy var4, boolean var5, A var6, boolean var7, float var8, float var9, float var10, @Nullable String var11) {
      dfq _snowman = efo.a(_snowman, eao.a(this.a(_snowman, _snowman, _snowman)), false, _snowman);
      _snowman.a(_snowman, _snowman, _snowman, ejw.a, _snowman, _snowman, _snowman, 1.0F);
   }

   private A a(aqf var1) {
      return this.b(_snowman) ? this.b : this.c;
   }

   private boolean b(aqf var1) {
      return _snowman == aqf.d;
   }

   private vk a(bjy var1, boolean var2, @Nullable String var3) {
      String _snowman = "textures/models/armor/" + _snowman.ab_().d() + "_layer_" + (_snowman ? 2 : 1) + (_snowman == null ? "" : "_" + _snowman) + ".png";
      return a.computeIfAbsent(_snowman, vk::new);
   }
}
