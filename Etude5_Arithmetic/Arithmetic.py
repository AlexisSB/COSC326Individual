import sys
import fileinput
import random
import itertools
from pprint import pprint

from operator import add,mul

          
class ArithmeticTreeNode:

    node_equation = ""
    depth_in_tree = 0
    def __init__(self, node_equation,depth_in_tree):
        self.node_equation = node_equation
        self.depth_in_tree = depth_in_tree

    def recursive_evaluate(self,node_equation):
        node_array = node_equation.split(" ");
        print(node_array)
        if len(node_array) <= 3:
            print("Evaluation Rec : ", eval(node_equation))
            return eval(node_equation)
        else:
            tail = node_array[3:]
            head = eval("".join(node_array[0:3]))
            print(head, tail)
            node_equation = str(head) + "".join(tail)
            # print(equation_as_string)
            return self.recursive_evaluate(node_equation)

    def evaluateNode(self,evaluation_option):
        if evaluation_option == "N":
            return int(eval(self.node_equation))
        elif evaluation_option =="L":
            return int(self.recursive_evaluate(self.node_equation))

    



        
        
class Arithmetic:

    myStack =[]
           
    def depth_first_search(self,root,option,total,numbers):
        self.myStack.append(root)
        while len(self.myStack) != 0:
            current_node = self.myStack.pop()
            print("Current Node",current_node.node_equation, current_node.depth_in_tree)
            current_evaluation = current_node.evaluateNode(option)
            if current_evaluation == total and current_node.depth_in_tree == len(numbers)-1:
                return str(current_node.node_equation)
            elif current_evaluation < total and current_node.depth_in_tree<len(numbers)-1:
                #print ("Depth",current_node.depth_in_tree)
                addition_node_equation = str(current_node.node_equation + " + "
                                             + numbers[current_node.depth_in_tree+1])
                addition_node = ArithmeticTreeNode(addition_node_equation,
                                                   current_node.depth_in_tree+1)
                multiplication_node_equation = str(current_node.node_equation + " * "
                                                   + numbers[current_node.depth_in_tree+1])
                multiplication_node = ArithmeticTreeNode(multiplication_node_equation,
                                                         current_node.depth_in_tree+1)
                self.myStack.append(addition_node)
                self.myStack.append(multiplication_node)
        return "impossible"
                
                
    def main(self):
        f = sys.stdin
        line = f.readline().strip()
        while line:
            numbers = line.split(" ")
            print(numbers)
            line = f.readline().strip()
            total = int(line.split(" ")[0])
            option = line.split(" ")[1]
            line = f.readline().strip()
            root = ArithmeticTreeNode(numbers[0],0)
           

            #print(root.node_equation)
            #root.evaluateNode(option)
            print("Total", total, type(total))
            print(option + " " + (self.depth_first_search(root,option,total,numbers).replace(" ","")))
                        
if __name__  == '__main__':
    Arithmetic().main()
    
