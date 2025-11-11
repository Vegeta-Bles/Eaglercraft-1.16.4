import com.google.common.collect.Lists;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cro extends cru {
   private static final Logger d = LogManager.getLogger();
   protected final coi a;
   protected fx b;
   private final int e;
   protected final bzm c;
   private final List<cod> f = Lists.newArrayList();
   private final csw g;

   public cro(csw var1, coi var2, fx var3, int var4, bzm var5, cra var6) {
      super(clb.ad, 0);
      this.g = _snowman;
      this.a = _snowman;
      this.b = _snowman;
      this.e = _snowman;
      this.c = _snowman;
      this.n = _snowman;
   }

   public cro(csw var1, md var2) {
      super(clb.ad, _snowman);
      this.g = _snowman;
      this.b = new fx(_snowman.h("PosX"), _snowman.h("PosY"), _snowman.h("PosZ"));
      this.e = _snowman.h("ground_level_delta");
      this.a = coi.e.parse(mo.a, _snowman.p("pool_element")).resultOrPartial(d::error).orElse(cob.b);
      this.c = bzm.valueOf(_snowman.l("rotation"));
      this.n = this.a.a(_snowman, this.b, this.c);
      mj _snowman = _snowman.d("junctions", 10);
      this.f.clear();
      _snowman.forEach(var1x -> this.f.add(cod.a(new Dynamic(mo.a, var1x))));
   }

   @Override
   protected void a(md var1) {
      _snowman.b("PosX", this.b.u());
      _snowman.b("PosY", this.b.v());
      _snowman.b("PosZ", this.b.w());
      _snowman.b("ground_level_delta", this.e);
      coi.e.encodeStart(mo.a, this.a).resultOrPartial(d::error).ifPresent(var1x -> _snowman.a("pool_element", var1x));
      _snowman.a("rotation", this.c.name());
      mj _snowman = new mj();

      for (cod _snowmanx : this.f) {
         _snowman.add((mt)_snowmanx.a(mo.a).getValue());
      }

      _snowman.a("junctions", _snowman);
   }

   @Override
   public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
      return this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, false);
   }

   public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, fx var6, boolean var7) {
      return this.a.a(this.g, _snowman, _snowman, _snowman, this.b, _snowman, this.c, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(int var1, int var2, int var3) {
      super.a(_snowman, _snowman, _snowman);
      this.b = this.b.b(_snowman, _snowman, _snowman);
   }

   @Override
   public bzm ap_() {
      return this.c;
   }

   @Override
   public String toString() {
      return String.format("<%s | %s | %s | %s>", this.getClass().getSimpleName(), this.b, this.c, this.a);
   }

   public coi b() {
      return this.a;
   }

   public fx c() {
      return this.b;
   }

   public int d() {
      return this.e;
   }

   public void a(cod var1) {
      this.f.add(_snowman);
   }

   public List<cod> e() {
      return this.f;
   }
}
