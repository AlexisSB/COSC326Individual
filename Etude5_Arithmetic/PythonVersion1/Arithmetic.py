import sys
import fileinput

"""
COSC 326 SS 2018
Etude 5 Arithmetic
Etude brief can be found at: 
http://www.cs.otago.ac.nz/cosc326/etudes2018ss/05.pdf
First time using python to attempt etude. Choose it because of string eval function.
@author Alexis Barltrop
"""

"""
Arithmetic Tree Node
Hold information about one of the nodes when doing depth first search
of the possible combinations of numbers and +/*.
"""
class ArithmeticTreeNode:
    
    #Store the specific combination of numbers and operations for this node
    node_equation = ""
    #Holds the depth of the node from the root of the tree(root depth = 0)
    depth_in_tree = 0

    #Constructor
    #@param node_equation - the combination of numbers and operations for this node
    #@depth_in_tree - depth from root.
    def __init__(self, node_equation,depth_in_tree):
        self.node_equation = node_equation
        self.depth_in_tree = depth_in_tree

    # Used to evaluate the equation left to right.
    # Recursively evaluates the first two numbers on the left.
    # @param node_equation - equation to evaluate left to right.
    # @return the left to right evaluation of the equation.
    def recursive_evaluate(self,node_equation):
        node_array = node_equation.split(" ");
        #print(node_array)
        if len(node_array) <= 3:
            #print("Evaluation Rec : ", eval(node_equation))
            return eval(node_equation)
        else:
            tail = node_array[3:]
            head = eval("".join(node_array[0:3]))
            #print(head, tail)
            node_equation = str(head) + " "+ " ".join(tail)
            #print ("Node Equation: ",node_equation)
            return self.recursive_evaluate(node_equation)

    def bracket_left_to_right_evaluate(self,node_equation):
        #print(node_equation)        
        node_array = node_equation.split(" ");
        #print(node_array)
        if len(node_array) == 1:
            return node_array[0]
        else:
            
            for index in range(1,len(node_array)):
                if node_array[index].isdigit():
                    node_array[index] = node_array[index] + ")"
                    
        #print(node_array)
        bracketed_equation = "("*(int((len(node_array)-1)/2)) + "".join(node_array)
        #print( bracketed_equation)
        #print eval(bracketed_equation)
        return eval(bracketed_equation)
        
        
        
    # Manages whether evaluation is done normally or left to right.
    # @param evaluation_option - N for normal, L for left to right.
    # @return the evaluation of the equation in the requested way.
    def evaluateNode(self,evaluation_option):
        if evaluation_option == "N":
            return eval(self.node_equation)
        elif evaluation_option =="L":
            return self.bracket_left_to_right_evaluate(self.node_equation)

    def __str__(self):
       
        return str(self.node_equation)

    def __repr__(self):
        return str(self.node_equation.replace(" ",""))
    


"""
Arithmetic
Class holds main method for the program and runs depth first search of possible combinations.
"""
class Arithmetic:

    #List used as stack for the depth first search.
    myStack =[]

    # Depth first search of combinations of numbers and operations.
    # Where multiply options exist for a solution the search will find the one with
    # the most multiplication first.
    # @param root - root of tree to search
    # @param option - evaluation option either N - normal, L for left to right
    # @param total - the target evaluation taken from the input.
    # @param numbers - a list of input numbers used to generate the tree.
    # @return an equation that results in the total with the given option or "impossible"
    # if none can be found.    
    def depth_first_search(self,root,option,total,numbers):
        self.myStack = []
        self.myStack.append(root)
        while len(self.myStack) != 0:
            #print(self.myStack)
            current_node = self.myStack.pop()
            #print("Current Node",current_node.node_equation, current_node.depth_in_tree)
            current_evaluation = int(current_node.evaluateNode(option))
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
    
    # Main method.
    # Reads in input and processes it into the different variables.
    # Calls the depth first method to find a solution.
    def main(self):
        f = sys.stdin
        line = f.readline().strip()
        while line:
            numbers = line.split(" ")
            #print(numbers)
            line = f.readline().strip()
            total = int(line.split(" ")[0])
            option = line.split(" ")[1]
            line = f.readline().strip()
            root = ArithmeticTreeNode(numbers[0],0)
            

            #print(root.node_equation)
            #root.evaluateNode(option)
            #print("Total", total, type(total))
            print(option + " " + (self.depth_first_search(root,option,total,numbers).replace(" ","")))

# Starts up the main method.
if __name__  == '__main__':
    Arithmetic().main()
    
