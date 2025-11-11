public class dou extends dot {
   private static final nr a = new of("selectWorld.allowCommands");
   private static final nr b = new of("selectWorld.gameMode");
   private static final nr c = new of("lanServer.otherPlayers");
   private final dot p;
   private dlj q;
   private dlj r;
   private String s = "survival";
   private boolean t;

   public dou(dot var1) {
      super(new of("lanServer.title"));
      this.p = _snowman;
   }

   @Override
   protected void b() {
      this.a(new dlj(this.k / 2 - 155, this.l - 28, 150, 20, new of("lanServer.start"), var1 -> {
         this.i.a(null);
         int _snowman = aff.a();
         nr _snowmanx;
         if (this.i.H().a(bru.a(this.s), this.t, _snowman)) {
            _snowmanx = new of("commands.publish.started", _snowman);
         } else {
            _snowmanx = new of("commands.publish.failed");
         }

         this.i.j.c().a(_snowmanx);
         this.i.c();
      }));
      this.a(new dlj(this.k / 2 + 5, this.l - 28, 150, 20, nq.d, var1 -> this.i.a(this.p)));
      this.r = this.a(new dlj(this.k / 2 - 155, 100, 150, 20, oe.d, var1 -> {
         if ("spectator".equals(this.s)) {
            this.s = "creative";
         } else if ("creative".equals(this.s)) {
            this.s = "adventure";
         } else if ("adventure".equals(this.s)) {
            this.s = "survival";
         } else {
            this.s = "spectator";
         }

         this.h();
      }));
      this.q = this.a(new dlj(this.k / 2 + 5, 100, 150, 20, a, var1 -> {
         this.t = !this.t;
         this.h();
      }));
      this.h();
   }

   private void h() {
      this.r.a(new of("options.generic_value", b, new of("selectWorld.gameMode." + this.s)));
      this.q.a(nq.a(a, this.t));
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.d, this.k / 2, 50, 16777215);
      a(_snowman, this.o, c, this.k / 2, 82, 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
