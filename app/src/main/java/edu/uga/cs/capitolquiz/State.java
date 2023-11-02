package edu.uga.cs.capitolquiz;

/***
 * State POJO Class
 */
public class State {
    private String state;
    private String capitol;
    private String secondCity;
    private String thirdCity;
    private long statehood;
    private long capitolSince;
    private long sizeRank;
    private long id;

    public State() {
        this.id = -1;
        this.state = null;
        this.capitol = null;
        this.secondCity = null;
        this.thirdCity = null;
        this.statehood = -1;
        this.capitolSince = -1;
        this.sizeRank = -1;
    }

    public State(String state, String capitol, String secondCity,String thirdCity, long statehood, long capitolSince,long sizeRank){
        this.id = -1;
        this.state = state;
        this.capitol = capitol;
        this.secondCity = secondCity;
        this.thirdCity = thirdCity;
        this.statehood = statehood;
        this.capitolSince = capitolSince;
        this.sizeRank = sizeRank;
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
    public Long getStatehood(){return statehood;}
    public void setStatehood(long statehood){this.statehood = statehood;}
    public Long getCapitolSince(){return capitolSince;}
    public void setCapitolSince(long capitolSince){this.capitolSince = capitolSince;}
    public Long getSizeRank(){return sizeRank;}
    public void setSizeRank(long sizeRank){this.sizeRank = sizeRank;}

    public String toString() {
        return id + ":" + state + " " + capitol + " " + secondCity + " " + thirdCity + " " + statehood + " " + capitolSince + " " + sizeRank;
    }

}
