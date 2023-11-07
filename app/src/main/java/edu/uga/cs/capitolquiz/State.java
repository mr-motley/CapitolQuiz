package edu.uga.cs.capitolquiz;

/***
 * State POJO Class
 */
public class State {
    private String state;
    private String capitol;
    private String secondCity;
    private String thirdCity;
    private long id;

    public State() {
        this.id = -1;
        this.state = null;
        this.capitol = null;
        this.secondCity = null;
        this.thirdCity = null;
    }

    public State(String state, String capitol, String secondCity,String thirdCity){
        this.id = -1;
        this.state = state;
        this.capitol = capitol;
        this.secondCity = secondCity;
        this.thirdCity = thirdCity;
    }

    public long getId() {return id;}
    public void setId(long id){this.id = id;}
    public String getState(){return state;}
    public void setState(String state){this.state = state;}
    public String getCapitol(){return capitol;}
    public void setCapitol(String capitol){this.capitol = capitol;}
    public String getSecondCity(){return secondCity;}
    public void setSecondCity(String secondCity){this.secondCity = secondCity;}
    public String getThirdCity(){return thirdCity;}
    public void setThirdCity(String thirdCity){this.thirdCity = thirdCity;}

    public String toString() {
        return id + ":" + state + " " + capitol + " " + secondCity + " " + thirdCity;
    }

}
