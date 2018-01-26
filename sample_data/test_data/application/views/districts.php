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


	  var district_list = ["Shah Alam","Subang","Klang","Petaling Jaya"];
	  var district_data = {};
	  var country_data = {};

	  for(var i=0; i<district_list.length; i++)
	  {
		  	  var district_id = firebase.database().ref().child('districts').push().key;
		  
			  district_data[district_id] = {
				  district_id:district_id,
				  district_name: district_list[i]
			  };
	  }



	  database.ref().child("districts").set(district_data);

	  alert(JSON.stringify(district_data));

	</script>
</head>
<body>

<p>Directory access is forbidden.</p>

</body>
</html>
