import com.mojang.datafixers.DataFixer;
import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class cgu implements AutoCloseable {
   private final cgv a;
   protected final DataFixer b;
   @Nullable
   private crg c;

   public cgu(File var1, DataFixer var2, boolean var3) {
      this.b = _snowman;
      this.a = new cgv(_snowman, _snowman, "chunk");
   }

   public md a(vj<brx> var1, Supplier<cyc> var2, md var3) {
      int _snowman = a(_snowman);
      int _snowmanx = 1493;
      if (_snowman < 1493) {
         _snowman = mp.a(this.b, aga.c, _snowman, _snowman, 1493);
         if (_snowman.p("Level").q("hasLegacyStructureData")) {
            if (this.c == null) {
               this.c = crg.a(_snowman, _snowman.get());
            }

            _snowman = this.c.a(_snowman);
         }
      }

      _snowman = mp.a(this.b, aga.c, _snowman, Math.max(1493, _snowman));
      if (_snowman < w.a().getWorldVersion()) {
         _snowman.b("DataVersion", w.a().getWorldVersion());
      }

      return _snowman;
   }

   public static int a(md var0) {
      return _snowman.c("DataVersion", 99) ? _snowman.h("DataVersion") : -1;
   }

   @Nullable
   public md e(brd var1) throws IOException {
      return this.a.a(_snowman);
   }

   public void a(brd var1, md var2) {
      this.a.a(_snowman, _snowman);
      if (this.c != null) {
         this.c.a(_snowman.a());
      }
   }

   public void i() {
      this.a.a().join();
   }

   @Override
   public void close() throws IOException {
      this.a.close();
   }
}
