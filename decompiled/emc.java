public abstract class emc implements emt {
   protected emq a;
   protected final adr b;
   protected final vk c;
   protected float d = 1.0F;
   protected float e = 1.0F;
   protected double f;
   protected double g;
   protected double h;
   protected boolean i;
   protected int j;
   protected emt.a k = emt.a.b;
   protected boolean l;
   protected boolean m;

   protected emc(adp var1, adr var2) {
      this(_snowman.a(), _snowman);
   }

   protected emc(vk var1, adr var2) {
      this.c = _snowman;
      this.b = _snowman;
   }

   @Override
   public vk a() {
      return this.c;
   }

   @Override
   public env a(enu var1) {
      env _snowman = _snowman.a(this.c);
      if (_snowman == null) {
         this.a = enu.a;
      } else {
         this.a = _snowman.a();
      }

      return _snowman;
   }

   @Override
   public emq b() {
      return this.a;
   }

   @Override
   public adr c() {
      return this.b;
   }

   @Override
   public boolean d() {
      return this.i;
   }

   @Override
   public int e() {
      return this.j;
   }

   @Override
   public float f() {
      return this.d * this.a.c();
   }

   @Override
   public float g() {
      return this.e * this.a.d();
   }

   @Override
   public double h() {
      return this.f;
   }

   @Override
   public double i() {
      return this.g;
   }

   @Override
   public double j() {
      return this.h;
   }

   @Override
   public emt.a k() {
      return this.k;
   }

   @Override
   public boolean m() {
      return this.m;
   }

   @Override
   public String toString() {
      return "SoundInstance[" + this.c + "]";
   }
}
