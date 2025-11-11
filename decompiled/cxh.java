public class cxh extends cxb {
   private float m = Float.MAX_VALUE;
   private cxb n;
   private boolean o;

   public cxh(cxb var1) {
      super(_snowman.a, _snowman.b, _snowman.c);
   }

   public cxh(int var1, int var2, int var3) {
      super(_snowman, _snowman, _snowman);
   }

   public void a(float var1, cxb var2) {
      if (_snowman < this.m) {
         this.m = _snowman;
         this.n = _snowman;
      }
   }

   public cxb d() {
      return this.n;
   }

   public void e() {
      this.o = true;
   }

   public static cxh c(nf var0) {
      cxh _snowman = new cxh(_snowman.readInt(), _snowman.readInt(), _snowman.readInt());
      _snowman.j = _snowman.readFloat();
      _snowman.k = _snowman.readFloat();
      _snowman.i = _snowman.readBoolean();
      _snowman.l = cwz.values()[_snowman.readInt()];
      _snowman.g = _snowman.readFloat();
      return _snowman;
   }
}
