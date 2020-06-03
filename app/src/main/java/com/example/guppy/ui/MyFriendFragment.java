package com.example.guppy.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.guppy.R;
import com.example.guppy.databinding.FragmentMyFriendBinding;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MyFriendFragment extends Fragment {
    private FragmentMyFriendBinding binding;

    private ArrayList<CheckBox> cbs = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_friend, container, false);
        View root = binding.getRoot();

        SharedPreferences sp = requireContext().getSharedPreferences("MyGuppy", MODE_PRIVATE);

        binding.yeoldaeScroll.setOnClickListener(new onFriendClicked());
        binding.ingGoldfishScroll.setOnClickListener(new onFriendClicked());
        binding.turtleYangScroll.setOnClickListener(new onFriendClicked());
        binding.etcAnimalScoll.setOnClickListener(new onFriendClicked());

        cbs.add(binding.guppyCb); cbs.add(binding.flattyCb); cbs.add(binding.mollyCb); cbs.add(binding.discussCb);
        cbs.add(binding.tettraCb); cbs.add(binding.ingGoldfishCb); cbs.add(binding.turtleYangCb); cbs.add(binding.saewooCb);
        cbs.add(binding.gajaeCb); cbs.add(binding.snailCb);

        ids.add("cb_guppy"); ids.add("cb_flatty"); ids.add("cb_molly"); ids.add("cb_discuss");
        ids.add("cb_tettra"); ids.add("cb_goldfish"); ids.add("turtle"); ids.add("cb_saewoo");
        ids.add("cb_gajae"); ids.add("cb_snail");

        for(int i = 0; i<cbs.size();i++){
            boolean check = sp.getBoolean(ids.get(i), false);
            cbs.get(i).setChecked(check);
        }

        return root;
    }

    public void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyGuppy",MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        for(int i = 0; i<cbs.size();i++){
            boolean check = cbs.get(i).isChecked();
            editor.putBoolean(ids.get(i), check);
        }
        editor.apply();
    }

    private class onFriendClicked implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.yeoldae_scroll:
                    setVisibility(binding.guppyCb);
                    setVisibility(binding.flattyCb);
                    setVisibility(binding.mollyCb);
                    setVisibility(binding.discussCb);
                    setVisibility(binding.tettraCb);
                    break;

                case R.id.ing_goldfish_scroll:
                    setVisibility(binding.ingGoldfishCb);
                    break;

                case R.id.turtle_yang_scroll:
                    setVisibility(binding.turtleYangCb);
                    break;

                case R.id.etc_animal_scoll:
                    setVisibility(binding.saewooCb);
                    setVisibility(binding.gajaeCb);
                    setVisibility(binding.snailCb);
                    break;
            }
        }

        public void setVisibility(View v){
            if(v.getVisibility()==View.GONE)
                v.setVisibility(View.VISIBLE);
            else
                v.setVisibility(View.GONE);
        }
    }
}
