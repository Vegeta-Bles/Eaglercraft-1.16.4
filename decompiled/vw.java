import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class vw implements acc {
   private static final Logger a = LogManager.getLogger();
   private static final int b = "functions/".length();
   private static final int c = ".mcfunction".length();
   private volatile Map<vk, cy> d = ImmutableMap.of();
   private final aeo<cy> e = new aeo<>(this::a, "tags/functions", "function");
   private volatile aem<cy> f = aem.c();
   private final int g;
   private final CommandDispatcher<db> h;

   public Optional<cy> a(vk var1) {
      return Optional.ofNullable(this.d.get(_snowman));
   }

   public Map<vk, cy> a() {
      return this.d;
   }

   public aem<cy> b() {
      return this.f;
   }

   public ael<cy> b(vk var1) {
      return this.f.b(_snowman);
   }

   public vw(int var1, CommandDispatcher<db> var2) {
      this.g = _snowman;
      this.h = _snowman;
   }

   @Override
   public CompletableFuture<Void> a(acc.a var1, ach var2, anw var3, anw var4, Executor var5, Executor var6) {
      CompletableFuture<Map<vk, ael.a>> _snowman = this.e.a(_snowman, _snowman);
      CompletableFuture<Map<vk, CompletableFuture<cy>>> _snowmanx = CompletableFuture.<Collection<vk>>supplyAsync(
            () -> _snowman.a("functions", var0x -> var0x.endsWith(".mcfunction")), _snowman
         )
         .thenCompose(var3x -> {
            Map<vk, CompletableFuture<cy>> _snowmanxx = Maps.newHashMap();
            db _snowmanx = new db(da.a_, dcn.a, dcm.a, null, this.g, "", oe.d, null, null);

            for (vk _snowmanxx : var3x) {
               String _snowmanxxx = _snowmanxx.a();
               vk _snowmanxxxx = new vk(_snowmanxx.b(), _snowmanxxx.substring(b, _snowmanxxx.length() - c));
               _snowmanxx.put(_snowmanxxxx, CompletableFuture.supplyAsync(() -> {
                  List<String> _snowmanxxxxx = a(_snowman, _snowman);
                  return cy.a(_snowman, this.h, _snowman, _snowmanxxxxx);
               }, _snowman));
            }

            CompletableFuture<?>[] _snowmanxx = _snowmanxx.values().toArray(new CompletableFuture[0]);
            return CompletableFuture.allOf(_snowmanxx).handle((var1x, var2x) -> _snowman);
         });
      return _snowman.thenCombine(_snowmanx, Pair::of).thenCompose(_snowman::a).thenAcceptAsync(var1x -> {
         Map<vk, CompletableFuture<cy>> _snowmanxx = (Map<vk, CompletableFuture<cy>>)var1x.getSecond();
         Builder<vk, cy> _snowmanx = ImmutableMap.builder();
         _snowmanxx.forEach((var1xx, var2x) -> var2x.handle((var2xx, var3x) -> {
               if (var3x != null) {
                  a.error("Failed to load function {}", var1xx, var3x);
               } else {
                  _snowman.put(var1xx, var2xx);
               }

               return null;
            }).join());
         this.d = _snowmanx.build();
         this.f = this.e.a((Map<vk, ael.a>)var1x.getFirst());
      }, _snowman);
   }

   private static List<String> a(ach var0, vk var1) {
      try (acg _snowman = _snowman.a(_snowman)) {
         return IOUtils.readLines(_snowman.b(), StandardCharsets.UTF_8);
      } catch (IOException var16) {
         throw new CompletionException(var16);
      }
   }
}
