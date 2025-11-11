import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class elq implements elo {
   private final ebm a;
   private final ebk b;
   private final ekc c;
   private final boolean d;

   public elq(ebm var1, ebk var2, ekc var3, boolean var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   @Override
   public List<eba> a(@Nullable ceh var1, @Nullable gc var2, Random var3) {
      return Collections.emptyList();
   }

   @Override
   public boolean a() {
      return false;
   }

   @Override
   public boolean b() {
      return true;
   }

   @Override
   public boolean c() {
      return this.d;
   }

   @Override
   public boolean d() {
      return true;
   }

   @Override
   public ekc e() {
      return this.c;
   }

   @Override
   public ebm f() {
      return this.a;
   }

   @Override
   public ebk g() {
      return this.b;
   }
}
