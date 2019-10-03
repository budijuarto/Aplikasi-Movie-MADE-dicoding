package com.example.tabactivity;

import java.util.ArrayList;

public interface LoadNotesCallback {
    void preExecute();
    void postExecute(ArrayList<AnimeTraining> notes);
}
