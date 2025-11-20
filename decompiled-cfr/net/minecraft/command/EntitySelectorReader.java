/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.primitives.Doubles
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.command;

import com.google.common.primitives.Doubles;
import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
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
import net.minecraft.command.EntitySelector;
import net.minecraft.command.EntitySelectorOptions;
import net.minecraft.command.FloatRangeArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.predicate.NumberRange;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntitySelectorReader {
    public static final SimpleCommandExceptionType INVALID_ENTITY_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("argument.entity.invalid"));
    public static final DynamicCommandExceptionType UNKNOWN_SELECTOR_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("argument.entity.selector.unknown", object));
    public static final SimpleCommandExceptionType NOT_ALLOWED_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("argument.entity.selector.not_allowed"));
    public static final SimpleCommandExceptionType MISSING_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("argument.entity.selector.missing"));
    public static final SimpleCommandExceptionType UNTERMINATED_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("argument.entity.options.unterminated"));
    public static final DynamicCommandExceptionType VALUELESS_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("argument.entity.options.valueless", object));
    public static final BiConsumer<Vec3d, List<? extends Entity>> ARBITRARY = (vec3d, list) -> {};
    public static final BiConsumer<Vec3d, List<? extends Entity>> NEAREST = (vec3d, list) -> list.sort((entity, entity2) -> Doubles.compare((double)entity.squaredDistanceTo((Vec3d)vec3d), (double)entity2.squaredDistanceTo((Vec3d)vec3d)));
    public static final BiConsumer<Vec3d, List<? extends Entity>> FURTHEST = (vec3d, list) -> list.sort((entity, entity2) -> Doubles.compare((double)entity2.squaredDistanceTo((Vec3d)vec3d), (double)entity.squaredDistanceTo((Vec3d)vec3d)));
    public static final BiConsumer<Vec3d, List<? extends Entity>> RANDOM = (vec3d, list) -> Collections.shuffle(list);
    public static final BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> DEFAULT_SUGGESTION_PROVIDER = (suggestionsBuilder, consumer) -> suggestionsBuilder.buildFuture();
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
    private Predicate<Entity> predicate = entity -> true;
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
        Box _snowman2;
        if (this.dx != null || this.dy != null || this.dz != null) {
            _snowman2 = this.createBox(this.dx == null ? 0.0 : this.dx, this.dy == null ? 0.0 : this.dy, this.dz == null ? 0.0 : this.dz);
        } else if (this.distance.getMax() != null) {
            float f = ((Float)this.distance.getMax()).floatValue();
            _snowman2 = new Box(-f, -f, -f, f + 1.0f, f + 1.0f, f + 1.0f);
        } else {
            _snowman2 = null;
        }
        Function<Vec3d, Vec3d> function = this.x == null && this.y == null && this.z == null ? vec3d -> vec3d : vec3d -> new Vec3d(this.x == null ? vec3d.x : this.x, this.y == null ? vec3d.y : this.y, this.z == null ? vec3d.z : this.z);
        return new EntitySelector(this.limit, this.includesNonPlayers, this.localWorldOnly, this.predicate, this.distance, function, _snowman2, this.sorter, this.senderOnly, this.playerName, this.uuid, this.entityType, this.usesAt);
    }

    private Box createBox(double x, double y, double z) {
        boolean bl = x < 0.0;
        _snowman = y < 0.0;
        _snowman = z < 0.0;
        double _snowman2 = bl ? x : 0.0;
        double _snowman3 = _snowman ? y : 0.0;
        double _snowman4 = _snowman ? z : 0.0;
        double _snowman5 = (bl ? 0.0 : x) + 1.0;
        double _snowman6 = (_snowman ? 0.0 : y) + 1.0;
        double _snowman7 = (_snowman ? 0.0 : z) + 1.0;
        return new Box(_snowman2, _snowman3, _snowman4, _snowman5, _snowman6, _snowman7);
    }

    private void buildPredicate() {
        if (this.pitchRange != FloatRangeArgument.ANY) {
            this.predicate = this.predicate.and(this.rotationPredicate(this.pitchRange, entity -> entity.pitch));
        }
        if (this.yawRange != FloatRangeArgument.ANY) {
            this.predicate = this.predicate.and(this.rotationPredicate(this.yawRange, entity -> entity.yaw));
        }
        if (!this.levelRange.isDummy()) {
            this.predicate = this.predicate.and(entity -> {
                if (!(entity instanceof ServerPlayerEntity)) {
                    return false;
                }
                return this.levelRange.test(((ServerPlayerEntity)entity).experienceLevel);
            });
        }
    }

    private Predicate<Entity> rotationPredicate(FloatRangeArgument floatRangeArgument, ToDoubleFunction<Entity> toDoubleFunction) {
        double d = MathHelper.wrapDegrees(floatRangeArgument.getMin() == null ? 0.0f : floatRangeArgument.getMin().floatValue());
        _snowman = MathHelper.wrapDegrees(floatRangeArgument.getMax() == null ? 359.0f : floatRangeArgument.getMax().floatValue());
        return entity -> {
            double d3 = MathHelper.wrapDegrees(toDoubleFunction.applyAsDouble((Entity)entity));
            if (d > _snowman) {
                return d3 >= d || d3 <= _snowman;
            }
            return d3 >= d && d3 <= _snowman;
        };
    }

    protected void readAtVariable() throws CommandSyntaxException {
        this.usesAt = true;
        this.suggestionProvider = this::suggestSelectorRest;
        if (!this.reader.canRead()) {
            throw MISSING_EXCEPTION.createWithContext((ImmutableStringReader)this.reader);
        }
        int n = this.reader.getCursor();
        char _snowman2 = this.reader.read();
        if (_snowman2 == 'p') {
            this.limit = 1;
            this.includesNonPlayers = false;
            this.sorter = NEAREST;
            this.setEntityType(EntityType.PLAYER);
        } else if (_snowman2 == 'a') {
            this.limit = Integer.MAX_VALUE;
            this.includesNonPlayers = false;
            this.sorter = ARBITRARY;
            this.setEntityType(EntityType.PLAYER);
        } else if (_snowman2 == 'r') {
            this.limit = 1;
            this.includesNonPlayers = false;
            this.sorter = RANDOM;
            this.setEntityType(EntityType.PLAYER);
        } else if (_snowman2 == 's') {
            this.limit = 1;
            this.includesNonPlayers = true;
            this.senderOnly = true;
        } else if (_snowman2 == 'e') {
            this.limit = Integer.MAX_VALUE;
            this.includesNonPlayers = true;
            this.sorter = ARBITRARY;
            this.predicate = Entity::isAlive;
        } else {
            this.reader.setCursor(n);
            throw UNKNOWN_SELECTOR_EXCEPTION.createWithContext((ImmutableStringReader)this.reader, (Object)('@' + String.valueOf(_snowman2)));
        }
        this.suggestionProvider = this::suggestOpen;
        if (this.reader.canRead() && this.reader.peek() == '[') {
            this.reader.skip();
            this.suggestionProvider = this::suggestOptionOrEnd;
            this.readArguments();
        }
    }

    protected void readRegular() throws CommandSyntaxException {
        if (this.reader.canRead()) {
            this.suggestionProvider = this::suggestNormal;
        }
        int n = this.reader.getCursor();
        String _snowman2 = this.reader.readString();
        try {
            this.uuid = UUID.fromString(_snowman2);
            this.includesNonPlayers = true;
        }
        catch (IllegalArgumentException _snowman3) {
            if (_snowman2.isEmpty() || _snowman2.length() > 16) {
                this.reader.setCursor(n);
                throw INVALID_ENTITY_EXCEPTION.createWithContext((ImmutableStringReader)this.reader);
            }
            this.includesNonPlayers = false;
            this.playerName = _snowman2;
        }
        this.limit = 1;
    }

    protected void readArguments() throws CommandSyntaxException {
        this.suggestionProvider = this::suggestOption;
        this.reader.skipWhitespace();
        while (this.reader.canRead() && this.reader.peek() != ']') {
            this.reader.skipWhitespace();
            int n = this.reader.getCursor();
            String _snowman2 = this.reader.readString();
            EntitySelectorOptions.SelectorHandler _snowman3 = EntitySelectorOptions.getHandler(this, _snowman2, n);
            this.reader.skipWhitespace();
            if (!this.reader.canRead() || this.reader.peek() != '=') {
                this.reader.setCursor(n);
                throw VALUELESS_EXCEPTION.createWithContext((ImmutableStringReader)this.reader, (Object)_snowman2);
            }
            this.reader.skip();
            this.reader.skipWhitespace();
            this.suggestionProvider = DEFAULT_SUGGESTION_PROVIDER;
            _snowman3.handle(this);
            this.reader.skipWhitespace();
            this.suggestionProvider = this::suggestEndNext;
            if (!this.reader.canRead()) continue;
            if (this.reader.peek() == ',') {
                this.reader.skip();
                this.suggestionProvider = this::suggestOption;
                continue;
            }
            if (this.reader.peek() == ']') break;
            throw UNTERMINATED_EXCEPTION.createWithContext((ImmutableStringReader)this.reader);
        }
        if (!this.reader.canRead()) {
            throw UNTERMINATED_EXCEPTION.createWithContext((ImmutableStringReader)this.reader);
        }
        this.reader.skip();
        this.suggestionProvider = DEFAULT_SUGGESTION_PROVIDER;
    }

    public boolean readNegationCharacter() {
        this.reader.skipWhitespace();
        if (this.reader.canRead() && this.reader.peek() == '!') {
            this.reader.skip();
            this.reader.skipWhitespace();
            return true;
        }
        return false;
    }

    public boolean readTagCharacter() {
        this.reader.skipWhitespace();
        if (this.reader.canRead() && this.reader.peek() == '#') {
            this.reader.skip();
            this.reader.skipWhitespace();
            return true;
        }
        return false;
    }

    public StringReader getReader() {
        return this.reader;
    }

    public void setPredicate(Predicate<Entity> predicate) {
        this.predicate = this.predicate.and(predicate);
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

    public void setPitchRange(FloatRangeArgument floatRangeArgument) {
        this.pitchRange = floatRangeArgument;
    }

    public FloatRangeArgument getYawRange() {
        return this.yawRange;
    }

    public void setYawRange(FloatRangeArgument floatRangeArgument) {
        this.yawRange = floatRangeArgument;
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
                throw NOT_ALLOWED_EXCEPTION.createWithContext((ImmutableStringReader)this.reader);
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
        builder.suggest("@p", (Message)new TranslatableText("argument.entity.selector.nearestPlayer"));
        builder.suggest("@a", (Message)new TranslatableText("argument.entity.selector.allPlayers"));
        builder.suggest("@r", (Message)new TranslatableText("argument.entity.selector.randomPlayer"));
        builder.suggest("@s", (Message)new TranslatableText("argument.entity.selector.self"));
        builder.suggest("@e", (Message)new TranslatableText("argument.entity.selector.allEntities"));
    }

    private CompletableFuture<Suggestions> suggestSelector(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> consumer) {
        consumer.accept(builder);
        if (this.atAllowed) {
            EntitySelectorReader.suggestSelector(builder);
        }
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestNormal(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> consumer) {
        SuggestionsBuilder suggestionsBuilder = builder.createOffset(this.startCursor);
        consumer.accept(suggestionsBuilder);
        return builder.add(suggestionsBuilder).buildFuture();
    }

    private CompletableFuture<Suggestions> suggestSelectorRest(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> consumer) {
        SuggestionsBuilder suggestionsBuilder = builder.createOffset(builder.getStart() - 1);
        EntitySelectorReader.suggestSelector(suggestionsBuilder);
        builder.add(suggestionsBuilder);
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOpen(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> consumer) {
        builder.suggest(String.valueOf('['));
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOptionOrEnd(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> consumer) {
        builder.suggest(String.valueOf(']'));
        EntitySelectorOptions.suggestOptions(this, builder);
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestOption(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> consumer) {
        EntitySelectorOptions.suggestOptions(this, builder);
        return builder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestEndNext(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> consumer) {
        builder.suggest(String.valueOf(','));
        builder.suggest(String.valueOf(']'));
        return builder.buildFuture();
    }

    public boolean isSenderOnly() {
        return this.senderOnly;
    }

    public void setSuggestionProvider(BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> biFunction) {
        this.suggestionProvider = biFunction;
    }

    public CompletableFuture<Suggestions> listSuggestions(SuggestionsBuilder builder, Consumer<SuggestionsBuilder> consumer) {
        return this.suggestionProvider.apply(builder.createOffset(this.reader.getCursor()), consumer);
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

    public void setExcludesName(boolean bl) {
        this.excludesName = bl;
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

