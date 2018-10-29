<?php
 require 'dbconnect.php';

if($_SERVER['REQUEST_METHOD']=='GET'){
		
   $user_id=$_GET['user_id'];
   $user_pass=$_GET['user_pass'];
   $user_name=$_GET['user_name'];
   $user_contact=$_GET['user_contact'];
   $user_city=$_GET['user_city'];
   $user_state=$_GET['user_state'];
   $user_country=$_GET['user_country'];

   
		$sql1="INSERT INTO `userregister_tab`(`user_id`,`user_pass`,`user_name`,`user_contact`,`user_city`,`user_state`,`user_country`) VALUES ('$user_id','$user_pass','$user_name','$user_contact','$user_city','$user_state','$user_country')";

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
