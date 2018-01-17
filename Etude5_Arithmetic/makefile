build: ArithmeticApp.java Arithmetic.java Option.java
	javac -Xlint ArithmeticApp.java Arithmetic.java Option.java -d .

test: ArithmeticApp.java Arithmetic.java valid_input
	make build
	java arithmetic.ArithmeticApp < valid_input
