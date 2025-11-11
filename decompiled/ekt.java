import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class ekt extends ack<List<String>> {
   private static final vk a = new vk("texts/splashes.txt");
   private static final Random b = new Random();
   private final List<String> c = Lists.newArrayList();
   private final dkm d;

   public ekt(dkm var1) {
      this.d = _snowman;
   }

   protected List<String> a(ach var1, anw var2) {
      try (
         acg _snowman = djz.C().N().a(a);
         BufferedReader _snowmanx = new BufferedReader(new InputStreamReader(_snowman.b(), StandardCharsets.UTF_8));
      ) {
         return _snowmanx.lines().map(String::trim).filter(var0 -> var0.hashCode() != 125780783).collect(Collectors.toList());
      } catch (IOException var36) {
         return Collections.emptyList();
      }
   }

   protected void a(List<String> var1, ach var2, anw var3) {
      this.c.clear();
      this.c.addAll(_snowman);
   }

   @Nullable
   public String a() {
      Calendar _snowman = Calendar.getInstance();
      _snowman.setTime(new Date());
      if (_snowman.get(2) + 1 == 12 && _snowman.get(5) == 24) {
         return "Merry X-mas!";
      } else if (_snowman.get(2) + 1 == 1 && _snowman.get(5) == 1) {
         return "Happy new year!";
      } else if (_snowman.get(2) + 1 == 10 && _snowman.get(5) == 31) {
         return "OOoooOOOoooo! Spooky!";
      } else if (this.c.isEmpty()) {
         return null;
      } else {
         return this.d != null && b.nextInt(this.c.size()) == 42 ? this.d.c().toUpperCase(Locale.ROOT) + " IS YOU" : this.c.get(b.nextInt(this.c.size()));
      }
   }
}
