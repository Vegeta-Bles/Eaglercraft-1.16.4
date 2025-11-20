package net.minecraft.command;

import com.google.common.primitives.Doubles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.predicate.NumberRange;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntitySelectorReader {
   public static final SimpleCommandExceptionType INVALID_ENTITY_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("argument.entity.invalid"));
   public static final DynamicCommandExceptionType UNKNOWN_SELECTOR_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("argument.entity.selector.unknown", _snowman)
   );
   public static final SimpleCommandExceptionType NOT_ALLOWED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("argument.entity.selector.not_allowed")
   );
   public static final SimpleCommandExceptionType MISSING_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("argument.entity.selector.missing"));
   public static final SimpleCommandExceptionType UNTERMINATED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("argument.entity.options.unterminated")
   );
   public static final DynamicCommandExceptionType VALUELESS_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("argument.entity.options.valueless", _snowman)
   );
   public static final BiConsumer<Vec3d, List<? extends Entity>> ARBITRARY = (_snowman, _snowmanx) -> {
   };
   public static final BiConsumer<Vec3d, List<? extends Entity>> NEAREST = (_snowman, _snowmanx) -> _snowmanx.sort(
         (_snowmanxxxx, _snowmanxxx) -> Doubles.compare(_snowmanxxxx.squaredDistanceTo(_snowman), _snowmanxxx.squaredDistanceTo(_snowman))
      );
   public static final BiConsumer<Vec3d, List<? extends Entity>> FURTHEST = (_snowman, _snowmanx) -> _snowmanx.sort(
         (_snowmanxxxx, _snowmanxxx) -> Doubles.compare(_snowmanxxx.squaredDistanceTo(_snowman), _snowmanxxxx.squaredDistanceTo(_snowman))
      );
   public static final BiConsumer<Vec3d, List<? extends Entity>> RANDOM = (_snowman, _snowmanx) -> Collections.shuffle(_snowmanx);
   public static final BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> DEFAULT_SUGGESTION_PROVIDER = (_snowman, _snowmanx) -> _snowman.buildFuture();
   private final StringReader reader;
   private final boolean atAllowed;
   private int limit;
   private boolean includesNonPlayers;
   private boolean localWorldOnly;
   private NumberRange.FloatRange distance = NumberRange.FloatRange.ANY;
   private NumberRange.IntRange levelRange = NumberRange.IntRange.ANY;
   @Nullable
   private Double x;
   @Nullable
   private Double y;
   @Nullable
   private Double z;
   @Nullable
   private Double dx;
   @Nullable
   private Double dy;
   @Nullable
   private Double dz;
   private FloatRangeArgument pitchRange = FloatRangeArgument.ANY;
   private FloatRangeArgument yawRange = FloatRangeArgument.ANY;
   private Predicate<Entity> predicate = _snowman -> true;
   private BiConsumer<Vec3d, List<? extends Entity>> sorter = ARBITRARY;
   private boolean senderOnly;
   @Nullable
   private String playerName;
   private int startCursor;
   @Nullable
   private UUID uuid;
   private BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> suggestionProvider = DEFAULT_SUGGESTION_PROVIDER;
   private boolean selectsName;
   private boolean excludesName;
   private boolean hasLimit;
   private boolean hasSorter;
   private boolean selectsGameMode;
   private boolean excludesGameMode;
   private boolean selectsTeam;
   private boolean excludesTeam;
   @Nullable
   private EntityType<?> entityType;
   private boolean excludesEntityType;
   private boolean selectsScores;
   private boolean selectsAdvancements;
   private boolean usesAt;

   public EntitySelectorReader(StringReader reader) {
      this(reader, true);
   }

   public EntitySelectorReader(StringReader reader, boolean atAllowed) {
      this.reader = reader;
      this.atAllowed = atAllowed;
   }

   public EntitySelector build() {
      Box _snowman;
      if (this.dx == null && this.dy == null && this.dz == null) {
         if (this.distance.getMax() != null) {
            float _snowmanx = (Float)this.distance.getMax();
            _snowman = new Box((double)(-_snowmanx), (double)(-_snowmanx), (double)(-_snowmanx), (double)(_snowmanx + 1.0F), (double)(_snowmanx + 1.0F), (double)(_snowmanx + 1.0F));
         } else {
            _snowman = null;
         }
      } else {
         _snowman = this.createBox(this.dx == null ? 0.0 : this.dx, this.dy == null ? 0.0 : this.dy, this.dz == null ? 0.0 : this.dz);
      }

      Function<Vec3d, Vec3d> _snowmanx;
      if (this.x == null && this.y == null && this.z == null) {
         _snowmanx = _snowmanxx -> _snowmanxx;
      } else {
         _snowmanx = _snowmanxx -> new Vec3d(this.x == null ? _snowmanxx.x : this.x, this.y == null ? _snowmanxx.y : this.y, this.z == null ? _snowmanxx.z : this.z);
      }

      return new EntitySelector(
         this.limit,
         this.includesNonPlayers,
         this.localWorldOnly,
         this.predicate,
         this.distance,
         _snowmanx,
         _snowman,
         this.sorter,
         this.senderOnly,
         this.playerName,
         this.uuid,
         this.entityType,
         this.usesAt
      );
   }

   private Box createBox(double x, double y, double z) {
      boolean _snowman = x < 0.0;
      boolean _snowmanx = y < 0.0;
      boolean _snowmanxx = z < 0.0;
      double _snowmanxxx = _snowman ? x : 0.0;
      double _snowmanxxxx = _snowmanx ? y : 0.0;
      double _snowmanxxxxx = _snowmanxx ? z : 0.0;
      double _snowmanxxxxxx = (_snowman ? 0.0 : x) + 1.0;
      double _snowmanxxxxxxx = (_snowmanx ? 0.0 : y) + 1.0;
      double _snowmanxxxxxxxx = (_snowmanxx ? 0.0 : z) + 1.0;
      return new Box(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
   }

   private void buildPredicate() {
      if (this.pitchRange != FloatRangeArgument.ANY) {
         this.predicate = this.predicate.and(this.rotationPredicate(this.pitchRange, _snowman -> (double)_snowman.pitch));
      }

      if (this.yawRange != FloatRangeArgument.ANY) {
         this.predicate = this.predicate.and(this.rotationPredicate(this.yawRange, _snowman -> (double)_snowman.yaw));
      }

      if (!this.levelRange.isDummy()) {
         this.predicate = this.predicate.and(_snowman -> !(_snowman instanceof ServerPlayerEntity) ? false : this.levelRange.test(((ServerPlayerEntity)_snowman).experienceLevel));
      }
   }

   private Predicate<Entity> rotationPredicate(FloatRangeArgument _snowman, ToDoubleFunction<Entity> _snowman) {
      double _snowmanxx = (double)MathHelper.wrapDegrees(_snowman.getMin() == null ? 0.0F : _snowman.getMin());
      double _snowmanxxx = (double)MathHelper.wrapDegrees(_snowman.getMax() == null ? 359.0F : _snowman.getMax());
      return _snowmanxxxx -> {
         double _snowmanxxxx = MathHelper.wrapDegrees(_snowman.applyAsDouble(_snowmanxxxx));
         return _snowman > _snowman ? _snowmanxxxx >= _snowman || _snowmanxxxx <= _snowman : _snowmanxxxx >= _snowman && _snowmanxxxx <= _snowman;
      };
   }

   protected void readAtVariable() throws CommandSyntaxException {
      this.usesAt = true;
      this.suggestionProvider = this::suggestSelectorRest;
      if (!this.reader.canRead()) {
         throw MISSING_EXCEPTION.createWithContext(this.reader);
      } else {
         int _snowman = this.reader.getCursor();
         char _snowmanx = this.reader.read();
         if (_snowmanx == 'p') {
            this.limit = 1;
            this.includesNonPlayers = false;
            this.sorter = NEAREST;
            this.setEntityType(EntityType.PLAYER);
         } else if (_snowmanx == 'a') {
            this.limit = Integer.MAX_VALUE;
            this.includesNonPlayers = false;
            this.sorter = ARBITRARY;
            this.setEntityType(EntityType.PLAYER);
         } else if (_snowmanx == 'r') {
            this.limit = 1;
            this.includesNonPlayers = false;
            this.sorter = RANDOM;
            this.setEntityType(EntityType.PLAYER);
         } else if (_snowmanx == 's') {
            this.limit = 1;
            this.includesNonPlayers = true;
            this.senderOnly = true;
         } else {
            if (_snowmanx != 'e') {
               this.reader.setCursor(_snowman);
               throw UNKNOWN_SELECTOR_EXCEPTION.createWithContext(this.reader, '@' + String.valueOf(_snowmanx));
            }

            this.limit = Integer.MAX_VALUE;
            this.includesNonPlayers = true;
            this.sorter = ARBITRARY;
            this.predicate = Entity::isAlive;
         }

         this.suggestionProvider = this::suggestOpen;
         if (this.reader.canRead() && this.reader.peek() == '[') {
            this.reader.skip();
            this.suggestionProvider = this::suggestOptionOrEnd;
            this.readArguments();
         }
      }
   }

   protected void readRegular() throws CommandSyntaxException {
      if (this.reader.canRead()) {
         this.suggestionProvider = this::suggestNormal;
      }

      int _snowman = this.reader.getCursor();
      String _snowmanx = this.reader.readString();

      try {
         this.uuid = UUID.fromString(_snowmanx);
         this.includesNonPlayers = true;
      } catch (IllegalArgumentException var4) {
         if (_snowmanx.isEmpty() || _snowmanx.length() > 16) {
            this.reader.setCursor(_snowman);
            throw INVALID_ENTITY_EXCEPTION.createWithContext(this.reader);
         }

         this.includesNonPlayers = false;
         this.playerName = _snowmanx;
      }

      this.limit = 1;
   }

   protected void readArguments() throws CommandSyntaxException {
      this.suggestionProvider = this::suggestOption;
      this.reader.skipWhitespace();

      while (this.reader.canRead() && this.reader.peek() != ']') {
         this.reader.skipWhitespace();
         int _snowman = this.reader.getCursor();
         String _snowmanx = this.reader.readString();
         EntitySelectorOptions.SelectorHandler _snowmanxx = EntitySelectorOptions.getHandler(this, _snowmanx, _snowman);
         this.reader.skipWhitespace();
         if (!this.reader.canRead() || this.reader.peek() != '=') {
            this.reader.setCursor(_snowman);
            throw VALUELESS_EXCEPTION.createWithContext(this.reader, _snowmanx);
         }

         this.reader.skip();
         this.reader.skipWhitespace();
         this.suggestionProvider = DEFAULT_SUGGESTION_PROVIDER;
         _snowmanxx.handle(this);
         this.reader.skipWhitespace();
         this.suggestionProvider = this::suggestEndNext;
         if (this.reader.canRead()) {
            if (this.reader.peek() != ',') {
               if (this.reader.peek() != ']') {
                  throw UNTERMINATED_EXCEPTION.createWithContext(this.reader);
               }
               break;
            }

            this.reader.skip();
            this.suggestionProvider = this::suggestOption;
         }
      }

      if (this.reader.canRead()) {
         this.reader.skip();
         this.suggestionProvider = DEFAULT_SUGGESTION_PROVIDER;
      } else {
         throw UNTERMINATED_EXCEPTION.createWithContext(this.reader);
      }
   }

   public boolean readNegationCharacter() {
      this.reader.skipWhitespace();
      if (this.reader.canRead() && this.reader.peek() == '!') {
         this.reader.skip();
         this.reader.skipWhitespace();
         return true;
      } else {
         return false;
      }
   }

   public boolean readTagCharacter() {
      this.reader.skipWhitespace();
      if (this.reader.canRead() && this.reader.peek() == '#') {
         this.reader.skip();
         this.reader.skipWhitespace();
         return true;
      } else {
         return false;
      }
   }

   public StringReader getReader() {
      return this.reader;
   }

   public void setPredicate(Predicate<Entity> _snowman) {
      this.predicate = this.predicate.and(_snowman);
   }

   public void setLocalWorldOnly() {
      this.localWorldOnly = true;
   }

   public NumberRange.FloatRange getDistance() {
      return this.distance;
   }

   public void setDistance(NumberRange.FloatRange distance) {
      this.distance = distance;
   }

   public NumberRange.IntRange getLevelRange() {
      return this.levelRange;
   }

   public void setLevelRange(NumberRange.IntRange experienceRange) {
      this.levelRange = experienceRange;
   }

   public FloatRangeArgument getPitchRange() {
      return this.pitchRange;
   }

   public void setPitchRange(FloatRangeArgument _snowman) {
      this.pitchRange = _snowman;
   }

   public FloatRangeArgument getYawRange() {
      return this.yawRange;
   }

   public void setYawRange(FloatRangeArgument _snowman) {
      this.yawRange = _snowman;
   }

   @Nullable
   public Double getX() {
      return this.x;
   }

   @Nullable
   public Double getY() {
      return this.y;
   }

   @Nullable
   public Double getZ() {
      return this.z;
   }

   public void setX(double x) {
      this.x = x;
   }

   public void setY(double y) {
      this.y = y;
   }

   public void setZ(double z) {
      this.z = z;
   }

   public void setDx(double dx) {
      this.dx = dx;
   }

   public void setDy(double dy) {
      this.dy = dy;
   }

   public void setDz(double dz) {
      this.dz = dz;
   }

   @Nullable
   public Double getDx() {
      return this.dx;
   }

   @Nullable
   public Double getDy() {
      return this.dy;
   }

   @Nullable
   public Double getDz() {
      return this.dz;
   }

   public void setLimit(int limit) {
      this.limit = limit;
   }

   public void setIncludesNonPlayers(boolean includesNonPlayers) {
      this.includesNonPlayers = includesNonPlayers;
   }

   public void setSorter(BiConsumer<Vec3d, List<? extends Entity>> sorter) {
      this.sorter = sorter;
   }

   public EntitySelector read() throws CommandSyntaxException {
      this.startCursor = this.reader.getCursor();
      this.suggestionProvider = this::suggestSelector;
      if (this.reader.canRead() && this.reader.peek() == '@') {
         if (!this.atAllowed) {
            throw NOT_ALLOWED_EXCEPTION.createWithContext(this.reader);
         }

         this.reader.skip();
         this.readAtVariable();
      } else {
         this.readRegular();
      }

      this.buildPredicate();
      return this.build();
   }

   private static void suggestSelector(SuggestionsBuilder builder) {
      builder.suggest("@p", new TranslatableText("argument.entity.selector.nearestPlayer"));
      builder.suggest("@a", new TranslatableText("argument.entity.selector.allPlayers"));
      builder.suggest("@r", new TranslatableText("argument.entity.selector.randomPlayer"));
      builder.suggest("@s", new TranslatableText("argument.entity.selector.self"));
      builder.suggest("@e", new TranslatableText("argument.entity.selector.allEntities"));
   }

   private CompletableFuture<Suggestions> suggestSelector(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> _snowman) {
      _snowman.accept(builder);
      if (this.atAllowed) {
         suggestSelector(builder);
      }

      return builder.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestNormal(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> _snowman) {
      SuggestionsBuilder _snowmanx = builder.createOffset(this.startCursor);
      _snowman.accept(_snowmanx);
      return builder.add(_snowmanx).buildFuture();
   }

   private CompletableFuture<Suggestions> suggestSelectorRest(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> _snowman) {
      SuggestionsBuilder _snowmanx = builder.createOffset(builder.getStart() - 1);
      suggestSelector(_snowmanx);
      builder.add(_snowmanx);
      return builder.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestOpen(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> _snowman) {
      builder.suggest(String.valueOf('['));
      return builder.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestOptionOrEnd(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> _snowman) {
      builder.suggest(String.valueOf(']'));
      EntitySelectorOptions.suggestOptions(this, builder);
      return builder.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestOption(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> _snowman) {
      EntitySelectorOptions.suggestOptions(this, builder);
      return builder.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestEndNext(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> _snowman) {
      builder.suggest(String.valueOf(','));
      builder.suggest(String.valueOf(']'));
      return builder.buildFuture();
   }

   public boolean isSenderOnly() {
      return this.senderOnly;
   }

   public void setSuggestionProvider(BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> _snowman) {
      this.suggestionProvider = _snowman;
   }

   public CompletableFuture<Suggestions> listSuggestions(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> _snowman) {
      return this.suggestionProvider.apply(builder.createOffset(this.reader.getCursor()), _snowman);
   }

   public boolean selectsName() {
      return this.selectsName;
   }

   public void setSelectsName(boolean selectsName) {
      this.selectsName = selectsName;
   }

   public boolean excludesName() {
      return this.excludesName;
   }

   public void setExcludesName(boolean _snowman) {
      this.excludesName = _snowman;
   }

   public boolean hasLimit() {
      return this.hasLimit;
   }

   public void setHasLimit(boolean hasLimit) {
      this.hasLimit = hasLimit;
   }

   public boolean hasSorter() {
      return this.hasSorter;
   }

   public void setHasSorter(boolean hasSorter) {
      this.hasSorter = hasSorter;
   }

   public boolean selectsGameMode() {
      return this.selectsGameMode;
   }

   public void setSelectsGameMode(boolean selectsGameMode) {
      this.selectsGameMode = selectsGameMode;
   }

   public boolean excludesGameMode() {
      return this.excludesGameMode;
   }

   public void setHasNegatedGameMode(boolean hasNegatedGameMode) {
      this.excludesGameMode = hasNegatedGameMode;
   }

   public boolean selectsTeam() {
      return this.selectsTeam;
   }

   public void setSelectsTeam(boolean selectsTeam) {
      this.selectsTeam = selectsTeam;
   }

   public void setExcludesTeam(boolean excludesTeam) {
      this.excludesTeam = excludesTeam;
   }

   public void setEntityType(EntityType<?> entityType) {
      this.entityType = entityType;
   }

   public void setExcludesEntityType() {
      this.excludesEntityType = true;
   }

   public boolean selectsEntityType() {
      return this.entityType != null;
   }

   public boolean excludesEntityType() {
      return this.excludesEntityType;
   }

   public boolean selectsScores() {
      return this.selectsScores;
   }

   public void setSelectsScores(boolean selectsScores) {
      this.selectsScores = selectsScores;
   }

   public boolean selectsAdvancements() {
      return this.selectsAdvancements;
   }

   public void setSelectsAdvancements(boolean selectsAdvancements) {
      this.selectsAdvancements = selectsAdvancements;
   }
}
