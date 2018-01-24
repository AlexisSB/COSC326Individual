quilting : *.java
	javac -Xlint *.java -d .

run : *.java
	javac -Xlint *.java -d .
	java quilting.QuiltingApp

test : *.java test_input
	javac -Xlint *.java -d .
	java quilting.QuiltingApp < test_input

clean :
	rm -r quilting
