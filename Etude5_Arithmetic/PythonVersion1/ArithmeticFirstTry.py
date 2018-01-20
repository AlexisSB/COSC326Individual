import sys
import fileinput
import random
import itertools
from pprint import pprint

from operator import add,mul

def recursive_evaluate(equation_as_string):
    if len(equation_as_string) ==3:
        print("Evaluation Rec : " , eval(equation_as_string)   )
        return eval(equation_as_string)
    else:
        tail = equation_as_string[3:]
        head = eval(equation_as_string[0:3])
        print(head, tail)
        equation_as_string = str(head) + tail
        #print(equation_as_string)
        return recursive_evaluate(equation_as_string)



for line in fileinput.input():

    line = line.rstrip('\n')
    numbers = line.split(" ")
    print(numbers)
    number_of_permutations = 2**(len(numbers)-1)
    #print("Perms : " , number_of_permutations)
    #print(line,type(line),end = "\n")

    line = input()
    line= line.rstrip('\n')
    line_list = line.split(" ")
    total = int(line_list[0])
    operation_order = line_list[1]
    print(total)
    #print(type(total))
    #print(operation_order)

    #ops = (add,mul)
    equation_char_array = [numbers[0]]
    for i in range(1,len(numbers)):
        #equation_char_array.append(numbers[i])
        for element in itertools.product(equation_char_array ,['+','*'],numbers[i]):
            #print("Char Array: ", list(equation_char_array))
            new_array = []
            for item in list(element):
                new_array.append(item)
            equation_char_array.append("".join(new_array))
            #print(list(element), type(element))

    #print("Final equation = ",list(equation_char_array))

    index = len(equation_char_array)-1
    evaluation_list =[]

    if operation_order is 'N':
        while index != (len(equation_char_array)-number_of_permutations-1):
            #print (equation_char_array[index])
            #print(eval(equation_char_array[index]))
            if eval(equation_char_array[index])==total:
                print(operation_order,equation_char_array[index])
            index -= 1
    elif operation_order is 'L':
        while index != (len(equation_char_array)-number_of_permutations-1):
            #print(equation_char_array[index])
            left_to_right_eval = recursive_evaluate(equation_char_array[index])
            #print("Left to right: ", left_to_right_eval)
            if left_to_right_eval==total:
               print(operation_order,equation_char_array[index])
            index -= 1

