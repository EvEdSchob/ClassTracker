package biz.no_ip.evedschob.classtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Evan on 11/20/2017.
 */

public class ClassListFragment extends Fragment {

    private RecyclerView mClassListRecyclerView;

    private ClassAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_activity, container, false);

        mClassListRecyclerView = (RecyclerView) view.findViewById(R.id.generic_recycler_view);

        mClassListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        ClassList classList = ClassList.get(getActivity());

        List<Class> classes = classList.getClasses();

        if (mAdapter == null) {
            mAdapter = new ClassAdapter(classes);

            mClassListRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    //*****************************
    //Inner classes
    //*****************************

    private class ClassHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private Class mClass;

        //Class Details TextViews
        private TextView mClassSubjTextView;
        private TextView mClassSecTextView;
        private TextView mClassNameTextView;

        //Class Schedule TextViews
        private TextView mClassDaysTextView;
        private TextView mClassTimeTextView;

        //Grade TextView
        private TextView mClassGradeTextView;

        public ClassHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            //Wire up the class detail widgets
            mClassSubjTextView = (TextView)
                    itemView.findViewById(R.id.list_item_class_subj_text_label);
            mClassSecTextView = (TextView)
                    itemView.findViewById(R.id.list_item_class_sec_text_view);
            mClassNameTextView = (TextView)
                    itemView.findViewById(R.id.list_item_class_name_text_view);

            //Wire up the class schedule widgets
            mClassDaysTextView = (TextView)
                    itemView.findViewById(R.id.list_item_class_days_text_view);
            mClassTimeTextView = (TextView)
                    itemView.findViewById(R.id.list_item_class_time_text_view);

            //Wire up the class grade widget
            mClassGradeTextView = (TextView)
                    itemView.findViewById(R.id.list_item_class_grade_text_view);
        }

        //Using course because "class" is a reserved word.
        public void bindClass(Class course) {
            mClass = course;
            mClassSubjTextView.setText(mClass.getSubject());
            mClassSecTextView.setText(mClass.getSection());
            mClassNameTextView.setText(mClass.getClassName());

            mClassDaysTextView.setText(mClass.getDaysAsString());

            String TimeString = getString(R.string.class_time_format,
                    mClass.getStartTimeAsString(), mClass.getEndTimeAsString());
            mClassTimeTextView.setText(TimeString);

            //REPLACE THIS!!!!!!!!!!!!!!!
            //Replace the static string resource with functionality
            //in the class to calculate the overall grade based on the
            //assignments in the AssignmentList
            mClassGradeTextView.setText(R.string.class_grade_text_label);
        }

        @Override
        public void onClick(View view) {
            //Do nothing for now
        }
    }

    private class ClassAdapter extends RecyclerView.Adapter<ClassHolder> {
        private List<Class> mClasses;

        public ClassAdapter(List<Class> classes){
            mClasses = classes;
        }

        @Override
        public ClassHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_class, parent, false);
            return new ClassHolder(view);
        }

        @Override
        public void onBindViewHolder(ClassHolder holder, int position) {
            //Using course because "class" is a reserved word.
            Class course = mClasses.get(position);

            holder.bindClass(course);
        }

        @Override
        public int getItemCount() {
            return mClasses.size();
        }
    }
}
