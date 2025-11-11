import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public enum c implements afs {
   a("identity", e.a, false, false, false),
   b("rot_180_face_xy", e.a, true, true, false),
   c("rot_180_face_xz", e.a, true, false, true),
   d("rot_180_face_yz", e.a, false, true, true),
   e("rot_120_nnn", e.d, false, false, false),
   f("rot_120_nnp", e.e, true, false, true),
   g("rot_120_npn", e.e, false, true, true),
   h("rot_120_npp", e.d, true, false, true),
   i("rot_120_pnn", e.e, true, true, false),
   j("rot_120_pnp", e.d, true, true, false),
   k("rot_120_ppn", e.d, false, true, true),
   l("rot_120_ppp", e.e, false, false, false),
   m("rot_180_edge_xy_neg", e.b, true, true, true),
   n("rot_180_edge_xy_pos", e.b, false, false, true),
   o("rot_180_edge_xz_neg", e.f, true, true, true),
   p("rot_180_edge_xz_pos", e.f, false, true, false),
   q("rot_180_edge_yz_neg", e.c, true, true, true),
   r("rot_180_edge_yz_pos", e.c, true, false, false),
   s("rot_90_x_neg", e.c, false, false, true),
   t("rot_90_x_pos", e.c, false, true, false),
   u("rot_90_y_neg", e.f, true, false, false),
   v("rot_90_y_pos", e.f, false, false, true),
   w("rot_90_z_neg", e.b, false, true, false),
   x("rot_90_z_pos", e.b, true, false, false),
   y("inversion", e.a, true, true, true),
   z("invert_x", e.a, true, false, false),
   A("invert_y", e.a, false, true, false),
   B("invert_z", e.a, false, false, true),
   C("rot_60_ref_nnn", e.e, true, true, true),
   D("rot_60_ref_nnp", e.d, true, false, false),
   E("rot_60_ref_npn", e.d, false, false, true),
   F("rot_60_ref_npp", e.e, false, false, true),
   G("rot_60_ref_pnn", e.d, false, true, false),
   H("rot_60_ref_pnp", e.e, true, false, false),
   I("rot_60_ref_ppn", e.e, false, true, false),
   J("rot_60_ref_ppp", e.d, true, true, true),
   K("swap_xy", e.b, false, false, false),
   L("swap_yz", e.c, false, false, false),
   M("swap_xz", e.f, false, false, false),
   N("swap_neg_xy", e.b, true, true, false),
   O("swap_neg_yz", e.c, false, true, true),
   P("swap_neg_xz", e.f, true, false, true),
   Q("rot_90_ref_x_neg", e.c, true, false, true),
   R("rot_90_ref_x_pos", e.c, true, true, false),
   S("rot_90_ref_y_neg", e.f, true, true, false),
   T("rot_90_ref_y_pos", e.f, false, true, true),
   U("rot_90_ref_z_neg", e.b, false, true, true),
   V("rot_90_ref_z_pos", e.b, true, false, true);

   private final a W;
   private final String X;
   @Nullable
   private Map<gc, gc> Y;
   private final boolean Z;
   private final boolean aa;
   private final boolean ab;
   private final e ac;
   private static final c[][] ad = x.a(new c[values().length][values().length], var0 -> {
      Map<Pair<e, BooleanList>, c> _snowman = Arrays.stream(values()).collect(Collectors.toMap(var0x -> Pair.of(var0x.ac, var0x.b()), var0x -> (c)var0x));

      for (c _snowmanx : values()) {
         for (c _snowmanxx : values()) {
            BooleanList _snowmanxxx = _snowmanx.b();
            BooleanList _snowmanxxxx = _snowmanxx.b();
            e _snowmanxxxxx = _snowmanxx.ac.a(_snowmanx.ac);
            BooleanArrayList _snowmanxxxxxx = new BooleanArrayList(3);

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 3; _snowmanxxxxxxx++) {
               _snowmanxxxxxx.add(_snowmanxxx.getBoolean(_snowmanxxxxxxx) ^ _snowmanxxxx.getBoolean(_snowmanx.ac.a(_snowmanxxxxxxx)));
            }

            var0[_snowmanx.ordinal()][_snowmanxx.ordinal()] = _snowman.get(Pair.of(_snowmanxxxxx, _snowmanxxxxxx));
         }
      }
   });
   private static final c[] ae = Arrays.stream(values())
      .map(var0 -> Arrays.stream(values()).filter(var1 -> var0.a(var1) == a).findAny().get())
      .toArray(c[]::new);

   private c(String var3, e var4, boolean var5, boolean var6, boolean var7) {
      this.X = _snowman;
      this.Z = _snowman;
      this.aa = _snowman;
      this.ab = _snowman;
      this.ac = _snowman;
      this.W = new a();
      this.W.a = _snowman ? -1.0F : 1.0F;
      this.W.e = _snowman ? -1.0F : 1.0F;
      this.W.i = _snowman ? -1.0F : 1.0F;
      this.W.b(_snowman.a());
   }

   private BooleanList b() {
      return new BooleanArrayList(new boolean[]{this.Z, this.aa, this.ab});
   }

   public c a(c var1) {
      return ad[this.ordinal()][_snowman.ordinal()];
   }

   @Override
   public String toString() {
      return this.X;
   }

   @Override
   public String a() {
      return this.X;
   }

   public gc a(gc var1) {
      if (this.Y == null) {
         this.Y = Maps.newEnumMap(gc.class);

         for (gc _snowman : gc.values()) {
            gc.a _snowmanx = _snowman.n();
            gc.b _snowmanxx = _snowman.e();
            gc.a _snowmanxxx = gc.a.values()[this.ac.a(_snowmanx.ordinal())];
            gc.b _snowmanxxxx = this.a(_snowmanxxx) ? _snowmanxx.c() : _snowmanxx;
            gc _snowmanxxxxx = gc.a(_snowmanxxx, _snowmanxxxx);
            this.Y.put(_snowman, _snowmanxxxxx);
         }
      }

      return this.Y.get(_snowman);
   }

   public boolean a(gc.a var1) {
      switch (_snowman) {
         case a:
            return this.Z;
         case b:
            return this.aa;
         case c:
         default:
            return this.ab;
      }
   }

   public ge a(ge var1) {
      return ge.a(this.a(_snowman.b()), this.a(_snowman.c()));
   }
}
