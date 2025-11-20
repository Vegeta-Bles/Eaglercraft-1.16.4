/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.text;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.BaseText;
import net.minecraft.text.MutableText;
import net.minecraft.text.ParsableText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslationException;
import net.minecraft.util.Language;

public class TranslatableText
extends BaseText
implements ParsableText {
    private static final Object[] EMPTY_ARGUMENTS = new Object[0];
    private static final StringVisitable LITERAL_PERCENT_SIGN = StringVisitable.plain("%");
    private static final StringVisitable NULL_ARGUMENT = StringVisitable.plain("null");
    private final String key;
    private final Object[] args;
    @Nullable
    private Language languageCache;
    private final List<StringVisitable> translations = Lists.newArrayList();
    private static final Pattern ARG_FORMAT = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

    public TranslatableText(String key) {
        this.key = key;
        this.args = EMPTY_ARGUMENTS;
    }

    public TranslatableText(String key, Object ... args) {
        this.key = key;
        this.args = args;
    }

    private void updateTranslations() {
        Language language = Language.getInstance();
        if (language == this.languageCache) {
            return;
        }
        this.languageCache = language;
        this.translations.clear();
        String _snowman2 = language.get(this.key);
        try {
            this.setTranslation(_snowman2);
        }
        catch (TranslationException _snowman3) {
            this.translations.clear();
            this.translations.add(StringVisitable.plain(_snowman2));
        }
    }

    private void setTranslation(String translation) {
        Matcher matcher = ARG_FORMAT.matcher(translation);
        try {
            int n;
            int n2 = 0;
            n = 0;
            while (matcher.find(n)) {
                int n3;
                String _snowman2;
                _snowman = matcher.start();
                n3 = matcher.end();
                if (_snowman > n) {
                    _snowman2 = translation.substring(n, _snowman);
                    if (_snowman2.indexOf(37) != -1) {
                        throw new IllegalArgumentException();
                    }
                    this.translations.add(StringVisitable.plain(_snowman2));
                }
                _snowman2 = matcher.group(2);
                String _snowman3 = translation.substring(_snowman, n3);
                if ("%".equals(_snowman2) && "%%".equals(_snowman3)) {
                    this.translations.add(LITERAL_PERCENT_SIGN);
                } else if ("s".equals(_snowman2)) {
                    String string = matcher.group(1);
                    int n4 = _snowman = string != null ? Integer.parseInt(string) - 1 : n2++;
                    if (_snowman < this.args.length) {
                        this.translations.add(this.getArg(_snowman));
                    }
                } else {
                    throw new TranslationException(this, "Unsupported format: '" + _snowman3 + "'");
                }
                n = n3;
            }
            if (n < translation.length()) {
                String string = translation.substring(n);
                if (string.indexOf(37) != -1) {
                    throw new IllegalArgumentException();
                }
                this.translations.add(StringVisitable.plain(string));
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new TranslationException(this, (Throwable)illegalArgumentException);
        }
    }

    private StringVisitable getArg(int index) {
        if (index >= this.args.length) {
            throw new TranslationException(this, index);
        }
        Object object = this.args[index];
        if (object instanceof Text) {
            return (Text)object;
        }
        return object == null ? NULL_ARGUMENT : StringVisitable.plain(object.toString());
    }

    @Override
    public TranslatableText copy() {
        return new TranslatableText(this.key, this.args);
    }

    @Override
    public <T> Optional<T> visitSelf(StringVisitable.StyledVisitor<T> visitor, Style style) {
        this.updateTranslations();
        for (StringVisitable stringVisitable : this.translations) {
            Optional<T> optional = stringVisitable.visit(visitor, style);
            if (!optional.isPresent()) continue;
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public <T> Optional<T> visitSelf(StringVisitable.Visitor<T> visitor) {
        this.updateTranslations();
        for (StringVisitable stringVisitable : this.translations) {
            Optional<T> optional = stringVisitable.visit(visitor);
            if (!optional.isPresent()) continue;
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public MutableText parse(@Nullable ServerCommandSource source, @Nullable Entity sender, int depth) throws CommandSyntaxException {
        Object[] objectArray = new Object[this.args.length];
        for (int i = 0; i < objectArray.length; ++i) {
            Object object = this.args[i];
            objectArray[i] = object instanceof Text ? Texts.parse(source, (Text)object, sender, depth) : object;
        }
        return new TranslatableText(this.key, objectArray);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof TranslatableText) {
            TranslatableText translatableText = (TranslatableText)object;
            return Arrays.equals(this.args, translatableText.args) && this.key.equals(translatableText.key) && super.equals(object);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = 31 * n + this.key.hashCode();
        n = 31 * n + Arrays.hashCode(this.args);
        return n;
    }

    @Override
    public String toString() {
        return "TranslatableComponent{key='" + this.key + '\'' + ", args=" + Arrays.toString(this.args) + ", siblings=" + this.siblings + ", style=" + this.getStyle() + '}';
    }

    public String getKey() {
        return this.key;
    }

    public Object[] getArgs() {
        return this.args;
    }

    @Override
    public /* synthetic */ BaseText copy() {
        return this.copy();
    }

    @Override
    public /* synthetic */ MutableText copy() {
        return this.copy();
    }
}

