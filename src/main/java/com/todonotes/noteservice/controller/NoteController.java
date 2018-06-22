package com.todonotes.noteservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.todonotes.noteservice.model.Note;
import com.todonotes.noteservice.service.INoteService;

@Controller
public class NoteController {

	@Autowired
	private INoteService noteService;

	@PostMapping("/note")
	public ResponseEntity<?> createNote(@RequestBody Note note, @RequestHeader("userId") long id) {
		noteService.createNote(note, id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/note")
	public ResponseEntity<?> updateNote(@RequestBody Note note,@RequestHeader("userId") long id) {
		noteService.updateNote(note,id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/note/{noteId}")
	public ResponseEntity<?> deleteNote(@PathVariable("noteId") long noteId,@RequestHeader("userId") long id) {
		if (noteService.deleteNote(noteId,id))
			return new ResponseEntity<>("Note not found", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/note")
	public ResponseEntity<?> getAllNotes(@RequestHeader("userId") long id) {
		List<Note> notes = noteService.getAllNotes(id);
		return new ResponseEntity<>(notes,HttpStatus.OK);
	}
}
