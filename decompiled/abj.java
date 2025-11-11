import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public interface abj extends AutoCloseable {
   InputStream b(String var1) throws IOException;

   InputStream a(abk var1, vk var2) throws IOException;

   Collection<vk> a(abk var1, String var2, String var3, int var4, Predicate<String> var5);

   boolean b(abk var1, vk var2);

   Set<String> a(abk var1);

   @Nullable
   <T> T a(abn<T> var1) throws IOException;

   String a();

   @Override
   void close();
}
