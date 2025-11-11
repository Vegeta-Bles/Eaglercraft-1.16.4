import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ekz implements aci {
   private static final Logger a = LogManager.getLogger();
   private static final eky b = new eky("en_us", "US", "English", false);
   private Map<String, eky> c = ImmutableMap.of("en_us", b);
   private String d;
   private eky e = b;

   public ekz(String var1) {
      this.d = _snowman;
   }

   private static Map<String, eky> a(Stream<abj> var0) {
      Map<String, eky> _snowman = Maps.newHashMap();
      _snowman.forEach(var1x -> {
         try {
            elh _snowmanx = var1x.a(elh.a);
            if (_snowmanx != null) {
               for (eky _snowmanx : _snowmanx.a()) {
                  _snowman.putIfAbsent(_snowmanx.getCode(), _snowmanx);
               }
            }
         } catch (IOException | RuntimeException var5) {
            a.warn("Unable to parse language metadata section of resourcepack: {}", var1x.a(), var5);
         }
      });
      return ImmutableMap.copyOf(_snowman);
   }

   @Override
   public void a(ach var1) {
      this.c = a(_snowman.b());
      eky _snowman = this.c.getOrDefault("en_us", b);
      this.e = this.c.getOrDefault(this.d, _snowman);
      List<eky> _snowmanx = Lists.newArrayList(new eky[]{_snowman});
      if (this.e != _snowman) {
         _snowmanx.add(this.e);
      }

      ekv _snowmanxx = ekv.a(_snowman, _snowmanx);
      ekx.a(_snowmanxx);
      ly.a(_snowmanxx);
   }

   public void a(eky var1) {
      this.d = _snowman.getCode();
      this.e = _snowman;
   }

   public eky b() {
      return this.e;
   }

   public SortedSet<eky> d() {
      return Sets.newTreeSet(this.c.values());
   }

   public eky a(String var1) {
      return this.c.get(_snowman);
   }
}
