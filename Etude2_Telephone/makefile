telephone: Telephone.java numbers.txt correct_output
	javac -Xlint Telephone.java -d .
	java telephone.Telephone < numbers.txt

test : Telephone.java numbers.txt correct_output
	javac -Xlint Telephone.java -d .
	echo "Valid Input Test"
	java telephone.Telephone < valid_input > output
	diff output valid_output
	echo "Invalid Output Test"
	java telephone.Telephone < invalid_input > output
	diff output invalid_output
