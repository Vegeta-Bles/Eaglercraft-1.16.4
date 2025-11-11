import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class asf<E extends aqm> extends arv<E> {
   private final Set<ayd<?>> b;
   private final asf.a c;
   private final asf.b d;
   private final aup<arv<? super E>> e = new aup<>();

   public asf(Map<ayd<?>, aye> var1, Set<ayd<?>> var2, asf.a var3, asf.b var4, List<Pair<arv<? super E>, Integer>> var5) {
      super(_snowman);
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      _snowman.forEach(var1x -> this.e.a((arv<? super E>)var1x.getFirst(), (Integer)var1x.getSecond()));
   }

   @Override
   protected boolean b(aag var1, E var2, long var3) {
      return this.e.c().filter(var0 -> var0.a() == arv.a.b).anyMatch(var4 -> var4.b(_snowman, _snowman, _snowman));
   }

   @Override
   protected boolean a(long var1) {
      return false;
   }

   @Override
   protected void a(aag var1, E var2, long var3) {
      this.c.a(this.e);
      this.d.a(this.e, _snowman, _snowman, _snowman);
   }

   @Override
   protected void d(aag var1, E var2, long var3) {
      this.e.c().filter(var0 -> var0.a() == arv.a.b).forEach(var4 -> var4.f(_snowman, _snowman, _snowman));
   }

   @Override
   protected void c(aag var1, E var2, long var3) {
      this.e.c().filter(var0 -> var0.a() == arv.a.b).forEach(var4 -> var4.g(_snowman, _snowman, _snowman));
      this.b.forEach(_snowman.cJ()::b);
   }

   @Override
   public String toString() {
      Set<? extends arv<? super E>> _snowman = this.e.c().filter(var0 -> var0.a() == arv.a.b).collect(Collectors.toSet());
      return "(" + this.getClass().getSimpleName() + "): " + _snowman;
   }

   static enum a {
      a(var0 -> {
      }),
      b(aup::a);

      private final Consumer<aup<?>> c;

      private a(Consumer<aup<?>> var3) {
         this.c = _snowman;
      }

      public void a(aup<?> var1) {
         this.c.accept(_snowman);
      }
   }

   static enum b {
      a {
         @Override
         public <E extends aqm> void a(aup<arv<? super E>> var1, aag var2, E var3, long var4) {
            _snowman.c().filter(var0 -> var0.a() == arv.a.a).filter(var4x -> var4x.e(_snowman, _snowman, _snowman)).findFirst();
         }
      },
      b {
         @Override
         public <E extends aqm> void a(aup<arv<? super E>> var1, aag var2, E var3, long var4) {
            _snowman.c().filter(var0 -> var0.a() == arv.a.a).forEach(var4x -> var4x.e(_snowman, _snowman, _snowman));
         }
      };

      private b() {
      }

      public abstract <E extends aqm> void a(aup<arv<? super E>> var1, aag var2, E var3, long var4);
   }
}
