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

   public EulaReader(Path path) {
      this.eulaFile = path;
      this.eulaAgreedTo = SharedConstants.isDevelopment || this.checkEulaAgreement();
   }

   private boolean checkEulaAgreement() {
      try (InputStream inputStream = Files.newInputStream(this.eulaFile)) {
         Properties properties = new Properties();
         properties.load(inputStream);
         return Boolean.parseBoolean(properties.getProperty("eula", "false"));
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
         try (OutputStream outputStream = Files.newOutputStream(this.eulaFile)) {
            Properties properties = new Properties();
            properties.setProperty("eula", "false");
            properties.store(
               outputStream,
               "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula)."
            );
         } catch (Exception var14) {
            LOGGER.warn("Failed to save {}", this.eulaFile, var14);
         }
      }
   }
}
