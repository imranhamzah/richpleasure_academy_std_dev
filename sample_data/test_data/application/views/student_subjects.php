<html>
<head>
	<title>DataGenerator</title>
	<script src="https://www.gstatic.com/firebasejs/4.9.0/firebase.js"></script>
	<script>
	  // Initialize Firebase
	  // TODO: Replace with your project's customized code snippet
	  var config = {
		apiKey: "AIzaSyBy6peGxkyYlDestgBZkXb3pIqEbnXM53w",
		databaseURL: "https://student-app-90e13.firebaseio.com/"
	  };
	  firebase.initializeApp(config);
	  
	  // Get a key for a new Post.
	  
	  var database = firebase.database();
	  
	  
	  var year = [];
	  var ey = ["Additional Mathematics","Mathematics","Physics","Chemistry","Biology","English"];	
	  var ey2 = ["Matematik Tambahan","Matematik","Fizik","Kimia","Biologi","Bahasa Inggeris"];	
	  // alert(JSON.stringify(ey));

	  var data_edu = {};
	  var data_tutor_students = {};
	  
	  var c=0;
	  var p=0;


			  
		var tutorId = firebase.database().ref().child('student_subjects').push().key;	  
			  
			  
			 var students_data = {};
			 var subjects_data = {};
			 var edu_year_data = {};
			 var tutor_data = {};
			  
 			  
			  
			  subjects_data["-L3kTnDO2hLFiIAYKhpD"] = {
				  subject_id:"-L3kTnDO2hLFiIAYKhpD"
			  };
			  
			  students_data["LBnU75DJuUSeKSQ9dij9pokStD83"] = {
				  student_id: "LBnU75DJuUSeKSQ9dij9pokStD83",
				  subjects: subjects_data
			  }; 
			  
			  edu_year_data["-L3hZo9HNCVNZiG6teYr"] = {
				  edu_year_id:"-L3hZo9HNCVNZiG6teYr",
				  students: students_data
			  };
		  
			  
			  
			 
			  
				  
			  database.ref().child("student_subjects").set(edu_year_data);
		  
	  alert(JSON.stringify(edu_year_data));

	</script>
</head>
<body>

<p>Directory access is forbidden.</p>

</body>
</html>
