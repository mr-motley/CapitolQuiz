package edu.uga.cs.capitolquiz;

/**
 * This is a POJO class that represents a Quiz object.
 */
public class Quiz {
    private long id;
    private int result;
    private int q1State;
    private int q2State;
    private int q3State;
    private int q4State;
    private int q5State;
    private int q6State;
    private String quizDate;
    private int currentQ;

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
        this.currentQ = -1;
    }

    public Quiz(int result, String quizDate){
        this.id = -1;
        this.result = result;
        this.quizDate = quizDate;
    }
    public Quiz(int result, String quizDate, int[] states, int currentQ){
        this.id = -1;
        this.result = result;
        this.quizDate = quizDate;
        this.q1State = states[0];
        this.q2State = states[1];
        this.q3State = states[2];
        this.q4State = states[3];
        this.q5State = states[4];
        this.q6State = states[5];
        this.currentQ = currentQ;
    }

    public long getId() {return id;}
    public void setId(long id){this.id = id;}
    public int getResult() {return result;}
    public void setResult(int result) {this.result =result;}

    public String getQuizDate() {return quizDate;}
    public void setQuizDate(String quizDate) {this.quizDate = quizDate;}
    public int getQ1State() {return q1State;}
    public void setQ1State(int q1){ this.q1State = q1;}
    public int getQ2State() {return q2State;}
    public void setQ2State(int q2){ this.q2State = q2;}
    public int getQ3State() {return q3State;}
    public void setQ3State(int q3){ this.q3State = q3;}
    public int getQ4State() {return q4State;}
    public void setQ4State(int q4){ this.q4State = q4;}
    public int getQ5State() {return q5State;}
    public void setQ5State(int q5){ this.q5State = q5;}
    public int getQ6State() {return q6State;}
    public void setQ6State(int q6){ this.q6State = q6;}
    public int getCurrentQ() {return currentQ;}
    public void setCurrentQ(int currentQ) {this.currentQ =currentQ;}


    public String toString(){
        return id + "| Score: " + result + " Date: " + quizDate;
    }
}
