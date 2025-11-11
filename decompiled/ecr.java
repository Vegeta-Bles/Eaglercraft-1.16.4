public class ecr extends ecs<cdk> {
   private static final vk d = new vk("textures/entity/end_gateway_beam.png");

   public ecr(ecd var1) {
      super(_snowman);
   }

   public void a(cdk var1, float var2, dfm var3, eag var4, int var5, int var6) {
      if (_snowman.d() || _snowman.f()) {
         float _snowman = _snowman.d() ? _snowman.a(_snowman) : _snowman.b(_snowman);
         double _snowmanx = _snowman.d() ? 256.0 : 50.0;
         _snowman = afm.a(_snowman * (float) Math.PI);
         int _snowmanxx = afm.c((double)_snowman * _snowmanx);
         float[] _snowmanxxx = _snowman.d() ? bkx.c.e() : bkx.k.e();
         long _snowmanxxxx = _snowman.v().T();
         eca.a(_snowman, _snowman, d, _snowman, _snowman, _snowmanxxxx, 0, _snowmanxx, _snowmanxxx, 0.15F, 0.175F);
         eca.a(_snowman, _snowman, d, _snowman, _snowman, _snowmanxxxx, 0, -_snowmanxx, _snowmanxxx, 0.15F, 0.175F);
      }

      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected int a(double var1) {
      return super.a(_snowman) + 1;
   }

   @Override
   protected float a() {
      return 1.0F;
   }
}
