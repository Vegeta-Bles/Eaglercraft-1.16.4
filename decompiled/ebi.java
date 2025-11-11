import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ebi {
   public static final List<String> a = Lists.newArrayList(new String[]{"layer0", "layer1", "layer2", "layer3", "layer4"});

   public ebi() {
   }

   public ebf a(Function<elr, ekc> var1, ebf var2) {
      Map<String, Either<elr, String>> _snowman = Maps.newHashMap();
      List<ebb> _snowmanx = Lists.newArrayList();

      for (int _snowmanxx = 0; _snowmanxx < a.size(); _snowmanxx++) {
         String _snowmanxxx = a.get(_snowmanxx);
         if (!_snowman.b(_snowmanxxx)) {
            break;
         }

         elr _snowmanxxxx = _snowman.c(_snowmanxxx);
         _snowman.put(_snowmanxxx, Either.left(_snowmanxxxx));
         ekc _snowmanxxxxx = _snowman.apply(_snowmanxxxx);
         _snowmanx.addAll(this.a(_snowmanxx, _snowmanxxx, _snowmanxxxxx));
      }

      _snowman.put("particle", _snowman.b("particle") ? Either.left(_snowman.c("particle")) : _snowman.get("layer0"));
      ebf _snowmanxx = new ebf(null, _snowmanx, _snowman, false, _snowman.c(), _snowman.h(), _snowman.e());
      _snowmanxx.b = _snowman.b;
      return _snowmanxx;
   }

   private List<ebb> a(int var1, String var2, ekc var3) {
      Map<gc, ebc> _snowman = Maps.newHashMap();
      _snowman.put(gc.d, new ebc(null, _snowman, _snowman, new ebe(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0)));
      _snowman.put(gc.c, new ebc(null, _snowman, _snowman, new ebe(new float[]{16.0F, 0.0F, 0.0F, 16.0F}, 0)));
      List<ebb> _snowmanx = Lists.newArrayList();
      _snowmanx.add(new ebb(new g(0.0F, 0.0F, 7.5F), new g(16.0F, 16.0F, 8.5F), _snowman, null, true));
      _snowmanx.addAll(this.a(_snowman, _snowman, _snowman));
      return _snowmanx;
   }

   private List<ebb> a(ekc var1, String var2, int var3) {
      float _snowman = (float)_snowman.f();
      float _snowmanx = (float)_snowman.g();
      List<ebb> _snowmanxx = Lists.newArrayList();

      for (ebi.a _snowmanxxx : this.a(_snowman)) {
         float _snowmanxxxx = 0.0F;
         float _snowmanxxxxx = 0.0F;
         float _snowmanxxxxxx = 0.0F;
         float _snowmanxxxxxxx = 0.0F;
         float _snowmanxxxxxxxx = 0.0F;
         float _snowmanxxxxxxxxx = 0.0F;
         float _snowmanxxxxxxxxxx = 0.0F;
         float _snowmanxxxxxxxxxxx = 0.0F;
         float _snowmanxxxxxxxxxxxx = 16.0F / _snowman;
         float _snowmanxxxxxxxxxxxxx = 16.0F / _snowmanx;
         float _snowmanxxxxxxxxxxxxxx = (float)_snowmanxxx.b();
         float _snowmanxxxxxxxxxxxxxxx = (float)_snowmanxxx.c();
         float _snowmanxxxxxxxxxxxxxxxx = (float)_snowmanxxx.d();
         ebi.b _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxx.a();
         switch (_snowmanxxxxxxxxxxxxxxxxx) {
            case a:
               _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxxxx = _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx + 1.0F;
               _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx + 1.0F;
               break;
            case b:
               _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx + 1.0F;
               _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxxxx = _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx + 1.0F;
               _snowmanxxxxx = _snowmanxxxxxxxxxxxxxxxx + 1.0F;
               _snowmanxxxxxxx = _snowmanxxxxxxxxxxxxxxxx + 1.0F;
               break;
            case c:
               _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx + 1.0F;
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxxxxx = _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx + 1.0F;
               break;
            case d:
               _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx + 1.0F;
               _snowmanxxxx = _snowmanxxxxxxxxxxxxxxxx + 1.0F;
               _snowmanxxxxxx = _snowmanxxxxxxxxxxxxxxxx + 1.0F;
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxxxxx = _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx + 1.0F;
         }

         _snowmanxxxx *= _snowmanxxxxxxxxxxxx;
         _snowmanxxxxxx *= _snowmanxxxxxxxxxxxx;
         _snowmanxxxxx *= _snowmanxxxxxxxxxxxxx;
         _snowmanxxxxxxx *= _snowmanxxxxxxxxxxxxx;
         _snowmanxxxxx = 16.0F - _snowmanxxxxx;
         _snowmanxxxxxxx = 16.0F - _snowmanxxxxxxx;
         _snowmanxxxxxxxx *= _snowmanxxxxxxxxxxxx;
         _snowmanxxxxxxxxx *= _snowmanxxxxxxxxxxxx;
         _snowmanxxxxxxxxxx *= _snowmanxxxxxxxxxxxxx;
         _snowmanxxxxxxxxxxx *= _snowmanxxxxxxxxxxxxx;
         Map<gc, ebc> _snowmanxxxx = Maps.newHashMap();
         _snowmanxxxx.put(_snowmanxxxxxxxxxxxxxxxxx.a(), new ebc(null, _snowman, _snowman, new ebe(new float[]{_snowmanxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx}, 0)));
         switch (_snowmanxxxxxxxxxxxxxxxxx) {
            case a:
               _snowmanxx.add(new ebb(new g(_snowmanxxxx, _snowmanxxxxx, 7.5F), new g(_snowmanxxxxxx, _snowmanxxxxx, 8.5F), _snowmanxxxx, null, true));
               break;
            case b:
               _snowmanxx.add(new ebb(new g(_snowmanxxxx, _snowmanxxxxxxx, 7.5F), new g(_snowmanxxxxxx, _snowmanxxxxxxx, 8.5F), _snowmanxxxx, null, true));
               break;
            case c:
               _snowmanxx.add(new ebb(new g(_snowmanxxxx, _snowmanxxxxx, 7.5F), new g(_snowmanxxxx, _snowmanxxxxxxx, 8.5F), _snowmanxxxx, null, true));
               break;
            case d:
               _snowmanxx.add(new ebb(new g(_snowmanxxxxxx, _snowmanxxxxx, 7.5F), new g(_snowmanxxxxxx, _snowmanxxxxxxx, 8.5F), _snowmanxxxx, null, true));
         }
      }

      return _snowmanxx;
   }

   private List<ebi.a> a(ekc var1) {
      int _snowman = _snowman.f();
      int _snowmanx = _snowman.g();
      List<ebi.a> _snowmanxx = Lists.newArrayList();

      for (int _snowmanxxx = 0; _snowmanxxx < _snowman.n(); _snowmanxxx++) {
         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanx; _snowmanxxxx++) {
            for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman; _snowmanxxxxx++) {
               boolean _snowmanxxxxxx = !this.a(_snowman, _snowmanxxx, _snowmanxxxxx, _snowmanxxxx, _snowman, _snowmanx);
               this.a(ebi.b.a, _snowmanxx, _snowman, _snowmanxxx, _snowmanxxxxx, _snowmanxxxx, _snowman, _snowmanx, _snowmanxxxxxx);
               this.a(ebi.b.b, _snowmanxx, _snowman, _snowmanxxx, _snowmanxxxxx, _snowmanxxxx, _snowman, _snowmanx, _snowmanxxxxxx);
               this.a(ebi.b.c, _snowmanxx, _snowman, _snowmanxxx, _snowmanxxxxx, _snowmanxxxx, _snowman, _snowmanx, _snowmanxxxxxx);
               this.a(ebi.b.d, _snowmanxx, _snowman, _snowmanxxx, _snowmanxxxxx, _snowmanxxxx, _snowman, _snowmanx, _snowmanxxxxxx);
            }
         }
      }

      return _snowmanxx;
   }

   private void a(ebi.b var1, List<ebi.a> var2, ekc var3, int var4, int var5, int var6, int var7, int var8, boolean var9) {
      boolean _snowman = this.a(_snowman, _snowman, _snowman + _snowman.b(), _snowman + _snowman.c(), _snowman, _snowman) && _snowman;
      if (_snowman) {
         this.a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   private void a(List<ebi.a> var1, ebi.b var2, int var3, int var4) {
      ebi.a _snowman = null;

      for (ebi.a _snowmanx : _snowman) {
         if (_snowmanx.a() == _snowman) {
            int _snowmanxx = _snowman.d() ? _snowman : _snowman;
            if (_snowmanx.d() == _snowmanxx) {
               _snowman = _snowmanx;
               break;
            }
         }
      }

      int _snowmanxx = _snowman.d() ? _snowman : _snowman;
      int _snowmanxxx = _snowman.d() ? _snowman : _snowman;
      if (_snowman == null) {
         _snowman.add(new ebi.a(_snowman, _snowmanxxx, _snowmanxx));
      } else {
         _snowman.a(_snowmanxxx);
      }
   }

   private boolean a(ekc var1, int var2, int var3, int var4, int var5, int var6) {
      return _snowman >= 0 && _snowman >= 0 && _snowman < _snowman && _snowman < _snowman ? _snowman.a(_snowman, _snowman, _snowman) : true;
   }

   static class a {
      private final ebi.b a;
      private int b;
      private int c;
      private final int d;

      public a(ebi.b var1, int var2, int var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      public void a(int var1) {
         if (_snowman < this.b) {
            this.b = _snowman;
         } else if (_snowman > this.c) {
            this.c = _snowman;
         }
      }

      public ebi.b a() {
         return this.a;
      }

      public int b() {
         return this.b;
      }

      public int c() {
         return this.c;
      }

      public int d() {
         return this.d;
      }
   }

   static enum b {
      a(gc.b, 0, -1),
      b(gc.a, 0, 1),
      c(gc.f, -1, 0),
      d(gc.e, 1, 0);

      private final gc e;
      private final int f;
      private final int g;

      private b(gc var3, int var4, int var5) {
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
      }

      public gc a() {
         return this.e;
      }

      public int b() {
         return this.f;
      }

      public int c() {
         return this.g;
      }

      private boolean d() {
         return this == b || this == a;
      }
   }
}
