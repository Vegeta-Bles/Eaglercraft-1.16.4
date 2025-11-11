public class dnm extends dov {
   private static final dkc[] c = new dkc[]{dkc.A, dkc.R, dkc.t, dkc.D, dkc.d, dkc.g, dkc.h, dkc.E, dkc.T, dkc.U, dkc.k, dkc.j};

   public dnm(dot var1, dkd var2) {
      super(_snowman, _snowman, new of("options.accessibility.title"), c);
   }

   @Override
   protected void c() {
      this.a(new dlj(this.k / 2 - 155, this.l - 27, 150, 20, new of("options.accessibility.link"), var1 -> this.i.a(new dnr(var1x -> {
            if (var1x) {
               x.i().a("https://aka.ms/MinecraftJavaAccessibility");
            }

            this.i.a(this);
         }, "https://aka.ms/MinecraftJavaAccessibility", true))));
      this.a(new dlj(this.k / 2 + 5, this.l - 27, 150, 20, nq.c, var1 -> this.i.a(this.a)));
   }
}
