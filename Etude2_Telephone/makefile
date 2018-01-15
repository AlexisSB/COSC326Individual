telephone: Telephone.java
	javac -Xlint Telephone.java -d .

test : Telephone.java valid_input valid_output invalid_input invalid_output
	javac -Xlint Telephone.java -d .
	echo "Valid Input Test"
	java telephone.Telephone < valid_input > output
	diff output valid_output
	echo "Invalid Output Test"
	java telephone.Telephone < invalid_input > output
	diff output invalid_output
