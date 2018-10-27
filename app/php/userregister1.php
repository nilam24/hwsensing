<?php
 require 'dbconnect.php';

if($_SERVER['REQUEST_METHOD']=='POST'){
		
   $user_id=$_POST['user_id'];
   $user_pass=$_POST['user_pass'];
   $user_name=$_POST['user_name'];
   $user_contact=$_POST['user_contact'];
   $user_address=$_POST['user_address'];
   
		$mytry="INSERT INTO `userregister_tab`(`user_id`,'user_pass',`user_name`,`user_contact`,`user_address`) VALUES ('$user_id','$user_pass','$user_name','$user_contact','$user_address')";

$q1="UPDATE `admin_tab` SET `admin_country`='qwe' WHERE `doc_Id`=101";

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
