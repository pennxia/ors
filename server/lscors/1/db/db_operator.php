<?php

function set_common_query($query, $object){
	// $mysql = new SaeMysql();
	// $sql = "select (count, latest_call) from common_query where query = '".$query."'";
	// $data = $mysql->getData($sql);
	// $latest_call = date('Y-m-d H:i:s', time());
	// if($data == NULL){
	// 	$sql = "insert into common_query values('"
	// 		.$query."','"
	// 		.$object."',1,'"
	// 		.$latest_call."')");
	// 	$mysql->runSql($sql);
	// }else{
	// 	$count = $data["count"];
	// 	$count ++;
	// 	$sql = "update common_query set count = "
	// 		.$count." , latest_call = "
	// 		.$latest_call." where query = '"
	// 		.$query."'";
	// 	$mysql->runSql($sql);
	// }
	// $mysql->closeDb();
}

function get_common_query($query){
	// $mysql = new SaeMysql();
	// $sql = "select (count, result_obj) from common_query where query = '".$query."'";
	// $data = $mysql->getData($sql);
	// if($data == NULL) return NULL;
	// else return $data['result_obj'];
	
	return NULL;
}

?>