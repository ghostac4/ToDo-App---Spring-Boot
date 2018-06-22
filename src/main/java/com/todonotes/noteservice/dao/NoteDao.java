package com.todonotes.noteservice.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.todonotes.noteservice.model.Note;
import com.todonotes.userservice.model.UserModel;

@Repository
public class NoteDao implements INoteDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addNote(UserModel userModel,Note note) {
		Session session = sessionFactory.getCurrentSession();
		List<Note> notes = userModel.getNotes();
		notes.add(note);
		userModel.setNotes(notes);
		session.saveOrUpdate(userModel);
		session.save(note);
	}
	
	@Override
	public void updateNote(Note note) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(note);
	}
	
	@Override
	public boolean deleteNote(long noteId) {
		Session session = sessionFactory.getCurrentSession();
		Note note = session.get(Note.class,noteId);
		if(note == null)
			return false;
		session.delete(note);
		return true;
	}
	
	@Override
	public Note getNoteById(long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Note.class, id);
	}
	
	@Override
	public List<Note> getAllNotesByUserId(long id){
		Session session = sessionFactory.getCurrentSession();
		UserModel userModel = session.get(UserModel.class, id);
		return userModel.getNotes();
	}
}
