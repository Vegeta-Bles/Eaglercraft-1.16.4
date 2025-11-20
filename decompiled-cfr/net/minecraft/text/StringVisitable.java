/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.text;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.text.Style;
import net.minecraft.util.Unit;

public interface StringVisitable {
    public static final Optional<Unit> TERMINATE_VISIT = Optional.of(Unit.INSTANCE);
    public static final StringVisitable EMPTY = new StringVisitable(){

        public <T> Optional<T> visit(Visitor<T> visitor) {
            return Optional.empty();
        }

        public <T> Optional<T> visit(StyledVisitor<T> styledVisitor, Style style) {
            return Optional.empty();
        }
    };

    public <T> Optional<T> visit(Visitor<T> var1);

    public <T> Optional<T> visit(StyledVisitor<T> var1, Style var2);

    public static StringVisitable plain(String string) {
        return new StringVisitable(string){
            final /* synthetic */ String field_25311;
            {
                this.field_25311 = string;
            }

            public <T> Optional<T> visit(Visitor<T> visitor) {
                return visitor.accept(this.field_25311);
            }

            public <T> Optional<T> visit(StyledVisitor<T> styledVisitor, Style style) {
                return styledVisitor.accept(style, this.field_25311);
            }
        };
    }

    public static StringVisitable styled(String string, Style style) {
        return new StringVisitable(string, style){
            final /* synthetic */ String field_25312;
            final /* synthetic */ Style field_25313;
            {
                this.field_25312 = string;
                this.field_25313 = style;
            }

            public <T> Optional<T> visit(Visitor<T> visitor) {
                return visitor.accept(this.field_25312);
            }

            public <T> Optional<T> visit(StyledVisitor<T> styledVisitor, Style style) {
                return styledVisitor.accept(this.field_25313.withParent(style), this.field_25312);
            }
        };
    }

    public static StringVisitable concat(StringVisitable ... visitables) {
        return StringVisitable.concat((List<StringVisitable>)ImmutableList.copyOf((Object[])visitables));
    }

    public static StringVisitable concat(List<StringVisitable> visitables) {
        return new StringVisitable(visitables){
            final /* synthetic */ List field_25314;
            {
                this.field_25314 = list;
            }

            public <T> Optional<T> visit(Visitor<T> visitor) {
                for (StringVisitable stringVisitable : this.field_25314) {
                    Optional<T> optional = stringVisitable.visit(visitor);
                    if (!optional.isPresent()) continue;
                    return optional;
                }
                return Optional.empty();
            }

            public <T> Optional<T> visit(StyledVisitor<T> styledVisitor, Style style) {
                for (StringVisitable stringVisitable : this.field_25314) {
                    Optional<T> optional = stringVisitable.visit(styledVisitor, style);
                    if (!optional.isPresent()) continue;
                    return optional;
                }
                return Optional.empty();
            }
        };
    }

    default public String getString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.visit(string -> {
            stringBuilder.append(string);
            return Optional.empty();
        });
        return stringBuilder.toString();
    }

    public static interface Visitor<T> {
        public Optional<T> accept(String var1);
    }

    public static interface StyledVisitor<T> {
        public Optional<T> accept(Style var1, String var2);
    }
}

