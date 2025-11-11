import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class iq {
   private static final iq a = new iq(ImmutableList.of());
   private static final Comparator<cfj.a<?>> b = Comparator.comparing(var0 -> var0.a().f());
   private final List<cfj.a<?>> c;

   public iq a(cfj.a<?> var1) {
      return new iq(ImmutableList.builder().addAll(this.c).add(_snowman).build());
   }

   public iq a(iq var1) {
      return new iq(ImmutableList.builder().addAll(this.c).addAll(_snowman.c).build());
   }

   private iq(List<cfj.a<?>> var1) {
      this.c = _snowman;
   }

   public static iq a() {
      return a;
   }

   public static iq a(cfj.a<?>... var0) {
      return new iq(ImmutableList.copyOf(_snowman));
   }

   @Override
   public boolean equals(Object var1) {
      return this == _snowman || _snowman instanceof iq && this.c.equals(((iq)_snowman).c);
   }

   @Override
   public int hashCode() {
      return this.c.hashCode();
   }

   public String b() {
      return this.c.stream().sorted(b).map(cfj.a::toString).collect(Collectors.joining(","));
   }

   @Override
   public String toString() {
      return this.b();
   }
}
