package net.minecraft.client.network;

import com.google.common.collect.Lists;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;
import net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class ClientCommandSource implements CommandSource {
   private final ClientPlayNetworkHandler networkHandler;
   private final MinecraftClient client;
   private int completionId = -1;
   private CompletableFuture<Suggestions> pendingCompletion;

   public ClientCommandSource(ClientPlayNetworkHandler networkHandler, MinecraftClient client) {
      this.networkHandler = networkHandler;
      this.client = client;
   }

   @Override
   public Collection<String> getPlayerNames() {
      List<String> _snowman = Lists.newArrayList();

      for (PlayerListEntry _snowmanx : this.networkHandler.getPlayerList()) {
         _snowman.add(_snowmanx.getProfile().getName());
      }

      return _snowman;
   }

   @Override
   public Collection<String> getEntitySuggestions() {
      return (Collection<String>)(this.client.crosshairTarget != null && this.client.crosshairTarget.getType() == HitResult.Type.ENTITY
         ? Collections.singleton(((EntityHitResult)this.client.crosshairTarget).getEntity().getUuidAsString())
         : Collections.emptyList());
   }

   @Override
   public Collection<String> getTeamNames() {
      return this.networkHandler.getWorld().getScoreboard().getTeamNames();
   }

   @Override
   public Collection<Identifier> getSoundIds() {
      return this.client.getSoundManager().getKeys();
   }

   @Override
   public Stream<Identifier> getRecipeIds() {
      return this.networkHandler.getRecipeManager().keys();
   }

   @Override
   public boolean hasPermissionLevel(int level) {
      ClientPlayerEntity _snowman = this.client.player;
      return _snowman != null ? _snowman.hasPermissionLevel(level) : level == 0;
   }

   @Override
   public CompletableFuture<Suggestions> getCompletions(CommandContext<CommandSource> context, SuggestionsBuilder builder) {
      if (this.pendingCompletion != null) {
         this.pendingCompletion.cancel(false);
      }

      this.pendingCompletion = new CompletableFuture<>();
      int _snowman = ++this.completionId;
      this.networkHandler.sendPacket(new RequestCommandCompletionsC2SPacket(_snowman, context.getInput()));
      return this.pendingCompletion;
   }

   private static String format(double d) {
      return String.format(Locale.ROOT, "%.2f", d);
   }

   private static String format(int i) {
      return Integer.toString(i);
   }

   @Override
   public Collection<CommandSource.RelativePosition> getBlockPositionSuggestions() {
      HitResult _snowman = this.client.crosshairTarget;
      if (_snowman != null && _snowman.getType() == HitResult.Type.BLOCK) {
         BlockPos _snowmanx = ((BlockHitResult)_snowman).getBlockPos();
         return Collections.singleton(new CommandSource.RelativePosition(format(_snowmanx.getX()), format(_snowmanx.getY()), format(_snowmanx.getZ())));
      } else {
         return CommandSource.super.getBlockPositionSuggestions();
      }
   }

   @Override
   public Collection<CommandSource.RelativePosition> getPositionSuggestions() {
      HitResult _snowman = this.client.crosshairTarget;
      if (_snowman != null && _snowman.getType() == HitResult.Type.BLOCK) {
         Vec3d _snowmanx = _snowman.getPos();
         return Collections.singleton(new CommandSource.RelativePosition(format(_snowmanx.x), format(_snowmanx.y), format(_snowmanx.z)));
      } else {
         return CommandSource.super.getPositionSuggestions();
      }
   }

   @Override
   public Set<RegistryKey<World>> getWorldKeys() {
      return this.networkHandler.getWorldKeys();
   }

   @Override
   public DynamicRegistryManager getRegistryManager() {
      return this.networkHandler.getRegistryManager();
   }

   public void onCommandSuggestions(int completionId, Suggestions suggestions) {
      if (completionId == this.completionId) {
         this.pendingCompletion.complete(suggestions);
         this.pendingCompletion = null;
         this.completionId = -1;
      }
   }
}
