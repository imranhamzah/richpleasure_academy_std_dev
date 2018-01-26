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


	  var state_list = ["Selangor","Kuala Lumpur","Terengganu","Pahang"];
	  var state_data = {};
	  var country_data = {};

	  for(var i=0; i<state_list.length; i++)
	  {
		  	  var stateId = firebase.database().ref().child('states').push().key;
		  
			  state_data[stateId] = {
				  state_id:stateId,
				  state_name: state_list[i]
			  };
	  }



	  database.ref().child("states").set(state_data);

	  alert(JSON.stringify(state_data));

	</script>
</head>
<body>

<p>Directory access is forbidden.</p>

</body>
</html>
