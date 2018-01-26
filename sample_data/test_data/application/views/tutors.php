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
	  var ey2 = ["Imran","Lee","Lim","Ali"];	
	  // alert(JSON.stringify(ey));

	  var data_edu = {};
	  var data_tutor = {};
	  
	  var c=0;
	  var p=0;
	  var year_list = ["-L3hZo9HNCVNZiG6teYq","-L3hZo9HNCVNZiG6teYr"];
	  
		  
		  for(i=1; i<=ey2.length; i++)
		  {
			  
			var tutorId = firebase.database().ref().child('tutors').push().key;	  
				  
				  
				  
				  data_tutor[tutorId] = {"edu_years":
					   
					  {
						  edu_year_id:"-L3hZo9HNCVNZiG6teYq",
						  "tutors_data":{
							  tutor_id:tutorId,
							  tutor_name:ey2[i-1]
						  }
					  }
						};
							
					  
					  
					  
				  database.ref().child("tutors").set(data_tutor);
		  }
	  
	  alert(JSON.stringify(data_tutor));

	</script>
</head>
<body>

<p>Directory access is forbidden.</p>

</body>
</html>
