telephone: Telephone.java numbers.txt correct_output
	javac -Xlint Telephone.java -d .
	java telephone.Telephone < numbers.txt

test : Telephone.java numbers.txt correct_output
	javac -Xlint Telephone.java -d .
	java telephone.Telephone < numbers.txt > output
	diff output correct_output
