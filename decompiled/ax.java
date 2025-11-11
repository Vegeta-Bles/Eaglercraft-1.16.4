import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ax {
   private static final Logger a = LogManager.getLogger();
   private final vk b;
   private final cza c;
   private final Gson d = cys.a().create();

   public ax(vk var1, cza var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public final dbo[] a(JsonArray var1, String var2, dba var3) {
      dbo[] _snowman = (dbo[])this.d.fromJson(_snowman, dbo[].class);
      czg _snowmanx = new czg(_snowman, this.c::a, var0 -> null);

      for (dbo _snowmanxx : _snowman) {
         _snowmanxx.a(_snowmanx);
         _snowmanx.a().forEach((var1x, var2x) -> a.warn("Found validation problem in advancement trigger {}/{}: {}", _snowman, var1x, var2x));
      }

      return _snowman;
   }

   public vk a() {
      return this.b;
   }
}
