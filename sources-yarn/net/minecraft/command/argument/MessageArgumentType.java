package net.minecraft.command.argument;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.EntitySelectorReader;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class MessageArgumentType implements ArgumentType<MessageArgumentType.MessageFormat> {
   private static final Collection<String> EXAMPLES = Arrays.asList("Hello world!", "foo", "@e", "Hello @p :)");

   public MessageArgumentType() {
   }

   public static MessageArgumentType message() {
      return new MessageArgumentType();
   }

   public static Text getMessage(CommandContext<ServerCommandSource> command, String name) throws CommandSyntaxException {
      return ((MessageArgumentType.MessageFormat)command.getArgument(name, MessageArgumentType.MessageFormat.class))
         .format((ServerCommandSource)command.getSource(), ((ServerCommandSource)command.getSource()).hasPermissionLevel(2));
   }

   public MessageArgumentType.MessageFormat parse(StringReader stringReader) throws CommandSyntaxException {
      return MessageArgumentType.MessageFormat.parse(stringReader, true);
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   public static class MessageFormat {
      private final String contents;
      private final MessageArgumentType.MessageSelector[] selectors;

      public MessageFormat(String string, MessageArgumentType.MessageSelector[] args) {
         this.contents = string;
         this.selectors = args;
      }

      public Text format(ServerCommandSource arg, boolean bl) throws CommandSyntaxException {
         if (this.selectors.length != 0 && bl) {
            MutableText lv = new LiteralText(this.contents.substring(0, this.selectors[0].getStart()));
            int i = this.selectors[0].getStart();

            for (MessageArgumentType.MessageSelector lv2 : this.selectors) {
               Text lv3 = lv2.format(arg);
               if (i < lv2.getStart()) {
                  lv.append(this.contents.substring(i, lv2.getStart()));
               }

               if (lv3 != null) {
                  lv.append(lv3);
               }

               i = lv2.getEnd();
            }

            if (i < this.contents.length()) {
               lv.append(this.contents.substring(i, this.contents.length()));
            }

            return lv;
         } else {
            return new LiteralText(this.contents);
         }
      }

      public static MessageArgumentType.MessageFormat parse(StringReader stringReader, boolean bl) throws CommandSyntaxException {
         String string = stringReader.getString().substring(stringReader.getCursor(), stringReader.getTotalLength());
         if (!bl) {
            stringReader.setCursor(stringReader.getTotalLength());
            return new MessageArgumentType.MessageFormat(string, new MessageArgumentType.MessageSelector[0]);
         } else {
            List<MessageArgumentType.MessageSelector> list = Lists.newArrayList();
            int i = stringReader.getCursor();

            while (true) {
               int j;
               EntitySelector lv2;
               while (true) {
                  if (!stringReader.canRead()) {
                     return new MessageArgumentType.MessageFormat(string, list.toArray(new MessageArgumentType.MessageSelector[list.size()]));
                  }

                  if (stringReader.peek() == '@') {
                     j = stringReader.getCursor();

                     try {
                        EntitySelectorReader lv = new EntitySelectorReader(stringReader);
                        lv2 = lv.read();
                        break;
                     } catch (CommandSyntaxException var8) {
                        if (var8.getType() != EntitySelectorReader.MISSING_EXCEPTION && var8.getType() != EntitySelectorReader.UNKNOWN_SELECTOR_EXCEPTION) {
                           throw var8;
                        }

                        stringReader.setCursor(j + 1);
                     }
                  } else {
                     stringReader.skip();
                  }
               }

               list.add(new MessageArgumentType.MessageSelector(j - i, stringReader.getCursor() - i, lv2));
            }
         }
      }
   }

   public static class MessageSelector {
      private final int start;
      private final int end;
      private final EntitySelector selector;

      public MessageSelector(int i, int j, EntitySelector arg) {
         this.start = i;
         this.end = j;
         this.selector = arg;
      }

      public int getStart() {
         return this.start;
      }

      public int getEnd() {
         return this.end;
      }

      @Nullable
      public Text format(ServerCommandSource arg) throws CommandSyntaxException {
         return EntitySelector.getNames(this.selector.getEntities(arg));
      }
   }
}
