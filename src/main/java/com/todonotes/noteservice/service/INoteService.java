package com.todonotes.noteservice.service;

import java.util.List;

import com.todonotes.noteservice.model.Note;

public interface INoteService {

	void createNote(Note note,long id);
	void updateNote(Note note,long id);
	boolean deleteNote(long noteId,long id);
	List<Note> getAllNotes(long id);
}
