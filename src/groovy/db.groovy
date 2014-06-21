import groovy.sql.Sql

println "Starting groovy script!"

// Iterate through the library directory and dynamically load the jars into the system class path
// http://groovy.codehaus.org/Influencing+class+loading+at+runtime
def jardir = new File('src/lib/')
def jars   = jardir.listFiles().findAll { it.name.endsWith('.jar') }
jars.each {
    // As per http://jira.codehaus.org/browse/GROOVY-3583 we need to load the jar into the system class loader
    ClassLoader.systemClassLoader.addURL(it.toURI().toURL())
}

Class.forName('com.mysql.jdbc.Driver').newInstance()
sql = Sql.newInstance('jdbc:mysql://localhost:3306/mysql', 'root', 'password', 'com.mysql.jdbc.Driver')
println sql.firstRow('select * from db')

println "Ending groovy script!"
