package ca.adrianchung;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;

import java.io.File;
import java.io.IOException;

public class Runner {
    public void run() throws IOException {
        File scriptFile = new File("src/groovy/db.groovy");

        ClassLoader parent = getClass().getClassLoader();
        GroovyClassLoader loader = new GroovyClassLoader(parent);
        Binding binding = new Binding();

        GroovyShell groovyShell = new GroovyShell(loader, binding);
        groovyShell.run(scriptFile, new String[] {});
    }

    public static void main(String[] args) throws IOException {
        Runner runner = new Runner();
        runner.run();
    }
}
