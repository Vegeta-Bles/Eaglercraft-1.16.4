package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.recipe.book.RecipeBookOptions;
import net.minecraft.util.Identifier;

public class UnlockRecipesS2CPacket implements Packet<ClientPlayPacketListener> {
   private UnlockRecipesS2CPacket.Action action;
   private List<Identifier> recipeIdsToChange;
   private List<Identifier> recipeIdsToInit;
   private RecipeBookOptions options;

   public UnlockRecipesS2CPacket() {
   }

   public UnlockRecipesS2CPacket(UnlockRecipesS2CPacket.Action _snowman, Collection<Identifier> _snowman, Collection<Identifier> _snowman, RecipeBookOptions options) {
      this.action = _snowman;
      this.recipeIdsToChange = ImmutableList.copyOf(_snowman);
      this.recipeIdsToInit = ImmutableList.copyOf(_snowman);
      this.options = options;
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onUnlockRecipes(this);
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.action = buf.readEnumConstant(UnlockRecipesS2CPacket.Action.class);
      this.options = RecipeBookOptions.fromPacket(buf);
      int _snowman = buf.readVarInt();
      this.recipeIdsToChange = Lists.newArrayList();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         this.recipeIdsToChange.add(buf.readIdentifier());
      }

      if (this.action == UnlockRecipesS2CPacket.Action.INIT) {
         _snowman = buf.readVarInt();
         this.recipeIdsToInit = Lists.newArrayList();

         for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
            this.recipeIdsToInit.add(buf.readIdentifier());
         }
      }
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeEnumConstant(this.action);
      this.options.toPacket(buf);
      buf.writeVarInt(this.recipeIdsToChange.size());

      for (Identifier _snowman : this.recipeIdsToChange) {
         buf.writeIdentifier(_snowman);
      }

      if (this.action == UnlockRecipesS2CPacket.Action.INIT) {
         buf.writeVarInt(this.recipeIdsToInit.size());

         for (Identifier _snowman : this.recipeIdsToInit) {
            buf.writeIdentifier(_snowman);
         }
      }
   }

   public List<Identifier> getRecipeIdsToChange() {
      return this.recipeIdsToChange;
   }

   public List<Identifier> getRecipeIdsToInit() {
      return this.recipeIdsToInit;
   }

   public RecipeBookOptions getOptions() {
      return this.options;
   }

   public UnlockRecipesS2CPacket.Action getAction() {
      return this.action;
   }

   public static enum Action {
      INIT,
      ADD,
      REMOVE;

      private Action() {
      }
   }
}
