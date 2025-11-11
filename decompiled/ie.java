import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ie implements hm {
   private static final Logger b = LogManager.getLogger();
   private static final Gson c = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private final hl d;
   private final List<Pair<Supplier<Consumer<BiConsumer<vk, cyy.a>>>, dba>> e = ImmutableList.of(
      Pair.of(ic::new, dbb.e), Pair.of(ia::new, dbb.b), Pair.of(ib::new, dbb.f), Pair.of(hz::new, dbb.l), Pair.of(ig::new, dbb.h), Pair.of(id::new, dbb.g)
   );

   public ie(hl var1) {
      this.d = _snowman;
   }

   @Override
   public void a(hn var1) {
      Path _snowman = this.d.b();
      Map<vk, cyy> _snowmanx = Maps.newHashMap();
      this.e.forEach(var1x -> ((Consumer)((Supplier)var1x.getFirst()).get()).accept((var2x, var3x) -> {
            if (_snowman.put(var2x, var3x.a((dba)var1x.getSecond()).b()) != null) {
               throw new IllegalStateException("Duplicate loot table " + var2x);
            }
         }));
      czg _snowmanxx = new czg(dbb.k, var0 -> null, _snowmanx::get);

      for (vk _snowmanxxx : Sets.difference(cyq.a(), _snowmanx.keySet())) {
         _snowmanxx.a("Missing built-in table: " + _snowmanxxx);
      }

      _snowmanx.forEach((var1x, var2x) -> cyz.a(_snowman, var1x, var2x));
      Multimap<String, String> _snowmanxxx = _snowmanxx.a();
      if (!_snowmanxxx.isEmpty()) {
         _snowmanxxx.forEach((var0, var1x) -> b.warn("Found validation problem in " + var0 + ": " + var1x));
         throw new IllegalStateException("Failed to validate loot tables, see logs");
      } else {
         _snowmanx.forEach((var2x, var3x) -> {
            Path _snowmanxxxx = a(_snowman, var2x);

            try {
               hm.a(c, _snowman, cyz.a(var3x), _snowmanxxxx);
            } catch (IOException var6) {
               b.error("Couldn't save loot table {}", _snowmanxxxx, var6);
            }
         });
      }
   }

   private static Path a(Path var0, vk var1) {
      return _snowman.resolve("data/" + _snowman.b() + "/loot_tables/" + _snowman.a() + ".json");
   }

   @Override
   public String a() {
      return "LootTables";
   }
}
