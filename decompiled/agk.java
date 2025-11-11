import com.mojang.datafixers.schemas.Schema;

public class agk extends aki {
   public agk(Schema var1) {
      super(_snowman, false);
   }

   @Override
   protected String a(String var1) {
      return _snowman.equals("minecraft:bee_hive") ? "minecraft:beehive" : _snowman;
   }
}
