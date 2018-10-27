<?php
require 'dbconnect.php';
$sql="select * from userregister_tab";
$result=mysqli_query($db,$sql);
$response1=array();
while($row=mysqli_fetch_array($result))
{     	        array_push($response1,array('user_id'=>$row['user_id'],'user_pass'=>$row['user_pass'],'user_name'=>$row['user_name'],'user_contact'=>$row['user_contact'],'user_address'=>$row['user_address']));
}
die(print_r(json_encode($response1),true));
mysqli_close($db);
?>
