import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

public class aep implements acc {
   private final aeo<buo> a = new aeo<>(gm.Q::b, "tags/blocks", "block");
   private final aeo<blx> b = new aeo<>(gm.T::b, "tags/items", "item");
   private final aeo<cuw> c = new aeo<>(gm.O::b, "tags/fluids", "fluid");
   private final aeo<aqe<?>> d = new aeo<>(gm.S::b, "tags/entity_types", "entity_type");
   private aen e = aen.a;

   public aep() {
   }

   public aen a() {
      return this.e;
   }

   @Override
   public CompletableFuture<Void> a(acc.a var1, ach var2, anw var3, anw var4, Executor var5, Executor var6) {
      CompletableFuture<Map<vk, ael.a>> _snowman = this.a.a(_snowman, _snowman);
      CompletableFuture<Map<vk, ael.a>> _snowmanx = this.b.a(_snowman, _snowman);
      CompletableFuture<Map<vk, ael.a>> _snowmanxx = this.c.a(_snowman, _snowman);
      CompletableFuture<Map<vk, ael.a>> _snowmanxxx = this.d.a(_snowman, _snowman);
      return CompletableFuture.allOf(_snowman, _snowmanx, _snowmanxx, _snowmanxxx)
         .thenCompose(_snowman::a)
         .thenAcceptAsync(
            var5x -> {
               aem<buo> _snowmanxxxx = this.a.a(_snowman.join());
               aem<blx> _snowmanx = this.b.a(_snowman.join());
               aem<cuw> _snowmanxx = this.c.a(_snowman.join());
               aem<aqe<?>> _snowmanxxx = this.d.a(_snowman.join());
               aen _snowmanxxxx = aen.a(_snowmanxxxx, _snowmanx, _snowmanxx, _snowmanxxx);
               Multimap<vk, vk> _snowmanxxxxx = aek.b(_snowmanxxxx);
               if (!_snowmanxxxxx.isEmpty()) {
                  throw new IllegalStateException(
                     "Missing required tags: "
                        + _snowmanxxxxx.entries().stream().map(var0 -> var0.getKey() + ":" + var0.getValue()).sorted().collect(Collectors.joining(","))
                  );
               } else {
                  aeh.a(_snowmanxxxx);
                  this.e = _snowmanxxxx;
               }
            },
            _snowman
         );
   }
}
