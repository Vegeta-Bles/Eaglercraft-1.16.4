import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class djo {
   private final List<nu> a = Lists.newArrayList();

   public djo() {
   }

   public void a(nu var1) {
      this.a.add(_snowman);
   }

   @Nullable
   public nu a() {
      if (this.a.isEmpty()) {
         return null;
      } else {
         return this.a.size() == 1 ? this.a.get(0) : nu.a(this.a);
      }
   }

   public nu b() {
      nu _snowman = this.a();
      return _snowman != null ? _snowman : nu.c;
   }
}
