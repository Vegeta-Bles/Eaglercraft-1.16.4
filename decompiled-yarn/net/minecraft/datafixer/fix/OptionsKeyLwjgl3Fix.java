package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.stream.Collectors;
import net.minecraft.datafixer.TypeReferences;

public class OptionsKeyLwjgl3Fix extends DataFix {
   private static final Int2ObjectMap<String> NUMERICAL_KEY_IDS_TO_KEY_NAMES = (Int2ObjectMap<String>)DataFixUtils.make(new Int2ObjectOpenHashMap(), _snowman -> {
      _snowman.put(0, "key.unknown");
      _snowman.put(11, "key.0");
      _snowman.put(2, "key.1");
      _snowman.put(3, "key.2");
      _snowman.put(4, "key.3");
      _snowman.put(5, "key.4");
      _snowman.put(6, "key.5");
      _snowman.put(7, "key.6");
      _snowman.put(8, "key.7");
      _snowman.put(9, "key.8");
      _snowman.put(10, "key.9");
      _snowman.put(30, "key.a");
      _snowman.put(40, "key.apostrophe");
      _snowman.put(48, "key.b");
      _snowman.put(43, "key.backslash");
      _snowman.put(14, "key.backspace");
      _snowman.put(46, "key.c");
      _snowman.put(58, "key.caps.lock");
      _snowman.put(51, "key.comma");
      _snowman.put(32, "key.d");
      _snowman.put(211, "key.delete");
      _snowman.put(208, "key.down");
      _snowman.put(18, "key.e");
      _snowman.put(207, "key.end");
      _snowman.put(28, "key.enter");
      _snowman.put(13, "key.equal");
      _snowman.put(1, "key.escape");
      _snowman.put(33, "key.f");
      _snowman.put(59, "key.f1");
      _snowman.put(68, "key.f10");
      _snowman.put(87, "key.f11");
      _snowman.put(88, "key.f12");
      _snowman.put(100, "key.f13");
      _snowman.put(101, "key.f14");
      _snowman.put(102, "key.f15");
      _snowman.put(103, "key.f16");
      _snowman.put(104, "key.f17");
      _snowman.put(105, "key.f18");
      _snowman.put(113, "key.f19");
      _snowman.put(60, "key.f2");
      _snowman.put(61, "key.f3");
      _snowman.put(62, "key.f4");
      _snowman.put(63, "key.f5");
      _snowman.put(64, "key.f6");
      _snowman.put(65, "key.f7");
      _snowman.put(66, "key.f8");
      _snowman.put(67, "key.f9");
      _snowman.put(34, "key.g");
      _snowman.put(41, "key.grave.accent");
      _snowman.put(35, "key.h");
      _snowman.put(199, "key.home");
      _snowman.put(23, "key.i");
      _snowman.put(210, "key.insert");
      _snowman.put(36, "key.j");
      _snowman.put(37, "key.k");
      _snowman.put(82, "key.keypad.0");
      _snowman.put(79, "key.keypad.1");
      _snowman.put(80, "key.keypad.2");
      _snowman.put(81, "key.keypad.3");
      _snowman.put(75, "key.keypad.4");
      _snowman.put(76, "key.keypad.5");
      _snowman.put(77, "key.keypad.6");
      _snowman.put(71, "key.keypad.7");
      _snowman.put(72, "key.keypad.8");
      _snowman.put(73, "key.keypad.9");
      _snowman.put(78, "key.keypad.add");
      _snowman.put(83, "key.keypad.decimal");
      _snowman.put(181, "key.keypad.divide");
      _snowman.put(156, "key.keypad.enter");
      _snowman.put(141, "key.keypad.equal");
      _snowman.put(55, "key.keypad.multiply");
      _snowman.put(74, "key.keypad.subtract");
      _snowman.put(38, "key.l");
      _snowman.put(203, "key.left");
      _snowman.put(56, "key.left.alt");
      _snowman.put(26, "key.left.bracket");
      _snowman.put(29, "key.left.control");
      _snowman.put(42, "key.left.shift");
      _snowman.put(219, "key.left.win");
      _snowman.put(50, "key.m");
      _snowman.put(12, "key.minus");
      _snowman.put(49, "key.n");
      _snowman.put(69, "key.num.lock");
      _snowman.put(24, "key.o");
      _snowman.put(25, "key.p");
      _snowman.put(209, "key.page.down");
      _snowman.put(201, "key.page.up");
      _snowman.put(197, "key.pause");
      _snowman.put(52, "key.period");
      _snowman.put(183, "key.print.screen");
      _snowman.put(16, "key.q");
      _snowman.put(19, "key.r");
      _snowman.put(205, "key.right");
      _snowman.put(184, "key.right.alt");
      _snowman.put(27, "key.right.bracket");
      _snowman.put(157, "key.right.control");
      _snowman.put(54, "key.right.shift");
      _snowman.put(220, "key.right.win");
      _snowman.put(31, "key.s");
      _snowman.put(70, "key.scroll.lock");
      _snowman.put(39, "key.semicolon");
      _snowman.put(53, "key.slash");
      _snowman.put(57, "key.space");
      _snowman.put(20, "key.t");
      _snowman.put(15, "key.tab");
      _snowman.put(22, "key.u");
      _snowman.put(200, "key.up");
      _snowman.put(47, "key.v");
      _snowman.put(17, "key.w");
      _snowman.put(45, "key.x");
      _snowman.put(21, "key.y");
      _snowman.put(44, "key.z");
   });

   public OptionsKeyLwjgl3Fix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "OptionsKeyLwjgl3Fix",
         this.getInputSchema().getType(TypeReferences.OPTIONS),
         _snowman -> _snowman.update(DSL.remainderFinder(), _snowmanx -> _snowmanx.getMapValues().map(_snowmanxxx -> _snowmanx.createMap(_snowmanxxx.entrySet().stream().map(_snowmanxxxx -> {
                     if (((Dynamic)_snowmanxxxx.getKey()).asString("").startsWith("key_")) {
                        int _snowmanx = Integer.parseInt(((Dynamic)_snowmanxxxx.getValue()).asString(""));
                        if (_snowmanx < 0) {
                           int _snowmanxx = _snowmanx + 100;
                           String _snowmanxxx;
                           if (_snowmanxx == 0) {
                              _snowmanxxx = "key.mouse.left";
                           } else if (_snowmanxx == 1) {
                              _snowmanxxx = "key.mouse.right";
                           } else if (_snowmanxx == 2) {
                              _snowmanxxx = "key.mouse.middle";
                           } else {
                              _snowmanxxx = "key.mouse." + (_snowmanxx + 1);
                           }

                           return Pair.of(_snowmanxxxx.getKey(), ((Dynamic)_snowmanxxxx.getValue()).createString(_snowmanxxx));
                        } else {
                           String _snowmanxx = (String)NUMERICAL_KEY_IDS_TO_KEY_NAMES.getOrDefault(_snowmanx, "key.unknown");
                           return Pair.of(_snowmanxxxx.getKey(), ((Dynamic)_snowmanxxxx.getValue()).createString(_snowmanxx));
                        }
                     } else {
                        return Pair.of(_snowmanxxxx.getKey(), _snowmanxxxx.getValue());
                     }
                  }).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)))).result().orElse(_snowmanx))
      );
   }
}
