public interface cvj<R extends cvf> extends cvk {
   void a(long var1, long var3);

   R a(cwv var1);

   default R a(cwv var1, R var2) {
      return this.a(_snowman);
   }

   default R a(cwv var1, R var2, R var3) {
      return this.a(_snowman);
   }

   default int a(int var1, int var2) {
      return this.a(2) == 0 ? _snowman : _snowman;
   }

   default int a(int var1, int var2, int var3, int var4) {
      int _snowman = this.a(4);
      if (_snowman == 0) {
         return _snowman;
      } else if (_snowman == 1) {
         return _snowman;
      } else {
         return _snowman == 2 ? _snowman : _snowman;
      }
   }
}
