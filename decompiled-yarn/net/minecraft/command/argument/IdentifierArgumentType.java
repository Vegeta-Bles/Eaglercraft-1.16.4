package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.advancement.Advancement;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionManager;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IdentifierArgumentType implements ArgumentType<Identifier> {
   private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo:bar", "012");
   private static final DynamicCommandExceptionType UNKNOWN_ADVANCEMENT_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("advancement.advancementNotFound", _snowman)
   );
   private static final DynamicCommandExceptionType UNKNOWN_RECIPE_EXCEPTION = new DynamicCommandExceptionType(_snowman -> new TranslatableText("recipe.notFound", _snowman));
   private static final DynamicCommandExceptionType field_21506 = new DynamicCommandExceptionType(_snowman -> new TranslatableText("predicate.unknown", _snowman));
   private static final DynamicCommandExceptionType field_24267 = new DynamicCommandExceptionType(_snowman -> new TranslatableText("attribute.unknown", _snowman));

   public IdentifierArgumentType() {
   }

   public static IdentifierArgumentType identifier() {
      return new IdentifierArgumentType();
   }

   public static Advancement getAdvancementArgument(CommandContext<ServerCommandSource> _snowman, String _snowman) throws CommandSyntaxException {
      Identifier _snowmanxx = (Identifier)_snowman.getArgument(_snowman, Identifier.class);
      Advancement _snowmanxxx = ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getAdvancementLoader().get(_snowmanxx);
      if (_snowmanxxx == null) {
         throw UNKNOWN_ADVANCEMENT_EXCEPTION.create(_snowmanxx);
      } else {
         return _snowmanxxx;
      }
   }

   public static Recipe<?> getRecipeArgument(CommandContext<ServerCommandSource> _snowman, String _snowman) throws CommandSyntaxException {
      RecipeManager _snowmanxx = ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getRecipeManager();
      Identifier _snowmanxxx = (Identifier)_snowman.getArgument(_snowman, Identifier.class);
      return (Recipe<?>)_snowmanxx.get(_snowmanxxx).orElseThrow(() -> UNKNOWN_RECIPE_EXCEPTION.create(_snowman));
   }

   public static LootCondition method_23727(CommandContext<ServerCommandSource> _snowman, String _snowman) throws CommandSyntaxException {
      Identifier _snowmanxx = (Identifier)_snowman.getArgument(_snowman, Identifier.class);
      LootConditionManager _snowmanxxx = ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPredicateManager();
      LootCondition _snowmanxxxx = _snowmanxxx.get(_snowmanxx);
      if (_snowmanxxxx == null) {
         throw field_21506.create(_snowmanxx);
      } else {
         return _snowmanxxxx;
      }
   }

   public static EntityAttribute method_27575(CommandContext<ServerCommandSource> _snowman, String _snowman) throws CommandSyntaxException {
      Identifier _snowmanxx = (Identifier)_snowman.getArgument(_snowman, Identifier.class);
      return Registry.ATTRIBUTE.getOrEmpty(_snowmanxx).orElseThrow(() -> field_24267.create(_snowman));
   }

   public static Identifier getIdentifier(CommandContext<ServerCommandSource> context, String name) {
      return (Identifier)context.getArgument(name, Identifier.class);
   }

   public Identifier parse(StringReader _snowman) throws CommandSyntaxException {
      return Identifier.fromCommandInput(_snowman);
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
