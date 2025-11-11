import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class oa extends nn implements nt {
   private static final Logger d = LogManager.getLogger();
   private final String e;
   @Nullable
   private final fc f;

   public oa(String var1) {
      this.e = _snowman;
      fc _snowman = null;

      try {
         fd _snowmanx = new fd(new StringReader(_snowman));
         _snowman = _snowmanx.t();
      } catch (CommandSyntaxException var4) {
         d.warn("Invalid selector component: {}", _snowman, var4.getMessage());
      }

      this.f = _snowman;
   }

   public String h() {
      return this.e;
   }

   @Override
   public nx a(@Nullable db var1, @Nullable aqa var2, int var3) throws CommandSyntaxException {
      return (nx)(_snowman != null && this.f != null ? fc.a(this.f.b(_snowman)) : new oe(""));
   }

   @Override
   public String a() {
      return this.e;
   }

   public oa j() {
      return new oa(this.e);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof oa)) {
         return false;
      } else {
         oa _snowman = (oa)_snowman;
         return this.e.equals(_snowman.e) && super.equals(_snowman);
      }
   }

   @Override
   public String toString() {
      return "SelectorComponent{pattern='" + this.e + '\'' + ", siblings=" + this.a + ", style=" + this.c() + '}';
   }
}
