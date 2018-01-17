package ml.amaze.design.register;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import ml.amaze.design.R;
import ml.amaze.design.selfview.TapeView;
import ml.amaze.design.userinfo.User;

/**
 *
 * @author hxj
 * @date 2017/12/12 0012
 */

public class RegisterSecondPageFragment extends Fragment {
    View view;
    User user;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.register_fragment_secondpage, null);


        Bundle bundle = this.getArguments();
        user = (User) bundle.get("user");

        Button submit = (Button) view.findViewById(R.id.register_secondpage_btn_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectData();
                saveData();

                //若arg2=1弹出回退栈中所有的fragment,若arg2=0,弹出一个fragment
                getActivity().getFragmentManager().popBackStackImmediate(null,1);
            }


        });

        return view;
    }

    private void collectData() {
        TapeView tapeviewHeight = (TapeView) view.findViewById(R.id.register_secondpage_tapeview_height);
        TapeView tapeviewWeight = (TapeView) view.findViewById(R.id.register_secondpage_tapeview_weight);
        TapeView tapeviewWaistline = (TapeView) view.findViewById(R.id.register_secondpage_tapeview_waistline);
        float height = tapeviewHeight.getValue();
        float weight = tapeviewWeight.getValue();
        float waistline = tapeviewWaistline.getValue();
        user.setHeight(height);
        user.setWeight(weight);
        user.setWaistline(waistline);
    }

    private void saveData() {
        SharedPreferences config = this.getActivity().getSharedPreferences("config", 0);
        SharedPreferences.Editor edit = config.edit();
        String json=new Gson().toJson(user);
        edit.putString("user",json);
        edit.apply();
        Log.d("SecondPage:saveUser",json);
        Toast.makeText(getActivity(),"注册成功!",Toast.LENGTH_LONG).show();
    }









    @Override
    public void onPause() {
        System.out.println("onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        System.out.println("onStop");

        super.onStop();
    }

    @Override
    public void onDestroy() {
        System.out.println("onDestroy");

        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        System.out.println("onAttach");

        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        System.out.println("onCreate");

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        System.out.println("onActivityCreated");

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        System.out.println("onStart");

        super.onStart();
    }

    @Override
    public void onResume() {
        System.out.println("onResume");

        super.onResume();
    }
}
