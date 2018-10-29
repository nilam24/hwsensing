<?php
 require 'dbconnect.php';

if($_SERVER['REQUEST_METHOD']=='GET'){
		
   $user_id=$_GET['user_id'];
   $user_pass=$_GET['user_pass'];
   $user_name=$_GET['user_name'];
   $user_contact=$_GET['user_contact'];
   
   
		$sql1="INSERT INTO `userregister_tab`(`user_id`,`user_pass`,`user_name`,`user_contact`) VALUES ('$user_id','$user_pass','$user_name','$user_contact')";

$q1="UPDATE `userregister_tab` SET `user_pass`='$user_pass' WHERE `user_id`='$user_id'";

  if(mysqli_query($db,$q1))

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
