import com.mojang.blaze3d.systems.RenderSystem;

public class ejw implements AutoCloseable {
   public static final int a = a(0, 10);
   private final ejs b = new ejs(16, 16, false);

   public ejw() {
      det _snowman = this.b.e();

      for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
            if (_snowmanx < 8) {
               _snowman.a(_snowmanxx, _snowmanx, -1308622593);
            } else {
               int _snowmanxxx = (int)((1.0F - (float)_snowmanxx / 15.0F * 0.75F) * 255.0F);
               _snowman.a(_snowmanxx, _snowmanx, _snowmanxxx << 24 | 16777215);
            }
         }
      }

      RenderSystem.activeTexture(33985);
      this.b.d();
      RenderSystem.matrixMode(5890);
      RenderSystem.loadIdentity();
      float _snowmanx = 0.06666667F;
      RenderSystem.scalef(0.06666667F, 0.06666667F, 0.06666667F);
      RenderSystem.matrixMode(5888);
      this.b.d();
      _snowman.a(0, 0, 0, 0, 0, _snowman.a(), _snowman.b(), false, true, false, false);
      RenderSystem.activeTexture(33984);
   }

   @Override
   public void close() {
      this.b.close();
   }

   public void a() {
      RenderSystem.setupOverlayColor(this.b::b, 16);
   }

   public static int a(float var0) {
      return (int)(_snowman * 15.0F);
   }

   public static int a(boolean var0) {
      return _snowman ? 3 : 10;
   }

   public static int a(int var0, int var1) {
      return _snowman | _snowman << 16;
   }

   public static int a(float var0, boolean var1) {
      return a(a(_snowman), a(_snowman));
   }

   public void b() {
      RenderSystem.teardownOverlayColor();
   }
}
