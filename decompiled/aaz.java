import net.minecraft.server.MinecraftServer;

public class aaz implements tw {
   private static final nr a = new oe("Ignoring status request");
   private final MinecraftServer b;
   private final nd c;

   public aaz(MinecraftServer var1, nd var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void a(tv var1) {
      switch (_snowman.b()) {
         case d:
            this.c.a(ne.d);
            if (_snowman.c() != w.a().getProtocolVersion()) {
               nr _snowman;
               if (_snowman.c() < 754) {
                  _snowman = new of("multiplayer.disconnect.outdated_client", w.a().getName());
               } else {
                  _snowman = new of("multiplayer.disconnect.incompatible", w.a().getName());
               }

               this.c.a(new ud(_snowman));
               this.c.a(_snowman);
            } else {
               this.c.a(new aba(this.b, this.c));
            }
            break;
         case c:
            if (this.b.am()) {
               this.c.a(ne.c);
               this.c.a(new abb(this.b, this.c));
            } else {
               this.c.a(a);
            }
            break;
         default:
            throw new UnsupportedOperationException("Invalid intention " + _snowman.b());
      }
   }

   @Override
   public void a(nr var1) {
   }

   @Override
   public nd a() {
      return this.c;
   }
}
