import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;

public class ccz extends ccj {
   private vk a = new vk("empty");
   private vk b = new vk("empty");
   private vk c = new vk("empty");
   private ccz.a g = ccz.a.a;
   private String h = "minecraft:air";

   public ccz(cck<?> var1) {
      super(_snowman);
   }

   public ccz() {
      this(cck.E);
   }

   public vk d() {
      return this.a;
   }

   public vk f() {
      return this.b;
   }

   public vk g() {
      return this.c;
   }

   public String h() {
      return this.h;
   }

   public ccz.a j() {
      return this.g;
   }

   public void a(vk var1) {
      this.a = _snowman;
   }

   public void b(vk var1) {
      this.b = _snowman;
   }

   public void c(vk var1) {
      this.c = _snowman;
   }

   public void a(String var1) {
      this.h = _snowman;
   }

   public void a(ccz.a var1) {
      this.g = _snowman;
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      _snowman.a("name", this.a.toString());
      _snowman.a("target", this.b.toString());
      _snowman.a("pool", this.c.toString());
      _snowman.a("final_state", this.h);
      _snowman.a("joint", this.g.a());
      return _snowman;
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.a = new vk(_snowman.l("name"));
      this.b = new vk(_snowman.l("target"));
      this.c = new vk(_snowman.l("pool"));
      this.h = _snowman.l("final_state");
      this.g = ccz.a.a(_snowman.l("joint")).orElseGet(() -> bxr.h(_snowman).n().d() ? ccz.a.b : ccz.a.a);
   }

   @Nullable
   @Override
   public ow a() {
      return new ow(this.e, 12, this.b());
   }

   @Override
   public md b() {
      return this.a(new md());
   }

   public void a(aag var1, int var2, boolean var3) {
      cfy _snowman = _snowman.i().g();
      csw _snowmanx = _snowman.n();
      bsn _snowmanxx = _snowman.a();
      Random _snowmanxxx = _snowman.u_();
      fx _snowmanxxxx = this.o();
      List<cro> _snowmanxxxxx = Lists.newArrayList();
      ctb _snowmanxxxxxx = new ctb();
      _snowmanxxxxxx.a(_snowman, _snowmanxxxx, new fx(1, 1, 1), false, null);
      coi _snowmanxxxxxxx = new coh(_snowmanxxxxxx);
      cro _snowmanxxxxxxxx = new cro(_snowmanx, _snowmanxxxxxxx, _snowmanxxxx, 1, bzm.a, new cra(_snowmanxxxx, _snowmanxxxx));
      coe.a(_snowman.r(), _snowmanxxxxxxxx, _snowman, cro::new, _snowman, _snowmanx, _snowmanxxxxx, _snowmanxxx);

      for (cro _snowmanxxxxxxxxx : _snowmanxxxxx) {
         _snowmanxxxxxxxxx.a(_snowman, _snowmanxx, _snowman, _snowmanxxx, cra.b(), _snowmanxxxx, _snowman);
      }
   }

   public static enum a implements afs {
      a("rollable"),
      b("aligned");

      private final String c;

      private a(String var3) {
         this.c = _snowman;
      }

      @Override
      public String a() {
         return this.c;
      }

      public static Optional<ccz.a> a(String var0) {
         return Arrays.stream(values()).filter(var1 -> var1.a().equals(_snowman)).findFirst();
      }
   }
}
