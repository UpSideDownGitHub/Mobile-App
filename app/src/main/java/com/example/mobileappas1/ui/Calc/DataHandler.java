package com.example.mobileappas1.ui.Calc;

import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/*
 * this class will calcualte the answer to given instructions for the calculator
 */
public class DataHandler
{
    // Public variables
    public double previousAnswer;
    public double currentAnswer;

    /*
     * this will take the data from the calculator and calculate the actual answer
     * to the sum
     */
    public boolean calculateInstructions(List<InputTypes> instructions)
    {
        // set the previous answer to be the current answer
        previousAnswer = currentAnswer;
        // syntax/semantic error detection
        for (int i = 0; i < instructions.size(); i++)
        {
            InputTypes previous = null;
            InputTypes current = null;

            // if not the first item get the item before
            if (i != 0)
                previous = instructions.get(i - 1);
            // get the current item
            current = instructions.get(i);

            // if current is first and not a number
            if (i == 0 || !number(previous)) {
                // if cant be the first item then error
                if (current == InputTypes.MULTIPLY || current == InputTypes.DIVIDE || current == InputTypes.POINT ||
                        current == InputTypes.PLUS )// || current == InputTypes.MINUS) need to add for minus values
                    return false; // syntax error from user
            }
            // if there is a number previous
            if (number(previous)) {
                // if ANS is current then error
                if (current == InputTypes.ANS)
                    return false; // syntax error from user
            }
            // if current is a point
            if (current == InputTypes.POINT)
            {
                // if i is not the last item
                if ( i + 1 < instructions.size())
                {
                    // there is not a number next then error
                    if (!number(instructions.get(i + 1)))
                        return false; // syntax error from the user
                }
            }
        }
        // if this far then should all be in the correct fashion IE number operator number oprtator ...
        List<String> finalCalculation = new ArrayList<String>();
        String part = "";
        boolean containsPoint = false;

        // convert from a list to actual things
        for (int i = 0; i < instructions.size(); i++)
        {
            InputTypes previous = null;
            InputTypes current = null;

            // if not the first item get the item before
            if (i != 0)
                previous = instructions.get(i - 1);
            // get the current item
            current = instructions.get(i);

            // if current instruction is ANS then add the previous answer to the final calc
            if (current == InputTypes.ANS)
            {
                finalCalculation.add(Double.toString(previousAnswer));
                continue;
            }

            // if current is point then add a point
            if (current == InputTypes.POINT)
            {
                // if there is not point then return error
                if (containsPoint)
                    return false;
                part += convertTypeToString(current);
                containsPoint = true;
                continue;
            }

            // if the current item is a number
            if (number(current))
            {
                // add it to the list of instructions
                part += convertTypeToString(current);

                // if not the last item
                if (i + 1 < instructions.size())
                {
                    // if there is apoint then add it as well
                    InputTypes next =  instructions.get(i + 1);
                    if (!number(next) && next != InputTypes.POINT)
                    {
                        finalCalculation.add(part);
                        part = "";
                        containsPoint = false;
                    }
                }
                else
                {
                    // add the number
                    finalCalculation.add(part);
                    part = "";
                    containsPoint = false;
                }
                continue;
            }
            // add item to the final calc
            finalCalculation.add(convertTypeToString(current));
        }

        List<String> tempFinalCalculation = new ArrayList<String>();

        // now need to go through the list of items and determine if there are any pre identifiers
        // on any of the values like -100 - 10 becuase curenltly it does not know of -100
        for (int i = 0; i < finalCalculation.size(); i++)
        {
            String previous = "NOTHING";
            String next = "NOTHING";
            // get previous
            if (i != 0)
                previous = finalCalculation.get(i - 1);
            // get next
            if (i + 1 < finalCalculation.size())
                next = finalCalculation.get(i + 1);
            String current = finalCalculation.get(i);

            // if there is a -
            if (current.compareTo("-") == 0)
            {
                // if nothing after error
                if (next.compareTo("NOTHING") == 0)
                    return false; // minus at the end of the line is not allowed
                // if number before and after then normal so just add the -
                if (number(previous) && number(next))
                {
                    tempFinalCalculation.add(finalCalculation.get(i)); // add the value as normal
                    continue; // this is a normal minus and is not directly effecting a value
                }
                // if more than 2 minus then error
                if (previous.compareTo("-") == 0 && next.compareTo("-") == 0)
                    return false; // not allowed more than 2 minuses as breaks shit
                // if not number previous but nuber after set number to be negative
                if (!number(previous) && number(next))
                {
                    // combine the current and next to form 1 value then skip the next value
                    tempFinalCalculation.add(finalCalculation.get(i) + finalCalculation.get(i + 1));
                    i++; // skip next value
                    continue;
                }
                // if nothing before and number next set number to be negative
                if (previous.compareTo("NOTHING") == 0 && number(next))
                {
                    // combine the two as a negative number was given at the start
                    tempFinalCalculation.add(finalCalculation.get(i) + finalCalculation.get(i + 1));
                    i++; // skip next value
                    continue;
                }
            }
            // add the value to the new list as we know is not a negative and so is correct
            tempFinalCalculation.add(finalCalculation.get(i));
        }
        // set the final calcualtion to the fixed value
        finalCalculation = tempFinalCalculation;

        // at this point should have a list which consists of all of the numbers and operators and all
        // i have to do is loop though it and calculate the output
        double value = 0;
        for (int i = 0; i < finalCalculation.size(); i++)
        {
            // get the first value
            if (i == 0)
            {
                value = Double.parseDouble(finalCalculation.get(i));
                continue;
            }
            try {
                // if there are more values
                if (i + 1 < finalCalculation.size()) {
                    // find the operator to be used and apply it
                    if (finalCalculation.get(i).compareTo("+") == 0) {
                        value = value + Double.parseDouble(finalCalculation.get(i + 1));
                    } else if (finalCalculation.get(i).compareTo("-") == 0) {
                        value = value - Double.parseDouble(finalCalculation.get(i + 1));
                    } else if (finalCalculation.get(i).compareTo("x") == 0) {
                        value = value * Double.parseDouble(finalCalculation.get(i + 1));
                    } else if (finalCalculation.get(i).compareTo("÷") == 0) {
                        value = value / Double.parseDouble(finalCalculation.get(i + 1));
                    }
                    i++;
                }
            }catch (Exception e)
            {
                return false;
            }
        }

        // calculation should be finished
        currentAnswer = value;

        // the function has been calculated correctly
        return true;
    }

