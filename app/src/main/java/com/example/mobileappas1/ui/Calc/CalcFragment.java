package com.example.mobileappas1.ui.Calc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappas1.databinding.FragmentCalculatorBinding;

import java.util.ArrayList;


public class CalcFragment extends Fragment {

    public Calculator calculator;
    RecyclerView recyclerView;
    CalcAdapter adapter;

    // Using ArrayList to store images data
    ArrayList courseName = new ArrayList<>();

    private FragmentCalculatorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalcViewModel calcViewModel =
                new ViewModelProvider(this).get(CalcViewModel.class);

        binding = FragmentCalculatorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // CALCULATOR CODE
        // buttons listeners
        // Line 1
        binding.zeroButton.setOnClickListener( view -> btn_zero(getView()));
        binding.pointButton.setOnClickListener( view -> btn_point(getView()));
        binding.rootButton.setOnClickListener( view -> btn_root(getView()));
        binding.ansButton.setOnClickListener( view -> btn_ans(getView()));
        binding.equalsButton.setOnClickListener( view -> btn_equals(getView()));
        // Line 2
        binding.oneButton.setOnClickListener( view -> btn_one(getView()));
        binding.twoButton.setOnClickListener( view -> btn_two(getView()));
        binding.threeButton.setOnClickListener( view -> btn_three(getView()));
        binding.plusButton.setOnClickListener( view -> btn_plus(getView()));
        binding.minusButton.setOnClickListener( view -> btn_minus(getView()));
        // Line 3
        binding.fourButton.setOnClickListener( view -> btn_four(getView()));
        binding.fiveButton.setOnClickListener( view -> btn_five(getView()));
        binding.sixButton.setOnClickListener( view -> btn_six(getView()));
        binding.multiplyButton.setOnClickListener( view -> btn_multiply(getView()));
        binding.divideButton.setOnClickListener( view -> btn_divide(getView()));
        // Line 4
        binding.sevenButton.setOnClickListener( view -> btn_seven(getView()));
        binding.eightButton.setOnClickListener( view -> btn_eight(getView()));
        binding.nineButton.setOnClickListener( view -> btn_nine(getView()));
        binding.delButton.setOnClickListener( view -> btn_del(getView()));
        binding.acButton.setOnClickListener( view -> btn_ac(getView()));


        // Getting reference of recyclerView
        RecyclerView recyclerView = (RecyclerView) binding.recyclerview;

        // Setting the layout as linear
        // layout for vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        // Sending reference and data to Adapter
        adapter = new CalcAdapter(this.getContext(), courseName);

        // Setting Adapter to RecyclerView
        recyclerView.setAdapter(adapter);

        calculator = new Calculator(binding, adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // LINE 1
    public void btn_zero(View view) { calculator.addInstruction(InputTypes.ZERO); }
    public void btn_point(View view) { calculator.addInstruction(InputTypes.POINT); }
    public void btn_root(View view) { /*calculator.addInstruction(InputTypes.ROOT); */}
    public void btn_ans(View view) { calculator.addInstruction(InputTypes.ANS); }
    public void btn_equals(View view) { calculator.addInstruction(InputTypes.EQUALS); }

    // LINE 2
    public void btn_one(View view) { calculator.addInstruction(InputTypes.ONE); }
    public void btn_two(View view) { calculator.addInstruction(InputTypes.TWO); }
    public void btn_three(View view) { calculator.addInstruction(InputTypes.THREE); }
    public void btn_plus(View view) { calculator.addInstruction(InputTypes.PLUS); }
    public void btn_minus(View view) { calculator.addInstruction(InputTypes.MINUS); }

    // LINE 3
    public void btn_four(View view) { calculator.addInstruction(InputTypes.FOUR); }
    public void btn_five(View view) { calculator.addInstruction(InputTypes.FIVE); }
    public void btn_six(View view) { calculator.addInstruction(InputTypes.SIX); }
    public void btn_multiply(View view) { calculator.addInstruction(InputTypes.MULTIPLY); }
    public void btn_divide(View view) { calculator.addInstruction(InputTypes.DIVIDE); }

    // LINE 4
    public void btn_seven(View view) { calculator.addInstruction(InputTypes.SEVEN); }
    public void btn_eight(View view) { calculator.addInstruction(InputTypes.EIGHT); }
    public void btn_nine(View view) { calculator.addInstruction(InputTypes.NINE); }
    public void btn_del(View view) { calculator.addInstruction(InputTypes.DEL); }
    public void btn_ac(View view) { calculator.addInstruction(InputTypes.AC); }
}