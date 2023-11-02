package edu.uga.cs.capitolquiz;

public class Quiz {
    private long id;
    private long result;
    private String quizDate;

    public Quiz(){
        this.id = -1;
        this.result = -1;
        this.quizDate = null;
    }

    public Quiz(Long result, String quizDate){
        this.id = -1;
        this.result = result;
        this.quizDate = quizDate;
    }

    public long getId() {return id;}
    public void setId(long id){this.id = id;}
    public long getResult() {return result;}
    public void setResult(long result) {this.result =result;}

    public String getQuizDate() {return quizDate;}
    public void setQuizDate(String quizDate) {this.quizDate = quizDate;}

    public String toString(){
        return id + ": " + result + " " + quizDate;
    }
}
