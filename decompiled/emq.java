public class emq implements enw<emq> {
   private final vk a;
   private final float b;
   private final float c;
   private final int d;
   private final emq.a e;
   private final boolean f;
   private final boolean g;
   private final int h;

   public emq(String var1, float var2, float var3, int var4, emq.a var5, boolean var6, boolean var7, int var8) {
      this.a = new vk(_snowman);
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
   }

   public vk a() {
      return this.a;
   }

   public vk b() {
      return new vk(this.a.b(), "sounds/" + this.a.a() + ".ogg");
   }

   public float c() {
      return this.b;
   }

   public float d() {
      return this.c;
   }

   @Override
   public int e() {
      return this.d;
   }

   public emq f() {
      return this;
   }

   @Override
   public void a(enr var1) {
      if (this.g) {
         _snowman.a(this);
      }
   }

   public emq.a g() {
      return this.e;
   }

   public boolean h() {
      return this.f;
   }

   public boolean i() {
      return this.g;
   }

   public int j() {
      return this.h;
   }

   @Override
   public String toString() {
      return "Sound[" + this.a + "]";
   }

   public static enum a {
      a("file"),
      b("event");

      private final String c;

      private a(String var3) {
         this.c = _snowman;
      }

      public static emq.a a(String var0) {
         for (emq.a _snowman : values()) {
            if (_snowman.c.equals(_snowman)) {
               return _snowman;
            }
         }

         return null;
      }
   }
}
