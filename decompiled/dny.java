import com.mojang.blaze3d.systems.RenderSystem;

public class dny extends dot {
   private static final vk a = new vk("textures/gui/demo_background.png");
   private dlu b = dlu.a;
   private dlu c = dlu.a;

   public dny() {
      super(new of("demo.help.title"));
   }

   @Override
   protected void b() {
      int _snowman = -16;
      this.a((dlj)(new dlj(this.k / 2 - 116, this.l / 2 + 62 + -16, 114, 20, new of("demo.help.buy"), var0 -> {
         var0.o = false;
         x.i().a("http://www.minecraft.net/store?source=demo");
      })));
      this.a((dlj)(new dlj(this.k / 2 + 2, this.l / 2 + 62 + -16, 114, 20, new of("demo.help.later"), var1x -> {
         this.i.a(null);
         this.i.l.i();
      })));
      dkd _snowmanx = this.i.k;
      this.b = dlu.a(
         this.o,
         new of("demo.help.movementShort", _snowmanx.af.j(), _snowmanx.ag.j(), _snowmanx.ah.j(), _snowmanx.ai.j()),
         new of("demo.help.movementMouse"),
         new of("demo.help.jump", _snowmanx.aj.j()),
         new of("demo.help.inventory", _snowmanx.am.j())
      );
      this.c = dlu.a(this.o, new of("demo.help.fullWrapped"), 218);
   }

   @Override
   public void a(dfm var1) {
      super.a(_snowman);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.i.M().a(a);
      int _snowman = (this.k - 248) / 2;
      int _snowmanx = (this.l - 166) / 2;
      this.b(_snowman, _snowman, _snowmanx, 0, 0, 248, 166);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      int _snowman = (this.k - 248) / 2 + 10;
      int _snowmanx = (this.l - 166) / 2 + 8;
      this.o.b(_snowman, this.d, (float)_snowman, (float)_snowmanx, 2039583);
      _snowmanx = this.b.c(_snowman, _snowman, _snowmanx + 12, 12, 5197647);
      this.c.c(_snowman, _snowman, _snowmanx + 20, 9, 2039583);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
