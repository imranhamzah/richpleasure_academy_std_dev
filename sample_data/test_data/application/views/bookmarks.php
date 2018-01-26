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



		var noteId = firebase.database().ref().child('bookmarks').push().key;


			  var notes_data = {};
			  notes_data["-L3kka2-erENZxhK7PRY"]={
				  note_id:"-L3kka2-erENZxhK7PRY",
				  division_id:1
			  };


			  var subchapter_data = {};
			  subchapter_data["subchapter_id"] = {
				  subchapter_id: "subchapter_id",
				  notes: notes_data
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

			  var ask_teachers_data = {};
			  ask_teachers_data[noteId] = {
				  bookmark_id:noteId,
				  edu_years: edu_year_data
			  };



			  database.ref().child("bookmarks").set(ask_teachers_data);

	  alert(JSON.stringify(ask_teachers_data));

	</script>
</head>
<body>

<p>Directory access is forbidden.</p>

</body>
</html>
