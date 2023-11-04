package edu.uga.cs.capitolquiz;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuizPagerAdapter extends FragmentStateAdapter{
    public QuizPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle){
        super(fragmentManager,lifecycle);
    }
    @Override
    public Fragment createFragment(int position){
        return QuestionFragment.newInstance(position);
    }

    @Override
    public int getItemCount(){
        return QuestionFragment.getQuestionNum();
    }
}
