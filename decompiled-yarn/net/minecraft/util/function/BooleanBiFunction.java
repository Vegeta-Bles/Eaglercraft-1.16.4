package net.minecraft.util.function;

public interface BooleanBiFunction {
   BooleanBiFunction FALSE = (_snowman, _snowmanx) -> false;
   BooleanBiFunction NOT_OR = (_snowman, _snowmanx) -> !_snowman && !_snowmanx;
   BooleanBiFunction ONLY_SECOND = (_snowman, _snowmanx) -> _snowmanx && !_snowman;
   BooleanBiFunction NOT_FIRST = (_snowman, _snowmanx) -> !_snowman;
   BooleanBiFunction ONLY_FIRST = (_snowman, _snowmanx) -> _snowman && !_snowmanx;
   BooleanBiFunction NOT_SECOND = (_snowman, _snowmanx) -> !_snowmanx;
   BooleanBiFunction NOT_SAME = (_snowman, _snowmanx) -> _snowman != _snowmanx;
   BooleanBiFunction NOT_AND = (_snowman, _snowmanx) -> !_snowman || !_snowmanx;
   BooleanBiFunction AND = (_snowman, _snowmanx) -> _snowman && _snowmanx;
   BooleanBiFunction SAME = (_snowman, _snowmanx) -> _snowman == _snowmanx;
   BooleanBiFunction SECOND = (_snowman, _snowmanx) -> _snowmanx;
   BooleanBiFunction CAUSES = (_snowman, _snowmanx) -> !_snowman || _snowmanx;
   BooleanBiFunction FIRST = (_snowman, _snowmanx) -> _snowman;
   BooleanBiFunction CAUSED_BY = (_snowman, _snowmanx) -> _snowman || !_snowmanx;
   BooleanBiFunction OR = (_snowman, _snowmanx) -> _snowman || _snowmanx;
   BooleanBiFunction TRUE = (_snowman, _snowmanx) -> true;

   boolean apply(boolean var1, boolean var2);
}
