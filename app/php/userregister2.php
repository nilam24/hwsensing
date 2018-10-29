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
   
		$mytry="INSERT INTO `userregister_tab`(`user_id`,`user_pass`,`user_name`,`user_contact`,`user_city`,`user_state`,`user_country`) VALUES ('$user_id',$user_pass`,'$user_name','$user_contact','$user_city','$user_state','$user_country')";

$q1="UPDATE `userregister_tab` SET `user_pass`='qwe' WHERE `user_id`=101";

  if(mysqli_query($db,$mytry))

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
