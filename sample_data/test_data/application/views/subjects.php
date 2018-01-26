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
	  var data_subject = {};
	  
	  var c=0;
	  var p=0;
	  var year_list = ["-L3hZo9HNCVNZiG6teYq","-L3hZo9HNCVNZiG6teYr"];
	  for(y=0; y<2; y++)
	  {
		  
		  var year = year_list[y];
		  
		  for(i=1; i<=ey.length; i++)
		  {
			  
			var newPostKey = firebase.database().ref().child('subjects').push().key;	  
				  
				  
				  
				  data_subject[newPostKey] ={		
						  edu_year_id: year,
						  subject_id: newPostKey,
						  subject_nam_my: ey2[i-1],
						  subject_nam_en: ey[i-1]
						};
						
				 data_edu[year] = 
				  {
					"edu_year_id":year,
					"data_subject": data_subject
				  };
					
					  
					  
					  
				  database.ref().child("subjects").set(data_edu);
		  }
		  i=0;
	  }
	  alert(JSON.stringify(data_edu));

	</script>
</head>
<body>

<p>Directory access is forbidden.</p>

</body>
</html>
