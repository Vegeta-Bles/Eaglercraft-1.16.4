import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class aej<T> {
   private aem<T> a = aem.c();
   private final List<aej.a<T>> b = Lists.newArrayList();
   private final Function<aen, aem<T>> c;

   public aej(Function<aen, aem<T>> var1) {
      this.c = _snowman;
   }

   public ael.e<T> a(String var1) {
      aej.a<T> _snowman = new aej.a<>(new vk(_snowman));
      this.b.add(_snowman);
      return _snowman;
   }

   public void a() {
      this.a = aem.c();
      ael<T> _snowman = aei.a();
      this.b.forEach(var1x -> var1x.a(var1xx -> _snowman));
   }

   public void a(aen var1) {
      aem<T> _snowman = this.c.apply(_snowman);
      this.a = _snowman;
      this.b.forEach(var1x -> var1x.a(_snowman::a));
   }

   public aem<T> b() {
      return this.a;
   }

   public List<? extends ael.e<T>> c() {
      return this.b;
   }

   public Set<vk> b(aen var1) {
      aem<T> _snowman = this.c.apply(_snowman);
      Set<vk> _snowmanx = this.b.stream().map(aej.a::a).collect(Collectors.toSet());
      ImmutableSet<vk> _snowmanxx = ImmutableSet.copyOf(_snowman.b());
      return Sets.difference(_snowmanx, _snowmanxx);
   }

   static class a<T> implements ael.e<T> {
      @Nullable
      private ael<T> b;
      protected final vk a;

      private a(vk var1) {
         this.a = _snowman;
      }

      @Override
      public vk a() {
         return this.a;
      }

      private ael<T> c() {
         if (this.b == null) {
            throw new IllegalStateException("Tag " + this.a + " used before it was bound");
         } else {
            return this.b;
         }
      }

      void a(Function<vk, ael<T>> var1) {
         this.b = _snowman.apply(this.a);
      }

      @Override
      public boolean a(T var1) {
         return this.c().a(_snowman);
      }

      @Override
      public List<T> b() {
         return this.c().b();
      }
   }
}