    /*
     * returns true if the given type is a number
     */
    public boolean number(InputTypes type)
    {
        // check if the type is a number
        if (type == null)
            return false;
        if (type == InputTypes.ONE ||
                type == InputTypes.TWO ||
                type == InputTypes.THREE ||
                type == InputTypes.FOUR ||
                type == InputTypes.FIVE ||
                type == InputTypes.SIX ||
                type == InputTypes.SEVEN ||
                type == InputTypes.EIGHT ||
                type == InputTypes.NINE ||
                type == InputTypes.ZERO ||
                type == InputTypes.ANS)
            return true;
        return false;
    }
    /*
     * returns true if the given string is a number
     */
    public boolean number(String type)
    {
        // if a number return true
        if (type == null)
            return false;
        try {
            Double.parseDouble(type);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * removed the last item from the list
     */
    public List<InputTypes> removeLast(List<InputTypes> instructions)
    {
        // if there are not items in the list return
        if (instructions.size() <= 0)
            return instructions;
        // remove the last instruction and return new list 
        instructions.remove(instructions.size() - 1);
        return instructions;
    }

    /*
     * remove all instructions from the list
     */
    public List<InputTypes> removeAll(List<InputTypes> instructions)
    {
        // clear list and return it
        instructions.clear();
        return instructions;
    }

    /*
     * convert the type list to a string list
     */
    public String convertToString(List<InputTypes> instructions)
    {
        String tempString = "";
        for (int i = 0; i < instructions.size(); i++)
        {
            // for each instruction add it to the string of instructions
            tempString += convertTypeToString(instructions.get(i));
        }
        return tempString;
    }

    /*
     * convert given type to a string
     */
    public String convertTypeToString(InputTypes type)
    {
        // convert type to correct string
        switch (type)
        {
            case ZERO:
                return "0";
            case ONE:
                return "1";
            case TWO:
                return "2";
            case THREE:
                return "3";
            case FOUR:
                return "4";
            case FIVE:
                return "5";
            case SIX:
                return "6";
            case SEVEN:
                return "7";
            case EIGHT:
                return "8";
            case NINE:
                return "9";
            case POINT:
                return ".";
            case PLUS:
                return "+";
            case MINUS:
                return "-";
            case MULTIPLY:
                return "x";
            case DIVIDE:
                return "÷";
            case ROOT:
                return "√";
            case ANS:
                return "ANS";
                /*
            THESE ONES SHOULD NOT NEED TO BE HERE AS THEY ARE HANDLED SEPERATLY

            case DEL:
                return "DEL";
            case AC:
                return "AC";
            case EQUALS:
                return "EQUALS";

                */
            default:
                return "FAIL";
        }
    }
}

