<?php
require_once 'db_function.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['username']) && isset($_POST['password'])) {
 
    // receiving the post params
    $username = $_POST['username'];
    $password = $_POST['password'];
 
    // get the user by email and password
    $user = $db->getUserByUsernameAndPassword($username, $password);
 
    if ($user != false) {
        // use is found
        $response["error"] = FALSE;
		$response["user"]["username"] = $user["user_name"];
        $response["user"]["first_name"] = $user["first_name"];
		$response["user"]["last_name"] = $user["last_name"];
		$response["user"]["password"] = $user["pass_code"];

        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Login credentials are wrong. Please try again!";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters email or password is missing!";
    echo json_encode($response);
}
?>