import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class auj extends arv<aqu> {
   private final float b;
   private final int c;
   private final int d;

   public auj(float var1) {
      this(_snowman, 10, 7);
   }

   public auj(float var1, int var2, int var3) {
      super(ImmutableMap.of(ayd.m, aye.b));
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   protected void a(aag var1, aqu var2, long var3) {
      fx _snowman = _snowman.cB();
      if (_snowman.a_(_snowman)) {
         this.a(_snowman);
      } else {
         gp _snowmanx = gp.a(_snowman);
         gp _snowmanxx = arw.a(_snowman, _snowmanx, 2);
         if (_snowmanxx != _snowmanx) {
            this.a(_snowman, _snowmanxx);
         } else {
            this.a(_snowman);
         }
      }
   }

   private void a(aqu var1, gp var2) {
      Optional<dcn> _snowman = Optional.ofNullable(azj.b(_snowman, this.c, this.d, dcn.c(_snowman.q())));
      _snowman.cJ().a(ayd.m, _snowman.map(var1x -> new ayf(var1x, this.b, 0)));
   }

   private void a(aqu var1) {
      Optional<dcn> _snowman = Optional.ofNullable(azj.b(_snowman, this.c, this.d));
      _snowman.cJ().a(ayd.m, _snowman.map(var1x -> new ayf(var1x, this.b, 0)));
   }
}
