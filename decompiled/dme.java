public class dme extends dle {
   private final adr c;

   public dme(djz var1, int var2, int var3, adr var4, int var5) {
      super(_snowman.k, _snowman, _snowman, _snowman, 20, (double)_snowman.k.a(_snowman));
      this.c = _snowman;
      this.b();
   }

   @Override
   protected void b() {
      nr _snowman = (nr)((float)this.b == (float)this.a(false) ? nq.b : new oe((int)(this.b * 100.0) + "%"));
      this.a(new of("soundCategory." + this.c.a()).c(": ").a(_snowman));
   }

   @Override
   protected void a() {
      this.a.a(this.c, (float)this.b);
      this.a.b();
   }
}
