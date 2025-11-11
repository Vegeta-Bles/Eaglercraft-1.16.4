import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ks implements hm {
   private static final Logger b = LogManager.getLogger();
   private static final Gson c = new GsonBuilder().setPrettyPrinting().create();
   private final hl d;

   public ks(hl var1) {
      this.d = _snowman;
   }

   @Override
   public void a(hn var1) {
      Path _snowman = this.d.b();

      for (Entry<vj<bsv>, bsv> _snowmanx : hk.i.d()) {
         Path _snowmanxx = a(_snowman, _snowmanx.getKey().a());
         bsv _snowmanxxx = _snowmanx.getValue();
         Function<Supplier<bsv>, DataResult<JsonElement>> _snowmanxxxx = JsonOps.INSTANCE.withEncoder(bsv.d);

         try {
            Optional<JsonElement> _snowmanxxxxx = _snowmanxxxx.apply(() -> _snowman).result();
            if (_snowmanxxxxx.isPresent()) {
               hm.a(c, _snowman, _snowmanxxxxx.get(), _snowmanxx);
            } else {
               b.error("Couldn't serialize biome {}", _snowmanxx);
            }
         } catch (IOException var9) {
            b.error("Couldn't save biome {}", _snowmanxx, var9);
         }
      }
   }

   private static Path a(Path var0, vk var1) {
      return _snowman.resolve("reports/biomes/" + _snowman.a() + ".json");
   }

   @Override
   public String a() {
      return "Biomes";
   }
}
