import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import javax.annotation.Nullable;

public class cha {
   private static final Int2ObjectMap<cha> d = new Int2ObjectOpenHashMap();
   public static final cha a = a(new cha(1, GZIPInputStream::new, GZIPOutputStream::new));
   public static final cha b = a(new cha(2, InflaterInputStream::new, DeflaterOutputStream::new));
   public static final cha c = a(new cha(3, var0 -> var0, var0 -> var0));
   private final int e;
   private final cha.a<InputStream> f;
   private final cha.a<OutputStream> g;

   private cha(int var1, cha.a<InputStream> var2, cha.a<OutputStream> var3) {
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
   }

   private static cha a(cha var0) {
      d.put(_snowman.e, _snowman);
      return _snowman;
   }

   @Nullable
   public static cha a(int var0) {
      return (cha)d.get(_snowman);
   }

   public static boolean b(int var0) {
      return d.containsKey(_snowman);
   }

   public int a() {
      return this.e;
   }

   public OutputStream a(OutputStream var1) throws IOException {
      return this.g.wrap(_snowman);
   }

   public InputStream a(InputStream var1) throws IOException {
      return this.f.wrap(_snowman);
   }

   @FunctionalInterface
   interface a<O> {
      O wrap(O var1) throws IOException;
   }
}
