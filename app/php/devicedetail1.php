<?php
require 'dbconnect.php';
$sql="select * from device_status_tab";
$result=mysqli_query($db,$sql);
$response1=array();
while($row=mysqli_fetch_array($result))
{     	        array_push($response1,array('user_id'=>$row['user_id'],'device_name'=>$row['device_name'],'device_status'=>$row['device_status'],'sensor_reading'=>$row['sensor_reading']));
}
die(print_r(json_encode($response1),true));
mysqli_close($db);
?>
