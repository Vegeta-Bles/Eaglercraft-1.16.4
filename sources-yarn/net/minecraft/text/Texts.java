package net.minecraft.text;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Formatting;

public class Texts {
   public static MutableText setStyleIfAbsent(MutableText text, Style style) {
      if (style.isEmpty()) {
         return text;
      } else {
         Style lv = text.getStyle();
         if (lv.isEmpty()) {
            return text.setStyle(style);
         } else {
            return lv.equals(style) ? text : text.setStyle(lv.withParent(style));
         }
      }
   }

   public static MutableText parse(@Nullable ServerCommandSource source, Text text, @Nullable Entity sender, int depth) throws CommandSyntaxException {
      if (depth > 100) {
         return text.shallowCopy();
      } else {
         MutableText lv = text instanceof ParsableText ? ((ParsableText)text).parse(source, sender, depth + 1) : text.copy();

         for (Text lv2 : text.getSiblings()) {
            lv.append(parse(source, lv2, sender, depth + 1));
         }

         return lv.fillStyle(method_27663(source, text.getStyle(), sender, depth));
      }
   }

   private static Style method_27663(@Nullable ServerCommandSource arg, Style arg2, @Nullable Entity arg3, int i) throws CommandSyntaxException {
      HoverEvent lv = arg2.getHoverEvent();
      if (lv != null) {
         Text lv2 = lv.getValue(HoverEvent.Action.SHOW_TEXT);
         if (lv2 != null) {
            HoverEvent lv3 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, parse(arg, lv2, arg3, i + 1));
            return arg2.withHoverEvent(lv3);
         }
      }

      return arg2;
   }

   public static Text toText(GameProfile profile) {
      if (profile.getName() != null) {
         return new LiteralText(profile.getName());
      } else {
         return profile.getId() != null ? new LiteralText(profile.getId().toString()) : new LiteralText("(unknown)");
      }
   }

   public static Text joinOrdered(Collection<String> strings) {
      return joinOrdered(strings, string -> new LiteralText(string).formatted(Formatting.GREEN));
   }

   public static <T extends Comparable<T>> Text joinOrdered(Collection<T> elements, Function<T, Text> transformer) {
      if (elements.isEmpty()) {
         return LiteralText.EMPTY;
      } else if (elements.size() == 1) {
         return transformer.apply(elements.iterator().next());
      } else {
         List<T> list = Lists.newArrayList(elements);
         list.sort(Comparable::compareTo);
         return join(list, transformer);
      }
   }

   public static <T> MutableText join(Collection<T> elements, Function<T, Text> transformer) {
      if (elements.isEmpty()) {
         return new LiteralText("");
      } else if (elements.size() == 1) {
         return transformer.apply(elements.iterator().next()).shallowCopy();
      } else {
         MutableText lv = new LiteralText("");
         boolean bl = true;

         for (T object : elements) {
            if (!bl) {
               lv.append(new LiteralText(", ").formatted(Formatting.GRAY));
            }

            lv.append(transformer.apply(object));
            bl = false;
         }

         return lv;
      }
   }

   public static MutableText bracketed(Text text) {
      return new TranslatableText("chat.square_brackets", text);
   }

   public static Text toText(Message message) {
      return (Text)(message instanceof Text ? (Text)message : new LiteralText(message.getString()));
   }
}
