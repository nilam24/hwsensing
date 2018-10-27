<?php
 require 'dbconnect.php';

if($_SERVER['REQUEST_METHOD']=='GET'){
		
   $user_id=$_GET['user_id'];
   $device_name=$_GET['device_name'];
   $device_status=$_GET['device_status'];
   $sensor_reading=$_GET['sensor_reading'];
   
		$sql1="INSERT INTO `device_status_tab`(`user_id`,`device_name`,`device_status`,`sensor_reading`) VALUES ('$user_id','$device_name','$device_status','$sensor_reading')";

$q1="UPDATE `device_status_tab` SET `device_status`='on' WHERE `device_name`='light'";

  if(mysqli_query($db,$sql1))

   {
 
 	$response['success'] = "200";
        
        //$response="success";
       die(print_r(json_encode($response),true));
  }
   else{
        $response="failed to register try again";
        die(print_r(json_encode($response),true));
    }
    mysqli_close($db);
}
?>
