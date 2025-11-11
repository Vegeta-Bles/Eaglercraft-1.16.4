import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Random;

public class aip extends ajv {
   private static final Random a = new Random();

   public aip(Schema var1, boolean var2) {
      super(_snowman, _snowman, "EntityZombieVillagerTypeFix", akn.p, "Zombie");
   }

   public Dynamic<?> a(Dynamic<?> var1) {
      if (_snowman.get("IsVillager").asBoolean(false)) {
         if (!_snowman.get("ZombieType").result().isPresent()) {
            int _snowman = this.a(_snowman.get("VillagerProfession").asInt(-1));
            if (_snowman == -1) {
               _snowman = this.a(a.nextInt(6));
            }

            _snowman = _snowman.set("ZombieType", _snowman.createInt(_snowman));
         }

         _snowman = _snowman.remove("IsVillager");
      }

      return _snowman;
   }

   private int a(int var1) {
      return _snowman >= 0 && _snowman < 6 ? _snowman : -1;
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), this::a);
   }
}
