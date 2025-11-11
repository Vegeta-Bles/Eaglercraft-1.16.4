import java.util.function.Supplier;

public interface anw {
   void a();

   void b();

   void a(String var1);

   void a(Supplier<String> var1);

   void c();

   void b(String var1);

   void b(Supplier<String> var1);

   void c(String var1);

   void c(Supplier<String> var1);

   static anw a(final anw var0, final anw var1) {
      if (_snowman == ant.a) {
         return _snowman;
      } else {
         return _snowman == ant.a ? _snowman : new anw() {
            @Override
            public void a() {
               _snowman.a();
               _snowman.a();
            }

            @Override
            public void b() {
               _snowman.b();
               _snowman.b();
            }

            @Override
            public void a(String var1x) {
               _snowman.a(_snowman);
               _snowman.a(_snowman);
            }

            @Override
            public void a(Supplier<String> var1x) {
               _snowman.a(_snowman);
               _snowman.a(_snowman);
            }

            @Override
            public void c() {
               _snowman.c();
               _snowman.c();
            }

            @Override
            public void b(String var1x) {
               _snowman.b(_snowman);
               _snowman.b(_snowman);
            }

            @Override
            public void b(Supplier<String> var1x) {
               _snowman.b(_snowman);
               _snowman.b(_snowman);
            }

            @Override
            public void c(String var1x) {
               _snowman.c(_snowman);
               _snowman.c(_snowman);
            }

            @Override
            public void c(Supplier<String> var1x) {
               _snowman.c(_snowman);
               _snowman.c(_snowman);
            }
         };
      }
   }
}
