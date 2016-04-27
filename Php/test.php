<?php

	$db = new mysqli('localhost', 'root', 'root', 'database');
	
	if(isset($_POST["name"]) && isset($_POST["macAddress"])&& isset($_POST["result"])&& isset($_POST["words"])&& isset($_POST["ready"]))
    {
     
		$name2 = $_POST["name"];
		$macAddress = $_POST["macAddress"]; 
		$result = $_POST["result"];
		$words= $_POST["words"];
		$ready = $_POST["ready"];
		$sql = "INSERT INTO ranking3 (name,macAddress,result,words,ready) VALUES ('$name2','$macAddress','$result','$words', '$ready') ON DUPLICATE KEY UPDATE name = '$name2', result = '$result', words = '$words', ready = '$ready'";
    }
    else
    {
      $sql = "SELECT name, macAddress, result, words, ready FROM ranking3";
	$result = $db->query($sql);

	if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo " Name: {$row["name"]}  <br> ".
         "Result: {$row["result"]}  <br> ".
		 "Words: {$row["words"]}  <br> ".
		 "Ready: {$row["ready"]}<br>".
		 "@<br>";
    }
	} else {
		echo "0 results";
	}
    }
	
	
	if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
	}
	
	


	if(!$result = $db->query($sql)){
   die('There was an error running the query [' . $db->error . ']');
	}
	
	
	$db->close();
?>