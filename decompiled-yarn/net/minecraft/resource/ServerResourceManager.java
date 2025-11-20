package net.minecraft.resource;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.condition.LootConditionManager;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.function.FunctionLoader;
import net.minecraft.tag.TagManager;
import net.minecraft.tag.TagManagerLoader;
import net.minecraft.util.Unit;

public class ServerResourceManager implements AutoCloseable {
   private static final CompletableFuture<Unit> field_25334 = CompletableFuture.completedFuture(Unit.INSTANCE);
   private final ReloadableResourceManager resourceManager = new ReloadableResourceManagerImpl(ResourceType.SERVER_DATA);
   private final CommandManager commandManager;
   private final RecipeManager recipeManager = new RecipeManager();
   private final TagManagerLoader registryTagManager = new TagManagerLoader();
   private final LootConditionManager lootConditionManager = new LootConditionManager();
   private final LootManager lootManager = new LootManager(this.lootConditionManager);
   private final ServerAdvancementLoader serverAdvancementLoader = new ServerAdvancementLoader(this.lootConditionManager);
   private final FunctionLoader functionLoader;

   public ServerResourceManager(CommandManager.RegistrationEnvironment _snowman, int _snowman) {
      this.commandManager = new CommandManager(_snowman);
      this.functionLoader = new FunctionLoader(_snowman, this.commandManager.getDispatcher());
      this.resourceManager.registerListener(this.registryTagManager);
      this.resourceManager.registerListener(this.lootConditionManager);
      this.resourceManager.registerListener(this.recipeManager);
      this.resourceManager.registerListener(this.lootManager);
      this.resourceManager.registerListener(this.functionLoader);
      this.resourceManager.registerListener(this.serverAdvancementLoader);
   }

   public FunctionLoader getFunctionLoader() {
      return this.functionLoader;
   }

   public LootConditionManager getLootConditionManager() {
      return this.lootConditionManager;
   }

   public LootManager getLootManager() {
      return this.lootManager;
   }

   public TagManager getRegistryTagManager() {
      return this.registryTagManager.getTagManager();
   }

   public RecipeManager getRecipeManager() {
      return this.recipeManager;
   }

   public CommandManager getCommandManager() {
      return this.commandManager;
   }

   public ServerAdvancementLoader getServerAdvancementLoader() {
      return this.serverAdvancementLoader;
   }

   public ResourceManager getResourceManager() {
      return this.resourceManager;
   }

   public static CompletableFuture<ServerResourceManager> reload(List<ResourcePack> _snowman, CommandManager.RegistrationEnvironment _snowman, int _snowman, Executor _snowman, Executor _snowman) {
      ServerResourceManager _snowmanxxxxx = new ServerResourceManager(_snowman, _snowman);
      CompletableFuture<Unit> _snowmanxxxxxx = _snowmanxxxxx.resourceManager.beginReload(_snowman, _snowman, _snowman, field_25334);
      return _snowmanxxxxxx.whenComplete((_snowmanxxxxxxx, _snowmanxxxxxxx) -> {
         if (_snowmanxxxxxxx != null) {
            _snowman.close();
         }
      }).thenApply(_snowmanxxxxxxx -> _snowman);
   }

   public void loadRegistryTags() {
      this.registryTagManager.getTagManager().apply();
   }

   @Override
   public void close() {
      this.resourceManager.close();
   }
}
