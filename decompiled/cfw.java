import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;

public interface cfw extends brc, cgd {
   @Nullable
   ceh a(fx var1, ceh var2, boolean var3);

   void a(fx var1, ccj var2);

   void a(aqa var1);

   @Nullable
   default cgi a() {
      cgi[] _snowman = this.d();

      for (int _snowmanx = _snowman.length - 1; _snowmanx >= 0; _snowmanx--) {
         cgi _snowmanxx = _snowman[_snowmanx];
         if (!cgi.a(_snowmanxx)) {
            return _snowmanxx;
         }
      }

      return null;
   }

   default int b() {
      cgi _snowman = this.a();
      return _snowman == null ? 0 : _snowman.g();
   }

   Set<fx> c();

   cgi[] d();

   Collection<Entry<chn.a, chn>> f();

   void a(chn.a var1, long[] var2);

   chn a(chn.a var1);

   int a(chn.a var1, int var2, int var3);

   brd g();

   void a(long var1);

   Map<cla<?>, crv<?>> h();

   void a(Map<cla<?>, crv<?>> var1);

   default boolean a(int var1, int var2) {
      if (_snowman < 0) {
         _snowman = 0;
      }

      if (_snowman >= 256) {
         _snowman = 255;
      }

      for (int _snowman = _snowman; _snowman <= _snowman; _snowman += 16) {
         if (!cgi.a(this.d()[_snowman >> 4])) {
            return false;
         }
      }

      return true;
   }

   @Nullable
   cfx i();

   void a(boolean var1);

   boolean j();

   cga k();

   void d(fx var1);

   default void e(fx var1) {
      LogManager.getLogger().warn("Trying to mark a block for PostProcessing @ {}, but this operation is not supported.", _snowman);
   }

   ShortList[] l();

   default void a(short var1, int var2) {
      a(this.l(), _snowman).add(_snowman);
   }

   default void a(md var1) {
      LogManager.getLogger().warn("Trying to set a BlockEntity, but this operation is not supported.");
   }

   @Nullable
   md i(fx var1);

   @Nullable
   md j(fx var1);

   Stream<fx> m();

   bso<buo> n();

   bso<cuw> o();

   cgr p();

   void b(long var1);

   long q();

   static ShortList a(ShortList[] var0, int var1) {
      if (_snowman[_snowman] == null) {
         _snowman[_snowman] = new ShortArrayList();
      }

      return _snowman[_snowman];
   }

   boolean r();

   void b(boolean var1);
}
