"""
You will start with an array of integers which describes a 2 dimensional roofline. The roof is flat, and each section is 1 unit wide. The integers describe the height of that section of roof. The challenge is to determine the volume of water that would be held by the roofline when it rains. So, for the array [1,4,2,5,1,2,3], the solution will be 5.

Assume that there will be between 1 and 1000 integers in the array, with values between 1 and 1000.
"""

def answer(heights):
    leftBaseline = rightBaseline = baseline = fill = i = 0
    while i < len(heights):
        rightBaseline = getHighest(heights[i+1:])
        if heights[i] > baseline:
            leftBaseline = heights[i]
            
        baseline = min(leftBaseline, rightBaseline)
        fill = fill + (baseline - min(heights[i],baseline))
        i = i + 1
    return fill        

def getHighest(heights):
    return max(heights) if heights else 0

if __name__ == "__main__":
    print answer([1,4,2,5,1,2,3])    
    print answer([5])    
    print answer([1,2,3,4,5,4,3,2,1])    
    print answer([5,4,3,2,1,2,3,4,5])    