public class dya extends dyg {
   private final eam a;
   private final aqa b;
   private final aqa B;
   private int C;
   private final eet D;

   public dya(eet var1, eam var2, dwt var3, aqa var4, aqa var5) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman.cC());
   }

   private dya(eet var1, eam var2, dwt var3, aqa var4, aqa var5, dcn var6) {
      super(_snowman, _snowman.cD(), _snowman.cE(), _snowman.cH(), _snowman.b, _snowman.c, _snowman.d);
      this.a = _snowman;
      this.b = this.a(_snowman);
      this.B = _snowman;
      this.D = _snowman;
   }

   private aqa a(aqa var1) {
      return (aqa)(!(_snowman instanceof bcv) ? _snowman : ((bcv)_snowman).t());
   }

   @Override
   public dyk b() {
      return dyk.e;
   }

   @Override
   public void a(dfq var1, djk var2, float var3) {
      float _snowman = ((float)this.C + _snowman) / 3.0F;
      _snowman *= _snowman;
      double _snowmanx = afm.d((double)_snowman, this.B.D, this.B.cD());
      double _snowmanxx = afm.d((double)_snowman, this.B.E, this.B.cE()) + 0.5;
      double _snowmanxxx = afm.d((double)_snowman, this.B.F, this.B.cH());
      double _snowmanxxxx = afm.d((double)_snowman, this.b.cD(), _snowmanx);
      double _snowmanxxxxx = afm.d((double)_snowman, this.b.cE(), _snowmanxx);
      double _snowmanxxxxxx = afm.d((double)_snowman, this.b.cH(), _snowmanxxx);
      eag.a _snowmanxxxxxxx = this.a.b();
      dcn _snowmanxxxxxxxx = _snowman.b();
      this.D.a(this.b, _snowmanxxxx - _snowmanxxxxxxxx.a(), _snowmanxxxxx - _snowmanxxxxxxxx.b(), _snowmanxxxxxx - _snowmanxxxxxxxx.c(), this.b.p, _snowman, new dfm(), _snowmanxxxxxxx, this.D.a(this.b, _snowman));
      _snowmanxxxxxxx.a();
   }

   @Override
   public void a() {
      this.C++;
      if (this.C == 3) {
         this.j();
      }
   }
}
