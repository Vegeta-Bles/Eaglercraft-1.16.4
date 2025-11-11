import java.util.UUID;

public abstract class aok {
   private final UUID h;
   protected nr a;
   protected float b;
   protected aok.a c;
   protected aok.b d;
   protected boolean e;
   protected boolean f;
   protected boolean g;

   public aok(UUID var1, nr var2, aok.a var3, aok.b var4) {
      this.h = _snowman;
      this.a = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.b = 1.0F;
   }

   public UUID i() {
      return this.h;
   }

   public nr j() {
      return this.a;
   }

   public void a(nr var1) {
      this.a = _snowman;
   }

   public float k() {
      return this.b;
   }

   public void a(float var1) {
      this.b = _snowman;
   }

   public aok.a l() {
      return this.c;
   }

   public void a(aok.a var1) {
      this.c = _snowman;
   }

   public aok.b m() {
      return this.d;
   }

   public void a(aok.b var1) {
      this.d = _snowman;
   }

   public boolean n() {
      return this.e;
   }

   public aok a(boolean var1) {
      this.e = _snowman;
      return this;
   }

   public boolean o() {
      return this.f;
   }

   public aok b(boolean var1) {
      this.f = _snowman;
      return this;
   }

   public aok c(boolean var1) {
      this.g = _snowman;
      return this;
   }

   public boolean p() {
      return this.g;
   }

   public static enum a {
      a("pink", k.m),
      b("blue", k.j),
      c("red", k.e),
      d("green", k.k),
      e("yellow", k.o),
      f("purple", k.b),
      g("white", k.p);

      private final String h;
      private final k i;

      private a(String var3, k var4) {
         this.h = _snowman;
         this.i = _snowman;
      }

      public k a() {
         return this.i;
      }

      public String b() {
         return this.h;
      }

      public static aok.a a(String var0) {
         for (aok.a _snowman : values()) {
            if (_snowman.h.equals(_snowman)) {
               return _snowman;
            }
         }

         return g;
      }
   }

   public static enum b {
      a("progress"),
      b("notched_6"),
      c("notched_10"),
      d("notched_12"),
      e("notched_20");

      private final String f;

      private b(String var3) {
         this.f = _snowman;
      }

      public String a() {
         return this.f;
      }

      public static aok.b a(String var0) {
         for (aok.b _snowman : values()) {
            if (_snowman.f.equals(_snowman)) {
               return _snowman;
            }
         }

         return a;
      }
   }
}
