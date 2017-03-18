package com.kebenny.learnchinesecharacters;

/**
 * Created by kebenny on 2/28/2017.
 */
public class DatabaseModelLearningProgress {
        private Integer _id;
        private String word;
        private String last_correct_date;
        private Integer correct_count;
        private Integer wrong_count;
        private Integer correct_combo;
        private String correct_rate;

    public Integer getID(){
        return _id;
    }

    public String getWord(){
        return word;
    }
    public void setWord(String word) {this.word = word;}

    public String getLastCorrectDate(){
        return last_correct_date;
    }
    public void setLastCorrectDate(String last_correct_date) {this.last_correct_date = last_correct_date;}

    public Integer getCorrectCount(){
        return correct_count;
    }
    public void setCorrectCount(Integer correct_count) {this.correct_count = correct_count;}

    public Integer getWrongCount(){
        return wrong_count;
    }
    public void setWrongCount(Integer wrong_count) {this.wrong_count = wrong_count;}

    public Integer getCorrectCombo(){
        return correct_combo;
    }
    public void setCorrectCombo(Integer correct_count) {this.correct_combo = correct_count;}

    public String getCorrectRate(){
        if (correct_count+wrong_count!=0){
            return (correct_count * 100)/(correct_count+wrong_count) + "%";
        } else {
            return "N/A";
        }

    }
    public void setCorrectRate(String correct_rate) {this.correct_rate=correct_rate;}
}

