import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import javax.annotation.Nullable;

public class dyh {
   @Nullable
   private final List<vk> a;

   private dyh(@Nullable List<vk> var1) {
      this.a = _snowman;
   }

   @Nullable
   public List<vk> a() {
      return this.a;
   }

   public static dyh a(JsonObject var0) {
      JsonArray _snowman = afd.a(_snowman, "textures", null);
      List<vk> _snowmanx;
      if (_snowman != null) {
         _snowmanx = Streams.stream(_snowman).map(var0x -> afd.a(var0x, "texture")).map(vk::new).collect(ImmutableList.toImmutableList());
      } else {
         _snowmanx = null;
      }

      return new dyh(_snowmanx);
   }
}
