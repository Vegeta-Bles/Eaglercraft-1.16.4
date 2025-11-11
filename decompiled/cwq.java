public interface cwq extends cwm, cwt {
   int a(cvk var1, int var2);

   @Override
   default int a(cvj<?> var1, cvf var2, int var3, int var4) {
      int _snowman = _snowman.a(this.a(_snowman + 1), this.b(_snowman + 1));
      return this.a(_snowman, _snowman);
   }
}
