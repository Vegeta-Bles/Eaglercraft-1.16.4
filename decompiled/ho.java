import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ho implements hm {
   private static final Logger b = LogManager.getLogger();
   private static final Gson c = new GsonBuilder().setPrettyPrinting().create();
   private final hl d;
   private final List<Consumer<Consumer<y>>> e = ImmutableList.of(new ht(), new hq(), new hp(), new hr(), new hs());

   public ho(hl var1) {
      this.d = _snowman;
   }

   @Override
   public void a(hn var1) throws IOException {
      Path _snowman = this.d.b();
      Set<vk> _snowmanx = Sets.newHashSet();
      Consumer<y> _snowmanxx = var3x -> {
         if (!_snowman.add(var3x.h())) {
            throw new IllegalStateException("Duplicate advancement " + var3x.h());
         } else {
            Path _snowmanxxx = a(_snowman, var3x);

            try {
               hm.a(c, _snowman, var3x.a().b(), _snowmanxxx);
            } catch (IOException var6x) {
               b.error("Couldn't save advancement {}", _snowmanxxx, var6x);
            }
         }
      };

      for (Consumer<Consumer<y>> _snowmanxxx : this.e) {
         _snowmanxxx.accept(_snowmanxx);
      }
   }

   private static Path a(Path var0, y var1) {
      return _snowman.resolve("data/" + _snowman.h().b() + "/advancements/" + _snowman.h().a() + ".json");
   }

   @Override
   public String a() {
      return "Advancements";
   }
}
