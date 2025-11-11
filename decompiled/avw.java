import com.google.common.collect.Sets;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class avw {
   private static final Logger a = LogManager.getLogger();
   private static final axl b = new axl(Integer.MAX_VALUE, new avv() {
      @Override
      public boolean a() {
         return false;
      }
   }) {
      @Override
      public boolean g() {
         return false;
      }
   };
   private final Map<avv.a, axl> c = new EnumMap<>(avv.a.class);
   private final Set<axl> d = Sets.newLinkedHashSet();
   private final Supplier<anw> e;
   private final EnumSet<avv.a> f = EnumSet.noneOf(avv.a.class);
   private int g = 3;

   public avw(Supplier<anw> var1) {
      this.e = _snowman;
   }

   public void a(int var1, avv var2) {
      this.d.add(new axl(_snowman, _snowman));
   }

   public void a(avv var1) {
      this.d.stream().filter(var1x -> var1x.j() == _snowman).filter(axl::g).forEach(axl::d);
      this.d.removeIf(var1x -> var1x.j() == _snowman);
   }

   public void b() {
      anw _snowman = this.e.get();
      _snowman.a("goalCleanup");
      this.d().filter(var1x -> !var1x.g() || var1x.i().stream().anyMatch(this.f::contains) || !var1x.b()).forEach(avv::d);
      this.c.forEach((var1x, var2) -> {
         if (!var2.g()) {
            this.c.remove(var1x);
         }
      });
      _snowman.c();
      _snowman.a("goalUpdate");
      this.d
         .stream()
         .filter(var0 -> !var0.g())
         .filter(var1x -> var1x.i().stream().noneMatch(this.f::contains))
         .filter(var1x -> var1x.i().stream().allMatch(var2 -> this.c.getOrDefault(var2, b).a(var1x)))
         .filter(axl::a)
         .forEach(var1x -> {
            var1x.i().forEach(var2 -> {
               axl _snowmanx = this.c.getOrDefault(var2, b);
               _snowmanx.d();
               this.c.put(var2, var1x);
            });
            var1x.c();
         });
      _snowman.c();
      _snowman.a("goalTick");
      this.d().forEach(axl::e);
      _snowman.c();
   }

   public Stream<axl> d() {
      return this.d.stream().filter(axl::g);
   }

   public void a(avv.a var1) {
      this.f.add(_snowman);
   }

   public void b(avv.a var1) {
      this.f.remove(_snowman);
   }

   public void a(avv.a var1, boolean var2) {
      if (_snowman) {
         this.b(_snowman);
      } else {
         this.a(_snowman);
      }
   }
}
