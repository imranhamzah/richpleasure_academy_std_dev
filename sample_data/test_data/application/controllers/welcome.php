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
			  // ->join("001chapters as b","b.subject_id=a.subject_id","left")
			  // ->join("002sub_chapters as c","c.chapter_id=b.chapter_id","left")
			  // ->join("003learning_outcome as d","d.subchapter_id=c.subchapter_id","left")
			  
			  ->get();
	    $output = [];
		$output2 = [];
		
		$a=-1;
		
		foreach($obj->result() as $row)
		{	
			
			if($row->subject_id != "")
			{				
				$a++;
				$output2["subjects"][$a] = (array) $row;
				$obj_chapters = $this->db->where("subject_id",$row->subject_id)->get("001chapters")->result();
				$b=-1;
				foreach($obj_chapters as $row_chapters)
				{
					$b++;
					$output2["subjects"][$a]["chapters"][$b] = (array) $row_chapters;
					$obj_subchapters = $this->db->where("chapter_id",$row_chapters->chapter_id)->get("002sub_chapters")->result();
					$c = -1;
					foreach($obj_subchapters as $row_subchapters)
					{
						$c++;
						$output2["subjects"][$a]["chapters"][$b]["subchapters"][$c] = (array) $row_subchapters;
						
						$obj_learning_outcome = $this->db->where("subchapter_id",$row_subchapters->subchapter_id)->get("003learning_outcome")->result();
						$d=-1;
						foreach($obj_learning_outcome as $row_learning_outcome)
						{
							$d++;
							$output2["subjects"][$a]["chapters"][$b]["subchapters"][$c]["outcome"][$d] = (array) $row_learning_outcome;
						}
					}
				}
			}
		}
		
		$obj_tutor = $this->db->from("tutors as t")
		->get()
		->result();
		
		$e=-1;
		foreach($obj_tutor as $row_tutor)
		{
			$e++;
			if($row_tutor->tutor_id != ""){
				$output2["tutors"][$e] = (array) $row_tutor;
				$obj_tutor_subjects = $this->db->where("tutor_id",$row_tutor->tutor_id)->get("tutor_subjects")->result();
				$f=-1;
				foreach($obj_tutor_subjects as $row_tutor_subjects)
				{
					$f++;
					$output2["tutors"][$e]["tutor_subjects"][$f]= (array) $row_tutor_subjects ;
				}
				
			}
		}
		
		$obj_std = $this->db->from("students as s")
		->get()
		->result();
		
		$g=-1;
		foreach($obj_std as $row_std)
		{
			if($row_std->student_id != ""){
				$g++;
				$output2["students"][$g] = (array) $row_std;
				$obj_student_subjects = $this->db->where("student_id",$row_std->student_id)->get("student_subjects")->result();
				$h=-1;
				foreach($obj_student_subjects as $row_std_subjects)
				{
					$h++;
					$output2["students"][$g]["student_subjects"][$h]= (array) $row_std_subjects ;
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