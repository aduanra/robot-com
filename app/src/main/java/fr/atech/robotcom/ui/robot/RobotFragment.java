package fr.atech.robotcom.ui.robot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import fr.atech.robotcom.R;

public class RobotFragment extends Fragment {

    private RobotViewModel robotViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        robotViewModel =
                ViewModelProviders.of(this).get(RobotViewModel.class);
        View root = inflater.inflate(R.layout.fragment_infos_robot, container, false);
        final TextView textView = root.findViewById(R.id.text_infos_robot_title);
        robotViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
