import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Set;

public class dba {
   private final Set<daz<?>> a;
   private final Set<daz<?>> b;

   private dba(Set<daz<?>> var1, Set<daz<?>> var2) {
      this.a = ImmutableSet.copyOf(_snowman);
      this.b = ImmutableSet.copyOf(Sets.union(_snowman, _snowman));
   }

   public Set<daz<?>> a() {
      return this.a;
   }

   public Set<daz<?>> b() {
      return this.b;
   }

   @Override
   public String toString() {
      return "[" + Joiner.on(", ").join(this.b.stream().map(var1 -> (this.a.contains(var1) ? "!" : "") + var1.a()).iterator()) + "]";
   }

   public void a(czg var1, cyw var2) {
      Set<daz<?>> _snowman = _snowman.a();
      Set<daz<?>> _snowmanx = Sets.difference(_snowman, this.b);
      if (!_snowmanx.isEmpty()) {
         _snowman.a("Parameters " + _snowmanx + " are not provided in this context");
      }
   }

   public static class a {
      private final Set<daz<?>> a = Sets.newIdentityHashSet();
      private final Set<daz<?>> b = Sets.newIdentityHashSet();

      public a() {
      }

      public dba.a a(daz<?> var1) {
         if (this.b.contains(_snowman)) {
            throw new IllegalArgumentException("Parameter " + _snowman.a() + " is already optional");
         } else {
            this.a.add(_snowman);
            return this;
         }
      }

      public dba.a b(daz<?> var1) {
         if (this.a.contains(_snowman)) {
            throw new IllegalArgumentException("Parameter " + _snowman.a() + " is already required");
         } else {
            this.b.add(_snowman);
            return this;
         }
      }

      public dba a() {
         return new dba(this.a, this.b);
      }
   }
}
