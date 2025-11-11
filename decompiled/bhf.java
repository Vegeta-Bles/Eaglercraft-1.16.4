public class bhf {
   public static final bhf a = a("core");
   public static final bhf b = a("idle");
   public static final bhf c = a("work");
   public static final bhf d = a("play");
   public static final bhf e = a("rest");
   public static final bhf f = a("meet");
   public static final bhf g = a("panic");
   public static final bhf h = a("raid");
   public static final bhf i = a("pre_raid");
   public static final bhf j = a("hide");
   public static final bhf k = a("fight");
   public static final bhf l = a("celebrate");
   public static final bhf m = a("admire_item");
   public static final bhf n = a("avoid");
   public static final bhf o = a("ride");
   private final String p;
   private final int q;

   private bhf(String var1) {
      this.p = _snowman;
      this.q = _snowman.hashCode();
   }

   public String a() {
      return this.p;
   }

   private static bhf a(String var0) {
      return gm.a(gm.an, _snowman, new bhf(_snowman));
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         bhf _snowman = (bhf)_snowman;
         return this.p.equals(_snowman.p);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.q;
   }

   @Override
   public String toString() {
      return this.a();
   }
}
