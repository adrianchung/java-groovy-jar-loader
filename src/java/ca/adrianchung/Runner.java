package ca.adrianchung;

import java.io.File;
import java.io.IOException;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;

public class Runner {
    public void run() throws IOException {
        File scriptFile = new File("src/groovy/db.groovy");

        ClassLoader parent = getClass().getClassLoader();
        GroovyClassLoader loader = new GroovyClassLoader(parent);

        File jars = new File("src/lib");
        for (File jar : jars.listFiles()) {
            if (jar.getName().endsWith(".jar")) {
                loader.addURL(jar.toURI().toURL());
            }
        }

        Binding binding = new Binding();

        GroovyShell groovyShell = new GroovyShell(loader, binding);
        groovyShell.evaluate(scriptFile);
    }

    public static void main(String[] args) throws IOException {
        Runner runner = new Runner();
        runner.run();
    }
}
