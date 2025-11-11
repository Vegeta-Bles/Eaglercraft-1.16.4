import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;

public class dvd<T extends aqm> extends dum<T> {
   private List<dwn> a = Lists.newArrayList();
   public final dwn t;
   public final dwn u;
   public final dwn v;
   public final dwn w;
   public final dwn x;
   private final dwn b;
   private final dwn y;
   private final boolean z;

   public dvd(float var1, boolean var2) {
      super(eao::h, _snowman, 0.0F, 64, 64);
      this.z = _snowman;
      this.y = new dwn(this, 24, 0);
      this.y.a(-3.0F, -6.0F, -1.0F, 6.0F, 6.0F, 1.0F, _snowman);
      this.b = new dwn(this, 0, 0);
      this.b.b(64, 32);
      this.b.a(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, _snowman);
      if (_snowman) {
         this.j = new dwn(this, 32, 48);
         this.j.a(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, _snowman);
         this.j.a(5.0F, 2.5F, 0.0F);
         this.i = new dwn(this, 40, 16);
         this.i.a(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, _snowman);
         this.i.a(-5.0F, 2.5F, 0.0F);
         this.t = new dwn(this, 48, 48);
         this.t.a(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, _snowman + 0.25F);
         this.t.a(5.0F, 2.5F, 0.0F);
         this.u = new dwn(this, 40, 32);
         this.u.a(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, _snowman + 0.25F);
         this.u.a(-5.0F, 2.5F, 10.0F);
      } else {
         this.j = new dwn(this, 32, 48);
         this.j.a(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
         this.j.a(5.0F, 2.0F, 0.0F);
         this.t = new dwn(this, 48, 48);
         this.t.a(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman + 0.25F);
         this.t.a(5.0F, 2.0F, 0.0F);
         this.u = new dwn(this, 40, 32);
         this.u.a(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman + 0.25F);
         this.u.a(-5.0F, 2.0F, 10.0F);
      }

      this.l = new dwn(this, 16, 48);
      this.l.a(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
      this.l.a(1.9F, 12.0F, 0.0F);
      this.v = new dwn(this, 0, 48);
      this.v.a(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman + 0.25F);
      this.v.a(1.9F, 12.0F, 0.0F);
      this.w = new dwn(this, 0, 32);
      this.w.a(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman + 0.25F);
      this.w.a(-1.9F, 12.0F, 0.0F);
      this.x = new dwn(this, 16, 32);
      this.x.a(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, _snowman + 0.25F);
      this.x.a(0.0F, 0.0F, 0.0F);
   }

   @Override
   protected Iterable<dwn> b() {
      return Iterables.concat(super.b(), ImmutableList.of(this.v, this.w, this.t, this.u, this.x));
   }

   public void a(dfm var1, dfq var2, int var3, int var4) {
      this.y.a(this.f);
      this.y.a = 0.0F;
      this.y.b = 0.0F;
      this.y.a(_snowman, _snowman, _snowman, _snowman);
   }

   public void b(dfm var1, dfq var2, int var3, int var4) {
      this.b.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.v.a(this.l);
      this.w.a(this.k);
      this.t.a(this.j);
      this.u.a(this.i);
      this.x.a(this.h);
      if (_snowman.b(aqf.e).a()) {
         if (_snowman.bz()) {
            this.b.c = 1.4F;
            this.b.b = 1.85F;
         } else {
            this.b.c = 0.0F;
            this.b.b = 0.0F;
         }
      } else if (_snowman.bz()) {
         this.b.c = 0.3F;
         this.b.b = 0.8F;
      } else {
         this.b.c = -1.1F;
         this.b.b = -0.85F;
      }
   }

   @Override
   public void d_(boolean var1) {
      super.d_(_snowman);
      this.t.h = _snowman;
      this.u.h = _snowman;
      this.v.h = _snowman;
      this.w.h = _snowman;
      this.x.h = _snowman;
      this.b.h = _snowman;
      this.y.h = _snowman;
   }

   @Override
   public void a(aqi var1, dfm var2) {
      dwn _snowman = this.a(_snowman);
      if (this.z) {
         float _snowmanx = 0.5F * (float)(_snowman == aqi.b ? 1 : -1);
         _snowman.a += _snowmanx;
         _snowman.a(_snowman);
         _snowman.a -= _snowmanx;
      } else {
         _snowman.a(_snowman);
      }
   }

   public dwn a(Random var1) {
      return this.a.get(_snowman.nextInt(this.a.size()));
   }

   @Override
   public void b(dwn var1) {
      if (this.a == null) {
         this.a = Lists.newArrayList();
      }

      this.a.add(_snowman);
   }
}
