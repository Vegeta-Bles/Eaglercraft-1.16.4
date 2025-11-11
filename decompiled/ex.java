import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class ex implements Predicate<bmb> {
   private static final Dynamic2CommandExceptionType a = new Dynamic2CommandExceptionType((var0, var1) -> new of("arguments.item.overstacked", var0, var1));
   private final blx b;
   @Nullable
   private final md c;

   public ex(blx var1, @Nullable md var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public blx a() {
      return this.b;
   }

   public boolean a(bmb var1) {
      return _snowman.b() == this.b && mp.a(this.c, _snowman.o(), true);
   }

   public bmb a(int var1, boolean var2) throws CommandSyntaxException {
      bmb _snowman = new bmb(this.b, _snowman);
      if (this.c != null) {
         _snowman.c(this.c);
      }

      if (_snowman && _snowman > _snowman.c()) {
         throw a.create(gm.T.b(this.b), _snowman.c());
      } else {
         return _snowman;
      }
   }

   public String c() {
      StringBuilder _snowman = new StringBuilder(gm.T.a(this.b));
      if (this.c != null) {
         _snowman.append(this.c);
      }

      return _snowman.toString();
   }
}
