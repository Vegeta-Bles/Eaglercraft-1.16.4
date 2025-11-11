import java.util.function.Function;

public enum eog {
   a("movement", eob::new),
   b("find_tree", eoa::new),
   c("punch_tree", eod::new),
   d("open_inventory", eoc::new),
   e("craft_planks", enz::new),
   f("none", eny::new);

   private final String g;
   private final Function<eoe, ? extends eof> h;

   private <T extends eof> eog(String var3, Function<eoe, T> var4) {
      this.g = _snowman;
      this.h = _snowman;
   }

   public eof a(eoe var1) {
      return this.h.apply(_snowman);
   }

   public String a() {
      return this.g;
   }

   public static eog a(String var0) {
      for (eog _snowman : values()) {
         if (_snowman.g.equals(_snowman)) {
            return _snowman;
         }
      }

      return f;
   }
}
