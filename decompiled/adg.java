import java.util.UUID;
import net.minecraft.server.MinecraftServer;

public class adg implements da {
   private static final oe b = new oe("Rcon");
   private final StringBuffer c = new StringBuffer();
   private final MinecraftServer d;

   public adg(MinecraftServer var1) {
      this.d = _snowman;
   }

   public void d() {
      this.c.setLength(0);
   }

   public String e() {
      return this.c.toString();
   }

   public db f() {
      aag _snowman = this.d.E();
      return new db(this, dcn.b(_snowman.u()), dcm.a, _snowman, 4, "Rcon", b, this.d, null);
   }

   @Override
   public void a(nr var1, UUID var2) {
      this.c.append(_snowman.getString());
   }

   @Override
   public boolean a() {
      return true;
   }

   @Override
   public boolean b() {
      return true;
   }

   @Override
   public boolean R_() {
      return this.d.i();
   }
}
