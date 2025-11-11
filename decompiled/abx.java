public interface abx {
   abx a = a();
   abx b = a("pack.source.builtin");
   abx c = a("pack.source.world");
   abx d = a("pack.source.server");

   nr decorate(nr var1);

   static abx a() {
      return var0 -> var0;
   }

   static abx a(String var0) {
      nr _snowman = new of(_snowman);
      return var1x -> new of("pack.nameAndSource", var1x, _snowman).a(k.h);
   }
}
