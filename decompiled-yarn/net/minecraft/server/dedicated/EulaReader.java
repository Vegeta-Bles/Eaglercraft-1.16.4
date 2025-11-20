package net.minecraft.server.dedicated;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import net.minecraft.SharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EulaReader {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Path eulaFile;
   private final boolean eulaAgreedTo;

   public EulaReader(Path _snowman) {
      this.eulaFile = _snowman;
      this.eulaAgreedTo = SharedConstants.isDevelopment || this.checkEulaAgreement();
   }

   private boolean checkEulaAgreement() {
      try (InputStream _snowman = Files.newInputStream(this.eulaFile)) {
         Properties _snowmanx = new Properties();
         _snowmanx.load(_snowman);
         return Boolean.parseBoolean(_snowmanx.getProperty("eula", "false"));
      } catch (Exception var16) {
         LOGGER.warn("Failed to load {}", this.eulaFile);
         this.createEulaFile();
         return false;
      }
   }

   public boolean isEulaAgreedTo() {
      return this.eulaAgreedTo;
   }

   private void createEulaFile() {
      if (!SharedConstants.isDevelopment) {
         try (OutputStream _snowman = Files.newOutputStream(this.eulaFile)) {
            Properties _snowmanx = new Properties();
            _snowmanx.setProperty("eula", "false");
            _snowmanx.store(
               _snowman, "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula)."
            );
         } catch (Exception var14) {
            LOGGER.warn("Failed to save {}", this.eulaFile, var14);
         }
      }
   }
}
