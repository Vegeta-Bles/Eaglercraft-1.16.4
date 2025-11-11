import com.google.common.collect.ImmutableList;
import java.util.List;

public class dto extends duv {
   private final dwn a = new dwn(64, 32, 0, 0).a(-6.0F, -5.0F, -0.005F, 6.0F, 10.0F, 0.005F);
   private final dwn b = new dwn(64, 32, 16, 0).a(0.0F, -5.0F, -0.005F, 6.0F, 10.0F, 0.005F);
   private final dwn c;
   private final dwn d;
   private final dwn e;
   private final dwn f;
   private final dwn g = new dwn(64, 32, 12, 0).a(-1.0F, -5.0F, 0.0F, 2.0F, 10.0F, 0.005F);
   private final List<dwn> h;

   public dto() {
      super(eao::b);
      this.c = new dwn(64, 32, 0, 10).a(0.0F, -4.0F, -0.99F, 5.0F, 8.0F, 1.0F);
      this.d = new dwn(64, 32, 12, 10).a(0.0F, -4.0F, -0.01F, 5.0F, 8.0F, 1.0F);
      this.e = new dwn(64, 32, 24, 10).a(0.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.005F);
      this.f = new dwn(64, 32, 24, 10).a(0.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.005F);
      this.h = ImmutableList.of(this.a, this.b, this.g, this.c, this.d, this.e, this.f);
      this.a.a(0.0F, 0.0F, -1.0F);
      this.b.a(0.0F, 0.0F, 1.0F);
      this.g.e = (float) (Math.PI / 2);
   }

   @Override
   public void a(dfm var1, dfq var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      this.b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public void b(dfm var1, dfq var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      this.h.forEach(var8x -> var8x.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
   }

   public void a(float var1, float var2, float var3, float var4) {
      float _snowman = (afm.a(_snowman * 0.02F) * 0.1F + 1.25F) * _snowman;
      this.a.e = (float) Math.PI + _snowman;
      this.b.e = -_snowman;
      this.c.e = _snowman;
      this.d.e = -_snowman;
      this.e.e = _snowman - _snowman * 2.0F * _snowman;
      this.f.e = _snowman - _snowman * 2.0F * _snowman;
      this.c.a = afm.a(_snowman);
      this.d.a = afm.a(_snowman);
      this.e.a = afm.a(_snowman);
      this.f.a = afm.a(_snowman);
   }
}
