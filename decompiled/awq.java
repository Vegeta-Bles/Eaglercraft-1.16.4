import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class awq<T extends bhc> extends avv {
   private final T a;

   public awq(T var1) {
      this.a = _snowman;
      this.a(EnumSet.of(avv.a.a));
   }

   @Override
   public boolean a() {
      return this.a.A() == null && !this.a.bs() && this.a.fb() && !this.a.fa().a() && !((aag)this.a.l).a_(this.a.cB());
   }

   @Override
   public boolean b() {
      return this.a.fb() && !this.a.fa().a() && this.a.l instanceof aag && !((aag)this.a.l).a_(this.a.cB());
   }

   @Override
   public void e() {
      if (this.a.fb()) {
         bhb _snowman = this.a.fa();
         if (this.a.K % 20 == 0) {
            this.a(_snowman);
         }

         if (!this.a.eI()) {
            dcn _snowmanx = azj.b(this.a, 15, 4, dcn.c(_snowman.t()));
            if (_snowmanx != null) {
               this.a.x().a(_snowmanx.b, _snowmanx.c, _snowmanx.d, 1.0);
            }
         }
      }
   }

   private void a(bhb var1) {
      if (_snowman.v()) {
         Set<bhc> _snowman = Sets.newHashSet();
         List<bhc> _snowmanx = this.a.l.a(bhc.class, this.a.cc().g(16.0), var1x -> !var1x.fb() && bhd.a(var1x, _snowman));
         _snowman.addAll(_snowmanx);

         for (bhc _snowmanxx : _snowman) {
            _snowman.a(_snowman.k(), _snowmanxx, null, true);
         }
      }
   }
}
