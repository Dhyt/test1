package ml.amaze.design.sidebar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ml.amaze.design.bean.NutritionDiseaseBean;
import ml.amaze.design.R;

/**
 *
 * @author hxj
 * @date 2017/12/22 0022
 */

public class DiseaseDetailsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        Bundle bundle = getArguments();
        NutritionDiseaseBean diseaseBean = (NutritionDiseaseBean) bundle.getSerializable("DiseaseBean");

        view=inflater.inflate(R.layout.diseasepasrt_details,null);
        TextView tvName        = (TextView) view.findViewById(R.id.disease_details_tv_name);
        TextView tvIntroduce = (TextView) view.findViewById(R.id.disease_details_tv_introduce);
        TextView tvReason = (TextView) view.findViewById(R.id.disease_details_tv_reason);
        TextView tvExpression = (TextView) view.findViewById(R.id.disease_details_tv_expression);
        TextView tvType = (TextView) view.findViewById(R.id.disease_details_tv_type);
        TextView tvSuggestion = (TextView) view.findViewById(R.id.disease_details_tv_suggestion);

        tvName.setText(diseaseBean.getName());
                tvIntroduce.setText(diseaseBean.getIntroduce());
        tvReason.setText(diseaseBean.getReason());
                tvExpression.setText(diseaseBean.getExpression());
        tvType.setText(diseaseBean.getType());
                tvSuggestion.setText(diseaseBean.getSuggestion());

        return view;
    }
}
