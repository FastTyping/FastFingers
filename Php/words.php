<?php
	$db = new mysqli('localhost', 'root', 'root', 'database');
	if(isset($_POST["words2"])){
	mysqli_query($db,'DELETE FROM words;');
	$words=  $_POST["words2"];
	$sql = "INSERT INTO words (words2) VALUES ('$words')";
	
	}else {
	
	 $sql = "SELECT words2 FROM words";
	$result = $db->query($sql);
	if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo  $row["words2"];
    }
	} else {
		//echo "0 results";
	}
    }
	
	
	if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
	}
	
	


	if(!$result = $db->query($sql)){
   die('There was an error running the query [' . $db->error . ']');
	}
	
	
	$db->close();


 // $words = "the be of end a to in he have it that for day I with as not on she at by this we you do but from or which one would all will there say who make when can more if no man out other so what time up go about than into could state only new year some take come these know see use get like then first any work now may such give over think most even find day also after way many must look before great back through long where much should well people down own just because good each those feel seem how high too place little world very still nation hand old life tell write become here show house both between need mean call develop under last right move thing general school never same another begin while number part turn real leave might want point form off child few small since against ask late home interest large person end open public follow during present without again gold govern around possible head consider word program problem however lead system set order eye plan run keep face fact group play stand increase early course change help line";
 // echo $words;
   ?>