import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface ach {
   Set<String> a();

   acg a(vk var1) throws IOException;

   boolean b(vk var1);

   List<acg> c(vk var1) throws IOException;

   Collection<vk> a(String var1, Predicate<String> var2);

   Stream<abj> b();

   public static enum a implements ach {
      a;

      private a() {
      }

      @Override
      public Set<String> a() {
         return ImmutableSet.of();
      }

      @Override
      public acg a(vk var1) throws IOException {
         throw new FileNotFoundException(_snowman.toString());
      }

      @Override
      public boolean b(vk var1) {
         return false;
      }

      @Override
      public List<acg> c(vk var1) {
         return ImmutableList.of();
      }

      @Override
      public Collection<vk> a(String var1, Predicate<String> var2) {
         return ImmutableSet.of();
      }

      @Override
      public Stream<abj> b() {
         return Stream.of();
      }
   }
}
