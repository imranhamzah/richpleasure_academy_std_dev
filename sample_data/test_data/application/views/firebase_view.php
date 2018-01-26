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
	  
	  
	  
	  var ey = new Array(11);	
	  // alert(JSON.stringify(ey));

	  var data_edu = {};
	  var c=0;
	  var p=0;
	  for(i=1; i<=ey.length; i++)
	  {
		  	  var newPostKey = firebase.database().ref().child('edu_year').push().key;
			  var j = "Standard";
			  var k = "Darjah";
			  
			  p=i;
			  
			  if(i>6)
			  {
				  var j = "Form";
				  var k = "Tingkatan";
				  c++;
				  p=c;
			  }
			  
			  data_edu[newPostKey] = {
				  "edu_year_id":newPostKey,
				  "edu_year_title_my":j+" "+p,
				  "edu_year_title_en":k+" "+p
			  };
			  

			  database.ref().child("edu_years").set(data_edu);
	  }
	  alert(JSON.stringify(data_edu));

	</script>
</head>
<body>

<p>Directory access is forbidden.</p>

</body>
</html>
