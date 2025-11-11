import com.mojang.text2speech.Narrator;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dkz implements dky {
   public static final nr a = oe.d;
   private static final Logger c = LogManager.getLogger();
   public static final dkz b = new dkz();
   private final Narrator d = Narrator.getNarrator();

   public dkz() {
   }

   @Override
   public void a(no var1, nr var2, UUID var3) {
      dkb _snowman = d();
      if (_snowman != dkb.a && this.d.active()) {
         if (_snowman == dkb.b || _snowman == dkb.c && _snowman == no.a || _snowman == dkb.d && _snowman == no.b) {
            nr _snowmanx;
            if (_snowman instanceof of && "chat.type.text".equals(((of)_snowman).i())) {
               _snowmanx = new of("chat.type.text.narrate", ((of)_snowman).j());
            } else {
               _snowmanx = _snowman;
            }

            this.a(_snowman.b(), _snowmanx.getString());
         }
      }
   }

   public void a(String var1) {
      dkb _snowman = d();
      if (this.d.active() && _snowman != dkb.a && _snowman != dkb.c && !_snowman.isEmpty()) {
         this.d.clear();
         this.a(true, _snowman);
      }
   }

   private static dkb d() {
      return djz.C().k.aU;
   }

   private void a(boolean var1, String var2) {
      if (w.d) {
         c.debug("Narrating: {}", _snowman.replaceAll("\n", "\\\\n"));
      }

      this.d.say(_snowman, _snowman);
   }

   public void a(dkb var1) {
      this.b();
      this.d.say(new of("options.narrator").c(" : ").a(_snowman.b()).getString(), true);
      dmr _snowman = djz.C().an();
      if (this.d.active()) {
         if (_snowman == dkb.a) {
            dmp.b(_snowman, dmp.a.b, new of("narrator.toast.disabled"), null);
         } else {
            dmp.b(_snowman, dmp.a.b, new of("narrator.toast.enabled"), _snowman.b());
         }
      } else {
         dmp.b(_snowman, dmp.a.b, new of("narrator.toast.disabled"), new of("options.narrator.notavailable"));
      }
   }

   public boolean a() {
      return this.d.active();
   }

   public void b() {
      if (d() != dkb.a && this.d.active()) {
         this.d.clear();
      }
   }

   public void c() {
      this.d.destroy();
   }
}
