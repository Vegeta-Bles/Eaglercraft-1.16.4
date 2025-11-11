import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;

public class drs extends dlh {
   private static final vk a = new vk("textures/gui/recipe_book.png");
   private static final nr b = new of("gui.recipebook.moreRecipes");
   private bjj<?> c;
   private adt d;
   private drt e;
   private float s;
   private float t;
   private int u;

   public drs() {
      super(0, 0, 25, 25, oe.d);
   }

   public void a(drt var1, drq var2) {
      this.e = _snowman;
      this.c = (bjj<?>)_snowman.d().s.bp;
      this.d = _snowman.e();
      List<boq<?>> _snowman = _snowman.a(this.d.a(this.c));

      for (boq<?> _snowmanx : _snowman) {
         if (this.d.d(_snowmanx)) {
            _snowman.a(_snowman);
            this.t = 15.0F;
            break;
         }
      }
   }

   public drt a() {
      return this.e;
   }

   public void a(int var1, int var2) {
      this.l = _snowman;
      this.m = _snowman;
   }

   @Override
   public void b(dfm var1, int var2, int var3, float var4) {
      if (!dot.x()) {
         this.s += _snowman;
      }

      djz _snowman = djz.C();
      _snowman.M().a(a);
      int _snowmanx = 29;
      if (!this.e.b()) {
         _snowmanx += 25;
      }

      int _snowmanxx = 206;
      if (this.e.a(this.d.a(this.c)).size() > 1) {
         _snowmanxx += 25;
      }

      boolean _snowmanxxx = this.t > 0.0F;
      if (_snowmanxxx) {
         float _snowmanxxxx = 1.0F + 0.1F * (float)Math.sin((double)(this.t / 15.0F * (float) Math.PI));
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)(this.l + 8), (float)(this.m + 12), 0.0F);
         RenderSystem.scalef(_snowmanxxxx, _snowmanxxxx, 1.0F);
         RenderSystem.translatef((float)(-(this.l + 8)), (float)(-(this.m + 12)), 0.0F);
         this.t -= _snowman;
      }

      this.b(_snowman, this.l, this.m, _snowmanx, _snowmanxx, this.j, this.k);
      List<boq<?>> _snowmanxxxx = this.k();
      this.u = afm.d(this.s / 30.0F) % _snowmanxxxx.size();
      bmb _snowmanxxxxx = _snowmanxxxx.get(this.u).c();
      int _snowmanxxxxxx = 4;
      if (this.e.e() && this.k().size() > 1) {
         _snowman.ad().b(_snowmanxxxxx, this.l + _snowmanxxxxxx + 1, this.m + _snowmanxxxxxx + 1);
         _snowmanxxxxxx--;
      }

      _snowman.ad().c(_snowmanxxxxx, this.l + _snowmanxxxxxx, this.m + _snowmanxxxxxx);
      if (_snowmanxxx) {
         RenderSystem.popMatrix();
      }
   }

   private List<boq<?>> k() {
      List<boq<?>> _snowman = this.e.b(true);
      if (!this.d.a(this.c)) {
         _snowman.addAll(this.e.b(false));
      }

      return _snowman;
   }

   public boolean b() {
      return this.k().size() == 1;
   }

   public boq<?> d() {
      List<boq<?>> _snowman = this.k();
      return _snowman.get(this.u);
   }

   public List<nr> a(dot var1) {
      bmb _snowman = this.k().get(this.u).c();
      List<nr> _snowmanx = Lists.newArrayList(_snowman.a(_snowman));
      if (this.e.a(this.d.a(this.c)).size() > 1) {
         _snowmanx.add(b);
      }

      return _snowmanx;
   }

   @Override
   public int h() {
      return 25;
   }

   @Override
   protected boolean a(int var1) {
      return _snowman == 0 || _snowman == 1;
   }
}
