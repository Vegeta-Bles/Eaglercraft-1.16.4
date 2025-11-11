import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.commons.lang3.tuple.Pair;

public enum ccb {
   a("base", "b", false),
   b("square_bottom_left", "bl"),
   c("square_bottom_right", "br"),
   d("square_top_left", "tl"),
   e("square_top_right", "tr"),
   f("stripe_bottom", "bs"),
   g("stripe_top", "ts"),
   h("stripe_left", "ls"),
   i("stripe_right", "rs"),
   j("stripe_center", "cs"),
   k("stripe_middle", "ms"),
   l("stripe_downright", "drs"),
   m("stripe_downleft", "dls"),
   n("small_stripes", "ss"),
   o("cross", "cr"),
   p("straight_cross", "sc"),
   q("triangle_bottom", "bt"),
   r("triangle_top", "tt"),
   s("triangles_bottom", "bts"),
   t("triangles_top", "tts"),
   u("diagonal_left", "ld"),
   v("diagonal_up_right", "rd"),
   w("diagonal_up_left", "lud"),
   x("diagonal_right", "rud"),
   y("circle", "mc"),
   z("rhombus", "mr"),
   A("half_vertical", "vh"),
   B("half_horizontal", "hh"),
   C("half_vertical_right", "vhr"),
   D("half_horizontal_bottom", "hhb"),
   E("border", "bo"),
   F("curly_border", "cbo"),
   G("gradient", "gra"),
   H("gradient_up", "gru"),
   I("bricks", "bri"),
   J("globe", "glb", true),
   K("creeper", "cre", true),
   L("skull", "sku", true),
   M("flower", "flo", true),
   N("mojang", "moj", true),
   O("piglin", "pig", true);

   private static final ccb[] S = values();
   public static final int P = S.length;
   public static final int Q = (int)Arrays.stream(S).filter(var0 -> var0.T).count();
   public static final int R = P - Q - 1;
   private final boolean T;
   private final String U;
   private final String V;

   private ccb(String var3, String var4) {
      this(_snowman, _snowman, false);
   }

   private ccb(String var3, String var4, boolean var5) {
      this.U = _snowman;
      this.V = _snowman;
      this.T = _snowman;
   }

   public vk a(boolean var1) {
      String _snowman = _snowman ? "banner" : "shield";
      return new vk("entity/" + _snowman + "/" + this.a());
   }

   public String a() {
      return this.U;
   }

   public String b() {
      return this.V;
   }

   @Nullable
   public static ccb a(String var0) {
      for (ccb _snowman : values()) {
         if (_snowman.V.equals(_snowman)) {
            return _snowman;
         }
      }

      return null;
   }

   public static class a {
      private final List<Pair<ccb, bkx>> a = Lists.newArrayList();

      public a() {
      }

      public ccb.a a(ccb var1, bkx var2) {
         this.a.add(Pair.of(_snowman, _snowman));
         return this;
      }

      public mj a() {
         mj _snowman = new mj();

         for (Pair<ccb, bkx> _snowmanx : this.a) {
            md _snowmanxx = new md();
            _snowmanxx.a("Pattern", ((ccb)_snowmanx.getLeft()).V);
            _snowmanxx.b("Color", ((bkx)_snowmanx.getRight()).b());
            _snowman.add(_snowmanxx);
         }

         return _snowman;
      }
   }
}
