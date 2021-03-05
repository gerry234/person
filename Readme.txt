With this program, you can parse Person entities from comma separated records and upload them 
on a server.
It can act as a text filter, which parse comma separated values fields from standard input and output
json formatted text.


To build it, you just need maven. 
There is a packaged jar file, target/PersonCSV-1.0.jar, which can be downloaded and run without compiling.

Some usage examples:

java -jar PersonCSV-1.0.jar filter

will wait for user input in the form: first_name,surname,age,nationality,favourite_colour.

java -jar PersonCSV-1.0.jar -help

will show a brief help page.

java -jar PersonCSV-1.0.jar upload 

will wait for user input and upload each record on a remote server (now localhost, but is configurable).

java -jar PersonCSV-1.0.jar -b filter
java -jar PersonCSV-1.0.jar -b upload 

will perform the same operation as above, but will stock person entities and use them as a person array,
printing or uploading them all in the same operation.

java -jar PersonCSV-1.0.jar -f person.csv filter
java -jar PersonCSV-1.0.jar -f person.csv upload 

will read csv records from the file person.csv. Input redirection works as well,
so you can use:

cat person.csv| java -jar PersonCSV-1.0.jar -b filter

or 

java -jar PersonCSV-1.0.jar -b upload<person.csv

java -jar PersonCSV-1.0.jar -q filter
java -jar PersonCSV-1.0.jar -q upload 

will suppress output from the program.

java -jar PersonCSV-1.0.jar -v filter
java -jar PersonCSV-1.0.jar -v upload 

will enable some debug messages.

It is possible to change the order of the fields, by using the switch -h as in:

java -jar PersonCSV-1.0.jar -h -f personsswapped.csv filter
java -jar PersonCSV-1.0.jar -h -f personsswapped.csv upload 

In this case, the first row of the input have to be the fields list, again in
comma separated format.

The files person.csv and personsswapped.csv can be found in the target directory.
It is possible to change the url of the target server, with a configuration file in
.properties format.
There is a model for such a file in the src/main/resources directory.

java -jar PersonCSV-1.0.jar -h -p newprops.properties -f personsswapped.csv upload 

will use the file newprops.properties to search for the server.

By default, a POST request is sent to http://localhost:8080/insert, the json variable
is sent as the request variable "person". 



