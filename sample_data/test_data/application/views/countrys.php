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


	  var country_list = ["Malaysia","Indonesia","Singapore","Brunei"];
	  var state_list = ["Selangor","Kuala Lumpur","Terengganu","Pahang"];
	  var district_list = ["Shah Alam","Subang","Klang","Petaling Jaya"];
	  var country_data = {};

	  
	  var district_data = {};
	  var district_id = "-L3l9M89XnEheDFr2Qu2";
	  district_data[district_id] = {
		  district_id:district_id,
		  district_name: "Shah Alam"
	  };
	  
	  var state_data = {};
	  var stateId = "-L3l8Vdy60xxR2yJaOEX";
	  state_data[stateId] = {
		  state_id:stateId,
		  state_name: "Selangor",
		  districts:district_data
	  };

	  var countryId = firebase.database().ref().child('countrys').push().key;
	  country_data[countryId] = {
		  country_id:countryId,
		  country_name: "Malaysia",
		  states: state_data
		  
	  };




	  database.ref().child("countrys").set(country_data);

	  alert(JSON.stringify(country_data));

	</script>
</head>
<body>

<p>Directory access is forbidden.</p>

</body>
</html>
