import com.google.gson.JsonObject;
import java.io.File;
import java.net.SocketAddress;

public class acr extends acy<String, acs> {
   public acr(File var1) {
      super(_snowman);
   }

   @Override
   protected acx<String> a(JsonObject var1) {
      return new acs(_snowman);
   }

   public boolean a(SocketAddress var1) {
      String _snowman = this.c(_snowman);
      return this.d(_snowman);
   }

   public boolean a(String var1) {
      return this.d(_snowman);
   }

   public acs b(SocketAddress var1) {
      String _snowman = this.c(_snowman);
      return this.b(_snowman);
   }

   private String c(SocketAddress var1) {
      String _snowman = _snowman.toString();
      if (_snowman.contains("/")) {
         _snowman = _snowman.substring(_snowman.indexOf(47) + 1);
      }

      if (_snowman.contains(":")) {
         _snowman = _snowman.substring(0, _snowman.indexOf(58));
      }

      return _snowman;
   }
}
