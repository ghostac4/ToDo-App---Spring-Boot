package com.todonotes.noteservice.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todonotes.noteservice.dao.INoteDao;
import com.todonotes.noteservice.exceptions.NoteNotFoundException;
import com.todonotes.noteservice.model.Note;
import com.todonotes.userservice.dao.IUserDao;
import com.todonotes.userservice.model.UserModel;

@Service
@Transactional
public class NoteService implements INoteService{

	@Autowired
	private INoteDao noteDao;
	@Autowired
	private IUserDao userDao;
	
	@Override
	public void createNote(Note note,long id) {
		UserModel userModel = userDao.getUserById(id);
		note.setCreatedDate(new Date(System.currentTimeMillis()));
		note.setLastUpdatedDate(new Date(System.currentTimeMillis()));
		noteDao.addNote(userModel, note);
	}
	
	@Override
	public void updateNote(Note note,long id) {
		note = noteDao.getNoteById(note.getNoteId());
		if(note == null) 
			throw new NoteNotFoundException("Note Not Found");
		if(!noteDao.getAllNotesByUserId(id).contains(note))
			throw new NoteNotFoundException("Note does not belong to given user");
		note.setLastUpdatedDate(new Date(System.currentTimeMillis()));
		noteDao.updateNote(note);
	}
	
	@Override
	public boolean deleteNote(long noteId,long id) {
		Note note = noteDao.getNoteById(noteId);
		if(note == null)
			throw new NoteNotFoundException("Note Not Found");
		if(!noteDao.getAllNotesByUserId(id).contains(note))
			throw new NoteNotFoundException("Note does not belong to given user");
		return noteDao.deleteNote(noteId);
	}
	
	@Override
	public List<Note> getAllNotes(long id){
		return noteDao.getAllNotesByUserId(id);
	}
}
