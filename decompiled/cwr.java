public interface cwr extends cwm, cwt {
   int a(cvk var1, int var2, int var3, int var4, int var5, int var6);

   @Override
   default int a(cvj<?> var1, cvf var2, int var3, int var4) {
      return this.a(
         _snowman,
         _snowman.a(this.a(_snowman + 1), this.b(_snowman + 0)),
         _snowman.a(this.a(_snowman + 2), this.b(_snowman + 1)),
         _snowman.a(this.a(_snowman + 1), this.b(_snowman + 2)),
         _snowman.a(this.a(_snowman + 0), this.b(_snowman + 1)),
         _snowman.a(this.a(_snowman + 1), this.b(_snowman + 1))
      );
   }
}
