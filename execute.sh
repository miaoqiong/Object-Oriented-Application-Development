javac -g -d classes -cp "lib/*" -sourcepath src src/is201/g5t08/farmcity/FarmCityApp.java
# java -cp "classes:lib/*" -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=1044 is201.g5t08.farmcity.FarmCityApp
java -cp "classes:lib/*" is201.g5t08.farmcity.FarmCityApp
