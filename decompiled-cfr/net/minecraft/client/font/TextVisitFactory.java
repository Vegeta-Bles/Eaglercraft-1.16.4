/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.font;

import java.util.Optional;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import net.minecraft.util.Unit;

public class TextVisitFactory {
    private static final Optional<Object> VISIT_TERMINATED = Optional.of(Unit.INSTANCE);

    private static boolean visitRegularCharacter(Style style, CharacterVisitor visitor, int index, char c) {
        if (Character.isSurrogate(c)) {
            return visitor.accept(index, style, 65533);
        }
        return visitor.accept(index, style, c);
    }

    public static boolean visitForwards(String text, Style style, CharacterVisitor visitor) {
        int n = text.length();
        for (_snowman = 0; _snowman < n; ++_snowman) {
            char c = text.charAt(_snowman);
            if (Character.isHighSurrogate(c)) {
                if (_snowman + 1 >= n) {
                    if (visitor.accept(_snowman, style, 65533)) break;
                    return false;
                }
                _snowman = text.charAt(_snowman + 1);
                if (Character.isLowSurrogate(_snowman)) {
                    if (!visitor.accept(_snowman, style, Character.toCodePoint(c, _snowman))) {
                        return false;
                    }
                    ++_snowman;
                    continue;
                }
                if (visitor.accept(_snowman, style, 65533)) continue;
                return false;
            }
            if (TextVisitFactory.visitRegularCharacter(style, visitor, _snowman, c)) continue;
            return false;
        }
        return true;
    }

    public static boolean visitBackwards(String text, Style style, CharacterVisitor visitor) {
        int n = text.length();
        for (_snowman = n - 1; _snowman >= 0; --_snowman) {
            char c = text.charAt(_snowman);
            if (Character.isLowSurrogate(c)) {
                if (_snowman - 1 < 0) {
                    if (visitor.accept(0, style, 65533)) break;
                    return false;
                }
                _snowman = text.charAt(_snowman - 1);
                if (!(Character.isHighSurrogate(_snowman) ? !visitor.accept(--_snowman, style, Character.toCodePoint(_snowman, c)) : !visitor.accept(_snowman, style, 65533))) continue;
                return false;
            }
            if (TextVisitFactory.visitRegularCharacter(style, visitor, _snowman, c)) continue;
            return false;
        }
        return true;
    }

    public static boolean visitFormatted(String text, Style style, CharacterVisitor visitor) {
        return TextVisitFactory.visitFormatted(text, 0, style, visitor);
    }

    public static boolean visitFormatted(String text, int startIndex, Style style, CharacterVisitor visitor) {
        return TextVisitFactory.visitFormatted(text, startIndex, style, style, visitor);
    }

    public static boolean visitFormatted(String text, int startIndex, Style startingStyle, Style resetStyle, CharacterVisitor visitor) {
        int n = text.length();
        Style _snowman2 = startingStyle;
        for (_snowman = startIndex; _snowman < n; ++_snowman) {
            char c = text.charAt(_snowman);
            if (c == '\u00a7') {
                if (_snowman + 1 >= n) break;
                _snowman = text.charAt(_snowman + 1);
                Formatting formatting = Formatting.byCode(_snowman);
                if (formatting != null) {
                    _snowman2 = formatting == Formatting.RESET ? resetStyle : _snowman2.withExclusiveFormatting(formatting);
                }
                ++_snowman;
                continue;
            }
            if (Character.isHighSurrogate(c)) {
                if (_snowman + 1 >= n) {
                    if (visitor.accept(_snowman, _snowman2, 65533)) break;
                    return false;
                }
                _snowman = text.charAt(_snowman + 1);
                if (Character.isLowSurrogate(_snowman)) {
                    if (!visitor.accept(_snowman, _snowman2, Character.toCodePoint(c, _snowman))) {
                        return false;
                    }
                    ++_snowman;
                    continue;
                }
                if (visitor.accept(_snowman, _snowman2, 65533)) continue;
                return false;
            }
            if (TextVisitFactory.visitRegularCharacter(_snowman2, visitor, _snowman, c)) continue;
            return false;
        }
        return true;
    }

    public static boolean visitFormatted(StringVisitable text, Style style2, CharacterVisitor visitor) {
        return !text.visit((style, string) -> TextVisitFactory.visitFormatted(string, 0, style, visitor) ? Optional.empty() : VISIT_TERMINATED, style2).isPresent();
    }

    public static String validateSurrogates(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        TextVisitFactory.visitForwards(text, Style.EMPTY, (n, style, n2) -> {
            stringBuilder.appendCodePoint(n2);
            return true;
        });
        return stringBuilder.toString();
    }

    public static String method_31402(StringVisitable stringVisitable) {
        StringBuilder stringBuilder = new StringBuilder();
        TextVisitFactory.visitFormatted(stringVisitable, Style.EMPTY, (int n, Style style, int n2) -> {
            stringBuilder.appendCodePoint(n2);
            return true;
        });
        return stringBuilder.toString();
    }
}

