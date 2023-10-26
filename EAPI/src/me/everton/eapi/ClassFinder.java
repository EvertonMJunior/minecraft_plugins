package me.everton.eapi;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassFinder {

    @SuppressWarnings("deprecation")
    public static List<Class> getClassesForPackage(String pack) throws IOException, ClassNotFoundException {
        String dottedPackage = pack.replaceAll("[/]", ".");
        List<Class> classes = new ArrayList<>();
        final ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL upackage = cl.getResource(pack);

        DataInputStream dis = new DataInputStream((InputStream) upackage.getContent());
        String line = null;
        while ((line = dis.readLine()) != null) {
            if (line.endsWith(".class")) {
                classes.add(Class.forName(dottedPackage + "." + line.substring(0, line.lastIndexOf('.'))));
            }
        }
        return classes;
    }

    public static Set<Class> getClassesInPackage(String packageName) {
        Set<Class> classes = new HashSet<>();
        String packageNameSlashed = "/" + packageName.replace(".", "/");
        URL directoryURL = Thread.currentThread().getContextClassLoader().getResource(packageNameSlashed);
        if (directoryURL == null) {
            return classes;
        }

        String directoryString = directoryURL.getFile();
        if (directoryString == null) {
            return classes;
        }

        File directory = new File(directoryString);
        if (directory.exists()) {
            String[] files = directory.list();
            for (String fileName : files) {
                if (fileName.endsWith(".class")) {
                    fileName = fileName.substring(0, fileName.length() - 6);
                    try {
                        classes.add(Class.forName(packageName + "." + fileName));
                    } catch (ClassNotFoundException e) {
                    }
                }
            }
        } else {
        }
        return classes;
    }
}
