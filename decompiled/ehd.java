public class ehd extends efw<baw, duc<baw>> {
   private final dwa<baw> a = new dwa<>(0.0F);
   private final dwb<baw> g = new dwb<>(0.0F);

   public ehd(eet var1) {
      super(_snowman, new dwa<>(0.0F), 0.15F);
      this.a(new ejd(this));
   }

   public vk a(baw var1) {
      return _snowman.eZ();
   }

   public void a(baw var1, float var2, float var3, dfm var4, eag var5, int var6) {
      dtu<baw> _snowman = (dtu<baw>)(_snowman.eX() == 0 ? this.a : this.g);
      this.e = _snowman;
      float[] _snowmanx = _snowman.eV();
      _snowman.a(_snowmanx[0], _snowmanx[1], _snowmanx[2]);
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.a(1.0F, 1.0F, 1.0F);
   }

   protected void a(baw var1, dfm var2, float var3, float var4, float var5) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      float _snowman = 4.3F * afm.a(0.6F * _snowman);
      _snowman.a(g.d.a(_snowman));
      if (!_snowman.aE()) {
         _snowman.a(0.2F, 0.1F, 0.0);
         _snowman.a(g.f.a(90.0F));
      }
   }
}
