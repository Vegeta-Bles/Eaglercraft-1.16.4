import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class czg {
   private final Multimap<String, String> a;
   private final Supplier<String> b;
   private final dba c;
   private final Function<vk, dbo> d;
   private final Set<vk> e;
   private final Function<vk, cyy> f;
   private final Set<vk> g;
   private String h;

   public czg(dba var1, Function<vk, dbo> var2, Function<vk, cyy> var3) {
      this(HashMultimap.create(), () -> "", _snowman, _snowman, ImmutableSet.of(), _snowman, ImmutableSet.of());
   }

   public czg(Multimap<String, String> var1, Supplier<String> var2, dba var3, Function<vk, dbo> var4, Set<vk> var5, Function<vk, cyy> var6, Set<vk> var7) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
   }

   private String b() {
      if (this.h == null) {
         this.h = this.b.get();
      }

      return this.h;
   }

   public void a(String var1) {
      this.a.put(this.b(), _snowman);
   }

   public czg b(String var1) {
      return new czg(this.a, () -> this.b() + _snowman, this.c, this.d, this.e, this.f, this.g);
   }

   public czg a(String var1, vk var2) {
      ImmutableSet<vk> _snowman = ImmutableSet.builder().addAll(this.g).add(_snowman).build();
      return new czg(this.a, () -> this.b() + _snowman, this.c, this.d, this.e, this.f, _snowman);
   }

   public czg b(String var1, vk var2) {
      ImmutableSet<vk> _snowman = ImmutableSet.builder().addAll(this.e).add(_snowman).build();
      return new czg(this.a, () -> this.b() + _snowman, this.c, this.d, _snowman, this.f, this.g);
   }

   public boolean a(vk var1) {
      return this.g.contains(_snowman);
   }

   public boolean b(vk var1) {
      return this.e.contains(_snowman);
   }

   public Multimap<String, String> a() {
      return ImmutableMultimap.copyOf(this.a);
   }

   public void a(cyw var1) {
      this.c.a(this, _snowman);
   }

   @Nullable
   public cyy c(vk var1) {
      return this.f.apply(_snowman);
   }

   @Nullable
   public dbo d(vk var1) {
      return this.d.apply(_snowman);
   }

   public czg a(dba var1) {
      return new czg(this.a, this.b, _snowman, this.d, this.e, this.f, this.g);
   }
}
