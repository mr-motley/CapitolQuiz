package edu.uga.cs.capitolquiz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import android.view.View;

public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.QuizHolder>{
    public static final String DEBUG_TAG = "quizRecycler";

    public final Context context;

    private List<Quiz> values;

    private List<Quiz> originalValues;

    public QuizRecyclerAdapter(Context context, List<Quiz> quizList){
        this.context = context;
        this.values = quizList;
        this.originalValues = new ArrayList<Quiz>(quizList);
    }

    public void sync() {
        originalValues = new ArrayList<Quiz>(values);
    }

    public static class QuizHolder extends RecyclerView.ViewHolder{
        TextView quizDate;
        TextView quizResult;

        public QuizHolder(View itemView) {
            super(itemView);
            quizDate = itemView.findViewById(R.id.textView4);
            quizResult = itemView.findViewById(R.id.textView6);
        }
    }

    @NonNull
    @Override
    public QuizHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.quiz_card, parent, false );
        return new QuizHolder( view );
    }

    @Override
    public void onBindViewHolder (QuizHolder holder, int position){
        Quiz quiz = values.get(position);
        Log.d(DEBUG_TAG, "onBindViewHolder: " + quiz);

        holder.quizDate.setText(quiz.getQuizDate());
        holder.quizResult.setText("Score: " +quiz.getResult());
    }

    @Override
    public int getItemCount() {
        if(values != null){
            return values.size();
        } else {
            return 0;
        }
    }

}
