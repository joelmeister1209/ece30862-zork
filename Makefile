
all: compiler

compiler:
	javac *.java 
clean:
	rm -rf *.class *~ 

.PHONY: all group compiler clean



