package ca.adrianchung;

import org.codehaus.groovy.control.CompilerConfiguration;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

public class Runner {
    public void run() throws IOException {
        File scriptFile = new File("src/groovy/db.groovy");

        ClassLoader parent = getClass().getClassLoader();
        GroovyClassLoader loader = new GroovyClassLoader(parent);
        File libDir = new File("src/lib/");
        for (File jar : libDir.listFiles()) {
            if (jar.getName().endsWith(".jar")) {
                try {
                    URL url = jar.toURI().toURL();
                    loader.addURL(url);
                } catch (MalformedURLException e) {
                    System.out.println(e.toString());
                }
            }
        }

        Binding binding = new Binding();
        binding.setProperty("libDir", libDir);

        //List<String> cp = new ArrayList<String>();
        //cp.add("/home/achung/dev/personal/java-groovy-jar-loader/src/lib/mysql-connector-java-5.1.27.jar");

        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setClasspath(libDir.getAbsolutePath());
        compilerConfiguration.setScriptBaseClass("ca.adrianchung.Runner.BaseScript");

        GroovyShell groovyShell = new GroovyShell(loader, binding, compilerConfiguration);
        Script script = groovyShell.parse(scriptFile);
        script.invokeMethod("__setup", null);
        script.run();
    }

    public static void main(String[] args) throws IOException {
        Runner runner = new Runner();
        runner.run();
    }

    public static abstract class BaseScript extends Script {
        public BaseScript() {

        }

        public void __setup() throws MalformedURLException {


            /*evaluate(
                "println ClassLoader.systemClassLoader.class.getName()\n"+
                "println ClassLoader.class.getName()\n"+
                "def jars = libDir.listFiles().findAll { it.name.endsWith('.jar') }\n" +
                "jars.each {\n" +
                "ClassLoader.systemClassLoader.addURL(it.toURI().toURL())\n" +
                "}");*/
            System.out.println("__setup()");
            File libDir = (File)getProperty("libDir");
            GroovyClassLoader groovyClassLoader = new GroovyClassLoader(getClass().getClassLoader());
            for (File jar : libDir.listFiles()) {
                if (jar.getName().endsWith(".jar")) {
                    try {
                        URL url = jar.toURI().toURL();
                        groovyClassLoader.addURL(url);
                    } catch (MalformedURLException e) {
                        System.out.println(e.toString());
                    }
                }
            }
        }
    }
}
