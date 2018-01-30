OBJ = JoinedWordsApp.java Node.java

joined_writing: $(OBJ)
	javac $(OBJ) -d .

run100: $(OBJ) 100words.txt
	make joined_writing
	java joined_writing.JoinedWordsApp item oyer < 100words.txt

run10000: $(OBJ) 10000wordssorted.txt
	make joined_writing
	java joined_writing.JoinedWordsApp stylus explain < 10000wordssorted.txt

run100000: $(OBJ) 100000words.txt
	make joined_writing
	java joined_writing.JoinedWordsApp slab randomish  < 100000words.txt

test: $(OBJ) 100000words.txt
	make joined_writing
	java joined_writing.JoinedWordsApp suffix read  < 100000words.txt
