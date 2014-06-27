import groovy.sql.Sql

println "Starting groovy script!"

def jardir = new File('src/lib/')
def jars   = jardir.listFiles().findAll { it.name.endsWith('.jar') }
jars.each {
    ClassLoader.systemClassLoader.addURL(it.toURI().toURL())
}

sql = Sql.newInstance('jdbc:mysql://localhost:3306/mysql', 'root', 'password', 'com.mysql.jdbc.Driver')
println sql.firstRow('select * from user')

println "Ending groovy script!"
