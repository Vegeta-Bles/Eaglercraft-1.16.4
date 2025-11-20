package net.minecraft.text;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@FunctionalInterface
public interface CharacterVisitor {
   @Environment(EnvType.CLIENT)
   boolean accept(int index, Style style, int codePoint);
}
