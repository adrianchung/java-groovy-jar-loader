import groovy.sql.Sql

println "Starting groovy script!"

this.class.classLoader.URLs.each{ println it }
this.class.classLoader.systemClassLoader.URLs.each{ println it }

sql = Sql.newInstance('jdbc:mysql://localhost:3306/mysql', 'root', 'abc123', 'com.mysql.jdbc.Driver')
println sql.firstRow('select * from user')

println "Ending groovy script!"
