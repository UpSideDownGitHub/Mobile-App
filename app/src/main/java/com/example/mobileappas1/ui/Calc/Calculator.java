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

/*
 * this calss will handle managing all the aspects of the calculator and showing the answers
 */
public class Calculator
{
    // Private varaibles
    private FragmentCalculatorBinding binding;
    private CalcAdapter recylerAdapter;
    private String currentText;
    private DataHandler dataHandler;
    private List<InputTypes> instructions = new ArrayList<InputTypes>();

    /*
     * Constructor for insitilisation
     */
    public Calculator(FragmentCalculatorBinding view, CalcAdapter calcAdapter)
    {
        recylerAdapter = calcAdapter;
        binding = view;
        dataHandler = new DataHandler();
    }

    /*
     * used to add instruction to the current list of instructions
     */
    public void addInstruction(InputTypes type) {
        // if equals then calculate the answer of the given isntruction
        if (type == InputTypes.EQUALS)
        {
            // work out the given sum
            boolean done = dataHandler.calculateInstructions(instructions);
            // if worked then show the answer otherwise show error
            if (done)
                showAnswer(dataHandler.currentAnswer);
            else
                showError("Syntax Error");
            return;
        }
        // if del then remove the last instruction
        else if (type == InputTypes.DEL)
        {
            // remove the last instruction
            dataHandler.removeLast(instructions);
            updateText();
            return;
        }
        // if Ac then remove all instructions
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

    /*
     * Show the given error in the sumtext area
     */
    public void showError(String error)
    {
        // Show the given error
        TextView text = binding.sumText;
        text.setText(error);
        dataHandler.removeAll(instructions);
    }
    /*
     * show the answer in the sumText area as well as adding it to the recylcer view
     */
    public void showAnswer(double value)
    {
        // show the answer
        TextView text = binding.sumText;
        text.setText(R.string.calc_default);
        dataHandler.removeAll(instructions);

        // add the answer to the recyler view
        recylerAdapter.addValue("= " + Double.toString(value));
    }

    /*
     * update the text that is being shown to show new answer to new instruction being added
     */
    public void updateText()
    {
        // update text being show to show the most current text
        String converted = dataHandler.convertToString(instructions);
        TextView text = binding.sumText;
        text.setText(converted);
    }
}
