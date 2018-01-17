<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Welcome extends CI_Controller {

	/**
	 * Index Page for this controller.
	 *
	 * Maps to the following URL
	 * 		http://example.com/index.php/welcome
	 *	- or -  
	 * 		http://example.com/index.php/welcome/index
	 *	- or -
	 * Since this controller is set as the default controller in 
	 * config/routes.php, it's displayed at http://example.com/
	 *
	 * So any other public methods not prefixed with an underscore will
	 * map to /index.php/welcome/<method_name>
	 * @see http://codeigniter.com/user_guide/general/urls.html
	 */
	public function index()
	{
		$obj = $this->db->select("*")
			  ->from("000subjects as a")
			  ->join("001chapters as b","b.subject_id=a.subject_id","left")
			  ->join("002sub_chapters as c","c.chapter_id=b.chapter_id","left")
			  ->join("003learning_outcome as d","d.subchapter_id=c.subchapter_id","left")
			  
			  ->get();
	    $output = [];
		$output2 = [];
		foreach($obj->result() as $row)
		{	
			if($row->subject_id != "")
			{				
				$output2["subjects"][$row->subject_id] = 
					[
						"subject_id" => $row->subject_id,
						"subject_no" => $row->subject_no,
						"subject_code" => $row->subject_code,
						"subject_title" => $row->subject_title,
						"subject_icon_url" => $row->subject_icon_url,
						"subject_remark" => $row->subject_remark
					];
			}
		}
		
		foreach($obj->result() as $row)
		{	 
			 if($row->subject_id != "")
			{
			 $output2["subjects"][$row->subject_id]["chapters"][$row->chapter_id] = 
			 [
				"chapter_id" => $row->chapter_id,
				"chapter_no" => $row->chapter_no,
				"chapter_title"=> $row->chapter_title,
				"chapter_remark" => $row->chapter_remark
			 ];
			}
			 
		}
		
		foreach($obj->result() as $row)
		{
			if($row->subject_id != "")
			{
			$output2["subjects"][$row->subject_id]["chapters"][$row->chapter_id]["subchapters"][$row->subchapter_id] = 
			[
				"subchapter_id" => $row->subchapter_id,
				"subchapter_no" => $row->subchapter_no,
				"subchapter_title" => $row->subchapter_title,
				"subchapter_remark" => $row->subchapter_remark
			];
			}
		}
		
		foreach($obj->result() as $row)
		{
			if($row->subject_id != "")
			{
			$output2
			["subjects"]
			[$row->subject_id]
			["chapters"]
			[$row->chapter_id]
			["subchapters"]
			[$row->subchapter_id]
			["outcome"]
			[$row->outcome_id]=
			[
				"outcome_id"=> $row->outcome_id,
				"outcome_no"=> $row->outcome_no,
				"outcome_level"=> $row->outcome_level,
				"outcome_description" => $row->outcome_description,
				"suggested_activity" => $row->suggested_activity,
				"outcome_remark" => $row->outcome_remark
			]
			;
			}
			
		}
		$obj_tutor = $this->db->from("tutors as t")
		->get()
		->result();
		
		foreach($obj_tutor as $row_tutor)
		{
			if($row_tutor->tutor_id != ""){
				$output2["tutors"][$row_tutor->tutor_id] = (array) $row_tutor;
				$obj_tutor_subjects = $this->db->where("tutor_id",$row_tutor->tutor_id)->get("tutor_subjects")->result();
				foreach($obj_tutor_subjects as $row_tutor_subjects)
				{
						$output2["tutors"][$row_tutor->tutor_id]["tutor_subjects"][$row_tutor_subjects->tutor_subject_id]= (array) $row_tutor_subjects ;
				}
				
			}
		}
		
		$obj_std = $this->db->from("students as s")
		->get()
		->result();
		
		foreach($obj_std as $row_std)
		{
			if($row_std->student_id != ""){
				$output2["students"][$row_std->student_id] = (array) $row_std;
				$obj_student_subjects = $this->db->where("student_id",$row_std->student_id)->get("student_subjects")->result();
				foreach($obj_student_subjects as $row_std_subjects)
				{
						$output2["students"][$row_std->student_id]["student_subjects"][$row_std_subjects->student_subject_id]= (array) $row_std_subjects ;
				}
				
			}
		}
		
		
		echo json_encode($output2);
		
		
		// echo json_encode($output);
	}

	public function tutor_list()
	{
		$obj_tutor = $this->db->get("tutors")->result();
		echo json_encode($obj_tutor);
	}
}

/* End of file welcome.php */
/* Location: ./application/controllers/welcome.php */