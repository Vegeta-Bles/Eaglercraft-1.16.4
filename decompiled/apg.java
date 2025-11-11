public class apg extends apk {
   protected apg() {
      super("badRespawnPoint");
      this.r();
      this.e();
   }

   @Override
   public nr a(aqm var1) {
      nr _snowman = ns.a((nr)(new of("death.attack.badRespawnPoint.link")))
         .a(var0 -> var0.a(new np(np.a.a, "https://bugs.mojang.com/browse/MCPE-28723")).a(new nv(nv.a.a, new oe("MCPE-28723"))));
      return new of("death.attack.badRespawnPoint.message", _snowman.d(), _snowman);
   }
}
