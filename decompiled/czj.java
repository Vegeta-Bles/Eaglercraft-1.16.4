import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;

public abstract class czj extends czq {
   protected final czq[] c;
   private final czi e;

   protected czj(czq[] var1, dbo[] var2) {
      super(_snowman);
      this.c = _snowman;
      this.e = this.a(_snowman);
   }

   @Override
   public void a(czg var1) {
      super.a(_snowman);
      if (this.c.length == 0) {
         _snowman.a("Empty children list");
      }

      for (int _snowman = 0; _snowman < this.c.length; _snowman++) {
         this.c[_snowman].a(_snowman.b(".entry[" + _snowman + "]"));
      }
   }

   protected abstract czi a(czi[] var1);

   @Override
   public final boolean expand(cyv var1, Consumer<czp> var2) {
      return !this.a(_snowman) ? false : this.e.expand(_snowman, _snowman);
   }

   public static <T extends czj> czq.b<T> a(final czj.a<T> var0) {
      return new czq.b<T>() {
         public void a(JsonObject var1, T var2, JsonSerializationContext var3) {
            _snowman.add("children", _snowman.serialize(_snowman.c));
         }

         public final T a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
            czq[] _snowman = afd.a(_snowman, "children", _snowman, czq[].class);
            return _snowman.create(_snowman, _snowman);
         }
      };
   }

   @FunctionalInterface
   public interface a<T extends czj> {
      T create(czq[] var1, dbo[] var2);
   }
}
