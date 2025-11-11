import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import java.io.IOException;
import java.nio.file.Path;

public class hw implements hm {
   private static final Gson b = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private final hl c;

   public hw(hl var1) {
      this.c = _snowman;
   }

   @Override
   public void a(hn var1) throws IOException {
      Path _snowman = this.c.b().resolve("reports/commands.json");
      CommandDispatcher<db> _snowmanx = new dc(dc.a.a).a();
      hm.a(b, _snowman, fk.a(_snowmanx, _snowmanx.getRoot()), _snowman);
   }

   @Override
   public String a() {
      return "Command Syntax";
   }
}
