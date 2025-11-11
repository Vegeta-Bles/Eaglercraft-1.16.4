public class dib extends eoo {
   private static final nr a = new of("mco.account.privacyinfo");
   private final dot b;
   private dlu c = dlu.a;

   public dib(dot var1) {
      this.b = _snowman;
   }

   @Override
   public void b() {
      eoj.a(a.getString());
      nr _snowman = new of("mco.account.update");
      nr _snowmanx = nq.h;
      int _snowmanxx = Math.max(this.o.a(_snowman), this.o.a(_snowmanx)) + 30;
      nr _snowmanxxx = new of("mco.account.privacy.info");
      int _snowmanxxxx = (int)((double)this.o.a(_snowmanxxx) * 1.2);
      this.a(new dlj(this.k / 2 - _snowmanxxxx / 2, j(11), _snowmanxxxx, 20, _snowmanxxx, var0 -> x.i().a("https://aka.ms/MinecraftGDPR")));
      this.a(new dlj(this.k / 2 - (_snowmanxx + 5), j(13), _snowmanxx, 20, _snowman, var0 -> x.i().a("https://aka.ms/UpdateMojangAccount")));
      this.a(new dlj(this.k / 2 + 5, j(13), _snowmanxx, 20, _snowmanx, var1x -> this.i.a(this.b)));
      this.c = dlu.a(this.o, a, (int)Math.round((double)this.k * 0.9));
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.c.a(_snowman, this.k / 2, 15, 15, 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
