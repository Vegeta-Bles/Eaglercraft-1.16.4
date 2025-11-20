package net.minecraft.text;

import com.google.common.base.Joiner;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.EntitySelectorReader;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.NbtPathArgumentType;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class NbtText extends BaseText implements ParsableText {
   private static final Logger LOGGER = LogManager.getLogger();
   protected final boolean interpret;
   protected final String rawPath;
   @Nullable
   protected final NbtPathArgumentType.NbtPath path;

   @Nullable
   private static NbtPathArgumentType.NbtPath parsePath(String rawPath) {
      try {
         return new NbtPathArgumentType().parse(new StringReader(rawPath));
      } catch (CommandSyntaxException var2) {
         return null;
      }
   }

   public NbtText(String rawPath, boolean interpret) {
      this(rawPath, parsePath(rawPath), interpret);
   }

   protected NbtText(String rawPath, @Nullable NbtPathArgumentType.NbtPath path, boolean interpret) {
      this.rawPath = rawPath;
      this.path = path;
      this.interpret = interpret;
   }

   protected abstract Stream<CompoundTag> toNbt(ServerCommandSource source) throws CommandSyntaxException;

   public String getPath() {
      return this.rawPath;
   }

   public boolean shouldInterpret() {
      return this.interpret;
   }

   @Override
   public MutableText parse(@Nullable ServerCommandSource source, @Nullable Entity sender, int depth) throws CommandSyntaxException {
      if (source != null && this.path != null) {
         Stream<String> _snowman = this.toNbt(source).flatMap(nbt -> {
            try {
               return this.path.get(nbt).stream();
            } catch (CommandSyntaxException var3) {
               return Stream.empty();
            }
         }).map(Tag::asString);
         return (MutableText)(this.interpret ? _snowman.flatMap(text -> {
            try {
               MutableText _snowmanx = Text.Serializer.fromJson(text);
               return Stream.of(Texts.parse(source, _snowmanx, sender, depth));
            } catch (Exception var5) {
               LOGGER.warn("Failed to parse component: " + text, var5);
               return Stream.of();
            }
         }).reduce((a, b) -> a.append(", ").append(b)).orElse(new LiteralText("")) : new LiteralText(Joiner.on(", ").join(_snowman.iterator())));
      } else {
         return new LiteralText("");
      }
   }

   public static class BlockNbtText extends NbtText {
      private final String rawPos;
      @Nullable
      private final PosArgument pos;

      public BlockNbtText(String rawPath, boolean rawJson, String rawPos) {
         super(rawPath, rawJson);
         this.rawPos = rawPos;
         this.pos = this.parsePos(this.rawPos);
      }

      @Nullable
      private PosArgument parsePos(String rawPos) {
         try {
            return BlockPosArgumentType.blockPos().parse(new StringReader(rawPos));
         } catch (CommandSyntaxException var3) {
            return null;
         }
      }

      private BlockNbtText(String rawPath, @Nullable NbtPathArgumentType.NbtPath path, boolean interpret, String rawPos, @Nullable PosArgument pos) {
         super(rawPath, path, interpret);
         this.rawPos = rawPos;
         this.pos = pos;
      }

      @Nullable
      public String getPos() {
         return this.rawPos;
      }

      public NbtText.BlockNbtText copy() {
         return new NbtText.BlockNbtText(this.rawPath, this.path, this.interpret, this.rawPos, this.pos);
      }

      @Override
      protected Stream<CompoundTag> toNbt(ServerCommandSource source) {
         if (this.pos != null) {
            ServerWorld _snowman = source.getWorld();
            BlockPos _snowmanx = this.pos.toAbsoluteBlockPos(source);
            if (_snowman.canSetBlock(_snowmanx)) {
               BlockEntity _snowmanxx = _snowman.getBlockEntity(_snowmanx);
               if (_snowmanxx != null) {
                  return Stream.of(_snowmanxx.toTag(new CompoundTag()));
               }
            }
         }

         return Stream.empty();
      }

      @Override
      public boolean equals(Object _snowman) {
         if (this == _snowman) {
            return true;
         } else if (!(_snowman instanceof NbtText.BlockNbtText)) {
            return false;
         } else {
            NbtText.BlockNbtText _snowmanx = (NbtText.BlockNbtText)_snowman;
            return Objects.equals(this.rawPos, _snowmanx.rawPos) && Objects.equals(this.rawPath, _snowmanx.rawPath) && super.equals(_snowman);
         }
      }

      @Override
      public String toString() {
         return "BlockPosArgument{pos='"
            + this.rawPos
            + '\''
            + "path='"
            + this.rawPath
            + '\''
            + ", siblings="
            + this.siblings
            + ", style="
            + this.getStyle()
            + '}';
      }
   }

   public static class EntityNbtText extends NbtText {
      private final String rawSelector;
      @Nullable
      private final EntitySelector selector;

      public EntityNbtText(String rawPath, boolean interpret, String rawSelector) {
         super(rawPath, interpret);
         this.rawSelector = rawSelector;
         this.selector = parseSelector(rawSelector);
      }

      @Nullable
      private static EntitySelector parseSelector(String rawSelector) {
         try {
            EntitySelectorReader _snowman = new EntitySelectorReader(new StringReader(rawSelector));
            return _snowman.read();
         } catch (CommandSyntaxException var2) {
            return null;
         }
      }

      private EntityNbtText(
         String rawPath, @Nullable NbtPathArgumentType.NbtPath path, boolean interpret, String rawSelector, @Nullable EntitySelector selector
      ) {
         super(rawPath, path, interpret);
         this.rawSelector = rawSelector;
         this.selector = selector;
      }

      public String getSelector() {
         return this.rawSelector;
      }

      public NbtText.EntityNbtText copy() {
         return new NbtText.EntityNbtText(this.rawPath, this.path, this.interpret, this.rawSelector, this.selector);
      }

      @Override
      protected Stream<CompoundTag> toNbt(ServerCommandSource source) throws CommandSyntaxException {
         if (this.selector != null) {
            List<? extends Entity> _snowman = this.selector.getEntities(source);
            return _snowman.stream().map(NbtPredicate::entityToTag);
         } else {
            return Stream.empty();
         }
      }

      @Override
      public boolean equals(Object _snowman) {
         if (this == _snowman) {
            return true;
         } else if (!(_snowman instanceof NbtText.EntityNbtText)) {
            return false;
         } else {
            NbtText.EntityNbtText _snowmanx = (NbtText.EntityNbtText)_snowman;
            return Objects.equals(this.rawSelector, _snowmanx.rawSelector) && Objects.equals(this.rawPath, _snowmanx.rawPath) && super.equals(_snowman);
         }
      }

      @Override
      public String toString() {
         return "EntityNbtComponent{selector='"
            + this.rawSelector
            + '\''
            + "path='"
            + this.rawPath
            + '\''
            + ", siblings="
            + this.siblings
            + ", style="
            + this.getStyle()
            + '}';
      }
   }

   public static class StorageNbtText extends NbtText {
      private final Identifier id;

      public StorageNbtText(String rawPath, boolean interpret, Identifier id) {
         super(rawPath, interpret);
         this.id = id;
      }

      public StorageNbtText(String rawPath, @Nullable NbtPathArgumentType.NbtPath path, boolean interpret, Identifier id) {
         super(rawPath, path, interpret);
         this.id = id;
      }

      public Identifier getId() {
         return this.id;
      }

      public NbtText.StorageNbtText copy() {
         return new NbtText.StorageNbtText(this.rawPath, this.path, this.interpret, this.id);
      }

      @Override
      protected Stream<CompoundTag> toNbt(ServerCommandSource source) {
         CompoundTag _snowman = source.getMinecraftServer().getDataCommandStorage().get(this.id);
         return Stream.of(_snowman);
      }

      @Override
      public boolean equals(Object _snowman) {
         if (this == _snowman) {
            return true;
         } else if (!(_snowman instanceof NbtText.StorageNbtText)) {
            return false;
         } else {
            NbtText.StorageNbtText _snowmanx = (NbtText.StorageNbtText)_snowman;
            return Objects.equals(this.id, _snowmanx.id) && Objects.equals(this.rawPath, _snowmanx.rawPath) && super.equals(_snowman);
         }
      }

      @Override
      public String toString() {
         return "StorageNbtComponent{id='"
            + this.id
            + '\''
            + "path='"
            + this.rawPath
            + '\''
            + ", siblings="
            + this.siblings
            + ", style="
            + this.getStyle()
            + '}';
      }
   }
}
