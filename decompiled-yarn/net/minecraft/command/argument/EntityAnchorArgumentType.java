package net.minecraft.command.argument;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;

public class EntityAnchorArgumentType implements ArgumentType<EntityAnchorArgumentType.EntityAnchor> {
   private static final Collection<String> EXAMPLES = Arrays.asList("eyes", "feet");
   private static final DynamicCommandExceptionType INVALID_ANCHOR_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("argument.anchor.invalid", _snowman)
   );

   public EntityAnchorArgumentType() {
   }

   public static EntityAnchorArgumentType.EntityAnchor getEntityAnchor(CommandContext<ServerCommandSource> _snowman, String _snowman) {
      return (EntityAnchorArgumentType.EntityAnchor)_snowman.getArgument(_snowman, EntityAnchorArgumentType.EntityAnchor.class);
   }

   public static EntityAnchorArgumentType entityAnchor() {
      return new EntityAnchorArgumentType();
   }

   public EntityAnchorArgumentType.EntityAnchor parse(StringReader _snowman) throws CommandSyntaxException {
      int _snowmanx = _snowman.getCursor();
      String _snowmanxx = _snowman.readUnquotedString();
      EntityAnchorArgumentType.EntityAnchor _snowmanxxx = EntityAnchorArgumentType.EntityAnchor.fromId(_snowmanxx);
      if (_snowmanxxx == null) {
         _snowman.setCursor(_snowmanx);
         throw INVALID_ANCHOR_EXCEPTION.createWithContext(_snowman, _snowmanxx);
      } else {
         return _snowmanxxx;
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      return CommandSource.suggestMatching(EntityAnchorArgumentType.EntityAnchor.anchors.keySet(), builder);
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   public static enum EntityAnchor {
      FEET("feet", (_snowman, _snowmanx) -> _snowman),
      EYES("eyes", (_snowman, _snowmanx) -> new Vec3d(_snowman.x, _snowman.y + (double)_snowmanx.getStandingEyeHeight(), _snowman.z));

      private static final Map<String, EntityAnchorArgumentType.EntityAnchor> anchors = Util.make(Maps.newHashMap(), _snowman -> {
         for (EntityAnchorArgumentType.EntityAnchor _snowmanx : values()) {
            _snowman.put(_snowmanx.id, _snowmanx);
         }
      });
      private final String id;
      private final BiFunction<Vec3d, Entity, Vec3d> offset;

      private EntityAnchor(String id, BiFunction<Vec3d, Entity, Vec3d> offset) {
         this.id = id;
         this.offset = offset;
      }

      @Nullable
      public static EntityAnchorArgumentType.EntityAnchor fromId(String id) {
         return anchors.get(id);
      }

      public Vec3d positionAt(Entity _snowman) {
         return this.offset.apply(_snowman.getPos(), _snowman);
      }

      public Vec3d positionAt(ServerCommandSource _snowman) {
         Entity _snowmanx = _snowman.getEntity();
         return _snowmanx == null ? _snowman.getPosition() : this.offset.apply(_snowman.getPosition(), _snowmanx);
      }
   }
}
