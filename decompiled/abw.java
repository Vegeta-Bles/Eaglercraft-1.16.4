import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class abw implements AutoCloseable {
   private final Set<aby> a;
   private Map<String, abu> b = ImmutableMap.of();
   private List<abu> c = ImmutableList.of();
   private final abu.a d;

   public abw(abu.a var1, aby... var2) {
      this.d = _snowman;
      this.a = ImmutableSet.copyOf(_snowman);
   }

   public abw(aby... var1) {
      this(abu::new, _snowman);
   }

   public void a() {
      List<String> _snowman = this.c.stream().map(abu::e).collect(ImmutableList.toImmutableList());
      this.close();
      this.b = this.g();
      this.c = this.b(_snowman);
   }

   private Map<String, abu> g() {
      Map<String, abu> _snowman = Maps.newTreeMap();

      for (aby _snowmanx : this.a) {
         _snowmanx.a(var1x -> {
            abu var10000 = _snowman.put(var1x.e(), var1x);
         }, this.d);
      }

      return ImmutableMap.copyOf(_snowman);
   }

   public void a(Collection<String> var1) {
      this.c = this.b(_snowman);
   }

   private List<abu> b(Collection<String> var1) {
      List<abu> _snowman = this.c(_snowman).collect(Collectors.toList());

      for (abu _snowmanx : this.b.values()) {
         if (_snowmanx.f() && !_snowman.contains(_snowmanx)) {
            _snowmanx.h().a(_snowman, _snowmanx, Functions.identity(), false);
         }
      }

      return ImmutableList.copyOf(_snowman);
   }

   private Stream<abu> c(Collection<String> var1) {
      return _snowman.stream().map(this.b::get).filter(Objects::nonNull);
   }

   public Collection<String> b() {
      return this.b.keySet();
   }

   public Collection<abu> c() {
      return this.b.values();
   }

   public Collection<String> d() {
      return this.c.stream().map(abu::e).collect(ImmutableSet.toImmutableSet());
   }

   public Collection<abu> e() {
      return this.c;
   }

   @Nullable
   public abu a(String var1) {
      return this.b.get(_snowman);
   }

   @Override
   public void close() {
      this.b.values().forEach(abu::close);
   }

   public boolean b(String var1) {
      return this.b.containsKey(_snowman);
   }

   public List<abj> f() {
      return this.c.stream().map(abu::d).collect(ImmutableList.toImmutableList());
   }
}
