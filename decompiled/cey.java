import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Optional;

public class cey extends cfj<Boolean> {
   private final ImmutableSet<Boolean> a = ImmutableSet.of(true, false);

   protected cey(String var1) {
      super(_snowman, Boolean.class);
   }

   @Override
   public Collection<Boolean> a() {
      return this.a;
   }

   public static cey a(String var0) {
      return new cey(_snowman);
   }

   @Override
   public Optional<Boolean> b(String var1) {
      return !"true".equals(_snowman) && !"false".equals(_snowman) ? Optional.empty() : Optional.of(Boolean.valueOf(_snowman));
   }

   public String a(Boolean var1) {
      return _snowman.toString();
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman instanceof cey && super.equals(_snowman)) {
         cey _snowman = (cey)_snowman;
         return this.a.equals(_snowman.a);
      } else {
         return false;
      }
   }

   @Override
   public int b() {
      return 31 * super.b() + this.a.hashCode();
   }
}
