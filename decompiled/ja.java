import javax.annotation.Nullable;

public final class ja {
   public static final ja a = a("all");
   public static final ja b = a("texture", a);
   public static final ja c = a("particle", b);
   public static final ja d = a("end", a);
   public static final ja e = a("bottom", d);
   public static final ja f = a("top", d);
   public static final ja g = a("front", a);
   public static final ja h = a("back", a);
   public static final ja i = a("side", a);
   public static final ja j = a("north", i);
   public static final ja k = a("south", i);
   public static final ja l = a("east", i);
   public static final ja m = a("west", i);
   public static final ja n = a("up");
   public static final ja o = a("down");
   public static final ja p = a("cross");
   public static final ja q = a("plant");
   public static final ja r = a("wall", a);
   public static final ja s = a("rail");
   public static final ja t = a("wool");
   public static final ja u = a("pattern");
   public static final ja v = a("pane");
   public static final ja w = a("edge");
   public static final ja x = a("fan");
   public static final ja y = a("stem");
   public static final ja z = a("upperstem");
   public static final ja A = a("crop");
   public static final ja B = a("dirt");
   public static final ja C = a("fire");
   public static final ja D = a("lantern");
   public static final ja E = a("platform");
   public static final ja F = a("unsticky");
   public static final ja G = a("torch");
   public static final ja H = a("layer0");
   public static final ja I = a("lit_log");
   private final String J;
   @Nullable
   private final ja K;

   private static ja a(String var0) {
      return new ja(_snowman, null);
   }

   private static ja a(String var0, ja var1) {
      return new ja(_snowman, _snowman);
   }

   private ja(String var1, @Nullable ja var2) {
      this.J = _snowman;
      this.K = _snowman;
   }

   public String a() {
      return this.J;
   }

   @Nullable
   public ja b() {
      return this.K;
   }

   @Override
   public String toString() {
      return "#" + this.J;
   }
}
