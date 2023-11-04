package edu.uga.cs.capitolquiz;

public class Quiz {
    private long id;
    private long result;
    private long q1State;
    private long q2State;
    private long q3State;
    private long q4State;
    private long q5State;
    private long q6State;
    private String quizDate;

    public Quiz(){
        this.id = -1;
        this.result = -1;
        this.quizDate = null;
        this.q1State = -1;
        this.q2State = -1;
        this.q3State = -1;
        this.q4State = -1;
        this.q5State = -1;
        this.q6State = -1;
    }

    public Quiz(Long result, String quizDate){
        this.id = -1;
        this.result = result;
        this.quizDate = quizDate;
    }
    public Quiz(Long result, String quizDate, long[] states){
        this.id = -1;
        this.result = result;
        this.quizDate = quizDate;
        this.q1State = states[0];
        this.q2State = states[1];
        this.q3State = states[2];
        this.q4State = states[3];
        this.q5State = states[4];
        this.q6State = states[5];
    }

    public long getId() {return id;}
    public void setId(long id){this.id = id;}
    public long getResult() {return result;}
    public void setResult(long result) {this.result =result;}

    public String getQuizDate() {return quizDate;}
    public void setQuizDate(String quizDate) {this.quizDate = quizDate;}
    public long getQ1State() {return q1State;}
    public void setQ1State(long q1){ this.q1State = q1;}
    public long getQ2State() {return q2State;}
    public void setQ2State(long q2){ this.q2State = q2;}
    public long getQ3State() {return q3State;}
    public void setQ3State(long q3){ this.q3State = q3;}
    public long getQ4State() {return q4State;}
    public void setQ4State(long q4){ this.q4State = q4;}
    public long getQ5State() {return q5State;}
    public void setQ5State(long q5){ this.q5State = q5;}
    public long getQ6State() {return q6State;}
    public void setQ6State(long q6){ this.q6State = q6;}


    public String toString(){
        return id + ": " + result + " " + quizDate;
    }
}
