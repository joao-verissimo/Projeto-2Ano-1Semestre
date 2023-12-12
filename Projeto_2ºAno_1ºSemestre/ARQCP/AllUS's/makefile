main: main.o asm.o asm1.o asm2.o asm3.o asm4.o asm5.o pcg32_random_r.o 
	gcc main.o asm.o asm1.o asm2.o asm3.o asm4.o asm5.o pcg32_random_r.o  -o main

main.o: main.c asm.h
	gcc -g -Wall -c main.c -o main.o
	
pcg32_random_r.o: pcg32_random_r.s
	gcc -g -Wall -c pcg32_random_r.s -o pcg32_random_r.o

asm.o: asm.s
	gcc -g -Wall -c asm.s -o asm.o

asm1.o: asm1.s
	gcc -g -Wall -c asm1.s -o asm1.o


asm2.o: asm2.s
	gcc -g -Wall -c asm2.s -o asm2.o


asm3.o: asm3.s
	gcc -g -Wall -c asm3.s -o asm3.o

asm4.o: asm4.s
	gcc -g -Wall -c asm4.s -o asm4.o

asm5.o: asm5.s
	gcc -g -Wall -c asm5.s -o asm5.o


clean:
	rm *.o main

run: main
	./main