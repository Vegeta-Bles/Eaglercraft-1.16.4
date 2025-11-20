package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public abstract class EntitySimpleTransformFix extends EntityTransformFix {
   public EntitySimpleTransformFix(String _snowman, Schema _snowman, boolean _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected Pair<String, Typed<?>> transform(String choice, Typed<?> _snowman) {
      Pair<String, Dynamic<?>> _snowmanx = this.transform(choice, (Dynamic<?>)_snowman.getOrCreate(DSL.remainderFinder()));
      return Pair.of(_snowmanx.getFirst(), _snowman.set(DSL.remainderFinder(), _snowmanx.getSecond()));
   }

   protected abstract Pair<String, Dynamic<?>> transform(String choice, Dynamic<?> var2);
}
