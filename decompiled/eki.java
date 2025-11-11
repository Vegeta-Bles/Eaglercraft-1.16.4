import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class eki extends abm {
   private final ekg d;

   public eki(ekg var1) {
      super("minecraft", "realms");
      this.d = _snowman;
   }

   @Nullable
   @Override
   protected InputStream c(abk var1, vk var2) {
      if (_snowman == abk.a) {
         File _snowman = this.d.a(_snowman);
         if (_snowman != null && _snowman.exists()) {
            try {
               return new FileInputStream(_snowman);
            } catch (FileNotFoundException var5) {
            }
         }
      }

      return super.c(_snowman, _snowman);
   }

   @Override
   public boolean b(abk var1, vk var2) {
      if (_snowman == abk.a) {
         File _snowman = this.d.a(_snowman);
         if (_snowman != null && _snowman.exists()) {
            return true;
         }
      }

      return super.b(_snowman, _snowman);
   }

   @Nullable
   @Override
   protected InputStream a(String var1) {
      File _snowman = this.d.a(_snowman);
      if (_snowman != null && _snowman.exists()) {
         try {
            return new FileInputStream(_snowman);
         } catch (FileNotFoundException var4) {
         }
      }

      return super.a(_snowman);
   }

   @Override
   public Collection<vk> a(abk var1, String var2, String var3, int var4, Predicate<String> var5) {
      Collection<vk> _snowman = super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.addAll(this.d.a(_snowman, _snowman, _snowman, _snowman));
      return _snowman;
   }
}
