import java.util.function.UnaryOperator;

public interface nx extends nr {
   nx a(ob var1);

   default nx c(String var1) {
      return this.a(new oe(_snowman));
   }

   nx a(nr var1);

   default nx a(UnaryOperator<ob> var1) {
      this.a(_snowman.apply(this.c()));
      return this;
   }

   default nx c(ob var1) {
      this.a(_snowman.a(this.c()));
      return this;
   }

   default nx a(k... var1) {
      this.a(this.c().a(_snowman));
      return this;
   }

   default nx a(k var1) {
      this.a(this.c().b(_snowman));
      return this;
   }
}
