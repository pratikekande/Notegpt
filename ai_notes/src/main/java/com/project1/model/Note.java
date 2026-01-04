package com.project1.model;

public class Note {
    
    private String question;
    private String answer;
    private String userName;

    
    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getUserName() {
        return userName;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Note [question=" + question + ", answer=" + answer + ", userName =" + userName + "]";
    }
    
}
