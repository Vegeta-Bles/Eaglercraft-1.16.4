package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.texture.Sprite;

public interface SpriteProvider {
   Sprite getSprite(int var1, int var2);

   Sprite getSprite(Random random);
}
