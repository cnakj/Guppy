package com.example.guppy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.guppy.R;
import com.example.guppy.databinding.FragmentBestWaterBinding;

public class BestWaterFragment extends Fragment {
    private FragmentBestWaterBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_best_water, container, false);
        View root = binding.getRoot();

        binding.yeoldaeScroll.setOnClickListener(new onScrollClicked());
        binding.ingGoldfishScroll.setOnClickListener(new onDetailClicked());
        binding.turtleYangScroll.setOnClickListener(new onDetailClicked());
        binding.etcAnimalScoll.setOnClickListener(new onScrollClicked());

        binding.guppyCb.setOnClickListener(new onDetailClicked());
        binding.flattyCb.setOnClickListener(new onDetailClicked());
        binding.mollyCb.setOnClickListener(new onDetailClicked());
        binding.discussCb.setOnClickListener(new onDetailClicked());
        binding.tettraCb.setOnClickListener(new onDetailClicked());
        binding.saewooCb.setOnClickListener(new onDetailClicked());
        binding.gajaeCb.setOnClickListener(new onDetailClicked());
        binding.snailCb.setOnClickListener(new onDetailClicked());

        return root;
    }

    private class onDetailClicked implements View.OnClickListener{
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.guppy_cb:
                    setVisibility(binding.guppyDetail);
                    break;
                case R.id.flatty_cb:
                    setVisibility(binding.flattyDetail);
                    break;
                case R.id.molly_cb:
                    setVisibility(binding.mollyDetail);
                    break;
                case R.id.discuss_cb:
                    setVisibility(binding.discussDetail);
                    break;
                case R.id.tettra_cb:
                    setVisibility(binding.tettraDetail);
                    break;
                case R.id.ing_goldfish_scroll:
                    setVisibility(binding.ingGoldfishDetail);
                    break;
                case R.id.turtle_yang_scroll:
                    setVisibility(binding.turtleYangDetail);
                    break;
                case R.id.saewoo_cb:
                    setVisibility(binding.saewooDetail);
                    break;
                case R.id.gajae_cb:
                    setVisibility(binding.gajaeDetail);
                    break;
                case R.id.snail_cb:
                    setVisibility(binding.snailDetail);
                    break;
            }
        }
    }
    private class onScrollClicked implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.yeoldae_scroll:
                    setVisibility(binding.guppyCb);
                    setVisibility(binding.flattyCb);
                    setVisibility(binding.mollyCb);
                    setVisibility(binding.discussCb);
                    setVisibility(binding.tettraCb);
                    if (binding.tettraCb.getVisibility()==View.GONE)
                        close(v);
                    break;

                case R.id.etc_animal_scoll:
                    setVisibility(binding.saewooCb);
                    setVisibility(binding.gajaeCb);
                    setVisibility(binding.snailCb);
                    if (binding.snailCb.getVisibility()==View.GONE)
                        close(v);
                    break;
            }
        }
    }

    public void close(View v){
        if(v.getId() == R.id.yeoldae_scroll){
            setVisibility(binding.guppyDetail, true);
            setVisibility(binding.flattyDetail, true);
            setVisibility(binding.mollyDetail, true);
            setVisibility(binding.discussDetail, true);
            setVisibility(binding.tettraDetail, true);
        }
        else{
            setVisibility(binding.saewooDetail, true);
            setVisibility(binding.gajaeDetail, true);
            setVisibility(binding.snailDetail, true);
        }
    }

    public void setVisibility(View v){
        if(v.getVisibility() == View.GONE)
            v.setVisibility(View.VISIBLE);
        else
            v.setVisibility(View.GONE);
    }

    public void setVisibility(View v, boolean off){
        if(off)
            v.setVisibility(View.GONE);
    }
}