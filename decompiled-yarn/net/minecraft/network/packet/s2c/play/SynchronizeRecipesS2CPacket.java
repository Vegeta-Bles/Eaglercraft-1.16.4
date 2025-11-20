package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SynchronizeRecipesS2CPacket implements Packet<ClientPlayPacketListener> {
   private List<Recipe<?>> recipes;

   public SynchronizeRecipesS2CPacket() {
   }

   public SynchronizeRecipesS2CPacket(Collection<Recipe<?>> recipes) {
      this.recipes = Lists.newArrayList(recipes);
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onSynchronizeRecipes(this);
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.recipes = Lists.newArrayList();
      int _snowman = buf.readVarInt();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         this.recipes.add(readRecipe(buf));
      }
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeVarInt(this.recipes.size());

      for (Recipe<?> _snowman : this.recipes) {
         writeRecipe(_snowman, buf);
      }
   }

   public List<Recipe<?>> getRecipes() {
      return this.recipes;
   }

   public static Recipe<?> readRecipe(PacketByteBuf buf) {
      Identifier _snowman = buf.readIdentifier();
      Identifier _snowmanx = buf.readIdentifier();
      return Registry.RECIPE_SERIALIZER.getOrEmpty(_snowman).orElseThrow(() -> new IllegalArgumentException("Unknown recipe serializer " + _snowman)).read(_snowmanx, buf);
   }

   public static <T extends Recipe<?>> void writeRecipe(T recipe, PacketByteBuf buf) {
      buf.writeIdentifier(Registry.RECIPE_SERIALIZER.getId(recipe.getSerializer()));
      buf.writeIdentifier(recipe.getId());
      ((RecipeSerializer<T>)recipe.getSerializer()).write(buf, recipe);
   }
}
