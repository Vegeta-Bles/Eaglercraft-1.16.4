import javax.annotation.Nullable;

public class apk {
   public static final apk a = new apk("inFire").l().o();
   public static final apk b = new apk("lightningBolt");
   public static final apk c = new apk("onFire").l().o();
   public static final apk d = new apk("lava").o();
   public static final apk e = new apk("hotFloor").o();
   public static final apk f = new apk("inWall").l();
   public static final apk g = new apk("cramming").l();
   public static final apk h = new apk("drown").l();
   public static final apk i = new apk("starve").l().n();
   public static final apk j = new apk("cactus");
   public static final apk k = new apk("fall").l();
   public static final apk l = new apk("flyIntoWall").l();
   public static final apk m = new apk("outOfWorld").l().m();
   public static final apk n = new apk("generic").l();
   public static final apk o = new apk("magic").l().u();
   public static final apk p = new apk("wither").l();
   public static final apk q = new apk("anvil");
   public static final apk r = new apk("fallingBlock");
   public static final apk s = new apk("dragonBreath").l();
   public static final apk t = new apk("dryout");
   public static final apk u = new apk("sweetBerryBush");
   private boolean w;
   private boolean x;
   private boolean y;
   private float z = 0.1F;
   private boolean A;
   private boolean B;
   private boolean C;
   private boolean D;
   private boolean E;
   public final String v;

   public static apk b(aqm var0) {
      return new apl("sting", _snowman);
   }

   public static apk c(aqm var0) {
      return new apl("mob", _snowman);
   }

   public static apk a(aqa var0, aqm var1) {
      return new apm("mob", _snowman, _snowman);
   }

   public static apk a(bfw var0) {
      return new apl("player", _snowman);
   }

   public static apk a(bga var0, @Nullable aqa var1) {
      return new apm("arrow", _snowman, _snowman).c();
   }

   public static apk a(aqa var0, @Nullable aqa var1) {
      return new apm("trident", _snowman, _snowman).c();
   }

   public static apk a(bgh var0, @Nullable aqa var1) {
      return new apm("fireworks", _snowman, _snowman).e();
   }

   public static apk a(bgg var0, @Nullable aqa var1) {
      return _snowman == null ? new apm("onFire", _snowman, _snowman).o().c() : new apm("fireball", _snowman, _snowman).o().c();
   }

   public static apk a(bgz var0, aqa var1) {
      return new apm("witherSkull", _snowman, _snowman).c();
   }

   public static apk b(aqa var0, @Nullable aqa var1) {
      return new apm("thrown", _snowman, _snowman).c();
   }

   public static apk c(aqa var0, @Nullable aqa var1) {
      return new apm("indirectMagic", _snowman, _snowman).l().u();
   }

   public static apk a(aqa var0) {
      return new apl("thorns", _snowman).x().u();
   }

   public static apk a(@Nullable brp var0) {
      return d(_snowman != null ? _snowman.d() : null);
   }

   public static apk d(@Nullable aqm var0) {
      return _snowman != null ? new apl("explosion.player", _snowman).r().e() : new apk("explosion").r().e();
   }

   public static apk a() {
      return new apg();
   }

   @Override
   public String toString() {
      return "DamageSource (" + this.v + ")";
   }

   public boolean b() {
      return this.B;
   }

   public apk c() {
      this.B = true;
      return this;
   }

   public boolean d() {
      return this.E;
   }

   public apk e() {
      this.E = true;
      return this;
   }

   public boolean f() {
      return this.w;
   }

   public float g() {
      return this.z;
   }

   public boolean h() {
      return this.x;
   }

   public boolean i() {
      return this.y;
   }

   protected apk(String var1) {
      this.v = _snowman;
   }

   @Nullable
   public aqa j() {
      return this.k();
   }

   @Nullable
   public aqa k() {
      return null;
   }

   protected apk l() {
      this.w = true;
      this.z = 0.0F;
      return this;
   }

   protected apk m() {
      this.x = true;
      return this;
   }

   protected apk n() {
      this.y = true;
      this.z = 0.0F;
      return this;
   }

   protected apk o() {
      this.A = true;
      return this;
   }

   public nr a(aqm var1) {
      aqm _snowman = _snowman.dw();
      String _snowmanx = "death.attack." + this.v;
      String _snowmanxx = _snowmanx + ".player";
      return _snowman != null ? new of(_snowmanxx, _snowman.d(), _snowman.d()) : new of(_snowmanx, _snowman.d());
   }

   public boolean p() {
      return this.A;
   }

   public String q() {
      return this.v;
   }

   public apk r() {
      this.C = true;
      return this;
   }

   public boolean s() {
      return this.C;
   }

   public boolean t() {
      return this.D;
   }

   public apk u() {
      this.D = true;
      return this;
   }

   public boolean v() {
      aqa _snowman = this.k();
      return _snowman instanceof bfw && ((bfw)_snowman).bC.d;
   }

   @Nullable
   public dcn w() {
      return null;
   }
}
