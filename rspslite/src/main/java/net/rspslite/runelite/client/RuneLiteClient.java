package net.rspslite.runelite.client;

import java.io.File;
import java.io.IOException;
import net.rspslite.injector.JarInjector;

public class RuneLiteClient {

  public static void injectClient(String clientJarPath, String[] jarDependencies) {
    String tmpInjectedJarPath = clientJarPath + ".tmp";

    try {
      JarInjector.inject(clientJarPath,
                         tmpInjectedJarPath,
                         RuneLiteClientInjector.getInjectors(),
                         jarDependencies,
                         (name) -> name.equals("META-INF/MANIFEST.MF"));

      File clientJar = new File(clientJarPath);
      File tmpInjectedJar = new File(tmpInjectedJarPath);
      clientJar.delete();
      boolean success = tmpInjectedJar.renameTo(clientJar);

      if (!success) {
        System.err.println("RuneLite injected client JAR could not be moved to replace original");
        System.exit(1);
      }
    }
    catch (IOException e) {
      System.err.println("RuneLite client could not be injected");
      System.exit(1);
    }
  }

}
