package com.project1.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.project1.dao.NotesDao;
import com.project1.model.Note;

public class NotesController {
    
    private NotesDao notesDao = new NotesDao();

    public void addNote(Note note){
        
        try{
            notesDao.addData("notes", 
            String.valueOf(System.currentTimeMillis()), note);
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
    }

    public List<Note> getAllNotesForUser(String userName) {

        try{
            return notesDao.getDataList("notes", userName);
        } catch (ExecutionException e){
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return List.of();
    }
}
