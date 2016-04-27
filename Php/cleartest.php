<?php
$db = new mysqli('localhost', 'root', 'root', 'database');
	
	if(isset($_POST["name"]) && isset($_POST["macAddress"])&& isset($_POST["result"]))
    {
     
	
		mysqli_query($db,'DELETE FROM ranking3;');
    }
    else if(isset($_POST["macAddress"]))
    {	
		$Mac = $_POST["macAddress"];
		mysqli_query($db,"DELETE FROM ranking3 WHERE macAddress ='$Mac';");
	}
	?>