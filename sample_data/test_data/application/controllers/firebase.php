<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Firebase extends CI_Controller {

public function index()
{
	$this->load->view("firebase_view");
}	

public function set_subject()
{
	$this->load->view("subjects");
}

public function tutors()
{
	$this->load->view("tutors");
}

public function tutor_students()
{
	$this->load->view("tutor_students");
}

public function tutor_subjects()
{
	$this->load->view("tutor_subjects");
}

public function student_subjects()
{
	$this->load->view("student_subjects");
}

public function notes()
{
	$this->load->view("notes");
}

public function ask_teachers()
{
	$this->load->view("ask_teachers");
}

public function bookmarks()
{
	$this->load->view("bookmarks");
}

public function reports()
{
	$this->load->view("reports");
}

public function countrys()
{
	$this->load->view("countrys");
}

public function states()
{
	$this->load->view("states");
}

public function districts()
{
	$this->load->view("districts");
}

}

