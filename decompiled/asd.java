import java.util.List;
import java.util.Optional;

public class asd implements atb {
   private final aqa a;
   private final boolean b;

   public asd(aqa var1, boolean var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public dcn a() {
      return this.b ? this.a.cA().b(0.0, (double)this.a.ce(), 0.0) : this.a.cA();
   }

   @Override
   public fx b() {
      return this.a.cB();
   }

   @Override
   public boolean a(aqm var1) {
      if (!(this.a instanceof aqm)) {
         return true;
      } else {
         Optional<List<aqm>> _snowman = _snowman.cJ().c(ayd.h);
         return this.a.aX() && _snowman.isPresent() && _snowman.get().contains(this.a);
      }
   }

   @Override
   public String toString() {
      return "EntityTracker for " + this.a;
   }
}
