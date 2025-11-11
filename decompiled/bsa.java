import com.mojang.serialization.Dynamic;

public final class bsa {
   private final String a;
   private final bru b;
   private final boolean c;
   private final aor d;
   private final boolean e;
   private final brt f;
   private final brk g;

   public bsa(String var1, bru var2, boolean var3, aor var4, boolean var5, brt var6, brk var7) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
   }

   public static bsa a(Dynamic<?> var0, brk var1) {
      bru _snowman = bru.a(_snowman.get("GameType").asInt(0));
      return new bsa(
         _snowman.get("LevelName").asString(""),
         _snowman,
         _snowman.get("hardcore").asBoolean(false),
         _snowman.get("Difficulty").asNumber().map(var0x -> aor.a(var0x.byteValue())).result().orElse(aor.c),
         _snowman.get("allowCommands").asBoolean(_snowman == bru.c),
         new brt(_snowman.get("GameRules")),
         _snowman
      );
   }

   public String a() {
      return this.a;
   }

   public bru b() {
      return this.b;
   }

   public boolean c() {
      return this.c;
   }

   public aor d() {
      return this.d;
   }

   public boolean e() {
      return this.e;
   }

   public brt f() {
      return this.f;
   }

   public brk g() {
      return this.g;
   }

   public bsa a(bru var1) {
      return new bsa(this.a, _snowman, this.c, this.d, this.e, this.f, this.g);
   }

   public bsa a(aor var1) {
      return new bsa(this.a, this.b, this.c, _snowman, this.e, this.f, this.g);
   }

   public bsa a(brk var1) {
      return new bsa(this.a, this.b, this.c, this.d, this.e, this.f, _snowman);
   }

   public bsa h() {
      return new bsa(this.a, this.b, this.c, this.d, this.e, this.f.b(), this.g);
   }
}
