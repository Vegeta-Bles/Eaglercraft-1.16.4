import java.util.Collection;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class la {
   private final String a;
   private final Collection<lu> b;
   @Nullable
   private final Consumer<aag> c;

   public la(String var1, Collection<lu> var2, @Nullable Consumer<aag> var3) {
      if (_snowman.isEmpty()) {
         throw new IllegalArgumentException("A GameTestBatch must include at least one TestFunction!");
      } else {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }
   }

   public String a() {
      return this.a;
   }

   public Collection<lu> b() {
      return this.b;
   }

   public void a(aag var1) {
      if (this.c != null) {
         this.c.accept(_snowman);
      }
   }
}
