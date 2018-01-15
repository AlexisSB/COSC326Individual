import sys
import fileinput
import random
import itertools
from pprint import pprint

from operator import add,mul
          
class ArithmeticTreeNode:

    node_equation = ""
    def __init__(self, node_equation):
        self.node_equation = node_equation

    def recursive_evaluate(self,node_equation):
        if len(node_equation) <= 3:
            print("Evaluation Rec : ", eval(node_equation))
            return eval(node_equation)
        else:
            tail = node_equation[3:]
            head = eval(node_equation[0:3])
            print(head, tail)
            node_equation = str(head) + tail
            # print(equation_as_string)
            return self.recursive_evaluate(node_equation)

    def evaluateNode(self,evaluation_option):
        if evaluation_option == "N":
            return eval(self.node_equation)
        elif evaluation_option =="L":
            return self.recursive_evaluate(self.node_equation)



        
        
class Arithmetic:

    myStack =[]

    def main(self):
        f = sys.stdin
        line = f.readline().strip()
        while line:
            numbers = line.split(" ")
            print(numbers)
            line = f.readline().strip()
            total = line.split(" ")[0]
            option = line.split(" ")[1]
            line = f.readline().strip()
            root = ArithmeticTreeNode(numbers[0])
            self.myStack.append(root)
            #print(root.node_equation)
            #root.evaluateNode(option)
            while len(self.myStack) != 0:
                current_node = self.myStack.pop()
                if current_node.evaluateNode(option) < total:
                    addition_node



if __name__  == '__main__':
    Arithmetic.main()
