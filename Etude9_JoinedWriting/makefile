OBJ = BreadthFirst.java Node.java
RUN = BreadthFirst

joined_writing: $(OBJ)
	javac $(OBJ) -d .

run100: $(OBJ) 100words.txt
	make joined_writing
	java joined_writing.$(RUN) item oyer < 100words.txt

run10000: $(OBJ) 10000wordssorted.txt
	make joined_writing
	java joined_writing.$(RUN) stylus explain < 10000wordssorted.txt

run100000: $(OBJ) 100000words.txt
	make joined_writing
	java joined_writing.$(RUN) slab randomish  < 100000words.txt

stress : $(OBJ) 100000words.txt
	make joined_writing
	java joined_writing.$(RUN) aaaa xxzz  < 100000words.txt

test: $(OBJ) 100000words.txt
	make joined_writing
	java joined_writing.$(RUN) suffix read  < 100000words.txt
