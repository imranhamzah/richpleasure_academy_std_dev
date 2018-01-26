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


			  
		var noteId = firebase.database().ref().child('notes').push().key;	  
			  
			  
			  var divisions_data = {};
			  divisions_data[0] = "<p>Content 1</p>";
			  divisions_data[1] = "<p>Content 1b</p>";
			  
			  var subchapter_data = {};
			  subchapter_data["subchapter_id"] = {
				  subchapter_id: "subchapter_id",
				  divisions: divisions_data
			  };
			  
			  var chapter_data = {};
			  chapter_data["chapter_id"] = {
				  chapter_id: "chapter_id",
				  subchapters: subchapter_data
			  };
			  
			  var subject_data = {};
			  subject_data["-L3kTnDO2hLFiIAYKhpD"] = {
				  subject_id: "-L3kTnDO2hLFiIAYKhpD",
				  chapters: chapter_data
			  };
			  
			  var edu_year_data = {};
			  edu_year_data["-L3hZo9HNCVNZiG6teYr"] = {
				  edu_year_id:"-L3hZo9HNCVNZiG6teYr",
				  subjects: subject_data
			  };
			  
			  var notes_data = {};
			  notes_data[noteId] = {
				  note_id:noteId,
				  edu_years: edu_year_data
			  };
			  
			  
				  
			  database.ref().child("notes").set(notes_data);
		  
	  alert(JSON.stringify(notes_data));

	</script>
</head>
<body>

<p>Directory access is forbidden.</p>

</body>
</html>
