import java.util.Random;
import javax.annotation.Nullable;

public class enp {
   private final Random a = new Random();
   private final djz b;
   @Nullable
   private emt c;
   private int d = 100;

   public enp(djz var1) {
      this.b = _snowman;
   }

   public void a() {
      adn _snowman = this.b.X();
      if (this.c != null) {
         if (!_snowman.a().a().equals(this.c.a()) && _snowman.d()) {
            this.b.W().b(this.c);
            this.d = afm.a(this.a, 0, _snowman.b() / 2);
         }

         if (!this.b.W().c(this.c)) {
            this.c = null;
            this.d = Math.min(this.d, afm.a(this.a, _snowman.b(), _snowman.c()));
         }
      }

      this.d = Math.min(this.d, _snowman.c());
      if (this.c == null && this.d-- <= 0) {
         this.a(_snowman);
      }
   }

   public void a(adn var1) {
      this.c = emp.a(_snowman.a());
      if (this.c.b() != enu.a) {
         this.b.W().a(this.c);
      }

      this.d = Integer.MAX_VALUE;
   }

   public void b() {
      if (this.c != null) {
         this.b.W().b(this.c);
         this.c = null;
      }

      this.d += 100;
   }

   public boolean b(adn var1) {
      return this.c == null ? false : _snowman.a().a().equals(this.c.a());
   }
}
