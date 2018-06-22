package com.todonotes.noteservice.dao;

import java.util.List;

import com.todonotes.noteservice.model.Note;
import com.todonotes.userservice.model.UserModel;

public interface INoteDao {

	void addNote(UserModel userModel,Note note);
	void updateNote(Note note);
	boolean deleteNote(long noteId);
	Note getNoteById(long id);
	List<Note> getAllNotesByUserId(long id);
}
