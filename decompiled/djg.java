public class djg extends dja {
   private final String c;
   private final String d;
   private final long e;
   private final dot f;

   public djg(long var1, String var3, String var4, dot var5) {
      this.e = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.f = _snowman;
   }

   @Override
   public void run() {
      this.b(new of("mco.create.world.wait"));
      dgb _snowman = dgb.a();

      try {
         _snowman.a(this.e, this.c, this.d);
         a(this.f);
      } catch (dhi var3) {
         a.error("Couldn't create world");
         this.a(var3.toString());
      } catch (Exception var4) {
         a.error("Could not create world");
         this.a(var4.getLocalizedMessage());
      }
   }
}
