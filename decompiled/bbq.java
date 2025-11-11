import java.util.Optional;
import javax.annotation.Nullable;

public class bbq extends aqa {
   private static final us<Optional<fx>> c = uv.a(bbq.class, uu.m);
   private static final us<Boolean> d = uv.a(bbq.class, uu.i);
   public int b;

   public bbq(aqe<? extends bbq> var1, brx var2) {
      super(_snowman, _snowman);
      this.i = true;
      this.b = this.J.nextInt(100000);
   }

   public bbq(brx var1, double var2, double var4, double var6) {
      this(aqe.s, _snowman);
      this.d(_snowman, _snowman, _snowman);
   }

   @Override
   protected boolean aC() {
      return false;
   }

   @Override
   protected void e() {
      this.ab().a(c, Optional.empty());
      this.ab().a(d, true);
   }

   @Override
   public void j() {
      this.b++;
      if (this.l instanceof aag) {
         fx _snowman = this.cB();
         if (((aag)this.l).D() != null && this.l.d_(_snowman).g()) {
            this.l.a(_snowman, bue.a(this.l, _snowman));
         }
      }
   }

   @Override
   protected void b(md var1) {
      if (this.g() != null) {
         _snowman.a("BeamTarget", mp.a(this.g()));
      }

      _snowman.a("ShowBottom", this.h());
   }

   @Override
   protected void a(md var1) {
      if (_snowman.c("BeamTarget", 10)) {
         this.a(mp.b(_snowman.p("BeamTarget")));
      }

      if (_snowman.c("ShowBottom", 1)) {
         this.a(_snowman.q("ShowBottom"));
      }
   }

   @Override
   public boolean aT() {
      return true;
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else if (_snowman.k() instanceof bbr) {
         return false;
      } else {
         if (!this.y && !this.l.v) {
            this.ad();
            if (!_snowman.d()) {
               this.l.a(null, this.cD(), this.cE(), this.cH(), 6.0F, brp.a.c);
            }

            this.a(_snowman);
         }

         return true;
      }
   }

   @Override
   public void aa() {
      this.a(apk.n);
      super.aa();
   }

   private void a(apk var1) {
      if (this.l instanceof aag) {
         chg _snowman = ((aag)this.l).D();
         if (_snowman != null) {
            _snowman.a(this, _snowman);
         }
      }
   }

   public void a(@Nullable fx var1) {
      this.ab().b(c, Optional.ofNullable(_snowman));
   }

   @Nullable
   public fx g() {
      return this.ab().a(c).orElse(null);
   }

   public void a(boolean var1) {
      this.ab().b(d, _snowman);
   }

   public boolean h() {
      return this.ab().a(d);
   }

   @Override
   public boolean a(double var1) {
      return super.a(_snowman) || this.g() != null;
   }

   @Override
   public oj<?> P() {
      return new on(this);
   }
}
