import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import java.util.UUID;

public class dli extends dkw {
   private static final vk a = new vk("textures/gui/bars.png");
   private final djz b;
   private final Map<UUID, dls> c = Maps.newLinkedHashMap();

   public dli(djz var1) {
      this.b = _snowman;
   }

   public void a(dfm var1) {
      if (!this.c.isEmpty()) {
         int _snowman = this.b.aD().o();
         int _snowmanx = 12;

         for (dls _snowmanxx : this.c.values()) {
            int _snowmanxxx = _snowman / 2 - 91;
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.b.M().a(a);
            this.a(_snowman, _snowmanxxx, _snowmanx, _snowmanxx);
            nr _snowmanxxxx = _snowmanxx.j();
            int _snowmanxxxxx = this.b.g.a(_snowmanxxxx);
            int _snowmanxxxxxx = _snowman / 2 - _snowmanxxxxx / 2;
            int _snowmanxxxxxxx = _snowmanx - 9;
            this.b.g.a(_snowman, _snowmanxxxx, (float)_snowmanxxxxxx, (float)_snowmanxxxxxxx, 16777215);
            _snowmanx += 10 + 9;
            if (_snowmanx >= this.b.aD().p() / 3) {
               break;
            }
         }
      }
   }

   private void a(dfm var1, int var2, int var3, aok var4) {
      this.b(_snowman, _snowman, _snowman, 0, _snowman.l().ordinal() * 5 * 2, 182, 5);
      if (_snowman.m() != aok.b.a) {
         this.b(_snowman, _snowman, _snowman, 0, 80 + (_snowman.m().ordinal() - 1) * 5 * 2, 182, 5);
      }

      int _snowman = (int)(_snowman.k() * 183.0F);
      if (_snowman > 0) {
         this.b(_snowman, _snowman, _snowman, 0, _snowman.l().ordinal() * 5 * 2 + 5, _snowman, 5);
         if (_snowman.m() != aok.b.a) {
            this.b(_snowman, _snowman, _snowman, 0, 80 + (_snowman.m().ordinal() - 1) * 5 * 2 + 5, _snowman, 5);
         }
      }
   }

   public void a(oz var1) {
      if (_snowman.c() == oz.a.a) {
         this.c.put(_snowman.b(), new dls(_snowman));
      } else if (_snowman.c() == oz.a.b) {
         this.c.remove(_snowman.b());
      } else {
         this.c.get(_snowman.b()).a(_snowman);
      }
   }

   public void a() {
      this.c.clear();
   }

   public boolean c() {
      if (!this.c.isEmpty()) {
         for (aok _snowman : this.c.values()) {
            if (_snowman.o()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean d() {
      if (!this.c.isEmpty()) {
         for (aok _snowman : this.c.values()) {
            if (_snowman.n()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean e() {
      if (!this.c.isEmpty()) {
         for (aok _snowman : this.c.values()) {
            if (_snowman.p()) {
               return true;
            }
         }
      }

      return false;
   }
}
