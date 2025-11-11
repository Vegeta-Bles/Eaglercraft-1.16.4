import com.mojang.blaze3d.systems.RenderSystem;

public interface dyk {
   dyk a = new dyk() {
      @Override
      public void a(dfh var1, ekd var2) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.depthMask(true);
         _snowman.a(ekb.d);
         _snowman.a(7, dfk.j);
      }

      @Override
      public void a(dfo var1) {
         _snowman.b();
      }

      @Override
      public String toString() {
         return "TERRAIN_SHEET";
      }
   };
   dyk b = new dyk() {
      @Override
      public void a(dfh var1, ekd var2) {
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
         _snowman.a(ekb.e);
         _snowman.a(7, dfk.j);
      }

      @Override
      public void a(dfo var1) {
         _snowman.b();
      }

      @Override
      public String toString() {
         return "PARTICLE_SHEET_OPAQUE";
      }
   };
   dyk c = new dyk() {
      @Override
      public void a(dfh var1, ekd var2) {
         RenderSystem.depthMask(true);
         _snowman.a(ekb.e);
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(dem.r.l, dem.j.j, dem.r.e, dem.j.j);
         RenderSystem.alphaFunc(516, 0.003921569F);
         _snowman.a(7, dfk.j);
      }

      @Override
      public void a(dfo var1) {
         _snowman.b();
      }

      @Override
      public String toString() {
         return "PARTICLE_SHEET_TRANSLUCENT";
      }
   };
   dyk d = new dyk() {
      @Override
      public void a(dfh var1, ekd var2) {
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
         _snowman.a(ekb.e);
         _snowman.a(7, dfk.j);
      }

      @Override
      public void a(dfo var1) {
         _snowman.b();
      }

      @Override
      public String toString() {
         return "PARTICLE_SHEET_LIT";
      }
   };
   dyk e = new dyk() {
      @Override
      public void a(dfh var1, ekd var2) {
         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
      }

      @Override
      public void a(dfo var1) {
      }

      @Override
      public String toString() {
         return "CUSTOM";
      }
   };
   dyk f = new dyk() {
      @Override
      public void a(dfh var1, ekd var2) {
      }

      @Override
      public void a(dfo var1) {
      }

      @Override
      public String toString() {
         return "NO_RENDER";
      }
   };

   void a(dfh var1, ekd var2);

   void a(dfo var1);
}
