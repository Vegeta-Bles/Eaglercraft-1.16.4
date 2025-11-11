import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class jw<T> implements hm {
   private static final Logger d = LogManager.getLogger();
   private static final Gson e = new GsonBuilder().setPrettyPrinting().create();
   protected final hl b;
   protected final gm<T> c;
   private final Map<vk, ael.a> f = Maps.newLinkedHashMap();

   protected jw(hl var1, gm<T> var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   protected abstract void b();

   @Override
   public void a(hn var1) {
      this.f.clear();
      this.b();
      ael<T> _snowman = aei.a();
      Function<vk, ael<T>> _snowmanx = var2x -> this.f.containsKey(var2x) ? _snowman : null;
      Function<vk, T> _snowmanxx = var1x -> this.c.b(var1x).orElse(null);
      this.f
         .forEach(
            (var4x, var5) -> {
               List<ael.b> _snowmanxxx = var5.b(_snowman, _snowman).collect(Collectors.toList());
               if (!_snowmanxxx.isEmpty()) {
                  throw new IllegalArgumentException(
                     String.format(
                        "Couldn't define tag %s as it is missing following references: %s",
                        var4x,
                        _snowmanxxx.stream().map(Objects::toString).collect(Collectors.joining(","))
                     )
                  );
               } else {
                  JsonObject _snowmanx = var5.c();
                  Path _snowmanxx = this.a(var4x);

                  try {
                     String _snowmanxxx = e.toJson(_snowmanx);
                     String _snowmanxxxx = a.hashUnencodedChars(_snowmanxxx).toString();
                     if (!Objects.equals(_snowman.a(_snowmanxx), _snowmanxxxx) || !Files.exists(_snowmanxx)) {
                        Files.createDirectories(_snowmanxx.getParent());

                        try (BufferedWriter _snowmanxxxxx = Files.newBufferedWriter(_snowmanxx)) {
                           _snowmanxxxxx.write(_snowmanxxx);
                        }
                     }

                     _snowman.a(_snowmanxx, _snowmanxxxx);
                  } catch (IOException var24) {
                     d.error("Couldn't save tags to {}", _snowmanxx, var24);
                  }
               }
            }
         );
   }

   protected abstract Path a(vk var1);

   protected jw.a<T> a(ael.e<T> var1) {
      ael.a _snowman = this.b(_snowman);
      return new jw.a<>(_snowman, this.c, "vanilla");
   }

   protected ael.a b(ael.e<T> var1) {
      return this.f.computeIfAbsent(_snowman.a(), var0 -> new ael.a());
   }

   public static class a<T> {
      private final ael.a a;
      private final gm<T> b;
      private final String c;

      private a(ael.a var1, gm<T> var2, String var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public jw.a<T> a(T var1) {
         this.a.a(this.b.b(_snowman), this.c);
         return this;
      }

      public jw.a<T> a(ael.e<T> var1) {
         this.a.c(_snowman.a(), this.c);
         return this;
      }

      @SafeVarargs
      public final jw.a<T> a(T... var1) {
         Stream.<T>of(_snowman).map(this.b::b).forEach(var1x -> this.a.a(var1x, this.c));
         return this;
      }
   }
}
