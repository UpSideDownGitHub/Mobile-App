package com.example.mobileappas1.ui.Calc;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappas1.R;
import com.example.mobileappas1.databinding.FragmentCalculatorBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator
{
    // PUBLIC

    // PRIVATE
    private FragmentCalculatorBinding binding;
    private CalcAdapter recylerAdapter;
    private String currentText;

    private DataHandler dataHandler;

    // instruction list
    private List<InputTypes> instructions = new ArrayList<InputTypes>();

    public Calculator(FragmentCalculatorBinding view, CalcAdapter calcAdapter)
    {
        recylerAdapter = calcAdapter;
        binding = view;
        dataHandler = new DataHandler();
    }

    public void addInstruction(InputTypes type) {
        // some instructions should not be added

        if (type == InputTypes.EQUALS)
        {
            // work out the given sum
            boolean done = dataHandler.calculateInstructions(instructions);
            if (done)
                showAnswer(dataHandler.currentAnswer);
            else
                showError("Syntax Error");
            return;
        }
        else if (type == InputTypes.DEL)
        {
            // remove the last instruction
            dataHandler.removeLast(instructions);
            updateText();
            return;
        }
        else if (type == InputTypes.AC)
        {
            // remove all instructions
            dataHandler.removeAll(instructions);
            updateText();
            return;
        }

        // add the instruction to the list of instructions
        instructions.add(type);

        // update the text to show the new value
        updateText();
    }

    public void showError(String error)
    {
        TextView text = binding.sumText;
        text.setText(error);
        dataHandler.removeAll(instructions);
    }
    public void showAnswer(double value)
    {
        TextView text = binding.sumText;
        // CHANGE THIS LINE TO SET IT TO THE DEFAULT VALUE
        text.setText(R.string.calc_default);
        dataHandler.removeAll(instructions);

        // need to add this answer to the list of answers then show the next the default text of the thing
        recylerAdapter.addValue("= " + Double.toString(value));
    }

    public void updateText()
    {
        // this function is used to show the text on screen
        String converted = dataHandler.convertToString(instructions);

        TextView text = binding.sumText;
        text.setText(converted);
    }
}
